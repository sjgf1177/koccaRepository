//**********************************************************
//  1. ��      ��: TUTOR ADMIN BEAN
//  2. ���α׷���: TutorAdminBean.java
//  3. ��      ��: ������� ������ bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 
//  7. ��      ��:
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
            row = Integer.parseInt(config.getProperty("page.manage.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
    ���������Ȳ ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls    			= null;
        ArrayList	list  			= null;
        DataBox 	dbox  			= null;
 
//		2005.11.15_�ϰ��� : TotalCount ���� ���� ���� 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
		
        String 		v_year         	= box.getStringDefault("s_year","");         	//�⵵
        String 		v_edugubun      = box.getStringDefault("s_edugubun","ALL");    	//��������
        String 		v_company   	= box.getStringDefault("s_company","ALL");   	//ȸ��
        String 		v_edudept   	= box.getStringDefault("s_edudept","ALL");   	//�����ְ���
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");   //������
        String  	v_me		 	= box.getString("p_me"); //�ڱⰡ ����� ����
        
        String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");          		//������ ����
        
        String  s_userid        = box.getSession("userid");
        int		v_pageno 		= box.getInt("p_pageno");        
    
        //�Ѱ�������/��������ڴ� Default N
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
			//2005.11.15_�ϰ��� : Oracle -> Mssql
			//sql+="			decode(SUBSTR(inusercomp,1,4),'0101','(HMC)','0102','(KMC)','') inusercompnm \n";
			head_sql+="			(case substring(inusercomp,1,4) When '0101' Then '(HMC)' When '0102' Then '(KMC)' Else '' end ) inusercompnm \n";
			
			body_sql+= " from  	tz_tsubj ";
			body_sql+= " where  	1 = 1 ";
			
			//�⵵
			if (!v_year.equals("")) {
				body_sql+= "		and substring(edustarta,1,4) = '" + v_year + "' ";
			}
			
			//��������
			if (!v_edugubun.equals("ALL")) {
				body_sql+= "		and edugubun = '" + v_edugubun + "' ";				
			}
			
			//ȸ��
			if (!v_company.equals("ALL")) {
				body_sql+= "		and substring(inusercomp,1,4) = '" + StringManager.substring(v_company, 0, 4) + "' ";				
			}
			
			//�����ְ���
			if (!v_edudept.equals("ALL")) {
				body_sql+= "		and inusercomp = '" + v_edudept + "' ";				
			}
			
			//������
			if (!v_srchsubjnm.equals("")) {
				body_sql+= "		and subjnm like '%" + v_srchsubjnm + "%' ";				
			}
			
			//�ڱⰡ ����� ����
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

            ls.setPageSize(row);                       //�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage();    //��ü ������ ���� ��ȯ�Ѵ�
            //int total_row_count = ls.getTotalCount();    //��ü row���� ��ȯ�Ѵ�

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
    �������뺸��
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
			//����
			sql+= "	   		edustarta,";
			sql+= "	   		eduenda,";
			sql+= "	   		edutargeta,";
			sql+= "	   		edutmema,";
			sql+= "	   		edutseqa,";
			sql+= "	   		seqpermema,";
			sql+= "	   		eduplacea,";
			//����
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
			//2005.11.15_�ϰ��� : Oracle -> Mssql
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
                //����
                dbox.put("edustarta"	,ls.getString("edustarta"));
                dbox.put("eduenda"		,ls.getString("eduenda"));
                dbox.put("edutargeta"	,ls.getString("edutargeta"));
                dbox.put("edutmema"		,ls.getString("edutmema"));
                dbox.put("edutseqa"		,ls.getString("edutseqa"));
                dbox.put("seqpermema"	,ls.getString("seqpermema"));
                dbox.put("eduplacea"	,ls.getString("eduplacea"));
                //����
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
    �����׸� ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
    //subj �ڵ尪�� ������� �ش� ������ ����/�����׸� ������
    //subj �ڵ尪�� ������� TZ_CODE GUBUN=24 �׸� ������
    public ArrayList selectBudgetSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        String 		sql      		= "";
        DataBox 	dbox  			= null;

		String 		v_subj			= box.getStringDefault("p_subj","");	//�����ڵ�
		String 		gubun			= box.getStringDefault("gubun","A");//����(A:�ο�,B:�����׸�)
		String 		gubuncode		= "";
		
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

			if (gubun.equals("A")) {
				gubuncode = "0026";	//�ο�
			} else {
				gubuncode = "0025"; //����
			}
				
			if (v_subj.equals("")) {
				//�����ڵ尡 ���� ��� tz_code gubun=25�� �����׸��� �����´�.
            	sql = "select 	a.code,";
				sql+= "	   		a.codenm,";
				sql+= "	   		0 plandata,";
				sql+= "	   		0 execdata ";
				sql+= "from   	tz_code a ";
				sql+= "where  	a.gubun = '"+ gubuncode +"' ";
				sql+= "order by a.code";
            } else {
            	//�����ڵ尡 �ִ� ��� �ش� ������ tz_code gubun=25 �����ڷḦ �����´�.
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
    ���α׷� ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
    //subj �ڵ尪�� ������� �ش� ������ ����/�����׸� ������
    //subj �ڵ尪�� ������� TZ_CODE GUBUN=24 �׸� ������
    public ArrayList selectSubjProgramList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        String 		sql      		= "";
        DataBox 	dbox  			= null;

		String 		v_subj			= box.getStringDefault("p_subj","");	//�����ڵ�
		
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

          	sql = "select  seq,";
			sql+= "		   lessonnm,";
			sql+= "		   userid tutorid, ";
			sql+= "		   tutorgubun,";
			//2005.11.16_�ϰ��� : Oracle -> Mssql
			//sql+= "		decode(tutorgubun, 'C', (select compnm from tz_tcomp where busino=userid), ";
			//sql+= "		 (select name from tz_tutor where userid=tz_tlesson.userid)) tutorname,";
			sql+= "		   decode(tutorgubun, 'I', '�系����', 'O', '��ܰ���', 'C', '�������') tutorgubunnm,";
			sql+= "		   case tutorgubun When 'C' Then (select compnm from tz_tcomp where busino=userid) Else ";
			sql+= "		   (select name from tz_tutor where userid=tz_tlesson.userid) End as tutorname,";
			sql+= "		   case tutorgubun When 'I' Then '�系����' When 'O' Then '��ܰ���' When 'C' Then '�������' end as tutorgubunnm,";
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
    ���ǰ��� ��ȸ
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
    �������
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
        String v_subjnm 	= box.getString("p_subjnm");			//������
        String v_edugubun 	= box.getString("p_edugubun");			// ��������
        
        String v_edustarta 	= box.getString("p_edustarta"); 		//����������
        String v_eduenda 	= box.getString("p_eduenda"); 			//����������
        String v_edutargeta = box.getString("p_edutargeta"); 		//�������
        
		//�����ο� ����	        
        int 	v_edumemcnt = box.getInt("p_edumemcnt"); 			//�����ο� �� ����
        String	[] v_edumem	= new String [v_edumemcnt];				//�����ο�
        String chkstring = "0";
        for(int i=0; i<v_edumemcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_edumem[i]		= box.getString("p_edumem" + chkstring + (i+1) + "a");
      	}
      	//�����ο� ��
        
        int 	v_edutmema 	 = box.getInt("p_edutmema"); 			//�������ο�
        int 	v_edutseqa 	 = box.getInt("p_edutseqa"); 			//����������
        int 	v_seqpermema = box.getInt("p_seqpermema"); 			//�������ο�
        String 	v_eduplacea  = box.getString("p_eduplacea"); 		//�������

		//�����׸� ����
        int	   	v_budgetcnt = box.getInt("p_budgetcnt"); 			//�����Ѱ���
        String [] v_budget	= new String [v_budgetcnt];				//����
        chkstring = "0";
        for(int i=0; i<v_budgetcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_budget[i]		= box.getString("p_budget" + chkstring + (i+1) + "a");
        	//System.out.println("v_budget[" + chkstring + (i+1) + "]a : "+v_budget[i]);
      	}
      	//�����׸� ��
        
        int    	v_gyamt 	= box.getInt("p_gyamt"); 				//��뺸��ȯ�޿���ݾ�

		//���α׷��׸� ����
        int		v_lessoncnt 	= box.getInt("p_lessoncnt"); 		//���α׷� �� ����
        String [] v_lessonnm	= new String [v_lessoncnt];	    	//���α׷���
        String [] v_tutorgubun	= new String [v_lessoncnt];	    	//���籸��(I:�系����,O:��ܰ���,C:�뿪��ü)
        String [] v_tutorid		= new String [v_lessoncnt];	    	//����ID/����ڹ�ȣ
        int	   [] v_eduhr		= new int [v_lessoncnt];	    	//�����ð�
        int	   [] v_eduprice	= new int [v_lessoncnt];	    	//�����
        
        for(int i=0; i<v_lessoncnt; i++) {
        	v_lessonnm[i]		= box.getStringDefault("p_lessonnm" + (i+1),"");
        	v_tutorgubun[i]		= box.getString("p_tutorgubun" + (i+1));
        	v_tutorid[i]		= box.getString("p_tutorid" + (i+1));
        	v_eduhr[i]			= box.getInt("p_eduhr" + (i+1));
        	v_eduprice[i]		= box.getInt("p_eduprice" + (i+1));
      	}
      	//���α׷��׸� ��
      	
        try {

            connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			String v_subj = getMaxSubjcode(connMgr);
			System.out.println("v_subj="+v_subj);
			
            //�������� ����[1/3]
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
			
			isOk1 = pstmt1.executeUpdate();              //TZ_TSUBJ ���
            
            //�ο����� ���� ����
            //GUBUN = 'A' : �ο�����
            sql2 = "insert into tz_tbudget( ";
			sql2+= "		   subj,";
			sql2+= "		   gubun,";
			sql2+= "		   gubuncode,";
			sql2+= "		   plandata,";
			sql2+= "		   execdata,";
			sql2+= "		   inusercomp ) ";
			sql2+= "values('" + v_subj + "','A',?,?,0,'"+v_usercomp+"') ";
			
			pstmt2 = connMgr.prepareStatement(sql2);
            
            //�ο����� ����[3/4]
            chkstring = "0";
        	for(int i=0; i<v_edumemcnt; i++) {
        		if (i>=9) chkstring = "";
        		pstmt2.setString(1, chkstring + (i+1));
	        	pstmt2.setString(2, v_edumem[i]);
				isOk2 = pstmt2.executeUpdate();	
	        }
	        //�ο����� ���� ��
	        
	        //�������� ���� ����
	        //GUBUN = 'B' : ��������
            sql3 = "insert into tz_tbudget( ";
			sql3+= "		   subj,";
			sql3+= "		   gubun,";
			sql3+= "		   gubuncode,";
			sql3+= "		   plandata,";
			sql3+= "		   execdata,";
			sql3+= "		   inusercomp ) ";
			sql3+= "values('" + v_subj + "','B',?,?,0,'"+v_usercomp+"') ";
            pstmt3 = connMgr.prepareStatement(sql3);
            
            //�������� ����[3/4]
            chkstring = "0";
	        for(int i=0; i<v_budgetcnt; i++) {
	        	if (i>=9) chkstring = "";
	        	pstmt3.setString(1, chkstring + (i+1));
	        	pstmt3.setString(2, v_budget[i]);
				isOk3 = pstmt3.executeUpdate();	
	        }
	        //�������� ���� ��

            
            //���α׷� ���� ����
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
			
			//���α׷����� ����[4/4]
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
			//���α׷� ���� ��
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
    ��������
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
        String v_subjnm 	= box.getString("p_subjnm");			//������
        String v_edugubun 	= box.getString("p_edugubun");			// ��������
        
        String v_edustarta 	= box.getString("p_edustarta"); 		//����������
        String v_eduenda 	= box.getString("p_eduenda"); 			//����������
        String v_edutargeta = box.getString("p_edutargeta"); 		//�������
        
		//�����ο� ����	        
        int 	v_edumemcnt = box.getInt("p_edumemcnt"); 			//�����ο� �� ����
        String	[] v_edumem	= new String [v_edumemcnt];				//�����ο�
        String chkstring = "0";
        for(int i=0; i<v_edumemcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_edumem[i]		= box.getString("p_edumem" + chkstring + (i+1) + "a");
      	}
      	//�����ο� ��
        
        int 	v_edutmema 	 = box.getInt("p_edutmema"); 			//�������ο�
        int 	v_edutseqa 	 = box.getInt("p_edutseqa"); 			//����������
        int 	v_seqpermema = box.getInt("p_seqpermema"); 			//�������ο�
        String 	v_eduplacea  = box.getString("p_eduplacea"); 		//�������

		//�����׸� ����
        int	   	v_budgetcnt = box.getInt("p_budgetcnt"); 			//�����Ѱ���
        String [] v_budget	= new String [v_budgetcnt];				//����
        chkstring = "0";
        for(int i=0; i<v_budgetcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_budget[i]		= box.getString("p_budget" + chkstring + (i+1) + "a");
        	//System.out.println("v_budget[" + chkstring + (i+1) + "]a : "+v_budget[i]);
      	}
      	//�����׸� ��
        
        int    	v_gyamt 	= box.getInt("p_gyamt"); 				//��뺸��ȯ�޿���ݾ�

		//���α׷��׸� ����
        int		v_lessoncnt 	= box.getInt("p_lessoncnt"); 		//���α׷� �� ����
        String [] v_lessonnm	= new String [v_lessoncnt];	    	//���α׷���
        String [] v_tutorgubun	= new String [v_lessoncnt];	    	//���籸��(I:�系����,O:��ܰ���,C:�뿪��ü)
        String [] v_tutorid		= new String [v_lessoncnt];	    	//����ID/����ڹ�ȣ
        int	   [] v_eduhr		= new int [v_lessoncnt];	    	//�����ð�
        int	   [] v_eduprice	= new int [v_lessoncnt];	    	//�����
        
        for(int i=0; i<v_lessoncnt; i++) {
        	v_lessonnm[i]		= box.getStringDefault("p_lessonnm" + (i+1),"");
        	v_tutorgubun[i]		= box.getString("p_tutorgubun" + (i+1));
        	v_tutorid[i]		= box.getString("p_tutorid" + (i+1));
        	v_eduhr[i]			= box.getInt("p_eduhr" + (i+1));
        	v_eduprice[i]		= box.getInt("p_eduprice" + (i+1));
      	}
      	//���α׷��׸� ��
      	
        try {

            connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			//String v_subj = getMaxSubjcode(connMgr);
			System.out.println("v_subj="+v_subj);
			
            //�������� ����[1/3]
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
		
			isOk1 = pstmt1.executeUpdate();              //TZ_TSUBJ ���
			System.out.println("isok1="+isOk1);
            
            //�ο����� ���� ����
            //GUBUN = 'A' : �ο�����
            sql2 = "update	tz_tbudget ";
			sql2+= "set		plandata  = ?,";
			sql2+= "		execdata  = 0 ";
			sql2+= "where	subj 	  = '" + v_subj + "' and ";
			sql2+= "		gubun 	  = 'A' and ";
			sql2+= "		gubuncode = ? ";
			
			pstmt2 = connMgr.prepareStatement(sql2);
            
            //�ο����� ����[3/4]
            chkstring = "0";
        	for(int i=0; i<v_edumemcnt; i++) {
        		if (i>=9) chkstring = "";
	        	pstmt2.setString(1, v_edumem[i]);		//plandata
        		pstmt2.setString(2, chkstring + (i+1)); //gubuncode
				isOk2 = pstmt2.executeUpdate();	
	        }
	        //�ο����� ���� ��
	        
	        //�������� ���� ����
	        //GUBUN = 'B' : ��������
            sql3 = "update	tz_tbudget ";
			sql3+= "set		plandata  = ?,";
			sql3+= "		execdata  = 0  ";
			sql3+= "where	subj 	  = '" + v_subj + "' and ";
			sql3+= "		gubun 	  = 'B' and ";
			sql3+= "		gubuncode = ? ";
			
            pstmt3 = connMgr.prepareStatement(sql3);
            
            //�������� ����[3/4]
            chkstring = "0";
	        for(int i=0; i<v_budgetcnt; i++) {
	        	if (i>=9) chkstring = "";
	        	pstmt3.setString(1, v_budget[i]);
	        	pstmt3.setString(2, chkstring + (i+1));
				isOk3 = pstmt3.executeUpdate();	
	        }
	        //�������� ���� ��

            
            //if exists then update else insert--�߰� �ʿ�
            
            
            //���α׷� ���� ����
            sql4 = "update 	tz_tlesson ";
			sql4+= "set		tutorgubun	= ?,";
			sql4+= "		userid		= ?,";
			sql4+= "		lessonnm	= ?,";
			sql4+= "		eduhr		= ?,";
			sql4+= "		price		= ? ";
			sql4+= "where	subj = '" + v_subj + "' and ";
			sql4+= "		seq = ? ";
			pstmt4 = connMgr.prepareStatement(sql4);
			
			//���α׷� ���� ����
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
			
			//���α׷����� ����[4/4]
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
			//���α׷� ���� ��
			
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
    ��������
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
    �������Ȳ ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList selectSubjStatusList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        DataBox 	dbox  			= null;
//		2005.11.15_�ϰ��� : TotalCount ���� ���� ���� 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";

        String 		v_searchtype    = box.getStringDefault("p_searchtype","S"); //S:������ ����, E:�����ϱ���
        String 		v_searchgubun   = box.getStringDefault("p_searchgubun","Y");//Y:�⵵�����˻�, T:�Ⱓ�˻�
        String 		v_edustartday   = box.getStringDefault("p_edustartday",""); //�˻�������
        String 		v_eduendday   	= box.getStringDefault("p_eduendday","");   //�˻�������
        
        String 		v_year         	= box.getStringDefault("s_year","");        //�⵵
        String 		v_edugubun      = box.getStringDefault("s_edugubun","ALL"); //��������
        String 		v_company   	= box.getStringDefault("s_company","ALL");  //ȸ��
        String 		v_edudept   	= box.getStringDefault("s_edudept","ALL");  //�����ְ���
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");  //������
        String  	v_me		 	= box.getString("p_me"); 					//�ڱⰡ ����� ����
        String  	v_isinput	 	= box.getStringDefault("p_isinput","ALL");	//�����Է¿���
        
        String  	v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  	v_orderType     = box.getString("p_orderType");           		//������ ����
        
        int			v_pageno 		= box.getInt("p_pageno");
        
        String  s_userid        = box.getSession("userid");

        try {
        	//�Ѱ�������/��������ڴ� Default N
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
			// 2005.11.16_�ϰ��� : Oracle -> Mssql
			//sql+="			decode(substr(inusercomp,1,4),'0101','(HMC)','0102','(KMC)','') inusercompnm, \n";
			head_sql +="			case(SUBSTR(inusercomp,1,4) When '0101' Then '(HMC)' When '0102' Then '(KMC)' Else '' end as inusercompnm, \n";
			head_sql += "			(select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') totplanmemcnt,";
			head_sql += "			(select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') totexecmemcnt,";
			// 2005.11.16_�ϰ��� : Oracle -> Mssql
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
			// 2005.11.16_�ϰ��� : Oracle -> Mssql 
			//sql+= "			decode ( (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B'), 0 , 0 , ";
			//sql+= "					 (select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') /  ";
			//sql+= "					 (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') * 100) execpercent " ;
			head_sql += "			case  (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') When  0  Then  0 ";
			head_sql += "				Else ((select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') /  ";
			head_sql += "					 (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B') * 100) end as execpercent " ;
			body_sql += "from 	tz_tsubj ";
			body_sql += "where  	1 = 1 ";
			
			//�⵵���� �˻����� ���ý�
			if (v_searchgubun.equals("Y")) {
				//�⵵
				if (!v_year.equals("")) {
					body_sql += "		and substring(edustarta,1,4) = '" + v_year + "' ";
				}
			} else {
				//�Ⱓ�˻� �˻����� ���ý�
				
				if (v_searchtype.equals("S")) {
					//������ ����
					body_sql +="		and edustarta between '" + v_edustartday + "' and '" + v_eduendday + "' ";
				} else {
					//������ ����
					body_sql +="		and eduenda between '" + v_edustartday + "' and '" + v_eduendday + "' ";
				}
				
			}
			
			//��������
			if (!v_edugubun.equals("ALL")) {
				body_sql += "		and edugubun = '" + v_edugubun + "' ";				
			}
			
			//ȸ��
			if (!v_company.equals("ALL")) {
				body_sql += "		and substring(inusercomp,1,4) = '" + StringManager.substring(v_company, 0, 4) + "' ";				
			}
			
			//�����ְ���
			if (!v_edudept.equals("ALL")) {
				body_sql += "		and inusercomp = '" + v_edudept + "' ";				
			}
			
			//������
			if (!v_srchsubjnm.equals("")) {
				body_sql += "		and subjnm like '%" + v_srchsubjnm + "%' ";				
			}
			
			//�ڱⰡ ����� ����
			if (v_me.equals("Y")) {
				body_sql += "		and inuserid = '" + s_userid + "' ";				
			}
			
			//�����Է� ����
			if (v_isinput.equals("Y")) {
				body_sql += "		and savefile is not null ";				
			}
			
			//�����Է� ����
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
            
            ls.setPageSize(row);                       //�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);               //������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage();    //��ü ������ ���� ��ȯ�Ѵ�
            int totalrowcount = ls.getTotalCount();    //��ü row���� ��ȯ�Ѵ�

			double v_execpercent = 0; //������
			//double v_satisfaction = 0.0; //��ü������
			int v_totplancost = 0;
			int v_totexeccost = 0;
			
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("subj"		   		,ls.getString("subj"));			//�����ڵ�
                dbox.put("subjnm"	   		,ls.getString("subjnm"));		//������
                dbox.put("edugubun"	   		,ls.getString("edugubun"));		//���������ڵ�
                dbox.put("d_edugubunnm"		,ls.getString("edugubunnm"));	//����������Ī
                dbox.put("edustarta"   		,ls.getString("edustarta"));	//����������(��ȹ)
                dbox.put("eduenda"	   		,ls.getString("eduenda"));		//����������(����)
                dbox.put("d_totplanmemcnt"	,new Integer(ls.getInt("totplanmemcnt")));//�� �ο�(��ȹ)
                dbox.put("d_totexecmemcnt"	,new Integer(ls.getInt("totexecmemcnt")));//�� �ο�(����)
                dbox.put("d_mempercent"	,new Integer(ls.getInt("mempercent")));//������
                v_totplancost = ls.getInt("totplancost");
                v_totexeccost = ls.getInt("totexeccost");
                dbox.put("d_totplancost"	,new Integer(v_totplancost));  //�� ���(��ȹ)
                dbox.put("d_totexeccost"	,new Integer(v_totexeccost));  //�� ���(����)
                dbox.put("d_totgrade"		,new Integer(ls.getInt("totgrade")));		//��ü ������
                //������ ��� : �� ������� / �� ��ȹ��� * 100
                if (v_totplancost==0.0) {
                	v_execpercent = 0.0;
                }
                else {
                	v_execpercent = (double)v_totexeccost / (double)v_totplancost * 100.0;
                }
                //System.out.println("����="+v_execpercent);
                dbox.put("d_execpercent"	,new Double(v_execpercent));
                dbox.put("d_lessoncnt"		,new Integer(ls.getInt("lessoncnt")));	//���α׷� ����
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
    �������� ���
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
        
        String v_edustartb 	= box.getString("p_edustartb"); 		//����������(����)
        String v_eduendb 	= box.getString("p_eduendb"); 			//����������(����)
        String v_edutargetb = box.getString("p_edutargetb"); 		//�������(����)
        String v_comments 	= box.getString("p_comments"); 			//����� �ǰ�
        double v_satisfaction = box.getDouble("p_satisfaction");	//��ü ������
        
        //----------------------   ���ε�Ǵ� ���� --------------------------------
        String realFileName = box.getRealFileName("p_file");  
        String newFileName  = box.getNewFileName("p_file"); 
        
        
        System.out.println("realFileName = "+realFileName);
        System.out.println("newFileName = "+newFileName);
        
        
        
		//�����ο� ����	        
        int 	v_edumemcnt = box.getInt("p_edumemcnt"); 			//�����ο� �� ����
        String	[] v_edumem	= new String [v_edumemcnt];				//�����ο�
        String chkstring = "0";
        for(int i=0; i<v_edumemcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_edumem[i]		= box.getString("p_edumem" + chkstring + (i+1) + "b");
      	}
      	//�����ο� ��
        
        int 	v_edutmemb 	= box.getInt("p_edutmemb"); 			//�������ο�(����)
        int 	v_edutseqb 	= box.getInt("p_edutseqb"); 			//����������(����)
        int 	v_seqpermemb= box.getInt("p_seqpermemb"); 			//�������ο�(����)
        String 	v_eduplaceb = box.getString("p_eduplaceb"); 		//�������(����)

		//�����׸� ����
        int	   	v_budgetcnt = box.getInt("p_budgetcnt"); 			//�����Ѱ���
        String [] v_budget	= new String [v_budgetcnt];				//����
        chkstring = "0";
        for(int i=0; i<v_budgetcnt; i++) {
        	if (i>=9) chkstring = "";
        	
        	v_budget[i]		= box.getString("p_budget" + chkstring + (i+1) + "b");
        	//System.out.println("v_budget[" + chkstring + (i+1) + "]a : "+v_budget[i]);
      	}
      	//�����׸� ��
        
		//���α׷��׸� ����
        int		v_lessoncnt = box.getInt("p_lessoncnt"); 		//���α׷� �� ����
        double  [] v_grade	= new double [v_lessoncnt];	    	//����
        
        for(int i=0; i<v_lessoncnt; i++) {
        	v_grade[i]		= box.getDouble("p_grade" + (i+1));
      	}
      	//���α׷��׸� ��
      	
        try {

            connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			//String v_subj = getMaxSubjcode(connMgr);
			System.out.println("v_subj="+v_subj);
			
            //�������� ����[1/3]
            sql1 = "update 	tz_tsubj ";
			sql1+= "set		edustartb	= ?,";	//����������(����)
			sql1+= "		eduendb		= ?,";  //����������(����)
			sql1+= "		edutmemb	= ?,";	//�������ο�(����)
			sql1+= "		edutseqb	= ?,";	//����������(����)
			sql1+= "		seqpermemb	= ?,";	//�������ο�(����)
			sql1+= "		comments	= ?,";	//����� �ǰ�
			sql1+= "		realfile	= ?,";	//÷������
			sql1+= "		savefile	= ?,";	//÷������
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
		
			isOk1 = pstmt1.executeUpdate();              //TZ_TSUBJ ���
			System.out.println("isok1="+isOk1);
            
            //�ο����� ���� ����
            //GUBUN = 'A' : �ο�����
            sql2 = "update	tz_tbudget ";
			sql2+= "set		execdata  = ? ";
			sql2+= "where	subj 	  = '" + v_subj + "' and ";
			sql2+= "		gubun 	  = 'A' and ";
			sql2+= "		gubuncode = ? ";
			
			pstmt2 = connMgr.prepareStatement(sql2);
            
            //�ο����� ����[3/4]
            chkstring = "0";
        	for(int i=0; i<v_edumemcnt; i++) {
        		if (i>=9) chkstring = "";
	        	pstmt2.setString(1, v_edumem[i]);		//plandata
        		pstmt2.setString(2, chkstring + (i+1)); //gubuncode
				isOk2 = pstmt2.executeUpdate();	
	        }
	        //�ο����� ���� ��
	        
	        //�������� ���� ����
	        //GUBUN = 'B' : ��������
            sql3 = "update	tz_tbudget ";
			sql3+= "set		execdata  = ?  ";
			sql3+= "where	subj 	  = '" + v_subj + "' and ";
			sql3+= "		gubun 	  = 'B' and ";
			sql3+= "		gubuncode = ? ";
			
            pstmt3 = connMgr.prepareStatement(sql3);
            
            //�������� ����[3/4]
            chkstring = "0";
	        for(int i=0; i<v_budgetcnt; i++) {
	        	if (i>=9) chkstring = "";
	        	pstmt3.setString(1, v_budget[i]);
	        	pstmt3.setString(2, chkstring + (i+1));
				isOk3 = pstmt3.executeUpdate();	
	        }
	        //�������� ���� ��
            
            //if exists then update else insert--�߰� �ʿ�
            
            //���α׷� ���� ����
            sql4 = "update 	tz_tlesson ";
			sql4+= "set		grade		= ? ";
			sql4+= "where	subj = '" + v_subj + "' and ";
			sql4+= "		seq = ? ";
			
			pstmt4 = connMgr.prepareStatement(sql4);
			
			//���α׷����� ����[4/4]
			chkstring = "0";
			for(int i=0; i<v_lessoncnt; i++) {
				if (i>=9) chkstring = "";
				System.out.println("v_grade[i]"+v_grade[i]);
				pstmt4.setDouble	(1, v_grade[i]);
				pstmt4.setString(2, chkstring + (i+1));
				isOk4 = pstmt4.executeUpdate();	
				
			}
			//���α׷� ���� ��
			
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
    ����������Ȳ ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList selectBudgetStatusList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ListSet 	ls1      		= null;
        ArrayList 	list  			= null;
        DataBox 	dbox  			= null;
//		2005.11.15_�ϰ��� : TotalCount ���� ���� ���� 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";

        String 		v_searchtype    = box.getStringDefault("p_searchtype","S"); //S:������ ����, E:�����ϱ���
        String 		v_searchgubun   = box.getStringDefault("p_searchgubun","Y");//Y:�⵵�����˻�, T:�Ⱓ�˻�
        String 		v_edustartday   = box.getStringDefault("p_edustartday",""); //�˻�������
        String 		v_eduendday   	= box.getStringDefault("p_eduendday","");   //�˻�������
        
        String 		v_year         	= box.getStringDefault("s_year","");        //�⵵
        String 		v_edugubun      = box.getStringDefault("s_edugubun","ALL"); //��������
        String 		v_company   	= box.getStringDefault("s_company","ALL");  //ȸ��
        String 		v_edudept   	= box.getStringDefault("s_edudept","ALL");  //�����ְ���
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");  //������
        
        String      v_budgetgubun   = box.getStringDefault("p_budgetgubun","B");//B:����(0025),A:�ο�(0026)
        
        String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");           		//������ ����

		String  v_code = "0025"; //����
        
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
			// 2005.11.16_�ϰ��� : Oracle -> Mssql
			//sql+="			decode(substr(inusercomp,1,4),'0101','(HMC)','0102','(KMC)','') inusercompnm, \n";
			head_sql+="			case (SUBSTR(inusercomp,1,4) When '0101' Then '(HMC)' When '0102' Then '(KMC)' Else '' end as inusercompnm, \n";
			
			head_sql+= "			(select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') totplanmemcnt,\n";
			head_sql+= "			(select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='A') totexecmemcnt,\n";
			
			head_sql+= "			round((select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B')/1000,0) totplancost,\n";
			head_sql+= "			round((select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='B')/1000,0) totexeccost,\n";
			
			head_sql+= "			(select NVL(avg(grade),0)	 from tz_tlesson where subj = tz_tsubj.subj) totgrade,\n";
			// 2005.11.16_�ϰ��� : Oracle -> Mssql
			//sql+= "			decode ( (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "'), 0 , 0 , \n";
			//sql+= "					 (select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "') /  \n";
			//sql+= "					 (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "') * 100) execpercent, \n" ;
			head_sql+= "			case  (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "') When  0  Then 0 \n";
			head_sql+= "				Else ((select NVL(sum(execdata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "') /  \n";
			head_sql+= "					 (select NVL(sum(plandata),0) from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "') * 100) end as execpercent, \n" ;

			//�����׸� ���� ���� ����
            ls1 = connMgr.executeQuery("select code from tz_code where gubun='" + v_code + "' order by code"); //0025

            while (ls1.next()){
            	
            	if (v_budgetgubun.equals("B")) {
					head_sql+= " round((NVL((select plandata from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "' and gubuncode='" + ls1.getString("code") + "'),0)/1000),0) plan" + ls1.getString("code") + ", \n";
					head_sql+= " round((NVL((select execdata from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "' and gubuncode='" + ls1.getString("code") + "'),0)/1000),0) exec" + ls1.getString("code") + ", \n";
            	} else { //�ο�����Ȳ�� 1000���� ������ �ʴ´�.
					head_sql+= " NVL((select plandata from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "' and gubuncode='" + ls1.getString("code") + "'),0) plan" + ls1.getString("code") + ", \n";
					head_sql+= " NVL((select execdata from tz_tbudget where subj = tz_tsubj.subj and gubun='" + v_budgetgubun + "' and gubuncode='" + ls1.getString("code") + "'),0) exec" + ls1.getString("code") + ", \n";
            	}
            }

			head_sql+= "			(select count(*)  from tz_tlesson where subj = tz_tsubj.subj) lessoncnt \n";
			body_sql+= " from 	tz_tsubj \n";
			body_sql+= " where  	1 = 1 \n";
			
			//�⵵���� �˻����� ���ý�
			if (v_searchgubun.equals("Y")) {
				//�⵵
				if (!v_year.equals("")) {
					body_sql+= "		and substring(edustarta,1,4) = '" + v_year + "' \n";
				}
			} else {
				//�Ⱓ�˻� �˻����� ���ý�
				
				if (v_searchtype.equals("S")) {
					//������ ����
					body_sql+="		and edustarta between '" + v_edustartday + "' and '" + v_eduendday + "' \n";
				} else {
					//������ ����
					body_sql+="		and eduenda between '" + v_edustartday + "' and '" + v_eduendday + "' \n";
				}
				
			}
			
			//��������
			if (!v_edugubun.equals("ALL")) {
				body_sql+= "		and edugubun = '" + v_edugubun + "' \n";				
			}
			
			//ȸ��
			if (!v_company.equals("ALL")) {
				body_sql+= "		and substring(inusercomp,1,4) = '" + StringManager.substring(v_company, 0, 4) + "' \n";				
			}
			
			//�����ְ���
			if (!v_edudept.equals("ALL")) {
				body_sql+= "		and inusercomp = '" + v_edudept + "' \n";				
			}
			
			//������
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
            
            ls.setPageSize(row);                       //�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); 	//������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage();    		//��ü ������ ���� ��ȯ�Ѵ�
            int totalrowcount = ls.getTotalCount();    		//��ü row���� ��ȯ�Ѵ�

			double v_execpercent = 0; //������
			int v_totplancost = 0;
			int v_totexeccost = 0; 
			
			double v_mempercent = 0; //������
			int v_totplanmemcnt = 0;
			int v_totexecmemcnt = 0; 
			
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("subj"		   		,ls.getString("subj"));			//�����ڵ�
                dbox.put("subjnm"	   		,ls.getString("subjnm"));		//������
                dbox.put("edugubun"	   		,ls.getString("edugubun"));		//���������ڵ�
                dbox.put("d_edugubunnm"		,ls.getString("edugubunnm"));	//����������Ī
                dbox.put("edustarta"   		,ls.getString("edustarta"));	//����������(��ȹ)
                dbox.put("eduenda"	   		,ls.getString("eduenda"));		//����������(����)
                //dbox.put("d_totplanmemcnt"	,new Integer(ls.getInt("totplanmemcnt")));//�� �ο�(��ȹ)
                //dbox.put("d_totexecmemcnt"	,new Integer(ls.getInt("totexecmemcnt")));//�� �ο�(����)
                v_totplancost = ls.getInt("totplancost");
                v_totexeccost = ls.getInt("totexeccost");
                dbox.put("d_totplancost"	,new Integer(v_totplancost));  //�� ���(��ȹ)
                dbox.put("d_totexeccost"	,new Integer(v_totexeccost));  //�� ���(����)
                dbox.put("d_totgrade"		,new Integer(ls.getInt("totgrade")));		//��ü ������
                
                v_totplanmemcnt = ls.getInt("totplanmemcnt");
                v_totexecmemcnt = ls.getInt("totexecmemcnt");
                dbox.put("d_totplanmemcnt"	,new Integer(v_totplanmemcnt));  //�� ���(��ȹ)
                dbox.put("d_totexecmemcnt"	,new Integer(v_totexecmemcnt));  //�� ���(����)
                
                //System.out.println("v_totplancost =" + v_totplancost);
                //System.out.println("v_totexeccost =" + v_totexeccost);
                //System.out.println("v_totplanmemcnt =" + v_totplanmemcnt);
                //System.out.println("v_totexecmemcnt=" + v_totexecmemcnt);                
                
                //�����׸� ��ȹ/���� ���
                //dbox.put("d_")
                
                //������ ��� : �� ������� / �� ��ȹ��� * 100
                if (v_totplancost==0) {
                	v_execpercent = 0;
                }
                else {
                	v_execpercent = (double)v_totexeccost / (double)v_totplancost * 100;
                }
                dbox.put("d_execpercent"	,new Double(v_execpercent));                
                
                //������ ��� : �� �����ο� / �� ��ȹ�ο� * 100
                if (v_totplanmemcnt==0) {
                	v_mempercent = 0;
                }
                else {
                	v_mempercent = (double)v_totexecmemcnt / (double)v_totplanmemcnt * 100;
                }
                dbox.put("d_mempercent"	,new Double(v_mempercent));
                dbox.put("d_lessoncnt"	,new Integer(ls.getInt("lessoncnt")));	//���α׷� ����

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
    �����Է���Ȳ ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList selectSubjInputList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        DataBox 	dbox  			= null;
//		2005.11.15_�ϰ��� : TotalCount ���� ���� ���� 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";

        String 		v_searchtype    = box.getStringDefault("p_searchtype","S"); //S:������ ����, E:�����ϱ���
        String 		v_searchgubun   = box.getStringDefault("p_searchgubun","Y");//Y:�⵵�����˻�, T:�Ⱓ�˻�
        String 		v_edustartday   = box.getStringDefault("p_edustartday",""); //�˻�������
        String 		v_eduendday   	= box.getStringDefault("p_eduendday","");   //�˻�������
        
        String 		v_year         	= box.getStringDefault("s_year","");        //�⵵
        String 		v_edugubun      = box.getStringDefault("s_edugubun","ALL"); //��������
        String 		v_company   	= box.getStringDefault("s_company","ALL");  //ȸ��
        String 		v_edudept   	= box.getStringDefault("s_edudept","ALL");  //�����ְ���
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");  //������
        
        String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");           		//������ ����
		
        int		v_pageno 		= box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            head_sql = "select 	distinct a.inusercomp,";
			//sql+= "	   		get_compnm(a.inusercomp,2,4) inusercompnm,";
			head_sql+="			get_compnm( inusercomp, 2,4  ) ||  \n";
			// 2005.11.16_�ϰ��� : Oracle -> Mssql
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
			
			//�⵵���� �˻����� ���ý�
			if (v_searchgubun.equals("Y")) {
				//�⵵
				if (!v_year.equals("")) {
					body_sql+= "		and substring(edustarta,1,4) = '" + v_year + "' ";
				}
			} else {
				//�Ⱓ�˻� �˻����� ���ý�
				
				if (v_searchtype.equals("S")) {
					//������ ����
					body_sql+="		and edustarta between '" + v_edustartday + "' and '" + v_eduendday + "' ";
				} else {
					//������ ����
					body_sql+="		and eduenda between '" + v_edustartday + "' and '" + v_eduendday + "' ";
				}
			}
			
			//��������
			if (!v_edugubun.equals("ALL")) {
				body_sql+= "		and edugubun = '" + v_edugubun + "' ";				
			}
			
			//ȸ��
			if (!v_company.equals("ALL")) {
				body_sql+= "		and substring(inusercomp, 1, 4) = '" + StringManager.substring(v_company, 0, 4) + "' ";				
			}
			
			//�����ְ���
			if (!v_edudept.equals("ALL")) {
				body_sql+= "		and inusercomp = '" + v_edudept + "' ";				
			}
			
			//������
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

            ls.setPageSize(row);                       //�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage();    //��ü ������ ���� ��ȯ�Ѵ�
            int totalrowcount = ls.getTotalCount();    //��ü row���� ��ȯ�Ѵ�

			double v_execpercent = 0; //������
			double v_mempercent = 0; //������
			int v_totplancost = 0;
			int v_totexeccost = 0;
			
			int v_totplanmemcnt = 0;
			int v_totexecmemcnt = 0;
			
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("inusercomp"			,ls.getString("inusercomp"));   	//�μ��ڵ�
                dbox.put("d_inusercompnm"		,ls.getString("inusercompnm"));   	//�μ���
                dbox.put("d_totsubjcnt"			,ls.getString("totsubjcnt"));   	//������ϼ�(��ȹ ������)
                dbox.put("d_totsubjinputcnt"	,ls.getString("totsubjinputcnt"));  //�����Է� ������
                dbox.put("d_totsubjfilecnt"		,ls.getString("totsubjfilecnt"));  	//������� ������
                
                v_totplancost = ls.getInt("totplancost");
                v_totexeccost = ls.getInt("totexeccost");
                dbox.put("d_totplancost"	,new Integer(v_totplancost));  //�� ���(��ȹ)
                dbox.put("d_totexeccost"	,new Integer(v_totexeccost));  //�� ���(����)
                //������ ��� : �� ������� / �� ��ȹ��� * 100
                if (v_totexeccost==0) {
                	v_execpercent = 0;
                }
                else {
                	v_execpercent = (double)v_totexeccost / (double)v_totplancost * 100;
                }
                dbox.put("d_execpercent"	,new Double(v_execpercent));
                
                //������
                v_totplanmemcnt = ls.getInt("totplanmemcnt");
                v_totexecmemcnt = ls.getInt("totexecmemcnt");
                dbox.put("d_totplanmemcnt"	,new Integer(v_totplanmemcnt));  //��ȹ �ο�
                dbox.put("d_totexecmemcnt"	,new Integer(v_totexecmemcnt));  //���� �ο�
                //������ ��� : �� ������� / �� ��ȹ��� * 100
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
    �����Է���Ȳ �� ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList selectSubjInputStatusList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        DataBox 	dbox  			= null;
//		2005.11.15_�ϰ��� : TotalCount ���� ���� ���� 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";

        String 		v_searchtype    = box.getStringDefault("p_searchtype","S"); //S:������ ����, E:�����ϱ���
        String 		v_searchgubun   = box.getStringDefault("p_searchgubun","Y");//Y:�⵵�����˻�, T:�Ⱓ�˻�
        String 		v_edustartday   = box.getStringDefault("p_edustartday",""); //�˻�������
        String 		v_eduendday   	= box.getStringDefault("p_eduendday","");   //�˻�������
        
        String 		v_year         	= box.getStringDefault("s_year","");        //�⵵
        String 		v_edugubun      = box.getStringDefault("s_edugubun","ALL"); //��������
        String 		v_company   	= box.getStringDefault("s_company","ALL");  //ȸ��
        String 		v_edudept   	= box.getStringDefault("s_edudept","ALL");  //�����ְ���
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");  //������
        
        String		v_inusercomp	= box.getString("p_inusercomp");			//�Է��� �μ��ڵ�
        
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
			// 2005.11.16_�ϰ��� : Oracle -> Mssql
			//sql+= "			decode(fdate,null,'�̿Ϸ�','','�̿Ϸ�','�Ϸ�') fdatedesc,";
			//sql+= "			decode(realfile,null,'�̿Ϸ�','','�̿Ϸ�','�Ϸ�') realfiledesc,";
			head_sql+= "			case fdate When null Then '�̿Ϸ�' When '' Then '�̿Ϸ�' Else '�Ϸ�' end as fdatedesc,";
			head_sql+= "			case realfile When null Then '�̿Ϸ�' When '' Then '�̿Ϸ�' Else '�Ϸ�' end as realfiledesc,";
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

            ls.setPageSize(row);                       //�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);               //������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage();    //��ü ������ ���� ��ȯ�Ѵ�
            //int totalrowcount = ls.getTotalCount();    //��ü row���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("subj"		   		,ls.getString("subj"));			//�����ڵ�
                dbox.put("subjnm"	   		,ls.getString("subjnm"));		//������
                dbox.put("edugubun"	   		,ls.getString("edugubun"));		//���������ڵ�
                dbox.put("d_edugubunnm"		,ls.getString("edububunnm"));	//����������Ī
                dbox.put("edustarta"   		,ls.getString("edustarta"));	//����������(��ȹ)
                dbox.put("eduenda"	   		,ls.getString("eduenda"));		//����������(����)
                dbox.put("subjgubun"	 	,ls.getString("subjgubun"));	//��������(A:LMS�Է°���,B:����/��������ý��� �Է� ����)
                dbox.put("inusercomp"		,ls.getString("inusercomp"));   //�μ��ڵ�
                dbox.put("d_inusercompnm"	,ls.getString("inusercompnm")); //�μ���
                dbox.put("d_fdatedesc"		,ls.getString("fdatedesc")); 	//���� �Է¿���
                dbox.put("d_realfiledesc"	,ls.getString("realfiledesc")); //������� �Է¿���
                dbox.put("d_realfile"		,ls.getString("realfile"));
                dbox.put("d_savefile"		,ls.getString("savefile"));
                dbox.put("d_email"			,ls.getString("email")); 		//����� EMail
                dbox.put("inuserid"			,ls.getString("inuserid")); 	//�����ID
                dbox.put("d_inusernm"		,ls.getString("inusernm")); 	//������̸�
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
    ������Ȳ ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList selecttutorStatusList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        DataBox 	dbox  			= null;
//		2005.11.15_�ϰ��� : TotalCount ���� ���� ���� 
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

        String 		v_tutorgubun    = box.getStringDefault("s_tutorgubun","ALL"); //���籸��(I:�系���� O:��ܰ��� G:�׷�簭��)
        String 		v_srchsubjnm   	= box.getStringDefault("p_srchsubjnm","");  //������
        String 		v_tutorname   	= box.getStringDefault("p_tutorname","");  //�����
        String 		v_subjclass   	= box.getStringDefault("p_subjclass","");  //�����
        
        String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");           		//������ ����

        
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
			// 2005.11.16_�ϰ��� : Oracle -> mssql
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
			// 2005.11.16_�ϰ��� : Oracle -> Mssql
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

			//���籸��
			if (!v_tutorgubun.equals("ALL")) {
				body_sql2+= "		and tutorgubun = '" + v_tutorgubun + "' ";				
			}
			
			//�����
			if (!v_tutorname.equals("")) {
				body_sql2+= "		and name like '%" + v_tutorname + "%' ";				
			}
			
			//��������
			if (!v_subjclass.equals("")) {
				body_sql2+= "		and subjclass = '" + v_subjclass + "' ";
			}
			
			//�������
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

            ls.setPageSize(row);                       //�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);               //������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage();    //��ü ������ ���� ��ȯ�Ѵ�
            //int totalrowcount = ls.getTotalCount();    //��ü row���� ��ȯ�Ѵ�

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
    �系����/��ܰ���/������� �˻�
    @param box      receive from the form object and session
    @return ArrayList
    */
    public ArrayList searchTutor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet 	ls      		= null;
        ArrayList 	list  			= null;
        String 		sql      		= "";
        DataBox 	dbox  			= null;

        String 		v_tutorgubun    = box.getStringDefault("p_tutorgubun","I");   //�˻��׸�(I-�系����,O-��ܰ���,C-�������)
        String 		v_searchtext    = box.getStringDefault("p_searchtext","");   //�˻���
        
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            
            if (v_tutorgubun.equals("I") || v_tutorgubun.equals("O")) {
            	//�系/��ܰ��� �˻�
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
            	//������� �˻�
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
    �����ڵ� �˻�
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
    �˻� �⵵ Select Box ����
    @param  box, isChange, isALL     receive from the form object and session
    @return String
    */
    public static String getYear(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        String sql = "";
        String result = "�⵵ ";
        
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
    �˻� �μ� Select Box ����
    @param  box, isChange, isALL     receive from the form object and session
    @return String
    */
    public static String getEduDept(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        String sql = "";
        String result = "�����ְ��� ";
        
        try {
            String v_year 	 = box.getStringDefault("s_year","");
            String v_company = box.getStringDefault("s_company","ALL");
            String v_edudept = box.getStringDefault("s_edudept","ALL");
            
            //String v_gadmin = StringManager.substring(s_gadmin, 0, 1);

            connMgr = new DBConnectionManager();

            sql = "select 	distinct inusercomp,";
			sql+= "		 	replace(get_compnm(inusercomp, 4, 4),'/','') ";
			//2005.11.16_�ϰ��� : Oracle -> Mssql
			//sql+= "			|| decode(substr(inusercomp,1,4),'0101','(HMC)','0102','(KMC)','' ) deptnm ";
			sql+= "			+ case substring(inusercomp,1,4) When '0101' Then '(HMC)' When '0102' Then '(KMC)' Else '' End as deptnm ";
			sql+= "from	 	tz_tsubj ";
			
			if (v_year.equals("")) {
            	//�⵵�� ������ ���� �ֱ��� �⵵�� ������(���� ȭ�� ���Խ�)
				sql+= "where	 substring(edustarta,1,4) = (select max(SUBSTR(edustarta,1,4)) from tz_tsubj)  ";
			} else {
				sql+= "where	substring(edustarta,1,4) = '" + v_year + "'  ";
			}
			
			//ȸ��
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
    �Է��� ������ �ߺ����� ��ȸ
    @param v_userid      ���� ���̵�
    @return v_result    1:�ߺ��� ,0:�ߺ����� ����
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
            //�ߺ��� ��� 1�� return�Ѵ�
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
    ���翡 �ش��ϴ� ���ǰ������
    @param box      receive from the form object and session
    @return int
    */
     public int insertTutorSubj(DBConnectionManager connMgr,RequestBox box) throws Exception {
        String sql                  = "";
        String v_user_id            = box.getSession("userid");
        String v_userid              = box.getString("p_userid");
        //p_subj�� �Ѿ�� �ټ��� value�� ó���ϱ� ���� vector�� ����
        Vector v_subj               = new Vector();
        v_subj                      = box.getVector("p_subj");
        Enumeration em              = v_subj.elements();
        String v_eachSubj           = "";   //���� �Ѿ�� ������ �����ڵ�
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
    ��ڱ��� ���̺� ���
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
    ���翡 �ش��ϴ� ���ǰ�������
    @param box      receive from the form object and session
    @return int
    */
     public int updateTutorSubj(DBConnectionManager connMgr,RequestBox box) throws Exception {
        String sql1                 = "";
        String sql2                 = "";
        String v_user_id            = box.getSession("userid");
        String v_userid             = box.getString("p_userid");
        //p_subj�� �Ѿ�� �ټ��� value�� ó���ϱ� ���� vector�� ����
        Vector v_subj               = new Vector();
        v_subj                      = box.getVector("p_subj");
        Enumeration em              = v_subj.elements();
        String v_eachSubj           = "";   //���� �Ѿ�� ������ �����ڵ�
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
    ��ڱ��� ���̺� ����
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
    �����׷� select box
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
          result += " <option value=''>=== ��ü ===</option> \n";
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
    ������ȸ
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

            ls.setPageSize(10);                         //�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno);                //������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage();   //��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount();   //��ü row ���� ��ȯ�Ѵ�
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
   * �系 ���� ��ȸ
    @param box      receive from the form object and session
    @return ArrayList
   */
    public ArrayList selectSearchTutor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        ListSet ls = null;
        ArrayList list = null;
//		2005.11.15_�ϰ��� : TotalCount ���� ���� ���� 
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
            if ( !v_searchtext.equals("")) {                            //    �˻�� ������
                if (v_search.equals("id")) {                            //    ID�� �˻��Ҷ�
					body_sql += " and a.userid like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("cono")) {                   //    ������� �˻��Ҷ�
					body_sql += " and a.cono like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("name")) {                   //    �̸����� �˻��Ҷ�
					body_sql += " and a.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            order_sql += " order by a.comp asc, a.jikwi asc ";
//            System.out.println("sql==============>"+sql);
			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(10);                         //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);                //     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage();   //     ��ü ������ ���� ��ȯ�Ѵ�
            //int total_row_count = ls.getTotalCount();   //     ��ü row ���� ��ȯ�Ѵ�

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
    ���� �̷� ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectTutorHistoryList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls      = null;
        ArrayList list  = null;
        String sql      = "";
        DataBox dbox  = null;

        String v_select         = box.getString("p_select");        //�˻��׸�(������1,�����2)
        String v_selectvalue    = box.getString("p_selectvalue");   //�˻���
        String v_gyear          = box.getString("p_gyear");         //�⵵
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            //select userid,name,comp,dept,handphone,email,isGubun,subj,subjnm,lecture,sdesc,lectlevel
            sql = "select A.userid,A.name,A.comp,A.dept,A.handphone,A.email,A.isGubun,B.subj,B.subjnm, ";
            sql+= "	C.lecture,C.sdesc,C.lectlevel ";
            sql+= "from TZ_TUTOR A,TZ_SUBJ B,TZ_OFFSUBJLECTURE C ";
            sql+= "where A.userid = C.tutorid and B.subj = C.subj  ";
/*
            if ( !v_search.equals("")) {            //�����о߰� �ִ� ���
                sql += "and A.professional like '%"+v_search+"%' ";
            }
*/
            if(v_select.equals("1")) {              //�˻��׸��� �������ΰ��
                sql += "and B.subjnm like '%"+v_selectvalue+"%' ";
            }else if (v_select.equals("2")) {       //�˻��׸��� ������ΰ��
                sql += "and A.name like '%"+v_selectvalue+"%' ";
            }
            if ( !v_gyear.equals("")) {             //�⵵�� �ִ� ���
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
    ���� �̷��� ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectTutorLectureList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls      = null;
        ArrayList list  = null;
        String sql      = "";
        DataBox	dbox	= null;
        String v_userid         = box.getString("p_userid");        //������̵�
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
    ���� �� ����
    @param box      receive from the form object and session
    @return int
    */
     public int updateTutorScore(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1         = "";
        int isOk            = 0;
        String v_user_id    = box.getSession("userid");
        String v_userid     = box.getString("p_userid");        //������̵�
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
    �Է�������
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