package com.james_storr.company_search.client;

import feign.codec.ErrorDecoder;

import com.james_storr.company_search.exception.GenericFeignException;
import com.james_storr.company_search.exception.NotFoundException;

import feign.Response;

public class ClientErrorDecoder implements ErrorDecoder { 

    @Override
    public Exception decode(String methodKey, Response response) {
        if(response.status() == 404) {
            return new NotFoundException("Resource not found");
        }
        return new GenericFeignException("Feign Client Error: " + response.status());
    }
}
