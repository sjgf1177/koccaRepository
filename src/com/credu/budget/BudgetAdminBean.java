//**********************************************************
//  1. 제      목: TUTOR ADMIN BEAN
//  2. 프로그램명: TutorAdminBean.java
//  3. 개      요: 강사관리 관리자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//**********************************************************
package com.credu.budget;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import com.credu.budget.*;
import com.credu.library.*;
import com.credu.system.*;

public class BudgetAdminBean {
    private ConfigSet config;
    private int row;

    public BudgetAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
    과정등록현황 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls    			= null;
        ArrayList	list  			= null;
        DataBox 	dbox  			= null;
 
//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
		
        String 		v_year         	= box.getStringDefault("s_year","");         	//년도
        String 		v_edugubun      = box.getStringDefault("s_edugubun","ALL");    	//교육유형
        String 		v_company   	= box.getStringDefault("s_company","ALL");   	//회사
        String 		v_edudept   	= box.getStringDefault("s_edudept","ALL");   	//교육주관팀
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");   //과정명
        String  	v_me		 	= box.getString("p_me"); //자기가 등록한 과정
        
        String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String  v_orderType     = box.getString("p_orderType");          		//정렬할 순서
        
        String  s_userid        = box.getSession("userid");
        int		v_pageno 		= box.getInt("p_pageno");        
    
        //총괄관리자/강사관리자는 Default N
	    if (v_me.equals("")) {
	    	if (box.getSession("gadmin").substring(0,1).equals("A") || box.getSession("gadmin").substring(0,1).equals("R"))
	    		v_me = "N";
	    	else
	    		v_me = "Y";
		}

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            head_sql = "select 	subj,";
			head_sql+= "	   		subjnm,";
			head_sql+= "	   		edugubun,";
			head_sql+= "	   		edustarta,";
			head_sql+= "	   		eduenda,";
			head_sql+= "	   		(select codenm from tz_code where gubun='0024' and code=tz_tsubj.edugubun) edugubunnm,";
			head_sql+= "	   		subjgubun,";
			//sql+= "	   		get_compnm(inusercomp,2,4) inusercompnm ";
			
			head_sql+="			get_compnm( inusercomp, 2,4  ) ||  \n";
			//2005.11.15_하경태 : Oracle -> Mssql
			//sql+="			decode(SUBSTR(inusercomp,1,4),'0101','(HMC)','0102','(KMC)','') inusercompnm \n";
			head_sql+="			(case substring(inusercomp,1,4) When '0101' Then '(HMC)' When '0102' Then '(KMC)' Else '' end ) inusercompnm \n";
			
			body_sql+= " from  	tz_tsubj ";
			body_sql+= " where  	1 = 1 ";
			
			//년도
			if (!v_year.equals("")) {
				body_sql+= "		and substring(edustarta,1,4) = '" + v_year + "' ";
			}
			
			//교육유형
			if (!v_edugubun.equals("ALL")) {
				body_sql+= "		and edugubun = '" + v_edugubun + "' ";				
			}
			
			//회사
			if (!v_company.equals("ALL")) {
				body_sql+= "		and substring(inusercomp,1,4) = '" + StringManager.substring(v_company, 0, 4) + "' ";				
			}
			
			//교육주관팀
			if (!v_edudept.equals("ALL")) {
				body_sql+= "		and inusercomp = '" + v_edudept + "' ";				
			}
			
			//과정명
			if (!v_srchsubjnm.equals("")) {
				body_sql+= "		and subjnm like '%" + v_srchsubjnm + "%' ";				
			}
			
			//자기가 등록한 과정
			if (v_me.equals("Y")) {
				body_sql+= "		and inuserid = '" + s_userid + "' ";				
			}
			
			if(v_orderColumn.equals("")) {
    			order_sql+= " order by subjnm ";
			} else {
				order_sql+= " order by " + v_orderColumn + v_orderType;
			}


			//sql+= "order by subjnm ";
			
            
			sql= head_sql+ body_sql+ order_sql;

			System.out.println("sql->" + sql);
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage();    //전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();    //전체 row수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("subj"		   ,ls.getString("subj"));
                dbox.put("subjnm"	   ,ls.getString("subjnm"));
                dbox.put("edugubun"	   ,ls.getString("edugubun"));
                dbox.put("subjgubun"	,ls.getString("subjgubun"));
                dbox.put("edustarta"   ,ls.getString("edustarta"));
                dbox.put("eduenda"	   ,ls.getString("eduenda"));
                dbox.put("d_edugubunnm",ls.getString("edugubunnm"));
                dbox.put("d_inusercompnm",ls.getString("inusercompnm"));

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
    과정내용보기
    @param box      receive from the form object and session
    @return TutorData
    */
     public DataBox selectSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls         	= null;
        String 	sql         = "";

        DataBox dbox        = null;
        String v_subj 		= box.getString("p_subj");
        
        try {
            connMgr = new DBConnectionManager();

             //select * from tz_tutor
            sql = "select 	subjnm,";
			sql+= "	   		edugubun,";
			sql+= "	   		(select codenm ";
			sql+= "	from 	tz_code ";
			sql+= "	where 	gubun = '0024' and ";
			sql+= "			 code  = tz_tsubj.edugubun) edugubunnm,";
			sql+= "	   		subjgubun,";
			//예산
			sql+= "	   		edustarta,";
			sql+= "	   		eduenda,";
			sql+= "	   		edutargeta,";
			sql+= "	   		edutmema,";
			sql+= "	   		edutseqa,";
			sql+= "	   		seqpermema,";
			sql+= "	   		eduplacea,";
			//실적
			sql+= "	   		edustartb,";
			sql+= "	   		eduendb,";
			sql+= "	   		edutargetb,";
			sql+= "	   		edutmemb,";
			sql+= "	   		edutseqb,";
			sql+= "	   		seqpermemb,";
			sql+= "	   		eduplaceb,";
			
			sql+= "	   		gyamt,";
			sql+= "	   		comments,";
			sql+= "	   		realfile,";
			sql+= "	   		savefile,";
			sql+= "	   		satisfaction,";
			sql+= "	   		indate,";
			sql+= "	   		inuserid,";
			sql+= "	   		inusercomp,";
			sql+="			get_compnm( inusercomp, 2,4  ) ||  \n";
			//2005.11.15_하경태 : Oracle -> Mssql
			//sql+="			decode(SUBSTR(inusercomp,1,4),'0101','(HMC)','0102','(KMC)','') inusercompnm \n";
			sql+="			case substring(inusercomp,1,4) When '0101' Then '(HMC)' When '0102' Then '(KMC)' Else '' End as inusercompnm \n";			
			//sql+= "	   		replace(get_compnm(inusercomp,4,4),'/','') inusercompnm ";
			sql+= "from 	tz_tsubj ";
			sql+= "where 	subj = '" + v_subj + "'";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("subjnm"		,ls.getString("subjnm"));
                dbox.put("edugubun"		,ls.getString("edugubun"));
                dbox.put("edugubunnm"	,ls.getString("edugubunnm"));
                dbox.put("subjgubun"	,ls.getString("subjgubun"));
                //예산
                dbox.put("edustarta"	,ls.getString("edustarta"));
                dbox.put("eduenda"		,ls.getString("eduenda"));
                dbox.put("edutargeta"	,ls.getString("edutargeta"));
                dbox.put("edutmema"		,ls.getString("edutmema"));
                dbox.put("edutseqa"		,ls.getString("edutseqa"));
                dbox.put("seqpermema"	,ls.getString("seqpermema"));
                dbox.put("eduplacea"	,ls.getString("eduplacea"));
                //실적
                dbox.put("edustartb"	,ls.getString("edustartb"));
                dbox.put("eduendb"		,ls.getString("eduendb"));
                dbox.put("edutargetb"	,ls.getString("edutargetb"));
                dbox.put("edutmemb"		,ls.getString("edutmemb"));
                dbox.put("edutseqb"		,ls.getString("edutseqb"));
                dbox.put("seqpermemb"	,ls.getString("seqpermemb"));
                dbox.put("eduplaceb"	,ls.getString("eduplaceb"));
                
                dbox.put("gyamt"		,ls.getString("gyamt"));
                dbox.put("comments"		,ls.getString("comments"));
                dbox.put("realfile"		,ls.getString("realfile"));
                dbox.put("savefile"		,ls.getString("savefile"));
                dbox.put("d_satisfaction"	,new Double(ls.getDouble("satisfaction"))); 
                dbox.put("indate"		,ls.getString("indate"));
                dbox.put("inuserid"		,ls.getString("inuserid"));
                dbox.put("inusercomp"	,ls.getString("inusercomp"));
                dbox.put("d_inusercompnm"	,ls.getString("inusercompnm"));
                
                System.out.println("here satisfaction = " + ls.getDouble("satisfaction"));
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }
    
	/**
    예산항목 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
    //subj 코드값이 있을경우 해당 과정의 예산/실적항목 리스팅
    //subj 코드값이 없을경우 TZ_CODE GUBUN=24 항목 리스팅
    public ArrayList selectBudgetSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        String 		sql      		= "";
        DataBox 	dbox  			= null;

		String 		v_subj			= box.getStringDefault("p_subj","");	//과정코드
		String 		gubun			= box.getStringDefault("gubun","A");//구분(A:인원,B:예산항목)
		String 		gubuncode		= "";
		
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

			if (gubun.equals("A")) {
				gubuncode = "0026";	//인원
			} else {
				gubuncode = "0025"; //예산
			}
				
			if (v_subj.equals("")) {
				//과정코드가 없는 경우 tz_code gubun=25의 예산항목을 가져온다.
            	sql = "select 	a.code,";
				sql+= "	   		a.codenm,";
				sql+= "	   		0 plandata,";
				sql+= "	   		0 execdata ";
				sql+= "from   	tz_code a ";
				sql+= "where  	a.gubun = '"+ gubuncode +"' ";
				sql+= "order by a.code";
            } else {
            	//과정코드가 있는 경우 해당 과정의 tz_code gubun=25 예산자료를 가져온다.
            	sql = "select 	a.code,";
				sql+= "	   		a.codenm,";
				sql+= "	   		NVL((select plandata from tz_tbudget where gubun='"+gubun+"' and subj='"+ v_subj +"' and gubuncode=a.code),0) plandata,";
				sql+= "	   		NVL((select execdata from tz_tbudget where gubun='"+gubun+"' and subj='"+ v_subj +"' and gubuncode=a.code),0) execdata ";
				sql+= "from   	tz_code a ";
				sql+= "where  	a.gubun = '"+ gubuncode +"' ";
				sql+= "order by a.code";
            }

			System.out.println("sql?"+sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("code",ls.getString("code"));
                dbox.put("codenm",ls.getString("codenm"));
                dbox.put("d_plandata",ls.getString("plandata"));
                dbox.put("d_execdata",ls.getString("execdata"));
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
    프로그램 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
    //subj 코드값이 있을경우 해당 과정의 예산/실적항목 리스팅
    //subj 코드값이 없을경우 TZ_CODE GUBUN=24 항목 리스팅
    public ArrayList selectSubjProgramList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        String 		sql      		= "";
        DataBox 	dbox  			= null;

		String 		v_subj			= box.getStringDefault("p_subj","");	//과정코드
		
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

          	sql = "select  seq,";
			sql+= "		   lessonnm,";
			sql+= "		   userid tutorid, ";
			sql+= "		   tutorgubun,";
			//2005.11.16_하경태 : Oracle -> Mssql
			//sql+= "		decode(tutorgubun, 'C', (select compnm from tz_tcomp where busino=userid), ";
			//sql+= "		 (select name from tz_tutor where userid=tz_tlesson.userid)) tutorname,";
			sql+= "		   decode(tutorgubun, 'I', '사내강사', 'O', '사외강사', 'C', '교육기관') tutorgubunnm,";
			sql+= "		   case tutorgubun When 'C' Then (select compnm from tz_tcomp where busino=userid) Else ";
			sql+= "		   (select name from tz_tutor where userid=tz_tlesson.userid) End as tutorname,";
			sql+= "		   case tutorgubun When 'I' Then '사내강사' When 'O' Then '사외강사' When 'C' Then '교육기관' end as tutorgubunnm,";
			sql+= "		   eduhr,";
			sql+= "		   price,";
			sql+= "		   grade ";
			sql+= "from    tz_tlesson ";
			sql+= "where   subj = '" + v_subj + "' ";
			sql+= "order by seq";

			System.out.println("sql?"+sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("seq"				,ls.getString("seq"));
                dbox.put("lessonnm"			,ls.getString("lessonnm"));
                dbox.put("tutorid"			,ls.getString("tutorid"));
                dbox.put("tutorgubun"		,ls.getString("tutorgubun"));
                dbox.put("d_tutorgubunnm"	,ls.getString("tutorgubunnm"));
                dbox.put("eduhr"			,new Integer(ls.getInt("eduhr")));
                dbox.put("price"			,new Integer(ls.getInt("price")));
                dbox.put("grade"			,new Double(ls.getDouble("grade")));
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
    강의과정 조회
    @param box      receive from the form object and session
    @return TUtorData
    */
     public ArrayList selectTutorSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls          = null;
        ArrayList 	list      	= null;
        String 		sql         = "";
        DataBox 	dbox      	= null;
        String 		v_userid	= box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            //select subj,subjnm
            sql = "select A.subj,B.subjnm from TZ_SUBJMAN A,TZ_SUBJ B ";
            sql+= "where A.userid = '"+v_userid+"' and A.gadmin='P1' and A.subj=B.subj";
            //System.out.println("sql=========>"+sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                //dbox.put("",);
                
