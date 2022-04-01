//**********************************************************
//  1. 제      목: 과정 SIMULATION BEAN
//  2. 프로그램명: BetaSubjectBean.java
//  3. 개      요: 과정 SIMULATION BEAN
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 27
//  7. 수      정:
//**********************************************************
package com.credu.beta;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class SubjectSimulationBean {

    public final static String LANGUAGE_GUBUN = "0017";
	private static final String FILE_TYPE = "p_file";          //      파일업로드되는 tag name
    private static final int FILE_LIMIT = 5;                   //    페이지에 세팅된 파일첨부 갯수
    private ConfigSet config;
    private int row;

    public SubjectSimulationBean() {}

    /**
    과정리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   과정리스트
    */
    public ArrayList SelectSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls2 = null;
        ArrayList list = null;
        String sql  = "";
        DataBox dbox = null;

        String  ss_subjcourse   = box.getStringDefault("s_subjcourse","ALL");    //과정&코스
        String  v_searchtext	= box.getString("p_searchtext");
        String  v_cp			= box.getString("p_cp");
        String  v_year			= box.getString("p_year");
        String  s_gadmin        = box.getSession("gadmin");
        String  s_userid        = box.getSession("userid");
        
        try {

			connMgr = new DBConnectionManager();


            //베타업체 리스트
            if(s_gadmin.equals("S1") || s_gadmin.equals("T1") || s_gadmin.equals("M1") ){
            	//베타업체 담당자일경우(해당업체의 정보만보여줌)
				sql  = " select  comp, compnm from tz_comp            ";
				sql += "  where comp in (select comp from tz_compman  ";
				sql += "                  where gadmin = 'T1'         ";
				sql += "                    and userid = " + SQLString.Format(s_userid) + " )";			            
	                        
            	ls = connMgr.executeQuery(sql);
            	
            	if(ls.next())
            		v_cp = ls.getString("compnm");
            	else
            		v_cp = "";

				ls.close();

System.out.println("sssssssssssssss"+v_cp);
           	}
				
			sql  = "select a.subj, a.subjnm, to_char(sysdate,'YYYY') year, '0000' subjseq, ";
			sql += "       a.isuse,	a.indate, a.cpapproval,	b.compnm	  					     ";
			sql += "  from tz_subj a, tz_comp b			                                         ";
			sql += "  where 1 = 1							                                     ";

			//과정명검색
			if ( !v_searchtext.equals("")) {	//    검색어가 있으면
                //v_pageno = 1;	//      검색할 경우 첫번째 페이지가 로딩된다
                sql += " and a.subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }
            
            //과정개설년도 검색
            if(!v_year.equals("") && !v_year.equals("ALL")){
            	sql += " and substring(a.indate,1,4) = " + StringManager.makeSQL(v_year);
            } else if(v_year.equals("")){
            	sql += " and substring(a.indate,1,4) = " + StringManager.makeSQL(FormatDate.getDate("yyyy"));
            }
            
            //베타업체 검색
            if(!v_cp.equals("") && !v_cp.equals("ALL")){
                sql += " and a.producer = " + StringManager.makeSQL(v_cp);
                sql += " and a.producer = b.comp ";
            } else if(!v_cp.equals("") || v_cp.equals("ALL")){
            	sql += " and a.producer = b.comp ";
            } else{
            	sql += " and a.producer = b.comp ";
            }

			sql += "  order by  a.indate desc  ";

            list = new ArrayList();
            ls2 = connMgr.executeQuery(sql);

            while (ls2.next()) {
                dbox = ls2.getDataBox();
                list.add(dbox);
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
        return list;
    }


    /**
    * 상신 등록
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */
    public int ResultInsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        int isOk1 = 1;

        int v_seq = box.getInt("p_seq");
        String v_subj 	= box.getString("p_subj");
        String v_gubun	= box.getString("p_gubun");
        String v_resultchk = box.getString("p_resultchk");
		String v_answer	= "";
		
        // 답변 유무 체크(답변 있을시 삭제불가)

            try {
                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);

                isOk1 = 1;
                sql = "update tz_subj set ";
                if (v_gubun.equals("first")) {
                	sql += "firstresult=?";
                } else {
                	sql += "finalresult=?";
                }
                sql += " where subj=?";
                pstmt1 = connMgr.prepareStatement(sql);
                pstmt1.setString(1, v_resultchk);
				pstmt1.setString(2, v_subj);
                isOk1 = pstmt1.executeUpdate();

                if (isOk1 > 0) {
                    connMgr.commit();
                } else connMgr.rollback();
            }
            catch (Exception ex) {
                connMgr.rollback();
                ErrorManager.getErrorStackTrace(ex, box,    sql);
                throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
            }
            finally {
                if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}  
                if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
                
            }
        return isOk1;
    }
    
    /**
    * 상신 결재
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */
    public int ResultUpdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        int isOk1 = 1;

        String v_subj 	= box.getString("p_subj");
        String v_first	= box.getString("s_first");
        String v_final	= box.getString("s_final");
        String v_cnt    = box.getString("p_cnt");
        
        v_first = v_first+v_cnt;
        v_final = v_final+v_cnt;
        
            try {
                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);

                isOk1 = 1;
                sql = "update tz_subj set ";
               	sql += " firstresult=?, ";
               	sql += " finalresult=? ";
                sql += " where subj=?";
                pstmt1 = connMgr.prepareStatement(sql);
                pstmt1.setString(1, v_first);
                pstmt1.setString(2, v_final);
				pstmt1.setString(3, v_subj);
                isOk1 = pstmt1.executeUpdate();

                if (isOk1 > 0) {
                    connMgr.commit();
                } else connMgr.rollback();
            }
            catch (Exception ex) {
                connMgr.rollback();
                ErrorManager.getErrorStackTrace(ex, box,    sql);
                throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
            }
            finally {
                if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}  
                
                if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
        return isOk1;
    }

	

    /**
    과정맛보기 등록
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
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
    과정데이타 조회
    @param box          receive from the form object and session
    @return ArrayList   과정데이타
    */
    public BetaPreviewData SelectPreviewData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        BetaPreviewData data = null;
        String sql  = "";
        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");

        try {
            sql =  " select grcode,   subj,        subjtext,  edumans, ";
            sql+=  "        intro,    explain,     expect,    master, ";
            sql+=  "        masemail, recommender, recommend, luserid, ";
            sql+=  "        ldate ";
            sql+=  "   from TZ_PREVIEW ";
            sql+=  "  where grcode = " + SQLString.Format(v_grcode);
            sql+=  "    and subj = " + SQLString.Format(v_subj);

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data=new BetaPreviewData();
                data.setGrcode   (ls.getString("grcode"));
                data.setSubj     (ls.getString("subj"));
                data.setSubjtext (ls.getString("subjtext"));
                data.setEdumans  (ls.getString("edumans"));
                data.setIntro    (ls.getString("intro"));
                data.setExplain  (ls.getString("explain"));
                data.setExpect   (ls.getString("expect"));
                data.setMaster   (ls.getString("master"));
                data.setMasemail (ls.getString("masemail"));
                data.setRecommender(ls.getString("recommender"));
                data.setRecommend(ls.getString("recommend"));
                data.setLuserid   (ls.getString("luserid"));
                data.setLdate    (ls.getString("ldate"));       }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return data;
    }

    /**
    과정맛보기 수정
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
     public int UpdatePreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_subj   = box.getString("p_subj");
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            //insert TZ_SUBJ table
            sql =  "update TZ_PREVIEW ";
            sql+=  "   set subjtext   = ?, ";
            sql+=  "       edumans    = ?, ";
            sql+=  "       intro      = ?, ";
            sql+=  "       explain    = ?, ";
            sql+=  "       expect     = ?, ";
            sql+=  "       master     = ?, ";
            sql+=  "       masemail   = ?, ";
            sql+=  "       recommender= ?, ";
            sql+=  "       recommend  = ?, ";
            sql+=  "       luserid     = ?, ";
            sql+=  "       ldate      = ?  ";
            sql+=  " where grcode     = ?  ";
            sql+=  "   and subj       = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString( 1, box.getString("p_subjtext"));
            pstmt.setString( 2, box.getString("p_edumans"));
            pstmt.setString( 3, box.getString("p_intro"));
            pstmt.setString( 4, box.getString("p_explain"));
            pstmt.setString( 5, box.getString("p_expect"));
            pstmt.setString( 6, box.getString("p_master"));
            pstmt.setString( 7, box.getString("p_masemail"));
            pstmt.setString( 8, box.getString("p_recommender"));
            pstmt.setString( 9, box.getString("p_recommend"));
            pstmt.setString(10, v_luserid);
            pstmt.setString(11, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(12, box.getString("p_grcode"));
            pstmt.setString(13, box.getString("p_subj"));

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
    선택된 과정맛보기 삭제
    @param box      receive from the form object and session
    @return isOk    1:delete success,0:delete fail
    */
    public int DeletePreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int    isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_subj  = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            //delete TZ_SUBJ table
            sql = "delete from TZ_PREVIEW where grcode = ? and subj = ?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_grcode);
            pstmt.setString(2, v_subj);
            isOk = pstmt.executeUpdate();
        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    선택된 과정맛보기 삭제
    @param box      receive from the form object and session
    @return isOk    1:delete success,0:delete fail
    */
    public int DeleteProgress(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        String sql = "";
        String sql2 = "";
        String sql3 = "";
        int    isOk = 0;
        int    isOk2 = 0;
        int    isOk3 = 0;

        String s_userid		= box.getSession("userid");
        String v_subj		= box.getString("p_subj");
        String v_year		= box.getString("p_year1");
        String v_subjseq	= box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);            

            //delete TZ_User_Scoinfo table
            sql3 = "delete from TZ_USER_SCOINFO where subj = ? and year = ? and subjseq = ? and userid = ?";
            pstmt3 = connMgr.prepareStatement(sql3);
            pstmt3.setString(1, v_subj);
            pstmt3.setString(2, v_year);
            pstmt3.setString(3, v_subjseq);
            pstmt3.setString(4, s_userid);
            isOk3 = pstmt3.executeUpdate();

            //delete TZ_Progress table
            sql = "delete from TZ_PROGRESS where subj = ? and year = ? and subjseq = ? and userid = ?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);
            pstmt.setString(4, s_userid);
            isOk = pstmt.executeUpdate();

            //delete TZ_Student_pre table
            sql2 = "delete from TZ_STUDENT_PRE where subj = ? and year = ? and subjseq = ? and userid = ?";
            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(1, v_subj);
            pstmt2.setString(2, v_year);
            pstmt2.setString(3, v_subjseq);
            pstmt2.setString(4, s_userid);
            isOk2 = pstmt2.executeUpdate();
			connMgr.commit();
        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
            if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


}
