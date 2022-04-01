//**********************************************************
//1. ��      ��:
//2. ���α׷���: ClassBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-07-29
//7. ��      ��:
//
//**********************************************************

package com.credu.study;

import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
public class ClassBean {
    public static String SINGLE_CLASS = "1";
    public static String PLURAL_CLASS = "2";

    public static String CLASSNM = "Ŭ����";
    public static String SINGLE_CLASS_CODE = "0001";

    public ClassBean() {
    }

    /**
     * Ŭ��������Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��������Ʈ
     */
    public ArrayList<ClassListData> SelectClassList(RequestBox box) throws Exception {
        ArrayList<ClassListData> list = new ArrayList<ClassListData>();

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ClassListData data = null;
        Hashtable<String, Object> classinfo = null;

        String v_grcode = box.getStringDefault("s_grcode", "ALL");
        String v_gyear = box.getString("s_gyear");
        String v_grseq = box.getStringDefault("s_grseq", "ALL");
        String v_uclass = box.getStringDefault("s_upperclass", "ALL");
        String v_mclass = box.getStringDefault("s_middleclass", "ALL");
        String v_lclass = box.getStringDefault("s_lowerclass", "ALL");
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_subjseq = box.getStringDefault("s_subjseq", "ALL");

        String v_orderColumn = box.getString("p_orderColumn"); //������ �÷���
        String v_orderType = box.getString("p_orderType"); //������ ����

        try {
            if (box.getString("p_action").equals("go")) {
                sql = " select course, cyear, courseseq, coursenm, subj, year, ";
                sql += " subjseq, subjseqgr, subjnm, edustart, eduend, isbelongcourse, subjcnt ";
                sql += " from vz_scsubjseq where 1 = 1 ";

                if (!v_grcode.equals("ALL"))
                    sql += "   and grcode = " + SQLString.Format(v_grcode);
                if (!v_gyear.equals("ALL"))
                    sql += "   and gyear = " + SQLString.Format(v_gyear);
                if (!v_grseq.equals("ALL"))
                    sql += "   and grseq = " + SQLString.Format(v_grseq);
                if (!v_uclass.equals("ALL"))
                    sql += "   and scupperclass = " + SQLString.Format(v_uclass);
                if (!v_mclass.equals("ALL"))
                    sql += "   and scmiddleclass = " + SQLString.Format(v_mclass);
                if (!v_lclass.equals("ALL"))
                    sql += "   and sclowerclass = " + SQLString.Format(v_lclass);
                if (!v_subj.equals("ALL"))
                    sql += "   and scsubj = " + SQLString.Format(v_subj);
                if (!v_subjseq.equals("ALL"))
                    sql += "   and scsubjseq = " + SQLString.Format(v_subjseq);

                if (v_orderColumn.equals("subj"))
                    v_orderColumn = "subj";
                if (v_orderColumn.equals("subjseq"))
                    v_orderColumn = "subjseqgr";
                if (v_orderColumn.equals("")) {
                    sql += " order by course, subj, year, subjseq ";
                } else {
                    sql += " order by  course, " + v_orderColumn + v_orderType;
                }
                System.out.println("class sql" + sql);

                connMgr = new DBConnectionManager();
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    classinfo = getClassListInfo(connMgr, ls.getString("subj"), ls.getString("year"), ls.getString("subjseq"));
                    //                    if (Integer.parseInt((String)classinfo.get("studentcnt")) > 0) {

                    data = new ClassListData();
                    data.setCoursenm(ls.getString("coursenm"));
                    data.setSubj(ls.getString("subj"));
                    data.setYear(ls.getString("year"));
                    data.setSubjseq(ls.getString("subjseq"));
                    data.setSubjseqgr(ls.getString("subjseqgr"));
                    data.setSubjnm(ls.getString("subjnm"));
                    data.setEdustart(ls.getString("edustart"));
                    data.setEduend(ls.getString("eduend"));
                    data.setSubjcnt(ls.getInt("subjcnt"));
                    data.setIsbelongcourse(ls.getString("isbelongcourse"));
                    data.setStudentcnt(StringManager.toInt((String) classinfo.get("studentcnt")));
                    data.setClasscnt(StringManager.toInt((String) classinfo.get("classcnt")));
                    data.setNoassignstudentcnt(StringManager.toInt((String) classinfo.get("noassignstudent")));
                    data.setTutor((Vector<?>) classinfo.get("tutor"));
                    data.setAvailabletutorcnt(StringManager.toInt((String) classinfo.get("availabletutorcnt")));
                    data.setSubtutor((String) classinfo.get("subtutor"));

                    list.add(data);
                    //                    }
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
     * Ŭ��������Ʈ ����
     * 
     * @param connMgr DB Connection Manager
     * @param subj �����ڵ�
     * @param year �����⵵
     * @param subjseq ��������
     * @param userid ���� ���̵�
     * @return Hashtable
     */
    public Hashtable<String, Object> getClassListInfo(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq) throws Exception {
        Hashtable<String, Object> classinfo = new Hashtable<String, Object>();
        TutorSelectData tutordata = null;
        Vector<TutorSelectData> tutor = new Vector<TutorSelectData>();
        String subtutor = "";
        int v_classcnt = 0;
        int v_studentcnt = 0;
        String v_mtutor = "";

        ListSet ls = null;
        String sql = "";
        try {
            // ��������
            sql = "select count(*) studentcnt";
            sql += "  from tz_student ";
            sql += " where subj    = " + SQLString.Format(p_subj);
            sql += "   and year    = " + SQLString.Format(p_year);
            sql += "   and subjseq = " + SQLString.Format(p_subjseq);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_studentcnt = ls.getInt("studentcnt");
                classinfo.put("studentcnt", String.valueOf(v_studentcnt));
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            if (v_studentcnt > 0) {
                // Ŭ������
                sql = "select count(*) classcnt";
                sql += "  from tz_class ";
                sql += " where subj    = " + SQLString.Format(p_subj);
                sql += "   and year    = " + SQLString.Format(p_year);
                sql += "   and subjseq = " + SQLString.Format(p_subjseq);
                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    v_classcnt = ls.getInt("classcnt");
                    classinfo.put("classcnt", String.valueOf(v_classcnt));
                }
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }

                // Ŭ���� ������ ��������
                sql = "select count(*) noassignstudent";
                sql += "  from tz_student ";
                sql += " where subj    = " + SQLString.Format(p_subj);
                sql += "   and year    = " + SQLString.Format(p_year);
                sql += "   and subjseq = " + SQLString.Format(p_subjseq);
                sql += "   and subjseq = " + SQLString.Format(p_subjseq);
                sql += "   and class is null ";
                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    classinfo.put("noassignstudent", String.valueOf(ls.getInt("noassignstudent")));
                }
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }

                // ���밭���
                sql = "select count(*) availabletutorcnt";
                sql += "  from tz_subjman  a, ";
                sql += "       tz_tutor    b ";
                sql += " where a.userid = b.userid ";
                sql += "   and a.gadmin='P1'       ";
                sql += "   and a.subj   = " + SQLString.Format(p_subj);
                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    classinfo.put("availabletutorcnt", String.valueOf(ls.getInt("availabletutorcnt")));
                }
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }

