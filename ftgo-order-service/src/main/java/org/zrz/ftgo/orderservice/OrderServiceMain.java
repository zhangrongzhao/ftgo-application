package org.zrz.ftgo.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.zrz.*","io.eventuate.common","io.eventuate.tram.*"})
public class OrderServiceMain {

    public static void main(String[] args){
        SpringApplication.run(OrderServiceMain.class,args);
    }
}
