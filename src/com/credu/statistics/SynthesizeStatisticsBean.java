//**********************************************************
//  1. 제      목: STUDENT STATUS ADMIN BEAN
//  2. 프로그램명: StudentStatusAdminBean.java
//  3. 개      요: 입과 현황 관리자 bean
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
//**********************************************************
package com.credu.statistics;

import com.credu.library.*;
import com.dunet.common.util.Constants;
import com.dunet.common.util.EncryptUtil;

import java.util.*;

@SuppressWarnings("unchecked")
public class SynthesizeStatisticsBean {
	private ConfigSet config;
	private int row;

	public SynthesizeStatisticsBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.manage.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
    회원 통계 리스트
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public ArrayList selectUsersStatisticList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String query = "";
		String countSql = "";

		int v_pageno   = box.getInt("p_pageno");
		int v_pagesize = box.getInt("p_pagesize");

		String v_edu_type = box.getString("p_edu_type");
		String v_grcode = box.getString("p_grcode");
		String v_sdt = box.getString("p_sdt");
		String v_edt = box.getString("p_edt");
		String v_sex = "";
		String[] sexArr = box.getStringArray("p_sex");
		String v_age = "";
		String[] ageArr = box.getStringArray("p_age");
		String v_loc = "";
		String[] locArr = box.getStringArray("p_loc");
		String v_job = "";
		String[] jobArr = box.getStringArray("p_job");
		String v_state = box.getString("p_state");
		String v_sort = box.getString("p_sort");
		String sd = "";
		String ed = FormatDate.getDate("yyyy-MM-dd");

		if(sexArr != null) {
			for (int z = 0; z < sexArr.length; z++) {
				v_sex += sexArr[z];
			}
		}

		if(ageArr != null) {
			for (int z = 0; z < ageArr.length; z++) {
				if(v_age.equals("")) {
					v_age = SQLString.Format(ageArr[z]);
				} else {
					v_age += "," + SQLString.Format(ageArr[z]);
				}
			}
		}

		if(locArr != null) {
			for (int z = 0; z < locArr.length; z++) {
				if(v_loc.equals("")) {
					v_loc = SQLString.Format(locArr[z]);
				} else {
					v_loc += "," + SQLString.Format(locArr[z]);
				}

				if(locArr[z].equals("99")) {
					v_loc += ", '98'";
				}
			}
		}

		if(jobArr != null) {
			for (int z = 0; z < jobArr.length; z++) {
				if(v_job.equals("")) {
					v_job = SQLString.Format(jobArr[z]);
				} else {
					v_job += "," + SQLString.Format(jobArr[z]);
				}
			}
		}

