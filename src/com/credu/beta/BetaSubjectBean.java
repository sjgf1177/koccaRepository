//**********************************************************
//  1. 제      목: 과정OPERATION BEAN
//  2. 프로그램명: BetaSubjectBean.java
//  3. 개      요: 과정OPERATION BEAN
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 14
//  7. 수      정:
//**********************************************************
package com.credu.beta;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.system.SelectionData;
import com.credu.system.SelectionUtil;

public class BetaSubjectBean {

    public final static String LANGUAGE_GUBUN = "0017";


    public BetaSubjectBean() {}

    /**
    과정리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   과정리스트
    */
    public ArrayList SelectSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql  = "";
        BetaSubjectData data = null;
        //DataBox dbox = null;

        String  ss_subjcourse   = box.getStringDefault("s_subjcourse","ALL");    //과정&코스
        String  v_search		= box.getString("p_search");
        String  s_userid        = box.getSession("userid");
        String  s_gadmin        = box.getSession("gadmin");
        
        try {
				
			sql  = "select a.subj, ";
			sql += "       a.subjnm, ";
			sql += "       a.cuserid, ";
			sql += "       b.name, ";
			sql += "       a.isuse ";
			sql += "  from tz_betasubj a, ";
			sql += "       tz_member b ";
			//2005.11.16_하경태 : Oracle -> Mssql
			//sql += " where a.cuserid=b.userid(+) ";
			sql += " where a.cuserid  =  b.userid(+) ";
			if (!s_gadmin.equals("A1")){
			    sql += "   and a.cuserid='"+s_userid+"'";
			}
			if (!ss_subjcourse.equals("ALL")){
				sql += "   and a.subj =" + SQLString.Format(ss_subjcourse);
			}
			if (!v_search.equals("")){
				sql += "   and a.subjnm like '%"+v_search+"%'";
			}
			sql += "   order by a.subj ";
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //dbox = ls.getDataBox();
                data=new BetaSubjectData();
                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setName(ls.getString("name"));
                data.setIsuse(ls.getString("isuse"));
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
    과정데이타 조회
    @param box          receive from the form object and session
    @return ArrayList   과정데이타
    */
    public BetaSubjectData SelectSubjectData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        BetaSubjectData data = null;
        //DataBox dbox = null;
        String sql  = "";
        String v_subj = box.getString("p_subj");
        try {
            sql =  " select subj,       subjnm,         contenttype,    cuserid,    isuse,    ";
            sql+=  "        edudays,    company,        crdate,     	dir,        		  ";
            sql+=  "        vodurl,     preurl,         framecnt,   	edulimit,   companynm,";
            sql+=  "  		cusernm															  ";       	
            sql+=  "   from tz_betasubj 													  ";  
            sql+=  "  where  subj     =  " + SQLString.Format(v_subj);

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data=new BetaSubjectData();
                data.setSubj        (ls.getString("subj"));
                data.setSubjnm      (ls.getString("subjnm"));
                data.setContenttype (ls.getString("contenttype"));
                data.setCuserid     (ls.getString("cuserid"));
                data.setIsuse       (ls.getString("isuse"));
                data.setEdudays     (ls.getInt("edudays"));
	            data.setCompany     (ls.getString("company"));
                data.setCrdate      (ls.getString("crdate"));
                data.setDir         (ls.getString("dir"));
                data.setVodurl      (ls.getString("vodurl"));
                data.setPreurl      (ls.getString("preurl"));
                data.setFramecnt    (ls.getInt("framecnt"));
                data.setEdulimit    (ls.getInt("edulimit"));
                data.setCuseridnm   (ls.getString("cusernm"));
	            data.setCompanynm   (ls.getString("companynm"));
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
        return data;
    }

    /**
    새로운 과정코드 등록 - 사이버
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
     public int InsertSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
		ListSet ls = null;
		
        PreparedStatement pstmt = null;
        String sql 		= "";
		
        int isOk = 0;

        String v_subj     = "B00001";
        String v_luserid  = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
			

			sql =  "select to_char(max(NVL(SUBSTR(subj,2,5),0)+1),'00000') cnt from tz_betasubj";

		//	sql =  "select to_char(max(NVL(substr(subj,2,5),0)+1),'00000') cnt from tz_betasubj";

			ls  =  connMgr.executeQuery(sql);
			if (ls.next()){
				v_subj = ls.getString("cnt");
			}
			ls.close();
			
			if (v_subj.equals("")){
				v_subj = "B00001";
			}
			else {
				v_subj = "B"+v_subj;
			}
			
            //insert TZ_SUBJ table
            sql =  "insert into TZ_BETASUBJ ( ";                                                    
            sql+=  " subj,      subjnm,      cuserid,     isuse,      edudays, ";
            sql+=  " company,   crdate,       dir,         vodurl,     preurl, ";
            sql+=  " framecnt,  edulimit,    luserid,     ldate,      contenttype, cusernm, companynm ) ";
            sql+=  " values (";
            sql+=  " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";

            pstmt = connMgr.prepareStatement(sql);

            //ErrorManager.systemOutPrintln(box);

            pstmt.setString(1, v_subj);										//과정코드
            pstmt.setString(2, box.getString("p_subjnm"));					//과정명
            pstmt.setString(3, box.getString("p_betauserid"));					//컨텐츠담당 ID
            pstmt.setString(4, box.getString("p_isuse"));					//사용여부(Y/N)
            pstmt.setInt   (5, box.getInt   ("p_edudays"));            		//학습일차
            pstmt.setString(6, box.getString("p_betacompanyno"));           		//과정제공자
            pstmt.setString(7, box.getString("p_crdate"));					//제작일자
            pstmt.setString(8, box.getString("p_dir"));                		//컨텐츠경로
            pstmt.setString(9, box.getString("p_vodurl"));             		//VOD경로
            pstmt.setString(10, box.getString("p_preurl"));      			//맛보기URL
            pstmt.setInt   (11, box.getInt   ("p_framecnt"));				//총프레임수
            pstmt.setInt   (12, box.getInt   ("p_edulimit"));				//1일최대학습량
            pstmt.setString(13, v_luserid);									//최종수정자
            pstmt.setString(14, FormatDate.getDate("yyyyMMddHHmmss"));		//최종수정일(ldate)
            pstmt.setString(15, box.getString("p_contenttype"));			//컨텐츠타입
            pstmt.setString(16, box.getString("p_betausernm"));				//과정제공회사명
            pstmt.setString(17, box.getString("p_betacompany"));				//컨텐츠담당자
            
            
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
    선택된 과정코드 수정 - 사이버
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
    */
     public int UpdateSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            //update TZ_SUBJ table
            sql = " update TZ_BETASUBJ ";
            sql+= "    set subjnm       = ?, ";
            sql+= "        cuserid      = ?, ";
            sql+= "        cusernm      = ?, ";
            sql+= "        isuse        = ?, ";
            sql+= "        edudays      = ?, ";
            sql+= "        company      = ?, ";
            sql+= "        companynm    = ?, ";
            sql+= "        crdate       = ?, ";
            sql+= "        dir          = ?, ";
            sql+= "        vodurl       = ?, ";
            sql+= "        preurl       = ?, ";
            sql+= "        framecnt     = ?, ";
            sql+= "        edulimit     = ?, ";
            sql+= "        luserid      = ?, ";
            sql+= "        ldate        = ?, ";
            sql+= "        contenttype  = ?  ";       
            sql+= "  where subj         = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, box.getString("p_subjnm"));
            pstmt.setString(2, box.getString("p_betauserid"));
            pstmt.setString(3, box.getString("p_betausernm"));
            pstmt.setString(4, box.getString("p_isuse"));
            pstmt.setInt   (5, box.getInt   ("p_edudays"));
            pstmt.setString(6, box.getString("p_betacompanyno"));
            pstmt.setString(7, box.getString("p_betacompany"));
            pstmt.setString(8, FormatDate.getDate("yyyyMMdd"));
            pstmt.setString(9, box.getString("p_dir"));
            pstmt.setString(10, box.getString("p_vodurl"));
            pstmt.setString(11, box.getString("p_preurl"));
            pstmt.setInt   (12, box.getInt   ("p_framecnt"));
            pstmt.setInt   (13, box.getInt   ("p_edulimit"));
            pstmt.setString(14, v_luserid);
            pstmt.setString(15, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(16, box.getString("p_contenttype"));    
            pstmt.setString(17, box.getString("p_subj"));
			
			System.out.println("subj="+box.getString("p_subj"));
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
    선택된 과정코드 삭제
    @param box      receive from the form object and session
    @return isOk    1:delete success,0:delete fail
    */
    public int DeleteSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int    isOk = 0;

        String v_subj  = box.getString("p_subj");
        try {
            connMgr = new DBConnectionManager();
            //delete TZ_SUBJ table
            sql = "delete from TZ_SUBJ where subj = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_subj);
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

    public ArrayList TargetGrcodeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql  = "";
        SelectionData data = null;

        try {
            sql = "select grcode code, grcodenm name ";
            sql+= "  from tz_grcode ";
            sql+= " order by grcode ";

            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data=new SelectionData();

                data.setCode(ls.getString("code"));
                data.setName(ls.getString("name"));

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

    public ArrayList SelectedGrcodeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql  = "";
        SelectionData data = null;
        String v_subj = box.getString("p_subj");

        try {
            sql = "select a.grcode code, a.grcodenm name ";
            sql+= "  from tz_grcode a, ";
            sql+= "       tz_grsubj b  ";
            sql+= " where a.grcode = b.grcode ";
            sql+= "   and b.subjcourse = " + SQLString.Format(v_subj);
            sql+= " order by a.grcode " ;

            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data=new SelectionData();

                data.setCode(ls.getString("code"));
                data.setName(ls.getString("name"));

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
    새로운 코스코드 등록
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
     public int RelatedGrcodeInsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
        String sql = "";
		String sql2 = "";
		String sql3 = "";
        int isOk = 0;
		int isOk2 = 0;
		int isOk3 = 0;

        String v_subj    = box.getString("p_subj");
        String v_grcode  = "";
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //delete TZ_GRSUBJ table
            sql = "delete from TZ_GRSUBJ where subjcourse = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_subj);
            isOk = pstmt.executeUpdate();

            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }

            String v_selectedgrcodes    = box.getString("p_selectedgrcodes");
            StringTokenizer v_token = new StringTokenizer(v_selectedgrcodes, ";");

            //insert TZ_COURSESUBJ table
            sql =  "insert into TZ_GRSUBJ(grcode, subjcourse, isnew, disseq, grpcode, grpname, luserid, ldate) ";
            sql+=  " values (?, ?, ?, ?, ?, ?, ?, ?)";
			
			//insert TZ_PREVIEW table
			//tz_preview 테이블은 tz_grsubj 테이블처럼 모든 데이타 삭제 후 Insert하지 않고
			//Insert하려는 데이타가 없는 경우에만 tz_subj 테이블의 맛보기 정보를 tz_preview에 Insert한다.
			sql2 = 	"insert into tz_preview(grcode, ";
			sql2+=	"						subj, ";
			sql2+=	"						edumans,";
			sql2+=	"						intro,";
			sql2+=	"						explain,";
			sql2+=	"						luserid,";
			sql2+=	"						ldate ) ";
			sql2+=	"select 				?, "; 				//v_grcode
			sql2+=	"						subj, ";
			sql2+=	"						edumans,";
			sql2+=	"						intro,";
			sql2+=	"						explain,";
			sql2+=	"						?,";				//luserid
			sql2+=	"						?";					//ldate
			sql2+=	"from 					tz_subj a ";
			sql2+=	"where 					subj 	= '" + v_subj + "' and ";
			sql2+=	"						not exists(	select 	grcode, ";
			sql2+=	"											subj ";
			sql2+=	"									from 	tz_preview b ";
			sql2+=	"									where 	b.grcode = ? and "; //v_grcode
			sql2+=	"											b.subj	 = '" + v_subj + "')";
			
			//교육주관연결시 연결되어 있는 교육주관을 삭제하는 경우
			//tz_grsubj 테이블에 없으므로 tz_preview 테이블에 있는 자료도 삭제한다.
			sql3 = "delete ";	
			sql3+= "from 	tz_preview a ";
			sql3+= "where 	a.subj = '" + v_subj + "' and ";
			sql3+= "		not exists(	select 	grcode,";
			sql3+= "							subj ";
			sql3+= "					from 	tz_grsubj ";
			sql3+= "					where 	grcode 	= a.grcode and ";
			sql3+= "							subj	= a.subj)";
			
            pstmt = connMgr.prepareStatement(sql);
			pstmt2 = connMgr.prepareStatement(sql2);
			pstmt3 = connMgr.prepareStatement(sql3);
			

            while (v_token.hasMoreTokens()) {
                v_grcode = v_token.nextToken();

                pstmt.setString(1, v_grcode);
                pstmt.setString(2, v_subj);
                pstmt.setString(3, "Y");
                pstmt.setInt   (4, 0);
                pstmt.setString(5, "");
                pstmt.setString(6, "");
                pstmt.setString(7, v_luserid);
                pstmt.setString(8, FormatDate.getDate("yyyyMMddHHmmss"));
                isOk = pstmt.executeUpdate();
				
				pstmt2.setString(1, v_grcode);
				pstmt2.setString(2, v_luserid);
                pstmt2.setString(3, FormatDate.getDate("yyyyMMddHHmmss"));
				pstmt2.setString(4, v_grcode);
				isOk2 = pstmt2.executeUpdate();
				//isOk2 = 1;
				
				if (isOk == 1 ) {
					connMgr.commit();
				} else {
					connMgr.rollback();
				}
            }
			
			pstmt3.executeUpdate();
			if (isOk > 0) {connMgr.commit();}
        }
        catch(Exception ex) {
            isOk = 0;
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

    public ArrayList PreviewGrcodeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql  = "";
        SelectionData data = null;
        String v_subj = box.getString("p_subj");
		System.out.println("p_subj="+box.getString("p_subj"));

        try {
            sql = "select a.grcode code, a.grcodenm name ";
            sql+= "  from tz_grcode  a, ";
            sql+= "       tz_preview b  ";
            sql+= " where a.grcode = b.grcode ";
            sql+= "   and b.subj = " + SQLString.Format(v_subj);
            sql+= " order by a.grcode " ;

            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql);
			System.out.println("sql ->" + sql);

            while (ls.next()) {
                data=new SelectionData();

                data.setCode(ls.getString("code"));System.out.println("code="+ls.getString("code"));
                data.setName(ls.getString("name"));System.out.println("name="+ls.getString("name"));

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

    public ArrayList RelatedSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql  = "";
        SelectionData data = null;

        String v_grcode = box.getStringDefault("p_grcode","N000001");
        String v_subj   = box.getString("p_subj");
        String v_subjgubun = box.getString("p_subjgubun");

        try {
            sql = "select a.rsubj code, b.subjnm name";
            sql+= "  from tz_subjrelate a, ";
            sql+= "       tz_subj   b   ";
            sql+= " where a.rsubj = b.subj  ";
            sql+= "   and a.grcode = " + SQLString.Format(v_grcode);
            sql+= "   and a.subj   = " + SQLString.Format(v_subj);
            sql+= "   and a.gubun  = " + SQLString.Format(v_subjgubun);
            sql+= " order by a.subj, b.subjnm";

            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data=new SelectionData();

                data.setCode(ls.getString("code"));
                data.setName(ls.getString("name"));

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

    public ArrayList SelectedRelatedSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";

        ArrayList list1 = null;
        list1 = new ArrayList();
        BetaSubjectInfoData data = null;

        String v_subjectcodes    = box.getString("p_selectedsubjcodes");
        String v_subjecttexts    = box.getString("p_selectedsubjtexts");
        String v_grcode = box.getString("p_grcode");
        String v_subj   = box.getString("p_subj");
        String v_subjgubun= box.getString("p_subjgubun");

        try {
            if (!v_subjectcodes.equals("")) {
                StringTokenizer v_tokencode = new StringTokenizer(v_subjectcodes, ";");
                StringTokenizer v_tokentext = new StringTokenizer(v_subjecttexts, ";");

                String v_code = "";
                String v_text = "";

                while (v_tokencode.hasMoreTokens() && v_tokentext.hasMoreTokens()) {
                    v_code = v_tokencode.nextToken();
                    v_text = v_tokentext.nextToken();

                    data=new BetaSubjectInfoData();

                    data.setSubj(v_code);
                    data.setDisplayname(v_text);

                    list1.add(data);
                }
            } else {
                if (!v_subj.equals("")) {

                    sql = "select c.rsubj, a.subjnm, a.isonoff, b.upperclass, b.classname, d.codenm ";
                    sql+= "  from tz_subj         a, ";
                    sql+= "       tz_subjatt      b, ";
                    sql+= "       tz_subjrelate   c, ";
                    sql+= "       tz_code         d  ";
                    sql+= " where a.subjclass = b.subjclass ";
                    sql+= "   and a.subj   = c.rsubj ";
                    sql+= "   and a.isonoff= d.code ";
                    sql+= "   and c.grcode = " + SQLString.Format(v_grcode);
                    sql+= "   and c.subj   = " + SQLString.Format(v_subj);
                    sql+= "   and c.gubun  = " + SQLString.Format(v_subjgubun);
                    sql+= "   and d.gubun  = " + SQLString.Format(SelectionUtil.ONOFF_GUBUN);
                    sql+= " order by c.subj ";


                    connMgr = new DBConnectionManager();
                    list1 = new ArrayList();
                    ls = connMgr.executeQuery(sql);

                    while (ls.next()) {
                        data=new BetaSubjectInfoData();

                        data.setSubj(ls.getString("rsubj"));
                        data.setSubjnm(ls.getString("subjnm"));
                        data.setIsonoff(ls.getString("isonoff"));
                        data.setUpperclass(ls.getString("upperclass"));
                        data.setClassname(ls.getString("classname"));
                        data.setIsonoffnm(ls.getString("codenm"));

                        list1.add(data);

                    }
                }
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

    public int RelatedSubjInsert(RequestBox box) throws Exception {
       DBConnectionManager connMgr = null;

       PreparedStatement pstmt = null;
       String sql = "";
       int isOk = 0;

       String v_grcode      = box.getString("p_grcode");
       String v_subj        = box.getString("p_subj");
       String v_subjgubun   = box.getString("p_subjgubun");
       String v_luserid     = box.getSession("userid");
       String v_rsubj       = "";

       try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

            //delete TZ_SUBJRELATE table
            sql = "delete from TZ_SUBJRELATE where grcode = ? and subj = ? and gubun = ?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_grcode);
            pstmt.setString(2, v_subj);
            pstmt.setString(3, v_subjgubun);

            isOk = pstmt.executeUpdate();

            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }

            String v_subjectcodes    = box.getString("p_selectedsubjcodes");
            StringTokenizer v_token = new StringTokenizer(v_subjectcodes, ";");

            //insert TZ_SUBJRELATE table
            sql =  "insert into TZ_SUBJRELATE(grcode, subj, gubun, rsubj, luserid, ldate) ";
            sql+=  " values (?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);
            while (v_token.hasMoreTokens()) {
                v_rsubj = v_token.nextToken();

                pstmt.setString(1, v_grcode);
                pstmt.setString(2, v_subj);
                pstmt.setString(3, v_subjgubun);
                pstmt.setString(4, v_rsubj);
                pstmt.setString(5, v_luserid);
                pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
                isOk = pstmt.executeUpdate();
            }
            if (isOk > 0) {connMgr.commit();}
        }
        catch(Exception ex) {
           isOk = 0;
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
}
