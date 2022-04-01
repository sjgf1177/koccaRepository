//**********************************************************
//1. 제      목:
//2. 프로그램명: ExamQuestionBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package com.credu.exam;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.system.SelectionUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ExamQuestionBean {

	private ConfigSet config;
    private int row;

    public ExamQuestionBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
    평가 문제 리스트 
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    @SuppressWarnings("unchecked")
	public ArrayList selectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String ss_subjcourse   = box.getString("s_subjcourse");
        String v_action = box.getStringDefault("p_action",  "change");

        int		v_pageno		= box.getInt("p_pageno");
        int		v_pagesize		= box.getInt("p_pagesize");

        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = getQuestionList(connMgr, ss_subjcourse, v_pageno, v_pagesize);
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
    평가 문제 리스트 
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    @SuppressWarnings("unchecked")
	public ArrayList getQuestionList(DBConnectionManager connMgr, String p_subj, int v_pageno, int v_pagesize) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String	sql  = "";
        String	head_sql		= "";
		String	body_sql		= "";		
        String	group_sql		= "";
        String	order_sql		= "";
		String	count_sql		= "";
        DataBox dbox = null;

        try {
            head_sql = "select a.subj,   a.examnum, a.lesson,  a.examtype, ";
            head_sql+= "       a.examtext,    a.exptext,     ";
            head_sql+= "       a.levels,   a.selcount,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
            head_sql+= "       b.codenm    examtypenm, ";
            head_sql+= "       c.codenm    levelsnm    ";
            body_sql+= "  from tz_exam   a, ";
            body_sql+= "       tz_code   b, ";
            body_sql+= "       tz_code   c  ";
            body_sql+= "   where a.examtype = b.code ";
            body_sql+= "   and a.levels   = c.code ";
            body_sql+= "   and a.subj     = " + SQLString.Format(p_subj);
            body_sql+= "   and b.gubun    = " + SQLString.Format(ExamBean.EXAM_TYPE);
            body_sql+= "   and c.gubun    = " + SQLString.Format(ExamBean.EXAM_LEVEL);
            order_sql+= " order by a.examnum ";

        	sql = head_sql+ body_sql+ group_sql+ order_sql;

            ls = connMgr.executeQuery(sql);

			count_sql = "select count(*) " + body_sql;
			int totalrowcount = BoardPaging.getTotalRow(connMgr, count_sql) ;

            ls.setPageSize(v_pagesize);                 //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();  	//전체 페이지 수를 반환한다

            
            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum",		new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage",		new Integer(total_page_count));
                dbox.put("d_rowcount",  	new Integer(row));
                dbox.put("d_totalrowcount",	new Integer(totalrowcount));

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
    문제 내용 보기
    @param box          receive from the form object and session
    @return QuestionExampleData   평가문제
    */
    @SuppressWarnings("unchecked")
	public ArrayList selectExampleData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_subj     = box.getString("p_subj");
        int    v_examnum  = box.getInt("p_examnum");
        String v_action   = box.getStringDefault("p_action","change");

        try {
            if (v_action.equals("go") && v_examnum > 0) {
                connMgr = new DBConnectionManager();
                list = getExampleData(connMgr, v_subj,  v_examnum);
            } else {
                list = new ArrayList();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }
        return list;
    }

    /**
    문제 내용 보기    
    @param box          receive from the form object and session
    @return QuestionExampleData   평가문제
    */
    @SuppressWarnings("unchecked")
	public ArrayList getExampleData(DBConnectionManager connMgr, String p_subj,  int p_examnum) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = "select a.subj,     a.examnum,  a.lesson,  a.examtype, ";
            sql+= "       a.examtext,      a.exptext,   a.levels,  a.selcount,  a.saveimage,   a.saveaudio, ";
            sql+= "       a.savemovie,  a.saveflash, a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
            sql+= "       b.selnum,   b.seltext,  b.isanswer,  ";
            sql+= "       c.codenm    examtypenm, ";
            sql+= "       d.codenm    levelsnm    ";
            sql+= "  from tz_exam      a, ";
            sql+= "       tz_examsel   b, ";
            sql+= "       tz_code      c, ";
            sql+= "       tz_code      d  ";
			// 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
