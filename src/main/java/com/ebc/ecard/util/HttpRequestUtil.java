package com.ebc.ecard.util;

import com.ebc.ecard.application.dto.RequestDtoInterface;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * 외부 Server로 Http Request를 전송하기 위한 Util Class
 *
 * @param <Payload>
 * @param <ReturnType>
 */
public class HttpRequestUtil<Payload extends RequestDtoInterface, ReturnType> {
    final String CHARACTER_SET = "UTF-8";

    String url;
    Payload payload;
    HttpMethod method;
    HttpHeaders headers;

    protected MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;
    protected FormHttpMessageConverter formHttpMessageConverter;

    public HttpRequestUtil(
            String url,
            Payload payload,
            HttpMethod method,
            HttpHeaders headers,
            List<MediaType> contentType
    ) {
        this.url = url;
        this.payload = payload;
        this.method = method;
        if (headers == null) {
            headers = new HttpHeaders();
        }

        headers.setContentType(contentType.get(0));
        this.headers = headers;

        this.mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        this.mappingJackson2HttpMessageConverter.setDefaultCharset(Charset.forName(CHARACTER_SET));
        this.mappingJackson2HttpMessageConverter.setSupportedMediaTypes(contentType);

        this.formHttpMessageConverter = new FormHttpMessageConverter();
        this.formHttpMessageConverter.setCharset(Charset.forName(CHARACTER_SET));
        this.formHttpMessageConverter.setSupportedMediaTypes(contentType);
    }

    public HttpRequestUtil(
            String url,
            Payload payload,
            HttpMethod method
    ) {
        this.url = url;
        this.payload = payload;
        this.method = method;
        this.headers = new HttpHeaders();

        this.mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        this.mappingJackson2HttpMessageConverter.setDefaultCharset(Charset.forName(CHARACTER_SET));

        this.formHttpMessageConverter = new FormHttpMessageConverter();
        this.formHttpMessageConverter.setCharset(Charset.forName(CHARACTER_SET));
    }

    public ResponseEntity<ReturnType> execute() {

        HttpEntity<Map> httpEntity = new HttpEntity<>(
                this.payload.convertToMap(),
                this.headers
        );

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        restTemplate.getMessageConverters().add(formHttpMessageConverter);

        return restTemplate.exchange(this.url, HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<ReturnType>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
    }
}
