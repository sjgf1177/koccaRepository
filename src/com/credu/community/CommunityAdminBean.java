//**********************************************************
//  1. 제      목: COMMUNITY ADMIN BEAN
//  2. 프로그램명: CommunityAdminBean.java
//  3. 개      요: 커뮤니티 관리자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.02.12 mscho
//  7. 수      정:
//**********************************************************
package com.credu.community;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.community.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

public class CommunityAdminBean {

    public CommunityAdminBean() {}
    /**
    community 승인 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectPermitList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql  = "";
        DataBox dbox = null;

        String v_search = box.getString("p_search");
        String v_select = box.getString("p_select");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            //select commId, sdesc, categoryId, intros, usercnt, isPublic, permittedStatus
            sql = "select commId, sdesc, categoryId, intros, usercnt, isPublic, permittedStatus ";
            sql+= "from TZ_COMMUNITY where commId > 0 ";
            if ( !v_search.equals("")) {    //검색어(커뮤니티명)가 있는 경우
                sql += " and sdesc like '%"+v_search+"%'";
            }
            if ( !v_select.equals("")) {    //검색항목(카테고리 아이디)이 있는 경우
            sql += " and categoryId like '%"+v_select+"%'";
            }
            sql += " order by permittedStatus desc,commId desc";
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
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    community 승인 조회
    @param box      receive from the form object and session
    @return CommunityData
    */
     public DataBox selectPermit(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls          = null;
        String sql          = "";
        DataBox dbox = null;

        String v_commId     = box.getString("p_commId");

        try {
            connMgr = new DBConnectionManager();

            //select grcode,sdesc,categoryId,master,userCnt,requestDate,intros,topics,permittedStatus,permittedDate,rejectedDate,rejectedReason
            sql = " select grcode,sdesc,categoryId,master,usercnt,requestDate,intros,topics,permittedstatus,permitteddate,";
            sql+= " 	rejecteddate,rejectedreason ";
            sql+= " from TZ_COMMUNITY where commId = '"+v_commId+"'";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox= ls.getDataBox();
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
        return dbox;
    }

    /**
    community 승인 처리
    @param box      receive from the form object and session
    @return int
    */
     public int handlingPermit(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_commId         = box.getString("p_commId");
        String v_permittedStatus= box.getString("p_permittedStatus");
        String v_rejectedReason = box.getString("p_rejectedReason");

        try {
            connMgr = new DBConnectionManager();

            //update TZ_COMMUNITY table
            sql = " update TZ_COMMUNITY set permittedStatus = ?, rejectedReason = ?, permittedDate = ?, rejectedDate = ? ";
            sql+= " where commId = '"+v_commId+"'";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_permittedStatus);
            if(v_permittedStatus.equals("01")){         //승인
                pstmt.setString(2, "");
                pstmt.setString(3, FormatDate.getDate("yyyyMMdd"));
                pstmt.setString(4,"");

               //========폼메일 발송
               isOk = this.sendFormMail(box);

            }else if(v_permittedStatus.equals("02")){   //폐쇄
                pstmt.setString(2, v_rejectedReason);
                pstmt.setString(3,"");
                pstmt.setString(4,FormatDate.getDate("yyyyMMdd"));

               //========폼메일 발송
               isOk = this.sendFormMail2(box);
            }
            isOk = pstmt.executeUpdate();

        }
        catch(Exception ex) {
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
    폼메일 발송(승인)
    @param box      receive from the form object and session
    @return int
    */
    public int sendFormMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        ListSet ls = null;
        String sql = "";
        String v_commId         = box.getString("p_commId");
        int cnt = 0;    //메일발송이 성공한 사람수
        try {
            connMgr = new DBConnectionManager();

////////////////////  폼메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "mail6.html";
            FormMail fmail = new FormMail(v_sendhtml);     //      폼메일발송인 경우
            MailSet mset = new MailSet(box);        //      메일 세팅 및 발송
            String v_mailTitle = "안녕하세요? 사이버러닝센터 운영자입니다.";
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //select sdesc,master,name,email,ismailing,cono
            sql = "select  A.sdesc,A.master,B.name,B.email,B.ismailing,B.cono ";
            sql+= " from TZ_COMMUNITY A,TZ_MEMBER B ";
            sql+= " where A.commid = "+SQLString.Format(v_commId);
            sql+= " and A.master=B.userid ";
//            System.out.println("sql========>"+sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                String v_toEmail =  ls.getString("email");
                String v_toCono  =  ls.getString("cono");
                String v_ismailing= ls.getString("ismailing");
                //String v_toEmail =  "jj1004@dreamwiz.com";

                mset.setSender(fmail);     //  메일보내는 사람 세팅

                fmail.setVariable("sdesc", ls.getString("sdesc"));
                fmail.setVariable("toname", ls.getString("name"));

                String v_mailContent = fmail.getNewMailContent();
//                System.out.println("ismailing" + ls.getString("ismailing"));

                boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent,v_ismailing, v_sendhtml);
                if(isMailed) cnt++;     //      메일발송에 성공하면
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
        return cnt;
    }

    /**
    폼메일 발송(폐쇄)
    @param box      receive from the form object and session
    @return int
    */
    public int sendFormMail2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        ListSet ls = null;
        String sql = "";
        String v_commId         = box.getString("p_commId");
        int cnt = 0;    //메일발송이 성공한 사람수
        try {
            connMgr = new DBConnectionManager();

////////////////////  폼메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "mail61.html";
            FormMail fmail = new FormMail(v_sendhtml);     //      폼메일발송인 경우
            MailSet mset = new MailSet(box);        //      메일 세팅 및 발송
            String v_mailTitle = "안녕하세요? 사이버러닝센터 운영자입니다.";
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //select sdesc,master,name,email,ismailing,cono
            sql = "select  A.sdesc,A.master,B.name,B.email,B.ismailing,B.cono ";
            sql+= " from TZ_COMMUNITY A,TZ_MEMBER B ";
            sql+= " where A.commid = "+SQLString.Format(v_commId);
            sql+= " and A.master=B.userid ";
//            System.out.println("sql========>"+sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                String v_toEmail =  ls.getString("email");
                String v_toCono  =  ls.getString("cono");
                String v_ismailing= ls.getString("ismailing");

                mset.setSender(fmail);     //  메일보내는 사람 세팅

                fmail.setVariable("sdesc", ls.getString("sdesc"));
                fmail.setVariable("toname", ls.getString("name"));

                String v_mailContent = fmail.getNewMailContent();
//                System.out.println("ismailing" + ls.getString("ismailing"));

                boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent,v_ismailing, v_sendhtml);
                if(isMailed) cnt++;     //      메일발송에 성공하면
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
        return cnt;
    }

