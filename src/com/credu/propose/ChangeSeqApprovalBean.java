//**********************************************************
//  1. 제      목: SUBJECT INFORMATION USER BEAN
//  2. 프로그램명: ChangeSeqApprovalBean.java
//  3. 개      요: 과정안내 사용자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.01.14
//  7. 수      정:
//**********************************************************
package com.credu.propose;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.propose.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

public class ChangeSeqApprovalBean {

    public ChangeSeqApprovalBean() {}

    /**
    수강과정 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */

     public ArrayList selectChangeApprovalList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
        String sql1         = "";
        String sql2         = "";
        int     l           = 0;
        
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수

        
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");    //과정분류
		String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");    //과정분류
		String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");    //과정분류
        
		String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수
        
        String  v_comp      = box.getSession("comp");
        v_comp              = v_comp.substring(0,4);
        
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            list2 = new ArrayList();
            sql1 = "";
            
            sql1 = "select ";
            sql1 += "  a.subj         ,         \n";
            sql1 += "  a.year         ,         \n";
            sql1 += "  a.subjseq      ,         \n";
            sql1 += "  b.subjseqgr    ,         \n";
            sql1 += "  a.userid       ,         \n";
            sql1 += "  a.subjnm       ,         \n";
            sql1 += "  a.usercono     ,         \n";
            sql1 += "  a.usercomp     ,         \n";
            sql1 += "  a.userjik      ,         \n";
            sql1 += "  a.prodate      ,         \n";
            sql1 += "  a.wantseq1     ,         \n";
            sql1 += "  a.wantseq2     ,         \n";
            sql1 += "  a.wantseq3     ,         \n";
            sql1 += "  a.appcono      ,         \n";
            sql1 += "  a.appid        ,         \n";
            sql1 += "  a.appmail      ,         \n";
            sql1 += "  a.appsubj      ,         \n";
            sql1 += "  a.appyear      ,         \n";
            sql1 += "  a.appsubjseq   ,         \n";
            sql1 += "  a.isupapproval ,         \n";
            sql1 += "  a.isdoapproval ,         \n";
            sql1 += "  a.isadmapproval,         \n";
            sql1 += "  d.mastercd,              \n";
            sql1 += "  d.isedutarget,           \n";
            sql1 += "  (select subjseqgr from vz_scsubjseq where subj = a.appsubj and subjseq = a.appsubjseq and year = a.appyear) appsubjseqgr,\n";
            sql1 += "  (select name from tz_member where userid = a.userid) name,\n";
            sql1 += "  (select orga_ename from tz_member where userid = a.userid) orga_ename,\n";
            sql1 += "  (select name from tz_member where userid = a.appid) appname,\n";
            sql1 += "  (select orga_ename from tz_member where userid = a.appid) apporga_ename\n";
            sql1 += "from                       \n";
            sql1 += "  tz_changeseq a,          \n";
            sql1 += "  vz_scsubjseq b,          \n";
            //sql1 += "  tz_propose c,             \n";
            sql1 += "  vz_mastersubjseq d       \n";
            sql1 += "where                      \n";
            sql1 += "a.subj = b.subj            \n";
            sql1 += "and a.subjseq = b.subjseq  \n";
            sql1 += "and a.year = b.year        \n";
			// 수정:lyh date:2005-11-16 내용:(+) 을 * 으로 
            //sql1 += "and a.subj = d.subj(+)     \n";
            //sql1 += "and a.subjseq = d.subjseq(+)\n";
            //sql1 += "and a.year = d.year(+)      \n";
            sql1 += "and a.subj* = d.subj     \n";
            sql1 += "and a.subjseq* = d.subjseq \n";
            sql1 += "and a.year* = d.year      \n";
            //sql1 += "and a.userid = c.userid    \n";
            sql1 += "and a.isupapproval = 'Y'    \n";
            sql1 += "and a.isdoapproval = 'Y'    \n";
            sql1 += "and to_char(sysdate,'YYYYMMDDHH24') between b.propstart and b.edustart \n";
            //sql1 += "and c.chkfinal = 'Y'       \n";
            
