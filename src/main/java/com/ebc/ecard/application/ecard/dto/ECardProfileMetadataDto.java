package com.ebc.ecard.application.ecard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ECardProfileMetadataDto {
    protected String ecardId;
    protected String name;
    protected String position;
    protected String profileUrl;
    protected String ecardChangedYn;

    protected String previewFileId;
    protected String previewFileName;
    protected String previewFileExt;
    protected String previewImageUrl;
}
