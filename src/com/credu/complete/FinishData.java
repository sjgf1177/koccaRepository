//**********************************************************
//1. ��      ��: 
//2. ���α׷���: FinishData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-09-08
//7. ��      ��: 
//                 
//********************************************************** 
 
package com.credu.complete;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FinishData {
	private String subj;
	private String subjnm;
	private String year;
	private String subjseq;
	private String subjseqgr;
	
	private String isonoff;
	private String propstart;
	private String propend;
	private String edustart;
	private String eduend;
	
	private String isclosed;
	
	private String isonoffnm;
	
	private String contenttype;

	// ����
	private int proposecnt;       // ��û
	private int firstapprovecnt;  // ����1
	private int finalapprovecnt;  // ����2
	private int notyetapprovecnt; // �̽���
	private int studentcnt;       // ����
	private int gradcnt;          // ����
	private int notgradcnt;       // �̼���
	
	// ���¸޽���
	private String proposemsg;      // ��û���� �޽��� [����|������|�Ϸ�] 
	private String firstapprovemsg;	// ����1���� �޽��� [������|00�� ��ó��|�Ϸ�]  
	private String finalapprovemsg; // ����2���� �޽��� [������|00�� ��ó��|�Ϸ�]  
	private String studentmsg;      // ���� �޽��� [����|������|����]           
	
	private String subjectcompletemsg;   // ���� �̼� �޽���
	private double subjectcompleterate;  // ���� �̼���
	private int    subjectaction;        // ���� ��ư [ó���Ϸ�|ó���Ϸ�+���|����ó��|������üũ] 
	
	//�������
	private String approvalstatus;	//Y:����Ϸ�,N:����ݷ�,B:��������,M:����̻��,'':����̻��
	private String approvalstatusdesc;
	
	public FinishData() {
		subj = "";
		subjnm = "";
		year = "";
		subjseq = "";
		subjseqgr = "";

		isonoff = "";
		propstart = "";
		propend = "";
		edustart = "";
		eduend = "";

		isclosed = "";
		isonoffnm = "";
		
		proposecnt = 0;       
		firstapprovecnt = 0;  
		finalapprovecnt = 0; 
		notyetapprovecnt = 0; 
		studentcnt = 0;       
		gradcnt = 0;          
		notgradcnt = 0;
		proposemsg = "";    
		firstapprovemsg = "";
		finalapprovemsg = "";
		studentmsg = "";     
		subjectcompletemsg = "";
		subjectcompleterate = 0;
		subjectaction = 0;
	}

	/**
	 * @return
	 */
	public String getEduend() {
		return eduend;
	}

	/**
	 * @return
	 */
	public String getEdustart() {
		return edustart;
	}

	/**
	 * @return
	 */
	public int getFinalapprovecnt() {
		return finalapprovecnt;
	}

	/**
	 * @return
	 */
	public String getFinalapprovemsg() {
		return finalapprovemsg;
	}

	/**
	 * @return
	 */
	public int getFirstapprovecnt() {
		return firstapprovecnt;
	}
	
	/**
	 * @return
	 */
	public int getNotyetapprovecnt() {
		return notyetapprovecnt;
	}

	/**
	 * @return
	 */
	public String getFirstapprovemsg() {
		return firstapprovemsg;
	}

	/**
	 * @return
	 */
	public int getGradcnt() {
		return gradcnt;
	}
	
	/**
	 * @return
	 */
	public int getNotgradcnt() {
		return notgradcnt;
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
	public String getPropend() {
		return propend;
	}

	/**
	 * @return
	 */
	public int getProposecnt() {
		return proposecnt;
	}

	/**
	 * @return
	 */
	public String getProposemsg() {
		return proposemsg;
	}

	/**
	 * @return
	 */
	public String getPropstart() {
		return propstart;
	}

	/**
	 * @return
	 */
	public int getStudentcnt() {
		return studentcnt;
	}

	/**
	 * @return
	 */
	public String getStudentmsg() {
		return studentmsg;
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
	public String getSubjectcompletemsg() {
		return subjectcompletemsg;
	}

	/**
	 * @return
	 */
	public double getSubjectcompleterate() {
		return subjectcompleterate;
	}

	/**
	 * @return
	 */
	public String getSubjnm() {
		return subjnm;
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
	public String getSubjseqgr() {
		return subjseqgr;
	}

	/**
	 * @return
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param string
	 */
	public void setEduend(String string) {
		eduend = string;
	}

	/**
	 * @param string
	 */
	public void setEdustart(String string) {
		edustart = string;
	}

	/**
	 * @param i
	 */
	public void setFinalapprovecnt(int i) {
		finalapprovecnt = i;
	}
	
	/**
	 * @param i
	 */
	public void setNotyetapprovecnt(int i) {
		notyetapprovecnt = i;
	}

	/**
	 * @param string
	 */
	public void setFinalapprovemsg(String string) {
		finalapprovemsg = string;
	}

	/**
	 * @param i
	 */
	public void setFirstapprovecnt(int i) {
		firstapprovecnt = i;
	}

	/**
	 * @param string
	 */
	public void setFirstapprovemsg(String string) {
		firstapprovemsg = string;
	}

	/**
	 * @param i
	 */
	public void setGradcnt(int i) {
		gradcnt = i;
	}
	
	/**
	 * @param i
	 */
	public void setNotgradcnt(int i) {
		notgradcnt = i;
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
	public void setPropend(String string) {
		propend = string;
	}

	/**
	 * @param i
	 */
	public void setProposecnt(int i) {
		proposecnt = i;
	}

	/**
	 * @param string
	 */
	public void setProposemsg(String string) {
		proposemsg = string;
	}

	/**
	 * @param string
	 */
	public void setPropstart(String string) {
		propstart = string;
	}

	/**
	 * @param i
	 */
	public void setStudentcnt(int i) {
		studentcnt = i;
	}

	/**
	 * @param string
	 */
	public void setStudentmsg(String string) {
		studentmsg = string;
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
	public void setSubjectcompletemsg(String string) {
		subjectcompletemsg = string;
	}

	/**
	 * @param d
	 */
	public void setSubjectcompleterate(double d) {
		subjectcompleterate = d;
	}

	/**
	 * @param string
	 */
	public void setSubjnm(String string) {
		subjnm = string;
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
	public void setSubjseqgr(String string) {
		subjseqgr = string;
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
	public String getIsclosed() {
		return isclosed;
	}

	/**
	 * @param string
	 */
	public void setIsclosed(String string) {
		isclosed = string;
	}

	/**
	 * @return
	 */
	public int getSubjectaction() {
		return subjectaction;
	}

	/**
	 * @param i
	 */
	public void setSubjectaction(int i) {
		subjectaction = i;
	}
	/**
	 * @return
	 */
	public String getIsonoffnm() {
		return isonoffnm;
	}

	/**
	 * @param string
	 */
	public void setIsonoffnm(String string) {
		isonoffnm = string;
	}
	
	/**
	 * @return
	 */
	public String getContenttype() {
		return contenttype;
	}

	/**
	 * @param string
	 */
	public void setContenttype(String string) {
		contenttype = string;
	}
	
	/**
	 * @return
	 */
	public String getApprovalstatus() {
		return approvalstatus;
	}

	/**
	 * @param string
	 */
	public void setApprovalstatus(String string) {
		approvalstatus = string;
	}
	
	/**
	 * @return
	 */
	public String getApprovalstatusdesc() {
		return approvalstatusdesc;
	}

	/**
	 * @param string
	 */
	public void setApprovalstatusdesc(String string) {
		approvalstatusdesc = string;
	}


}
