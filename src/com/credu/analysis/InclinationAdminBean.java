package com.credu.analysis;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.research.SulmunAllBean;
import com.credu.research.SulmunExampleData;
import com.credu.research.SulmunQuestionExampleData;
import com.credu.research.SulmunRegistBean;
import com.credu.system.SelectionUtil;

public class InclinationAdminBean {

    public InclinationAdminBean() {

    }

    /**
     * 직업별 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectJobList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_grcode     = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_action = box.getString("p_action");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if (v_action.equals("go")) {

                //              sql  = " select c.code, c.codenm, isnull(d.comlcnt,0) comlcnt, isnull(d.ncomlcnt,0) ncomlcnt   ";
                //				sql += "   from tz_code c,                                                                     ";
                //				sql += " 	   ( select a.subj, b.jikup,                                                       ";
                //				sql += " 	            sum(case  a.isgraduated when  'Y'  then 1  else  0 end) comlcnt,       ";  
                //				sql += " 		        sum(case  a.isgraduated when  'N'  then 1  else  0 end) ncomlcnt       ";  
                //				sql += " 		  from tz_stold a, tz_member b                                                 ";
                //				sql += " 		 where a.subj in (select subjcourse from tz_grsubj where grcode='"+v_grcode+"')";
                //				sql += " 		   and a.userid = b.userid                                                     ";
                //				sql += " 		   and subj = '"+ v_subj +"'                                                   ";
                //				sql += " 		 group by a.subj,  b.jikup                                                     ";
                //				sql += "        ) d                                                                            ";
                //				sql += "  where c.gubun ='0070'                                                                ";
                //				sql += "    and c.code  =  d.jikup			                                                  (+) ";
                //				sql += "  order by code                                                                        ";

                sql = "select  *                                                                 \n ";
                sql += "from    (                                                                 \n ";
                sql += "        select                                                            \n ";
                sql += "                code, codenm                                              \n ";
                sql += "                , sum(comlcnt) comlcnt                                    \n ";
                sql += "                , sum(ncomlcnt) ncomlcnt                                  \n ";
                sql += "        from                                                              \n ";
                sql += "                (                                                         \n ";
                sql += "                select  decode(c.code, null, '기타', c.code) code         \n ";
                sql += "                        , decode(c.codenm, null, '기타', c.codenm) codenm \n ";
                sql += "                        , decode(a.isgraduated, 'Y', 1, 0 ) comlcnt       \n ";
                sql += "                        , decode(a.isgraduated, 'N', 1, 0 ) ncomlcnt      \n ";
                sql += "                from    tz_stold a, tz_member b, tz_code c                \n ";
                sql += "                where   a.userid = b.userid(+)                            \n ";
                sql += "                and     subj = '" + v_subj + "'                             \n ";
                sql += "                and     jikup   = code(+)                                 \n ";
                sql += "                and     c.gubun(+) ='0070'                                \n ";
                sql += "                )                                                         \n ";
                sql += "        group by code, codenm                                             \n ";
                sql += "        )                                                                 \n ";
                sql += "order by code                                                             \n ";

                //				System.out.println("InclinationAdminBean selectJobList : "+sql);

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
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
     * 학력별 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectCarrerList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_grcode = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_action = box.getString("p_action");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if (v_action.equals("go")) {
                /*
                 * sql =
                 * " select c.code, c.codenm, isnull(d.comlcnt,0) comlcnt, isnull(d.ncomlcnt,0) ncomlcnt   "
                 * ; sql +=
                 * "   from tz_code c,                                                                     "
                 * ; sql +=
                 * " 	   ( select a.subj, b.degree,                                                      "
                 * ; sql +=
                 * " 	            sum(case  a.isgraduated when  'Y'  then 1  else  0 end) comlcnt,       "
                 * ; sql +=
                 * " 		        sum(case  a.isgraduated when  'N'  then 1  else  0 end) ncomlcnt       "
                 * ; sql +=
                 * " 		  from tz_stold a, tz_member b                                                 "
                 * ; sql +=
                 * " 		 where a.subj in (select subjcourse from tz_grsubj where grcode='"
                 * +v_grcode+"')"; sql +=
                 * " 		   and a.userid = b.userid                                                     "
                 * ; sql += " 		   and subj = '"+ v_subj
                 * +"'                                                   "; sql
                 * +=
                 * " 		 group by a.subj,  b.degree                                                    "
                 * ; sql +=
                 * "        ) d                                                                            "
                 * ; sql +=
                 * "  where c.gubun ='0069'                                                                "
                 * ; sql +=
                 * "    and c.code  =  d.degree			                                                  (+) "
                 * ; sql +=
                 * "  order by code                                                                        "
                 * ;
                 */

                sql = "select  *                                                                 \n ";
                sql += "from    (                                                                 \n ";
                sql += " select  decode(a.code, null, '기타', a.code) code" + "		 , decode(a.codenm, null, '기타', a.codenm) codenm                    \n ";
                sql += "         , sum(b.comlcnt) comlcnt, sum(b.ncomlcnt) ncomlcnt                         \n ";
                sql += " from    tz_code a                                                                  \n ";
                sql += "         , (                                                                        \n ";
                sql += "             select  a.subj                                                         \n ";
                sql += "                     , case when b.degree in ( null, ' ', '대졸', 'kk') then '기타' \n ";
                sql += "                            else b.degree                                           \n ";
                sql += "                     end degree                                                     \n ";
                sql += "                     , decode(a.isgraduated, 'Y', 1, 0 ) comlcnt                    \n ";
                sql += "                     , decode(a.isgraduated, 'N', 1, 0 ) ncomlcnt                   \n ";
                sql += "             from   tz_stold a, tz_member b                                         \n ";
                sql += "             where a.userid = b.userid(+)                                           \n ";
                sql += "             and subj = '" + v_subj + "'                                              \n ";
                sql += "         ) b                                                                        \n ";
                sql += " where   a.code(+)  =  b.degree                                                     \n ";
                sql += " and     a.gubun(+) ='0069'                                                         \n ";
                sql += " group by a.code, a.codenm                                                          \n ";
                sql += "        )                                                                 \n ";
                sql += "order by code                                                             \n ";

