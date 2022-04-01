//**********************************************************
//  1. 제	  목: 교육그룹OPERATION BEAN
//  2. 프로그램명: EduGroupBean.java
//  3. 개	  요:
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 0.1
//  6. 작	  성: LeeSuMin 2003. 07. 16
//  7. 수	  정:
//**********************************************************
package com.credu.course;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

@SuppressWarnings("unchecked")
public class EduGroupBean {

    public final static String GRTYPE_CODE = "0060";
    public final static String GRGUBUN_CODE = "0107";

    public EduGroupBean() {
    }

    /**
     * 
     * @param box
     * @throws Exception
     */
    public int updateCodeOrder(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuilder sb = new StringBuilder();
        
        String[] updateListArr = null;
        String[] tempArr = null;
        int resultCnt[] = null;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sb.append(" UPDATE  TZ_GRCODE       \n");
            sb.append("    SET  CODE_ORDER = ?  \n");
            sb.append("  WHERE  GRCODE = ?      \n");
            
            pstmt = connMgr.prepareStatement(sb.toString());
            
            updateListArr = box.getString("tempElem").split(",");

            for ( int i = 0; i < updateListArr.length; i++) {
                tempArr = updateListArr[i].split("_");
                
                System.out.println( " tempArr[0] : " + tempArr[0] + " || tempArr[1] : " + tempArr[1]);
                
                pstmt.setString(1, tempArr[1]);
                pstmt.setString(2, tempArr[0]);
                pstmt.addBatch();
            }
            
            resultCnt = pstmt.executeBatch();

            connMgr.commit();
            
        } catch (Exception ex) {
            System.out.println(ex);
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
        } finally {
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
        return resultCnt.length;
    }

    /**
     * 교육그룹리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 교육그룹리스트
     */
    public ArrayList SelectEduGroupList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list1 = null;

        // StringBuffer sql = new StringBuffer();
        StringBuilder sb = new StringBuilder();

        DataBox dbox = null;

        try {
            /*
             * sql.append(" SELECT A.GRCODE,A.GRCODENM,A.IDTYPE,A.MANAGER,A.REPDATE,A.DOMAIN,A.CHKFIRST, \n"
             * );sql.append(
             * "  	    A.CHKFINAL,A.ISLOGIN,A.ISJIK,A.ISONLYGATE,A.ISUSEBILL, \n"
             * );
             * sql.append(" 		A.MASTER,A.INDATE,A.LUSERID, A.LDATE, A.PROPCNT, \n"
             * );sql.append(
             * " 		A.COMP,(SELECT GET_COMPNM(COMP,2,4) FROM TZ_COMP WHERE COMP = A.COMP) DEPTNM, \n"
             * ); sql.append(" 		A.GRTYPE, GET_MULTICODENM(" +
             * SQLString.Format(GRTYPE_CODE) + ", GRTYPE) GRTYPENM, \n");
             * sql.append(
             * " 		B.NAME MASTERNAME,B.EMAIL MASTEREMAIL,B.COMPTEL MASTERCOMPTEL , A.USEYN \n"
             * ); sql.append("   FROM TZ_GRCODE A  \n");sql.append(
             * "   left join  TZ_MEMBER B on A.MASTER = B.USERID AND B.GRCODE = 'N000001' \n"
             * ); sql.append("  ORDER BY GRCODENM ");
             */

            sb.append("SELECT  /* 교육그룹 목록 조회 */             \n");
            sb.append("        A.GRCODE                             \n");
            sb.append("    ,   A.GRCODENM                           \n");
            sb.append("    ,   A.IDTYPE                             \n");
            sb.append("    ,   A.MANAGER                            \n");
            sb.append("    ,   A.REPDATE                            \n");
            sb.append("    ,   A.DOMAIN                             \n");
            sb.append("    ,   A.CHKFIRST                           \n");
            sb.append("    ,   A.CHKFINAL                           \n");
            sb.append("    ,   A.ISLOGIN                            \n");
            sb.append("    ,   A.ISJIK                              \n");
            sb.append("    ,   A.ISONLYGATE                         \n");
            sb.append("    ,   A.ISUSEBILL                          \n");
            sb.append("    ,   A.MASTER                             \n");
            sb.append("    ,   A.INDATE                             \n");
            sb.append("    ,   A.LUSERID                            \n");
            sb.append("    ,   A.LDATE                              \n");
            sb.append("    ,   A.PROPCNT                            \n");
            sb.append("    ,   A.COMP                               \n");
            sb.append("    ,   GET_COMPNM(A.COMP, 2, 4) AS EPTNM    \n");
            sb.append("    ,   A.GRTYPE                             \n");
            sb.append("    ,   GET_CODENM(?, GRTYPE) GRTYPENM       \n");
            sb.append("    ,   GET_CODENM(?, GUBUN) GRGUBUNNM       \n");
            sb.append("    ,   B.NAME MASTERNAME                    \n");
            sb.append("    ,   B.EMAIL MASTEREMAIL                  \n");
            sb.append("    ,   B.COMPTEL MASTERCOMPTEL              \n");
            sb.append("    ,   A.USEYN                              \n");
            sb.append("    ,   A.CODE_ORDER                         \n");
            sb.append("  FROM  TZ_GRCODE A                          \n");
            sb.append("  LEFT  JOIN TZ_MEMBER B                     \n");
            sb.append("    ON  A.MASTER = B.USERID                  \n");
            sb.append("   AND  B.GRCODE = 'N000001'                 \n");
            sb.append(" ORDER  BY TO_NUMBER(A.CODE_ORDER)           \n");
            sb.append("    ,   A.GRCODENM                           \n");

            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            pstmt = connMgr.prepareStatement(sb.toString());
            pstmt.setString(1, GRTYPE_CODE);
            pstmt.setString(2, GRGUBUN_CODE);

            // ls = connMgr.executeQuery(sql.toString());
            ls = new ListSet(pstmt);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list1.add(dbox);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }

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