            if (!ss_grcode.equals("ALL")) {
                    sql1+= " and b.grcode = "+SQLString.Format(ss_grcode);
            }
            if (!ss_gyear.equals("ALL")) {
                    sql1+= " and b.gyear = "+SQLString.Format(ss_gyear);
            }
            if (!ss_grseq.equals("ALL")) {
                sql1+= " and b.grseq = "+SQLString.Format(ss_grseq);
            }
            if (!ss_uclass.equals("ALL")) {
                sql1+= " and b.scupperclass = "+SQLString.Format(ss_uclass);
            }
            if (!ss_mclass.equals("ALL")) {
                sql1+= " and b.scmiddleclass = "+SQLString.Format(ss_mclass);
            }
			
            if (!ss_lclass.equals("ALL")) {
                sql1+= " and b.sclowerclass = "+SQLString.Format(ss_lclass);
            }

            if (!ss_subjcourse.equals("ALL")) {
                sql1+= " and b.scsubj = "+SQLString.Format(ss_subjcourse);
            }
            if (!ss_subjseq.equals("ALL")) {
                sql1+= " and b.scsubjseq = "+SQLString.Format(ss_subjseq);
            }
            sql1 += "order by prodate desc";

            System.out.println(sql1);
            
            ls1 = connMgr.executeQuery(sql1);

