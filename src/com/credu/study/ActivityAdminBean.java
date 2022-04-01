//**********************************************************
//  1. 제      목: ACTIVITY ADMIN BEAN
//  2. 프로그램명: ActivityAdminBean.java
//  3. 개      요: 참여도관리 관리자 bean
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.11
//  7. 수      정: 
//**********************************************************
package com.credu.study;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.study.ActivityAdminData;

public class ActivityAdminBean {
    @SuppressWarnings("unused")
	private ConfigSet config;
    private int row;
	
    public ActivityAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
    참여도관리 리스트 
    @param box      receive from the form object and session
    @return ArrayList
    */
     @SuppressWarnings("unchecked")
	public ArrayList selectActivityList(RequestBox box) throws Exception {
        DBConnectionManager	connMgr	= null;
        DataBox 			dbox1	= null;
        ListSet				ls1		= null;
        ListSet				ls2		= null;
        ArrayList			list1	= null;
        
        String	sql1			= "";
        String	head_sql1		= "";
		String	body_sql1		= "";		
        String	group_sql1		= "";
        String	order_sql1		= "";
		String	count_sql1		= "";

        String  ss_grcode   	= box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    	= box.getStringDefault("s_gyear","ALL");     //년도
        String  ss_grseq    	= box.getStringDefault("s_grseq","ALL");     //교육차수
        String  ss_uclass   	= box.getStringDefault("s_upperclass","ALL");    //과정분류
        String  ss_mclass   	= box.getStringDefault("s_middleclass","ALL");   //과정분류
        String  ss_lclass   	= box.getStringDefault("s_lowerclass","ALL");    //과정분류
        String  ss_subjcourse	= box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  	= box.getStringDefault("s_subjseq","ALL");   //과정 차수
        String  ss_action   	= box.getString("s_action");

        String  v_orderColumn	= box.getString("p_orderColumn");           //정렬할 컬럼명
        String  v_orderType		= box.getString("p_orderType");          //정렬할 순서
        int		v_pageno		= box.getInt("p_pageno");
        int		v_pagesize		= box.getInt("p_pagesize");

        try {
            if(ss_action.equals("go")){
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();

                head_sql1 =" SELECT  a.course, a.cyear, a.courseseq, a.coursenm, a.subj, a.YEAR, a.subjnm, ";
            	head_sql1+="         (select grcodenm from tz_grcode where grcode=a.grcode and gyear=a.gyear and grseq = a.grseq) grcodenm, ";
                head_sql1+="         a.subjseq, a.subjseqgr, b.userid, b.NAME, ";
            	head_sql1+="         CASE ";
            	head_sql1+="            WHEN b.membergubun = 'P' ";
            	head_sql1+="               THEN '개인' ";
            	head_sql1+="            WHEN b.membergubun = 'C' ";
            	head_sql1+="               THEN '기업' ";
            	head_sql1+="            WHEN b.membergubun = 'U' ";
            	head_sql1+="               THEN '대학교' ";
            	head_sql1+="            WHEN b.membergubun = 'U' ";
            	head_sql1+="               THEN '대학교' ";
            	head_sql1+="            ELSE '-' ";
            	head_sql1+="         END AS membergubunnm, ";
            	//head_sql1+="         (SELECT SUM(lesson_count) ";
            	//head_sql1+="            FROM tz_progress ";
            	//head_sql1+="           WHERE subj = a.subj ";
            	//head_sql1+="             AND year = a.year ";
            	//head_sql1+="             AND subjseq = a.subjseq ";
            	//head_sql1+="             AND userid = c.userid ";
            	//head_sql1+="         ) logincnt, ";
            	head_sql1+="         (SELECT COUNT(*) ";
            	head_sql1+="            FROM tz_subjloginid ";
            	head_sql1+="           WHERE subj = a.subj ";
            	head_sql1+="             AND year = a.year ";
            	head_sql1+="             AND subjseq = a.subjseq ";
            	head_sql1+="             AND userid = c.userid ";
            	head_sql1+="         ) logincnt, ";
            	head_sql1+="         (SELECT COUNT(*) ";
            	head_sql1+="            FROM tz_qna ";
            	head_sql1+="           WHERE subj = a.subj ";
            	head_sql1+="             AND year = a.year ";
            	head_sql1+="             AND subjseq = a.subjseq ";
            	head_sql1+="             AND inuserid = c.userid ";
            	head_sql1+="         ) qnacnt, ";
            	head_sql1+="         (SELECT COUNT(*) ";
            	head_sql1+="            FROM tz_toron ";
            	head_sql1+="           WHERE subj = a.subj ";
            	head_sql1+="             AND year = a.year ";
            	head_sql1+="             AND subjseq = a.subjseq ";
            	head_sql1+="             AND aduserid = c.userid ";
            	head_sql1+="         ) toroncnt, ";
            	head_sql1+="         a.isonoff, a.isbelongcourse, a.subjcnt,  ";
            	head_sql1+="         c.etc1, c.etc2, ";
            	head_sql1+="         a.wetc1, a.wetc2,  ";
            	head_sql1+="         c.avetc1, c.avetc2 ";
            	body_sql1+="    FROM vz_scsubjseq a, tz_member b, tz_student c ";
            	body_sql1+="   WHERE 1 = 1 ";
            	body_sql1+="     AND c.userid = b.userid ";
            	body_sql1+="     AND c.subj = a.subj ";
            	body_sql1+="     AND c.YEAR = a.YEAR ";
            	body_sql1+="     AND c.subjseq = a.subjseq ";
            	body_sql1+="	 AND A.GRCODE = "+SQLString.Format(ss_grcode);
            	body_sql1+="	 AND A.GRCODE = B.GRCODE";
            	body_sql1+="	 AND A.GYEAR = "+SQLString.Format(ss_gyear);
            	body_sql1+="	 AND A.GRSEQ = "+SQLString.Format(ss_grseq);
            	/*
            	if (!ss_grcode.equals("ALL"))     body_sql1+= " and a.grcode = "+SQLString.Format(ss_grcode);
            	if (!ss_gyear.equals("ALL"))      body_sql1+= " and a.gyear = "+SQLString.Format(ss_gyear);
                if (!ss_grseq.equals("ALL"))      body_sql1+= " and a.grseq = "+SQLString.Format(ss_grseq);
                */
                if (!ss_uclass.equals("ALL"))     body_sql1+= " and a.scupperclass = "+SQLString.Format(ss_uclass);
                if (!ss_mclass.equals("ALL"))     body_sql1+= " and a.scmiddleclass = "+SQLString.Format(ss_mclass);
                if (!ss_lclass.equals("ALL"))     body_sql1+= " and a.sclowerclass = "+SQLString.Format(ss_lclass);
                if (!ss_subjcourse.equals("ALL")) body_sql1+= " and a.scsubj = "+SQLString.Format(ss_subjcourse);
                if (!ss_subjseq.equals("ALL"))    body_sql1+= " and a.scsubjseq = "+SQLString.Format(ss_subjseq);
            	
            	order_sql1+="ORDER BY a.course, a.courseseq, b.NAME, a.subj, a.subjseq ";

            	sql1 = head_sql1+ body_sql1+ group_sql1+ order_sql1 ;
            	//System.out.println("##########"+sql1);
				ls1 = connMgr.executeQuery(sql1);
				Log.info.println(" 참여도 목록: "+sql1);

				count_sql1 = "select count(*) " + body_sql1;
				int totalrowcount = BoardPaging.getTotalRow(connMgr, count_sql1) ;

                ls1.setPageSize(v_pagesize);                   	//페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno, totalrowcount);    //현재페이지번호를 세팅한다.
                int total_page_count = ls1.getTotalPage();  	//전체 페이지 수를 반환한다

                while (ls1.next()) {
                    dbox1 = ls1.getDataBox();
                    
                    dbox1.put("d_dispnum",		new Integer(totalrowcount - ls1.getRowNum() + 1));
                    dbox1.put("d_totalpage",	new Integer(total_page_count));
                    dbox1.put("d_rowcount",  	new Integer(row));
                    dbox1.put("d_totalrowcount",new Integer(totalrowcount));

                    list1.add(dbox1);
                }       // END while
            }       // END if

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
    게시판 활동 리스트
    @param box          receive from the form object and session
    @return ArrayList   모듈 리스트
    */
    @SuppressWarnings("unchecked")
    public ArrayList selectBbsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //ActivityAdminData data = null;
        DataBox dbox1		= null;

        String v_userid  = box.getString("p_userid");
        String v_subj    = box.getString("p_subj");
        String v_year    = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            
            sql ="  select type, no, userid, title, sdate, ldate, subj, year, subjseq, tpcode ";
            sql+="    from (";
            sql+="          select 'SUBJ' type, a.seq no, a.inuserid userid,a.title, '' tpcode, ";
            sql+="                 a.indate sdate, a.ldate, a.subj, a.year, a.subjseq ";
            sql+="            from tz_qna a  ";
            sql+="           where a.inuserid = " + StringManager.makeSQL(v_userid);
            sql+="             and a.kind = '0' ";
            sql+="             and a.subj = " + StringManager.makeSQL(v_subj);
            sql+="             and a.year = " + StringManager.makeSQL(v_year);
            sql+="             and a.subjseq = " + StringManager.makeSQL(v_subjseq);
            sql+="           union all ";
            sql+="          select 'TOR' type,  a.seq no, a.aduserid userid, a.title, a.tpcode, ";
            sql+="                 a.addate sdate, a.ldate, a.subj, a.year, a.subjseq ";
            sql+="            from tz_toron a ";
            sql+="           where a.aduserid = " + StringManager.makeSQL(v_userid);
            sql+="             and a.subj = " + StringManager.makeSQL(v_subj);
            sql+="             and a.year = " + StringManager.makeSQL(v_year);
            sql+="             and a.subjseq = " + StringManager.makeSQL(v_subjseq);
            sql+="         )";
            sql+=" order by sdate desc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                dbox1 = ls.getDataBox();
                list.add(dbox1);
            	
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
    접속 리스트
    @param box          receive from the form object and session
    @return ArrayList   모듈 리스트
    */
    @SuppressWarnings("unchecked")
    public ArrayList selectLoginList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //ActivityAdminData data = null;
        DataBox dbox1		= null;

        String v_userid  = box.getString("p_userid");
        String v_subj    = box.getString("p_subj");
        String v_year    = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql =" select subj, year, subjseq, userid, lgip, ldate  ";
            sql+="  from tz_subjloginid ";
            sql+="  where year    = " + StringManager.makeSQL(v_year);
            sql+="    and subj    = " + StringManager.makeSQL(v_subj);
            sql+="    and subjseq = " + StringManager.makeSQL(v_subjseq);
            sql+="    and userid  = " + StringManager.makeSQL(v_userid);
            sql+=" order by ldate desc      ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                dbox1 = ls.getDataBox();
                list.add(dbox1);
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
   참여도정보 상세보기
    @param box          receive from the form object and session
    @return MenuData    조회한 상세정보
    */
    public ActivityAdminData selectViewActivity(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ActivityAdminData data = null;

        String v_userid  = box.getString("p_userid");
        String v_subj    = box.getString("p_subj");
        String v_year    = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT a.course, a.cyear, a.courseseq, a.coursenm, a.subj, a.YEAR, a.subjnm, ";
            sql+="         (select grcodenm from tz_grcode where grcode=a.grcode and gyear=a.gyear and grseq = a.grseq) grcodenm, ";
        	sql+="         a.subjseq, a.subjseqgr, b.userid, b.NAME, ";
        	sql+="         (SELECT SUM (lesson_count) ";
        	sql+="            FROM tz_progress ";
        	sql+="           WHERE subj = a.subj ";
        	sql+="             AND YEAR = a.YEAR ";
        	sql+="             AND subjseq = a.subjseq ";
        	sql+="             AND userid = c.userid) logincnt, ";
        	sql+="         (SELECT COUNT (*) ";
        	sql+="            FROM tz_qna ";
        	sql+="           WHERE subj = a.subj ";
        	sql+="             AND YEAR = a.YEAR ";
        	sql+="             AND subjseq = a.subjseq ";
        	sql+="             AND inuserid = c.userid) qnacnt, ";
        	sql+="         (SELECT COUNT (*) ";
        	sql+="            FROM tz_toron ";
        	sql+="           WHERE subj = a.subj ";
        	sql+="             AND YEAR = a.YEAR ";
        	sql+="             AND subjseq = a.subjseq ";
        	sql+="             AND aduserid = c.userid) toroncnt, ";
        	sql+="         a.isonoff, a.isbelongcourse, a.subjcnt, c.etc1, c.etc2, a.wetc1, ";
        	sql+="         a.wetc2, c.avetc1, c.avetc2 ";
        	sql+="    FROM vz_scsubjseq a, tz_member b, tz_student c ";
        	sql+="   WHERE 1 = 1 ";
        	sql+="     AND c.userid = b.userid ";
        	sql+="     AND c.subj = a.subj ";
        	sql+="     AND c.YEAR = a.YEAR ";
        	sql+="     AND c.subjseq = a.subjseq ";
        	sql+="     AND b.userid = " + StringManager.makeSQL(v_userid);
        	sql+="     AND a.subj = " + StringManager.makeSQL(v_subj);
        	sql+="     AND a.year = " + StringManager.makeSQL(v_year);
        	sql+="     AND a.subjseq = " + StringManager.makeSQL(v_subjseq);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data=new ActivityAdminData();
                data.setGrcodenm(ls.getString("grcodenm"));
                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setSubjseq(ls.getString("subjseq"));
                data.setSubjseqgr(ls.getString("subjseqgr"));
                data.setUserid(ls.getString("userid"));
                data.setName(ls.getString("name"));
                data.setLogincnt(ls.getInt("logincnt"));
                data.setQnacnt(ls.getInt("qnacnt"));
                data.setToroncnt(ls.getInt("toroncnt"));
                data.setEtc1(ls.getDouble("etc1"));
                data.setEtc2(ls.getDouble("etc2"));
                data.setWetc1(ls.getDouble("wetc1"));
                data.setWetc2(ls.getDouble("wetc2"));
                data.setAvetc1(ls.getDouble("avetc1"));
                data.setAvetc2(ls.getDouble("avetc2"));
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
        return data;
    }
    
    /**
    참여도, 기타 수정
    @param box      receive from the form object and session
    @return int
    */
     public int updateActivity(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1         = "";
        int isOk            = 0;

        String v_userid  = box.getString("p_userid");
        String v_subj    = box.getString("p_subj");
        String v_year    = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_etc1    = box.getString("p_etc1");
        String v_etc2    = box.getString("p_etc2");
        String s_userid  = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql1 =" update tz_student ";
            sql1+="   set etc1 = ?, ";
            sql1+="       etc2 = ?, ";
            sql1+="       avetc1 = ? * (select wetc1 from tz_subjseq where subj = ? and year = ? and subjseq = ?) / 100, ";
            sql1+="       avetc2 = ? * (select wetc1 from tz_subjseq where subj = ? and year = ? and subjseq = ?) / 100, ";
            sql1+="       ldate = to_char(sysdate, 'YYYYMMDDHH24MISS'), ";
            sql1+="       luserid = ? ";
            sql1+=" where userid = ? ";
            sql1+="   and subj = ? ";
            sql1+="   and year = ? ";
            sql1+="   and subjseq = ? ";
            //System.out.println("$$$$$$$$$$$"+sql1);
            pstmt1 = connMgr.prepareStatement(sql1);

            
            //System.out.println("1"+Double.parseDouble(v_etc1));
            //System.out.println("2"+Double.parseDouble(v_etc2));
            //System.out.println("3"+Double.parseDouble(v_etc1));
            //System.out.println("4"+v_subj);
            //System.out.println("5"+v_subjseq);
            //System.out.println("6"+Double.parseDouble(v_etc2));
            //System.out.println("7"+v_subj);
            //System.out.println("8"+v_subjseq);
            //System.out.println("9"+s_userid);
            //System.out.println("10"+v_userid);
            //System.out.println("11"+v_subj);
            //System.out.println("12"+v_year);
            //System.out.println("13"+v_subjseq);

            pstmt1.setDouble(1, Double.parseDouble(v_etc1));
            pstmt1.setDouble(2, Double.parseDouble(v_etc2));
            pstmt1.setDouble(3, Double.parseDouble(v_etc1));
            pstmt1.setString(4, v_subj);
            pstmt1.setString(5, v_year);
            pstmt1.setString(6, v_subjseq);
            pstmt1.setDouble(7, Double.parseDouble(v_etc2));
            pstmt1.setString(8, v_subj);
            pstmt1.setString(9, v_year);
            pstmt1.setString(10,v_subjseq);
            pstmt1.setString(11,s_userid);
            pstmt1.setString(12,v_userid);
            pstmt1.setString(13,v_subj);
            pstmt1.setString(14,v_year);
            pstmt1.setString(15,v_subjseq);

            isOk = pstmt1.executeUpdate();

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
}