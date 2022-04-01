package com.credu.statistics;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class CourseStatisticsBean {

    /**
     * 정규(상시) 과정별 통계 
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
        String gubun = box.getString("searchGubun");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();
            
            bwtweenSQL.append("'").append(searchStartDate).append("' and '").append(searchEndDate).append("'");
            

            sql.append("/* com.credu.statics.CourseStatisticsBean selectCourseStatisticList */ \n");
            sql.append("\n select area, get_codenm('0101', area) as areanm, subj, subjnm, max(crdate) as crdate, 					");
		    sql.append("\n        sum(student1) as student_cnt_b2c, sum(isgraduated1) as isgraduated_b2c,							");
		    sql.append("\n        sum(student2) as student_cnt_b2b, sum(isgraduated2) as isgraduated_b2b,							");
		    sql.append("\n        case when sum(sul_cnt) = 0 then 0																	");
		    sql.append("\n              else round(sum(distcode1_avg) / sum(sul_cnt), 2)                                            ");
		    sql.append("\n         end distcode1_avg                                                                                ");
		    sql.append("\n  from (                                                                                                  ");
		    sql.append("\n          select a.grcode, b.userid, b.edustart, a.subj, c.subjnm, a.subjseq, b.tstep, b.eduend, c.area, substr(c.crdate, 1, 4) as crdate,  ");
		    sql.append("\n                  decode( b.isgraduated , 'Y', 1, 0) as isgraduated,                                      ");
		    sql.append("\n                  case when a.grcode  = 'N000001' then 1 else 0 end student1,                             ");
		    sql.append("\n                  case when a.grcode  = 'N000001' and b.isgraduated = 'Y' then 1 else 0 end isgraduated1, ");
		    sql.append("\n                  case when a.grcode != 'N000001' then 1 else 0 end student2,                             ");
		    sql.append("\n                  case when a.grcode != 'N000001' and b.isgraduated = 'Y' then 1 else 0 end isgraduated2, ");
		    sql.append("\n                  nvl(e.distcode1_avg, 0) as distcode1_avg,                                               ");
		    sql.append("\n                  case when e.distcode1_avg is null then 0 else 1 end as sul_cnt                          ");
		    sql.append("\n            from tz_subjseq a                                                                             ");
		    sql.append("\n           inner join (  select subj, year, subjseq, userid, tstep,                                       ");
		    sql.append("\n                                isgraduated, edustart, eduend                                             ");
		    sql.append("\n                           from tz_student                                                                ");
		    sql.append("\n                          union all                                                                       ");
		    sql.append("\n                         select subj, year, subjseq, userid, to_number(tstep),                            ");
		    sql.append("\n                                isgraduated, edustart, eduend                                             ");
		    sql.append("\n                           from tz_lesson_history ) b                                                     ");
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
		    sql.append("\n           where b.tstep > 0                                                                              ");
		    sql.append("\n             and (                                                                                        ");
		    if(gubun.equals("ALL") || gubun.equals("B2B")){
	    		sql.append("\n                   -- B2B                                                                                 ");
			    sql.append("\n                      (a.grcode != 'N000001' and b.").append(dateGubun).append(" between ").append("'").append(searchStartDate).append("' and '").append(searchEndDate).append("'").append(" )         ");      
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
		    	sql.append("\n                              (grseq  != '0017' and b.").append(dateGubun).append(" between ").append(bwtweenSQL).append(")           ");
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
    
    
    /**
     * 정규(상시) 과정별 만족도 리스트
     * @param box
     * @return
     * @throws Exception
     */
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
    		
    		
    		sql.append("/* com.credu.statics.CourseStatisticsBean selectCourseSatisfactionList */ \n");
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

}
