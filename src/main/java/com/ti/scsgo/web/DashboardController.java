package com.ti.scsgo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Jonas
 */
@Controller
public class DashboardController {

    @RequestMapping("/")
    public String greeting() {
        return "charts";
    }
}