		if(v_state.equals("7d")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "date", -7);
		} else if(v_state.equals("1m")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -1);
		} else if(v_state.equals("3m")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -3);
		} else if(v_state.equals("6m")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -6);
		} else if(v_state.equals("1y")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -12);
		} else if(v_state.equals("2y")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -24);
		} else if(v_state.equals("3y")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -36);
		}  else if(v_state.equals("3h")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -36);
		}

		try {
			EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

			connMgr = new DBConnectionManager();
			list = new ArrayList<DataBox>();

			sql1  = " SELECT m.mber_se																								\n";
			sql1 +=	"      , m.mber_nm																								\n";
			sql1 +=	"      , m.edu_type																								\n";
			sql1 +=	"      , m.grcode																								\n";
			sql1 +=	"      , m.grcodenm																								\n";
			sql1 +=	"      , m.user_id																								\n";
			sql1 +=	"      , m.user_nm																								\n";
			sql1 +=	"      , m.sex																									\n";
			sql1 +=	"      , m.sex_nm																								\n";
			sql1 +=	"      , m.brthdy																								\n";
			sql1 +=	"      , m.region_cd																							\n";
			sql1 +=	"      , m.region																								\n";
			sql1 +=	"      , m.job																									\n";
			sql1 +=	"      , m.job_nm																								\n";
			sql1 +=	"      , m.age1																									\n";
			sql1 +=	"      , m.age2																									\n";
			sql1 +=	"      , m.indt																									\n";
			sql1 +=	"      , m.lslgdt																								\n";
			sql1 +=	"      , NVL(m2.pro_cnt, 0) pro_cnt																				\n";
			sql1 +=	"      , NVL(m3.edu_cnt, 0) edu_cnt																				\n";
			sql1 +=	"      , NVL(m4.gra_cnt, 0) gra_cnt																				\n";
			sql2 += "	FROM ( SELECT a.mber_se																						\n";
			sql2 += "				, CASE a.mber_se WHEN '01' THEN '일반' WHEN '02' THEN 'SNS' ELSE '문화광장' END mber_nm			\n";
			sql2 +=	"       		, 'B2C' edu_type																				\n";
			sql2 +=	"       		, 'N000001' grcode																				\n";
			sql2 +=	"       		, (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = 'N000001') grcodenm						\n";
			sql2 +=	"       		, a.user_id																						\n";
			sql2 +=	"       		, a.user_nm																						\n";
			sql2 +=	"       		, a.sex																							\n";
			sql2 +=	"       		, CASE a.sex WHEN 'M' THEN '남자' WHEN 'W' THEN '여자' WHEN 'F' THEN '여자' ELSE '' END sex_nm	\n";
			sql2 +=	"       		, TO_CHAR(a.brthdy, 'YYYY-MM-DD') brthdy														\n";
			sql2 +=	"       		, (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 age1								\n";
			sql2 +=	"       		, CASE WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 20 THEN '10'			\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 30 THEN '20'			\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 40 THEN '30'			\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 50 THEN '40'			\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 60 THEN '50'			\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 100 THEN '60'			\n";
			sql2 +=	"            		   ELSE '00' 																				\n";
			sql2 += "				  END age2																						\n";
			sql2 +=	"       		, NVL(CASE a.resdnc_se 																			\n";
			sql2 += "						WHEN '01' THEN (SELECT CASE WHEN x.code_nm LIKE '서울%' THEN '01'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '부산%' THEN '02'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '대구%' THEN '03'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '인천%' THEN '04'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '광주%' THEN '05'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '대전%' THEN '06'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '울산%' THEN '07'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '세종%' THEN '08'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '경기%' THEN '09'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '강원%' THEN '10'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '충북%' THEN '11'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '충남%' THEN '12'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '전북%' THEN '13'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '전남%' THEN '14'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '경북%' THEN '15'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '경남%' THEN '16'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '제주%' THEN '17'						\n";
			sql2 +=	"                        							ELSE '98'													\n";
			sql2 +=	"               							   END																\n";
			sql2 +=	"        								  FROM CKL_TREE.tc_cmmncode_detail x 									\n";
			sql2 += "										 WHERE x.code_id = 'COM063' 											\n";
			sql2 += "										   AND x.code 	 = a.sido)												\n";
			sql2 +=	"           			WHEN '02' THEN NVL2(a.resdnc_ovsea, '19', '99')											\n";
			sql2 += "						ELSE ''																					\n";
			sql2 += "					  END, '99') region_cd																		\n";
			sql2 +=	"       		, CASE a.resdnc_se																				\n";
			sql2 += "					WHEN '01' THEN (SELECT x.code_nm															\n";
			sql2 += "									  FROM CKL_TREE.tc_cmmncode_detail x										\n";
			sql2 += "									 WHERE x.code_id = 'COM063'													\n";
			sql2 += "									   AND x.code    = a.sido)													\n";
			sql2 += "					WHEN '02' THEN a.resdnc_ovsea																\n";
			sql2 += "					ELSE ''																						\n";
			sql2 += "				  END region																					\n";
			sql2 +=	"       		, a.job																							\n";
			sql2 +=	"       		, (SELECT x.code_nm FROM CKL_TREE.tc_cmmncode_detail x WHERE x.code_id = 'COM056' AND x.code = a.job) job_nm	\n";
			sql2 +=	"       		, NVL(TO_CHAR(a.sbscrb_dt,'YYYY-MM-DD'), '0000-00-00') indt										\n";
			sql2 +=	"       		, TO_CHAR(a.last_login_dt, 'YYYY-MM-DD HH24:MI:SS') lslgdt										\n";
			sql2 +=	"			 FROM CKL_TREE.tm_user a																			\n";
			sql2 +=	"			WHERE a.use_at = 'Y'																				\n";
			sql2 +=	"		    UNION ALL																							\n";
			sql2 +=	"		   SELECT a.membergubun																					\n";
			sql2 +=	"       		, CASE a.membergubun WHEN 'P' THEN '일반회원' WHEN 'C' THEN '기업회원' WHEN 'U' THEN '대학회원' WHEN 'F' THEN '외국인' END	\n";
			sql2 +=	"       		, 'B2B' edu_type																				\n";
			sql2 +=	"       		, a.grcode																						\n";
			sql2 +=	"       		, (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = a.grcode)								\n";
			sql2 +=	"       		, a.userid																						\n";
			sql2 +=	"       		, a.name																						\n";
			sql2 +=	"       		, a.sex																							\n";
			sql2 +=	"       		, CASE a.sex WHEN '1' THEN '남자' WHEN '2' THEN '여자' ELSE '' END								\n";
			sql2 +=	"       		, a.memberyear || '-' || a.membermonth || '-' || a.memberday									\n";
			sql2 +=	"       		, (TO_CHAR(SYSDATE, 'YYYY') - left(a.memberyear, 4)) + 1										\n";
			sql2 +=	"       		, CASE WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 20 THEN '10'						\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 30 THEN '20'						\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 40 THEN '30'						\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 50 THEN '40'						\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 60 THEN '50'						\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 100 THEN '60'						\n";
			sql2 +=	"            		   ELSE '00'																				\n";
			sql2 += "				  END																							\n";
			sql2 +=	"       		, NVL(CASE WHEN a.addr like '서울%' THEN '01'													\n";
			sql2 +=	"            			   WHEN a.addr like '부산%' THEN '02'													\n";
			sql2 +=	"            			   WHEN a.addr like '대구%' THEN '03'													\n";
			sql2 +=	"            			   WHEN a.addr like '인천%' THEN '04'													\n";
			sql2 +=	"            			   WHEN a.addr like '광주%' THEN '05'													\n";
			sql2 +=	"            			   WHEN a.addr like '대전%' THEN '06'													\n";
			sql2 +=	"            			   WHEN a.addr like '울산%' THEN '07'													\n";
			sql2 +=	"            			   WHEN a.addr like '세종%' THEN '08'													\n";
			sql2 +=	"            			   WHEN a.addr like '경기%' THEN '09'													\n";
			sql2 +=	"            			   WHEN a.addr like '강원%' THEN '10'													\n";
			sql2 +=	"            			   WHEN a.addr like '충북%' THEN '11'													\n";
			sql2 +=	"            			   WHEN a.addr like '충남%' THEN '12'													\n";
			sql2 +=	"            			   WHEN a.addr like '전북%' THEN '13'													\n";
			sql2 +=	"            			   WHEN a.addr like '전남%' THEN '14'													\n";
			sql2 +=	"            			   WHEN a.addr like '경북%' THEN '15'													\n";
			sql2 +=	"            			   WHEN a.addr like '경남%' THEN '16'													\n";
			sql2 +=	"            			   WHEN a.addr like '제주%' THEN '17'													\n";
			sql2 +=	"            			   WHEN a.addr IS NOT NULL THEN '19'													\n";
			sql2 +=	"           	  END, '99')																					\n";
			sql2 +=	"       		, a.addr																						\n";
			sql2 +=	"       		, '99'																							\n";
			sql2 +=	"       		, '기타'																							\n";
			sql2 +=	"       		, NVL(TO_CHAR(TO_DATE(RPAD(REPLACE(REPLACE(REPLACE(a.indate,'-',''),':',''),' ',''),14,'0'),'YYYYMMDDHH24MISS'), 'YYYY-MM-DD'), '0000-00-00')	\n";
			sql2 +=	"       		, TO_CHAR(TO_DATE(a.lglast,'YYYYMMDDHH24MISS'), 'YYYY-MM-DD HH24:MI:SS')						\n";
			sql2 +=	"			 FROM tz_member a																					\n";
			sql2 += "			WHERE a.state   = 'Y'																				\n";
			sql2 += "			  AND a.grcode != 'N000001'																			\n";
			sql2 += "	   ) m																										\n";
			sql2 +=	"	LEFT JOIN (SELECT userid																					\n";
			sql2 += "					, COUNT(userid) pro_cnt																		\n";
			sql2 += "				 FROM TZ_PROPOSE																				\n";
			sql2 += "				GROUP BY userid) m2 ON m.user_id = m2.userid													\n";
			sql2 +=	"	LEFT JOIN (SELECT userid																					\n";
			sql2 += "					, COUNT(userid) edu_cnt 																	\n";
			sql2 += "				 FROM tz_stold																					\n";
			sql2 += "				GROUP BY userid) m3 ON m.user_id = m3.userid													\n";
			sql2 +=	"	LEFT JOIN (SELECT userid																					\n";
			sql2 += "					, COUNT(userid) gra_cnt																		\n";
			sql2 += "				 FROM tz_stold																					\n";
			sql2 += "				WHERE isgraduated = 'Y'																			\n";
			sql2 += " 				GROUP BY userid) m4 ON m.user_id = m4.userid													\n";
			sql2 +=	"  WHERE m.edu_type IS NOT NULL																					\n";

			if (v_edu_type.equals("C")) {
				sql2 += "  AND m.edu_type = 'B2C'																					\n";
			} else if (v_edu_type.equals("B")) {
				sql2 += "  AND m.edu_type = 'B2B'																					\n";
			}

			if (!v_grcode.equals("")) {
				sql2 += "  AND m.grcode = " + SQLString.Format(v_grcode) + " 														\n";
			}

			if (v_sex.equals("M")) {
				sql2 += "  AND m.sex IN ('1', 'M') 							 														\n";
			} else if (v_sex.equals("W")) {
				sql2 += "  AND m.sex IN ('2', 'W', 'F')						 														\n";
			} else if (v_sex.equals("N")) {
				sql2 += "  AND m.sex NOT IN ('1', 'M', '2', 'W', 'F')						 										\n";
			} else if (v_sex.equals("MW")) {
				sql2 += "  AND m.sex IN ('1', 'M', '2', 'W', 'F')						 											\n";
			} else if (v_sex.equals("MN")) {
				sql2 += "  AND (m.sex IN ('1', 'M') OR m.sex NOT IN ('1', 'M', '2', 'W', 'F'))										\n";
			} else if (v_sex.equals("WN")) {
				sql2 += "  AND (m.sex IN ('2', 'W', 'F') OR m.sex NOT IN ('1', 'M', '2', 'W', 'F'))									\n";
			}

			if (ageArr != null) {
				sql2 += "  AND m.age2 IN (" + v_age + ")					 														\n";
			}

			if (locArr != null) {
				sql2 += "  AND m.region_cd IN (" + v_loc + ")					 													\n";
			}

			if (jobArr != null) {
				sql2 += "  AND m.job IN (" + v_job + ")					 															\n";
			}

			if (!v_state.equals("")) {
				if (v_state.equals("n")) {
					sql2 += " AND m.lslgdt IS NULL																					\n";
				} else if (v_state.equals("3h")) {
					sql2 += " AND substr(m.lslgdt, 0, 10) < " + SQLString.Format(sd) + "											\n";
					sql2 += " AND m.lslgdt IS NOT NULL																				\n";
				} else {
					sql2 += " AND substr(m.lslgdt, 0, 10) BETWEEN " + SQLString.Format(sd) + " AND " + SQLString.Format(ed) + "		\n";
					sql2 += " AND m.lslgdt IS NOT NULL																				\n";
				}
			}

			if (v_state.equals("")) {
				sql2 += " AND m.indt BETWEEN " + SQLString.Format(v_sdt) + " AND " + SQLString.Format(v_edt) + "					\n";
			}

			if (v_sort.equals("ND")) {
				sql3 += "  ORDER BY m.indt DESC																						  ";
			} else if (v_sort.equals("OD")) {
				sql3 += "  ORDER BY m.indt ASC																						  ";
			} else if (v_sort.equals("NM")) {
				sql3 += "  ORDER BY m.user_nm ASC																					  ";
			}

			query = sql1 + sql2 + sql3;

			ls = connMgr.executeQuery(query);

			countSql = "SELECT COUNT(*) " + sql2;

			int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql);

			ls.setPageSize(v_pagesize);                 //페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.
			int total_page_count = ls.getTotalPage();  	//전체 페이지 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();

				dbox.put("d_dispnum",		new Integer(totalrowcount - ls.getRowNum() + 1));
				dbox.put("d_totalpage",		new Integer(total_page_count));
				dbox.put("d_rowcount",  	new Integer(row));
				dbox.put("d_totalrowcount",	new Integer(totalrowcount));

				list.add(dbox);
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, query);
			throw new Exception("sql1 = " + query + "\r\n" + ex.getMessage());
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
	 교육인원 통계 리스트
	 @param box      receive from the form object and session
	 @return ArrayList
	 */
	public ArrayList selectEduUsersStatisticList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String query = "";
		String countSql = "";

		int v_pageno   = box.getInt("p_pageno");
		int v_pagesize = box.getInt("p_pagesize");

		String v_edu_type = box.getString("p_edu_type_eu");
		String v_grcode = box.getString("p_grcode_eu");
		String v_year = box.getString("p_year_eu");
		String v_seq = box.getString("p_seq_eu");
		String v_subj = box.getString("p_subj_eu");
		String v_g1 = box.getString("p_g1_eu");
		String v_g2 = box.getString("p_g2_eu");
		String v_g3 = box.getString("p_g3_eu");
		String v_lv = box.getString("p_lv_eu");
		String v_out = box.getString("p_out_eu");
		String v_sdt = box.getString("p_sdt_eu");
		String v_edt = box.getString("p_edt_eu");
		String v_learn = box.getString("p_learn_eu");
		String v_graduated = box.getString("p_graduated_eu");
		String v_sul = box.getString("p_sul_eu");
		String v_txt = box.getString("p_txt_eu");
		String v_sex = "";
		String[] sexArr = box.getStringArray("p_sex_eu");
		String v_age = "";
		String[] ageArr = box.getStringArray("p_age_eu");
		String v_loc = "";
		String[] locArr = box.getStringArray("p_loc_eu");
		String v_job = "";
		String[] jobArr = box.getStringArray("p_job_eu");
		String v_sort = box.getString("p_sort_eu");

		if(sexArr != null) {
			for (int z = 0; z < sexArr.length; z++) {
				v_sex += sexArr[z];
			}
		}

		if(ageArr != null) {
			for (int z = 0; z < ageArr.length; z++) {
				if(v_age.equals("")) {
					v_age = SQLString.Format(ageArr[z]);
				} else {
					v_age += "," + SQLString.Format(ageArr[z]);
				}
			}
		}

		if(locArr != null) {
			for (int z = 0; z < locArr.length; z++) {
				if(v_loc.equals("")) {
					v_loc = SQLString.Format(locArr[z]);
				} else {
					v_loc += "," + SQLString.Format(locArr[z]);
				}

				if(locArr[z].equals("99")) {
					v_loc += ", '98'";
				}
			}
		}

		if(jobArr != null) {
			for (int z = 0; z < jobArr.length; z++) {
				if(v_job.equals("")) {
					v_job = SQLString.Format(jobArr[z]);
				} else {
					v_job += "," + SQLString.Format(jobArr[z]);
				}
			}
		}

		try {
			EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

			connMgr = new DBConnectionManager();
			list = new ArrayList<DataBox>();

			sql1  = " SELECT m.edu_type																													\n";
			sql1 += "	   , m.grcode																													\n";
			sql1 += "	   , m.grcodenm																													\n";
			sql1 += "	   , m.subj																														\n";
			sql1 += "	   , m.year																														\n";
			sql1 += "	   , m.subjseq																													\n";
			sql1 += "	   , m.subjnm																													\n";
			sql1 += "	   , m.grseqnm																													\n";
			sql1 += "	   , m.gcd1																														\n";
			sql1 += "	   , m.gcd2																														\n";
			sql1 += "	   , m.gcd3																														\n";
			sql1 += "	   , m.gnm1																														\n";
			sql1 += "	   , m.gnm2																														\n";
			sql1 += "	   , m.gnm3																														\n";
			sql1 += "	   , m.lvcode																													\n";
			sql1 += "	   , m.lvnm																														\n";
			sql1 += "	   , m.upperclass																												\n";
			sql1 += "	   , m.user_id																													\n";
			sql1 += "	   , m.user_nm																													\n";
			sql1 += "	   , m.sex																														\n";
			sql1 += "	   , m.sex_nm																													\n";
			sql1 += "	   , m.brthdy																													\n";
			sql1 += "	   , m.age1																														\n";
			sql1 += "	   , m.age2																														\n";
			sql1 += "	   , m.region_cd																												\n";
			sql1 += "	   , m.region																													\n";
			sql1 += "	   , m.job																														\n";
			sql1 += "	   , m.job_nm																													\n";
			sql1 += "	   , m.appdt																													\n";
			sql1 += "	   , NVL(m.learn_yn, 'N') learn_yn																								\n";
			sql1 += "	   , NVL(m.graduated_yn, 'N') graduated_yn																						\n";
			sql1 += "	   , m.sul2																														\n";
			sql1 += "	   , NVL(m.suleach2_yn, 'N') suleach2_yn																						\n";
			sql1 += "	   , m.suldt																													\n";
			sql1 += "	   , m.answers																													\n";
			sql1 += "	   , m.distcode1_avg																											\n";
			sql1 += "	   , m.gyear																													\n";
			sql1 += "	   , m.grseq																													\n";
			sql2 += "   FROM ( SELECT 'B2C' edu_type 																									\n";
			sql2 += "   			, a.asp_gubun grcode 																								\n";
			sql2 += "				, (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = a.asp_gubun) grcodenm										\n";
			sql2 += "				, a.subj																											\n";
			sql2 += "				, a.year																											\n";
			sql2 += "				, a.subjseq																											\n";
			sql2 += "				, (SELECT x.subjnm FROM tz_subj x WHERE x.subj = a.subj) subjnm														\n";
			sql2 += "				, (SELECT x.grseqnm FROM tz_grseq x WHERE x.grcode = a.asp_gubun AND x.gyear = f.gyear AND x.grseq = f.grseq) grseqnm	\n";
			sql2 += "				, d.gubun_1 gcd1																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_1 AND x.levels = 1) gnm1				\n";
			sql2 += "				, d.gubun_2 gcd2																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_2 AND x.levels = 2) gnm2				\n";
			sql2 += "				, d.gubun_3 gcd3 																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_3 AND x.levels = 3) gnm3				\n";
			sql2 += "				, e.lvcode																											\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0121' AND x.code = e.lvcode AND x.levels = 2) lvnm				\n";
			sql2 += "				, (SELECT x.upperclass FROM tz_subj x where x.subj = a.subj) upperclass												\n";
			sql2 += "				, b.user_id																											\n";
			sql2 += "				, b.user_nm																											\n";
			sql2 += "				, b.sex																												\n";
			sql2 += "				, CASE b.sex WHEN 'M' THEN '남자' WHEN 'W' THEN '여자' WHEN 'F' THEN '여자' ELSE '' END sex_nm						\n";
			sql2 += "				, TO_CHAR(b.brthdy, 'YYYY-MM-DD') brthdy																			\n";
			sql2 += "				, (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 age1													\n";
			sql2 += "				, CASE WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 20 THEN '10'								\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 30 THEN '20'								\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 40 THEN '30'								\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 50 THEN '40'								\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 60 THEN '50'								\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 100 THEN '60'								\n";
			sql2 += "					   ELSE '00'																									\n";
			sql2 += "				   END age2																											\n";
			sql2 += "				, NVL(CASE b.resdnc_se																								\n";
			sql2 += "						WHEN '01' THEN (SELECT CASE WHEN x.code_nm LIKE '서울%' THEN '01'											\n";
			sql2 += "													WHEN x.code_nm LIKE '부산%' THEN '02'											\n";
			sql2 += "													WHEN x.code_nm LIKE '대구%' THEN '03'											\n";
			sql2 += "													WHEN x.code_nm LIKE '인천%' THEN '04'											\n";
			sql2 += "													WHEN x.code_nm LIKE '광주%' THEN '05'											\n";
			sql2 += "													WHEN x.code_nm LIKE '대전%' THEN '06'											\n";
			sql2 += "													WHEN x.code_nm LIKE '울산%' THEN '07'											\n";
			sql2 += "													WHEN x.code_nm LIKE '세종%' THEN '08'											\n";
			sql2 += "													WHEN x.code_nm LIKE '경기%' THEN '09'											\n";
			sql2 += "													WHEN x.code_nm LIKE '강원%' THEN '10'											\n";
			sql2 += "													WHEN x.code_nm LIKE '충북%' THEN '11'											\n";
			sql2 += "													WHEN x.code_nm LIKE '충남%' THEN '12'											\n";
			sql2 += "													WHEN x.code_nm LIKE '전북%' THEN '13'											\n";
			sql2 += "													WHEN x.code_nm LIKE '전남%' THEN '14'											\n";
			sql2 += "													WHEN x.code_nm LIKE '경북%' THEN '15'											\n";
			sql2 += "													WHEN x.code_nm LIKE '경남%' THEN '16'											\n";
			sql2 += "													WHEN x.code_nm LIKE '제주%' THEN '17'											\n";
			sql2 += "													ELSE '98'																		\n";
			sql2 += "											    END																					\n";
			sql2 += "										  FROM CKL_TREE.tc_cmmncode_detail x														\n";
			sql2 += "										 WHERE x.code_id = 'COM063'																	\n";
			sql2 += "										   AND x.code 	 = b.sido)																	\n";
			sql2 += "						WHEN '02' THEN NVL2(b.resdnc_ovsea, '19', '99')																\n";
			sql2 += "						ELSE ''																										\n";
			sql2 += "					   END, '99') region_cd																							\n";
			sql2 += "				, CASE b.resdnc_se																									\n";
			sql2 += "					   WHEN '01' THEN (SELECT x.code_nm FROM CKL_TREE.tc_cmmncode_detail x	WHERE x.code_id = 'COM063' AND x.code = b.sido)	\n";
			sql2 += "					   WHEN '02' THEN b.resdnc_ovsea																				\n";
			sql2 += "					   ELSE ''																										\n";
			sql2 += "				   END region																										\n";
			sql2 += "				, b.job																												\n";
			sql2 += "				, (SELECT x.code_nm FROM CKL_TREE.tc_cmmncode_detail x WHERE x.code_id = 'COM056' AND x.code = b.job) job_nm		\n";
			sql2 += "				, TO_CHAR(TO_DATE(a.appdate,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD') appdt											\n";
			sql2 += "				, (SELECT CASE WHEN COUNT(*) > 0 THEN 'Y' ELSE 'N' END 																\n";
			sql2 += "					 FROM tz_student x																								\n";
			sql2 += "					WHERE x.subj    = a.subj																						\n";
			sql2 += "					  AND x.year    = a.year																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq																						\n";
			sql2 += "					  AND x.userid  = b.user_id) learn_yn																			\n";
			sql2 += "				, (SELECT x.isgraduated																								\n";
			sql2 += "					 FROM tz_stold x																								\n";
			sql2 += "					WHERE x.subj    = a.subj																						\n";
			sql2 += "					  AND x.YEAR    = a.YEAR																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq																						\n";
			sql2 += "					  AND x.userid  = b.user_id) graduated_yn																		\n";
			sql2 += "				, NVL(f.sulpapernum2, 0) sul2																						\n";
			sql2 += "				, (SELECT CASE WHEN COUNT(*) > 0 THEN 'Y' ELSE 'N' END																\n";
			sql2 += "					 FROM tz_suleach x																								\n";
			sql2 += "					WHERE x.subj    = a.subj																						\n";
			sql2 += "					  AND x.year    = a.year																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq																						\n";
			sql2 += "					  AND x.grcode  = a.asp_gubun																					\n";
			sql2 += "					  AND x.userid  = b.user_id																						\n";
			sql2 += "					  AND NVL(x.sulpapernum, 0) = NVL(f.sulpapernum2, 0)) suleach2_yn												\n";
			sql2 += "				, NVL(TO_CHAR(TO_DATE(g.ldate,'YYYYMMDDHH24MISS'),'YYYY-MM-DD'), '') suldt											\n";
			sql2 += "				, g.answers																											\n";
			sql2 += "				, g.distcode1_avg																									\n";
			sql2 += "				, f.gyear																											\n";
			sql2 += "				, f.grseq																											\n";
			sql2 += "			 FROM tz_propose a																										\n";
			sql2 += "					INNER JOIN CKL_TREE.tm_user b ON a.userid = b.user_id AND b.use_at = 'Y'										\n";
			sql2 += "			 		 LEFT JOIN tz_subjhomegubun d ON d.subj = a.subj AND d.gubun = 'GS'												\n";
			sql2 += "			 		 LEFT JOIN tz_subjhomegubun_level e ON e.subj = a.subj															\n";
			sql2 += "			 		 LEFT JOIN tz_subjseq f ON f.subj = a.subj AND f.year = a.year AND f.subjseq = a.subjseq AND f.grcode = a.asp_gubun	\n";
			sql2 += "			 		 LEFT JOIN tz_suleach g ON g.subj = a.subj AND g.year = a.year AND g.subjseq = a.subjseq AND g.sulpapernum = f.sulpapernum2 AND g.userid = b.user_id	\n";
			sql2 += "			WHERE a.ASP_GUBUN = 'N000001'																							\n";
			sql2 += "			  AND NVL(a.cancelkind,' ') NOT IN ('P', 'F', 'D')																		\n";
			sql2 += "			  AND a.appdate IS NOT NULL																								\n";
			sql2 += "			UNION ALL																												\n";
			sql2 += "		   SELECT 'B2B' edu_type		 																							\n";
			sql2 += "				, a.asp_gubun grcode																								\n";
			sql2 += "				, (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = a.asp_gubun) grcodenm										\n";
			sql2 += "				, a.subj																											\n";
			sql2 += "				, a.year																											\n";
			sql2 += "				, a.subjseq																											\n";
			sql2 += "				, (SELECT x.subjnm FROM tz_subj x WHERE x.subj = a.subj) subjnm														\n";
			sql2 += "				, (SELECT x.grseqnm FROM tz_grseq x WHERE x.grcode = a.asp_gubun AND x.gyear = f.gyear AND x.grseq = f.grseq) grseqnm	\n";
			sql2 += "				, d.gubun_1 gcd1																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_1 AND x.levels = 1) gnm1				\n";
			sql2 += "				, d.gubun_2 gcd2																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_2 AND x.levels = 2) gnm2				\n";
			sql2 += "				, d.gubun_3 gcd3																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_3 AND x.levels = 3) gnm3				\n";
			sql2 += "				, e.lvcode																											\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0121' AND x.code = e.lvcode AND x.levels = 2) lvnm				\n";
			sql2 += "				, (SELECT x.upperclass FROM tz_subj x where x.subj = a.subj) upperclass												\n";
			sql2 += "				, b.userid user_id																									\n";
			sql2 += "				, b.name user_nm																									\n";
			sql2 += "				, b.sex																												\n";
			sql2 += "				, CASE b.sex WHEN '1' THEN '남자' WHEN '2' THEN '여자' ELSE '' END sex_nm											\n";
			sql2 += "				, b.memberyear || '-' || b.membermonth || '-' || b.memberday brthdy													\n";
			sql2 += "				, (TO_CHAR(SYSDATE, 'YYYY') - left(b.memberyear, 4)) + 1 age1 														\n";
			sql2 += "				, CASE WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 20 THEN '10'											\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 30 THEN '20'											\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 40 THEN '30'											\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 50 THEN '40'											\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 60 THEN '50'											\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 100 THEN '60'											\n";
			sql2 += "					   ELSE '00'																									\n";
			sql2 += "				   END age2																											\n";
			sql2 += "				, NVL(CASE WHEN b.addr like '서울%' THEN '01'																		\n";
			sql2 += "						   WHEN b.addr like '부산%' THEN '02'																		\n";
			sql2 += "						   WHEN b.addr like '대구%' THEN '03'																		\n";
			sql2 += "						   WHEN b.addr like '인천%' THEN '04'																		\n";
			sql2 += "						   WHEN b.addr like '광주%' THEN '05'																		\n";
			sql2 += "						   WHEN b.addr like '대전%' THEN '06'																		\n";
			sql2 += "						   WHEN b.addr like '울산%' THEN '07'																		\n";
			sql2 += "						   WHEN b.addr like '세종%' THEN '08'																		\n";
			sql2 += "						   WHEN b.addr like '경기%' THEN '09'																		\n";
			sql2 += "						   WHEN b.addr like '강원%' THEN '10'																		\n";
			sql2 += "						   WHEN b.addr like '충북%' THEN '11'																		\n";
			sql2 += "						   WHEN b.addr like '충남%' THEN '12'																		\n";
			sql2 += "						   WHEN b.addr like '전북%' THEN '13'																		\n";
			sql2 += "						   WHEN b.addr like '전남%' THEN '14'																		\n";
			sql2 += "						   WHEN b.addr like '경북%' THEN '15'																		\n";
			sql2 += "						   WHEN b.addr like '경남%' THEN '16'																		\n";
			sql2 += "						   WHEN b.addr like '제주%' THEN '17'																		\n";
			sql2 += "						   WHEN b.addr IS NOT NULL THEN '20'																		\n";
			sql2 += "					   END, '19') region_cd																							\n";
			sql2 += "				, b.addr region																										\n";
			sql2 += "				, '99' job																											\n";
			sql2 += "				, '기타' job_nm																										\n";
			sql2 += "				, TO_CHAR(TO_DATE(a.appdate,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD') appdt											\n";
			sql2 += "				, (SELECT CASE WHEN COUNT(*) > 0 THEN 'Y' ELSE 'N' END																\n";
			sql2 += "					 FROM tz_student x																								\n";
			sql2 += "					WHERE x.subj    = a.subj																						\n";
			sql2 += "					  AND x.year    = a.year																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq																						\n";
			sql2 += "					  AND x.userid  = b.userid) learn_yn																			\n";
			sql2 += "				, (SELECT x.isgraduated																								\n";
			sql2 += "					 FROM tz_stold x																								\n";
			sql2 += "					WHERE x.subj    = a.subj																						\n";
			sql2 += "					  AND x.year    = a.year																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq																						\n";
			sql2 += "					  AND x.userid  = b.userid) graduated_yn																		\n";
			sql2 += "				, nvl(f.sulpapernum2, 0) sul2																						\n";
			sql2 += "				, (SELECT CASE WHEN COUNT(*) > 0 THEN 'Y' ELSE 'N' END																\n";
			sql2 += "					 FROM tz_suleach x 																								\n";
			sql2 += "					WHERE x.subj    = a.subj 																						\n";
			sql2 += "					  AND x.year    = a.year 																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq 																					\n";
			sql2 += "					  AND x.grcode  = a.asp_gubun 																					\n";
			sql2 += "					  AND x.userid  = b.userid 																						\n";
			sql2 += "					  AND NVL(x.sulpapernum, 0) = NVL(f.sulpapernum2, 0)) suleach2_yn												\n";
			sql2 += "				, NVL(TO_CHAR(TO_DATE(g.ldate,'YYYYMMDDHH24MISS'),'YYYY-MM-DD'), '-') suldt											\n";
			sql2 += "				, g.answers																											\n";
			sql2 += "				, g.distcode1_avg																									\n";
			sql2 += "				, f.gyear																											\n";
			sql2 += "				, f.grseq																											\n";
			sql2 += "			 FROM tz_propose a																										\n";
			sql2 += "					INNER JOIN tz_member b ON a.userid = b.userid																	\n";
			sql2 += "					 LEFT JOIN tz_subjhomegubun d ON d.subj = a.subj AND d.gubun = 'GS'												\n";
			sql2 += "			 		 LEFT JOIN tz_subjhomegubun_level e ON e.subj = a.subj															\n";
			sql2 += "					 LEFT JOIN tz_subjseq f ON f.subj = a.subj AND f.year = a.year AND f.subjseq = a.subjseq AND f.grcode = a.asp_gubun		\n";
			sql2 += "					 LEFT JOIN tz_suleach g ON g.subj = a.subj AND g.year = a.year AND g.subjseq = a.subjseq AND g.sulpapernum = f.sulpapernum2 AND g.userid = b.userid	\n";
			sql2 += "			WHERE a.asp_gubun != 'N000001'																							\n";
			sql2 += "			  AND NVL(a.cancelkind,' ') NOT IN ('P', 'F', 'D')																		\n";
			sql2 += "			  AND a.appdate IS NOT NULL																								\n";
			sql2 += "	   ) m																															\n";
			sql2 +=	"  WHERE m.edu_type IS NOT NULL																										\n";

			if (v_edu_type.equals("C")) {
				sql2 += "  AND m.edu_type = 'B2C'																										\n";
			} else if (v_edu_type.equals("B")) {
				sql2 += "  AND m.edu_type = 'B2B'																										\n";
			}

			if (!v_grcode.equals("")) {
				sql2 += "  AND m.grcode = " + SQLString.Format(v_grcode) + " 																			\n";
			}

			if (!v_year.equals("")) {
				sql2 += "  AND m.gyear = " + SQLString.Format(v_year) + "	 																			\n";
			}

			if (!v_seq.equals("")) {
				sql2 += "  AND m.grseq = " + SQLString.Format(v_seq) + "	 																			\n";
			}

			if (!v_subj.equals("")) {
				sql2 += "  AND m.subj = " + SQLString.Format(v_subj) + "	 																			\n";
			}

			if (!v_g1.equals("")) {
				sql2 += "  AND m.gcd1 = " + SQLString.Format(v_g1) + "	 																				\n";
			}

			if (!v_g2.equals("")) {
				sql2 += "  AND m.gcd2 = " + SQLString.Format(v_g2) + "	 																				\n";
			}

			if (!v_g3.equals("")) {
				sql2 += "  AND m.gcd3 = " + SQLString.Format(v_g3) + "	 																				\n";
			}

			if (!v_lv.equals("")) {
				sql2 += "  AND m.lvcode = " + SQLString.Format(v_lv) + "	 																			\n";
			}

			if (v_out.equals("C01")) {
				sql2 += "  AND m.upperclass = 'X01'																										\n";
			} else if (v_out.equals("C02")) {
				sql2 += "  AND m.upperclass != 'X01'							 																		\n";
			} else if (v_out.equals("C03")) {
				sql2 += "  AND m.upperclass != 'X01' AND m.gcd1 IS NULL																					\n";
			} else if (v_out.equals("C04")) {
				sql2 += "  AND m.gcd1 IS NOT NULL								 																		\n";
			} else if (v_out.equals("C05")) {
				sql2 += "  AND m.upperclass != 'X01' AND m.gcd1 IS NOT NULL		 																		\n";
			}

			sql2 += " AND m.appdt BETWEEN " + SQLString.Format(v_sdt) + " AND " + SQLString.Format(v_edt) + "											\n";

			if (v_sex.equals("M")) {
				sql2 += "  AND m.sex IN ('1', 'M') 							 																			\n";
			} else if (v_sex.equals("W")) {
				sql2 += "  AND m.sex IN ('2', 'W', 'F')						 																			\n";
			} else if (v_sex.equals("N")) {
				sql2 += "  AND m.sex NOT IN ('1', 'M', '2', 'W', 'F')						 															\n";
			} else if (v_sex.equals("MW")) {
				sql2 += "  AND m.sex IN ('1', 'M', '2', 'W', 'F')						 																\n";
			} else if (v_sex.equals("MN")) {
				sql2 += "  AND (m.sex IN ('1', 'M') OR m.sex NOT IN ('1', 'M', '2', 'W', 'F'))															\n";
			} else if (v_sex.equals("WN")) {
				sql2 += "  AND (m.sex IN ('2', 'W', 'F') OR m.sex NOT IN ('1', 'M', '2', 'W', 'F'))														\n";
			}

			if (ageArr != null) {
				sql2 += "  AND m.age2 IN (" + v_age + ")					 																			\n";
			}

			if (locArr != null) {
				sql2 += "  AND m.region_cd IN (" + v_loc + ")					 																		\n";
			}

			if (jobArr != null) {
				sql2 += "  AND m.job IN (" + v_job + ")					 																				\n";
			}

			if (!v_learn.equals("")) {
				sql2 += "  AND m.learn_yn = " + SQLString.Format(v_learn) + "	 																		\n";
			}

			if (!v_graduated.equals("")) {
				sql2 += "  AND m.graduated_yn = " + SQLString.Format(v_graduated) + "	 																\n";
			}

			if (!v_sul.equals("")) {
				sql2 += "  AND m.suleach2_yn = " + SQLString.Format(v_sul) + "	 																		\n";
			}

			if (!v_txt.equals("")) {
				sql2 += "  AND (m.subjnm like '%" + v_txt + "%' OR m.subj like '%" + v_txt + "%' OR m.user_id like '%" + v_txt + "%' OR m.user_nm like '%" + v_txt + "%')	\n";
			}

			if (v_sort.equals("ND")) {
				sql3 += "  ORDER BY m.appdt DESC																						  				";
			} else if (v_sort.equals("OD")) {
				sql3 += "  ORDER BY m.appdt ASC																						  					";
			} else if (v_sort.equals("NM")) {
				sql3 += "  ORDER BY m.user_nm ASC																					 					";
			}

			query = sql1 + sql2 + sql3;

			ls = connMgr.executeQuery(query);

			countSql = "SELECT COUNT(*) " + sql2;

			int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql);

			ls.setPageSize(v_pagesize);                 //페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.
			int total_page_count = ls.getTotalPage();  	//전체 페이지 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();

				dbox.put("d_dispnum",		new Integer(totalrowcount - ls.getRowNum() + 1));
				dbox.put("d_totalpage",		new Integer(total_page_count));
				dbox.put("d_rowcount",  	new Integer(row));
				dbox.put("d_totalrowcount",	new Integer(totalrowcount));

				list.add(dbox);
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, query);
			throw new Exception("sql1 = " + query + "\r\n" + ex.getMessage());
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
	 교육성과 통계 리스트
	 @param box      receive from the form object and session
	 @return ArrayList
	 */
	public ArrayList selectEduResultStatisticList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String query = "";
		String countSql = "";

		int v_pageno   = box.getInt("p_pageno");
		int v_pagesize = box.getInt("p_pagesize");

		String v_edu_type = box.getString("p_edu_type_er");
		String v_grcode = box.getString("p_grcode_er");
		String v_year = box.getString("p_year_er");
		String v_seq = box.getString("p_seq_er");
		String v_subj = box.getString("p_subj_er");
		String v_g1 = box.getString("p_g1_er");
		String v_g2 = box.getString("p_g2_er");
		String v_g3 = box.getString("p_g3_er");
		String v_lv = box.getString("p_lv_er");
		String v_out = box.getString("p_out_er");
		String v_sdt = box.getString("p_sdt_er");
		String v_edt = box.getString("p_edt_er");
		String v_txt = box.getString("p_txt_er");
		String v_dt_type = box.getString("p_dt_type_er");
		String[] avgArr = box.getStringArray("p_avg_er");
		String v_avg_all = box.getString("p_avg_er_all");
		String avgSql = "";

		if(avgArr != null && !v_avg_all.equals("A")) {
			for (int z = 0; z < avgArr.length; z++) {
				if(NumberManager.getInt(avgArr[z]) == 1) {
					if (avgSql.equals("")) {
						avgSql += " AND (m.su_rate BETWEEN 0 AND 1";
					} else {
						avgSql += " OR m.su_rate BETWEEN 0 AND 1";
					}
				} else if(NumberManager.getInt(avgArr[z]) == 2) {
					if (avgSql.equals("")) {
						avgSql += " AND (m.su_rate BETWEEN 1 AND 2";
					} else {
						avgSql += " OR m.su_rate BETWEEN 1 AND 2";
					}
				} else if(NumberManager.getInt(avgArr[z]) == 3) {
					if (avgSql.equals("")) {
						avgSql += " AND (m.su_rate BETWEEN 2 AND 3";
					} else {
						avgSql += " OR m.su_rate BETWEEN 2 AND 3";
					}
				} else if(NumberManager.getInt(avgArr[z]) == 4) {
					if (avgSql.equals("")) {
						avgSql += " AND (m.su_rate BETWEEN 3 AND 4";
					} else {
						avgSql += " OR m.su_rate BETWEEN 3 AND 4";
					}
				} else if(NumberManager.getInt(avgArr[z]) == 5) {
					if (avgSql.equals("")) {
						avgSql += " AND (m.su_rate BETWEEN 4 AND 5";
					} else {
						avgSql += " OR m.su_rate BETWEEN 4 AND 5";
					}
				}
			}

			avgSql += ")";
		}

		try {
			EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

			connMgr = new DBConnectionManager();
			list = new ArrayList<DataBox>();

			sql1  = " SELECT m.edu_type																																							\n";
			sql1 += "      , m.grcode																																							\n";
			sql1 += "      , m.grcodenm																																							\n";
			sql1 += "      , m.subj																																								\n";
			sql1 += "      , m.year																																								\n";
			sql1 += "      , m.subjseq																																							\n";
			sql1 += "      , m.gyear																																							\n";
			sql1 += "      , m.grseq																																							\n";
			sql1 += "      , m.subjnm																																							\n";
			sql1 += "      , m.grseqnm																																							\n";
			sql1 += "      , m.gcd1																																								\n";
			sql1 += "      , m.gcd2																																								\n";
			sql1 += "      , m.gcd3																																								\n";
			sql1 += "      , m.gnm1																																								\n";
			sql1 += "      , m.gnm2																																								\n";
			sql1 += "      , m.gnm3																																								\n";
			sql1 += "      , m.lvcode																																							\n";
			sql1 += "      , m.lvnm																																								\n";
			sql1 += "      , m.upperclass																																						\n";
			sql1 += "      , m.ap_cnt																																							\n";
			sql1 += "      , m.st_cnt																																							\n";
			sql1 += "      , m.gr_cnt																																							\n";
			sql1 += "      , m.su_cnt																																							\n";
			sql1 += "      , m.gr_rate																																							\n";
			sql1 += "      , m.dis_sum																																							\n";
			sql1 += "      , m.su_avg																																							\n";
			sql1 += "      , m.su_rate																																							\n";
			sql1 += "      , m.sdt																																								\n";
			sql1 += "      , m.edt																																								\n";
			sql2 += " FROM ( SELECT 'B2C' edu_type																																				\n";
			sql2 += "      		  , a.asp_gubun grcode																																			\n";
			sql2 += "      		  , (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = a.asp_gubun) grcodenm																					\n";
			sql2 += "      		  , a.subj																																						\n";
			sql2 += "      		  , a.year																																						\n";
			sql2 += "      		  , a.subjseq																																					\n";
			sql2 += "      		  , f.gyear																																						\n";
			sql2 += "      		  , f.grseq																																						\n";
			sql2 += "      		  , (SELECT x.subjnm FROM tz_subj x WHERE x.subj = a.subj) subjnm																								\n";
			sql2 += "      		  , (SELECT x.grseqnm FROM tz_grseq x WHERE x.grcode = a.asp_gubun AND x.gyear = f.gyear AND x.grseq = f.grseq) grseqnm											\n";
			sql2 += "      		  , d.gubun_1 gcd1																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_1 AND x.levels = 1) gnm1														\n";
			sql2 += "      		  , d.gubun_2 gcd2																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_2 AND x.levels = 2) gnm2														\n";
			sql2 += "      		  , d.gubun_3 gcd3																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_3 AND x.levels = 3) gnm3														\n";
			sql2 += "      		  , e.lvcode																																					\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0121' AND x.code = e.lvcode AND x.levels = 2) lvnm															\n";
			sql2 += "      		  , (SELECT x.upperclass FROM tz_subj x where x.subj = a.subj) upperclass																						\n";
			sql2 += "      		  , COUNT(a.userid) ap_cnt																																		\n";
			sql2 += "      		  , COUNT(h.userid) st_cnt																																		\n";
			sql2 += "      		  , COUNT(i.userid) gr_cnt																																		\n";
			sql2 += "      		  , COUNT(g.userid) su_cnt																																		\n";
			sql2 += "      		  , CASE WHEN (COUNT(i.userid) * COUNT(h.userid)) > 0 THEN ROUND((COUNT(i.userid) / COUNT(h.userid) * 100), 2) ELSE 0 END gr_rate								\n";
			sql2 += "      		  , SUM(g.distcode1_avg) dis_sum																																\n";
			sql2 += "      		  , CASE WHEN (SUM(g.distcode1_avg) * COUNT(g.userid)) > 0 THEN ROUND(SUM(g.distcode1_avg) / COUNT(g.userid), 2) ELSE 0 END su_avg								\n";
			sql2 += "      		  , CASE WHEN (SUM(g.distcode1_avg) * COUNT(g.userid)) > 0 THEN ROUND(SUM(g.distcode1_avg) / COUNT(g.userid) / 20, 1) ELSE 0 END su_rate						\n";
			sql2 += "      		  , TO_CHAR(TO_DATE(f.edustart, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') sdt																						\n";
			sql2 += "      		  , TO_CHAR(TO_DATE(f.eduend, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') edt																						\n";
			sql2 += " 		   FROM tz_propose a																																				\n";
			sql2 += "          			INNER JOIN CKL_TREE.tm_user b ON a.userid = b.user_id AND b.use_at = 'Y'																				\n";
			sql2 += "          			 LEFT JOIN tz_subjhomegubun d ON d.subj = a.subj AND d.gubun = 'GS'																						\n";
			sql2 += "          			 LEFT JOIN tz_subjhomegubun_level e ON e.subj = a.subj																									\n";
			sql2 += "          			 LEFT JOIN tz_subjseq f ON f.subj = a.subj AND f.year = a.year AND f.subjseq = a.subjseq AND f.grcode = a.asp_gubun										\n";
			sql2 += "          			 LEFT JOIN tz_suleach g ON g.subj = a.subj AND g.year = a.year AND g.subjseq = a.subjseq AND g.sulpapernum = f.sulpapernum2 AND g.userid = b.user_id	\n";
			sql2 += "          			 LEFT JOIN tz_student h ON h.subj = a.subj AND h.year = a.year AND h.subjseq = a.subjseq AND h.userid = b.user_id										\n";
			sql2 += "          			 LEFT JOIN tz_stold i ON i.subj = a.subj AND i.year = a.year AND i.subjseq = a.subjseq AND i.userid = b.user_id AND i.isgraduated = 'Y'					\n";
			sql2 += " 		  WHERE a.ASP_GUBUN = 'N000001'																																		\n";
			sql2 += "   		AND NVL(a.cancelkind,' ') NOT IN ('P', 'F', 'D')																												\n";
			sql2 += "   		AND a.appdate IS NOT NULL																																		\n";
			sql2 += "         GROUP BY a.asp_gubun, a.subj, a.year, a.subjseq, f.gyear, f.grseq, d.gubun_1, d.gubun_2, d.gubun_3, e.lvcode, f.edustart, f.eduend								\n";
			sql2 += " 		  UNION ALL																																							\n";
			sql2 += " 		 SELECT 'B2B' edu_type																																				\n";
			sql2 += "      		  , a.asp_gubun grcode																																			\n";
			sql2 += "      		  , (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = a.asp_gubun) grcodenm																					\n";
			sql2 += "      		  , a.subj																																						\n";
			sql2 += "      		  , a.year																																						\n";
			sql2 += "      		  , a.subjseq																																					\n";
			sql2 += "      		  , f.gyear																																						\n";
			sql2 += "      		  , f.grseq																																						\n";
			sql2 += "      		  , (SELECT x.subjnm FROM tz_subj x WHERE x.subj = a.subj) subjnm																								\n";
			sql2 += "      		  , (SELECT x.grseqnm FROM tz_grseq x WHERE x.grcode = a.asp_gubun AND x.gyear = f.gyear AND x.grseq = f.grseq) grseqnm											\n";
			sql2 += "      		  , d.gubun_1 gcd1																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_1 AND x.levels = 1) gnm1														\n";
			sql2 += "      		  , d.gubun_2 gcd2																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_2 AND x.levels = 2) gnm2														\n";
			sql2 += "      		  , d.gubun_3 gcd3																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_3 AND x.levels = 3) gnm3														\n";
			sql2 += "      		  , e.lvcode																																					\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0121' AND x.code = e.lvcode AND x.levels = 2) lvnm															\n";
			sql2 += "      		  , (SELECT x.upperclass FROM tz_subj x where x.subj = a.subj) upperclass																						\n";
			sql2 += "      		  , COUNT(a.userid) ap_cnt																																		\n";
			sql2 += "      		  , COUNT(h.userid) st_cnt																																		\n";
			sql2 += "      		  , COUNT(i.userid) gr_cnt																																		\n";
			sql2 += "      		  , COUNT(g.userid) su_cnt																																		\n";
			sql2 += "      		  , CASE WHEN (COUNT(i.userid) * COUNT(h.userid)) > 0 THEN ROUND((COUNT(i.userid) / COUNT(h.userid) * 100), 2) ELSE 0 END gr_rate								\n";
			sql2 += "      		  , SUM(g.distcode1_avg) dis_sum																																\n";
			sql2 += "      		  , CASE WHEN (SUM(g.distcode1_avg) * COUNT(g.userid)) > 0 THEN ROUND(SUM(g.distcode1_avg) / COUNT(g.userid), 2) ELSE 0 END su_avg								\n";
			sql2 += "      		  , CASE WHEN (SUM(g.distcode1_avg) * COUNT(g.userid)) > 0 THEN ROUND(SUM(g.distcode1_avg) / COUNT(g.userid) / 20, 1) ELSE 0 END su_rate						\n";
			sql2 += "      		  , TO_CHAR(TO_DATE(f.edustart, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') sdt																						\n";
			sql2 += "      		  , TO_CHAR(TO_DATE(f.eduend, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') edt																						\n";
			sql2 += " 		   FROM tz_propose a																																				\n";
			sql2 += "          			INNER JOIN tz_member b ON a.userid = b.userid																											\n";
			sql2 += "          			 LEFT JOIN tz_subjhomegubun d ON d.subj = a.subj AND d.gubun = 'GS'																						\n";
			sql2 += "          			 LEFT JOIN tz_subjhomegubun_level e ON e.subj = a.subj																									\n";
			sql2 += "          			 LEFT JOIN tz_subjseq f ON f.subj = a.subj AND f.year = a.year AND f.subjseq = a.subjseq AND f.grcode = a.asp_gubun										\n";
			sql2 += "          			 LEFT JOIN tz_suleach g ON g.subj = a.subj AND g.year = a.year AND g.subjseq = a.subjseq AND g.sulpapernum = f.sulpapernum2 AND g.userid = b.userid		\n";
			sql2 += "          			 LEFT JOIN tz_student h ON h.subj = a.subj AND h.year = a.year AND h.subjseq = a.subjseq AND h.userid = b.userid										\n";
			sql2 += "          			 LEFT JOIN tz_stold i ON i.subj = a.subj AND i.year = a.year AND i.subjseq = a.subjseq AND i.userid = b.userid AND i.isgraduated = 'Y'					\n";
			sql2 += " 		  WHERE a.asp_gubun != 'N000001'																																	\n";
			sql2 += "   	    AND NVL(a.cancelkind,' ') NOT IN ('P', 'F', 'D')																												\n";
			sql2 += "   		AND a.appdate IS NOT NULL																																		\n";
			sql2 += " 		  GROUP BY a.asp_gubun, a.subj, a.year, a.subjseq, f.gyear, f.grseq, d.gubun_1, d.gubun_2, d.gubun_3, e.lvcode, f.edustart, f.eduend								\n";
			sql2 += "	   ) m 																																									\n";
			sql2 += "  WHERE m.edu_type IS NOT NULL																																				\n";

			if (v_edu_type.equals("C")) {
				sql2 += "  AND m.edu_type = 'B2C'																										\n";
			} else if (v_edu_type.equals("B")) {
				sql2 += "  AND m.edu_type = 'B2B'																										\n";
			}

			if (!v_grcode.equals("")) {
				sql2 += "  AND m.grcode = " + SQLString.Format(v_grcode) + " 																			\n";
			}

			if (!v_year.equals("")) {
				sql2 += "  AND m.gyear = " + SQLString.Format(v_year) + "	 																			\n";
			}

			if (!v_seq.equals("")) {
				sql2 += "  AND m.grseq = " + SQLString.Format(v_seq) + "	 																			\n";
			}

			if (!v_subj.equals("")) {
				sql2 += "  AND m.subj = " + SQLString.Format(v_subj) + "	 																			\n";
			}

			if (!v_g1.equals("")) {
				sql2 += "  AND m.gcd1 = " + SQLString.Format(v_g1) + "	 																				\n";
			}

			if (!v_g2.equals("")) {
				sql2 += "  AND m.gcd2 = " + SQLString.Format(v_g2) + "	 																				\n";
			}

			if (!v_g3.equals("")) {
				sql2 += "  AND m.gcd3 = " + SQLString.Format(v_g3) + "	 																				\n";
			}

			if (!v_lv.equals("")) {
				sql2 += "  AND m.lvcode = " + SQLString.Format(v_lv) + "	 																			\n";
			}

			if (v_out.equals("C01")) {
				sql2 += "  AND m.upperclass = 'X01'																										\n";
			} else if (v_out.equals("C02")) {
				sql2 += "  AND m.upperclass != 'X01'							 																		\n";
			} else if (v_out.equals("C03")) {
				sql2 += "  AND m.upperclass != 'X01' AND m.gcd1 IS NULL																					\n";
			} else if (v_out.equals("C04")) {
				sql2 += "  AND m.gcd1 IS NOT NULL								 																		\n";
			} else if (v_out.equals("C05")) {
				sql2 += "  AND m.upperclass != 'X01' AND m.gcd1 IS NOT NULL		 																		\n";
			}

			if (v_dt_type.equals("S") ) {
				sql2 += " AND m.sdt >= " + SQLString.Format(v_sdt) + " AND m.sdt <= " + SQLString.Format(v_edt) + "										\n";
			} else if (v_dt_type.equals("E") ) {
				sql2 += " AND m.edt >= " + SQLString.Format(v_sdt) + " AND m.edt <= " + SQLString.Format(v_edt) + "										\n";
			}

			if (!v_txt.equals("")) {
				sql2 += "  AND (m.subjnm like '%" + v_txt + "%' OR m.subj like '%" + v_txt + "%')														\n";
			}

			if(avgArr != null && !v_avg_all.equals("A")) {
				sql2 += avgSql												;
			}

			sql3 += "  ORDER BY m.gyear DESC, m.grseq, m.subjseq, m.grcode, m.subj															 			  ";

			query = sql1 + sql2 + sql3;

			ls = connMgr.executeQuery(query);

			countSql = "SELECT COUNT(*) " + sql2;

			int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql);

			ls.setPageSize(v_pagesize);                 //페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.
			int total_page_count = ls.getTotalPage();  	//전체 페이지 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();

				dbox.put("d_dispnum",		new Integer(totalrowcount - ls.getRowNum() + 1));
				dbox.put("d_totalpage",		new Integer(total_page_count));
				dbox.put("d_rowcount",  	new Integer(row));
				dbox.put("d_totalrowcount",	new Integer(totalrowcount));

				list.add(dbox);
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, query);
			throw new Exception("sql1 = " + query + "\r\n" + ex.getMessage());
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
	 교육 그룹 리스트
	 @param box      receive from the form object and session
	 @return ArrayList
	 */
	public ArrayList selectGrcodeList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;
		String sql = "";

		try {
			EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

			connMgr = new DBConnectionManager();
			list = new ArrayList<DataBox>();

			sql  = " SELECT grcode, grcodenm FROM tz_grcode WHERE useyn = 'Y' AND grcode != 'N000001' ORDER BY grcodenm";

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);
			}
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
	 코드 리스트
	 @param cd      receive from the form object and session
	 @return ArrayList
	 */
	public ArrayList selectCodeList(String cd) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;
		String sql = "";

		try {
			EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

			connMgr = new DBConnectionManager();
			list = new ArrayList<DataBox>();

			sql  = " SELECT code cd, code_nm nm FROM CKL_TREE.tc_cmmncode_detail WHERE code_id = " + SQLString.Format(cd) ;

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);
			}
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
	 코드 리스트
	 @param v_where      receive from the form object and session
	 @return ArrayList
	 */
	public ArrayList selectCodeList2(String v_where) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;
		String sql = "";

		try {
			EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

			connMgr = new DBConnectionManager();
			list = new ArrayList<DataBox>();

			sql  = " SELECT code cd, codenm nm FROM tz_code WHERE " + v_where ;

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);
			}
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
	 종합통계 회원 엑셀 리스트
	 @param box      receive from the form object and session
	 @return ArrayList
	 */
	public ArrayList selectUsersStatisticListExcel(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String query = "";

		String v_edu_type = box.getString("p_edu_type");
		String v_grcode = box.getString("p_grcode");
		String v_sdt = box.getString("p_sdt");
		String v_edt = box.getString("p_edt");
		String v_sex = "";
		String[] sexArr = box.getStringArray("p_sex");
		String v_age = "";
		String[] ageArr = box.getStringArray("p_age");
		String v_loc = "";
		String[] locArr = box.getStringArray("p_loc");
		String v_job = "";
		String[] jobArr = box.getStringArray("p_job");
		String v_state = box.getString("p_state");
		String sd = "";
		String ed = FormatDate.getDate("yyyy-MM-dd");

		if(sexArr != null) {
			for (int z = 0; z < sexArr.length; z++) {
				v_sex += sexArr[z];
			}
		}

		if(ageArr != null) {
			for (int z = 0; z < ageArr.length; z++) {
				if(v_age.equals("")) {
					v_age = SQLString.Format(ageArr[z]);
				} else {
					v_age += "," + SQLString.Format(ageArr[z]);
				}
			}
		}

		if(locArr != null) {
			for (int z = 0; z < locArr.length; z++) {
				if(v_loc.equals("")) {
					v_loc = SQLString.Format(locArr[z]);
				} else {
					v_loc += "," + SQLString.Format(locArr[z]);
				}

				if(locArr[z].equals("99")) {
					v_loc += ", '98'";
				}
			}
		}

		if(jobArr != null) {
			for (int z = 0; z < jobArr.length; z++) {
				if(v_job.equals("")) {
					v_job = SQLString.Format(jobArr[z]);
				} else {
					v_job += "," + SQLString.Format(jobArr[z]);
				}
			}
		}

		if(v_state.equals("7d")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "date", -7);
		} else if(v_state.equals("1m")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -1);
		} else if(v_state.equals("3m")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -3);
		} else if(v_state.equals("6m")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -6);
		} else if(v_state.equals("1y")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -12);
		} else if(v_state.equals("2y")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -24);
		} else if(v_state.equals("3y")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -36);
		}  else if(v_state.equals("3h")) {
			sd = FormatDate.getDateAdd("yyyy-MM-dd", "month", -36);
		}

		try {
			EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

			connMgr = new DBConnectionManager();
			list = new ArrayList<DataBox>();

			sql1  = " SELECT m.mber_se																								\n";
			sql1 +=	"      , m.mber_nm																								\n";
			sql1 +=	"      , m.edu_type																								\n";
			sql1 +=	"      , m.grcode																								\n";
			sql1 +=	"      , m.grcodenm																								\n";
			sql1 +=	"      , m.user_id																								\n";
			sql1 +=	"      , m.user_nm																								\n";
			sql1 +=	"      , m.sex																									\n";
			sql1 +=	"      , m.sex_nm																								\n";
			sql1 +=	"      , m.brthdy																								\n";
			sql1 +=	"      , m.region_cd																							\n";
			sql1 +=	"      , m.region																								\n";
			sql1 +=	"      , m.job																									\n";
			sql1 +=	"      , m.job_nm																								\n";
			sql1 +=	"      , m.age1																									\n";
			sql1 +=	"      , m.age2																									\n";
			sql1 +=	"      , m.indt																									\n";
			sql1 +=	"      , m.lslgdt																								\n";
			sql1 +=	"      , NVL(m2.pro_cnt, 0) pro_cnt																				\n";
			sql1 +=	"      , NVL(m3.edu_cnt, 0) edu_cnt																				\n";
			sql1 +=	"      , NVL(m4.gra_cnt, 0) gra_cnt																				\n";
			sql2 += "	FROM ( SELECT a.mber_se																						\n";
			sql2 += "				, CASE a.mber_se WHEN '01' THEN '일반' WHEN '02' THEN 'SNS' ELSE '문화광장' END mber_nm			\n";
			sql2 +=	"       		, 'B2C' edu_type																				\n";
			sql2 +=	"       		, 'N000001' grcode																				\n";
			sql2 +=	"       		, (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = 'N000001') grcodenm						\n";
			sql2 +=	"       		, a.user_id																						\n";
			sql2 +=	"       		, a.user_nm																						\n";
			sql2 +=	"       		, a.sex																							\n";
			sql2 +=	"       		, CASE a.sex WHEN 'M' THEN '남자' WHEN 'W' THEN '여자' WHEN 'F' THEN '여자' ELSE '' END sex_nm	\n";
			sql2 +=	"       		, TO_CHAR(a.brthdy, 'YYYY-MM-DD') brthdy														\n";
			sql2 +=	"       		, (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 age1								\n";
			sql2 +=	"       		, CASE WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 20 THEN '10'			\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 30 THEN '20'			\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 40 THEN '30'			\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 50 THEN '40'			\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 60 THEN '50'			\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(a.brthdy, 'YYYY')) + 1 < 100 THEN '60'			\n";
			sql2 +=	"            		   ELSE '00' 																				\n";
			sql2 += "				  END age2																						\n";
			sql2 +=	"       		, NVL(CASE a.resdnc_se 																			\n";
			sql2 += "						WHEN '01' THEN (SELECT CASE WHEN x.code_nm LIKE '서울%' THEN '01'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '부산%' THEN '02'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '대구%' THEN '03'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '인천%' THEN '04'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '광주%' THEN '05'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '대전%' THEN '06'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '울산%' THEN '07'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '세종%' THEN '08'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '경기%' THEN '09'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '강원%' THEN '10'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '충북%' THEN '11'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '충남%' THEN '12'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '전북%' THEN '13'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '전남%' THEN '14'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '경북%' THEN '15'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '경남%' THEN '16'						\n";
			sql2 +=	"                        							WHEN x.code_nm LIKE '제주%' THEN '17'						\n";
			sql2 +=	"                        							ELSE '98'													\n";
			sql2 +=	"               							   END																\n";
			sql2 +=	"        								  FROM CKL_TREE.tc_cmmncode_detail x 									\n";
			sql2 += "										 WHERE x.code_id = 'COM063' 											\n";
			sql2 += "										   AND x.code 	 = a.sido)												\n";
			sql2 +=	"           			WHEN '02' THEN NVL2(a.resdnc_ovsea, '19', '99')											\n";
			sql2 += "						ELSE ''																					\n";
			sql2 += "					  END, '99') region_cd																		\n";
			sql2 +=	"       		, CASE a.resdnc_se																				\n";
			sql2 += "					WHEN '01' THEN (SELECT x.code_nm															\n";
			sql2 += "									  FROM CKL_TREE.tc_cmmncode_detail x										\n";
			sql2 += "									 WHERE x.code_id = 'COM063'													\n";
			sql2 += "									   AND x.code    = a.sido)													\n";
			sql2 += "					WHEN '02' THEN a.resdnc_ovsea																\n";
			sql2 += "					ELSE ''																						\n";
			sql2 += "				  END region																					\n";
			sql2 +=	"       		, a.job																							\n";
			sql2 +=	"       		, (SELECT x.code_nm FROM CKL_TREE.tc_cmmncode_detail x WHERE x.code_id = 'COM056' AND x.code = a.job) job_nm	\n";
			sql2 +=	"       		, NVL(TO_CHAR(a.sbscrb_dt,'YYYY-MM-DD'), '0000-00-00') indt										\n";
			sql2 +=	"       		, TO_CHAR(a.last_login_dt, 'YYYY-MM-DD HH24:MI:SS') lslgdt										\n";
			sql2 +=	"			 FROM CKL_TREE.tm_user a																			\n";
			sql2 +=	"			WHERE a.use_at = 'Y'																				\n";
			sql2 +=	"		    UNION ALL																							\n";
			sql2 +=	"		   SELECT a.membergubun																					\n";
			sql2 +=	"       		, CASE a.membergubun WHEN 'P' THEN '일반회원' WHEN 'C' THEN '기업회원' WHEN 'U' THEN '대학회원' WHEN 'F' THEN '외국인' END	\n";
			sql2 +=	"       		, 'B2B' edu_type																				\n";
			sql2 +=	"       		, a.grcode																						\n";
			sql2 +=	"       		, (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = a.grcode)								\n";
			sql2 +=	"       		, a.userid																						\n";
			sql2 +=	"       		, a.name																						\n";
			sql2 +=	"       		, a.sex																							\n";
			sql2 +=	"       		, CASE a.sex WHEN '1' THEN '남자' WHEN '2' THEN '여자' ELSE '' END								\n";
			sql2 +=	"       		, a.memberyear || '-' || a.membermonth || '-' || a.memberday									\n";
			sql2 +=	"       		, (TO_CHAR(SYSDATE, 'YYYY') - left(a.memberyear, 4)) + 1										\n";
			sql2 +=	"       		, CASE WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 20 THEN '10'						\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 30 THEN '20'						\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 40 THEN '30'						\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 50 THEN '40'						\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 60 THEN '50'						\n";
			sql2 +=	"            		   WHEN (TO_CHAR(SYSDATE, 'YYYY') - a.memberyear) + 1 < 100 THEN '60'						\n";
			sql2 +=	"            		   ELSE '00'																				\n";
			sql2 += "				  END																							\n";
			sql2 +=	"       		, NVL(CASE WHEN a.addr like '서울%' THEN '01'													\n";
			sql2 +=	"            			   WHEN a.addr like '부산%' THEN '02'													\n";
			sql2 +=	"            			   WHEN a.addr like '대구%' THEN '03'													\n";
			sql2 +=	"            			   WHEN a.addr like '인천%' THEN '04'													\n";
			sql2 +=	"            			   WHEN a.addr like '광주%' THEN '05'													\n";
			sql2 +=	"            			   WHEN a.addr like '대전%' THEN '06'													\n";
			sql2 +=	"            			   WHEN a.addr like '울산%' THEN '07'													\n";
			sql2 +=	"            			   WHEN a.addr like '세종%' THEN '08'													\n";
			sql2 +=	"            			   WHEN a.addr like '경기%' THEN '09'													\n";
			sql2 +=	"            			   WHEN a.addr like '강원%' THEN '10'													\n";
			sql2 +=	"            			   WHEN a.addr like '충북%' THEN '11'													\n";
			sql2 +=	"            			   WHEN a.addr like '충남%' THEN '12'													\n";
			sql2 +=	"            			   WHEN a.addr like '전북%' THEN '13'													\n";
			sql2 +=	"            			   WHEN a.addr like '전남%' THEN '14'													\n";
			sql2 +=	"            			   WHEN a.addr like '경북%' THEN '15'													\n";
			sql2 +=	"            			   WHEN a.addr like '경남%' THEN '16'													\n";
			sql2 +=	"            			   WHEN a.addr like '제주%' THEN '17'													\n";
			sql2 +=	"            			   WHEN a.addr IS NOT NULL THEN '19'													\n";
			sql2 +=	"           	  END, '99')																					\n";
			sql2 +=	"       		, a.addr																						\n";
			sql2 +=	"       		, '99'																							\n";
			sql2 +=	"       		, '기타'																							\n";
			sql2 +=	"       		, NVL(TO_CHAR(TO_DATE(RPAD(REPLACE(REPLACE(REPLACE(a.indate,'-',''),':',''),' ',''),14,'0'),'YYYYMMDDHH24MISS'), 'YYYY-MM-DD'), '0000-00-00')	\n";
			sql2 +=	"       		, TO_CHAR(TO_DATE(a.lglast,'YYYYMMDDHH24MISS'), 'YYYY-MM-DD HH24:MI:SS')						\n";
			sql2 +=	"			 FROM tz_member a																					\n";
			sql2 += "			WHERE a.state   = 'Y'																				\n";
			sql2 += "			  AND a.grcode != 'N000001'																			\n";
			sql2 += "	   ) m																										\n";
			sql2 +=	"	LEFT JOIN (SELECT userid																					\n";
			sql2 += "					, COUNT(userid) pro_cnt																		\n";
			sql2 += "				 FROM TZ_PROPOSE																				\n";
			sql2 += "				GROUP BY userid) m2 ON m.user_id = m2.userid													\n";
			sql2 +=	"	LEFT JOIN (SELECT userid																					\n";
			sql2 += "					, COUNT(userid) edu_cnt 																	\n";
			sql2 += "				 FROM tz_stold																					\n";
			sql2 += "				GROUP BY userid) m3 ON m.user_id = m3.userid													\n";
			sql2 +=	"	LEFT JOIN (SELECT userid																					\n";
			sql2 += "					, COUNT(userid) gra_cnt																		\n";
			sql2 += "				 FROM tz_stold																					\n";
			sql2 += "				WHERE isgraduated = 'Y'																			\n";
			sql2 += " 				GROUP BY userid) m4 ON m.user_id = m4.userid													\n";
			sql2 +=	"  WHERE m.edu_type IS NOT NULL																					\n";

			if (v_edu_type.equals("C")) {
				sql2 += "  AND m.edu_type = 'B2C'																					\n";
			} else if (v_edu_type.equals("B")) {
				sql2 += "  AND m.edu_type = 'B2B'																					\n";
			}

			if (!v_grcode.equals("")) {
				sql2 += "  AND m.grcode = " + SQLString.Format(v_grcode) + " 														\n";
			}

			if (v_sex.equals("M")) {
				sql2 += "  AND m.sex IN ('1', 'M') 							 														\n";
			} else if (v_sex.equals("W")) {
				sql2 += "  AND m.sex IN ('2', 'W', 'F')						 														\n";
			} else if (v_sex.equals("N")) {
				sql2 += "  AND m.sex NOT IN ('1', 'M', '2', 'W', 'F')						 										\n";
			} else if (v_sex.equals("MW")) {
				sql2 += "  AND m.sex IN ('1', 'M', '2', 'W', 'F')						 											\n";
			} else if (v_sex.equals("MN")) {
				sql2 += "  AND (m.sex IN ('1', 'M') OR m.sex NOT IN ('1', 'M', '2', 'W', 'F'))										\n";
			} else if (v_sex.equals("WN")) {
				sql2 += "  AND (m.sex IN ('2', 'W', 'F') OR m.sex NOT IN ('1', 'M', '2', 'W', 'F'))									\n";
			}

			if (ageArr != null) {
				sql2 += "  AND m.age2 IN (" + v_age + ")					 														\n";
			}

			if (locArr != null) {
				sql2 += "  AND m.region_cd IN (" + v_loc + ")					 													\n";
			}

			if (jobArr != null) {
				sql2 += "  AND m.job IN (" + v_job + ")					 															\n";
			}

			if (!v_state.equals("")) {
				if (v_state.equals("n")) {
					sql2 += " AND m.lslgdt IS NULL																					\n";
				} else if (v_state.equals("3h")) {
					sql2 += " AND substr(m.lslgdt, 0, 10) < " + SQLString.Format(sd) + "											\n";
					sql2 += " AND m.lslgdt IS NOT NULL																				\n";
				} else {
					sql2 += " AND substr(m.lslgdt, 0, 10) BETWEEN " + SQLString.Format(sd) + " AND " + SQLString.Format(ed) + "		\n";
					sql2 += " AND m.lslgdt IS NOT NULL																				\n";
				}
			}

			if (v_state.equals("")) {
				sql2 += " AND m.indt BETWEEN " + SQLString.Format(v_sdt) + " AND " + SQLString.Format(v_edt) + "					\n";
			}

			sql3 +=	"  ORDER BY m.indt DESC																							  ";

			query = sql1 + sql2 + sql3;

			ls = connMgr.executeQuery(query);

			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, query);
			throw new Exception("sql1 = " + query + "\r\n" + ex.getMessage());
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
	 종합통계 교육인원 엑셀 리스트
	 @param box      receive from the form object and session
	 @return ArrayList
	 */
	public ArrayList selectEduUsersStatisticListExcel(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String query = "";

		String v_edu_type = box.getString("p_edu_type_eu");
		String v_grcode = box.getString("p_grcode_eu");
		String v_year = box.getString("p_year_eu");
		String v_seq = box.getString("p_seq_eu");
		String v_subj = box.getString("p_subj_eu");
		String v_g1 = box.getString("p_g1_eu");
		String v_g2 = box.getString("p_g2_eu");
		String v_g3 = box.getString("p_g3_eu");
		String v_lv = box.getString("p_lv_eu");
		String v_out = box.getString("p_out_eu");
		String v_sdt = box.getString("p_sdt_eu");
		String v_edt = box.getString("p_edt_eu");
		String v_learn = box.getString("p_learn_eu");
		String v_graduated = box.getString("p_graduated_eu");
		String v_sul = box.getString("p_sul_eu");
		String v_txt = box.getString("p_txt_eu");
		String v_sex = "";
		String[] sexArr = box.getStringArray("p_sex_eu");
		String v_age = "";
		String[] ageArr = box.getStringArray("p_age_eu");
		String v_loc = "";
		String[] locArr = box.getStringArray("p_loc_eu");
		String v_job = "";
		String[] jobArr = box.getStringArray("p_job_eu");

		if(sexArr != null) {
			for (int z = 0; z < sexArr.length; z++) {
				v_sex += sexArr[z];
			}
		}

		if(ageArr != null) {
			for (int z = 0; z < ageArr.length; z++) {
				if(v_age.equals("")) {
					v_age = SQLString.Format(ageArr[z]);
				} else {
					v_age += "," + SQLString.Format(ageArr[z]);
				}
			}
		}

		if(locArr != null) {
			for (int z = 0; z < locArr.length; z++) {
				if(v_loc.equals("")) {
					v_loc = SQLString.Format(locArr[z]);
				} else {
					v_loc += "," + SQLString.Format(locArr[z]);
				}

				if(locArr[z].equals("99")) {
					v_loc += ", '98'";
				}
			}
		}

		if(jobArr != null) {
			for (int z = 0; z < jobArr.length; z++) {
				if(v_job.equals("")) {
					v_job = SQLString.Format(jobArr[z]);
				} else {
					v_job += "," + SQLString.Format(jobArr[z]);
				}
			}
		}

		try {
			EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

			connMgr = new DBConnectionManager();
			list = new ArrayList<DataBox>();

			sql1  = " SELECT m.edu_type																													\n";
			sql1 += "	   , m.grcode																													\n";
			sql1 += "	   , m.grcodenm																													\n";
			sql1 += "	   , m.subj																														\n";
			sql1 += "	   , m.year																														\n";
			sql1 += "	   , m.subjseq																													\n";
			sql1 += "	   , m.subjnm																													\n";
			sql1 += "	   , m.grseqnm																													\n";
			sql1 += "	   , m.gcd1																														\n";
			sql1 += "	   , m.gcd2																														\n";
			sql1 += "	   , m.gcd3																														\n";
			sql1 += "	   , m.gnm1																														\n";
			sql1 += "	   , m.gnm2																														\n";
			sql1 += "	   , m.gnm3																														\n";
			sql1 += "	   , m.lvcode																													\n";
			sql1 += "	   , m.lvnm																														\n";
			sql1 += "	   , m.upperclass																												\n";
			sql1 += "	   , m.user_id																													\n";
			sql1 += "	   , m.user_nm																													\n";
			sql1 += "	   , m.sex																														\n";
			sql1 += "	   , m.sex_nm																													\n";
			sql1 += "	   , m.brthdy																													\n";
			sql1 += "	   , m.age1																														\n";
			sql1 += "	   , m.age2																														\n";
			sql1 += "	   , m.region_cd																												\n";
			sql1 += "	   , m.region																													\n";
			sql1 += "	   , m.job																														\n";
			sql1 += "	   , m.job_nm																													\n";
			sql1 += "	   , m.appdt																													\n";
			sql1 += "	   , NVL(m.learn_yn, 'N') learn_yn																								\n";
			sql1 += "	   , NVL(m.graduated_yn, 'N') graduated_yn																						\n";
			sql1 += "	   , m.sul2																														\n";
			sql1 += "	   , NVL(m.suleach2_yn, 'N') suleach2_yn																						\n";
			sql1 += "	   , m.suldt																													\n";
			sql1 += "	   , m.answers																													\n";
			sql1 += "	   , m.distcode1_avg																											\n";
			sql1 += "	   , m.gyear																													\n";
			sql1 += "	   , m.grseq																													\n";
			sql2 += "   FROM ( SELECT 'B2C' edu_type 																									\n";
			sql2 += "   			, a.asp_gubun grcode 																								\n";
			sql2 += "				, (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = a.asp_gubun) grcodenm										\n";
			sql2 += "				, a.subj																											\n";
			sql2 += "				, a.year																											\n";
			sql2 += "				, a.subjseq																											\n";
			sql2 += "				, (SELECT x.subjnm FROM tz_subj x WHERE x.subj = a.subj) subjnm														\n";
			sql2 += "				, (SELECT x.grseqnm FROM tz_grseq x WHERE x.grcode = a.asp_gubun AND x.gyear = f.gyear AND x.grseq = f.grseq) grseqnm	\n";
			sql2 += "				, d.gubun_1 gcd1																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_1 AND x.levels = 1) gnm1				\n";
			sql2 += "				, d.gubun_2 gcd2																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_2 AND x.levels = 2) gnm2				\n";
			sql2 += "				, d.gubun_3 gcd3 																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_3 AND x.levels = 3) gnm3				\n";
			sql2 += "				, e.lvcode																											\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0121' AND x.code = e.lvcode AND x.levels = 2) lvnm				\n";
			sql2 += "				, (SELECT x.upperclass FROM tz_subj x where x.subj = a.subj) upperclass												\n";
			sql2 += "				, b.user_id																											\n";
			sql2 += "				, b.user_nm																											\n";
			sql2 += "				, b.sex																												\n";
			sql2 += "				, CASE b.sex WHEN 'M' THEN '남자' WHEN 'W' THEN '여자' WHEN 'F' THEN '여자' ELSE '' END sex_nm						\n";
			sql2 += "				, TO_CHAR(b.brthdy, 'YYYY-MM-DD') brthdy																			\n";
			sql2 += "				, (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 age1													\n";
			sql2 += "				, CASE WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 20 THEN '10'								\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 30 THEN '20'								\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 40 THEN '30'								\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 50 THEN '40'								\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 60 THEN '50'								\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - TO_CHAR(b.brthdy, 'YYYY')) + 1 < 100 THEN '60'								\n";
			sql2 += "					   ELSE '00'																									\n";
			sql2 += "				   END age2																											\n";
			sql2 += "				, NVL(CASE b.resdnc_se																								\n";
			sql2 += "						WHEN '01' THEN (SELECT CASE WHEN x.code_nm LIKE '서울%' THEN '01'											\n";
			sql2 += "													WHEN x.code_nm LIKE '부산%' THEN '02'											\n";
			sql2 += "													WHEN x.code_nm LIKE '대구%' THEN '03'											\n";
			sql2 += "													WHEN x.code_nm LIKE '인천%' THEN '04'											\n";
			sql2 += "													WHEN x.code_nm LIKE '광주%' THEN '05'											\n";
			sql2 += "													WHEN x.code_nm LIKE '대전%' THEN '06'											\n";
			sql2 += "													WHEN x.code_nm LIKE '울산%' THEN '07'											\n";
			sql2 += "													WHEN x.code_nm LIKE '세종%' THEN '08'											\n";
			sql2 += "													WHEN x.code_nm LIKE '경기%' THEN '09'											\n";
			sql2 += "													WHEN x.code_nm LIKE '강원%' THEN '10'											\n";
			sql2 += "													WHEN x.code_nm LIKE '충북%' THEN '11'											\n";
			sql2 += "													WHEN x.code_nm LIKE '충남%' THEN '12'											\n";
			sql2 += "													WHEN x.code_nm LIKE '전북%' THEN '13'											\n";
			sql2 += "													WHEN x.code_nm LIKE '전남%' THEN '14'											\n";
			sql2 += "													WHEN x.code_nm LIKE '경북%' THEN '15'											\n";
			sql2 += "													WHEN x.code_nm LIKE '경남%' THEN '16'											\n";
			sql2 += "													WHEN x.code_nm LIKE '제주%' THEN '17'											\n";
			sql2 += "													ELSE '98'																		\n";
			sql2 += "											    END																					\n";
			sql2 += "										  FROM CKL_TREE.tc_cmmncode_detail x														\n";
			sql2 += "										 WHERE x.code_id = 'COM063'																	\n";
			sql2 += "										   AND x.code 	 = b.sido)																	\n";
			sql2 += "						WHEN '02' THEN NVL2(b.resdnc_ovsea, '19', '99')																\n";
			sql2 += "						ELSE ''																										\n";
			sql2 += "					   END, '99') region_cd																							\n";
			sql2 += "				, CASE b.resdnc_se																									\n";
			sql2 += "					   WHEN '01' THEN (SELECT x.code_nm FROM CKL_TREE.tc_cmmncode_detail x	WHERE x.code_id = 'COM063' AND x.code = b.sido)	\n";
			sql2 += "					   WHEN '02' THEN b.resdnc_ovsea																				\n";
			sql2 += "					   ELSE ''																										\n";
			sql2 += "				   END region																										\n";
			sql2 += "				, b.job																												\n";
			sql2 += "				, (SELECT x.code_nm FROM CKL_TREE.tc_cmmncode_detail x WHERE x.code_id = 'COM056' AND x.code = b.job) job_nm		\n";
			sql2 += "				, TO_CHAR(TO_DATE(a.appdate,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD') appdt											\n";
			sql2 += "				, (SELECT CASE WHEN COUNT(*) > 0 THEN 'Y' ELSE 'N' END 																\n";
			sql2 += "					 FROM tz_student x																								\n";
			sql2 += "					WHERE x.subj    = a.subj																						\n";
			sql2 += "					  AND x.year    = a.year																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq																						\n";
			sql2 += "					  AND x.userid  = b.user_id) learn_yn																			\n";
			sql2 += "				, (SELECT x.isgraduated																								\n";
			sql2 += "					 FROM tz_stold x																								\n";
			sql2 += "					WHERE x.subj    = a.subj																						\n";
			sql2 += "					  AND x.YEAR    = a.YEAR																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq																						\n";
			sql2 += "					  AND x.userid  = b.user_id) graduated_yn																		\n";
			sql2 += "				, NVL(f.sulpapernum2, 0) sul2																						\n";
			sql2 += "				, (SELECT CASE WHEN COUNT(*) > 0 THEN 'Y' ELSE 'N' END																\n";
			sql2 += "					 FROM tz_suleach x																								\n";
			sql2 += "					WHERE x.subj    = a.subj																						\n";
			sql2 += "					  AND x.year    = a.year																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq																						\n";
			sql2 += "					  AND x.grcode  = a.asp_gubun																					\n";
			sql2 += "					  AND x.userid  = b.user_id																						\n";
			sql2 += "					  AND NVL(x.sulpapernum, 0) = NVL(f.sulpapernum2, 0)) suleach2_yn												\n";
			sql2 += "				, NVL(TO_CHAR(TO_DATE(g.ldate,'YYYYMMDDHH24MISS'),'YYYY-MM-DD'), '') suldt											\n";
			sql2 += "				, g.answers																											\n";
			sql2 += "				, g.distcode1_avg																									\n";
			sql2 += "				, f.gyear																											\n";
			sql2 += "				, f.grseq																											\n";
			sql2 += "			 FROM tz_propose a																										\n";
			sql2 += "					INNER JOIN CKL_TREE.tm_user b ON a.userid = b.user_id AND b.use_at = 'Y'										\n";
			sql2 += "			 		 LEFT JOIN tz_subjhomegubun d ON d.subj = a.subj AND d.gubun = 'GS'												\n";
			sql2 += "			 		 LEFT JOIN tz_subjhomegubun_level e ON e.subj = a.subj															\n";
			sql2 += "			 		 LEFT JOIN tz_subjseq f ON f.subj = a.subj AND f.year = a.year AND f.subjseq = a.subjseq AND f.grcode = a.asp_gubun	\n";
			sql2 += "			 		 LEFT JOIN tz_suleach g ON g.subj = a.subj AND g.year = a.year AND g.subjseq = a.subjseq AND g.sulpapernum = f.sulpapernum2 AND g.userid = b.user_id	\n";
			sql2 += "			WHERE a.ASP_GUBUN = 'N000001'																							\n";
			sql2 += "			  AND NVL(a.cancelkind,' ') NOT IN ('P', 'F', 'D')																		\n";
			sql2 += "			  AND a.appdate IS NOT NULL																								\n";
			sql2 += "			UNION ALL																												\n";
			sql2 += "		   SELECT 'B2B' edu_type		 																							\n";
			sql2 += "				, a.asp_gubun grcode																								\n";
			sql2 += "				, (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = a.asp_gubun) grcodenm										\n";
			sql2 += "				, a.subj																											\n";
			sql2 += "				, a.year																											\n";
			sql2 += "				, a.subjseq																											\n";
			sql2 += "				, (SELECT x.subjnm FROM tz_subj x WHERE x.subj = a.subj) subjnm														\n";
			sql2 += "				, (SELECT x.grseqnm FROM tz_grseq x WHERE x.grcode = a.asp_gubun AND x.gyear = f.gyear AND x.grseq = f.grseq) grseqnm	\n";
			sql2 += "				, d.gubun_1 gcd1																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_1 AND x.levels = 1) gnm1				\n";
			sql2 += "				, d.gubun_2 gcd2																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_2 AND x.levels = 2) gnm2				\n";
			sql2 += "				, d.gubun_3 gcd3																									\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_3 AND x.levels = 3) gnm3				\n";
			sql2 += "				, e.lvcode																											\n";
			sql2 += "				, (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0121' AND x.code = e.lvcode AND x.levels = 2) lvnm				\n";
			sql2 += "				, (SELECT x.upperclass FROM tz_subj x where x.subj = a.subj) upperclass												\n";
			sql2 += "				, b.userid user_id																									\n";
			sql2 += "				, b.name user_nm																									\n";
			sql2 += "				, b.sex																												\n";
			sql2 += "				, CASE b.sex WHEN '1' THEN '남자' WHEN '2' THEN '여자' ELSE '' END sex_nm											\n";
			sql2 += "				, b.memberyear || '-' || b.membermonth || '-' || b.memberday brthdy													\n";
			sql2 += "				, (TO_CHAR(SYSDATE, 'YYYY') - left(b.memberyear, 4)) + 1 age1 														\n";
			sql2 += "				, CASE WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 20 THEN '10'											\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 30 THEN '20'											\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 40 THEN '30'											\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 50 THEN '40'											\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 60 THEN '50'											\n";
			sql2 += "					   WHEN (TO_CHAR(SYSDATE, 'YYYY') - b.memberyear) + 1 < 100 THEN '60'											\n";
			sql2 += "					   ELSE '00'																									\n";
			sql2 += "				   END age2																											\n";
			sql2 += "				, NVL(CASE WHEN b.addr like '서울%' THEN '01'																		\n";
			sql2 += "						   WHEN b.addr like '부산%' THEN '02'																		\n";
			sql2 += "						   WHEN b.addr like '대구%' THEN '03'																		\n";
			sql2 += "						   WHEN b.addr like '인천%' THEN '04'																		\n";
			sql2 += "						   WHEN b.addr like '광주%' THEN '05'																		\n";
			sql2 += "						   WHEN b.addr like '대전%' THEN '06'																		\n";
			sql2 += "						   WHEN b.addr like '울산%' THEN '07'																		\n";
			sql2 += "						   WHEN b.addr like '세종%' THEN '08'																		\n";
			sql2 += "						   WHEN b.addr like '경기%' THEN '09'																		\n";
			sql2 += "						   WHEN b.addr like '강원%' THEN '10'																		\n";
			sql2 += "						   WHEN b.addr like '충북%' THEN '11'																		\n";
			sql2 += "						   WHEN b.addr like '충남%' THEN '12'																		\n";
			sql2 += "						   WHEN b.addr like '전북%' THEN '13'																		\n";
			sql2 += "						   WHEN b.addr like '전남%' THEN '14'																		\n";
			sql2 += "						   WHEN b.addr like '경북%' THEN '15'																		\n";
			sql2 += "						   WHEN b.addr like '경남%' THEN '16'																		\n";
			sql2 += "						   WHEN b.addr like '제주%' THEN '17'																		\n";
			sql2 += "						   WHEN b.addr IS NOT NULL THEN '20'																		\n";
			sql2 += "					   END, '19') region_cd																							\n";
			sql2 += "				, b.addr region																										\n";
			sql2 += "				, '99' job																											\n";
			sql2 += "				, '기타' job_nm																										\n";
			sql2 += "				, TO_CHAR(TO_DATE(a.appdate,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD') appdt											\n";
			sql2 += "				, (SELECT CASE WHEN COUNT(*) > 0 THEN 'Y' ELSE 'N' END																\n";
			sql2 += "					 FROM tz_student x																								\n";
			sql2 += "					WHERE x.subj    = a.subj																						\n";
			sql2 += "					  AND x.year    = a.year																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq																						\n";
			sql2 += "					  AND x.userid  = b.userid) learn_yn																			\n";
			sql2 += "				, (SELECT x.isgraduated																								\n";
			sql2 += "					 FROM tz_stold x																								\n";
			sql2 += "					WHERE x.subj    = a.subj																						\n";
			sql2 += "					  AND x.year    = a.year																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq																						\n";
			sql2 += "					  AND x.userid  = b.userid) graduated_yn																		\n";
			sql2 += "				, nvl(f.sulpapernum2, 0) sul2																						\n";
			sql2 += "				, (SELECT CASE WHEN COUNT(*) > 0 THEN 'Y' ELSE 'N' END																\n";
			sql2 += "					 FROM tz_suleach x 																								\n";
			sql2 += "					WHERE x.subj    = a.subj 																						\n";
			sql2 += "					  AND x.year    = a.year 																						\n";
			sql2 += "					  AND x.subjseq = a.subjseq 																					\n";
			sql2 += "					  AND x.grcode  = a.asp_gubun 																					\n";
			sql2 += "					  AND x.userid  = b.userid 																						\n";
			sql2 += "					  AND NVL(x.sulpapernum, 0) = NVL(f.sulpapernum2, 0)) suleach2_yn												\n";
			sql2 += "				, NVL(TO_CHAR(TO_DATE(g.ldate,'YYYYMMDDHH24MISS'),'YYYY-MM-DD'), '-') suldt											\n";
			sql2 += "				, g.answers																											\n";
			sql2 += "				, g.distcode1_avg																									\n";
			sql2 += "				, f.gyear																											\n";
			sql2 += "				, f.grseq																											\n";
			sql2 += "			 FROM tz_propose a																										\n";
			sql2 += "					INNER JOIN tz_member b ON a.userid = b.userid																	\n";
			sql2 += "					 LEFT JOIN tz_subjhomegubun d ON d.subj = a.subj AND d.gubun = 'GS'												\n";
			sql2 += "			 		 LEFT JOIN tz_subjhomegubun_level e ON e.subj = a.subj															\n";
			sql2 += "					 LEFT JOIN tz_subjseq f ON f.subj = a.subj AND f.year = a.year AND f.subjseq = a.subjseq AND f.grcode = a.asp_gubun		\n";
			sql2 += "					 LEFT JOIN tz_suleach g ON g.subj = a.subj AND g.year = a.year AND g.subjseq = a.subjseq AND g.sulpapernum = f.sulpapernum2 AND g.userid = b.userid	\n";
			sql2 += "			WHERE a.asp_gubun != 'N000001'																							\n";
			sql2 += "			  AND NVL(a.cancelkind,' ') NOT IN ('P', 'F', 'D')																		\n";
			sql2 += "			  AND a.appdate IS NOT NULL																								\n";
			sql2 += "	   ) m																															\n";
			sql2 +=	"  WHERE m.edu_type IS NOT NULL																										\n";

			if (v_edu_type.equals("C")) {
				sql2 += "  AND m.edu_type = 'B2C'																										\n";
			} else if (v_edu_type.equals("B")) {
				sql2 += "  AND m.edu_type = 'B2B'																										\n";
			}

			if (!v_grcode.equals("")) {
				sql2 += "  AND m.grcode = " + SQLString.Format(v_grcode) + " 																			\n";
			}

			if (!v_year.equals("")) {
				sql2 += "  AND m.gyear = " + SQLString.Format(v_year) + "	 																			\n";
			}

			if (!v_seq.equals("")) {
				sql2 += "  AND m.grseq = " + SQLString.Format(v_seq) + "	 																			\n";
			}

			if (!v_subj.equals("")) {
				sql2 += "  AND m.subj = " + SQLString.Format(v_subj) + "	 																			\n";
			}

			if (!v_g1.equals("")) {
				sql2 += "  AND m.gcd1 = " + SQLString.Format(v_g1) + "	 																				\n";
			}

			if (!v_g2.equals("")) {
				sql2 += "  AND m.gcd2 = " + SQLString.Format(v_g2) + "	 																				\n";
			}

			if (!v_g3.equals("")) {
				sql2 += "  AND m.gcd3 = " + SQLString.Format(v_g3) + "	 																				\n";
			}

			if (!v_lv.equals("")) {
				sql2 += "  AND m.lvcode = " + SQLString.Format(v_lv) + "	 																			\n";
			}

			if (v_out.equals("C01")) {
				sql2 += "  AND m.upperclass = 'X01'																										\n";
			} else if (v_out.equals("C02")) {
				sql2 += "  AND m.upperclass != 'X01'							 																		\n";
			} else if (v_out.equals("C03")) {
				sql2 += "  AND m.upperclass != 'X01' AND m.gcd1 IS NULL																					\n";
			} else if (v_out.equals("C04")) {
				sql2 += "  AND m.gcd1 IS NOT NULL								 																		\n";
			} else if (v_out.equals("C05")) {
				sql2 += "  AND m.upperclass != 'X01' AND m.gcd1 IS NOT NULL		 																		\n";
			}

			sql2 += " AND m.appdt BETWEEN " + SQLString.Format(v_sdt) + " AND " + SQLString.Format(v_edt) + "											\n";

			if (v_sex.equals("M")) {
				sql2 += "  AND m.sex IN ('1', 'M') 							 																			\n";
			} else if (v_sex.equals("W")) {
				sql2 += "  AND m.sex IN ('2', 'W', 'F')						 																			\n";
			} else if (v_sex.equals("N")) {
				sql2 += "  AND m.sex NOT IN ('1', 'M', '2', 'W', 'F')						 															\n";
			} else if (v_sex.equals("MW")) {
				sql2 += "  AND m.sex IN ('1', 'M', '2', 'W', 'F')						 																\n";
			} else if (v_sex.equals("MN")) {
				sql2 += "  AND (m.sex IN ('1', 'M') OR m.sex NOT IN ('1', 'M', '2', 'W', 'F'))															\n";
			} else if (v_sex.equals("WN")) {
				sql2 += "  AND (m.sex IN ('2', 'W', 'F') OR m.sex NOT IN ('1', 'M', '2', 'W', 'F'))														\n";
			}

			if (ageArr != null) {
				sql2 += "  AND m.age2 IN (" + v_age + ")					 																			\n";
			}

			if (locArr != null) {
				sql2 += "  AND m.region_cd IN (" + v_loc + ")					 																		\n";
			}

			if (jobArr != null) {
				sql2 += "  AND m.job IN (" + v_job + ")					 																				\n";
			}

			if (!v_learn.equals("")) {
				sql2 += "  AND m.learn_yn = " + SQLString.Format(v_learn) + "	 																		\n";
			}

			if (!v_graduated.equals("")) {
				sql2 += "  AND m.graduated_yn = " + SQLString.Format(v_graduated) + "	 																\n";
			}

			if (!v_sul.equals("")) {
				sql2 += "  AND m.suleach2_yn = " + SQLString.Format(v_sul) + "	 																		\n";
			}

			if (!v_txt.equals("")) {
				sql2 += "  AND (m.subjnm like '%" + v_txt + "%' OR m.subj like '%" + v_txt + "%' OR m.user_id like '%" + v_txt + "%' OR m.user_nm like '%" + v_txt + "%')	\n";
			}

			sql3 += "  ORDER BY m.appdt DESC																						  				";


			query = sql1 + sql2 + sql3;

			ls = connMgr.executeQuery(query);

			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, query);
			throw new Exception("sql1 = " + query + "\r\n" + ex.getMessage());
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
	 교육성과 통계 리스트
	 @param box      receive from the form object and session
	 @return ArrayList
	 */
	public ArrayList selectEduResultStatisticListExcel(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String query = "";

		String v_edu_type = box.getString("p_edu_type_er");
		String v_grcode = box.getString("p_grcode_er");
		String v_year = box.getString("p_year_er");
		String v_seq = box.getString("p_seq_er");
		String v_subj = box.getString("p_subj_er");
		String v_g1 = box.getString("p_g1_er");
		String v_g2 = box.getString("p_g2_er");
		String v_g3 = box.getString("p_g3_er");
		String v_lv = box.getString("p_lv_er");
		String v_out = box.getString("p_out_er");
		String v_sdt = box.getString("p_sdt_er");
		String v_edt = box.getString("p_edt_er");
		String v_txt = box.getString("p_txt_er");
		String v_dt_type = box.getString("p_dt_type_er");
		String[] avgArr = box.getStringArray("p_avg_er");
		String v_avg_all = box.getString("p_avg_er_all");
		String avgSql = "";

		if(avgArr != null && !v_avg_all.equals("A")) {
			for (int z = 0; z < avgArr.length; z++) {
				if(NumberManager.getInt(avgArr[z]) == 1) {
					if (avgSql.equals("")) {
						avgSql += " AND (m.su_rate BETWEEN 0 AND 1";
					} else {
						avgSql += " OR m.su_rate BETWEEN 0 AND 1";
					}
				} else if(NumberManager.getInt(avgArr[z]) == 2) {
					if (avgSql.equals("")) {
						avgSql += " AND (m.su_rate BETWEEN 1 AND 2";
					} else {
						avgSql += " OR m.su_rate BETWEEN 1 AND 2";
					}
				} else if(NumberManager.getInt(avgArr[z]) == 3) {
					if (avgSql.equals("")) {
						avgSql += " AND (m.su_rate BETWEEN 2 AND 3";
					} else {
						avgSql += " OR m.su_rate BETWEEN 2 AND 3";
					}
				} else if(NumberManager.getInt(avgArr[z]) == 4) {
					if (avgSql.equals("")) {
						avgSql += " AND (m.su_rate BETWEEN 3 AND 4";
					} else {
						avgSql += " OR m.su_rate BETWEEN 3 AND 4";
					}
				} else if(NumberManager.getInt(avgArr[z]) == 5) {
					if (avgSql.equals("")) {
						avgSql += " AND (m.su_rate BETWEEN 4 AND 5";
					} else {
						avgSql += " OR m.su_rate BETWEEN 4 AND 5";
					}
				}
			}

			avgSql += ")";
		}

		try {
			EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

			connMgr = new DBConnectionManager();
			list = new ArrayList<DataBox>();

			sql1  = " SELECT m.edu_type																																							\n";
			sql1 += "      , m.grcode																																							\n";
			sql1 += "      , m.grcodenm																																							\n";
			sql1 += "      , m.subj																																								\n";
			sql1 += "      , m.year																																								\n";
			sql1 += "      , m.subjseq																																							\n";
			sql1 += "      , m.gyear																																							\n";
			sql1 += "      , m.grseq																																							\n";
			sql1 += "      , m.subjnm																																							\n";
			sql1 += "      , m.grseqnm																																							\n";
			sql1 += "      , m.gcd1																																								\n";
			sql1 += "      , m.gcd2																																								\n";
			sql1 += "      , m.gcd3																																								\n";
			sql1 += "      , m.gnm1																																								\n";
			sql1 += "      , m.gnm2																																								\n";
			sql1 += "      , m.gnm3																																								\n";
			sql1 += "      , m.lvcode																																							\n";
			sql1 += "      , m.lvnm																																								\n";
			sql1 += "      , m.upperclass																																						\n";
			sql1 += "      , m.ap_cnt																																							\n";
			sql1 += "      , m.st_cnt																																							\n";
			sql1 += "      , m.gr_cnt																																							\n";
			sql1 += "      , m.su_cnt																																							\n";
			sql1 += "      , m.gr_rate																																							\n";
			sql1 += "      , m.dis_sum																																							\n";
			sql1 += "      , m.su_avg																																							\n";
			sql1 += "      , m.su_rate																																							\n";
			sql1 += "      , m.sdt																																							\n";
			sql1 += "      , m.edt																																							\n";
			sql2 += " FROM ( SELECT 'B2C' edu_type																																				\n";
			sql2 += "      		  , a.asp_gubun grcode																																			\n";
			sql2 += "      		  , (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = a.asp_gubun) grcodenm																					\n";
			sql2 += "      		  , a.subj																																						\n";
			sql2 += "      		  , a.year																																						\n";
			sql2 += "      		  , a.subjseq																																					\n";
			sql2 += "      		  , f.gyear																																						\n";
			sql2 += "      		  , f.grseq																																						\n";
			sql2 += "      		  , (SELECT x.subjnm FROM tz_subj x WHERE x.subj = a.subj) subjnm																								\n";
			sql2 += "      		  , (SELECT x.grseqnm FROM tz_grseq x WHERE x.grcode = a.asp_gubun AND x.gyear = f.gyear AND x.grseq = f.grseq) grseqnm											\n";
			sql2 += "      		  , d.gubun_1 gcd1																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_1 AND x.levels = 1) gnm1														\n";
			sql2 += "      		  , d.gubun_2 gcd2																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_2 AND x.levels = 2) gnm2														\n";
			sql2 += "      		  , d.gubun_3 gcd3																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_3 AND x.levels = 3) gnm3														\n";
			sql2 += "      		  , e.lvcode																																					\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0121' AND x.code = e.lvcode AND x.levels = 2) lvnm															\n";
			sql2 += "      		  , (SELECT x.upperclass FROM tz_subj x where x.subj = a.subj) upperclass																						\n";
			sql2 += "      		  , COUNT(a.userid) ap_cnt																																		\n";
			sql2 += "      		  , COUNT(h.userid) st_cnt																																		\n";
			sql2 += "      		  , COUNT(i.userid) gr_cnt																																		\n";
			sql2 += "      		  , COUNT(g.userid) su_cnt																																		\n";
			sql2 += "      		  , CASE WHEN (COUNT(i.userid) * COUNT(h.userid)) > 0 THEN ROUND((COUNT(i.userid) / COUNT(h.userid) * 100), 2) ELSE 0 END gr_rate								\n";
			sql2 += "      		  , SUM(g.distcode1_avg) dis_sum																																\n";
			sql2 += "      		  , CASE WHEN (SUM(g.distcode1_avg) * COUNT(g.userid)) > 0 THEN ROUND(SUM(g.distcode1_avg) / COUNT(g.userid), 2) ELSE 0 END su_avg								\n";
			sql2 += "      		  , CASE WHEN (SUM(g.distcode1_avg) * COUNT(g.userid)) > 0 THEN ROUND(SUM(g.distcode1_avg) / COUNT(g.userid) / 20, 1) ELSE 0 END su_rate						\n";
			sql2 += "      		  , TO_CHAR(TO_DATE(f.edustart, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') sdt																						\n";
			sql2 += "      		  , TO_CHAR(TO_DATE(f.eduend, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') edt																						\n";
			sql2 += " 		   FROM tz_propose a																																				\n";
			sql2 += "          			INNER JOIN CKL_TREE.tm_user b ON a.userid = b.user_id AND b.use_at = 'Y'																				\n";
			sql2 += "          			 LEFT JOIN tz_subjhomegubun d ON d.subj = a.subj AND d.gubun = 'GS'																						\n";
			sql2 += "          			 LEFT JOIN tz_subjhomegubun_level e ON e.subj = a.subj																									\n";
			sql2 += "          			 LEFT JOIN tz_subjseq f ON f.subj = a.subj AND f.year = a.year AND f.subjseq = a.subjseq AND f.grcode = a.asp_gubun										\n";
			sql2 += "          			 LEFT JOIN tz_suleach g ON g.subj = a.subj AND g.year = a.year AND g.subjseq = a.subjseq AND g.sulpapernum = f.sulpapernum2 AND g.userid = b.user_id	\n";
			sql2 += "          			 LEFT JOIN tz_student h ON h.subj = a.subj AND h.year = a.year AND h.subjseq = a.subjseq AND h.userid = b.user_id										\n";
			sql2 += "          			 LEFT JOIN tz_stold i ON i.subj = a.subj AND i.year = a.year AND i.subjseq = a.subjseq AND i.userid = b.user_id AND i.isgraduated = 'Y'					\n";
			sql2 += " 		  WHERE a.ASP_GUBUN = 'N000001'																																		\n";
			sql2 += "   		AND NVL(a.cancelkind,' ') NOT IN ('P', 'F', 'D')																												\n";
			sql2 += "   		AND a.appdate IS NOT NULL																																		\n";
			sql2 += "         GROUP BY a.asp_gubun, a.subj, a.year, a.subjseq, f.gyear, f.grseq, d.gubun_1, d.gubun_2, d.gubun_3, e.lvcode, f.edustart, f.eduend								\n";
			sql2 += " 		  UNION ALL																																							\n";
			sql2 += " 		 SELECT 'B2B' edu_type																																				\n";
			sql2 += "      		  , a.asp_gubun grcode																																			\n";
			sql2 += "      		  , (SELECT x.grcodenm FROM tz_grcode x WHERE x.grcode = a.asp_gubun) grcodenm																					\n";
			sql2 += "      		  , a.subj																																						\n";
			sql2 += "      		  , a.year																																						\n";
			sql2 += "      		  , a.subjseq																																					\n";
			sql2 += "      		  , f.gyear																																						\n";
			sql2 += "      		  , f.grseq																																						\n";
			sql2 += "      		  , (SELECT x.subjnm FROM tz_subj x WHERE x.subj = a.subj) subjnm																								\n";
			sql2 += "      		  , (SELECT x.grseqnm FROM tz_grseq x WHERE x.grcode = a.asp_gubun AND x.gyear = f.gyear AND x.grseq = f.grseq) grseqnm											\n";
			sql2 += "      		  , d.gubun_1 gcd1																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_1 AND x.levels = 1) gnm1														\n";
			sql2 += "      		  , d.gubun_2 gcd2																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_2 AND x.levels = 2) gnm2														\n";
			sql2 += "      		  , d.gubun_3 gcd3																																				\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0110' AND x.code = d.gubun_3 AND x.levels = 3) gnm3														\n";
			sql2 += "      		  , e.lvcode																																					\n";
			sql2 += "      		  , (SELECT x.codenm FROM tz_code x WHERE x.gubun = '0121' AND x.code = e.lvcode AND x.levels = 2) lvnm															\n";
			sql2 += "      		  , (SELECT x.upperclass FROM tz_subj x where x.subj = a.subj) upperclass																						\n";
			sql2 += "      		  , COUNT(a.userid) ap_cnt																																		\n";
			sql2 += "      		  , COUNT(h.userid) st_cnt																																		\n";
			sql2 += "      		  , COUNT(i.userid) gr_cnt																																		\n";
			sql2 += "      		  , COUNT(g.userid) su_cnt																																		\n";
			sql2 += "      		  , CASE WHEN (COUNT(i.userid) * COUNT(h.userid)) > 0 THEN ROUND((COUNT(i.userid) / COUNT(h.userid) * 100), 2) ELSE 0 END gr_rate								\n";
			sql2 += "      		  , SUM(g.distcode1_avg) dis_sum																																\n";
			sql2 += "      		  , CASE WHEN (SUM(g.distcode1_avg) * COUNT(g.userid)) > 0 THEN ROUND(SUM(g.distcode1_avg) / COUNT(g.userid), 2) ELSE 0 END su_avg								\n";
			sql2 += "      		  , CASE WHEN (SUM(g.distcode1_avg) * COUNT(g.userid)) > 0 THEN ROUND(SUM(g.distcode1_avg) / COUNT(g.userid) / 20, 1) ELSE 0 END su_rate						\n";
			sql2 += "      		  , TO_CHAR(TO_DATE(f.edustart, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') sdt																						\n";
			sql2 += "      		  , TO_CHAR(TO_DATE(f.eduend, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD') edt																						\n";
			sql2 += " 		   FROM tz_propose a																																				\n";
			sql2 += "          			INNER JOIN tz_member b ON a.userid = b.userid																											\n";
			sql2 += "          			 LEFT JOIN tz_subjhomegubun d ON d.subj = a.subj AND d.gubun = 'GS'																						\n";
			sql2 += "          			 LEFT JOIN tz_subjhomegubun_level e ON e.subj = a.subj																									\n";
			sql2 += "          			 LEFT JOIN tz_subjseq f ON f.subj = a.subj AND f.year = a.year AND f.subjseq = a.subjseq AND f.grcode = a.asp_gubun										\n";
			sql2 += "          			 LEFT JOIN tz_suleach g ON g.subj = a.subj AND g.year = a.year AND g.subjseq = a.subjseq AND g.sulpapernum = f.sulpapernum2 AND g.userid = b.userid		\n";
			sql2 += "          			 LEFT JOIN tz_student h ON h.subj = a.subj AND h.year = a.year AND h.subjseq = a.subjseq AND h.userid = b.userid										\n";
			sql2 += "          			 LEFT JOIN tz_stold i ON i.subj = a.subj AND i.year = a.year AND i.subjseq = a.subjseq AND i.userid = b.userid AND i.isgraduated = 'Y'					\n";
			sql2 += " 		  WHERE a.asp_gubun != 'N000001'																																	\n";
			sql2 += "   	    AND NVL(a.cancelkind,' ') NOT IN ('P', 'F', 'D')																												\n";
			sql2 += "   		AND a.appdate IS NOT NULL																																		\n";
			sql2 += " 		  GROUP BY a.asp_gubun, a.subj, a.year, a.subjseq, f.gyear, f.grseq, d.gubun_1, d.gubun_2, d.gubun_3, e.lvcode, f.edustart, f.eduend								\n";
			sql2 += "	   ) m 																																									\n";
			sql2 += "  WHERE m.edu_type IS NOT NULL																																				\n";


			if (v_edu_type.equals("C")) {
				sql2 += "  AND m.edu_type = 'B2C'																										\n";
			} else if (v_edu_type.equals("B")) {
				sql2 += "  AND m.edu_type = 'B2B'																										\n";
			}

			if (!v_grcode.equals("")) {
				sql2 += "  AND m.grcode = " + SQLString.Format(v_grcode) + " 																			\n";
			}

			if (!v_year.equals("")) {
				sql2 += "  AND m.gyear = " + SQLString.Format(v_year) + "	 																			\n";
			}

			if (!v_seq.equals("")) {
				sql2 += "  AND m.grseq = " + SQLString.Format(v_seq) + "	 																			\n";
			}

			if (!v_subj.equals("")) {
				sql2 += "  AND m.subj = " + SQLString.Format(v_subj) + "	 																			\n";
			}

			if (!v_g1.equals("")) {
				sql2 += "  AND m.gcd1 = " + SQLString.Format(v_g1) + "	 																				\n";
			}

			if (!v_g2.equals("")) {
				sql2 += "  AND m.gcd2 = " + SQLString.Format(v_g2) + "	 																				\n";
			}

			if (!v_g3.equals("")) {
				sql2 += "  AND m.gcd3 = " + SQLString.Format(v_g3) + "	 																				\n";
			}

			if (!v_lv.equals("")) {
				sql2 += "  AND m.lvcode = " + SQLString.Format(v_lv) + "	 																			\n";
			}

			if (v_out.equals("C01")) {
				sql2 += "  AND m.upperclass = 'X01'																										\n";
			} else if (v_out.equals("C02")) {
				sql2 += "  AND m.upperclass != 'X01'							 																		\n";
			} else if (v_out.equals("C03")) {
				sql2 += "  AND m.upperclass != 'X01' AND m.gcd1 IS NULL																					\n";
			} else if (v_out.equals("C04")) {
				sql2 += "  AND m.gcd1 IS NOT NULL								 																		\n";
			} else if (v_out.equals("C05")) {
				sql2 += "  AND m.upperclass != 'X01' AND m.gcd1 IS NOT NULL		 																		\n";
			}

			if (v_dt_type.equals("S") ) {
				sql2 += " AND m.sdt >= " + SQLString.Format(v_sdt) + " AND m.sdt <= " + SQLString.Format(v_edt) + "										\n";
			} else if (v_dt_type.equals("E") ) {
				sql2 += " AND m.edt >= " + SQLString.Format(v_sdt) + " AND m.edt <= " + SQLString.Format(v_edt) + "										\n";
			}

			if (!v_txt.equals("")) {
				sql2 += "  AND (m.subjnm like '%" + v_txt + "%' OR m.subj like '%" + v_txt + "%')														\n";
			}

			if(avgArr != null && !v_avg_all.equals("A")) {
				sql2 += avgSql												;
			}

			sql3 += "  ORDER BY m.gyear DESC, m.grseq, m.subjseq, m.grcode, m.subj																				 			  ";

			query = sql1 + sql2 + sql3;

			ls = connMgr.executeQuery(query);

			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, query);
			throw new Exception("sql1 = " + query + "\r\n" + ex.getMessage());
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