//**********************************************************
//  1. 제      목: Poll 관리
//  2. 프로그램명 : PollBean.java
//  3. 개      요: Poll 관리(사용자)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7.  2
//  7. 수      정:
//**********************************************************

package com.credu.homepage;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.homepage.*;

public class PollBean {

    public PollBean() {}

  /**
   * 투표여부 구하기
   */
   public int getPollVoteCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;
        int v_seq = box.getInt("p_seq");
        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql  = " select count(*) cnt from TZ_POLLLOG  ";
            sql += " where seq = " + v_seq;
            sql += "   and userid = " + StringManager.makeSQL(s_userid);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result  = ls.getInt("cnt");
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
        return result;
    }


  /**
   * 진행중인 설문 SEQ 구하기
   */
   public int getPollSeq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;
        String today = FormatDate.getDate("yyyyMMdd");
        String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));        
 
        try {
            connMgr = new DBConnectionManager();

            sql  = " select seq from TZ_POLL  ";
            sql += " where f_use = 'Y'        ";
            sql += "  and grcode = '"+tem_grcode+"'";
            sql += "  and started <= " + StringManager.makeSQL(today);
            sql += "  and ended   >= " + StringManager.makeSQL(today);
            sql += " order by seq desc        ";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result  = ls.getInt("seq");
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
        return result;
    }


  /**
   * Poll화면 상세보기
   */
   public PollData selectPoll(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        PollData data = null;

        int v_seq = box.getInt("p_seq");
 
        try {
            connMgr = new DBConnectionManager();

            sql  = " select a.seq seq, a.title title, a.started  started, a.ended ended,      ";
            sql += "        a.f_use f_use, a.luserid luserid, a.ldate ldate,                   ";
            sql += "        NVL((select sum(cnt) from TZ_POllSEL where seq = a.seq),0) total  ";
            sql += "   from TZ_POLL a                                                         ";
            sql += "  where a.seq  = " + v_seq;
            System.out.println(sql);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data=new PollData();
                data.setSeq(ls.getInt("seq"));
                data.setTitle(ls.getString("title"));
                data.setStarted(ls.getString("started"));
                data.setEnded(ls.getString("ended"));
                data.setF_use(ls.getString("f_use"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
                data.setTotal(ls.getInt("total"));
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
   * Poll화면 상세보기 (지문 및 결과)
   */
   public ArrayList selectPollSel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
		DataBox dbox = null;

        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql  = " select a.seq seq, a.selnum selnum, a.seltxt seltxt, a.cnt cnt, (select sum(cnt) from TZ_POllSEL where seq = a.seq) total, " ;
            sql += "   (select max(cnt) from TZ_POllSEL where seq = a.seq) maxcnt  ";
            sql += "   from tz_pollsel a               ";
            sql += "  where a.seq = " + v_seq;
            sql += " order by a.seq asc                 ";

            ls = connMgr.executeQuery(sql);	

            while (ls.next()) {
            	dbox = ls.getDataBox();
	            list.add(dbox);
//                data = new PollSelData();
//                data.setSeq(ls.getInt("seq"));
//                data.setSelnum(ls.getInt("selnum"));
//                data.setSeltxt(ls.getString("seltxt"));
//                data.setCnt(ls.getInt("cnt"));
//				  data.setCnt(ls.getInt("total"));
//				  data.setCnt(ls.getInt("maxcnt"));
//                list.add(data);
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
        return list;
    }


    /**
    * Poll 등록할때
    */
    public int insertPollResult(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";


        int isOk1 = 0;
        int isOk2 = 0;

        int v_seq    = box.getInt("p_seq");
        int v_selnum = box.getInt("p_selnum");

        String s_userid = box.getSession("userid");
        String v_ip    = box.getString("p_ip");
//		String v_comp  = "";
//        String v_jikup = "";
//        String v_jikun = "";


        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

		   System.out.println("1222222 v_selnum "+v_selnum +"// "+v_seq);
           // comp, jikup, jikun
//           sql1  = " select comp from TZ_MEMBER  ";
//           sql1 += "  where userid = " + StringManager.makeSQL(s_userid); 
//           ls = connMgr.executeQuery(sql1);
//           if (ls.next()) {
//               v_comp  = ls.getString("comp");
//           }
//           ls.close();

           // 투표로그 등록
           sql2 =  "insert into TZ_POLLLOG(seq, ip, userid, tmp1, tmp2, tmp3, selnum, indate)    ";
           sql2 += "            values (?, ?, ?, ? , ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            pstmt1 = connMgr.prepareStatement(sql2);

            pstmt1.setInt(1, v_seq);
            pstmt1.setString(2, v_ip);
            pstmt1.setString(3, s_userid);
            pstmt1.setString(4, "");
            pstmt1.setString(5, "");
            pstmt1.setString(6, "");
            pstmt1.setInt(7, v_selnum);

           isOk1 = pstmt1.executeUpdate();

           /* ======================== 지문 결과 등록 =========================*/
           sql3  = " update TZ_POLLSEL set cnt = cnt + 1   ";
           sql3 += "  where seq = ? and selnum = ?         ";

           pstmt2 = connMgr.prepareStatement(sql3);

           pstmt2.setInt(1, v_seq);
           pstmt2.setInt(2, v_selnum);
           isOk2 = pstmt2.executeUpdate();


            if(isOk1 > 0 && isOk2 > 0) connMgr.commit();
            else connMgr.rollback();
        }
        catch (Exception ex) {connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1 * isOk2;
    }

}
