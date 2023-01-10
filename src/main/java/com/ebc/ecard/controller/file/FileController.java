package com.ebc.ecard.controller.file;

import com.ebc.ecard.application.file.FileService;
import com.ebc.ecard.controller.CorsDisabledController;
import com.ebc.ecard.application.file.dto.FileDto;
import com.ebc.ecard.application.file.dto.S3FileUploadDto;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileController implements CorsDisabledController {

	@Resource
    private FileService fileService;

	@Resource
	private XeCommon common;
	
	/**
	 * @title	- 파일 업로드
	 * @desc	- 미디어 파일을 업로드합니다.
	 * @author	- Jinyeon
	 * @date	- 2021.12.20
	 * @param	- ECardBean
	 * @return	- 결과 코드
	 */
	@PostMapping("")
	public ReturnMessage saveFile(
		HttpServletRequest httpServletRequest,
		MultipartRequest multipartRequest
	) {
		try {

			log.info("---------------------------------------------");
			log.info("POST files upload");
			log.info("---------------------------------------------");

			//List<FileDto> files = fileHandler.saveFiles(multipartRequest);
			List<FileDto> files = fileService.saveFiles(getFilesFromRequest(multipartRequest));

			return new ReturnMessage(files);
		} catch(Exception e) {
			return new ReturnMessage("9999", "이미지 업로드 API 에러", e);
		}
	}

	protected List<S3FileUploadDto> getFilesFromRequest(MultipartRequest multipartRequest) {

		List<S3FileUploadDto> files = new ArrayList<>();
		Iterator<String> ite = multipartRequest.getFileNames();
		while (ite.hasNext()) {
			String fileKey = ite.next();

			List<MultipartFile> multipartFiles = multipartRequest.getFiles(fileKey);
			if (multipartFiles == null) {
				continue;
			}

			multipartFiles.forEach(file -> {
				files.add(S3FileUploadDto.fromMultipartFile(file, common));
			});
		}

		return files;
	}
}
