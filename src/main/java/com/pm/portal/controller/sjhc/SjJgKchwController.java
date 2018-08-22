package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgKchwClient;
import com.pm.portal.util.SimpleExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/sjJgKchw")
public class SjJgKchwController {
    private final SjJgKchwClient sjJgKchwClient;

    @Autowired
    public SjJgKchwController(SjJgKchwClient sjJgKchwClient) {
        this.sjJgKchwClient = sjJgKchwClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgKchwClient.list(vars);
            if ("sjJgKchwFail".equals(data)) {
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

    @RequestMapping(value = "/report", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object report(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgKchwClient.report(vars);
            if ("sjJgKchwFailB".equals(data)) {
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

    @RequestMapping(value = "/report2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object report2(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgKchwClient.report2(vars);
            if ("sjJgKchwFailC".equals(data)) {
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

    @RequestMapping(value = "/warnList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object warnList(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgKchwClient.warnList(vars);
            if ("sjJgKchwFailF".equals(data)) {
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

    @RequestMapping(value = "/warnExcelExport", method = RequestMethod.POST)
    public void warnExcelExport(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgKchwClient.warnList(vars);

            if (!"sjJgKchwFailF".equals(data)) {

                String type = (String) vars.get("type");
                String sheetName = "库存提醒表";
                if ("3".equals(type)) {
                    sheetName = "低储量不足提醒表";
                } else if ("4".equals(type)) {
                    sheetName = "高储量偏高提醒表";
                } else if ("2".equals(type)) {
                    sheetName = "效期提醒表";
                }

                List<String> headList = Arrays.asList("ID", "物品名称", "规格", "单位", "数量", "相同仓库物品总数量", "批号",
                        "货位", "高储", "低储", "效期(月)", "生产日期", "截止日期");
                List<String> colNames = Arrays.asList("id", "wpmc", "wpgg", "wpdwmc", "monthTail", "monthTailTotal", "wpph",
                        "hwbmmc", "zgcc", "zdcc", "validityMonth", "scrqmc", "sxrqmc");

                OutputStream os = null;
                try {
                    os = response.getOutputStream();
                    SimpleExcelUtil.expExcel(sheetName, headList, colNames, data).write(os);
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/resolve", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object resolve(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgKchwClient.resolve(vars);
            if ("sjJgKchwFailD".equals(data)) {
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

    @RequestMapping(value = "/beforeResolve", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object beforeResolve(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgKchwClient.beforeResolve(vars);
            if ("sjJgKchwFailE".equals(data)) {
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

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, Integer wpbh, String wpph, Integer hwbm, Integer outbound) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            vars.put("ckbh", 1);
            vars.put("wpbh", wpbh);
            vars.put("wpph", wpph);
            vars.put("hwbm", hwbm);
            vars.put("outbound", outbound);
            result.put("message", sjJgKchwClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
}
