package com.ebc.ecard.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("ecard.services.coocon")
@Getter
@Setter
public class CooconPropertyBean {

    private String url;

    public String getUrlWithProtocol() {
        return "https://" + url;
    }
}
