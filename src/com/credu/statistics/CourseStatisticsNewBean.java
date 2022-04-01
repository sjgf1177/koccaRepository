package com.credu.statistics;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class CourseStatisticsNewBean {

    /**
     * 정규(상시) 과정별 통계 오류수정ver. by rsg 
     * @param box
     * @return  
     * @throws Exception
     */
    public ArrayList<DataBox> selectCourseStatisticList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        StringBuffer bwtweenSQL = new StringBuffer();
        DataBox dbox = null;

        String searchStartDate = box.getString("searchStartDate");
        String searchEndDate = box.getString("searchEndDate");
        String dateGubun = box.getString("searchDateGubun");
        String chkStep = box.getString("chkStep");
        String gubun = box.getString("searchGubun");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();
            // 조회기간
            bwtweenSQL.append(" BETWEEN '").append(searchStartDate).append("' and '").append(searchEndDate).append("'");

            sql.append("/* com.credu.statics.CourseStatisticsBean selectCourseStatisticList */ \n");
            sql.append("\n select area, get_codenm('0101', area) as areanm, subj, subjnm, max(crdate) as crdate,					");
		    sql.append("\n        sum(student1) as student_cnt_b2c									");
		    //sql.append("\n        sum(student2) as student_cnt_b2b, sum(isgraduated2) as isgraduated_b2b,									");
		    //sql.append("\n        case when sum(sul_cnt) = 0 then 0																	");
		    //sql.append("\n              else round(sum(distcode1_avg) / sum(sul_cnt), 2)                                            ");
		    //sql.append("\n         end distcode1_avg                                                                                ");
		    sql.append("\n  from (                                                                                                  ");
		    sql.append("\n          select a.grcode, b.userid, b.edustart, a.subj, c.subjnm, a.subjseq, b.tstep, b.eduend, c.area, ");
//		    sql.append("\n                  decode( b.isgraduated , 'Y', 1, 0) as isgraduated,                                      ");
		    sql.append("\n                  substr(c.crdate, 1, 4) as crdate, 1 student1                             ");
//		    sql.append("\n                  case when a.grcode  = 'N000001' and b.isgraduated = 'Y' then 1 else 0 end isgraduated1, ");
//		    sql.append("\n                  case when a.grcode != 'N000001' then 1 else 0 end student2,                             ");
//		    sql.append("\n                  case when a.grcode != 'N000001' and b.isgraduated = 'Y' then 1 else 0 end isgraduated2, ");
//		    sql.append("\n                  nvl(e.distcode1_avg, 0) as distcode1_avg,                                               ");
//		    sql.append("\n                  case when e.distcode1_avg is null then 0 else 1 end as sul_cnt                          ");
		    sql.append("\n            from tz_subjseq a                                                                             ");
//		    sql.append("\n           inner join (  select subj, year, subjseq, userid, tstep,                                       ");
		    sql.append("\n           inner join (  	                                       											");
		    sql.append("\n           		SELECT  	                                       										");
		    sql.append("\n           			his.USERID, his.SUBJ, his.YEAR, his.SUBJSEQ, his.EDUSTART, min(his.EDUEND) EDUEND, max(his.isgraduated) isgraduated, min(his.TABLENM) TABLENM, max(his.tstep) tstep  	                                       											");
		    sql.append("\n           		FROM (	  	                                       											");
		    
		    sql.append("\n           			SELECT st.subj, st.year, st.subjseq, st.userid, st.tstep, st.isgraduated, st.EDUSTART, st.EDUEND, 'TZ_STUDENT' TABLENM  	                                       											");
		    sql.append("\n           			FROM TZ_STUDENT st  	                                       											");
		    sql.append("\n           			LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ=st.SUBJ AND sbj.YEAR=st.YEAR AND st.SUBJSEQ=st.SUBJSEQ  	                                       											");
		    sql.append("\n           			WHERE 1=1  	                                       											");
		    sql.append("\n           			  AND sbj.GRCODE = 'N000001'	                                       											");
		    sql.append("\n           			  AND (	                                       											");
		    sql.append("\n           			  	 (sbj.grseq   = '0017' and substr(st.EDUSTART,1,8) "+bwtweenSQL+")                                      											");
		    sql.append("\n           			  	  or                                     											");
		    sql.append("\n           			  	 (sbj.grseq  != '0017' and substr(sbj.EDUSTART,1,8) "+bwtweenSQL+")                                      											");
		    sql.append("\n           			  	  )                                     											");
		    sql.append("\n           			UNION ALL  -- !!!!!!!!!!!!!	                                       											");
		    sql.append("\n           			SELECT rehis.subj, rehis.year, rehis.subjseq, rehis.userid, to_number(rehis.tstep), rehis.isgraduated, rehis.EDUSTART, rehis.EDUEND, 'TZ_LESSON_HISTORY' TABLENM  	                                       											");
		    sql.append("\n           			FROM tz_lesson_history rehis  	                                       											");
		    sql.append("\n           			LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ = rehis.SUBJ AND sbj.YEAR = rehis.YEAR AND sbj.SUBJSEQ = rehis.SUBJSEQ  	                                       											");
		    sql.append("\n           			WHERE 1=1  	                                       											");
		    sql.append("\n           			  AND sbj.GRCODE = 'N000001'	                                       											");
		    sql.append("\n           			  AND substr(rehis.EDUSTART, 1, 8) "+bwtweenSQL+"	                                       											");
		    sql.append("\n           			UNION ALL  -- !!!!!!!!!!!!!	                                       											");
		    sql.append("\n           			SELECT st.subj, st.year, st.subjseq, st.userid, st.tstep, st.isgraduated, st.EDUSTART, st.EDUEND, 'TZ_STOLD' TABLENM  	                                       											");
		    sql.append("\n           			FROM TZ_STOLD st  	                                       											");
		    sql.append("\n           			LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ=st.SUBJ AND sbj.YEAR=st.YEAR AND sbj.SUBJSEQ=st.SUBJSEQ  	                                       											");
		    sql.append("\n           			WHERE 1=1  	                                       											");
		    sql.append("\n           			  AND sbj.GRCODE = 'N000001'	                                       											");
		    sql.append("\n           			  AND substr(st.EDUSTART,1,8) "+bwtweenSQL+"	                                       											");
		    
		    sql.append("\n           		) his	  	                                       											");
		    sql.append("\n           		GROUP BY his.USERID, his.SUBJ, his.YEAR, his.SUBJSEQ, his.EDUSTART	  	                                       											");
		    
//		    sql.append("\n                                isgraduated, edustart, eduend                                             ");
//		    sql.append("\n                           from tz_student                                                                ");
//		    sql.append("\n                          union all                                                                       ");
//		    sql.append("\n                         select subj, year, subjseq, userid, to_number(tstep),                            ");
//		    sql.append("\n                                isgraduated, edustart, eduend                                             ");
//		    sql.append("\n                           from tz_lesson_history ) b                                                     ");
		    sql.append("\n                       ) b                            							                        ");
		    sql.append("\n              on a.subj = b.subj                                                                          ");
		    sql.append("\n             and a.year = b.year                                                                          ");
		    sql.append("\n             and a.subjseq = b.subjseq                                                                    ");
		    sql.append("\n           inner join tz_subj c                                                                           ");
		    sql.append("\n              on a.subj = c.subj                                                                          ");
		    sql.append("\n           inner join tz_member d                                                                         ");
		    sql.append("\n              on a.grcode = d.grcode                                                                      ");
		    sql.append("\n             and b.userid = d.userid                                                                      ");
		    sql.append("\n            left outer join tz_suleach e                                                                  ");
		    sql.append("\n              on a.grcode = e.grcode                                                                      ");
		    sql.append("\n             and a.subj = e.subj                                                                          ");
		    sql.append("\n             and a.year = e.year                                                                          ");
		    sql.append("\n             and a.subjseq = e.subjseq                                                                    ");
		    sql.append("\n             and b.userid = e.userid                                                                      ");
//		    sql.append("\n           where b.tstep > 0                                                                              ");
		    sql.append("\n           where 1=1                                                                              ");
if( "".equals(chkStep) ) sql.append("\n   AND b.tstep > 0				                                                                            ");
//		    sql.append("\n           					                                                                            ");
//		    sql.append("\n             and (                                                                                        "); 
//		    if(gubun.equals("ALL") || gubun.equals("B2B")){
//	    		sql.append("\n                   -- B2B                                                                                 ");
//			    sql.append("\n                      (a.grcode != 'N000001' and a.").append(dateGubun).append(" between ").append("'").append(searchStartDate).append("0000' and '").append(searchEndDate).append("2359'").append(" )         ");      
//		    }
//		    if(gubun.equals("ALL")){
//		    	sql.append("\n                      OR                                                                                  ");
//		    }
//		     
//		    if(gubun.equals("ALL") || gubun.equals("B2C")){
//		    	sql.append("\n                   -- B2C                                                                                 ");
//		    	sql.append("\n                      (                                                                                   ");
//		    	sql.append("\n                            a.grcode  = 'N000001'                                                         ");
//		    	sql.append("\n                        and (                                                                             ");
//		    	sql.append("\n                              (grseq   = '0017' and b.").append(dateGubun).append(" between ").append(bwtweenSQL).append(")           ");
//		    	sql.append("\n                              or                                                                          ");
//		    	sql.append("\n                              (grseq  != '0017' and a.").append(dateGubun).append(" between ").append(bwtweenSQL).append(")           ");
//		    	sql.append("\n                            )                                                                             ");
//		    	sql.append("\n                       )                                                                                  ");
//		    }
//		    sql.append("\n                  )                                                                                       ");
		    sql.append("\n        ) t                                                                                               ");
		    sql.append("\n group by area, subj, subjnm                                                                              ");
		    sql.append("\n order by area, subj                                                                                      ");
            

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }
    
    /**
     * 정규(상시) 과정별 통계 LowData 오류수정ver. by rsg  
     * @param box 
     * @return  
     * @throws Exception
     */
    public ArrayList<DataBox> selectCourseStatisticLowDataList(RequestBox box) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	ListSet ls = null;
    	ArrayList<DataBox> list = null;
    	StringBuffer sql = new StringBuffer();
    	StringBuffer bwtweenSQL = new StringBuffer();
    	DataBox dbox = null;
    	
    	String searchStartDate = box.getString("searchStartDate");
    	String searchEndDate = box.getString("searchEndDate");
    	String dateGubun = box.getString("searchDateGubun");
    	String chkStep = box.getString("chkStep");
    	String gubun = box.getString("searchGubun");
    	
    	try {
    		connMgr = new DBConnectionManager();
    		list = new ArrayList<DataBox>();
    		// 조회기간
    		bwtweenSQL.append(" BETWEEN '").append(searchStartDate).append("' and '").append(searchEndDate).append("'");
    		
    		sql.append("\n       SELECT lst.*, mem.NAME USERNAME, sbj.SUBJNM, st.EDUEND     					                                                                            ");
    		sql.append("\n       FROM (    					                                                                            ");
    		sql.append("\n          SELECT his.USERID, his.SUBJ, his.YEAR, his.SUBJSEQ, min(his.EDUSTART) EDUSTART, max(his.EDUEND) EDUEND, max(DATE_GRAD) DATE_GRAD, max(his.isgraduated) isgraduated, min(his.TABLENM) TABLENM, max(his.TSTEP) TSTEP  ");
    		sql.append("\n          FROM (	  	                                       											");
    		
    		sql.append("\n           			SELECT st.subj, st.year, st.subjseq, st.userid, st.tstep, st.isgraduated, st.EDUSTART, st.EDUEND, '' AS DATE_GRAD, 'TZ_STUDENT' TABLENM  	                                       											");
    		sql.append("\n           			FROM TZ_STUDENT st  	                                       											");
    		sql.append("\n           			LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ=st.SUBJ AND sbj.YEAR=st.YEAR AND st.SUBJSEQ=st.SUBJSEQ  	                                       											");
    		sql.append("\n           			WHERE 1=1  	                                       											");
    		sql.append("\n           			  AND sbj.GRCODE = 'N000001'	                                       											");
    		sql.append("\n           			  AND (	                                       											");
    		sql.append("\n           			  	 (sbj.grseq   = '0017' and substr(st.EDUSTART,1,8) "+bwtweenSQL+")                                      											");
    		sql.append("\n           			  	  or                                     											");
    		sql.append("\n           			  	 (sbj.grseq  != '0017' and substr(sbj.EDUSTART,1,8) "+bwtweenSQL+")                                      											");
    		sql.append("\n           			  	  )                                     											");
    		sql.append("\n           			UNION ALL  -- !!!!!!!!!!!!!	                                       											");
    		sql.append("\n           			SELECT rehis.subj, rehis.year, rehis.subjseq, rehis.userid, to_number(rehis.tstep), rehis.isgraduated, rehis.EDUSTART, rehis.EDUEND, '' AS DATE_GRAD, 'TZ_LESSON_HISTORY' TABLENM  	                                       											");
    		sql.append("\n           			FROM tz_lesson_history rehis  	                                       											");
    		sql.append("\n           			LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ = rehis.SUBJ AND sbj.YEAR = rehis.YEAR AND sbj.SUBJSEQ = rehis.SUBJSEQ  	                                       											");
    		sql.append("\n           			WHERE 1=1  	                                       											");
    		sql.append("\n           			  AND sbj.GRCODE = 'N000001'	                                       											");
    		sql.append("\n           			  AND substr(rehis.EDUSTART, 1, 8) "+bwtweenSQL+"	                                       											");
    		sql.append("\n           			UNION ALL  -- !!!!!!!!!!!!!	                                       											");
    		sql.append("\n           			SELECT st.subj, st.year, st.subjseq, st.userid, st.tstep, st.isgraduated, st.EDUSTART, '' AS EDUEND, st.EDUEND AS DATE_GRAD, 'TZ_STOLD' TABLENM  	                                       											");
    		sql.append("\n           			FROM TZ_STOLD st  	                                       											");
    		sql.append("\n           			LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ=st.SUBJ AND sbj.YEAR=st.YEAR AND sbj.SUBJSEQ=st.SUBJSEQ  	                                       											");
    		sql.append("\n           			WHERE 1=1  	                                       											");
    		sql.append("\n           			  AND sbj.GRCODE = 'N000001'	                                       											");
    		sql.append("\n           			  AND substr(st.EDUSTART,1,8) "+bwtweenSQL+"	                                       											");
    		
    		sql.append("\n           	) his	  	                                       											");
    		sql.append("\n           WHERE 1=1                                                                              ");
    if( "".equals(chkStep) ) sql.append("\n   AND his.TSTEP > 0				                                                                            ");
    		sql.append("\n           GROUP BY his.USERID, his.SUBJ, his.YEAR, his.SUBJSEQ	  	                                        											");
    		sql.append("\n        ) lst   					                                                                            ");
    		sql.append("\n        LEFT JOIN TZ_MEMBER mem ON mem.USERID = lst.USERID AND mem.GRCODE LIKE 'N000001'   					                                                                            ");
    		sql.append("\n        LEFT JOIN TZ_SUBJ sbj ON sbj.SUBJ = lst.SUBJ   					                                                                            ");
    		sql.append("\n        LEFT JOIN TZ_STUDENT st ON st.SUBJ = lst.SUBJ AND st.YEAR = lst.YEAR AND st.SUBJSEQ = lst.SUBJSEQ AND st.USERID = lst.USERID     					                                                                            ");
    		
    		
    		ls = connMgr.executeQuery(sql.toString());
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			list.add(dbox);
    		}
    		
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, box, sql.toString());
    		throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
    	} finally {
    		if (ls != null) {
    			try {
    				ls.close();
    			} catch (Exception e) {
    			}
    		}
    		if (connMgr != null) {
    			try {
    				connMgr.freeConnection();
    			} catch (Exception e10) {
    			}
    		}
    	}
    	return list;
    }
    
    
    /**
     * 정규(상시) 과정별 수료+만족도 리스트 오류수정ver. by rsg 
     * @param box
     * @return 
     * @throws Exception
     */
    public ArrayList<DataBox> selectCourseGraduatedList(RequestBox box) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	ListSet ls = null;
    	ArrayList<DataBox> list = null;
    	StringBuffer sql = new StringBuffer();
    	StringBuffer bwtweenSQL = new StringBuffer();
    	DataBox dbox = null;
    	
    	String searchStartDate = box.getString("searchStartDate");
    	String searchEndDate = box.getString("searchEndDate");
    	String dateGubun = box.getString("searchDateGubun");
    	String gubun = box.getString("searchGubun");
    	
    	try {
    		connMgr = new DBConnectionManager();
    		list = new ArrayList<DataBox>();

            String chkStep = box.getString("chkStep");
    		
            // 조회기간
            bwtweenSQL.append(" BETWEEN '").append(searchStartDate).append("' and '").append(searchEndDate).append("'");

            sql.append("/* com.credu.statics.CourseStatisticsNewBean selectCourseGraduatedList */ \n");
            sql.append("\n           					                                                                            ");
            sql.append("\n SELECT area, get_codenm('0101', area) as areanm, subj, subjnm, max(crdate) as crdate,					");
            sql.append("\n 		sum(student1) as isgraduated_b2c, sum(sul_cnt) SUL_CNT,                                             ");
            sql.append("\n      case when sum(sul_cnt) = 0 then 0 else round(sum(distcode1_avg) / sum(sul_cnt), 2) end distcode_avg                      ");
            sql.append("\n FROM (          					                                                                        ");
            sql.append("\n 		select a.grcode, b.userid, b.edustart, a.subj, c.subjnm, a.subjseq, b.tstep, b.GRADUATEDDATE, c.area, substr(c.crdate, 1, 4) as crdate,               ");
		    sql.append("\n      	1 student1, case when e.distcode1_avg is null then 0 else 1 end as sul_cnt, nvl(e.distcode1_avg, 0) as distcode1_avg                             ");
		    sql.append("\n      from tz_subjseq a                                                                             ");
		    sql.append("\n      inner join (  	                                       											");
		    sql.append("\n           		SELECT  	                                       										");
		    sql.append("\n           			his.USERID, his.SUBJ, his.YEAR, his.SUBJSEQ, max(his.EDUSTART) EDUSTART, max(his.GRADUATEDDATE) GRADUATEDDATE, max(his.isgraduated) isgraduated, min(his.TABLENM) TABLENM, max(his.tstep) tstep  	                                       											");
		    sql.append("\n           		FROM (	  	                                       											");
		    
		    sql.append("\n           			SELECT rehis.subj, rehis.year, rehis.subjseq, rehis.userid, CASE WHEN rehis.subj != 'CB09061' THEN nvl(to_number(rehis.tstep),70) WHEN rehis.subj = 'CB09061' THEN nvl(to_number(rehis.tstep),100) ELSE nvl(to_number(rehis.tstep),70) END AS tstep, rehis.isgraduated, rehis.EDUSTART, substr(rehis.GRADUATEDDATE,0,8) GRADUATEDDATE, 'TZ_LESSON_HISTORY' TABLENM  	                                       											");
		    sql.append("\n           			FROM tz_lesson_history rehis  	                                       											");
		    sql.append("\n           			LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ = rehis.SUBJ AND sbj.YEAR = rehis.YEAR AND sbj.SUBJSEQ = rehis.SUBJSEQ  	                                       											");
		    sql.append("\n           			WHERE 1=1  	                                       											");
		    sql.append("\n           			  AND sbj.GRCODE = 'N000001'	                                       											");
		    sql.append("\n           			  AND rehis.isgraduated='Y'	                                       											");
		    sql.append("\n           			  AND substr(rehis.GRADUATEDDATE, 1, 8) "+bwtweenSQL+"	                                       											");
		    sql.append("\n           			UNION ALL  -- !!!!!!!!!!!!!	                                       											");
		    sql.append("\n           			SELECT st.subj, st.year, st.subjseq, st.userid, st.tstep, st.isgraduated, st.EDUSTART, substr(st.EDUEND,0,8) GRADUATEDDATE, 'TZ_STOLD' TABLENM  	                                       											");
		    sql.append("\n           			FROM TZ_STOLD st  	                                       											");
		    sql.append("\n           			LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ=st.SUBJ AND sbj.YEAR=st.YEAR AND sbj.SUBJSEQ=st.SUBJSEQ  	                                       											");
		    sql.append("\n           			WHERE 1=1  	                                       											");
		    sql.append("\n           			  AND sbj.GRCODE = 'N000001'	                                       											");
		    sql.append("\n           			  AND st.isgraduated='Y'	                                       											");
		    sql.append("\n           			  AND substr(st.EDUEND,1,8) "+bwtweenSQL+"	                                       											");
		    
		    sql.append("\n           		) his	  	                                       											");
		    sql.append("\n           		GROUP BY his.USERID, his.SUBJ, his.YEAR, his.SUBJSEQ	  	                                       											");
		    sql.append("\n       		) b on a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq                                                                    ");
		    sql.append("\n       inner join tz_subj c on a.subj = c.subj                                                                          ");
		    sql.append("\n       inner join tz_member d on a.grcode = d.grcode and b.userid = d.userid                                                                      ");
		    sql.append("\n       left outer join tz_suleach e on a.grcode = e.grcode and a.subj = e.subj and a.year = e.year and a.subjseq = e.subjseq and b.userid = e.userid                                                                      ");
		    sql.append("\n       where 1=1                                                                              ");
	if( "".equals(chkStep) ) sql.append("\n   AND b.tstep > 0				                                                                            ");
		    sql.append("\n       ) t                                                                                               ");
		    sql.append("\n group by area, subj, subjnm                                                                              ");
		    sql.append("\n order by area, subj                                                                                      ");    		
    		
    		ls = connMgr.executeQuery(sql.toString());
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			list.add(dbox);
    		}
    		
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, box, sql.toString());
    		throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
    	} finally {
    		if (ls != null) {
    			try {
    				ls.close();
    			} catch (Exception e) {
    			}
    		}
    		if (connMgr != null) {
    			try {
    				connMgr.freeConnection();
    			} catch (Exception e10) {
    			}
    		}
    	}
    	return list;
    }

    /**
     * 정규(상시) 과정별 수료+만족도 LowData 통계 오류수정ver. by rsg
     * @param box
     * @return  
     * @throws Exception
     */
    public ArrayList<DataBox> selectCourseGraduatedLowDataList(RequestBox box) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	ListSet ls = null;
    	ArrayList<DataBox> list = null;
    	StringBuffer sql = new StringBuffer();
    	StringBuffer bwtweenSQL = new StringBuffer();
    	DataBox dbox = null;
    	
    	String searchStartDate = box.getString("searchStartDate");
    	String searchEndDate = box.getString("searchEndDate");
    	String dateGubun = box.getString("searchDateGubun");
    	String chkStep = box.getString("chkStep");
    	String gubun = box.getString("searchGubun");
    	
    	try {
    		connMgr = new DBConnectionManager();
    		list = new ArrayList<DataBox>();
    		// 조회기간
    		bwtweenSQL.append(" BETWEEN '").append(searchStartDate).append("' and '").append(searchEndDate).append("'");
    		
    		sql.append("\n       SELECT lst.*, mem.NAME USERNAME, sbj.SUBJNM, substr(sbj.EDUEND,1,8) EDUEND     					                                                                            ");
    		sql.append("\n       FROM (    					                                                                            ");
		    sql.append("\n           		SELECT  	                                       										");
		    sql.append("\n           			his.USERID, his.SUBJ, his.YEAR, his.SUBJSEQ, max(his.EDUSTART) EDUSTART, max(his.GRADUATEDDATE) GRADUATEDDATE, max(his.isgraduated) isgraduated, min(his.TABLENM) TABLENM, max(his.tstep) tstep  	                                       											");
		    sql.append("\n           		FROM (	  	                                       											");
		    
		    sql.append("\n           			SELECT rehis.subj, rehis.year, rehis.subjseq, rehis.userid, CASE WHEN rehis.subj != 'CB09061' then nvl(to_number(rehis.tstep),70) WHEN rehis.subj = 'CB09061' then nvl(to_number(rehis.tstep),100) else nvl(to_number(rehis.tstep),70) END AS tstep, rehis.isgraduated, rehis.EDUSTART, substr(rehis.GRADUATEDDATE,0,8) GRADUATEDDATE, 'TZ_LESSON_HISTORY' TABLENM  	                                       											");
		    sql.append("\n           			FROM tz_lesson_history rehis  	                                       											");
		    sql.append("\n           			LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ = rehis.SUBJ AND sbj.YEAR = rehis.YEAR AND sbj.SUBJSEQ = rehis.SUBJSEQ  	                                       											");
		    sql.append("\n           			WHERE 1=1  	                                       											");
		    sql.append("\n           			  AND sbj.GRCODE = 'N000001'	                                       											");
		    sql.append("\n           			  AND rehis.isgraduated='Y'	                                       											");
		    sql.append("\n           			  AND substr(rehis.GRADUATEDDATE, 1, 8) "+bwtweenSQL+"	                                       											");
		    sql.append("\n           			UNION ALL  -- !!!!!!!!!!!!!	                                       											");
		    sql.append("\n           			SELECT st.subj, st.year, st.subjseq, st.userid, st.tstep, st.isgraduated, st.EDUSTART, substr(st.EDUEND,0,8) GRADUATEDDATE, 'TZ_STOLD' TABLENM  	                                       											");
		    sql.append("\n           			FROM TZ_STOLD st  	                                       											");
		    sql.append("\n           			LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ=st.SUBJ AND sbj.YEAR=st.YEAR AND sbj.SUBJSEQ=st.SUBJSEQ  	                                       											");
		    sql.append("\n           			WHERE 1=1  	                                       											");
		    sql.append("\n           			  AND sbj.GRCODE = 'N000001'	                                       											");
		    sql.append("\n           			  AND st.isgraduated='Y'	                                       											");
		    sql.append("\n           			  AND substr(st.EDUEND,1,8) "+bwtweenSQL+"	                                       											");
		    
		    sql.append("\n           		) his	  	                                       											");
    		sql.append("\n          	 	WHERE 1=1                                                                              ");
	if( "".equals(chkStep) ) sql.append("\n   AND his.TSTEP > 0				                                                                            ");
		    sql.append("\n           		GROUP BY his.USERID, his.SUBJ, his.YEAR, his.SUBJSEQ	  	                                       											");
    		sql.append("\n        ) lst   					                                                                            ");
    		sql.append("\n        LEFT JOIN TZ_MEMBER mem ON mem.USERID = lst.USERID AND mem.GRCODE LIKE 'N000001'   					                                                                            ");
    		sql.append("\n        LEFT JOIN TZ_SUBJSEQ sbj ON sbj.SUBJ = lst.SUBJ AND sbj.YEAR = lst.YEAR AND sbj.SUBJSEQ = lst.SUBJSEQ 				                                                                            ");
    		
    		
    		ls = connMgr.executeQuery(sql.toString());
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			list.add(dbox);
    		}
    		
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, box, sql.toString());
    		throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
    	} finally {
    		if (ls != null) {
    			try {
    				ls.close();
    			} catch (Exception e) {
    			}
    		}
    		if (connMgr != null) {
    			try {
    				connMgr.freeConnection();
    			} catch (Exception e10) {
    			}
    		}
    	}
    	return list;
    }
    /*
    public ArrayList<DataBox> selectCourseSatisfactionList(RequestBox box) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	ListSet ls = null;
    	ArrayList<DataBox> list = null;
    	StringBuffer sql = new StringBuffer();
    	StringBuffer bwtweenSQL = new StringBuffer();
    	DataBox dbox = null;
    	
    	String searchStartDate = box.getString("searchStartDate");
    	String searchEndDate = box.getString("searchEndDate");
    	String dateGubun = box.getString("searchDateGubun");
    	String gubun = box.getString("searchGubun");
    	
    	try {
    		connMgr = new DBConnectionManager();
    		list = new ArrayList<DataBox>();
    		
    		bwtweenSQL.append("'").append(searchStartDate).append("' and '").append(searchEndDate).append("'");
    		
    		 
    		sql.append("-- com.credu.statics.CourseStatisticsBean selectCourseSatisfactionList \n");
			sql.append("\n select area, get_codenm('0101', area) as areanm, subj, subjnm, 														");
			sql.append("\n        sum(sul_cnt) as sul_cnt, 						                           				                        ");
			sql.append("\n        case when sum(sul_cnt) = 0                                            										");
			sql.append("\n             then 0                                                                                                   ");
			sql.append("\n             else round(sum(distcode_avg) / sum(sul_cnt), 2)                                                          ");
			sql.append("\n         end as distcode_avg                                                                                          ");
			sql.append("\n  from (                                                                                                              ");
			sql.append("\n          select a.grcode, b.userid, b.edustart, a.subj, c.subjnm, a.subjseq, b.tstep, b.eduend, c.area,              ");
			sql.append("\n                  nvl(e.distcode1_avg, 0) as distcode_avg,                                                            ");
			sql.append("\n                  case when e.distcode1_avg is null then 0 else 1 end as sul_cnt                                      ");
			sql.append("\n            from tz_subjseq a                                                                                         ");
			sql.append("\n           inner join (select subj, year, subjseq, userid, tstep,                                                     ");
			sql.append("\n                                 isgraduated, edustart, eduend                                                        ");
			sql.append("\n                           from tz_student                                                                            ");
			sql.append("\n                          union all                                                                                   ");
			sql.append("\n                         select subj, year, subjseq, userid, to_number(tstep),                                        ");
			sql.append("\n                                 isgraduated, edustart, eduend                                                        ");
			sql.append("\n                           from tz_lesson_history ) b                                                                 ");
			sql.append("\n              on a.subj = b.subj                                                                                      ");
			sql.append("\n             and a.year = b.year                                                                                      ");
			sql.append("\n             and a.subjseq = b.subjseq                                                                                ");
			sql.append("\n           inner join tz_subj c                                                                                       ");
			sql.append("\n              on a.subj = c.subj                                                                                      ");
			sql.append("\n           inner join tz_member d                                                                                     ");
			sql.append("\n              on a.grcode = d.grcode                                                                                  ");
			sql.append("\n             and b.userid = d.userid                                                                                  ");
			sql.append("\n            left outer join tz_suleach e                                                                              ");
			sql.append("\n              on a.grcode = e.grcode                                                                                  ");
			sql.append("\n             and a.subj = e.subj                                                                                      ");
			sql.append("\n             and a.year = e.year                                                                                      ");
			sql.append("\n             and a.subjseq = e.subjseq                                                                                ");
			sql.append("\n             and b.userid = e.userid                                                                                  ");
    		sql.append("\n           where (                                                                                        ");
    		if(gubun.equals("ALL") || gubun.equals("B2B")){
    			sql.append("\n                   -- B2B                                                                                 ");
    			sql.append("\n                      (a.grcode != 'N000001' and a.").append(dateGubun).append(" between ").append("'").append(searchStartDate).append("0000' and '").append(searchEndDate).append("2359'").append(" )         ");         
    		}
    		if(gubun.equals("ALL")){
    			sql.append("\n                      OR                                                                                  ");
    		}
    		
    		if(gubun.equals("ALL") || gubun.equals("B2C")){
    			sql.append("\n                   -- B2C                                                                                 ");
    			sql.append("\n                      (                                                                                   ");
    			sql.append("\n                            a.grcode  = 'N000001'                                                         ");
    			sql.append("\n                        and (                                                                             ");
    			sql.append("\n                              (grseq   = '0017' and b.").append(dateGubun).append(" between ").append(bwtweenSQL).append(")           ");
    			sql.append("\n                              or                                                                          ");
    			sql.append("\n                              (grseq  != '0017' and a.").append(dateGubun).append(" between ").append(bwtweenSQL).append(")           ");
    			sql.append("\n                            )                                                                             ");
    			sql.append("\n                       )                                                                                  ");
    		}
    		sql.append("\n                  )                                                                                       ");
    		sql.append("\n        ) t                                                                                               ");
    		sql.append("\n group by area, subj, subjnm                                                                              ");
    		sql.append("\n order by area, subj                                                                                      ");
    		
    		
    		ls = connMgr.executeQuery(sql.toString());
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			list.add(dbox);
    		}
    		
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, box, sql.toString());
    		throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
    	} finally {
    		if (ls != null) {
    			try {
    				ls.close();
    			} catch (Exception e) {
    			}
    		}
    		if (connMgr != null) {
    			try {
    				connMgr.freeConnection();
    			} catch (Exception e10) {
    			}
    		}
    	}
    	return list;
    }
    */

}
