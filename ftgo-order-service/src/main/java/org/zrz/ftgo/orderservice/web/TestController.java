package org.zrz.ftgo.orderservice.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(path = "/")
    public boolean Test(){
        return true;
    }
}