//            sql+= " where a.subj     = b.subj(+) ";
//            sql+= "   and a.examnum  = b.examnum(+) ";
			sql+= " where a.subj      =  b.subj(+) ";
            sql+= "   and a.examnum   =  b.examnum(+) ";
            sql+= "   and a.examtype  = c.code ";
            sql+= "   and a.levels    = d.code ";
            sql+= "   and a.subj      = " + SQLString.Format(p_subj);
            sql+= "   and a.examnum   = " + SQLString.Format(p_examnum);
            sql+= "   and c.gubun     = " + SQLString.Format(ExamBean.EXAM_TYPE);
            sql+= "   and d.gubun     = " + SQLString.Format(ExamBean.EXAM_LEVEL);
            sql+= " order by a.examnum, b.selnum ";

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
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return list;
    }

    /**
    풀등록 에서 평가문제 리스트 
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    @SuppressWarnings("unchecked")
	public ArrayList selectQuestionPool(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_subj   = box.getString("p_subj");
        String v_action = box.getStringDefault("p_action",  "change");

        int v_uppageno = box.getInt("p_uppageno");
        int v_uppagesize = box.getInt("p_uppagesize");
        
        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = getQuestionPool(connMgr, v_subj, v_uppageno, v_uppagesize);
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
    풀등록 에서 평가문제 리스트     
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    @SuppressWarnings("unchecked")
	public ArrayList getQuestionPool(DBConnectionManager connMgr, String p_subj, int p_uppageno, int p_uppagesize) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;
        String	head_sql		= "";
		String	body_sql		= "";		
        String	group_sql		= "";
        String	order_sql		= "";
		String	count_sql		= "";

        int		v_uppageno		= p_uppageno;
        int		v_uppagesize	= p_uppagesize;

        try {
            head_sql = "select a.subj,   a.examnum, a.lesson,  a.examtype, ";
            head_sql+= "       a.examtext,    a.exptext,     ";
            head_sql+= "       a.levels,   a.selcount,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
            head_sql+= "       b.codenm    examtypenm, ";
            head_sql+= "       c.codenm    levelsnm,    ";
            head_sql+= "       d.subjnm    subjnm    ";
            body_sql+= "  from tz_exam   a, ";
            body_sql+= "       tz_code   b, ";
            body_sql+= "       tz_code   c,  ";
            body_sql+= "       tz_subj   d  ";
            body_sql+= "   where a.examtype = b.code ";
            body_sql+= "   and a.levels   = c.code ";
            body_sql+= "   and a.subj   = d.subj ";
            body_sql+= "   and a.subj    ! = " + SQLString.Format(p_subj);
            body_sql+= "   and b.gubun    = " + SQLString.Format(ExamBean.EXAM_TYPE);
            body_sql+= "   and c.gubun    = " + SQLString.Format(ExamBean.EXAM_LEVEL);
            order_sql+= " order by a.subj, a.examnum ";

        	sql = head_sql+ body_sql+ group_sql+ order_sql ;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
			int totalrowcount = BoardPaging.getTotalRow(connMgr, count_sql) ;

            ls.setPageSize(v_uppagesize);                   	//페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_uppageno, totalrowcount);    //현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();  	//전체 페이지 수를 반환한다

            
            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum",		new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage",	new Integer(total_page_count));
                dbox.put("d_rowcount",  	new Integer(row));
                dbox.put("d_totalrowcount",new Integer(totalrowcount));

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
    평가 문제 를 찾기위한 Pool
    @param box          receive from the form object and session
    @return ArrayList   
    */
    @SuppressWarnings("unchecked")
	public ArrayList selectQuestionPoolList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        
        String	head_sql		= "";
		String	body_sql		= "";		
        String	group_sql		= "";
        String	order_sql		= "";
		String	count_sql		= "";
      
        try {      
           
            String ss_searchtype = box.getString("s_searchtype");
            String ss_searchtext = box.getString("s_searchtext");
            
            String v_action  = box.getString("p_action");
            String v_subj  = box.getString("p_subj");
             
            int		v_uppageno		= box.getInt("p_uppageno");
            int		v_uppagesize	= box.getInt("p_uppagesize");

            list = new ArrayList();
            
            if (v_action.equals("go")) {   
                connMgr = new DBConnectionManager();
                
	            head_sql = "select a.subj,   a.examnum, a.lesson,  a.examtype, ";
	            head_sql+= "       a.examtext,    a.exptext,     ";
	            head_sql+= "       a.levels,   a.selcount,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
	            head_sql+= "       b.codenm    examtypenm, ";
	            head_sql+= "       c.codenm    levelsnm,    ";
	            head_sql+= "       d.subjnm    subjnm    ";
	            body_sql+= "  from tz_exam   a, ";
	            body_sql+= "       tz_code   b, ";
	            body_sql+= "       tz_code   c,  ";
	            body_sql+= "       tz_subj   d  ";
	            body_sql+= "   where a.examtype = b.code ";
	            body_sql+= "   and a.levels   = c.code ";
	            body_sql+= "   and a.subj   = d.subj ";
	            body_sql+= "   and a.subj     != " + SQLString.Format(v_subj);
	            body_sql+= "   and b.gubun    = " + SQLString.Format(ExamBean.EXAM_TYPE);
	            body_sql+= "   and c.gubun    = " + SQLString.Format(ExamBean.EXAM_LEVEL);
	
	            if (ss_searchtype.equals("1")) {  // 과정명
	                body_sql+= "  and d.subjnm like " + SQLString.Format("%"+ss_searchtext+"%");
	            } 
	            else if (ss_searchtype.equals("2")) {  // 문제
	            	body_sql+= "  and a.examtext like " + SQLString.Format("%"+ss_searchtext+"%");
	            } 
	            else if (ss_searchtype.equals("3")) {  // 난이도
	            	body_sql+= "  and c.codenm like " + SQLString.Format("%"+ss_searchtext+"%");
	            }
				else if (ss_searchtype.equals("4")) {  // 문제분류
					body_sql+= "  and b.codenm like " + SQLString.Format("%"+ss_searchtext+"%");
	            }
	
	            order_sql+= " order by a.subj, a.examnum ";
	
	        	sql = head_sql+ body_sql+ group_sql+ order_sql ;

                ls = connMgr.executeQuery(sql);//System.out.println(sql);

				count_sql = "select count(*) " + body_sql;
				int totalrowcount = BoardPaging.getTotalRow(connMgr, count_sql) ;

                ls.setPageSize(v_uppagesize);                   	//페이지당 row 갯수를 세팅한다
                ls.setCurrentPage(v_uppageno, totalrowcount);    //현재페이지번호를 세팅한다.
                int total_page_count = ls.getTotalPage();  	//전체 페이지 수를 반환한다
                
                while (ls.next()) {
                    dbox = ls.getDataBox();

                    dbox.put("d_dispnum",		new Integer(totalrowcount - ls.getRowNum() + 1));
                    dbox.put("d_totalpage",	new Integer(total_page_count));
                    dbox.put("d_rowcount",  	new Integer(row));
                    dbox.put("d_totalrowcount",new Integer(totalrowcount));

                    list.add(dbox);
                }
            }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }	

    /**
    문제 등록   
    @param box          receive from the form object and session
    @return int
    */
    public int insertQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String  v_subj     = box.getString("p_subj");
        int     v_examnum  = 0;
        String  v_lesson   = box.getString("p_lesson");
        String  v_examtype = box.getString("p_examtype");
        String  v_examtext     = box.getString("p_examtext");
        String  v_exptext  = box.getString("p_exptext");
        String  v_levels   = box.getString("p_levels");

        int    v_selnum     = 0;
        String v_seltext    = "";
        String v_isanswer   = "";


        String v_luserid    = box.getSession("userid");

		int    v_selcount     = box.getInt("p_selcount1");

		String v_saveimage       = box.getNewFileName ("p_img1");
		String v_realimage   = box.getRealFileName("p_img1");
        String v_saveaudio     = box.getNewFileName ("p_audio1");
		String v_realaudio   = box.getRealFileName("p_audio1");
        String v_savemovie  = box.getNewFileName("p_movie1");
		String v_realmovie   = box.getRealFileName("p_movie1");
		String v_saveflash      = box.getNewFileName ("p_flash1");
		String v_realflash   = box.getRealFileName("p_flash1");

        try {
            v_examnum = getExamnumSeq(v_subj);

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            isOk = insertTZ_exam(connMgr,   v_subj,     v_examnum,  v_lesson,   v_examtype,    v_examtext,     v_exptext,    v_levels,  v_selcount,   v_saveimage,   v_saveaudio, v_savemovie,   v_saveflash,   v_realimage,   v_realaudio, v_realmovie,   v_realflash, v_luserid );

            sql =  "insert into TZ_EXAMSEL(subj, examnum, selnum, seltext, isanswer, luserid, ldate) ";
            sql+=  " values (?, ?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);

            for (int i=1; i<=10; i++) {
                v_seltext  = box.getString("p_seltext"+String.valueOf(i));
                if (!v_seltext.trim().equals("")) {
                    v_selnum++;
                    v_isanswer = box.getString("p_isanswer"+String.valueOf(i));
                    isOk = insertTZ_examsel(pstmt, v_subj,  v_examnum, v_selnum, v_seltext, v_isanswer, v_luserid);
                }
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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    풀 문제 등록   
    @param box          receive from the form object and session
    @return int
    */
    @SuppressWarnings("unchecked")
	public int insertQuestionPool(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        StringTokenizer st = null;
        String v_tokens  = "";
		ArrayList list = new ArrayList();
		DataBox dbox = null;

        String  v_subj     = box.getString("p_subj");
        String v_luserid    = box.getSession("userid");
        int v_examnum    = 0;

		String s_subj = "";
		int s_examnum = 0;

        Vector  v_checks    = box.getVector("p_checks");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            v_examnum = getExamnumSeq(v_subj);

            for(int i=0; i < v_checks.size(); i++){
               v_tokens = (String)v_checks.elementAt(i); 
			   st = new StringTokenizer(v_tokens,"|");
			       s_subj = (String)st.nextToken();
				   s_examnum = Integer.parseInt((String)st.nextToken());
                   list = getExampleData(connMgr, s_subj, s_examnum);
                   dbox = (DataBox)list.get(0);

                   String v_lesson = "01";
				   String v_examtype = dbox.getString("d_examtype");
				   String v_examtext = dbox.getString("d_examtext");
				   String v_exptext = dbox.getString("d_exptext");
				   String v_levels = dbox.getString("d_levels");
				   int v_selcount = dbox.getInt("d_selcount");
				   String v_saveimage = dbox.getString("d_saveimage");
				   String v_saveaudio = dbox.getString("d_saveaudio");
				   String v_savemovie = dbox.getString("d_savemovie");
				   String v_saveflash = dbox.getString("d_saveflash");
				   String v_realimage = dbox.getString("d_realimage");
				   String v_realaudio = dbox.getString("d_realaudio");
				   String v_realmovie = dbox.getString("d_realmovie");
				   String v_realflash = dbox.getString("d_realflash");

			   isOk = insertTZ_exam(connMgr,   v_subj,     v_examnum,  v_lesson,   v_examtype,    v_examtext,     v_exptext,    v_levels,  v_selcount,   v_saveimage,   v_saveaudio, v_savemovie,   v_saveflash,   v_realimage,   v_realaudio, v_realmovie,   v_realflash, v_luserid );
    
			   sql =  "insert into TZ_EXAMSEL(subj, examnum, selnum, seltext, isanswer, luserid, ldate) ";
               sql+=  " values (?, ?, ?, ?, ?, ?, ?)";
               pstmt = connMgr.prepareStatement(sql);

               for (int j=0; j<list.size(); j++) {
                  dbox  = (DataBox)list.get(j);
				  int v_selnum = dbox.getInt("d_selnum");
				  String v_seltext = dbox.getString("d_seltext");
				  String v_isanswer = dbox.getString("d_isanswer");
                  
					  isOk = insertTZ_examsel(pstmt, v_subj,  v_examnum, v_selnum, v_seltext, v_isanswer, v_luserid);
               }
			   v_examnum++;
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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    등록   
    @param box          receive from the form object and session
    @return int
    */
    public int insertTZ_exam(DBConnectionManager connMgr,
                            String  p_subj,     int     p_examnum,  String  p_lesson,   String  p_examtype,
                            String  p_examtext,     String  p_exptext,   String  p_levels,   int p_selcount, String  p_saveimage,   String  p_saveaudio, String p_savemovie, 
		                    String p_saveflash, String  p_realimage,   String  p_realaudio, String p_realmovie, String p_realflash, String p_luserid ) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_EXAM table
            sql =  " insert into TZ_EXAM ";
            sql+=  " (subj,        examnum,    lesson,     examtype, ";
            sql+=  "  examtext,        exptext,    ";
            sql+=  "  levels,       selcount,  saveimage,     saveaudio,     savemovie,   saveflash,  realimage,     realaudio,     realmovie,   realflash, ";
            sql+=  "  luserid,      ldate   ) ";
            sql+=  " values ";
            sql+=  " (?,         ?,              ?,              ?, ";
            sql+=  "  ?,         ?,     ";
            sql+=  "  ?,         ?,          ?,          ?,            ?,              ?,             ?,                 ?,            ?,              ?, ";
            sql+=  "  ?,         ?) ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_subj);
            pstmt.setInt   ( 2, p_examnum);
            pstmt.setString( 3, p_lesson);
            pstmt.setString( 4, p_examtype);
            pstmt.setString( 5, p_examtext);
            pstmt.setString( 6, p_exptext);
            pstmt.setString( 7, p_levels);
            pstmt.setInt   (8, p_selcount);
            pstmt.setString(9, p_saveimage);
            pstmt.setString(10, p_saveaudio);
            pstmt.setString(11, p_savemovie);
            pstmt.setString(12, p_saveflash);
            pstmt.setString(13, p_realimage);
            pstmt.setString(14, p_realaudio);
            pstmt.setString(15, p_realmovie);
            pstmt.setString(16, p_realflash);
            pstmt.setString(17, p_luserid);
            pstmt.setString(18, FormatDate.getDate("yyyyMMddHHmmss"));

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
    문제 보기 등록   
    @param box          receive from the form object and session
    @return int
    */
    public int insertTZ_examsel(PreparedStatement pstmt, String p_subj,  int p_examnum, int p_selnum, String p_seltext, String p_isanswer, String p_luserid) throws Exception {
        int isOk = 0;

        try {
            pstmt.setString(1, p_subj);
            pstmt.setInt   (2, p_examnum);
            pstmt.setInt   (3, p_selnum);
            pstmt.setString(4, p_seltext);
            pstmt.setString(5, p_isanswer);
            pstmt.setString(6, p_luserid);
            pstmt.setString(7, FormatDate.getDate("yyyyMMddHHmmss"));
            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return isOk;
    }


    /**
    일련번호 구하기 
    @param p_subj
    @return int
    */
    @SuppressWarnings("unchecked")
	public int getExamnumSeq(String p_subj) throws Exception {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn","examnum");
        maxdata.put("seqtable","tz_exam");
        maxdata.put("paramcnt","1");
        maxdata.put("param0","subj");
        maxdata.put("subj",   SQLString.Format(p_subj));

        return SelectionUtil.getSeq(maxdata);
    }


    /**
    문제 수정
    @param box          receive from the form object and session
    @return int  
    */
    public int updateQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
		    ResultSet rs = null;

		
        String  v_subj     = box.getString("p_subj");
		    int     v_examnum     = box.getInt("p_examnum");
        String  v_lesson   = box.getString("p_lesson");
        String  v_examtype = box.getString("p_examtype");
        String  v_examtext     = box.getString("p_examtext");
        String  v_exptext  = box.getString("p_exptext");
        String  v_levels   = box.getString("p_levels");

        int    v_selnum     = 0;
        String v_seltext    = "";
        String v_isanswer   = "";

        String v_luserid    = box.getSession("userid");

		int    v_selcount     = box.getInt("p_selcount1");

		String v_saveimage       = box.getNewFileName ("p_img1");
		String v_realimage   = box.getRealFileName("p_img1");
        String v_saveaudio     = box.getNewFileName ("p_audio1");
		String v_realaudio   = box.getRealFileName("p_audio1");
        String v_savemovie  = box.getNewFileName("p_movie1");
		String v_realmovie   = box.getRealFileName("p_movie1");
		String v_saveflash      = box.getNewFileName ("p_flash1");
		String v_realflash   = box.getRealFileName("p_flash1");

        String v_imgfile      = box.getString("p_img2");
        String v_audiofile      = box.getString("p_audio2");
        String v_moviefile      = box.getString("p_movie2");
        String v_flashfile      = box.getString("p_flash2");
        String v_realimgfile  = box.getString("p_img3");
        String v_realaudiofile  = box.getString("p_audio3");
        String v_realmoviefile  = box.getString("p_movie3");
        String v_realflashfile  = box.getString("p_flash3");
        int sulcnt  = 10;  // 2005.8.20 by 정은년 
        
        if(v_saveimage.length()       == 0) { v_saveimage       = v_imgfile;     }
        if(v_saveaudio.length()     == 0) { v_saveaudio     = v_audiofile;     }
        if(v_savemovie.length()      == 0) { v_savemovie      = v_moviefile;     }
        if(v_saveflash.length()      == 0) { v_saveflash      = v_flashfile;     }
        if(v_realimage.length()      == 0) { v_realimage      = v_realimgfile;     }
        if(v_realaudio.length()   == 0) { v_realaudio   = v_realaudiofile; }
        if(v_realmovie.length() == 0) { v_realmovie = v_realmoviefile; }
        if(v_realflash.length()  == 0) { v_realflash  = v_realflashfile; }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            
            // 수정여부 체크
			// 수정일 : 05.11.25 수정자 : 이나연 _ || 수정
//          sql = " select count(*) cnt from tz_examresult where subj='"+v_subj+"' and ','||exam||',' like '%,"+v_examnum+",%' ";
			sql = " select count(*) cnt from tz_examresult where subj='"+v_subj+"' and ','||exam||',' like '%,"+v_examnum+",%' ";
            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();
		    		if (rs.next()) {
		    				if(rs.getInt("cnt") > 0) isOk = -2;
		    		}
		    		            
						if(isOk==0){
	            isOk = updateTZ_exam(connMgr,   v_subj,     v_examnum,  v_lesson,   v_examtype,    v_examtext,     v_exptext,    v_levels,  v_selcount,   v_saveimage,   v_saveaudio, v_savemovie,   v_saveflash,   v_realimage,   v_realaudio, v_realmovie,   v_realflash, v_luserid );
	
	            isOk = deleteTZ_examsel(connMgr, v_subj, v_examnum);
	
	            sql =  "insert into TZ_EXAMSEL(subj, examnum, selnum, seltext, isanswer, luserid, ldate) ";
	            sql+=  " values (?, ?, ?, ?, ?, ?, ?)";
	            pstmt = connMgr.prepareStatement(sql);
	
	            if (v_examtype.equals("3")) sulcnt = 2; // OX식 
	
	            for (int i=1; i<=sulcnt; i++) {
	                v_seltext  = box.getString("p_seltext"+String.valueOf(i));
	                if (!v_seltext.trim().equals("")) {
	                    v_selnum++;
	                    v_isanswer = box.getString("p_isanswer"+String.valueOf(i));
	                    isOk = insertTZ_examsel(pstmt, v_subj,  v_examnum, v_selnum, v_seltext, v_isanswer, v_luserid);
	                
	                }
	            }
	
	/*
	            for (int i=0; i<v_seltexts.size(); i++) {
	                v_seltext  = (String)v_seltexts.get(i);
	
	                if (!v_seltext.trim().equals("")) {
	                    v_selnum++;
	                    v_isanswer = "N";
	                    for (int j=0; j<v_isanswers.size(); j++) {
	                        v_checked_selnum  = Integer.valueOf((String)v_isanswers.get(j)).intValue();
	                        if (i+1 == v_checked_selnum) {
	                            v_isanswer = "Y";
	                        }
	                    }
	
	                    isOk = InsertTZ_examsel(pstmt, v_subj, v_examnum, v_selnum, v_seltext, v_isanswer, v_luserid);
	                }
	            }
	*/
	
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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }                
            if(rs != null) { try { rs.close(); } catch (Exception e) {} }                    
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
     }


    /**
    문제 삭제 
    @param box
    @return int
    */
     @SuppressWarnings("unchecked")
	public int deleteQuestion(RequestBox box) throws Exception {
          DBConnectionManager connMgr = null;
          int isOk = 0;

          String v_subj       = box.getString("p_subj");
          int    v_examnum     = box.getInt("p_examnum");
          String v_duserid    = box.getSession("userid");

          try {
              connMgr = new DBConnectionManager();
              connMgr.setAutoCommit(false);

              isOk = deleteTZ_exam(connMgr, v_subj, v_examnum, v_duserid);
              if(isOk>0){
                isOk = deleteTZ_examsel(connMgr, v_subj, v_examnum);
              }
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
    문제 수정  
    @param box
    @return int
    */
    public int updateTZ_exam(DBConnectionManager connMgr,
                            String  p_subj,     int     p_examnum,  String  p_lesson,   String  p_examtype,
                            String  p_examtext,     String  p_exptext,   String  p_levels,   int p_selcount, String  p_saveimage,   String  p_saveaudio, String p_savemovie, 
		                    String p_saveflash, String  p_realimage,   String  p_realaudio, String p_realmovie, String p_realflash, String p_luserid ) throws Exception {


        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_EXAM table

            sql =  " update TZ_EXAM ";
            sql+=  "    set lesson   = ?, ";
            sql+=  "        examtype = ?, ";
            sql+=  "        examtext     = ?, ";
            sql+=  "        exptext  = ?, ";
            sql+=  "        levels   = ?, ";
            sql+=  "        selcount   = ?, ";
            sql+=  "        saveimage   = ?, ";
            sql+=  "        saveaudio = ?, ";
            sql+=  "        savemovie = ?, ";
            sql+=  "        saveflash      = ?, ";
            sql+=  "        realimage   = ?, ";
            sql+=  "        realaudio = ?, ";
            sql+=  "        realmovie  = ?, ";
            sql+=  "        realflash  = ?, ";
            sql+=  "        luserid  = ?, ";
            sql+=  "        ldate    = ?  ";
            sql+=  "  where subj     = ?  ";
            sql+=  "    and examnum  = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_lesson);
            pstmt.setString( 2, p_examtype);
            pstmt.setString( 3, p_examtext);
            pstmt.setString( 4, p_exptext);
            pstmt.setString( 5, p_levels);
            pstmt.setInt   (6, p_selcount);
            pstmt.setString(7, p_saveimage);
            pstmt.setString(8, p_saveaudio);
            pstmt.setString(9, p_savemovie);
            pstmt.setString(10, p_saveflash);
            pstmt.setString(11, p_realimage);
            pstmt.setString(12, p_realaudio);
            pstmt.setString(13, p_realmovie);
            pstmt.setString(14, p_realflash);
            pstmt.setString(15, p_luserid);
            pstmt.setString(16, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString( 17, p_subj);
            pstmt.setInt( 18, p_examnum);

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
    문제 삭제 
    @param box
    @return int
    */
    public int deleteTZ_exam(DBConnectionManager connMgr, String p_subj, int p_examnum, String p_duserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
	    	ResultSet rs = null;

        try {
            // 삭제체크
            //sql = " select exam  from tz_examresult where subj='"+p_subj+"' ";
            //sql+= " and ((exam like '%"+SQLString.Format(p_examnum)+",%') or (exam  like '%,"+SQLString.Format(p_examnum)+",%') or (exam = '"+SQLString.Format(p_examnum)+"') or (exam like '%,"+SQLString.Format(p_examnum)+"%')) ";
            
			// 수정일 : 05.11.25 수정자 : 이나연 _ || 수정
//            sql = " select count(*) cnt from tz_examresult where subj='"+p_subj+"' and ','||exam||',' like '%,"+p_examnum+",%' ";
			sql = " select count(*) cnt from tz_examresult where subj='"+p_subj+"' and ','||exam||',' like '%,"+p_examnum+",%' ";
            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();
		    		if (rs.next()) {
		    				if(rs.getInt("cnt") > 0) isOk = -2;
		    		}	
		    		
   		
                        
            if(isOk==0){ 
                
                sql =  " delete from TZ_EXAM ";
                sql+=  "  where subj     = ?  ";
                sql+=  "    and examnum  = ?  ";
    
                pstmt = connMgr.prepareStatement(sql);
                
    						pstmt.setString(1, p_subj);
                pstmt.setInt   (2, p_examnum);
    
                isOk = pstmt.executeUpdate();
            }
       }
       catch(Exception ex) {
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
       }
       finally {
           if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
           if(rs != null) { try { rs.close(); } catch (Exception e) {} }           
       }
       return isOk;
    }


    /**
    문제 보기 삭제 
    @param box
    @return int
    */
    public int deleteTZ_examsel(DBConnectionManager connMgr, String p_subj, int p_examnum) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";

        try {
            //delete TZ_EXAMSEL table
            sql =  " delete from TZ_EXAMSEL ";
            sql+=  "  where subj     = ?  ";
            sql+=  "    and examnum   = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setInt   (2, p_examnum);

//            isOk = 
            	pstmt.executeUpdate();
       }
       catch(Exception ex) {
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception(ex.getMessage());
       }
       finally {
           if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
       }
//     return isOk; // 주관식,논술식일경우 없을수 있으므로
       return 1;
    }



    /**
	과정 컨텐츠타입 구하기
    @param box          receive from the form object and session
    @return string
    */
    public String getContentType(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
		String p_subj = box.getString("p_subj");
        String result = "";
        try {
			connMgr = new DBConnectionManager();
            sql =  " select contenttype from tz_subj where subj="+SQLString.Format(p_subj);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
				dbox = ls.getDataBox();
                result = dbox.getString("d_contenttype");
            }
       }
       catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
       }
       finally {
           if(ls != null) { try { ls.close(); } catch (Exception e) {} }
           if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
       }
       return result;
    }

    // 문제지와 문제를 디비에서 가져옴
    @SuppressWarnings("unchecked")
     public ArrayList getExamDataFromDb(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";

        try {
			connMgr = new DBConnectionManager();
            sql="  SELECT   f.subjnm,a.subj,\n";
            sql +=" a.examnum,a.lesson,a.examtype,a.examtext,\n";
            sql +=" a.exptext,b.codenm examtypenm,c.codenm levelsnm,e.selnum,\n";
            sql +=" e.seltext,e.isanswer\n";
            sql +=" FROM   tz_exam a, tz_code b, tz_code c,tz_examsel e,tz_subj f\n";
            sql +=" WHERE       a.examtype = b.code\n";
            sql +="       AND a.levels = c.code\n";
            sql +="       and a.subj = e.subj(+)\n";
            sql +="       AND a.EXAMNUM = e.examnum(+)\n";
            sql +="       and a.subj=f.subj\n";
            sql +="       AND a.subj = '"+ box.getString("s_subjcourse")+"'";
            sql +="       AND b.gubun = "+SQLString.Format(ExamBean.EXAM_TYPE);
            sql +="       AND c.gubun = "+SQLString.Format(ExamBean.EXAM_LEVEL);
            sql +=" ORDER BY   a.examnum,e.selnum";
                            
            ls = connMgr.executeQuery(sql);

			int totalrowcount = 65000;

            ls.setPageSize(65000);                 //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(1, totalrowcount); //현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();  	//전체 페이지 수를 반환한다

            while (ls.next()) {
				dbox = ls.getDataBox();
                dbox.put("d_dispnum",		new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage",		new Integer(total_page_count));
                dbox.put("d_rowcount",  	new Integer(row));
                dbox.put("d_totalrowcount",	new Integer(totalrowcount));
                list.add(dbox);
            }
       }
       catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
       }
       finally {
           if(ls != null) { try { ls.close(); } catch (Exception e) {} }
           if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
       }
       return list;
    }
}