        return list1;
    }

    /**
     * 교육그룹코드
     * 
     * @param box receive from the form object and session
     * @return ArrayList 교육그룹리스트
     */
    public ArrayList<EduGroupData> SelectGrcodeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<EduGroupData> list1 = null;
        String sql = "";//, sql2="";
        //		DataBox dbox = null;
        //		GrcompBean	grcompBean=null;

        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = "select   b.grcode, b.grcodenm " 
                + "  from   tz_grcodeman a, tz_grcode b " 
                + " where   a.grcode = b.grcode and b.useyn = 'Y' " 
                + "   and   a.userid = " + StringManager.makeSQL(v_userid) 
                + " group by b.grcode, b.grcodenm";

            ls = connMgr.executeQuery(sql);
            list1 = new ArrayList();

            while (ls.next()) {
                //				dbox = ls.getDataBox();
                //				dbox.put("grcode",ls.getString("grcode"));
                //				dbox.put("grcodenm",ls.getString("grcodenm"));
                EduGroupData data = new EduGroupData();
                data.setGrcode(ls.getString("grcode"));
                data.setGrcodenm(ls.getString("grcodenm"));

                list1.add(data);
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
        return list1;
    }

    /**
     * 교육그룹데이타 조회
     * 
     * @param box receive from the form object and session
     * @return DataBox
     **/
    public DataBox SelectEduGroupData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        String v_grcode = box.getString("p_grcode");
        DataBox dbox = null;
        String sql = "";

        try {
            sql = "select   grcode, grcodenm, idtype, manager, repdate, domain, chkFirst, chkFinal,   \n" 
                + "         islogin, isjik, isonlygate, isusebill, menutype, gubun, GET_CODENM(" + GRGUBUN_CODE + ", GUBUN) GRGUBUNNM,\n"
                + "         master, indate, luserid, ldate, propcnt, etcdata, comp, grtype, useyn, gr_prefix \n" 
                + "  from   tz_grcode where grcode='" + v_grcode + "'";

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();

                sql = "select name,email,comptel from tz_member where userid='" + ls.getString("master") + "'";

                if (ls2 != null) {
                    try {
                        ls2.close();
                    } catch (Exception e) {
                    }
                }
                ls2 = connMgr.executeQuery(sql);
                if (ls2.next()) {
                    dbox.put("d_mastername", ls2.getString("name"));
                    dbox.put("d_masteremail", ls2.getString("email"));
                    dbox.put("d_mastercomptel", ls2.getString("comptel"));
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
        return dbox;
    }

    /**
     * 교육그룹데이타 조회
     * 
     * @param box receive from the form object and session
     * @return EduGroupData
     **/

    //이 메소드는 원래 SelectEduGroupData 이었음
    public EduGroupData SelectEduGroupDataSubj(RequestBox box) throws Exception {
        String v_grcode = box.getString("p_grcode");
        EduGroupData data = null;

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        //		ArrayList list1 = null;
        String sql = "";

        try {
            sql = "select grcode,grcodenm,idtype,manager,repdate,domain,chkFirst,chkFinal,islogin,isjik,isonlygate,isusebill,"
                    + "		master,indate, luserid, ldate, propcnt,etcdata,comp " + "  from tz_grcode where grcode='" + v_grcode + "'";

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new EduGroupData();
                //dbox = ls.getDataBox();

                data.setGrcode(ls.getString("grcode"));
                data.setGrcodenm(ls.getString("grcodenm"));
                data.setComp(ls.getString("comp"));
                data.setIdtype(ls.getString("idtype"));
                data.setMaster(ls.getString("master"));
                data.setManager(ls.getString("manager"));
                data.setRepdate(ls.getString("repdate"));
                data.setDomain(ls.getString("domain"));
                data.setChkFirst(ls.getString("chkfirst"));
                data.setChkFinal(ls.getString("chkfinal"));
                data.setIslogin(ls.getString("islogin"));
                data.setIsjik(ls.getString("isjik"));
                data.setIsonlygate(ls.getString("isonlygate"));
                data.setIsusebill(ls.getString("isusebill"));
                data.setIndate(ls.getString("indate"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
                data.setEtcdata(ls.getString("etcdata"));
                data.setPropcnt(ls.getInt("propcnt"));
                break;
            }

            if (data.getManager() != null) {
                sql = "select name from tz_member where userid=" + StringManager.makeSQL(data.getManager());
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }
                ls = connMgr.executeQuery(sql);
                if (ls.next())
                    data.setManagerName(ls.getString("name"));
            }

            if (data.getMaster() != null) {
                sql = "select name,email,comptel from tz_member where userid=" + StringManager.makeSQL(data.getMaster());
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }
                ls = connMgr.executeQuery(sql);
                if (ls.next()) {
                    data.setMasterName(ls.getString("name"));
                    data.setMasterEmail(ls.getString("email"));
                    data.setMasterComptel(ls.getString("comptel"));
                }
            }

            // get 연결회사정보
            sql = "select a.comp comp, b.companynm compnm  from tz_grcomp a, tz_comp b " + " where a.comp=b.comp and a.grcode="
                    + StringManager.makeSQL(v_grcode);
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data.makeSub(ls.getString("comp"), ls.getString("compnm"));
                //dbox.put("makesub",ls.getString("comp"),ls.getString("compnm"));
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
        return data;
    }

    /**
     * 새로운 교육그룹코드 등록
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int InsertEduGroup(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = "";
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "select 'N'||isnull(ltrim(rtrim(to_char(to_number(max(substring(grcode,2,6)))+1,'000000'))),'000001') GRS " + "  from tz_grcode";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                v_grcode = ls.getString("GRS");
            } else {
                v_grcode = "N000001";
            }

            //insert TZ_EduGroup table
            sql = "INSERT INTO TZ_GRCODE("
                + "GRCODE, GRCODENM, IDTYPE, MANAGER, REPDATE,"
                + "DOMAIN, CHKFIRST, CHKFINAL, ISLOGIN, ISJIK,"
                + "ISONLYGATE, ISUSEBILL, MASTER, INDATE, LUSERID,"
                + "LDATE, PROPCNT, ETCDATA,COMP,GRTYPE,USEYN,MENUTYPE,"
                + "GR_PREFIX,"
                + "GUBUN"
                + ") VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?,TO_CHAR(SYSDATE, 'YYYYMMDD'),?,  TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'), ?, ?,?, ?,'Y',?,?,?)";

            //System.out.println(box.getArrayToString("p_grtype") + "\n\nsql==>"+ sql);
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_grcode);
            pstmt.setString(2, box.getString("p_grcodenm"));
            pstmt.setString(3, box.getStringDefault("p_idtype", "ID"));
            pstmt.setString(4, box.getString("p_manager"));
            pstmt.setString(5, box.getString("p_repdate"));
            pstmt.setString(6, box.getString("p_domain"));
            pstmt.setString(7, box.getString("p_chkfirst"));
            pstmt.setString(8, box.getString("p_chkfinal"));
            pstmt.setString(9, box.getStringDefault("p_islogin", "N"));
            pstmt.setString(10, box.getStringDefault("p_isjik", "N"));
            pstmt.setString(11, box.getStringDefault("p_isonlygate", "N"));
            pstmt.setString(12, box.getStringDefault("p_isusebill", "N"));
            pstmt.setString(13, box.getString("p_master"));
            pstmt.setString(14, v_luserid);
            pstmt.setInt(15, box.getInt("p_propcnt"));
            pstmt.setString(16, box.getString("p_etcdata"));
            pstmt.setString(17, box.getString("s_seldept"));
            pstmt.setString(18, box.getArrayToString("p_grtype"));
            pstmt.setString(19, box.getArrayToString("p_menutype"));
            pstmt.setString(20, box.getString("p_gr_prefix"));
            pstmt.setString(21, box.getString("p_grgubun"));

            isOk = pstmt.executeUpdate();

            if (isOk == 1)
                assignComp(connMgr, box, v_grcode); //회사지정정보저장
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (isOk > 0) {
                connMgr.commit();
            } else
                connMgr.rollback();
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            connMgr.setAutoCommit(true);
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
     * 회사연결
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     **/
    public int assignComp(DBConnectionManager connMgr, RequestBox box, String p_grcode) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = p_grcode;
        String v_luserid = box.getSession("userid"); // 세션변수에서 사용자 id를 가져온다.

        try {

            String v_codes = box.getString("p_compTxt");
            StringTokenizer v_token = new StringTokenizer(v_codes, ";");
            String v_comp = "";
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // TZ_GRcomp initialize
            sql = " DELETE FROM TZ_GRCOMP WHERE GRCODE=?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_grcode);
            isOk = pstmt.executeUpdate();

            System.out.println("Delete isOk = " + isOk);

            sql = " insert into tz_grcomp" + " (grcode, comp, indate, luserid, ldate) "
                    + " values (?,?,to_char(sysdate, 'YYYYMMDD'),?,to_char(sysdate,'YYYYMMDDHH24MISS'))";
            pstmt = connMgr.prepareStatement(sql);
            while (v_token.hasMoreTokens()) {
                v_comp = v_token.nextToken();
                //insert TZ_GRCOMP table
                pstmt.setString(1, v_grcode);
                pstmt.setString(2, v_comp);
                pstmt.setString(3, v_luserid);
                System.out.println("v_grcode=" + v_grcode);
                System.out.println("v_comp=" + v_comp);
                isOk = pstmt.executeUpdate();

                System.out.println("token isOk = " + isOk);
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            connMgr.setAutoCommit(true);
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
     * 선택된 교육그룹코드 수정
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int UpdateEduGroup(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_luserid = "session"; // 세션변수에서 사용자 id를 가져온다.

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //insert TZ_EduGroup table
            sql = "UPDATE TZ_GRCODE set " 
                + " GRCODENM	=?,  IDTYPE	 =?,  MANAGER	=?, " 
                + " MASTER	 =?,  REPDATE	=?,  PROPCNT	=?, "
                + " DOMAIN	 =?,  CHKFIRST	=?,  CHKFINAL	=?, " 
                + " ISLOGIN	=?,  ISJIK	  =?,  ISONLYGATE =?, "
                + " ISUSEBILL  =?,  Luserid	=?,  LDATE	  = to_char(sysdate,'YYYYMMDDHH24MISS'), " 
                + " ETCDATA	=?,	COMP =?, GRTYPE =?, useyn= ?" 
                + ", MENUTYPE=? " //swchoi 추가
                + ", GR_PREFIX= ? " //아이디 접두어 추가
                + ", GUBUN= ? " //교육구분 추가
                + " WHERE GRCODE = ? ";

            pstmt = connMgr.prepareStatement(sql);
            int index = 1;

            pstmt.setString(index++, box.getString("p_grcodenm"));
            pstmt.setString(index++, box.getStringDefault("p_idtype", "ID"));
            pstmt.setString(index++, box.getString("p_manager"));
            pstmt.setString(index++, box.getString("p_master"));
            pstmt.setString(index++, box.getString("p_repdate"));
            pstmt.setInt(index++, box.getInt("p_propcnt"));
            pstmt.setString(index++, box.getString("p_domain"));
            pstmt.setString(index++, box.getStringDefault("p_chkfirst", "N"));
            pstmt.setString(index++, box.getStringDefault("p_chkfinal", "Y"));
            pstmt.setString(index++, box.getStringDefault("p_islogin", "N"));
            pstmt.setString(index++, box.getStringDefault("p_isjik", "N"));
            pstmt.setString(index++, box.getStringDefault("p_isonlygate", "N"));
            pstmt.setString(index++, box.getStringDefault("p_isusebill", "N"));
            pstmt.setString(index++, v_luserid);
            pstmt.setString(index++, box.getString("p_etcdata"));
            pstmt.setString(index++, box.getString("s_seldept"));
            pstmt.setString(index++, box.getString("p_grtype"));
            pstmt.setString(index++, box.getString("p_useyn"));
            pstmt.setString(index++, box.getString("p_menutype"));
            pstmt.setString(index++, box.getString("p_gr_prefix"));
            pstmt.setString(index++, box.getString("p_grgubun"));
            
            pstmt.setString(index++, v_grcode);

            isOk = pstmt.executeUpdate();

            System.out.println("isOk 1 = " + isOk);
            if (isOk == 1) {
                //isOk = 
                assignComp(connMgr, box, v_grcode); //회사지정정보저장
                System.out.println("isOk 2 = " + isOk);
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            } else
                connMgr.rollback();
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            connMgr.setAutoCommit(true);
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
     * 코스리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 코스 리스트
     */
    public ArrayList TargetCourseList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql = "";
        SubjectInfoData data = null;

        try {
            sql = "select course, coursenm, inuserid, indate, gradscore, gradfailcnt, biyong, subjcnt, luserid, ldate"
                    + "  from tz_course order by coursenm";

            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new SubjectInfoData();
                data.setSubj(ls.getString("course"));
                data.setSubjnm(ls.getString("coursenm"));
                list1.add(data);
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
        return list1;
    }

    /**
     * 집합,사이버/대분류별 과정리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정 리스트
     */
    public ArrayList TargetSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql = "";
        SubjectInfoData data = null;

        String v_gubun = box.getString("p_gubun");
        String v_upperclass = box.getString("p_upperclass");

        if (v_gubun.equals(""))
            v_gubun = "ALL";
        if (v_upperclass.equals(""))
            v_upperclass = "ALL";

        try {
            sql = "select a.subj, a.subjnm, a.isonoff, b.upperclass, b.classname ";
            sql += "  from tz_subj a, tz_subjatt  b								";
            //			sql+= " where a.subjclass = b.subjclass ";
            sql += " where a.upperclass  = b.upperclass ";
            sql += "	and b.middleclass = '000' ";
            sql += "	and b.lowerclass  = '000' ";

            if (!v_gubun.equals("ALL"))
                sql += " and a.isonoff = " + SQLString.Format(v_gubun);

            if (!v_upperclass.equals("ALL"))
                sql += " and a.upperclass = " + SQLString.Format(v_upperclass);

            sql += " order by a.isonoff desc, b.upperclass, a.subjnm ";

            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new SubjectInfoData();

                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setIsonoff(ls.getString("isonoff"));
                if (ls.getString("isonoff").equals("ON")) {
                    data.setIsonoffnm("사이버");
                } else if (ls.getString("isonoff").equals("OFF")) {
                    data.setIsonoffnm("집합");
                }
                data.setUpperclass(ls.getString("upperclass"));
                data.setClassname(ls.getString("classname"));

                list1.add(data);
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
        return list1;
    }

    /**
     * 선택한 과정/코스 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 선택한 과정/코스 리스트
     */
    public ArrayList SelectedList(RequestBox box) throws Exception {
        ArrayList list1 = null;
        list1 = new ArrayList();
        SubjectInfoData data = null;
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        String v_subjectcodes = box.getString("p_selectedcodes");
        String v_subjecttexts = box.getString("p_selectedtexts");
        String v_grcode = box.getString("p_grcode");

        try {
            if (!v_subjectcodes.equals("")) {
                try {
                    StringTokenizer v_tokencode = new StringTokenizer(v_subjectcodes, ";");
                    StringTokenizer v_tokentext = new StringTokenizer(v_subjecttexts, ";");

                    String v_code = "";
                    String v_text = "";

                    while (v_tokencode.hasMoreTokens() && v_tokentext.hasMoreTokens()) {
                        v_code = v_tokencode.nextToken();
                        v_text = v_tokentext.nextToken();

                        data = new SubjectInfoData();

                        data.setSubj(v_code);
                        data.setDisplayname(v_text);

                        list1.add(data);
                    }
                } catch (Exception ex) {
                    ErrorManager.getErrorStackTrace(ex, box, "");
                    throw new Exception(ex.getMessage());
                } finally {

                }
            } else {
                if (!v_grcode.equals("")) {
                    sql = "select a.course, a.coursenm, a.inuserid, a.indate, a.gradscore, a.gradfailcnt, a.luserid, a.ldate "
                            + "  from tz_course		a, " + "		tz_grsubj	  b" + " where a.course=b.subjcourse and b.grcode="
                            + SQLString.Format(v_grcode) + " order by course";

                    connMgr = new DBConnectionManager();
                    list1 = new ArrayList();
                    ls = connMgr.executeQuery(sql);

                    while (ls.next()) {
                        data = new SubjectInfoData();

                        data.setSubj(ls.getString("course"));
                        data.setSubjnm(ls.getString("coursenm"));
                        list1.add(data);
                    }

                    sql = "select a.subj, a.subjnm, a.isonoff, a.upperclass, "
                            + "		(select classname from tz_subjatt where upperclass = a.upperclass and middleclass = '000' and lowerclass  = '000') classname "
                            + "  from tz_subj		a, " + "		tz_grsubj	  b" + " where a.subj=b.subjcourse and b.grcode=" + SQLString.Format(v_grcode)
                            + " order by a.isonoff desc, a.upperclass, a.subjnm, disseq";
                    if (ls != null) {
                        try {
                            ls.close();
                        } catch (Exception e) {
                        }
                    }
                    ls = connMgr.executeQuery(sql);

                    while (ls.next()) {
                        data = new SubjectInfoData();

                        data.setSubj(ls.getString("subj"));
                        data.setSubjnm(ls.getString("subjnm"));
                        data.setIsonoff(ls.getString("isonoff"));
                        if (ls.getString("isonoff").equals("ON")) {
                            data.setIsonoffnm("사이버");
                        } else if (ls.getString("isonoff").equals("OFF")) {
                            data.setIsonoffnm("집합");
                        }
                        data.setUpperclass(ls.getString("upperclass"));
                        data.setClassname(ls.getString("classname"));

                        list1.add(data);
                    }
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

        return list1;
    }

    /**
     * 과정/코스 지정정보 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int SaveAssign(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_luserid = "session"; // 세션변수에서 사용자 id를 가져온다.

        try {

            String v_codes = box.getString("p_selectedcodes");
            StringTokenizer v_token = new StringTokenizer(v_codes, ";");
            String v_code = "";
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // TZ_GRSUBJ initialize (tz_subjseq에 존재하는 경우 삭제불가)
            sql = " delete from tz_grsubj where grcode=?" + "	and (subjcourse not in (select distinct subj from tz_subjseq where grcode=?))"
                    + "	and (subjcourse not in (select distinct course from tz_courseseq where grcode=?))";
            pstmt1 = connMgr.prepareStatement(sql);
            pstmt1.setString(1, v_grcode);
            pstmt1.setString(2, v_grcode);
            pstmt1.setString(3, v_grcode);
            isOk = pstmt1.executeUpdate();

            while (v_token.hasMoreTokens()) {
                v_code = v_token.nextToken();
                // check exists in TZ_GRSUBJ
                sql = " select case count(*) When 0 Then 'N' Else 'Y' End as isExist from tz_grsubj " + "where grcode='" + v_grcode
                        + "' and subjcourse=rtrim('" + v_code + "')";
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }
                ls = connMgr.executeQuery(sql);
                ls.next();

                if (ls.getString("isExist").equals("N")) {
                    //insert TZ_GRSUBJ table
                    sql = " insert into tz_grsubj" + " (grcode, subjcourse, isnew, disseq, grpcode, grpname, luserid, ldate) "
                            + " values (?,?,'N',0,'','',?,to_char(sysdate,'YYYYMMDDHH24MISS'))";

                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_grcode);
                    pstmt.setString(2, v_code);
                    pstmt.setString(3, v_luserid);
                    isOk = pstmt.executeUpdate();
                }

            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            connMgr.setAutoCommit(true);
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
     * 과정 지정정보 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int SaveAssign2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ListSet ls = null;
        String sql = "", sql1 = null;
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_luserid = box.getSession("userid");
        String v_subj = "";
        String v_isCourse = "";
        Vector v_checks = box.getVector("p_checks");
        String v_code = "";
        Enumeration em = v_checks.elements();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // TZ_GRSUBJ initialize (tz_subjseq에 존재하는 경우 삭제불가)
            sql = " delete from tz_grsubj where grcode=?" + "	and (subjcourse not in (select distinct subj from tz_subjseq where grcode=?))"
                    + "	and (subjcourse not in (select distinct course from tz_courseseq where grcode=?))";
            pstmt1 = connMgr.prepareStatement(sql);
            pstmt1.setString(1, v_grcode);
            pstmt1.setString(2, v_grcode);
            pstmt1.setString(3, v_grcode);
            isOk = pstmt1.executeUpdate();

            sql1 = " insert into tz_grsubj" + " (grcode, subjcourse, iscourse,isnew, disseq, grpcode, grpname, luserid, ldate) "
                    + " values (?,?,?,'N',0,'','',?, to_char(sysdate,'YYYYMMDDHH24MISS'))";

            pstmt = connMgr.prepareStatement(sql1);

            while (em.hasMoreElements()) {
                //v_code = v_token.nextToken();
                v_code = (String) em.nextElement();

                StringTokenizer st = new StringTokenizer(v_code, "|");
                v_subj = st.nextToken();
                v_isCourse = st.nextToken();

                // check exists in TZ_GRSUBJ
                sql = " select case count(*) When 0 Then 'N' Else 'Y' End as isExist from tz_grsubj " + "where grcode='" + v_grcode
                        + "' and subjcourse=rtrim('" + v_subj + "')";
                if (ls != null) {
                    try {
                        ls.close();
                    } catch (Exception e) {
                    }
                }
                ls = connMgr.executeQuery(sql);
                ls.next();

                if (ls.getString("isExist").equals("N")) {
                    ls.close();
                    //insert TZ_GRSUBJ table
                    pstmt.setString(1, v_grcode);
                    pstmt.setString(2, v_subj);
                    pstmt.setString(3, v_isCourse);
                    pstmt.setString(4, v_luserid);
                    pstmt.addBatch();
                    //					pstmt.executeUpdate();
                }

            }
            pstmt.executeBatch();
            isOk = 1;//의미없이 마지막것만 인서트 된걸 체크하나 마찬가지 결과..

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
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
        return isOk;
    }

    /**
     * 과정 지정정보 저장
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     **/
    public int SaveRecom(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_luserid = box.getSession("userid");
        String v_code = "";
        Vector v_checks = box.getVector("p_checks");
        Enumeration em = v_checks.elements();

        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // TZ_GRSUBJ initialize (tz_subjseq에 존재하는 경우 삭제불가)
            sql = " delete from tz_grrecom where grcode=?";
            pstmt1 = connMgr.prepareStatement(sql);
            pstmt1.setString(1, v_grcode);
            isOk = pstmt1.executeUpdate();

            while (em.hasMoreElements()) {
                //v_code = v_token.nextToken();
                v_code = (String) em.nextElement();

                // check exists in TZ_GRSUBJ
                //sql = " select decode(count(*),0,'N','Y') isExist from tz_grrecom "
                //	+ "where grcode='"+v_grcode+"' and subjcourse=rtrim('"+v_code+"')";
                //if(ls != null) { try { ls.close(); }catch (Exception e) {} }
                //ls = connMgr.executeQuery(sql);
                //ls.next();
                //
                //if(ls.getString("isExist").equals("N")){
                //insert TZ_GRSUBJ table
                sql = " insert into tz_grrecom" + " (grcode, subjcourse, isnew, disseq, grpcode, grpname, luserid, ldate) "
                        + " values (?,?,'N',0,'','',?,to_char(sysdate,'YYYYMMDDHH24MISS'))";

                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, v_grcode);
                pstmt.setString(2, v_code);
                pstmt.setString(3, v_luserid);
                isOk = pstmt.executeUpdate();
                //}
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
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
        return isOk;
    }

    /**
     * 선택된 코스코드 삭제
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * 
     *         public int DeleteEduGroup(RequestBox box) throws Exception {
     *         DBConnectionManager connMgr = null; PreparedStatement pstmt1 =
     *         null; String sql1 = ""; int isOk1 = 0; PreparedStatement pstmt2 =
     *         null; String sql2 = ""; int isOk2 = 0;
     * 
     *         String v_EduGroup = box.getString("p_EduGroup"); try { connMgr =
     *         new DBConnectionManager(); connMgr.setAutoCommit(false);
     * 
     *         //delete TZ_EduGroup table sql1 =
     *         "delete from TZ_EduGroup where EduGroup = ? "; pstmt1 =
     *         connMgr.prepareStatement(sql1); pstmt1.setString(1, v_EduGroup);
     *         isOk1 = pstmt1.executeUpdate();
     * 
     *         //delete TZ_EduGroupSUBJ table sql2 =
     *         "delete from TZ_EduGroupSUBJ where EduGroup = ? "; pstmt2 =
     *         connMgr.prepareStatement(sql2); pstmt2.setString(1, v_EduGroup);
     *         isOk2 = pstmt2.executeUpdate();
     * 
     *         if(isOk1 > 0 && isOk2 >0) connMgr.commit(); // 2가지 sql 이 꼭 같이
     *         insert 되어야 하는 경우이므로 else connMgr.rollback(); } catch (Exception
     *         ex) { connMgr.rollback(); ErrorManager.getErrorStackTrace(ex,
     *         box, sql2); throw new Exception("sql2 = " + sql2 + "\r\n" +
     *         ex.getMessage()); } finally { connMgr.setAutoCommit(true);
     *         if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1)
     *         {} } if(pstmt2 != null) { try { pstmt2.close(); } catch
     *         (Exception e1) {} } if(connMgr != null) { try {
     *         connMgr.freeConnection(); }catch (Exception e10) {} } } return
     *         isOk1*isOk2; }
     */

    /**
     * 과정리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정리스트
     */
    public ArrayList SelectSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        ConfigSet conf = new ConfigSet();
        boolean isCourse = conf.getBoolean("course.use"); //	  코스가 있어야 하는지 여부

        String v_grcode = box.getString("p_grcode"); //교육그룹
        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        //		String  ss_subjcourse	= box.getStringDefault("s_subjcourse","ALL");	//과정&코스

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {
            sql += " select  a.upperclass, a.middleclass, b.classname, a.isonoff, c.codenm, ";
            sql += "		 a.subj, a.subjnm, a.isuse,";
            sql += "		 (select grcode from tz_grsubj where subjcourse=a.subj and grcode=" + SQLString.Format(v_grcode) + ") grcode ";
            sql += "	from  tz_subj a,  tz_subjatt b,  tz_code c";
            sql += "  where  a.upperclass  = b.upperclass and a.middleclass = b.middleclass and b.lowerclass='000'  ";
            sql += "	and  a.isonoff	 = c.code  ";
            sql += "	and  a.isuse	 = 'Y' ";
            if (!ss_upperclass.equals("ALL")) {
                sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
            }
            if (!ss_middleclass.equals("ALL")) {
                sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
            }
            if (!ss_lowerclass.equals("ALL")) {
                sql += " and a.lowerclass = " + SQLString.Format(ss_lowerclass);
            }

            if (isCourse) {
                sql += " union													";
                sql += " select  a.upperclass, a.middleclass, b.classname, 'ON' isonoff, '사이버' codenm,  ";
                sql += "		 a.course subj,  a.coursenm  subjnm,  'Y' isuse, ";
                sql += "		 (select grcode from tz_grsubj where subjcourse=a.course and grcode=" + SQLString.Format(v_grcode) + ") grcode ";
                sql += "	from  tz_course a,  tz_subjatt b  ";
                sql += "  where  a.upperclass  = b.upperclass and a.middleclass = b.middleclass and b.lowerclass='000'  ";
                if (!ss_upperclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                }
                if (!ss_middleclass.equals("ALL")) {
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                }
                if (!ss_lowerclass.equals("ALL")) {
                    sql += " and a.lowerclass = " + SQLString.Format(ss_lowerclass);
                }
            }
            if (v_orderColumn.equals("")) {
                sql += " order by upperclass,  middleclass, subjnm ";
            } else {
                sql += " order by " + v_orderColumn + v_orderType;
            }
            System.out.println("sql-------------------------->=" + sql);
            connMgr = new DBConnectionManager();
            list = new ArrayList();
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
     * 과정리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정리스트
     */
    public ArrayList SelectSubjList2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        //		ConfigSet conf = new ConfigSet();
        //		boolean isCourse = conf.getBoolean("course.use");		//	  코스가 있어야 하는지 여부

        String v_grcode = box.getString("p_grcode"); //교육그룹
        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        //		String  ss_subjcourse	= box.getStringDefault("s_subjcourse","ALL");	//과정&코스

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {

            sql = " select  a.upperclass, a.middleclass, b.classname, a.isonoff, c.codenm, ";
            sql += "		 a.subj, a.subjnm, a.isuse,a.introducefilenamereal,a.introducefilenamenew,";
            sql += "		 (select grcode from tz_grrecom where subjcourse=a.subj and grcode=" + SQLString.Format(v_grcode) + ") grcode ";
            sql += "	from  tz_subj a,  tz_subjatt b,  tz_code c, tz_grsubj d";
            sql += "  where  a.subj = d. subjcourse and d.grcode=" + SQLString.Format(v_grcode)
                    + " and a.upperclass  = b.upperclass and a.middleclass = b.middleclass and b.lowerclass='000'  ";
            sql += "	and  a.isonoff	 = c.code ";
            if (!ss_upperclass.equals("ALL")) {
                sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
            }
            if (!ss_middleclass.equals("ALL")) {
                sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
            }
            if (!ss_lowerclass.equals("ALL")) {
                sql += " and a.lowerclass = " + SQLString.Format(ss_lowerclass);
            }
            /*
             * -- 홈페이지쪽 추천과정 보여주기가 해결되면 주석 삭제 if (isCourse) { sql+=
             * " union													"; sql+= " (														"; sql+=
             * " select  a.upperclass, a.middleclass, b.classname, 'ON' isonoff, '사이버' codenm,  "
             * ; sql+=
             * "		 a.course subj,  a.coursenm  subjnm,  'Y' isuse, '' introducefilenamereal, '' introducefilenamenew,"
             * ; sql+=
             * "		 (select grcode from tz_grrecom where subjcourse=a.course and grcode="
             * +SQLString.Format(v_grcode)+") grcode "; sql+=
             * "	from  tz_course a,  tz_subjatt b  "; sql+=
             * "  where  a.upperclass  = b.upperclass and a.middleclass = b.middleclass and b.lowerclass='000'  "
             * ; if (!ss_upperclass.equals("ALL")) { sql +=
             * " and a.upperclass = "+SQLString.Format(ss_upperclass); } if
             * (!ss_middleclass.equals("ALL")) { sql +=
             * " and a.middleclass = "+SQLString.Format(ss_middleclass); } if
             * (!ss_lowerclass.equals("ALL")) { sql +=
             * " and a.lowerclass = "+SQLString.Format(ss_lowerclass); } sql+=
             * " )														"; }
             */
            if (v_orderColumn.equals("")) {
                sql += " order by a.upperclass, a.middleclass, subjnm ";
            } else {
                sql += " order by " + v_orderColumn + v_orderType;
            }
            connMgr = new DBConnectionManager();
            list = new ArrayList();
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
