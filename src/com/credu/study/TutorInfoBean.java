//**********************************************************
//  1. ��      ��: ���� �������� ��ȸ
//  2. ���α׷��� : TutorInfoBean.java
//  3. ��      ��: ���� �������� ��ȸ
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 12. 20
//  7. ��      ��:
//**********************************************************

package com.credu.study;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;

/**
 * ���� ������ ��Ȳ(ADMIN)
 *
 * @date   : 2004. 12
 * @author : S.W.Kang
 */
public class TutorInfoBean {
	private int row;
    public TutorInfoBean() {}


    /**
    ���ȭ�� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ��� ����Ʈ
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
