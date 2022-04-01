//**********************************************************
//1. ��      ��: ������������Ʈ DATA
//2. ���α׷���: MasterFormListData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: LeeSuMin 2003. 08. 13
//7. ��      ��:
//
//**********************************************************
package com.credu.contents;

public class MasterFormListData {

	private String  subj;               //�����ڵ�
	private String  dir;                //������ġ
	private String  subjnm;             //������
	private String  ismfbranch;         //�б�Ŀ���
	private String  iscentered;         //�߾����Ŀ���
	private String  isuse;              //��뿩��
	private String  contenttype;        //ContentType(N/O:obc/S:scorm)
	private String  mftype;             //�޴������� ��ġ
	private String  mftypenm;             //�޴������� ��ġ
	private String  width;              //âũ��(WIDTH)
	private String  height;             //âũ��(HEIGHT)
	private String  eduprocess;         //�н�������
	private String  otbgcolor;          //Ʈ���޴�����
	private String  isvisible;          //�н��ڿ��� �����ֱ�
	private String  isoutsourcing;      //���ֿ���

	private int cnt_module=0;       //����
	private int cnt_lesson=0;       //���ü�
	/* addon */
	private String  contenttypenm="";
	//SERVER,a.PORT,a.EDUURL
	private String  server="",port="",eduurl="";

	public String getServer() {
		return server;
	}



	public void setServer(String server) {
		this.server = server;
	}



	public String getPort() {
		return port;
	}



	public void setPort(String port) {
		this.port = port;
	}



	public String getEduurl() {
		return eduurl;
	}



	public void setEduurl(String eduurl) {
		this.eduurl = eduurl;
	}



	public String getMftypenm() {
		return mftypenm;
	}



	public void setMftypenm(String mftypenm) {
		this.mftypenm = mftypenm;
	}



	public MasterFormListData() {};



	/**
	 * @return
	 */
	public int getCnt_lesson() {
		return cnt_lesson;
	}

	/**
	 * @return
	 */
	public int getCnt_module() {
		return cnt_module;
	}

	/**
	 * @return
	 */
	public String getContenttype() {
		return contenttype;
	}

	/**
	 * @return
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @return
	 */
	public String getIscentered() {
		return iscentered;
	}

	/**
	 * @return
	 */
	public String getIsmfbranch() {
		return ismfbranch;
	}

	/**
	 * @return
	 */
	public String getSubj() {
		return subj;
	}

	/**
	 * @return
	 */
	public String getSubjnm() {
		return subjnm;
	}

	/**
	 * @param i
	 */
	public void setCnt_lesson(int i) {
		cnt_lesson = i;
	}

	/**
	 * @param i
	 */
	public void setCnt_module(int i) {
		cnt_module = i;
	}

	/**
	 * @param string
	 */
	public void setContenttype(String string) {
		contenttype = string;
	}

	/**
	 * @param string
	 */
	public void setDir(String string) {
		dir = string;
	}

	/**
	 * @param string
	 */
	public void setIscentered(String string) {
		iscentered = string;
	}

	/**
	 * @param string
	 */
	public void setIsmfbranch(String string) {
		ismfbranch = string;
	}

	/**
	 * @param string
	 */
	public void setSubj(String string) {
		subj = string;
	}

	/**
	 * @param string
	 */
	public void setSubjnm(String string) {
		subjnm = string;
	}

	/**
	 * @return
	 */
	public String getIsuse() {
		return isuse;
	}

	/**
	 * @param string
	 */
	public void setIsuse(String string) {
		isuse = string;
	}

	/**
	 * @return
	 */
	public String getContenttypenm() {
		return contenttypenm;
	}

	/**
	 * @param string
	 */
	public void setContenttypenm(String string) {
		contenttypenm = string;
	}


	/**
	 * @return
	 */
	public String getMftype() {
		return mftype;
	}

	/**
	 * @param string
	 */
	public void setMftype(String string) {
		mftype = string;
	}



	/**
	 * @return
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string) {
		width = string;
	}

	/**
	 * @return
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param string
	 */
	public void setHeight(String string) {
		height = string;
	}

	/**
	 * @return
	 */
	public String getEduprocess() {
		return eduprocess;
	}

	/**
	 * @param string
	 */
	public void setEduprocess(String string) {
		eduprocess = string;
	}

	/**
	 * @return
	 */
	public String getOtbgcolor() {
		return otbgcolor;
	}

	/**
	 * @param string
	 */
	public void setOtbgcolor(String string) {
		otbgcolor = string;
	}

	/**
	 * @return
	 */
	public String getIsvisible() {
		return isvisible;
	}

	/**
	 * @param string
	 */
	public void setIsvisible(String string) {
		isvisible = string;
	}



	public String getIsoutsourcing() {
		return isoutsourcing;
	}



	public void setIsoutsourcing(String isoutsourcing) {
		this.isoutsourcing = isoutsourcing;
	}

}



