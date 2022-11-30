package com.xtt.xitian.controller;

import com.xtt.xitian.proxy.consumeMsgProxy;
import com.xtt.xitian.proxy.produceMsgProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class KafkaDemoController {
    @PostMapping(value = "produce")
    public void produce(HttpServletRequest request, HttpServletResponse response, @RequestParam String param1, @RequestParam String param2)  {
        new produceMsgProxy().execute(request, response, param1, param2);
    }

    @GetMapping(value = "consume")
    public void consume(HttpServletRequest request, HttpServletResponse response)  {
        new consumeMsgProxy().execute(request, response);
    }


}
