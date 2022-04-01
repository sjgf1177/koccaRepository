//**********************************************************
//1. 제      목: 
//2. 프로그램명: QuestionData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.etest;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QuestionData {
	private String	subj; 		// 과정코드
	private int 	etestnum;	// 문제번호
	private String	lesson;		// 해당일차,  online test : OT
	private String	etesttype;	// etest type : S(single), M(multi), D(short descript)
	private String	text;		// etest text
	private int		addseq;		// 지문번호 (없을경우 9999)
	private String	exptext;	// 해답설명
	private String 	sdesc;
	private String	levels;		// 난이도:first(1) ,second(2),third(3), set level in config
	private String	imgurl;		// Image URL
	private int     branch;		// 분기식과정일 경우 분기 코드, 해당이 안될경우에는 '99'
	private String  mediaurl;	// 미디어파일을 링크시키는 경우 경로
	private String	subj_sub;	// 평가문제 or 기출문제(?);퀴즈 M: 메인, S:서브
	private String	oid;		// OBC 과정일 경우 Object ID
	private String	refurl1;	// 문제와 관련한 학습파일URL1
	private String	refurl2;	// 문제와 관련한 학습파일URL2
	
	private String	realimgurl;		// Image URL
	private String  realmediaurl;	// 미디어파일을 링크시키는 경우 경로
	private String	realrefurl1;	// 문제와 관련한 학습파일URL1
	private String	realrefurl2;	// 문제와 관련한 학습파일URL2
	
	private String	levelsnm;
	private String	etesttypenm;
	
	private double  marks;      // 기준 점수
	private double  getmarks;   // 획득 점수

	public QuestionData() {
		subj     = ""; 		
		etestnum  = 0;	       
		lesson   = "";		       
		etesttype = "";	
		text     = "";		       
		addseq   = 0;		       
		exptext  = "";	       
		sdesc    = "";       
		levels   = "";		    
		imgurl   = "";		       
		branch   = 0;		       
		mediaurl = "";	
		subj_sub = "";	
		oid      = "";		       
		refurl1  = "";	       
		refurl2  = "";	       
		levelsnm   = "";
		etesttypenm = "";
		marks      = 0;
		getmarks   = 0;

		realimgurl   = "";		       
		realmediaurl = "";	
		realrefurl1  = "";	       
		realrefurl2  = "";	       
	}
	
	/**
	 * @return
	 */
	public int getAddseq() {
		return addseq;
	}

	/**
	 * @return
	 */
	public int getBranch() {
		return branch;
	}

	/**
	 * @return
	 */
	public int getETestnum() {
		return etestnum;
	}

	/**
	 * @return
	 */
	public String getETesttype() {
		return etesttype;
	}

	/**
	 * @return
	 */
	public String getExptext() {
		return exptext;
	}

	/**
	 * @return
	 */
	public String getImgurl() {
		return imgurl;
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
	public String getLevels() {
		return levels;
	}

	/**
	 * @return
	 */
	public String getMediaurl() {
		return mediaurl;
	}

	/**
	 * @return
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @return
	 */
	public String getRefurl1() {
		return refurl1;
	}

	/**
	 * @return
	 */
	public String getRefurl2() {
		return refurl2;
	}

	/**
	 * @return
	 */
	public String getSdesc() {
		return sdesc;
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
	public String getSubj_sub() {
		return subj_sub;
	}

	/**
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param i
	 */
	public void setAddseq(int i) {
		addseq = i;
	}

	/**
	 * @param i
	 */
	public void setBranch(int i) {
		branch = i;
	}

	/**
	 * @param i
	 */
	public void setETestnum(int i) {
		etestnum = i;
	}

	/**
	 * @param string
	 */
	public void setETesttype(String string) {
		etesttype = string;
	}

	/**
	 * @param string
	 */
	public void setExptext(String string) {
		exptext = string;
	}

	/**
	 * @param string
	 */
	public void setImgurl(String string) {
		imgurl = string;
	}

	/**
	 * @param string
	 */
	public void setLesson(String string) {
		lesson = string;
	}

	/**
	 * @param i
	 */
	public void setLevels(String string) {
		levels = string;
	}

	/**
	 * @param string
	 */
	public void setMediaurl(String string) {
		mediaurl = string;
	}

	/**
	 * @param string
	 */
	public void setOid(String string) {
		oid = string;
	}

	/**
	 * @param string
	 */
	public void setRefurl1(String string) {
		refurl1 = string;
	}

	/**
	 * @param string
	 */
	public void setRefurl2(String string) {
		refurl2 = string;
	}

	/**
	 * @param string
	 */
	public void setSdesc(String string) {
		sdesc = string;
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
	public void setSubj_sub(String string) {
		subj_sub = string;
	}

	/**
	 * @param string
	 */
	public void setText(String string) {
		text = string;
	}

	/**
	 * @return
	 */
	public String getETesttypenm() {
		return etesttypenm;
	}

	/**
	 * @return
	 */
	public String getLevelsnm() {
		return levelsnm;
	}

	/**
	 * @param string
	 */
	public void setETesttypenm(String string) {
		etesttypenm = string;
	}

	/**
	 * @param string
	 */
	public void setLevelsnm(String string) {
		levelsnm = string;
	}

	/**
	 * @return
	 */
	public double getGetmarks() {
		return getmarks;
	}

	/**
	 * @return
	 */
	public double getMarks() {
		return marks;
	}

	/**
	 * @param d
	 */
	public void setGetmarks(double d) {
		getmarks = d;
	}

	/**
	 * @param d
	 */
	public void setMarks(double d) {
		marks = d;
	}

	/**
	 * @return
	 */
	public String getRealimgurl() {
		return realimgurl;
	}

	/**
	 * @return
	 */
	public String getRealmediaurl() {
		return realmediaurl;
	}

	/**
	 * @return
	 */
	public String getRealrefurl1() {
		return realrefurl1;
	}

	/**
	 * @return
	 */
	public String getRealrefurl2() {
		return realrefurl2;
	}

	/**
	 * @param string
	 */
	public void setRealimgurl(String string) {
		realimgurl = string;
	}

	/**
	 * @param string
	 */
	public void setRealmediaurl(String string) {
		realmediaurl = string;
	}

	/**
	 * @param string
	 */
	public void setRealrefurl1(String string) {
		realrefurl1 = string;
	}

	/**
	 * @param string
	 */
	public void setRealrefurl2(String string) {
		realrefurl2 = string;
	}

}
