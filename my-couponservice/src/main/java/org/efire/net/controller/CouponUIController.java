package org.efire.net.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CouponUIController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
