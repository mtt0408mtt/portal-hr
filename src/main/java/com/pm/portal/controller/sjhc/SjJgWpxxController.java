package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgWpxxClient;
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
@RequestMapping(value = "/sjJgWpxx")
public class SjJgWpxxController {
    private final SjJgWpxxClient sjJgWpxxClient;

    @Autowired
    public SjJgWpxxController(SjJgWpxxClient sjJgWpxxClient) {
        this.sjJgWpxxClient = sjJgWpxxClient;
    }

    @RequestMapping(value = "/fuzzy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object fuzzyList(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("ckbh", 1);
            String data = sjJgWpxxClient.fuzzyList(vars);
            if ("sjJgWpxxFail".equals(data)) {
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

    @RequestMapping(value = "/fuzzy_b", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object fuzzyList2(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("ckbh", 1);
            String data = sjJgWpxxClient.fuzzyList2(vars);
            if ("sjJgWpxxFailB".equals(data)) {
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
    public Object insert(HttpServletResponse response, Integer cwfl, Integer wpfl, String wpmc, Integer tsbs, String wpbm, String pym, String wbm, String wpgg, Integer wpdw, Integer zxdw, String zxgg, String zhxs, String zgcc, String zdcc, String dcyl, Integer llyc, String gzwp, String ycxwp, String satConditions) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("ckbh", 1);
            vars.put("cwfl", cwfl);
            vars.put("wpfl", wpfl);
            vars.put("wpmc", wpmc);
            vars.put("tsbs", tsbs);
            vars.put("wpbm", wpbm);
            vars.put("pym", pym);
            vars.put("wbm", wbm);
            vars.put("wpgg", wpgg);
            vars.put("wpdw", wpdw);
            vars.put("zxdw", zxdw);
            vars.put("zxgg", zxgg);
            vars.put("zhxs", zhxs);
            vars.put("zgcc", zgcc);
            vars.put("zdcc", zdcc);
            vars.put("dcyl", dcyl);
            vars.put("llyc", llyc);
            vars.put("gzwp", gzwp);
            vars.put("ycxwp", ycxwp);
            vars.put("satConditions", satConditions);
            result.put("message", sjJgWpxxClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, Integer id, Integer cwfl, String wpmc, Integer tsbs, String wpbm, String pym, String wbm, String wpgg, Integer wpdw, Integer zxdw, String zxgg, String zhxs, String zgcc, String zdcc, String dcyl, Integer llyc, String gzwp, String ycxwp, String satConditions) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            vars.put("cwfl", cwfl);
            vars.put("wpmc", wpmc);
            vars.put("tsbs", tsbs);
            vars.put("wpbm", wpbm);
            vars.put("pym", pym);
            vars.put("wbm", wbm);
            vars.put("wpgg", wpgg);
            vars.put("wpdw", wpdw);
            vars.put("zxdw", zxdw);
            vars.put("zxgg", zxgg);
            vars.put("zhxs", zhxs);
            vars.put("zgcc", zgcc);
            vars.put("zdcc", zdcc);
            vars.put("dcyl", dcyl);
            vars.put("llyc", llyc);
            vars.put("gzwp", gzwp);
            vars.put("ycxwp", ycxwp);
            vars.put("satConditions", satConditions);
            result.put("message", sjJgWpxxClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(HttpServletResponse response, Integer id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            result.put("message", sjJgWpxxClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgWpxxRemoveFail");
        }
        return result;
    }
}
