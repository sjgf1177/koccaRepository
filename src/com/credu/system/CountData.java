//**********************************************************
//  1. ��      ��: COUNT Data
//  2. ���α׷��� : CountData.java
//  3. ��      ��: ������� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 5. 28
//  7. ��      ��:
//**********************************************************
package com.credu.system;


public class CountData
{
    private String gubun;               // ����
    private String date_year;           // ��
    private String date_month;          // ��
    private String date_day;            // ��
    private String date_time;           // �ð�
    private String date_week;           // ����
    private int    cnt;                 // ����Ƚ��

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