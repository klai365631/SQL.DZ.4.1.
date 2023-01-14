package com.example.sqldz41.controller;


import com.example.sqldz41.service.InfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class infoController {

    private final InfoService infoService;

    public infoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping
                public void testParallelStream(){
            infoService.testParallelStream();
    }
}
