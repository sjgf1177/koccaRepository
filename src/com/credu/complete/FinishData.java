//**********************************************************
//1. 제      목: 
//2. 프로그램명: FinishData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-09-08
//7. 수      정: 
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

	// 정원
	private int proposecnt;       // 신청
	private int firstapprovecnt;  // 승인1
	private int finalapprovecnt;  // 승인2
	private int notyetapprovecnt; // 미승인
	private int studentcnt;       // 교육
	private int gradcnt;          // 수료
	private int notgradcnt;       // 미수료
	
	// 상태메시지
	private String proposemsg;      // 신청상태 메시지 [이전|진행중|완료] 
	private String firstapprovemsg;	// 승인1상태 메시지 [대상없음|00명 미처리|완료]  
	private String finalapprovemsg; // 승인2상태 메시지 [대상없음|00명 미처리|완료]  
	private String studentmsg;      // 교육 메시지 [이전|교육중|종료]           
	
	private String subjectcompletemsg;   // 과정 이수 메시지
	private double subjectcompleterate;  // 과정 이수율
	private int    subjectaction;        // 과정 버튼 [처리완료|처리완료+취소|수료처리|수료율체크] 
	
	//결재상태
	private String approvalstatus;	//Y:결재완료,N:결재반려,B:결재상신중,M:결재미상신,'':결재미상신
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
