//**********************************************************
//1. ��      ��: �������� ��
//2. ���α׷���: CPSulmunReportBean.java
//3. ��      ��: ������������ ���α׷�
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: ��â�� 2005. 8. 21
//7. ��      ��: �̳��� 05.11.16 _ Oracle -> MSSQL (OuterJoin) ����
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

public class CPSulmunReportBean {
    private ConfigSet config;
    private int row;
	    	
    public CPSulmunReportBean() {
        try{
            config = new ConfigSet();  
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }	
    }

    /**
    ���־�ü ��������Ʈ
    @param	box			receive from the form object and session
    @return	ArrayList	���־�ü ��������Ʈ
    */
    public String getReportPaperList(String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_upperclass, int p_sulpapernum) throws Exception {
        String sql = "";
			sql = "select a.grcode,       a.subj,    a.subjseq,     ";
            sql+= "       a.sulpapernum,  a.sulpapernm, a.year, a.subjseq, ";
            sql+= "       a.totcnt,       a.sulnums, a.sulmailing, a.sulstart, a.sulend, ";
            sql+= "       'CP'      subjnm, ";
            sql+= "       (select count(*) from tz_sulpapercp x where ";
            sql+= "         x.subj = a.subj and x.grcode = a.grcode ";
            sql+= "         and x.subjseq = a.subjseq and x.year = a.year ";
            sql+= "         and x.sulpapernum = a.sulpapernum ";
            sql+= "         ) cpcnt";
            sql+= "  from tz_sulpaper a";
            sql+= " where a.grcode = " + SQLString.Format(p_grcode);
            sql+= "   and a.subj   = " + SQLString.Format(p_subj);
            if (p_sulpapernum > 0) {
                sql+= "   and sulpapernum   = " + p_sulpapernum;
            }
            sql+= " order by subj, sulpapernum ";
		return sql;
    }


