//**********************************************************
//  1. ��      ��: ���μ��� Data
//  2. ���α׷��� : MenuSubProcessData.java
//  3. ��      ��: ��� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 22
//  7. ��      ��:
//**********************************************************
package com.credu.system;
import com.credu.library.*;

/**
 * file name : MenuSubProcessData.java
 * date      : 2003/7/9
 * programmer: ������ (puggjsj@hanmail.net)
 * function  : MenuSubProcess DATA
 */

//TZ_MENUSUB
//grcode, menu, seq, process, servlettype, method, luserid, ldate

public class MenuSubProcessData
{

    private String grcode;            // �����׷� ID
    private String menu;              // �޴� ID
    private String process;           // ���μ��� ID
    private String servlettype;       // ����Ÿ��
    private String method;            // �Լ���
    private String luserid;           // ����������
    private String ldate;             // ����������
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
