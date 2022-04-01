//**********************************************************
//  1. ��      ��: ��� Data
//  2. ���α׷��� : MenuData.java
//  3. ��      ��: ��� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 22
//  7. ��      ��:
//**********************************************************
package com.credu.system;
import com.credu.library.*;

/**
 * file name : MenuData.java
 * date      : 2003/7/9
 * programmer: ������ (puggjsj@hanmail.net)
 * function  : MenuSub DATA
 */

//TZ_MENUSUB
//grcode, menu, seq, servlet, modulenm, luserid, ldate

public class MenuSubData
{

    private String grcode;            // �����׷� ID
    private String menu;              // �޴� ID
    private String servlet;           // �޴���
    private String modulenm;          // �����ڵ� ID
    private String luserid;           // ����������
    private String ldate;             // ����������
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
