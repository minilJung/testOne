package com.ebc.ecard.application.ecard.dto;

import com.ebc.ecard.application.dto.RequestDtoInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ECardProfileCaptureRequestDto implements RequestDtoInterface<String, Object> {
    protected String requestId;
    protected String url;
    protected String selector;
    protected int deviceScale;
    protected int viewportWidth;
    protected int viewportHeight;

    public ECardProfileCaptureRequestDto(
            String requestId,
            String url
    ) {
        this.requestId = requestId;
        this.url = url;

        this.selector = "div#ecard-view-image";
        this.deviceScale = 2;
        this.viewportWidth = 380;
        this.viewportHeight = 800;
    }

    @Override
    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("requestId", requestId);
        map.put("url", url);
        map.put("selector", selector);
        map.put("deviceScale", deviceScale);
        map.put("viewportWidth", viewportWidth);
        map.put("viewportHeight", viewportHeight);

        return map;
    }
}
