//**********************************************************
//1. 제      목: 교재 판매 마스터
//2. 프로그램명: BookBean.java
//3. 개      요: 교재  판매  마스터
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 정상진 2005. 02. 09
//7. 수      정:
//**********************************************************

package com.credu.book;

import java.sql.*;
import java.util.*;

import com.credu.library.*;
import com.credu.propose.ProposeBean;

public class BookSellBean {

	/**
	 * 교재판매 리스트화면 select
	 * @param box        receive from the form object and session
	 * @return ArrayList 교재판매 리스트
	 * @throws Exception
	 */
	public ArrayList selectBookSellList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		Connection conn = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;

	    String ss_startday  = box.getStringDefault("s_startday", FormatDate.getDateAdd("yyyyMMdd", "month", -1));
	    String ss_endday    = box.getStringDefault("s_endday",   FormatDate.getDate("yyyyMMdd"));	
        String ss_paystat   = box.getString("s_paystat");
        String ss_bookcode  = box.getString("s_bookcode");
		String ss_action    = box.getString("s_action");

		try {
            if(ss_action.equals("go")){
				connMgr = new DBConnectionManager();
				list = new ArrayList();
				  
				sql = " select a.seq, a.userid, get_name(a.userid) name, a.bookcode, b.bookname, "; 
				sql+= "        a.dis_price, a.indate, a.accountname, a.realpaymoney, a.paydate,      ";
				sql+= "        a.paystat, a.receive, a.phone,a.post1, a.post2, a.addr1, a.addr2,     ";
				sql+= "        a.luserid, a.ldate                                                    ";
	            sql+= "   from TZ_BOOKSELL a, TZ_BOOK b                                              ";
	            sql+= "  where a.bookcode = b.bookcode                                               ";
				sql+= "    and a.iscancel = 'N'                                                      ";				
				sql+= "    and a.indate between "+SQLString.Format(ss_startday)+" and "+SQLString.Format(ss_endday); ;
				
	            if (!ss_paystat.equals("ALL"))  sql+= " and a.paystat  = "+SQLString.Format(ss_paystat);
	            if (!ss_bookcode.equals("ALL")) sql+= " and a.bookcode = " + StringManager.makeSQL(ss_bookcode);

				sql+= " order by a.indate desc                                                      ";			
//   			System.out.println("selectBookList 리스트화면 : "+sql);
	
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
			if (ls != null) { try {ls.close();} catch (Exception e) {}}
			if (connMgr != null) {try {	connMgr.freeConnection();} catch (Exception e10) {}}
		}
		return list;
	}