    /**
    우수 community 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectSuperiorList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql  = "";
        DataBox dbox = null;
        String v_search = box.getString("p_search");
        String v_select = box.getString("p_select");
        String ss_year  = box.getStringDefault("s_year","ALL");
        String ss_month = box.getStringDefault("s_month","ALL");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            //select commId, sdesc, categoryId, intros, usercnt, isPublic, isSuperior
            sql = "select commId, sdesc, categoryId, intros, usercnt, isPublic, isSuperior ";
            sql+= "from TZ_COMMUNITY where permittedStatus = '01' ";
            if ( !v_search.equals("")) {    //검색어(커뮤니티명)가 있는 경우
                sql += " and sdesc like '%"+v_search+"%'";
            }
            if ( !v_select.equals("")) {    //검색항목(카테고리 아이디)이 있는 경우 
            sql += " and categoryId like '%"+v_select+"%'";
            }
            sql += " order by isSuperior, commId desc";
            ls = connMgr.executeQuery(sql);
//            System.out.println("sql=======>"+sql);
          

			ls = connMgr.executeQuery(sql);
//System.out.println("sql=======>"+sql);

            while (ls.next()) {
                dbox=ls.getDataBox();
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
    우수 community 승인 처리
    @param box      receive from the form object and session
    @return int
    */
     public int handlingSuperior(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;        
        String sql = "";
        int isOk    = 0;           
        //p_supcheck로 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_supcheck   = new Vector();
        v_supcheck          = box.getVector("p_supcheck");
        Enumeration em      = v_supcheck.elements();
        String v_str        = "";   //실제 넘어온 각각의 value
        String v_yn         = "";   //v_str중에서 Y/N부분
        String v_commId     = "";   //v_str중에서 community ID부분
        try {
            connMgr = new DBConnectionManager();                                         
            while(em.hasMoreElements()){
			    v_str   = (String)em.nextElement();
			    v_yn    = StringManager.substring(v_str,0,1);
			    v_commId= StringManager.substring(v_str,2);
                //update TZ_COMMUNITY table
                sql = "update TZ_COMMUNITY set isSuperior = '"+v_yn+"' ";
                sql+= "where commId = '"+Integer.parseInt(v_commId)+"'"; 
                //System.out.println("sql========>"+sql);
                isOk = connMgr.executeUpdate(sql);                
            }  
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            //if(stmt != null) { try { stmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;     
    }



    /**
    ActionLearning 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectActionLearningList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        ListSet ls      = null;
        ResultSet rs2   = null;
        ResultSet rs3   = null;
        ResultSet rs4   = null;
        ArrayList list  = null;
        String sql1     = "";
        String sql2     = "";
        String sql3     = "";
        String sql4     = "";
        String v_subj   = "";           //과정아이디
        String v_year   = "";           //과정년도
        String v_subjseq= "";           //과정차수
        String v_sdesc  = "";           //동호회명
        int    v_cntpropose= 0;         //총수강자수
        int    v_cntstold=  0;          //총수료자수
        int    v_commid =  0;           //동호회ID

        DataBox dbox = null;
        String v_search = box.getString("p_search");
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            //select B.grcode,A.subj,A.subjnm,B.subjseq,B.edustart,B,eduend,B.isClosed
            sql1 = "select B.grcode,A.subj,A.subjnm,B.year,B.subjseq,B.edustart,B.eduend,B.isClosed ";
            sql1+= "from TZ_SUBJ A,TZ_SUBJSEQ B where A.subj=B.subj and A.isonoff='OFF' ";
            if ( !v_search.equals("")) {    //검색어(과정명)가 있는 경우
                sql1+= "and A.subjnm like '%"+v_search+"%' ";
            }
            sql1+= "order by B.eduend desc,B.isclosed";

            ls = connMgr.executeQuery(sql1);

            //select commId,sdesc
            sql2 = "select commId,sdesc from TZ_SUBJ_ASSOCIATION ";
            sql2+= "where subj=? and year=? and subjseq=?";
            pstmt2 = connMgr.prepareStatement(sql2);

            //select count propose
            sql3  = "select  count(subj) cntpropose from TZ_STUDENT ";
            sql3 += "where subj=? and year=? and subjseq=?";
            pstmt3 = connMgr.prepareStatement(sql3);

            //select count graduate
            sql4  = "select count(subj) cntstold  from TZ_STOLD ";
            sql4 += "where subj=? and year=? and subjseq=? and isgraduate = 'Y'";
            pstmt4 = connMgr.prepareStatement(sql4);

            while (ls.next()) {
                dbox=ls.getDataBox();
                v_subj      = dbox.getString("d_subj");
                v_year      = dbox.getString("d_year");
                v_subjseq   = dbox.getString("d_subjseq");
/*
                data=new CommunityData();
                data.setGrcode(ls.getString("grcode"));
                data.setSubjnm(ls.getString("subjnm"));
                v_subj      = ls.getString("subj");
                v_year      = ls.getString("year");
                v_subjseq   = ls.getString("subjseq");
                data.setSubj(v_subj);
                data.setYear(v_year);
                data.setSubjseq(v_subjseq);
                data.setEdustart(ls.getString("edustart"));
                data.setEduend(ls.getString("eduend"));
                data.setIsclosed(ls.getString("isclosed"));
*/

                //TZ_SUBJ_ASSOCIATION table에서 select
                pstmt2.setString(1,v_subj);
                pstmt2.setString(2,v_year);
                pstmt2.setString(3,v_subjseq);
                try{
                    rs2 = pstmt2.executeQuery();
                    if(rs2.next()){
                        v_commid    = rs2.getInt("commId");
                        v_sdesc     = rs2.getString("sdesc");
                    }else{
                        v_commid    = 0;
                        v_sdesc     = "";
                    }
                }catch(Exception ex) {}
                finally{ if(rs2 != null) { try { rs2.close(); }catch (Exception e) {} } }

                //TZ_PROPOSE table에서 select
                pstmt3.setString(1,v_subj);
                pstmt3.setString(2,v_year);
                pstmt3.setString(3,v_subjseq);
                try{
                    rs3 = pstmt3.executeQuery();
                    if(rs3.next()){
                        v_cntpropose= rs3.getInt("cntPropose");
                    }else{
                        v_cntpropose= 0;
                    }
                }catch(Exception ex) {}
                finally{ if(rs3 != null) { try { rs3.close(); }catch (Exception e) {} } }

                //TZ_STOLD table에서 select
                pstmt4.setString(1,v_subj);
                pstmt4.setString(2,v_year);
                pstmt4.setString(3,v_subjseq);
                try{
                    rs4 = pstmt4.executeQuery();
                    if(rs4.next()){
                        v_cntstold= rs4.getInt("cntstold");
                    }else{
                        v_cntstold= 0;
                    }
                }catch(Exception ex) {}
                finally{ if(rs4 != null) { try { rs4.close(); }catch (Exception e) {} } }
                /*
                data.setCommId(v_commid);
                data.setSdesc(v_sdesc);
                data.setCntpropose(v_cntpropose);
                data.setCntstold(v_cntstold);
                list.add(data);
                */
                dbox.put("d_commid",new Integer(v_commid));
                dbox.put("d_sdesc", v_sdesc);
                dbox.put("d_cntpropose",  new Integer(v_cntpropose));
                dbox.put("d_cntstold",  new Integer(v_cntstold));
                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql4);
            throw new Exception("sql4 = " + sql4 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    ActionLearning community 생성
    @param box      receive from the form object and session
    @return int
    */
     public int insertActionLearning(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls  = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
       
        int isOk1   = 0;
        int isOk2   = 0;
        int isOk3   = 0;
        int isOk4   = 0;
       
        int p_commId= 1;
        int v_result= 0;
        int v_max   = 1;
        String v_id         = box.getString("p_masterID");
        String v_name       = box.getString("p_masterName");
        int    v_commId     = 1;
        String v_sdesc      = box.getString("p_sdesc");
        String v_categoryId = box.getString("p_categoryId");
        String v_intros     = box.getString("p_intros");
        String v_topics     = box.getString("p_topics");
        String v_isPublic   = box.getString("p_isPublic");
        String v_subj       = box.getString("p_subj");
        String v_grcode     = box.getString("p_grcode");
        String v_year       = box.getString("p_year");
        String v_subjseq    = box.getString("p_subjseq");
        int    v_cntpropose = box.getInt("p_cntpropose");
        try {
            connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

            //커뮤니티의 중복여부 조회
            v_result = overlapping(v_sdesc,v_id);
            if(v_result == 1){ return 0; }

            //select max(commId)
            sql1 = "select max(commId) from TZ_COMMUNITY";
            ls = connMgr.executeQuery(sql1);

            if(ls.next()){
                v_max = ls.getInt(1);
                if(v_max != 0){ v_commId = v_max + 1; }
            }

            //insert TZ_COMMUNITY table
            sql2 =  "insert into TZ_COMMUNITY(grcode,commId,sdesc,categoryId,requestDate,permittedStatus,permitteddate,master,";
            sql2+=  "intros,topics,isPublic,isPermit,usercnt)";
            sql2+=  " values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDD'),'01',  to_char(sysdate, 'YYYYMMDD'),?, ?, ?, ? ,'N',?)";
            pstmt2 = connMgr.prepareStatement(sql2);

            //insert TZ_COMMUNITY_MEMBER table
            //sql3 =  "insert into TZ_COMMUNITY_MEMBER(grcode,commId,userid,name,requestDate,";
            //sql3+=  "permittedStatus,permittedDate,isLeader) ";
            //sql3+=  "values(?, ?, ?, ?, to_char(sysdate, 'YYYYMMDD'), '01', to_char(sysdate, 'YYYYMMDD'),'Y')";
            //pstmt3 = connMgr.prepareStatement(sql3);

            //insert TZ_SUBJ_ASSOCIATION table
            sql3 =  "insert into TZ_SUBJ_ASSOCIATION(subj,year,subjseq,grcode,commId,sdesc) ";
            sql3+=  "values(?,?,?,?,?,?)";
            pstmt3 = connMgr.prepareStatement(sql3);

            pstmt2.setString(1, v_grcode);
            pstmt2.setInt(2, v_commId);
            pstmt2.setString(3, v_sdesc);
            pstmt2.setString(4, v_categoryId);
            pstmt2.setString(5, v_id);
            pstmt2.setString(6, v_intros);
            pstmt2.setString(7, v_topics);
            pstmt2.setString(8, v_isPublic);
            pstmt2.setInt(9, v_cntpropose);
            isOk2 = pstmt2.executeUpdate(); //Action Learning 등록

            //pstmt3.setString(1, v_grcode);
            //pstmt3.setInt(2, v_commId);
            //pstmt3.setString(3, v_id);
            //pstmt3.setString(4, v_name);
            //isOk3 = pstmt3.executeUpdate(); //ACtion Learning 시샵등록

            pstmt3.setString(1,v_subj);
            pstmt3.setString(2,v_year);
            pstmt3.setString(3,v_subjseq);
            pstmt3.setString(4, v_grcode);
            pstmt3.setInt(5,v_commId);
            pstmt3.setString(6,v_sdesc);
            isOk3 = pstmt3.executeUpdate(); //Action Learning mapping table 등록


            isOk4 = insertActionLearningMember(connMgr, v_grcode,v_commId, v_subj, v_year, v_subjseq, v_id);   //Action Learning 회원등록

            if(isOk2 > 0 && isOk3 >0 && isOk4 >0){
                connMgr.commit();
            }else{ connMgr.rollback(); }

		}
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql2 = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt3.close(); } catch (Exception e1) {} }
            if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e1) {} }
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk2*isOk3*isOk4;
    }

    /**
    입력한 커뮤니티의 중복여부 조회
    @param v_sdesc      커뮤니티명
    @param v_master     시샵ID
    @return v_result    1:중복됨 ,0:중복되지 않음
    */
    public int overlapping(String v_sdesc,String v_master) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls      = null;
        String sql      = "";
        int v_result    = 0;
        try{
            connMgr = new DBConnectionManager();
            sql  = "select commId from TZ_COMMUNITY where sdesc ='"+v_sdesc+"'";
            sql += " and master='"+v_master+"'";
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
    ActionLearning community member insert
    @param box      receive from the form object and session
    @return int
    */
     public int insertActionLearningMember(DBConnectionManager connMgr, String v_grcode,int v_commId,String v_subj,String v_year,String v_subjseq, String v_masterId) throws Exception {
        ListSet ls                  = null;
        PreparedStatement pstmt2    = null;
        String sql1                 = "";
        String sql2                 = "";
        int isOk                    = 0;
        String v_id                 = "";
        String v_name               = "";
		String v_isLeader			= "N";
		
        try{
            sql1  = "select userid,(select name from TZ_MEMBER where userid=A.userid) name ";
            sql1 += "from TZ_STUDENT A ";
            sql1 += "where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"'";
            ls = connMgr.executeQuery(sql1);

            sql2  = "insert into TZ_COMMUNITY_MEMBER(commId,grcode,userid,name,requestDate,permittedStatus,permittedDate, isLeader) ";
            sql2 += "values(?, ?, ?, ?, to_char(sysdate, 'YYYYMMDD'), '01',  to_char(sysdate, 'YYYYMMDD'), ?)";
            pstmt2 = connMgr.prepareStatement(sql2);

            while(ls.next()){
                v_id    = ls.getString("userid");
                v_name  = ls.getString("name");
				if (v_id.equals(v_masterId)){ v_isLeader = "Y"; }	//시삽일경우 
                pstmt2.setInt	(1,v_commId);
                pstmt2.setString(2,v_grcode);
                pstmt2.setString(3,v_id);
                pstmt2.setString(4,v_name);
				pstmt2.setString(5,v_isLeader);
                isOk = pstmt2.executeUpdate();
            }
        }
        catch(Exception ex) {
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null)      { try { ls.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); }catch (Exception e) {} }
        }
        return isOk;
    }

    /**
    선택된 Action Learning Community 상세내용 select(수정 page)
    @param box              receive from the form object and session
    @return CommunityData   선택한 커뮤니티 내용
    */
   public DataBox selectActionLearning(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls          = null;
        String sql          = "";

        String v_commId     = box.getString("p_commId");
        DataBox dbox        = null;

        try {
            connMgr = new DBConnectionManager();

            //select grcode,sdesc,categoryId,master,nickname,intros,targets,topics,isPublic,isPermit
            sql = "select grcode,sdesc,categoryid,master,nickname,intros,targets,topics,isPublic,isPermit ";
            sql+= "from TZ_COMMUNITY where commId = '"+v_commId+"'";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
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
        return dbox;
    }

    /**
    ActionLearning community 수정
    @param box      receive from the form object and session
    @return int
    */
     public int updateActionLearning(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls  = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        int isOk1   = 0;
        int isOk2   = 0;
        int isOk3   = 0;
        int isOk4   = 0;
        String v_id         = box.getString("p_masterID");
        String v_name       = box.getString("p_masterName");
        int    v_commId     = box.getInt("p_commId");
        String v_sdesc      = box.getString("p_sdesc");
        String v_categoryId = box.getString("p_categoryId");
        String v_intros     = box.getString("p_intros");
        String v_topics     = box.getString("p_topics");
        String v_isPublic   = box.getString("p_isPublic");
        String v_isPermit   = box.getString("p_isPermit");
        String v_subj       = box.getString("p_subj");
        String v_year       = box.getString("p_year");
        String v_subjseq    = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //update TZ_COMMUNITY table
            sql1 =  "update TZ_COMMUNITY set sdesc=? , categoryId=? , master=? , ";
            sql1+=  "intros=? , topics=? , isPublic=?  ";
            sql1+=  "where commid='"+v_commId+"'";
            pstmt1 = connMgr.prepareStatement(sql1);

            //update TZ_COMMUNITY_MEMBER table
            sql2 =  "update TZ_COMMUNITY_MEMBER set isLeader='N' ";
            sql2+=  "where commid=? ";
            pstmt2 = connMgr.prepareStatement(sql2);

            //update TZ_COMMUNITY_MEMBER table
            sql3 =  "update TZ_COMMUNITY_MEMBER set isLeader='Y' ";
            sql3+=  "where commid=? and userid=? and name=? ";
            pstmt3 = connMgr.prepareStatement(sql3);

            //update TZ_SUBJ_ASSOCIATION table
            sql4 = "update TZ_SUBJ_ASSOCIATION set sdesc=? ";
            sql4+= "where subj=? and year=? and subjseq=? and commid=?";
            pstmt4 = connMgr.prepareStatement(sql4);

            pstmt1.setString(1, v_sdesc);
            pstmt1.setString(2, v_categoryId);
            pstmt1.setString(3, v_id);
            pstmt1.setString(4, v_intros);
            pstmt1.setString(5, v_topics);
            pstmt1.setString(6, v_isPublic);

            isOk1 = pstmt1.executeUpdate(); //Action Learning 수정

            if(!v_name.equals(""))  //시샵을 수정한 경우
            {
                pstmt2.setInt(1, v_commId);
                isOk2 = pstmt2.executeUpdate(); //ACtion Learning 멤버 수정

                pstmt3.setInt(1, v_commId);
                pstmt3.setString(2, v_id);
                pstmt3.setString(3, v_name);
                isOk3 = pstmt3.executeUpdate(); //ACtion Learning 시샵 수정
            }else{
                isOk2 = 1;
                isOk3 = 1;
            }
            pstmt4.setString(1,v_sdesc);
            pstmt4.setString(2,v_subj);
            pstmt4.setString(3,v_year);
            pstmt4.setString(4,v_subjseq);
            pstmt4.setInt(5,v_commId);
            isOk4 = pstmt4.executeUpdate(); //Action Learning mapping table 수정

            if(isOk1 > 0 && isOk2 >0 && isOk3 >0 && isOk4 >0){
                connMgr.commit();
            }else{ connMgr.rollback(); }
        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql3 = " + sql3 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
            if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e1) {} }
            if(pstmt4 != null) { try { pstmt4.close(); } catch (Exception e1) {} }
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2*isOk3;
    }

   /**
   * 시샵 조회
    @param box      receive from the form object and session
    @return ArrayList
   */
    public static ArrayList selectSearchMaster(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
//        MemberData data = null;
        DataBox dbox = null;
        String v_subj       = box.getString("p_subj");
        String v_year       = box.getString("p_year");
        String v_subjseq    = box.getString("p_subjseq");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = "select a.userid userid, a.name name, a.jikwi jikwi, a.jikwinm jikwinm, b.companynm companynm,";
            sql += "b.gpmnm gpmnm,b.deptnm deptnm, (b.companynm || '/' || b.gpmnm || '/' ||  b.deptnm) compnm,a.cono cono ";
            sql += "from TZ_MEMBER a, TZ_COMP b, TZ_STUDENT c ";
            sql += "where a.comp = b.comp and a.userid = c.userid and c.subj='"+ v_subj +"' and c.year='"+ v_year +"' and c.subjseq='"+ v_subjseq +"' ";
            sql += "order by a.comp asc, a.jikwi asc";
            ls = connMgr.executeQuery(sql);

//System.out.println("sql==============>"+sql);
            ls.setPageSize(10);                         //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);                //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();   //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();   //     전체 row 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpagecount", new Integer(total_page_count));
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


    public boolean isSuperior(DBConnectionManager connMgr, String p_commid, String p_year, String p_month) throws Exception {
        ListSet ls      = null;
        String sql      = "";
        String v_commid = "";

        try {
            sql = "select commid ";
            sql+= "  from tz_community_superior ";
            sql+= " where commid    = " + SQLString.Format(p_commid);
            sql+= "   and year      = " + SQLString.Format(p_year);
            sql+= "   and month     = " + SQLString.Format(p_month);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                v_commid = ls.getString("commid");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }

        return v_commid.equals(p_commid);
    }


/*
Community 우수동호회 선정 년도 select box
*/
    public ArrayList getCyear(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList();
            sql = "select distinct year";
            sql += " from tz_community_superior";
            sql += " order by year desc";

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
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }


}