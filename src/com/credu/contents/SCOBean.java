//**********************************************************
//  1. 제      목: SCO Object Operation Bean
//  2. 프로그램명: SCOBean.java
//  3. 개      요: SCO관리에 관련된 Bean
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.11
//  7. 수      정: 박미복 2004. 11.11
//**********************************************************

package com.credu.contents;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.contents.*;
import com.credu.scorm.*;
import com.credu.common.*;
import java.util.*;
import java.util.Date;


public class SCOBean {

    private static final String FILE_TYPE = "p_file";           // 파일업로드되는 tag name
    private static final int FILE_LIMIT = 1;                    // 페이지에 세팅된 파일첨부 갯수
    
    public SCOBean() {}

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
        SCOData data = null;
        
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
                //sql += " from tz_object a , tz_met_m c where a.oid = c.oid(+) and a.otype='SCO' and ";
				sql += " from tz_object a , tz_met_m c where a.oid  =  c.oid(+) and a.otype='SCO' and ";
            }else if(s_subj.equals("NO-SUBJ")){
                sql += " from tz_object a , tz_met_m c "
                     //+ " where a.otype='SCO' and a.oid = c.oid(+)  and a.oid not in (select distinct oid from tz_subjobj) and ";
					+ " where a.otype='SCO' and a.oid  =  c.oid(+)  and a.oid not in (select distinct oid from tz_subjobj) and ";
            }else{
                sql += " from tz_object a, tz_subjobj b , tz_met_m c "
                     + " where a.otype='SCO' and a.oid=b.oid and b.subj="+StringManager.makeSQL(s_subj)+" and "
					 //+ " a.oid = c.oid(+) and ";
					+ " a.oid  =  c.oid(+) and ";
            }
            sql += "  upper(a.sdesc) like '%"+s_gubun.toUpperCase()+"%' ";
