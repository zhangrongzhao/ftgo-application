package org.zrz.ftgo.orderservice.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(path = "/hello",method= RequestMethod.GET)
    public String Test(){
        return "hello world";
    }
}
