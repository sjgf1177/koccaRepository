//**********************************************************
//  1. 제      목: COUNT Data
//  2. 프로그램명 : CountData.java
//  3. 개      요: 접속통계 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 5. 28
//  7. 수      정:
//**********************************************************
package com.credu.system;


public class CountData
{
    private String gubun;               // 구분
    private String date_year;           // 년
    private String date_month;          // 월
    private String date_day;            // 일
    private String date_time;           // 시간
    private String date_week;           // 요일
    private int    cnt;                 // 접속횟수

    public CountData() {}
	/**
	 * @return
	 */
	public int getCnt() {
		return cnt;
	}

	/**
	 * @return
	 */
	public String getDate_day() {
		return date_day;
	}

	/**
	 * @return
	 */
	public String getDate_month() {
		return date_month;
	}

	/**
	 * @return
	 */
	public String getDate_time() {
		return date_time;
	}

	/**
	 * @return
	 */
	public String getDate_week() {
		return date_week;
	}

	/**
	 * @return
	 */
	public String getDate_year() {
		return date_year;
	}

	/**
	 * @return
	 */
	public String getGubun() {
		return gubun;
	}

	/**
	 * @param i
	 */
	public void setCnt(int i) {
		cnt = i;
	}

	/**
	 * @param string
	 */
	public void setDate_day(String string) {
		date_day = string;
	}

	/**
	 * @param string
	 */
	public void setDate_month(String string) {
		date_month = string;
	}

	/**
	 * @param string
	 */
	public void setDate_time(String string) {
		date_time = string;
	}

	/**
	 * @param string
	 */
	public void setDate_week(String string) {
		date_week = string;
	}

	/**
	 * @param string
	 */
	public void setDate_year(String string) {
		date_year = string;
	}

	/**
	 * @param string
	 */
	public void setGubun(String string) {
		gubun = string;
	}

}