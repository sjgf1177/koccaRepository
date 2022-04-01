//**********************************************************
//  1. 제      목: 검색 DATA  
//  2. 프로그램명: SearchData.java
//  3. 개      요: 검색 DATA BEAN
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 1
//  7. 수      정: 
//**********************************************************

package com.credu.common;

/**
 * @author s.j Jung
 * 
 */
public class SearchData {
	private String code;              // 코드
	private String codenm;            // 코드명
	private String isonoff;           // ON, OFF 여부
	private String tmp1;              // 추가필드1
	private String tmp2;              // 추가필드2
	private String tmp3;              // 추가필드3
	private String tmp4;              // 추가필드4
	private String tmp5;              // 추가필드5
	private String tmp6;              // 추가필드6
	private String tmp7;              // 추가필드7
	private String tmp8;              // 추가필드8
	private String tmp9;              // 추가필드9
	private String tmp10;             // 추가필드10
	private String userid;			  // 담당자ID
	private String usernm;			  // 담당자성명

	private int dispnum;              // 총게시물수
	private int totalpagecount;     // 게시물총페이지수
	
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
