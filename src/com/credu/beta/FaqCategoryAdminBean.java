
//**********************************************************
//  1. 제      목: FAQ 카테고리 관리
//  2. 프로그램명 : FaqCategoryAdminBean.java
//  3. 개      요: FAQ 카테고리 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7.  2
//  7. 수      정:
//**********************************************************

package com.credu.beta;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;

/**
 * FAQ 카테고리 관리(ADMIN)
 *
 * @date   : 2003. 7
 * @author : s.j Jung
 */
public class FaqCategoryAdminBean {

    public FaqCategoryAdminBean() {}

	/**
    * FAQ 카테고리화면 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   대분류 코드 리스트
    * @throws Exception
	*/
    public ArrayList selectListFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        FaqCategoryData data = null;

        String v_searchtext = box.getString("p_searchtext");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select faqcategory, faqcategorynm, luserid, ldate from TZ_FAQ_CATEGORY    ";

            if ( !v_searchtext.equals("")) {      //    검색어가 있으면
               sql += " where faqcategorynm like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }
            sql += " order by faqcategory asc ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new FaqCategoryData();

                data.setFaqCategory(ls.getString("faqcategory"));
                data.setFaqCategorynm(ls.getString("faqcategorynm"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
                list.add(data);
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
        return list;
    }


	/**
	* FAQ 카테고리화면 상세보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 상세정보
	* @throws Exception
	*/  
   public FaqCategoryData selectViewFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        FaqCategoryData data = null;

        String v_faqcategory = box.getString("p_faqcategory");

        try {
            connMgr = new DBConnectionManager();

            sql  = " select faqcategory, faqcategorynm, luserid, ldate  from TZ_FAQ_CATEGORY  ";
            sql += "  where faqcategory  = " + StringManager.makeSQL(v_faqcategory);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data=new FaqCategoryData();
                data.setFaqCategory(ls.getString("faqcategory"));
                data.setFaqCategorynm(ls.getString("faqcategorynm"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return data;
    }


	/**
	* FAQ 카테고리 등록할때
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/  
    public int insertFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        String v_faqcategory = "";

        String v_faqcategorynm = box.getString("p_faqcategorynm");
        String s_userid = box.getSession("userid");

        try {
           connMgr = new DBConnectionManager();

           sql  = "select max(faqcategory) from TZ_FAQ_CATEGORY  ";
           ls = connMgr.executeQuery(sql);
           if (ls.next()) {
               v_faqcategory = CodeConfigBean.addZero(StringManager.toInt(ls.getString(1)) + 1, 4);
           } else {
               v_faqcategory = "0001";
           }

           sql1 =  "insert into TZ_FAQ_CATEGORY(faqcategory, faqcategorynm, luserid, ldate)           ";
           sql1 += "            values (?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";

           pstmt = connMgr.prepareStatement(sql1);

           pstmt.setString(1,  v_faqcategory);
           pstmt.setString(2,  v_faqcategorynm);
           pstmt.setString(3, s_userid);

           isOk = pstmt.executeUpdate();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


	/**
	* FAQ 카테고리 수정하여 저장할때
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/  
     public int updateFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_faqcategory   = box.getString("p_faqcategory");
        String v_faqcategorynm = box.getString("p_faqcategorynm");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql  = " update TZ_FAQ_CATEGORY set faqcategorynm = ? , luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where faqcategory = ?                                                                                    ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_faqcategorynm);
            pstmt.setString(2, s_userid);
            pstmt.setString(3, v_faqcategory);

            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


	/**
	* FAQ 카테고리 삭제할때 - 하위 FAQ 삭제
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/
    public int deleteFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";

        int isOk1 = 0;
        int isOk2 = 0;

        String v_faqcategory  = box.getString("p_faqcategory");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql1  = " delete from TZ_FAQ_CATEGORY  ";
            sql1 += "   where faqcategory = ?             ";

            sql2  = " delete from TZ_FAQ ";
            sql2 += "   where faqcategory = ?   ";


            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setString(1, v_faqcategory);
            isOk1 = pstmt1.executeUpdate();


            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(1, v_faqcategory);
            isOk2 = pstmt2.executeUpdate();

            if(isOk1 > 0 ) connMgr.commit();        //하위 분류의 로우가 없을경우 isOk2 가 0이므로 isOk2 >0 조건 제외
            else connMgr.rollback();

        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1+ "\r\n" +sql2);
            throw new Exception("sql = " + sql1 + "\r\n" + sql2 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {};
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
//        return isOk1*isOk2;
        return isOk1;
    }



/************************************* 공통함수들 ***********************************************************/


	/**
	 * FAQ 카테고리명 
	 * @param faqcategory      카테고리 코드
	 * @return result   카테고리 코드명
	 * @throws Exception
	 */    
    public static String getFaqCategoryName (String faqcategory) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        FaqCategoryData data = null;

        try {
            connMgr = new DBConnectionManager();

            sql  = " select faqcategorynm from TZ_FAQ_CATEGORY  ";
            sql += "  where faqcategory = " + StringManager.makeSQL(faqcategory); 
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data=new FaqCategoryData();
                data.setFaqCategorynm(ls.getString("faqcategorynm"));
                result = data.getFaqCategorynm();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return result;
    }

    /**
     * FAQ 카테고리 셀렉트박스
	 * @param name           셀렉트박스명
	 * @param selected       선택값
	 * @param event          이벤트명
	 * @param allcheck       전체유무
	 * @return
	 * @throws Exception
	 */
    public static String getFaqCategorySelecct (String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        FaqCategoryData data = null;


        result = "  <SELECT name=" + name + " " + event + " > \n";
        if (allcheck == 1) {
          result += "    <option value=''>== 선택하세요 ==</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql  = " select faqcategory,faqcategorynm from TZ_FAQ_CATEGORY  ";
            sql += " order by faqcategory asc                        ";

            ls = connMgr.executeQuery(sql);
			System.out.println(sql);
            while (ls.next()) {
                data = new FaqCategoryData();
                data.setFaqCategory(ls.getString("faqcategory"));
                data.setFaqCategorynm(ls.getString("faqcategorynm"));

                result += "    <option value=" + data.getFaqCategory();
                if (selected.equals(data.getFaqCategory())) {
                    result += " selected ";
                }
                
                result += ">" + data.getFaqCategorynm() + "</option> \n";
            }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result += "  </SELECT> \n";
        return result;

    }

}
