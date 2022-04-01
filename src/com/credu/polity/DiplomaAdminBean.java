//**********************************************************
//1. 제      목: 수료증  관리 (어드민)
//2. 프로그램명: DiplomaAdminBean.java
//3. 개      요: 게시판
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 하경태 2006.01.10
//7. 수      정:
//**********************************************************
package com.credu.polity;

import java.util.ArrayList;

import com.credu.course.SubjGongAdminBean;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;


@SuppressWarnings("unchecked")
public class DiplomaAdminBean {

	private ConfigSet config;
	private int row;
//	private static final String FILE_TYPE = "p_file";           //      파일업로드되는 tag name
//	private static final int FILE_LIMIT = 1;                    //    페이지에 세팅된 파일첨부 갯수

	public DiplomaAdminBean() {
	  try{
	      config = new ConfigSet();
	      row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
	  }
	  catch(Exception e) {
	      e.printStackTrace();
	  }
}

	/**
    수료명단 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
	public ArrayList selectList(RequestBox box) throws Exception {
//		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls         = null;
        ArrayList list     = null;
//        String sql1         = "";
//        String sql2         = "";
        DataBox dbox = null;

//        String  v_Bcourse   = ""; //이전코스
//        String  v_course    = ""; //현재코스
//        String  v_Bcourseseq= ""; //이전코스차수
//        String  v_courseseq = ""; //현재코스차수
        int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
//        int     l           = 0;
        
        String v_search     = box.getString("p_search");
		String v_searchtext = box.getString("p_searchtext");
        
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");       //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");        //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");        //교육차수
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //과정분류
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //과정분류
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //과정분류
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");   //과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //과정 차수
//        String  ss_action   = box.getString("s_action");

//        String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
//        String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";
        String group_sql  = "";
        String order_sql  = "";

//        ManagerAdminBean bean = null;
//        String  v_sql_add   = "";
//        String  v_userid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            head_sql = "select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,c.subjseqgr,A.serno, ";
			head_sql += "	C.isonoff, B.resno, B.membergubun, ";
			head_sql += " 	B.userid, B.name, C.edustart, C.eduend, A.tstep, A.avtstep, ";
			head_sql += "	A.score,A.isgraduated,B.email,B.ismailing,C.place, B.MemberGubun ";
			body_sql += " from TZ_STOLD A,TZ_MEMBER B,VZ_SCSUBJSEQ C ";
			body_sql += " where c.isclosed='Y' /*and a.isgoyong = 'Y'*/ and a.userid = b.userid and A.subj=C.subj ";
			body_sql += "	and A.year=C.year and A.subjseq=C.subjseq and A.isgraduated='Y' ";

            if (!ss_grcode.equals("ALL")) {
				body_sql += " and C.grcode = "+SQLString.Format(ss_grcode);
            }
            if (!ss_grseq.equals("ALL")) {
				body_sql += " and C.grseq = "+SQLString.Format(ss_grseq);
            }
            if (!ss_uclass.equals("ALL")) {
				body_sql += " and C.scupperclass = "+SQLString.Format(ss_uclass);
            }
            if (!ss_mclass.equals("ALL")) {
				body_sql += " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
            }
            if (!ss_lclass.equals("ALL")) {
				body_sql += " and C.sclowerclass = "+SQLString.Format(ss_lclass);
            }

