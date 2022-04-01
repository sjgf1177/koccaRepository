//*******************a***************************************
//  1. 제      목: 관련사이ta트 BEAN
//  2. 프로그램명: LinkSiteBean.java
//  3. 개      요: 관련사이트 BEAN
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 08. 03
//  7. 수      정:
//**********************************************************
package com.credu.homepage;

import java.util.*;
import java.sql.*;
import com.credu.library.*;

public class LinkSiteBean {
	
	private static HashMap<String, ArrayList<DataBox>> linkSiteList;
	
    public LinkSiteBean() {
    	if(linkSiteList == null) linkSiteList	= new HashMap<String, ArrayList<DataBox>>();
    }
    
    public void resetLinkSiteList(String grcode) {
    	linkSiteList.put(grcode, null);
	}

    /**
          관련사이트 리스트 조회
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList<DataBox> SelectList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls          = null;
        ArrayList<DataBox> list = null;
        StringBuffer  sql          = new StringBuffer();
        DataBox dbox        = null;

        String  v_grcode     = box.getStringDefault("s_grcode",box.getSession("tem_grcode"));
        String  v_isuse		= box.getStringDefault("s_isuse","Y");
        
		// 존재시 반환 후 종료
	    if( linkSiteList.get(v_grcode) != null && linkSiteList.get(v_grcode).size() != 0) {
			return linkSiteList.get(v_grcode);
		} 

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql.append("select grcode, \n");
            sql.append("       seq,\n");
            sql.append("       sort,\n");
            sql.append("       sitenm,\n");
            sql.append("       url,\n");
            sql.append("       banner,\n");
            sql.append("       isuse, \n");
            sql.append("       islogin \n");
            sql.append("     from   TZ_LINKSITE \n");
			sql.append(" where 1=1 \n");
            if(!v_grcode.equals("") && !v_grcode.equals("ALL")) sql.append(" and grcode = '" + v_grcode + "' \n");
            if(v_isuse.equals("Y")) sql.append("	and isuse = 'Y'\n");
            sql.append(" order by sort \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            
            linkSiteList.put(v_grcode, list);  // static 적용

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }


   /**
          관련사이트 순위 수정
    @param box      receive from the form object and session
    @return int		isOk
    */
     public int updateSort(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String  sql			= "";
        int isOk			= 0;

        String v_grcode = box.getString("s_grcode");
        Vector v_seq	= box.getVector("seq");
        Vector v_sort	= box.getVector("p_sort");
		String  s_grcode     = "";
        String  s_seq		= "";
        String  s_sort		= "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

			sql  = " update TZ_LINKSITE set sort=? ";
			sql += " where grcode = ? and seq = ?";

			pstmt = connMgr.prepareStatement(sql);

            if (v_grcode != null) {
				for(int i = 0; i < v_seq.size() ; i++){
					s_seq = (String )v_seq.elementAt(i);
					s_sort = (String )v_sort.elementAt(i);

					pstmt.setString (1,  s_sort);
					pstmt.setString (2,  v_grcode);
					pstmt.setString (3,  s_seq);

					isOk = pstmt.executeUpdate();
					
					if(isOk == 0 )
						return 0;
				}
			}
            
            if(isOk > 0){
				connMgr.commit();
				isOk = 1;
				this.resetLinkSiteList(v_grcode);  // static 초기화
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null)  { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e2) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }



