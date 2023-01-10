package com.ebc.ecard.application.file.dto;

import com.ebc.ecard.util.XeCommon;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class S3FileUploadDto {

    protected MultipartFile file;
    protected String newFileName;
    protected String originalFileName;
    protected String fileExtension;

    public static S3FileUploadDto fromMultipartFile(MultipartFile multipartFile, XeCommon common) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.equals("")) {
            return null;
        }

        String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = common.getRandomString(false, 36) + fileExt;

        return new S3FileUploadDto(multipartFile, newFileName, originalFilename, fileExt);
    }

    public S3FileUploadDto(MultipartFile file, String newFileName, String originalFileName, String fileExtension) {
        this.file = file;
        this.newFileName = newFileName;
        this.originalFileName = originalFileName;
        this.fileExtension = fileExtension;
    }
}
