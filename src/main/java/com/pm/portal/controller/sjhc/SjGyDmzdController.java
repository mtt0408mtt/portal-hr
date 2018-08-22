package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjGyDmzdClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/sjGyDmzd")
public class SjGyDmzdController {
    private final SjGyDmzdClient sjGyDmzdClient;

    @Autowired
    public SjGyDmzdController(SjGyDmzdClient sjGyDmzdClient) {
        this.sjGyDmzdClient = sjGyDmzdClient;
    }

    @RequestMapping(value = "/list_a", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object categoryList(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            String data = sjGyDmzdClient.categoryList(vars);
            if ("sjGyDmzdFailA".equals(data)) {
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
    public Object itemList(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            String data = sjGyDmzdClient.itemList(vars);
            if ("sjGyDmzdFailB".equals(data)) {
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

    @RequestMapping(value = "/list_c", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object itemList2(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("zfpb", "0");
            String data = sjGyDmzdClient.itemList2(vars);
            if ("sjGyDmzdFailC".equals(data)) {
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
    public Object insert(HttpServletResponse response, String jtbh, Integer dmlb, Integer lbxh, String dmmc, String pym, String wbm, String smr, String dmjb, String bz) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", jtbh);
            vars.put("dmlb", dmlb);
            vars.put("lbxh", lbxh);
            vars.put("dmmc", dmmc);
            vars.put("pym", pym);
            vars.put("wbm", wbm);
            vars.put("smr", smr);
            vars.put("zfpb", "0");
            vars.put("dmjb", dmjb);
            vars.put("bz", bz);
            result.put("message", sjGyDmzdClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, String jtbh, Integer dmlb, Integer lbxh, String dmmc, String pym, String wbm, String smr, String dmjb, String bz) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", jtbh);
            vars.put("dmlb", dmlb);
            vars.put("lbxh", lbxh);
            vars.put("dmmc", dmmc);
            vars.put("pym", pym);
            vars.put("wbm", wbm);
            vars.put("smr", smr);
            vars.put("dmjb", dmjb);
            vars.put("bz", bz);
            result.put("message", sjGyDmzdClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object disable(HttpServletResponse response, String jtbh, Integer dmlb, Integer lbxh, String zfpb) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", jtbh);
            vars.put("zfpb", "1".equals(zfpb) ? "0" : "1");
            vars.put("dmlb", dmlb);
            vars.put("lbxh", lbxh);
            result.put("message", sjGyDmzdClient.disable(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
}
