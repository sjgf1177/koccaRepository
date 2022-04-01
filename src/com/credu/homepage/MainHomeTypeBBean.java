package com.credu.homepage;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class MainHomeTypeBBean {
    /**
     * 메인 정규과정 조회
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        
        String v_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        StringBuffer sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.MainHomeTypeBBean selectSubjectList (정규강좌 리스트 조회) */ 	\n");
            sql.append("SELECT * FROM (																	\n");
            sql.append("	SELECT A.*, B.INTRODUCEFILENAMENEW, B.INTRO									\n");
            sql.append("	FROM VZ_SCSUBJSEQ A LEFT JOIN TZ_SUBJ B ON A.SUBJ = B.SUBJ					\n");
            sql.append("	WHERE A.GRCODE = " + StringManager.makeSQL(v_grcode) + "					\n");
            sql.append("		AND A.ISUSE = 'Y'														\n");
            //sql.append("		AND TO_CHAR(SYSDATE,'YYYYMMDDHH24') BETWEEN A.PROPSTART AND A.PROPEND	\n");
            sql.append("		AND SUBJVISIBLE = 'Y'													\n");
            sql.append("	ORDER BY DBMS_RANDOM.RANDOM()												\n");
            sql.append(")																				\n");
            sql.append("WHERE ROWNUM <= 6																\n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }
    
    /**
     * 메인 열린과정 조회
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectGoldClassList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        
        String v_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        StringBuffer sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.MainHomeTypeBBean selectGoldClassList (열린강좌 리스트 조회) */	\n");
            sql.append("SELECT * FROM (																	\n");
            sql.append("	SELECT A.SEQ, A.LECNM, A.LECTURE_CLS, A.VODIMG, A.INTRO						\n");
            sql.append("	FROM TZ_GOLDCLASS A INNER JOIN TZ_GOLDCLASS_GRMNG B ON A.SEQ = B.SEQ		\n");
            sql.append("	WHERE B.GRCODE = " + StringManager.makeSQL(v_grcode) + "					\n");
            sql.append("		AND A.USEYN = 'Y'														\n");
            sql.append("	ORDER BY DBMS_RANDOM.RANDOM()												\n");
            sql.append(")																				\n");
            sql.append("WHERE ROWNUM <= 6																\n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }
    
    /**
     * 메인 공지사항 조회
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectNoticeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        
        String v_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
        String v_tabseq = box.getStringDefault("p_tabseq", "12");
        boolean b_login = !box.getSession("userid").equals("");
        boolean b_popup = box.getStringDefault("p_popup", "N").equals("Y");

        StringBuffer sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.MainHomeTypeBBean selectNoticeList (공지사항 리스트 조회) */	\n");
            sql.append("SELECT * FROM (																	\n");
            sql.append("	SELECT A.*																	\n");
            sql.append("	FROM TZ_NOTICE A															\n");
            sql.append("	WHERE A.TABSEQ = " + v_tabseq + "											\n");
            sql.append("		AND A.GRCODECD LIKE '%" + v_grcode + "%'								\n");
            sql.append("		AND A.USEYN = 'Y'														\n");
            if(b_login){
            	sql.append("		AND (A.LOGINYN = 'AL' OR A.LOGINYN = 'Y' )							\n");
            }else{
            	sql.append("		AND (A.LOGINYN = 'AL' OR A.LOGINYN = 'N' )							\n");
            }
            if(b_popup){
            	sql.append("		AND A.POPUP = 'Y'													\n");
            }
            sql.append("	ORDER BY ADDATE DESC														\n");
            sql.append(")																				\n");
            sql.append("WHERE ROWNUM <= 6																\n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }
}
