
//**********************************************************
//  1. 제      목: 상담 관리(mail/sms)
//  2. 프로그램명 : CounselMailBean.java
//  3. 개      요: 상담 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신상철 2008. 9. 29
//  7. 수      정:
//**********************************************************

package com.credu.study;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * 상담 관리(ADMIN)
 *
 * @date   : 2008. 9
 * @author : s.c Sinn
 */
@SuppressWarnings("unchecked")
public class SpotStudyBean {
	
	/**
    @param box          receive from the form object and session
    @return ArrayList   
	*/
	public ArrayList SelecSubjComplRateList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;
		String v_userid = box.getString("p_userid");
		String v_year = box.getString("p_year");
		
		String v_subjcourse = box.getString("s_subjcourse");
		String v_grcode = "N000001";
		String v_stdt = box.getString("p_stdt");
		String v_edt = box.getString("p_edt");
		String v_gubun = box.getString("p_gubun1");
		
		String ss_action = box.getString("s_action");
		
		try {
			if (ss_action.equals("go")) {
				connMgr = new DBConnectionManager();
				list = new ArrayList();
	
				sql.append("   SELECT   MAX(SUBJ) SUBJ , MAX(SUBJNM) SUBJNM, AVG(TSTEP) TSTEP  \n");
				sql.append("        ,  CASE WHEN SUM(ISGRADUATED) > 0 THEN SUM(ISGRADUATED) ELSE 0 END   GRADUATE            \n"); 
				sql.append("        ,  CASE WHEN  SUM(EDU) > 0 THEN SUM(EDU) ELSE 0 END   EDU             \n");
				sql.append("		,  SUM(ISGRADUATED) / COUNT(USERID) * 100 COMPL_RATE     \n");
				sql.append("   FROM (          \n");
				sql.append("  SELECT  C.SUBJ          \n");  
				sql.append("      ,   C.SUBJNM         \n");
				sql.append("      ,   D.EDUSTART       \n");
				sql.append("      ,   D.EDUEND           \n");
				sql.append("      ,   D.TSTEP        \n");
				sql.append("  ,   DECODE(D.ISGRADUATED, 'Y','1' ) ISGRADUATED  \n");                                                   
				sql.append("  ,   DECODE(D.ISGRADUATED, 'N','1' ) EDU                 \n");          
				sql.append("  ,  D.USERID    \n");  
				sql.append("  , (      \n");
				sql.append("  	select isalways from tz_grseq  \n");  
				sql.append("  	 where grcode = a.grcode       \n");
				sql.append("  	  and gyear = a.gyear          \n");
				sql.append("  	  and grseq = a.grseq       \n");
				sql.append("  	)    ISALWAYS     \n");
				sql.append("  FROM  TZ_SUBJSEQ A          \n");
				sql.append("  	,   TZ_PROPOSE B          \n");
				sql.append("  	,   TZ_SUBJ C            \n");
				sql.append("  	,   TZ_STUDENT D         \n");
				sql.append("  	,   TZ_STOLD E           \n");
				sql.append("  	,   TZ_MEMBER F           \n");
				sql.append("  WHERE  A.GRCODE = 'N000001'    \n");
				sql.append("  --  AND  A.EDUEND < TO_CHAR(SYSDATE, 'YYYYMMDDHH24')   \n");  
				sql.append("  	AND  B.YEAR = A.YEAR           \n");
				sql.append("  	AND  B.SUBJ = A.SUBJ          \n");
				sql.append("    AND  B.SUBJSEQ = A.SUBJSEQ    \n");
				sql.append(" 	AND  B.SUBJ = C.SUBJ          \n"); 
				sql.append("  	AND  B.YEAR = D.YEAR          \n");
				sql.append("  	AND  B.SUBJ = D.SUBJ          \n");
				sql.append("  	AND  B.SUBJSEQ = D.SUBJSEQ    \n");
				sql.append("  	AND  B.USERID = D.USERID      \n");
				sql.append("  	AND  B.YEAR = E.YEAR(+)       \n");
				sql.append("  	AND  B.SUBJ = E.SUBJ(+)          \n");      
				sql.append("  	AND  B.SUBJSEQ = E.SUBJSEQ(+)     \n");
				sql.append("  	AND  B.USERID = E.USERID(+)       \n");
				sql.append("  	AND  A.GRCODE = F.GRCODE           \n");
				sql.append("  	AND  B.USERID = F.USERID             \n");
				sql.append("  ORDER  BY A.EDUSTART DESC, C.SUBJNM      \n");
				sql.append("  	) A   \n");
	            sql.append("  WHERE 1=1       \n");
	            sql.append("    AND ISALWAYS = 'Y' \n");
	            sql.append("  --AND SUBJ = 'CB12003' \n");	            
	            if(v_gubun.equals("1"))
	            	sql.append("   AND  EDUSTART BETWEEN '").append(v_stdt).append("' AND '").append(v_edt).append("'  \n");
	            else 
	            	sql.append("   AND  EDUEND BETWEEN '").append(v_stdt).append("' AND '").append(v_edt).append("'    \n");
	            
	            if(!v_subjcourse.equals("ALL")){
		            sql.append("   AND  A.SUBJ = '").append(v_subjcourse).append("' \n");
				}
	            
	            sql.append("  GROUP BY SUBJ   \n");
				ls = connMgr.executeQuery(sql.toString());
	
				while (ls.next()) {
					dbox = ls.getDataBox();
	
					list.add(dbox);
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	/**
    @param box          receive from the form object and session
    @return ArrayList   
	*/
	public ArrayList SelectCompleteList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;
		String v_userid = box.getString("p_userid");
		String v_subj = box.getString("p_subj");
		
		String v_subjcourse = box.getString("s_subjcourse");
		String v_grcode = "N000001";
		String v_stdt = box.getString("p_stdt");
		String v_edt = box.getString("p_edt");
		String v_gubun1 = box.getString("p_gubun1");
		String v_gubun2 = box.getString("p_gubun2");
		String v_gubun3 = box.getString("p_gubun3");
		String v_searchTxt = box.getString("p_searchTxt");
		String ss_action = box.getString("s_action");
		
		try {
			if (ss_action.equals("go")) {
				connMgr = new DBConnectionManager();
				list = new ArrayList();
	
				 
				sql.append("	SELECT    A.SUBJ, B.SUBJNM,  A.SUBJSEQ, A.SERNO, A.EDUSTART, A.EDUEND, A.TSTEP, A.ISGRADUATED, A.ISGRADUATED_TXT, A.USERID, B.GRCODE, NAME, MEMBERGUBUN, EDULATEST   \n");
				sql.append("       FROM (                                                                                                                                                            \n");
				sql.append("             SELECT USERID, EDUSTART, EDUEND ,SERNO, SUBJ, YEAR, SUBJSEQ, TSTEP, NAME, MEMBERGUBUN, ISGRADUATED_TXT, ISGRADUATED,  EDULATEST                             \n");
				sql.append("               FROM                                                                                                                                                      \n");
				sql.append("                   (                                                                                                                                                     \n");
				sql.append("                     SELECT A.USERID, A.EDUSTART, A.EDUEND, B.SERNO, A.SUBJ, A.YEAR, A.SUBJSEQ, A.TSTEP                                                                  \n");
				sql.append("                      , (SELECT NAME FROM TZ_MEMBER WHERE USERID = C.USERID AND GRCODE = C.ASP_GUBUN) NAME                                                               \n");
				sql.append("                      , (SELECT DECODE(MEMBERGUBUN, 'P', '개인')  FROM TZ_MEMBER WHERE USERID = C.USERID AND GRCODE = C.ASP_GUBUN) MEMBERGUBUN                           \n");
				sql.append("                      ,   DECODE(B.ISGRADUATED, 'Y','수료' ,'진행중' ) ISGRADUATED_TXT, B.ISGRADUATED                                                                    \n");
				sql.append("                      , FIRST_EDU  EDULATEST                                                                                                                             \n");
				sql.append("                      , RANK() OVER(PARTITION by D.SUBJ ORDER BY FIRST_EDU DESC) LATESTSEQ                                                                               \n");
				sql.append("                      FROM TZ_STUDENT A , TZ_STOLD B , TZ_PROPOSE C, TZ_PROGRESS D                                                                                       \n");
				sql.append("                      WHERE A.SUBJ = B.SUBJ (+)                                                                                                                          \n");
				sql.append("                          AND A.YEAR = B.YEAR (+)                                                                                                                        \n");
				sql.append("                          AND A.SUBJSEQ = B.SUBJSEQ (+)                                                                                                                  \n");
				sql.append("                          AND A.USERID = B.USERID (+)                                                                                                                    \n");
				sql.append("                          AND A.SUBJ = C.SUBJ                                                                                                                            \n");
				sql.append("                          AND A.YEAR = C.YEAR                                                                                                                            \n");
				sql.append("                          AND A.SUBJSEQ = C.SUBJSEQ                                                                                                                      \n");
				sql.append("                          AND A.USERID = C.USERID                                                                                                                        \n");
				sql.append("                          AND A.SUBJ = D.SUBJ(+)                                                                                                                         \n");
				sql.append("                          AND A.YEAR = D.YEAR(+)                                                                                                                         \n");
				sql.append("                          AND A.SUBJSEQ = D.SUBJSEQ(+)                                                                                                                   \n");
				sql.append("                          AND A.USERID = D.USERID(+)                                                                                                                     \n");
				if(v_gubun1.equals("1"))
	            	sql.append("   AND  A.EDUSTART BETWEEN '").append(v_stdt).append("' AND '").append(v_edt).append("'  \n");
	            else 
	            	sql.append("   AND  A.EDUEND BETWEEN '").append(v_stdt).append("' AND '").append(v_edt).append("'    \n");
				if(!v_gubun2.equals("ALL")){
		            if(v_gubun2.equals("1"))
		            	sql.append("   AND  A.ISGRADUATED = 'Y'          \n");
		            else 
		            	sql.append("   AND  NVL(A.ISGRADUATED, 'N') = 'N'  \n");
				}
				sql.append("                    ) A                                                                                                                                                  \n");
				sql.append("                WHERE LATESTSEQ(+) = 1                                                                                                                                   \n");
				sql.append("                ) A ,                                                                                                                                                    \n");
				sql.append("              (                                                                                                                                                          \n");
				sql.append("                SELECT A.SUBJ, A.SUBJNM, B.YEAR, B.GRCODE, SUBJSEQ                                                                                                       \n");
				sql.append("                FROM TZ_SUBJ A, TZ_SUBJSEQ B, TZ_GRSEQ C                                                                                                                 \n");
				sql.append("                WHERE A.SUBJ = B.SUBJ                                                                                                                                    \n");
				sql.append("                    AND B.GRCODE = C.GRCODE                                                                                                                              \n");
				sql.append("                    AND B.YEAR = C.GYEAR                                                                                                                                 \n");
				sql.append("                    AND B.GRSEQ = C.GRSEQ                                                                                                                                \n");
				sql.append("                    AND B.GRCODE = '").append(v_grcode).append("'                                                                                                                             \n");
				sql.append("                    AND ISALWAYS = 'Y'                                                                                                                                   \n");
				sql.append("             ) B                                                                                                                                                         \n");
				sql.append("    WHERE A.SUBJ = B.SUBJ                                                                                                                                                \n");
				sql.append("        AND A.YEAR = B.YEAR                                                                                                                                              \n");
				sql.append("        AND A.SUBJSEQ = B.SUBJSEQ                                                                                                                                        \n");
	            
	            
	            
	            
	            if(!v_searchTxt.equals("")){
	                if(v_gubun3.equals("1"))
		            	sql.append("   AND  USERID = '").append(v_searchTxt).append("' ");
		            else 
		            	sql.append("   AND  NAME like '%").append(v_searchTxt).append("%' ");
	            }
	            
	            if(!v_subjcourse.equals("ALL")){
		            sql.append("   AND  A.SUBJ = '").append(v_subjcourse).append("' ");
				}
				ls = connMgr.executeQuery(sql.toString());
	
				while (ls.next()) {
					dbox = ls.getDataBox();
	
					list.add(dbox);
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}
}