   /**
    상태 변경 처리
    @param box      receive from the form object and session
    @return int     결과값(0,1)
    **/
    public int updateBookSell(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt=null;
        int isOk =1, isOk_check=0;
		
        String sql  = "";
		
        Vector vec_param   = box.getVector("p_params");
        Vector vec_paystat = box.getVector("p_paystat");

        int    v_seq       = 0;
		String v_paystat   = "";
        String v_luserid   = box.getSession("userid");     // 세션변수에서 사용자 id를 가져온다.
		
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
			
            sql  = " update tz_booksell set paystat = ? ,luserid  =?                          ";
			sql += "                       ,ldate = to_char(sysdate,'YYYYMMDDHH24MISS') ";			
            sql += "  where seq = ?                                                           ";
               
            pstmt = connMgr.prepareStatement(sql);
			
            for(int i=0; i < vec_param.size(); i++)	{
                v_seq     = Integer.parseInt(vec_param.elementAt(i).toString());
				v_paystat = vec_paystat.elementAt(i).toString();
				
                pstmt.setString(1, v_paystat);
                pstmt.setString(2, v_luserid);				
                pstmt.setInt   (3, v_seq);
				isOk_check = pstmt.executeUpdate();	
				if (isOk_check == 0) isOk = 0;
			}
			
            if (isOk > 0) {connMgr.commit();}
            else          {connMgr.rollback();}
        }catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }			
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }	
	

	/**
	 * 교재판매 리스트화면 select
	 * @param box        receive from the form object and session
	 * @return ArrayList 교재판매 리스트
	 * @throws Exception
	 */
	public ArrayList selectCancelList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		Connection conn = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;
		
	    String ss_startday   = box.getStringDefault("s_startday", FormatDate.getDateAdd("yyyyMMdd", "month", -1));
	    String ss_endday     = box.getStringDefault("s_endday",   FormatDate.getDate("yyyyMMdd"));	
        String ss_refundstat = box.getString("s_refundstat");
        String ss_bookcode   = box.getString("s_bookcode");
		String ss_action     = box.getString("s_action");

		try {
            if(ss_action.equals("go")){
				connMgr = new DBConnectionManager();
				list = new ArrayList();
				  
				sql = " select a.seq, a.userid, get_name(a.userid) name, a.bookcode, b.bookname, "; 
				sql+= "        a.dis_price, a.indate, a.accountname, a.realpaymoney, a.paydate,      ";
				sql+= "        a.refundregdate, a.refundbank, a.refundaccount, a.refundmoney,        ";
				sql+= "        a.refunddate, a.refundstat, a.luserid, a.ldate                        ";
	            sql+= "   from TZ_BOOKSELL a, TZ_BOOK b                                              ";
	            sql+= "  where a.bookcode = b.bookcode                                               ";
				sql+= "    and a.iscancel = 'Y'                                                      ";				
				sql+= "    and a.refundregdate between "+SQLString.Format(ss_startday)+" and "+SQLString.Format(ss_endday); ;
				
	            if (!ss_refundstat.equals("ALL"))sql+= " and a.refundstat = "+SQLString.Format(ss_refundstat);
	            if (!ss_bookcode.equals("ALL"))  sql+= " and a.bookcode   = " + StringManager.makeSQL(ss_bookcode);

				sql+= " order by a.refundregdate desc                                                      ";			
//   			System.out.println("selectBookList 리스트화면 : "+sql);
	
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
			if (ls != null) { try {ls.close();} catch (Exception e) {}}
			if (connMgr != null) {try {	connMgr.freeConnection();} catch (Exception e10) {}}
		}
		return list;
	}
	
	 /**
    환불 처리
    @param box      receive from the form object and session
    @return int     결과값(0,1)
    **/
    public int updateBookCancel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt=null;
        int isOk =1, isOk_check=0;
		
        String sql  = "";
		
        Vector vec_param       = box.getVector("p_params");
        Vector vec_refunddate  = box.getVector("p_refunddate");    //환불일자
        Vector vec_refundmoney = box.getVector("p_refundmoney");   //환불액		
        Vector vec_refundstat  = box.getVector("p_refundstat");    // 상태 ( 환불대기, 환불완료 )


        int    v_seq         = 0;
		String v_refunddate  = "";
		int    v_refundmoney = 0;		
		String v_refundstat  = "";
        String v_luserid     = box.getSession("userid");     // 세션변수에서 사용자 id를 가져온다.
		
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
			
            sql  = " update tz_booksell set refunddate=?, refundmoney=?, refundstat=?,luserid=? ";
			sql += "                       ,ldate = to_char(sysdate,'YYYYMMDDHH24MISS')   ";			
            sql += "  where seq = ?                                                             ";
                
            pstmt = connMgr.prepareStatement(sql);
			
            for(int i=0; i < vec_param.size(); i++)	{
                v_seq         = Integer.parseInt(vec_param.elementAt(i).toString());
				v_refunddate  = StringManager.replace(vec_refunddate.elementAt(i).toString(), "-", "");
				v_refundmoney = Integer.parseInt(vec_refundmoney.elementAt(i).toString());
				v_refundstat  = vec_refundstat.elementAt(i).toString();
				
                pstmt.setString(1, v_refunddate);				
                pstmt.setInt   (2, v_refundmoney);
                pstmt.setString(3, v_refundstat);
                pstmt.setString(4, v_luserid);				
                pstmt.setInt   (5, v_seq);
				isOk_check = pstmt.executeUpdate();	
				if (isOk_check == 0) isOk = 0;
			}
			
            if (isOk > 0) {connMgr.commit();}
            else          {connMgr.rollback();}
        }catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }			
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }	
	
	/* =====================================================================================*/	
   /**
    * 교재신청
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
     public int insertBookPropose(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1   = null;
        Statement stmt1 = null;
        PreparedStatement pstmt1  = null;
        String sql    = "";
        String sql1   = "";
        int isOk1     = 0;

		int     v_seq           = 0;
		String  s_userid        = box.getSession("userid");		
		String  v_bookcode      = box.getString("p_bookcode");
	    int     v_dis_price     = box.getInt   ("p_dis_price");
	    int     v_realpaymoney  = box.getInt   ("p_realpaymoney");
		String  v_accountname   = box.getString("p_accountname");
		String  v_paydate       = box.getString("p_paydate");
	    String  v_receive       = box.getString("p_receive");
	    String  v_post1         = box.getString("p_post1");
	    String  v_post2         = box.getString("p_post2");
	    String  v_addr1         = box.getString("p_addr1");
	    String  v_addr2         = box.getString("p_addr2");
	    String  v_phone         = box.getString("p_phone");
		
        try {
            connMgr = new DBConnectionManager();

            //---------------------- 교재 번호 가져온다 ----------------------------
            stmt1 = connMgr.createStatement();				
            sql = "select NVL(max(seq) + 1, 1) maxseq from TZ_BOOKSELL ";
            rs1 = stmt1.executeQuery(sql);
            if (rs1.next()) {
				v_seq = rs1.getInt("maxseq");
            }
            rs1.close();
            //-------------------------------------------------------------------------
	
            //----------------------   교재판매 table 에 입력  --------------------------
            sql1  = " insert into TZ_BOOKSELL(seq, userid, bookcode, dis_price, indate, accountname, realpaymoney, paydate,  ";
            sql1 += "                         paystat, receive, phone, post1, post2, addr1, addr2, iscancel, luserid, ldate) ";
            sql1 += "                 values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDD'), ?, ?, ?,               ";
			sql1 += "                         'A', ?, ?, ?, ?, ?, ?, 'N', ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))     ";
		
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt   ( 1, v_seq);				
            pstmt1.setString( 2, s_userid);
            pstmt1.setString( 3, v_bookcode);
            pstmt1.setInt   ( 4, v_dis_price);
            pstmt1.setString( 5, v_accountname);			
			pstmt1.setInt   ( 6, v_realpaymoney);
			pstmt1.setString( 7, v_paydate);			
			pstmt1.setString( 8, v_receive);
			pstmt1.setString( 9, v_phone);			
			pstmt1.setString(10, v_post1);
			pstmt1.setString(11, v_post2);
			pstmt1.setString(12, v_addr1);
			pstmt1.setString(13, v_addr2);			
            pstmt1.setString(14, s_userid);

            isOk1 = pstmt1.executeUpdate();
        
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(rs1 != null) { try { rs1.close(); } catch (Exception e) {} }
            if(stmt1 != null) { try { stmt1.close(); } catch (Exception e1) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
		return isOk1;
    }

    /**
    * 신청 환불 
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
	public int updateBookPropose(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1  = null;
        String sql    = "";
        String sql1   = "";
        int isOk1     = 0;

		String s_userid        = box.getSession("userid");
		int    v_seq           = box.getInt   ("p_seq");		
	    String v_refundbank    = box.getString("p_refundbank");      // 환불은행
	    String v_refundaccount = box.getString("p_refundaccount");   // 환불계좌
		
        try {
            connMgr = new DBConnectionManager();
	
            //----------------------   교재 table 에 입력  --------------------------
            sql1  = " update TZ_BOOKSELL ";
			sql1 += "    set refundregdate=to_char(sysdate, 'YYYYMMDD'),            ";
			sql1 +="         refundbank=?, refundaccount=?, refundstat='A', iscancel='Y', ";
 			sql1 += "        luserid=?, ldate=to_char(sysdate, 'YYYYMMDDHH24MISS')  ";
			sql1 += "  where  seq = ?                                                     ";
		
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_refundbank);
            pstmt1.setString(2, v_refundaccount);
            pstmt1.setString(3, s_userid);
            pstmt1.setInt   (4, v_seq);
			
            isOk1 = pstmt1.executeUpdate();
        
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
		return isOk1;
	}		
	 
	 
	/**
	* 선택된 신청인원 삭제
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/    	
	public int deleteBookPropose(RequestBox	box) throws	Exception {
		DBConnectionManager	connMgr	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "";
		int	isOk1 =	0;
		
		int v_seq = box.getInt("p_seq");
		
		try	{
			connMgr	= new DBConnectionManager();

			sql1 = "delete from	TZ_BOOKSELL where seq = ?  ";
//				sql1 = "update TZ_BOOKSELL set isCancel='Y' where bookcode = ? ";				
			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setInt(1, v_seq);
			isOk1 =	pstmt1.executeUpdate();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box,	sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}	
		
	 
	/**
	 * 교재판매 리스트화면 select
	 * @param box        receive from the form object and session
	 * @return ArrayList 교재판매 리스트
	 * @throws Exception
	 */
	public ArrayList selectUserSellList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		Connection conn = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;

		String s_userid     = box.getSession("userid");

		try {

			connMgr = new DBConnectionManager();
			list = new ArrayList();
			  
			sql = " select a.seq, a.userid, get_name(a.userid) name, a.bookcode, b.bookname, "; 
			sql+= "        a.dis_price, a.indate, a.accountname, a.realpaymoney, a.paydate,      ";
			sql+= "        a.paystat, a.receive, a.phone,a.post1, a.post2, a.addr1, a.addr2,     ";
			sql+= "        a.iscancel, a.refundstat, a.luserid, a.ldate                          ";
            sql+= "   from TZ_BOOKSELL a, TZ_BOOK b                                              ";
            sql+= "  where a.bookcode = b.bookcode                                               ";
// 취소된것도 환불완료 전까지는 나오게 한다.			
//			sql+= "    and a.iscancel = 'N'                                                      ";				
			sql+= "    and NVL(a.refundstat,'') <> 'B'                                        ";			
			sql+= "    and a.userid   = "+SQLString.Format(s_userid);
			sql+= " order by a.indate desc                                                      ";			

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
			}
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		} finally {
			if (ls != null) { try {ls.close();} catch (Exception e) {}}
			if (connMgr != null) {try {	connMgr.freeConnection();} catch (Exception e10) {}}
		}
		return list;
	}

}
