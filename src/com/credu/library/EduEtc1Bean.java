package com.credu.library;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import com.credu.common.GetCodenm;

/**
 * ����: �н����� ���̺귯��<br/>
 * ����: Copyright: Copyright (c) 2004<br/>
 * 
 * Company: Credu
 * 
 * @author ������
 * @date 2003. 12
 * @version 1.0
 */
public class EduEtc1Bean {

    public EduEtc1Bean() {
    }

    /**
     * �н��� ���� üũ
     * 
     * @param String p_subj �����ڵ�
     * @param String p_year �����ڵ�
     * @param String p_subjseq ���������ڵ�
     * @param String p_userid �����ID
     * @return String �н��� ���� ����
     */
    public static String isCurrentStudent(String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        // String sql = "";
        StringBuilder sb = new StringBuilder();
        int check = 0;
        String v_edustart = "";
        String v_eduend = "";

        if (p_year.equals("PREV") || p_subjseq.equals("PREV")) {
            return "Y";
        }

        int v_sysdate = Integer.parseInt(FormatDate.getDate("yyyyMMddhh"));

        try {
            connMgr = new DBConnectionManager();

            // 1.�н��ڿ��� üũ
            /*
            sql = "select count(userid) CNTS from tz_propose " + " where subj   =" + StringManager.makeSQL(p_subj) + "   and year   ="
                    + StringManager.makeSQL(p_year) + "   and subjseq=" + StringManager.makeSQL(p_subjseq) + "   and userid ="
                    + StringManager.makeSQL(p_userid);
            */

            sb.append(" SELECT  /* �н��� ���� üũ (EduEtc1Bean.isCurrentStudent) */  \n");
            sb.append("         COUNT(USERID) CNTS  \n");
            sb.append("   FROM  TZ_PROPOSE          \n");
            sb.append("  WHERE  SUBJ = '").append( p_subj ).append("'   \n");
            sb.append("    AND  YEAR = '").append( p_year ).append("'   \n");
            sb.append("    AND  SUBJSEQ = '").append( p_subjseq ).append("' \n");
            sb.append("    AND  USERID = '").append( p_userid ).append("'   \n");
            
            ls1 = connMgr.executeQuery(sb.toString());

            Log.info.println("�н��� ���� üũ (EduEtc1Bean.isCurrentStudent)\n" + sb.toString());

            if (ls1.next()) {
                check = ls1.getInt("CNTS");
            }

            Log.info.println("check = " + check);

            if (check > 0) {
                //1.2 �н����̹Ƿ� �н��Ⱓ���� üũ
                /*
                 * sql =
                 * "select a.grcode, a.edustart, a.eduend, b.edustart s_edustart, b.eduend s_eduend "
                 * + "  from tz_subjseq a, tz_student b " +
                 * " where a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq "
                 * + "   and a.subj   ="+StringManager.makeSQL(p_subj) +
                 * "   and a.year   ="+StringManager.makeSQL(p_year) +
                 * "   and a.subjseq="+StringManager.makeSQL(p_subjseq) +
                 * "   and userid ="+StringManager.makeSQL(p_userid);
                 */
                // to get the value of isonoff (����/���̹� ���� ���� edited by MSCHO ) (2003.11.24)

                /*
                 * sql=
                 * "select a.grcode, a.edustart, a.eduend, a.isonoff, b.edustart s_edustart, b.eduend s_eduend "
                 * +"  from vz_scsubjseq a, tz_student b "+
                 * "  where a.scsubj=b.subj and a.scyear=b.year and a.scsubjseq=b.subjseq "
                 * +"   and a.scsubj   ="+StringManager.makeSQL(p_subj)
                 * +"   and a.scyear   ="+StringManager.makeSQL(p_year)
                 * +"   and a.scsubjseq="+StringManager.makeSQL(p_subjseq)
                 * +"   and userid ="+StringManager.makeSQL(p_userid);
                 */

                //

                /*
                sql = "select a.grcode, a.edustart, a.eduend, "
                        + "		(select isonoff from tz_subj where subj=a.subj) isonoff, "
                        + "		(select edustart from tz_courseseq where course=a.course and grcode=a.grcode and gyear=a.gyear and grseq=a.grseq) s_edustart, "
                        + "		(select eduend   from tz_courseseq where course=a.course and grcode=a.grcode and gyear=a.gyear and grseq=a.grseq) s_eduend "
                        + "  from tz_subjseq a, tz_propose b " + "  where a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq "
                        + "   and a.subj   =" + StringManager.makeSQL(p_subj) + "   and a.year   =" + StringManager.makeSQL(p_year)
                        + "   and a.subjseq=" + StringManager.makeSQL(p_subjseq) + "   and userid =" + StringManager.makeSQL(p_userid);
                */
                
                sb.setLength(0);
                sb.append(" SELECT  /* �н��Ⱓ���� üũ (EduEtc1Bean.isCurrentStudent) */  \n");
                sb.append("         A.GRCODE                    \n");
                sb.append("     ,   A.EDUSTART                  \n");
                sb.append("     ,   A.EDUEND                    \n");
                sb.append("     ,   C.ISONOFF                   \n");
                sb.append("     ,   D.EDUSTART AS  S_EDUSTART   \n");
                sb.append("     ,   D.EDUEND AS S_EDUEND        \n");
                sb.append("   FROM  TZ_SUBJSEQ A                \n");
                sb.append("     ,   TZ_PROPOSE B                \n");
                sb.append("     ,   TZ_SUBJ C                   \n");
                sb.append("     ,   TZ_COURSESEQ D              \n");
                sb.append("  WHERE  A.SUBJ = '").append(p_subj).append("'   \n");
                sb.append("    AND  A.YEAR = '").append(p_year).append("'   \n");
                sb.append("    AND  A.SUBJSEQ = '").append(p_subjseq).append("' \n");
                sb.append("    AND  B.USERID = '").append(p_userid).append("'   \n");
                sb.append("    AND  A.SUBJ = B.SUBJ             \n");
                sb.append("    AND  A.YEAR = B.YEAR             \n");
                sb.append("    AND  A.SUBJSEQ = B.SUBJSEQ       \n");
                sb.append("    AND  A.SUBJ = C.SUBJ             \n");
                sb.append("    AND  A.COURSE = D.COURSE(+)      \n");
                sb.append("    AND  A.GRCODE = D.GRCODE(+)      \n");
                sb.append("    AND  A.GYEAR = D.GYEAR(+)        \n");
                sb.append("    AND  A.GRSEQ = D.GRSEQ(+)        \n");


                Log.info.println("�н����� query = " + sb.toString());
                ls2 = connMgr.executeQuery(sb.toString());

                if (ls2.next()) {
                    Log.info.println("Data Exists ");

                    if (ls2.getString("grcode").substring(0, 1).equals("C")) { //B2C�����׷��̸� tz_student�� �н��Ⱓ
                        v_edustart = ls2.getString("s_edustart");
                        v_eduend = ls2.getString("s_eduend");
                    } else {
                        v_edustart = ls2.getString("edustart");
                        v_eduend = ls2.getString("eduend");
                    }

                    //���հ����� �н��Ⱓ ���Ŀ��� ����Ʈ ���� ���� (2003.11.24)
                    if ((ls2.getString("isonoff")).equals("ON")) {
                        if (v_sysdate >= Integer.parseInt(v_edustart) && v_sysdate <= Integer.parseInt(v_eduend)) {
                            return "Y"; //�н��Ⱓ���̸�  Y
                        } else {
                            return "N";
                        }
                    } else {
                        return "Y";
                    }
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            Log.info.println("Error | " + ex.getMessage());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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

        return "N";
    }

    /**
     * �н��� ���Ѱ� ����
     * 
     * @param RequestBox box
     * @return String �н�����Ѱ� - �н����ɿ��� üũ 1. �н����̸� = �н��Ⱓ���̸� Y = ����� �����̸�
     *         >.û���Ⱓ�̸� P >.Reject N 2. Ư���������̸� P 3. Reject N
     * 
     *         return 'Y' : ������ ��ȸ ����, ����/��/��Ƽ��Ƽ/���/�ǰ��Է� ����, �α� return 'N';
     *         �Ұ�=> â�ݾƹ���. return 'P'; ������ ��ȸ�� ����.�α����� ����.
     * 
     *         ==> ������� session���� ("s_eduauth")�� Set.
     */
    public static String get_eduAuth(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;

        ListSet ls1 = null;
        ListSet ls2 = null;

        StringBuffer sql = new StringBuffer();

        int check = 0;

        String s_gadmin = box.getSession("gadmin");
        String s_subj = box.getSession("s_subj");
        String s_year = box.getSession("s_year");
        String s_subjseq = box.getSession("s_subjseq");
        String s_userid = box.getSession("userid");

        String v_edustart = "";
        String v_eduend = "";
        int v_sysdate = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));

        if (s_gadmin.equals("") || s_userid.equals("") || s_subj.equals("") || s_year.equals("") || s_subjseq.equals("")) {
            return "N";
        }
        if (s_year.equals("PREV") || s_subjseq.equals("PREV")) {
            return "Y";
        }

        try {

            connMgr = new DBConnectionManager();

            // 2. Ư���������̸�
            if (!s_gadmin.substring(0, 1).equals("Z")) {
                // Ultra, Super, ����������, ������ ��츸 �Է°������ �Ѵ�.
                String s = s_gadmin.substring(0, 1);
                if (s.equals("A") || s.equals("B") || s.equals("F") || s.equals("P") || s.equals("T") || s.equals("M"))
                    return "Y";
                else
                    return "N";
            }

            // 1.�н��ڿ��� üũ
            sql.append(" SELECT COUNT(USERID) CNTS FROM tz_propose\n");
            sql.append("  WHERE SUBJ    = " + StringManager.makeSQL(s_subj) + " \n");
            sql.append("    AND YEAR    = " + StringManager.makeSQL(s_year) + " \n");
            sql.append("    AND SUBJSEQ = " + StringManager.makeSQL(s_subjseq) + " \n");
            sql.append("    AND USERID  = " + StringManager.makeSQL(s_userid) + " \n");

            ls1 = connMgr.executeQuery(sql.toString());

            if (ls1.next()) {
                check = ls1.getInt("CNTS");
            }

            if (check > 0) {
                sql.setLength(0);
                //1.2 �н����̹Ƿ� �н��Ⱓ���� üũ
                //				sql.append(" SELECT A.GRCODE, A.EDUSTART, A.EDUEND, A.EDUSTART S_EDUSTART, A.EDUEND S_EDUEND \n");
                //				sql.append("   FROM TZ_SUBJSEQ A, tz_propose B \n");
                //				sql.append("  WHERE A.SUBJ=B.SUBJ AND A.YEAR=B.YEAR AND A.SUBJSEQ=B.SUBJSEQ \n");
                //				sql.append("    AND A.SUBJ    = " + StringManager.makeSQL(s_subj) + " \n");
                //				sql.append("    AND A.YEAR    = " + StringManager.makeSQL(s_year) + " \n");
                //				sql.append("    AND A.SUBJSEQ = " + StringManager.makeSQL(s_subjseq) + " \n");
                //				sql.append("    AND USERID = " + StringManager.makeSQL(s_userid) + " \n");

                sql.append(" SELECT A.GRCODE, '2015070100' EDUSTART, '2099123123' EDUEND, '2015070100' S_EDUSTART, '2099123123' S_EDUEND \n");
                sql.append("   FROM TZ_SUBJSEQ A \n");
                sql.append("  WHERE \n");
                sql.append("    A.SUBJ    = " + StringManager.makeSQL(s_subj) + " \n");
                sql.append("    AND A.YEAR    = " + StringManager.makeSQL(s_year) + " \n");
                sql.append("    AND A.SUBJSEQ = " + StringManager.makeSQL(s_subjseq) + " \n");

                ls2 = connMgr.executeQuery(sql.toString());

                if (ls2.next()) {
                    if (ls2.getString("grcode").substring(0, 1).equals("C")) { //B2C�����׷��̸� tz_student�� �н��Ⱓ
                        v_edustart = ls2.getString("s_edustart");
                        v_eduend = ls2.getString("s_eduend");
                    } else {
                        v_edustart = ls2.getString("edustart");
                        v_eduend = ls2.getString("eduend");
                    }

                    if (v_sysdate >= Integer.parseInt(v_edustart) && v_sysdate <= Integer.parseInt(v_eduend)) {
                        return "Y"; //�н��Ⱓ���̸�  Y
                    } else if (!s_gadmin.substring(0, 1).equals("Z")) {
                        sql.setLength(0); //dummy code for process
                    } else if (v_sysdate > Integer.parseInt(v_eduend)) {
                        // �н��Ⱓ �����̰�
                        int overTerm = Integer.parseInt(GetCodenm.get_config("overEduTerm"));
                        // 1.3 tz_config�� û����ȿ������ �����̸� P   (v_eduend �ڸ��������� �������� "00" �߰�
                        if (v_sysdate <= Integer.parseInt(FormatDate.getDayAdd(v_eduend + "00", "yyyyMMddhh", "month", overTerm))) {
                            return "P";
                        } else {
                            return "N";
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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

        return "N";
    }

    /**
     * �н�â ����URL �����
     * 
     * @param String �����ڵ�, String ����("PGM":����Url,"DOC":Document-Base)
     * @return String tz_config.vals
     */
    public static String make_betaEduURL(String p_subj, String p_gubun) throws Exception {
        // String v_vals = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String v_server = "";
        String v_dir = "";
        String v_port = "";
        String v_eduurl = "";
        String v_domain = GetCodenm.get_config("eduDomain");

        String results = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  server,domain,port,contenttype, dir  from tz_subj " + "  where  subj=" + SQLString.Format(p_subj);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_server = ls.getString("server");
                v_domain = ls.getString("domain");
                v_port = ls.getString("port");
                v_dir = ls.getString("dir");
            }

            if (v_eduurl.equals("")) {
                if (p_gubun.equals("DOC")) { // Contents Document Base
                    results = "http://" + v_server + v_domain + v_port + "/" + v_dir + "/";
                } else { // Servlet Url
                    results = "http://" + v_server + v_domain + v_port + "/servlet/controller.beta.BetaEduStart?p_subj=" + p_subj;
                    //results = "/servlet/controller.beta.BetaEduStart?p_subj="+p_subj;
                }
            } else {
                results = v_eduurl;
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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
        return results;
    }

    /**
     * �н�â ����URL �����
     * 
     * @param String �����ڵ�, String ����("PGM":����Url,"DOC":Document-Base)
     * @return String tz_config.vals
     */
    public static String make_eduURL(String p_subj, String p_gubun) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        String v_dir = "";
        String v_eduurl = "";

        String results = "";

        try {
            connMgr = new DBConnectionManager();
            sql.append(" SELECT  SERVER, PORT, CONTENTTYPE, EDUURL,DIR  FROM TZ_SUBJ \n");
            sql.append("  WHERE  SUBJ = " + SQLString.Format(p_subj));

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                v_eduurl = ls.getString("eduurl");
                v_dir = ls.getString("dir");
            }

            if (v_eduurl.equals("")) {

                if (p_gubun.equals("DOC")) { // Contents Document Base
                    results = "/" + v_dir + "/";
                } else { // Servlet Url
                    results = "/servlet/controller.contents.EduStart?p_subj=" + p_subj;
                }
            } else {
                results = v_eduurl;
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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

        return results;
    }

    /**
     * �н�â ����URL �����
     * 
     * @param String �����ڵ�, String ����("PGM":����Url,"DOC":Document-Base)
     * @return String tz_config.vals
     */
    public static String make_eduURL(String p_subj, String p_year, String p_subjseq) throws Exception {
        String v_year = "2000", v_subjseq = "0001";
        if (!p_year.equals(""))
            v_year = p_year;
        if (!p_subjseq.equals(""))
            v_subjseq = p_subjseq;

        return EduEtc1Bean.make_eduURL(p_subj, "PGM") + "&p_year=" + v_year + "&p_subjseq=" + v_subjseq;

    }

    /**
     * �н�â ����URL �����
     * 
     * @param String �����ڵ�, String ����("PGM":����Url,"DOC":Document-Base)
     * @return String tz_config.vals
     */
    public static String make_eduURL(String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        String v_year = "2000", v_subjseq = "0001";
        String results = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String v_contenttype = "";
        String v_resno = "";
        String v_eduurl = "";

        if (!p_year.equals(""))
            v_year = p_year;
        if (!p_subjseq.equals(""))
            v_subjseq = p_subjseq;

        try {
            connMgr = new DBConnectionManager();

            sql = " select  contenttype, eduurl  from tz_subj " + "  where  subj=" + SQLString.Format(p_subj);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_contenttype = ls.getString("contenttype");
                v_eduurl = ls.getString("eduurl");
            }

            //Link �����ϰ��
            if (v_contenttype.equals("L")) {
                if (v_eduurl.equalsIgnoreCase("CREDU")) { //CREDU Link�ϰ��
                    if (ls != null) {
                        try {
                            ls.close();
                        } catch (Exception e) {
                        }
                    }
                    sql = "select resno from tz_member where userid='" + p_userid + "'";
                    ls = connMgr.executeQuery(sql);
                    ls.next();
                    v_resno = ls.getString("resno");
                    results = "http://www.credu.com/pls/cyber/zasp.new_study1?p_subj=" + p_subj + "&p_resno=" + v_resno;
                } else {
                    results = v_eduurl;
                }
            } else {
                results = EduEtc1Bean.make_eduURL(p_subj, "PGM") + "&p_year=" + v_year + "&p_subjseq=" + v_subjseq;
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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

        //return EduEtc1Bean.make_eduURL(p_subj,"PGM")+"&p_year="+v_year+"&p_subjseq="+v_subjseq;
        return results;

    }

    /**
     * Lesson ����URL �����
     * 
     * @param String p_gubun : '1':whole url, '2':lesson����
     * @return String tz_config.vals
     */
    public static String make_startURL(String p_gubun, String p_subj, String p_server, String p_port, String p_dir, String p_lesson)
            throws Exception {
        //������ controlLessonBranch ���� ��!!!
        String results = "";

        String sql = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        try {
            results = "/contents/" + p_subj + "/" + p_lesson;
            System.out.println("***** ������>����>������������>Lesson(za_MasterFormLesson_U.jsp)�� ��츸 ȣ��Ǿ�� ������ �۵��� *****");

            /**
             * ���� �� �ּ� �������� String v_server="", v_port=""; String v_upperclass =
             * ""; //��з� String v_middleclass = ""; //�ߺз� connMgr = new
             * DBConnectionManager();
             * 
             * sql = " select upperclass, middleclass from tz_subj where subj="+
             * SQLString.Format(p_subj); ls = connMgr.executeQuery(sql);
             * if(ls.next()) { v_upperclass = ls.getString("upperclass");
             * v_middleclass = ls.getString("middleclass"); }
             * 
             * if(v_upperclass.equals("G01")) { //��з� ���Ӿ�ī���� results =
             * "/contents/"+v_middleclass+"/"+p_subj+"/"+p_lesson; } else {
             * if(!p_server.equals("")) v_server = v_server + ".";
             * if(!p_port.equals("80") && !p_port.equals("")) v_port =
             * ":"+p_port; results =
             * "http://"+v_server+GetCodenm.get_config("eduDomain"
             * )+v_port+"/"+p_dir+"/docs/"+p_lesson; }
             */
            if (p_gubun.equals("1"))
                results += "/start.html";

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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

        return results;
    }

    /**
     * preview URL �����
     * 
     * @param String ������Ÿ��,�����ڵ�,����,��Ʈ,���丮,�ܺ�preurl
     * @return String url
     */
    public static String make_previewURL(String p_contenttype, String p_subj, String p_server, String p_port, String p_dir, String p_preurl)
            throws Exception {
        // String results = "";
        String v_server = "", v_port = "";
        if (p_preurl != null && !p_preurl.equals(""))
            return p_preurl;

        if (!p_server.equals(""))
            v_server = v_server + ".";
        if (!p_port.equals("80") && !p_port.equals(""))
            v_port = ":" + p_port;
        if (p_contenttype.equals("O"))
            return make_eduURL(p_subj, "PREV", "PREV");
        else
            return "http://" + v_server + GetCodenm.get_config("eduDomain") + v_port + "/" + p_dir + "/guest/guest.html";
    }

    /**
     * preview URL �����
     * 
     * @param String �����ڵ�
     * @return String url
     */
    public static String make_previewURL(String p_subj) throws Exception {
        // String v_vals = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String v_contenttype = "";
        String v_server = "";
        String v_dir = "";
        String v_port = "";
        String v_preurl = "";
        String results = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  server, port, contenttype, preurl,dir  from tz_subj " + "  where  subj=" + SQLString.Format(p_subj);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_server = ls.getString("server");
                v_port = ls.getString("port");
                v_preurl = ls.getString("preurl");
                v_dir = ls.getString("dir");
                v_contenttype = ls.getString("contenttype");
            }
            if (v_preurl.equals(""))
                results = make_previewURL(v_contenttype, p_subj, v_server, v_port, v_dir, v_preurl);
            else
                results = v_preurl;

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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
        return results;
    }

    /**
     * �н��� branch ���
     * 
     * @param String �����ڵ�, �⵵, ��������, userid
     * @return int branch
     */
    public static int get_mybranch(String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        int resulti = 99;

        try {

            connMgr = new DBConnectionManager();

            sql.append(" SELECT  BRANCH  FROM TZ_STUDENT \n");
            sql.append("  WHERE  SUBJ = " + SQLString.Format(p_subj) + " \n");
            sql.append("    AND  YEAR = " + SQLString.Format(p_year) + " \n");
            sql.append("    AND  SUBJSEQ = " + SQLString.Format(p_subjseq) + " \n");
            sql.append("    AND  USERID = " + SQLString.Format(p_userid) + " \n");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                resulti = ls.getInt("branch");
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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

        return resulti;
    }

    /**
     * �н��� branch Set
     * 
     * @param String �����ڵ�, �⵵, ��������, userid, branch
     * @return int isOk
     */
    public static int set_mybranch(String p_subj, String p_year, String p_subjseq, String p_userid, int p_branch) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int resulti = 1;

        try {
            connMgr = new DBConnectionManager();
            sql = " update tz_student set branch= " + p_branch + "  where  subj=" + SQLString.Format(p_subj) + "    and  year="
                    + SQLString.Format(p_year) + "    and  subjseq=" + SQLString.Format(p_subjseq) + "    and  userid="
                    + SQLString.Format(p_userid);

            resulti = connMgr.executeUpdate(sql);
            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
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
        return resulti;
    }

    /**
     * ���н� �н���ü�� ���(����/��)
     * 
     * @param String �����ڵ�, �⵵, ��������, userid, ����Ÿ��(N/O/S),lesson, oid
     * @return int ���н���ü��
     */
    public static int get_noteducnt(String p_subj, String p_year, String p_subjseq, String p_userid, String p_contenttype, String p_lesson,
            String p_oid) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ResultSet rs = null;
        String sql = "", v_contenttype = "";
        int resulti = 99, v_basecnt = 0, v_educnt = 0;
        // int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            if (p_contenttype.equals("")) {
                sql = "select contenttype from tz_subj where subj=" + StringManager.makeSQL(p_subj);
                ls = connMgr.executeQuery(sql);
                ls.next();
                v_contenttype = ls.getString("contenttype");
            } else
                v_contenttype = p_contenttype;

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            // ����ƮŸ�Ժ� ���� �н���ü�� ��� (SCORM�� ���� �߰�)
            if (v_contenttype.equals("O")) {
                sql = "select count(oid) CNTS  from tz_subjobj ";

                if (p_oid.equals("EXAM")) {
                    sql = sql + " where subj=? and lesson<=?)";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, p_subj);
                    pstmt.setString(2, p_lesson);
                } else {
                    sql = sql + " where subj=? " + "   and ( (lesson < ?)" + "        or (lesson=? and "
                            + "            ordering < (select ordering from tz_subjobj where subj=? and lesson=? and oid=?) ) )";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, p_subj);
                    pstmt.setString(2, p_lesson);
                    pstmt.setString(3, p_lesson);
                    pstmt.setString(4, p_subj);
                    pstmt.setString(5, p_lesson);
                    pstmt.setString(6, p_oid);
                }

            } else if (v_contenttype.equals("N") || v_contenttype.equals("M")) {
                if (p_oid.equals("EXAM")) {
                    sql = "select count(lesson) CNTS from tz_subjlesson where subj=? and lesson<=?";
                } else {
                    sql = "select count(lesson) CNTS from tz_subjlesson where subj=? and lesson<?";
                }
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, p_subj);
                pstmt.setString(2, p_lesson);
            }
            rs = pstmt.executeQuery();
            rs.next();
            v_basecnt = rs.getInt("CNTS");
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }

            //Base �򰡼� ���
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            sql = "select count(ptype) CNTS from tz_exammaster where subj=? and lesson<? and year='TEST'";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_lesson);
            rs = pstmt.executeQuery();
            rs.next();
            v_basecnt += rs.getInt("CNTS");

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            //���н� ��ü�� ���
            if (v_contenttype.equals("O")) {
                sql = "select count(a.userid) CNTS  from tz_progress a, tz_subjobj b "
                        + " where a.subj=b.subj and a.oid=b.oid and a.subj=? and a.year=? and  a.subjseq=? and a.userid=?"
                        + "   and first_end is not null ";
                if (p_oid.equals("EXAM")) {
                    sql = sql + "  and b.lesson <=?";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, p_subj);
                    pstmt.setString(2, p_year);
                    pstmt.setString(3, p_subjseq);
                    pstmt.setString(4, p_userid);
                    pstmt.setString(5, p_lesson);
                } else {
                    sql = sql + "   and (b.lesson < ? or (b.lesson=? "
                            + "                         and b.ordering<(select ordering from tz_subjobj where subj=? and lesson=? and oid=?)))";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, p_subj);
                    pstmt.setString(2, p_year);
                    pstmt.setString(3, p_subjseq);
                    pstmt.setString(4, p_userid);
                    pstmt.setString(5, p_lesson);
                    pstmt.setString(6, p_lesson);
                    pstmt.setString(7, p_subj);
                    pstmt.setString(8, p_lesson);
                    pstmt.setString(9, p_oid);
                }

            } else if (v_contenttype.equals("N") || v_contenttype.equals("M")) {
                sql = "select count(userid) CNTS  from tz_progress where subj=? and year=? and subjseq=? and userid=?";
                if (p_oid.equals("EXAM")) {
                    sql = sql + "   and first_end is not null and  lesson <= ? ";
                } else {
                    sql = sql + "   and first_end is not null and  lesson < ? ";
                }
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, p_subj);
                pstmt.setString(2, p_year);
                pstmt.setString(3, p_subjseq);
                pstmt.setString(4, p_userid);
                pstmt.setString(5, p_lesson);
            }
            rs = pstmt.executeQuery();
            rs.next();
            v_educnt = rs.getInt("CNTS");
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            //Base �򰡰���� ���
            sql = "select count(userid) CNTS from tz_examresult where subj=? and year=? and subjseq=? and userid=? and lesson<? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_year);
            pstmt.setString(3, p_subjseq);
            pstmt.setString(4, p_userid);
            pstmt.setString(5, p_lesson);
            rs = pstmt.executeQuery();
            rs.next();
            v_educnt += rs.getInt("CNTS");

            resulti = v_basecnt - v_educnt; //=(�н��� ����/�򰡼�)-(�н��� ����/�򰡼�)

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return resulti;
    }

    /*
     * �� �ǽÿ��� üũ (��뺸�� ����) added 2003/10/24 icarus .. (for OBC)
     */
    public String chkeckExamGoOn(RequestBox box) throws Exception {
        // String v_vals = "";
        // DBConnectionManager connMgr = null;
        // ListSet ls = null;
        // String sql = "";
        String v_contenttype = "";
        String results = "Y";
        int resulti = 0;
        String p_subj = box.getSession("s_subj");
        String p_year = box.getSession("s_year");
        String p_subjseq = box.getSession("s_subjseq");
        String p_lesson = box.getString("p_lesson");
        String p_userid = box.getSession("userid");
        if (p_subj.equals("")) {
            p_subj = box.getString("p_subj");
        }
        if (p_year.equals("")) {
            p_year = box.getString("p_year");
        }
        if (p_subjseq.equals("")) {
            p_subjseq = box.getString("p_subjseq");
        }
        // int v_branch = 0;
        // Hashtable data = null;
        // String v_ptype = box.getString("p_ptype");
        // int v_papernum = 0;
        // String v_oid = "";
        //��뺸�� ��������
        //Added by LeeSuMin.. 2003.11.15
        if (GetCodenm.get_isgoyong_seq(p_subj, p_year, p_subjseq).equals("Y")
                && EduEtc1Bean.isCurrentStudent(p_subj, p_year, p_subjseq, p_userid).equals("Y")) {
            //OBC�̸� Object-ID�� ���� ptype���Ѵ�. ('TM0000001','TT00000002')
            if (GetCodenm.get_contenttype(p_subj).equals("O")) {
                // v_oid = box.getString("p_oid");
                // v_ptype = v_oid.substring(1, 2);
            }
            resulti = get_noteducnt(p_subj, p_year, p_subjseq, p_userid, v_contenttype, p_lesson, "EXAM");
            if (resulti > 0)
                results = "�������� ���� �н�Object�� �����Ƿ� �򰡸� ������ �� �����ϴ�.";
        }

        return results;
    }

    /**
     * Session_time ���
     * 
     * @param String �����Ͻ�(varchar2 14), �����Ͻ�(varchar2 14)
     * @return String session_time string (03:01:39.52)
     */
    public static String get_duringtime(String p_s, String p_e) throws Exception {
        String results = "";
        int gap = 0;
        try {
            long l_gap = FormatDate.getTimeDifference(p_s, p_e);
            int hh = (int) (l_gap / (1000 * 60 * 60));
            gap = (int) (l_gap - hh * 1000 * 60 * 60);
            /*
             * int mm = (int)(l_gap/(1000*60)); int ss = (int)(l_gap/(1000));
             * int ms = (int)l_gap;
             */
            int mm = (gap / (1000 * 60));
            gap = gap - mm * 1000 * 60;
            int ss = (gap / 1000);
            gap = gap - ss * 1000;
            int ms = gap;
            results = (new DecimalFormat("00").format(hh)) + ":" + (new DecimalFormat("00").format(mm)) + ":"
                    + (new DecimalFormat("00").format(ss)) + "." + (new DecimalFormat("00").format(ms));
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
        }

        return results;
    }

    /**
     * Session_time ���ϱ�
     * 
     * @param String ���ҳ�1(03:01:39.52), ���ҳ�2(03:01:39.52)
     * @return String total_time string (03:01:39.52)
     */
    public static String add_duringtime(String p_s, String p_e) throws Exception {
        String results = "";

        int hh = 0;
        int mm = 0;
        float ss = (float) 0.0;

        ss = Float.parseFloat(p_s.substring(6, 11)) + Float.parseFloat(p_e.substring(6, 11));
        if (ss > 60.0) {
            ss = ss - (float) 60.0;
            mm = mm + 1;
        }

        ss = ((int) (ss * 100)) / (float) 100.00;

        mm = mm + Integer.parseInt(p_s.substring(3, 5)) + Integer.parseInt(p_e.substring(3, 5));
        if (mm > 60) {
            mm = mm - 60;
            hh = hh + 1;
        }
        hh = hh + Integer.parseInt(p_s.substring(0, 2)) + Integer.parseInt(p_e.substring(0, 2));

        results = (new DecimalFormat("00").format(hh)) + ":" + (new DecimalFormat("00").format(mm)) + ":" + (new DecimalFormat("00.00").format(ss));

        return results;
    }

    /**
     * �н����� ��ũ �����
     * 
     * @param String �����ڵ�, �⵵, ��������, userid
     * @return String
     */
    public static String get_starting(String p_subj, String p_year, String p_subjseq, String p_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        String sql = "";
        String v_contenttype = "";
        String results = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  contenttype  from tz_subj " + "  where  subj=" + SQLString.Format(p_subj);
            ls = connMgr.executeQuery(sql);
            ls.next();
            v_contenttype = ls.getString("contenttype");

            if (v_contenttype.equals("N") || v_contenttype.equals("M")) {
                sql = " select  ltrim(to_char(to_number(max(lesson))+1,'000'))  Maxlesson" + "   from tz_progress " + "  where  subj="
                        + SQLString.Format(p_subj) + "    and  year=" + SQLString.Format(p_year) + "    and  subjseq="
                        + SQLString.Format(p_subjseq) + "    and  userid=" + SQLString.Format(p_userid) + "    and  first_end is not null ";
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }
                ls = connMgr.executeQuery(sql);
                if (ls.next()) {
                    results = ls.getString("Maxlesson");
                } else {
                    results = "001";
                }

                sql = "select count(lesson) CNTS from tz_subjlesson where subj=" + SQLString.Format(p_subj) + "   and lesson=ltrim('" + results
                        + "')";

                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }
                ls = connMgr.executeQuery(sql);
                ls.next();
                if (ls.getInt("CNTS") == 0)
                    results = "001";

                results = "('" + results + "')";
            } else if (v_contenttype.equals("O")) {
                String v_module = "001", v_lesson = "001", v_oid = "1000000001";
                if (!p_year.equals("PREV")) {
                    sql = "select subj,module,lesson,oid,ordering " + "  from tz_subjobj  where subj=" + StringManager.makeSQL(p_subj)
                            + " order by lesson,ordering";
                    System.out.println("sql ==>" + sql);

                    if (ls != null) {
                        try {
                            ls.close();
                        } catch (Exception e) {
                        }
                    }
                    ls = connMgr.executeQuery(sql);
                    while (ls.next()) {
                        sql = "select count(*) CNTS from tz_progress " + " where subj=" + StringManager.makeSQL(p_subj) + "   and year="
                                + StringManager.makeSQL(p_year) + "   and subjseq=" + StringManager.makeSQL(p_subjseq) + "   and userid="
                                + StringManager.makeSQL(p_userid) + "   and lesson=" + StringManager.makeSQL(ls.getString("lesson"))
                                + "   and oid=" + StringManager.makeSQL(ls.getString("oid"));
                        if (ls2 != null) {
                            try {
                                ls2.close();
                            } catch (Exception e) {
                            }
                        }
                        ls2 = connMgr.executeQuery(sql);
                        ls2.next();
                        if (ls2.getInt("CNTS") == 0) {
                            v_module = ls.getString("module");
                            v_lesson = ls.getString("lesson");
                            v_oid = ls.getString("oid");
                            break;
                        }

                    }
                    results = "('" + v_module + "','" + v_lesson + "','" + v_oid + "')";

                } else {
                    results = "('001','001','0000000000')";
                }
            }
            System.out.println("v_contenttype ==>" + v_contenttype);
            System.out.println("results ==>" + results);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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
        return results;
    }

    /**
     * �н�â ����URL �����(��Ÿ�׽�Ʈ ���ǰ����� ���
     * 
     * @param String �����ڵ�, String ����("PGM":����Url,"DOC":Document-Base)
     * @return String tz_config.vals
     */
    public static String make_eduURL_beta(String p_subj, String p_gubun) throws Exception {
        // String v_vals = "";
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        // String v_server = "";
        String v_dir = "";
        // String v_port = "";
        // String v_eduurl = "";
        // String v_domain = GetCodenm.get_config("eduDomain");

        String results = "";

        try {
            connMgr = new DBConnectionManager();
            sql = " select  contenttype, dir  from tz_subj " + "  where  subj=" + SQLString.Format(p_subj);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                //v_server = ls.getString("server");
                //v_port   = ls.getString("port");
                //v_eduurl = ls.getString("eduurl");
                v_dir = ls.getString("dir");
            }

            //if (v_eduurl.equals("")){
            //if(!v_server.equals(""))                        v_server = v_server + ".";
            //if(!v_port.equals("80") && !v_port.equals(""))  v_port   = ":"+v_port;

            if (p_gubun.equals("DOC")) { // Contents Document Base
                //                    results = "http://"+v_server+v_domain+v_port+"/"+v_dir+"/";
                results = "/" + v_dir + "/";
            } else { // Servlet Url
                //                  results = "http://"+v_server+v_domain+v_port+"/servlet/controller.beta.BetaEduStart?p_subj="+p_subj;
                results = "/servlet/controller.beta.BetaEduStart?p_subj=" + p_subj;
            }
            //}else{
            //   results = v_eduurl;
            //}

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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
        return results;
    }

    /**
     * �н�â ����URL �����
     * 
     * @param String �����ڵ�, String ����("PGM":����Url,"DOC":Document-Base)
     * @return String tz_config.vals
     */
    public static String make_eduURL_beta(String p_subj, String p_year, String p_subjseq) throws Exception {
        String v_year = "2000", v_subjseq = "0001";
        if (!p_year.equals(""))
            v_year = p_year;
        if (!p_subjseq.equals(""))
            v_subjseq = p_subjseq;

        return EduEtc1Bean.make_eduURL_beta(p_subj, "PGM") + "&p_year=" + v_year + "&p_subjseq=" + v_subjseq;

    }

    /**
     * ���ް��� ������ �������� üũ
     * 
     * @param String �����ڵ�
     * @return String
     */
    public static String hasPreviewObj(String p_subj) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String v_hasPreviewObj = "N";

        try {
            connMgr = new DBConnectionManager();
            sql = " select a.subj                      ";
            sql += "   from tz_previewobj a, tz_subj b  ";
            sql += "  where a.subj = b.subj             ";
            sql += "    and b.contenttype in ('S','O')         ";
            sql += "    and b.subj = '" + p_subj + "'";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_hasPreviewObj = "Y";
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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
        return v_hasPreviewObj;
    }

    /**
     * ���� URL
     * 
     * @param String �����ڵ�
     * @return String
     */
    public static String getCpEduurl(String userid, String p_subj, String p_year, String p_subjseq, String p_gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "N";

        try {
            connMgr = new DBConnectionManager();
            sql = " select get_cpsubjeduurl( '" + userid + "','" + p_subj + "','" + p_year + "','" + p_subjseq + "','" + p_gadmin + "' ) cpurl";
            sql += "   from dual  ";
            System.out.println("sql+=====" + sql);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                result = ls.getString("cpurl");
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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
        return result;
    }
}
