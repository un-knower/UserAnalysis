package com.tracker.db.dao.webstats.model;

import com.tracker.db.simplehbase.annotation.HBaseColumn;
import com.tracker.db.simplehbase.annotation.HBaseTable;
import com.tracker.db.util.RowUtil;

@HBaseTable(tableName = "website_user_session", defaultFamily = "data")
public class UserSessionData {
	public static enum Columns{
		startVisitTime, lastVisitTime, lastPageSign, visitPageCount, kpiDimesion
	}
	public static final int DATE_INDEX = 0;
	public static final int WEB_ID_INDEX = 1;
	public static final int COOKIE_ID_INDEX = 2;
	
	@HBaseColumn(qualifier = "startVisitTime")
	public Long startVisitTime;
	
	@HBaseColumn(qualifier = "lastVisitTime")
	public Long lastVisitTime;
	
	//上次访问页面
	@HBaseColumn(qualifier = "lastPageSign")
	public String lastPageSign;
	
	@HBaseColumn(qualifier = "kpiDimesion")
	public String kpiDimesion;
	
	public static String generateKey(String date, String webId, String cookieId){
		return generateRowPrefix(date) + webId + RowUtil.ROW_SPLIT + cookieId;
	}
	
	public static String generateRowPrefix(String date){
		return date  + RowUtil.ROW_SPLIT;
	}
	
	public String getKpiDimesion() {
		return kpiDimesion;
	}
	public void setKpiDimesion(String kpiDimesion) {
		this.kpiDimesion = kpiDimesion;
	}

	public Long getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(Long lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}

	public String getLastPageSign() {
		return lastPageSign;
	}

	public void setLastPageSign(String lastPageSign) {
		this.lastPageSign = lastPageSign;
	}

	public Long getStartVisitTime() {
		return startVisitTime;
	}

	public void setStartVisitTime(Long startVisitTime) {
		this.startVisitTime = startVisitTime;
	}

}