package com.ebc.ecard.application.ecard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardPreviewImageDto {
    protected String ecardId;

    protected String previewFileId;

    public ECardPreviewImageDto(String ecardId, String previewFileId) {
        this.ecardId = ecardId;
        this.previewFileId = previewFileId;
    }
}