                list.add(dbox);
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
    과정등록
    @param box      receive from the form object and session
    @return int
    */
     public int insertSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        
        String 	sql1         = "";
        String 	sql2         = "";
        String 	sql3         = "";
        String 	sql4         = "";
        
        int 	isOk1        = 0;
        int 	isOk2        = 0;
        int 	isOk3        = 0;
        int 	isOk4        = 0;
        
        int 	v_result     = 0;
        String 	v_userid     = box.getSession("userid");
        String 	v_usercomp 	 = box.getSession("comp");
        
        System.out.println("v_usercomp ==================="+v_usercomp);

        //Request
        String v_subjnm 	= box.getString("p_subjnm");			//과정명
        String v_edugubun 	= box.getString("p_edugubun");			// 교육유형
        
        String v_edustarta 	= box.getString("p_edustarta"); 		//교육시작일
        String v_eduenda 	= box.getString("p_eduenda"); 			//교육종료일
        String v_edutargeta = box.getString("p_edutargeta"); 		//교육대상
        
		//교육인원 시작	        
        int 	v_edumemcnt = box.getInt("p_edumemcnt"); 			//교육인원 총 갯수
        String	[] v_edumem	= new String [v_edumemcnt];				//교육인원
        String chkstring = "0";
        for(int i=0; i<v_edumemcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_edumem[i]		= box.getString("p_edumem" + chkstring + (i+1) + "a");
      	}
      	//교육인원 끝
        
        int 	v_edutmema 	 = box.getInt("p_edutmema"); 			//교육총인원
        int 	v_edutseqa 	 = box.getInt("p_edutseqa"); 			//교육총차수
        int 	v_seqpermema = box.getInt("p_seqpermema"); 			//차수당인원
        String 	v_eduplacea  = box.getString("p_eduplacea"); 		//교육장소

		//예산항목 시작
        int	   	v_budgetcnt = box.getInt("p_budgetcnt"); 			//예산총갯수
        String [] v_budget	= new String [v_budgetcnt];				//예산
        chkstring = "0";
        for(int i=0; i<v_budgetcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_budget[i]		= box.getString("p_budget" + chkstring + (i+1) + "a");
        	//System.out.println("v_budget[" + chkstring + (i+1) + "]a : "+v_budget[i]);
      	}
      	//예산항목 끝
        
        int    	v_gyamt 	= box.getInt("p_gyamt"); 				//고용보험환급예상금액

		//프로그램항목 시작
        int		v_lessoncnt 	= box.getInt("p_lessoncnt"); 		//프로그램 총 갯수
        String [] v_lessonnm	= new String [v_lessoncnt];	    	//프로그램명
        String [] v_tutorgubun	= new String [v_lessoncnt];	    	//강사구분(I:사내강사,O:사외강사,C:용역업체)
        String [] v_tutorid		= new String [v_lessoncnt];	    	//강사ID/사업자번호
        int	   [] v_eduhr		= new int [v_lessoncnt];	    	//교육시간
        int	   [] v_eduprice	= new int [v_lessoncnt];	    	//강사료
        
        for(int i=0; i<v_lessoncnt; i++) {
        	v_lessonnm[i]		= box.getStringDefault("p_lessonnm" + (i+1),"");
        	v_tutorgubun[i]		= box.getString("p_tutorgubun" + (i+1));
        	v_tutorid[i]		= box.getString("p_tutorid" + (i+1));
        	v_eduhr[i]			= box.getInt("p_eduhr" + (i+1));
        	v_eduprice[i]		= box.getInt("p_eduprice" + (i+1));
      	}
      	//프로그램항목 끝
      	
