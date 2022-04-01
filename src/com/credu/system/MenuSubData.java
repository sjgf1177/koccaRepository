//**********************************************************
//  1. 제      목: 모듈 Data
//  2. 프로그램명 : MenuData.java
//  3. 개      요: 모듈 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 22
//  7. 수      정:
//**********************************************************
package com.credu.system;
import com.credu.library.*;

/**
 * file name : MenuData.java
 * date      : 2003/7/9
 * programmer: 정상진 (puggjsj@hanmail.net)
 * function  : MenuSub DATA
 */

//TZ_MENUSUB
//grcode, menu, seq, servlet, modulenm, luserid, ldate

public class MenuSubData
{

    private String grcode;            // 교육그룹 ID
    private String menu;              // 메뉴 ID
    private String servlet;           // 메뉴명
    private String modulenm;          // 상위코드 ID
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일
    private int    seq;               // seq

    public MenuSubData() {}
	/**
	 * @return
	 */
	public String getGrcode() {
		return grcode;
	}

	/**
	 * @return
	 */
	public String getLdate() {
		return ldate;
	}

	/**
	 * @return
	 */
	public String getLuserid() {
		return luserid;
	}

	/**
	 * @return
	 */
	public String getMenu() {
		return menu;
	}

	/**
	 * @return
	 */
	public String getModulenm() {
		return modulenm;
	}

	/**
	 * @return
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @return
	 */
	public String getServlet() {
		return servlet;
	}

	/**
	 * @param string
	 */
	public void setGrcode(String string) {
		grcode = string;
	}

	/**
	 * @param string
	 */
	public void setLdate(String string) {
		ldate = string;
	}

	/**
	 * @param string
	 */
	public void setLuserid(String string) {
		luserid = string;
	}

	/**
	 * @param string
	 */
	public void setMenu(String string) {
		menu = string;
	}

	/**
	 * @param string
	 */
	public void setModulenm(String string) {
		modulenm = string;
	}

	/**
	 * @param i
	 */
	public void setSeq(int i) {
		seq = i;
	}

	/**
	 * @param string
	 */
	public void setServlet(String string) {
		servlet = string;
	}

}
