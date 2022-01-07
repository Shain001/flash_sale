package com.java.com.java.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="payment")
public interface PaymentConsumerFeign {
    @RequestMapping(value = "/processPayment", method = RequestMethod.POST)
    String consumePayment(@RequestParam("userId") String userId, @RequestParam("saleId") String saleId);
}
