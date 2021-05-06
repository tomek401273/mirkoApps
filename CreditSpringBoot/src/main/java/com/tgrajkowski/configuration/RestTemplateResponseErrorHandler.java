package com.tgrajkowski.configuration;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;

@Component
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
       if (httpResponse.getStatusCode().series() == CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == NOT_FOUND) {
                throw new ResponseStatusException(NOT_FOUND, "something go w wrong with request");
            }
        }
    }
}



