//**********************************************************
//  1. ��      ��: �˻� DATA  
//  2. ���α׷���: SearchData.java
//  3. ��      ��: �˻� DATA BEAN
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 8. 1
//  7. ��      ��: 
//**********************************************************

package com.credu.common;

/**
 * @author s.j Jung
 * 
 */
public class SearchData {
	private String code;              // �ڵ�
	private String codenm;            // �ڵ��
	private String isonoff;           // ON, OFF ����
	private String tmp1;              // �߰��ʵ�1
	private String tmp2;              // �߰��ʵ�2
	private String tmp3;              // �߰��ʵ�3
	private String tmp4;              // �߰��ʵ�4
	private String tmp5;              // �߰��ʵ�5
	private String tmp6;              // �߰��ʵ�6
	private String tmp7;              // �߰��ʵ�7
	private String tmp8;              // �߰��ʵ�8
	private String tmp9;              // �߰��ʵ�9
	private String tmp10;             // �߰��ʵ�10
	private String userid;			  // �����ID
	private String usernm;			  // ����ڼ���

	private int dispnum;              // �ѰԽù���
	private int totalpagecount;     // �Խù�����������
	
	/**
	 * 
	 */
	public SearchData() {}

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return
	 */
	public String getCodenm() {
		return codenm;
	}

    /**
	 * @return
	 */
	public String getIsonoff() {
		return isonoff;
	}
	
	/**
	 * @return
	 */
	public String getTmp1() {
		return tmp1;
	}

	/**
	 * @return
	 */
	public String getTmp10() {
		return tmp10;
	}

	/**
	 * @return
	 */
	public String getTmp2() {
		return tmp2;
	}

	/**
	 * @return
	 */
	public String getTmp3() {
		return tmp3;
	}

	/**
	 * @return
	 */
	public String getTmp4() {
		return tmp4;
	}

	/**
	 * @return
	 */
	public String getTmp5() {
		return tmp5;
	}

	/**
	 * @return
	 */
	public String getTmp6() {
		return tmp6;
	}

	/**
	 * @return
	 */
	public String getTmp7() {
		return tmp7;
	}

	/**
	 * @return
	 */
	public String getTmp8() {
		return tmp8;
	}

	/**
	 * @return
	 */
	public String getTmp9() {
		return tmp9;
	}
	
	/**
	 * @return
	 */
	public String getUserid() {
		return userid;
	}
	
	/**
	 * @return
	 */
	public String getUsernm() {
		return usernm;
	}

	/**
	 * @return
	 */
	public int getDispnum() {
		return dispnum;
	}

	/**
	 * @return
	 */
	public int getTotalpagecount() {
		return totalpagecount;
	}

	
	/**
	 * @param string
	 */
	public void setCode(String string) {
		code = string;
	}

	/**
	 * @param string
	 */
	public void setCodenm(String string) {
		codenm = string;
	}

    /**
	 * @param string
	 */
	public void setIsonoff(String string) {
		isonoff = string;
	}
	
	/**
	 * @param string
	 */
	public void setTmp1(String string) {
		tmp1 = string;
	}

	/**
	 * @param string
	 */
	public void setTmp10(String string) {
		tmp10 = string;
	}

	/**
	 * @param string
	 */
	public void setTmp2(String string) {
		tmp2 = string;
	}

	/**
	 * @param string
	 */
	public void setTmp3(String string) {
		tmp3 = string;
	}

	/**
	 * @param string
	 */
	public void setTmp4(String string) {
		tmp4 = string;
	}

	/**
	 * @param string
	 */
	public void setTmp5(String string) {
		tmp5 = string;
	}

	/**
	 * @param string
	 */
	public void setTmp6(String string) {
		tmp6 = string;
	}

	/**
	 * @param string
	 */
	public void setTmp7(String string) {
		tmp7 = string;
	}

	/**
	 * @param string
	 */
	public void setTmp8(String string) {
		tmp8 = string;
	}

	/**
	 * @param string
	 */
	public void setTmp9(String string) {
		tmp9 = string;
	}
	
	/**
	 * @param string
	 */
	public void setUserid(String string) {
		userid = string;
	}
	
	/**
	 * @param string
	 */
	public void setUsernm(String string) {
		usernm = string;
	}


	/**
	 * @param i
	 */
	public void setDispnum(int i) {
		dispnum = i;
	}


	/**
	 * @param i
	 */
	public void setTotalpagecount(int i) {
		totalpagecount = i;
	}

}
