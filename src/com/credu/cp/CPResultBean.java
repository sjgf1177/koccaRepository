//**********************************************************
//  1. 제      목: 과정교육결과 리스트 빈
//  2. 프로그램명: CPResultBean.java
//  3. 개      요: 과정교육결과 리스트 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 12. 27
//  7. 수      정:
//**********************************************************

package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;

/**
*과정교육결과 리스트
*<p>제목:CPResultBean.java</p>
*<p>설명:과정교육결과 리스트 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/

public class CPResultBean {
    private ConfigSet config;
    private int row;

    public CPResultBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
    과정교육결과 리스트화면
    @param box          receive from the form object and session
    @return ArrayList   검색 리스트
    */
    public ArrayList selectResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
		/* 2005.11.10_하경태 : TotalCount 해주기 위한 쿼리를 담을 변수 "*/
        String sql     = "";
        String count_sql     = "";
        String head_sql     = "";
		String body_sql     = "";		
        String group_sql     = "";
        String order_sql     = "";
        //BulletinData data = null;
        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_cp = box.getString("p_cp");			//외주업체
        String v_grcode = box.getString("s_grcode");	//교육주관
        String v_grseq  = box.getString("s_grseq");
        String v_gyear = box.getString("s_gyear");		//교육년도
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

			if(v_gyear.equals("")){
				v_gyear = FormatDate.getDate("yyyy");
				box.put("s_gyear",v_gyear);
			}

			head_sql = "select a.subj, a.subjnm, b.year, b.subjseq, b.subjseqgr, b.propstart, b.propend, b.edustart, b.eduend, c.cpnm, b.cpsubjseq, count(d.userid) as usercnt, ";
			head_sql += " b.isclosed, b.iscpresult, b.cpresultrealfile, b.cpresultnewfile, b.cpresultldate, NVL(iscpflag,'N') as iscpflag ";
			body_sql += " from ";
			body_sql += "   tz_subj a, ";
			body_sql += "   tz_subjseq b, ";
			body_sql += "   tz_cpinfo c, ";
			body_sql += "   tz_student d ";
			body_sql += " where               ";
			body_sql += " a.subj = b.subj and ";
			/* 2005.11.10_하경태 : Oracle -> mssql
			sql += " b.subj = d.subj(+) and ";
			sql += " b.year = d.year(+) and ";
			sql += " b.subjseq = d.subjseq(+) and ";
			*/
			body_sql += " b.subj  =  d.subj(+) and ";
			body_sql += " b.year  =  d.year(+) and ";
			body_sql += " b.subjseq  =  d.subjseq(+) and ";

			//교육그룹검색(교육주관)
			if(!v_grcode.equals("")){
				body_sql += " b.grcode = " + SQLString.Format(v_grcode);
				body_sql += " and ";

				if(!v_grseq.equals("ALL")){
					body_sql += " b.grseq = " + SQLString.Format(v_grseq);
					body_sql += " and ";
				}

				//과정명검색
				if ( !v_searchtext.equals("")) {	//    검색어가 있으면
	                //v_pageno = 1;	//      검색할 경우 첫번째 페이지가 로딩된다

					body_sql += " lower(a.subjnm) like " + SQLString.Format("%" + v_searchtext.toLowerCase() + "%");
	                if(!v_gyear.equals("")){
						body_sql += " and ";
	                }
	            }

	            //교육년도 검색
	            if(!v_gyear.equals("") && !v_gyear.equals("ALL")){
					body_sql += " b.gyear = " + SQLString.Format(v_gyear);
					body_sql += " and ";
	            }
	            else if(v_gyear.equals("")){
					body_sql += " b.gyear = " + SQLString.Format(FormatDate.getDate("yyyy"));
					body_sql += " and ";
	            }

	            //외주업체 검색
	            if(!v_cp.equals("") && !v_cp.equals("ALL")){
					body_sql += " a.owner = " + SQLString.Format(v_cp);
					body_sql += " and a.owner = c.cpseq ";
	            }
	            else if(!v_cp.equals("") || v_cp.equals("ALL")){
					body_sql += " a.owner = c.cpseq ";
	            }
	            else{
					body_sql += " a.owner = c.cpseq ";
	            }
	        }
	        else{
				body_sql += " b.grcode = 'zzzzzz'";
	        }

            group_sql += " group by a.subj, a.subjnm, b.year, b.subjseq, b.subjseqgr, b.propstart, b.propend, b.edustart, b.eduend, b.cpsubjseq, ";
			group_sql += " c.cpnm, b.isclosed, b.iscpresult, b.cpresultrealfile, b.cpresultnewfile, b.cpresultldate, b.iscpflag ";

			sql = head_sql + body_sql + group_sql + order_sql;		
			
            ls = connMgr.executeQuery(sql);
			
			count_sql = "select count(*) " + body_sql;      		
			int i_row_count = BoardPaging.getTotalRow(connMgr, count_sql);
			
            System.out.println("sql_resultlist="+sql);

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성

            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, i_row_count);	//	현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();   //  전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();   //  전체 row 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }


    /**
    수강생 리스트
    @param box          receive from the form object and session
    @param v_year		교육년도
    @param v_subj		과정코드
    @param v_subjseq	과정차수
    @return ArrayList   수강생 리스트
    */
    public ArrayList selectStudentList(RequestBox box, String v_year, String v_subj, String v_subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        //String v_year = box.getString("p_year");		//과정년도
        //String v_subj = box.getString("p_subj");		//과정코드
        //String v_subjseq = box.getString("p_subjseq");		//과정차수
        //int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

			sql = "select userid from tz_student ";
			sql += " where subj = " + SQLString.Format(v_subj);
			sql += " and subjseq = " + SQLString.Format(v_subjseq);
			sql += " and year = " + SQLString.Format(v_year);

            //pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성

			ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }


    /**
    수강생인지 체크
    @param v_year		교육년도
    @param v_subj		과정코드
    @param v_subjseq	과정차수
    @param v_userid		사용자아이디
    @return int   		수강생 여부
    */
    public int selectStudent(String v_year, String v_subj, String v_subjseq, String v_userid) throws Exception {
        DBConnectionManager connMgr = null;
        int isOK = 0;
        
        try {
            connMgr = new DBConnectionManager();
            isOK = selectStudent(v_year, v_subj, v_subjseq, v_userid);
        
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOK;
    }
    
    
     /**
    수강생인지 체크
    @param v_year		교육년도
    @param v_subj		과정코드
    @param v_subjseq	과정차수
    @param v_userid		사용자아이디
    @return int   		수강생 여부
    */
    public int selectStudent(DBConnectionManager connMgr, String v_year, String v_subj, String v_subjseq, String v_userid) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        int isOK = 0;
        DataBox dbox = null;

        try {

            list = new ArrayList();

            sql  = "select a.userid ";
            sql += " from tz_student a ";
            sql += " where ";
            sql += " a.userid = "+SQLString.Format(v_userid)+"";
            sql += " and a.year = " + SQLString.Format(v_year);
            sql += " and a.subj = " + SQLString.Format(v_subj);
            sql += " and a.subjseq = " + SQLString.Format(v_subjseq);

			ls = connMgr.executeQuery(sql);

			if(ls.next()) {
                isOK = 1;
            }
            else{
            	isOK = 0;
            }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return isOK;
    }


    /**
    수강생수 확인
    @param v_year		교육년도
    @param v_subj		과정코드
    @param v_subjseq	과정차수
    @return int   		수강생 수
    */
    public int selectStudentCnt(String v_year, String v_subj, String v_subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        int v_cnt = 0;

        try {
            connMgr = new DBConnectionManager();
            v_cnt = selectStudentCnt(connMgr, v_year, v_subj, v_subjseq);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_cnt;
    }
    
    
     /**
    수강생수 확인
    @param v_year		교육년도
    @param v_subj		과정코드
    @param v_subjseq	과정차수
    @return int   		수강생 수
    */
    public int selectStudentCnt(DBConnectionManager connMgr, String v_year, String v_subj, String v_subjseq) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        int isOK = 0;
        int v_cnt = 0;
        DataBox dbox = null;

        try {

            list = new ArrayList();
            sql  = "select  \n";
            sql += "   count(a.userid) cnt  \n";
            sql += " from  \n";
            sql += "   tz_student a   \n";
            sql += " where  \n";
            sql += " year = " + SQLString.Format(v_year);
            sql += " and subj = " + SQLString.Format(v_subj);
            sql += " and subjseq = " + SQLString.Format(v_subjseq);

			ls = connMgr.executeQuery(sql);

			ls.next();
			v_cnt = ls.getInt("cnt");


        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_cnt;
    }

    /**
    외부교육과정 결과 파일 업데이트
    @param v_year		교육년도
    @param v_subj		과정코드
    @param v_subjseq	과정차수
    @param realfilename	실제 결과파일이름
    @param newfilename	새로운 결과파일이름
    @return int   		업데이트 정상처리여부
    */
    public int update_cpresult(String v_year, String v_subj, String v_subjseq, String realfilename, String newfilename)  throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            isOk = update_cpresult(connMgr, v_year, v_subj, v_subjseq, realfilename, newfilename);

        }

        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
    
    
    /**
    외부교육과정 결과 파일 업데이트
    @param v_year		교육년도
    @param v_subj		과정코드
    @param v_subjseq	과정차수
    @param realfilename	실제 결과파일이름
    @param newfilename	새로운 결과파일이름
    @return int   		업데이트 정상처리여부
    */
    public int update_cpresult(DBConnectionManager connMgr, String v_year, String v_subj, String v_subjseq, String realfilename, String newfilename)  throws Exception {
        String sql = "";
        int isOk = 0;

        try {

			sql = " update tz_subjseq set ";
			sql += " iscpresult = 'Y' ";
			sql += ", iscpflag = 'N' ";
			sql += ", cpresultrealfile = " + SQLString.Format(realfilename);
			sql += ", cpresultnewfile = " + SQLString.Format(newfilename);
			sql += ", cpresultldate = " + SQLString.Format(FormatDate.getDate("yyyyMMddHH"));
			sql += " where ";
			sql += " year = " + SQLString.Format(v_year);
			sql += " and subj = " + SQLString.Format(v_subj);
			sql += " and subjseq = " + SQLString.Format(v_subjseq);


            isOk = connMgr.executeUpdate(sql);

        }

        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            
        }
        return isOk;
    }

    /**
    외부교육과정 현황 파일 업데이트
    @param v_year		교육년도
    @param v_subj		과정코드
    @param v_subjseq	과정차수
    @param realfilename	실제 결과파일이름
    @param newfilename	새로운 결과파일이름
    @return int   		업데이트 정상처리여부
    */
    public int update_cpstatus(DBConnectionManager connMgr,String v_year, String v_subj, String v_subjseq, String realfilename, String newfilename, String v_userid)  throws Exception {
        String sql1 = "";
        PreparedStatement pstmt = null;
        ListSet ls = null;
        int v_seq = 0;
        int isOk = 0;

        try {

        	sql1  = " select NVL(max(seq),0)+1 maxseq from TZ_CPUPDATESTATUS ";
            sql1 += "  where subj    = " + StringManager.makeSQL(v_subj);
            sql1 += "    and year    = " + StringManager.makeSQL(v_year);
            sql1 += "    and subjseq = " + StringManager.makeSQL(v_subjseq);

            ls = connMgr.executeQuery(sql1);
            if (ls.next()) {
                v_seq = ls.getInt(1);
            }

			sql1 =  "insert into TZ_CPUPDATESTATUS(subj, year, subjseq, seq, inuserid, indate, newfile, realfile, luserid, ldate)";
            sql1 += " values (?, ?, ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'))                     ";

            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);
		    pstmt.setInt(4, v_seq);
		    pstmt.setString(5, v_userid);
		    pstmt.setString(6, newfilename);
            pstmt.setString(7, realfilename);
            pstmt.setString(8, v_userid);
            isOk = pstmt.executeUpdate();

        }

        catch(Exception ex) {
              ErrorManager.getErrorStackTrace(ex, null, sql1);
              throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
        	if(ls != null)      { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
        }
        return isOk;
    }

    /**
    과정코드정보
    @param box          receive from the form object and session
    @return ArrayList   과정코드정보
    */
    public ArrayList selectExcelSubjDown(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        //String v_year = box.getString("p_year");		//과정년도
        //String v_subj = box.getString("p_subj");		//과정코드
        //String v_subjseq = box.getString("p_subjseq");		//과정차수
        //int v_pageno = box.getInt("p_pageno");

        String v_cp = box.getString("p_cp");			//외주업체
        String v_grcode = box.getString("s_grcode");
        String v_gyear = box.getString("s_gyear");
        String v_grseq = box.getString("s_grseq");

        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = StringManager.substring(s_gadmin, 0, 1);

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql+= " select                \n";
            sql+= "   a.subjnm,             \n";
            sql+= "   a.scsubj,             \n";
            sql+= "   a.scyear,             \n";
            sql+= "   a.scsubjseq,          \n";
            sql+= "   a.subjseqgr,          \n";
            sql+= "   a.edustart,           \n";
            sql+= "   a.eduend,              \n";
            sql+= "   (select               \n";
            sql+= "       count(*)  \n";
            sql+= "     from  \n";
            sql+= "       tz_student  \n";
            sql+= "     where  \n";
            sql+= "       a.subj = subj  \n";
            sql+= "       and a.subjseq = subjseq  \n";
            sql+= "       and a.year = year  \n";
            sql+= "    )   stucnt  \n";
            sql+= " from                  \n";
            sql+= "   vz_scsubjseq  a, tz_cpinfo b  \n";
            sql+= " where                 \n";
            sql+= "   a.grcode = '"+v_grcode+"'  \n";
            sql+= "   and a.gyear = '"+v_gyear+"'  \n";
            sql+= "   and a.grseq = '"+v_grseq+"'  \n";
            sql+= "   and a.owner = b.cpseq \n";

            if(!v_gadmin.equals("A")){
            	sql+= "   and a.owner = '"+v_cp+"'  \n";
            }

            sql+= " order by subjnm        \n";
System.out.println("sql_subjcode="+sql);


			ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }



    public int insertCPexcelTemp(Hashtable data) throws Exception {
        int isOk = 0;
        boolean v_CreateConnManager = false;
        boolean v_CreatePreparedStatement = false;

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";

        ListSet ls1         = null;
        ListSet ls          = null;

        String v_subj		 = (String)data.get("subj");
		String v_year		 = (String)data.get("year");
		String v_subjseq	 = (String)data.get("subjseq");
		String v_userid      = (String)data.get("userid");
		String v_inputtime   = (String)data.get("inputtime");
        String v_isgraduated = (String)data.get("isgraduated");
        String v_luserid     = (String)data.get("luserid");
        String v_subjseqgr   = (String)data.get("subjseqgr");
        String v_rank        = (String)data.get("rank");
        String v_sangdamtxt  = (String)data.get("sangdamtxt");
        double v_score       = ((Double)data.get("score")).doubleValue(); //출석률 점수
		double v_tstep		 = ((Double)data.get("tstep")).doubleValue(); //출석률 점수
		double v_mtest		 = ((Double)data.get("mtest")).doubleValue(); //middle exam 점수
		double v_ftest		 = ((Double)data.get("ftest")).doubleValue(); //평가 점수
		double v_report		 = ((Double)data.get("report")).doubleValue(); //리포트 점수
		double v_act 		 = ((Double)data.get("act")).doubleValue(); //기타 점수
		double v_etc1		 = ((Double)data.get("etc1")).doubleValue(); //기타 점수
		double v_etc2		 = ((Double)data.get("etc2")).doubleValue(); //기타 점수2
		
		//System.out.println(v_score       );
        //System.out.println(v_tstep		 );
        //System.out.println(v_mtest		 );
        //System.out.println(v_ftest		 );
        //System.out.println(v_report		 );
        //System.out.println(v_act 		 );
        //System.out.println(v_etc1		 );
        //System.out.println(v_etc2		 );

		String maxseq = "";
		
		
        int cnt = 0;

        try {
            connMgr = (DBConnectionManager)data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }
            
            sql1  =" select NVL(ltrim(rtrim(to_char(to_number(max(seq))+1,'00000'))),'00001') MSTCD " ;
            sql1 += "From tz_cpexceltemp where inputtime = '"+v_inputtime+"' ";

            ls = connMgr.executeQuery(sql1);
            if(ls.next()){
                maxseq = ls.getString("MSTCD");	
            }
            else{
                maxseq = "00001";
            }
            

                //insert TZ_EDUTARGET table
                sql+= " insert into tz_cpexceltemp(                                       \n";
                sql+= "   INPUTTIME  ,  SUBJ       ,  YEAR       ,  SUBJSEQ    ,          \n";
                sql+= "   USERID     ,  SCORE      ,  TSTEP      ,  MTEST      ,          \n";
                sql+= "   FTEST      ,  REPORT     ,  ACT        ,  ETC1       ,          \n";
                sql+= "   ETC2       ,  AVTSTEP    ,  AVMTEST    ,  AVFTEST    ,          \n";
                sql+= "   AVREPORT   ,  AVACT      ,  AVETC1     ,  AVETC2     ,          \n";
                sql+= "   ISGRADUATED,  LUSERID    ,  LDATE      ,  SEQ        ,          \n";
                sql+= "   SUBJSEQGR  ,  RANK       ,  SANGDAM                             \n";
                sql+= " )                                                                 \n";
                sql+= " values(                                                           \n";
                sql+= "   ?,  ?,  ?,  ?,                                                  \n";
                sql+= "   ?,  ?,  ?,  ?,                                                  \n";
                sql+= "   ?,  ?,  ?,  ?,                                                  \n";
                sql+= "   ?,  ?,  ?,  ?,                                                  \n";
                sql+= "   ?,  ?,  ?,  ?,                                                  \n";
                sql+= "   ?,  ?,  ?,  ?,                                                  \n";
                sql+= "   ?,  ?,  ?                                                       \n";
                sql+= " )                                                                 \n";
                
                pstmt = connMgr.prepareStatement(sql);
                
                pstmt.setString( 1, v_inputtime);
                pstmt.setString( 2, v_subj);
                pstmt.setString( 3, v_year);
                pstmt.setString( 4, v_subjseq);
                pstmt.setString( 5, v_userid);
                pstmt.setDouble( 6, v_score);
                pstmt.setDouble( 7, v_tstep);
                pstmt.setDouble( 8, v_mtest);
                pstmt.setDouble( 9, v_ftest);
                pstmt.setDouble( 10, v_report);
                pstmt.setDouble( 11, v_act);
                pstmt.setDouble( 12, v_etc1);
                pstmt.setDouble( 13, v_etc2);
                pstmt.setDouble( 14, 0);
                pstmt.setDouble( 15, 0);
                pstmt.setDouble( 16, 0);
                pstmt.setDouble( 17, 0);
                pstmt.setDouble( 18, 0);
                pstmt.setDouble( 19, 0);
                pstmt.setDouble( 20, 0);
                pstmt.setString( 21, v_isgraduated);
                pstmt.setString( 22, v_luserid);
                pstmt.setString( 23, FormatDate.getDate("yyyyMMddHHmmss")); // ldate
                pstmt.setString( 24, maxseq);                               // maxseq
                pstmt.setString( 25, v_subjseqgr);                          // subjseqgr
                pstmt.setString( 26, v_rank);                               // rank
                pstmt.setString( 27, v_sangdamtxt);                               // rank
                isOk = pstmt.executeUpdate();

        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(ls1 != null) { try { ls1.close(); } catch (Exception e) {} }
            if(ls  != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
    
    
    /**
    등록파일 결과 리스트화면
    @param box          receive from the form object and session
    @return ArrayList   검색 리스트
    */
    public ArrayList selectInpuSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            list = selectInpuSubjList(connMgr, box);
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
    등록파일 결과 리스트화면
    @param box          receive from the form object and session
    @return ArrayList   검색 리스트
    */
    public ArrayList selectInpuSubjList(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //BulletinData data = null;
        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_cp = box.getString("p_cp");			//외주업체
        String v_grcode = box.getString("s_grcode");	//교육주관
        String v_grseq  = box.getString("s_grseq");
        String v_gyear = box.getString("s_gyear");		//교육년도
        String v_inputtime = box.getString("p_inputtime");		//교육년도
        int v_pageno = box.getInt("p_pageno");
        SubjComBean scbean = new SubjComBean();
        String v_eduterm = "";

        try {

            list = new ArrayList();
            
            sql += " select                                                                                                                                             \n";
            sql += "   inputtime,                                                                                                                                       \n";
            sql += "   subj,                                                                                                                                            \n";
            sql += "   subjseq,                                                                                                                                         \n";
            sql += "   subjseqgr,                                                                                                                                       \n";
            sql += "   year,                                                                                                                                            \n";
            sql += "   count(userid) inputstucnt,                                                                                                                       \n";
            sql += "   (select count(*) from tz_student x where exceltable.subj = x.subj  and exceltable.subjseq = x.subjseq and exceltable.year = x.year ) realstucnt, \n";
            sql += "   (select subjnm from tz_subjseq x where exceltable.subj = x.subj  and exceltable.subjseq = x.subjseq and exceltable.year = x.year   ) subjnm,     \n";
            sql += "   (select iscpflag from tz_subjseq x where exceltable.subj = x.subj  and exceltable.subjseq = x.subjseq and exceltable.year = x.year ) iscpflag,   \n";
            sql += "   (select isclosed from tz_subjseq x where exceltable.subj = x.subj  and exceltable.subjseq = x.subjseq and exceltable.year = x.year ) isclosed    \n"; 
            sql += " from                                                                                                                                               \n";
            sql += "   tz_cpexceltemp exceltable                                                                                                                        \n";
            sql += " where                                                                                                                                              \n";
            sql += "   inputtime = '"+v_inputtime+"'                                                                                                                    \n";
            sql += " group by inputtime, subj,subjseq, subjseqgr,year                                                                                                   \n";

            ls = connMgr.executeQuery(sql);                                                                                                                            

            while (ls.next()) {                                                                                                                                        
                dbox = ls.getDataBox();                                                                                                                                
                
                v_eduterm = scbean.getEduTerm(ls.getString("subj"), ls.getString("subjseq"), ls.getString("year"));
                dbox.put("d_eduterm",    v_eduterm);
                
                list.add(dbox);                                                                                                                                        
            }                                                                                                                                                          
        }                                                                                                                                                              
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
        }
        return list;
    }

}
