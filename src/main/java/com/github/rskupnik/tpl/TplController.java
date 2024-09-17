package com.github.rskupnik.tpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TplController {

    private final TplService tplService;

    @Autowired
    public TplController(TplService tplService) {
        this.tplService = tplService;
    }

    @PostMapping("test")
    public void test() {
        tplService.test();
    }
}
