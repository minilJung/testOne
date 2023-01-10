package com.ebc.ecard.application.file;

import com.ebc.ecard.application.file.dto.FileDto;
import com.ebc.ecard.application.file.dto.S3FileUploadDto;

import java.util.List;

public interface FileService {
	List<FileDto> saveFiles(List<S3FileUploadDto> files) throws Exception;
}
