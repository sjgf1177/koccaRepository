//**********************************************************
//1. 제      목:
//2. 프로그램명: SulmunTargetUserBean.java
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
public class KSulmunTargetUserBean {


    public KSulmunTargetUserBean() {}


    /**
    대상자설문  결과 등록 
    @param box          receive from the form object and session
    @return int   
    */
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
        String v_userid     = box.getStringDefault("p_userid", box.getSession("userid"));
        String v_sulnums    = box.getString("p_sulnums");System.out.println("v_sulnums : " + v_sulnums);
		String v_answers    = box.getString("p_answers");System.out.println("v_answers : " + v_answers);
        String v_luserid    = box.getStringDefault("p_userid", box.getSession("userid"));

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////
//ErrorManager.systemOutPrintln(box);
			//설문기간 확인
            isOk = getSulmunGigan(box);System.out.println("isOk1 : " + isOk);

			if (isOk == 2) {

            sql1 = "select userid from TZ_SULEACH";
            sql1+=  " where subj = ? and grcode = ? and year = ? and subjseq = ? and sulpapernum = ?  and  userid = ? "; 
            pstmt1 = connMgr.prepareStatement(sql1);   
            
            pstmt1.setString( 1, v_subj);      System.out.println("v_subj : " + v_subj);
            pstmt1.setString( 2, v_grcode);       System.out.println("v_grcode : " + v_grcode);
            pstmt1.setString( 3, v_gyear);    System.out.println("v_gyear : " + v_gyear);
            pstmt1.setString( 4, v_subjseq);System.out.println("v_subjseq : " + v_subjseq);
            pstmt1.setInt( 5, v_sulpapernum);System.out.println("v_sulpapernum : " + v_sulpapernum);
            pstmt1.setString( 6, v_userid);System.out.println("v_userid : " + v_userid);

        
                try {
                    rs = pstmt1.executeQuery();
                    //System.out.println("aa");System.out.println("rs"+ rs.next());System.out.println("rs"+ rs.getString(1));
                    if(!rs.next()) {     //     과거에 등록된 userid 를 확인하고 없을 경우에만 등록          
                       System.out.println("bb");
                        isOk1 = InsertTZ_suleach(connMgr, v_subj , v_grcode, v_gyear,    v_subjseq, v_sulpapernum, v_userid, v_sulnums, v_answers, v_luserid);System.out.println("isOk2 : " + isOk1);
                        
                        updateTZ_sulmail(connMgr, v_subj , v_grcode, v_gyear,    v_subjseq, v_sulpapernum, v_userid, v_sulnums, v_answers, v_luserid);System.out.println("isOk3 : " + isOk1);
                    } 

                }catch(Exception e) {}
                finally { if (rs != null) { try { rs.close(); } catch (Exception e) {} }}           

			}
			if (isOk > 0 && isOk1 > 0){
						connMgr.commit();
					} else {
						connMgr.rollback();
					}

       }
        catch(Exception ex) {
            isOk1 = 0;
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }      
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return isOk*isOk1;
    }

    /**
    대상자설문  결과 등록 
    @param box          receive from the form object and session
    @return int   
    */
    public int InsertSulmunUserResult2(RequestBox box) throws Exception {
  
        DBConnectionManager connMgr = null;        
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        int isOk1 = 0;

        SulmunTargetPaperBean bean = null;
        ArrayList blist = null;

		String v_grcode     = box.getString("p_grcode");
        String v_subj       = box.getString("p_subj");
        String v_gyear       = box.getString("p_gyear");
        String v_subjseq    = box.getString("p_subjseq");
        int    v_sulpapernum= box.getInt("p_sulpapernum");
        String v_userid     = box.getString("p_userid");
        String v_sulnums    = box.getString("p_sulnums");
        String v_luserid    = box.getString("p_userid");

		String v_answers    = "";
		Vector v_checked = null;


        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

			bean = new SulmunTargetPaperBean();
			blist = bean.selectPaperQuestionExampleList(box);

			for (int i=0; i<blist.size(); i++) {
                ArrayList list = (ArrayList)blist.get(i);
                DataBox dbox = (DataBox)list.get(0);

                if (dbox.getString("d_sultype").equals(SulmunTargetBean.OBJECT_QUESTION)) {        // 단일선택 
                     v_answers += box.getString(String.valueOf(dbox.getInt("d_sulnum")));
					 v_answers += ",";
                }else if (dbox.getString("d_sultype").equals(SulmunTargetBean.MULTI_QUESTION)) {  // 복수선택
                     v_checked = box.getVector(String.valueOf(dbox.getInt("d_sulnum")));
					 for (int j =0; j < v_checked.size() ; j++ ) {
						 v_answers += (String)v_checked.get(j);
						 if(j == v_checked.size()-1){
						     v_answers += ",";
						 }else{
						     v_answers += ":";
						 }
					 }
                }else if (dbox.getString("d_sultype").equals(SulmunTargetBean.SUBJECT_QUESTION)) {  // 서술형
                     v_answers += box.getString(String.valueOf(dbox.getInt("d_sulnum")));
					 v_answers += ",";
                }else if (dbox.getString("d_sultype").equals(SulmunTargetBean.COMPLEX_QUESTION)) {        // 복합형 
                     v_answers += box.getString(String.valueOf(dbox.getInt("d_sulnum")));
					 v_answers += ":";
                     v_answers += box.getString((String.valueOf(dbox.getInt("d_sulnum"))+"|C"));
					 v_answers += ",";
                }else if (dbox.getString("d_sultype").equals(SulmunTargetBean.FSCALE_QUESTION)) {        // 5점척도
                     v_answers += box.getString(String.valueOf(dbox.getInt("d_sulnum")));
					 v_answers += ",";
                }else if (dbox.getString("d_sultype").equals(SulmunTargetBean.SSCALE_QUESTION)) {        // 7점척도
                     v_answers += box.getString(String.valueOf(dbox.getInt("d_sulnum")));
					 v_answers += ",";
                }
			}


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
						isOk1 = updateTZ_sulmail(connMgr, v_subj , v_grcode, v_gyear,    v_subjseq, v_sulpapernum, v_userid,  v_sulnums, v_answers, v_luserid);

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
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return isOk*isOk1;
    }
    
    
    
    public int updateTZ_sulmail(DBConnectionManager connMgr, String p_subj,  String p_grcode,  String p_gyear,    String p_subjseq, int p_sulpapernum,
                            String p_userid, String  p_sulnums, String p_answers, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {

	    sql =  " update tz_sulmail ";
            sql+=  "    set   return  = ?, ";
            sql+=  "       returntime  = ? ";
            sql+=  "  where subj     = ?  ";
            sql+=  "    and grcode   = ?  ";
            sql+=  "    and year   = ?  ";
            sql+=  "    and subjseq   = ?  ";
            sql+=  "    and sulpapernum   = ?  ";
            sql+=  "    and userid   = ?  ";				

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, "Y");
            pstmt.setString(2, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(3, p_subj);
	    pstmt.setString(4, p_grcode);
            pstmt.setString(5, p_gyear);
            pstmt.setString   (6, p_subjseq);
            pstmt.setInt(7, p_sulpapernum);
            pstmt.setString(8, p_userid);

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

    public int getSulmunGigan(RequestBox box) throws Exception {

		SulmunTargetPaperBean bean = null;
		DataBox  dbox0 = null; 

        String v_sulstart = "";
        String v_sulend = "";
		int v_update = 0;

        try {
			bean = new SulmunTargetPaperBean();
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
		         } else if (v_fstart <= v_now && v_now <= v_fend){
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


    /**
    대상자설문  결과 등록 
    @param box          receive from the form object and session
    @return int   
    */
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
       catch(Exception ex) {ex.printStackTrace();
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
       }
       finally {
           if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
       }
       return isOk;
    }

   /**
    설문 대상자 리스트
    @param box          receive from the form object and session
    @return ArrayList   설문 대상자
    */
    public DataBox SelectUserPaperResult(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        

        String v_grcode    = box.getString("p_grcode");
        String v_subj      = box.getStringDefault("p_subj", "TARGET");
        String v_gyear    = box.getString("p_gyear");
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
    설문 대상자 리스트
    @param box          receive from the form object and session
    @return ArrayList   설문 대상자
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
                sql+= "	   	  b.jikup,       get_jikwinm(b.jikwi, b.comp) jikupnm, ";
                sql+= "	   	  b.cono,     b.name ";
                sql+= "  from tz_member   b ";
                sql+= "  where b.userid    = " + SQLString.Format(v_userid);
     
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
    교육그룹 구하기 
    @param box          receive from the form object and session
    @return String   
    */
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
        String v_subj     = box.getStringDefault("s_subjcourse", SulmunTargetBean.DEFAULT_SUBJ);
//        String v_grcode = box.getStringDefault("s_grcode", SulmunTargetBean.DEFAULT_GRCODE);
//System.out.println(box.getString("s_grcode"));
			connMgr = new DBConnectionManager();
            
            list = new ArrayList();

			sql = "select a.grcode,       a.subj,   a.subjseq,      ";
            sql+= "       a.sulpapernum,  a.sulpapernm, a.year, ";
            sql+= "       a.totcnt,       a.sulnums, a.sulmailing, a.sulstart, a.sulend, ";
            sql+= "       'TARGET'      subjnm ";
            sql+= "  from (select subj, grcode, year, subjseq, sulpapernum, sulpapernm, totcnt, sulnums, sulmailing, sulstart, sulend from tz_sulpaper  ";
            sql+= "   where sulmailing != 'Y' ) a, ";			
	        sql += "  (select subj, grcode, year, subjseq, sulpapernum, userid from tz_sulmember where userid = " + SQLString.Format(s_userid);
            sql += "   minus select subj, grcode, year, subjseq, sulpapernum, userid from tz_suleach where userid = " + SQLString.Format(s_userid) + ") b";
            sql+= "   where a.grcode = b.grcode ";
            sql+= "   and a.subj = b.subj ";
            sql += "  and a.year = b.year                                                ";
            sql += "  and a.subjseq = b.subjseq                                          ";
            sql+= "   and a.sulpapernum = b.sulpapernum ";
//			sql+= "   and a.grcode = " + SQLString.Format(v_grcode);
            sql+= "   and a.subj   = " + SQLString.Format(v_subj);
            sql+= "   and b.userid   = " + SQLString.Format(s_userid);
            sql += "  and substr(a.sulstart,1,8) <= to_char(sysdate, 'yyyyMMdd')         ";
            sql += "  and substr(a.sulend,1,8) >= to_char(sysdate, 'yyyyMMdd')         ";
            sql += "  and (a.sulmailing = 'C'  or a.sulmailing = 'N'  )                                         ";
            sql+= " order by a.subj, a.sulpapernum ";

            ls = connMgr.executeQuery(sql);//System.out.println(sql);
            
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
    
    /**
    대상 설문
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectSulmunTargetList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls     = null;
        ArrayList list = null;
		DataBox   dbox = null;

        String  sql1        = "";
        String  v_user_id   = box.getSession("userid");
		String  v_grcode 	= box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

              sql1 = "SELECT a.grcode, b.year, a.sulpapernum,  a.sulpapernm , a.sulstart, a.sulend, a.totcnt  ,   \n"
                   + " (select count(*) from tz_suleach where userid='"+v_user_id+"' and subj='TARGET' " 
                   + " AND GRCODE=a.grcode and sulpapernum=a.sulpapernum) sulresult ,  \n"
                   + " (case when a.sulend >= to_char(sysdate, 'YYYYMMDD') then 'Y' else 'N' end ) issul  \n "
                   + " FROM TZ_SULPAPER a, TZ_SULMEMBER b  \n"
                   + " WHERE a.sulpapernum=b.sulpapernum and a.subj='TARGET' \n" 
                   + " and b.USERID='"+v_user_id+"'  \n"
			  		+" and a.grcode = 	"+SQLString.Format(v_grcode);
			  		System.out.println(" 대상 설문 selectSulmunTargetList: "+sql1);

            ls = connMgr.executeQuery(sql1);

            while (ls.next()) {
    
                dbox = ls.getDataBox();
                list.add(dbox);                
            }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;        
    }        
}