    /**
    관련사이트 정보 조회
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList SelectView(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls          = null;
        ArrayList list = null;
        String  sql          = "";
        DataBox dbox        = null;

        String  v_grcode     = box.getString ("p_grcode");
        String  v_seq		= box.getString ("p_seq");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = "select grcode		,";
            sql+= "       seq			,";
            sql+= "       sort			,";
			sql+= "       sitenm		,";
            sql+= "       url			,";
            sql+= "       banner		,";
            sql+= "       isuse			,";
            sql+= "       islogin		,";
			sql+= "       useridparam    ,";
			sql+= "       nameparam      ,";
			sql+= "       resnoparam     ,";
			sql+= "       conoparam      ,";
			sql+= "       pwdparam       ,";
			sql+= "       deptnmparam    ,";
			sql+= "       jikwiparam     ,";
			sql+= "       jikwinmparam   ,";
			sql+= "       compparam      ,";
			sql+= "       companynmparam ,";
			sql+= "       subjparam      ,";
			sql+= "       subjseqparam   ,";
			sql+= "       gadminparam    ,";
			sql+= "       param1         ,";
			sql+= "       paramvalue1    ,";
			sql+= "       param2         ,";
			sql+= "       paramvalue2    ,";
			sql+= "       param3         ,";
			sql+= "       paramvalue3    ,";
			sql+= "       param4         ,";
			sql+= "       paramvalue4    ,";
			sql+= "       param5         ,";
			sql+= "       paramvalue5    ,";
			sql+= "       param6         ,";
			sql+= "       paramvalue6    ";
            sql+= "  from TZ_LINKSITE ";
            sql+= "  where grcode = '" + v_grcode + "'";
            sql+= "    and seq  = '" + v_seq + "'";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }



   /**
    관련사이트 등록
    @param box      receive from the form object and session
    @return int		isOk
    */
     public int insert(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
        ListSet ls = null;
        ArrayList list = null;
        String  sql			= "", sql1 = "" , sql2 = "";
        int isOk            = 0;
		int v_seq = 0, v_sort =0;

        String v_userid = box.getSession("userid");

		String v_grcode			= box.getStringDefault ("p_grcode", box.getSession("tem_grcode"));
		String v_sitenm			= box.getString ("p_sitenm");
		String v_url			= box.getString ("p_url");
		String v_banner			= box.getNewFileName("p_banner");
		String v_isuse			= box.getString ("p_isuse");
		String v_islogin		= box.getString ("p_islogin");
		String v_useridparam	= box.getString ("p_useridparam");
		String v_nameparam		= box.getString ("p_nameparam");
		String v_resnoparam		= box.getString ("p_resnoparam");
		String v_param1			= box.getString ("p_param1");
		String v_paramvalue1	= box.getString ("p_paramvalue1");
		String v_param2			= box.getString ("p_param2");
		String v_paramvalue2	= box.getString ("p_paramvalue2");
		String v_param3			= box.getString ("p_param3");
		String v_paramvalue3	= box.getString ("p_paramvalue3");
		String v_param4			= box.getString ("p_param4");
		String v_paramvalue4	= box.getString ("p_paramvalue4");
		String v_param5			= box.getString ("p_param5");
		String v_paramvalue5	= box.getString ("p_paramvalue5");
		String v_param6			= box.getString ("p_param6");
		String v_paramvalue6	= box.getString ("p_paramvalue6");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
			
			sql1 = " select NVL(max(seq),1) seq from TZ_LINKSITE ";
			   ls = connMgr.executeQuery(sql1);
			   if (ls.next()) {
				   v_seq = ls.getInt(1) + 1;
			   }
			
			sql1 = " select NVL(max(sort),1) sort from TZ_LINKSITE where grcode = "+StringManager.makeSQL(v_grcode);
			   ls = connMgr.executeQuery(sql1);
			   if (ls.next()) {
				   v_sort = ls.getInt(1) + 1;
			   }

			sql  = " insert into TZ_LINKSITE(grcode, seq, sort, sitenm, url, banner, isuse, islogin,"; // 8
			sql += "useridparam, nameparam, resnoparam, "; //3
			sql += "param1, paramvalue1, param2, paramvalue2, param3, paramvalue3, param4, paramvalue4,"; //8
			sql += "param5, paramvalue5, param6, paramvalue6, luserid, ldate) "; //6

			sql += " values (?, ?, ?,"; // 3
			sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "; // 10
			sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))"; //12

			pstmt = connMgr.prepareStatement(sql);

			pstmt.setString (1,   v_grcode);
			pstmt.setInt	(2,   v_seq);
			pstmt.setInt 	(3,   v_sort);
			pstmt.setString (4,   v_sitenm);
			pstmt.setString (5,   v_url);
			pstmt.setString (6,   v_banner);
			pstmt.setString (7,   v_isuse);
			pstmt.setString (8,   v_islogin);
			pstmt.setString (9,   v_useridparam);
			pstmt.setString (10,  v_nameparam);
			pstmt.setString (11,  v_resnoparam);
			pstmt.setString (12,  v_param1);
			pstmt.setString (13,  v_paramvalue1);
			pstmt.setString (14,  v_param2);
			pstmt.setString (15,  v_paramvalue2);
			pstmt.setString (16,  v_param3);
			pstmt.setString (17,  v_paramvalue3);
			pstmt.setString (18,  v_param4);
			pstmt.setString (19,  v_paramvalue4);
			pstmt.setString (20,  v_param5);
			pstmt.setString (21,  v_paramvalue5);
			pstmt.setString (22,  v_param6);
			pstmt.setString (23,  v_paramvalue6);
			pstmt.setString (24,  v_userid);

			isOk = pstmt.executeUpdate();
			
			if(isOk > 0 ) {
				connMgr.commit();
				this.resetLinkSiteList(v_grcode);  // static 초기화
				isOk = 1;
			} else {
				connMgr.rollback();
				isOk = 0;
			}

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null)  { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


   /**
    관련사이트 수정
    @param box      receive from the form object and session
    @return int		isOk
    */
     public int update(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        String  sql			= "";
        int isOk			= 0;

        String  v_userid = box.getSession("userid");

        String v_grcode			= box.getString ("p_grcode");
        int v_seq				= box.getInt    ("p_seq");
        String v_sitenm			= box.getString ("p_sitenm");
        String v_url			= box.getString ("p_url");
		String v_banner			= box.getNewFileName("p_banner");
		String v_banner1		= box.getString ("p_banner1");
        String v_isuse			= box.getString ("p_isuse");
        String v_islogin		= box.getString ("p_islogin");
		String v_useridparam	= box.getString ("p_useridparam");
		String v_nameparam		= box.getString ("p_nameparam");
		String v_resnoparam		= box.getString ("p_resnoparam");
		String v_param1			= box.getString ("p_param1");
		String v_paramvalue1	= box.getString ("p_paramvalue1");
		String v_param2			= box.getString ("p_param2");
		String v_paramvalue2	= box.getString ("p_paramvalue2");
		String v_param3			= box.getString ("p_param3");
		String v_paramvalue3	= box.getString ("p_paramvalue3");
		String v_param4			= box.getString ("p_param4");
		String v_paramvalue4	= box.getString ("p_paramvalue4");
		String v_param5			= box.getString ("p_param5");
		String v_paramvalue5	= box.getString ("p_paramvalue5");
		String v_param6			= box.getString ("p_param6");
		String v_paramvalue6	= box.getString ("p_paramvalue6");

		if(v_banner.equals("")) v_banner = v_banner1;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

			sql  = "update TZ_LINKSITE set";
			sql += " sitenm			 = ?, ";
			sql += " url			 = ?, "; 
			sql += " banner			 = ?, "; 
			sql += " isuse			 = ?, "; 
			sql += " islogin		 = ?, "; 
			sql += " luserid		 = ?, ";
			sql += " ldate			 = to_char(sysdate, 'YYYYMMDDHH24MISS'), ";
			sql += " useridparam     = ?, ";
			sql += " nameparam       = ?, ";
			sql += " resnoparam      = ?, ";
			sql += " param1          = ?, ";
			sql += " paramvalue1     = ?, ";
			sql += " param2          = ?, ";
			sql += " paramvalue2     = ?, ";
			sql += " param3          = ?, ";
			sql += " paramvalue3     = ?, ";
			sql += " param4          = ?, ";
			sql += " paramvalue4     = ?, ";
			sql += " param5          = ?, ";
			sql += " paramvalue5     = ?, ";
			sql += " param6          = ?, ";
			sql += " paramvalue6     = ?  ";
			sql += "where grcode = ? and seq = ?";

			pstmt = connMgr.prepareStatement(sql);

			pstmt.setString (1,  v_sitenm);
			pstmt.setString (2,  v_url);
			pstmt.setString (3,  v_banner);
			pstmt.setString (4,  v_isuse);
            pstmt.setString (5,  v_islogin);
			pstmt.setString (6,  v_userid);
			pstmt.setString (7,  v_useridparam);
			pstmt.setString (8,  v_nameparam);
			pstmt.setString (9,  v_resnoparam);
			pstmt.setString (10,  v_param1);
			pstmt.setString (11,  v_paramvalue1);
			pstmt.setString (12,  v_param2);
			pstmt.setString (13,  v_paramvalue2);
			pstmt.setString (14,  v_param3);
			pstmt.setString (15,  v_paramvalue3);
			pstmt.setString (16,  v_param4);
			pstmt.setString (17,  v_paramvalue4);
			pstmt.setString (18,  v_param5);
			pstmt.setString (19,  v_paramvalue5);
			pstmt.setString (20,  v_param6);
			pstmt.setString (21,  v_paramvalue6);
			pstmt.setString (22,  v_grcode);
			pstmt.setInt 	(23,  v_seq);

			isOk = pstmt.executeUpdate();
			
			if(isOk > 0 ) {
				connMgr.commit();
				this.resetLinkSiteList(v_grcode);  // static 초기화
				isOk = 1;
			} else {
				connMgr.rollback();
				isOk = 0;
			}

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null)  { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


   /**
    관련사이트 삭제
    @param box      receive from the form object and session
    @return int		isOk
    */
     public int delete(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ListSet ls = null;
        ArrayList list = null;
        String  sql			= "";
        String  sql1			= "";
        int isOk            = 0;
        int isOk1           = 0;

        int v_seq			= box.getInt("p_seq");
        int v_sort			= box.getInt("p_sort");
        String v_grcode     = box.getString("p_grcode");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

			sql  = " delete from TZ_LINKSITE ";
			sql += " where seq = ?";

			pstmt = connMgr.prepareStatement(sql);

			pstmt.setInt(1,  v_seq);

			isOk = pstmt.executeUpdate();

			sql1  = " update TZ_LINKSITE set sort=sort-1 ";
			sql1 += " where sort > "+v_sort;

			pstmt2 = connMgr.prepareStatement(sql1);
			isOk1 = pstmt2.executeUpdate();

			if(isOk*isOk1 > 0)	{
				connMgr.commit();
				this.resetLinkSiteList(v_grcode);  // static 초기화
				isOk = 1;
			}
			else		{
				connMgr.rollback();
				isOk = 0;
			}
			
        }
        catch (Exception ex) {connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e2) {} }
            if(pstmt != null)  { try { pstmt.close(); } catch (Exception e1) {} }
            if(pstmt2 != null)  { try { pstmt2.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk*isOk1;
    }


}
