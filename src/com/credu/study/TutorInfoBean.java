//**********************************************************
//  1. 제      목: 과정 강사정보 조회
//  2. 프로그램명 : TutorInfoBean.java
//  3. 개      요: 과정 강사정보 조회
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 20
//  7. 수      정:
//**********************************************************

package com.credu.study;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;

/**
 * 과정 수강생 현황(ADMIN)
 *
 * @date   : 2004. 12
 * @author : S.W.Kang
 */
public class TutorInfoBean {
	private int row;
    public TutorInfoBean() {}


    /**
    운영자화면 리스트
    @param box          receive from the form object and session
    @return ArrayList   운영자 리스트
    */
    public ArrayList selectView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        
        String v_subj		= box.getString("p_subj");
        String v_year		= box.getString("p_year");
        String v_subjseq 	= box.getString("p_subjseq");
        String v_usernm		= box.getString("p_usernm");
        
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
			
			sql  = " select b.name, ";
			sql += "        b.academic, ";
			sql += "        b.major, ";
			sql += "        b.professional, ";
			sql += "        b.career, ";
			sql += "        b.majorbook, ";
			sql += " 		b.intro, ";
			sql += "        get_compnm(b.compcd,2,4) compnm, ";
			sql += "        b.comp ";
			sql += "   from tz_subjman a, ";
			sql += "        tz_tutor b ";
			sql += "  where a.userid=b.userid ";
			sql += "    and a.subj='"+v_subj+"'";
			sql += "    and a.gadmin='P1'";
            sql += "  order by b.name asc";

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
