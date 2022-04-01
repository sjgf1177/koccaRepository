//**********************************************************
//  1. 제      목: COMPLETE STATUS ADMIN BEAN
//  2. 프로그램명: DbMgrBean.java
//  3. 개      요: 수료 현황 관리자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2003. 8. 21
//  7. 수      정:
//**********************************************************
package com.credu.complete;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.complete.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

public class DbMgrBean {
    private ConfigSet config;
    private int row;

    public DbMgrBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.DbMgr.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
    수료자 리스트(yeslearn)
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectYesLarnStold(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ArrayList list1 = new ArrayList();
//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
		
        int v_pageno        = box.getInt("p_pageno");
        int     l           = 0;
        
        String  ss_action   = box.getString("p_action");
        String  v_orderColumn= box.getString("p_orderColumn");                  //정렬할 컬럼명

        String  v_gubun       = box.getString("p_gubun");
        String  v_subjnm      = box.getString("p_subjnm");
        String  v_edustart    = box.getString("p_edustart");
        String  v_eduend      = box.getString("p_eduend");
        String  v_edustart1   = box.getString("p_edustart1");
        String  v_eduend1     = box.getString("p_eduend1");
        String  v_isgraduated = box.getString("p_isgraduated");
        String  v_userid      = box.getString("p_userid");
        String  s_aes_code    = box.getString("s_aes_code");
        
        ManagerAdminBean bean = null;
        String  v_sql_add   = "";
        String  s_userid    = box.getSession("userid");
        String  s_gadmin    = box.getSession("gadmin");
        String  v_order     = box.getString("p_order");           //정렬할 컬럼명
        String v_orderType     = box.getString("p_orderType");    //정렬할 순서
        DataBox dbox = null;

        try {
            if(ss_action.equals("go")){
                connMgr = new DBConnectionManager();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,isonoff,compnm,
                //jikwinm,userid,cono,name,edustart,eduend,score,isgraduated,email,ismailing
                head_sql+= "  select                                                              ";
				head_sql+= "    serno, groupid, comid, userid, name,                              ";
				head_sql+= "    resno, category, customerid, customername, serialno,              ";
				head_sql+= "    groupcount, contentid, subjnm, edustart, eduend,                  ";
				head_sql+= "    processpoint, reportpoint, discusspoint, ideapoint, qnapoint,     ";
				head_sql+= "    attendpoint, testpoint, finaltestpoint, score, enterdate,         ";
				head_sql+= "    isgraduated, ipcode, gubun                                        ";
				body_sql+= "  from                                                                ";
				body_sql+= "    tz_aesstold                                                       ";
				body_sql+= "  where 1=1                                                       ";
                
                if(!v_gubun.equals("ALL")){
					body_sql+= "  and gubun = '"+v_gubun+"' ";
                }
                
                if(!v_isgraduated.equals("ALL")){
					body_sql+= "  and isgraduated = '"+v_isgraduated+"' ";
                }
                
                if(!v_subjnm.equals("")){
					body_sql+= "  and subjnm like '%"+v_subjnm+"%' ";
                }
                
                if(!v_userid.equals("")){
					body_sql+= "  and userid = '"+v_userid+"' ";
                }
                
                if(!v_edustart1.equals("") && !v_eduend1.equals("")){
					body_sql+= "  and ( edustart >= '"+v_edustart+"' and eduend <= '"+v_eduend+"') ";
                }
                
                if(!s_aes_code.equals("ALL")){
					body_sql+= "  and customerid = '"+s_aes_code+"'";
                }                
                
                if(v_orderColumn.equals("")) {
					order_sql+= " order by subjnm,name" ;
                } else {
					order_sql+= " order by " + v_orderColumn + v_orderType;
                }
                
                //sql1+= "  and ipcode = '31'";
                //sql1+= "  and serialno = '0045_2001_63640419'";
                //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                //if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
                //    sql1+= " and C.gyear = "+SQLString.Format(ss_gyear);
                //}
				sql= head_sql+ body_sql+ order_sql;
				
				ls1 = connMgr.executeQuery(sql);		
				
				count_sql= "select count(*) "+ body_sql;
				int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);
			
                ls1.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno, total_row_count);	//현재페이지번호를 세팅한다.
                int total_page_count = ls1.getTotalPage();  	//전체 페이지 수를 반환한다
                //int total_row_count = ls1.getTotalCount();  	//전체 row 수를 반환한다

                while (ls1.next()) {
                  dbox = ls1.getDataBox();
                  dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                  dbox.put("d_totalpage", new Integer(total_page_count));
                  dbox.put("d_rowcount", new Integer(row));
                  list1.add(dbox);
                }
                
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }

}