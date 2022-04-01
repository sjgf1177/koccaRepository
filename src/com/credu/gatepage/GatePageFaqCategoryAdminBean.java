package com.credu.gatepage;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;
import com.dunet.common.util.StringUtil;

/**
*GatePage FaqCategoryAdmin 빈
*<p>제목:GatePageFaqCategoryAdminBean.java</p>
*<p>설명:FaqCategoryAdmin 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 박준현
*@version 1.0
*/
public class GatePageFaqCategoryAdminBean {

    public GatePageFaqCategoryAdminBean() {}

	/**
    * GatePageFaqCategoryAdmin 화면 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   FaqCategory 리스트
    */
    public ArrayList selectListGatePageFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
		DataBox dbox = null;
		String v_faqgubun = "";
		String v_gubun = box.getString("p_gubun");

		if (v_gubun.equals("01")){
			v_faqgubun = "1";
		}else if (v_gubun.equals("02")){
			v_faqgubun = "2";
		}else if (v_gubun.equals("03")){
			v_faqgubun = "3";
		}else if (v_gubun.equals("04")){
			v_faqgubun = "4";
		}

        String v_faqcategorynm = box.getString("p_faqcategorynm2");
		if(v_faqcategorynm.equals("")){
		   v_faqcategorynm = "%";
		}

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select faqcategory, faqcategorynm, luserid, ldate from TZ_FAQ_CATEGORY    ";
            sql += " where faqgubun = " + SQLString.Format(v_faqgubun) + " and faqcategorynm like " +  SQLString.Format("%"+v_faqcategorynm+"%") ;
            sql += " order by faqcategory asc ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                dbox = ls.getDataBox();

