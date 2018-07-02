package com.liwp.reco.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by liwp on 2017/8/5.
 */
@Controller
public class FtlController {
    @RequestMapping("/api/qa_show")
    public String show(ModelMap model,
                       @RequestParam("sel") String sel,
                       @RequestParam("value") String value) {
        model.addAttribute("value", value);
        model.addAttribute("sel", sel);
        return "qa_show";
    }
}
