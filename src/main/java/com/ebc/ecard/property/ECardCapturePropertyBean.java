package com.ebc.ecard.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("ecard.services.util")
@Getter
@Setter
public class ECardCapturePropertyBean {
    private String url;

    public String getUrlWithProtocol() {
        return "https://" + url;
    }
}
