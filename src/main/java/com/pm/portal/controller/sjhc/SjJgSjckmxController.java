package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgSjckmxClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/sjJgSjckmx")
public class SjJgSjckmxController {
    private final SjJgSjckmxClient sjJgSjckmxClient;

    @Autowired
    public SjJgSjckmxController(SjJgSjckmxClient sjJgSjckmxClient) {
        this.sjJgSjckmxClient = sjJgSjckmxClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String data = sjJgSjckmxClient.list(vars);
            if ("sjJgSjckmxFail".equals(data)) {
                result.put("message", "fail");
            } else {
                result.put("message", "success");
                result.put("data", data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insert(HttpServletResponse response, String ckid, Integer wpbh, Integer sccj, BigDecimal cksl, Integer wpdw, String wpph, Date scrq, Date sxrq, Integer kwbm, String quality, BigDecimal currentInventory) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("ckid", ckid);
            vars.put("wpbh", wpbh);
            vars.put("sccj", sccj);
            vars.put("cksl", cksl);
            vars.put("wpdw", wpdw);
            vars.put("wpph", wpph);
            vars.put("scrq", scrq);
            vars.put("sxrq", sxrq);
            vars.put("kwbm", kwbm);
            vars.put("quality", quality);
            vars.put("currentInventory", currentInventory);
            result.put("message", sjJgSjckmxClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, String id, Integer wpbh, Integer sccj, BigDecimal cksl, Integer wpdw, String wpph, Date scrq, Date sxrq, Integer kwbm, String quality, BigDecimal currentInventory) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            vars.put("wpbh", wpbh);
            vars.put("sccj", sccj);
            vars.put("cksl", cksl);
            vars.put("wpdw", wpdw);
            vars.put("wpph", wpph);
            vars.put("scrq", scrq);
            vars.put("sxrq", sxrq);
            vars.put("kwbm", kwbm);
            vars.put("quality", quality);
            vars.put("currentInventory", currentInventory);
            result.put("message", sjJgSjckmxClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(HttpServletResponse response, String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            result.put("message", sjJgSjckmxClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgSjckmxRemoveFail");
        }
        return result;
    }
}
