//**********************************************************
//1. 제      목: 통게 매출관리
//2. 프로그램명: 통계 과정별 매출관 
//3. 개      요: 
//4. 환      경: 
//5. 버      젼: 
//6. 작      성: 
//7. 수      정:
//**********************************************************
package com.credu.statistics;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.statistics.*;
import com.credu.propose.*;
import com.credu.library.*;
import com.credu.system.*;

public class SubjYnAdminBean {

public SubjYnAdminBean() {}

/**
* 과정별 매출현황 
* @param box          receive from the form object and session
* @return ArrayList         
* @throws Exception
*/
public ArrayList selectSubjYnList(RequestBox box) throws Exception {
    DBConnectionManager connMgr = null;
    DataBox dbox                = null;
    ListSet ls                  = null;
    ArrayList list              = null;
    String sql                  = "";
  	int v_pageno         = 1;  

	String  ss_grcode    = box.getStringDefault("s_grcode","ALL");    //교육그룹
    String  ss_gyear     = box.getStringDefault("s_gyear","ALL");     //년도
    String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수
	String  ss_uclass    = box.getStringDefault("s_upperclass","ALL");    //과정분류
	String  ss_mclass    = box.getStringDefault("s_middleclass","ALL");    //과정분류
	String  ss_lclass    = box.getStringDefault("s_lowerclass","ALL");    //과정분류
	String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
    String  ss_subjseq   = box.getStringDefault("s_subjseq","ALL");   //과정 차수
    String  ss_edustart = box.getStringDefault("s_edustart","");  //교육시작일
    String  ss_eduend   = box.getStringDefault("s_eduend","");    //교육종료일
    String  ss_isuse   = box.getStringDefault("ss_isuse","ALL");    //교육종료일
	
	String  v_startdate  = box.getString("p_startdate");
	String  v_enddate    = box.getString("p_enddate");

	String  v_order     = box.getString("p_order");           //정렬할 컬럼명
    String  v_orderType     = box.getString("p_orderType");                 //정렬할 순서

	
	ProposeBean probean = new ProposeBean();
	String v_year       = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
	
	if (v_year.equals("")) v_year = ss_gyear;        

    try {
        connMgr = new DBConnectionManager();
        list = new ArrayList();

		sql += " select c.grcode, a.upperclass, a.middleclass, a.lowerclass, a.subj, a.subjnm, a.muserid, \n";
		sql += "		get_name(a.muserid) musernm, a.producer,  b.compnm, a.isuse \n";
		sql += " from tz_subj a, tz_comp b, tz_grsubj c \n"; 
		sql += " 			 where a.producer  =  b.comp(+) \n";
		sql += " 	  and a.subj = c.subjcourse \n";

		if (!ss_grcode.equals("ALL"))	  sql+= " and c.grcode = "+SQLString.Format(ss_grcode);
        if (!ss_uclass.equals("ALL"))     sql+= " and a.upperclass = "+SQLString.Format(ss_uclass)+"\n";
        if (!ss_mclass.equals("ALL"))     sql+= " and a.middleclass = "+SQLString.Format(ss_mclass)+"\n";
        if (!ss_lclass.equals("ALL"))	  sql+= " and a.lowerclass = "+SQLString.Format(ss_lclass)+"\n";
		if (!ss_isuse.equals("ALL"))	  sql+= " and a.isuse = "+SQLString.Format(ss_isuse)+"\n";
		
		if(!v_startdate.equals("") ){//
		  sql += "  and substring(c.edustart, 1 , 8)  ="+SQLString.Format(v_startdate);
		}
		
		if(!v_enddate.equals("") ){//
		  sql += "  and substring(c.eduend, 1 , 8 ) ="+SQLString.Format(v_enddate);
		}
		System.out.println("ss_grcode: "+ss_grcode);
		System.out.println("SubjYnAdminBean =========== : selectSubjYnList: "+sql);
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
        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
    }
    return list;
}  
   
}