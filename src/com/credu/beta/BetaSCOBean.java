//**********************************************************
//  1. 제      목: SCO Object Operation Bean
//  2. 프로그램명: BetaSCOBean.java
//  3. 개      요: SCO관리에 관련된 Bean
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.11
//  7. 수      정: 박미복 2004. 11.11
//**********************************************************

package com.credu.beta;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.beta.*;
import com.credu.scorm.*;
import com.credu.common.*;

public class BetaSCOBean {

    private static final String FILE_TYPE = "p_file";           // 파일업로드되는 tag name
    private static final int FILE_LIMIT = 1;                    // 페이지에 세팅된 파일첨부 갯수
    
    public BetaSCOBean() {}

    /**
    SCO 리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   Object리스트
    */      
    public ArrayList SelectObjectList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2=null;
        ArrayList list1 = null, list2=null;
        String sql  = "", sql2="";
        BetaSCOData data = null;
        
        String s_subj = box.getString("s_subj");
        String s_gubun = box.getString("s_gubun");
        if(s_gubun==null)   s_gubun="";
        
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select distinct a.oid , a.otype, a.filetype, a.npage, a.sdesc,a.master "
                + " , get_name(a.master) mastername "
                + " ,a.starting,a.server,a.subj,a.parameterstring,a.datafromlms "
                + " ,a.identifier,a.prerequisites,a.masteryscore,a.maxtimeallowed "
                + " ,a.timelimitaction,a.sequence,a.thelevel,a.luserid,a.ldate,a.oidnumber,a.highoid "
				+ " , a.metalocation,a.scolocate, a.scotitle , a.thelevel as scothelevel , c.metadata_idx  ";
//				+ " , a.metalocation,a.scolocate, a.scotitle , level as scothelevel , c.metadata_idx  ";			

            if (s_subj.equals("ALL")){
                //2005.11.15_하경태 : Oracle -> Mssql
				//sql += " from tz_object a , tz_met_m c where a.oid = c.oid(+) and a.otype='SCO' and ";
				sql += " from tz_object a , tz_met_m c where a.oid  =  c.oid(+) and a.otype='SCO' and ";
            }else if(s_subj.equals("NO-SUBJ")){
                sql += " from tz_object a , tz_met_m c "
                     //2005.11.15_하경태 : Oracle -> Mssql
					//+ " where a.otype='SCO' and a.oid = c.oid(+)  and a.oid not in (select distinct oid from tz_subjobj) and ";
					+ " where a.otype='SCO' and a.oid  =  c.oid(+) and a.oid not in (select distinct oid from tz_subjobj) and ";
            }else{
                sql += " from tz_object a, tz_subjobj b , tz_met_m c "
                     + " where a.otype='SCO' and a.oid=b.oid and b.subj="+StringManager.makeSQL(s_subj)+" and "
					 // 2005.11.15_하경태 : Oracle -> Mssql
                     //+ " a.oid = c.oid(+) and ";
                     + " a.oid  =  c.oid(+) and ";
            }
            sql += "  upper(a.sdesc) like '%"+s_gubun.toUpperCase()+"%' ";
/*
		 // connect by 해결해야됨		
			sql += " connect by a.highoid = prior a.oid start with a.highoid is null";
*/			
			sql += " order by a.oid asc";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data=new BetaSCOData(); 
                data.setOid             (ls.getString("oid"));
                data.setOtype           (ls.getString("otype"));
                data.setFiletype        (ls.getString("filetype"));
                data.setNpage           (ls.getInt("npage"));
                data.setSdesc           (ls.getString("sdesc"));
                data.setMaster          (ls.getString("master"));
                data.setMastername      (ls.getString("mastername"));
                data.setStarting        (ls.getString("starting"));
                data.setServer          (ls.getString("server"));
                data.setSubj            (ls.getString("subj"));
                data.setParameterstring (ls.getString("parameterstring"));
                data.setDatafromlms     (ls.getString("datafromlms"));
                data.setIdentifier      (ls.getString("identifier"));
                data.setPrerequisites   (ls.getString("prerequisites"));
                data.setMasteryscore    (ls.getInt("masteryscore"));
                data.setMaxtimeallowed  (ls.getString("maxtimeallowed"));
                data.setTimelimitaction (ls.getString("timelimitaction"));
                data.setSequence        (ls.getInt("sequence"));
                data.setThelevel        (ls.getInt("scothelevel"));
                data.setLuserid         (ls.getString("luserid"));
                data.setLdate           (ls.getString("ldate"));
                data.setOidnumber       (ls.getInt("oidnumber"));
                data.setHighoid         (ls.getString("highoid"));
                data.setMetalocation    (ls.getString("metalocation"));
                data.setScolocate       (ls.getString("scolocate"));
				data.setScoTitle       (ls.getString("scotitle"));
				data.setMetadata       (ls.getInt("metadata_idx"));

                // Object가 사용된 과정리스트 저장
                if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                sql = "select distinct a.subj, b.subjnm from tz_subjobj a, tz_subj b "
                    + " where a.subj=b.subj and a.oid="+StringManager.makeSQL(data.getOid());
                ls2 = connMgr.executeQuery(sql);

