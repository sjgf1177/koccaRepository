//**********************************************************
//  1. 제      목: 과정차수관리 빈
//  2. 프로그램명: CPCourseSeqBean.java
//  3. 개      요: 과정차수관리 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 12. 22
//  7. 수      정:
//**********************************************************

package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;

/**
*과정차수관리
*<p>제목:CPCourseSeqBean.java</p>
*<p>설명:과정차수관리 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/

public class CPCourseSeqBean {
    private ConfigSet config;
    private int row;
	    	
    public CPCourseSeqBean() {
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
    public ArrayList selectCourseSeqList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
		/* 2005.11.10_하경태 : TotalCount 해주기 위한 쿼리를 담을 변수 "*/
        String sql     = "";
        String count_sql     = "";
        String head_sql     = "";
		String body_sql     = "";		
        String group_sql     = "";
        String order_sql     = "";
        //BulletinData data = null;
        DataBox dbox = null;
        
        String v_searchtext = box.getString("p_searchtext");
        String v_cp = box.getString("p_cp");			//외주업체
        String v_grcode = box.getString("s_grcode");	//교육주관
        String v_gyear = box.getString("s_gyear");		//교육년도
        String v_grseq = box.getString("s_grseq");		//교육년도
        
        //String v_select = box.getString("p_select");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();

			if(v_gyear.equals("")){
				v_gyear = FormatDate.getDate("yyyy");
				box.put("s_gyear",v_gyear);
			}

			head_sql = "select          \n";
			head_sql += " a.subj,       \n";
			head_sql += " a.subjnm,     \n";
			head_sql += " b.year,       \n";
			head_sql += " b.subjseq,    \n";
			head_sql += " b.propstart,  \n"; 
			head_sql += " b.propend,    \n";
			head_sql += " b.edustart,   \n";
			head_sql += " b.eduend,     \n";
			head_sql += " c.cpnm,       \n";
			head_sql += " b.cpsubjseq,  \n"; 
			head_sql += " b.subjseqgr,   \n";
			head_sql += " b.grcode,   ";
			head_sql += " gr.grcodenm  \n"; 
			body_sql += " from tz_subj a, tz_subjseq b, tz_cpinfo c , tz_grcode gr\n";
			body_sql += " where \n";
			body_sql += " a.subj = b.subj \n";
			body_sql += " and  a.owner = c.cpseq and  b.grcode = gr.grcode ";
	        
			//교육그룹검색(교육주관)
			if(!v_grcode.equals("")){
				body_sql += " and b.grcode = " + SQLString.Format(v_grcode);
				//과정명검색
				if ( !v_searchtext.equals("")) {	//    검색어가 있으면
	                //v_pageno = 1;	//      검색할 경우 첫번째 페이지가 로딩된다
					body_sql += " and lower(a.subjnm) like " + SQLString.Format("%"+v_searchtext.toLowerCase()+"%");
	            }
	            //교육년도 검색
	            if(!v_gyear.equals("") && !v_gyear.equals("ALL")){
					body_sql += " and b.gyear = " + SQLString.Format(v_gyear);
	            }
	            //교육년도 검색
	            if(!v_grseq.equals("") && !v_grseq.equals("ALL")){
					body_sql += " and b.grseq = " + SQLString.Format(v_grseq);
	            }
	            else if(v_gyear.equals("")){
					body_sql += " and  b.gyear = " + SQLString.Format(FormatDate.getDate("yyyy"));
	            }
	            //외주업체 검색
	            if(!v_cp.equals("") && !v_cp.equals("ALL")){
					body_sql += " and a.owner = " + SQLString.Format(v_cp);
	            }
	        }
	        
			sql = head_sql + body_sql + group_sql + order_sql;		
			
            ls = connMgr.executeQuery(sql);
			
			count_sql = "select count(*) " + body_sql;      		
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);
            
            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);                    //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다

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
    과정업체차수 등록
    @param	box		receive from the form object and session
    @return	int		업체차수등록 정상처리여부(1:update success,0:update fail)
    */
     public int insertSubjSeq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk1 = 1;
        
		String v_subj = box.getString("p_subj");
		String v_subjseq = box.getString("p_subjseq");
		String v_cpsubjseq = box.getString("p_cpsubjseq");
        String v_grcode = box.getString("s_grcode");	//교육주관
        String v_gyear = box.getString("s_gyear");		//교육년도
        String v_year = box.getString("p_year");		//과정년도

        try {
            connMgr = new DBConnectionManager();
            
            //////////////////////////////////   과정차수정보 table 수정  ///////////////////////////////////////////////////////////////////
            sql1 =  "update tz_subjseq set cpsubjseq = ? ";
            sql1 += "  where subj = ? ";
            sql1 += "  and subjseq = ? ";
            sql1 += "  and grcode = ? ";
            sql1 += "  and year = ? ";
            
            
            pstmt1 = connMgr.prepareStatement(sql1);
            
            pstmt1.setString(1, v_cpsubjseq);
            pstmt1.setString(2, v_subj);                         
            pstmt1.setString(3, v_subjseq);
            pstmt1.setString(4, v_grcode);   
            pstmt1.setString(5, v_year);
            
            isOk1 = pstmt1.executeUpdate();            
 
            Log.info.println(this, box, "update process to " + v_cpsubjseq);
        }
        catch(Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql1);            
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {       
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }	


    /**
    선택된 과정업체차수정보
    @param	box			receive from the form object and session
    @return	DataBox		과정업체차수 정보
    */
	public DataBox selectCourseCPseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
		/* 2005.11.10_하경태 : TotalCount 해주기 위한 쿼리를 담을 변수 "*/
        String sql     = "";
        String count_sql     = "";
        String head_sql     = "";
		String body_sql     = "";		
        String group_sql     = "";
        String order_sql     = "";
        DataBox dbox = null;
                
        //String v_seq = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_cpseq")), 5);
		String v_subj = box.getString("p_subj");
		String v_subjseq = box.getString("p_subjseq");
        String v_grcode = box.getString("s_grcode");	//교육주관
        String v_gyear = box.getString("s_gyear");		//교육년도
        String v_year = box.getString("p_year");		//과정년도

        try {
            connMgr = new DBConnectionManager();

			head_sql = "select          \n";
			head_sql += "   a.subj,     \n";
			head_sql += "   a.subjnm,   \n";
			head_sql += "   b.year,     \n";
			head_sql += "   b.subjseq,  \n";
			head_sql += "   b.propstart,\n"; 
			head_sql += "   b.propend,  \n";
			head_sql += "   b.edustart, \n";
			head_sql += "   b.eduend,   \n";
			head_sql += "   b.cpsubjseq \n"; 
			body_sql += " from ";
			body_sql += "   tz_subj a, tz_subjseq b ";
			body_sql += " where ";
			body_sql += "   a.subj = b.subj and a.subj = " + SQLString.Format(v_subj);
			body_sql += "   and b.subjseq = " + SQLString.Format(v_subjseq);
			body_sql += "   and b.year = " + SQLString.Format(v_year);
			body_sql += "   and b.grcode = " + SQLString.Format(v_grcode);
			
			sql = head_sql + body_sql + group_sql + order_sql;		
			
            ls = connMgr.executeQuery(sql);
			
            while(ls.next()) {
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
	과정차수데이터 조회
	@param box          receive from the form object and session
	@return CPSubjseqData   
	**/
	public CPSubjseqData SelectSubjseqData(RequestBox box) throws Exception {               
		String v_subj = box.getString("p_subj");
		String v_year = box.getString("p_year");            
		String v_subjseq = box.getString("p_subjseq");
		CPSubjseqData data = null;
		
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql  = "";
	
		try {
			sql  = "select subj          ,year          ,subjseq       ,grcode        ,gyear         ,\n";
			sql += "       grseq         ,isbelongcourse,course        ,cyear         ,courseseq     ,\n";
			sql += "       propstart     ,propend       ,edustart      ,eduend        ,isclosed      ,\n";
			sql += "       isgoyong      ,ismultipaper  ,subjnm        ,luserid       ,ldate         ,\n";
			sql += "       studentlimit  ,point         ,biyong        ,edulimit      ,warndays      ,\n";
			sql += "       stopdays      ,gradscore     ,gradstep      ,wstep         ,wmtest        ,\n";
			sql += "       wftest        ,wreport       ,wact          ,wetc1         ,wetc2         ,\n";
			sql += "       endfirst      ,endfinal	   ,proposetype, \n";
			sql += "	   whtest		,score,			gradreport	   ,gradexam	  , \n";
			sql += "	   rndcreditreq	,rndcreditchoice			   ,rndcreditadd  ,rndcreditdeduct,\n";
			sql += "	   isablereview,tsubjbudget,rndjijung,isusebudget,cpsubjseq, \n";
			sql += "	   gradftest, gradhtest \n";
			sql += "  from tz_subjseq where subj="+SQLString.Format(v_subj);
			sql += " and year="+SQLString.Format(v_year);
			sql += " and subjseq="+SQLString.Format(v_subjseq);

			connMgr = new DBConnectionManager();
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				data=new CPSubjseqData();  
				data.setSubj           (ls.getString("subj")) ;
				data.setYear           (ls.getString("year")) ;
				data.setSubjseq        (ls.getString("subjseq")) ;
				data.setGrcode         (ls.getString("grcode")) ;
				data.setGyear          (ls.getString("gyear")) ;
				data.setGrseq          (ls.getString("grseq")) ;
				data.setIsbelongcourse (ls.getString("isbelongcourse")) ;
				data.setCourse         (ls.getString("course")) ;
				data.setCyear          (ls.getString("cyear")) ;
				data.setCourseseq      (ls.getString("courseseq")) ;
				data.setPropstart      (ls.getString("propstart")) ;
				data.setPropend        (ls.getString("propend")) ;
				data.setEdustart       (ls.getString("edustart")) ;
				data.setEduend         (ls.getString("eduend")) ;
				data.setEndfirst       (ls.getString("endfirst")) ;
				data.setEndfinal       (ls.getString("endfinal")) ;
				data.setIsclosed       (ls.getString("isclosed")) ;
				data.setIsgoyong       (ls.getString("isgoyong")) ;
				data.setIsmultipaper   (ls.getString("ismultipaper")) ;
				data.setSubjnm         (ls.getString("subjnm")) ;
				data.setLuserid        (ls.getString("luserid")) ;
				data.setLdate          (ls.getString("ldate")) ;
				data.setStudentlimit   (ls.getInt("studentlimit"));
				data.setPoint          (ls.getInt("point"));
				data.setBiyong         (ls.getInt("biyong"));
				data.setEdulimit       (ls.getInt("edulimit"));	
				data.setWarndays       (ls.getInt("warndays"));
				data.setStopdays       (ls.getInt("stopdays"));
				data.setGradscore      (ls.getInt("gradscore"));
				data.setGradstep       (ls.getInt("gradstep"));
				data.setWstep          (ls.getDouble("wstep"));
				data.setWmtest         (ls.getDouble("wmtest"));
				data.setWftest         (ls.getDouble("wftest"));
				data.setWreport        (ls.getDouble("wreport"));
				data.setWact           (ls.getDouble("wact"));
				data.setWetc1          (ls.getDouble("wetc1"));
				data.setWetc2          (ls.getDouble("wetc2"));
				data.setProposetype    (ls.getString("proposetype"));  
				data.setGrcodenm	   (GetCodenm.get_grcodenm(data.getGrcode()));
				data.setGrseqnm		   (GetCodenm.get_grseqnm(data.getGrcode(), data.getGyear(), data.getGrseq()));
				
				data.setGradreport      (ls.getInt("gradreport"));
				data.setGradexam       (ls.getInt("gradexam"));
				data.setWhtest         (ls.getDouble("whtest"));
				data.setScore          (ls.getInt("score"));
				
				data.setRndcreditreq         (ls.getDouble("rndcreditreq"));
				data.setRndcreditchoice      (ls.getDouble("rndcreditchoice"));
				data.setRndcreditadd         (ls.getDouble("rndcreditadd"));
				data.setRndcreditdeduct      (ls.getDouble("rndcreditdeduct"));
				data.setRndjijung      		 (ls.getString("rndjijung"));
				data.setIsablereview         (ls.getString("isablereview"));
				data.setTsubjbudget        	(ls.getInt("tsubjbudget"));
				data.setIsusebudget        	(ls.getString("isusebudget"));
				data.setCPsubjseq        	(ls.getString("cpsubjseq"));
				data.setGradftest       	(ls.getInt("gradftest"));
				data.setGradhtest       	(ls.getInt("gradhtest"));
				

			}
			
			sql = "select isgoyong from tz_subj where subj="+SQLString.Format(v_subj);
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			ls = connMgr.executeQuery(sql);
			if(ls.next())	data.setSubj_isgoyong(ls.getString("isgoyong"));
			else			data.setSubj_isgoyong("N");

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
	과정차수정보 수정
	@param box      receive from the form object and session
	@return isOk    1:update success,0:update fail    
	**/
     public int UpdateSubjectSeq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;        
        
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            
            //update TZ_SUBJSEQ table
            sql = " update TZ_SUBJSEQ ";
            sql+= "    SET point        = ?, ";
            sql+= "        gradscore    = ?, ";
            sql+= "        gradstep     = ?, ";
            sql+= "        wstep        = ?, ";
            sql+= "        wmtest       = ?, ";
            sql+= "        wftest       = ?, ";
            sql+= "        wreport      = ?, ";
            sql+= "        wact         = ?, ";
            sql+= "        wetc1        = ?, ";
            sql+= "        wetc2        = ?, ";
            
			sql+= "	 	   gradexam		= ?, ";
			sql+= "		   gradftest	= ?, ";
			sql+= "		   gradhtest	= ?, ";
			sql+= "		   gradreport	= ?, ";
			sql+= "        whtest		= ?, ";
			sql+= "		   cpsubjseq 	= ?, ";
			sql+= "		   luserid		= ?, ";
			sql+= "		   ldate		= to_char(sysdate,'YYYYMMDDHH24MISS') ";

			sql+= "  where subj 		= ? and ";
			sql+= "		   year			= ? and ";
			sql+= "		   subjseq		= ?";



            pstmt = connMgr.prepareStatement(sql);		
            
            pstmt.setInt   (1, box.getInt   ("p_point")); 					//이수점수
            pstmt.setInt   (2, box.getInt   ("p_gradscore")); 				//이수기준(점수)
            pstmt.setInt   (3, box.getInt   ("p_gradstep")); 				//이수기준(출석율)
            pstmt.setDouble(4, box.getDouble("p_wstep")); 					//가중치(진도율)
            pstmt.setDouble(5, box.getDouble("p_wmtest")); 					//가중치(중간평가)
            pstmt.setDouble(6, box.getDouble("p_wftest")); 					//가중치(최종평가)
            pstmt.setDouble(7, box.getDouble("p_wreport")); 				//가중치(리포트)
            pstmt.setDouble(8, box.getDouble("p_wact")); 					//가중치(액티비티)
            pstmt.setDouble(9, box.getDouble("p_wetc1")); 					//가중치(기타1)
            pstmt.setDouble(10, box.getDouble("p_wetc2")); 					//가중치(기타2)
			
			//------------------------------------------------------------------------------//
            pstmt.setInt   (11, box.getInt("p_gradexam")); 					//이수기준(중간평가)				//
			pstmt.setInt(12, box.getInt("p_gradftest")); 				//이수기준(최종평가)				//
			pstmt.setInt(13, box.getInt("p_gradhtest")); 				//이수기준(형성평가)				//
            pstmt.setInt   (14, box.getInt("p_gradreport")); 				//이수기준(리포트)
            pstmt.setDouble   (15, box.getDouble("p_whtest")); 					//가중치(형성평가)
			//------------------------------------------------------------------------------//
			pstmt.setString(16, box.getString("p_cpsubjseq"));
			pstmt.setString(17, v_luserid);
            pstmt.setString(18, box.getString("p_subj"));
            pstmt.setString(19, box.getString("p_year"));
            pstmt.setString(20, box.getString("p_subjseq"));


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
    교육그룹별 회사리스트
    @param	box			receive from the form object and session
    @return	ArrayList	교육그룹별 회사리스트
    */
    public ArrayList selectGrcompList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        
        String v_grcode = box.getString("p_grcode");	//교육주관

        try {
            connMgr = new DBConnectionManager();            
            list = new ArrayList();

			sql += " select                                       \n";
            sql += "   b.comp,                                      \n";
            sql += "   b.companynm                                  \n";
            sql += " from                                         \n";
            sql += "   tz_grcomp a, tz_comp b                     \n";
            sql += " where                                        \n";
            sql += "   grcode = "+SQLString.Format(v_grcode)+"    \n";
            sql += "   and a.comp = b.comp                        \n";

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

}
