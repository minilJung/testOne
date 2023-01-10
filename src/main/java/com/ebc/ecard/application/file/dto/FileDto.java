package com.ebc.ecard.application.file.dto;

import com.ebc.ecard.util.FileBean;
import com.ebc.ecard.util.XeCommon;

import lombok.Getter;

@Getter
public class FileDto {
    private String fileId;
    private String fileName;
    private String filePath;
    private String filePreSignedUrl;
    private String fileUploadUrl;
    private String oriFileName;
    private String lastUpdatedAt;

    public static FileDto fromBean(FileBean bean, XeCommon common) {
        return new FileDto(
            bean.getFileId(),
            bean.getFileName(),
            bean.getFilePath(),
            common.getS3FilePath(bean.getFileName()),
            bean.getFileUploadUrl(),
            bean.getOriFileName(),
            bean.getLastUpdatedAt()
        );
    }

    public FileDto(
        String fileId,
        String fileName,
        String filePath,
        String filePreSignedUrl,
        String fileUploadUrl,
        String oriFileName,
        String lastUpdatedAt
    ) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.filePreSignedUrl = filePreSignedUrl;
        this.fileUploadUrl = fileUploadUrl;
        this.oriFileName = oriFileName;
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