                while(ls2.next()){
                    data.makeSub(ls2.getString("subj"),ls2.getString("subjnm"));                                
                }
                //사용된 과정갯수..
                data.setCntUsed(ls2.getTotalCount());
                list1.add(data);    
            }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }
    
    /**
    Object정보 조회
    @param box          receive from the form object and session
    @return SCOData   Object 정보
    */  
    public BetaSCOData SelectObjectData(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null,ls2=null;
        ArrayList list1 = null, list2=null;
        String sql  = "", sql2="";
        BetaSCOData data = null;
        
        String p_oid = box.getString("p_oid");
        String p_process = box.getString("p_process");
        
        try {
            connMgr = new DBConnectionManager();

            sql = "select oid , otype, filetype, npage, sdesc,master "
                + " ,starting,server,subj,parameterstring,datafromlms "
                + " ,identifier,prerequisites,masteryscore,maxtimeallowed "
                + " ,timelimitaction,sequence,thelevel,luserid,ldate,oidnumber,highoid,metalocation,scolocate, scoall, scotitle, master "
                + "  from tz_object where oid="+StringManager.makeSQL(p_oid);

				 System.out.println(sql);
                    
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                data=new BetaSCOData(); 
                data.setOid             (ls.getString("oid"));
                data.setOtype           (ls.getString("otype"));
                data.setFiletype        (ls.getString("filetype"));
                data.setNpage           (ls.getInt("npage"));
                data.setSdesc           (ls.getString("sdesc"));
                data.setMaster          (ls.getString("master"));
                data.setStarting        (ls.getString("starting"));
                data.setServer          (ls.getString("server"));
                data.setSubj            (ls.getString("subj"));
                data.setParameterstring (ls.getString("parameterstring"));
                data.setDatafromlms     (ls.getString("datafromlms"));
                data.setIdentifier      (ls.getString("identifier"));
                data.setPrerequisites   (ls.getString("prerequisites"));
                data.setMasteryscore    (ls.getInt("masteryscore"));
                data.setMaxtimeallowed  (ls.getString("maxtimeallowed"));
                data.setTimelimitaction (ls.getString("timelimitaction"));
                data.setSequence        (ls.getInt("sequence"));
                data.setThelevel        (ls.getInt("thelevel"));
                data.setLuserid         (ls.getString("luserid"));
                data.setLdate           (ls.getString("ldate"));
                data.setOidnumber       (ls.getInt("oidnumber"));
                data.setHighoid         (ls.getString("highoid"));
                data.setMetalocation    (ls.getString("metalocation"));
                data.setScolocate       (ls.getString("scolocate"));
				data.setScoAll          (ls.getInt("scoall"));
				data.setScoTitle          (ls.getString("scotitle"));
                data.setMastername  (ls.getString("master")); 
                
                // Object가 사용된 과정리스트 저장
                if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                
                sql = "select distinct a.subj, b.subjnm from tz_subjobj a, tz_subj b "
                    + " where a.subj=b.subj and a.oid="+StringManager.makeSQL(p_oid);
                
                ls2 = connMgr.executeQuery(sql);
                
                while(ls2.next()){
                    data.makeSub(ls2.getString("subj"),ls2.getString("subjnm"));                                
                }
                //사용된 과정갯수..
                data.setCntUsed(ls2.getTotalCount());
                
            }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return data;
    }


    /**
    Object정보 조회
    @param box          receive from the form object and session
    @return SCOData   Object 정보
    */  
    public ArrayList SelectScoInData(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        BetaSCOData data = null;
        
        String p_scolocate = box.getString("p_scolocate");
		
        try {
             connMgr = new DBConnectionManager();
			 list1 = new ArrayList();

            sql = "select a.oid , a.otype, a.filetype, a.npage, a.sdesc,a.master "
                + " ,a.starting,a.server,a.subj,a.parameterstring,a.datafromlms "
                + " ,a.identifier,a.prerequisites,a.masteryscore,a.maxtimeallowed "
                + " ,a.timelimitaction,a.sequence,a.thelevel,a.luserid,a.ldate,a.oidnumber,a.highoid,a.metalocation,a.scolocate "
				+ " , a.scoall, a.scotitle, a.master, level as scothelevel, b.metadata_idx  "
                //2005.11.15_하경태 : Oracle -> mssql 
				//+ "  from tz_object a, tz_met_m b  where a.oid = b.oid(+) and a.scolocate="+StringManager.makeSQL(p_scolocate)
				+ "  from tz_object a, tz_met_m b  where a.oid  =  b.oid(+) and a.scolocate="+StringManager.makeSQL(p_scolocate)
				+ "  connect by a.highoid = prior a.oid start with a.highoid is null order by a.oidnumber ";
                                
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data=new BetaSCOData(); 
                data.setOid             (ls.getString("oid"));
                data.setOtype           (ls.getString("otype"));
                data.setFiletype        (ls.getString("filetype"));
                data.setNpage           (ls.getInt("npage"));
                data.setSdesc           (ls.getString("sdesc"));
                data.setMaster          (ls.getString("master"));
                data.setStarting        (ls.getString("starting"));
                data.setServer          (ls.getString("server"));
                data.setSubj            (ls.getString("subj"));
                data.setParameterstring (ls.getString("parameterstring"));
                data.setDatafromlms     (ls.getString("datafromlms"));
                data.setIdentifier      (ls.getString("identifier"));
                data.setPrerequisites   (ls.getString("prerequisites"));
                data.setMasteryscore    (ls.getInt("masteryscore"));
                data.setMaxtimeallowed  (ls.getString("maxtimeallowed"));
                data.setTimelimitaction (ls.getString("timelimitaction"));
                data.setSequence        (ls.getInt("sequence"));          
                data.setLuserid         (ls.getString("luserid"));
                data.setLdate           (ls.getString("ldate"));
                data.setOidnumber       (ls.getInt("oidnumber"));
                data.setHighoid         (ls.getString("highoid"));
                data.setMetalocation    (ls.getString("metalocation"));
                data.setScolocate       (ls.getString("scolocate"));
				data.setScoAll          (ls.getInt("scoall"));
				data.setScoTitle          (ls.getString("scotitle"));
                data.setMastername  (ls.getString("master"));  
				data.setThelevel  (ls.getInt("scothelevel"));  
				data.setMetadata  (ls.getInt("metadata_idx"));
				
				list1.add(data);  
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
        return list1;
    }

	
    public String SelectScoLocate() throws Exception {
	   DBConnectionManager connMgr = null;
       ListSet ls = null, ls2 = null;
	   String sql  = "";
	   String sconumber = "";
	   int isOk = -1;

	   try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);  

			sql = " select count(*) as scocount from tz_scolocate ";
            
			ls = connMgr.executeQuery(sql);
            
			if (ls.next()) {
			sconumber = ls.getString("scocount") ;
			                      			
				if (!sconumber.equals("0")) {

					sql = " update tz_scolocate set sconumber =  sconumber + 1  ";
					isOk = connMgr.executeUpdate(sql);

					sql = " select max(sconumber) as maxnumber from tz_scolocate ";
					ls2 = connMgr.executeQuery(sql);
					if (ls2.next()) {
						sconumber = ls2.getString("maxnumber");						
					}
				} else {
					sql = " insert into tz_scolocate ( sconumber) values (1) ";
			
					isOk = connMgr.executeUpdate(sql);
					sconumber = "1";
				}
            }
            
			if (isOk > -1) {connMgr.commit();}
	   }            
        catch (Exception ex) {          
			connMgr.rollback();
			ex.printStackTrace();
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }    
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
       return sconumber;
	}

	public String SelectStarting(String oid) throws Exception {
	   DBConnectionManager connMgr = null;
       ListSet ls = null, ls2 = null;
	   String sql  = "";
	   String starting = "";

	   try {
            connMgr = new DBConnectionManager();

			sql = " select starting from tz_object where oid = '"+ oid + "'";
            
			ls = connMgr.executeQuery(sql);
            
			if (ls.next()) {
				starting = ls.getString("starting");
            }
	   }            
        catch (Exception ex) {          
			ex.printStackTrace();
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {

            if(ls != null) { try { ls.close(); }catch (Exception e) {} }    
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
       return starting;
	}

	public ArrayList BookmarkInfo(String subj, String year, String subjseq , String userid) throws Exception {
	   DBConnectionManager connMgr = null;
       ListSet ls = null, ls2 = null, ls3 = null, ls4 = null;
	   String sql  = "", sql2 = "", sql3 ="";	   
	   ArrayList list1 = null;
	   BetaSCOData data = null;
	   String oid = null;
	   
	   try {
            connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			sql = " select * from (select a.oid, a.lesson from tz_subjobj a , tz_object b  "
			    + " where a.subj = '"+ subj + "' and a.oid=b.oid and rtrim(ltrim(b.STARTING)) is not null  and a.oid > ( "
			    + " SELECT max(oid) FROM tz_progress   WHERE subj = '"+ subj + "'  AND YEAR = '"+ year + "' AND subjseq = '"+ subjseq + "' "
				+ " AND userid = '"+ userid + "' and lessonstatus = 'complete' )) where rownum = 1 " ;

			ls = connMgr.executeQuery(sql);
            
			if (ls.next()) {
				data=new BetaSCOData(); 
				oid = ls.getString("oid");
                data.setOid             (ls.getString("oid"));
                data.setLesson           (ls.getString("lesson"));

				sql = " select starting from tz_object where oid = '"+ oid + "' ";
				ls2 = connMgr.executeQuery(sql);
				if (ls2.next()) {
					data.setStarting        (ls2.getString("starting"));
				}		
				
				sql = " select core_lesson_location from tz_user_scoinfo "
				    + " where oid = '"+ oid + "' and subj = '"+ subj + "' and year = '"+ year +"' and subjseq = '"+ subjseq +"' and userid = '"+ userid +"' ";
				
				ls3 = connMgr.executeQuery(sql);
				if (ls3.next()) {
					data.setCoreLessonLocation (ls3.getString("core_lesson_location"));
				}		
				list1.add(data);  
            } else {
				
				sql = " select * from ( select rownum rnum,  a.OID, a.lesson FROM tz_subjobj a, tz_object b "
					+ "  WHERE a.subj = '"+ subj + "' AND a.OID = b.OID AND rtrim(ltrim(b.starting)) IS NOT NULL order by a.oid asc ) where rnum < 2";
                 
				ls4 = connMgr.executeQuery(sql);
				
				if (ls4.next()) {

					data=new BetaSCOData(); 
					oid = ls4.getString("oid");
					data.setOid             (ls4.getString("oid"));
					data.setLesson           (ls4.getString("lesson"));

					sql = " select starting from tz_object where oid = '"+ oid + "' ";
					ls2 = connMgr.executeQuery(sql);
					if (ls2.next()) {
						data.setStarting        (ls2.getString("starting"));
					}		
					
					sql = " select core_lesson_location from tz_user_scoinfo "
						+ " where oid = '"+ oid + "' and subj = '"+ subj + "' and year = '"+ year +"' and subjseq = '"+ subjseq +"' and userid = '"+ userid +"' ";
					
					ls3 = connMgr.executeQuery(sql);
					if (ls3.next()) {
						data.setCoreLessonLocation (ls3.getString("core_lesson_location"));
					}		
					list1.add(data);  
				}                
            }
	   }            
        catch (Exception ex) {          
			ex.printStackTrace();
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }    
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }    
			if(ls3 != null) { try { ls3.close(); }catch (Exception e) {} }    
			if(ls4 != null) { try { ls4.close(); }catch (Exception e) {} }    
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
       return list1;
	}


	public String DeleteScoOid(RequestBox box) throws Exception {
	   DBConnectionManager connMgr = null;       
	   ListSet ls = null, ls2 = null;
	   String sql  = "";	
       String results  = "";	
	   int metadata_idx = 0;
	   int isOk = -1, isOk1 = -1, isOk2 = -1, isOk3 = -1, isOk4 = -1, isOk5 = -1, isOk6 = -1, isOk7 = -1, isOk8 = -1, isOk9 = -1 ;
	   int isOk10 = -1, isOk11 = -1, isOk12 = -1, isOk13 = -1, isOk14 = -1, isOk15 = -1, isOk16 = -1, isOk17 = -1, isOk18 = -1, isOk19 = -1;

	   String oid = box.getString("p_oid");
	   
	   try {
            connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false); 
			
			sql = " select oid from tz_subjobj where  oid = '"+ oid + "' ";
			
			ls2 = connMgr.executeQuery(sql);

			if (ls2.next()) {
			    results = "scodelete.fail";
				return results;
			}
			
            sql = " select metadata_idx from tz_met_m where  oid = '"+ oid +"' ";

			System.out.println(sql);
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				metadata_idx = ls.getInt("metadata_idx");						
				
			//* 메타데이타 삭제*//

			sql = " delete from tz_classif where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk1 = connMgr.executeUpdate(sql);

			sql = " delete from tz_classif where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' )";
            isOk1 = connMgr.executeUpdate(sql);

			sql = " delete from tz_annot where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk2 = connMgr.executeUpdate(sql);

			sql = " delete from tz_annot where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk2 = connMgr.executeUpdate(sql);

			sql = " delete from tz_relat where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk3 = connMgr.executeUpdate(sql);

			sql = " delete from tz_relat where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk3 = connMgr.executeUpdate(sql);

			sql = " delete from tz_edu_rol where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk4 = connMgr.executeUpdate(sql);

			sql = " delete from tz_edu_rol where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk4 = connMgr.executeUpdate(sql);

			sql = " delete from tz_edu_lan where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk5 = connMgr.executeUpdate(sql);

			sql = " delete from tz_edu_lan where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' )"; 
            isOk5 = connMgr.executeUpdate(sql);

			sql = " delete from tz_tec_req where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk6 = connMgr.executeUpdate(sql);

			sql = " delete from tz_tec_req where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk6 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_sch where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk7 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_sch where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk7 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_cen where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk8 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_cen where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk8 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_con where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk9 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_con where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk9 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_cat where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk10 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_cat where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk10 = connMgr.executeUpdate(sql);

			sql = " delete from tz_lif_cen where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk11 = connMgr.executeUpdate(sql);

			sql = " delete from tz_lif_cen where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk11 = connMgr.executeUpdate(sql);

			sql = " delete from tz_lif_con where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk12 = connMgr.executeUpdate(sql);

			sql = " delete from tz_lif_con where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk12 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_cat where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk13 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_cat where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' )"; 
            isOk13 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_lan where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk14 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_lan where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk14 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_des where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk15 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_des where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' )"; 
            isOk15 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_key where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk16 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_key where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' )"; 
            isOk16 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_cov where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk17 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_cov where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' ) ";
            isOk17 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_m where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid in "
				+ " ( select oid from tz_object connect by highoid = prior oid start with highoid = '"+oid+"'))";
            isOk18 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_m where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.oid = '"+oid+"' )"; 
            isOk18 = connMgr.executeUpdate(sql);

			//* 메타데이타 삭제 끝 *//

			}

		  sql = " delete from tz_object where oid in ( select oid from tz_object connect by highoid = prior oid "
			+ " start with highoid = '"+ oid +"' )  ";
			System.out.println(sql);
			
		   isOk = connMgr.executeUpdate(sql);      
		   
		   sql = " delete from tz_object where oid = '"+ oid +"' ";
		   System.out.println(sql);
			
		   isOk19 = connMgr.executeUpdate(sql);   
		   
		   results = "OK";
		   if ((isOk > -1) && (isOk1 > -1) && (isOk2 > -1) && (isOk3 > -1) && (isOk4 > -1) && (isOk5 > -1) && (isOk6 > -1) && (isOk7 > -1) && (isOk8 > -1) && (isOk9 > -1) && (isOk10 > -1) && (isOk11 > -1) && (isOk12 > -1) && (isOk13 > -1) && (isOk14 > -1) && (isOk15 > -1) && (isOk16 > -1) && (isOk17 > -1) && (isOk18 > -1) && (isOk19 > -1)) {connMgr.commit();}            
	   }            
        catch (Exception ex) {
			connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);		
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {			
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }   
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }   
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }   
		
		return results;
	}

    /**
    스콤패키지삭제
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail    
    **/
	public String DeleteScoPackage(RequestBox box) throws Exception {
	   DBConnectionManager connMgr = null;       
	   ListSet ls = null;
	   String results  = "";	
	   String sql  = "";		   	
	   int isOk = -1, isOk1 = -1, isOk2 = -1, isOk3 = -1, isOk4 = -1, isOk5 = -1, isOk6 = -1, isOk7 = -1, isOk8 = -1, isOk9 = -1 ;
	   int isOk10 = -1, isOk11 = -1, isOk12 = -1, isOk13 = -1, isOk14 = -1, isOk15 = -1, isOk16 = -1, isOk17 = -1, isOk18 = -1;

	   String scolocate = box.getString("p_scolocate");
	   
	   try {
            connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false); 
			
			sql = " select oid from tz_object where scolocate = '"+ scolocate + "' and  oid in  (select oid from tz_subjobj ) ";
			
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
			    results = "scodelete.fail";
				return results;
			}

			//* 메타데이타 삭제*//
			sql = " delete from tz_classif where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' )";
            isOk1 = connMgr.executeUpdate(sql);

			sql = " delete from tz_annot where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk2 = connMgr.executeUpdate(sql);

			sql = " delete from tz_relat where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk3 = connMgr.executeUpdate(sql);

			sql = " delete from tz_edu_rol where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk4 = connMgr.executeUpdate(sql);

			sql = " delete from tz_edu_lan where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' )"; 
            isOk5 = connMgr.executeUpdate(sql);

			sql = " delete from tz_tec_req where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk6 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_sch where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk7 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_cen where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk8 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_con where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk9 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_cat where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk10 = connMgr.executeUpdate(sql);

			sql = " delete from tz_lif_cen where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk11 = connMgr.executeUpdate(sql);

			sql = " delete from tz_lif_con where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk12 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_cat where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' )"; 
            isOk13 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_lan where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk14 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_des where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' )"; 
            isOk15 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_key where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' )"; 
            isOk16 = connMgr.executeUpdate(sql);

			sql = " delete from tz_gen_cov where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' ) ";
            isOk17 = connMgr.executeUpdate(sql);

			sql = " delete from tz_met_m where metadata_idx in "
				+ " ( select a.metadata_idx from tz_met_m a , tz_object b where a.oid = b.oid and b.SCOLOCATE = '"+scolocate+"' )"; 
            isOk18 = connMgr.executeUpdate(sql);

			//* 메타데이타 삭제 끝 *//

			sql = " delete from tz_object where scolocate ='"+scolocate+"' and otype='SCO' ";
			isOk = connMgr.executeUpdate(sql); 
			
			results = "OK";
			
			if ((isOk > -1) && (isOk1 > -1) && (isOk2 > -1) && (isOk3 > -1) && (isOk4 > -1) && (isOk5 > -1) && (isOk6 > -1) && (isOk7 > -1) && (isOk8 > -1) && (isOk9 > -1) && (isOk10 > -1) && (isOk11 > -1) && (isOk12 > -1) && (isOk13 > -1) && (isOk14 > -1) && (isOk15 > -1) && (isOk16 > -1) && (isOk17 > -1) && (isOk18 > -1)) {connMgr.commit();}                       
	   }            
        catch (Exception ex) {
			connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);		
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }   
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        } 
		
		return results;
	}


	public ArrayList performScoPackageList(RequestBox box) throws Exception {
	   DBConnectionManager connMgr = null;       
	   ListSet ls = null;
	   ArrayList list1 = null;
	   String sql  = "";
	   BetaSCOData data = null;
       String v_cp = "";
       String s_gadmin = box.getSession("gadmin");
       String s_userid = box.getSession("userid");

	   try {
            connMgr = new DBConnectionManager();
			list1 = new ArrayList();

            //베타업체 리스트
            if(s_gadmin.equals("S1") || s_gadmin.equals("T1") || s_gadmin.equals("M1") ){
            	//베타업체 담당자일경우(해당업체의 정보만보여줌)
	            sql = "select cpseq, cpnm ";
	            sql += " from tz_cpinfo where userid = " + SQLString.Format(s_userid);	
	            sql += " order by cpnm";	            
	                        
            	ls = connMgr.executeQuery(sql);
            	
            	if(ls.next())
            		v_cp = ls.getString("cpseq");
            	else
            		v_cp = "";
           	}


			sql = " select distinct scolocate, scotitle from tz_object a, tz_cpinfo b where otype='SCO'";            


            //베타업체 검색
            if(!v_cp.equals("")){
                sql += " and a.producer = " + StringManager.makeSQL(v_cp);
                sql += " and a.producer = b.cpseq ";
            }
            else{
                sql += " and a.producer is not null";
            	sql += " and a.producer = b.cpseq ";
            }

            sql += " order by scolocate ";

			ls = connMgr.executeQuery(sql);
			
			while(ls.next()) {
				data=new BetaSCOData(); 
				data.setScolocate       (ls.getString("scolocate"));				
				data.setScoTitle          (ls.getString("scotitle"));
				list1.add(data);
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
		return list1;
	}

	public void InsertScormContent(String scono, String filename,RequestBox box ) throws Exception {
	   DBConnectionManager connMgr = null;
	   PreparedStatement pstmt = null; 
       ListSet ls = null, ls2 = null;
	   String sql  = "";
	   String max_scoid = "";
	   String next_scoid = "";
	   String all_url = null;
	   String ContentTitle="";
	   String temp_scono="";
	   int isOk = 0;
	   int temp_scoid=0;

	   String v_luserid      = box.getSession("userid");

	   ConfigSet conf = new ConfigSet();			
	   String savePath = conf.getProperty("dir.scoobjectpath");

	   String str_DcourseCode	= "0000000000" + scono;			
	   str_DcourseCode	= str_DcourseCode.substring(str_DcourseCode.length()-10);

	   String[][] table;

	    try {
				connMgr = new DBConnectionManager();    			
				connMgr.setAutoCommit(false);  

				sql = " select max(oid) as max_scoid from tz_object where otype='SCO'";
				ls = connMgr.executeQuery(sql);

				if (ls.next()) {
					max_scoid = ls.getString("max_scoid") ;	
				}

				max_scoid = max_scoid.substring(max_scoid.length()-9);
				temp_scoid = Integer.parseInt(max_scoid) +1;

				manifestTableBean tablebean = new manifestTableBean();
				String tmpfilepath = savePath + str_DcourseCode;
				tablebean.setXmlFile(tmpfilepath,filename);
				table = tablebean.getTableValue();
				ContentTitle = tablebean.getContentTitle();

				//System.out.print(ContentTitle);

				// 상위객체 코드값 넣기 먼저 부모의 identifier를 찾아 그 코드값을 넣어준다.
				if(scono != null){					
					for(int j=0;j<table.length;j++){
						table[j][11] = String.valueOf(temp_scoid);

						if (table[j][9] == "")
						{
							table[j][10] = "";
						}
						else{
							for(int k=0;k<j;k++){
								if (table[k][8].trim().equals(table[j][9].trim()))
								{
									temp_scono = "0000000000" + Integer.parseInt(table[k][11]);
									temp_scono	= "S"+temp_scono.substring(temp_scono.length()-9);
									table[j][10] = temp_scono;							
									break;
								}
								else{
									table[j][10] = "";								
								}
							}
						}

						temp_scoid++;
					}
				}
				
				sql = "insert into tz_object (OID, SDESC,STARTING,  PREREQUISITES, MAXTIMEALLOWED, TIMELIMITACTION, MASTERYSCORE";
				sql = sql +", DATAFROMLMS, METALOCATION, OIDNUMBER, MASTER, SCOLOCATE,  OTYPE, SCOALL, FILETYPE, NPAGE, SCOTITLE, LDATE, HIGHOID)";
				sql = sql +" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'),?)";					
	
				pstmt = connMgr.prepareStatement(sql);

                //  scono = null;
				if(scono != null){
					int count=1;
					for(int i=0;i<table.length;i++){						

						next_scoid = "0000000000" + Integer.parseInt(table[i][11]);
						next_scoid	= "S"+next_scoid.substring(next_scoid.length()-9);

						pstmt.setString(1,next_scoid);		
						pstmt.setString(2,table[i][0].trim());
						if (table[i][1]==null || table[i][1].trim().equals("") || table[i][1].trim()=="" || table[i][1].trim().equals("null"))
						{
							//all_url = table[i][1].trim();						
							all_url = " ";						
						}else{
							all_url = str_DcourseCode +"/"+ table[i][1].trim();
						}

						pstmt.setString(3,all_url);								// content_url1					
						pstmt.setString(4,table[i][2].trim());					// prerequisites
						pstmt.setString(5,table[i][3].trim());					// maxtimeallowed
						pstmt.setString(6,table[i][4].trim());					// timelimitaction
						pstmt.setString(7,table[i][5].trim());					// masteryscore
						pstmt.setString(8,table[i][6].trim());					// datafromlms
						pstmt.setString(9,table[i][7].trim());					// METALOCATION
						pstmt.setInt(10,count);										// OIDNUMBER
						pstmt.setString(11,v_luserid);					// MASTER
						pstmt.setString(12,str_DcourseCode);			// SCOLOCATE
						pstmt.setString(13,"SCO");						// OTYPE
						pstmt.setInt(14,1);                          // SCOALL
						pstmt.setString(15,"HTML");			                // FILETYPE
						pstmt.setInt(16,0);			                // NPAGE
						pstmt.setString(17,ContentTitle);			                // SCOTITLE							
						pstmt.setString(18,table[i][10].trim());	// high_sco_code
				
						 isOk = pstmt.executeUpdate();
						 count++;
					}
				}
				 connMgr.commit();
			}
		catch (Exception ex) {
			connMgr.rollback();
			ex.printStackTrace();			
            throw new Exception("results = " + "\r\n" +"sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
            if (isOk > 0) {connMgr.commit();}
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
	}


    /**
    Object등록
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail    
    **/
    public String InsertObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;        
        String sql = "";
        int isOk = -1, j=0;   
        
        String  v_luserid   = box.getSession("userid");
        String  v_oid       = "";
        String  results     ="";

        try {
            connMgr = new DBConnectionManager();
            

            sql = "select ltrim(to_char( to_number(isnull(max(oid),'1000000000'))+1, '0000000000')) oid from tz_object";

         //   sql = "select ltrim(to_char( to_number(isnull(max(oid),'1000000000'))+1, '0000000000')) oid from tz_object";


            try{
                ls = connMgr.executeQuery(sql);
                ls.next();
                v_oid = ls.getString("oid");
            }catch(Exception se){
                v_oid = "1000000001";
            }

            //FILE MOVE & UNZIP
            results = this.controlObjectFile("insert",v_oid, box);

            if(results.equals("OK")){                       

                if(ls != null) { try { ls.close(); }catch (Exception e) {} }
                sql = "insert into tz_object "
                + "(oid , otype, filetype, npage, sdesc,master "
                + " ,starting,server,subj,parameterstring,datafromlms "
                + " ,identifier,prerequisites,masteryscore,maxtimeallowed "
                + " ,timelimitaction,sequence,thelevel,luserid,ldate)"     
                + " values "
                + "("+ StringManager.makeSQL(v_oid)
                + ","+ StringManager.makeSQL("SCO")
                + ","+ StringManager.makeSQL("HTML")
                + ","+ box.getInt("p_npage")
                + ","+ StringManager.makeSQL(box.getString("p_sdesc"))
                + ","+ StringManager.makeSQL(box.getString("p_master"))
                + ","+ StringManager.makeSQL(GetCodenm.get_config("object_locate")+v_oid+"/1001.html")
                + ","+ StringManager.makeSQL(box.getString("p_server"))
                + ","+ StringManager.makeSQL(box.getString("p_subj")) 
                + ","+ StringManager.makeSQL(box.getString("p_parameterstring"))
                + ","+ StringManager.makeSQL(box.getString("p_datafromlms"))
                + ","+ StringManager.makeSQL(box.getString("p_identifier"))
                + ","+ StringManager.makeSQL(box.getString("p_prerequisites"))  
                + ","+ box.getInt("p_masteryscore")
                + ","+ StringManager.makeSQL(box.getString("p_maxtimeallowed")) 
                + ","+ StringManager.makeSQL(box.getString("p_timelimitaction"))
                + ","+ box.getInt("p_sequence")
                + ","+ box.getInt("p_thelevel")
                + ","+ StringManager.makeSQL("v_luserid")        
                + ", to_char(sysdate,'YYYYMMDDHH24MISS') "
                + ")";                                
                isOk = connMgr.executeUpdate(sql);
            }
            
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("results = " + results + "\r\n" +"sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > -1) {connMgr.commit();}
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return results;
    }   
 
     /**
    Object수정
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail    
    **/
    public String UpdateObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null; 
		ListSet ls = null, ls2=null;  
        String sql = "";
        int isOk = 0, j=0;
		int highoidcount = 0;
		//PreparedStatement pstmt = null;  
        
        String results = "";
        String v_luserid    = box.getSession("userid");
        String v_oid        = box.getString("p_oid");
		String p_scolocate  = box.getString("p_scolocate");
		String v_starting 	=  box.getString("p_starting");
        
        try{
            //FILE MOVE & UNZIP
            results = this.controlObjectFile("update",v_oid, box);
        } catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("results = " + results + "\r\n" + ex.getMessage());
        }
            
        try {
			connMgr = new DBConnectionManager(); 

			sql = " select oid from tz_progress where  oid = '"+ v_oid + "' ";
			ls2 = connMgr.executeQuery(sql);

			if (ls2.next()) {

			    results = "scomodify.fail";
				return results;
			}

			sql = " select count(*) as highoidcount from tz_object where otype='SCO' and highoid = '"+ v_oid + "' ";
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				highoidcount = ls.getInt("highoidcount") ;	
			}

			if (highoidcount == 0) {
				v_starting = p_scolocate+"/"+ v_starting ;  
			} else {
			    v_starting = " ";  
			}

			sql = "update tz_object set "                   
				+ "     npage           = "+ box.getInt("p_npage")
				+ "     ,sdesc              = "+ StringManager.makeSQL(box.getString("p_sdesc"))
				+ "     ,master             = "+ StringManager.makeSQL(box.getString("p_master"))                    
				+ "     ,starting           = "+ StringManager.makeSQL(v_starting)
				+ "     ,server             = "+ StringManager.makeSQL(box.getString("p_server"))
				+ "     ,subj               = "+ StringManager.makeSQL(box.getString("p_subj")) 
				+ "     ,parameterstring    = "+ StringManager.makeSQL(box.getString("p_parameterstring"))
				+ "     ,datafromlms        = "+ StringManager.makeSQL(box.getString("p_datafromlms"))
				+ "     ,identifier         = "+ StringManager.makeSQL(box.getString("p_identifier"))
				+ "     ,prerequisites      = "+ StringManager.makeSQL(box.getString("p_prerequisites"))  
				+ "     ,masteryscore       = "+ box.getInt("p_masteryscore")
				+ "     ,maxtimeallowed     = "+ StringManager.makeSQL(box.getString("p_maxtimeallowed")) 
				+ "     ,timelimitaction    = "+ StringManager.makeSQL(box.getString("p_timelimitaction"))
				+ "     ,sequence           = "+ box.getInt("p_sequence")
				+ "     ,thelevel           = "+ box.getInt("p_thelevel")
				+ "     ,luserid            = "+ StringManager.makeSQL(v_luserid)        
				+ "     ,ldate              = to_char(sysdate,'YYYYMMDDHH24MISS') "
				+ " where oid="+ StringManager.makeSQL(v_oid);

		//	ls.close();

       //     if(isOk==1){
     //           	sql = "select isnull(max(seq)+1,1) seq from TZ_BETATESTHISTORY";
	//                ls = connMgr.executeQuery(sql);
   //             if (ls.next()) {
    //    	        v_seq = ls.getInt(1);
      //          }
     //        ls.close();

		//	sql  = "insert into TZ_BETATESTHISTORY ";
	//		sql += "    (seq,";
	//		sql += "    oid,";
	//		sql += "    subj,";
//			sql += "    sdesc,";
	//		sql += "    master,";
	//		sql += "    luserid,";
	//		sql += "    ldate)";
	//		sql += " values (?,?,?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'))";

	//		pstmt = connMgr.prepareStatement(sql);

	//		pstmt.setInt   (1, v_seq);
       //     pstmt.setString(2, v_oid);
   //         pstmt.setString(3, box.getString("s_subj"));
	//		pstmt.setString(4, box.getString("p_sdesc"));
	//		pstmt.setString(5, box.getString("p_master"));
	//		pstmt.setString(6, v_luserid);
	//		isOk = pstmt.executeUpdate();

//			isOk = connMgr.executeUpdate(sql);
			results = "OK";
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            connMgr.setAutoCommit(true); 
            if (isOk > 0) {connMgr.commit();}
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return results;
    }   
    
    /**
    Object 삭제
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail    
    **/
    public int DeleteObject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;        

        PreparedStatement pstmt = null;        
        String sql = "";
        int isOk = 0, j=0;   
        
        String p_subj       = box.getString("p_subj");
        String p_mftype     = box.getString("p_mftype");
        int    p_width      = box.getInt("p_width");
        int    p_height     = box.getInt("p_height");
        String p_ismfbranch = box.getString("p_ismfbranch");
        String p_iscentered = box.getString("p_iscentered");
        String p_menus      = box.getString("p_menus");
        
        //String v_luserid      = box.getSession("userid");
        String v_luserid      = "icarus";

        try {
            connMgr = new DBConnectionManager();                             
            connMgr.setAutoCommit(false);  
                        
            //insert TZ_Grseq table
            sql = "update TZ_subj set "
                + " mftype=?, width=?, height=?, ismfbranch=?, iscentered=?,"
                + " Luserid  =?, LDATE = to_char(sysdate,'YYYYMMDDHH24MISS') "
                + " where subj=?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, box.getString("p_mftype"));
            pstmt.setInt   (2, box.getInt   ("p_width"));    
            pstmt.setInt   (3, box.getInt   ("p_height"));   
            pstmt.setString(4, box.getString("p_ismfbranch"));    
            pstmt.setString(5, box.getString("p_iscentered"));   
            pstmt.setString(6, v_luserid);   
            pstmt.setString(7, p_subj);
            isOk = pstmt.executeUpdate();
            connMgr.commit();
            
            if(isOk==1){
                if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
                sql = "delete from tz_mfsubj where subj=?";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, p_subj);
                isOk = pstmt.executeUpdate();
                
                if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
                String  v_menu = "";
                sql = "insert into tz_mfsubj (subj, menu, menunm, "
                    + " pgm, pgmtype, pgram1, pgram2, pgram3, pgram4, pgram5, orders, luserid, ldate) "
                    + " select ?,menu,menunm,pgm,pgmtype,pgram1,pgram2,pgram3,pgram4,pgram5,?,"
                    + "        ?, to_char(sysdate,'YYYYMMDDHH24MISS') "
                    + "   from tz_mfmenu where menu=?";  
                pstmt = connMgr.prepareStatement(sql);
                
                for(int i=0;i<(p_menus.length()/2);i++){
                    j++;
                    v_menu = p_menus.substring(i*2,i*2+2);
                    pstmt.setString(1, p_subj);
                    pstmt.setInt   (2, j);
                    pstmt.setString(3, v_luserid);
                    pstmt.setString(4, v_menu);
                    isOk = pstmt.executeUpdate();
                }
                
            }
            if (isOk > 0) {connMgr.commit();}
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            connMgr.setAutoCommit(true);            
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }   
            
    /**
    OBC 과정선택 List만들기
    @param String   
    @return String 
    */
    public static String makeObcSubjSelect(RequestBox box, String p_selsubj, String p_onchange) throws Exception{
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        String v_cp = "";
        String s_gadmin = box.getSession("gadmin");
        String s_userid = box.getSession("userid");

        String results = "<select name=\"s_subj\" ";
        if (!p_onchange.equals("")) results += " onChange=\""+p_onchange+"\" ";
        results += ">";
        
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();


            //베타업체 리스트
            if(s_gadmin.equals("S1") || s_gadmin.equals("T1") || s_gadmin.equals("M1") ){
            	//베타업체 담당자일경우(해당업체의 정보만보여줌)
	            sql = "select cpseq, cpnm ";
	            sql += " from tz_cpinfo where userid = " + SQLString.Format(s_userid);	
	            sql += " order by cpnm";	            
	                        
            	ls = connMgr.executeQuery(sql);
            	
            	if(ls.next())
            		v_cp = ls.getString("cpseq");
            	else
            		v_cp = "";
           	}
			
            sql  = "select subj, ";
            sql += "       subjnm ";
            sql += "  from tz_subj a, tz_cpinfo b";
            sql += " where contenttype='S'";
            //if (s_gadmin.equals("A1") && s_gadmin.equals("A2")){
            //	sql += " and cuserid='"+s_userid+"'";
            //}

            //베타업체 검색
            if(!v_cp.equals("")){
                sql += " and a.producer = " + StringManager.makeSQL(v_cp);
                sql += " and a.producer = b.cpseq ";
            }
            else{
            	sql += " and a.producer = b.cpseq ";
            }

            sql += " order by subjnm ";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                results +="<option value='"+ls.getString("subj")+"' ";
                if (p_selsubj.equals(ls.getString("subj"))) results +=" selected";
                results +=" >"+ls.getString("subjnm")+"</option>";
            }
        }            
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        results +="</select>";
        
        return  results;
    }
    
    /**
    Object ZIP 파일로 Directory 구성
    @param String   p_job   등록/수정구분(insert/update)
                    p_oid   Object ID
                    box     RequestBox
    @return String resuts   결과메세지
    */    
    public String   controlObjectFile(String p_job, String p_oid, RequestBox box) throws Exception{
        String  results="OK";
        String sql = "";   
        int isOk = 1;
        
        String  v_realPath="";
        String  v_tempPath="";
        boolean insert_success = false;
        boolean move_success = false;
        boolean update_success = false;
        boolean extract_success = false;
        boolean allDelete_success = false;

/*
        //----------------------   업로드되는 파일의 형식을 알고 코딩해야한다  --------------------------------
        String v_realFileName = box.getRealFileName("p_file");      //원 파일명
        String v_newFileName  = box.getNewFileName("p_file");       //실제 upload된 파일명
        //----------------------------------------------------------------------------------------------------------------------------
*/
        //---------------   업로드되는 파일의 형식을 알고 코딩해야한다  --------------------

        String [] v_realFileName = new String [FILE_LIMIT];
        String [] v_newFileName = new String [FILE_LIMIT];
        String v_file1 ="";

        for(int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName [i] = box.getRealFileName(FILE_TYPE + (i+1));
            v_newFileName [i] = box.getNewFileName(FILE_TYPE + (i+1));
        }

        if (!v_newFileName[0].equals("")) {
            v_file1=   v_newFileName[0];
        }

        //첨부파일이 있을 경우에만 실행.
        if(!v_file1.equals("")){
            // Object 폴더결정
            ConfigSet conf = new ConfigSet();
            //v_realPath = GetCodenm.get_config("object_locate") + p_oid;       //실제 Un-zip될 Dir        
            v_realPath = conf.getProperty("dir.objectupload") + p_oid;      //실제 Un-zip될 Dir
            v_tempPath = conf.getProperty("dir.upload.default");                    //upload된 파일 위치
    
            results = p_job;
            results+= "\\n\\n 0. v_realPath="+v_realPath;
            results+= "\\n\\n 0. v_tempPath="+v_tempPath;
            
            try {
                if(p_job.equals("insert")){                 // Object 등록시 
                    // 1. 폴더 생성
                    File newDir = new File(v_realPath);
                    newDir.mkdirs();
                    allDelete_success=true; 
                    results+= "\\n\\n 1. makeDirecotry OK. ";               
                }else if(p_job.equals("update")){           // Object 수정시
                    //1. 수정할 파일및 폴더 모두 삭제
                    FileDelete fd = new FileDelete();
                    allDelete_success = fd.allDelete(v_realPath);
                    results+= "\\n\\n 1. allDelete =  "+(new Boolean(allDelete_success).toString());
                    // 1. 폴더 생성
                    File newDir = new File(v_realPath);
                    newDir.mkdirs();
                    allDelete_success=true;
                    results+= "\\n\\n 1. makeDirecotry OK. ";
                }   
                
                // 2. 파일 이동
                if(allDelete_success) {
                    FileMove fc = new FileMove();
                    move_success = fc.move(v_realPath, v_tempPath, v_file1);
                }
                results+= "\\n\\n 2. move to ["+v_realPath+"] =  "+(new Boolean(move_success).toString());
                
                // 3. 압축 풀기
                if(move_success){
                    FileUnzip unzip = new FileUnzip();
                    extract_success = unzip.extract(v_realPath, v_file1);
                    results+= "\\n\\n 3. unzip to ["+v_realPath+"] =  "+(new Boolean(extract_success).toString());              
                    if(!extract_success){
                        FileDelete fd = new FileDelete();
                        fd.allDelete(v_realPath);
                    }
                }
                results+= "\\n\\n END of controlObjectFile() ";
                results = "OK";
            }
            catch (Exception ex) {
                FileDelete fd = new FileDelete();
                fd.allDelete(v_realPath);
                throw new Exception("ERROR results=" + results + "\r\n" + ex.getMessage());
            }
            finally {
                FileManager.deleteFile(v_realPath+"\\"+v_file1);
            }
        }
        
        return results;
    }
        
}
