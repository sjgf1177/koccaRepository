package com.credu.statistics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class SpotStudyStatisticsBean {

    /**
     * 열린강좌 년도별 통계 자료를 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList 전문가 관리 리스트 뷰카운트
     * @throws Exception
     */
    public ArrayList<DataBox> selectAppplyCountList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        String searchStartDate = box.getString("searchStartDate");
        String searchEndDate = box.getString("searchEndDate");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.statics.SpotStudyStaticsBean selectAppplyCountList */ \n");
            
            sql.append("select sum(isnotgraduated) applyCnt, COUNT(*) total , sum(male) msum , sum(femail) fsum, area	\n");
            sql.append("from (                                                                                          \n");
            sql.append("      select b.userid, b.edustart, a.subj, a.subjseq, b.tstep, b.eduend, c.area,                \n");
            sql.append("              decode( b.isgraduated , 'Y', 1, 0) as isgraduated,                                \n");
            sql.append("              decode( b.isgraduated , 'N', 1, 0) as isnotgraduated,                             \n");
            sql.append("              case when b.isgraduated = 'Y' and sex in ('1','3') then 1 else 0 end as male,     \n");
            sql.append("              case when b.isgraduated = 'Y' and sex in ('2','4') then 1 else 0 end as femail    \n");
            sql.append("        from tz_subjseq a                                                                       \n");
            sql.append("       inner join (select subj, year, subjseq, userid, tstep,                                   \n");
            sql.append("                             isgraduated, edustart, eduend                                      \n");
            sql.append("                       from tz_student                                                          \n");
            sql.append("                      union all                                                                 \n");
            sql.append("                     select subj, year, subjseq, userid, to_number(tstep),                      \n");
            sql.append("                             isgraduated, edustart, eduend                                      \n");
            sql.append("                       from tz_lesson_history ) b                                               \n");
            sql.append("          on a.subj = b.subj                                                                    \n");
            sql.append("         and a.year = b.year                                                                    \n");
            sql.append("         and a.subjseq = b.subjseq                                                              \n");
            sql.append("       inner join tz_subj c                                                                     \n");
            sql.append("          on a.subj = c.subj                                                                    \n");
            sql.append("       inner join tz_member d                                                                   \n");
            sql.append("          on a.grcode = d.grcode                                                                \n");
            sql.append("         and b.userid = d.userid                                                                \n");
            sql.append("       where a.grcode = 'N000001'                                                               \n");
            sql.append("         and a.year = '2015'                                                                    \n");
            sql.append("         and grseq = '0017'                                                                     \n");
            sql.append("         and b.tstep > 0                                                                        \n");
            sql.append("         and b.edustart between '").append(searchStartDate).append("' and '").append(searchEndDate).append("' \n");
            sql.append("    )                                                                                           \n");
            sql.append("group by area																					\n");
            
            
            

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
     * 열린강좌 조회건수를 검색기간내 일별로 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectGraduatedCountList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        String searchStartDate = box.getString("searchStartDate");
        String searchEndDate = box.getString("searchEndDate");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.statics.SpotStudyStaticsBean selectGraduatedCountList */ \n");
            sql.append("select sum(ISGRADUATED) graduatedcnt, COUNT(*) total , sum(male) msum , sum(femail) fsum, area	\n");
            sql.append("from (                                                                                          \n");
            sql.append("      select b.userid, b.edustart, a.subj, a.subjseq, b.tstep, b.eduend, c.area,                \n");
            sql.append("              decode( b.isgraduated , 'Y', 1, 0) as isgraduated,                                \n");
            sql.append("              decode( b.isgraduated , 'N', 1, 0) as isnotgraduated,                             \n");
            sql.append("              case when b.isgraduated = 'Y' and sex in ('1','3') then 1 else 0 end as male,     \n");
            sql.append("              case when b.isgraduated = 'Y' and sex in ('2','4') then 1 else 0 end as femail    \n");
            sql.append("        from tz_subjseq a                                                                       \n");
            sql.append("       inner join (select subj, year, subjseq, userid, tstep,                                   \n");
            sql.append("                             isgraduated, edustart, eduend                                      \n");
            sql.append("                       from tz_student                                                          \n");
            sql.append("                      union all                                                                 \n");
            sql.append("                     select subj, year, subjseq, userid, to_number(tstep),                      \n");
            sql.append("                             isgraduated, edustart, eduend                                      \n");
            sql.append("                       from tz_lesson_history ) b                                               \n");
            sql.append("          on a.subj = b.subj                                                                    \n");
            sql.append("         and a.year = b.year                                                                    \n");
            sql.append("         and a.subjseq = b.subjseq                                                              \n");
            sql.append("       inner join tz_subj c                                                                     \n");
            sql.append("          on a.subj = c.subj                                                                    \n");
            sql.append("       inner join tz_member d                                                                   \n");
            sql.append("          on a.grcode = d.grcode                                                                \n");
            sql.append("         and b.userid = d.userid                                                                \n");
            sql.append("       where a.grcode = 'N000001'                                                               \n");
            sql.append("         and a.year = '2015'                                                                    \n");
            sql.append("        and grseq = '0017'                                                                      \n");
            sql.append("         and b.tstep > 0                                                                        \n");
            sql.append("         and b.eduend between '").append(searchStartDate).append("' and '").append(searchEndDate).append("' \n");
            sql.append("    )                                                                                           \n");
            sql.append("group by area																					\n");
            
            System.out.println("query :: " + sql);

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