        try {

            connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			String v_subj = getMaxSubjcode(connMgr);
			System.out.println("v_subj="+v_subj);
			
            //과정정보 저장[1/3]
            sql1 = "insert into tz_tsubj(";
			sql1+= "		   subj,";
			sql1+= "		   subjnm,";
			sql1+= "		   edugubun,";
			sql1+= "		   subjgubun,";
			sql1+= "		   edustarta,";
			sql1+= "		   eduenda,";
			sql1+= "		   edutargeta,";
			sql1+= "		   edutmema,";
			sql1+= "		   edutseqa,";
			sql1+= "		   seqpermema,";
			sql1+= "		   eduplacea,";
			sql1+= "		   gyamt,";
			sql1+= "		   indate,";
			sql1+= "		   inuserid,";
			sql1+= "		   inusercomp) ";
			sql1+= "values(?,?,?,'B',?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDDHHMMss'),?,?) ";

            pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setString(1, v_subj);	
			pstmt1.setString(2, v_subjnm);
			pstmt1.setString(3, v_edugubun);
			pstmt1.setString(4, v_edustarta);
			pstmt1.setString(5, v_eduenda);
			pstmt1.setString(6, v_edutargeta);
			pstmt1.setInt(7, v_edutmema);
			pstmt1.setInt(8, v_edutseqa);
			pstmt1.setInt(9, v_seqpermema);
			pstmt1.setString(10, v_eduplacea);
			pstmt1.setInt(11, v_gyamt);
			pstmt1.setString(12, v_userid);
			pstmt1.setString(13, v_usercomp);
			
			isOk1 = pstmt1.executeUpdate();              //TZ_TSUBJ 등록
            
            //인원정보 저장 시작
            //GUBUN = 'A' : 인원정보
            sql2 = "insert into tz_tbudget( ";
			sql2+= "		   subj,";
			sql2+= "		   gubun,";
			sql2+= "		   gubuncode,";
			sql2+= "		   plandata,";
			sql2+= "		   execdata,";
			sql2+= "		   inusercomp ) ";
			sql2+= "values('" + v_subj + "','A',?,?,0,'"+v_usercomp+"') ";
			
			pstmt2 = connMgr.prepareStatement(sql2);
            
            //인원정보 저장[3/4]
            chkstring = "0";
        	for(int i=0; i<v_edumemcnt; i++) {
        		if (i>=9) chkstring = "";
        		pstmt2.setString(1, chkstring + (i+1));
	        	pstmt2.setString(2, v_edumem[i]);
				isOk2 = pstmt2.executeUpdate();	
	        }
	        //인원정보 저장 끝
	        
	        //예산정보 저장 시작
	        //GUBUN = 'B' : 예산정보
            sql3 = "insert into tz_tbudget( ";
			sql3+= "		   subj,";
			sql3+= "		   gubun,";
			sql3+= "		   gubuncode,";
			sql3+= "		   plandata,";
			sql3+= "		   execdata,";
			sql3+= "		   inusercomp ) ";
			sql3+= "values('" + v_subj + "','B',?,?,0,'"+v_usercomp+"') ";
            pstmt3 = connMgr.prepareStatement(sql3);
            
            //예산정보 저장[3/4]
            chkstring = "0";
	        for(int i=0; i<v_budgetcnt; i++) {
	        	if (i>=9) chkstring = "";
	        	pstmt3.setString(1, chkstring + (i+1));
	        	pstmt3.setString(2, v_budget[i]);
				isOk3 = pstmt3.executeUpdate();	
	        }
	        //예산정보 저장 끝

            
            //프로그램 저장 시작
            sql4 = "insert into tz_tlesson( ";
			sql4+= "		   subj,";
			sql4+= "		   seq,";
			sql4+= "		   tutorgubun,";
			sql4+= "		   userid,";
			sql4+= "		   lessonnm,";
			sql4+= "		   eduhr,";
			sql4+= "		   price) ";
			sql4+= "values('" + v_subj + "',?,?,?,?,?,?) ";
			pstmt4 = connMgr.prepareStatement(sql4);
			
			//프로그램정보 저장[4/4]
			chkstring = "0";
			for(int i=0; i<v_lessoncnt; i++) {
				if (i>=9) chkstring = "";
				
				if (v_lessonnm[i].equals("")) 
					break;
					
				pstmt4.setString(1, chkstring + (i+1));
				pstmt4.setString(2, v_tutorgubun[i]);
				pstmt4.setString(3, v_tutorid[i]);
				pstmt4.setString(4, v_lessonnm[i]);
				pstmt4.setInt(5, v_eduhr[i]);
				pstmt4.setInt(6, v_eduprice[i]);
				isOk4 = pstmt4.executeUpdate();	
				
			}
			//프로그램 저장 끝
			if(isOk1*isOk2*isOk3*isOk4 > 0){
			  connMgr.commit();
			}else{
			  connMgr.rollback();
			}

        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql4);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }
    
    /**
    과정수정
    @param box      receive from the form object and session
    @return int
    */
     public int updateSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        PreparedStatement pstmt5 = null;
        
        String 	sql1         = "";
        String 	sql2         = "";
        String 	sql3         = "";
        String 	sql4         = "";
        String 	sql5         = "";
        
        int 	isOk1        = 0;
        int 	isOk2        = 0;
        int 	isOk3        = 0;
        int 	isOk4        = 0;
        int 	isOk5        = 0;
        
        int 	v_result     = 0;
        
        String  v_subj 		 = box.getString("p_subj");
        String 	v_userid     = box.getSession("userid");

        //Request
        String v_subjnm 	= box.getString("p_subjnm");			//과정명
        String v_edugubun 	= box.getString("p_edugubun");			// 교육유형
        
        String v_edustarta 	= box.getString("p_edustarta"); 		//교육시작일
        String v_eduenda 	= box.getString("p_eduenda"); 			//교육종료일
        String v_edutargeta = box.getString("p_edutargeta"); 		//교육대상
        
		//교육인원 시작	        
        int 	v_edumemcnt = box.getInt("p_edumemcnt"); 			//교육인원 총 갯수
        String	[] v_edumem	= new String [v_edumemcnt];				//교육인원
        String chkstring = "0";
        for(int i=0; i<v_edumemcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_edumem[i]		= box.getString("p_edumem" + chkstring + (i+1) + "a");
      	}
      	//교육인원 끝
        
        int 	v_edutmema 	 = box.getInt("p_edutmema"); 			//교육총인원
        int 	v_edutseqa 	 = box.getInt("p_edutseqa"); 			//교육총차수
        int 	v_seqpermema = box.getInt("p_seqpermema"); 			//차수당인원
        String 	v_eduplacea  = box.getString("p_eduplacea"); 		//교육장소

		//예산항목 시작
        int	   	v_budgetcnt = box.getInt("p_budgetcnt"); 			//예산총갯수
        String [] v_budget	= new String [v_budgetcnt];				//예산
        chkstring = "0";
        for(int i=0; i<v_budgetcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_budget[i]		= box.getString("p_budget" + chkstring + (i+1) + "a");
        	//System.out.println("v_budget[" + chkstring + (i+1) + "]a : "+v_budget[i]);
      	}
      	//예산항목 끝
        
        int    	v_gyamt 	= box.getInt("p_gyamt"); 				//고용보험환급예상금액

		//프로그램항목 시작
        int		v_lessoncnt 	= box.getInt("p_lessoncnt"); 		//프로그램 총 갯수
        String [] v_lessonnm	= new String [v_lessoncnt];	    	//프로그램명
        String [] v_tutorgubun	= new String [v_lessoncnt];	    	//강사구분(I:사내강사,O:사외강사,C:용역업체)
        String [] v_tutorid		= new String [v_lessoncnt];	    	//강사ID/사업자번호
        int	   [] v_eduhr		= new int [v_lessoncnt];	    	//교육시간
        int	   [] v_eduprice	= new int [v_lessoncnt];	    	//강사료
        
        for(int i=0; i<v_lessoncnt; i++) {
        	v_lessonnm[i]		= box.getStringDefault("p_lessonnm" + (i+1),"");
        	v_tutorgubun[i]		= box.getString("p_tutorgubun" + (i+1));
        	v_tutorid[i]		= box.getString("p_tutorid" + (i+1));
        	v_eduhr[i]			= box.getInt("p_eduhr" + (i+1));
        	v_eduprice[i]		= box.getInt("p_eduprice" + (i+1));
      	}
      	//프로그램항목 끝
      	
        try {

            connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			//String v_subj = getMaxSubjcode(connMgr);
			System.out.println("v_subj="+v_subj);
			
            //과정정보 저장[1/3]
            sql1 = "update 	tz_tsubj ";
			sql1+= "set		subjnm		= ?,";
			sql1+= "		edugubun	= ?,";
			sql1+= "		edustarta	= ?,";
			sql1+= "		eduenda		= ?,";
			sql1+= "		edutargeta	= ?,";
			sql1+= "		edutmema	= ?,";
			sql1+= "		edutseqa	= ?,";
			sql1+= "		seqpermema	= ?,";
			sql1+= "		eduplacea	= ?,";
			sql1+= "		gyamt		= ?,";
			sql1+= "		ldate		= to_char(sysdate, 'YYYYMMDDHHMMss'),";
			sql1+= "		luserid	 	= ? ";
			sql1+= "where	subj = '" + v_subj + "'";

            pstmt1 = connMgr.prepareStatement(sql1);
            System.out.println("sql1="+sql1);

			pstmt1.setString(1, v_subjnm);
			pstmt1.setString(2, v_edugubun);
			pstmt1.setString(3, v_edustarta);
			pstmt1.setString(4, v_eduenda);
			pstmt1.setString(5, v_edutargeta);
			pstmt1.setInt	(6, v_edutmema);
			pstmt1.setInt	(7, v_edutseqa);
			pstmt1.setInt	(8, v_seqpermema);
			pstmt1.setString(9, v_eduplacea);
			pstmt1.setInt	(10, v_gyamt);
			pstmt1.setString(11, v_userid);
		
			isOk1 = pstmt1.executeUpdate();              //TZ_TSUBJ 등록
			System.out.println("isok1="+isOk1);
            
            //인원정보 저장 시작
            //GUBUN = 'A' : 인원정보
            sql2 = "update	tz_tbudget ";
			sql2+= "set		plandata  = ?,";
			sql2+= "		execdata  = 0 ";
			sql2+= "where	subj 	  = '" + v_subj + "' and ";
			sql2+= "		gubun 	  = 'A' and ";
			sql2+= "		gubuncode = ? ";
			
			pstmt2 = connMgr.prepareStatement(sql2);
            
            //인원정보 저장[3/4]
            chkstring = "0";
        	for(int i=0; i<v_edumemcnt; i++) {
        		if (i>=9) chkstring = "";
	        	pstmt2.setString(1, v_edumem[i]);		//plandata
        		pstmt2.setString(2, chkstring + (i+1)); //gubuncode
				isOk2 = pstmt2.executeUpdate();	
	        }
	        //인원정보 저장 끝
	        
	        //예산정보 저장 시작
	        //GUBUN = 'B' : 예산정보
            sql3 = "update	tz_tbudget ";
			sql3+= "set		plandata  = ?,";
			sql3+= "		execdata  = 0  ";
			sql3+= "where	subj 	  = '" + v_subj + "' and ";
			sql3+= "		gubun 	  = 'B' and ";
			sql3+= "		gubuncode = ? ";
			
            pstmt3 = connMgr.prepareStatement(sql3);
            
            //예산정보 저장[3/4]
            chkstring = "0";
	        for(int i=0; i<v_budgetcnt; i++) {
	        	if (i>=9) chkstring = "";
	        	pstmt3.setString(1, v_budget[i]);
	        	pstmt3.setString(2, chkstring + (i+1));
				isOk3 = pstmt3.executeUpdate();	
	        }
	        //예산정보 저장 끝

            
            //if exists then update else insert--추가 필요
            
            
            //프로그램 저장 시작
            sql4 = "update 	tz_tlesson ";
			sql4+= "set		tutorgubun	= ?,";
			sql4+= "		userid		= ?,";
			sql4+= "		lessonnm	= ?,";
			sql4+= "		eduhr		= ?,";
			sql4+= "		price		= ? ";
			sql4+= "where	subj = '" + v_subj + "' and ";
			sql4+= "		seq = ? ";
			pstmt4 = connMgr.prepareStatement(sql4);
			
			//프로그램 저장 시작
            sql5 = "insert into tz_tlesson( ";
			sql5+= "		   subj,";
			sql5+= "		   seq,";
			sql5+= "		   tutorgubun,";
			sql5+= "		   userid,";
			sql5+= "		   lessonnm,";
			sql5+= "		   eduhr,";
			sql5+= "		   price) ";
			sql5+= "values('" + v_subj + "',?,?,?,?,?,?) ";
			pstmt5 = connMgr.prepareStatement(sql5);
			
			//프로그램정보 저장[4/4]
			chkstring = "0";
			for(int i=0; i<v_lessoncnt; i++) {
				if (i>=9) chkstring = "";
				
				if (v_lessonnm[i].equals("")) 
					break;
					
				pstmt4.setString(1, v_tutorgubun[i]);
				pstmt4.setString(2, v_tutorid[i]);
				pstmt4.setString(3, v_lessonnm[i]);
				pstmt4.setInt	(4, v_eduhr[i]);
				pstmt4.setInt	(5, v_eduprice[i]);
				pstmt4.setString(6, chkstring + (i+1));
				isOk4 = pstmt4.executeUpdate();	
				
				if (isOk4==0) {
					pstmt5.setString(1, chkstring + (i+1));
					pstmt5.setString(2, v_tutorgubun[i]);
					pstmt5.setString(3, v_tutorid[i]);
					pstmt5.setString(4, v_lessonnm[i]);
					pstmt5.setInt(5, v_eduhr[i]);
					pstmt5.setInt(6, v_eduprice[i]);
					isOk5 = pstmt5.executeUpdate();		
				}
				
			}
			//프로그램 저장 끝
			
			connMgr.commit();

        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql4);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }
    
    /**
    과정삭제
    @param box      receive from the form object and session
    @return int
    */
     public int deleteSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql1         = "";
        String sql2         = "";
        String sql3         = "";
        int isOk1           = 0;
        int isOk2           = 0;
        int isOk3           = 0;
        String v_subj       = box.getString("p_subj");
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);  

            //delete TZ_TLESSON table
            sql2    =  "delete from TZ_TLESSON where subj = '"+ v_subj +"'";
            isOk2   = connMgr.executeUpdate(sql2);

            //delete TZ_TBUDGET table
            sql3    =  "delete from TZ_TBUDGET where subj = '"+ v_subj +"'";
            isOk3   = connMgr.executeUpdate(sql3);
            
            //delete TZ_TSUBJ table
            sql1    =  "delete from TZ_TSUBJ where subj = '"+ v_subj +"'";
            isOk1   = connMgr.executeUpdate(sql1);
            
            connMgr.commit();  
        }
        catch (Exception ex) {
            connMgr.rollback();  
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }
    
    /**
    교육운영현황 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList selectSubjStatusList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        DataBox 	dbox  			= null;
//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";

        String 		v_searchtype    = box.getStringDefault("p_searchtype","S"); //S:시작일 기준, E:종료일기준
        String 		v_searchgubun   = box.getStringDefault("p_searchgubun","Y");//Y:년도단위검색, T:기간검색
        String 		v_edustartday   = box.getStringDefault("p_edustartday",""); //검색시작일
        String 		v_eduendday   	= box.getStringDefault("p_eduendday","");   //검색종료일
        
        String 		v_year         	= box.getStringDefault("s_year","");        //년도
        String 		v_edugubun      = box.getStringDefault("s_edugubun","ALL"); //교육유형
        String 		v_company   	= box.getStringDefault("s_company","ALL");  //회사
        String 		v_edudept   	= box.getStringDefault("s_edudept","ALL");  //교육주관팀
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");  //과정명
        String  	v_me		 	= box.getString("p_me"); 					//자기가 등록한 과정
        String  	v_isinput	 	= box.getStringDefault("p_isinput","ALL");	//실적입력여부
        
        String  	v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String  	v_orderType     = box.getString("p_orderType");           		//정렬할 순서
        
        int			v_pageno 		= box.getInt("p_pageno");
        
        String  s_userid        = box.getSession("userid");

        try {
        	//총괄관리자/강사관리자는 Default N
		    if (v_me.equals("")) {
		    	if (box.getSession("gadmin").substring(0,1).equals("A") || box.getSession("gadmin").substring(0,1).equals("R"))
		    		v_me = "N";
		    	else
		    		v_me = "Y";
			}
			
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            head_sql = "select	subj,";
			head_sql += "			subjnm,";
			head_sql += "			edugubun,";
			head_sql += "			edustarta,";
			head_sql += "			eduenda,";
			head_sql += "			(select codenm from tz_code where gubun='0024' and code=tz_tsubj.edugubun) edugubunnm,";
			head_sql += "			subjgubun,";
			//sql+= "			get_compnm(inusercomp,2,4) inusercompnm,";
			head_sql +="			get_compnm( inusercomp, 2,4  ) ||  \n";
			// 2005.11.16_하경태 : Oracle -> Mssql
			//sql+="			decode(substr(inusercomp,1,4),'0101','(HMC)','0102','(KMC)','') inusercompnm, \n";
			head_sql +="			case(SUBSTR(inusercomp,1,4) When '0101' Then '(HMC)' When '0102' Then '(KMC)' Else '' end as inusercompnm, \n";
			head_sql += "			(select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') totplanmemcnt,";
			head_sql += "			(select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') totexecmemcnt,";
			// 2005.11.16_하경태 : Oracle -> Mssql
			//sql+= "			decode ((select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A'),0,0,";
			//sql+= "			round(((select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') / ";
			//sql+= "			(select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') * 100),0) ) mempercent,";
			head_sql += "			case (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') When 0 Then 0 ";
			head_sql += "				Else round(((select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') / ";
			head_sql += "			(select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') * 100),0) end  mempercent,";
			head_sql += "			round((select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') / 1000,0) totplancost,";
			head_sql += "			round((select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') / 1000,0) totexeccost,";
			head_sql += "			(select NVL(avg(grade),0)  from tz_tlesson where subj = tz_tsubj.subj) totgrade,";
			head_sql += "			(select count(*) from tz_tlesson where subj = tz_tsubj.subj) lessoncnt, ";
			head_sql += "			inusercomp, ";
			head_sql += "			satisfaction, ";
			// 2005.11.16_하경태 : Oracle -> Mssql 
			//sql+= "			decode ( (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B'), 0 , 0 , ";
			//sql+= "					 (select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') /  ";
			//sql+= "					 (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') * 100) execpercent " ;
			head_sql += "			case  (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') When  0  Then  0 ";
			head_sql += "				Else ((select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') /  ";
			head_sql += "					 (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') * 100) end as execpercent " ;
			body_sql += "from 	tz_tsubj ";
			body_sql += "where  	1 = 1 ";
			
			//년도단위 검색으로 선택시
			if (v_searchgubun.equals("Y")) {
				//년도
				if (!v_year.equals("")) {
					body_sql += "		and substring(edustarta,1,4) = '" + v_year + "' ";
				}
			} else {
				//기간검색 검색으로 선택시
				
				if (v_searchtype.equals("S")) {
					//시작일 기준
					body_sql +="		and edustarta between '" + v_edustartday + "' and '" + v_eduendday + "' ";
				} else {
					//종료일 기준
					body_sql +="		and eduenda between '" + v_edustartday + "' and '" + v_eduendday + "' ";
				}
				
			}
			
			//교육유형
			if (!v_edugubun.equals("ALL")) {
				body_sql += "		and edugubun = '" + v_edugubun + "' ";				
			}
			
			//회사
			if (!v_company.equals("ALL")) {
				body_sql += "		and substring(inusercomp,1,4) = '" + StringManager.substring(v_company, 0, 4) + "' ";				
			}
			
			//교육주관팀
			if (!v_edudept.equals("ALL")) {
				body_sql += "		and inusercomp = '" + v_edudept + "' ";				
			}
			
			//과정명
			if (!v_srchsubjnm.equals("")) {
				body_sql += "		and subjnm like '%" + v_srchsubjnm + "%' ";				
			}
			
			//자기가 등록한 과정
			if (v_me.equals("Y")) {
				body_sql += "		and inuserid = '" + s_userid + "' ";				
			}
			
			//실적입력 여부
			if (v_isinput.equals("Y")) {
				body_sql += "		and savefile is not null ";				
			}
			
			//실적입력 여부
			if (v_isinput.equals("N")) {
				body_sql += "		and savefile is null ";				
			}
			
			if(v_orderColumn.equals("")) {
				order_sql += " order by subjnm ";
			} else {
				order_sql += " order by " + v_orderColumn + v_orderType;
			}
			
			//sql+= "order by subjnm ";
			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);
			
			System.out.println("sql->" + sql);
            
            ls.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);               //현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage();    //전체 페이지 수를 반환한다
            int totalrowcount = ls.getTotalCount();    //전체 row수를 반환한다

			double v_execpercent = 0; //집행율
			//double v_satisfaction = 0.0; //전체만족도
			int v_totplancost = 0;
			int v_totexeccost = 0;
			
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("subj"		   		,ls.getString("subj"));			//과정코드
                dbox.put("subjnm"	   		,ls.getString("subjnm"));		//과정명
                dbox.put("edugubun"	   		,ls.getString("edugubun"));		//교육유형코드
                dbox.put("d_edugubunnm"		,ls.getString("edugubunnm"));	//교육유형명칭
                dbox.put("edustarta"   		,ls.getString("edustarta"));	//교육시작일(계획)
                dbox.put("eduenda"	   		,ls.getString("eduenda"));		//교육종료일(실적)
                dbox.put("d_totplanmemcnt"	,new Integer(ls.getInt("totplanmemcnt")));//총 인원(계획)
                dbox.put("d_totexecmemcnt"	,new Integer(ls.getInt("totexecmemcnt")));//총 인원(실적)
                dbox.put("d_mempercent"	,new Integer(ls.getInt("mempercent")));//참석율
                v_totplancost = ls.getInt("totplancost");
                v_totexeccost = ls.getInt("totexeccost");
                dbox.put("d_totplancost"	,new Integer(v_totplancost));  //총 비용(계획)
                dbox.put("d_totexeccost"	,new Integer(v_totexeccost));  //총 비용(실적)
                dbox.put("d_totgrade"		,new Integer(ls.getInt("totgrade")));		//전체 만족도
                //집행율 계산 : 총 실적비용 / 총 계획비용 * 100
                if (v_totplancost==0.0) {
                	v_execpercent = 0.0;
                }
                else {
                	v_execpercent = (double)v_totexeccost / (double)v_totplancost * 100.0;
                }
                //System.out.println("비율="+v_execpercent);
                dbox.put("d_execpercent"	,new Double(v_execpercent));
                dbox.put("d_lessoncnt"		,new Integer(ls.getInt("lessoncnt")));	//프로그램 개수
                dbox.put("d_satisfaction"	,new Double(ls.getDouble("satisfaction")));

				dbox.put("inusercomp"	   	,ls.getString("inusercomp"));		
				
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
    과정집행 등록
    @param box      receive from the form object and session
    @return int
    */
    public int insertSubjStatus(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        
        String 	sql1         = "";
        String 	sql2         = "";
        String 	sql3         = "";
        String 	sql4         = "";
        
        int 	isOk1        = 0;
        int 	isOk2        = 0;
        int 	isOk3        = 0;
        int 	isOk4        = 0;
        
        int 	v_result     = 0;
        
        String  v_subj 		 = box.getString("p_subj");
        String 	v_userid     = box.getSession("userid");

        //Request
        
        String v_edustartb 	= box.getString("p_edustartb"); 		//교육시작일(실적)
        String v_eduendb 	= box.getString("p_eduendb"); 			//교육종료일(실적)
        String v_edutargetb = box.getString("p_edutargetb"); 		//교육대상(실적)
        String v_comments 	= box.getString("p_comments"); 			//담당자 의견
        double v_satisfaction = box.getDouble("p_satisfaction");	//전체 만족도
        
        //----------------------   업로드되는 파일 --------------------------------
        String realFileName = box.getRealFileName("p_file");  
        String newFileName  = box.getNewFileName("p_file"); 
        
        
        System.out.println("realFileName = "+realFileName);
        System.out.println("newFileName = "+newFileName);
        
        
        
		//교육인원 시작	        
        int 	v_edumemcnt = box.getInt("p_edumemcnt"); 			//교육인원 총 갯수
        String	[] v_edumem	= new String [v_edumemcnt];				//교육인원
        String chkstring = "0";
        for(int i=0; i<v_edumemcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_edumem[i]		= box.getString("p_edumem" + chkstring + (i+1) + "b");
      	}
      	//교육인원 끝
        
        int 	v_edutmemb 	= box.getInt("p_edutmemb"); 			//교육총인원(실적)
        int 	v_edutseqb 	= box.getInt("p_edutseqb"); 			//교육총차수(실적)
        int 	v_seqpermemb= box.getInt("p_seqpermemb"); 			//차수당인원(실적)
        String 	v_eduplaceb = box.getString("p_eduplaceb"); 		//교육장소(실적)

		//예산항목 시작
        int	   	v_budgetcnt = box.getInt("p_budgetcnt"); 			//예산총갯수
        String [] v_budget	= new String [v_budgetcnt];				//예산
        chkstring = "0";
        for(int i=0; i<v_budgetcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_budget[i]		= box.getString("p_budget" + chkstring + (i+1) + "b");
        	//System.out.println("v_budget[" + chkstring + (i+1) + "]a : "+v_budget[i]);
      	}
      	//예산항목 끝
        
		//프로그램항목 시작
        int		v_lessoncnt = box.getInt("p_lessoncnt"); 		//프로그램 총 갯수
        double  [] v_grade	= new double [v_lessoncnt];	    	//평점
        
        for(int i=0; i<v_lessoncnt; i++) {
        	v_grade[i]		= box.getDouble("p_grade" + (i+1));
      	}
      	//프로그램항목 끝
      	
        try {

            connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			//String v_subj = getMaxSubjcode(connMgr);
			System.out.println("v_subj="+v_subj);
			
            //과정정보 저장[1/3]
            sql1 = "update 	tz_tsubj ";
			sql1+= "set		edustartb	= ?,";	//교육시작일(실적)
			sql1+= "		eduendb		= ?,";  //교육종료일(실적)
			sql1+= "		edutmemb	= ?,";	//교육총인원(실적)
			sql1+= "		edutseqb	= ?,";	//교육총차수(실적)
			sql1+= "		seqpermemb	= ?,";	//차수당인원(실적)
			sql1+= "		comments	= ?,";	//담당자 의견
			sql1+= "		realfile	= ?,";	//첨부파일
			sql1+= "		savefile	= ?,";	//첨부파일
			sql1+= "		fdate		= to_char(sysdate, 'YYYYMMDDHHMMss'),";
			sql1+= "		fuserid	 	= ?, ";
			sql1+= "		satisfaction 	= ? ";
			sql1+= "where	subj = '" + v_subj + "'";

            pstmt1 = connMgr.prepareStatement(sql1);
            System.out.println("sql1="+sql1);

			pstmt1.setString(1, v_edustartb);
			pstmt1.setString(2, v_eduendb);
			pstmt1.setInt	(3, v_edutmemb);
			pstmt1.setInt	(4, v_edutseqb);
			pstmt1.setInt	(5, v_seqpermemb);
			pstmt1.setString(6, v_comments);
			pstmt1.setString(7, realFileName);
			pstmt1.setString(8, newFileName);
			pstmt1.setString(9, v_userid);
			pstmt1.setDouble(10, v_satisfaction);
		
			isOk1 = pstmt1.executeUpdate();              //TZ_TSUBJ 등록
			System.out.println("isok1="+isOk1);
            
            //인원정보 저장 시작
            //GUBUN = 'A' : 인원정보
            sql2 = "update	tz_tbudget ";
			sql2+= "set		execdata  = ? ";
			sql2+= "where	subj 	  = '" + v_subj + "' and ";
			sql2+= "		gubun 	  = 'A' and ";
			sql2+= "		gubuncode = ? ";
			
			pstmt2 = connMgr.prepareStatement(sql2);
            
            //인원정보 저장[3/4]
            chkstring = "0";
        	for(int i=0; i<v_edumemcnt; i++) {
        		if (i>=9) chkstring = "";
	        	pstmt2.setString(1, v_edumem[i]);		//plandata
        		pstmt2.setString(2, chkstring + (i+1)); //gubuncode
				isOk2 = pstmt2.executeUpdate();	
	        }
	        //인원정보 저장 끝
	        
	        //예산정보 저장 시작
	        //GUBUN = 'B' : 예산정보
            sql3 = "update	tz_tbudget ";
			sql3+= "set		execdata  = ?  ";
			sql3+= "where	subj 	  = '" + v_subj + "' and ";
			sql3+= "		gubun 	  = 'B' and ";
			sql3+= "		gubuncode = ? ";
			
            pstmt3 = connMgr.prepareStatement(sql3);
            
            //예산정보 저장[3/4]
            chkstring = "0";
	        for(int i=0; i<v_budgetcnt; i++) {
	        	if (i>=9) chkstring = "";
	        	pstmt3.setString(1, v_budget[i]);
	        	pstmt3.setString(2, chkstring + (i+1));
				isOk3 = pstmt3.executeUpdate();	
	        }
	        //예산정보 저장 끝
            
            //if exists then update else insert--추가 필요
            
            //프로그램 저장 시작
            sql4 = "update 	tz_tlesson ";
			sql4+= "set		grade		= ? ";
			sql4+= "where	subj = '" + v_subj + "' and ";
			sql4+= "		seq = ? ";
			
			pstmt4 = connMgr.prepareStatement(sql4);
			
			//프로그램정보 저장[4/4]
			chkstring = "0";
			for(int i=0; i<v_lessoncnt; i++) {
				if (i>=9) chkstring = "";
				System.out.println("v_grade[i]"+v_grade[i]);
				pstmt4.setDouble	(1, v_grade[i]);
				pstmt4.setString(2, chkstring + (i+1));
				isOk4 = pstmt4.executeUpdate();	
				
			}
			//프로그램 저장 끝
			
			connMgr.commit();

        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql4);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }
    
    
    /**
    예산집행현황 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList selectBudgetStatusList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ListSet 	ls1      		= null;
        ArrayList 	list  			= null;
        DataBox 	dbox  			= null;
//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";

        String 		v_searchtype    = box.getStringDefault("p_searchtype","S"); //S:시작일 기준, E:종료일기준
        String 		v_searchgubun   = box.getStringDefault("p_searchgubun","Y");//Y:년도단위검색, T:기간검색
        String 		v_edustartday   = box.getStringDefault("p_edustartday",""); //검색시작일
        String 		v_eduendday   	= box.getStringDefault("p_eduendday","");   //검색종료일
        
        String 		v_year         	= box.getStringDefault("s_year","");        //년도
        String 		v_edugubun      = box.getStringDefault("s_edugubun","ALL"); //교육유형
        String 		v_company   	= box.getStringDefault("s_company","ALL");  //회사
        String 		v_edudept   	= box.getStringDefault("s_edudept","ALL");  //교육주관팀
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");  //과정명
        
        String      v_budgetgubun   = box.getStringDefault("p_budgetgubun","B");//B:예산(0025),A:인원(0026)
        
        String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

		String  v_code = "0025"; //예산
        
        int			v_pageno 		= box.getInt("p_pageno");

        try {
        	
        	if (v_budgetgubun.equals("B")) {
        		v_code = "0025"; //tz_code
        	} else {
        		v_code = "0026"; //tz_code
        	}
        	
        	
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            head_sql = "select	subj,\n";
			head_sql+= "			subjnm,\n";
			head_sql+= "			edugubun,\n";
			head_sql+= "			edustarta,\n";
			head_sql+= "			eduenda,\n";
			head_sql+= "			(select codenm from tz_code where gubun='0024' and code=tz_tsubj.edugubun) edugubunnm,\n";
			head_sql+= "			subjgubun,\n";
			//sql+= "			get_compnm(inusercomp,2,4) inusercompnm,\n";
			head_sql+="			get_compnm( inusercomp, 2,4  ) ||  \n";
			// 2005.11.16_하경태 : Oracle -> Mssql
			//sql+="			decode(substr(inusercomp,1,4),'0101','(HMC)','0102','(KMC)','') inusercompnm, \n";
			head_sql+="			case (SUBSTR(inusercomp,1,4) When '0101' Then '(HMC)' When '0102' Then '(KMC)' Else '' end as inusercompnm, \n";
			
			head_sql+= "			(select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') totplanmemcnt,\n";
			head_sql+= "			(select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') totexecmemcnt,\n";
			
			head_sql+= "			round((select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B')/1000,0) totplancost,\n";
			head_sql+= "			round((select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B')/1000,0) totexeccost,\n";
			
			head_sql+= "			(select NVL(avg(grade),0)	 from tz_tlesson where subj = tz_tsubj.subj) totgrade,\n";
			// 2005.11.16_하경태 : Oracle -> Mssql
			//sql+= "			decode ( (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "'), 0 , 0 , \n";
			//sql+= "					 (select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "') /  \n";
			//sql+= "					 (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "') * 100) execpercent, \n" ;
			head_sql+= "			case  (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "') When  0  Then 0 \n";
			head_sql+= "				Else ((select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "') /  \n";
			head_sql+= "					 (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "') * 100) end as execpercent, \n" ;

			//예산항목별 서브 쿼리 구성
            ls1 = connMgr.executeQuery("select code from tz_code where gubun='" + v_code + "' order by code"); //0025

            while (ls1.next()){
            	
            	if (v_budgetgubun.equals("B")) {
					head_sql+= " round((NVL((select plandata from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "' and gubuncode='" + ls1.getString("code") + "'),0)/1000),0) plan" + ls1.getString("code") + ", \n";
					head_sql+= " round((NVL((select execdata from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "' and gubuncode='" + ls1.getString("code") + "'),0)/1000),0) exec" + ls1.getString("code") + ", \n";
            	} else { //인원별현황은 1000으로 나누지 않는다.
					head_sql+= " NVL((select plandata from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "' and gubuncode='" + ls1.getString("code") + "'),0) plan" + ls1.getString("code") + ", \n";
					head_sql+= " NVL((select execdata from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "' and gubuncode='" + ls1.getString("code") + "'),0) exec" + ls1.getString("code") + ", \n";
            	}
            }

			head_sql+= "			(select count(*)  from tz_tlesson where subj = tz_tsubj.subj) lessoncnt \n";
			body_sql+= " from 	tz_tsubj \n";
			body_sql+= " where  	1 = 1 \n";
			
			//년도단위 검색으로 선택시
			if (v_searchgubun.equals("Y")) {
				//년도
				if (!v_year.equals("")) {
					body_sql+= "		and substring(edustarta,1,4) = '" + v_year + "' \n";
				}
			} else {
				//기간검색 검색으로 선택시
				
				if (v_searchtype.equals("S")) {
					//시작일 기준
					body_sql+="		and edustarta between '" + v_edustartday + "' and '" + v_eduendday + "' \n";
				} else {
					//종료일 기준
					body_sql+="		and eduenda between '" + v_edustartday + "' and '" + v_eduendday + "' \n";
				}
				
			}
			
			//교육유형
			if (!v_edugubun.equals("ALL")) {
				body_sql+= "		and edugubun = '" + v_edugubun + "' \n";				
			}
			
			//회사
			if (!v_company.equals("ALL")) {
				body_sql+= "		and substring(inusercomp,1,4) = '" + StringManager.substring(v_company, 0, 4) + "' \n";				
			}
			
			//교육주관팀
			if (!v_edudept.equals("ALL")) {
				body_sql+= "		and inusercomp = '" + v_edudept + "' \n";				
			}
			
			//과정명
			if (!v_srchsubjnm.equals("")) {
				body_sql+= "		and subjnm like '%" + v_srchsubjnm + "%' \n";				
			}
			
			if(v_orderColumn.equals("")) {
				order_sql+= " order by subjnm \n";
			} else {
				order_sql+= " order by " + v_orderColumn + v_orderType;
			}
			
			//sql+= "order by subjnm ";
			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);
			
			System.out.println("selectBudgetStatusList ->" + sql);
            
            ls.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); 	//현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage();    		//전체 페이지 수를 반환한다
            int totalrowcount = ls.getTotalCount();    		//전체 row수를 반환한다

			double v_execpercent = 0; //집행율
			int v_totplancost = 0;
			int v_totexeccost = 0; 
			
			double v_mempercent = 0; //참석율
			int v_totplanmemcnt = 0;
			int v_totexecmemcnt = 0; 
			
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("subj"		   		,ls.getString("subj"));			//과정코드
                dbox.put("subjnm"	   		,ls.getString("subjnm"));		//과정명
                dbox.put("edugubun"	   		,ls.getString("edugubun"));		//교육유형코드
                dbox.put("d_edugubunnm"		,ls.getString("edugubunnm"));	//교육유형명칭
                dbox.put("edustarta"   		,ls.getString("edustarta"));	//교육시작일(계획)
                dbox.put("eduenda"	   		,ls.getString("eduenda"));		//교육종료일(실적)
                //dbox.put("d_totplanmemcnt"	,new Integer(ls.getInt("totplanmemcnt")));//총 인원(계획)
                //dbox.put("d_totexecmemcnt"	,new Integer(ls.getInt("totexecmemcnt")));//총 인원(실적)
                v_totplancost = ls.getInt("totplancost");
                v_totexeccost = ls.getInt("totexeccost");
                dbox.put("d_totplancost"	,new Integer(v_totplancost));  //총 비용(계획)
                dbox.put("d_totexeccost"	,new Integer(v_totexeccost));  //총 비용(실적)
                dbox.put("d_totgrade"		,new Integer(ls.getInt("totgrade")));		//전체 만족도
                
                v_totplanmemcnt = ls.getInt("totplanmemcnt");
                v_totexecmemcnt = ls.getInt("totexecmemcnt");
                dbox.put("d_totplanmemcnt"	,new Integer(v_totplanmemcnt));  //총 비용(계획)
                dbox.put("d_totexecmemcnt"	,new Integer(v_totexecmemcnt));  //총 비용(실적)
                
                //System.out.println("v_totplancost =" + v_totplancost);
                //System.out.println("v_totexeccost =" + v_totexeccost);
                //System.out.println("v_totplanmemcnt =" + v_totplanmemcnt);
                //System.out.println("v_totexecmemcnt=" + v_totexecmemcnt);                
                
                //예산항목별 계획/실적 비용
                //dbox.put("d_")
                
                //집행율 계산 : 총 실적비용 / 총 계획비용 * 100
                if (v_totplancost==0) {
                	v_execpercent = 0;
                }
                else {
                	v_execpercent = (double)v_totexeccost / (double)v_totplancost * 100;
                }
                dbox.put("d_execpercent"	,new Double(v_execpercent));                
                
                //참석율 계산 : 총 실적인원 / 총 계획인원 * 100
                if (v_totplanmemcnt==0) {
                	v_mempercent = 0;
                }
                else {
                	v_mempercent = (double)v_totexecmemcnt / (double)v_totplanmemcnt * 100;
                }
                dbox.put("d_mempercent"	,new Double(v_mempercent));
                dbox.put("d_lessoncnt"	,new Integer(ls.getInt("lessoncnt")));	//프로그램 개수

                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        	if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }
    
    /**
    과정입력현황 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList selectSubjInputList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        DataBox 	dbox  			= null;
//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";

        String 		v_searchtype    = box.getStringDefault("p_searchtype","S"); //S:시작일 기준, E:종료일기준
        String 		v_searchgubun   = box.getStringDefault("p_searchgubun","Y");//Y:년도단위검색, T:기간검색
        String 		v_edustartday   = box.getStringDefault("p_edustartday",""); //검색시작일
        String 		v_eduendday   	= box.getStringDefault("p_eduendday","");   //검색종료일
        
        String 		v_year         	= box.getStringDefault("s_year","");        //년도
        String 		v_edugubun      = box.getStringDefault("s_edugubun","ALL"); //교육유형
        String 		v_company   	= box.getStringDefault("s_company","ALL");  //회사
        String 		v_edudept   	= box.getStringDefault("s_edudept","ALL");  //교육주관팀
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");  //과정명
        
        String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서
		
        int		v_pageno 		= box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            head_sql = "select 	distinct a.inusercomp,";
			//sql+= "	   		get_compnm(a.inusercomp,2,4) inusercompnm,";
			head_sql+="			get_compnm( inusercomp, 2,4  ) ||  \n";
			// 2005.11.16_하경태 : Oracle -> Mssql
			//sql+="			decode(substr(inusercomp,1,4),'0101','(HMC)','0102','(KMC)','') inusercompnm, \n";
			head_sql+="			case substring(inusercomp,1,4) When '0101' Then '(HMC)' When '0102' Then '(KMC)' Else '' end as inusercompnm, \n";
			head_sql+= "	   		(select count(*) from tz_tsubj where inusercomp=a.inusercomp) totsubjcnt,";
			head_sql+= "	   		(select count(*) from tz_tsubj where inusercomp=a.inusercomp and fdate is not null) totsubjinputcnt,";
			head_sql+= "	   		(select count(*) from tz_tsubj where inusercomp=a.inusercomp and realfile is not null) totsubjfilecnt,";
			
			head_sql+= "	   		(select NVL(sum(plandata),0) from tz_tbudget where gubun='A' and inusercomp=a.inusercomp) totplanmemcnt,";
			head_sql+= "	   		(select NVL(sum(execdata),0) from tz_tbudget where gubun='A' and inusercomp=a.inusercomp) totexecmemcnt, ";
			
			head_sql+= "	   		round(((select NVL(sum(plandata),0) from tz_tbudget where gubun='B' and inusercomp=a.inusercomp)/1000),0) totplancost,";
			head_sql+= "	   		round(((select NVL(sum(execdata),0) from tz_tbudget where gubun='B' and inusercomp=a.inusercomp)/1000),0) totexeccost ";
			body_sql+= " from 	tz_tsubj a ";
			body_sql+= " where	1 =1 ";
			
			//년도단위 검색으로 선택시
			if (v_searchgubun.equals("Y")) {
				//년도
				if (!v_year.equals("")) {
					body_sql+= "		and substring(edustarta,1,4) = '" + v_year + "' ";
				}
			} else {
				//기간검색 검색으로 선택시
				
				if (v_searchtype.equals("S")) {
					//시작일 기준
					body_sql+="		and edustarta between '" + v_edustartday + "' and '" + v_eduendday + "' ";
				} else {
					//종료일 기준
					body_sql+="		and eduenda between '" + v_edustartday + "' and '" + v_eduendday + "' ";
				}
			}
			
			//교육유형
			if (!v_edugubun.equals("ALL")) {
				body_sql+= "		and edugubun = '" + v_edugubun + "' ";				
			}
			
			//회사
			if (!v_company.equals("ALL")) {
				body_sql+= "		and substring(inusercomp, 1, 4) = '" + StringManager.substring(v_company, 0, 4) + "' ";				
			}
			
			//교육주관팀
			if (!v_edudept.equals("ALL")) {
				body_sql+= "		and inusercomp = '" + v_edudept + "' ";				
			}
			
			//과정명
			if (!v_srchsubjnm.equals("")) {
				body_sql+= "		and subjnm like '%" + v_srchsubjnm + "%' ";				
			}
			
			//sql+= "order by 2 ";
			
			if(v_orderColumn.equals("")) {
				order_sql+= " order by inusercompnm ";
			} else {
				order_sql+= " order by " + v_orderColumn + v_orderType;
			}

			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage();    //전체 페이지 수를 반환한다
            int totalrowcount = ls.getTotalCount();    //전체 row수를 반환한다

			double v_execpercent = 0; //집행율
			double v_mempercent = 0; //집행율
			int v_totplancost = 0;
			int v_totexeccost = 0;
			
			int v_totplanmemcnt = 0;
			int v_totexecmemcnt = 0;
			
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("inusercomp"			,ls.getString("inusercomp"));   	//부서코드
                dbox.put("d_inusercompnm"		,ls.getString("inusercompnm"));   	//부서명
                dbox.put("d_totsubjcnt"			,ls.getString("totsubjcnt"));   	//과정등록수(계획 과정수)
                dbox.put("d_totsubjinputcnt"	,ls.getString("totsubjinputcnt"));  //실적입력 과정수
                dbox.put("d_totsubjfilecnt"		,ls.getString("totsubjfilecnt"));  	//결과보고서 과정수
                
                v_totplancost = ls.getInt("totplancost");
                v_totexeccost = ls.getInt("totexeccost");
                dbox.put("d_totplancost"	,new Integer(v_totplancost));  //총 비용(계획)
                dbox.put("d_totexeccost"	,new Integer(v_totexeccost));  //총 비용(실적)
                //집행율 계산 : 총 실적비용 / 총 계획비용 * 100
                if (v_totexeccost==0) {
                	v_execpercent = 0;
                }
                else {
                	v_execpercent = (double)v_totexeccost / (double)v_totplancost * 100;
                }
                dbox.put("d_execpercent"	,new Double(v_execpercent));
                
                //참석율
                v_totplanmemcnt = ls.getInt("totplanmemcnt");
                v_totexecmemcnt = ls.getInt("totexecmemcnt");
                dbox.put("d_totplanmemcnt"	,new Integer(v_totplanmemcnt));  //계획 인원
                dbox.put("d_totexecmemcnt"	,new Integer(v_totexecmemcnt));  //실적 인원
                //집행율 계산 : 총 실적비용 / 총 계획비용 * 100
                if (v_totexecmemcnt==0) {
                	v_mempercent = 0;
                }
                else {
                	v_mempercent = (double)v_totexecmemcnt / (double)v_totplanmemcnt * 100;
                }
                dbox.put("d_mempercent"	,new Double(v_mempercent));

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
    과정입력현황 상세 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList selectSubjInputStatusList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        DataBox 	dbox  			= null;
//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";

        String 		v_searchtype    = box.getStringDefault("p_searchtype","S"); //S:시작일 기준, E:종료일기준
        String 		v_searchgubun   = box.getStringDefault("p_searchgubun","Y");//Y:년도단위검색, T:기간검색
        String 		v_edustartday   = box.getStringDefault("p_edustartday",""); //검색시작일
        String 		v_eduendday   	= box.getStringDefault("p_eduendday","");   //검색종료일
        
        String 		v_year         	= box.getStringDefault("s_year","");        //년도
        String 		v_edugubun      = box.getStringDefault("s_edugubun","ALL"); //교육유형
        String 		v_company   	= box.getStringDefault("s_company","ALL");  //회사
        String 		v_edudept   	= box.getStringDefault("s_edudept","ALL");  //교육주관팀
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");  //과정명
        
        String		v_inusercomp	= box.getString("p_inusercomp");			//입력자 부서코드
        
        int			v_pageno 		= box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            head_sql = "select	subj,";
			head_sql+= "			subjnm,";
			head_sql+= "			edugubun,";
			head_sql+= "			edustarta,";
			head_sql+= "			eduenda,";
			head_sql+= "			(select codenm from tz_code where gubun='0024' and code=tz_tsubj.edugubun) edububunnm,";
			head_sql+= "			subjgubun,";
			head_sql+= "			get_compnm(inusercomp,2,4) inusercompnm,";
			head_sql+= "			inusercomp,";
			head_sql+= "			realfile,";
			head_sql+= "			savefile,";
			// 2005.11.16_하경태 : Oracle -> Mssql
			//sql+= "			decode(fdate,null,'미완료','','미완료','완료') fdatedesc,";
			//sql+= "			decode(realfile,null,'미완료','','미완료','완료') realfiledesc,";
			head_sql+= "			case fdate When null Then '미완료' When '' Then '미완료' Else '완료' end as fdatedesc,";
			head_sql+= "			case realfile When null Then '미완료' When '' Then '미완료' Else '완료' end as realfiledesc,";
			head_sql+= "			inuserid,";
			head_sql+= "			(select email from tz_member where userid=tz_tsubj.inuserid) email, ";
			head_sql+= "			(select name from tz_member where userid=tz_tsubj.inuserid) inusernm ";
			body_sql+= " from 	tz_tsubj ";
			body_sql+= " where	inusercomp = '" + v_inusercomp + "' ";
			order_sql+= " order by subjnm ";
			
			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);               //현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage();    //전체 페이지 수를 반환한다
            //int totalrowcount = ls.getTotalCount();    //전체 row수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("subj"		   		,ls.getString("subj"));			//과정코드
                dbox.put("subjnm"	   		,ls.getString("subjnm"));		//과정명
                dbox.put("edugubun"	   		,ls.getString("edugubun"));		//교육유형코드
                dbox.put("d_edugubunnm"		,ls.getString("edububunnm"));	//교육유형명칭
                dbox.put("edustarta"   		,ls.getString("edustarta"));	//교육시작일(계획)
                dbox.put("eduenda"	   		,ls.getString("eduenda"));		//교육종료일(실적)
                dbox.put("subjgubun"	 	,ls.getString("subjgubun"));	//과정구분(A:LMS입력과정,B:강사/예산관리시스템 입력 과정)
                dbox.put("inusercomp"		,ls.getString("inusercomp"));   //부서코드
                dbox.put("d_inusercompnm"	,ls.getString("inusercompnm")); //부서명
                dbox.put("d_fdatedesc"		,ls.getString("fdatedesc")); 	//실적 입력여부
                dbox.put("d_realfiledesc"	,ls.getString("realfiledesc")); //결과보고서 입력여부
                dbox.put("d_realfile"		,ls.getString("realfile"));
                dbox.put("d_savefile"		,ls.getString("savefile"));
                dbox.put("d_email"			,ls.getString("email")); 		//담당자 EMail
                dbox.put("inuserid"			,ls.getString("inuserid")); 	//담당자ID
                dbox.put("d_inusernm"		,ls.getString("inusernm")); 	//담당자이름
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
    강사운영현황 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList selecttutorStatusList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        DataBox 	dbox  			= null;
//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
		String head_sql0  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = ""; 
		String head_sql1   = "";
		String body_sql1   = "";
		String body_sql2   = "";
        String group_sql1  = "";

        String 		v_tutorgubun    = box.getStringDefault("s_tutorgubun","ALL"); //강사구분(I:사내강사 O:사외강사 G:그룹사강사)
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");  //교과목
        String 		v_tutorname   	= box.getStringDefault("p_tutorname","");  //강사명
        String 		v_subjclass   	= box.getStringDefault("p_subjclass","");  //강사명
        
        String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

        
        int			v_pageno 		= box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

			head_sql0 ="select * from  ( \n";
			head_sql+="		select a.tutorgubun, \n";
			head_sql+="			   get_codenm('0027',a.tutorgubun) tutorgubunnm, \n";
			head_sql+="			   a.userid, \n";
			head_sql+="			   (select name from tz_tutor where userid = a.userid) name, \n";
			head_sql+="			   (select subjclass from tz_tutor where userid=a.userid) subjclass, \n";
			head_sql+="			   (select codenm from tz_code where gubun = '0039' and code = (select subjgubun from tz_tsubj where subj=a.subj)) subjclassnm, \n";
			head_sql+="			   a.eduhr, \n";
			head_sql+="			   a.price, \n";
			head_sql+="			   a.grade, \n";
			head_sql+="			   a.subj, \n";
			head_sql+="			   (select subjnm from tz_tsubj where subj=a.subj) subjnm, \n";
			head_sql+="			   a.lessonnm, \n";
			head_sql+="			   get_compnm( (select inusercomp from tz_tsubj where subj=a.subj), 4,4 ) inusercompnm, \n";
			head_sql+="			   (select phone from tz_tutor where userid = a.userid) phone, \n";
			head_sql+="			   (select handphone from tz_tutor where userid = a.userid) handphone, \n";
			// 2005.11.16_하경태 : Oracle -> mssql
			//head_sql+="			   decode(tutorgubun,'O',(select comp from tz_tutor where userid = a.userid),'I',(select replace(get_compnm(comp,4,4),'/','') from tz_member where userid=a.userid),'') compnm, \n";
			head_sql+="			   case tutorgubun When 'O' Then (select comp from tz_tutor where userid = a.userid) When 'I' Then (select replace(get_compnm(comp,4,4),'/','') from tz_member where userid=a.userid) Else '' end as compnm, \n";
			head_sql+="			   NVL((select edustartb from tz_tsubj where subj=a.subj),(select edustarta from tz_tsubj where subj=a.subj)) edustart, \n";
			head_sql+="			   NVL((select eduendb from tz_tsubj where subj=a.subj),(select eduenda from tz_tsubj where subj=a.subj)) eduend, \n";
			head_sql+="			   (select inuserid from tz_tsubj where subj=a.subj) inuserid,\n";
			head_sql+="			   (select name from tz_member where userid=(select inuserid from tz_subj where subj=a.subj)) inusername,\n";
			head_sql+="			   (select get_jikwinm(jikwi,comp) from tz_member where userid=(select inuserid from tz_subj where subj=a.subj)) inuserjikwinm,\n";
			head_sql+="			   (select comptel from tz_member where userid=(select inuserid from tz_subj where subj=a.subj)) inusercomptel,\n";
			head_sql+="			   (select handphone from tz_member where userid=(select inuserid from tz_subj where subj=a.subj)) inuserhandphone, \n";
			head_sql+="			   (select indate from tz_tsubj where subj=a.subj) indate \n";
			
			body_sql+="		from tz_tlesson a	    \n";
			body_sql+="		union all  \n";
			head_sql1+="		select a.tutorgubun,  \n";
			head_sql1+="			   get_codenm('0027',a.tutorgubun) tutorgubunnm,  \n";
			head_sql1+="			   a.userid, \n";
			head_sql1+="			   (select name from tz_tutor where userid = a.userid) name, \n";
			head_sql1+="			   a.subjclass, \n";
			head_sql1+="			   a.subjclassnm, \n";
			head_sql1+="			   a.eduhr, \n";
			head_sql1+="			   a.price, \n";
			head_sql1+="			   a.grade, \n";
			head_sql1+="			   '' subj, \n";
			head_sql1+="			   a.subjnm, \n";
			head_sql1+="			   a.lessonnm, \n";
			head_sql1+="			   get_compnm( (select comp from tz_member where userid=a.inuserid), 4,4  ) inusercompnm, \n";
			head_sql1+="			   (select phone from tz_tutor where userid = a.userid) phone, \n";
			head_sql1+="			   (select handphone from tz_tutor where userid = a.userid) handphone, \n";
			// 2005.11.16_하경태 : Oracle -> Mssql
			//head_sql1+="			   decode(tutorgubun, 'O',(select comp from tz_tutor where userid = a.userid),'I',(select replace(get_compnm(comp,4,4),'/','') from tz_member where userid=a.userid),'') compnm, \n";
			head_sql1+="			   case tutorgubun When 'O' Then (select comp from tz_tutor where userid = a.userid) When 'I' Then (select replace(get_compnm(comp,4,4),'/','') from tz_member where userid=a.userid) Else '' end as compnm, \n";
			head_sql1+="			   a.edustart, \n";
			head_sql1+="			   a.eduend, \n";
			
			head_sql1+="			   a.inuserid,\n";
			head_sql1+="			   (select name from tz_member where userid=a.inuserid) inusername,\n";
			head_sql1+="			   (select get_jikwinm(jikwi,comp) from tz_member where userid=a.inuserid) inuserjikwinm,\n";
			head_sql1+="			   (select comptel from tz_member where userid=a.inuserid) inusercomptel,\n";
			head_sql1+="			   (select handphone from tz_member where userid=a.inuserid) inuserhandphone, \n";
			head_sql1+="			   '' indate \n";
			
			body_sql1+="		from   tz_tlessonhst a \n";
			body_sql2+=") where 1=1 \n";

			//강사구분
			if (!v_tutorgubun.equals("ALL")) {
				body_sql2+= "		and tutorgubun = '" + v_tutorgubun + "' ";				
			}
			
			//강사명
			if (!v_tutorname.equals("")) {
				body_sql2+= "		and name like '%" + v_tutorname + "%' ";				
			}
			
			//강의유형
			if (!v_subjclass.equals("")) {
				body_sql2+= "		and subjclass = '" + v_subjclass + "' ";
			}
			
			//교과목명
			if (!v_srchsubjnm.equals("")) {
				body_sql2+= "		and lessonnm like '%" + v_srchsubjnm + "%' ";				
			}
			
			if(v_orderColumn.equals("")) { 
				order_sql+= " order by edustart desc "; 
			} else { 
				order_sql+= " order by " + v_orderColumn + v_orderType; 
			}
			
			//sql+="order by edustart desc ";

			//System.out.println("sql->" + sql);
            
			sql= head_sql0 + head_sql+ body_sql+head_sql1+ body_sql1 + body_sql2 + order_sql;
			
			System.out.println("sql->" + sql);
			
			ls = connMgr.executeQuery(sql);				
			count_sql= "select count(*) From ("+ head_sql + body_sql + head_sql + body_sql1 + body_sql2;
			
			System.out.println("sql->" + count_sql);
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);               //현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage();    //전체 페이지 수를 반환한다
            //int totalrowcount = ls.getTotalCount();    //전체 row수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_tutorgubun"		,ls.getString("tutorgubun"));
                dbox.put("d_tutorgubunnm"	,ls.getString("tutorgubunnm"));		
                dbox.put("d_userid"	   		,ls.getString("userid"));		
                dbox.put("d_name"			,ls.getString("name"));	
                dbox.put("d_subjclassnm"   ,ls.getString("subjclassnm"));	
                dbox.put("d_inusercompnm"	,ls.getString("inusercompnm"));		
                dbox.put("d_compnm"	,ls.getString("compnm"));		
                
                dbox.put("d_eduhr"			, new Integer(ls.getInt("eduhr")));
                dbox.put("d_price"			, new Integer(ls.getInt("price")));
                dbox.put("d_grade"			, new Double(ls.getDouble("grade")));
                dbox.put("d_subj"			,ls.getString("subj"));
                dbox.put("d_subjnm"			,ls.getString("subjnm"));
                dbox.put("d_lessonnm"		,ls.getString("lessonnm"));
                dbox.put("d_phone"			,ls.getString("phone"));
                dbox.put("d_handphone"		,ls.getString("handphone"));
                dbox.put("d_edustart"		,ls.getString("edustart"));
                dbox.put("d_eduend"			,ls.getString("eduend"));
                dbox.put("d_inuserid"		,ls.getString("inuserid"));
                dbox.put("d_inusername"		,ls.getString("inusername"));
                dbox.put("d_inuserjikwiname",ls.getString("inuserjikwinm"));
                dbox.put("d_inusercomptel"	,ls.getString("inusercomptel"));
                dbox.put("d_inuserhandphone",ls.getString("inuserhandphone"));
                dbox.put("d_indate"			,ls.getString("indate"));
                
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
    사내강사/사외강사/교육기관 검색
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList searchTutor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        String 		sql      		= "";
        DataBox 	dbox  			= null;

        String 		v_tutorgubun    = box.getStringDefault("p_tutorgubun","I");   //검색항목(I-사내강사,O-사외강사,C-교육기관)
        String 		v_searchtext    = box.getStringDefault("p_searchtext","");   //검색어
        
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            
            if (v_tutorgubun.equals("I") || v_tutorgubun.equals("O")) {
            	//사내/사외강사 검색
            	sql = "select 	userid, name,";
            	sql+="			(select codenm from tz_code where gubun = '0039' and code = tz_tutor.subjclass) subjclassnm, ";
				sql+= "	   		get_compnm( (select comp from tz_member where userid = tz_tutor.userid),2 , 4) compnm ";
				sql+= "from   	tz_tutor ";
				sql+= "where  	tutorgubun = '" + v_tutorgubun + "' ";
				
				if (!v_searchtext.equals("")) {
					sql+= "	and name like '%" + v_searchtext + "%'";
				}
				sql+= "order by name ";
				
            } else {
            	//교육기관 검색
            	sql = "select 	busino userid, compnm name,";
            	sql+="			(select codenm from tz_code where gubun = '0039' and code = tz_tcomp.subjclass) subjclassnm, ";
				sql+= "	   		get_compnm(comp,2 , 4) compnm ";
				sql+= "from   	tz_tcomp ";
				sql+= "where	1 = 1 ";
				
				if (!v_searchtext.equals("")) {
					sql+= "	and compnm like '%" + v_searchtext + "%'";
				}
				sql+= "order by compnm ";
            }          
            
            //System.out.println("sql->" + sql);
                     
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("userid",ls.getString("userid"));
                dbox.put("name",ls.getString("name"));
                dbox.put("subjclassnm",ls.getString("subjclassnm"));
                dbox.put("compnm",ls.getString("compnm"));
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
    과정코드 검색
    @param  connMgr      receive from the form object and session
    @return String
    */
    public String getMaxSubjcode(DBConnectionManager connMgr) throws Exception {
        String v_subjcode = "";
        String v_maxsubj = "";
        int    v_maxno   = 0;

        ListSet ls = null;
        String sql  = "";
        try {
            sql = " select max(subj) maxsubj";
            sql+= "   from tz_tsubj ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                 v_maxsubj = ls.getString("maxsubj");
            }

            if (v_maxsubj.equals("")) {
                v_subjcode = "S00001";
            } else {
                v_maxno = Integer.valueOf(v_maxsubj.substring(1)).intValue();
                v_subjcode = "S" + new DecimalFormat("00000").format(v_maxno+1);
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
    검색 년도 Select Box 구성
    @param  box, isChange, isALL     receive from the form object and session
    @return String
    */
    public static String getYear(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        String sql = "";
        String result = "년도 ";
        
        try {
            //String s_gadmin = box.getSession("gadmin");
            String v_year = box.getString("s_year");
            //String v_gadmin = StringManager.substring(s_gadmin, 0, 1);

            connMgr = new DBConnectionManager();

            sql = "select distinct substring(edustarta,1,4) ";
			sql+= "from tz_tsubj ";
			sql+= "order by 1 desc";

            ls = connMgr.executeQuery(sql);     
            
            result += getSelectTag(ls, isChange, isALL, "s_year", v_year);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }    
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }
    
     /**
    검색 부서 Select Box 구성
    @param  box, isChange, isALL     receive from the form object and session
    @return String
    */
    public static String getEduDept(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        String sql = "";
        String result = "교육주관팀 ";
        
        try {
            String v_year 	 = box.getStringDefault("s_year","");
            String v_company = box.getStringDefault("s_company","ALL");
            String v_edudept = box.getStringDefault("s_edudept","ALL");
            
            //String v_gadmin = StringManager.substring(s_gadmin, 0, 1);

            connMgr = new DBConnectionManager();

            sql = "select 	distinct inusercomp,";
			sql+= "		 	replace(get_compnm(inusercomp, 4, 4),'/','') ";
			//2005.11.16_하경태 : Oracle -> Mssql
			//sql+= "			|| decode(substr(inusercomp,1,4),'0101','(HMC)','0102','(KMC)','' ) deptnm ";
			sql+= "			+ case substring(inusercomp,1,4) When '0101' Then '(HMC)' When '0102' Then '(KMC)' Else '' End as deptnm ";
			sql+= "from	 	tz_tsubj ";
			
			if (v_year.equals("")) {
            	//년도가 없으면 가장 최근의 년도를 가져옴(최초 화면 진입시)
				sql+= "where	 substring(edustarta,1,4) = (select max(SUBSTR(edustarta,1,4)) from tz_tsubj)  ";
			} else {
				sql+= "where	substring(edustarta,1,4) = '" + v_year + "'  ";
			}
			
			//회사
			if (!v_company.equals("ALL")) {
				sql+= " 		and substring(inusercomp,1,4) = '" + StringManager.substring(v_company, 0, 4) + "' ";	
			}
			
			sql+= "order	by 1";

            ls = connMgr.executeQuery(sql);     
            
            result += getSelectTag(ls, isChange, isALL, "s_edudept", v_edudept);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }    
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }
    
    public static String getSelectTag(ListSet ls, boolean isChange, boolean isALL, String selname, String optionselected) throws Exception {
        StringBuffer sb = null;
        
        try {
            sb = new StringBuffer();  

            sb.append("<select name = \"" + selname + "\"");
            if(isChange) sb.append(" onChange = \"javascript:whenSelection('change')\"");  
            sb.append(">\r\n");
            if(isALL) {
                sb.append("<option value = \"ALL\">ALL</option>\r\n");  
            }
            else if(isChange) {
                if(selname.indexOf("year") == -1) 
                    sb.append("<option value = \"----\">----</option>\r\n");  
            }

            while (ls.next()) {    
                ResultSetMetaData meta = ls.getMetaData();
                int columnCount = meta.getColumnCount();
                
                sb.append("<option value = \"" + ls.getString(1) + "\"");

                if (optionselected.equals(ls.getString(1))) sb.append(" selected");
               
                sb.append(">" + ls.getString(columnCount) + "</option>\r\n");
            }
            sb.append("</select>\r\n");
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return sb.toString();
    }
	
    
    ///////////////////////////////////////////////////////////

    /**
    입력한 강사의 중복여부 조회
    @param v_userid      강사 아이디
    @return v_result    1:중복됨 ,0:중복되지 않음
    */
    public int overlapping(String v_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls      = null;
        String sql      = "";
        int v_result    = 0;
        try{
            connMgr = new DBConnectionManager();
            sql  = "select name from TZ_TUTOR where userid ='"+v_userid+"'";
            ls = connMgr.executeQuery(sql);
            //중복된 경우 1을 return한다
            if(ls.next()){  v_result = 1;   }
        }
        catch(Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null)      { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_result;
    }

    /**
    강사에 해당하는 강의과정등록
    @param box      receive from the form object and session
    @return int
    */
     public int insertTutorSubj(DBConnectionManager connMgr,RequestBox box) throws Exception {
        String sql                  = "";
        String v_user_id            = box.getSession("userid");
        String v_userid              = box.getString("p_userid");
        //p_subj로 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_subj               = new Vector();
        v_subj                      = box.getVector("p_subj");
        Enumeration em              = v_subj.elements();
        String v_eachSubj           = "";   //실제 넘어온 각각의 과정코드
        int isOk                    = 0;
        try{
            while(em.hasMoreElements()){
                v_eachSubj   = (String)em.nextElement();
                //insert TZ_SUBJMAN table
                sql =  "insert into TZ_SUBJMAN(userid,gadmin,subj,luserid,ldate) ";
                sql+=  "values('"+v_userid+"','P1','"+v_eachSubj+"','"+v_user_id+"', to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                isOk = connMgr.executeUpdate(sql);
            }
        }
        catch(Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        return isOk;
    }

    /**
    운영자권한 테이블에 등록
    @param box      receive from the form object and session
    @return int
    */
     public int insertManager(DBConnectionManager connMgr,RequestBox box) throws Exception {
        String sql                  = "";
        String v_user_id            = box.getSession("userid");
        String v_userid             = box.getString("p_userid");
        String v_fmon               = box.getString("p_fmon");
        String v_tmon               = box.getString("p_tmon");
        String v_compcd             = box.getString("p_compcd");
        int    isOk                 = 0;
        try{
            //insert TZ_MANAGER table
            sql =  "insert into TZ_MANAGER(userid,gadmin,comp,isdeleted,fmon,tmon,luserid,ldate) ";
            sql+=  "values('"+v_userid+"','P1','"+v_compcd+"','N','"+v_fmon+"','"+v_tmon+"','"+v_user_id+"', to_char(sysdate, 'YYYYMMDDHH24MISS'))";
            isOk = connMgr.executeUpdate(sql);
        }
        catch(Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        return isOk;
    }

    

    /**
    강사에 해당하는 강의과정수정
    @param box      receive from the form object and session
    @return int
    */
     public int updateTutorSubj(DBConnectionManager connMgr,RequestBox box) throws Exception {
        String sql1                 = "";
        String sql2                 = "";
        String v_user_id            = box.getSession("userid");
        String v_userid             = box.getString("p_userid");
        //p_subj로 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_subj               = new Vector();
        v_subj                      = box.getVector("p_subj");
        Enumeration em              = v_subj.elements();
        String v_eachSubj           = "";   //실제 넘어온 각각의 과정코드
        int isOk                    = 0;
        try{
            //delete TZ_SUBJMAN table
            sql1 = "delete from TZ_SUBJMAN where userid='"+v_userid+"' and gadmin='P1'";
            isOk = connMgr.executeUpdate(sql1);
            while(em.hasMoreElements()){
                v_eachSubj   = (String)em.nextElement();
                //insert TZ_SUBJMAN table
                sql2 = "insert into TZ_SUBJMAN(userid,gadmin,subj,luserid,ldate) ";
                sql2+= "values('"+v_userid+"','P1','"+v_eachSubj+"','"+v_user_id+"',to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                isOk = connMgr.executeUpdate(sql2);
            }
        }
        catch(Exception ex) {
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        return isOk;
     }

    /**
    운영자권한 테이블에 수정
    @param box      receive from the form object and session
    @return int
    */
     public int updateManager(DBConnectionManager connMgr,RequestBox box) throws Exception {
        String sql1                 = "";
        String sql2                 = "";
        String v_user_id            = box.getSession("userid");
        String v_userid             = box.getString("p_userid");
        String v_manager            = box.getString("p_manager");
        String v_fmon               = box.getString("p_fmon");
        String v_tmon               = box.getString("p_tmon");
        String v_compcd             = box.getString("p_compcd");
        int    isOk                 = 0;
        try{
            //delete TZ_MANAGER table
            sql1 =  "delete from TZ_MANAGER where userid='"+v_userid+"' and gadmin='P1'";
            isOk = connMgr.executeUpdate(sql1);

            if(v_manager.equals("Y")){
                sql2 = "insert into TZ_MANAGER(userid,gadmin,comp,isdeleted,fmon,tmon,luserid,ldate) ";
                sql2+=  "values('"+v_userid+"','P1','"+v_compcd+"','N','"+v_fmon+"','"+v_tmon+"','"+v_user_id+"', to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                isOk = connMgr.executeUpdate(sql2);
            }
        }
        catch(Exception ex) {
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        return isOk;
    }

    /**
    교육그룹 select box
    @param selectname       select box name
    @param selected         selected valiable
    @param allcheck         all check Y(1),all check N(0)
    @return int
    */
    public static String getGrcodeSelect (String selectname, String selected, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        ListSet ls = null;
        String result = null;
        String sql = "";

        result = "  <SELECT name=" + selectname + " > \n";

        if (allcheck == 1) {
          result += " <option value=''>=== 전체 ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql  = "select grcode, grcodenm from tz_grcode  ";
            sql += " order by grcodenm";

            ls = connMgr.executeQuery(sql);
//            System.out.println("selected===>"+selected);
            while (ls.next()) {
                result += " <option value=" + ls.getString("grcode");
                if (selected.equals(ls.getString("grcode"))) {
                    result += " selected ";
                }

                result += ">" + ls.getString("grcodenm") + "</option> \n";
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

        result += "  </SELECT> \n";
        return result;
    }

    /**
    과정조회
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectSearchSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls          = null;
        ArrayList list      = null;
        String sql          = "";
        DataBox dbox        = null;
        String v_open_select     = box.getString("p_open_select");
        int    v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            //select subj,subjnm
            sql = "select subj,subjnm from TZ_SUBJ ";
            sql+= "where subjnm like '%"+v_open_select+"%'";
            //System.out.println("sql=========>"+sql);
            ls = connMgr.executeQuery(sql);

            ls.setPageSize(10);                         //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);                //현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();   //전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();   //전체 row 수를 반환한다
            while (ls.next()) {
                dbox = ls.getDataBox();
                //dbox.put
                list.add(dbox);
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
   * 사내 강사 조회
    @param box      receive from the form object and session
    @return ArrayList
   */
    public ArrayList selectSearchTutor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        ListSet ls = null;
        ArrayList list = null;
//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
		
        MemberData data = null;
        String v_search     = box.getString("p_mode1");
        String v_searchtext = box.getString("p_mode2");
        int v_pageno = box.getInt("p_pageno");

        ConfigSet config =new ConfigSet();
        int row = Integer.parseInt(config.getProperty("page.bulletin.row"));

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            head_sql  = "select a.userid userid, a.resno resno, a.pwd pwd, a.name name, a.email email, a.birthday birthday, a.cono cono, ";
			head_sql += "	a.authority authority, a.post1 post1, a.post2 post2, a.addr addr, a.hometel hometel, a.handphone handphone, ";
			head_sql += "	a.comptel comptel, a.tel_line tel_line, a.comp comp, a.interest interest, a.recomid recomid, ";
			head_sql += "	a.ismailing ismailing, a.indate indate, a.lgcnt lgcnt, a.lglast lglast, a.lgip lgip, a.pwd_date pwd_date, ";
			head_sql += "	a.old_pwd old_pwd, a.asgn asgn, a.asgnnm asgnnm, a.jikun jikun, a.jikunnm jikunnm, a.jikup jikup, ";
			head_sql += "	a.jikupnm jikupnm, a.jikwi jikwi, a.jikwinm jikwinm,  ";
			head_sql += "	a.jikmunm jikmunm, a.jikchek jikchek, a.jikcheknm jikcheknm, a.ent_date ent_date, ";
			head_sql += "	a.grp_ent_date grp_ent_date, a.pmt_date pmt_date, a.old_cono old_cono, a.cono_chg_date cono_chg_date, ";
			head_sql += "	a.office_gbn office_gbn, a.office_gbnnm office_gbnnm, a.retire_date retire_date, a.work_plc work_plc, ";
			head_sql += "	a.work_plcnm work_plcnm, a.sex sex, b.companynm companynm, b.gpmnm gpmnm, b.deptnm deptnm, ";
			head_sql += "	get_compnm(a.comp,3,5) compnm ";
            body_sql += " from TZ_MEMBER A, TZ_COMP B  ";
			body_sql += " where a.comp = b.comp ";
            if ( !v_searchtext.equals("")) {                            //    검색어가 있으면
                if (v_search.equals("id")) {                            //    ID로 검색할때
					body_sql += " and a.userid like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("cono")) {                   //    사번으로 검색할때
					body_sql += " and a.cono like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("name")) {                   //    이름으로 검색할때
					body_sql += " and a.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            order_sql += " order by a.comp asc, a.jikwi asc ";
//            System.out.println("sql==============>"+sql);
			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(10);                         //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);                //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();   //     전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();   //     전체 row 수를 반환한다

            while (ls.next()) {
                data = new MemberData();

                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setPwd(ls.getString("pwd"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setBirthday(ls.getString("birthday"));
                data.setCono(ls.getString("cono"));
                data.setAuthority(ls.getString("authority"));
                data.setPost1(ls.getString("post1"));
                data.setPost2(ls.getString("post2"));
                data.setAddr(ls.getString("addr"));
                data.setHometel(ls.getString("hometel"));
                data.setHandphone(ls.getString("handphone"));
                data.setComptel(ls.getString("comptel"));
                data.setTel_line(ls.getString("tel_line"));
                data.setComp(ls.getString("comp"));
                data.setInterest(ls.getString("interest"));
                data.setRecomid(ls.getString("recomid"));
                data.setIsmailing(ls.getString("ismailing"));
                data.setIndate(ls.getString("indate"));
                data.setLglast(ls.getString("lglast"));
                data.setLgip(ls.getString("lgip"));
                data.setPwd_date(ls.getString("pwd_date"));
                data.setOld_pwd(ls.getString("old_pwd"));
                data.setAsgn(ls.getString("asgn"));
                data.setAsgnnm(ls.getString("asgnnm"));
                data.setJikun(ls.getString("jikun"));
                data.setJikunnm(ls.getString("jikunnm"));
                data.setJikup(ls.getString("jikup"));
                data.setJikupnm(ls.getString("jikupnm"));
                data.setJikwi(ls.getString("jikwi"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setJikchek(ls.getString("jikchek"));
                data.setJikcheknm(ls.getString("jikcheknm"));
                data.setEnt_date(ls.getString("ent_date"));
                data.setGrp_ent_date(ls.getString("grp_ent_date"));
                data.setPmt_date(ls.getString("pmt_date"));
                data.setOld_cono(ls.getString("old_cono"));
                data.setOffice_gbn(ls.getString("office_gbn"));
                data.setSex(ls.getString("sex"));
                data.setCompanynm(ls.getString("companynm"));
                data.setGpmnm(ls.getString("gpmnm"));
                data.setDeptnm(ls.getString("deptnm"));
                data.setCompnm(ls.getString("compnm"));
                data.setLgcnt(ls.getInt("lgcnt"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalPageCount(total_page_count);

                list.add(data);
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
    강사 이력 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectTutorHistoryList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls      = null;
        ArrayList list  = null;
        String sql      = "";
        DataBox dbox  = null;

        String v_select         = box.getString("p_select");        //검색항목(과정명1,강사명2)
        String v_selectvalue    = box.getString("p_selectvalue");   //검색어
        String v_gyear          = box.getString("p_gyear");         //년도
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            //select userid,name,comp,dept,handphone,email,isGubun,subj,subjnm,lecture,sdesc,lectlevel
            sql = "select A.userid,A.name,A.comp,A.dept,A.handphone,A.email,A.isGubun,B.subj,B.subjnm, ";
            sql+= "	C.lecture,C.sdesc,C.lectlevel ";
            sql+= "from TZ_TUTOR A,TZ_SUBJ B,TZ_OFFSUBJLECTURE C ";
            sql+= "where A.userid = C.tutorid and B.subj = C.subj  ";
/*
            if ( !v_search.equals("")) {            //전문분야가 있는 경우
                sql += "and A.professional like '%"+v_search+"%' ";
            }
*/
            if(v_select.equals("1")) {              //검색항목이 과정명인경우
                sql += "and B.subjnm like '%"+v_selectvalue+"%' ";
            }else if (v_select.equals("2")) {       //검색항목이 강사명인경우
                sql += "and A.name like '%"+v_selectvalue+"%' ";
            }
            if ( !v_gyear.equals("")) {             //년도가 있는 경우
                sql += "and C.year ="+SQLString.Format(v_gyear);
            }
           sql += "order by A.name,B.subj,C.subjseq,C.lecture ";

//           System.out.println("sql============>"+sql);
           ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                //dbox.put
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
    강사 이력평가 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectTutorLectureList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls      = null;
        ArrayList list  = null;
        String sql      = "";
        DataBox	dbox	= null;
        String v_userid         = box.getString("p_userid");        //강사아이디
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            //select subj,subjnm,year,subjseq,lecture,sdesc,lectdate,lectsttime,lecttime,lectscore,lectlevel
            sql = "select B.subj,B.subjnm,C.year,C.subjseq,C.lecture,C.sdesc,C.lectdate,C.lectsttime,C.lecttime, ";
            sql+= "	C.lectscore,C.lectlevel ";
            sql+= " from TZ_TUTOR A,TZ_SUBJ B,TZ_OFFSUBJLECTURE C ";
            sql+= " where A.userid = C.tutorid and B.subj = C.subj and A.userid="+SQLString.Format(v_userid);
            sql+= " order by B.subj,C.subjseq,C.lecture";

//           System.out.println("sql============>"+sql);
           ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                //dbox.put
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
    강사 평가 저장
    @param box      receive from the form object and session
    @return int
    */
     public int updateTutorScore(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1         = "";
        int isOk            = 0;
        String v_user_id    = box.getSession("userid");
        String v_userid     = box.getString("p_userid");        //강사아이디
        String v_subj       = "";
        String v_year       = "";
        String v_subjseq    = "";
        String v_lecture    = "";
        //String v_lectscore  = "";
        String v_lectlevel  = "";
        int    v_lectscore  =  0;
        Vector v_vec1 = new Vector();
        Vector v_vec2 = new Vector();
        Vector v_vec3 = new Vector();
        Vector v_vec4 = new Vector();
        Vector v_vec5 = new Vector();
        v_vec1  = box.getVector("p_subj");
        v_vec2  = box.getVector("p_year");
        v_vec3  = box.getVector("p_subjseq");
        v_vec4  = box.getVector("p_lecture");
        v_vec5  = box.getVector("p_lectscore");
        Enumeration em1      = v_vec1.elements();
        Enumeration em2      = v_vec2.elements();
        Enumeration em3      = v_vec3.elements();
        Enumeration em4      = v_vec4.elements();
        Enumeration em5      = v_vec5.elements();

        try {
            connMgr = new DBConnectionManager();

            //update TZ_OFFSUBJLECTURE table
            sql1 = "update TZ_OFFSUBJLECTURE set lectscore=?,lectlevel=? ";
            sql1+= "where subj=? and year=? and subjseq=? and lecture=? ";
//            System.out.println("sql1========>"+sql1);
            pstmt1 = connMgr.prepareStatement(sql1);

            while(em1.hasMoreElements()){
                v_subj      = (String)em1.nextElement();
                v_year      = (String)em2.nextElement();
                v_subjseq   = (String)em3.nextElement();
                v_lecture   = (String)em4.nextElement();
                v_lectscore = Integer.parseInt((String)em5.nextElement());
                if(v_lectscore >= 90)       { v_lectlevel = "A";
                }else if(v_lectscore >= 80) { v_lectlevel = "B";
                }else if(v_lectscore >= 70) { v_lectlevel = "C";
                }else if(v_lectscore >= 60) { v_lectlevel = "D";
                }else if(v_lectscore >= 50) { v_lectlevel = "E";
                }else                       { v_lectlevel = "F";  }

                pstmt1.setInt(1, v_lectscore);
                pstmt1.setString(2, v_lectlevel);
                pstmt1.setString(3, v_subj);
                pstmt1.setString(4, v_year);
                pstmt1.setString(5, v_subjseq);
                pstmt1.setInt(6, Integer.parseInt(v_lecture));
                isOk = pstmt1.executeUpdate();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
    
    
    /**
    입력자정보
    @param box      receive from the form object and session
    @return ArrayList
    */
    public DataBox selectInuserInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        String 		sql      		= "";
        DataBox 	dbox  			= null;
        
        String v_subjgubun  = box.getStringDefault("p_subjgubun","A");
        String v_subj	    = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();

			sql ="select a.inuserid,\n";
			sql+="	   	 (select name from tz_member where userid = a.inuserid) inusername,\n";
			sql+="	   	 (select get_jikwinm(jikwi,comp) from tz_member where userid = a.inuserid) inuserjikwinm,\n";
			sql+="	   	 (select email from tz_member where userid = a.inuserid) inuseremail,\n";
			sql+="	   	 (select comptel from tz_member where userid = a.inuserid) inuserecomptel,\n";
			sql+="	   	 (select handphone from tz_member where userid = a.inuserid) inuserhandphone,\n";
			sql+="	   	 a.indate \n";
			sql+="from   tz_tsubj a \n";
			sql+="where  a.subj = '" + v_subj + "' ";
			

			System.out.println("sql->" + sql);
            
            ls = connMgr.executeQuery(sql);


            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_inuserid"		,ls.getString("inuserid"));
                dbox.put("d_inusername"		,ls.getString("inusername"));
                dbox.put("d_inuserjikwinm"	,ls.getString("inuserjikwinm"));
                dbox.put("d_inuseremail"	,ls.getString("inuseremail"));
                dbox.put("d_inuserecomptel"	,ls.getString("inuserecomptel"));
                dbox.put("d_inuserhandphone",ls.getString("inuserhandphone"));
                
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
}