/*
			 // connect by 해결해야됨					
			sql += " connect by a.highoid = prior a.oid start with a.highoid is null ";
*/
            sql += "  order by a.oid asc";
			
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data=new SCOData(); 
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
    public SCOData SelectObjectData(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null,ls2=null;
        ArrayList list1 = null, list2=null;
        String sql  = "", sql2="";
        SCOData data = null;
        
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
                data=new SCOData(); 
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
        SCOData data = null;
        
        String p_scolocate = box.getString("p_scolocate");
		
        try {
             connMgr = new DBConnectionManager();
			 list1 = new ArrayList();

            sql = "select a.oid , a.otype, a.filetype, a.npage, a.sdesc,a.master "
                + " ,a.starting,a.server,a.subj,a.parameterstring,a.datafromlms "
                + " ,a.identifier,a.prerequisites,a.masteryscore,a.maxtimeallowed "
                + " ,a.timelimitaction,a.sequence,a.thelevel,a.luserid,a.ldate,a.oidnumber,a.highoid,a.metalocation,a.scolocate "
				+ " , a.scoall, a.scotitle, a.master, level as scothelevel, b.metadata_idx  "
                //+ "  from tz_object a, tz_met_m b  where a.oid = b.oid(+) and a.scolocate="+StringManager.makeSQL(p_scolocate)
				+ "  from tz_object a, tz_met_m b  where a.oid  =  b.oid(+) and a.scolocate="+StringManager.makeSQL(p_scolocate)
				+ "  connect by a.highoid = prior a.oid start with a.highoid is null order by a.oidnumber ";
                                
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data=new SCOData(); 
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
	   }            
        catch (Exception ex) {          
			ex.printStackTrace();
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }    
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
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
	   SCOData data = null;
	   String oid = null;
	   
	   try {
            connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			sql = " select * from ( select rownum rnum, a.oid, a.lesson from tz_subjobj a , tz_object b  "
			    + " where a.subj = '"+ subj + "' and a.oid=b.oid and rtrim(ltrim(b.STARTING)) is not null  and a.oid > ( "
			    + " SELECT max(oid) FROM tz_progress   WHERE subj = '"+ subj + "'  AND YEAR = '"+ year + "' AND subjseq = '"+ subjseq + "' "
				+ " AND userid = '"+ userid + "' and lessonstatus = 'complete' ) ) where rnum < 2" ;
			
			ls = connMgr.executeQuery(sql);
            
			if (ls.next()) {
				data=new SCOData(); 
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

					data=new SCOData(); 
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


	public ArrayList BookmarkInfoPreview(String subj, String year, String subjseq , String userid) throws Exception {
	   DBConnectionManager connMgr = null;
       ListSet ls = null, ls2 = null, ls3 = null, ls4 = null;
	   String sql  = "", sql2 = "", sql3 ="";	   
	   ArrayList list1 = null;
	   SCOData data = null;
	   String oid = null;
	   
	   try {
            connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			/* 2005.11.17_하경태 : Oracle -> Mssql
			sql = "  select * from ( select distinct a.oid,  a.starting, b.lesson   "
			    + " from tz_object a , tz_subjobj b , tz_previewobj c  "
			    + " where a.oid = b.oid  and b.oid = c.oid and  b.subj = '"+ subj + "' and trim(a.starting) is not null "
				+ " connect by a.highoid = prior a.oid start with a.highoid is null  order by  "
				+ " to_number(b.lesson) asc , a.oid asc) where rownum = 1 " ;
				*/
			sql = "  select distinct a.oid,  a.starting, b.lesson   "
			    + " from tz_object a , tz_subjobj b , tz_previewobj c  "
			    + " where a.oid = b.oid  and b.oid = c.oid and  b.subj = '"+ subj + "' and rtrim(ltrim(a.starting)) is not null ";
// MS_SQL CONNECT BY 임시 주석			
//			sql += " connect by a.highoid = prior a.oid start with a.highoid is null  ";
			sql	+= " order by b.lesson asc , a.oid asc " ;

			System.out.println("BookmarkPreview sql = " + sql);
			ls = connMgr.executeQuery(sql);
            
			if (ls.next()) {
				data=new SCOData(); 
				//oid = ls.getString("oid");
                data.setOid             (ls.getString("oid"));
                data.setLesson           (ls.getString("lesson"));
				data.setStarting        (ls.getString("starting"));				
				list1.add(data);  
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
       return list1;
	}



	public String DeleteScoOid(RequestBox box) throws Exception {
	   DBConnectionManager connMgr = null;       
	   ListSet ls = null, ls2 = null;
	   String sql  = "";	
       String results  = "";	
	   int metadata_idx = 0;
	   int metacount = 0;
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

			if(ls2 != null) { try { ls.close(); }catch (Exception e1) {} }

         
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
			
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }   
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        } 
		
		return results;
	}


	public ArrayList performScoPackageList(RequestBox box) throws Exception {
	   DBConnectionManager connMgr = null;       
	   ListSet ls = null;
	   ArrayList list1 = null;
	   String sql  = "";
	   SCOData data = null;

	   try {
            connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			sql = " select distinct scolocate, scotitle, substring(ldate, 1,10) as ldate "
			    + " from tz_object where otype='SCO' order by scotitle ";            
			ls = connMgr.executeQuery(sql);
			
			while(ls.next()) {
				data=new SCOData(); 
				data.setScolocate       (ls.getString("scolocate"));				
				data.setScoTitle          (ls.getString("scotitle"));
				data.setLdate          (ls.getString("ldate"));
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

	public ArrayList performScoPackageListBeta(RequestBox box) throws Exception {
	   DBConnectionManager connMgr = null;       
	   ListSet ls = null;
	   ArrayList list1 = null;
	   String sql  = "";
	   SCOData data = null;
       String s_gadmin = box.getSession("gadmin");
       String s_userid = box.getSession("userid");	
	   String p_cpseq = box.getString("p_cpseq"); 

	   try {
            connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			if ((s_gadmin.equals("A1")) || (s_gadmin.equals("A2"))) {
				sql = " select distinct scolocate, scotitle, substring(ldate, 1,10) as ldate "
			    + " from tz_object where otype='SCO' order by scotitle ";                     
            } else {

				sql = " select distinct scolocate, scotitle, substring(ldate, 1,10) as ldate "
			    + " from tz_object where otype='SCO' and cpseq = '"+ p_cpseq +"' order by scotitle ";       
			
            }
			
			ls = connMgr.executeQuery(sql);
			
			while(ls.next()) {
				data=new SCOData(); 
				data.setScolocate       (ls.getString("scolocate"));				
				data.setScoTitle          (ls.getString("scotitle"));
				data.setLdate          (ls.getString("ldate"));
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
	   String p_cpseq  = box.getString("p_cpseq"); 

	   ConfigSet conf = new ConfigSet();			
	   String savePath = conf.getProperty("dir.scoobjectpath");

	   String str_DcourseCode	= "0000000000" + scono;			
	   str_DcourseCode	= str_DcourseCode.substring(str_DcourseCode.length()-10);

	   String[][] table;

	    try {
				connMgr = new DBConnectionManager();    			
				connMgr.setAutoCommit(false);  

				sql = " select NVL(max(oid),0) as max_scoid from tz_object where otype='SCO'";
				ls = connMgr.executeQuery(sql);

				if (ls.next()) {
					max_scoid = ls.getString("max_scoid") ;	
				}

				if (max_scoid.equals("0"))
				{
					max_scoid = "S0000000000";
				}

				System.out.println("max_scoidmax_scoidmax_scoidmax_scoidmax_scoid="+max_scoid);

				max_scoid = max_scoid.substring(max_scoid.length()-9);
				temp_scoid = Integer.parseInt(max_scoid) +1;

				System.out.print("temp_scoidtemp_scoidtemp_scoidtemp_scoidtemp_scoid="+temp_scoid);

				manifestTableBean tablebean = new manifestTableBean();
				String tmpfilepath = savePath + str_DcourseCode;
				tablebean.setXmlFile(tmpfilepath,filename);
				table = tablebean.getTableValue();
				ContentTitle = tablebean.getContentTitle();

		//		System.out.print(ContentTitle);

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
				sql += ", datafromlms, METALOCATION, OIDNUMBER, MASTER, SCOLOCATE,  OTYPE, SCOALL, FILETYPE, NPAGE, SCOTITLE, LDATE, HIGHOID, CPSEQ)";
				sql += " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'),?, '"+p_cpseq+"')";					
	
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
						pstmt.setInt(10,count);									// OIDNUMBER
						pstmt.setString(11,v_luserid);					// MASTER
						pstmt.setString(12,str_DcourseCode);			// SCOLOCATE
						pstmt.setString(13,"SCO");						// OTYPE
						pstmt.setInt(14,1);                          // SCOALL
						pstmt.setString(15,"HTML");			                // FILETYPE
						pstmt.setInt(16,0);			                // NPAGE
						pstmt.setString(17,ContentTitle);			                // SCOTITLE							
						pstmt.setString(18,table[i][10].trim());	// high_sco_code
				
						 isOk = pstmt.executeUpdate();
						 connMgr.commit();
						 count++;
					}
				}if (isOk > 0) {connMgr.commit();}
			}
		catch (Exception ex) {
			connMgr.rollback();
			ex.printStackTrace();			
            throw new Exception("results = " + "\r\n" +"sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
            
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
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
            
            sql = "select ltrim(to_char( to_number(NVL(max(oid),'1000000000'))+1, '0000000000')) oid from tz_object";

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
                + " , starting, server, subj, parameterstring, datafromlms "
                + " , identifier, prerequisites, masteryscore, maxtimeallowed "
                + " , timelimitaction, sequence, thelevel, luserid, ldate)"     
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
        
        String results = "";
        String v_luserid    = box.getSession("userid");
        String v_oid        = box.getString("p_oid");
		String p_scolocate  = box.getString("p_scolocate");
		String v_starting =  box.getString("p_starting");
        
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

		//	sql = " select oid from tz_progress where  oid = '"+ v_oid + "' ";
		//	ls2 = connMgr.executeQuery(sql);

		//	if (ls2.next()) {

		//	    results = "scomodify.fail";
		//		return results;
		//	}

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
				+ " where oid="+ StringManager.makeSQL(v_oid);

			isOk = connMgr.executeUpdate(sql);

			sql = "update tz_scosubjobj set "                   
				+ "  sdesc  = "+ StringManager.makeSQL(box.getString("p_sdesc"))
				+ " where oid="+ StringManager.makeSQL(v_oid);
			isOk = connMgr.executeUpdate(sql);

			sql = "update tz_subjobj set "                   
				+ "  sdesc  = "+ StringManager.makeSQL(box.getString("p_sdesc"))
				+ " where oid="+ StringManager.makeSQL(v_oid);
			isOk = connMgr.executeUpdate(sql);

			results = "OK";
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
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
                + " Luserid     =?, LDATE      = to_char(sysdate,'YYYYMMDDHH24MISS') "
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
                
            }if (isOk > 0) {connMgr.commit();}
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
    OBC 과정선택 List만들기
    @param String   
    @return String 
    */
    public static String makeObcSubjSelect(String p_selsubj, String p_onchange) throws Exception{
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        String results = "<select name=\"s_subj\" ";
        if (!p_onchange.equals("")) results += " onChange=\""+p_onchange+"\" ";
        results += ">";
        
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = "select subj,subjnm from tz_subj where isonoff='ON' and contenttype='S' order by subjnm ";
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


    
   public void ScoJindo(String strCourse_children, String strCourse_info_sco, String strLecture_info_detail, String strUser_sco_info, String strStudent_preference, String strObjectives, String strInteractions, String strInteractions_objectives, String strInteractions_correct_responses , String strError_info , String strDiagnostic_info, String strMember_info, String strError_init, String strRecord_count ) throws Exception{
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        Log.sys.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

		String course_children[] = setArray(strCourse_children);

		Log.sys.println("course_children[1]="+course_children[1]);


        String course_info_sco[] = setArray(strCourse_info_sco);

		Log.sys.println("1");


        String lecture_info_detail[] = setArray(strLecture_info_detail);
		Log.sys.println("2");
        String user_sco_info[] = setArray(strUser_sco_info);
		Log.sys.println("3");
       String student_preference[] = setArray(strStudent_preference);
		Log.sys.println("4");
        String objectives[][] = setMultiArray(strObjectives, 7);
		Log.sys.println("5");
        String interactions[][] = setMultiArray(strInteractions, 9);
		Log.sys.println("6");

	
		String interactions_objectives[][] = setMultiArray(strInteractions_objectives, 4);
	
		Log.sys.println("7");
        String interactions_correct_responses[][] = setMultiArray(strInteractions_correct_responses, 4);
	    Log.sys.println("8");
	
        String error_info[][] = setMultiArray(strError_info, 2);
		Log.sys.println("9");
        String diagnostic_info[][] = setMultiArray(strDiagnostic_info, 2);
		Log.sys.println("10");

        String member_info[] = setArray(strMember_info);
		Log.sys.println("11");
        String error_init[] = setArray(strError_init);
		Log.sys.println("12");
        String record_count[] = setArray(strRecord_count);
		Log.sys.println("13");
        String userid = member_info[1];
        String user_name = member_info[2];
        String p_oid = member_info[3];
        String Commit_value = member_info[0];
		String p_subj = member_info[4];
		String p_year = member_info[5];
		String p_subjseq = member_info[6];
		String p_lesson = member_info[7];
		String gojindo = member_info[8];
		String wholetime = member_info[9];
		int mhour = 0;
		int mminute = 0;
		int msecond = 0;
		int tempSecond1 =0;
		int tempSecond2 =0;
		String milisecond ="";
		String total_time = "";
		String session_time = "";
		String rhour = "";
		String rminute = "";
		String rsecond = "";
		String rmilisecond = "";
		String rhour_1 = "";
		String rminute_1 = "";
		String rsecond_1 = "";
		String rmilisecond_1 = "";


		String wholetime_1= String.valueOf(new Date().getTime());

	
		int elapsedSeconds = (int)( (Long.parseLong(wholetime_1) - Long.parseLong(wholetime)) / 1000);
		milisecond = String.valueOf(wholetime_1).substring(11,13);		

        if (elapsedSeconds >= 3600)
        {
			mhour = ( elapsedSeconds / 3600);
			tempSecond1 = (elapsedSeconds - (mhour * 3600));
			 if (tempSecond1 >= 60)
			 {
				 mminute = ( tempSecond1 / 60);
				 msecond = (tempSecond1 - (mminute * 60));
			 } else {
				 msecond = tempSecond1 ;
			 }
        } else {
			if (elapsedSeconds >= 60)
			{
				mminute = ( elapsedSeconds / 60);
				msecond = (elapsedSeconds - (mminute * 60));
			} else {
				msecond = elapsedSeconds;
			}
		}

		rhour = String.valueOf(mhour);
		rminute = String.valueOf(mminute);
		rsecond = String.valueOf(msecond);

		if (rhour.length() < 2)
		{
			rhour = "0" +rhour;
		}
		
		if (rminute.length() < 2 )
		{
			rminute = "0" + rminute;
		}

		if (rsecond.length() < 2 )
		{
			rsecond = "0" + rsecond;
		}

		session_time =  rhour +":"+rminute+":"+rsecond+"."+milisecond;

//		System.out.println("session_time session_time session_time session_time ="+session_time); 
				
		Log.sys.println("gojindo="+gojindo);

		int nObjCount = Integer.parseInt(record_count[0]);
        int nInteractCount = Integer.parseInt(record_count[1]);
        int nInteractObjCount = Integer.parseInt(record_count[2]);
        int ninteractCorResCount = Integer.parseInt(record_count[3]);
        int nErrorCount = Integer.parseInt(record_count[4]);
        int nDiagnosticCount = Integer.parseInt(record_count[5]);
        int IdxNo = 1;
        String sResult = "true";
        String currentErrorCode = "0";
		String first_end="";
        boolean exit = false;
		String sql  = "";
        String v_totaltime = "";
		int total_count = 0;
		int jindo_count = 0;
		String jindo_pct = "";
		int jindo_pre_count = 0;
		
		PreparedStatement pstmt = null;
		
		try
        { 
			connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sResult = "true";

	//		System.out.println("gojindogojindogojindogojindo="+gojindo);

			if (gojindo.equals("1")) { // 일 진도제한에 걸리지 않는다면 진도체크한다.

			   sql = " update   tz_object  set datafromlms =  ?  where oid = ?  ";  
				
				pstmt = connMgr.prepareStatement(sql);
				pstmt.setString(1,course_info_sco[12]);
				pstmt.setString(2, p_oid);

				pstmt.executeUpdate();
				if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }

				sql = " update  tz_subjobj  set commentsfromlms = ? where subj = ? and oid = ? ";   				

				pstmt = connMgr.prepareStatement(sql);
				pstmt.setString(1, lecture_info_detail[7]);
				pstmt.setString(2, p_subj);
				pstmt.setString(3, p_oid);

				pstmt.executeUpdate();
				if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }				

				String strJindoCk = "incomplete";
				if(user_sco_info[5].trim().equals("passed") || user_sco_info[5].trim().equals("failed") || user_sco_info[5].trim().equals("completed") || user_sco_info[5].trim().equals("browsed"))
				strJindoCk = "complete";

				Log.sys.println("strJindoCk="+strJindoCk);
				Log.sys.println("strJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCk="+strJindoCk);

				v_totaltime = user_sco_info[10].substring(user_sco_info[10].length()-11);

				sql = " select total_time from tz_progress "
					+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
					+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "'   ";		
                 System.out.println(sql);

				ls = connMgr.executeQuery(sql);
				if(ls.next())
				{
					total_time = ls.getString("total_time");
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

				if (total_time.length() == 10 )
				{
					total_time = total_time +"0";
				}

		//			System.out.println("total_timetotal_timetotal_timetotal_timetotal_time="+total_time);

				if (total_time.equals(""))
				{
					total_time = "00:00:00.00";
				}

		//		System.out.println("total_timetotal_timetotal_timetotal_timetotal_time="+total_time);

                rhour_1 = total_time.substring(0,2);
				rminute_1 = total_time.substring(3,5);
				rsecond_1  = total_time.substring(6,8);
				rmilisecond_1 = String.valueOf(Integer.parseInt(total_time.substring(9,11)) + Integer.parseInt(milisecond)) ;

				
                rhour_1 = String.valueOf(Integer.parseInt(rhour) + Integer.parseInt(rhour_1));
				rminute_1 = String.valueOf(Integer.parseInt(rminute) + Integer.parseInt(rminute_1));
				rsecond_1 = String.valueOf(Integer.parseInt(rsecond) + Integer.parseInt(rsecond_1));

                if (Integer.parseInt(rmilisecond_1) >= 100)
                {
					rsecond_1 = String.valueOf(Integer.parseInt(rsecond_1) + 1);
					rmilisecond_1 = String.valueOf(Integer.parseInt(rmilisecond_1) - 100);
                }
				
				if (Integer.parseInt(rsecond_1) >= 60)
				{
                   rminute_1 = String.valueOf(Integer.parseInt(rminute_1) + 1);
				   rsecond_1 = String.valueOf(Integer.parseInt(rsecond_1) - 60) ;
				}

				if (Integer.parseInt(rminute_1) >= 60)
				{
					rhour_1 = String.valueOf(Integer.parseInt(rhour_1) + 1);
					rminute_1 = String.valueOf(Integer.parseInt(rminute_1) - 60) ;
				}

				if (rhour_1.length() < 2)
				{
					rhour_1 = "0" +rhour_1;
				}
				
				if (rminute_1.length() < 2 )
				{
					rminute_1 = "0" + rminute_1;
				}

				if (rsecond_1.length() < 2 )
				{
					rsecond_1 = "0" + rsecond_1;
				}


				total_time =  rhour_1 +":"+rminute_1+":"+rsecond_1+"."+rmilisecond_1;

		//		System.out.println("22222222222222222222total_time="+total_time);

		///		System.out.println("strJindoCkstrJindoCkstrJindoCkstrJindoCk="+strJindoCk);				


				if (strJindoCk.equals("complete")) {

					sql = " select count(*) jindo_pre_count from tz_progress "
						+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
						+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' and lessonstatus ='complete'  ";		



					ls = connMgr.executeQuery(sql);
					if(ls.next())
					{
						jindo_pre_count = ls.getInt("jindo_pre_count");
					}
					if(ls != null) { try { ls.close(); }catch (Exception e1) {} }
				    
					if (jindo_pre_count == 0)
					{
						sql = "update tz_progress set lessonstatus = '" + strJindoCk + "'  , ldate =  to_char(sysdate,'YYYYMMDDHH24MISS')  "
						+ " , first_end = to_char(sysdate,'YYYYMMDDHH24MISS') , session_time = '"+session_time+"' , total_time = '"+total_time+"' "
						+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
						+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";		
						Log.sys.println(sql);
					} else {
						sql = "update tz_progress set lessonstatus = '" + strJindoCk + "'  ,  session_time = '"+session_time+"' , total_time = '"+total_time+"' "
						+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
						+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";		
						Log.sys.println(sql);
					}
						
						connMgr.executeQuery(sql);	


//					sql = " select count(*) as total_count from tz_object a, tz_subjobj b "
//						+ " where  a.oid = b.oid and b.SUBJ = '" + p_subj + "' and trim(starting) is not null ";

//					ls = connMgr.executeQuery(sql);
//					if(ls.next())
//					{
//						total_count = ls.getInt("total_count");
//					}
//					if(ls != null) { try { ls.close(); }catch (Exception e1) {} }
					
					//sql = " select count(*) jindo_count from tz_progress "
					//	+ " where subj= '" + p_subj + "'  and userid ='" + userid + "'  and lessonstatus ='complete' ";
					//2005.06.15 by heesung2
//					sql = " select count(*) jindo_count from tz_progress "
//						+ " where subj= '" + p_subj + "' and subjseq ='" + p_subjseq + "' "
//						+ "		  and userid ='" + userid + "' and year ='" + p_year + "' "
//						+ "		  and lessonstatus ='complete' ";

//					ls = connMgr.executeQuery(sql);
//					if(ls.next())
//					{
//						jindo_count = ls.getInt("jindo_count");
//					}
//					 double d = (double)Math.round((double)jindo_count/total_count*100*100)/100;

					 
					
	//				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

					//2005.06.24 by heesung2
					//sql = "update tz_student set tstep = " + d + " where subj = '" + p_subj + "' and  userid = '" + userid + "' ";                
					
					//sql = "update tz_student set tstep = " + d + " where subj = '" + p_subj + "' and year = '" + p_year + "' and subjseq = '" + p_subjseq + "' and  userid = '" + userid + "' ";                
					//Log.sys.println(sql);
					//connMgr.executeQuery(sql);	
					
					
					//2005.07.13 by heesung2
					CalcUtil.getInstance().calc_score(connMgr,CalcUtil.STEP,p_subj,p_year,p_subjseq,userid);

					
				} else {

					Log.sys.println("strJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCk="+strJindoCk);
					
					sql = "update tz_progress set lessonstatus = '"+strJindoCk+"'   "
						+ " ,  session_time = '"+session_time+"', total_time = '"+total_time+"' "
						+ " where subjseq = '"+p_subjseq+"' and lesson = '"+p_lesson+"'  and oid = '"+p_oid+"' "
						+ " and subj = '"+p_subj+"' and year ='"+p_year+"' and userid = '"+userid+"' ";
				   
				  
					Log.sys.println(sql);
					connMgr.executeQuery(sql);	
				}

				total_time = "00" + total_time;
				
				sql = " select oid from tz_user_scoinfo "
					+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
					+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";
						
				ls = connMgr.executeQuery(sql);
				if(ls.next())
				{
					
					sql = "update tz_user_scoinfo set "
						+ " core_lesson_location = ? ,"
						+ " core_credit = ? , "
						+ " core_lesson_status = ? ,  "
						+ " core_entry = ? ,  "
						+ " core_score_raw =  ? , "
						+ " core_score_max = ? ,  "
						+ " core_score_min = ? ,  "
						+ " core_total_time = ? ,  "
						+ " core_lesson_mode = ? ,  "
						+ " core_exit = ? ,  "
						+ " core_session_time = ? ,  "
						+ " suspend_data = ? ,  "
						+ " Comments = ?   "					
						+ " where subjseq =? and lesson = ?  and oid = ? "
						+ " and subj = ? and year =? and userid = ? ";	
						
						
						pstmt = connMgr.prepareStatement(sql);
						pstmt.setString(1, user_sco_info[3]);
						pstmt.setString(2, user_sco_info[4]);
						pstmt.setString(3, user_sco_info[5]);
						pstmt.setString(4, user_sco_info[6]);
						pstmt.setString(5, user_sco_info[7]);
						pstmt.setString(6, user_sco_info[8]);
						pstmt.setString(7, user_sco_info[9]);
						pstmt.setString(8, total_time);
						pstmt.setString(9, user_sco_info[11]);
						pstmt.setString(10, user_sco_info[12]);
						pstmt.setString(11, session_time);
						pstmt.setString(12, user_sco_info[14]);
						pstmt.setString(13, user_sco_info[15]);
						pstmt.setString(14, p_subjseq);
						pstmt.setString(15, p_lesson);
						pstmt.setString(16, p_oid);
						pstmt.setString(17, p_subj);
						pstmt.setString(18, p_year);
						pstmt.setString(19, userid);

						pstmt.executeUpdate();
						if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
					
				} else {
					
					 sql = " insert into tz_user_scoinfo ( "
						+ " subj, year, userid, subjseq, lesson, oid , core_lesson_location, core_credit, core_lesson_status, "
						+ " core_entry, core_score_raw, core_score_max, core_score_min, core_total_time, core_lesson_mode, "
						+ " core_exit, core_session_time, suspend_data, Comments ) values ( "
						+ " ?, ?, ?, ?, ?,?,?,? ,? ,? ,? ,? ,? ,? ,? ,? , ? ,?,? )";

						
						pstmt = connMgr.prepareStatement(sql);
						pstmt.setString(1, p_subj);
						pstmt.setString(2, p_year);
						pstmt.setString(3, userid);
						pstmt.setString(4, p_subjseq);
						pstmt.setString(5, p_lesson);
						pstmt.setString(6, p_oid);
						pstmt.setString(7, user_sco_info[3]);
						pstmt.setString(8, user_sco_info[4]);
						pstmt.setString(9, user_sco_info[5]);
						pstmt.setString(10, user_sco_info[6]);
						pstmt.setString(11, user_sco_info[7]);
						pstmt.setString(12, user_sco_info[8]);
						pstmt.setString(13, user_sco_info[9]);
						pstmt.setString(14, total_time);
						pstmt.setString(15, user_sco_info[11]);
						pstmt.setString(16, user_sco_info[12]);
						pstmt.setString(17, session_time);
						pstmt.setString(18, user_sco_info[14]);
						pstmt.setString(19, user_sco_info[15]);

						pstmt.executeUpdate();
						if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }				
				}

				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

				sql = "select oid from tz_student_pre "
				+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
				+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";
							
				ls = connMgr.executeQuery(sql);
				if(ls.next())
				{
					sql = "update tz_student_pre set "
						+ " student_preference_audio = ?,"
						+ " student_preference_language = ? , "
						+ " student_preference_speed = ?,  "
						+ " student_preference_text = ?   "								
						+ " where subjseq =? and lesson = ? and oid = ? "
						+ " and subj = ? and year =? and userid = ? ";					
						
					   
						pstmt = connMgr.prepareStatement(sql);
						pstmt.setString(1, student_preference[1]);
						pstmt.setString(2, student_preference[2]);
						pstmt.setString(3, student_preference[3]);
						pstmt.setString(4, student_preference[4]);
						pstmt.setString(5, p_subjseq);
						pstmt.setString(6, p_lesson);
						pstmt.setString(7, p_oid);
						pstmt.setString(8, p_subj);
						pstmt.setString(9, p_year);
						pstmt.setString(10, userid);
						
						pstmt.executeUpdate();
						if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }			
				} else {
					sql = " insert into tz_student_pre ( "
						+ " subj, year, userid, subjseq, lesson, oid , student_preference_audio, student_preference_language,  "
						+ " student_preference_speed, student_preference_text ) values ( "
						+ " ?, ?, ?, ?, ?, ?,  ? , ? , ? , ? )";
											
						pstmt = connMgr.prepareStatement(sql);
						pstmt.setString(1, p_subj);
						pstmt.setString(2, p_year);
						pstmt.setString(3, userid);
						pstmt.setString(4, p_subjseq);
						pstmt.setString(5, p_lesson);
						pstmt.setString(6, p_oid);
						pstmt.setString(7, student_preference[1]);
						pstmt.setString(8, student_preference[2]);
						pstmt.setString(9, student_preference[3]);
						pstmt.setString(10, student_preference[4]);
						
						pstmt.executeUpdate();
						if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }			
				}

				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }  

		/* 		for(int i = 0; i < nObjCount; i++)
				{
					sql = " select oid from tz_objectives "
						+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
						+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
						+ " and objectives_num = "+ Integer.parseInt(objectives[i][1]) +" ";
				
					ls = connMgr.executeQuery(sql);

				sql = "update tz_objectives set "
						+ " objectives_id = ? ,"
						+ " objectives_score_row = ? , "
						+ " objectives_score_max = ? ,  "
						+ " objectives_score_min = ? ,  "		
						+ " objectives_status = ?  "	
						+ " where subjseq = ? and lesson = ?  and oid = ? "
						+ " and subj = ? and year =? and userid = ? "
						+ " and objectives_num = ? "; 

				pstmt = connMgr.prepareStatement(sql);
		
				for(exit = false; ls.next(); exit = true)
					{
						pstmt.setString(1, objectives[i][2]);
						pstmt.setString(2, objectives[i][3]);
						pstmt.setString(3, objectives[i][4]);
						pstmt.setString(4, objectives[i][5]);
						pstmt.setString(5, objectives[i][6]);
						pstmt.setString(6, p_subjseq);
						pstmt.setString(7, p_lesson);
						pstmt.setString(8, p_oid);
						pstmt.setString(9, p_subj);
						pstmt.setString(10, p_year);
						pstmt.setString(11, userid);
						pstmt.setInt(12, Integer.parseInt(objectives[i][1]));
						
						pstmt.executeUpdate();														
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }			
				
					sql = " insert into tz_objectives ( "
						+ " subj, year, userid, subjseq, lesson, oid , objectives_num,  objectives_id, objectives_score_row,  "
						+ " objectives_score_max, objectives_score_min, objectives_status ) values ( "
						+ " ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ?, ?  )";
					pstmt = connMgr.prepareStatement(sql);

					if(!exit)
					{ 	
						pstmt.setString(1, p_subj);
						pstmt.setString(2, p_year);
						pstmt.setString(3, userid);
						pstmt.setString(4, p_subjseq);
						pstmt.setString(5, p_lesson);
						pstmt.setString(6, p_oid);
						pstmt.setInt(7, Integer.parseInt(objectives[i][1]));
						pstmt.setString(8, objectives[i][2]);
						pstmt.setString(9, objectives[i][3]);
						pstmt.setString(10, objectives[i][4]);
						pstmt.setString(11, objectives[i][5]);
						pstmt.setString(12, objectives[i][6]);
						
						pstmt.executeUpdate();																
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }					
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} } 

				for(int i = 0; i < nInteractCount; i++)
				{
					sql = "select oid from tz_interactions "
					+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
					+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
					+ " and interactions_num = "+ Integer.parseInt(interactions[i][1]) +" "; 			
				
					ls = connMgr.executeQuery(sql);	
					
					sql = "update tz_interactions set "
						+ " interactions_id = ? ,"
						+ " interactions_time = ? , "
						+ " interactions_type = ? ,  "
						+ " interactions_weighting= ? ,  "		
						+ " interactions_student_response = ? ,  "	
						+ " interactions_result = ? ,  "	
						+ " interactions_latency = ?  "	
						+ " where subjseq =? and lesson = ?  and oid = ? "
						+ " and subj = ? and year =? and userid = ? "
						+ " and interactions_num = ? "; 
						
					pstmt = connMgr.prepareStatement(sql);
					
					for(exit = false; ls.next(); exit = true)
					{
						pstmt.setString(1, interactions[i][2]);
						pstmt.setString(2, interactions[i][3]);
						pstmt.setString(3, interactions[i][4]);
						pstmt.setString(4, interactions[i][5]);
						pstmt.setString(5, interactions[i][6]);
						pstmt.setString(6, interactions[i][7]);
						pstmt.setString(7, interactions[i][8]);
						pstmt.setString(8, p_subjseq);
						pstmt.setString(9, p_lesson);
						pstmt.setString(10, p_oid);
						pstmt.setString(11, p_subj);
						pstmt.setString(12, p_year);
						pstmt.setString(13, userid);
						pstmt.setInt(14, Integer.parseInt(interactions[i][1]));

						pstmt.executeUpdate();					
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }	

					sql = " insert into tz_interactions ( "
						+ " subj, year, userid, subjseq, lesson, oid , interactions_num , interactions_id, interactions_time,  "
						+ " interactions_type, interactions_weighting, interactions_student_response,interactions_result, interactions_latency ) values ( "
						+ " ?, ?, ?, ?, ?, ?, ?, ? , ? , ?, ?, ?, ?, ? )";
						
					pstmt = connMgr.prepareStatement(sql);

					if(!exit)
					{					
						pstmt.setString(1, p_subj);
						pstmt.setString(2, p_year);
						pstmt.setString(3, userid);
						pstmt.setString(4, p_subjseq);
						pstmt.setString(5, p_lesson);
						pstmt.setString(6, p_oid);
						pstmt.setInt(7, Integer.parseInt(interactions[i][1]));
						pstmt.setString(8, interactions[i][2]);
						pstmt.setString(9, interactions[i][3]);
						pstmt.setString(10, interactions[i][4]);
						pstmt.setString(11, interactions[i][5]);
						pstmt.setString(12, interactions[i][6]);
						pstmt.setString(13, interactions[i][7]);
						pstmt.setString(14, interactions[i][8]);

						pstmt.executeUpdate();						
					} 
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }		
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

				for(int i = 0; i < nInteractObjCount; i++)
				{
					sql = "select oid from tz_inter_obj "
					+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
					+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
					+ " and interactions_num = "+ Integer.parseInt(interactions_objectives[i][1])+"  and  interactions_objectives_num = "+ Integer.parseInt(interactions_objectives[i][2]) +" "; 			
				
					ls = connMgr.executeQuery(sql);

					sql = "update tz_inter_obj set "
						+ " interactions_objectives_id = ? "					
						+ " where subjseq =? and lesson = ?  and oid = ? "
						+ " and subj = ? and year =? and userid = ? "
						+ " and interactions_num = ?  and  interactions_objectives_num = ? "; 			
						
					pstmt = connMgr.prepareStatement(sql);

					for(exit = false; ls.next(); exit = true)
					{	
						pstmt.setString(1, interactions_objectives[i][3]);					
						pstmt.setString(2, p_subjseq);
						pstmt.setString(3, p_lesson);
						pstmt.setString(4, p_oid);
						pstmt.setString(5, p_subj);
						pstmt.setString(6, p_year);
						pstmt.setString(7, userid);
						pstmt.setInt(8, Integer.parseInt(interactions_objectives[i][1]));
						pstmt.setInt(9, Integer.parseInt(interactions_objectives[i][2]));

						pstmt.executeUpdate();					
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }	

					sql = " insert into tz_inter_obj ( "
						+ " subj, year, userid, subjseq, lesson, oid , interactions_num, interactions_objectives_num,  "
						+ " interactions_objectives_id ) values ( "
						+ " '" + p_subj + "', '" + p_year + "', '" + userid + "', '" + p_subjseq + "', '" + p_lesson + "', '" + p_oid + "', "
						+ " "+ Integer.parseInt(interactions_objectives[i][1]) + ",  "+ Integer.parseInt(interactions_objectives[i][2]) +" , '"+interactions_objectives[i][3]+"' )";
						
					pstmt = connMgr.prepareStatement(sql);

					if(!exit)
					{
						pstmt.setString(1, p_subj);
						pstmt.setString(2, p_year);
						pstmt.setString(3, userid);
						pstmt.setString(4, p_subjseq);
						pstmt.setString(5, p_lesson);
						pstmt.setString(6, p_oid);
						pstmt.setInt(7, Integer.parseInt(interactions_objectives[i][1]));
						pstmt.setInt(8, Integer.parseInt(interactions_objectives[i][2]));
						pstmt.setString(9, interactions_objectives[i][3]);					

						pstmt.executeUpdate();					
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }			
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

				for(int i = 0; i < ninteractCorResCount; i++)
				{
					sql = "select oid from tz_inter_cor "
					+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
					+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
					+ " and interactions_num = "+ Integer.parseInt(interactions_correct_responses[i][1])+"  and  nteractions_cor_res_num = "+ Integer.parseInt(interactions_correct_responses[i][2]) +" "; 			
				
					ls = connMgr.executeQuery(sql);

					sql = "update tz_inter_cor set "
						+ " interactions_cor_res_pattern = ? "					
						+ " where subjseq =? and lesson = ?  and oid = ? "
						+ " and subj = ? and year = ? and userid = ? "
						+ " and interactions_num = ?  and  nteractions_cor_res_num = ? "; 			
						
					pstmt = connMgr.prepareStatement(sql);

					for(exit = false; ls.next(); exit = true)
					{					
						pstmt.setString(1, interactions_objectives[i][3]);					
						pstmt.setString(2, p_subjseq);
						pstmt.setString(3, p_lesson);
						pstmt.setString(4, p_oid);
						pstmt.setString(5, p_subj);
						pstmt.setString(6, p_year);
						pstmt.setString(7, userid);
						pstmt.setInt(8, Integer.parseInt(interactions_correct_responses[i][1]));
						pstmt.setInt(9, Integer.parseInt(interactions_correct_responses[i][2]));

						pstmt.executeUpdate();					
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }	

					sql = " insert into tz_inter_cor ( "
						+ " subj, year, userid, subjseq, lesson, oid , interactions_num, interactions_cor_res_num,  "
						+ " interactions_cor_res_pattern ) values ( "
						+ " '" + p_subj + "', '" + p_year + "', '" + userid + "', '" + p_subjseq + "', '" + p_lesson + "', '" + p_oid + "', "
						+ " "+ Integer.parseInt(interactions_correct_responses[i][1]) + ",  "+ Integer.parseInt(interactions_correct_responses[i][2]) +" , '"+interactions_correct_responses[i][3]+"' )";
						
					pstmt = connMgr.prepareStatement(sql);

					if(!exit)
					{					
						pstmt.setString(1, p_subjseq);
						pstmt.setString(2, p_lesson);
						pstmt.setString(3, p_oid);
						pstmt.setString(4, p_subj);
						pstmt.setString(5, p_year);
						pstmt.setString(6, userid);
						pstmt.setInt(7, Integer.parseInt(interactions_correct_responses[i][1]));
						pstmt.setInt(8, Integer.parseInt(interactions_correct_responses[i][2]));
						pstmt.setString(9, interactions_objectives[i][3]);					

						pstmt.executeUpdate();						
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }		
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }  */

				Log.sys.println("SetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApi");
              
				connMgr.commit();

			}
		}            
        catch (Exception ex) {
            connMgr.rollback();
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
		
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

		
        
    }


	public String[] setArray(String strArray)
    {

		Log.sys.println("setArray=");
        StringTokenizer eleTok = new StringTokenizer(strArray, "*", false);
        int eleTokens = eleTok.countTokens();
        String ele_name[] = new String[eleTokens];
        for(int i = 0; i < eleTokens; i++)
        {
            ele_name[i] = eleTok.nextToken();
            if(i != 0)
                ele_name[i] = ele_name[i].substring(1, ele_name[i].length());
        }

        return ele_name;
    }

	public String[][] setMultiArray(String strArray, int intArrayNum)
    {
        StringTokenizer eleTok = new StringTokenizer(strArray, "*", false);
        int eleTokens = eleTok.countTokens();
        String ele_name[] = new String[eleTokens];
        String return_name[][] = new String[eleTokens][intArrayNum];
        for(int i = 0; i < eleTokens; i++)
        {
            ele_name[i] = eleTok.nextToken();

			Log.sys.println("ele_name[i]="+ele_name[i]);
            if(i != 0)
                ele_name[i] = ele_name[i].substring(1, ele_name[i].length());
            StringTokenizer eleTok1 = new StringTokenizer(ele_name[i], "@", false);
            int eleTokens1 = eleTok1.countTokens();
            String ele_name1[] = new String[eleTokens1];
            for(int j = 0; j < eleTokens1; j++)
            {
                ele_name1[j] = eleTok1.nextToken();
                if(j != 0)
                    return_name[i][j] = ele_name1[j].substring(1, ele_name1[j].length());
            }
        }
        return return_name;
    }
        
}