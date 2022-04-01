//**********************************************************
//  1. 제      목: 과정소개 BEAN
//  2. 프로그램명:  CourseIntroBean.java
//  3. 개      요:
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 정상진 2005. 12. 16
//  7. 수      정:
//**********************************************************
package com.credu.course;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;

public class CourseIntroBean {
    
    public CourseIntroBean() {}

    /**
    과정소개 조회
    @param box          receive from the form object and session
    @return ArrayList   과정소개 리스트
    */      
    public ArrayList SelectSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        DataBox dbox = null;

	    // 사이트의 GRCODE 로 과정 리스트
	    String v_grcode      = box.getSession("tem_grcode");
		String v_grgubun     = box.getStringDefault("p_grgubun", "K01");
        String v_sphere      = box.getString("p_sphere");
		String v_subjtype    = box.getString("p_subjtype");
	    String  v_searchtext   = "";
	    if (v_subjtype.equals("alway")) {		              // 상시과정
	      v_searchtext   = "    and a.isordinary = 'Y'  ";
	    } else if (v_subjtype.equals("sometime")) {           // 수시과정
	      v_searchtext   = "    and a.isordinary = 'N'  ";		 
	    } else if (v_subjtype.equals("prof")) {				  // 전문가과정
		  v_searchtext   = "    and a.course <> '000000'";
	    } else if (v_subjtype.equals("workshop")) {           // 워크숍과정
		  v_searchtext   = "    and a.isworkshop = 'Y'  ";
	    } else if (v_subjtype.equals("recognition")) {        // 학점인증과정
		  v_searchtext   = "    and a.isunit = 'Y'      ";
	    }		
            			
		

        try {
            connMgr = new DBConnectionManager();
			
            sql  = " select a.subj, a.subjnm, a.isonoff, a.upperclass, a.middleclass, a.lowerclass,          ";
	        sql += "        a.usebook, a.introducefilenamereal, a.introducefilenamenew,                      ";
			sql += "        a.muserid, get_name(a.muserid)  mname,                                       ";
	        sql += "        (select classname from tz_subjatt x                                              ";
		    sql += "          where x.upperclass = a.upperclass and x.middleclass = a.middleclass            ";
		    sql += "            and x.lowerclass = '000' ) mclassnm                                          ";
			sql += "   from TZ_SUBJ a, tz_grsubj b                                                           ";
	        sql += "  where a.subj = b.subjcourse                                                            ";
	        sql += "    and b.grcode       = " + SQLString.Format(v_grcode); 
	        sql += "    and a.isuse        = 'Y'                                                             ";
	        sql += "    and a.upperclass  = " + SQLString.Format(v_grgubun); // 게임,문콘
			if (!v_sphere.equals("XXX")) {
				sql += "    and a.sphere = " + SQLString.Format(v_sphere)  ;
			}
	        sql += "    and isvisible = 'Y'                                                                  ";
			sql += v_searchtext;
			sql += "  order by a.middleclass, a.subjnm                                                       ";
			
//System.out.println("intro=>" + sql);			
            list1 = new ArrayList();
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                dbox = ls.getDataBox(); 
                list1.add(dbox);
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
        return list1;
    }

}
