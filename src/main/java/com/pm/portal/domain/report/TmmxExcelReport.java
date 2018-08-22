package com.pm.portal.domain.report;


import lombok.Data;

@Data
public class TmmxExcelReport {
	private String jgmc;
	private String sjdwmc;
	private String brxm;
	private String sexmc;
	private String tmh;
	private String zxsj;
	private String zhmc;
	private String jgid;
	private String sjdw;
	private String tmztmc;
	private String sbmc;
	private String jyrmc;
	private String ksdmmc;
	private String lbmc;
	private String sqmsmc;
	private String brlxmc;
	
}


//SELECT JGMC,SJDWMC,brxm,SEXMC,tmh zxsj,ZHMC_STR FROM v_tmxx_static group by 