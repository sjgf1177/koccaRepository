//**********************************************************
//1. 제      목: 교육차수관리화면용 DATA
//2. 프로그램명: CourseProgressData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 정상진 2003. 07. 22
//7. 수      정: 
//                 
//**********************************************************
package com.credu.course;

import java.util.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CourseProgressData {

        private String  grcode      ;
        private String  grcodenm    ;
        private String  grseq       ;
        private String  gyear       ;
        private String  grseqnm     ;
        private String  subj        ;
        private String  subjnm      ;
        private String  year        ;
        private String  subjseq     ;
		private String  subjseqgr   ;
        private String  course      ;
        private String  cyear       ;
        private String  courseseq   ;
        private String  coursenm    ;
        
        private String  isbelongcourse ;
        private String  propstart      ;
        private String  propend        ;
        private String  edustart       ;
        private String  eduend         ;
        private String  isclosed       ;
        private String  isgoyong       ;
        private String  ismultipaper   ;
        private String  luserid        ;
        private String  ldate          ;

        private String  isonoff    ;
        private String  subjtypenm ;
        private String  propose    ;
        private String  chkfirst   ;
        private String  chkfinal   ;
        private String  student    ;
        private String  completion ;
        private int     cnt_propose     = 0;
        private int     cnt_chkfirst    = 0;
        private int     cnt_chkfinal    = 0;
        private int     cnt_student     = 0;
        private int     cnt_completion  = 0;

        private String  isnewcourse ;
        private int     rowspan     ;

        public  CourseProgressData() {}


        public String getSubjtypenm(){
            if (isonoff==null || isonoff.equals(""))        return "aaa";
            if (isonoff.equals("ON"))       return "사이버";
            else                                            return "집합";
        }


		/**
		 * @return
		 */
		public String getChkfinal() {
			return chkfinal;
		}

		/**
		 * @return
		 */
		public String getChkfirst() {
			return chkfirst;
		}

		/**
		 * @return
		 */
		public int getCnt_chkfinal() {
			return cnt_chkfinal;
		}

		/**
		 * @return
		 */
		public int getCnt_chkfirst() {
			return cnt_chkfirst;
		}

		/**
		 * @return
		 */
		public int getCnt_completion() {
			return cnt_completion;
		}

		/**
		 * @return
		 */
		public int getCnt_propose() {
			return cnt_propose;
		}

		/**
		 * @return
		 */
		public int getCnt_student() {
			return cnt_student;
		}

		/**
		 * @return
		 */
		public String getCompletion() {
			return completion;
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
		public String getGrcode() {
			return grcode;
		}

		/**
		 * @return
		 */
		public String getGrcodenm() {
			return grcodenm;
		}

		/**
		 * @return
		 */
		public String getGrseq() {
			return grseq;
		}

		/**
		 * @return
		 */
		public String getGrseqnm() {
			return grseqnm;
		}

		/**
		 * @return
		 */
		public String getGyear() {
			return gyear;
		}

		/**
		 * @return
		 */
		public String getIsbelongcourse() {
			return isbelongcourse;
		}

		/**
		 * @return
		 */
		public String getIsclosed() {
			return isclosed;
		}

		/**
		 * @return
		 */
		public String getIsgoyong() {
			return isgoyong;
		}

		/**
		 * @return
		 */
		public String getIsmultipaper() {
			return ismultipaper;
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
		public String getIsonoff() {
			return isonoff;
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
		public String getPropend() {
			return propend;
		}

		/**
		 * @return
		 */
		public String getPropose() {
			return propose;
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
		public int getRowspan() {
			return rowspan;
		}

		/**
		 * @return
		 */
		public String getStudent() {
			return student;
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
		public String getYear() {
			return year;
		}

		/**
		 * @param string
		 */
		public void setChkfinal(String string) {
			chkfinal = string;
		}

		/**
		 * @param string
		 */
		public void setChkfirst(String string) {
			chkfirst = string;
		}

		/**
		 * @param i
		 */
		public void setCnt_chkfinal(int i) {
			cnt_chkfinal = i;
		}

		/**
		 * @param i
		 */
		public void setCnt_chkfirst(int i) {
			cnt_chkfirst = i;
		}

		/**
		 * @param i
		 */
		public void setCnt_completion(int i) {
			cnt_completion = i;
		}

		/**
		 * @param i
		 */
		public void setCnt_propose(int i) {
			cnt_propose = i;
		}

		/**
		 * @param i
		 */
		public void setCnt_student(int i) {
			cnt_student = i;
		}

		/**
		 * @param string
		 */
		public void setCompletion(String string) {
			completion = string;
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
		 * @param string
		 */
		public void setGrcode(String string) {
			grcode = string;
		}

		/**
		 * @param string
		 */
		public void setGrcodenm(String string) {
			grcodenm = string;
		}

		/**
		 * @param string
		 */
		public void setGrseq(String string) {
			grseq = string;
		}

		/**
		 * @param string
		 */
		public void setGrseqnm(String string) {
			grseqnm = string;
		}

		/**
		 * @param string
		 */
		public void setGyear(String string) {
			gyear = string;
		}

		/**
		 * @param string
		 */
		public void setIsbelongcourse(String string) {
			isbelongcourse = string;
		}

		/**
		 * @param string
		 */
		public void setIsclosed(String string) {
			isclosed = string;
		}

		/**
		 * @param string
		 */
		public void setIsgoyong(String string) {
			isgoyong = string;
		}

		/**
		 * @param string
		 */
		public void setIsmultipaper(String string) {
			ismultipaper = string;
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
		public void setIsonoff(String string) {
			isonoff = string;
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
		public void setPropend(String string) {
			propend = string;
		}

		/**
		 * @param string
		 */
		public void setPropose(String string) {
			propose = string;
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
		public void setRowspan(int i) {
			rowspan = i;
		}

		/**
		 * @param string
		 */
		public void setStudent(String string) {
			student = string;
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
		public void setSubjtypenm(String string) {
			subjtypenm = string;
		}

		/**
		 * @param string
		 */
		public void setYear(String string) {
			year = string;
		}

}