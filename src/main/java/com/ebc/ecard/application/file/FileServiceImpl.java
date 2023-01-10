package com.ebc.ecard.application.file;

import com.ebc.ecard.mapper.FileMapper;
import com.ebc.ecard.application.file.dto.FileDto;
import com.ebc.ecard.application.file.dto.S3FileUploadDto;
import com.ebc.ecard.util.FileBean;
import com.ebc.ecard.util.ImageUtil;
import com.ebc.ecard.util.S3;
import com.ebc.ecard.util.XeCommon;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

	@Resource
	XeCommon common;

	@Resource
	S3 s3;

	@Resource
    FileMapper mapper;

	public List<FileDto> saveFiles(List<S3FileUploadDto> files) throws Exception {
		List<FileBean> makeFileList = new ArrayList<>();
		for (S3FileUploadDto fileDto : files) {

			String fileExtension = fileDto.getFileExtension().replaceAll("\\.", "");

			// 이미지 돌아가는 문제 해결
			int orientation = ImageUtil.getOrientation(fileDto.getFile().getInputStream());

			BufferedImage image = ImageIO.read(fileDto.getFile().getInputStream());
			if (orientation > 0) {
				image = ImageUtil.rotateImageForMobile(fileDto.getFile().getInputStream(), orientation);
			}
			InputStream resizedFile  = ImageUtil.resizeImage(fileExtension, image, 600);

			String filePath = s3.s32Upload(resizedFile , resizedFile.available(), fileDto.getNewFileName());
			makeFileList.add(
				new FileBean(
					common.getUuid(false),
					fileDto.getNewFileName(),
					filePath,
					fileDto.getOriginalFileName(),
					fileDto.getFileExtension()
				)
			);
		}
		if (makeFileList.size() > 0) {
			mapper.saveFiles(makeFileList);
		}

		List<FileDto> uploadedFiles = new ArrayList<>();
		makeFileList.forEach(bean -> {
			uploadedFiles.add(FileDto.fromBean(bean, common));
		});

		return uploadedFiles;
	}

}
