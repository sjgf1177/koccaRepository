//**********************************************************
//1. 제      목:
//2. 프로그램명: SulmunSubjectUserBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-18
//7. 수      정:
//
//**********************************************************

package com.credu.research;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunSubjectUserBean {


    public SulmunSubjectUserBean() {}


    public int InsertSulmunUserResult(RequestBox box) throws Exception {
  
        DBConnectionManager connMgr = null;        
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        int isOk1 = 0;

		String v_grcode     = box.getString("p_grcode");
        String v_subj       = box.getString("p_subj");
        String v_gyear       = box.getString("p_gyear");
        String v_subjseq    = box.getString("p_subjseq");
        int    v_sulpapernum= box.getInt("p_sulpapernum");
        String v_userid     = box.getString("p_userid");
        String v_sulnums    = box.getString("p_sulnums");
		String v_answers    = box.getString("p_answers");
        String v_luserid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

			//설문기간 확인
            isOk = getSulmunGigan(box);

			if (isOk == 2) {

            sql1 = "select userid from TZ_SULEACH";
            sql1+=  " where subj = ? and grcode = ? and year = ? and subjseq = ? and sulpapernum = ?  and  userid = ? "; 
            pstmt1 = connMgr.prepareStatement(sql1);   
            
            pstmt1.setString( 1, v_subj);      
            pstmt1.setString( 2, v_grcode);       
            pstmt1.setString( 3, v_gyear);    
            pstmt1.setString( 4, v_subjseq);
            pstmt1.setInt( 5, v_sulpapernum);
            pstmt1.setString( 6, v_userid);

        
                try {
                    rs = pstmt1.executeQuery();
                    
                    if(!rs.next()) {     //     과거에 등록된 userid 를 확인하고 없을 경우에만 등록          
                       
                        isOk1 = InsertTZ_suleach(connMgr, v_subj , v_grcode, v_gyear,    v_subjseq, v_sulpapernum, v_userid, v_sulnums, v_answers, v_luserid);
                    } 

                }catch(Exception e) {}
                finally { if (rs != null) { try { rs.close(); } catch (Exception e) {} }}           

			}

       }
        catch(Exception ex) {
            isOk1 = 0;
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (isOk1 > 0) {connMgr.commit();}
            if (pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }      
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return isOk*isOk1;
    }

    public int getSulmunGigan(RequestBox box) throws Exception {

		SulmunSubjectPaperBean bean = null;
		DataBox  dbox0 = null; 

        String v_sulstart = "";
        String v_sulend = "";
		int v_update = 0;

        try {
			bean = new SulmunSubjectPaperBean();
			dbox0 = bean.getPaperData(box);

            v_sulstart = FormatDate.getFormatDate(dbox0.getString("d_sulstart"),"yyyy-MM-dd");
            v_sulend = FormatDate.getFormatDate(dbox0.getString("d_sulend"),"yyyy-MM-dd");
		    
			if(dbox0.getInt("d_sulpapernum") > 0){
          
		      long v_fstart = Long.parseLong(dbox0.getString("d_sulstart"));
              long v_fend = Long.parseLong(dbox0.getString("d_sulend"));
              
                 java.util.Date d_now = new java.util.Date();
                 String d_year = String.valueOf(d_now.getYear()+1900);
				 String d_month = String.valueOf(d_now.getMonth()+1);
				 String d_day = String.valueOf(d_now.getDate());

                 if(d_month.length() == 1){
				        d_month = "0" + d_month; 
				 }
				 if (d_day.length() == 1){
				        d_day = "0" + d_day; 				 
				 }
		         long v_now = Long.parseLong(d_year+d_month+d_day); 

		         if (v_fstart > v_now){
					v_update = 1;                                         //설문 전
		         } else if (v_now > v_fend){
					v_update = 3;                                         //설문 완료
		         } else if (v_fstart <= v_now && v_now < v_fend){
					v_update = 2;                                          //설문 중
		         }
			}
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return v_update;

	}

    public int InsertTZ_suleach(DBConnectionManager connMgr, String p_subj,  String p_grcode,  String p_gyear,    String p_subjseq, int p_sulpapernum,
                            String p_userid, String  p_sulnums, String p_answers, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SULEACH table
            sql =  "insert into TZ_SULEACH ";
            sql+=  " (subj,  grcode,   year,    subjseq, sulpapernum, ";
            sql+=  "  userid,  sulnums, answers,  ";
            sql+=  "  luserid, ldate) ";
            sql+=  " values ";
            sql+=  " (?,       ?,       ?,       ?,      ?, ";
            sql+=  "  ?,       ?,       ?, ";
            sql+=  "  ?,       ?) ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString( 1, p_subj);
            pstmt.setString( 2, p_grcode);
			pstmt.setString( 3, p_gyear);
            pstmt.setString( 4, p_subjseq);
            pstmt.setInt   ( 5, p_sulpapernum);
            pstmt.setString( 6, p_userid);
            pstmt.setString( 7, p_sulnums);
            pstmt.setString( 8, p_answers);
            pstmt.setString( 9, p_luserid);
            pstmt.setString(10, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();

       }
       catch(Exception ex) {
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
       }
       finally {
           if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
       }
       return isOk;
    }

   /**
    온라인테스트 대상자 리스트
    @param box          receive from the form object and session
    @return ArrayList   온라인테스트 대상자
    */
    public DataBox SelectUserPaperResult(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        

        String v_grcode    = box.getString("p_grcode");
        String v_subj      = box.getStringDefault("p_subj", "TARGET");
        java.util.Date d_now = new java.util.Date();
        String v_gyear    = box.getStringDefault("p_gyear", String.valueOf(d_now.getYear()+1900));
        String v_subjseq   = box.getStringDefault("p_subjseq","0001");
        int    v_sulpapernum = box.getInt("p_sulpapernum");
        String v_userid  = box.getString("p_userid");		
        try {      
                connMgr = new DBConnectionManager();
                
			sql = "select sulnums, answers    ";
            sql+= "  from tz_suleach ";
            sql+= " where grcode = " + SQLString.Format(v_grcode);
            sql+= "   and subj   = " + SQLString.Format(v_subj);
            sql+= "   and year   = " + SQLString.Format(v_gyear);
            sql+= "   and subjseq   = " + SQLString.Format(v_subjseq);
            sql+= "   and sulpapernum   = " + SQLString.Format(v_sulpapernum);
            sql+= "   and userid   = " + SQLString.Format(v_userid);
			
                ls = connMgr.executeQuery(sql);
                
                while (ls.next()) {
                    dbox = ls.getDataBox();
                }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }

    /**
    온라인테스트 대상자 리스트
    @param box          receive from the form object and session
    @return ArrayList   온라인테스트 대상자
    */
    public DataBox selectSulmunUser(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        

        String v_userid  = box.getString("p_userid");		
        try {      
                connMgr = new DBConnectionManager();
                
                sql+= "select       b.comp  asgn,  get_compnm(b.comp,2,4)       asgnnm, ";
                sql+= "	   	  b.jikup,       get_jikupnm(b.jikup, b.comp) jikupnm, ";
                sql+= "	   	  b.cono,     b.name ";
                sql+= "  from tz_member   b ";
                sql+= "   where b.userid    = " + SQLString.Format(v_userid);
     
                ls = connMgr.executeQuery(sql);
                
                while (ls.next()) {
                    dbox = ls.getDataBox();
                }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }

    public String getGrcode(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq)  throws Exception {
        String v_grcode = "";
        ListSet ls = null;
        String sql  = "";
        try {
            sql = "select grcode ";
            sql+= "  from tz_subjseq  ";
            sql+= " where subj    = " + SQLString.Format(p_subj);
            sql+= "   and year    = " + SQLString.Format(p_year);
            sql+= "   and subjseq = " + SQLString.Format(p_subjseq);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_grcode = ls.getString("grcode");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_grcode;
    }

    /**
    사용자 해당과정리스트
    @param box          receive from the form object and session
    @return ArrayList   해당과정리스트
    */
    public ArrayList SelectUserList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql = "";
                        
        try {
        String s_userid     = box.getSession("userid");
        String v_subj     = box.getStringDefault("s_subjcourse", SulmunSubjectBean.DEFAULT_SUBJ);
        String v_grcode = box.getStringDefault("s_grcode", SulmunSubjectBean.DEFAULT_GRCODE);

			connMgr = new DBConnectionManager();
            
            list = new ArrayList();

			sql = "select a.grcode,       a.subj,   a.subjseq,      ";
            sql+= "       a.sulpapernum,  a.sulpapernm, a.year, ";
            sql+= "       a.totcnt,       a.sulnums, a.sulmailing, a.sulstart, a.sulend, ";
            sql+= "       'COMMON'      subjnm ";
            sql+= "  from tz_sulpaper a ";
			sql+= "   where a.grcode = " + SQLString.Format(v_grcode);
            sql+= "   and a.subj   = " + SQLString.Format(v_subj);

            sql += "  and substr(a.sulstart,1,8) <= to_char(sysdate, 'yyyyMMdd')         ";
            sql += "  and substr(a.sulend,1,8) > to_char(sysdate, 'yyyyMMdd')         ";
            sql += "  and 0 = ( select count(userid) from tz_suleach where grcode =  " + SQLString.Format(v_grcode);
            sql+= "   and subj   = " + SQLString.Format(v_subj);
            sql+= "   and userid   = " + SQLString.Format(s_userid)  + " ) ";

            sql+= " order by a.subj, a.sulpapernum ";
System.out.println(sql);
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                dbox = ls.getDataBox();
                
                list.add(dbox);
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
}