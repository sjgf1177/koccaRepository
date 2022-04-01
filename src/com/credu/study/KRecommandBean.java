//**********************************************************
//1. ��      ��: ������õ���� 
//2. ���α׷���: KRecommandBean.java
//3. ��      ��: ������õ���� bean
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: lyh
//**********************************************************
package com.credu.study;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

public class KRecommandBean {

    public KRecommandBean() {
    }

    /**
     * ���¼����� ����� ��õ���� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���¼����� ����� ��õ���� ����Ʈ
     */

    public ArrayList<DataBox> selectProposeRecomList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String sql = "";

        String v_grcode = box.getSession("tem_grcode");
        String v_subj = box.getString("p_subj");

        try {
            list1 = new ArrayList<DataBox>();

            sql = " select a.subj, a.subjnm, a.muserid, get_name(a.muserid)  mname,            ";
            sql += "        a.introducefilenamereal, a.introducefilenamenew,   a.sphere, b.cnt            ";
            sql += "   from TZ_SUBJ a ,                                                                   ";
            sql += "        (select subj,  count(*) cnt  from tz_stold                                    ";
            sql += "          where userid in (select userid from tz_stold where subj = '" + v_subj + "'  ";
            sql += "          		 union all select userid from tz_stoldhst where subj = '" + v_subj + "')  ";
            sql += "            and subj in (select subjcourse from tz_grsubj where grcode = '" + v_grcode + "')";
            sql += "          group by subj ) b                                                           ";
            sql += "  where a.subj = b.subj                                                               ";
            sql += "    and a.subj <> " + SQLString.Format(v_subj);
            sql += "    and a.isuse = 'Y'                                                                 ";
            sql += "    and a.isvisible = 'Y'   and rownum<4                                              ";
            sql += "  order by b.cnt desc                                                                 ";
            System.out.println("sql_ProposeRecom=" + sql);
            connMgr = new DBConnectionManager();
            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
        return list1;
    }

    /**
     * �н��ڼ���м� - ���ɺ�
     * 
     * @param box receive from the form object and session
     * @return ArrayList �н��ڼ���м� - ���ɺ�
     */

    public ArrayList<DataBox> selectAgeRecomList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String sql = "";

        String v_subj = box.getString("p_subj");

        try {
            list1 = new ArrayList<DataBox>();

            sql = " select a.age, count(a.age) cnt                                              ";
            sql += "   from                                                                      ";
            sql += "      ( select  a.userid,                                                    ";
            sql += "               ((TO_CHAR(SYSDATE, 'YYYY') -                             ";
            sql += "               CASE SUBSTRING(b.resno,7,1)  WHEN 1 THEN LEFT(b.resno,2)+1900 ";
            sql += "                                            WHEN 2 THEN LEFT(b.resno,2)+1900 ";
            sql += "                                            WHEN 3 THEN LEFT(b.resno,2)+2000 ";
            sql += "                                            WHEN 4 THEN LEFT(b.resno,2)+2000 ";
            sql += "                                            END ) / 10 ) * 10 AS AGE         ";
            sql += "          from tz_stold a, tz_member b                                     ";
            sql += "         where a.userid = b.userid                                           ";
            sql += "           and a.subj = " + SQLString.Format(v_subj);

            //
            sql += "      union all select  a.userid,                                                    ";
            sql += "               ((TO_CHAR(SYSDATE, 'YYYY') -                             ";
            sql += "               CASE SUBSTRING(b.resno,7,1)  WHEN 1 THEN LEFT(b.resno,2)+1900 ";
            sql += "                                            WHEN 2 THEN LEFT(b.resno,2)+1900 ";
            sql += "                                            WHEN 3 THEN LEFT(b.resno,2)+2000 ";
            sql += "                                            WHEN 4 THEN LEFT(b.resno,2)+2000 ";
            sql += "                                            END ) / 10 ) * 10 AS AGE         ";
            sql += "          from tz_stoldhst a, tz_member b                                     ";
            sql += "         where a.userid = b.userid                                           ";
            sql += "           and a.subj = " + SQLString.Format(v_subj);

            sql += "      ) a                                                                    ";
            sql += "  group by a.age                                                             ";
            sql += "  order by a.age desc                                                        ";

            //System.out.println("sql_AgeRecom="+sql);
            connMgr = new DBConnectionManager();
            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
        return list1;
    }

    /**
     * �н��ڼ���м� - ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList �н��ڼ���м� - ����
     */

    public ArrayList<DataBox> selectSexRecomList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String sql = "";

        String v_subj = box.getString("p_subj");

        try {
            list1 = new ArrayList<DataBox>();

            sql = " select a.sex, count(a.sex) cnt                            ";
            sql += "   from                                                    ";
            sql += "      ( select  a.userid,                                  ";
            sql += "               CASE SUBSTRING(b.resno,7,1)  WHEN 1 THEN 1  ";
            sql += "                                            WHEN 2 THEN 2  ";
            sql += "                                            WHEN 3 THEN 1  ";
            sql += "                                            WHEN 4 THEN 2  ";
            sql += "                                            END  AS SEX    ";
            sql += "          from tz_stold a, tz_member b                   ";
            sql += "         where a.userid = b.userid                         ";
            sql += "           and a.subj = " + SQLString.Format(v_subj);

            //
            sql += "      union all select  a.userid,                                  ";
            sql += "               CASE SUBSTRING(b.resno,7,1)  WHEN 1 THEN 1  ";
            sql += "                                            WHEN 2 THEN 2  ";
            sql += "                                            WHEN 3 THEN 1  ";
            sql += "                                            WHEN 4 THEN 2  ";
            sql += "                                            END  AS SEX    ";
            sql += "          from tz_stoldhst a, tz_member b                   ";
            sql += "         where a.userid = b.userid                         ";
            sql += "           and a.subj = " + SQLString.Format(v_subj);

            sql += "      ) a                                                  ";
            sql += "  group by a.sex                                           ";
            sql += "  order by a.sex desc                                      ";

            //System.out.println("sql_AgeRecom="+sql);
            connMgr = new DBConnectionManager();
            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
        return list1;
    }