    /**
    ��������Ÿ ��ȸ
    @param box          receive from the form object and session
    @return DataBox   ��������Ÿ
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
			// ������ : 05.11.16 ������ : �̳��� _(+)  ����
//          sql+=  "  where a.cuserid  = b.userid(+)                                                      ";
//          sql+=  "    and a.muserid  = c.userid(+)                                                      ";
//          sql+=  "    and a.producer = d.comp(+)                                                        ";
//          sql+=  "    and a.owner    = e.comp(+)                                                        ";
			sql+=  "  where a.cuserid   =  b.userid(+)                                                      ";
	        sql+=  "    and a.muserid   =  c.userid(+)                                                     ";
	        sql+=  "    and a.producer  =  d.comp(+)                                                        ";
	        sql+=  "    and a.owner     =  e.comp(+)                                                        ";
            sql+=  "    and a.subj     =  " + SQLString.Format(v_subj);

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
				//data.setGradexam    (ls.getInt("gradexam"));//�̼�����-�߰���
				//data.setGradftest   (ls.getInt("gradftest"));//�̼�����-������
				//data.setGradhtest   (ls.getInt("gradhtest"));//�̼�����-������
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
    ���õ� �������� ���� - ���̹�
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
            connMgr.setAutoCommit(false);

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
			sql+= "		   crdate		= ?, ";
			sql+= "        introducefilenamereal   = ?, ";
            sql+= "        introducefilenamenew    = ?, ";
            sql+= "        informationfilenamereal = ?, ";
            sql+= "        informationfilenamenew  = ?, ";
            sql+= "        usebook = ? ";
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
            pstmt.setInt   (21, box.getInt("p_gradexam")); 					//�̼�����(�߰���)
            pstmt.setInt   (22, box.getInt("p_gradreport")); 				//�̼�����(����Ʈ)
            pstmt.setInt   (23, box.getInt("p_whtest")); 					//����ġ(������)

            pstmt.setString(24, box.getString("p_gradftest"));				//�̼�����-������
            pstmt.setString(25, box.getString("p_gradhtest"));				//�̼�����-������
			//------------------------------------------------------------------------------//
			
			pstmt.setInt(26, box.getInt("p_edutimes"));				//�н��ð�
			pstmt.setInt(27, box.getInt("p_biyong"));				
			pstmt.setInt(28, box.getInt("p_bookprice"));
			pstmt.setString(29, box.getString("p_crdate"));
			pstmt.setString(30, v_introducefilenamereal);          //�����Ұ� �̹���
            pstmt.setString(31, v_introducefilenamenew);           //�����Ұ� �̹���
            pstmt.setString(32, v_informationfilenamereal);        //����(����)
            pstmt.setString(33, v_informationfilenamenew);         //����(����)
            pstmt.setString(34, box.getString("p_usebook"));         //��������
            pstmt.setString(35, box.getString("p_subj"));

            isOk = pstmt.executeUpdate();
            
            if(isOk > 0){
            	isOk = UpdateCpParam(connMgr, box);
            }
            
            if(isOk > 0){
            	connMgr.commit();
            }else{
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
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
    

    /**
    ���ο� �����ڵ� ��� - ���̹�
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
		String v_grcode	  = box.getStringDefault("s_grcode","ALL");	//��������Ʈ������ SELECTBOX �����ְ� �ڵ�

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

            pstmt.setString( 1, v_subj);																		//�����ڵ�
            pstmt.setString( 2, box.getString("p_subjnm"));									//������
            pstmt.setString( 3, box.getString("p_isonoff"));								//���̹�/���ձ���
            pstmt.setString( 4, box.getString("s_upperclass") + box.getString("s_middleclass") + "000"); //�з��ڵ�
            pstmt.setString( 5, box.getString("s_upperclass"));								//��з�
            pstmt.setString( 6, box.getString("s_middleclass")); 							//�ߺз�
            pstmt.setString( 7, box.getStringDefault("s_lowerclass","000")); 				//�Һз�
            pstmt.setString( 8, box.getString("p_specials"));								//����Ư��(YYY)
            pstmt.setString( 9, box.getString("p_muserid")); 								//���� ID
            pstmt.setString(10, box.getString("p_cuserid"));								//��������� ID
            pstmt.setString(11, box.getString("p_isuse"));									//��뿩��(Y/N)
            pstmt.setString(12, box.getString("p_isgoyong"));								//��뺸�迩��(Y/N)
            pstmt.setString(13, box.getStringDefault("p_ispropose","Y"));	 	//[X]������û����(Y/N)
            pstmt.setInt   (14, box.getInt   ("p_biyong"));             		//������
            pstmt.setInt   (15, box.getInt   ("p_edudays"));            		//�н�����
            pstmt.setInt   (16, box.getInt   ("p_studentlimit"));       		//����
            pstmt.setString(17, box.getString("p_usebook"));            		//�����뿩��
            pstmt.setInt   (18, box.getInt   ("p_bookprice"));          		//�����
            pstmt.setString(19, box.getString("p_owner"));              		//����������
            pstmt.setString(20, box.getString("p_producer"));           		//����������
            pstmt.setString(21, box.getString("p_crdate"));					    		//��������
            pstmt.setString(22, box.getString("p_language"));           		//[X]����
            pstmt.setString(23, box.getString("p_server"));             		//[X]����
            pstmt.setString(24, box.getString("p_dir"));                		//���������
            pstmt.setString(25, box.getString("p_eduurl"));             		//����URL
            pstmt.setString(26, box.getString("p_vodurl"));             		//VOD���
            pstmt.setString(27, box.getString("p_preurl"));      						//������URL
            pstmt.setInt   (28, box.getInt   ("p_ratewbt"));     						//�н����(WBT%)
            pstmt.setInt   (29, box.getInt   ("p_ratevod"));								//�н����(VOD%)
            pstmt.setInt   (30, box.getInt   ("p_framecnt"));								//�������Ӽ�
            pstmt.setString(31, box.getString("p_env"));										//�н�ȯ��
            pstmt.setString(32, box.getString("p_tutor"));									//���缳��
            pstmt.setString(33, box.getString("p_bookname"));       				//�����
            pstmt.setString(34, box.getString("p_sdesc"));     							//[X]���
            pstmt.setInt   (35, box.getInt   ("p_warndays"));								//[X]�н����������
            pstmt.setInt   (36, box.getInt   ("p_stopdays"));								//[X]�н�����������
            pstmt.setInt   (37, box.getInt   ("p_point"));									//�̼�����
            pstmt.setInt   (38, box.getInt   ("p_edulimit"));								//1���ִ��н���
            pstmt.setInt   (39, box.getInt   ("p_gradscore"));							//�̼�����-����
            pstmt.setInt   (40, box.getInt   ("p_gradstep"));								//�̼�����-������
            pstmt.setDouble(41, box.getDouble("p_wstep"));									//����ġ-������
            pstmt.setDouble(42, box.getDouble("p_wmtest"));									//����ġ-�߰���
            pstmt.setDouble(43, box.getDouble("p_wftest"));									//����ġ-������
            pstmt.setDouble(44, box.getDouble("p_wreport"));								//����ġ-����Ʈ
            pstmt.setDouble(45, box.getDouble("p_wact"));										//����ġ-��Ƽ��Ƽ
            pstmt.setDouble(46, box.getDouble("p_wetc1"));									//����ġ-��Ÿ1
            pstmt.setDouble(47, box.getDouble("p_wetc2"));									//����ġ-��Ÿ2
            pstmt.setInt   (48, box.getInt("p_goyongprice"));								//��뺸��ȯ�ޱݾ�
            pstmt.setString(49, v_luserid);																	//������
            pstmt.setString(50, FormatDate.getDate("yyyyMMddHHmmss"));			//������
            pstmt.setString(51, v_luserid);																	//����������
            pstmt.setString(52, FormatDate.getDate("yyyyMMddHHmmss"));			//����������(ldate)
            pstmt.setString(53, box.getString("p_proposetype"));						//������û����(TZ_CODE GUBUN='0019')
            pstmt.setString(54, box.getStringDefault("p_edumans",""));		//������ �������
            pstmt.setString(55, box.getStringDefault("p_intro",""));				//������ ��������
            pstmt.setString(56, box.getStringDefault("p_explain",""));			//������ ��������
            pstmt.setString(57, box.getStringDefault("p_isessential","D"));	//[X]��������(�ǹ�-D,�ʼ�-S,����(������)-W,����(������)-T)
            pstmt.setInt	 (58, box.getInt("p_score"));											//��������
            pstmt.setString(59, box.getString("p_contenttype"));						//������Ÿ��
            
            //------------------------------------------------------------------------------//
            pstmt.setString(60, box.getString("p_gradexam")); 							//�̼�����(��)
            pstmt.setString(61, box.getString("p_gradreport")); 						//�̼�����(����Ʈ)
            pstmt.setString(62, box.getString("p_whtest")); 								//����ġ(������)
            pstmt.setString(63, box.getString("p_usesubjseqapproval")); 		//���������ְ�����(Y/N)
            pstmt.setString(64, box.getString("p_useproposeapproval")); 		//������û��������(Y/N)
            pstmt.setString(65, box.getString("p_usemanagerapproval")); 		//�ְ������������(Y/N)
            pstmt.setString(66, box.getString("p_rndcreditreq")); 					//��������(��������)-�ʼ�
            pstmt.setString(67, box.getString("p_rndcreditchoice")); 				//��������(��������)-����
            pstmt.setString(68, box.getString("p_rndcreditadd")); 					//��������(��������)-��������
            pstmt.setString(69, box.getString("p_rndcreditdeduct")); 				//��������(��������)-��������
            pstmt.setString(70, box.getString("p_isoutsourcing")); 					//��Ź��������
            pstmt.setString(71, box.getString("p_isablereview")); 					//�������ɿ���
            pstmt.setString(72, box.getStringDefault("p_rndjijung","N"));			//R&D ������������
            pstmt.setString(73, box.getString("p_cpapproval"));			
            
            isOk = pstmt.executeUpdate();
			
			box.put("s_upperclass",box.getString("p_upperclass"));
			box.put("s_middleclass",box.getString("p_middleclass"));

			//�����ְ��� �����ϰ� �߰��ϴ� ���� �ش� �����ְ��� ���� TZ_PREVIEW�� INSERT�Ѵ�.			
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
    �����ڵ� max �� ���
    @param	box		receive from the form object and session
    @return	String	�����ڵ�
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
			//�����ڵ� =  ��з�(3) + �ߺз�(3) + �Ϸù�ȣ(4)
			
			// ������ : 05.11.16 ������ : �̳��� _ substr ����
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
    ���������� ���
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
    cp��ü ����������� UPDate
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
            sql+= "  where subj         = ? ";

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
            pstmt.setInt   (21, box.getInt("p_gradexam")); 					//�̼�����(�߰���)
            pstmt.setInt   (22, box.getInt("p_gradreport")); 				//�̼�����(����Ʈ)
            pstmt.setInt   (23, box.getInt("p_whtest")); 					//����ġ(������)

            pstmt.setString(24, box.getString("p_gradftest"));				//�̼�����-������
            pstmt.setString(25, box.getString("p_gradhtest"));				//�̼�����-������
			//------------------------------------------------------------------------------//
			
			pstmt.setInt(26, box.getInt("p_edutimes"));				//�н��ð�
			pstmt.setInt(27, box.getInt("p_musertel"));				
			pstmt.setString(28, box.getString("p_cpapproval"));
            pstmt.setString(29, box.getString("p_subj"));
			
            isOk = pstmt.executeUpdate();       
            if(isOk > 0){
            	isOk = UpdateCpParam(connMgr, box);
            }          
            if(isOk > 0){
            	connMgr.commit();
            }else{
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
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
    
    /**
    cp��ü ����������� UPDate1
    @param box      receive from the form object and session
    @return int    1:insert success,0:insert fail
    */
     public int UpdateCpApproval1(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            //update TZ_SUBJ table
            sql = " update TZ_SUBJ ";
            sql+= "    set  ";
			sql+= "		   cpapproval   = ?  ";
            sql+= "  where subj         = ? ";

            pstmt = connMgr.prepareStatement(sql);			
			pstmt.setString(1, box.getString("p_cpapproval"));
            pstmt.setString(2, box.getString("p_subj"));

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
    �����Ķ���� ��ȸ
    @param box          receive from the form object and session
    @return DataBox     parameter data
    */
    
    public DataBox SelectCpParamData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
        DataBox  dbox = null;
        String v_subj = box.getString("p_subj");
    
        try {
        	connMgr = new DBConnectionManager();
        	
            sql =  " select ";
            sql+=  "     useridparam,         \n";
            sql+=  "     nameparam,           \n";
            sql+=  "     resnoparam,          \n";
            sql+=  "     conoparam,           \n";
            sql+=  "     pwdparam,            \n";
            sql+=  "     deptnmparam,         \n";
            sql+=  "     jikwiparam,          \n";
            sql+=  "     jikwinmparam,        \n";
            sql+=  "     compparam,           \n";
            sql+=  "     companynmparam,      \n";
            sql+=  "     subjparam,           \n";
            sql+=  "     subjseqparam,        \n";
            sql+=  "     gadminparam,         \n";
            sql+=  "     param1,              \n";
            sql+=  "     paramvalue1,         \n";
			sql+=  "	 param2,              \n";
			sql+=  "	 paramvalue2,   	  \n";
			sql+=  "	 param3,        	  \n";
			sql+=  "     paramvalue3,         \n";  
			sql+=  "     param4,              \n";
			sql+=  "     paramvalue4,         \n";
			sql+=  "     param5,              \n";
			sql+=  "     paramvalue5,         \n";
			sql+=  "     param6,              \n";
			sql+=  "     paramvalue6          \n";
            sql+=  "   from                   \n";
            sql+=  "     TZ_CPPARAM           \n";
            sql+=  "   where                  \n";
            sql+=  "     subj     =  " + SQLString.Format(v_subj);

			ls = connMgr.executeQuery(sql);
            while (ls.next()) {
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
    ���� parameter ����
    @param box      receive from the form object and session
    @param connMgr  DB Connection Manager
    @return int    1:insert success,0:insert fail
    */
     public int UpdateCpParam(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk  = 0;
        int isOk1 = 0;
        int isOk2 = 0;
        String v_luserid = box.getSession("userid");
        String v_subj    = box.getString("p_subj");

        try {
            sql1 = "delete from tz_cpparam where subj = ?";
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setString(1, v_subj);
            isOk1 = pstmt1.executeUpdate();

            //if(isOk1 > 0){
              sql2+= " insert into                                                                \n";
              sql2+= " tz_cpparam(                                                                \n";
              sql2+= "   subj,              useridparam,                                          \n";
              sql2+= "   nameparam,         resnoparam,        conoparam,           pwdparam,     \n";
              sql2+= "   deptnmparam,       jikwiparam,        jikwinmparam,        compparam,    \n";
              sql2+= "   companynmparam,    subjparam,         subjseqparam,        gadminparam,  \n";
              sql2+= "   param1,            paramvalue1,       param2,              paramvalue2,  \n";
              sql2+= "   param3,            paramvalue3,       param4,              paramvalue4,  \n";
              sql2+= "   param5,            paramvalue5,       param6,              paramvalue6,  \n";
              sql2+= "   luserid,           ldate                                                 \n";
              sql2+= " )                                                                          \n";
              sql2+= " values(                                                                    \n";
              sql2+= "   ?,              ?,              ?,              ?,                       \n";
              sql2+= "   ?,              ?,              ?,              ?,                       \n";
              sql2+= "   ?,              ?,              ?,              ?,                       \n";
              sql2+= "   ?,              ?,              ?,              ?,                       \n";
              sql2+= "   ?,              ?,              ?,              ?,                       \n";
              sql2+= "   ?,              ?,              ?,              ?,                       \n";
              sql2+= "   ?,              ?,              ?,              to_char(sysdate, 'YYYYMMDDHH24MISS') \n";
              sql2+= " )                                                                          \n";

              pstmt2 = connMgr.prepareStatement(sql2);			
			  pstmt2.setString(1,  v_subj);
              pstmt2.setString(2,  box.getString("p_useridparam"));   
              pstmt2.setString(3,  box.getString("p_nameparam"));        
              pstmt2.setString(4,  box.getString("p_resnoparam"));    
              pstmt2.setString(5,  box.getString("p_conoparam"));     
              pstmt2.setString(6,  box.getString("p_pwdparam"));      
              pstmt2.setString(7,  box.getString("p_deptnmparam"));   
              pstmt2.setString(8,  box.getString("p_jikwiparam"));    
              pstmt2.setString(9,  box.getString("p_jikwinmparam"));  
              pstmt2.setString(10, box.getString("p_compparam"));     
              pstmt2.setString(11, box.getString("p_companynmparam"));
              pstmt2.setString(12, box.getString("p_subjparam"));     
              pstmt2.setString(13, box.getString("p_subjseqparam"));  
              pstmt2.setString(14, box.getString("p_gadminparam"));   
              pstmt2.setString(15, box.getString("p_param1"));        
              pstmt2.setString(16, box.getString("p_paramvalue1"));   
              pstmt2.setString(17, box.getString("p_param2"));        
              pstmt2.setString(18, box.getString("p_paramvalue2"));   
              pstmt2.setString(19, box.getString("p_param3"));        
              pstmt2.setString(20, box.getString("p_paramvalue3"));   
              pstmt2.setString(21, box.getString("p_param4"));        
              pstmt2.setString(22, box.getString("p_paramvalue4"));   
              pstmt2.setString(23, box.getString("p_param5"));        
              pstmt2.setString(24, box.getString("p_paramvalue5"));   
              pstmt2.setString(25, box.getString("p_param6"));        
              pstmt2.setString(26, box.getString("p_paramvalue6"));   
              pstmt2.setString(27, box.getSession("userid"));         
              
              isOk2 = pstmt2.executeUpdate();
            //}
            if(isOk2 > 0){
            	isOk = 1;
            }
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
        }
        return isOk;
    }
}
