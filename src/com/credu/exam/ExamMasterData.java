//**********************************************************
//1. ��      ��: 
//2. ���α׷���: ExamMasterData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-09-03
//7. ��      ��: 
//                 
//********************************************************** 
 
package com.credu.exam;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ExamMasterData {
	private String  subj;          /* �����ڵ� */
	private String  year;          /* ��������(�������ΰ��:TEST) */
	private String  subjseq;       /* ��������(�������ΰ��:TEST) */
	private String  lesson;        /* ����(�¶����� ���:OT) 		*/
	private String  ptype;         /* �� type : [�߰�|����|�¶����׽�Ʈ] */
	private int     cnttotal;      /* �򰡼�(���򰡰���)	*/
	private String  partstart;     /* �򰡹���(��������)	*/
	private String  partend;       /* �򰡹���(��������)	*/
	private int     cntlevel1;     /* ���׼�(��)	*/
	private int     cntlevel2;     /* ���׼�(��)	*/
	private int     cntlevel3;     /* ���׼�(��)	*/
	private String  testdate;      /* test �Ͻ� :�¶���/ �ǽð� �ƴϸ� 0���� set */
	private int     testtime;      /* �ҿ�ð�	*/
	private int     jointotal;     /* �����ڼ�	*/
	private String  f_multiex;     /* ���������� ����:�¶������ϰ�쿡�� ����*/
	private String  f_expurl;      /* ���ȭ�鿡 ������ ���� ��ŭ�� ���� ���� �ؼ��� �ʿ��� ���� �� �������� �ۼ��Ͽ� url��ũ�� �ɾ���*/
	private String  f_usehtml;     /* �������� ������ HTML�� ����� ��� (���а�����) */
	private String  ptypenm;        /* �� type �̸� */

	public ExamMasterData() {
		subj       = "";
		year       = "";
		subjseq    = "";
		lesson     = "";
		ptype      = "";
		cnttotal   = 0;
		partstart  = "";
		partend    = "";
		cntlevel1  = 0;
		cntlevel2  = 0;
		cntlevel3  = 0;
		testdate   = "";
		testtime   = 0;
		jointotal  = 0;
		f_multiex  = "";
		f_expurl   = "";
		f_usehtml  = "";
		ptypenm    = "";
	}

	/**
	 * @return
	 */
	public int getCntlevel1() {
		return cntlevel1;
	}

	/**
	 * @return
	 */
	public int getCntlevel2() {
		return cntlevel2;
	}

	/**
	 * @return
	 */
	public int getCntlevel3() {
		return cntlevel3;
	}

	/**
	 * @return
	 */
	public int getCnttotal() {
		return cnttotal;
	}

	/**
	 * @return
	 */
	public String getF_expurl() {
		return f_expurl;
	}

	/**
	 * @return
	 */
	public String getF_multiex() {
		return f_multiex;
	}

	/**
	 * @return
	 */
	public String getF_usehtml() {
		return f_usehtml;
	}

	/**
	 * @return
	 */
	public int getJointotal() {
		return jointotal;
	}

	/**
	 * @return
	 */
	public String getLesson() {
		return lesson;
	}

	/**
	 * @return
	 */
	public String getPartend() {
		return partend;
	}

	/**
	 * @return
	 */
	public String getPartstart() {
		return partstart;
	}

	/**
	 * @return
	 */
	public String getPtype() {
		return ptype;
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
	public String getSubjseq() {
		return subjseq;
	}

	/**
	 * @return
	 */
	public String getTestdate() {
		return testdate;
	}

	/**
	 * @return
	 */
	public int getTesttime() {
		return testtime;
	}

	/**
	 * @return
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param i
	 */
	public void setCntlevel1(int i) {
		cntlevel1 = i;
	}

	/**
	 * @param i
	 */
	public void setCntlevel2(int i) {
		cntlevel2 = i;
	}

	/**
	 * @param i
	 */
	public void setCntlevel3(int i) {
		cntlevel3 = i;
	}

	/**
	 * @param i
	 */
	public void setCnttotal(int i) {
		cnttotal = i;
	}

	/**
	 * @param string
	 */
	public void setF_expurl(String string) {
		f_expurl = string;
	}

	/**
	 * @param string
	 */
	public void setF_multiex(String string) {
		f_multiex = string;
	}

	/**
	 * @param string
	 */
	public void setF_usehtml(String string) {
		f_usehtml = string;
	}

	/**
	 * @param i
	 */
	public void setJointotal(int i) {
		jointotal = i;
	}

	/**
	 * @param string
	 */
	public void setLesson(String string) {
		lesson = string;
	}

	/**
	 * @param string
	 */
	public void setPartend(String string) {
		partend = string;
	}

	/**
	 * @param string
	 */
	public void setPartstart(String string) {
		partstart = string;
	}

	/**
	 * @param string
	 */
	public void setPtype(String string) {
		ptype = string;
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
	public void setSubjseq(String string) {
		subjseq = string;
	}

	/**
	 * @param string
	 */
	public void setTestdate(String string) {
		testdate = string;
	}

	/**
	 * @param i
	 */
	public void setTesttime(int i) {
		testtime = i;
	}

	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
	}
	/**
	 * @return
	 */
	public String getPtypenm() {
		return ptypenm;
	}

	/**
	 * @param string
	 */
	public void setPtypenm(String string) {
		ptypenm = string;
	}

}
