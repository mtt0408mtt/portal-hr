package com.pm.portal.service.sjhc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SjJgRkhwClient {
    private final RestTemplate restTpl;

    @Autowired
    public SjJgRkhwClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }
}
