package com.credu.tutor;

import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * ---------------------------------------------------------Project<br>
 * Project : e-HRD<br>
 * System Name : position<br>
 * File Name : TutorPreviewBean.java Creater : fruitys<br>
 * Create Date : 2006. 1. 22.<br>
 * <br>
 * ---------------------------------------------------------Modify<br>
 * Modify Date : Modifier : Modify Contents<br>
 * 2006. 1. 22. : fruitys : create<br>
 * ...<br>
 * <br>
 * ---------------------------------------------------------Description<br>
 * Description : ���ް��� <br>
 * <br>
 * ---------------------------------------------------------JavaDocs<br>
 * 
 * @author fruitys<br>
 * @version v1.0<br>
 */
public class TutorIntroduceBean {

    // private ConfigSet config;
    // private int row;

    public TutorIntroduceBean() {
        try {
            // config = new ConfigSet();
            // row = Integer.parseInt(config.getProperty("page.bulletin.row")); //		  �� �����	�������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���� �޼��� ����Ʈ
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectMessageList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        // String sql2 = ""; /* 2005.11.10_�ϰ��� : TotalCount ������ ���� ���� */
        // NoticeData data = null;
        DataBox dbox = null;

        // String s_userid = box.getSession("userid");
        String v_subj = box.getString("p_subj");

        //		int v_pageno = box.getInt("p_pageno");
        //		int i_rowCount = 0; /* 2005.11.10_�ϰ��� : TotalCount ���ֱ� ���� ���� " */

        //        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //�����׷�
        //        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //�⵵
        //        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //��������
        //        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");    //�����з�
        //        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");   //�����з�
        //        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");    //�����з�
        //        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//����&�ڽ�
        //        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //���� ����
        //        String  ss_action   = box.getString("s_action");

        try {
            //            if(ss_action.equals("go")){
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            /* TotalCount ���� */
            //			sql2 = "select count(*) as rownum from TZ_tutormessage a ";

            //			ls = connMgr.executeQuery(sql2);

            //				while (ls.next()) {
            //					i_rowCount = ls.getInt("rownum");
            //				}

            sql = "select a.content \n";
            sql += "  from TZ_tutormessage a,VZ_SCSUBJSEQ c                                       \n ";
            sql += "  where a.subj =* c.subj  \n";
            //sql+= "  and A.userid = '"+s_userid+"'  \n";
            sql += "  and c.scsubj = '" + v_subj + "'    \n"; //�̺κ� ���� �ؾ� ��.

            //            if (!ss_uclass.equals("ALL"))     sql+= " and c.scupperclass = "+SQLString.Format(ss_uclass);
            //            if (!ss_mclass.equals("ALL"))     sql+= " and c.scmiddleclass = "+SQLString.Format(ss_mclass);
            //            if (!ss_lclass.equals("ALL"))     sql+= " and c.sclowerclass = "+SQLString.Format(ss_lclass);
            //            if (!ss_subjcourse.equals("ALL")) sql+= " and c.scsubj       = "+SQLString.Format(ss_subjcourse);

            //			sql+= " order by a.seq desc                                                               \n";			

            ls = connMgr.executeQuery(sql);

            //			ls.setPageSize(row); // �������� row ������ �����Ѵ�
            //			ls.setCurrentPage(v_pageno, i_rowCount); // ������������ȣ�� �����Ѵ�.
            //			int totalpagecount = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ�
            //			int totalrowcount = ls.getTotalCount(); // ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
            //            }
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
     * ������ ���, ���� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return TutorData
     */
    public DataBox selectTutor(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        // TutorData data = null;
        DataBox dbox = null;

        Vector<String> photoVector = new Vector<String>();
        Vector<String> newphotoVector = new Vector<String>();

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql1 = " select a.academic, a.book,a.photo, a.newphoto \n";
            sql1 += " from TZ_TUTOR a, TZ_MEMBER b \n";
            sql1 += "  where a.userid = b.userid(+) and a.userid = '" + s_userid + "' \n";

            ls1 = connMgr.executeQuery(sql1);

            //			System.out.println("selectTutor : " + sql1);

            if (ls1.next()) {
                // data = new TutorData();
                dbox = ls1.getDataBox();

                photoVector.addElement(dbox.getString("d_photo"));
                newphotoVector.addElement(dbox.getString("d_newphoto"));

                if (photoVector != null)
                    dbox.put("d_photo", photoVector);
                if (newphotoVector != null)
                    dbox.put("d_newphoto", newphotoVector);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return dbox;
    }

    /**
     * ���� �Ұ�.
     * 
     * @param box receive from the form object and session
     * @return TutorData
     */
    public DataBox introduceTutor(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        Vector<String> photoVector = new Vector<String>();
        Vector<String> newphotoVector = new Vector<String>();

        String s_userid = box.getString("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " Select ";
            sql += " a.userid, a.name , academic, major, career, book, photo, newphoto, intro, content ";
            sql += " From tz_tutor a, TZ_tutormessage c ";
            sql += " Where ";
            sql += "  a.userid = c.userid(+) and a.userid = '" + s_userid + "' \n";

            ls = connMgr.executeQuery(sql);

            //				System.out.println("selectTutor : " + sql1);

            if (ls.next()) {
                dbox = ls.getDataBox();

                photoVector.addElement(dbox.getString("d_photo"));
                newphotoVector.addElement(dbox.getString("d_newphoto"));

                if (photoVector != null)
                    dbox.put("d_photo", photoVector);
                if (newphotoVector != null)
                    dbox.put("d_newphoto", newphotoVector);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
        return dbox;
    }

    /**
     * ���� ����Ʈ ���
     * 
     * @param box receive from the form object and session
     * @return TUtorData
     */
    public ArrayList<TutorData> selectTutorSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<TutorData> list = null;
        // DataBox dbox = null;
        String sql = "";
        TutorData data = null;
        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<TutorData>();

            //select subj,subjnm
            sql = "select A.subj,B.subjnm from TZ_SUBJMAN A,TZ_SUBJ B ";
            sql += "where A.userid = '" + s_userid + "' and A.gadmin='P1' and A.subj=B.subj";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new TutorData();
                //dbox = ls.getDataBox();

                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                //dbox.put("subj",ls.getString("subj"));
                //dbox.put("subjnm",ls.getString("subjnm"));

                list.add(data);
                //list.add(dbox); 

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
     * ���� ����Ʈ ���
     * 
     * @param box receive from the form object and session
     * @return TUtorData
     */
    public ArrayList<DataBox> selectTutorSubjListNew(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String sql = "";
        // TutorData data = null;

        String v_userid = box.getString("userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //select subj,subjnm
            sql = " Select subjnm, subj ";
            sql += " From tz_tutor a, tz_subj b ";
            sql += " Where a.userid = b.muserid and a.userid = '" + v_userid + "' ";

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
}