                while (ls1.next()) {
                	dbox = ls1.getDataBox();
                    list1.add(dbox);
                }

        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }
    
    
    /**
    수강과정 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */

     public int UpdateChangeSeqApproval(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        int isOk            = 0;
        int isOk1           = 0;
        int isOk2           = 0;
        int isOk3           = 0;
        int isOk4           = 0;
        int isOk5           = 0;
        int isOk6           = 0;
        int isOk7           = 0;
        int isOk8           = 0;
        int isOk9           = 0;
        String  sql1 = "";
        
        ProposeBean probean = new ProposeBean();
        Hashtable insertData = new Hashtable();
        
        
        String v_isdoapproval  = box.getString("p_isdoapproval");
        String v_isupapproval  = box.getString("p_isupapproval");
        String v_isadmapproval = box.getString("p_isadmapproval");
        
        Vector v_check1     = new Vector();
        v_check1            = box.getVector("p_checks"); // userid,subj,year,subjseq
        
        Enumeration em1     = v_check1.elements();
        
        StringTokenizer st1 = null;
        String v_checks     = "";
        
        String v_subj       = "";
        String v_year       = "";
        String v_subjseq    = "";
        String v_userid     = "";
        
        String v_appsubj    = "";
        String v_appyear    = "";
        String v_appsubjseq = "";
        String v_appid      = "";
        String v_isproposeapproval = "";
        String v_useproposeapproval = "";
        String v_luserid = box.getSession("userid");
        try {
        	    
                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);///////////
                
                
                while(em1.hasMoreElements()){
                    v_checks    = (String)em1.nextElement();
                    st1      = new StringTokenizer(v_checks,",");
                    while (st1.hasMoreElements()) {
                        v_subj          = (String)st1.nextToken();
                        v_subjseq       = (String)st1.nextToken();
                        v_year          = (String)st1.nextToken();
                        v_userid        = (String)st1.nextToken();
                        v_appsubj       = (String)st1.nextToken();
                        v_appsubjseq    = (String)st1.nextToken();
                        v_appyear       = (String)st1.nextToken();
                        v_appid         = (String)st1.nextToken();                        
                        v_isupapproval  = (String)st1.nextToken();                        
                        v_isdoapproval  = (String)st1.nextToken();                        
                        v_isadmapproval = (String)st1.nextToken();                        
                        break;
                    }
                
                    insertData.clear();
                    insertData.put("connMgr",  connMgr);
                    insertData.put("subj",          v_subj);
                    insertData.put("subjseq",       v_subjseq);
                    insertData.put("year",          v_year);
                    insertData.put("userid",        v_userid);
                    insertData.put("isupapproval",  v_isupapproval);
                    insertData.put("isdoapproval" , v_isdoapproval);
                    insertData.put("isadmapproval", v_isadmapproval);
                    isOk1 = probean.updateChangeSeq(insertData);
                    
                    //최초 변경신청자 신청,학생테이블 업데이트
                    insertData.put("subj",          v_subj);
                    insertData.put("subjseq",       v_subjseq);
                    insertData.put("year",          v_year);
                    insertData.put("userid",        v_userid);
                    
                    //isOk2 = (probean.updateStudent(insertData));
                    //isOk3 = (probean.updateChangeSeqPropose(insertData));
                    isOk2 = (probean.deleteStudent(insertData));
                    isOk3 = (probean.deletePropose(insertData));
                    
                    v_useproposeapproval = probean.getProposeApproval(v_subj,v_year,v_appsubjseq);
                    if (v_useproposeapproval.equals("N")){
                    	v_isproposeapproval = "L";               //팀장승인여부 L:불필요 B:상신중 Y:승인 N:반려
                    }
                    else if (v_useproposeapproval.equals("Y")){
                    	v_isproposeapproval = "Y";               //팀장승인여부 L:불필요 B:상신중 Y:승인 N:반려
                    }
                    
                    insertData.put("subjseq",    v_appsubjseq);
                    insertData.put("isdinsert",    "Y");
                    insertData.put("ischkfirst",    "Y");
                    insertData.put("chkfinal",    "Y");
                    insertData.put("isproposeapproval",    v_isproposeapproval);
                    insertData.put("luserid",    v_luserid);
                    isOk4 = (probean.insertStudent(insertData));
                    
                    isOk5 = (probean.insertPropose(insertData));
                    ////////////////////////////////////////////
                    
                    
                    
                    //변경 대상자 신청,학생테이블 업데이트
                    insertData.put("subj",          v_appsubj);
                    insertData.put("subjseq",       v_appsubjseq);
                    insertData.put("year",          v_appyear);
                    insertData.put("userid",        v_appid);
                    
                    //isOk6 = probean.updateStudent(insertData);
                    //isOk7 = probean.updateChangeSeqPropose(insertData);
                    isOk6 = (probean.deleteStudent(insertData));
                    isOk7 = (probean.deletePropose(insertData));
                    
                    v_useproposeapproval = probean.getProposeApproval(v_appsubj,v_appyear,v_subjseq);
                    
                    if (v_useproposeapproval.equals("N")){
                    	v_isproposeapproval = "L";               //팀장승인여부 L:불필요 B:상신중 Y:승인 N:반려
                    }
                    else if (v_useproposeapproval.equals("Y")){
                    	v_isproposeapproval = "Y";               //팀장승인여부 L:불필요 B:상신중 Y:승인 N:반려
                    }
                    
                    insertData.put("subjseq",    v_subjseq);
                    insertData.put("isdinsert",    "Y");
                    insertData.put("ischkfirst",    "Y");
                    insertData.put("chkfinal",    "Y");
                    insertData.put("isproposeapproval",    v_isproposeapproval);
                    insertData.put("luserid",    v_luserid);
                    
                    isOk8 = (probean.insertPropose(insertData));
                    isOk9 = (probean.insertStudent(insertData));
                    //////////////////////////////////////////////////////


                    //System.out.println("isOk1="+isOk1);
                    //System.out.println("isOk2="+isOk2);
                    //System.out.println("isOk3="+isOk3);
                    //System.out.println("isOk4="+isOk3);
                    //System.out.println("isOk5="+isOk3);
                    
                    isOk = isOk1 * isOk2 * isOk3 * isOk4 * isOk5;
                    
                    if(isOk > 0){
                      connMgr.commit();
                    }else{
                      connMgr.rollback();
                    }
                }
        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }       
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(pstmt != null)  { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }       
        return isOk;
    
        }
        
        
}

