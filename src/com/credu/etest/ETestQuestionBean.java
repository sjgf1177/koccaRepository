
//**********************************************************
//1. 제      목: 온라인테스트 과정관리
//2. 프로그램명: ETestSubjectBean.java
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
public class ETestQuestionBean {

    public ETestQuestionBean() {}


    /**
    평가문제 리스트
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    public ArrayList selectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        String ss_etestsubj    = box.getString("s_etestsubj");
        String v_action        = box.getStringDefault("p_action",  "change");
        String v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String v_orderType     = box.getString("p_orderType");              //정렬할 순서 
        try {
            list = new ArrayList();
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
            
                sql = "select a.etestsubj, a.etestnum, a.etesttype, a.etesttext, a.exptext, a.levels,    ";
                sql+= "       a.selcnt, a.realimage, a.saveimage, a.realaudio, a.saveaudio, a.realmovie, a.savemovie, a.realflash, a.saveflash, ";
                sql+= "       b.codenm    etesttypenm, c.codenm levelsnm ,    ";
                sql+= "       ( select codenm from TZ_CODE where gubun = '0048' and levels = 1 and code = a.etestgubun ) codenm ";
                sql+= "  from tz_etest a, tz_code b, tz_code c                                                    ";
                sql+= " where    a.etesttype = b.code                                                                ";
                sql+= "   and a.levels   = c.code                                                                ";
                sql+= "   and a.etestsubj     = " + SQLString.Format(ss_etestsubj);
                sql+= "   and b.gubun    = " + SQLString.Format(ETestBean.ETEST_TYPE);
                sql+= "   and c.gubun    = " + SQLString.Format(ETestBean.ETEST_LEVEL);
                //sql+= " order by a.etestnum                                                                       ";

    			if(v_orderColumn.equals("")) {
                	sql+= " order by a.etestnum  ";
    			} else {
    			    sql+= " order by " + v_orderColumn + v_orderType;
    			} 
			
                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();

                    list.add(dbox);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }


    /**
    평가문제 상세보기
    @param box          receive from the form object and session
    @return QuestionExampleData   평가문제
    */
    public ArrayList selectExampleData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_etestsubj     = box.getString("p_etestsubj");
        int    v_etestnum  = box.getInt("p_etestnum");
        String v_action   = box.getStringDefault("p_action","change");

        try {
            if (v_action.equals("go") && v_etestnum > 0) {
                connMgr = new DBConnectionManager();
                list = getExampleData(connMgr, v_etestsubj,  v_etestnum);
            } else {
                list = new ArrayList();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }
        return list;
    }

