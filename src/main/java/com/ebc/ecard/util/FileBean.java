package com.ebc.ecard.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileBean extends BaseBean {
	private String fileId;
	private String fileName;
	private String filePath;
	private String fileUploadUrl;
	private String oriFileName;
	private String fileExt;
	private int order;
	private int download;
	private String lastUpdatedAt;
	
	public FileBean(String fileId, String fileName, String filePath, String oriFileName, String fileExt) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.filePath = filePath;
		this.oriFileName = oriFileName;
		this.fileExt = fileExt;
	}
}
