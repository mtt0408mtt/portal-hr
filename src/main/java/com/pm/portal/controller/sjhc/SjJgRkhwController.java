package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgRkhwClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sjJgRkhw")
public class SjJgRkhwController {
    private final SjJgRkhwClient sjJgRkhwClient;

    @Autowired
    public SjJgRkhwController(SjJgRkhwClient sjJgRkhwClient) {
        this.sjJgRkhwClient = sjJgRkhwClient;
    }
}