    /**
     * �н��ڼ���м� - ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList �н��ڼ���м� - ����
     */

    public ArrayList<DataBox> selectJikupRecomList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String sql = "";

        String v_subj = box.getString("p_subj");

        try {
            list1 = new ArrayList<DataBox>();

            sql = " select a.jikup, count(a.jikup) cnt                ";
            sql += "   from                                            ";
            sql += "      ( select a.userid, b.jikup                   ";
            sql += "          from tz_stold a, tz_member b           ";
            sql += "         where a.userid = b.userid                 ";
            sql += "           and a.subj = " + SQLString.Format(v_subj);

            //
            sql += "      union all select a.userid, b.jikup                   ";
            sql += "          from tz_stoldhst a, tz_member b           ";
            sql += "         where a.userid = b.userid                 ";
            sql += "           and a.subj = " + SQLString.Format(v_subj);

            sql += "      ) a                                          ";
            sql += "  group by a.jikup                                 ";
            sql += "  order by a.jikup                                 ";

            //System.out.println("sql_AgeRecom="+sql);
            connMgr = new DBConnectionManager();
            ls1 = connMgr.executeQuery(sql);
            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
        return list1;
    }

    /**
     * ������õ���� �������� ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<DataBox> selectRecommandList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        //	String v_grcode = box.getSession("tem_grcode");
        String v_user_id = box.getSession("userid");

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //-------------------------------------------
            // �α��� ��������
            sql += " select z.userid, z.name, z.jikup, z.degree, z.sexdis, z.jikupnm, z.degreenm,  z.age , \n"; //���������ϱ����� �ѹ��� ��
            sql += "    case LEFT(age,1)  when 1 then '10��'   when 2 then '20��' 	when 3 then '30��'  \n";
            sql += " 	when 4 then '40��'   when 5 then '50��'   \n";
            sql += " 	 else '��Ÿ����'  end  as agegubun   \n";
            sql += " from (   \n";
            sql += " select userid, isnull(name,'-') name, isnull(jikup,'-') jikup,   isnull(degree,'-') degree , \n";
            sql += " case SUBSTRING(resno, 7, 1) ";
            sql += " when  '1'  then  '����' when '3' then '����'  else '���� ' end  as sexdis , \n";
            sql += " (select  codenm from tz_code where  code = a.jikup and gubun='0070' ) as  jikupnm  , \n";
            sql += " (select  codenm from tz_code where  code = a.degree and gubun='0069' ) as  degreenm,  \n";
            sql += " TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(resno,7,1)   \n";
            sql += " WHEN 1 THEN LEFT(resno,2)+1900  \n";
            sql += " WHEN 2 THEN LEFT(resno,2)+1900  \n";
            sql += " WHEN 3 THEN LEFT(resno,2)+2000 \n";
            sql += " WHEN 4 THEN LEFT(resno,2)+2000 \n";
            sql += " END AS AGE  \n";
            sql += " from tz_member  a  \n";
            sql += " where userid ='" + v_user_id + "' 			\n";
            sql += " and registgubun = 'KOCCA'  )  z   		\n";

            //System.out.println("KRecommandBean ���㰭�� selectRecommandList: "+sql);	  
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {

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
     * ������õ���� �˻������� select��� �������� - ����,�з�
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<ArrayList<DataBox>> selectRecommandSelectbox(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        ArrayList<DataBox> list2 = null;
        ArrayList<ArrayList<DataBox>> totalList = null;
        String jikupSql = "";
        String degreeSql = "";
        DataBox dbox = null;

        //	String v_grcode = box.getSession("tem_grcode");
        //  String  v_user_id   = box.getSession("userid");

        try {

            connMgr = new DBConnectionManager();
            totalList = new ArrayList<ArrayList<DataBox>>();
            list = new ArrayList<DataBox>();
            list2 = new ArrayList<DataBox>();

            // ���� selectbox - all
            jikupSql += " select  codenm||';'||code as jikupnm from tz_code where  gubun='0070' ";

            //System.out.println("���㰭�� ����selectbox:"+jikupSql);
            ls = connMgr.executeQuery(jikupSql);
            while (ls.next()) {

                dbox = ls.getDataBox();
                list.add(dbox);
            }
            System.out.println("list.size():" + list.size());
            totalList.add(list);

            ls = null;
            dbox = null;

            //  �з� selectbox  - all 
            degreeSql += " select  codenm||';'||code as degreenm  from tz_code where  gubun='0069' ";

            //System.out.println("���㰭�� �з� selectbox: "+degreeSql);	  
            ls = connMgr.executeQuery(degreeSql);

            while (ls.next()) {

                dbox = ls.getDataBox();
                list2.add(dbox);
            }
            totalList.add(list2);
            System.out.println("list2.size():" + list2.size());
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, jikupSql);
            throw new Exception("sql = " + jikupSql + "\r\n" + ex.getMessage());
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
        return totalList;
    }

    /**
     * ���� ������õ���� 3�� ��������
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectRecommandSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String sql2 = "";
        String cntSql = "";
        String searchSql = "";
        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        String v_user_id = box.getSession("userid");

        String v_agegubun = ""; //����
        String v_degree = ""; //�з�
        String v_jikup = ""; //����
        String v_sexdis = ""; //����

        int v_ageCnt = 0;
        int v_degreeCnt = 0;
        int v_jikupCnt = 0;
        //	int v_sexdisCnt = 0;	

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //----------------------------------------------------------------------
            // �α��� ��������
            sql += " select isnull(jikup,'-') jikup,   isnull(degree,'-') degree , \n";
            sql += " case SUBSTRING(resno, 7, 1) ";
            sql += " when  '1'  then  'M' when '3' then 'M'  else 'F ' end  as sexdis , \n";
            sql += "  case LEFT( \n";
            sql += "TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(resno,7,1) \n";
            sql += "WHEN 1 THEN LEFT(resno,2)+1900   \n";
            sql += "WHEN 2 THEN LEFT(resno,2)+1900   \n";
            sql += "WHEN 3 THEN LEFT(resno,2)+2000  \n";
            sql += "WHEN 4 THEN LEFT(resno,2)+2000  \n";
            sql += "END   \n";
            sql += ",1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n";
            sql += "when 4 then '40'   when 5 then '50'  \n";
            sql += " else '��Ÿ����'  end  as agegubun   \n";
            sql += " from tz_member  a  \n";
            sql += " where userid ='" + v_user_id + "' 			\n";

            //System.out.println("KRecommandBean ���㰭�� selectRecommandList: "+sql);	  
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                // ��õ���� ��ȸ�� ���� �˻����� ���� �� �� ����
                v_agegubun = ls.getString("agegubun");
                v_degree = ls.getString("degree");
                v_jikup = ls.getString("jikup");
                v_sexdis = ls.getString("sexdis");
            }

            ls = null;

            //-------------------------------------------------------------
            // 4��(����,�з�,����,����)�� ���ǿ� �ش��ϴ� count�� �ؼ� ������ �ִ�
            // ������ �������� ���¸� �����´�.

            cntSql = " select  \n";
            cntSql += "(  \n";
            cntSql += "-- ���� \n";
            cntSql += "	select   count(z.subj) \n";
            cntSql += "	from (   \n";
            cntSql += "	select  \n";
            cntSql += "		b.subj,  \n";
            cntSql += "		case LEFT( \n";
            cntSql += "			 TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(resno,7,1)   \n";
            cntSql += "		 	WHEN 1 THEN LEFT(resno,2)+1900   \n";
            cntSql += " 		WHEN 2 THEN LEFT(resno,2)+1900     \n";
            cntSql += " 		WHEN 3 THEN LEFT(resno,2)+2000  \n";
            cntSql += " 		WHEN 4 THEN LEFT(resno,2)+2000  \n";
            cntSql += " 		END   \n";
            cntSql += " 	 ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n";
            cntSql += " 	 when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   \n";
            cntSql += " 	from tz_member  a  , tz_stold  b  \n";
            cntSql += " 	where  a.userid = b.userid  \n";
            cntSql += " 	and  a.userid != '" + v_user_id + "'   \n";

            //
            cntSql += "	union all select  \n";
            cntSql += "		b.subj,  \n";
            cntSql += "		case LEFT( \n";
            cntSql += "			 TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(resno,7,1)   \n";
            cntSql += "		 	WHEN 1 THEN LEFT(resno,2)+1900   \n";
            cntSql += " 		WHEN 2 THEN LEFT(resno,2)+1900     \n";
            cntSql += " 		WHEN 3 THEN LEFT(resno,2)+2000  \n";
            cntSql += " 		WHEN 4 THEN LEFT(resno,2)+2000  \n";
            cntSql += " 		END   \n";
            cntSql += " 	 ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n";
            cntSql += " 	 when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   \n";
            cntSql += " 	from tz_member  a  , tz_stoldhst  b  \n";
            cntSql += " 	where  a.userid = b.userid  \n";
            cntSql += " 	and  a.userid != '" + v_user_id + "'   \n";

            cntSql += " 	 )  z   \n";
            cntSql += " 	where  z.agegubun  ='" + v_agegubun + "' \n";
            cntSql += " )  agecnt, \n";
            cntSql += " ( \n";
            cntSql += " 	--���� \n";

            cntSql += " 	select   ( \n";
            cntSql += " 	select   count(b.subj) \n";
            cntSql += " 	from tz_member  a  , tz_stold  b , tz_code  c \n";
            cntSql += " 	where  a.userid = b.userid  \n";
            cntSql += " 	and  a.jikup = c.code  \n";
            cntSql += " 	and  c.gubun = '0070'  \n";
            cntSql += " 	and  a.jikup = '" + v_jikup + "'  \n";
            cntSql += " 	and  a.userid != '" + v_user_id + "'   \n";
            cntSql += " 	) + ( \n";

            cntSql += " 	select   count(b.subj) \n";
            cntSql += " 	from tz_member  a  , tz_stoldhst  b , tz_code  c \n";
            cntSql += " 	where  a.userid = b.userid  \n";
            cntSql += " 	and  a.jikup = c.code  \n";
            cntSql += " 	and  c.gubun = '0070'  \n";
            cntSql += " 	and  a.jikup = '" + v_jikup + "'  \n";
            cntSql += " 	and  a.userid != '" + v_user_id + "'   \n";
            cntSql += " 	) \n";

            cntSql += " ) jikupcnt, \n";
            cntSql += " ( \n";
            cntSql += " 	--�з� \n";

            cntSql += " 	select   ( \n";
            cntSql += " 	select   count(b.subj) \n";
            cntSql += " 	from tz_member  a  , tz_stold  b , tz_code  c \n";
            cntSql += " 	where  a.userid = b.userid  \n";
            cntSql += " 	and  a.degree = c.code  \n";
            cntSql += " 	and  c.gubun = '0069'  \n";
            cntSql += "		and  a.degree = '" + v_degree + "'  \n";
            cntSql += " 	and  a.userid != '" + v_user_id + "'   \n";
            cntSql += " 	) + ( \n";

            cntSql += " 	select   count(b.subj) \n";
            cntSql += " 	from tz_member  a  , tz_stoldhst  b , tz_code  c \n";
            cntSql += " 	where  a.userid = b.userid  \n";
            cntSql += " 	and  a.degree = c.code  \n";
            cntSql += " 	and  c.gubun = '0069'  \n";
            cntSql += "		and  a.degree = '" + v_degree + "'  \n";
            cntSql += " 	and  a.userid != '" + v_user_id + "'   \n";
            cntSql += " 	) \n";

            cntSql += ") degreecnt, \n";
            cntSql += "( \n";
            cntSql += "	--���� \n";
            cntSql += "	select   count(z.subj) \n";
            cntSql += "	 from (    \n";
            cntSql += "		select   b.subj, \n";
            cntSql += "		 case  SUBSTRING(resno, 7, 1)  when   '1'  then  '����' when '3' then '����' else '���� ' end  as sexdis \n";
            cntSql += "		from tz_member  a  , tz_stold  b   \n";
            cntSql += "		where  a.userid = b.userid \n";
            cntSql += " 	and  a.userid != '" + v_user_id + "'   \n";

            cntSql += "		union all select   b.subj, \n";
            cntSql += "		 case  SUBSTRING(resno, 7, 1)  when   '1'  then  '����' when '3' then '����' else '���� ' end  as sexdis \n";
            cntSql += "		from tz_member  a  , tz_stoldhst  b   \n";
            cntSql += "		where  a.userid = b.userid \n";
            cntSql += " 	and  a.userid != '" + v_user_id + "'   \n";

            cntSql += "	) z \n";
            cntSql += "	where z.sexdis = '" + v_sexdis + "' \n";
            cntSql += ") sexdiscnt  		 \n";

            System.out.println("KRecommandBean ���� count selectRecommandList: " + cntSql);
            ls = connMgr.executeQuery(cntSql);

            if (ls.next()) {

                v_ageCnt = ls.getInt("agecnt"); // ������ �˻��� ��� ��
                v_degreeCnt = ls.getInt("degreecnt"); // �з����� �˻��� ��� ��	
                v_jikupCnt = ls.getInt("jikupcnt"); // �������� �˻��� ��� ��
                //			v_sexdisCnt = ls.getInt("sexdiscnt");		// ������ �˻��� ��� ��

            }

            //System.out.println("v_ageCnt:"+v_ageCnt+"/"+"v_degreeCnt:"+v_degreeCnt+"/v_jikupCnt:"+v_jikupCnt+"/v_sexdisCnt:"+v_sexdisCnt);		

            // 0 �� �ƴѰ͸� ���������Ͽ� �˻��Ѵ�
            ls = null;

            // ���ǿ� �´� �˻��� ���� 3���� �����´�.
            searchSql = " select distinct top 3  z.subj, z.subjnm, z.sphere  ,z.muserid, z.name , z.newphoto  \n";
            searchSql += " from (  \n";
            searchSql += " select  \n";
            searchSql += " 	b.subj,  c.subjnm, c.sphere, c.muserid, get_name(c.muserid) name, a.jikup , a.degree, c.introducefilenamenew newphoto, \n";
            searchSql += " 	case LEFT(  \n";
            searchSql += " 		 TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(a.resno,7,1) \n";
            searchSql += " 	 	WHEN 1 THEN LEFT(a.resno,2)+1900    \n";
            searchSql += " 	WHEN 2 THEN LEFT(a.resno,2)+1900      \n";
            searchSql += " 	WHEN 3 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += " 	WHEN 4 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += " 	END    \n";
            searchSql += "  ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n";
            searchSql += "  when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   , \n";
            searchSql += " case  SUBSTRING(a.resno, 7, 1)  when   '1'  then  'M' when '3' then 'M' else 'F' end  as sexdis \n";
            searchSql += " from tz_member a    , tz_stold  b  , tz_subj  c,  tz_grsubj e  \n";
            searchSql += " where  a.userid = b.userid   \n";
            searchSql += " and b.subj = c.subj   \n";
            searchSql += " and c.subj = e.subjcourse   \n";
            searchSql += " and e.grcode = " + SQLString.Format(v_grcode);

            //
            searchSql += " union all select  \n";
            searchSql += " 	b.subj,  c.subjnm, c.sphere, c.muserid, get_name(c.muserid) name, a.jikup , a.degree, c.introducefilenamenew newphoto, \n";
            searchSql += " 	case LEFT(  \n";
            searchSql += " 		 TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(a.resno,7,1) \n";
            searchSql += " 	 	WHEN 1 THEN LEFT(a.resno,2)+1900    \n";
            searchSql += " 	WHEN 2 THEN LEFT(a.resno,2)+1900      \n";
            searchSql += " 	WHEN 3 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += " 	WHEN 4 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += " 	END    \n";
            searchSql += "  ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n";
            searchSql += "  when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   , \n";
            searchSql += " case  SUBSTRING(a.resno, 7, 1)  when   '1'  then  'M' when '3' then 'M' else 'F' end  as sexdis \n";
            searchSql += " from tz_member a    , tz_stoldhst  b  , tz_subj  c,  tz_grsubj e  \n";
            searchSql += " where  a.userid = b.userid   \n";
            searchSql += " and b.subj = c.subj   \n";
            searchSql += " and c.subj = e.subjcourse   \n";
            searchSql += " and e.grcode = " + SQLString.Format(v_grcode);
            searchSql += "  )  z  \n";
            searchSql += "  where 1=1  \n";

            if (v_ageCnt > 0) {
                searchSql += " and z.agegubun = '" + v_agegubun + "'  \n";
            } else if (v_degreeCnt > 0) {
                searchSql += " and z.degree  = '" + v_degree + "'  \n";
            } else if (v_jikupCnt > 0) {
                searchSql += " and z.jikup  = '" + v_jikup + "'  \n";
            } else {
                searchSql += " and z.sexdis  = '" + v_sexdis + "'  \n";
            }

            System.out.println("searchSql:" + searchSql);
            ls = connMgr.executeQuery(searchSql);

            if (ls != null) { // ������ ���������� �ִٸ� 

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }

            } else { // ������ ��õ�������� �����������Ѵ�

                sql2 += " select b.subj, b.subjnm ,b.sphere,  b.muserid , (select name from tz_member where b.muserid = userid ) name, \n";
                sql2 += " (select newphoto from tz_tutor  where  b.muserid = userid )  newphoto  \n";
                sql2 += " from tz_grrecom  a, tz_subj b  \n";
                sql2 += " where  a.subjcourse = b.subj  \n";
                sql2 += " and  grcode = '" + v_grcode + "'   and rownum<4 \n";
                sql2 += " searorder by ldate desc \n";

                System.out.println("�ش����ǳ������ ��õ�������� ������:" + sql2);
                ls = connMgr.executeQuery(sql2);

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
     * �˻���õ���� ��������
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectRecommandSearch(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String searchSql = "";
        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        //	String  v_user_id   = box.getSession("userid");

        String v_select1 = box.getStringDefault("ind", "age");
        String v_select2 = box.getStringDefault("job", "20");
        box.put("ind", v_select1);
        box.put("job", v_select2);

        //	String v_agegubun = "";				//����
        //	String v_degree = "";			//�з�
        //	String v_jikup = "";			//����
        //	String v_sexdis = "";			//����

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            // �˻��� ���� 3���� �����´�.
            searchSql = " select distinct top 3  z.subj, z.subjnm, z.sphere ,z.muserid, z.name , z.newphoto  \n";
            searchSql += " from (  \n";
            searchSql += " select  \n";
            searchSql += " 	b.subj,  c.subjnm, c.sphere, c.muserid, get_name(c.muserid) name, a.jikup , a.degree, c.introducefilenamenew newphoto, \n";
            searchSql += " 	case LEFT(  \n";
            searchSql += " 		 TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(a.resno,7,1) \n";
            searchSql += " 	     WHEN 1 THEN LEFT(a.resno,2)+1900    \n";
            searchSql += " 	     WHEN 2 THEN LEFT(a.resno,2)+1900      \n";
            searchSql += " 	     WHEN 3 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += "        WHEN 4 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += " 	END    \n";
            searchSql += "  ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n";
            searchSql += "  when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   , \n";
            searchSql += " case  SUBSTRING(a.resno, 7, 1)  when   '1'  then  'M' when '3' then 'M' else 'F' end  as sexdis \n";
            searchSql += " from tz_member a    , tz_stold  b  , tz_subj  c , tz_grsubj e  \n";
            searchSql += " where  a.userid = b.userid   \n";
            searchSql += " and b.subj = c.subj   \n";
            searchSql += " and c.subj = e.subjcourse  \n";
            searchSql += " and e.grcode = " + SQLString.Format(v_grcode);

            //
            searchSql += " union all select  \n";
            searchSql += " 	b.subj,  c.subjnm, c.sphere, c.muserid, get_name(c.muserid) name, a.jikup , a.degree, c.introducefilenamenew newphoto, \n";
            searchSql += " 	case LEFT(  \n";
            searchSql += " 		 TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(a.resno,7,1) \n";
            searchSql += " 	     WHEN 1 THEN LEFT(a.resno,2)+1900    \n";
            searchSql += " 	     WHEN 2 THEN LEFT(a.resno,2)+1900      \n";
            searchSql += " 	     WHEN 3 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += "        WHEN 4 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += " 	END    \n";
            searchSql += "  ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n";
            searchSql += "  when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   , \n";
            searchSql += " case  SUBSTRING(a.resno, 7, 1)  when   '1'  then  'M' when '3' then 'M' else 'F' end  as sexdis \n";
            searchSql += " from tz_member a    , tz_stoldhst  b  , tz_subj  c , tz_grsubj e  \n";
            searchSql += " where  a.userid = b.userid   \n";
            searchSql += " and b.subj = c.subj   \n";
            searchSql += " and c.subj = e.subjcourse  \n";
            searchSql += " and e.grcode = " + SQLString.Format(v_grcode);
            searchSql += "  )  z  \n";

            if (v_select1.equals("age")) {
                searchSql += " where z.agegubun = '" + v_select2 + "'  \n";
            } else if (v_select1.equals("degree")) {
                searchSql += " where z.degree  = '" + v_select2 + "'  \n";
            } else if (v_select1.equals("jikup")) {
                searchSql += " where z.jikup  = '" + v_select2 + "'  \n";
            } else {
                searchSql += " where z.sexdis  = '" + v_select2 + "'  \n";
            }
            //System.out.println("KRecommandBean �˻���õ���� ��������:"+ searchSql);

            ls = connMgr.executeQuery(searchSql);
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
     * ���� - ���ǿ��ɴ� �αⰭ�� 1�� �������� - �α� ��/�α� �� ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox selectMainRecommandSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String sql2 = "";
        String searchSql = "";
        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        String v_user_id = box.getSession("userid");

        String v_agegubun = ""; //����

        try {

            connMgr = new DBConnectionManager();

            //----------------------------------------------------------------------
            // �α��� ������ ���ɴ븦 ������
            sql += " select  \n";
            sql += " case LEFT( \n";
            sql += "TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(resno,7,1) \n";
            sql += "WHEN 1 THEN LEFT(resno,2)+1900   \n";
            sql += "WHEN 2 THEN LEFT(resno,2)+1900   \n";
            sql += "WHEN 3 THEN LEFT(resno,2)+2000  \n";
            sql += "WHEN 4 THEN LEFT(resno,2)+2000  \n";
            sql += "END   \n";
            sql += ",1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n";
            sql += "when 4 then '40'   when 5 then '50'  \n";
            sql += " else '��Ÿ����'  end  as agegubun   \n";
            sql += " from tz_member  a  \n";
            sql += " where userid ='" + v_user_id + "' 			\n";

            System.out.println("KRecommandBean ���㰭�� selectRecommandList: " + sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) { // ��õ���� ��ȸ�� ���� �˻����� ���� �� �� ����

                v_agegubun = ls.getString("agegubun");
            } else { // �α� �� �Ǵ� ����userid�� ���� �˻����� ������ 20������  
                v_agegubun = "20";
            }

            ls.close();

            //-------------------------------------------------------------
            // ���ο�- �������ɴ뿡 ���ǿ� �´� �˻��� ���� 1���� �����´�.
            searchSql = " SELECT * FROM (SELECT X.*, ROWNUM RNUM FROM ( select distinct z.subj, z.subjnm, z.intro, z.sphere  ,z.muserid, z.name , z.newphoto,  agegubun||'��' agegubun  \n";
            searchSql += " from (  \n";
            searchSql += " select  substring(isnull(c.intro,''),1,20) intro ,\n";
            searchSql += " 	b.subj,  c.subjnm, c.sphere, c.muserid, get_name(c.muserid) name, a.jikup , a.degree, c.introducefilenamenew newphoto, \n";
            searchSql += " 	case LEFT(  \n";
            searchSql += " 	TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(a.resno,7,1) \n";
            searchSql += " 	WHEN 1 THEN LEFT(a.resno,2)+1900    \n";
            searchSql += " 	WHEN 2 THEN LEFT(a.resno,2)+1900      \n";
            searchSql += " 	WHEN 3 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += " 	WHEN 4 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += " 	END    \n";
            searchSql += "  ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n";
            searchSql += "  when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   \n";
            searchSql += " from tz_member a    , tz_stold  b  , tz_subj  c,  tz_grsubj e  \n";
            searchSql += " where  a.userid = b.userid   \n";
            searchSql += " and b.subj = c.subj   \n";
            searchSql += " and c.subj = e.subjcourse   \n";
            searchSql += " and e.grcode = " + SQLString.Format(v_grcode);

            //
            searchSql += " union all select  substring(isnull(c.intro,''),1,20) intro ,\n";
            searchSql += " 	b.subj,  c.subjnm, c.sphere, c.muserid, get_name(c.muserid) name, a.jikup , a.degree, c.introducefilenamenew newphoto, \n";
            searchSql += " 	case LEFT(  \n";
            searchSql += " 	TO_CHAR(SYSDATE, 'YYYY') - CASE SUBSTRING(a.resno,7,1) \n";
            searchSql += " 	WHEN 1 THEN LEFT(a.resno,2)+1900    \n";
            searchSql += " 	WHEN 2 THEN LEFT(a.resno,2)+1900      \n";
            searchSql += " 	WHEN 3 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += " 	WHEN 4 THEN LEFT(a.resno,2)+2000   \n";
            searchSql += " 	END    \n";
            searchSql += "  ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n";
            searchSql += "  when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   \n";
            searchSql += " from tz_member a    , tz_stoldhst  b  , tz_subj  c,  tz_grsubj e  \n";
            searchSql += " where  a.userid = b.userid   \n";
            searchSql += " and b.subj = c.subj   \n";
            searchSql += " and c.subj = e.subjcourse   \n";
            searchSql += " and e.grcode = " + SQLString.Format(v_grcode);

            searchSql += "  )  z  \n";
            searchSql += "  where 1=1  \n";
            searchSql += " and z.agegubun = " + SQLString.Format(v_agegubun);
            searchSql += " ) X ) WHERE RNUM < 2";

            //System.out.println("searchSql:"+searchSql);
            ls = connMgr.executeQuery(searchSql);

            //if(ls != null){				// ������ ���������� �ִٸ� 

            if (ls.next()) {
                dbox = ls.getDataBox();

            } else { // ������ ��õ�������� �����������Ѵ�

                sql2 += " select b.subj, b.subjnm , substring(isnull(b.intro,''),1,20) intro , b.sphere,  b.muserid , (select name from tz_member where b.muserid = userid ) name, \n";
                sql2 += " (select newphoto from tz_tutor  where  b.muserid = userid )  newphoto  \n";
                sql2 += " from tz_grrecom  a, tz_subj b  \n";
                sql2 += " where  a.subjcourse = b.subj  \n";
                sql2 += " and  grcode = '" + v_grcode + "'  and rownum=1  \n";
                sql2 += " order by a.ldate desc \n";

                //System.out.println("�ش����ǳ������ ��õ�������� ������:"+sql2);
                ls = connMgr.executeQuery(sql2);

                if (ls.next()) {
                    dbox = ls.getDataBox();
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
        return dbox;
    }

    /**
     * ���� - ���ǿ��ɴ� �αⰭ�� 1�� �������� - �α� ��/�α� �� ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<DataBox> selectMainRecommandSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String sql2 = "";
        String searchSql = "";
        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        //	String  v_user_id   = box.getSession("userid");

        //	String v_agegubun = "";			//����

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //-------------------------------------------------------------
            // ���ο�- �������ɴ뿡 ���ǿ� �´� �˻��� ���� 1���� �����´�.
            //		searchSql = "  select distinct top 2  z.subj, z.subjnm, z.intro, z.sphere  ,z.muserid, z.name , z.newphoto,  agegubun+'��' agegubun  \n";   //��
            searchSql = "  select X.* FROM (SELECT ROWNUM RNUM,  z.subj, z.subjnm, z.intro, z.sphere  ,z.muserid, z.name , z.newphoto,  agegubun||'��' agegubun  \n"; //��
            searchSql += " from (  \n";
            searchSql += " select  substr(NVL(c.intro,''),1,20) intro ,\n";
            searchSql += " 	b.subj,  c.subjnm, c.sphere, c.muserid, get_name(c.muserid) name, a.jikup , a.degree, c.introducefilenamenew newphoto, \n";
            searchSql += " 	case LEFT(  \n";
            searchSql += " 	TO_CHAR(SYSDATE, 'YYYY')- CASE SUBSTR(a.resno,7,1) \n";
            searchSql += " 	WHEN '1' THEN SUBSTR(a.resno,0,2)+1900    \n";
            searchSql += " 	WHEN '2' THEN SUBSTR(a.resno,0,2)+1900      \n";
            searchSql += " 	WHEN '3' THEN SUBSTR(a.resno,0,2)+2000   \n";
            searchSql += " 	WHEN '4' THEN SUBSTR(a.resno,0,2)+2000   \n";
            searchSql += " 	END    \n";
            searchSql += "  ,1)  when '1' then '10'   when '2' then '20' 	when '3' then '30' \n";
            searchSql += "  when '4' then '40'   when '5' then '50'  else '��Ÿ����'  end  as agegubun   \n"; //��Ÿ����
            searchSql += " from tz_member a    , tz_stold  b  , tz_subj  c,  tz_grsubj e  \n";
            searchSql += " where  a.userid = b.userid   \n";
            searchSql += " and b.subj = c.subj   \n";
            searchSql += " and c.subj = e.subjcourse   \n";
            searchSql += " and e.grcode = " + SQLString.Format(v_grcode);
            searchSql += "\n) Z";

            //
            /*
             * searchSql
             * +=" union all select  substring(isnull(c.intro,''),1,20) intro ,\n"
             * ; searchSql+=
             * " 	b.subj,  c.subjnm, c.sphere, c.muserid, dbo.get_name(c.muserid) name, a.jikup , a.degree, c.introducefilenamenew newphoto, \n"
             * ; searchSql +=" 	case LEFT(  \n"; searchSql
             * +=" 	CONVERT(CHAR(4),GETDATE(),20) - CASE SUBSTRING(a.resno,7,1) \n"
             * ; searchSql +=" 	WHEN 1 THEN LEFT(a.resno,2)+1900    \n";
             * searchSql +=" 	WHEN 2 THEN LEFT(a.resno,2)+1900      \n";
             * searchSql +=" 	WHEN 3 THEN LEFT(a.resno,2)+2000   \n"; searchSql
             * +=" 	WHEN 4 THEN LEFT(a.resno,2)+2000   \n"; searchSql
             * +=" 	END    \n"; searchSql
             * +="  ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n"
             * ; searchSql+=
             * "  when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   \n"
             * ; searchSql+=
             * " from tz_member a    , tz_stoldhst  b  , tz_subj  c,  tz_grsubj e  \n"
             * ; searchSql +=" where  a.userid = b.userid   \n"; searchSql
             * +=" and b.subj = c.subj   \n"; searchSql
             * +=" and c.subj = e.subjcourse   \n"; searchSql
             * +=" and e.grcode = "+SQLString.Format(v_grcode);
             * 
             * searchSql +="  )  z  \n"; searchSql +="  where 1=1  \n";
             * searchSql += " and z.agegubun = '20' ) ";
             */
            //-------------------------------------------------------------
            // ���ο�- �������ɴ뿡 ���ǿ� �´� �˻��� ���� 1���� �����´�.
            /*
             * searchSql +=
             * " union all ( select distinct top 1  z.subj, z.subjnm, z.intro, z.sphere  ,z.muserid, z.name , z.newphoto,  agegubun+'��' agegubun  \n"
             * ; searchSql +=" from (  \n" ; searchSql
             * +=" select  substring(isnull(c.intro,''),1,20) intro ,\n";
             * searchSql+=
             * " 	b.subj,  c.subjnm, c.sphere, c.muserid, dbo.get_name(c.muserid) name, a.jikup , a.degree, c.introducefilenamenew newphoto, \n"
             * ; searchSql +=" 	case LEFT(  \n"; searchSql
             * +=" 	CONVERT(CHAR(4),GETDATE(),20) - CASE SUBSTRING(a.resno,7,1) \n"
             * ; searchSql +=" 	WHEN 1 THEN LEFT(a.resno,2)+1900    \n";
             * searchSql +=" 	WHEN 2 THEN LEFT(a.resno,2)+1900      \n";
             * searchSql +=" 	WHEN 3 THEN LEFT(a.resno,2)+2000   \n"; searchSql
             * +=" 	WHEN 4 THEN LEFT(a.resno,2)+2000   \n"; searchSql
             * +=" 	END    \n"; searchSql
             * +="  ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n"
             * ; searchSql+=
             * "  when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   \n"
             * ; searchSql+=
             * " from tz_member a    , tz_stold  b  , tz_subj  c,  tz_grsubj e  \n"
             * ; searchSql +=" where  a.userid = b.userid   \n"; searchSql
             * +=" and b.subj = c.subj   \n"; searchSql
             * +=" and c.subj = e.subjcourse   \n"; searchSql
             * +=" and e.grcode = "+SQLString.Format(v_grcode);
             * 
             * // searchSql
             * +=" union all select  substring(isnull(c.intro,''),1,20) intro ,\n"
             * ; searchSql+=
             * " 	b.subj,  c.subjnm, c.sphere, c.muserid, dbo.get_name(c.muserid) name, a.jikup , a.degree, c.introducefilenamenew newphoto, \n"
             * ; searchSql +=" 	case LEFT(  \n"; searchSql
             * +=" 	CONVERT(CHAR(4),GETDATE(),20) - CASE SUBSTRING(a.resno,7,1) \n"
             * ; searchSql +=" 	WHEN 1 THEN LEFT(a.resno,2)+1900    \n";
             * searchSql +=" 	WHEN 2 THEN LEFT(a.resno,2)+1900      \n";
             * searchSql +=" 	WHEN 3 THEN LEFT(a.resno,2)+2000   \n"; searchSql
             * +=" 	WHEN 4 THEN LEFT(a.resno,2)+2000   \n"; searchSql
             * +=" 	END    \n"; searchSql
             * +="  ,1)  when 1 then '10'   when 2 then '20' 	when 3 then '30' \n"
             * ; searchSql+=
             * "  when 4 then '40'   when 5 then '50'  else '��Ÿ����'  end  as agegubun   \n"
             * ; searchSql+=
             * " from tz_member a    , tz_stoldhst  b  , tz_subj  c,  tz_grsubj e  \n"
             * ; searchSql +=" where  a.userid = b.userid   \n"; searchSql
             * +=" and b.subj = c.subj   \n"; searchSql
             * +=" and c.subj = e.subjcourse   \n"; searchSql
             * +=" and e.grcode = "+SQLString.Format(v_grcode);
             */

