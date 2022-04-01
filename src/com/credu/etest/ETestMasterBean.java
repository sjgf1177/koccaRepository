
//**********************************************************
//1. 제      목: 온라인테스트 마스터 관리
//2. 프로그램명: ETestMasterBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh
//**********************************************************

package com.credu.etest;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;
import com.credu.etest.*;
import java.text.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ETestMasterBean {

    public ETestMasterBean() {}


    /**
    온라인테스트 마스터 리스트
    @param box          receive from the form object and session
    @return ArrayList   온라인테스트 마스터
    */
    public ArrayList selectETestMasterList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql  = "";
        String sql2 = "";
        ResultSet rs = null;
        ArrayList list = null;
        ArrayList blist = null;
        DataBox dbox = null;
        DataBox dbox2 = null;
        String v_etestsubj_bef = "";

        String ss_upperclass = box.getStringDefault("s_upperclass","ALL");
        String ss_etestsubj       = box.getString("s_etestsubj");
        String ss_gyear       = box.getString("s_gyear");
        String v_action      = box.getStringDefault("p_action","change");

        try {
            connMgr = new DBConnectionManager();
            
            blist = new ArrayList();

            if (v_action.equals("go")) {
                sql = "select b.etestsubj,      b.upperclass,  a.etestcode, a.year,  a.etesttext, a.startdt, a.enddt, a.etestlimit, a.totalscore, a.etestpoint,  a.etestcnt,  \n";
                sql+= "       a.level1text, a.level2text, a.level3text, a.cntlevel1, a.cntlevel2, a.cntlevel3, a.isopenanswer, a.isopenexp,  a.papercnt,  a.etesttime,   \n";
                sql+= "       b.etestsubjnm  \n";
                sql+= "  from tz_etestmaster a,  tz_etestsubj b          \n";
				sql+= " where  a.etestsubj    = b.etestsubj             \n";	// 기존 outer join을 없앰,  년도조회시 없는값이 출력됨.
                if (!ss_upperclass.equals("ALL"))
                    sql+= "   and b.upperclass = " + SQLString.Format(ss_upperclass);
                if (!ss_etestsubj.equals("ALL"))
                    sql+= "   and b.etestsubj       = " + SQLString.Format(ss_etestsubj);
                    sql+= "   and a.year       = " + SQLString.Format(ss_gyear);
                sql+= " order by b.etestsubj, a.year \n";

                ls = connMgr.executeQuery(sql);
				System.out.println("ETestMasterBean E-test 마스터 리스트:"+sql);
                
                sql2 = "select count(userid) from tz_etestmember where etestsubj = ? and etestcode = ? and year = ? ";
                pstmt = connMgr.prepareStatement(sql2);

                while (ls.next()) {    
                    dbox = ls.getDataBox();
                    
                    if (!v_etestsubj_bef.equals(ls.getString("etestsubj"))) {
                        list = new ArrayList();
                        dbox2 = new DataBox("responsebox");
                        dbox2.put("d_etestsubj", ls.getString("etestsubj"));
                        dbox2.put("d_etestsubjnm", ls.getString("etestsubjnm"));
                        dbox2.put("d_upperclass", ls.getString("upperclass"));
                    }                    

                    dbox.put("d_etestsubj", dbox2.getString("d_etestsubj"));
                    dbox.put("d_etestsubjnm", dbox2.getString("d_etestsubjnm"));
                    dbox.put("d_upperclass", dbox2.getString("d_upperclass"));
                    
                    
                    //--------------------------------------------------------------------------------------------
                    pstmt.setString(1, dbox.getString("d_etestsubj"));
                    pstmt.setString(2, dbox.getString("d_etestcode"));
                    pstmt.setString(3, dbox.getString("d_year"));
                    try{
                        rs = pstmt.executeQuery(); 
                        rs.next();
                        dbox.put("d_membercnt", new Integer(rs.getInt(1)));
                    }catch(Exception e) {
                    }finally {
                        if (rs != null) { try { rs.close(); } catch (Exception e) {} }
                    }
                    //---------------------------------------------------------------------------------------------
                    
                    list.add(dbox);

                    if (!v_etestsubj_bef.equals(dbox.getString("d_etestsubj"))) {
                        blist.add(list);
                        v_etestsubj_bef = dbox.getString("d_etestsubj");
                    }                               
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return blist;
    }


    /**
    온라인테스트 마스터 상세보기
    @param box                receive from the form object and session
    @return ETestMasterData  조회한 마스터정보
    */
    public DataBox selectETestMasterData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;

        String v_etestsubj    = box.getString("p_etestsubj");
        String v_gyear    = box.getString("p_gyear");
        String v_etestcode = box.getString("p_etestcode");

        try {
            connMgr = new DBConnectionManager();
            dbox = getETestMasterData(connMgr, v_etestsubj, v_etestcode, v_gyear);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }


    /**
    마스타 데이타 
    @param box          receive from the form object and session
    @return DataBox
    */
    public DataBox getETestMasterData(DBConnectionManager connMgr, String p_etestsubj, String p_etestcode, String p_gyear) throws Exception {
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = "select a.etestsubj,     a.etestcode, a.year,  a.etesttext, a.startdt, a.enddt, a.etestlimit, a.totalscore, a.etestpoint,  a.etestcnt,  ";
            sql+= "       a.level1text, a.level2text, a.level3text, a.cntlevel1, a.cntlevel2, a.cntlevel3, a.isopenanswer, a.isopenexp,  a.papercnt , a.etesttime ,a.retrynum   ";
            sql+= "  from tz_etestmaster a ";
            sql+= " where a.etestsubj    = " + SQLString.Format(p_etestsubj);
            sql+= "   and a.etestcode  = " + SQLString.Format(p_etestcode);
            sql+= "   and a.year  = " + SQLString.Format(p_gyear);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return dbox;
    }

    /**
    온라인테스트 마스터 등록
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int insertETestMaster(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
      
		String  v_etestsubj      = box.getString("p_etestsubj");
        String  v_etestcode      = "";
		String  v_gyear      = box.getString("p_gyear");

        String  v_etesttext      = box.getString("p_etesttext");
        String  v_startdt      = box.getString("p_startdt");
        String  v_enddt      = box.getString("p_enddt");
		int     v_etestlimit = box.getInt   ("p_etestlimit");
		int     v_totalscore = box.getInt   ("p_totalscore");
		int     v_etestpoint = box.getInt   ("p_etestpoint");
		int     v_etesttime = box.getInt   ("p_etesttime");
		int     v_etestcnt = box.getInt   ("p_etestcnt");
        String  v_level1text      = box.getString("p_level1text");
        String  v_level2text      = box.getString("p_level2text");
        String  v_level3text      = box.getString("p_level3text");
		int     v_cntlevel1 = box.getInt   ("p_cntlevel1");
        int     v_cntlevel2 = box.getInt   ("p_cntlevel2");
        int     v_cntlevel3 = box.getInt   ("p_cntlevel3");       
        String  v_isopenanswer      = box.getString("p_isopenanswer");
        String  v_isopenexp      = box.getString("p_isopenexp");
        int     v_retrynum  = box.getInt   ("p_retrynum");
        int     v_papercnt  = box.getInt   ("p_papercnt");

        String  v_luserid   = box.getSession("userid");
        String  v_month = "00";

        try {
            connMgr = new DBConnectionManager();

            if(v_startdt.length() >= 6) {
                v_month = v_startdt.substring(4, 6);
            }
            v_etestcode   = getMaxSubjseq(connMgr, v_etestsubj, v_gyear, v_month);

            //insert TZ_EXAM MASTER table
            sql =  " insert into tz_etestmaster (etestsubj, etestcode, year, etesttext, startdt, enddt, etestlimit, totalscore, etestpoint, etestcnt, level1text, level2text, level3text, cntlevel1, cntlevel2, cntlevel3, isopenanswer, isopenexp, papercnt, etesttime, luserid, ldate,retrynum  ) ";
            sql+= " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, v_etestsubj);
            pstmt.setString( 2, v_etestcode);
            pstmt.setString( 3, v_gyear);
            pstmt.setString( 4, v_etesttext);
            pstmt.setString( 5, v_startdt);
            pstmt.setString( 6, v_enddt);
            pstmt.setInt   ( 7, v_etestlimit);
            pstmt.setInt   ( 8, v_totalscore);
            pstmt.setInt   ( 9, v_etestpoint);
            pstmt.setInt   ( 10, v_etestcnt);
            pstmt.setString( 11, v_level1text);
            pstmt.setString( 12, v_level2text);
            pstmt.setString( 13, v_level3text);
            pstmt.setInt   ( 14, v_cntlevel1);
            pstmt.setInt   (15, v_cntlevel2);
            pstmt.setInt   (16, v_cntlevel3);
            pstmt.setString(17, v_isopenanswer);
            pstmt.setString(18, v_isopenexp);
            pstmt.setInt   (19, v_papercnt);
            pstmt.setInt   (20, v_etesttime);
            pstmt.setString(21, v_luserid);
            pstmt.setString(22, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setInt(23, v_retrynum);

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
    온라인테스트 수정
    @param box     receive from the form object and session
    @return isOk   1:update success,0:update fail
    */
    public int updateETestMaster(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

		String  v_etestsubj      = box.getString("p_etestsubj");
		String  v_etestcode      = box.getString("p_etestcode");
		String  v_gyear      = box.getString("p_gyear");

        String  v_etesttext      = box.getString("p_etesttext");
        String  v_startdt      = box.getString("p_startdt");
        String  v_enddt      = box.getString("p_enddt");
		int     v_etestlimit = box.getInt   ("p_etestlimit");
		int     v_totalscore = box.getInt   ("p_totalscore");
		int     v_etestpoint = box.getInt   ("p_etestpoint");
		int     v_etestcnt = box.getInt   ("p_etestcnt");
        String  v_level1text      = box.getString("p_level1text");
        String  v_level2text      = box.getString("p_level2text");
        String  v_level3text      = box.getString("p_level3text");
		int     v_cntlevel1 = box.getInt   ("p_cntlevel1");
        int     v_cntlevel2 = box.getInt   ("p_cntlevel2");
        int     v_cntlevel3 = box.getInt   ("p_cntlevel3");       
        String  v_isopenanswer      = box.getString("p_isopenanswer");
        String  v_isopenexp      = box.getString("p_isopenexp");
        int     v_retrynum  = box.getInt   ("p_retrynum");
        int     v_papercnt  = box.getInt   ("p_papercnt");
        int     v_etesttime  = box.getInt   ("p_etesttime");

        String  v_luserid   = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            //update tz_etestmaster table
            sql =  " update tz_etestmaster ";
            sql+=  "    set etesttext = ?, startdt = ?, enddt = ?, etestlimit = ?, totalscore = ?, etestpoint = ?, etestcnt = ?, level1text = ?, level2text = ?, level3text = ?, ";
            sql+=  "        cntlevel1  = ?,  cntlevel2  = ?,  cntlevel3 = ?,  isopenanswer = ?, isopenexp  = ?, papercnt = ?, etesttime = ?, ";
            sql+=  "        luserid   = ?,  ldate     = ?,retrynum =  ?  ";
            sql+=  "  where etestsubj = ? and etestcode = ? and year = ? ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, v_etesttext);
            pstmt.setString( 2, v_startdt);
            pstmt.setString( 3, v_enddt);
            pstmt.setInt   ( 4, v_etestlimit);
            pstmt.setInt   ( 5, v_totalscore);
            pstmt.setInt   ( 6, v_etestpoint);
            pstmt.setInt   ( 7, v_etestcnt);
            pstmt.setString( 8, v_level1text);
            pstmt.setString( 9, v_level2text);
            pstmt.setString( 10, v_level3text);
            pstmt.setInt   ( 11, v_cntlevel1);
            pstmt.setInt   ( 12, v_cntlevel2);
            pstmt.setInt   ( 13, v_cntlevel3);
            pstmt.setString( 14, v_isopenanswer);
            pstmt.setString( 15, v_isopenexp);
            pstmt.setInt   ( 16, v_papercnt);
            pstmt.setInt   ( 17, v_etesttime);
            pstmt.setString(18, v_luserid);
            pstmt.setString(19, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setInt   ( 20, v_retrynum);
            pstmt.setString(21, v_etestsubj);
            pstmt.setString(22, v_etestcode);
            pstmt.setString(23, v_gyear);

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
    온라인테스트 마스터 삭제
    @param box       receive from the form object and session
    @return isOk     1:delete success,0:update fail
    */
    public int deleteETestMaster(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        ListSet ls = null;   

         String  v_etestsubj      = box.getString("p_etestsubj");
         String  v_etestcode   = box.getString("p_etestcode");
		 String  v_gyear      = box.getString("p_gyear");
         String  v_duserid   = box.getSession("userid");

         try {
             connMgr = new DBConnectionManager();

            // 삭제체크
            sql = " select etestnum from tz_etestpaper where etestsubj='"+v_etestsubj+"' and etestcode='"+v_etestcode+"'  ";
            ls = connMgr.executeQuery(sql);
    		if (ls.next()) {
    		    isOk = -2;
    		}

            if(isOk==0){ 
                                
                sql =  " delete from tz_etestmaster ";
                sql+=  "  where etestsubj      = ?  ";
                sql+=  "    and etestcode    = ?  ";
                sql+=  "    and year     = ?  ";
    
                 pstmt = connMgr.prepareStatement(sql);
                 pstmt.setString(1, v_etestsubj);
                 pstmt.setString(2, v_etestcode);
                 pstmt.setString(3, v_gyear);
    
                 isOk = pstmt.executeUpdate();
             }
                 
             box.put("p_sulnum", String.valueOf("0"));

         }
         catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
         }
         finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }            
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }
         return isOk;
    }

    /**
    온라인테스트마스터 시퀀스
    @param connMgr         Connection Manager
    @param s_subj          과정코드
    @param p_gyear          년도
    @param p_month         월
    @return getExamnumSeq  문제번호
    */
    public String getMaxSubjseq(DBConnectionManager connMgr, String p_etestsubj, String p_gyear, String p_month) throws Exception {
        String v_etestsubjseqcode = "";
        String v_maxsubjseq = "";
        int    v_maxno   = 0;

        ListSet ls = null;
        String sql  = "";
        try {
            sql = " select max(etestcode) maxetestcode";
            sql+= "   from tz_etestmaster ";
            sql+= "  where etestsubj = "  + SQLString.Format(p_etestsubj);
            sql+= "    and year = "  + SQLString.Format(p_gyear);
            sql+= "    and etestcode like "  + SQLString.Format(p_month+"%");

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                 v_maxsubjseq = ls.getString("maxetestcode");
            }

            if (v_maxsubjseq.equals("")) {
                v_etestsubjseqcode = p_month + "01";
            } else {
                v_maxno = Integer.valueOf(v_maxsubjseq.substring(2,4)).intValue();
                v_etestsubjseqcode = p_month + new DecimalFormat("00").format(v_maxno+1);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_etestsubjseqcode;
    }
    
    /**
    온라인테스트 마스터 상중하 문제수 가져오기
    @param box                receive from the form object and session
    @return ETestMasterData  조회한 마스터정보
    */
    public ArrayList selectETestLevels(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList typelist = new ArrayList();
        String sql  = "";
        DataBox dbox = null;
		int v_etesttype = 0;
		int v_levels = 0;

        String v_etestsubj    = box.getString("p_etestsubj");

        try {
            connMgr = new DBConnectionManager();
				
                 for ( int j = 1; j <= 2; j++ ){
                     v_etesttype = j;
                     ArrayList levellist = new ArrayList();
                     for ( int k =1; k <= 3 ; k++){
                         v_levels = k;

						 sql = "select count(levels) levelscount  ";
                         sql+= " from tz_etest ";
                         sql+= "  where etestsubj  = " + SQLString.Format(v_etestsubj) ;
                         sql+= "  and etesttype = " + SQLString.Format(v_etesttype) ;
                         sql+= "  and levels  = " + SQLString.Format(v_levels) ;  

						 ls = connMgr.executeQuery(sql); 

			             while (ls.next()) {
                              dbox = ls.getDataBox();
                              levellist.add(dbox);
                         }
					 }
					 typelist.add(levellist);
                 }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return typelist;
    }
}