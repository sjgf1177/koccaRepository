//**********************************************************
//  1. 제      목: MySchedule BEAN
//  2. 프로그램명: MyScheduleBean.java
//  3. 개      요: 나의 일정 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2003. 8. 19
//  7. 수      정:
//**********************************************************
package com.credu.homepage;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.common.*;
import com.credu.library.*;
import com.credu.system.*;

public class MyScheduleBean {

    public MyScheduleBean() {}

    /**
    수강중인 과정 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectEducationSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ArrayList list1     = null;
        String sql1         = "";
        DataBox dbox = null;

        String  v_user_id		= box.getSession("userid");
        String  v_edustart		= box.getString("edustart");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            sql1  = " select  A.subj, A.year, A.subjseq, A.subjnm, A.edustart, A.eduend ";
            sql1 += "  from VZ_SCSUBJSEQ A,TZ_STUDENT B									";
            sql1 += "  where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq	";
            sql1 += "    and B.userid="+SQLString.Format(v_user_id);
			sql1 += "    and "+SQLString.Format(v_edustart)+ " between substring(A.edustart,1,6) and substring(A.eduend,1,6)";
            sql1 += "  order by A.subj,A.year,A.subjseq,A.edustart,A.eduend				";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }

    /**
    수강중인 과정 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectEducationSubjectList2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ArrayList list1     = null;
        String sql1         = "";
        DataBox dbox = null;

        String  v_user_id		= box.getSession("userid");
        String  v_edustart		= box.getString("edustart");
        String  v_eduend		= box.getString("eduend");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            sql1  = " select  A.subj, A.year, A.subjseq, A.subjnm, A.edustart, A.eduend ";
            sql1 += "  from VZ_SCSUBJSEQ A,TZ_STUDENT B									";
            sql1 += "  where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq	";
            sql1 += "    and B.userid="+SQLString.Format(v_user_id);
			sql1 += "    and ( substring(A.edustart,1,8)  between "+SQLString.Format(v_edustart)+" and "+SQLString.Format(v_eduend);
			sql1 += "	 or substring(A.eduend,1,8)  between "+SQLString.Format(v_edustart)+" and "+SQLString.Format(v_eduend)+")";
            sql1 += "  order by A.subj,A.year,A.subjseq,A.edustart,A.eduend				";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql2 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }


}