            searchSql += "  )  X  \n";
            searchSql += "  where X.agegubun = '20'  \n";
            searchSql += " and  RNUM < 3";

            //System.out.println("searchSql:"+searchSql);
            ls = connMgr.executeQuery(searchSql);

            //if(ls != null){				// ������ ���������� �ִٸ� 
            int cnt = 0;
            //if (ls.next()){
            if (cnt == 2) {
                dbox = ls.getDataBox();
                list.add(dbox);
                System.out.println("cnt = " + cnt++);
                while (ls.next()) {
                    System.out.println("cnt = " + cnt++);
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }

            } else { // ������ ��õ�������� �����������Ѵ�

                sql2 += " SELECT * FROM (select ROWNUM RNUM, b.subj, b.subjnm , substring(isnull(b.intro,''),1,20) intro , b.sphere,  b.muserid , (select name from tz_member where b.muserid = userid ) name, \n";
                sql2 += " (select newphoto from tz_tutor  where  b.muserid = userid )  newphoto  \n";
                //sql2 +=" from tz_grrecom  a, tz_subj b  \n";
                sql2 += " from tz_grsubj  a, tz_subj b  \n";
                sql2 += " where  a.subjcourse = b.subj  \n";
                sql2 += " and  grcode = '" + v_grcode + "'   \n";
                sql2 += " and  substring(b.specials,2,1)='Y' \n";
                sql2 += " order by a.ldate desc ) WHERE RNUM < 3\n";

                //System.out.println("�ش����ǳ������ ��õ�������� ������:"+sql2);
                ls = connMgr.executeQuery(sql2);

                while (ls.next()) {
                    System.out.println("cnt = " + cnt++);
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
}
