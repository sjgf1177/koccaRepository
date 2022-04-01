//**********************************************************
//  1. ��      ��: �����Ұ� BEAN
//  2. ���α׷���:  CourseIntroBean.java
//  3. ��      ��:
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 0.1
//  6. ��      ��: ������ 2005. 12. 16
//  7. ��      ��:
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
    �����Ұ� ��ȸ
    @param box          receive from the form object and session
    @return ArrayList   �����Ұ� ����Ʈ
    */      
    public ArrayList SelectSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        DataBox dbox = null;

	    // ����Ʈ�� GRCODE �� ���� ����Ʈ
	    String v_grcode      = box.getSession("tem_grcode");
		String v_grgubun     = box.getStringDefault("p_grgubun", "K01");
        String v_sphere      = box.getString("p_sphere");
		String v_subjtype    = box.getString("p_subjtype");
	    String  v_searchtext   = "";
	    if (v_subjtype.equals("alway")) {		              // ��ð���
	      v_searchtext   = "    and a.isordinary = 'Y'  ";
	    } else if (v_subjtype.equals("sometime")) {           // ���ð���
	      v_searchtext   = "    and a.isordinary = 'N'  ";		 
	    } else if (v_subjtype.equals("prof")) {				  // ����������
		  v_searchtext   = "    and a.course <> '000000'";
	    } else if (v_subjtype.equals("workshop")) {           // ��ũ�����
		  v_searchtext   = "    and a.isworkshop = 'Y'  ";
	    } else if (v_subjtype.equals("recognition")) {        // ������������
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
	        sql += "    and a.upperclass  = " + SQLString.Format(v_grgubun); // ����,����
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
