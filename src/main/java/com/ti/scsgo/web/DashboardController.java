package com.ti.scsgo.web;

import com.ti.scsgo.service.FileUploadService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Jonas
 */
@Controller
public class DashboardController {

    Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    protected FileUploadService fs;

    @GetMapping("/")
    public String home() throws IOException {
        fs.clearTmp();
        return "index";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(
            MultipartFile file) throws IOException {
        System.out.println("file = " + file.getOriginalFilename());
        fs.saveStream(file);
        return "SUCCESS";
    }

    @GetMapping("/report")
    public String report(Model model) {
        return "report";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
