//**********************************************************
//1. 제      목: SO설문 설문 관리
//2. 프로그램명: SoSulmunQuestionBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 정은년 2005.8.22
//7. 수      정:
//
//**********************************************************

package com.credu.so;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.so.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoSulmunQuestionBean {


    public SoSulmunQuestionBean() {}

    /**
    SO평가 설문 리스트 
    @param box          receive from the form object and session
    @return ArrayList   평가설문 리스트
    */
    public ArrayList selectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_distcode   = box.getString("p_distcode");
        int    v_levels     = box.getInt("p_levels");        
        String v_action     = box.getStringDefault("p_action",  "change"); 
        String p_distcode   = "";
Log.info.println("sql"+v_levels);
Log.info.println("sql"+v_distcode);
        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();                
                p_distcode = v_distcode.substring(0, v_levels*3);
                list = getQuestionList(connMgr, p_distcode);
            } else {
                list = new ArrayList();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    @param box          receive from the form object and session
    @return ArrayList   평가설문 리스트
    */
    public ArrayList getQuestionList(DBConnectionManager connMgr, String p_sodistcode) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            /*sql = " select a.distcode, a.SoSulmunnum, a.SoSulmuntype, a.SoSulmuntext,    a.soexptext,     ";
            sql+= "       a.levels,   a.selcount,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
            sql+= "       b.codenm    sotypenm,   ";
            sql+= "       c.codenm    levelsnm    ";
            sql+= " from tz_SoSulmun   a, ";
            sql+= "      tz_code     b, ";
            sql+= "      tz_code     c  ";
            sql+= " where a.SoSulmuntype   = b.code ";
            sql+= "   and a.levels       = c.code ";
            sql+= "   and b.gubun        = " + SQLString.Format(SoBean.EXAM_TYPE);
            sql+= "   and c.gubun        = " + SQLString.Format(SoBean.EXAM_LEVEL);
            sql+= "   and a.distcode like '"+p_distcode+"%' ";            
            sql+= " order by a.SoSulmunnum ";*/
           
		   // 수정일 : 05.11.04 수정자 : 이나연 _ decode 수정
//         sql  = " select  sulnum, decode(distcode,'1','학습','2','운영','3','공통','4','강좌','강사') distcode,   sultype, ";
           sql  = " select  sulnum, case distcode ";
		   sql += " 						When '1' 	Then  '학습' ";
		   sql += " 						When '2' 	Then  '운영' ";
		   sql += " 						When '3' 	Then  '공통' ";
		   sql += " 						When '4' 	Then  '강좌' ";
		   sql += " 						Else '강사' ";	
		   sql += " 				End as distcode,   sultype, ";
           sql += " (select codenm from tz_code where gubun='0011' and levels='1' and code=tz_sosul.sultype) sultypenm, ";
           sql += " sultext, sodistcode ";           
           sql += " from tz_sosul ";
           sql += " where  sodistcode like '"+p_sodistcode+"%' ";  
Log.info.println("sql"+sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return list;
    }
    
    /**
    설문 문제등록
    @param box          receive from the form object and session
    @return String      조건쿼리
    */
    public int insertQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;        
        String sql = "";
        int isOk = 0;

        //String v_gubun      = box.getStringDefault("p_gubun", SulmunAllBean.DEFAULT_SUBJ);    
		String v_grcode     = box.getString("p_grcode");
        String v_distcode   = box.getString("p_distcode");  // 문제분류
        String v_sultype    = box.getString("p_sultype");
        String v_sultext    = box.getString("p_sultext");
        String v_sulreturn  = box.getString("p_sulreturn");
        String  v_sodistcode = box.getString("p_sodistcode"); // 문제속성값
        
        int v_sulnum   = 0;
        int v_selnum   = 0;
		int v_selcount = 0;
        int v_selmax   = 0;
		int v_scalecode= 0;
        String v_seltext    = "";
        Vector v_seltexts   = null;
		Enumeration em      = null;
        String v_selpoint    = "";
        Vector v_selpoints   = null;
		Enumeration em1      = null;
        
		if (v_sultype.equals("1")) {
            v_selcount    = box.getInt("p_selcount1");
            v_selmax      = box.getInt("p_selmax1");
            v_scalecode   = box.getInt("p_scalecode1");
            v_seltexts    = box.getVector("p_seltext1");
            em            = v_seltexts.elements();
            v_selpoints   = box.getVector("p_selpoint1");
    		em1      = v_selpoints.elements();
		} else if (v_sultype.equals("2")) {
            v_selcount    = box.getInt("p_selcount2");
            v_selmax    = box.getInt("p_selmax2");
            v_scalecode    = box.getInt("p_scalecode2");
            v_seltexts   = box.getVector("p_seltext2");
            em      = v_seltexts.elements();
            v_selpoints   = box.getVector("p_selpoint2");
    		em1      = v_selpoints.elements();
		} else if (v_sultype.equals("3")) {
            v_selcount    = box.getInt("p_selcount3");
            v_selmax    = box.getInt("p_selmax3");
            v_scalecode    = box.getInt("p_scalecode3");
            v_seltexts   = box.getVector("p_seltext3");
            em      = v_seltexts.elements();
            v_selpoints   = box.getVector("p_selpoint3");
    		em1      = v_selpoints.elements();
		} else if (v_sultype.equals("4")) {
            v_selcount    = box.getInt("p_selcount4");
            v_selmax    = box.getInt("p_selmax4");
            v_scalecode    = box.getInt("p_scalecode4");
            v_seltexts   = box.getVector("p_seltext4");
            em      = v_seltexts.elements();
            v_selpoints   = box.getVector("p_selpoint4");
    		em1      = v_selpoints.elements();
		} else if (v_sultype.equals("5")) {
            v_selcount    = box.getInt("p_selcount5");
            v_selmax    = box.getInt("p_selmax5");
            v_scalecode    = box.getInt("p_scalecode");
            v_seltexts   = box.getVector("p_seltext5");
            em      = v_seltexts.elements();
            v_selpoints   = box.getVector("p_selpoint5");
    		em1      = v_selpoints.elements();
		} else if (v_sultype.equals("6")) {
            v_selcount    = box.getInt("p_selcount6");
            v_selmax    = box.getInt("p_selmax6");
            v_scalecode    = box.getInt("p_scalecode");
            v_seltexts   = box.getVector("p_seltext6");
            em      = v_seltexts.elements();
            v_selpoints   = box.getVector("p_selpoint6");
    		em1      = v_selpoints.elements();
		}
        
		String v_luserid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////
                        
            // 설문번호
            sql = "select NVL(max(sulnum), 0)+1 from TZ_SOSUL";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) v_sulnum = ls.getInt(1);
            ls.close();                   

            isOk = insertTZ_sul(connMgr, v_sulnum, v_distcode, v_sultype, v_sultext, v_selcount, v_selmax, v_sulreturn, v_scalecode, v_luserid, v_sodistcode);

            sql =  "insert into TZ_SOSULSEL(sulnum, selnum, seltext, selpoint, luserid, ldate) ";
            sql+=  " values ( ?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);
            while(em.hasMoreElements()){
                v_seltext   = (String)em.nextElement();
                v_selpoint   = (String)em1.nextElement();

                if (!v_seltext.trim().equals("")) {
                    v_selnum++;
                    isOk = insertTZ_sulsel(pstmt, v_sulnum, v_selnum, v_seltext, Integer.parseInt(v_selpoint), v_luserid);
                }
            }
       }
       catch(Exception ex) {
           isOk = 0;
           connMgr.rollback();
           ErrorManager.getErrorStackTrace(ex, box, sql);
           throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
       }
       finally {
           if (isOk > 0) {connMgr.commit();}
           if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
           if(connMgr != null) { try { connMgr.setAutoCommit(true);  connMgr.freeConnection(); }catch (Exception e10) {} }
       }
       return isOk;
   }
   
    public int insertTZ_sul(DBConnectionManager connMgr, int p_sulnum, String p_distcode, String p_sultype, String p_sultext, int p_selcount, int p_selmax, String p_sulreturn, int p_scalecode, String p_luserid, String p_sodistcode) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SUL table
            sql =  "insert into TZ_SOSUL(sulnum, distcode, sultype, sultext, selcount, selmax, sulreturn, scalecode, luserid, ldate, sodistcode) ";
            sql+=  " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt   (1, p_sulnum);
            pstmt.setString(2, p_distcode);
            pstmt.setString(3, p_sultype);
            pstmt.setString(4, p_sultext);
            pstmt.setInt   (5, p_selcount);
            pstmt.setInt   (6, p_selmax);
            pstmt.setString(7, p_sulreturn);
            pstmt.setInt   (8, p_scalecode);
            pstmt.setString(9, p_luserid);
            pstmt.setString(10, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(11, p_sodistcode);
            
            
            
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

    public int insertTZ_sulsel(PreparedStatement pstmt, int p_sulnum, int p_selnum, String p_seltext, int p_selpoint, String p_luserid) throws Exception {
        int isOk = 0;

        try {
            pstmt.setInt   (1, p_sulnum);
            pstmt.setInt   (2, p_selnum);
            pstmt.setString(3, p_seltext);
            pstmt.setInt   (4, p_selpoint);
            pstmt.setString(5, p_luserid);
            pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
            isOk = pstmt.executeUpdate();
       }
       catch(Exception ex) {
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception(ex.getMessage());
       }
       return isOk;
    }

    /**
    수정시 상세보기
    @param box          receive from the form object and session
    @return QuestionExampleData   설문문제
    */
    public ArrayList selectQuestionExample(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list1 = null;
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;
        //String v_gubun    = box.getStringDefault("p_gubun", SulmunAllBean.DEFAULT_SUBJ);
        String v_subj     = box.getString("p_subj");
        String v_grcode   = box.getString("p_grcode");
        int    v_sulnum   = box.getInt("p_sulnum");
        String v_action   = box.getStringDefault("p_action","change");
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            
            /*sql = "select a.subj, a.sulnum, a.distcode, a.sultype, a.sultext, a.sulreturn, a.selmax, a.scalecode, b.selnum, b.seltext, b.selpoint ";
            sql+= "  from tz_sul     a, ";
            sql+= "       tz_sulsel  b  ";
            sql+= " where a.subj   = b.subj(+)    ";
            sql+= "   and a.sulnum = b.sulnum(+)  ";
            sql+= "   and a.grcode = b.grcode(+)  ";
            sql+= "   and a.subj   = " +   SQLString.Format(p_subj);
            sql+= "   and a.grcode   = " +   SQLString.Format(p_grcode);
            sql+= "   and a.sulnum = " +   SQLString.Format(p_sulnum);
            sql+= " order by b.selnum ";*/

            sql = " select a.sulnum, a.sultext, a.distcode, a.sultype, a.selmax, a.scalecode,  a.sodistcode, a.sulreturn, ";
            sql+= " b.selnum, b.seltext, b.selpoint ";
            sql+= " from tz_sosul a, tz_sosulsel b  ";
			// 수정일 : 05.11.11 수정자 : 이나연 _(+)  수정
//          sql+= " where a.sulnum = b.sulnum(+) and a.sulnum="+v_sulnum+"  ";
            sql+= " where a.sulnum  =  b.sulnum(+) and a.sulnum="+v_sulnum+"  ";
            sql+= " order by b.selnum  ";
            
            ls = connMgr.executeQuery(sql);

		    while (ls.next()) {
                dbox = ls.getDataBox();
                list1.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }            
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }  
 
     // 삭제
     public int deleteQuestion(RequestBox box) throws Exception {
         DBConnectionManager connMgr = null;
         int isOk = 0;

        //String v_gubun    = box.getStringDefault("p_gubun", SulmunAllBean.DEFAULT_SUBJ);
        String v_subj       = box.getString("p_subj");   
		String v_grcode       = box.getString("p_grcode");
		String v_duserid    = box.getSession("userid");
		
        String v_sultype    = box.getString("p_sultype");
        int    v_sulnum     = box.getInt("p_sulnum");
                
         try {
             connMgr = new DBConnectionManager();
             connMgr.setAutoCommit(false);////

             isOk = deleteTZ_sul(connMgr,  v_sulnum);
             if (!v_sultype.equals("3")) {
                isOk = deleteTZ_sulsel(connMgr, v_sulnum);
			 }
         }
         catch(Exception ex) {
             isOk = 0;
             connMgr.rollback();
             ErrorManager.getErrorStackTrace(ex);
             throw new Exception(ex.getMessage());
         }
         finally {
             if (isOk > 0) {connMgr.commit(); box.put("p_sulnum", String.valueOf("0"));}
             if (connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
         }
         return isOk;
     }


    /**
    문제수정
    @param box          receive from the form object and session
    @return String      조건쿼리
    */
    public int updateQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        //String v_gubun    = box.getStringDefault("p_gubun", SulmunAllBean.DEFAULT_SUBJ);
        //String v_subj     = box.getString("p_subj");
        //String v_subj     = v_gubun;  
		String v_grcode     = box.getString("p_grcode");
        int    v_sulnum     = box.getInt("p_sulnum");
        String v_distcode   = box.getString("p_distcode");
        String v_sultype    = box.getString("p_sultype");
        String v_sultext    = box.getString("p_sultext");
        String v_sulreturn =  box.getString("p_sulreturn");
        String v_sodistcode   = box.getString("p_sodistcode");
        		
        int    v_selnum     = 0;
		int v_selcount    = 0;
        int v_selmax    = 0;
		int v_scalecode    = 0;
        String v_seltext    = "";
        Vector v_seltexts   = null;
		Enumeration em      = null;
        String v_selpoint    = "";
        Vector v_selpoints   = null;
		Enumeration em1      = null;
        
		if (v_sultype.equals("1")) {
        v_selcount    = box.getInt("p_selcount1");
        v_selmax    = box.getInt("p_selmax1");
        v_scalecode    = box.getInt("p_scalecode1");
        v_seltexts   = box.getVector("p_seltext1");
        em      = v_seltexts.elements();
        v_selpoints   = box.getVector("p_selpoint1");
		em1      = v_selpoints.elements();
		} else if (v_sultype.equals("2")) {
        v_selcount    = box.getInt("p_selcount2");
        v_selmax    = box.getInt("p_selmax2");
        v_scalecode    = box.getInt("p_scalecode2");
        v_seltexts   = box.getVector("p_seltext2");
        em      = v_seltexts.elements();
        v_selpoints   = box.getVector("p_selpoint2");
		em1      = v_selpoints.elements();
		} else if (v_sultype.equals("3")) {
        v_selcount    = box.getInt("p_selcount3");
        v_selmax    = box.getInt("p_selmax3");
        v_scalecode    = box.getInt("p_scalecode3");
        v_seltexts   = box.getVector("p_seltext3");
        em      = v_seltexts.elements();
        v_selpoints   = box.getVector("p_selpoint3");
		em1      = v_selpoints.elements();
		} else if (v_sultype.equals("4")) {
        v_selcount    = box.getInt("p_selcount4");
        v_selmax    = box.getInt("p_selmax4");
        v_scalecode    = box.getInt("p_scalecode4");
        v_seltexts   = box.getVector("p_seltext4");
        em      = v_seltexts.elements();
        v_selpoints   = box.getVector("p_selpoint4");
		em1      = v_selpoints.elements();
		} else if (v_sultype.equals("5")) {
        v_selcount    = box.getInt("p_selcount5");
        v_selmax    = box.getInt("p_selmax5");
        v_scalecode    = box.getInt("p_scalecode");
        v_seltexts   = box.getVector("p_seltext5");
        em      = v_seltexts.elements();
        v_selpoints   = box.getVector("p_selpoint5");
		em1      = v_selpoints.elements();
		} else if (v_sultype.equals("6")) {
        v_selcount    = box.getInt("p_selcount6");
        v_selmax    = box.getInt("p_selmax6");
        v_scalecode    = box.getInt("p_scalecode");
        v_seltexts   = box.getVector("p_seltext6");
        em      = v_seltexts.elements();
        v_selpoints   = box.getVector("p_selpoint6");
		em1      = v_selpoints.elements();
		}
        
		String v_luserid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            isOk = updateTZ_sul(connMgr, v_sulnum, v_distcode, v_sultype, v_sultext, v_selcount, v_selmax, v_sulreturn, v_scalecode, v_luserid, v_sodistcode);
            if (!v_sultype.equals("3")) {       
			   isOk = deleteTZ_sulsel(connMgr, v_sulnum);
            }

            sql =  "insert into TZ_SOSULSEL(sulnum, selnum, seltext, selpoint, luserid, ldate) ";
            sql+=  " values ( ?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);
            while(em.hasMoreElements()){
                v_seltext   = (String)em.nextElement();
                v_selpoint   = (String)em1.nextElement();

                if (!v_seltext.trim().equals("")) {
                    v_selnum++;
                    isOk = insertTZ_sulsel(pstmt, v_sulnum, v_selnum, v_seltext, Integer.parseInt(v_selpoint), v_luserid);
                }
            }
        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
    
    // 수정
    public int updateTZ_sul(DBConnectionManager connMgr, int p_sulnum, String p_distcode, String p_sultype, String p_sultext, int p_selcount, int p_selmax, String p_sulreturn, int p_scalecode, String p_luserid, String p_sodistcode) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_SUL table
            sql =  " update TZ_SOSUL ";
            sql+=  "    set distcode = ?, ";
            sql+=  "        sultype  = ?, ";
            sql+=  "        sultext  = ?, ";
            sql+=  "        selcount  = ?, ";
            sql+=  "        selmax  = ?, ";
            sql+=  "        sulreturn  = ?, ";
            sql+=  "        scalecode  = ?, ";
            sql+=  "        luserid  = ?, ";
            sql+=  "        ldate    = ?,  ";
            sql+=  "        sodistcode    = ?  ";
            sql+=  "  where sulnum   = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_distcode);
            pstmt.setString(2, p_sultype);
            pstmt.setString(3, p_sultext);
            pstmt.setInt(4, p_selcount);
            pstmt.setInt(5, p_selmax);
            pstmt.setString(6, p_sulreturn);
            pstmt.setInt(7, p_scalecode);
            pstmt.setString(8, p_luserid);
            pstmt.setString(9, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(10, p_sodistcode);
            pstmt.setInt   (11, p_sulnum);

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
    
        
     // 삭제
    public int deleteTZ_sul(DBConnectionManager connMgr,  int p_sulnum) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_SUL table
            sql =  " delete from TZ_SOSUL ";
            sql+=  "  where sulnum   = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt   (1, p_sulnum);

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
    
     // 삭제    
    public int deleteTZ_sulsel(DBConnectionManager connMgr, int p_sulnum) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //delete TZ_SUL table
            sql =  " delete from TZ_SOSULSEL ";
            sql+=  "  where sulnum     = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt   (1, p_sulnum);

            isOk = pstmt.executeUpdate();
       }
       catch(Exception ex) {
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception(ex.getMessage());
       }
       finally {
           if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
       }
       return isOk;
    }    
}                                                                                                                                                                                     