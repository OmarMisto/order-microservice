package com.example.order_service.client.config;

import com.example.order_service.service.exception.ProductNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class StockProductNotFoundErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaulteErrorDecoder=new ErrorDecoder.Default();
    @Override
    public Exception decode(String methodKey, Response response) {
        if(response.status()==404){
            throw new ProductNotFoundException("stock product not found");
        }
        return defaulteErrorDecoder.decode(methodKey,response);
    }
}
