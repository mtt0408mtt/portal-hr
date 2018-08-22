package com.pm.portal.domain.sjhc;


import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
@Data
public class StockReportResult {
	
    //private String jtbhmc;
    private String enjtbh;
    
	@JSONField(name = "机构名称enjgid")
    private String jgidmc;
    private String enjgid;
    
	@JSONField(name = "A物品编号enwpbh")
    private String wpbhmc;
    private String enwpbh;
    
	@JSONField(name = "B物品批号enwpph")
    private String wpph;
    private String enwpph;
 
	@JSONField(name = "C货位名称enhwbm")
    private String hwbmmc;
    private String enhwbm;
    

	@JSONField(name = "D仓库编号enckbh")
    private String ckbhmc;
    private String enckbh;
    
    
    private String year;
    private String month;
    
    @JSONField(name = "ZA月初enmonthHead")
    private Integer monthHead;
    private Integer enmonthHead;
    
    @JSONField(name = "ZB月入eninbound")
    private Integer inbound;
    private Integer eninbound;
    
    @JSONField(name = "ZC月出enoutbound")
    private Integer outbound;
    private Integer enoutbound;
    
    @JSONField(name = "ZD月余enmonthTail")
    private Integer monthTail;
    private Integer enmonthTail;
    
    
    
    
    private String ensearch;




	

}
