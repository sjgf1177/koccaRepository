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
 * Description : 직급관리 <br>
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
            // row = Integer.parseInt(config.getProperty("page.bulletin.row")); //		  이 모듈의	페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 교수 메세지 리스트
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
        // String sql2 = ""; /* 2005.11.10_하경태 : TotalCount 쿼리를 담을 변수 */
        // NoticeData data = null;
        DataBox dbox = null;

        // String s_userid = box.getSession("userid");
        String v_subj = box.getString("p_subj");

        //		int v_pageno = box.getInt("p_pageno");
        //		int i_rowCount = 0; /* 2005.11.10_하경태 : TotalCount 해주기 위한 변수 " */

        //        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
        //        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
        //        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수
        //        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");    //과정분류
        //        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");   //과정분류
        //        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");    //과정분류
        //        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        //        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수
        //        String  ss_action   = box.getString("s_action");

        try {
            //            if(ss_action.equals("go")){
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            /* TotalCount 시작 */
            //			sql2 = "select count(*) as rownum from TZ_tutormessage a ";

            //			ls = connMgr.executeQuery(sql2);

            //				while (ls.next()) {
            //					i_rowCount = ls.getInt("rownum");
            //				}

            sql = "select a.content \n";
            sql += "  from TZ_tutormessage a,VZ_SCSUBJSEQ c                                       \n ";
            sql += "  where a.subj =* c.subj  \n";
            //sql+= "  and A.userid = '"+s_userid+"'  \n";
            sql += "  and c.scsubj = '" + v_subj + "'    \n"; //이부분 수정 해야 함.

            //            if (!ss_uclass.equals("ALL"))     sql+= " and c.scupperclass = "+SQLString.Format(ss_uclass);
            //            if (!ss_mclass.equals("ALL"))     sql+= " and c.scmiddleclass = "+SQLString.Format(ss_mclass);
            //            if (!ss_lclass.equals("ALL"))     sql+= " and c.sclowerclass = "+SQLString.Format(ss_lclass);
            //            if (!ss_subjcourse.equals("ALL")) sql+= " and c.scsubj       = "+SQLString.Format(ss_subjcourse);

            //			sql+= " order by a.seq desc                                                               \n";			

            ls = connMgr.executeQuery(sql);

            //			ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            //			ls.setCurrentPage(v_pageno, i_rowCount); // 현재페이지번호를 세팅한다.
            //			int totalpagecount = ls.getTotalPage(); // 전체 페이지 수를 반환한다
            //			int totalrowcount = ls.getTotalCount(); // 전체 row 수를 반환한다

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
     * 교수님 약력, 저서 리스트
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
     * 교수 소개.
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
     * 강좌 리스트 목록
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
     * 강좌 리스트 목록
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