                //				System.out.println("InclinationAdminBean selectCarrerList : "+sql);

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
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
     * 성별 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectGenderList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_grcode = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_action = box.getString("p_action");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if (v_action.equals("go")) {
                sql = " select a.sex, sum(a.comlcnt) comlcnt,  sum(a.ncomlcnt) ncomlcnt     \n";
                sql += "    from                                                             \n";
                sql += "    ( select a.userid,                                               \n";
                sql += "             case a.isgraduated when 'Y' then 1 else 0 end comlcnt,  \n";
                sql += "             case a.isgraduated when 'N' then 1 else 0 end ncomlcnt, \n";
                sql += "             CASE to_number(SUBSTRING(b.resno,7,1)) WHEN 1 THEN 1 WHEN 3 THEN 1 \n";
                sql += "                                         WHEN 2 THEN 2 WHEN 4 THEN 2 \n";
                sql += "                                         ELSE to_number(SUBSTRING(b.resno,7,1)) \n";
                sql += "                                         END  AS SEX                 \n";
                sql += "        from tz_stold a, tz_member b                                 \n";
                sql += " 	   where a.subj in (select subjcourse from tz_grsubj             \n";
                sql += "                         where grcode='" + v_grcode + "')            \n";
                sql += "         and a.userid = b.userid (+)                                 \n";
                sql += "         and a.subj = '" + v_subj + "'                               \n";
                sql += "     ) a                                                             \n";
                sql += "  group by a.sex                                                     \n";
                sql += "  order by a.sex                                                     \n";
                //				System.out.println("InclinationAdminBean selectGenderList : "+sql);

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
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
     * 나이별 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectAgeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_grcode = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_action = box.getString("p_action");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if (v_action.equals("go")) {
                /*
                 * sql =
                 * " select a.age, sum(a.comlcnt) comlcnt,  sum(a.ncomlcnt) ncomlcnt     "
                 * ; sql +=
                 * "    from                                                             "
                 * ; sql +=
                 * "    ( select a.userid,                                               "
                 * ; sql +=
                 * "             case a.isgraduated when 'Y' then 1 else 0 end comlcnt,  "
                 * ; sql +=
                 * "             case a.isgraduated when 'N' then 1 else 0 end ncomlcnt, "
                 * ; sql +=
                 * "   	        (to_number(TO_CHAR(SYSDATE, 'YYYY') -                                 "
                 * ; sql +=
                 * "   			     CASE to_number(SUBSTRING(b.resno,7,1)) WHEN 1 THEN to_number(LEFT(b.resno,2))+1900 "
                 * ; sql +=
                 * "   			                                 WHEN 2 THEN to_number(LEFT(b.resno,2))+1900 "
                 * ; sql +=
                 * "   		                                     WHEN 3 THEN to_number(LEFT(b.resno,2))+2000 "
                 * ; sql +=
                 * "   		                                     WHEN 4 THEN to_number(LEFT(b.resno,2))+2000 "
                 * ; sql +=
                 * "   		                                     END ) / 10 ) * 10 AS AGE         "
                 * ; sql +=
                 * "        from tz_stold a, tz_member b                                 "
                 * ; sql +=
                 * " 	   where a.subj in (select subjcourse from tz_grsubj             "
                 * ; sql += "                         where grcode='" + v_grcode
                 * + "')            "; sql +=
                 * "         and a.userid = b.userid(+)                                  "
                 * ; sql += "         and a.subj = '" + v_subj +
                 * "'                               "; sql +=
                 * "     ) a                                                             "
                 * ; sql +=
                 * "  group by a.age                                                     "
                 * ; sql +=
                 * "  order by a.age                                                     "
                 * ;
                 */

                sql = " select  age                                                                                                  \n";
                sql += "         , sum(comlcnt) comlcnt                                                                               \n";
                sql += "         ,  sum(ncomlcnt) ncomlcnt                                                                            \n";
                sql += " from                                                                                                         \n";
                sql += "         (                                                                                                    \n";
                sql += "         select  case when trunc((a.age / 10), 0 ) = 1 then 10                                                     \n";
                sql += "                      when trunc((a.age / 10), 0 ) = 2 then 20                                                     \n";
                sql += "                      when trunc((a.age / 10), 0 ) = 3 then 30                                                     \n";
                sql += "                      when trunc((a.age / 10), 0 ) = 4 then 40                                                     \n";
                sql += "                      when trunc((a.age / 10), 0 ) = 5 then 50                                                     \n";
                sql += "                      else 0                                                                                  \n";
                sql += "                 end age                                                                                      \n";
                sql += "                 , comlcnt                                                                                    \n";
                sql += "                 , ncomlcnt                                                                                   \n";
                sql += "         from                                                                                                 \n";
                sql += "                 (                                                                                            \n";
                sql += "                 select  case a.isgraduated when 'Y' then 1 else 0 end comlcnt,                               \n";
                sql += "                         case a.isgraduated when 'N' then 1 else 0 end ncomlcnt,                              \n";
                sql += "                         (to_number(a.year -                                                \n";
                sql += "                          CASE to_number(SUBSTRING(b.resno,7,1)) WHEN 1 THEN to_number(LEFT(b.resno,2))+1900  \n";
                sql += "                                                      WHEN 2 THEN to_number(LEFT(b.resno,2))+1900             \n";
                sql += "                                                      WHEN 3 THEN to_number(LEFT(b.resno,2))+2000             \n";
                sql += "                                                      WHEN 4 THEN to_number(LEFT(b.resno,2))+2000             \n";
                sql += "                                                      END ) / 10 ) * 10 AS AGE                                \n";
                sql += "                from     tz_stold a, tz_member b                                                              \n";
                sql += "                where    a.userid = b.userid(+)                                                               \n";
                sql += "                and      a.subj = '" + v_subj + "'                                                                 \n";
                sql += "                 )   a                                                                                        \n";
                sql += "         )                                                                                                    \n";
                sql += " group by    age";

                //				System.out.println("InclinationAdminBean selectAgeList : "+sql);

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
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
     * 직업별 팝업 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectJobDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_grcode = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_code = box.getString("p_code");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select a.subj, a.year, a.subjseq, a.userid, b.name, b.membergubun           ";
            sql += "   from tz_stold a, tz_member b                                              ";
            sql += "  where a.subj in (select subjcourse from tz_grsubj where grcode = '" + v_grcode + "') ";
            sql += "    and a.userid = b.userid                                                  ";
            sql += "    and a.subj =  " + SQLString.Format(v_subj);
            sql += "    and b.jikup = " + SQLString.Format(v_code);
            sql += "  order by a.subj, a.year, a.subjseq, a.userid                               ";
            //			System.out.println("InclinationAdminBean selectJobDetailList : "+sql);

            sql = " select  *                                                                           \n";
            sql += " from    (                                                                           \n";
            sql += "         select                                                                      \n";
            sql += "                 subj, year, subjseq, userid, name, membergubun, jikup               \n";
            sql += "         from                                                                        \n";
            sql += "                 (                                                                   \n";
            sql += "                 select  a.subj, a.year, a.subjseq, a.userid, b.name, b.membergubun  \n";
            sql += "                         , decode(c.code, null, '기타', c.code) jikup                  \n";
            sql += "                         , decode(c.codenm, null, '기타', c.codenm) codenm             \n";
            sql += "                 from    tz_stold a, tz_member b, tz_code c                          \n";
            sql += "                 where   a.userid = b.userid(+)                                      \n";
            sql += "                 and     subj = " + SQLString.Format(v_subj) + "                       \n";
            sql += "                 and     jikup   = code(+)                                           \n";
            sql += "                 and     c.gubun(+) ='0070'                                          \n";
            sql += "                 )                                                                   \n";
            sql += "         )                                                                           \n";
            sql += " where   jikup  = " + SQLString.Format(v_code) + "                                   \n";
            sql += " order by subj, year, subjseq, userid                                                \n";

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
     * 학력별 팝업 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectCarrerDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_grcode = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_code = box.getString("p_code");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            /*
             * sql =
             * " select a.subj, a.year, a.subjseq, a.userid, b.name, b.membergubun            "
             * ; sql +=
             * "   from tz_stold a, tz_member b                                              "
             * ; sql +=
             * "  where a.subj in (select subjcourse from tz_grsubj where grcode = '"
             * + v_grcode + "') "; sql +=
             * "    and a.userid(+) = b.userid                                                  "
             * ; sql += "    and a.subj =  " + SQLString.Format(v_subj); sql +=
             * "    and b.degree(+) = " + SQLString.Format(v_code); sql +=
             * "  order by a.subj, a.year, a.subjseq, a.userid                               "
             * ;
             */

            sql = " select *                                                                               \n ";
            sql += " from                                                                                   \n ";
            sql += "         (                                                                              \n ";
            sql += "         select  a.subj, a.year, a.subjseq, a.userid, a.name, a.membergubun             \n ";
            sql += "                 , decode(b.code, null, '기타', b.code) degree                          \n ";
            sql += "         from                                                                           \n ";
            sql += "                 (                                                                      \n ";
            sql += "                 select  a.subj, a.year, a.subjseq, a.userid, b.name, b.membergubun     \n ";
            sql += "                         , case when b.degree in ( null, ' ', '대졸', 'kk') then '기타'     \n ";
            sql += "                                else b.degree                                           \n ";
            sql += "                         end degree                                                     \n ";
            sql += "                 from    tz_stold a, tz_member b                                        \n ";
            sql += "                 where   a.userid    = b.userid(+)                                      \n ";
            sql += "                 and     subj        = " + SQLString.Format(v_subj) + "                 \n ";
            sql += "                 ) a                                                                    \n ";
            sql += "                 , tz_code b                                                            \n ";
            sql += "         where   a.degree    = b.code(+)                                                \n ";
            sql += "         and     b.gubun(+)   = '0069'                                                  \n ";
            sql += "         )                                                                              \n ";
            sql += " where   degree = " + SQLString.Format(v_code) + "                                      \n ";
            sql += " order by subj, year, subjseq, userid                                                   \n ";

            // 		   System.out.println("InclinationAdminBean selectCarrerDetailList : "+sql);

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
     * 성별 팝업 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectGenderDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_grcode = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_code = box.getString("p_code");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            /*
             * sql =
             * " select  a.subj, a.year, a.subjseq, a.userid, a.name, a.membergubun, a.SEX          "
             * ; sql +=
             * "   from                                                                              "
             * ; sql +=
             * "      ( select  a.subj, a.year, a.subjseq, a.userid, b.name, b.membergubun,          "
             * ; sql +=
             * "                case  a.isgraduated when 'Y' then 1 else 0 end  comlcnt,             "
             * ; sql +=
             * "                case  a.isgraduated when 'N' then 1 else 0 end  ncomlcnt,            "
             * ; sql +=
             * "                CASE SUBSTRING(b.resno,7,1) WHEN 1 THEN 1 WHEN 3 THEN 1              "
             * ; sql +=
             * "                                            WHEN 2 THEN 2 WHEN 4 THEN 2              "
             * ; sql +=
             * "                                            END  AS SEX                              "
             * ; sql +=
             * "          from tz_stold a, tz_member b                                           "
             * ; sql +=
             * "         where a.subj in (select subjcourse from tz_grsubj where grcode='"
             * + v_grcode + "') "; sql +=
             * "           and a.userid = b.userid                                                   "
             * ; sql += "           and a.subj = " + SQLString.Format(v_subj);
             * sql +=
             * "         ) a                                                                         "
             * ; sql += "  where sex = " + SQLString.Format(v_code); sql +=
             * "  order by a.subj, a.year, a.subjseq, a.userid                                       "
             * ;
             */

            sql = "select  a.subj, a.year, a.subjseq, a.userid, a.name, a.membergubun, a.SEX   \n";
            sql += "from    (                                                                   \n";
            sql += "        select  a.subj, a.year, a.subjseq, a.userid, b.name, b.membergubun  \n";
            sql += "                , case a.isgraduated when 'Y' then 1 else 0 end comlcnt     \n";
            sql += "                , case a.isgraduated when 'N' then 1 else 0 end ncomlcnt    \n";
            sql += "                , CASE SUBSTRING(b.resno,7,1) WHEN '1' THEN '1'             \n";
            sql += "                                              WHEN '3' THEN '1'             \n";
            sql += "                                              WHEN '2' THEN '2'             \n";
            sql += "                                              WHEN '4' THEN '2'             \n";
            sql += "                END AS SEX                                                  \n";
            sql += "        from    tz_stold a, tz_member b                                     \n";
            sql += "        where   a.userid = b.userid(+)                                      \n";
            sql += "        and     a.subj = " + SQLString.Format(v_subj) + "                    \n";
            sql += "        ) a                                                                 \n";
            sql += "where   sex = " + SQLString.Format(v_code) + "                              \n";
            sql += "order by a.subj, a.year, a.subjseq, a.userid                                \n";

            //			System.out.println("InclinationAdminBean selectGenderDetailList : "+sql);

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
     * 나이별 팝업 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectAgeDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_grcode = box.getString("s_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_code = box.getString("p_code");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            /*
             * sql =
             * " select  a.subj, a.year, a.subjseq, a.userid, a.name, a.membergubun, a.AGE          "
             * ; sql +=
             * "   from                                                                              "
             * ; sql +=
             * "      ( select  a.subj, a.year, a.subjseq, a.userid, b.name, b.membergubun,          "
             * ; sql +=
             * "                case  a.isgraduated when 'Y' then 1 else 0 end  comlcnt,             "
             * ; sql +=
             * "                case  a.isgraduated when 'N' then 1 else 0 end  ncomlcnt,            "
             * ; sql +=
             * "   	        ((TO_CHAR(SYSDATE, 'YYYY') -                                 "
             * ; sql +=
             * "   			     CASE SUBSTRING(b.resno,7,1) WHEN 1 THEN LEFT(b.resno,2)+1900 "
             * ; sql +=
             * "   			                                 WHEN 2 THEN LEFT(b.resno,2)+1900 "
             * ; sql +=
             * "   		                                     WHEN 3 THEN LEFT(b.resno,2)+2000 "
             * ; sql +=
             * "   		                                     WHEN 4 THEN LEFT(b.resno,2)+2000 "
             * ; sql +=
             * "   		                                     END ) / 10 ) * 10 AS AGE         "
             * ; sql +=
             * "          from tz_stold a, tz_member b                                           "
             * ; sql +=
             * "         where a.subj in (select subjcourse from tz_grsubj where grcode='"
             * + v_grcode + "') "; sql +=
             * "           and a.userid = b.userid                                                   "
             * ; sql += "           and a.subj = " + SQLString.Format(v_subj);
             * sql +=
             * "         ) a                                                                         "
             * ; sql += "  where AGE = " + SQLString.Format(v_code); sql +=
             * "  order by a.subj, a.year, a.subjseq, a.userid                                       "
             * ;
             */

            sql += " select  a.subj, a.year, a.subjseq, a.userid, a.name, a.membergubun, age                             \n";
            sql += " from                                                                                                \n";
            sql += "         (                                                                                           \n";
            sql += "         select  a.subj, a.year, a.subjseq, a.userid, b.name, b.membergubun,                         \n";
            sql += "                 case a.isgraduated when 'Y' then 1 else 0 end comlcnt,                              \n";
            sql += "                 case a.isgraduated when 'N' then 1 else 0 end ncomlcnt,                             \n";
            sql += "                 (to_number(a.year -                                                                 \n";
            sql += "                  CASE to_number(SUBSTRING(b.resno,7,1)) WHEN 1 THEN to_number(LEFT(b.resno,2))+1900 \n";
            sql += "                                              WHEN 2 THEN to_number(LEFT(b.resno,2))+1900            \n";
            sql += "                                              WHEN 3 THEN to_number(LEFT(b.resno,2))+2000            \n";
            sql += "                                              WHEN 4 THEN to_number(LEFT(b.resno,2))+2000            \n";
            sql += "                                              END ) / 10 ) * 10 AS AGE                               \n";
            sql += "        from     tz_stold a, tz_member b                                                             \n";
            sql += "        where    a.userid = b.userid(+)                                                              \n";
            sql += "        and      a.subj = " + SQLString.Format(v_subj) + "                                           \n";
            sql += "         )   a                                                                                       \n";
            sql += " where  trunc((a.age / 10), 0 ) * 10 = " + SQLString.Format(v_code) + "                              \n";
            sql += " order by a.subj, a.year, a.subjseq, a.userid                                                        \n";

            //				System.out.println("InclinationAdminBean selectAgeDetailList : "+sql);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            // }
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
     * 과정설문 분석하기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<SulmunQuestionExampleData> SelectObectResultAnalisys(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<SulmunQuestionExampleData> list = null;
        String v_action = box.getString("p_action");
        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = getObjectSulmunAnalysis(connMgr, box);
            } else {
                list = new ArrayList<SulmunQuestionExampleData>();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
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
     * 과정설문 결과 분석 receive from the form object and session
     * 
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList<SulmunQuestionExampleData> getObjectSulmunAnalysis(DBConnectionManager connMgr, RequestBox box) throws Exception {
        Vector<String> v_sulnums = null;
        ArrayList<SulmunQuestionExampleData> QuestionExampleDataList = null;
        Vector<String> v_answers = null;

        String v_subj = box.getString("s_subjcourse"); //tlfwp 과정코드 
        String v_subjseq = box.getStringDefault("s_subjseq", "0001");
        int v_sulpapernum = box.getInt("s_sulpapernum");

        String v_grseq = box.getStringDefault("s_grseq", "0001");

        // 설문번호 선택조건
        String v_grcode = box.getString("p_grcode");
        //String v_gyear  = box.getString("p_gyear");
        String v_gyear = box.getString("s_gyear");
        int v_studentcount = 0;

        try {

            // 설문지번호에 해당하는 설문번호를 vector로 받아온다. 벡터(설문번호1,3,5 ....)
            v_sulnums = getSulnums(connMgr, v_grcode, "ALL", v_sulpapernum);

            // 설문번호에 해당하는 문제리스트를 만든다. 리스트((설문번호1, 보기1,2,3..))
            QuestionExampleDataList = getSelnums(connMgr, v_grcode, "ALL", v_sulnums);

            // 설문지 답변모음을 읽어온다. 벡터(답변1,3,2,3..)
            v_answers = getSulmunAnswers(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_sulpapernum, v_subjseq);
            // 리스트((설문번호1, 보기1,2,3..)) + 벡터(답변1,3,2,3..) 이용해서 응답자수 및 비율을 계산한다.
            ComputeCount(QuestionExampleDataList, v_answers);
            // 응답자수
            box.put("p_replycount", String.valueOf(v_answers.size()));
            // 수강자수
            v_studentcount = getStudentCount(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_subjseq);
            box.put("p_studentcount", String.valueOf(v_studentcount));
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return QuestionExampleDataList;
    }

    /**
     * 문제번호
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public Vector<String> getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql = "";
        Vector<String> v_sulnums = new Vector<String>();
        String v_tokens = "";
        StringTokenizer st = null;

        try {
            sql = "select sulnums  ";
            sql += "  from tz_sulpaper ";
            sql += " where grcode      = " + SQLString.Format(p_grcode);
            sql += "   and subj        = " + SQLString.Format(p_subj);
            sql += "   and sulpapernum = " + SQLString.Format(p_sulpapernum);
            //			System.out.println("SulmunAllResultBean 설문번호 getSulnums:"+sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }

            st = new StringTokenizer(v_tokens, SulmunAllBean.SPLIT_COMMA);
            while (st.hasMoreElements()) {
                v_sulnums.add((String) st.nextToken());
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_sulnums;
    }

    public ArrayList<SulmunQuestionExampleData> getSelnums(DBConnectionManager connMgr, String p_grcode, String p_subj, Vector<String> p_sulnums) throws Exception {
        Hashtable<String, SulmunQuestionExampleData> hash = new Hashtable<String, SulmunQuestionExampleData>();
        ArrayList<SulmunQuestionExampleData> list = new ArrayList<SulmunQuestionExampleData>();

        ListSet ls = null;
        String sql = "";
        SulmunQuestionExampleData data = null;
        SulmunExampleData exampledata = null;
        int v_bef_sulnum = 0;

        String v_sulnums = "";
        for (int i = 0; i < p_sulnums.size(); i++) {
            v_sulnums += (String) p_sulnums.get(i);
            if (i < p_sulnums.size() - 1) {
                v_sulnums += ", ";
            }
        }
        if (v_sulnums.equals(""))
            v_sulnums = "-1";

        try {
            sql = "select a.subj,     a.sulnum, ";
            sql += "        a.distcode, c.codenm distcodenm, ";
            sql += "       a.sultype,  d.codenm sultypenm, ";
            sql += "       a.sultext,  b.selnum, b.seltext, b.selpoint ";
            sql += "  from tz_sul     a, ";
            sql += "       tz_sulsel  b, ";
            sql += "       tz_code    c, ";
            sql += "       tz_code    d  ";
            // 수정일 : 05.11.04 수정자 : 이나연 _ (+) 수정
            //            sql+= " where a.subj     = b.subj(+)    ";
            //            sql+= "   and a.sulnum   = b.sulnum(+)  ";
            //            sql+= "   and a.grcode  = b.grcode(+) ";
            sql += " where a.subj      =  b.subj   (+) ";
            sql += "   and a.sulnum    =  b.sulnum (+) ";
            sql += "   and a.grcode    =  b.grcode(+) ";
            sql += "   and a.distcode = c.code ";
            sql += "   and a.sultype  = d.code ";
            sql += "   and a.subj     = " + SQLString.Format(p_subj);
            sql += "   and a.grcode     = " + SQLString.Format(p_grcode);
            sql += "   and a.sulnum in (" + v_sulnums + ")";
            sql += "   and c.gubun    = " + SQLString.Format(SulmunAllBean.DIST_CODE);
            sql += "   and d.gubun    = " + SQLString.Format(SulmunAllBean.SUL_TYPE);
            sql += " order by a.subj, a.sulnum, b.selnum ";

            //			System.out.println("SulmunAllResultBean 문제리스트:"+sql);			

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                if (v_bef_sulnum != ls.getInt("sulnum")) {
                    data = new SulmunQuestionExampleData();
                    data.setSubj(ls.getString("subj"));
                    data.setSulnum(ls.getInt("sulnum"));
                    data.setSultext(ls.getString("sultext"));
                    data.setSultype(ls.getString("sultype"));
                    data.setSultypenm(ls.getString("sultypenm"));
                    data.setDistcode(ls.getString("distcode"));
                    data.setDistcodenm(ls.getString("distcodenm"));
                }
                exampledata = new SulmunExampleData();
                exampledata.setSubj(data.getSubj());
                exampledata.setSulnum(data.getSulnum());
                exampledata.setSelnum(ls.getInt("selnum"));
                exampledata.setSelpoint(ls.getInt("selpoint"));
                exampledata.setSeltext(ls.getString("seltext"));
                data.add(exampledata);
                if (v_bef_sulnum != data.getSulnum()) {
                    hash.put(String.valueOf(data.getSulnum()), data);
                    v_bef_sulnum = data.getSulnum();
                }
            }
            data = null;
            for (int i = 0; i < p_sulnums.size(); i++) {
                data = (SulmunQuestionExampleData) hash.get((String) p_sulnums.get(i));
                if (data != null) {
                    list.add(data);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public void ComputeCount(ArrayList p_list, Vector p_answers) throws Exception {
        StringTokenizer st1 = null;
        StringTokenizer st2 = null;

        SulmunQuestionExampleData data = null;
        Vector subject = new Vector();
        Vector complex = new Vector();
        String v_answers = "";
        String v_answer = "";
        int index = 0;

        try {
            for (int i = 0; i < p_answers.size(); i++) {
                v_answers = (String) p_answers.get(i);
                st1 = new StringTokenizer(v_answers, SulmunAllBean.SPLIT_COMMA);
                index = 0;
                while (st1.hasMoreElements() && index < p_list.size()) {
                    v_answer = (String) st1.nextToken();
                    data = (SulmunQuestionExampleData) p_list.get(index);

                    if (data.getSultype().equals(SulmunAllBean.OBJECT_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } else if (data.getSultype().equals(SulmunAllBean.MULTI_QUESTION)) {
                        st2 = new StringTokenizer(v_answer, SulmunAllBean.SPLIT_COLON);
                        while (st2.hasMoreElements()) {
                            v_answer = (String) st2.nextToken();
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                        }
                    } else if (data.getSultype().equals(SulmunAllBean.SUBJECT_QUESTION)) {
                        subject.add(v_answer);
                    } else if (data.getSultype().equals(SulmunAllBean.COMPLEX_QUESTION)) {
                        st2 = new StringTokenizer(v_answer, SulmunAllBean.SPLIT_COLON);
                        v_answer = (String) st2.nextToken();
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                        while (st2.hasMoreElements()) {
                            v_answer = (String) st2.nextToken();
                            complex.add(v_answer);
                        }
                    } else if (data.getSultype().equals(SulmunAllBean.FSCALE_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } else if (data.getSultype().equals(SulmunAllBean.SSCALE_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    }
                    index++;
                }
            }
            // 응답비율을 계산한다. 리스트((설문번호1, 보기1,2,3..))
            for (int i = 0; i < p_list.size(); i++) {
                data = (SulmunQuestionExampleData) p_list.get(i);
                data.ComputeRate();
                data.setComplexAnswer(complex);
                data.setSubjectAnswer(subject);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    public Vector<String> getSulmunAnswers(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, int p_sulpapernum, String p_subjseq) throws Exception {
        ListSet ls = null;
        String sql = "";
        Vector<String> v_answers = new Vector<String>();
        // String v_comp = "";

        try {
            sql = "  select c.answers  \n";
            sql += "     from    tz_suleach    c, 	 \n";
            sql += "         tz_member     d 		  \n";
            sql += "     where c.userid  = d.userid   \n";
            sql += " and c.grcode  = " + SQLString.Format(p_grcode);
            if (!p_gyear.equals("ALL"))
                sql += " and c.year   = " + SQLString.Format(p_gyear);
            sql += "     and c.subj    = " + SQLString.Format(p_subj);
            sql += " and c.gubun ='ALL'   \n";
            if (!p_subjseq.equals("ALL"))
                sql += "     and c.subjseq = " + SQLString.Format(p_subjseq);
            sql += "     and c.sulpapernum = " + SQLString.Format(p_sulpapernum);

            //			System.out.println("SulmunAllResultBean 설문지 답변모음 :"+sql);			

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_answers.add(ls.getString("answers"));
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_answers;
    }

    public int getStudentCount(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, String p_subjseq) throws Exception {
        ListSet ls = null;
        String sql = "";
        // String v_comp = "";
        int v_count = 0;

        try {
            sql = "  select count(*) cnt ";
            sql += "    from tz_subjseq    a, ";
            sql += "         tz_student    b, ";
            sql += "         tz_member     c  ";
            sql += "   where a.subj    = b.subj  ";
            sql += "     and a.year    = b.year  ";
            sql += "     and a.subjseq = b.subjseq  ";
            sql += "     and b.userid  = c.userid   ";
            sql += "     and a.subj  = " + SQLString.Format(p_subj);
            if (!p_grcode.equals("ALL"))
                sql += " and a.grcode  = " + SQLString.Format(p_grcode);
            if (!p_gyear.equals("ALL"))
                sql += " and a.gyear   = " + SQLString.Format(p_gyear);
            if (!p_gyear.equals("ALL"))
                sql += " and a.subjseq   = " + SQLString.Format(p_subjseq);
            if (!p_grseq.equals("ALL"))
                //                sql+= " and a.grseq   = " + SQLString.Format(p_grseq);

                ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_count = ls.getInt("cnt");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_count;
    }

    /**
     * 개인별 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_action = box.getString("p_action");
        String v_subj = box.getString("p_subj");
        String v_gyear = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        String v_grcode = box.getString("p_grcode");
        int v_sulpapernum = box.getInt("p_sulpapernum");
        if (v_sulpapernum == 0)
            v_sulpapernum = box.getInt("s_sulpapernum");
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if (v_action.equals("go")) {
                sql = "select * ";
                sql += "  from tz_suleach a  ";
                sql += " where a.subj='" + v_subj + "' and a.grcode='" + v_grcode + "' and a.year='" + v_gyear + "' and a.subjseq='" + v_subjseq + "' and sulpapernum=" + v_sulpapernum + " ";

                //		System.out.println("sql-------------------------============"+sql);

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
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

    // ====================================  가입 경로 분석 메서드  ===========================================================================

    /**
     * 가입경로설문 결과 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> SelectObectResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        String v_action = box.getString("p_action");

        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = getObjectSulmunResult(connMgr, box);
            } else {
                list = new ArrayList<DataBox>();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
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
     * 가입경로설문 결과 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> getObjectSulmunResult(DBConnectionManager connMgr, RequestBox box) throws Exception {
        ArrayList<SulmunQuestionExampleData> QuestionExampleDataList = null;
        ArrayList<DataBox> list = null;
        String v_sulnums = "";
        String v_answers = "";

        String v_tem_grcode = box.getString("tem_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_subjseq = box.getStringDefault("s_subjseq", "ALL");
        String v_grseq = box.getString("s_grseq");
        int v_sulpapernum = 0;

        // 설문번호 선택조건
        String v_grcode = box.getStringDefault("s_grcode", v_tem_grcode);
        String v_gyear = box.getStringDefault("s_gyear", "ALL");

        // 응답자 선택조건
        String v_company = box.getStringDefault("p_company", "ALL");
        String v_jikwi = box.getStringDefault("p_jikwi", "ALL");
        String v_jikun = box.getStringDefault("p_jikun", "ALL");
        String v_workplc = box.getStringDefault("p_workplc", "ALL");

        // int v_studentcount = 0;

        try {

            v_sulpapernum = getPapernumSeqRegist("REGIST", v_grcode) - 1;

            // 설문지번호에 해당하는 설문번호를 vector로 받아온다. 벡터(설문번호1,3,5 ....)
            v_sulnums = getSulnumsRegist(connMgr, v_grcode, "REGIST", v_sulpapernum);
            box.put("p_sulnums", v_sulnums);

            // 설문번호에 해당하는 문제리스트를 만든다. 리스트((설문번호1, 보기1,2,3..))
            QuestionExampleDataList = getSelnumsRegist(connMgr, v_grcode, "REGIST", v_sulnums);

            // 설문지 답변모음을 읽어온다. 벡터(답변1,3,2,3..)
            v_answers = getSulmunAnswersRegist(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_sulpapernum, v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);

            // 리스트((설문번호1, 보기1,2,3..)) + 벡터(답변1,3,2,3..) 이용해서 응답자수 및 비율을 계산한다.
            ComputeCountRegist(QuestionExampleDataList, v_answers);

            // 응답자수
            //            box.put("p_replycount", String.valueOf(v_answers.size()));

            // 수강자수
            list = getStudentCountRegist(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_subjseq, v_sulnums, box);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return list;
    }

    /**
     * 가입경로설문 설문지 번호 구하기
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public String getSulnumsRegist(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql = "";
        // Vector v_sulnums = new Vector();
        String v_tokens = "";
        // StringTokenizer st = null;

        try {
            sql = "select sulnums  ";
            sql += "  from tz_sulpaper ";
            sql += " where grcode      = " + SQLString.Format(p_grcode);
            sql += "   and subj        = " + SQLString.Format(p_subj);
            sql += "   and sulpapernum = " + SQLString.Format(p_sulpapernum);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }

            //            st = new StringTokenizer(v_tokens,SulmunRegistBean.SPLIT_COMMA);
            //            while (st.hasMoreElements()) {
            //                v_sulnums.add((String)st.nextToken());
            //            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_tokens;
    }

    /**
     * 가입경로설문 설문지 번호,보기 구하기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<SulmunQuestionExampleData> getSelnumsRegist(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_sulnums) throws Exception {
        Hashtable<String, SulmunQuestionExampleData> hash = new Hashtable<String, SulmunQuestionExampleData>();
        ArrayList<SulmunQuestionExampleData> list = new ArrayList<SulmunQuestionExampleData>();

        ListSet ls = null;
        String sql = "";
        SulmunQuestionExampleData data = null;
        SulmunExampleData exampledata = null;
        int v_bef_sulnum = 0;

        //        String v_sulnums = "";
        //        for (int i=0; i < p_sulnums.size(); i++) {
        //            v_sulnums += (String)p_sulnums.get(i);
        //            if (i<p_sulnums.size()-1) {
        //                v_sulnums += ", ";
        //            }
        //        }
        //        if (v_sulnums.equals("")) v_sulnums = "-1";

        try {
            sql = "select a.subj,     a.sulnum, ";
            sql += "        a.distcode, 'ALL' distcodenm, ";
            sql += "       a.sultype,  d.codenm sultypenm, ";
            sql += "       a.sultext,  b.selnum, b.seltext, b.selpoint ";
            sql += "  from tz_sul     a, ";
            sql += "       tz_sulsel  b, ";
            sql += "       tz_code    d  ";
            sql += " where a.subj       =  b.subj   (+) ";
            sql += "   and a.sulnum     =  b.sulnum (+) ";
            sql += "   and a.sultype    = d.code ";
            sql += "   and b.grcode     = " + SQLString.Format(p_grcode);
            sql += "   and a.grcode     = " + SQLString.Format(p_grcode);
            sql += "   and a.subj       = " + SQLString.Format(p_subj); //설문문제테이블 SUBJ은: REGIST
            sql += "   and a.sulnum 	   in  (" + p_sulnums + ") ";
            sql += "   and a.distcode      = " + SQLString.Format(SulmunRegistBean.DIST_CODE);
            sql += "   and d.gubun      = " + SQLString.Format(SulmunRegistBean.SUL_TYPE);
            sql += "   and d.levels     =  1 ";
            sql += " order by a.subj, a.sulnum, b.selnum ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                if (v_bef_sulnum != ls.getInt("sulnum")) {
                    data = new SulmunQuestionExampleData();
                    data.setSubj(ls.getString("subj"));
                    data.setSulnum(ls.getInt("sulnum"));
                    data.setSultext(ls.getString("sultext"));
                    data.setSultype(ls.getString("sultype"));
                    data.setSultypenm(ls.getString("sultypenm"));
                    data.setDistcode(ls.getString("distcode"));
                    data.setDistcodenm(ls.getString("distcodenm"));
                }
                exampledata = new SulmunExampleData();
                exampledata.setSubj(data.getSubj());
                exampledata.setSulnum(data.getSulnum());
                exampledata.setSelnum(ls.getInt("selnum"));
                exampledata.setSelpoint(ls.getInt("selpoint"));
                exampledata.setSeltext(ls.getString("seltext"));
                data.add(exampledata);
                if (v_bef_sulnum != data.getSulnum()) {
                    hash.put(String.valueOf(data.getSulnum()), data);
                    v_bef_sulnum = data.getSulnum();
                }
            }
            //            data = null;
            //            for (int i=0; i < p_sulnums.size(); i++) {
            //                data = (SulmunQuestionExampleData)hash.get((String)p_sulnums.get(i));
            //                if (data != null) {
            //                    list.add(data);
            //                }
            //            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    /**
     * 가입경로설문 결과 응답자율 구하기
     * 
     * @param box receive from the form object and session
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void ComputeCountRegist(ArrayList p_list, String p_answers) throws Exception {
        StringTokenizer st1 = null;
        StringTokenizer st2 = null;

        SulmunQuestionExampleData data = null;
        Vector subject = new Vector();
        Vector complex = new Vector();
        // String v_answers = "";
        String v_answer = "";
        int index = 0;

        try {
            //            for (int i=0; i < p_answers.size(); i++) {
            //                v_answers = (String)p_answers.get(i);
            st1 = new StringTokenizer(p_answers, SulmunRegistBean.SPLIT_COMMA);
            index = 0;
            while (st1.hasMoreElements() && index < p_list.size()) {
                v_answer = (String) st1.nextToken();
                data = (SulmunQuestionExampleData) p_list.get(index);

                if (data.getSultype().equals(SulmunRegistBean.OBJECT_QUESTION)) {
                    data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                } else if (data.getSultype().equals(SulmunRegistBean.MULTI_QUESTION)) {
                    st2 = new StringTokenizer(v_answer, SulmunRegistBean.SPLIT_COLON);
                    while (st2.hasMoreElements()) {
                        v_answer = (String) st2.nextToken();
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    }
                } else if (data.getSultype().equals(SulmunRegistBean.SUBJECT_QUESTION)) {
                    subject.add(v_answer);
                } else if (data.getSultype().equals(SulmunRegistBean.COMPLEX_QUESTION)) {
                    st2 = new StringTokenizer(v_answer, SulmunRegistBean.SPLIT_COLON);
                    v_answer = (String) st2.nextToken();
                    data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    while (st2.hasMoreElements()) {
                        v_answer = (String) st2.nextToken();
                        complex.add(v_answer);
                    }
                } else if (data.getSultype().equals(SulmunRegistBean.FSCALE_QUESTION)) {
                    data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                } else if (data.getSultype().equals(SulmunRegistBean.SSCALE_QUESTION)) {
                    data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                }
                index++;
            }
            //            }
            // 응답비율을 계산한다. 리스트((설문번호1, 보기1,2,3..))
            for (int i = 0; i < p_list.size(); i++) {
                data = (SulmunQuestionExampleData) p_list.get(i);
                data.ComputeRate();
                data.setComplexAnswer(complex);
                data.setSubjectAnswer(subject);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 가입경로설문 결과 응답 답변 구하기
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public String getSulmunAnswersRegist(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, int p_sulpapernum, String p_subjseq, String p_company, String p_jikwi, String p_jikun, String p_workplc) throws Exception {
        ListSet ls = null;
        String sql = "";
        String v_answers = "";
        // String v_comp = "";

        try {
            sql = "  select c.answers 			 \n";
            sql += "     from    tz_suleach    c,  \n";
            sql += "         tz_member     d  	 \n";
            sql += "     where c.userid  = d.userid   \n";
            sql += " and c.grcode  = " + SQLString.Format(p_grcode);
            if (!p_gyear.equals("ALL"))
                sql += " and c.year   = " + SQLString.Format(p_gyear);
            sql += "     and c.subj    = " + SQLString.Format(p_subj);
            sql += " 	and c.gubun = 'REGIST'  \n";
            if (!p_subjseq.equals("ALL"))
                sql += "     and c.subjseq = " + SQLString.Format(p_subjseq);
            sql += "     and c.sulpapernum = " + SQLString.Format(p_sulpapernum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_answers = ls.getString("answers");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_answers;
    }

    /**
     * 가입경로설문 text 구하기
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public ArrayList<DataBox> getselText(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        // int v_count = 0;
        DataBox dbox = null;
        ArrayList<DataBox> list = null;

        String v_tem_grcode = box.getString("tem_grcode");
        String v_grcode = box.getStringDefault("s_grcode", v_tem_grcode);
        String v_sulnums = box.getString("p_sulnums");

        try {
            connMgr = new DBConnectionManager();

            //sql = "  select * from tz_sulsel where subj='REGIST' and  grcode = '"+v_grcode+"' and sulnum = '"+v_sulnums+"' ";
            sql = "  select * from tz_sulsel where subj='REGIST' and  grcode = '" + v_grcode + "' and sulnum in (" + v_sulnums + ") ";

            //System.out.println(sql);

            ls = connMgr.executeQuery(sql);

            list = new ArrayList<DataBox>();
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
     * 가입경로설문 결과 학생수 구하기
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public ArrayList<DataBox> getStudentCountRegist(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, String p_subjseq, String p_sulnums, RequestBox box) throws Exception {

        ListSet ls = null;
        String sql = "";
        // String v_comp = "";
        // int v_comlcnt = 0;
        // int v_ncomlcnt = 0;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        // Vector v_student = new Vector();
        // String v_suryo = "";
        // String v_nosuryo = "";

        //		String v_answers = (String)p_sulnums.get(0);

        try {
            sql = " select  a.subj, a.subjseq, v.subjnm,  v.grcode , e.answers, g.seltext,     \n";
            sql += "		count(s.isgraduated) comlcnt,  \n";
            sql += "		count(m.isgraduated) ncomlcnt ";
            sql += " from tz_student a	 \n";
            sql += " join vz_scsubjseq v  on a.subj = v.subj and a.subjseq = v.subjseq	 \n";
            sql += " left outer join tz_stold b  on a.subj = b.subj and a.subjseq = b.subjseq	 \n";
            sql += " left outer join tz_suleach    e on a.subj = e.subj and a.year = e.year and a.userid = e.userid	 \n";
            sql += " join (select * from tz_sulsel where subj='REGIST' and  grcode = '" + p_grcode + "' and sulnum in (" + p_sulnums + ")) g  \n";
            sql += " 	on g.subj = e.gubun and v.grcode = g.grcode and g.sulnum =e.sulnums and e.answers = g.selnum	 \n";
            sql += " join  tz_member c on a.userid = c.userid	 \n";
            sql += " left outer join (select isgraduated , subj, subjseq, userid from tz_student  where isgraduated ='Y') s  \n";
            sql += " 	on s.subj = a.subj and s.subjseq = a.subjseq and s.userid = a.userid   \n";
            sql += " left outer join (select isgraduated , subj, subjseq, userid from tz_student  where isgraduated ='N') m	 \n";
            sql += "		on m.subj = a.subj and m.subjseq = a.subjseq and m.userid = a.userid	 \n";
            sql += " Where v.grcode = '" + p_grcode + "' and a.subj = '" + p_subj + "' and e.gubun = 'REGIST'  \n";
            sql += " group by  v.grcode, a.subj,  a.subjseq , v.subjnm  , e.answers , g.seltext 	 \n";
            sql += " order by e.answers asc	 \n";

            //				System.out.println(" 설문 결과  "+sql);

            ls = connMgr.executeQuery(sql);

            list = new ArrayList<DataBox>();
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    /*
     * select a.subj, a.subjseq, v.subjnm, v.grcode , e.answers, g.seltext,
     * count(s.isgraduated) comlcnt, count(m.isgraduated) ncomlcnt
     * 
     * from tz_student a
     * 
     * join vz_scsubjseq v on a.subj = v.subj and a.subjseq = v.subjseq
     * 
     * left outer join tz_stold b on a.subj = b.subj and a.subjseq = b.subjseq
     * 
     * left outer join tz_suleach e on a.subj = e.subj and a.year = e.year and
     * a.userid = e.userid
     * 
     * join (select * from tz_sulsel where subj='REGIST' and grcode = 'N000001'
     * and sulnum = '1') g on g.subj = e.gubun and v.grcode = g.grcode and
     * g.sulnum =e.sulnums and e.answers = g.selnum
     * 
     * join tz_member c on a.userid = c.userid
     * 
     * left outer join (select isgraduated , subj, subjseq, userid from
     * tz_student where isgraduated ='Y') s on s.subj = a.subj and s.subjseq =
     * a.subjseq and s.userid = a.userid
     * 
     * left outer join (select isgraduated , subj, subjseq, userid from
     * tz_student where isgraduated ='N') m on m.subj = a.subj and m.subjseq =
     * a.subjseq and m.userid = a.userid
     * 
     * Where v.grcode = 'N000001' and a.subj = 'cbu001' and e.gubun = 'REGIST'
     * group by v.grcode, a.subj, a.subjseq , v.subjnm , e.answers , g.seltext
     * order by e.answers desc
     */

    /**
     * 일련번호 구하기
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int getPapernumSeqRegist(String p_subj, String p_grcode) throws Exception {
        Hashtable<String, String> maxdata = new Hashtable<String, String>();
        maxdata.put("seqcolumn", "sulpapernum");
        maxdata.put("seqtable", "tz_sulpaper");
        maxdata.put("paramcnt", "2");
        maxdata.put("param0", "subj");
        maxdata.put("param1", "grcode");
        maxdata.put("subj", SQLString.Format(p_subj));
        maxdata.put("grcode", SQLString.Format(p_grcode));

        return SelectionUtil.getSeq(maxdata);
    }

    // ===============================================================================================================================

}
