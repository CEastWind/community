package com.liudl.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by TwistedFate on 2020/1/20 14:47
 */
@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish() {

        return "publish";
    }
}
