package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgDdmxClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/sjJgDdmx")
public class SjJgDdmxController {
    private final SjJgDdmxClient sjJgDdmxClient;

    @Autowired
    public SjJgDdmxController(SjJgDdmxClient sjJgDdmxClient) {
        this.sjJgDdmxClient = sjJgDdmxClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String data = sjJgDdmxClient.list(vars);
            if ("sjJgDdmxFail".equals(data)) {
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

    @RequestMapping(value = "/list_b", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object listByJhids(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String data = sjJgDdmxClient.listByJhids(vars);
            if ("sjJgDdmxFailB".equals(data)) {
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
    public Object insert(HttpServletResponse response, String jhid, Integer wpbh, String wpgg, Integer wpdw, BigDecimal jhsl, BigDecimal fzsl, Integer fzdw, BigDecimal hsl) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("jhid", jhid);
            vars.put("wpbh", wpbh);
            vars.put("wpgg", wpgg);
            vars.put("wpdw", wpdw);
            vars.put("jhsl", jhsl);
            vars.put("spsl", 0);
            vars.put("yrkl", 0);
            vars.put("sywcg", jhsl);
            vars.put("fzsl", fzsl);
            vars.put("fzdw", fzdw);
            vars.put("hsl", hsl);
            result.put("message", sjJgDdmxClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, String id, Integer wpbh, String wpgg, Integer wpdw, BigDecimal jhsl, BigDecimal spsl, BigDecimal yrkl, BigDecimal sywcg, BigDecimal fzsl, Integer fzdw, BigDecimal hsl) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            vars.put("wpbh", wpbh);
            vars.put("wpgg", wpgg);
            vars.put("wpdw", wpdw);
            vars.put("jhsl", jhsl);
            vars.put("spsl", spsl);
            vars.put("yrkl", yrkl);
            vars.put("sywcg", sywcg);
            vars.put("fzsl", fzsl);
            vars.put("fzdw", fzdw);
            vars.put("hsl", hsl);
            result.put("message", sjJgDdmxClient.update(vars));
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
            result.put("message", sjJgDdmxClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgDdmxRemoveFail");
        }
        return result;
    }
}