                list.add(dbox);
            }         
            ls.close();
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
    * GatePageFaqCategoryAdmin 코드구하기 
    * @param box          receive from the form object and session
    * @return DataBox	  조회한 값을 DataBox에 담아 리턴
    */
	public DataBox GatePageFaqCategorySetting(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		DataBox dbox = null;
		ListSet ls = null;
		String sql = "";
		
		String v_faqcategory = "";
		 
		
		try {
			connMgr = new DBConnectionManager();
			
			//----------------------카테고리 코드를 구함 ----------------------------
			sql = "select max(NVL(faqcategory,'0000')) as faqcategory from TZ_FAQ_CATEGORY ";
            
			ls = connMgr.executeQuery(sql);
            ls.next();
			v_faqcategory = ls.getString("faqcategory");
			if (v_faqcategory == ""){
				v_faqcategory = "0000";
			}

			int v_faqcategory_int = Integer.parseInt(v_faqcategory);  //카테고리코드를 int형으로 변환

            v_faqcategory_int = v_faqcategory_int + 1; //변환된 카테고리코드에 + 1
            
			v_faqcategory = "" + v_faqcategory_int;  //카테고리코드를 다시 String형으로 변환

            v_faqcategory = CodeConfigBean.addZero(StringManager.toInt(v_faqcategory), 4);  // '0'을 붙여주는 메소드 (4자리)

			//------------------------------------------------------------------------------------	
			dbox = ls.getDataBox();

			dbox.put("d_faqcategory", v_faqcategory);

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return dbox;
	}   

	/**
	* GatePage FAQ 카테고리화면 상세보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 상세정보
	* @throws Exception
	*/  
   public DataBox selectGatePageFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		DataBox dbox = null;

        String v_faqcategory = box.getString("p_faqcategory");

        try {
            connMgr = new DBConnectionManager();

            sql  = " select faqcategory, faqcategorynm, luserid, ldate  from TZ_FAQ_CATEGORY  ";
            sql += "  where faqcategory  = " +  SQLString.Format(v_faqcategory) ;

            ls = connMgr.executeQuery(sql);
			
			ls.next();
			
			dbox = ls.getDataBox();

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }

	/**
	* FAQ 카테고리 등록할때
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/  
    public int insertGatePageFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;

		String v_faqgubun = "";
		String v_gubun = box.getString("p_gubun");

		if (v_gubun.equals("01")){
			v_faqgubun = "1";
		}else if (v_gubun.equals("02")){
			v_faqgubun = "2";
		}else if (v_gubun.equals("03")){
			v_faqgubun = "3";
		}else if (v_gubun.equals("04")){
			v_faqgubun = "4";
		}
        String v_faqcategory = box.getString("p_faqcategory");
        String v_faqcategorynm = box.getString("p_faqcategorynm");
        String s_userid = box.getSession("userid");

        try {
           connMgr = new DBConnectionManager();

           sql1 =  "insert into TZ_FAQ_CATEGORY(faqcategory, faqcategorynm, luserid, ldate, faqgubun)           ";
           sql1 += "            values (?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?)  ";

           pstmt = connMgr.prepareStatement(sql1);

           pstmt.setString(1, v_faqcategory);
           pstmt.setString(2, v_faqcategorynm);
           pstmt.setString(3, s_userid);
		   pstmt.setString(4, v_faqgubun);
           isOk = pstmt.executeUpdate();

		   if(isOk > 0){
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}else{
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}
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
     public int updateGatePageFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
		
		String s_userid = box.getSession("userid");

        String v_faqcategory   = box.getString("p_faqcategory");
        String v_faqcategorynm = box.getString("p_faqcategorynm");
        
        try {
            connMgr = new DBConnectionManager();

            sql  = " update TZ_FAQ_CATEGORY set faqcategorynm = ? , luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where faqcategory = ?                                                                                    ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_faqcategorynm);
            pstmt.setString(2, s_userid);
            pstmt.setString(3, v_faqcategory);

            isOk = pstmt.executeUpdate();

			if(isOk > 0){
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}else{
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}
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
    public int deleteGatePageFaqCategory(RequestBox box) throws Exception {
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
	
	/**
    * FAQ 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   리스트
    * @throws Exception
	*/
    public ArrayList selectListGatePageFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
		DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_search     = box.getString("p_search");
        String v_faqcategory = box.getString("p_faqcategory");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select fnum, title, contents, indate, luserid, ldate  ";
            sql += "   from TZ_FAQ                                         ";
            sql += "  where faqcategory   = " + SQLString.Format(v_faqcategory) ;

			if ( !v_searchtext.equals("")) {                            //    검색어가 있으면

				if (v_search.equals("title")) {                          //    제목으로 검색할때
                    sql += " and title like " + SQLString.Format("%" + v_searchtext + "%");
                } else if (v_search.equals("contents")) {                //    내용으로 검색할때
                    sql += " and contents like " + SQLString.Format("%" + v_searchtext + "%");
                }
            }
            sql += " order by faqcategory desc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                dbox = ls.getDataBox();

                list.add(dbox);
            }
            ls.close();
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
	 * faq 코드를 구함 - VLC
	 */
	public DataBox GatePageFaqSetting(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		DataBox dbox = null;
		ListSet ls = null;
		String sql = "";
		
		int v_fnum = 0;
		String v_faqcategory = box.getString("p_faqcategory");
		
		try {
			connMgr = new DBConnectionManager();
			
			//----------------------카테고리 코드를 구함 ----------------------------
			sql = "select max(NVL(fnum,'0000')) as fnum from TZ_FAQ ";
            sql += " where faqcategory  = " + SQLString.Format(v_faqcategory) ;

			ls = connMgr.executeQuery(sql);
            ls.next();

			v_fnum = ls.getInt("fnum") + 1; 
          
			dbox = ls.getDataBox();

			dbox.put("d_fnum", "" + v_fnum);

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return dbox;
	}   
	
	/**
	* FAQ 등록할때
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/  
    public int insertGatePageFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;

        String v_faqcategory = box.getString("p_faqcategory");
		String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
		int v_fnum = box.getInt("p_fnum");
        String s_userid = box.getSession("userid");

        try {
           connMgr = new DBConnectionManager();

           sql1 =  "insert into TZ_FAQ(faqcategory, fnum, title, contents, indate, luserid, ldate) ";
           sql1 += "values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";

           pstmt = connMgr.prepareStatement(sql1);

           pstmt.setString(1, v_faqcategory);
           pstmt.setInt(2, v_fnum);
           pstmt.setString(3, v_title);
		   pstmt.setString(4, v_contents);
		   pstmt.setString(5, s_userid);

           isOk = pstmt.executeUpdate();

		   if(isOk > 0){
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}else{
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}
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
	* GatePage FAQ 화면 상세보기
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/
	public DataBox selectGatePageFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		DataBox dbox = null;
        String v_faqcategory = box.getString("p_faqcategory");
		String v_faqcategorynm = box.getString("p_faqcategorynm");
		int v_fnum = box.getInt("p_fnum");

        try {
            connMgr = new DBConnectionManager();

            sql  = " select title, contents  from TZ_FAQ  ";
            sql += "  where faqcategory  = " +  SQLString.Format(v_faqcategory) ;
			sql += "  and fnum  = " +  v_fnum ;

            ls = connMgr.executeQuery(sql);
			
			ls.next();
			
			dbox = ls.getDataBox();

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }
	
	/**
	* FAQ 수정하여 저장할때
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/  
     public int updateGatePageFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
		
		String s_userid = box.getSession("userid");

        String v_faqcategory   = box.getString("p_faqcategory");
		int v_fnum = box.getInt("p_fnum");
		String v_title = StringUtil.removeTag(box.getString("p_title"));
		String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        
        try {
            connMgr = new DBConnectionManager();

            sql  = " update TZ_FAQ set title = ? , contents = ?, luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where faqcategory = ? and fnum = ? ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_title);
            pstmt.setString(2, v_contents);
            pstmt.setString(3, s_userid);
			pstmt.setString(4, v_faqcategory);
			pstmt.setInt(5, v_fnum);

            isOk = pstmt.executeUpdate();

			if(isOk > 0){
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}else{
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}
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
	* FAQ 삭제할때
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/
    public int deleteGatePageFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_faqcategory  = box.getString("p_faqcategory");
		int v_fnum = box.getInt("p_fnum");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql  = " delete from TZ_FAQ ";
            sql += "   where faqcategory = ? and fnum = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_faqcategory);
            pstmt.setInt(2, v_fnum);

            isOk = pstmt.executeUpdate();
		
			if(isOk > 0){
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}else{
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}

        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n"  + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {};
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
//        return isOk1*isOk2;
        return isOk;
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

            sql  = " select faqcategorynm from TZ_FAQ_CATEGORY        ";
            sql += "  where faqcategory = " + SQLString.Format(faqcategory); 
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data=new FaqCategoryData();
                data.setFaqCategorynm(ls.getString("faqcategorynm"));
                result = data.getFaqCategorynm();
            }                   
            ls.close();
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
    public static String getFaqCategorySelecct (String name, String selected, String event, int allcheck, RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        FaqCategoryData data = null;
		String v_faqgubun = "";
		String v_gubun = box.getString("p_gubun");

		
		if (v_gubun.equals("01")){
			v_faqgubun = "1";
		}else if (v_gubun.equals("02")){
			v_faqgubun = "2";
		}else if (v_gubun.equals("03")){
			v_faqgubun = "3";
		}else if (v_gubun.equals("04")){
			v_faqgubun = "4";
		}


       
		result = "  <SELECT name= " + "\"" +name+ "\"" + " " + event + " > \n";
        if (allcheck == 1) {
          result += "    <option value=''>== 선택하세요 ==</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql  = " select faqcategory,faqcategorynm from TZ_FAQ_CATEGORY where faqgubun = " + SQLString.Format(v_faqgubun) ;
            sql += " order by faqcategory asc                        ";
            ls = connMgr.executeQuery(sql);
			
			
			while (ls.next()) {
                
				data = new FaqCategoryData();
				
               
				data.setFaqCategory(ls.getString("faqcategory"));
			
               
				data.setFaqCategorynm(ls.getString("faqcategorynm"));
			
                result += "    <option value=" + "\"" +data.getFaqCategory()  + "\"";
				
               
				if (selected.equals(data.getFaqCategory())) {
                    result += " selected ";
                }
                
                result += ">" + data.getFaqCategorynm() + "</option> \n";
            }
            ls.close();

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
