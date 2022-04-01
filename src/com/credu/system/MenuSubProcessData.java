//**********************************************************
//  1. 제      목: 프로세스 Data
//  2. 프로그램명 : MenuSubProcessData.java
//  3. 개      요: 모듈 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 22
//  7. 수      정:
//**********************************************************
package com.credu.system;
import com.credu.library.*;

/**
 * file name : MenuSubProcessData.java
 * date      : 2003/7/9
 * programmer: 정상진 (puggjsj@hanmail.net)
 * function  : MenuSubProcess DATA
 */

//TZ_MENUSUB
//grcode, menu, seq, process, servlettype, method, luserid, ldate

public class MenuSubProcessData
{

    private String grcode;            // 교육그룹 ID
    private String menu;              // 메뉴 ID
    private String process;           // 프로세스 ID
    private String servlettype;       // 서블릿타입
    private String method;            // 함수명
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일
    private int    seq;               // seq

    public MenuSubProcessData() {}
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
	public String getMethod() {
		return method;
	}

	/**
	 * @return
	 */
	public String getProcess() {
		return process;
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
	public String getServlettype() {
		return servlettype;
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
	public void setMethod(String string) {
		method = string;
	}

	/**
	 * @param string
	 */
	public void setProcess(String string) {
		process = string;
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
	public void setServlettype(String string) {
		servlettype = string;
	}

}
