package com.ebc.ecard.application.ecard.handler;

import com.ebc.ecard.application.ecard.dto.ECardProfileCaptureResponseDto;
import com.ebc.ecard.application.ecard.exception.PreviewImageUpdateFailedException;
import com.ebc.ecard.domain.ecard.ECardCaptureScheduleBean;
import com.ebc.ecard.application.ecard.dto.ECardPreviewImageDto;
import com.ebc.ecard.application.ecard.dto.ECardProfileCaptureRequestDto;
import com.ebc.ecard.mapper.ECardMapper;
import com.ebc.ecard.mapper.FileMapper;
import com.ebc.ecard.property.ECardCapturePropertyBean;
import com.ebc.ecard.property.ECardUiPropertyBean;
import com.ebc.ecard.util.FileBean;
import com.ebc.ecard.util.ImageUtil;
import com.ebc.ecard.util.S3;
import com.ebc.ecard.util.XeCommon;
import com.ebc.ecard.mapper.ECardCaptureScheduleMapper;
import com.ebc.ecard.util.request.ECardHttpRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ECardCaptureImageHandler {

	@Resource
	XeCommon common;

	@Resource
	S3 s3;

	@Resource
	private ECardCapturePropertyBean captureProperty;

	@Resource
	private ECardUiPropertyBean uiProperty;

	@Resource
	private ECardCaptureScheduleMapper captureScheduleMapper;

	@Resource
	private ECardMapper eCardMapper;

	@Resource
	private FileMapper fileMapper;

	@Resource(name = "applicationThreadPoolExecutor")
	private Executor applicationTaskExecutor;

	@Resource(name = "schedulerThreadPoolExecutor")
	private Executor schedulerThreadPoolExecutor;

	public byte[] getECardCaptureImageWithDownloadAt(String ecardId) {
		try {
        	return captureImageUsingUtil(ecardId, "Y");
		} catch(Exception e) {
			return new byte[0];
		}
	}

	@Async("applicationThreadPoolExecutor")
	public CompletableFuture<Void> updateECardCaptureImage(String ecardId) {

		return CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				try {
					FileBean file = getImageAndUploadToS3(ecardId, "N");
                    eCardMapper.updatePreviewImage(
                        new ECardPreviewImageDto(ecardId, file.getFileId())
                    );

					captureScheduleMapper.cancelRemainingSchedules(ecardId);
				} catch(Exception e) {
					log.info("Failed to update preview image", e);
				}
			}
		}, applicationTaskExecutor);
	}

	@Async("schedulerThreadPoolExecutor")
	public CompletableFuture<Void> updateECardCaptureImageUsingScheduleBean(ECardCaptureScheduleBean bean) {

		return CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {

				try {
					FileBean file = getImageAndUploadToS3(bean.getEcardId(), "N");
                    eCardMapper.updatePreviewImage(
                        new ECardPreviewImageDto(bean.getEcardId(), file.getFileId())
                    );

                    bean.success();
                    captureScheduleMapper.updateSchedule(bean);

				} catch(Exception e) {
					bean.setRetryCount(bean.getRetryCount() + 1);

					captureScheduleMapper.updateSchedule(bean);
					log.info("Failed to upadte preview image", e);
				}
			}
		}, schedulerThreadPoolExecutor);

	}

	protected byte[] captureImageUsingUtil(String ecardId, String includeDownloadAtYn) {
		String targetUrl = uiProperty.getUrlWithProtocol() + "/ecard-export/" + Base64Utils.encodeToUrlSafeString(ecardId.getBytes()) + "?includeDownloadAtYn=" + includeDownloadAtYn;
		ECardProfileCaptureRequestDto requestDto = new ECardProfileCaptureRequestDto(
			common.getUuid(false),
			targetUrl
		);

		ECardProfileCaptureResponseDto response = ECardHttpRequest.Builder.build(captureProperty.getUrlWithProtocol())
			.post("/util/capture")
			.payload(requestDto)
			.executeAndGetBodyAs(ECardProfileCaptureResponseDto.class);

		if (response == null || StringUtils.isEmpty((String) response.getResult().get("data"))) {
			throw new PreviewImageUpdateFailedException("capture image response is null");
		}
		String data = (String) response.getResult().get("data");
		return Base64Utils.decodeFromString(data);
	}

	protected FileBean getImageAndUploadToS3(String ecardId, String includeDownloadAtYn) throws Exception {

		ByteArrayInputStream inputStream = new ByteArrayInputStream(captureImageUsingUtil(ecardId, includeDownloadAtYn));
		InputStream resizedFile  = ImageUtil.resizeImage("png", ImageIO.read(inputStream), 1200);

		Date date = new Date();
		String fileName = ecardId + "_preview_image_"+ date.getTime() + ".png";
		String uploadedFilePath = s3.s32Upload(resizedFile, resizedFile.available(), fileName);

		return new FileBean(
			common.getUuid(false),
			fileName,
			uploadedFilePath,
			fileName,
			"png"
		);
	}
}