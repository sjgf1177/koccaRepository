//**********************************************************

//1. 제      목:
//2. 프로그램명: ExamMasterBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package com.credu.exam;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ExamMasterBean {

    public ExamMasterBean() {}



    /**
    마스타 리스트
    @param box          receive from the form object and session
    @return ArrayList
    */
    @SuppressWarnings("unchecked")
	public ArrayList selectExamMasterList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
        String ss_middleclass = box.getStringDefault("s_middleclass","ALL");
        String ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
        String ss_subjcourse  = box.getString("s_subjcourse");
        String v_action      = box.getStringDefault("p_action","change");

        try {
            connMgr = new DBConnectionManager();
            if (v_action.equals("go")) {
                list = getExamMasterList(connMgr, ss_upperclass, ss_middleclass, ss_lowerclass, ss_subjcourse);
            } else {
                list = new ArrayList();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }


    /**
    마스타 리스트
    @param box          receive from the form object and session
    @return ArrayList
    */
    public ArrayList getExamMasterList(DBConnectionManager connMgr, String p_upperclass, String p_middleclass, String p_lowerclass,String p_subjcourse) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox    = null;
        String v_subj_bef = "";

        try {
            sql = "select b.subj,      a.lesson,  a.startdt,  a.enddt,   \n";
            sql+= "       a.examtype,     a.lessonstart, a.lessonend,  a.examtime, a.exampoint, a.examcnt, a.totalscore, a.retrycnt, a.progress,  \n";
            sql+= "       a.cntlevel1, a.cntlevel2, a.cntlevel3, a.level1text, a.level2text, a.level3text, a.isopenanswer,   \n";
            sql+= "       a.isopenexp,  a.retrycnt,  b.subjnm,  get_codenm(" + SQLString.Format(ExamBean.PTYPE) + ", NVL(a.examtype, '')) examtypenm   \n";
            sql+= "  from tz_exammaster a,   \n";
            sql+= "       tz_subj       b    \n";
			sql+= " where a.subj          = b.subj(+)    \n";

            if (!p_upperclass.equals("ALL") && p_middleclass.equals("ALL")) {
                sql += " and b.upperclass = " + SQLString.Format(p_upperclass);
            }
            else if (!p_upperclass.equals("ALL") && !p_middleclass.equals("ALL") && p_lowerclass.equals("ALL")) {
                sql += " and b.upperclass = " + SQLString.Format(p_upperclass);
                sql += " and b.middleclass = " + SQLString.Format(p_middleclass);
            }
            else if (!p_upperclass.equals("ALL") && !p_middleclass.equals("ALL") && !p_lowerclass.equals("ALL")) {
                sql += " and b.upperclass = " + SQLString.Format(p_upperclass);
                sql += " and b.middleclass = " + SQLString.Format(p_middleclass);
                sql += " and b.middleclass = " + SQLString.Format(p_lowerclass);
            }

            if (!p_subjcourse.equals("ALL"))
                sql+= "   and b.subj       = " + SQLString.Format(p_subjcourse);
            sql+= " order by b.subj, a.lesson, a.examtype ";

			ls = connMgr.executeQuery(sql);
            while (ls.next()) {

                dbox = ls.getDataBox();
                    list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return list;
    }

    /**
    평가 마스터 상중하 문제수 가져오기
    @param box                receive from the form object and session
    @return ExamMasterData  조회한 마스터정보
    */
    public ArrayList selectExamLevels(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList lessonlist = new ArrayList();
		ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        String v_subj    = box.getString("p_subj");
        String v_lessonstart    = box.getString("p_lessonstart");
        String v_lessonend    = box.getString("p_lessonend");

        int v_startlesson = Integer.parseInt(v_lessonstart);
        int v_endlesson = Integer.parseInt(v_lessonend);

		int v_lesson = 0;
		int v_examtype = 0;
		int v_levels = 0;

        try {
            connMgr = new DBConnectionManager();

            for ( int i = v_startlesson; i <= v_endlesson ;  i ++ ){
                 v_lesson = i;
				 ArrayList levelslist = new ArrayList();
                 for ( int j = 1; j <= 3; j++ ){
                     v_levels = j;
                     ArrayList typelist = new ArrayList();
                     for ( int k =1; k <= 3 ; k++){  // by 정은년 (2005.8.20)
                         v_examtype = k;


						 sql = "select count(levels) levelscount  ";
                         sql+= " from tz_exam ";
                         sql+= "  where subj  = " + SQLString.Format(v_subj) ;
                         sql+= "  and lesson  = " + SQLString.Format(v_lesson) ;
                         sql+= "  and examtype = '"+SQLString.Format(v_examtype)+"' " ;
                         sql+= "  and levels  =  '"+SQLString.Format(v_levels)+"' "   ;    

						 ls = connMgr.executeQuery(sql);

			             while (ls.next()) {
                              dbox = ls.getDataBox();
                              typelist.add(dbox);
                         }
                         if(ls != null) { try { ls.close(); } catch (Exception e) {} }
					 }
					 levelslist.add(typelist);
                     
                 }
                 lessonlist.add(levelslist);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return lessonlist;
    }

    /**
    평가 마스터 상중하 문제수 가져오기
    @param box                receive from the form object and session
    @return ExamMasterData  조회한 마스터정보
    */
    public ArrayList selectMasterLevels(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList lessonlist = new ArrayList();
		ListSet ls = null;
        String sql  = "";
		DataBox dbox0 = null;

        StringTokenizer st = null;
        StringTokenizer st2 = null;
        StringTokenizer st3= null;	
        StringTokenizer sst= null;	
		StringTokenizer ssst= null;	

        String v_subj    = box.getString("p_subj");
        String v_lessonstart    = box.getString("p_lessonstart");
        String v_lessonend    = box.getString("p_lessonend");

        int v_startlesson = Integer.parseInt(v_lessonstart);
        int v_endlesson = Integer.parseInt(v_lessonend);

		String v_lesson = box.getString("p_lesson");
		String v_examtype = box.getString("p_examtype");

        try {
            connMgr = new DBConnectionManager();

            dbox0 = getExamMasterData(connMgr, v_subj, v_lesson, v_examtype);

			st = new StringTokenizer(dbox0.getString("d_level1text"), "/");
			st2 = new StringTokenizer(dbox0.getString("d_level2text"), "/");
			st3 = new StringTokenizer(dbox0.getString("d_level3text"), "/");

            for ( int i = v_startlesson; i <= v_endlesson ;  i ++ ){
                
                String ss = "";
        		String ss2 = "";
        		String ss3 = "";
        		String ss4 = "";				

				if(st.hasMoreTokens()) ss = st.nextToken();
				if(st2.hasMoreTokens()) ss2 = st2.nextToken();
				if(st3.hasMoreTokens()) ss3 = st3.nextToken();

				 ArrayList levelslist = new ArrayList();
                 for ( int j = 1; j <= 3; j++ ){

                     if (j==1){
						 ss4 = ss;
                     }else if(j==2){
						 ss4 = ss2;
					 }else if(j==3){
						 ss4 = ss3;
					 }

                     sst = new StringTokenizer(ss4, "|");
		    
        		     ArrayList typelist = new ArrayList();
        		     
        		     String str1 = "";
        		     String str2 = "";
		     
                     for ( int k =1; k <= 3 ; k++){   // by 정은년 2->3
                        
                        if(sst.hasMoreTokens()) 
                        str1 = sst.nextToken();System.out.println("str1" + str1);
                        
                        ssst = new StringTokenizer(str1, ",");
		                for ( int l =1; l <= 3 ; l++){  // by 정은년 2->3
    			            if(ssst.hasMoreTokens()) {
    			                ssst.nextToken();
    			                str2 = ssst.nextToken();System.out.println("str2" + str2);
    			                typelist.add(str2);
                            }                            
                        }
		            }
		            levelslist.add(typelist);
                     
                 }
                 lessonlist.add(levelslist);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return lessonlist;
    }


    /**
    마스타 데이타 
    @param box          receive from the form object and session
    @return DataBox
    */
    public DataBox selectExamMasterData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;

        String v_subj    = box.getString("p_subj");
        String v_lesson  = box.getString("p_lesson");
        String v_examtype   = box.getString("p_examtype");

        try {
            connMgr = new DBConnectionManager();
            dbox = getExamMasterData(connMgr, v_subj, v_lesson, v_examtype);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }



    /**
    마스타 데이타 
    @param box          receive from the form object and session
    @return DataBox
    */
    public DataBox getExamMasterData(DBConnectionManager connMgr, String p_subj, String p_lesson, String p_examtype) throws Exception {
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = "select a.subj,      a.lesson,  a.startdt,  a.enddt,  ";
            sql+= "       a.examtype,     a.lessonstart, a.lessonend, a.retrycnt, a.progress, ";
            sql+= "       a.examtime,   a.exampoint,   a.examcnt,  a.totalscore,  a.cntlevel1, a.cntlevel2, a.cntlevel3, a.level1text, a.level2text, a.level3text, ";
            sql+= "       a.isopenanswer,  a.isopenexp, a.retrycnt, get_codenm(" + SQLString.Format(ExamBean.PTYPE) + ", NVL(a.examtype, '')) examtypenm ";
            sql+= "  from tz_exammaster a ";
            sql+= " where a.subj    = " + SQLString.Format(p_subj);
            sql+= "   and a.lesson  = " + SQLString.Format(p_lesson);
            sql+= "   and a.examtype   = " + SQLString.Format(p_examtype);

            ls = connMgr.executeQuery(sql);     //System.out.println(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return dbox;
    }
    
    
    
    /**
    시험지 여부 
    @param box          receive from the form object and session
    @return boolean
    */
    public boolean isExamPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
        boolean result = false;

        try {
            String v_subj    = box.getString("p_subj");
            String v_lesson  = box.getString("p_lesson");
            String v_examtype   = box.getString("p_examtype");
        
            connMgr = new DBConnectionManager();
            
            sql = "select subj";
            sql+= "  from tz_exampaper ";
            sql+= " where subj    = " + SQLString.Format(v_subj);
            sql+= "   and lesson  = " + SQLString.Format(v_lesson);
            sql+= "   and examtype   = " + SQLString.Format(v_examtype);

            ls = connMgr.executeQuery(sql);     //System.out.println(sql);
            if (ls.next()) {
                result = true;
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }


    /**
    평가 마스터 등록
    @param box                receive from the form object and session
    @return int  
    */
    public int insertExamMaster(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        String sql = "";
        int isOk = 0;

		String  v_subj      	= box.getString("p_subj");
        String  v_lesson    	= box.getString("p_lesson");
        String  v_examtype     	= box.getString("p_examtype");
        String  v_lessonstart 	= box.getString("p_lessonstart");
        String  v_lessonend   	= box.getString("p_lessonend");
        String  v_examtime   	= box.getString("p_examtime");
        int     v_exampoint 	= box.getInt   ("p_exampoint");
        int     v_examcnt 		= box.getInt   ("p_examcnt");
        int     v_totalscore 	= box.getInt   ("p_totalscore");
        String  v_level1text  	= box.getString("p_level1text");
        String  v_level2text  	= box.getString("p_level2text");
        String  v_level3text  	= box.getString("p_level3text");
        int  	v_cntlevel1  	= box.getInt("p_cntlevel1");
        int  	v_cntlevel2  	= box.getInt("p_cntlevel2");
        int  	v_cntlevel3  	= box.getInt("p_cntlevel3");
        String  v_isopenanswer 	= box.getString("p_isopenanswer");
        String  v_isopenexp  	= box.getString("p_isopenexp");
        int     v_retrycnt 		= box.getInt("p_retrycnt");
        int     v_progress 		= box.getInt("p_progress");
		String  v_startdt 		= box.getString("p_startdt");
		String  v_enddt 		= box.getString("p_enddt");		

        String  v_luserid   	= box.getSession("userid");

        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            dbox = getExamMasterData(connMgr, v_subj, v_lesson, v_examtype);
            if (dbox == null) {
               isOk = insertTZ_exammaster(connMgr, v_subj, v_lesson, v_startdt, v_enddt, 
					   v_examtype, v_lessonstart, v_lessonend, v_examtime, v_exampoint, v_examcnt, v_totalscore, v_cntlevel1, v_cntlevel2, v_cntlevel3, v_level1text, v_level2text, v_level3text, v_isopenanswer, v_isopenexp, v_retrycnt, v_progress, v_luserid);
           } else {
              isOk = 0;
           }
        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    평가 마스터 등록 -  응시기간추가 2006.1.
    @param box                receive from the form object and session
    @return int  
    */
    public int insertTZ_exammaster(DBConnectionManager connMgr, String p_subj, String p_lesson,
							String p_startdt, String p_enddt, 
							String p_examtype, String p_lessonstart, String p_lessonend, 
		                    String p_examtime,    int    p_exampoint,     int p_examcnt,      int p_totalscore, 
                            int    p_cntlevel1,  int    p_cntlevel2,  int    p_cntlevel3,  String p_level1text, String p_level2text, String p_level3text, String p_isopenanswer,
                            String    p_isopenexp,  int     p_retrycnt,    int  p_progress,            String p_luserid ) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAMMASTER table
            sql =  " insert into TZ_EXAMMASTER \n";
            sql+=  " (subj,      lesson,    \n";
			sql+=  "  startdt,		enddt,		  \n";			
            sql+=  "  examtype,     lessonstart, lessonend, examtime,  exampoint,  examcnt,  totalscore,  \n";
            sql+=  "  cntlevel1, cntlevel2, cntlevel3, level1text, level2text, level3text, isopenanswer,  isopenexp,  \n";
            sql+=  "  retrycnt, progress, luserid,   ldate   )  \n";
            sql+=  " values ";
            sql+=  " (?,         ?,          ";
			sql+=  "  ?,	 	?,			 ";
            sql+=  "  ?,         ?,         ?,         ?,            ?,               ?,             ?, ";
            sql+=  "  ?,         ?,         ?,         ?,            ?,                ?,            ?,                 ?,  ";
            sql+=  "  ?,         ?,         ?,       ?  ) ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_subj);
            pstmt.setString( 2, p_lesson);
			pstmt.setString( 3, p_startdt);
			pstmt.setString( 4, p_enddt);			
            pstmt.setString( 5, p_examtype);
            pstmt.setString( 6, p_lessonstart);
            pstmt.setString( 7, p_lessonend);
            pstmt.setString( 8, p_examtime);
			pstmt.setInt   ( 9, p_exampoint);
            pstmt.setInt   ( 10, p_examcnt);
            pstmt.setInt   (11, p_totalscore);
            pstmt.setInt(12, p_cntlevel1);
            pstmt.setInt(13, p_cntlevel2);
            pstmt.setInt(14, p_cntlevel3);
            pstmt.setString(15, p_level1text);
            pstmt.setString(16, p_level2text);
            pstmt.setString(17, p_level3text);
            pstmt.setString(18, p_isopenanswer);
            pstmt.setString(19, p_isopenexp);
            pstmt.setInt   (20, p_retrycnt);
            pstmt.setInt   (21, p_progress);
            pstmt.setString(22, p_luserid);
            pstmt.setString(23, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
        }
        return isOk;
    }


    /**
    평가 마스타 수정 
    @param box          receive from the form object and session
    @return int
    */
    public int updateExamMaster(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

		String  v_subj      = box.getString("p_subj");
        String  v_lesson    = box.getString("p_lesson");
        String  v_examtype     = box.getString("p_examtype");
        String  v_lessonstart = box.getString("p_lessonstart");
        String  v_lessonend   = box.getString("p_lessonend");
        String  v_examtime   = box.getString("p_examtime");
        int     v_exampoint = box.getInt   ("p_exampoint");
        int     v_examcnt = box.getInt   ("p_examcnt");
        int     v_totalscore = box.getInt   ("p_totalscore");
        String  v_level1text  = box.getString("p_level1text");//System.out.println("v_level1text" + v_level1text);
        String  v_level2text  = box.getString("p_level2text");//System.out.println("v_level2text" + v_level2text);
        String  v_level3text  = box.getString("p_level3text");//System.out.println("v_level3text" + v_level3text);
        int  v_cntlevel1  = box.getInt("p_cntlevel1");
        int  v_cntlevel2  = box.getInt("p_cntlevel2");
        int  v_cntlevel3  = box.getInt("p_cntlevel3");
        String  v_isopenanswer = box.getString("p_isopenanswer");
        String  v_isopenexp  = box.getString("p_isopenexp");
        int     v_retrycnt = box.getInt("p_retrycnt");
        int     v_progress = box.getInt("p_progress");
		String  v_startdt = box.getString("p_startdt");
		String  v_enddt	 = box.getString("p_enddt");		

        String  v_luserid   = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

			//평가마스터테이블 update 
            isOk = updateTZ_exammaster(connMgr, v_subj, v_lesson, v_startdt, v_enddt,  
					v_examtype, v_lessonstart, v_lessonend, v_examtime, v_exampoint, 
					v_examcnt, v_totalscore, v_cntlevel1, v_cntlevel2, v_cntlevel3, v_level1text, v_level2text, v_level3text,  v_isopenanswer, v_isopenexp, v_retrycnt, v_progress, v_luserid);
		
        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    평가 마스타 수정 
    @param box          receive from the form object and session
    @return int
    */
    public int updateTZ_exammaster(DBConnectionManager connMgr, String p_subj, String p_lesson, String p_startdt, String p_enddt,  
							String p_examtype, String p_lessonstart, String p_lessonend, 
		                    String p_examtime,    int    p_exampoint,     int p_examcnt,             int p_totalscore, 
                            int    p_cntlevel1,  int    p_cntlevel2,  int    p_cntlevel3,  String p_level1text,  String p_level2text,  String p_level3text,  String p_isopenanswer,
                            String    p_isopenexp,  int     p_retrycnt,        int p_progress,        String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_EXAMMASTER table
            sql =  " update TZ_EXAMMASTER ";
            sql+=  "     set   lessonstart = ?, ";
            sql+=  "        lessonend   = ?, ";
            sql+=  "        examtime = ?, ";
            sql+=  "        exampoint = ?, ";
            sql+=  "        examcnt = ?, ";
            sql+=  "        totalscore  = ?, ";
			sql+=  "        startdt  = ?, ";
			sql+=  "        enddt  = ?, ";			
            sql+=  "        cntlevel1  = ?, ";
            sql+=  "        cntlevel2 = ?, ";
            sql+=  "        cntlevel3 = ?, ";
            sql+=  "        level1text  = ?, ";
            sql+=  "        level2text = ?, ";
            sql+=  "        level3text = ?, ";
            sql+=  "        isopenanswer  = ?, ";
            sql+=  "        isopenexp = ?, ";
            sql+=  "        retrycnt   = ?, ";
            sql+=  "        progress   = ?, ";
            sql+=  "        luserid     = ?,  ";
            sql+=  "        ldate     = ?  ";
            sql+=  "  where subj      = ?  ";
            sql+=  "    and lesson    = ?  ";
            sql+=  "    and examtype     = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString   ( 1, p_lessonstart);
            pstmt.setString( 2, p_lessonend);
            pstmt.setString( 3, p_examtime);
            pstmt.setInt   ( 4, p_exampoint);
            pstmt.setInt   ( 5, p_examcnt);
            pstmt.setInt   ( 6, p_totalscore);
			pstmt.setString   ( 7, p_startdt);
			pstmt.setString   ( 8, p_enddt);
            pstmt.setInt( 9, p_cntlevel1);
            pstmt.setInt   ( 10, p_cntlevel2);
            pstmt.setInt   ( 11, p_cntlevel3);
            pstmt.setString( 12, p_level1text);
            pstmt.setString   ( 13, p_level2text);
            pstmt.setString   ( 14, p_level3text);
            pstmt.setString(15, p_isopenanswer);
            pstmt.setString(16, p_isopenexp);
            pstmt.setInt    (17, p_retrycnt);
            pstmt.setInt    (18, p_progress);
            pstmt.setString(19, p_luserid);
            pstmt.setString(20, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(21, p_subj);
            pstmt.setString(22, p_lesson);
            pstmt.setString(23, p_examtype);

            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
        }
        return isOk;
    }


    /**
    평가 마스타 삭제 
    @param box          receive from the form object and session
    @return int
    */
    public int deleteExamMaster(RequestBox box) throws Exception {
         DBConnectionManager connMgr = null;
         int isOk = 0;

		String  v_subj      = box.getString("p_subj");
        String  v_lesson    = box.getString("p_lesson");
        String  v_examtype     = box.getString("p_examtype");
        String  v_duserid   = box.getSession("userid");

         try {
             connMgr = new DBConnectionManager();
             connMgr.setAutoCommit(false);

             isOk = deleteTZ_exammaster(connMgr, v_subj, v_lesson, v_examtype, v_duserid);
         }
         catch(Exception ex) {
             isOk = 0;
             connMgr.rollback();
             ErrorManager.getErrorStackTrace(ex);
             throw new Exception(ex.getMessage());
         }
         finally {
             if (isOk > 0) {connMgr.commit(); box.put("p_sulnum", String.valueOf("0"));}
             if (connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
         }
         return isOk;
    }


    /**
    평가 마스타 삭제 
    @param box          receive from the form object and session
    @return int
    */
    public int deleteTZ_exammaster(DBConnectionManager connMgr, String p_subj, String p_lesson, String p_examtype, String p_duserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_EXAMMASTER table
            sql =  " delete from TZ_EXAMMASTER ";
            sql+=  "  where subj      = ?  ";
            sql+=  "    and lesson    = ?  ";
            sql+=  "    and examtype     = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_lesson);
            pstmt.setString(3, p_examtype);

            isOk = pstmt.executeUpdate();
       }
       catch(Exception ex) {
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
       }
       finally {
           if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
       }
       return isOk;
    }

}
