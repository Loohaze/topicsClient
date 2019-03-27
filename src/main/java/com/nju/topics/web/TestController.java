package com.nju.topics.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("")
    public String test(){
        return "Test";
    }
}