                // ����Ŭ������ ��츸 ���� �����´�.
                if (v_classcnt < 2) {
                    // �ΰ���
                    sql = "select a.tuserid, a.ttype, b.name";
                    sql += "  from tz_classtutor  a, ";
                    sql += "       tz_tutor       b  ";
                    sql += " where a.tuserid = b.userid ";
                    sql += "   and a.subj    = " + SQLString.Format(p_subj);
                    sql += "   and a.year    = " + SQLString.Format(p_year);
                    sql += "   and a.subjseq = " + SQLString.Format(p_subjseq);

                    ls = connMgr.executeQuery(sql);
                    while (ls.next()) {
                        if (ls.getString("ttype").equals("M")) {
                            v_mtutor = ls.getString("tuserid");
                        } else {
                            subtutor += ls.getString("name") + ":";
                        }
                    }
                    if (ls != null) {
                        try {
                            ls.close();
                        } catch (Exception e) {
                        }
                    }

                    // ����
                    sql = "select b.userid, b.name";
                    sql += "  from tz_subjman     a, ";
                    sql += "       tz_tutor       b  ";
                    sql += " where a.userid = b.userid ";
                    sql += "   and a.gadmin = 'P1'     ";
                    sql += "   and a.subj   = " + SQLString.Format(p_subj);

                    ls = connMgr.executeQuery(sql);
                    while (ls.next()) {
                        tutordata = new TutorSelectData();
                        tutordata.setUserid(ls.getString("userid"));
                        tutordata.setName(ls.getString("name"));
                        tutordata.setSelected(v_mtutor.equals(tutordata.getUserid()) ? true : false);
                        tutor.add(tutordata);
                    }

                    if (tutor.size() < 1) {
                        tutordata = new TutorSelectData();
                        tutordata.setUserid("notutor");
                        tutordata.setName("-����-");
                        tutor.add(tutordata);
                    }
                }
                classinfo.put("tutor", tutor);
                classinfo.put("subtutor", subtutor);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return classinfo;
    }

    /**
     * ����Ŭ���� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    @SuppressWarnings("unchecked")
    public int OneStopMakeClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        String sql = "";
        int isOk = -1;

        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_tutorid = "";

        StringTokenizer v_token = null;
        String v_tempStr = "";
        int i = 0;
        Hashtable insertData = new Hashtable();

        try {
            //p_tutors �Ѿ�� �ټ��� value�� ó���ϱ� ���� vector�� ����
            Vector v_tutors = box.getVector("p_tutors");
            if (v_tutors != null) {
                Enumeration em = v_tutors.elements();

                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);////

                while (em.hasMoreElements()) {
                    i = 0;
                    v_token = new StringTokenizer((String) em.nextElement(), ";");
                    while (v_token.hasMoreTokens()) {
                        v_tempStr = v_token.nextToken();
                        switch (i) {
                        case 0:
                            v_subj = v_tempStr;
                            break;
                        case 1:
                            v_year = v_tempStr;
                            break;
                        case 2:
                            v_subjseq = v_tempStr;
                            break;
                        case 3:
                            v_tutorid = v_tempStr;
                            break;
                        }
                        i++;
                    }

                    insertData.clear();
                    insertData.put("connMgr", connMgr);
                    insertData.put("subj", v_subj);
                    insertData.put("year", v_year);
                    insertData.put("subjseq", v_subjseq);
                    insertData.put("tutorid", v_tutorid);
                    insertData.put("ttype", "M");
                    insertData.put("class", ClassBean.SINGLE_CLASS_CODE);
                    insertData.put("classtype", ClassBean.SINGLE_CLASS);
                    insertData.put("luserid", box.getString("userid"));

                    isOk = InsertClass(insertData);
                    insertData.put("ttype", "ALL");
                    isOk = DeleteClassTutor(insertData);
                    insertData.put("ttype", "M");
                    isOk = InsertClassTutor(insertData);
                    isOk = UpdateStudentClass(insertData);
                }
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                connMgr.rollback();
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (isOk >= 0) {
                connMgr.commit();
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * ����Ŭ���� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    @SuppressWarnings("unchecked")
    public int CreateClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        String sql = "";
        int isOk = -1;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq01");
        int v_classcnt = box.getInt("p_" + v_subj + v_year + v_subjseq);

        String v_class = "";
        String v_classnm = "";
        Hashtable insertData = new Hashtable();
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////
            for (int i = 0; i < v_classcnt; i++) {
                insertData.clear();
                insertData.put("connMgr", connMgr);
                insertData.put("subj", v_subj);
                insertData.put("year", v_year);
                insertData.put("subjseq", v_subjseq);
                insertData.put("luserid", box.getString("userid"));

                if (i == 0) {
                    insertData.put("classtype", ClassBean.SINGLE_CLASS);
                    isOk = DeleteClass(insertData);
                }

                v_class = new DecimalFormat("0000").format(i + 1);
                v_classnm = ClassBean.CLASSNM + v_class;

                insertData.put("class", v_class);
                insertData.put("classnm", v_classnm);
                insertData.put("classtype", ClassBean.PLURAL_CLASS);
                isOk = InsertClass(insertData);
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                connMgr.rollback();
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk >= 0) {
                connMgr.commit();
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 
     * @param data
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int InsertClass(Hashtable data) throws Exception {
        int isOk = -1;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String v_subj = (String) data.get("subj");
        String v_year = (String) data.get("year");
        String v_subjseq = (String) data.get("subjseq");
        String v_classtype = (String) data.get("classtype");
        String v_class = "";
        String v_classnm = "";

        if (v_classtype.equals(ClassBean.SINGLE_CLASS)) {
            DeleteClass(data);
            v_class = ClassBean.SINGLE_CLASS_CODE;
            v_classnm = ClassBean.CLASSNM + v_class;
        } else {
            v_class = (String) data.get("class");
            v_classnm = (String) data.get("classnm");
        }
        String v_luserid = (String) data.get("luserid");
        String v_comp = "";

        //insert TZ_CLASS table
        sql = "insert into TZ_CLASS (  subj,    year,   subjseq,   class,   classnm, comp,   luserid,   ldate ) ";
        sql += "              values (  ?, ?, ?, ?,  ?, ?, ?, ? ) ";

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);
            pstmt.setString(4, v_class);
            pstmt.setString(5, v_classnm);
            pstmt.setString(6, v_comp);
            pstmt.setString(7, v_luserid);
            pstmt.setString(8, FormatDate.getDate("yyyyMMddHHmmss")); // ldate
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {

            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    @SuppressWarnings("unchecked")
    public int DeleteClass(Hashtable data) throws Exception {
        int isOk = -1;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        String sql = "";

        String v_subj = (String) data.get("subj");
        String v_year = (String) data.get("year");
        String v_subjseq = (String) data.get("subjseq");

        String v_classtype = (String) data.get("classtype");
        String v_class = (String) data.get("class");

        //insert TZ_CLASS table
        sql = "delete from TZ_CLASS ";
        sql += " where subj    = ? ";
        sql += "   and year    = ? ";
        sql += "   and subjseq = ? ";
        if (!v_classtype.equals(ClassBean.SINGLE_CLASS)) {
            sql += "   and class   = ? ";
        }

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);
            if (!v_classtype.equals(ClassBean.SINGLE_CLASS)) {
                pstmt.setString(4, v_class);
            }
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 
     * @param data
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int InsertClassTutor(Hashtable data) throws Exception {
        int isOk = -1;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        String sql = "";
        String v_subj = (String) data.get("subj");
        String v_year = (String) data.get("year");
        String v_subjseq = (String) data.get("subjseq");
        String v_classtype = (String) data.get("classtype");

        String v_class = "";
        if (v_classtype.equals(ClassBean.SINGLE_CLASS)) {
            v_class = ClassBean.SINGLE_CLASS_CODE;
        } else {
            v_class = (String) data.get("class");
        }
        String v_tutor = (String) data.get("tutorid");
        String v_ttype = (String) data.get("ttype");

        String v_luserid = (String) data.get("luserid");

        //insert TZ_CLASSTUTOR table
        sql = "insert into TZ_CLASSTUTOR ( ";
        sql += " subj,    year,   subjseq,   class,  ";
        sql += " tuserid, ttype,  luserid,   ldate ) ";
        sql += " values ( ";
        sql += " ?, ?, ?, ?, ";
        sql += " ?, ?, ?, ? ) ";

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);
            pstmt.setString(4, v_class);
            pstmt.setString(5, v_tutor);
            pstmt.setString(6, v_ttype);
            pstmt.setString(7, v_luserid);
            pstmt.setString(8, FormatDate.getDate("yyyyMMddHHmmss")); // ldate
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 
     * @param data
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int DeleteClassTutor(Hashtable data) throws Exception {
        int isOk = -1;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        String sql = "";
        String v_subj = (String) data.get("subj");
        String v_year = (String) data.get("year");
        String v_subjseq = (String) data.get("subjseq");

        String v_classtype = (String) data.get("classtype");
        String v_class = (String) data.get("class");
        String v_tutor = (String) data.get("tutorid");
        String v_ttype = (String) data.get("ttype");

        //delete TZ_CLASSTUTOR table
        sql = "delete from TZ_CLASSTUTOR  ";
        sql += " where subj = ? ";
        sql += "   and year = ? ";
        sql += "   and subjseq =  ? ";
        if (v_classtype.equals(ClassBean.SINGLE_CLASS)) {
            if (v_ttype.equals("ALL")) {

            } else {
                // sql+=  "   and class   = ? ";
                sql += "   and tuserid = ? ";
            }
        } else {
            sql += "   and class   = ? ";
            sql += "   and tuserid = ? ";
        }

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);
            if (v_classtype.equals(ClassBean.SINGLE_CLASS)) {
                if (!v_ttype.equals("ALL"))
                    pstmt.setString(4, v_ttype);
            } else {
                pstmt.setString(4, v_class);
                pstmt.setString(5, v_tutor);
            }

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 2009.10.28 DeleteClassTutor �� SubTutorInsert ���� ȣ��ÿ� ��� �Ƿ��� ���縦 �������� �����ϰ� �ٽ� insert �ϱ� ������
     * ��������δ� �����Ǵ� ���簡 ���� �Ǵ� ������ �߻��Ͽ� DeleteClassTutor�� �״�� �����Ͽ� ���� �߰� �� ����
     * DeleteClassTutor�� ���� ��ġ�� DeleteClassTutor�� ȣ���ϴ� ���� ���Ƽ� ������ ������ ���� �߰� ������ 
     */
    @SuppressWarnings("unchecked")
    public int DeleteSubTutor(Hashtable data) throws Exception {
        int isOk = -1;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        String sql = "";
        String v_subj = (String) data.get("subj");
        String v_year = (String) data.get("year");
        String v_subjseq = (String) data.get("subjseq");

        String v_classtype = (String) data.get("classtype");
        String v_class = (String) data.get("class");
        String v_ttype = (String) data.get("ttype");

        //delete TZ_CLASSTUTOR table
        sql = "delete from TZ_CLASSTUTOR  ";
        sql += " where subj = ? ";
        sql += "   and year = ? ";
        sql += "   and subjseq =  ? ";
        if (v_classtype.equals(ClassBean.SINGLE_CLASS)) {
            if (v_ttype.equals("ALL")) {

            } else {
                // sql+=  "   and class   = ? ";
                //sql+=  "   and tuserid = ? ";//class ���� �ΰ���� ��� ����
                sql += "   and ttype   = 'S'"; //�ְ���� ���� ���� �ʵ���
            }
        } else {
            sql += "   and class   = ? ";
            sql += "   and ttype   = 'S'"; //�ְ���� ���� ���� �ʵ���
            //sql+=  "   and tuserid = ? ";//class ���� �ΰ���� ��� ����
        }

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);
            if (v_classtype.equals(ClassBean.SINGLE_CLASS)) {
                if (!v_ttype.equals("ALL"))
                    pstmt.setString(4, v_ttype);
            } else {
                pstmt.setString(4, v_class);
                //pstmt.setString( 5, v_tutor);
            }

            isOk = pstmt.executeUpdate();
            System.out.println(sql + "#################");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 
     * @param data
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int UpdateStudentClass(Hashtable data) throws Exception {
        int isOk = -1;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";

        String v_subj = (String) data.get("subj");
        String v_year = (String) data.get("year");
        String v_subjseq = (String) data.get("subjseq");
        String v_class = (String) data.get("class");
        ;
        String v_classtype = (String) data.get("classtype");
        String v_userid = (String) data.get("userid");
        String v_luserid = (String) data.get("luserid");

        //update TZ_STUDENT table
        sql = "update TZ_STUDENT  ";
        sql += "   set class   =  ?, ";
        sql += "       luserid =  ?, ";
        sql += "       ldate   =  ?  ";
        sql += " where subj    =  ?  ";
        sql += "   and year    =  ?  ";
        sql += "   and subjseq =  ?  ";
        if (!v_classtype.equals(ClassBean.SINGLE_CLASS)) {
            sql += "   and userid   = ? ";
        }

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_class);
            pstmt.setString(2, v_luserid);
            pstmt.setString(3, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(4, v_subj);
            pstmt.setString(5, v_year);
            pstmt.setString(6, v_subjseq);
            if (!v_classtype.equals(ClassBean.SINGLE_CLASS)) {
                pstmt.setString(7, v_userid);
            }
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * �������� ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ����������Ʈ
     */
    public ArrayList<DataBox> SelectSubtutorList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        DataBox dbox = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq01");

        String v_mtutor = "";

        try {
            // �ְ��� ����
            sql = "select a.tuserid, a.ttype, b.name";
            sql += "  from tz_classtutor  a, ";
            sql += "       tz_tutor       b  ";
            sql += " where a.tuserid = b.userid ";
            sql += "   and a.subj    = " + SQLString.Format(v_subj);
            sql += "   and a.year    = " + SQLString.Format(v_year);
            sql += "   and a.subjseq = " + SQLString.Format(v_subjseq);
            sql += "   and a.class   = " + SQLString.Format(ClassBean.SINGLE_CLASS_CODE);

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                if (ls.getString("ttype").equals("M")) {
                    v_mtutor = ls.getString("tuserid");
                }
            }

            // ���� ����Ʈ
            sql = "select b.userid, b.name";
            //sql+= "       , (select DECODE(tuserid,null,'N','Y') ";
            //sql+= "            from tz_classtutor";
            //sql+= "           where subj    = a.subj ";
            //sql+= "             and year    =" + SQLString.Format(v_year);
            //sql+= "             and subjseq =" + SQLString.Format(v_subjseq);
            //sql+= "             and tuserid = a.userid";
            //sql+= "          ) ischeck";
            sql += "  from tz_subjman     a, ";
            sql += "       tz_tutor       b  ";
            sql += " where a.userid = b.userid ";
            sql += "   and a.gadmin='P1'       ";
            sql += "   and a.subj   = " + SQLString.Format(v_subj);

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {

                dbox = ls.getDataBox();
                dbox.put("d_ttype", v_mtutor.equals(dbox.getString("d_userid")) ? "M" : "S");

                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
     * ����Ŭ���� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    @SuppressWarnings("unchecked")
    public int SubTutorInsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        String sql = "";
        int isOk = -1;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq01");
        String v_class = box.getStringDefault("p_class", ClassBean.SINGLE_CLASS_CODE);
        String v_tutorid = "";

        //p_tutors �Ѿ�� �ټ��� value�� ó���ϱ� ���� vector�� ����
        Vector v_tutors = box.getVector("p_checks");
        Enumeration em = v_tutors.elements();
        int i = 0;
        Hashtable insertData = new Hashtable();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            //2009.10.28 ��ü ������ ���� �Ѵ�.
            //�����ȿ��� ������ �ʿ䰡 ����, ��� ���縦 ���� �Ϸ���  �ƹ��͵� üũ���� �ʰ� �Ѿ� �������� ���縦 �������� ���� �� �� �ִ�.
            insertData.clear();
            insertData.put("connMgr", connMgr);
            insertData.put("subj", v_subj);
            insertData.put("year", v_year);
            insertData.put("subjseq", v_subjseq);
            insertData.put("class", v_class);
            insertData.put("ttype", "S");
            insertData.put("classtype", ClassBean.PLURAL_CLASS);
            insertData.put("luserid", box.getSession("userid"));

            isOk = DeleteSubTutor(insertData);

            while (em.hasMoreElements()) {
                v_tutorid = (String) em.nextElement();

                insertData.clear();
                insertData.put("connMgr", connMgr);

                insertData.put("subj", v_subj);
                insertData.put("year", v_year);
                insertData.put("subjseq", v_subjseq);
                insertData.put("class", v_class);
                insertData.put("tutorid", v_tutorid);

                insertData.put("ttype", "S");
                insertData.put("classtype", ClassBean.PLURAL_CLASS);
                insertData.put("luserid", box.getSession("userid"));

                //isOk = DeleteClassTutor(insertData); //2009.10.28
                isOk = InsertClassTutor(insertData);
                i++;
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                connMgr.rollback();
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * Ŭ�����Է¸���Ʈ ��ȸ - ���κ�
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���κ� ����Ʈ
     */
    public ArrayList<ClassMemberData> SelectClassInsertList(RequestBox box) throws Exception {
        ArrayList<ClassMemberData> list = new ArrayList<ClassMemberData>();
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ClassMemberData data = null;

        try {
            sql = "select b.comp asgn, get_compnm(b.comp,2,2) companynm, get_deptnm(b.deptnam,'') asgnnm, b.jikwi, get_jikwinm(b.jikwi, b.comp) jikwinm, b.userid, b.cono, b.name, a.class, c.classnm, a.subj, a.year, a.subjseq ";
            sql += "  from tz_student  a, ";
            sql += "       tz_member   b, ";
            sql += "       tz_class    c ";
            sql += " where a.userid = b.userid ";
            /*
             * sql+= "   and a.subj   = c.subj(+) "; sql+=
             * "   and a.year   = c.year(+) "; sql+=
             * "   and a.subjseq = c.subjseq(+) "; sql+=
             * "   and a.class   = c.class(+) ";
             */

            sql += "   and a.subj    =  c.subj(+) ";
            sql += "   and a.year    =  c.year(+) ";
            sql += "   and a.subjseq  =  c.subjseq(+) ";
            sql += "   and a.class    =  c.class(+) ";
            sql += "   and a.subj = " + SQLString.Format(box.getString("p_subj"));
            sql += "   and a.year = " + SQLString.Format(box.getString("p_year"));
            sql += "   and a.subjseq = " + SQLString.Format(box.getString("p_subjseq01"));

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new ClassMemberData();
                data.setCompanynm(ls.getString("companynm"));
                data.setAsgn(ls.getString("asgn"));
                data.setAsgnnm(ls.getString("asgnnm"));
                data.setJikwi(ls.getString("jikwi"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setUserid(ls.getString("userid"));
                data.setCono(ls.getString("cono"));
                data.setName(ls.getString("name"));
                data.setClass1(ls.getString("class"));
                data.setClassnm(ls.getString("classnm"));
                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
     * Ŭ�����Է¸���Ʈ ��ȸ - ����κ�
     * 
     * @param box receive from the form object and session
     * @return ArrayList ����κ� ����Ʈ
     */
    public ArrayList<ClassInsertData> SelectGroupStudentList(RequestBox box) throws Exception {
        ArrayList<ClassInsertData> list = new ArrayList<ClassInsertData>();
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ClassInsertData data = null;
        String v_subpage = box.getStringDefault("p_subpage", "individual_page");
        try {
            if (v_subpage.equals("asgn_page")) {
                sql = "select b.comp code, get_compnm(b.comp,2,4) codenm, count(*) studentcnt, c.class,  min(c.classnm) classnm ";
            } else if (v_subpage.equals("jikun_page")) {
                sql = "select b.jikun code, get_jikunnm(b.jikun, b.comp) codenm, count(*) studentcnt, c.class,  min(c.classnm) classnm ";
            } else if (v_subpage.equals("jikup_page")) {
                sql = "select b.jikup code, get_jikupnm(b.jikup, b.comp) codenm, count(*) studentcnt, c.class,  min(c.classnm) classnm ";
            }
            sql += "  from tz_student  a, ";
            sql += "       tz_member   b, ";
            sql += "       tz_class    c ";
            sql += " where a.userid = b.userid ";
            /*
             * sql+= "   and a.subj   = c.subj(+) "; sql+=
             * "   and a.year   = c.year(+) "; sql+=
             * "   and a.subjseq = c.subjseq(+) "; sql+=
             * "   and a.class   = c.class(+) ";
             */

            sql += "   and a.subj    =  c.subj(+) ";
            sql += "   and a.year    =  c.year(+) ";
            sql += "   and a.subjseq  =  c.subjseq(+) ";
            sql += "   and a.class    =  c.class(+) ";

            sql += "   and a.subj = " + SQLString.Format(box.getString("p_subj"));
            sql += "   and a.year = " + SQLString.Format(box.getString("p_year"));
            sql += "   and a.subjseq = " + SQLString.Format(box.getString("p_subjseq01"));
            if (v_subpage.equals("asgn_page")) {
                sql += " group by b.comp, get_compnm(b.comp,2,4), c.class ";
            } else if (v_subpage.equals("jikun_page")) {
                sql += " group by b.jikun, get_jikunnm(b.jikun, b.comp), c.class ";
            } else if (v_subpage.equals("jikup_page")) {
                sql += " group by b.jikup, get_jikupnm(b.jikup, b.comp), c.class ";
            }

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new ClassInsertData();
                data.setCode(ls.getString("code"));
                data.setCodenm(ls.getString("codenm"));
                data.setStudentcnt(ls.getInt("studentcnt"));
                data.setClass1(ls.getString("class"));
                data.setClassnm(ls.getString("classnm"));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
     * Ŭ������������Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��������Ʈ
     */
    public ArrayList<ClassMemberData> SelectClassUpdateList(RequestBox box) throws Exception {
        ArrayList<ClassMemberData> list = new ArrayList<ClassMemberData>();
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ClassMemberData data = null;
        Hashtable<String, String> tutorinfo = null;

        try {
            sql = "select b.comp asgn, get_compnm(b.comp,2,4) asgnnm, b.jikwi, get_jikwinm(b.jikwi, b.comp) jikwinm, b.userid, b.cono, b.name, a.class, c.classnm, a.subj, a.year, a.subjseq ";
            sql += "  from tz_student  a, ";
            sql += "       tz_member   b, ";
            sql += "       tz_class    c ";
            sql += " where a.userid = b.userid ";
            /*
             * sql+= "   and a.subj   = c.subj(+) "; sql+=
             * "   and a.year   = c.year(+) "; sql+=
             * "   and a.subjseq = c.subjseq(+) "; sql+=
             * "   and a.class   = c.class(+) ";
             */

            sql += "   and a.subj    =  c.subj(+) ";
            sql += "   and a.year    =  c.year(+) ";
            sql += "   and a.subjseq  =  c.subjseq(+) ";
            sql += "   and a.class    =  c.class(+) ";

            sql += "   and a.subj = " + SQLString.Format(box.getString("p_subj"));
            sql += "   and a.year = " + SQLString.Format(box.getString("p_year"));
            sql += "   and a.subjseq = " + SQLString.Format(box.getString("p_subjseq01"));

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new ClassMemberData();
                data.setAsgn(ls.getString("asgn"));
                data.setAsgnnm(ls.getString("asgnnm"));
                data.setJikwi(ls.getString("jikwi"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setUserid(ls.getString("userid"));
                data.setCono(ls.getString("cono"));
                data.setName(ls.getString("name"));
                data.setClass1(ls.getString("class"));
                data.setClassnm(ls.getString("classnm"));
                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));
                tutorinfo = getTutorInfo(connMgr, data.getSubj(), data.getYear(), data.getSubjseq(), data.getClass1());
                data.setMtutor((String) tutorinfo.get("p_mtutor"));
                data.setStutor((String) tutorinfo.get("p_stutor"));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
     * Ŭ���� ��ü����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success, 0:delete fail
     */
    public int DeleteAllClass(RequestBox box) throws Exception {
        int isOk = -1;

        DBConnectionManager connMgr = null;
        String sql = "";
        Hashtable<String, Object> deleteData = new Hashtable<String, Object>();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            deleteData.put("connMgr", connMgr);
            deleteData.put("subj", box.getString("p_subj"));
            deleteData.put("year", box.getString("p_year"));
            deleteData.put("subjseq", box.getString("p_subjseq01"));
            deleteData.put("classtype", ClassBean.SINGLE_CLASS);
            deleteData.put("ttype", "ALL");
            deleteData.put("luserid", box.getSession("userid"));

            isOk = DeleteClass(deleteData);
            isOk = DeleteClassTutor(deleteData);
            isOk = UpdateStudentClass(deleteData);
        } catch (Exception ex) {
            if (connMgr != null) {
                connMgr.rollback();
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk >= 0) {
                connMgr.commit();
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    public Hashtable<String, String> getSubjInfo(RequestBox box) throws Exception {
        Hashtable<String, String> subjinfo = new Hashtable<String, String>();

        ListSet ls = null;
        String sql = "";
        DBConnectionManager connMgr = null;

        try {
            // ��������
            sql = "select a.grcode, c.grcodenm, a.gyear, a.grseq, d.grseqnm, b.upperclass, e.classname, ";
            sql += "       a.subjnm, a.subj, a.year, a.subjseq, a.subjseqgr, a.edustart, a.eduend        ";
            sql += "  from tz_subjseq  a, ";
            sql += "       tz_subj     b, ";
            sql += "       tz_grcode   c, ";
            sql += "       tz_grseq    d, ";
            sql += "       tz_subjatt  e  ";
            sql += " where a.subj = b.subj ";
            sql += "   and a.grcode = c.grcode ";
            sql += "   and a.grcode = d.grcode ";
            sql += "   and a.gyear  = d.gyear  ";
            sql += "   and a.grseq  = d.grseq  ";
            sql += "   and b.upperclass  = e.upperclass ";
            sql += "   and e.middleclass = '000' ";
            sql += "   and e.lowerclass  = '000' ";
            sql += "   and a.subj   = " + SQLString.Format(box.getString("p_subj"));
            sql += "   and a.year   = " + SQLString.Format(box.getString("p_year"));
            sql += "   and a.subjseq= " + SQLString.Format(box.getString("p_subjseq01"));

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                subjinfo.put("p_grcode", ls.getString("grcode"));
                subjinfo.put("p_grcodenm", ls.getString("grcodenm"));
                subjinfo.put("p_gyear", ls.getString("gyear"));
                subjinfo.put("p_grseq", ls.getString("grseq"));
                subjinfo.put("p_grseqnm", ls.getString("grseqnm"));
                subjinfo.put("p_uperclass", ls.getString("upperclass"));
                subjinfo.put("p_classname", ls.getString("classname"));
                subjinfo.put("p_subjnm", ls.getString("subjnm"));
                subjinfo.put("p_subj", ls.getString("subj"));
                subjinfo.put("p_year", ls.getString("year"));
                subjinfo.put("p_subjseq", ls.getString("subjseq"));
                subjinfo.put("p_subjseqgr", ls.getString("subjseqgr"));
                subjinfo.put("p_edustart", ls.getString("edustart"));
                subjinfo.put("p_eduend", ls.getString("eduend"));
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return subjinfo;
    }

    public ArrayList<ClassMemberData> SelectMemberList(RequestBox box) throws Exception {
        ArrayList<ClassMemberData> list = new ArrayList<ClassMemberData>();
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ClassMemberData data = null;
        Hashtable<String, String> tutorinfo = null;

        try {
            sql += " select b.userid, b.name, b.comptext,  ";
            sql += "	a.class, c.classnm, a.subj, a.year, a.subjseq ";
            sql += "  from tz_student  a, ";
            sql += "       tz_member   b, ";
            sql += "       tz_class    c ";
            sql += " where a.userid = b.userid ";
            /*
             * sql+= "   and a.subj   = c.subj(+) "; sql+=
             * "   and a.year   = c.year(+) "; sql+=
             * "   and a.subjseq = c.subjseq(+) "; sql+=
             * "   and a.class   = c.class(+) ";
             */

            sql += "   and a.subj    =  c.subj(+) ";
            sql += "   and a.year    =  c.year(+) ";
            sql += "   and a.subjseq  =  c.subjseq(+) ";
            sql += "   and a.class    =  c.class(+) ";

            sql += "   and a.subj = " + SQLString.Format(box.getString("p_subj"));
            sql += "   and a.year = " + SQLString.Format(box.getString("p_year"));
            sql += "   and a.subjseq = " + SQLString.Format(box.getString("p_subjseq01"));

            if (!box.getStringDefault("p_class", "ALL").equals("ALL")) {
                sql += " and a.class = " + SQLString.Format(box.getString("p_class"));
            }

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new ClassMemberData();
                data.setUserid(ls.getString("userid"));
                data.setName(ls.getString("name"));
                data.setComptext(ls.getString("comptext"));
                data.setClass1(ls.getString("class"));
                data.setClassnm(ls.getString("classnm"));
                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));

                tutorinfo = getTutorInfo(connMgr, data.getSubj(), data.getYear(), data.getSubjseq(), data.getClass1());
                data.setMtutor((String) tutorinfo.get("p_mtutor"));
                data.setStutor((String) tutorinfo.get("p_stutor"));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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

    public Hashtable<String, String> getTutorInfo(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_class) throws Exception {
        Hashtable<String, String> tutorinfo = new Hashtable<String, String>();
        String v_stutor = "";
        String v_mtutor = "";
        String v_mtutorid = "";
        int v_tutorcnt = 0;

        ListSet ls = null;
        String sql = "";
        try {
            // �ΰ���
            sql = "select a.tuserid, a.ttype, b.name";
            sql += "  from tz_classtutor  a, ";
            sql += "       tz_tutor       b  ";
            sql += " where a.tuserid = b.userid ";
            sql += "   and a.subj    = " + SQLString.Format(p_subj);
            sql += "   and a.year    = " + SQLString.Format(p_year);
            sql += "   and a.subjseq = " + SQLString.Format(p_subjseq);
            sql += "   and a.class   = " + SQLString.Format(p_class);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                if (ls.getString("ttype").equals("M")) {
                    v_mtutor = ls.getString("name");
                    v_mtutorid = ls.getString("tuserid");
                } else if (ls.getString("ttype").equals("S")) {
                    v_stutor += ls.getString("name") + "<br>";
                }
            }
            tutorinfo.put("p_mtutor", v_mtutor);
            tutorinfo.put("p_mtutorid", v_mtutorid);
            tutorinfo.put("p_stutor", v_stutor);

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            // �������
            sql = "select count(*) tutorcnt ";
            sql += "  from tz_subjman  a, ";
            sql += "       tz_tutor    b  ";
            sql += " where a.userid = b.userid ";
            sql += "   and a.gadmin='P1'     ";
            sql += "   and a.subj   = " + SQLString.Format(p_subj);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tutorcnt = ls.getInt("tutorcnt");
                tutorinfo.put("p_tutorcnt", String.valueOf(v_tutorcnt));
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return tutorinfo;
    }

    public Hashtable<String, String> getTutorInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Hashtable<String, String> tutorinfo = null;
        try {
            connMgr = new DBConnectionManager();
            tutorinfo = getTutorInfo(connMgr, box.getString("p_subj"), box.getString("p_year"), box.getString("p_subjseq01"), box.getStringDefault("p_class", ClassBean.SINGLE_CLASS_CODE));
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, "");
            throw new Exception("sql = " + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return tutorinfo;
    }

    /**
     * �н��� Ŭ���� ���� - ���κ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    @SuppressWarnings("unchecked")
    public int UpdateStudentClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        String sql = "";
        int isOk = -1;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq01");
        String v_class = box.getString("p_class");
        String v_userid = "";
        String v_tutorid = box.getString("p_mtutorid");

        //p_userids �Ѿ�� �ټ��� value�� ó���ϱ� ���� vector�� ����
        Vector v_userids = box.getVector("p_checks");
        Enumeration em = v_userids.elements();
        Hashtable paramData = new Hashtable();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            paramData.put("connMgr", connMgr);
            paramData.put("subj", v_subj);
            paramData.put("year", v_year);
            paramData.put("subjseq", v_subjseq);
            paramData.put("class", v_class);
            paramData.put("tutorid", v_tutorid);
            paramData.put("classtype", ClassBean.PLURAL_CLASS);
            paramData.put("ttype", "M");
            paramData.put("luserid", box.getSession("userid"));

            isOk = DeleteClassTutor(paramData);
            isOk = InsertClassTutor(paramData);

            while (em.hasMoreElements()) {
                v_userid = (String) em.nextElement();
                paramData.clear();
                paramData.put("connMgr", connMgr);
                paramData.put("subj", v_subj);
                paramData.put("year", v_year);
                paramData.put("subjseq", v_subjseq);
                paramData.put("class", v_class);
                paramData.put("userid", v_userid);
                paramData.put("tutorid", v_tutorid);
                paramData.put("classtype", ClassBean.PLURAL_CLASS);
                paramData.put("luserid", box.getSession("userid"));

                isOk = UpdateStudentClass(paramData);
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                connMgr.rollback();
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk >= 0) {
                connMgr.commit();
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * �н��� Ŭ���� ���� - �����,����, ���޺�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    @SuppressWarnings("unchecked")
    public int UpdateGroupClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        String sql = "";
        int isOk = -1;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq01");
        String v_class = box.getString("p_class");
        String v_code = "";
        String v_tutorid = box.getString("p_mtutorid");
        String v_subpage = box.getString("p_subpage");

        //p_userids �Ѿ�� �ټ��� value�� ó���ϱ� ���� vector�� ����
        Vector v_checks = box.getVector("p_checks");
        Enumeration em = v_checks.elements();
        Hashtable paramData = new Hashtable();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            paramData.put("connMgr", connMgr);
            paramData.put("subj", v_subj);
            paramData.put("year", v_year);
            paramData.put("subjseq", v_subjseq);
            paramData.put("class", v_class);
            paramData.put("tutorid", v_tutorid);
            paramData.put("classtype", ClassBean.PLURAL_CLASS);
            paramData.put("ttype", "M");
            paramData.put("luserid", box.getSession("userid"));

            isOk = DeleteClassTutor(paramData);
            isOk = InsertClassTutor(paramData);

            while (em.hasMoreElements()) {
                v_code = (String) em.nextElement();
                paramData.clear();
                paramData.put("connMgr", connMgr);
                paramData.put("subj", v_subj);
                paramData.put("year", v_year);
                paramData.put("subjseq", v_subjseq);
                paramData.put("class", v_class);
                paramData.put("code", v_code);
                paramData.put("subpage", v_subpage);
                paramData.put("luserid", box.getSession("userid"));

                isOk = UpdateStudentClassGroup(paramData);
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                connMgr.rollback();
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk >= 0) {
                connMgr.commit();
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 
     * @param data
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int UpdateStudentClassGroup(Hashtable data) throws Exception {
        int isOk = -1;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";

        String v_subj = (String) data.get("subj");
        String v_year = (String) data.get("year");
        String v_subjseq = (String) data.get("subjseq");
        String v_class = (String) data.get("class");
        ;
        String v_code = (String) data.get("code");
        String v_subpage = (String) data.get("subpage");
        String v_luserid = (String) data.get("luserid");

        //update TZ_STUDENT table
        sql = "update tz_student    ";
        sql += "    set class   = ?, ";
        sql += "        luserid = ?, ";
        sql += "        ldate   = ?  ";
        sql += "  where subj    = ?  ";
        sql += "    and year    = ?  ";
        sql += "    and subjseq = ?  ";
        sql += "    and userid in (select a.userid ";
        sql += "                     from tz_student a, ";
        sql += "                          tz_member  b  ";
        sql += "                    where a.userid  = b.userid ";
        sql += "                      and a.subj    = ?  ";
        sql += "                      and a.year    = ?  ";
        sql += "                      and a.subjseq = ?  ";
        if (v_subpage.equals("asgn_page")) {
            sql += "                      and b.comp    = ?) ";
        } else if (v_subpage.equals("jikun_page")) {
            sql += "                      and b.jikun   = ?) ";
        } else if (v_subpage.equals("jikup_page")) {
            sql += "                      and b.jikup   = ?) ";
        }

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_class);
            pstmt.setString(2, v_luserid);
            pstmt.setString(3, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(4, v_subj);
            pstmt.setString(5, v_year);
            pstmt.setString(6, v_subjseq);
            pstmt.setString(7, v_subj);
            pstmt.setString(8, v_year);
            pstmt.setString(9, v_subjseq);
            pstmt.setString(10, v_code);
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * ��ü Ŭ���� ����
     * 
     * @param Hashtable input data
     * @return Hashtable delete data
     */
    @SuppressWarnings("unchecked")
    public Hashtable DeleteAllClass(Hashtable inputdata) {
        Hashtable outputdata = new Hashtable();
        Hashtable deleteData = new Hashtable();

        outputdata.put("p_errorcode", "1");

        DBConnectionManager connMgr = null;
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_luserid = "";

        try {
            connMgr = (DBConnectionManager) inputdata.get("connMgr");

            v_subj = (String) inputdata.get("subj");
            v_year = (String) inputdata.get("year");
            v_subjseq = (String) inputdata.get("subjseq");
            v_luserid = (String) inputdata.get("luserid");

            deleteData.put("connMgr", connMgr);
            deleteData.put("subj", v_subj);
            deleteData.put("year", v_year);
            deleteData.put("subjseq", v_subjseq);
            deleteData.put("classtype", ClassBean.SINGLE_CLASS);
            deleteData.put("ttype", "ALL");
            deleteData.put("luserid", v_luserid);

            DeleteClass(deleteData);
            DeleteClassTutor(deleteData);
            UpdateStudentClass(deleteData);
        } catch (Exception ex) {
            outputdata.put("p_errorcode", "0");
            outputdata.put("p_exception", ex);
        } finally {
        }
        return outputdata;
    }

    @SuppressWarnings("unchecked")
    public Hashtable InsertClass2(Hashtable data) {
        Hashtable outputdata = new Hashtable();
        outputdata.put("p_errorcode", "1");

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String v_subj = (String) data.get("subj");
        String v_year = (String) data.get("year");
        String v_subjseq = (String) data.get("subjseq");
        String v_class = (String) data.get("class");
        String v_classnm = (String) data.get("classnm");
        String v_luserid = (String) data.get("luserid");
        String v_comp = "";

        //insert TZ_CLASS table
        sql = "insert into TZ_CLASS ( ";
        sql += " subj,    year,   subjseq,   class,  ";
        sql += " classnm, comp,   luserid,   ldate ) ";
        sql += " values ( ";
        sql += " ?, ?, ?, ?, ";
        sql += " ?, ?, ?, ? ) ";

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);
            pstmt.setString(4, v_class);
            pstmt.setString(5, v_classnm);
            pstmt.setString(6, v_comp);
            pstmt.setString(7, v_luserid);
            pstmt.setString(8, FormatDate.getDate("yyyyMMddHHmmss")); // ldate
            pstmt.executeUpdate();
        } catch (Exception ex) {
            outputdata.put("p_errorcode", "0");
            outputdata.put("p_exception", ex);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return outputdata;
    }

    @SuppressWarnings("unchecked")
    public Hashtable UpdateStudentClass2(Hashtable data) {
        Hashtable outputdata = new Hashtable();
        outputdata.put("p_errorcode", "1");

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";

        String v_subj = (String) data.get("subj");
        String v_year = (String) data.get("year");
        String v_subjseq = (String) data.get("subjseq");
        String v_class = (String) data.get("class");
        ;
        String v_userid = (String) data.get("userid");
        String v_luserid = (String) data.get("luserid");

        //update TZ_STUDENT table
        sql = "update TZ_STUDENT  ";
        sql += "   set class   =  ?, ";
        sql += "       luserid =  ?, ";
        sql += "       ldate   =  ?  ";
        sql += " where subj    =  ?  ";
        sql += "   and year    =  ?  ";
        sql += "   and subjseq =  ?  ";
        sql += "   and userid   = ? ";

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_class);
            pstmt.setString(2, v_luserid);
            pstmt.setString(3, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(4, v_subj);
            pstmt.setString(5, v_year);
            pstmt.setString(6, v_subjseq);
            pstmt.setString(7, v_userid);

            pstmt.executeUpdate();
        } catch (Exception ex) {
            outputdata.put("p_errorcode", "0");
            outputdata.put("p_exception", ex);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }

        return outputdata;
    }

    /**
     * Ŭ�����й� FILE TO DB
     * 
     * @param Hashtable Ŭ���� �й� ����
     * @return Hashtable ��������
     */
    @SuppressWarnings("unchecked")
    public Hashtable FileToDB(Hashtable data) {
        Hashtable outputdata = new Hashtable();
        outputdata.put("p_errorcode", "1");
        try {
            // tz_class  select �� delete / insert   Ŭ���� ����
            data.put("classtype", ClassBean.PLURAL_CLASS);

            DeleteClass(data);
            InsertClass(data);
            //System.out.println("InsertClass : " + isOk);
            // tz_classtutor select �� delete / insert ���� ����
            data.put("ttype", "M");
            DeleteClassTutor(data);
            InsertClassTutor(data);
            InsertManager(data);
            InsertSubjman(data);
            UpdateStudentClass(data);
        } catch (Exception ex) {
            outputdata.put("p_errorcode", "0");
            outputdata.put("p_exception", ex);
        } finally {
        }

        return outputdata;
    }

    /**
     * ���� ���� ���
     * 
     * @param Hashtable Ŭ���� �й� ����
     * @return isOk 1:insert success, 0:insert fail
     */
    @SuppressWarnings("unchecked")
    public int InsertManager(Hashtable data) throws Exception {
        int isOk = -1;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;

        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String v_tutorid = (String) data.get("tutorid");
        String v_fmon = FormatDate.getDate("yyyyMMdd"); // ���� �������� (���ó��ڷ� ����)
        String v_tmon = FormatDate.getDateAdd(StringManager.substring((String) data.get("eduend"), 0, 8), "yyyyMMdd", "month", 1); // ���� �������� (���� �н������Ϸκ��� 1����)
        String v_compcd = "";
        String v_luserid = (String) data.get("luserid");

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            //������� select
            sql1 = "select fmon, tmon from TZ_MANAGER where userid = '" + v_tutorid + "' and gadmin='P1'";
            ls1 = connMgr.executeQuery(sql1);

            if (ls1.next()) {
                if (StringManager.toInt(v_tmon) < StringManager.toInt(ls1.getString("tmon")))
                    v_tmon = ls1.getString("tmon"); // �� �� ū ���ڷ�
                sql2 = " update TZ_MANAGER set isdeleted = 'N' , fmon ='" + v_fmon + "', tmon='" + v_tmon + "', ";
                sql2 += "                       luserid = '" + v_luserid + "', ldate =to_char(sysdate, 'YYYYMMDDHH24MISS') ";
                sql2 += "  where userid = '" + v_tutorid + "' and gadmin = 'P1' ";
                isOk = connMgr.executeUpdate(sql2);
            } else {
                sql3 = "insert into TZ_MANAGER(userid,gadmin,comp,isdeleted,fmon,tmon,luserid,ldate) ";
                sql3 += "values('" + v_tutorid + "','P1','" + v_compcd + "','N','" + v_fmon + "','" + v_tmon + "','" + v_luserid + "', to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                isOk = connMgr.executeUpdate(sql3);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * ���� ��� ���� ���
     * 
     * @param Hashtable Ŭ���� �й� ����
     * @return isOk 1:insert success, 0:insert fail
     */
    @SuppressWarnings("unchecked")
    public int InsertSubjman(Hashtable data) throws Exception {
        int isOk = -1;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;

        String sql1 = "";
        String sql3 = "";
        String v_tutorid = (String) data.get("tutorid");
        String v_subj = (String) data.get("subj");
        String v_luserid = (String) data.get("luserid");

        try {
            connMgr = (DBConnectionManager) data.get("connMgr");
            if (connMgr == null) {
                connMgr = new DBConnectionManager();
                v_CreateConnManager = true;
            }

            //������� select
            sql1 = "select userid from TZ_SUBJMAN where userid = '" + v_tutorid + "' and gadmin='P1' and subj = '" + v_subj + "'";
            ls1 = connMgr.executeQuery(sql1);

            if (ls1.next()) {

            } else {
                sql3 = "insert into TZ_SUBJMAN(userid,gadmin,subj,luserid,ldate) ";
                sql3 += "                 values('" + v_tutorid + "','P1','" + v_subj + "','" + v_luserid + "', to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                isOk = connMgr.executeUpdate(sql3);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * �����ڵ� �� �л� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����ڵ� �� �л� ����Ʈ
     */
    public ArrayList<DataBox> ClassStudentList(RequestBox box) throws Exception {
        ArrayList<DataBox> list = new ArrayList<DataBox>();

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_grcode = box.getStringDefault("s_grcode", "ALL");
        String v_gyear = box.getString("s_gyear");
        String v_grseq = box.getStringDefault("s_grseq", "ALL");
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_subjseq = box.getStringDefault("s_subjseq", "ALL");

        try {
            connMgr = new DBConnectionManager();
            sql = "  select a.subj, a.year, a.subjseq, b.userid                       ";
            sql += "    from tz_subjseq a, tz_student b                                ";
            sql += "   where a.subj = b.subj and a.year=b.year and a.subjseq=b.subjseq ";

            if (!v_grcode.equals("ALL"))
                sql += "   and a.grcode = " + SQLString.Format(v_grcode);
            if (!v_gyear.equals("ALL"))
                sql += "   and a.gyear = " + SQLString.Format(v_gyear);
            if (!v_grseq.equals("ALL"))
                sql += "   and a.grseq = " + SQLString.Format(v_grseq);
            if (!v_subj.equals("ALL"))
                sql += "   and a.subj = " + SQLString.Format(v_subj);
            if (!v_subjseq.equals("ALL"))
                sql += "   and a.subjseq = " + SQLString.Format(v_subjseq);
            sql += " order by a.subj, a.year, a.subjseq";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
     * ����Ŭ���� ��������
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ClassMemberData> SelectMemberListExcel(RequestBox box) throws Exception {
        ArrayList<ClassMemberData> list = new ArrayList<ClassMemberData>();
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ClassMemberData data = null;
        Hashtable<String, String> tutorinfo = null;

        String v_grcode = box.getString("s_grcode");
        String v_grseq = box.getString("s_grseq");
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_year = box.getStringDefault("s_gyear", "ALL");
        String v_subjseq = box.getStringDefault("s_subjseq", "ALL");

        try {

            sql = "select b.comp asgn, get_compnm(b.comp,2,2) companynm, get_compnm(b.comp,2,4) asgnnm, b.jikwi, get_jikwinm(b.jikwi, b.comp) jikwinm, b.userid, b.cono, b.name, a.class, c.classnm, a.subj, a.year, a.subjseq ";
            sql += "       , d.scsubjnm, d.subjseqgr  ";
            sql += "  from tz_student  a, ";
            sql += "       tz_member   b, ";
            sql += "       tz_class    c, ";
            sql += "       vz_scsubjseq d  ";
            sql += " where a.userid = b.userid ";
            /*
             * sql+= "   and a.subj   = c.subj(+) "; sql+=
             * "   and a.year   = c.year(+) "; sql+=
             * "   and a.subjseq = c.subjseq(+) "; sql+=
             * "   and a.class   = c.class(+) ";
             */

            sql += "   and a.subj	 =  c.subj(+) ";
            sql += "   and a.year	 =  c.year(+) ";
            sql += "   and a.subjseq = c.subjseq ";
            sql += "   and a.class   = c.class ";

            sql += "   and a.subj   = d.subj      ";
            sql += "   and a.year   = d.year      ";
            sql += "   and a.subjseq = d.subjseq  ";
            sql += "   and d.grcode=" + SQLString.Format(v_grcode);
            if (!v_grseq.equals("")) {
                sql += "   and d.grseq= " + SQLString.Format(v_grseq);
            }
            if (!v_subj.equals("ALL")) {
                sql += "   and a.subj = " + SQLString.Format(v_subj);
            }
            if (!v_year.equals("ALL")) {
                sql += "   and a.year = " + SQLString.Format(v_year);
            }
            if (!v_subjseq.equals("ALL")) {
                sql += "   and a.subjseq = " + SQLString.Format(v_subjseq);
            }
            sql += " order by subj, b.userid ";

            //Log.info.println("<<<EEE>>>"+sql);

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new ClassMemberData();
                data.setCompanynm(ls.getString("companynm"));
                data.setAsgn(ls.getString("asgn"));
                data.setAsgnnm(ls.getString("asgnnm"));
                data.setJikwi(ls.getString("jikwi"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setUserid(ls.getString("userid"));
                data.setCono(ls.getString("cono"));
                data.setName(ls.getString("name"));
                data.setClass1(ls.getString("class"));
                data.setClassnm(ls.getString("classnm"));
                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseqgr")); // ��������
                data.setSubjnm(ls.getString("scsubjnm")); // ������

                tutorinfo = getTutorInfo(connMgr, data.getSubj(), data.getYear(), data.getSubjseq(), data.getClass1());
                data.setMtutor((String) tutorinfo.get("p_mtutor"));
                data.setMtutorid((String) tutorinfo.get("p_mtutorid"));
                data.setStutor((String) tutorinfo.get("p_stutor"));

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
