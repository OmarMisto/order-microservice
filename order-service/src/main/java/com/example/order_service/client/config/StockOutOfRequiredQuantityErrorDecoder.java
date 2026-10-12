package com.example.order_service.client.config;

import com.example.order_service.service.exception.StockOutOfRequiredQuantityException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.http.HttpStatus;

public class StockOutOfRequiredQuantityErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder =new ErrorDecoder.Default();
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status()== 400){
            throw  new StockOutOfRequiredQuantityException("stock out of the required quantity");
        }
        return errorDecoder.decode(methodKey,response) ;
    }
}