            if (!ss_subjcourse.equals("ALL")) {
				body_sql += " and C.scsubj = "+SQLString.Format(ss_subjcourse);
            }
            if (!ss_subjseq.equals("ALL")) {
				body_sql += " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
            }
			if (!ss_gyear.equals("ALL")) {
				body_sql += " and C.scyear = "+SQLString.Format(ss_gyear);
            }
			
			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				if (v_search.equals("userid")) {                          //    제목으로 검색할때
					body_sql +=" and b.userid like " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n";                      			                      			
				} else if (v_search.equals("name")) {                //    내용으로 검색할때
					body_sql +=" and b.name like " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n";                      		
				}
			}

			order_sql += " order by b.ldate desc ";

			sql= head_sql+ body_sql+group_sql+ order_sql;

			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;

	        int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);	//     전체 row 수를 반환한다
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize);             		//  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.

	        while (ls.next()) {
	            dbox = ls.getDataBox();

	            dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount",	new Integer(total_row_count));
	            list.add(dbox);

		    }
        }
	    catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql);
	        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls != null) { try { ls.close(); }catch (Exception e) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
		return list;
	}

	 /**
    수료명단 리스트(Excel)
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectExcelList(RequestBox box) throws Exception {
//		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls         = null;
        ArrayList list     = null;
//        String sql1         = "";
//        String sql2         = "";
        DataBox dbox = null;

//        String  v_Bcourse   = ""; //이전코스
//        String  v_course    = ""; //현재코스
//        String  v_Bcourseseq= ""; //이전코스차수
//        String  v_courseseq = ""; //현재코스차수
//        int v_pageno        = box.getInt("p_pageno");
//        int     l           = 0;
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");       //교육그룹
//        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");        //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");        //교육차수
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //과정분류
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //과정분류
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //과정분류
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");   //과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //과정 차수
//        String  ss_action   = box.getString("s_action");

//        String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
//        String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

        String sql    	  = "";
//        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";
        String group_sql  = "";
        String order_sql  = "";

//        ManagerAdminBean bean = null;
//        String  v_sql_add   = "";
//        String  v_userid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            head_sql = "select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,c.subjseqgr,A.serno, ";
			head_sql += "	C.isonoff, B.resno, B.membergubun, ";
			head_sql += " 	B.userid, B.name, C.edustart, C.eduend, A.tstep, A.avtstep, ";
			head_sql += "	A.score,A.isgraduated,B.email,B.ismailing,C.place, B.MemberGubun ";
			body_sql += " from TZ_STOLD A,TZ_MEMBER B,VZ_SCSUBJSEQ C where c.isclosed = 'Y'  ";
			body_sql += " 	and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";
            if (!ss_grcode.equals("ALL")) {
				body_sql += " and C.grcode = "+SQLString.Format(ss_grcode);
            }
            if (!ss_grseq.equals("ALL")) {
				body_sql += " and C.grseq = "+SQLString.Format(ss_grseq);
            }

            if (!ss_uclass.equals("ALL")) {
				body_sql += " and C.scupperclass = "+SQLString.Format(ss_uclass);
            }
            if (!ss_mclass.equals("ALL")) {
				body_sql += " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
            }
            if (!ss_lclass.equals("ALL")) {
				body_sql += " and C.sclowerclass = "+SQLString.Format(ss_lclass);
            }

            if (!ss_subjcourse.equals("ALL")) {
				body_sql += " and C.scsubj = "+SQLString.Format(ss_subjcourse);
            }
            if (!ss_subjseq.equals("ALL")) {
				body_sql += " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
            }

			order_sql += " order by b.ldate desc ";

			sql= head_sql+ body_sql+group_sql+ order_sql;
System.out.println("sql = " + sql);
			ls = connMgr.executeQuery(sql);
			/*
			count_sql= "select count(*) "+ body_sql;

			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

	        ls.setPageSize(row);                       //  페이지당 row 갯수를 세팅한다
	        ls.setCurrentPage(v_pageno, total_row_count);	// 현재페이지번호를 세팅한다.
	        int totalpagecount = ls.getTotalPage();    		// 전체 페이지 수를 반환한다
	*/
	        while (ls.next()) {
	            dbox = ls.getDataBox();

	            //dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
//		            dbox.put("d_totalpage", new Integer(totalpagecount));
//		            dbox.put("d_rowcount", new Integer(row));
//		            dbox.put("d_total_rowcount", new Integer(total_row_count));
	            list.add(dbox);

		    }
        }
	    catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql);
	        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls != null) { try { ls.close(); }catch (Exception e) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
		return list;
	}

	/** 수료증 출력
    @param box      receive from the form object and session
    @return ArrayList
    */
     public DataBox diplomaPrint(RequestBox box) throws Exception {
//		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls         = null;
//        ArrayList list     = null;
//        String sql1         = "";
//        String sql2         = "";
        DataBox dbox = null;

		String sql    	  = "";
		String v_userid 	= box.getString("p_userid");
		String v_grcode 	= box.getString("p_grcode").equals("") ? box.getString("s_grcode") : box.getString("p_grcode");
		String v_subj	 	= box.getString("p_subj");
		String v_year	 	= box.getString("p_year");
		String v_subjseq 	= box.getString("p_scsubjseq");
		String v_kind = box.getString("p_kind");
		
		box.put("p_subjseq", v_subjseq);
		
		SubjGongAdminBean bean = new SubjGongAdminBean();
		String isalways  = bean.getIsalways(box);
		System.out.println("isalways :" + isalways);
//System.out.println(v_userid + " : "  + v_grcode + " : "  +  v_subj + " : "  +  v_year + " : "  +  v_subjseq);
//        ManagerAdminBean bean = null;
//        String  v_sql_add   = "";

        try {
            connMgr = new DBConnectionManager();
//            list = new ArrayList();
			if(v_kind.equals("1")){
				sql = " Select \n";
				sql +=" 	s.name, s.userid, s.subj, q.subjnm, s.serno,  (select count(subj) from tz_subjlesson where subj=q.subj) grseq, \n";
				if(("Y").equals(isalways)){
				    if(v_grcode.equals("N000001")){
    					sql +=" 	to_char(TO_DATE(substring(s.edustart, 1,8),'YYYYMMDD'), 'YY.MM.DD') as edustart,   \n";
    					sql +=" 	to_char(TO_DATE(substring(s.eduend, 1, 8),'YYYYMMDD'), 'YY.MM.DD')  as eduend,     \n";
				    } else {
    				    sql +="     to_char(TO_DATE(substring(q.edustart, 1,8),'YYYYMMDD'), 'YY.MM.DD') as edustart,   \n";
                        sql +="     to_char(TO_DATE(substring(s.eduend, 1, 8),'YYYYMMDD'), 'YY.MM.DD')  as eduend,     \n";
    				}
				} else {  
					sql +=" 	to_char(TO_DATE(substring(q.edustart, 1,8),'YYYYMMDD'), 'YY.MM.DD') as edustart,   \n";
					sql +=" 	to_char(TO_DATE(substring(q.eduend, 1, 8),'YYYYMMDD'), 'YY.MM.DD')  as eduend,     \n";	
				}
				sql +=" 	substring(m.resno, 1,6) ||' - '||	substring(m.resno, 7,13) as resno, z.isunit, z.degree,nvl(j.YEUNSUNO,'') as YEUNSUNO, m.grcode \n";
				sql +=" From TZ_STOLD s	                 \n";
				sql +=" 	join TZ_SUBJSEQ q            \n"; 
				sql +=" 		on s.subj = q.subj and s.year = q.year and s.subjseq = q.subjseq                   \n";
				sql +=" 	join TZ_MEMBER m on s.userid = m.userid and m.grcode = q.grcode                        \n";
				sql +=" 	join tz_subj z on z.subj = s.subj                                           \n";
                sql +="    left join TZ_PROPOSE_ADDINFO j on s.subj=j.subj and s.year=j.year and s.subjseq=j.subjseq and s.userid=j.userid  \n";
				sql +=" Where q.grcode = '" + v_grcode + "' and s.userid = '" + v_userid + "'   \n";
				sql +="		and s.year = '" + v_year + "' and s.subj = '" + v_subj + "' and s.subjseq = '" + v_subjseq + "'      \n";
				
			}else if(v_kind.equals("2")){
				sql = " Select ";
				sql +=" 	s.name, s.userid, s.subj, s.subjnm, s.serno, (select count(subj) from tz_subjlesson where subj=q.subj) grseq,";
				sql +=" 	to_char(TO_DATE(substring(s.edustart, 1,8), 'YYYYMMDD'), 'YY.MM.DD') as edustart,";
				sql +=" 	to_char(TO_DATE(substring(s.eduend, 1, 8), 'YYYYMMDD'), 'YY.MM.DD')  as eduend, ";
				sql +=" 	substring(m.resno, 1,6) ||' - '||	substring(m.resno, 7,13) as resno ";
				sql +=" From TZ_STOLDHST s	";
				sql +=" 	join TZ_MEMBER m on s.userid = m.userid ";
				sql +=" Where s.grcode = '" + v_grcode + "' and s.userid = '" + v_userid + "'";
				sql +="		and s.year = '" + v_year + "' and s.subj = '" + v_subj + "' and s.subjseq = '" + v_subjseq + "'";

			}

			System.out.println("sql = " + sql);
			
			ls = connMgr.executeQuery(sql);
	        while (ls.next()) {
	            dbox = ls.getDataBox();
		    }
        }
	    catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql);
	        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls != null) { try { ls.close(); }catch (Exception e) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
		return dbox;
	}

 	/** 수료증 출력 off-line
     @param box      receive from the form object and session
     @return ArrayList
     */
      public DataBox diplomaOffPrint(RequestBox box) throws Exception {
         DBConnectionManager connMgr = null;
         ListSet ls         = null;
         DataBox dbox = null;

 		String sql    	  = "";
 		String v_userid 	= box.getString("p_userid");
 		String v_subj	 	= box.getString("p_subj");
 		String v_year	 	= box.getString("p_year");
 		String v_subjseq 	= box.getString("p_scsubjseq");

 		//System.out.println(v_userid + " : "  + v_grcode + " : "  +  v_subj + " : "  +  v_year + " : "  +  v_subjseq);

         try {
             connMgr = new DBConnectionManager();
             
             sql = " SELECT M.NAME, S.USERID, S.SUBJ, Q.SUBJNM, S.SERNO, ";
        	 sql += "       TO_CHAR (TO_DATE (SUBSTRING (Q.EDUSTART, 1, 8), 'YYYYMMDD'), ";
        	 sql += "                'YYYY.MM.DD' ";
        	 sql += "               ) AS EDUSTART, ";
        	 sql += "       TO_CHAR (TO_DATE (SUBSTRING (Q.EDUEND, 1, 8), 'YYYYMMDD'), ";
        	 sql += "                'YYYY.MM.DD' ";
        	 sql += "               ) AS EDUEND, ";
        	 sql += "          SUBSTRING (M.RESNO, 1, 6) ";
        	 sql += "       || ' - ' ";
        	 sql += "       || SUBSTRING (M.RESNO, 7, 13) AS RESNO, ";
        	 sql += "       Q.EDUDAYS, Q.EDUDAYSTYPE, N.MIDDLECLASS ";
             sql += "       , Q.YEAR, Q.SUBJSEQ ";
        	 sql += "  FROM TZ_OFFSTUDENT S JOIN TZ_OFFSUBJSEQ Q ";
        	 sql += "       ON S.SUBJ = Q.SUBJ AND S.YEAR = Q.YEAR AND S.SUBJSEQ = Q.SUBJSEQ ";
        	 sql += "       JOIN TZ_MEMBER M ON S.USERID = M.USERID ";
        	 sql += "       JOIN TZ_OFFSUBJ N ON S.SUBJ = N.SUBJ ";
        	 sql += " WHERE S.USERID = " + SQLString.Format(v_userid);
        	 sql += "   AND S.YEAR = " + SQLString.Format(v_year);
        	 sql += "   AND S.SUBJ = " + SQLString.Format(v_subj);
        	 sql += "   AND S.SUBJSEQ = " + SQLString.Format(v_subjseq);
        	 sql += "   AND Q.SEQ = '1'";
 			
        	 ls = connMgr.executeQuery(sql);
        	 
        	 while (ls.next()) {
 	            dbox = ls.getDataBox();
        	 }
        	 
        } catch (Exception ex) {
 	        ErrorManager.getErrorStackTrace(ex, box, sql);
 	        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
 	    } finally {
 	        if(ls != null) { try { ls.close(); }catch (Exception e) {} }
 	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
 	    }
 	    
 		return dbox;
 	}
}