    /**
    문제Pool에서 문제보기
    @param box          receive from the form object and session
    @return QuestionExampleData   평가문제
    */
    public ArrayList getExampleData(DBConnectionManager connMgr, String p_etestsubj,  int p_etestnum) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
                sql = "select a.etestsubj, a.etestnum, a.etesttype, a.etesttext, a.exptext,   ";
                sql+= "       a.levels, a.selcnt, a.realimage, a.saveimage, a.realaudio, a.saveaudio, a.realmovie, a.savemovie,    a.realflash,   a.saveflash,              ";
                sql+= "       b.selnum, b.seltext,  b.isanswer, c.codenm etesttypenm, d.codenm levelsnm, a.etestgubun gubun ,          ";
                sql+= "       ( select codenm from TZ_CODE where gubun = "+SQLString.Format(ETestBean.ETEST_GUBUN)+" and levels = 1 and code = a.etestgubun ) etestgubunnm  ";
                sql+= "  from tz_etest a, tz_etestsel b, tz_code c, tz_code d                                       ";
				// 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
//              sql+= " where   a.etestsubj     = b.etestsubj(+)                                                             ";
				sql+= " where   a.etestsubj     =  b.etestsubj(+)                                                             ";
//              sql+= "   and a.etestnum  = b.etestnum(+)                                                          ";
	            sql+= "   and a.etestnum   =  b.etestnum(+)                                                          ";
				// 수정일 : 05.11.04 수정자 : 이나연 _ 여기까지 수정
                sql+= "   and a.etesttype = c.code                                                                ";
                sql+= "   and a.levels   = d.code                                                                ";
                sql+= "   and a.etestsubj     = " + SQLString.Format(p_etestsubj);
                sql+= "   and a.etestnum  = " + SQLString.Format(p_etestnum);
                sql+= "   and c.gubun    = " + SQLString.Format(ETestBean.ETEST_TYPE);
                sql+= "   and d.gubun    = " + SQLString.Format(ETestBean.ETEST_LEVEL);
                sql+= " order by a.etestnum, b.selnum                                                             ";
System.out.println("pppppppppppppp");
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
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return list;
    }

    /**
    평가문제 등록
    @param box          receive from the form object and session
    @return isOk        1:insert success,0:insert fail
    */
    public int insertQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk2_check = 0;

        String  v_etestsubj     = box.getString("p_etestsubj");
        int     v_etestnum  = 0;
        String  v_etesttype = box.getString("p_etesttype");
        String  v_etesttext     = box.getString("p_etesttext");
        String  v_exptext  = box.getString("p_exptext");
        String  v_levels   = box.getString("p_levels");
        String  v_gubun    = box.getString("p_gubun");  // 문제분류
        int    v_selnum     = 0;
        Vector v_seltexts   = box.getVector("p_seltext");
        String v_seltext    = "";
        Vector v_isanswers  = box.getVector("p_isanswer");
        String v_isanswer   = "";

        int v_checked_selnum = 0;

        String v_luserid    = box.getSession("userid");

		int    v_selcnt     = box.getInt("p_selcount1");
		
		String v_saveimage       = box.getNewFileName ("p_img1");
		String v_realimage   = box.getRealFileName("p_img1");
        String v_saveaudio     = box.getNewFileName ("p_audio1");
		String v_realaudio   = box.getRealFileName("p_audio1");
        String v_savemovie  = box.getNewFileName("p_movie1");
		String v_realmovie   = box.getRealFileName("p_movie1");
		String v_saveflash      = box.getNewFileName ("p_flash1");
		String v_realflash   = box.getRealFileName("p_flash1");
		
        try {
            v_etestnum = this.getEtestnumSeq(v_etestsubj);         // 평가문제번호

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            isOk1 = insertTZ_etest(connMgr, v_etestsubj, v_etestnum, v_etesttype, v_etesttext, v_exptext, v_levels, v_selcnt, v_saveimage, v_saveaudio, v_savemovie, v_saveflash, v_realimage, v_realaudio, v_realmovie, v_realflash, v_luserid, v_gubun );

            sql =  "insert into tz_etestsel(etestsubj, etestnum, selnum, seltext, isanswer, luserid, ldate) ";
            sql+=  " values (?, ?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);

            isOk2 = 1;

            for (int i=1; i<=10; i++) {
                v_seltext  = box.getString("p_seltext"+String.valueOf(i));
                if (!v_seltext.trim().equals("")) {
                    v_selnum++;
                    v_isanswer = box.getString("p_isanswer"+String.valueOf(i));

                    isOk2 = insertTZ_etestsel(pstmt, v_etestsubj,  v_etestnum, v_selnum, v_seltext, v_isanswer, v_luserid);
                }
            }			

            if (isOk1*isOk2 > 0) {                
 /*               ConfigSet conf = new ConfigSet();
                //System.out.println("v_updir pre ");
                String v_dirKey = conf.getDir(conf.getProperty("dir.upload"), FileManager.getServletName("etest"));//System.out.println("v_dirKey " + v_dirKey);
                String v_movedir = conf.getProperty("dir.move") + v_dirKey;//System.out.println("v_movedir " + v_movedir);
                String v_updir = conf.getProperty("dir.upload." + v_dirKey);System.out.println("v_updir " + v_updir);
                //boolean isMove = new FileMove().move(v_movedir, v_updir, v_saveimage);
                //jkh 2005/0224 각파일종류별 추가
                boolean isMove = true;
                if (!v_saveimage.equals("")) { isMove = new FileMove().move(v_movedir, v_updir, v_saveimage);  }
                if (!v_saveaudio.equals("")) { isMove = new FileMove().move(v_movedir, v_updir, v_saveaudio);  }
                if (!v_savemovie.equals("")) { isMove = new FileMove().move(v_movedir, v_updir, v_savemovie);  }
                if (!v_saveflash.equals("")) { isMove = new FileMove().move(v_movedir, v_updir, v_saveflash);  }
                if(isMove) connMgr.commit();
*/                


                connMgr.commit();

            }
            else {
                connMgr.rollback();
            }
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2;
    }


    /**
    평가문제내용 등록
    @param box          receive from the form object and session
    @return isOk        1:insert success,0:insert fail
    */
    public int insertTZ_etest(DBConnectionManager connMgr,
                            String  p_etestsubj,     int     p_etestnum,  String  p_etesttype,
                            String  p_etesttext,     String  p_exptext,   String  p_levels,   int p_selcnt, String  p_saveimage,   String  p_saveaudio, String p_savemovie, 
		                    String p_saveflash, String  p_realimage,   String  p_realaudio, String p_realmovie, String p_realflash, String p_luserid, String p_gubun ) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {

            //insert tz_etest table
            sql =  " insert into tz_etest (etestsubj, etestnum, etesttype, etesttext, exptext, levels, selcnt, saveimage, realimage, saveaudio, realaudio, savemovie, realmovie, saveflash, realflash, luserid, ldate, etestgubun ) ";
            sql+=  "              values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )                                       ";

            pstmt = connMgr.prepareStatement(sql);
            
            pstmt.setString( 1, p_etestsubj);
            pstmt.setInt   ( 2, p_etestnum);
            pstmt.setString( 3, p_etesttype);
            pstmt.setString( 4, p_etesttext);
            pstmt.setString( 5, p_exptext);
            pstmt.setString( 6, p_levels);
            pstmt.setInt   ( 7, p_selcnt);
            pstmt.setString( 8, p_saveimage);
            pstmt.setString( 9, p_realimage);
            pstmt.setString(10, p_saveaudio);
            pstmt.setString(11, p_realaudio);
            pstmt.setString(12, p_savemovie );
            pstmt.setString(13, p_realmovie);
            pstmt.setString(14, p_saveflash );
            pstmt.setString(15, p_realflash);
            pstmt.setString(16, p_luserid);
            pstmt.setString(17, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(18, p_gubun);            

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
    평가문제보기 등록
    @param box          receive from the form object and session
    @return isOk        1:insert success,0:insert fail
    */
    public int insertTZ_etestsel(PreparedStatement pstmt, String p_etestsubj,  int p_etestnum, int p_selnum, String p_seltext, String p_isanswer, String p_luserid) throws Exception {
        int isOk = 0;

        try {
            pstmt.setString(1, p_etestsubj);
            pstmt.setInt   (2, p_etestnum);
            pstmt.setInt   (3, p_selnum);
            pstmt.setString(4, p_seltext);
            pstmt.setString(5, p_isanswer);
            pstmt.setString(6, p_luserid);
            pstmt.setString(7, FormatDate.getDate("yyyyMMddHHmmss"));
            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return isOk;
    }


    /**
    평가문제 수정
    @param box     receive from the form object and session
    @return isOk   1:update success,0:update fail
    */
    public int updateQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk3 = 0;      

        String  v_etestsubj   = box.getString("p_etestsubj");
		int    v_etestnum     = box.getInt("p_etestnum");
        String  v_etesttype   = box.getString("p_etesttype");
        String  v_etesttext   = box.getString("p_etesttext");
        String  v_exptext     = box.getString("p_exptext");
        String  v_levels      = box.getString("p_levels");
        String  v_gubun       = box.getString("p_gubun");    // 문제분류
        int    v_selnum     = 0;
        Vector v_seltexts   = box.getVector("p_seltext");
        String v_seltext    = "";
        Vector v_isanswers  = box.getVector("p_isanswer");
        String v_isanswer   = "";

        int v_checked_selnum = 0;

        String v_luserid    = box.getSession("userid");

		int    v_selcnt     = box.getInt("p_selcount1");

		String v_saveimage       = box.getNewFileName ("p_img1");
		String v_realimage   = box.getRealFileName("p_img1");
        String v_saveaudio     = box.getNewFileName ("p_audio1");
		String v_realaudio   = box.getRealFileName("p_audio1");
        String v_savemovie  = box.getNewFileName("p_movie1");
		String v_realmovie   = box.getRealFileName("p_movie1");
		String v_saveflash      = box.getNewFileName ("p_flash1");
		String v_realflash   = box.getRealFileName("p_flash1");

        String v_imgfile      = box.getString("p_img2");
        String v_audiofile      = box.getString("p_audio2");
        String v_moviefile      = box.getString("p_movie2");
        String v_flashfile      = box.getString("p_flash2");
        String v_realimgfile  = box.getString("p_img3");
        String v_realaudiofile  = box.getString("p_audio3");
        String v_realmoviefile  = box.getString("p_movie3");
        String v_realflashfile  = box.getString("p_flash3");

        if(v_saveimage.length()       == 0) { v_saveimage       = v_imgfile;     }
        if(v_saveaudio.length()     == 0) { v_saveaudio     = v_audiofile;     }
        if(v_savemovie.length()      == 0) { v_savemovie      = v_moviefile;     }
        if(v_saveflash.length()      == 0) { v_saveflash      = v_flashfile;     }
        if(v_realimage.length()      == 0) { v_realimage      = v_realimgfile;     }
        if(v_realaudio.length()   == 0) { v_realaudio   = v_realaudiofile; }
        if(v_realmovie.length() == 0) { v_realmovie = v_realmoviefile; }
        if(v_realflash.length()  == 0) { v_realflash  = v_realflashfile; }
  
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 수정여부 체크
			// 수정일 : 05.11.17 수정자 : 이나연 _ || 수정
//          sql = " select count(*) cnt from tz_etestpaper where etestsubj='"+v_etestsubj+"'  and ','||etest||',' like '%,"+v_etestnum+",%' ";
			sql = " select count(*) cnt from tz_etestpaper where etestsubj='"+v_etestsubj+"'  and ','||etest||',' like '%,"+v_etestnum+",%' ";
            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();
		    		if (rs.next()) {
		    				if(rs.getInt("cnt") > 0) {
		    					isOk1 = -2;
		    					isOk2 = 1;
		    				}
		    		}
		    		
		    		if(isOk1==0){
	            isOk1 = updateTZ_etest(connMgr, v_etestsubj, v_etestnum, v_etesttype, v_etesttext, v_exptext, v_levels, v_selcnt, v_saveimage, v_saveaudio, v_savemovie, v_saveflash, v_realimage, v_realaudio, v_realmovie, v_realflash, v_luserid, v_gubun );
	
	            // 기존 지문 삭제
	            isOk3 = this.deleteTZ_etestsel(connMgr, v_etestsubj, v_etestnum);
	
	            // 지문 등록
	            sql =  "insert into tz_etestsel(etestsubj, etestnum, selnum, seltext, isanswer, luserid, ldate) ";
	            sql+=  " values (?, ?, ?, ?, ?, ?, ?)";
	            pstmt = connMgr.prepareStatement(sql);
	
	            isOk2 = 1;
	
	            for (int i=1; i<=10; i++) {
	                v_seltext  = box.getString("p_seltext"+String.valueOf(i));
	                if (!v_seltext.trim().equals("")) {
	                    v_selnum++;
	                    v_isanswer = box.getString("p_isanswer"+String.valueOf(i));
	                    
						if(isOk3 > 0)
	                    isOk2 = insertTZ_etestsel(pstmt, v_etestsubj,  v_etestnum, v_selnum, v_seltext, v_isanswer, v_luserid);
	                }
	            }
	
	            if (isOk1*isOk2*isOk3 > 0) {
	/*
	                
	                ConfigSet conf = new ConfigSet();
	                String v_dirKey = conf.getDir(conf.getProperty("dir.upload"), FileManager.getServletName("etest"));System.out.println("v_dirKey " + v_dirKey);
	                String v_movedir = conf.getProperty("dir.move") + v_dirKey;System.out.println("v_movedir " + v_movedir);
	                String v_updir = conf.getProperty("dir.upload." + v_dirKey);System.out.println("v_updir " + v_updir);
	                //jkh 2005/0224 각파일종류별 추가
	                boolean isMove = true;
	                if (!v_saveimage.equals("")) { isMove = new FileMove().move(v_movedir, v_updir, v_saveimage);  }
	                if (!v_saveaudio.equals("")) { isMove = new FileMove().move(v_movedir, v_updir, v_saveaudio);  }
	                if (!v_savemovie.equals("")) { isMove = new FileMove().move(v_movedir, v_updir, v_savemovie);  }
	                if (!v_saveflash.equals("")) { isMove = new FileMove().move(v_movedir, v_updir, v_saveflash);  }
	                
	                System.out.println("isMove " + isMove);
	                if(isMove) connMgr.commit();
	*/
	                connMgr.commit();
	            }
	            else            connMgr.rollback();
	            
	          }
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(rs != null) { try { rs.close(); } catch (Exception e) {} }        	
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2;
     }


    /**
    평가문제 수정
    @param box     receive from the form object and session
    @return isOk   1:update success,0:update fail
    */
    public int updateTZ_etest(DBConnectionManager connMgr,
                            String  p_etestsubj,     int     p_etestnum,  String  p_etesttype,
                            String  p_etesttext,     String  p_exptext,   String  p_levels,   int p_selcnt, String  p_saveimage,   String  p_saveaudio, String p_savemovie, 
		                    String p_saveflash, String  p_realimage,   String  p_realaudio, String p_realmovie, String p_realflash, String p_luserid, String p_gubun ) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_EXAM table

            sql =  " update TZ_ETEST ";
            sql+=  "    set etesttype   = ?, ";
            sql+=  "        etesttext     = ?, ";
            sql+=  "        exptext  = ?, ";
            sql+=  "        levels   = ?, ";
            sql+=  "        selcnt   = ?, ";
            sql+=  "        saveimage   = ?, ";
            sql+=  "        saveaudio = ?, ";
            sql+=  "        savemovie = ?, ";
            sql+=  "        saveflash      = ?, ";
            sql+=  "        realimage   = ?, ";
            sql+=  "        realaudio = ?, ";
            sql+=  "        realmovie  = ?, ";
            sql+=  "        realflash  = ?, ";
            sql+=  "        luserid  = ?,  ";
            sql+=  "        ldate    = ?,  ";
            sql+=  "        etestgubun    = ?   ";            
            sql+=  "  where etestsubj     = ?  ";
            sql+=  "    and etestnum  = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, p_etesttype);
            pstmt.setString( 2, p_etesttext);
            pstmt.setString( 3, p_exptext);
            pstmt.setString( 4, p_levels);
            pstmt.setInt   ( 5, p_selcnt);
            pstmt.setString( 6, p_saveimage);
            pstmt.setString( 7, p_saveaudio);
            pstmt.setString( 8, p_savemovie);
            pstmt.setString( 9, p_saveflash);
            pstmt.setString(10, p_realimage);
            pstmt.setString(11, p_realaudio);
            pstmt.setString(12, p_realmovie);
            pstmt.setString(13, p_realflash);
            pstmt.setString(14, p_luserid);
            pstmt.setString(15, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(16, p_gubun);            
            pstmt.setString(17, p_etestsubj);
            pstmt.setInt(   18, p_etestnum);

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
    평가문제 삭제
    @param box            receive from the form object and session
    @return isOk1*isOk2   1:delete success,0:update fail
    */
     public int deleteQuestion(RequestBox box) throws Exception {

          DBConnectionManager connMgr = null;
          int isOk1 = 0;
          int isOk2 = 0;

          String v_etestsubj       = box.getString("p_etestsubj");
          int    v_etestnum     = box.getInt("p_etestnum");
          String v_duserid    = box.getSession("userid");

          try {
              connMgr = new DBConnectionManager();
              connMgr.setAutoCommit(false);

              isOk1 = deleteTZ_etest(connMgr, v_etestsubj, v_etestnum, v_duserid);
              if(isOk1>0){
                isOk2 = deleteTZ_etestsel(connMgr, v_etestsubj, v_etestnum);
              }  

            if (isOk1*isOk2 > 0) {
                connMgr.commit(); 
                box.put("p_sulnum", String.valueOf("0"));
            }else {
                connMgr.rollback();
            }
          }
          catch(Exception ex) {
              connMgr.rollback();
              ErrorManager.getErrorStackTrace(ex);
              throw new Exception(ex.getMessage());
          }
          finally {
              if (connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
          }
          return isOk1;
    }

    public int deleteTZ_etest(DBConnectionManager connMgr, String p_etestsubj, int v_etestnum, String p_duserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;      
        int isOk = 0;

        try {
            // 삭제체크
            sql = " select etest from tz_etestpaper  where etestsubj='"+p_etestsubj+"' ";
            sql+= " and ((etest like '%"+SQLString.Format(v_etestnum)+",%') or (etest  like '%,"+SQLString.Format(v_etestnum)+",%') or (etest = '"+SQLString.Format(v_etestnum)+"') or (etest like '%,"+SQLString.Format(v_etestnum)+"%')) ";
            ls = connMgr.executeQuery(sql);
    		if (ls.next()) {
    		    isOk = -2;
    		}


            if(isOk==0){ 
                            
                //update TZ_EXAM table
                sql =  " delete from TZ_ETEST ";
                sql+=  "  where etestsubj     = ?  ";
                sql+=  "    and etestnum  = ?  ";
    
                pstmt = connMgr.prepareStatement(sql);
                
    			pstmt.setString(1, p_etestsubj);
                pstmt.setInt   (2, v_etestnum);
    
                isOk = pstmt.executeUpdate();
            }
       }
       catch(Exception ex) {
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
       }
       finally {
           if(ls != null) { try { ls.close(); } catch (Exception e) {} }          
           if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
       }
       return isOk;
    }

    /**
    평가 지문삭제
    @param connMgr         Connection Manager
    @param s_etestsubj          과정코드
    @return isOk           1:delete success,0:update fail
    */
    public int deleteTZ_etestsel(DBConnectionManager connMgr, String p_etestsubj, int p_etestnum) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //delete tz_etestsel table
            sql =  " delete from tz_etestsel where etestsubj = ? and etestnum = ?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_etestsubj);
            pstmt.setInt   (2, p_etestnum);

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

    /**
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트
    */
    public ArrayList selectQuestionPool(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_etestsubj   = box.getString("p_etestsubj");
        String v_action = box.getStringDefault("p_action",  "change");
		String v_grcode = box.getSession("tem_grcode");

        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                //list = getQuestionPool(connMgr, v_etestsubj);
                list = getQuestionPool(connMgr, v_etestsubj,v_grcode);				
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
    @return ArrayList   평가문제 리스트
    */
    public ArrayList getQuestionPool(DBConnectionManager connMgr, String p_etestsubj) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = "select a.etestsubj,   a.etestnum, a.etestgubun,  a.etesttext,    a.exptext, a.etesttext,    a.exptext,  \n";
            sql+= "       a.levels,   a.selcnt,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,   \n";
            sql+= "       a.realaudio, a.realmovie, a.realflash,  b.codenm    etestgubunnm,  \n";
            sql+= "       c.codenm    levelsnm, d.etestsubjnm    etestsubjnm    \n";
            sql+= "  from tz_etest  a,  \n";
            sql+= "       tz_code   b,  \n";
            sql+= "       tz_code   c,   \n";
            sql+= "       tz_etestsubj   d   \n";
			sql+= "   where a.etestgubun  =  b.code(+)  \n";
            sql+= "   and   a.levels     = c.code  \n";
            sql+= "   and   a.etestsubj  = d.etestsubj  \n";
            sql+= "   and   a.etestsubj != " + SQLString.Format(p_etestsubj);
			//sql+= "   and b.gubun    =* " + SQLString.Format(ETestBean.ETEST_GUBUN);  // 문제분류			
			sql+= "   and b.gubun    = " + SQLString.Format(ETestBean.ETEST_GUBUN);  // 문제분류
            sql+= "   and c.gubun       = " + SQLString.Format(ETestBean.ETEST_LEVEL);  // 문제난이도
            sql+= " order by a.etestsubj, a.etestnum  \n";

			System.out.println("ETestQuestionBean  E-test pool 리스트:"+sql);
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
    @param box          receive from the form object and session
    @return ArrayList   평가문제 리스트 오버로딩- 인자 3개
    */
    public ArrayList getQuestionPool(DBConnectionManager connMgr, String p_etestsubj,String p_grcode) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        try {
            sql = "select a.etestsubj,   a.etestnum, a.etestgubun,  a.etesttext,    a.exptext, a.etesttext,    a.exptext,  \n";
            sql+= "       a.levels,   a.selcnt,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,   \n";
            sql+= "       a.realaudio, a.realmovie, a.realflash,  b.codenm    etestgubunnm,  \n";
            sql+= "       c.codenm    levelsnm, d.etestsubjnm    etestsubjnm    \n";
            sql+= "  from tz_etest  a,  \n";
            sql+= "       tz_code   b,  \n";
            sql+= "       tz_code   c,   \n";
            sql+= "       tz_etestsubj   d   \n";
			sql+= "   where a.etestgubun  =  b.code(+)  \n";
            sql+= "   and   a.levels     = c.code  \n";
            sql+= "   and   a.etestsubj  = d.etestsubj  \n";
            sql+= "   and   a.etestsubj != " + SQLString.Format(p_etestsubj);
			//sql+= "   and b.gubun    =* " + SQLString.Format(ETestBean.ETEST_GUBUN);  // 문제분류			
			sql+= "   and b.gubun    = " + SQLString.Format(ETestBean.ETEST_GUBUN);  // 문제분류
            sql+= "   and c.gubun       = " + SQLString.Format(ETestBean.ETEST_LEVEL);  // 문제난이도
			sql+= " and d.grcode = "+SQLString.Format(p_grcode);
            sql+= " order by a.etestsubj, a.etestnum  \n";

			System.out.println("ETestQuestionBean  E-test pool 리스트:"+sql);
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
    평가 문제 를 찾기위한 Pool
    @param box          receive from the form object and session
    @return ArrayList   
    */
    public ArrayList selectQuestionPoolList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        
        
        try {      
           
			String v_grcode = box.getSession("tem_grcode");
            String ss_searchtype = box.getString("s_searchtype");
            String ss_searchtext = box.getString("s_searchtext");
            
            String v_action  = box.getString("p_action");
            String v_etestsubj  = box.getString("p_etestsubj");
             
            list = new ArrayList();
            
            if (v_action.equals("go")) {   
                connMgr = new DBConnectionManager();
                
                sql = "select a.etestsubj,   a.etestnum, a.etestgubun, ";
                sql+= "       a.etesttext,    a.exptext,     ";
                sql+= "       a.levels,   a.selcnt,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
                sql+= "       b.codenm    etestgubunnm, ";
                sql+= "       c.codenm    levelsnm,    ";
                sql+= "       d.etestsubjnm    etestsubjnm    ";
                sql+= "  from tz_etest   a, ";
                sql+= "       tz_code   b, ";
                sql+= "       tz_code   c,  ";
                sql+= "       tz_etestsubj   d  ";
				sql+= "   where a.etestgubun  =  b.code(+) ";
                sql+= "   and a.levels   = c.code ";
                sql+= "   and a.etestsubj   = d.etestsubj ";
                sql+= "   and a.etestsubj     != " + SQLString.Format(v_etestsubj);
				sql+= "   and d.grcode   = " + SQLString.Format(v_grcode);				
                //sql+= "   and b.gubun    = " + SQLString.Format(ETestBean.ETEST_TYPE);
				//sql+= "   and b.gubun    =  " + SQLString.Format(ETestBean.ETEST_GUBUN);    // 문제분류
				sql+= "   and b.gubun   = " + SQLString.Format(ETestBean.ETEST_GUBUN);    // 문제분류				
                sql+= "   and c.gubun    = " + SQLString.Format(ETestBean.ETEST_LEVEL);    // 난이도
    
                if (ss_searchtype.equals("1")) {       // 과정명
                    //sql+= "  and d.etestsubjnm like " + SQLString.Format("%"+ss_searchtext+"%");
                    sql+= "  and (lower(d.etestsubjnm) like " + SQLString.Format("%"+ss_searchtext+"%") + " or upper(d.etestsubjnm) like "+ SQLString.Format("%"+ss_searchtext+"%") +")";
                } 
                else if (ss_searchtype.equals("2")) {  // 문제분류
                    sql+= "  and b.codenm like " + SQLString.Format("%"+ss_searchtext+"%");
                } 
                else if (ss_searchtype.equals("3")) {  // 문제
                    //sql+= "  and a.etesttext like " + SQLString.Format("%"+ss_searchtext+"%");
                    sql+= "  and (lower(a.etesttext) like " + SQLString.Format("%"+ss_searchtext+"%") + " or upper(a.etesttext) like "+ SQLString.Format("%"+ss_searchtext+"%") +")";
                }
    			else if (ss_searchtype.equals("4")) {  // 난이도
                    sql+= "  and c.codenm like " + SQLString.Format("%"+ss_searchtext+"%");
                }
    
                sql+= " order by a.etestsubj, a.etestnum ";
                
                ls = connMgr.executeQuery(sql);//System.out.println(sql);
                
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    
                    list.add(dbox);
                }
            }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }	

    public int insertQuestionPool(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        StringTokenizer st = null;
        String v_tokens  = "";
		ArrayList list = new ArrayList();
		DataBox dbox = null;

        String  v_etestsubj     = box.getString("p_etestsubj");
        String v_luserid    = box.getSession("userid");
        int v_etestnum    = 0;

		String s_etestsubj = "";
		int s_etestnum = 0;

        Vector  v_checks    = box.getVector("p_checks");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            v_etestnum = getEtestnumSeq(v_etestsubj);

            for(int i=0; i < v_checks.size(); i++){
               v_tokens = (String)v_checks.elementAt(i); 
			   st = new StringTokenizer(v_tokens,"|");
			       s_etestsubj = (String)st.nextToken();
				   s_etestnum = Integer.parseInt((String)st.nextToken());
                   list = getExampleData(connMgr, s_etestsubj, s_etestnum);
                   dbox = (DataBox)list.get(0);

                   String v_lesson = "00";
				   String v_etesttype = dbox.getString("d_etesttype");
				   String v_etesttext = dbox.getString("d_etesttext");
				   String v_exptext = dbox.getString("d_exptext");
				   String v_levels = dbox.getString("d_levels");
				   int v_selcnt = dbox.getInt("d_selcnt");
				   String v_saveimage = dbox.getString("d_saveimage");
				   String v_saveaudio = dbox.getString("d_saveaudio");
				   String v_savemovie = dbox.getString("d_savemovie");
				   String v_saveflash = dbox.getString("d_saveflash");
				   String v_realimage = dbox.getString("d_realimage");
				   String v_realaudio = dbox.getString("d_realaudio");
				   String v_realmovie = dbox.getString("d_realmovie");
				   String v_realflash = dbox.getString("d_realflash");
				   String v_gubun     = dbox.getString("d_gubun");
				   
               isOk = insertTZ_etest(connMgr, v_etestsubj, v_etestnum, v_etesttype, v_etesttext, v_exptext, v_levels, v_selcnt, v_saveimage, v_saveaudio, v_savemovie, v_saveflash, v_realimage, v_realaudio, v_realmovie, v_realflash, v_luserid, v_gubun );

			   sql =  "insert into TZ_ETESTSEL(etestsubj, etestnum, selnum, seltext, isanswer, luserid, ldate) ";
               sql+=  " values (?, ?, ?, ?, ?, ?, ?)";
               pstmt = connMgr.prepareStatement(sql);

               for (int j=0; j<list.size(); j++) {
                  dbox  = (DataBox)list.get(j);
				  int v_selnum = dbox.getInt("d_selnum");
				  String v_seltext = dbox.getString("d_seltext");
				  String v_isanswer = dbox.getString("d_isanswer");
                  
					  isOk = insertTZ_etestsel(pstmt, v_etestsubj,  v_etestnum, v_selnum, v_seltext, v_isanswer, v_luserid);
               }
			   v_etestnum++;
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


    /**
    문제번호
    @param v_etestsubj          과정코드
    @return getEtestnumSeq  문제번호
    */
    public int getEtestnumSeq(String v_etestsubj) throws Exception {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn","etestnum");
        maxdata.put("seqtable","tz_etest");
        maxdata.put("paramcnt","1");
        maxdata.put("param0","etestsubj");
        maxdata.put("etestsubj",   SQLString.Format(v_etestsubj));

        return SelectionUtil.getSeq(maxdata);
    }

    /**
    과정명
    @param box       receive from the form object and session
    @return result   과정명
    */
     public static String get_subjnm(String etestsubj) throws Exception{
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
        String result ="";

         try {
             connMgr = new DBConnectionManager();
             sql = " select  etestsubjnm  from TZ_ETESTSUBJ where etestsubj="+SQLString.Format(etestsubj);
             ls = connMgr.executeQuery(sql);
             if(ls.next())  result = ls.getString("etestsubjnm");
         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex);
             throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
         }finally {
             if(ls != null) { try { ls.close(); }catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }

         return result;
    }

    /**
    *  대상자 설문지 셀렉트박스 (RequestBox, 셀렉트박스명,선택값,이벤트명)
	*  TZ_SULPAPER 이용
    */
    public static String getGroupSelect (String p_grcode, String p_upperclass, String name, String selected, String event) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        DataBox     dbox = null;

        result = "  <SELECT name=" + name + " " + event + " >  \n";

        result += " <option value='0'>그룹을 선택하세요.</option>  \n";

        try {
            connMgr = new DBConnectionManager();

			sql = "select grcode,       etestsubj,     etestsubjnm    ";
            sql+= "  from tz_etestsubj ";
            sql+= " where  grcode = " + StringManager.makeSQL(p_grcode);
            if (!p_upperclass.equals("ALL"))
            sql+= "   and upperclass = " + SQLString.Format(p_upperclass);
            sql+= " order by etestsubj ";

            ls = connMgr.executeQuery(sql);

                String v_null_test = "";
                String v_etestsubj_bef = "";

            while (ls.next()) {

                    dbox = ls.getDataBox();

                result += " <option value=" + dbox.getString("d_etestsubj");
                if (selected.equals(dbox.getString("d_etestsubj"))) {
                    result += " selected ";
                }
                
                result += ">" + dbox.getString("d_etestsubjnm") + "</option>  \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result += "  </SELECT>  \n";
        return result;
    } 
}