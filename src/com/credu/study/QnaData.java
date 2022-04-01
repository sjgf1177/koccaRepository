//**********************************************************
//  1. 제      목: QNA DATA
//  2. 프로그램명: QnaData.java
//  3. 개      요: 질문 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김수진 2003. 8. 18
//  7. 수      정:
//**********************************************************
package com.credu.study;
import com.credu.library.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QnaData {

	private String subj;
	private String year;
	private String subjseq;
	private String subjseqgr;
	private String lesson;
	private int seq;
	private int types;
	private int kind;
	private String title;
	private String contents;
	private String indate;
	private String inuserid;
	private String upfile;
	private String isOpen;
	private String replygubun;
	private String luserid;
	private String ldate;

	private String course;
	private String cyear;
	private String courseseq;
	private String subjnm;
	private String coursenm;
	private String isnewcourse;
	private String lessonnm;    //일차명
	private String asgnnm;      //소속
	private String jikwinm;     //직위명
	private String cono;		//사번
	private String inusernm;	//작성자성명
	private String compnm;		//작성자회사명
	private String replycono;	//응답자 사번
	private String replyusernm; //응답자 성명
	private String replyasgnnm; //응답자 소속
	private String replyindate; //최초 응답일
	private String isclosed;    //교육진행여부
	private String togubun;    //구분(운영자에게,강사에게)
	private String replyuserid;
	private String replydate;
	private String name;
	
	private int	delayday;		//지체일
	private int rowspan;
	private int qcnt;
	private int anscnt;
	private int noanscnt;
	/**
	 * @return
	 */

	/**
	 * @return
	 */
	public int getAnscnt() {
		return anscnt;
	}

	/**
	 * @return
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @return
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * @return
	 */
	public String getCoursenm() {
		return coursenm;
	}

	/**
	 * @return
	 */
	public String getCourseseq() {
		return courseseq;
	}

	/**
	 * @return
	 */
	public String getCyear() {
		return cyear;
	}

	/**
	 * @return
	 */
	public String getIndate() {
		return indate;
	}

	/**
	 * @return
	 */
	public String getInuserid() {
		return inuserid;
	}

	/**
	 * @return
	 */
	public String getIsnewcourse() {
		return isnewcourse;
	}

	/**
	 * @return
	 */
	public String getIsOpen() {
		return isOpen;
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
	public String getLesson() {
		return lesson;
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
	public String getName() {
		return name;
	}
	
	/**
	 * @return
	 */
	public String getReplyuserid() {
		return replyuserid;
	}
	
	/**
	 * @return
	 */
	public String getReplyusernm() {
		return replyusernm;
	}
	
	/**
	 * @return
	 */
	public String getReplydate() {
		return replydate;
	}
	

	/**
	 * @return
	 */
	public int getNoanscnt() {
		return noanscnt;
	}

	/**
	 * @return
	 */
	public int getQcnt() {
		return qcnt;
	}

	/**
	 * @return
	 */
	public int getRowspan() {
		return rowspan;
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
	public String getTitle() {
		return title;
	}

	/**
	 * @return
	 */
	public int getTypes() {
		return types;
	}
	
	/**
	 * @return
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * @return
	 */
	public String getUpfile() {
		return upfile;
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
	public void setAnscnt(int i) {
		anscnt = i;
	}

	/**
	 * @param string
	 */
	public void setContents(String string) {
		contents = string;
	}

	/**
	 * @param string
	 */
	public void setCourse(String string) {
		course = string;
	}

	/**
	 * @param string
	 */
	public void setCoursenm(String string) {
		coursenm = string;
	}

	/**
	 * @param string
	 */
	public void setCourseseq(String string) {
		courseseq = string;
	}

	/**
	 * @param string
	 */
	public void setCyear(String string) {
		cyear = string;
	}

	/**
	 * @param string
	 */
	public void setIndate(String string) {
		indate = string;
	}

	/**
	 * @param string
	 */
	public void setInuserid(String string) {
		inuserid = string;
	}

	/**
	 * @param string
	 */
	public void setIsnewcourse(String string) {
		isnewcourse = string;
	}

	/**
	 * @param string
	 */
	public void setIsOpen(String string) {
		isOpen = string;
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
	public void setLesson(String string) {
		lesson = string;
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
	public void setName(String string) {
		name = string;
	}
	
	/**
	 * @param string
	 */
	public void setReplyuserid(String string) {
		replyuserid = string;
	}
	
	/**
	 * @param string
	 */
	public void setReplyusernm(String string) {
		replyusernm = string;
	}
	
	/**
	 * @param string
	 */
	public void setReplydate(String string) {
		replydate = string;
	}
	
	/**
	 * @param i
	 */
	public void setNoanscnt(int i) {
		noanscnt = i;
	}

	/**
	 * @param i
	 */
	public void setQcnt(int i) {
		qcnt = i;
	}

	/**
	 * @param i
	 */
	public void setRowspan(int i) {
		rowspan = i;
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
	public void setTitle(String string) {
		title = string;
	}

	/**
	 * @param string
	 */
	public void setTypes(int i) {
		types = i;
	}
	
	/**
	 * @param string
	 */
	public void setKind(int i) {
		kind = i;
	}

	/**
	 * @param string
	 */
	public void setUpfile(String string) {
		upfile = string;
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
	public String getLessonnm() {
		return lessonnm;
	}

	/**
	 * @param string
	 */
	public void setLessonnm(String string) {
		lessonnm = string;
	}



	/**
	 * @return
	 */
	public String getAsgnnm() {
		return asgnnm;
	}

	/**
	 * @return
	 */
	public String getJikwinm() {
		return jikwinm;
	}

	/**
	 * @param string
	 */
	public void setAsgnnm(String string) {
		asgnnm = string;
	}

	/**
	 * @param string
	 */
	public void setJikwinm(String string) {
		jikwinm = string;
	}

	/**
	 * @return
	 */
	public String getCono() {
		return cono;
	}

	/**
	 * @param string
	 */
	public void setCono(String string) {
		cono = string;
	}

	/**
	 * @return
	 */
	public String getInusernm() {
		return inusernm;
	}

	/**
	 * @param string
	 */
	public void setInusernm(String string) {
		inusernm = string;
	}

	/**
	 * @return
	 */
	public String getCompnm() {
		return compnm;
	}

	/**
	 * @param string
	 */
	public void setCompnm(String string) {
		compnm = string;
	}

	/**
	 * @return
	 */
	public String getReplygubun() {
		return replygubun;
	}

	/**
	 * @param string
	 */
	public void setReplygubun(String string) {
		replygubun = string;
	}
	
		
	/**
	 * @return
	 */
	public String getReplycono() {
		return replygubun;
	}

	/**
	 * @param string
	 */
	public void setReplycono(String string) {
		replycono = string;
	}



	/**
	 * @return
	 */
	public String getReplyasgnnm() {
		return replyasgnnm;
	}

	/**
	 * @param string
	 */
	public void setReplyasgnnm(String string) {
		replyasgnnm = string;
	}
	
	/**
	 * @return
	 */
	public int getDelayday() {
		return delayday;
	}

	/**
	 * @param string
	 */
	public void setDelayday(int i) {
		delayday = i;
	}
	
	/**
	 * @return
	 */
	public String getReplyindate() {
		return replyindate;
	}

	/**
	 * @param string
	 */
	public void setReplyindate(String string) {
		replyindate = string;
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
	public void setIsclosed(String isclosed) {
		this.isclosed = isclosed;  
	}
	

	public void setTogubun(String togubun) {  this.togubun = togubun;  }
	public String getTogubun() { return this.togubun; }	
}
