package com.tracker.db.dao.webstats.model;

import com.tracker.db.simplehbase.annotation.HBaseColumn;
import com.tracker.db.simplehbase.annotation.HBaseTable;
import com.tracker.db.util.RowUtil;
import com.tracker.db.util.Util;

@HBaseTable(tableName = "offline_website_kpi", defaultFamily = "stats")
public class WebSiteSysEnvStats {
	public final static String SIGN_WEB_SYS = "web-sys"; //基于系统环境

	public static final int WEB_ID_INDEX = 0;
	public static final int TIME_TYPE_INDEX = 1;
	public static final int TIME_INDEX = 2;
	public static final int SIGN_INDEX = 3;
	public static final int VISITOR_TYPE_INDEX = 4;
	public static final int SYS_TYPE_INDEX = 5;
	public static final int NAME_INDEX = 6;
	
	//kpi
	@HBaseColumn(qualifier = "uv")
	public Long uv;
	@HBaseColumn(qualifier = "ip_count")
	public Long ipCount;
	@HBaseColumn(qualifier = "pv")
	public Long pv;
	@HBaseColumn(qualifier = "visit_times")
	public Long visitTimes;//访问次数
	@HBaseColumn(qualifier = "total_visit_time")
	public Long totalVisitTime;// 总访问时长
	@HBaseColumn(qualifier = "jump_count")
	public Long jumpCount;//跳出次数
	
	public static String generateRow(Integer webId, Integer timeType, String time, Integer visitorType, Integer sysType, String name){
		Util.checkEmptyString(name);
		return generateRowPrefix(webId, timeType, time, visitorType, sysType) + name;
	}
	
	public static String generateRowPrefix(Integer webId, Integer timeType, String time, Integer visitorType, Integer sysType){
		Util.checkNull(webId);
		Util.checkNull(timeType);
		Util.checkEmptyString(time);
		Util.checkNull(sysType);

		StringBuffer sb = new StringBuffer();
		sb.append(webId).append(RowUtil.ROW_SPLIT);
		sb.append(timeType).append(RowUtil.ROW_SPLIT);
		sb.append(time).append(RowUtil.ROW_SPLIT);
		sb.append(SIGN_WEB_SYS).append(RowUtil.ROW_SPLIT);
		sb.append(visitorType == null ? "":visitorType).append(RowUtil.ROW_SPLIT);
		sb.append(sysType).append(RowUtil.ROW_SPLIT);
		
		return sb.toString();
	}
	
	public Long getJumpCount() {
		return jumpCount;
	}

	public void setJumpCount(Long jumpCount) {
		this.jumpCount = jumpCount;
	}
	public Long getUv() {
		return uv;
	}
	public void setUv(Long uv) {
		this.uv = uv;
	}
	public Long getIpCount() {
		return ipCount;
	}
	public void setIpCount(Long ipCount) {
		this.ipCount = ipCount;
	}
	public Long getPv() {
		return pv;
	}
	public void setPv(Long pv) {
		this.pv = pv;
	}
	public Long getVisitTimes() {
		return visitTimes;
	}
	public void setVisitTimes(Long visitTimes) {
		this.visitTimes = visitTimes;
	}
	public Long getTotalVisitTime() {
		return totalVisitTime;
	}
	public void setTotalVisitTime(Long totalVisitTime) {
		this.totalVisitTime = totalVisitTime;
	}
	
	
}
