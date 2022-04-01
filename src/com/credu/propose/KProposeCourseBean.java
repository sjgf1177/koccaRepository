//**********************************************************
//1. 제      목: SUBJECT INFORMATION USER BEAN
//2. 프로그램명: ProposeCourseBean.java
//3. 개      요: 과정안내 사용자 bean
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 2004.01.14
//7. 수      정:
//**********************************************************
package com.credu.propose;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

import com.credu.propose.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

public class KProposeCourseBean {


public KProposeCourseBean() {}

/**
수강과정 리스트 (온라인)
@param box      receive from the form object and session
@return ArrayList
*/
	 public ArrayList selectSubjectList(RequestBox box) throws Exception {
	    DBConnectionManager connMgr = null;
	    DataBox dbox1       = null;
	    ListSet ls1         = null;
	    ArrayList list1     = null;
	    String sql1         = "";
	
	    String  v_select    = box.getStringDefault("p_select", "ON");
	    String  v_tab_gubun = box.getStringDefault("p_tab_gubun", "apply_all"); // 탭 구분
	    String  v_user_id   = box.getSession("userid");
	    String  v_gadmin    = box.getSession("gadmin");
		String v_grgubun	= box.getStringDefault("p_grgubun", "G01");
		
		String v_subjclass  = "";
		
		ConfigSet conf = new ConfigSet();
		
	
	    // 사이트의 GRCODE 로 과정 리스트
	    String v_grcode = box.getSession("tem_grcode");	
	    String  gyear    = box.getStringDefault("gyear",FormatDate.getDate("yyyy"));		
	
	    try {
	        connMgr = new DBConnectionManager();
	        list1 = new ArrayList();
	
	        // 수강신청 가능한 과정
	        sql1 = " select distinct a.subj, a.subjseq, a.subjnm, a.scyear, a.isonoff, a.scupperclass, a.scmiddleclass, a.sclowerclass, a.usebook, ";
	        sql1+= "        substring(a.specials,1,1) isnew, substring(a.specials,2,1) ishit, substring(a.specials,3,1) isrecom,   ";
	        sql1+= "        (select classname from tz_subjatt x  ";
	        sql1+= "          where x.upperclass = a.scupperclass and x.middleclass  = '000' and x.lowerclass = '000' ) uclassnm,  ";
	        sql1+= "        (select classname from tz_subjatt x   ";
	        sql1+= "          where x.upperclass = a.scupperclass and x.middleclass = a.scmiddleclass and x.lowerclass = '000' ) mclassnm  ";
			sql1+= " 		, a.ISORDINARY, a.ISWORKSHOP, a.ISUNIT, a.propstart, a.propend, a.edustart, b.eduperiod, b.Muserid, c.name, a.biyong, a.scbiyong, b.preurl ";
	        sql1+= " 		,  ( Select count(*) From tz_propose p Where subj= a.subj and subjseq = a.subjseq and year = a.year and userid='" + v_user_id + "') as procnt,a.isbelongcourse, a.subjcnt,a.coursenm , a.course ";
			sql1+= "   from VZ_SCSUBJSEQ a , tz_subj b, tz_tutor c                                                                                                    ";
	//사이트기준인지
	        sql1+= "  where ";
			sql1+= " 	a.subj = b.subj and b.muserid   =  c.userid(+) and ";
			sql1+= " 	a.grcode = " + SQLString.Format(v_grcode);
			
			if(v_tab_gubun.equals("alway"))
			{
				sql1+= "	and a.ISORDINARY = 'Y' and a.isbelongcourse = 'N'" ;
			}
			else if(v_tab_gubun.equals("sometime"))
			{
				sql1+= "	and a.ISORDINARY = 'N' and a.isbelongcourse = 'N' " ;
			}
			else if(v_tab_gubun.equals("prof"))
			{
				sql1+= "	and a.isbelongcourse = 'Y' " ;				
			}
			else if(v_tab_gubun.equals("workshop"))
			{
				sql1+= "	and a.ISWORKSHOP = 'Y' and a.isbelongcourse = 'N' " ;				
			}
			else if(v_tab_gubun.equals("recognition"))
			{
				sql1+= "	and a.ISUNIT = 'Y' and a.isbelongcourse = 'N' " ;				
			}
	        
			sql1+= "    and a.isuse        = 'Y' ";
	        //sql1+= "    and a.scupperclass = " + SQLString.Format(v_grgubun); // 게임,문콘
	        sql1+= "    and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.propend ";
	        if(!v_gadmin.equals("A1")) sql1+= " and subjvisible = 'Y'  ";
	
	        sql1 += " order by a.course, a.subjseq, a.scupperclass, a.scmiddleclass, a.subjnm   ";
	System.out.println("selectSubjectList.sql = " + sql1 );
	
	        ls1 = connMgr.executeQuery(sql1);
	
	        while (ls1.next() ) {
	            dbox1 = ls1.getDataBox();
	            list1.add(dbox1);
	        }
	    }
	    catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql1);
	        throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
	    return list1;
	}
	 
	 public static int selectDiscount(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
	    DataBox dbox       = null;
	    ListSet ls         = null;
	    String sql         = "";
		
		int result = 0;
		
		String  v_userid   = box.getSession("userid");
		
		try
		{
			connMgr = new DBConnectionManager();
			
			sql = " Select * From TZ_MEMBER Where userid = '" + v_userid + "'";
			
			ls = connMgr.executeQuery(sql);

            for (int i = 0; ls.next(); i++) {
                dbox = ls.getDataBox();		
            }
			
			result = dbox.getInt("d_");			
			
		}
		catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql);
	        throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls != null) { try { ls.close(); }catch (Exception e) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
		return result;	 
	 }
	 
	 /**
    오프라인 신청 가능한 과정리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   과정리스트
    */
    public static ArrayList OffLineSelectSubjectList(RequestBox box) throws Exception {
		PreparedStatement pstmt = null;  
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
		
		String v_seq = box.getString("p_seq");
		String v_userid = box.getSession("userid");
		
        try {
			
			connMgr = new DBConnectionManager();

            list = new ArrayList();
			
			head_sql += " Select a.seq, a.subjgubun, b.codenm as gubunNm , subj, subjseq, propStart, " ;
			head_sql += "	propEnd, subjNm, tUserid, tName,  dday, startTime, endTime, place, tName ";
			body_sql += " From TZ_OFFLINESUBJ a, tz_code b ";
			body_sql += " Where ";
			body_sql += " 	a.subjgubun = b.code and a.grcode = 'N000001'";
			body_sql += "	and b.gubun = '0061' ";
			body_sql += "	and seq not in (Select p.seq From tz_offlinepropose p join TZ_OFFLINESUBJ s on p.seq = s.seq ";
			body_sql += " Where userid = '" + v_userid + "' ) ";
			body_sql += "	and to_char(sysdate,'YYYYMMDDHH24') between a.propStart and a.propEnd ";
			body_sql += " Order By a.seq DESC";
			
			sql= head_sql+ body_sql+group_sql+ order_sql;
System.out.println("sql = " + sql);
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
    오프라인 신청한 과정리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   과정리스트
    */
    public static ArrayList OffLineApplySelectSubjectList(RequestBox box) throws Exception {
		PreparedStatement pstmt = null;  
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
		
		String v_seq = box.getString("p_seq");
		String v_userid = box.getSession("userid");
		
        try {
			
			connMgr = new DBConnectionManager();

            list = new ArrayList();
			
			head_sql += "Select c.seq, a.subjgubun, b.codenm as gubunNm , subj, subjseq, propStart, " ;
			head_sql += "	propEnd, subjNm, tUserid, tName,  dday, startTime, endTime, place, tName, c.status ";
			body_sql += " From TZ_OFFLINESUBJ a, tz_code b, tz_offlinepropose c ";
			body_sql += " Where ";
			body_sql += " 	a.subjgubun = b.code and a.seq = c.seq  and a.grcode = 'N000001' ";
			body_sql += "	and b.gubun = '0061' ";
			body_sql += " 	and userid = '" + v_userid + "' ";
			body_sql += "	and to_char(sysdate,'YYYYMMDDHH24') between a.propStart and a.propEnd ";
			body_sql += " Order By a.seq DESC";
			
			sql= head_sql+ body_sql+group_sql+ order_sql;
System.out.println("sql = " + sql);
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
	OffLine 과정상세보기
	@param box      receive from the form object and session
	@return ProposeCourseData
	*/	
   public DataBox OffLineApplyView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_upcnt = "Y";
		String v_grcode = box.getString("p_grcode");
		
        int v_seq       = box.getInt("p_seq");

        Vector realfileVector = new Vector();
        Vector savefileVector = new Vector();
        Vector fileseqVector  = new Vector();
        try {
            connMgr = new DBConnectionManager();

			sql = "Select ";
			sql += " 	a.seq, a.subjgubun, a.subj, a.subjseq, a.propStart, a.propEnd, a.subjNm, a.tUserid, ";
			sql += " 	a.tName,  a.dday, a.startTime, a.endTime, a.place, b.codenm as gubunNm, a.content,a.limitmember ";
			sql += " From TZ_OFFLINESUBJ a join tz_code b on a.subjgubun = b.code";
			sql += "	left outer join tz_subj c on a.subj = c.subj ";
			sql += " Where  ";
			sql += " 	b.gubun = '0061' ";
			sql += " 	and seq = " + v_seq;
			sql += " Order By seq DESC ";
System.out.println("OffLineApplyView.sql = " + sql);
            ls = connMgr.executeQuery(sql);

            for (int i = 0; ls.next(); i++) {

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
	오프라인 수강 등록 취소
	@param box      receive from the form object and session
	@return isOk    1:insert success,0:insert fail
	*/
	public int OffLineApplyCancelSubject(RequestBox box) throws Exception {
	    DBConnectionManager connMgr = null;
	    ListSet ls = null;
	    PreparedStatement pstmt = null;
	    String sql = "";
	    String sql1 = "";
	    int isOk = 0;

	    String v_userid = box.getSession("userid");
	    String v_grcode = box.getString("p_grcode");	
		int v_seq  		= box.getInt("p_seq");
	
	    try {
	       connMgr = new DBConnectionManager();
		
	       sql1 =  " Delete From tz_offlinepropose";
	       sql1 += " Where seq=? and grcode ='N000001' and userid=?";
	
	       pstmt = connMgr.prepareStatement(sql1);
	       pstmt.setInt   (1, v_seq);
	       pstmt.setString(2, v_userid);
	
	       isOk = pstmt.executeUpdate();
	    }
	    catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql1);
	        throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls != null) { try { ls.close(); } catch (Exception e) {} }
	        if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
	    return isOk;
	}
	
	/**
	오프라인 수강 등록
	@param box      receive from the form object and session
	@return isOk    1:insert success,0:insert fail
	*/
	public int OffLineApplySubject(RequestBox box) throws Exception {
	    DBConnectionManager connMgr = null;
	    ListSet ls = null;
	    PreparedStatement pstmt = null;
	    String sql = "";
	    String sql1 = "";
	    int isOk = 0;

	    String v_userid = box.getSession("userid");
	    String v_grcode = box.getString("p_grcode");	
		int v_seq  		= box.getInt("p_seq");
	
	    try {
	       connMgr = new DBConnectionManager();
		
	       sql1 =  " insert into tz_offlinepropose(seq, grcode, userid, ldate, status)";
	       sql1 += " values (?, 'N000001', ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), 'W')";
	
	       pstmt = connMgr.prepareStatement(sql1);
	       pstmt.setInt   (1, v_seq);
	       pstmt.setString(2, v_userid);
	
	       isOk = pstmt.executeUpdate();
	//System.out.println("1111111111로그===>"+isOk);
	    }
	    catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql1);
	        throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls != null) { try { ls.close(); } catch (Exception e) {} }
	        if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
	    return isOk;
	}
	
	/**
    오프라인 신청한 과정리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   과정리스트
    */
    public static ArrayList OffLineSelectSubjectTopList(RequestBox box) throws Exception {
		PreparedStatement pstmt = null;  
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
		
		String v_seq = box.getString("p_seq");
		String v_userid = box.getSession("userid");
		
        try {
			
			connMgr = new DBConnectionManager();

            list = new ArrayList();
			
			head_sql += "select rnum, seq, subjgubun,gubunNm, subj, subjseq, propStart, propEnd, subjNm,tUserid, tName, dday,startTime, endTime, place,t tName from (select rownum rnum, a.seq, a.subjgubun, b.codenm as gubunNm , subj, subjseq, propStart, " ;
			head_sql += "	propEnd, subjNm, tUserid, tName,  dday, startTime, endTime, place, tName t ";
			body_sql += " From TZ_OFFLINESUBJ a, tz_code b ";
			body_sql += " Where ";
			body_sql += " 	a.subjgubun = b.code and a.grcode = 'N000001' ";
			body_sql += "	and b.gubun = '0061' ";
			body_sql += "	and to_char(sysdate,'YYYYMMDDHH24') between a.propStart and a.propEnd ";
			body_sql += " Order By a.seq DESC) where rnum < 3 ";
			
			sql= head_sql+ body_sql+group_sql+ order_sql;
System.out.println("sql = " + sql);
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
	차수정보상세보기
	@param box      receive from the form object and session
	@return ProposeCourseData
	*/
	 public DataBox getSelectBill(RequestBox box) throws Exception {
	    DBConnectionManager connMgr = null;
	    DataBox dbox        = null;
	    ListSet ls1         = null;
	    String sql          = "";
	    String  v_subj      = box.getString("p_subj");
	    String  v_subjseq   = box.getString("p_subjseq");
	    String  v_year   	= box.getString("p_year");
	    String  gyear       = box.getString("gyear");
	    String  v_course    = box.getString("p_course");
	    String  v_isonoff   = box.getString("p_isonoff");
	
	    try {
	        connMgr = new DBConnectionManager();
	         sql = "  Select s.subj, s.subjseq, s.[year], s.propstart, s.propend, s.edustart, s.eduend, ";
			 sql += "	s.subjnm, s.studentlimit, s.biyong, su.muserid  , m.name , s.bookprice ";
			 sql += "	, v.isbelongcourse, v.course, v.subjcnt, v.coursenm ";
			 sql += " From tz_subjseq s ";
			 sql += "	join tz_subj su on su.subj = s.subj ";
			 sql +=	"	left outer join tz_member m on m.userid = su.muserid ";
			 sql += " 	join vz_scsubjseq v on s.subj = v.subj and s.year = v.year and s.subjseq = v.subjseq ";
	         sql+= "  where                    \n";
	         sql+= "    s.subj = "+SQLString.Format(v_subj)+"  \n";
	         sql+= "    and s.subjseq = "+SQLString.Format(v_subjseq)+"  \n";
	         sql+= "    and s.year = "+SQLString.Format(v_year)+"  \n";
			 
	        System.out.println(sql);
	
	        ls1 = connMgr.executeQuery(sql);
	
	            if (ls1.next()) {
	                dbox = ls1.getDataBox();
	            }
	    }
	    catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql);
	        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
	    return dbox;
	}
	 
	/**
	코스과정일 경우 교육비, 교재비 구하기.
	@param box      receive from the form object and session
	@return ProposeCourseData
	*/
	 public static DataBox getBill(String v_course, String v_subjseq, String v_year) throws Exception {
	    DBConnectionManager connMgr = null;
	    DataBox dbox        = null;
	    ListSet ls1         = null;
	    String sql          = "";
	
	    try {
	        connMgr = new DBConnectionManager();
	         sql =  " select sum(V.bookprice) as bookprice, V.scbiyong ";
			 sql += " From VZ_SCSUBJSEQ V ";
			 sql += "	Join TZ_COURSESEQ C On V.scsubj = C.course and V.scyear = C.gyear and V.scsubjseq = C.courseseq  ";
			 sql += " where ";
	         sql += "    V.scsubj = "+SQLString.Format(v_course)+"  \n";
	         sql += "    and V.subjseq = "+SQLString.Format(v_subjseq)+"  \n";
	         sql += "    and V.scyear = "+SQLString.Format(v_year)+"  \n";
			 sql += " Group by V.scbiyong";
			 
	        System.out.println(sql);
	
	        ls1 = connMgr.executeQuery(sql);
	
	            if (ls1.next()) {
	                dbox = ls1.getDataBox();
	            }
	    }
	    catch (Exception ex) {
			Log.sys.println("ErrorManager.getErrorStackTrace(Throwable ex, boolean isHtml) is critical error\r\n" + ex.getMessage());
	        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
	    return dbox;
	}
	 
	 
	   
}