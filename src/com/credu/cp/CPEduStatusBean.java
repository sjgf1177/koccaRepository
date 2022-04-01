//**********************************************************
//  1. 제      목: 과정관리 빈
//  2. 프로그램명: CPEduStatusBean.java
//  3. 개      요: 과정관리관리 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 12. 21
//  7. 수      정: 이나연 05.11.17 _ Oracle -> MSSQL (OuterJoin) 수정
//**********************************************************

package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

/**
*외주과정관리
*<p>제목:CPEduStatusBean.java</p>
*<p>설명:외주과정관리 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/

public class CPEduStatusBean {
    private ConfigSet config;
    private int row;
	    	
    public CPEduStatusBean() {
        try{
            config = new ConfigSet();  
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }	
    }

    /**
    외주업체 과정리스트
    @param	box			receive from the form object and session
    @return	ArrayList	외주업체 과정리스트
    */
    public ArrayList selectCourseList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //BulletinData data = null;
        DataBox dbox = null;
        
        String v_searchtext = box.getString("p_searchtext");
        String v_cp = box.getString("p_cp");			//외주업체
        String v_grcode = box.getString("s_grcode");	//교육주관
        String v_gyear = box.getString("s_gyear");		//교육년도
        String v_grseq = box.getString("s_grseq");		//교육차수
        //String v_select = box.getString("p_select");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();

			if(v_gyear.equals("")){
				v_gyear = FormatDate.getDate("yyyy");
				box.put("s_gyear",v_gyear);
			}

			sql += " select            \n";
			sql += "   subj,           \n";
            sql += "   subjnm,         \n";
            sql += "   year,           \n";
            sql += "   subjseq,        \n";
            sql += "   subjseqgr,      \n";
            sql += "   propstart,      \n";
            sql += "   propend,        \n";
            sql += "   edustart,       \n";
            sql += "   eduend,         \n";
            sql += "   cpnm,           \n";
            sql += "   cpsubjseq,      \n"; 
            sql += "   usercnt,        \n";
            sql += "   upcnt,          \n";
            sql += "   recentdt        \n";
            sql += " from              \n";
            sql += "   (               \n";
			sql += "   select          \n";
            sql += "     b.subj,       \n";
            sql += "     b.subjnm,     \n";
            sql += "     b.year,       \n";
            sql += "     b.subjseq,    \n";
            sql += "     b.subjseqgr,  \n";
            sql += "     b.propstart,  \n";
            sql += "     b.propend,    \n";
            sql += "     b.edustart,   \n";
            sql += "     b.eduend,     \n";
            sql += "     c.cpnm,       \n";
            sql += "     b.cpsubjseq,  \n"; 
            sql += "     (select count(userid) from tz_student where subj = b.subj and subjseq = b.subjseq and year = b.year) as usercnt,   \n";
            sql += "     (select count(*)       from tz_CPUPDATESTATUS where subj = b.subj and subjseq = b.subjseq and year = b.year) as upcnt,   \n";
            sql += "     (select max(indate)   from tz_CPUPDATESTATUS where subj = b.subj and subjseq = b.subjseq and year = b.year) as recentdt  \n";
            sql += "   from            \n";
            sql += "     vz_scsubjseq b,  \n";
            sql += "     tz_cpinfo c      \n";
			sql += "     where            \n";
			sql += "     1=1              \n";
            sql += "     and b.owner = c.cpseq   \n";
			
			//교육그룹검색(교육주관)
			if(!v_grcode.equals("")){
				sql += " and b.grcode = " + SQLString.Format(v_grcode);

				//과정명검색
				if ( !v_searchtext.equals("")) {	//    검색어가 있으면
	                sql += " and lower(b.subjnm) like " + SQLString.Format("%" + v_searchtext.toLowerCase() + "%");
	            }
	            
	            sql += " and b.gyear = " + SQLString.Format(v_gyear);

	            //교육차수 검색
	            if(!v_grseq.equals("ALL")){
	            	sql += " and b.grseq = " + SQLString.Format(v_grseq);
	            }

	            //외주업체 검색
	            if(!v_cp.equals("ALL")){
	                sql += " and b.owner = " + SQLString.Format(v_cp) ;
	            }
	        }
            sql += " ) cpcourselist";
            sql += " where ";
            sql += " cpcourselist.usercnt != 0";
            
            System.out.println(sql);

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성
            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);                    //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();    //     전체 row 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                
                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  


    /**
    과정데이타 조회
    @param box          receive from the form object and session
    @return CPSubjectData   과정데이타
    */
    public DataBox SelectSubjectData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        CPSubjectData data = null;
        String sql  = "";
        DataBox  dbox = null;
        String v_subj = box.getString("p_subj");
        try {
            sql =  " select a.subj,       a.subjnm,         a.isonoff,        a.subjclass,                ";
            sql+=  "        a.upperclass, a.middleclass,    a.lowerclass,     a.specials, a.contenttype,  ";
            sql+=  "        a.muserid,    a.cuserid,        a.isuse,          a.isgoyong,   a.ispropose,  ";
            sql+=  "        a.biyong,     a.edudays,        a.studentlimit,   a.usebook,                  ";
            sql+=  "        a.bookprice,  a.owner,          a.producer,       a.crdate,     a.language,   ";
            sql+=  "        a.server,     a.dir,            a.eduurl,         a.vodurl,     a.preurl,     ";
            sql+=  "        a.ratewbt,    a.ratevod,        a.framecnt,       a.env,                      ";
            sql+=  "        a.tutor,      a.bookname,       a.sdesc,          a.warndays,   a.stopdays,   ";
            sql+=  "        a.point,      a.edulimit,       a.gradscore,      a.gradstep,                 ";
            sql+=  "        a.wstep,      a.wmtest,         a.wftest,         a.wreport,                  ";
            sql+=  "        a.wact,       a.wetc1,          a.wetc2,          a.goyongprice,              ";
            sql+=  "        a.place,      a.isessential,    a.score,          a.edumans subjtarget,       ";
            sql+=  "        a.inuserid,   a.indate,         a.luserid,        a.ldate,                    ";
            sql+=  "        b.name        cuseridnm,        c.name museridnm,                             ";
            sql+=  "        d.compnm  producernm,           e.compnm ownernm, a.proposetype, a.edumans,   ";
            sql+=  "        a.edutimes,   a.edutype,        a.intro,          a.explain ,                 ";
			sql+=  "	    a.whtest,     a.gradreport,		a.gradexam, a.gradftest, a.gradhtest,         ";
			sql+=  "		a.isoutsourcing,	NVL(a.cpsubj,'') cpsubj,	a.conturl,  a.eduprice,       ";
			sql+=  "		a.musertel,   a.cpapproval,                                                   ";
			sql+=  "        a.introducefilenamereal,                                                      ";  
			sql+=  "        a.introducefilenamenew,                                                       ";
			sql+=  "        a.informationfilenamereal,                                                    ";
			sql+=  "        a.informationfilenamenew                                                      ";
            sql+=  "   from tz_subj a,  tz_member b,  tz_member c,  tz_comp d,  tz_comp e                 ";
			// 수정일 : 05.11.17 수정자 : 이나연 _(+)  수정
//          sql+=  "  where a.cuserid  = b.userid(+)                                                      ";
//          sql+=  "    and a.muserid  = c.userid(+)                                                      ";
//          sql+=  "    and a.producer = d.comp(+)                                                        ";
//          sql+=  "    and a.owner    = e.comp(+)                                                        ";
			sql+=  "  where a.cuserid   =  b.userid(+)                                                      ";
            sql+=  "    and a.muserid   =  c.userid(+)                                                      ";
            sql+=  "    and a.producer  =  d.comp(+)                                                        ";
            sql+=  "    and a.owner     =  e.comp(+)                                                        ";
            sql+=  "    and a.subj     =  " + SQLString.Format(v_subj);

            System.out.println(sql);

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //data=new CPSubjectData();
                //data.setSubj        (ls.getString("subj"));
                //data.setCPSubj      (ls.getString("cpsubj"));
                //data.setSubjnm      (ls.getString("subjnm"));
                //data.setIsonoff     (ls.getString("isonoff"));
                //data.setSubjclass   (ls.getString("subjclass"));
                //data.setUpperclass  (ls.getString("upperclass"));
                //data.setMiddleclass (ls.getString("middleclass"));
                //data.setLowerclass  (ls.getString("lowerclass"));
                //data.setSpecials    (ls.getString("specials"));
                //data.setContenttype (ls.getString("contenttype"));
                //data.setMuserid     (ls.getString("muserid"));
                //data.setCuserid     (ls.getString("cuserid"));
                //data.setIsuse       (ls.getString("isuse"));
                //data.setIsgoyong    (ls.getString("isgoyong"));
                //data.setIspropose   (ls.getString("ispropose"));
                //data.setBiyong      (ls.getInt("biyong"));
                //data.setEdudays     (ls.getInt("edudays"));
                //data.setStudentlimit(ls.getInt("studentlimit"));
                //data.setUsebook     (ls.getString("usebook"));
                //data.setBookprice   (ls.getInt("bookprice"));
                //data.setOwner       (ls.getString("owner"));
                //data.setProducer    (ls.getString("producer"));
                //data.setCrdate      (ls.getString("crdate"));
                //data.setLanguage    (ls.getString("language"));
                //data.setServer      (ls.getString("server"));
                //data.setDir         (ls.getString("dir"));
                //data.setEduurl      (ls.getString("eduurl"));
                //data.setVodurl      (ls.getString("vodurl"));
                //data.setPreurl      (ls.getString("preurl"));
                //data.setConturl     (ls.getString("conturl"));
                //data.setRatewbt     (ls.getInt("ratewbt"));
                //data.setRatevod     (ls.getInt("ratevod"));
                //data.setFramecnt    (ls.getInt("framecnt"));
                //data.setEnv         (ls.getString("env"));
                //data.setTutor       (ls.getString("tutor"));
                //data.setBookname    (ls.getString("bookname"));
                //data.setSdesc       (ls.getString("sdesc"));
                //data.setWarndays    (ls.getInt("warndays"));
                //data.setStopdays    (ls.getInt("stopdays"));
                //data.setPoint       (ls.getInt("point"));
                //data.setEdulimit    (ls.getInt("edulimit"));
                //data.setGradscore   (ls.getInt("gradscore"));
                //data.setGradstep    (ls.getInt("gradstep"));
                //data.setWstep       (ls.getDouble("wstep"));
                //data.setWmtest      (ls.getDouble("wmtest"));
                //data.setWftest      (ls.getDouble("wftest"));
                //data.setWreport     (ls.getDouble("wreport"));
                //data.setWact        (ls.getDouble("wact"));
                //data.setWetc1       (ls.getDouble("wetc1"));
                //data.setWetc2       (ls.getDouble("wetc2"));
                //data.setGoyongprice (ls.getInt("goyongprice"));
                //data.setPlace       (ls.getString("place"));
                //data.setIsessential (ls.getString("isessential"));
                //data.setScore       (ls.getInt("score"));
                //data.setSubjtarget  (ls.getString("subjtarget"));
                //data.setInuserid    (ls.getString("inuserid"));
                //data.setIndate      (ls.getString("indate"));
                //data.setLuserid     (ls.getString("luserid"));
                //data.setLdate       (ls.getString("ldate"));
                //data.setCuseridnm   (ls.getString("cuseridnm"));
                //data.setMuseridnm   (ls.getString("museridnm"));
                //data.setProducernm  (ls.getString("producernm"));
                //data.setOwnernm     (ls.getString("ownernm"));
                //data.setProposetype (ls.getString("proposetype"));
				//data.setEdumans     (ls.getString("edumans"));
                //data.setEdutimes    (ls.getInt("edutimes"));
                //data.setEdutype     (ls.getString("edutype"));
                //data.setIntro       (ls.getString("intro"));
                //data.setExplain     (ls.getString("explain"));
				//data.setWhtest      (ls.getDouble("whtest"));
				//data.setGradexam    (ls.getInt("gradexam"));//이수기준-중간평가
				//data.setGradftest   (ls.getInt("gradftest"));//이수기준-최종평가
				//data.setGradhtest   (ls.getInt("gradhtest"));//이수기준-형성평가
                //data.setGradreport  (ls.getInt("gradreport"));
                //data.setUsesubjseqapproval	(ls.getString("usesubjseqapproval"));
                //data.setUseproposeapproval	(ls.getString("useproposeapproval"));
				//data.setUsemanagerapproval	(ls.getString("usemanagerapproval"));
				//data.setRndcreditreq		(ls.getDouble("rndcreditreq"));
                //data.setRndcreditchoice		(ls.getDouble("rndcreditchoice"));
				//data.setRndcreditadd		(ls.getDouble("rndcreditadd"));
				//data.setRndcreditdeduct		(ls.getDouble("rndcreditdeduct"));
				//data.setIsablereview		(ls.getString("isablereview"));
				//data.setIsoutsourcing		(ls.getString("isoutsourcing"));
				
				dbox = ls.getDataBox();
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
        return dbox;
    }
    
    

    /**
    선택된 과정정보 수정 - 사이버
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
    */
     public int UpdateSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        String v_luserid = box.getSession("userid");
        
        
        String v_introducefilenamereal    = box.getRealFileName("p_introducefile");
        String v_introducefilenamenew     = box.getNewFileName ("p_introducefile");
        String v_informationfilenamereal = box.getRealFileName("p_informationfile");
        String v_informationfilenamenew  = box.getNewFileName ("p_informationfile");

        String v_introducefile0   = box.getStringDefault("p_introducefile0", "0");
        String v_informationfile0 = box.getStringDefault("p_informationfile0", "0");

        if(v_introducefilenamereal.length()       == 0) {
            if (v_introducefile0.equals("1")){
                v_introducefilenamereal    = "";
                v_introducefilenamenew     = "";
            } else {
                v_introducefilenamereal    = box.getString("p_introducefile1");
                v_introducefilenamenew     = box.getString("p_introducefile2");
            }
        }

        if(v_informationfilenamereal.length()       == 0) {
            if (v_informationfile0.equals("1")){
                v_informationfilenamereal    = "";
                v_informationfilenamenew     = "";
            } else {
                v_informationfilenamereal    = box.getString("p_informationfile1");
                v_informationfilenamenew     = box.getString("p_informationfile2");
            }
        }
        
        

        try {
            connMgr = new DBConnectionManager();

            //update TZ_SUBJ table
            sql = " update TZ_SUBJ ";
            sql+= "    set subjnm       = ?, ";
            sql+= "        eduurl       = ?, ";
            sql+= "        preurl       = ?, ";
            sql+= "        cpsubj       = ?, ";
            sql+= "        conturl      = ?, ";
            sql+= "        point        = ?, ";
            sql+= "        gradscore    = ?, ";
            sql+= "        gradstep     = ?, ";
            sql+= "        wstep        = ?, ";
            sql+= "        wmtest       = ?, ";
            sql+= "        wftest       = ?, ";
            sql+= "        wreport      = ?, ";
            sql+= "        wact         = ?, ";
            sql+= "        wetc1        = ?, ";
            sql+= "        wetc2        = ?, ";
            sql+= "        luserid      = ?, ";
            sql+= "        ldate        = ?, ";
            sql+= "        edumans      = ?, ";
            sql+= "        intro        = ?, ";
            sql+= "        explain      = ?, ";      
			sql+= "	 	   gradexam		= ?, ";
			sql+= "		   gradreport	= ?, ";
			sql+= "        whtest		= ?, ";
			sql+= "		   gradftest	= ?, ";
			sql+= "		   gradhtest	= ?, ";
			sql+= "		   edutimes		= ?, ";
			sql+= "		   biyong       = ?, ";
			sql+= "		   bookprice    = ?, ";
			sql+= "		   musertel		= ?, ";
			sql+= "        introducefilenamereal   = ?, ";
            sql+= "        introducefilenamenew    = ?, ";
            sql+= "        informationfilenamereal = ?, ";
            sql+= "        informationfilenamenew  = ? ";
            sql+= "  where subj         = ? ";

            pstmt = connMgr.prepareStatement(sql);			
			
            pstmt.setString(1, box.getString("p_subjnm"));
            pstmt.setString(2, box.getString("p_eduurl"));
            pstmt.setString(3, box.getString("p_preurl"));
            pstmt.setString(4, box.getString("p_cpsubj"));
            pstmt.setString(5, box.getString("p_conturl"));
            pstmt.setInt   (6, box.getInt   ("p_point"));
            pstmt.setInt   (7, box.getInt   ("p_gradscore"));
            pstmt.setInt   (8, box.getInt   ("p_gradstep"));
            pstmt.setDouble(9, box.getDouble("p_wstep"));
            pstmt.setDouble(10, box.getDouble("p_wmtest"));
            pstmt.setDouble(11, box.getDouble("p_wftest"));
            pstmt.setDouble(12, box.getDouble("p_wreport"));
            pstmt.setDouble(13, box.getDouble("p_wact"));
            pstmt.setDouble(14, box.getDouble("p_wetc1"));
            pstmt.setDouble(15, box.getDouble("p_wetc2"));
            pstmt.setString(16, v_luserid);
            pstmt.setString(17, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(18, box.getString("p_edumans"));
            pstmt.setString(19, box.getString("p_intro"));
            pstmt.setString(20, box.getString("p_explain"));
			
			//------------------------------------------------------------------------------//
            pstmt.setInt   (21, box.getInt("p_gradexam")); 					//이수기준(중간평가)
            pstmt.setInt   (22, box.getInt("p_gradreport")); 				//이수기준(리포트)
            pstmt.setInt   (23, box.getInt("p_whtest")); 					//가중치(형성평가)

            pstmt.setString(24, box.getString("p_gradftest"));				//이수기준-최종평가
            pstmt.setString(25, box.getString("p_gradhtest"));				//이수기준-형성평가
			//------------------------------------------------------------------------------//
			
			pstmt.setInt(26, box.getInt("p_edutimes"));				//학습시간
			pstmt.setInt(27, box.getInt("p_biyong"));				
			pstmt.setInt(28, box.getInt("p_bookprice"));
			pstmt.setString(29, box.getString("p_musertel"));
			pstmt.setString(30, v_introducefilenamereal);          //과정소개 이미지
            pstmt.setString(31, v_introducefilenamenew);           //과정소개 이미지
            pstmt.setString(32, v_informationfilenamereal);        //파일(목차)
            pstmt.setString(33, v_informationfilenamenew);         //파일(목차)
            pstmt.setString(34, box.getString("p_subj"));

            isOk = pstmt.executeUpdate();

        }
        catch(Exception ex) {
            connMgr.rollback();
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
    새로운 과정코드 등록 - 사이버
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
     public int InsertSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
        String sql = "";
		String sql2 = "";
		
        int isOk = 0;
		int isOk2 = 0;

        String v_subj     = "";
        String v_luserid  = box.getSession("userid");
		String v_grcode	  = box.getStringDefault("s_grcode","ALL");	//과정리스트에서의 SELECTBOX 교육주관 코드

        try {
            connMgr = new DBConnectionManager();

            //insert TZ_SUBJ table
            sql =  "insert into TZ_SUBJ ( ";                                                    
            sql+=  " subj,      subjnm,     isonoff,     subjclass, ";                           //01 ~ 04
            sql+=  " upperclass,middleclass,lowerclass,  specials,  ";                           //05 ~ 08
            sql+=  " muserid,   cuserid,    isuse,       isgoyong,   ispropose, ";               //09 ~ 13
            sql+=  " biyong,    edudays,    studentlimit,usebook,  ";                            //14 ~ 17
            sql+=  " bookprice, owner,      producer,    crdate,     language,  ";               //18 ~ 22
            sql+=  " server,    dir,        eduurl,      vodurl,     preurl,    ";               //23 ~ 27
            sql+=  " ratewbt,   ratevod,    framecnt,    env,      ";                            //28 ~ 31
            sql+=  " tutor,     bookname,   sdesc,       warndays,   stopdays, ";                //32 ~ 36
            sql+=  " point,     edulimit,   gradscore,   gradstep,   ";                          //37 ~ 40
            sql+=  " wstep,     wmtest,     wftest,      wreport,    ";                          //41 ~ 44
            sql+=  " wact,      wetc1,      wetc2,       goyongprice, ";                         //45 ~ 48
            sql+=  " inuserid,  indate,     luserid,     ldate,     proposetype, ";              //49 ~ 53
            sql+=  " edumans,   intro,      explain,     isessential,  score, contenttype,   ";  //54 ~ 59
            sql+=  " gradexam,	gradreport,	whtest,			 usesubjseqapproval, ";				 //60 ~ 63
			sql+=  " useproposeapproval,		usemanagerapproval,	rndcreditreq,	rndcreditchoice, ";         //64 ~ 67
			sql+=  " rndcreditadd,rndcreditdeduct,		 isoutsourcing,	isablereview, rndjijung,  ";			//68 ~ 71
			sql+=  " cpapproval) ";
            sql+=  " values (";
            sql+=  " ?,    ?,    ?,    ?, ";				//01 ~ 04
            sql+=  " ?,    ?,    ?,    ?, ";				//05 ~ 08
            sql+=  " ?,    ?,    ?,    ?, ?,";			//09 ~ 13
            sql+=  " ?,    ?,    ?,    ?, ";				//14 ~ 17
            sql+=  " ?,    ?,    ?,    ?, ?, ";			//18 ~ 22
            sql+=  " ?,    ?,    ?,    ?, ?,";			//23 ~ 27
            sql+=  " ?,    ?,    ?,    ?,  ";				//28 ~ 31
            sql+=  " ?,    ?,    ?,    ?, ?,";			//32 ~ 36
            sql+=  " ?,    ?,    ?,    ?, ";				//37 ~ 40
            sql+=  " ?,    ?,    ?,    ?, ";				//41 ~ 44
            sql+=  " ?,    ?,    ?,    ?, ";				//45 ~ 48
            sql+=  " ?,    ?,    ?,    ?,   ?, ";		//49 ~ 53
            sql+=  " ?,    ?,    ?,    ?,   ?, ?,";	        //54 ~ 59
            sql+=  " ?,    ?,    ?,    ?,     ";		    //60 ~ 63
            sql+=  " ?,    ?,    ?,    ?,     ";			//64 ~ 67
            sql+=  " ?,    ?,    ?,    ?, ?,   ";			//68 ~ 71
            sql+=  " ?  ) ";

            pstmt = connMgr.prepareStatement(sql);


            v_subj = this.getMaxSubjcode(connMgr, box.getString("s_upperclass"), box.getString("s_middleclass"));

            //ErrorManager.systemOutPrintln(box);

            pstmt.setString( 1, v_subj);																		//과정코드
            pstmt.setString( 2, box.getString("p_subjnm"));									//과정명
            pstmt.setString( 3, box.getString("p_isonoff"));								//사이버/집합구분
            pstmt.setString( 4, box.getString("s_upperclass") + box.getString("s_middleclass") + "000"); //분류코드
            pstmt.setString( 5, box.getString("s_upperclass"));								//대분류
            pstmt.setString( 6, box.getString("s_middleclass")); 							//중분류
            pstmt.setString( 7, box.getStringDefault("s_lowerclass","000")); 				//소분류
            pstmt.setString( 8, box.getString("p_specials"));								//과정특성(YYY)
            pstmt.setString( 9, box.getString("p_muserid")); 								//운영담당 ID
            pstmt.setString(10, box.getString("p_cuserid"));								//컨텐츠담당 ID
            pstmt.setString(11, box.getString("p_isuse"));									//사용여부(Y/N)
            pstmt.setString(12, box.getString("p_isgoyong"));								//고용보험여부(Y/N)
            pstmt.setString(13, box.getStringDefault("p_ispropose","Y"));	 	//[X]수강신청여부(Y/N)
            pstmt.setInt   (14, box.getInt   ("p_biyong"));             		//수강료
            pstmt.setInt   (15, box.getInt   ("p_edudays"));            		//학습일차
            pstmt.setInt   (16, box.getInt   ("p_studentlimit"));       		//정원
            pstmt.setString(17, box.getString("p_usebook"));            		//교재사용여부
            pstmt.setInt   (18, box.getInt   ("p_bookprice"));          		//교재비
            pstmt.setString(19, box.getString("p_owner"));              		//과정소유자
            pstmt.setString(20, box.getString("p_producer"));           		//과정제공자
            pstmt.setString(21, box.getString("p_crdate"));					    		//제작일자
            pstmt.setString(22, box.getString("p_language"));           		//[X]언어선택
            pstmt.setString(23, box.getString("p_server"));             		//[X]서버
            pstmt.setString(24, box.getString("p_dir"));                		//컨텐츠경로
            pstmt.setString(25, box.getString("p_eduurl"));             		//교육URL
            pstmt.setString(26, box.getString("p_vodurl"));             		//VOD경로
            pstmt.setString(27, box.getString("p_preurl"));      						//맛보기URL
            pstmt.setInt   (28, box.getInt   ("p_ratewbt"));     						//학습방법(WBT%)
            pstmt.setInt   (29, box.getInt   ("p_ratevod"));								//학습방법(VOD%)
            pstmt.setInt   (30, box.getInt   ("p_framecnt"));								//총프레임수
            pstmt.setString(31, box.getString("p_env"));										//학습환경
            pstmt.setString(32, box.getString("p_tutor"));									//강사설명
            pstmt.setString(33, box.getString("p_bookname"));       				//교재명
            pstmt.setString(34, box.getString("p_sdesc"));     							//[X]비고
            pstmt.setInt   (35, box.getInt   ("p_warndays"));								//[X]학습경고적용일
            pstmt.setInt   (36, box.getInt   ("p_stopdays"));								//[X]학습중지적용일
            pstmt.setInt   (37, box.getInt   ("p_point"));									//이수점수
            pstmt.setInt   (38, box.getInt   ("p_edulimit"));								//1일최대학습량
            pstmt.setInt   (39, box.getInt   ("p_gradscore"));							//이수기준-점수
            pstmt.setInt   (40, box.getInt   ("p_gradstep"));								//이수기준-진도율
            pstmt.setDouble(41, box.getDouble("p_wstep"));									//가중치-진도율
            pstmt.setDouble(42, box.getDouble("p_wmtest"));									//가중치-중간평가
            pstmt.setDouble(43, box.getDouble("p_wftest"));									//가중치-최종평가
            pstmt.setDouble(44, box.getDouble("p_wreport"));								//가중치-리포트
            pstmt.setDouble(45, box.getDouble("p_wact"));										//가중치-액티비티
            pstmt.setDouble(46, box.getDouble("p_wetc1"));									//가중치-기타1
            pstmt.setDouble(47, box.getDouble("p_wetc2"));									//가중치-기타2
            pstmt.setInt   (48, box.getInt("p_goyongprice"));								//고용보험환급금액
            pstmt.setString(49, v_luserid);																	//생성자
            pstmt.setString(50, FormatDate.getDate("yyyyMMddHHmmss"));			//생성일
            pstmt.setString(51, v_luserid);																	//최종수정자
            pstmt.setString(52, FormatDate.getDate("yyyyMMddHHmmss"));			//최종수정일(ldate)
            pstmt.setString(53, box.getString("p_proposetype"));						//수강신청구분(TZ_CODE GUBUN='0019')
            pstmt.setString(54, box.getStringDefault("p_edumans",""));		//맛보기 교육대상
            pstmt.setString(55, box.getStringDefault("p_intro",""));				//맛보기 교육목적
            pstmt.setString(56, box.getStringDefault("p_explain",""));			//맛보기 교육내용
            pstmt.setString(57, box.getStringDefault("p_isessential","D"));	//[X]과정구분(의무-D,필수-S,선택(직위별)-W,선택(직군별)-T)
            pstmt.setInt	 (58, box.getInt("p_score"));											//학점배점
            pstmt.setString(59, box.getString("p_contenttype"));						//컨텐츠타입
            
            //------------------------------------------------------------------------------//
            pstmt.setString(60, box.getString("p_gradexam")); 							//이수기준(평가)
            pstmt.setString(61, box.getString("p_gradreport")); 						//이수기준(리포트)
            pstmt.setString(62, box.getString("p_whtest")); 								//가중치(형성평가)
            pstmt.setString(63, box.getString("p_usesubjseqapproval")); 		//차수개설주관팀장(Y/N)
            pstmt.setString(64, box.getString("p_useproposeapproval")); 		//수강신청현업팀장(Y/N)
            pstmt.setString(65, box.getString("p_usemanagerapproval")); 		//주관팀장수강승인(Y/N)
            pstmt.setString(66, box.getString("p_rndcreditreq")); 					//학점배점(연구개발)-필수
            pstmt.setString(67, box.getString("p_rndcreditchoice")); 				//학점배점(연구개발)-선택
            pstmt.setString(68, box.getString("p_rndcreditadd")); 					//학점배점(연구개발)-지정가점
            pstmt.setString(69, box.getString("p_rndcreditdeduct")); 				//학점배점(연구개발)-지정감점
            pstmt.setString(70, box.getString("p_isoutsourcing")); 					//위탁교육여부
            pstmt.setString(71, box.getString("p_isablereview")); 					//복습가능여부
            pstmt.setString(72, box.getStringDefault("p_rndjijung","N"));			//R&D 지정과정여부
            pstmt.setString(73, box.getString("p_cpapproval"));			
            
            isOk = pstmt.executeUpdate();
			
			box.put("s_upperclass",box.getString("p_upperclass"));
			box.put("s_middleclass",box.getString("p_middleclass"));

			//교육주관을 선택하고 추가하는 경우는 해당 교육주관에 대해 TZ_PREVIEW에 INSERT한다.			
			if (!v_grcode.equals("ALL")) {
				box.put("p_grcode",v_grcode);
				box.put("p_subj",v_subj);
				isOk2 = this.InsertPreview(box);
			}
			
        }
        catch(Exception ex) {
            connMgr.rollback();
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
    과정코드 max 값 얻기
    @param	box		receive from the form object and session
    @return	String	과정코드
    */
    public String getMaxSubjcode(DBConnectionManager connMgr, String v_upperclass, String v_middleclass) throws Exception {
        String v_subjcode = "";
        String v_maxsubj = "";
        int    v_maxno   = 0;

        ListSet ls = null;
        String sql  = "";
        try {
            //sql = " select max(subj) maxsubj";
            //sql+= "   from tz_subj where ";
			//과정코드 =  대분류(3) + 중분류(3) + 일련번호(4)
			
			// 수정일 : 05.11.17 수정자 : 이나연 _ substr 수정 
//			sql = " select max(substr(subj,7,4)) maxsubj";
			sql = " select max(SUBSTR(subj,7,4)) maxsubj";
            sql+= "   from tz_subj where upperclass = '" + v_upperclass + "' and middleclass = '" + v_middleclass + "'";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                 v_maxsubj = ls.getString("maxsubj");
            }

            if (v_maxsubj.equals("")) {
                //v_subjcode = "0001";
				v_subjcode = v_upperclass + v_middleclass + "0001";
            } else {
                v_maxno = Integer.valueOf(v_maxsubj.substring(1)).intValue();
                //v_subjcode = "S" + new DecimalFormat("000").format(v_maxno+1);
				v_subjcode = v_upperclass + v_middleclass + new DecimalFormat("0000").format(v_maxno+1);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_subjcode;
    }
    
    /**
    과정맛보기 등록
    @param box      receive from the form object and session
    @return int    1:insert success,0:insert fail
    */
     public int InsertPreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            //insert TZ_SUBJ table
            sql =  "insert into TZ_PREVIEW ( ";
            sql+=  " grcode,   subj,        subjtext,  edumans, ";
            sql+=  " intro,    explain,     expect,    master, ";
            sql+=  " masemail, recommender, recommend, luserid, ";
            sql+=  " ldate) ";
            sql+=  " values (";
            sql+=  " ?,    ?,    ?,    ?, ";
            sql+=  " ?,    ?,    ?,    ?, ";
            sql+=  " ?,    ?,    ?,    ?, ";
            sql+=  " ? )";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, box.getString("p_grcode"));
            pstmt.setString( 2, box.getString("p_subj"));
            pstmt.setString( 3, box.getString("p_subjtext"));
            pstmt.setString( 4, box.getString("p_edumans"));
            pstmt.setString( 5, box.getString("p_intro"));
            pstmt.setString( 6, box.getString("p_explain"));
            pstmt.setString( 7, box.getString("p_expect"));
            pstmt.setString( 8, box.getString("p_master"));
            pstmt.setString( 9, box.getString("p_masemail"));
            pstmt.setString(10, box.getString("p_recommender"));
            pstmt.setString(11, box.getString("p_recommend"));
            pstmt.setString(12, v_luserid);
            pstmt.setString(13, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            connMgr.rollback();
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
    cp업체 과정결재상태 UPDate
    @param box      receive from the form object and session
    @return int    1:insert success,0:insert fail
    */
     public int UpdateCpApproval(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            //update TZ_SUBJ table
            sql = " update TZ_SUBJ ";
            sql+= "    set subjnm       = ?, ";
            sql+= "        eduurl       = ?, ";
            sql+= "        preurl       = ?, ";
            sql+= "        cpsubj       = ?, ";
            sql+= "        conturl      = ?, ";
            sql+= "        point        = ?, ";
            sql+= "        gradscore    = ?, ";
            sql+= "        gradstep     = ?, ";
            sql+= "        wstep        = ?, ";
            sql+= "        wmtest       = ?, ";
            sql+= "        wftest       = ?, ";
            sql+= "        wreport      = ?, ";
            sql+= "        wact         = ?, ";
            sql+= "        wetc1        = ?, ";
            sql+= "        wetc2        = ?, ";
            sql+= "        luserid      = ?, ";
            sql+= "        ldate        = ?, ";
            sql+= "        edumans      = ?, ";
            sql+= "        intro        = ?, ";
            sql+= "        explain      = ?, ";      
			sql+= "	 	   gradexam		= ?, ";
			sql+= "		   gradreport	= ?, ";
			sql+= "        whtest		= ?, ";
			sql+= "		   gradftest	= ?, ";
			sql+= "		   gradhtest	= ?, ";
			sql+= "		   edutimes		= ?, ";
			sql+= "		   musertel		= ?, ";
			sql+= "		   cpapproval   = ?  ";
            sql+= "  where subj         		= ? ";

            pstmt = connMgr.prepareStatement(sql);			
			
            pstmt.setString( 1, box.getString("p_subjnm"));
            pstmt.setString(2, box.getString("p_eduurl"));
            pstmt.setString(3, box.getString("p_preurl"));
            pstmt.setString(4, box.getString("p_cpsubj"));
            pstmt.setString(5, box.getString("p_conturl"));
            pstmt.setInt   (6, box.getInt   ("p_point"));
            pstmt.setInt   (7, box.getInt   ("p_gradscore"));
            pstmt.setInt   (8, box.getInt   ("p_gradstep"));
            pstmt.setDouble(9, box.getDouble("p_wstep"));
            pstmt.setDouble(10, box.getDouble("p_wmtest"));
            pstmt.setDouble(11, box.getDouble("p_wftest"));
            pstmt.setDouble(12, box.getDouble("p_wreport"));
            pstmt.setDouble(13, box.getDouble("p_wact"));
            pstmt.setDouble(14, box.getDouble("p_wetc1"));
            pstmt.setDouble(15, box.getDouble("p_wetc2"));
            pstmt.setString(16, v_luserid);
            pstmt.setString(17, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(18, box.getString("p_edumans"));
            pstmt.setString(19, box.getString("p_intro"));
            pstmt.setString(20, box.getString("p_explain"));
			
			//------------------------------------------------------------------------------//
            pstmt.setInt   (21, box.getInt("p_gradexam")); 					//이수기준(중간평가)
            pstmt.setInt   (22, box.getInt("p_gradreport")); 				//이수기준(리포트)
            pstmt.setInt   (23, box.getInt("p_whtest")); 					//가중치(형성평가)

            pstmt.setString(24, box.getString("p_gradftest"));				//이수기준-최종평가
            pstmt.setString(25, box.getString("p_gradhtest"));				//이수기준-형성평가
			//------------------------------------------------------------------------------//
			
			pstmt.setInt(26, box.getInt("p_edutimes"));				//학습시간
			pstmt.setInt(27, box.getInt("p_musertel"));				
			pstmt.setString(28, box.getString("p_cpapproval"));
            pstmt.setString(29, box.getString("p_subj"));
			

            isOk = pstmt.executeUpdate();

        }
        catch(Exception ex) {
            connMgr.rollback();
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
    학습현황 업데이트리스트
    @param	box			receive from the form object and session
    @return	ArrayList	교육그룹별 회사리스트
    */
    public ArrayList selectUpdateList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        
        String v_subj= box.getString("p_subj");	
        String v_subjseq = box.getString("p_subjseq");	
        String v_year = box.getString("p_year");	

        try {
            connMgr = new DBConnectionManager();            
            list = new ArrayList();

			//sql += " select                                        \n";
            //sql += "   b.comp,                                       \n";
            //sql += "   b.companynm                                   \n";
            //sql += " from                                          \n";
            //sql += "   tz_grcomp a, tz_comp b                      \n";
            //sql += " where                                         \n";
            //sql += "   grcode = "+SQLString.Format(v_grcode)+"     \n";
            //sql += "   and a.comp = b.comp                         \n";
            
            sql += " select              ";
            sql += "   subj,             ";
            sql += "   year,             ";
            sql += "   subjseq,          ";
            sql += "   seq,              ";
            sql += "   inuserid,         ";
            sql += "   indate,           ";
            sql += "   newfile,          ";
            sql += "   realfile,         ";
            sql += "   luserid,          ";
            sql += "   ldate             ";
            sql += " from                ";
            sql += "   TZ_CPUPDATESTATUS ";
            sql += " where ";
            sql += "       subj    = '"+v_subj+"'";
            sql += "   and year    = '"+v_year+"'";
            sql += "   and subjseq = '"+v_subjseq+"'";

            //pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성

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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  
    
    
    /**
    학습현황 최신업데이트
    @param	box			receive from the form object and session
    @return	ArrayList	학습현황 최신업데이트
    */
    public DataBox recentUpdateStatus(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        
        String v_subj= box.getString("p_subj");	
        String v_subjseq = box.getString("p_subjseq");	
        String v_year = box.getString("p_year");	

        try {
            connMgr = new DBConnectionManager();            
            list = new ArrayList();

			//sql += " select                                        \n";
            //sql += "   b.comp,                                       \n";
            //sql += "   b.companynm                                   \n";
            //sql += " from                                          \n";
            //sql += "   tz_grcomp a, tz_comp b                      \n";
            //sql += " where                                         \n";
            //sql += "   grcode = "+SQLString.Format(v_grcode)+"     \n";
            //sql += "   and a.comp = b.comp                         \n";
            
            sql += " select              ";
            sql += "   subj,             ";
            sql += "   year,             ";
            sql += "   subjseq,          ";
            sql += "   seq,              ";
            sql += "   inuserid,         ";
            sql += "   indate,           ";
            sql += "   newfile,          ";
            sql += "   realfile,         ";
            sql += "   luserid,          ";
            sql += "   ldate             ";
            sql += " from                ";
            sql += "   TZ_CPUPDATESTATUS ";
            sql += " where ";
            sql += "       subj    = '"+v_subj+"'";
            sql += "   and year    = '"+v_year+"'";
            sql += "   and subjseq = '"+v_subjseq+"'";
            sql += "   and seq = (select max(seq) from TZ_CPUPDATESTATUS  ";
            sql += "   where ";
            sql += "       subj    = '"+v_subj+"'";
            sql += "   and year    = '"+v_year+"'";
            sql += "   and subjseq = '"+v_subjseq+"'";
            sql += "   ) ";
            System.out.println("sqll====="+sql);

            //pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성

            ls = connMgr.executeQuery(sql);

            if(ls.next()) {
              dbox = ls.getDataBox();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }  
}
