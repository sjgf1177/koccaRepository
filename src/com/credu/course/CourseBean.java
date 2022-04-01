//**********************************************************
//  1. 제      목: 코스OPERATION BEAN
//  2. 프로그램명: CourseBean.java
//  3. 개      요:
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: anonymous 2003. 07. 07
//  7. 수      정:
//**********************************************************
package com.credu.course;

import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class CourseBean {

    public final static String ONOFF_GUBUN = "0004";  

    public CourseBean() {}

    /**
    코스리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   코스리스트
    */
    @SuppressWarnings("unchecked")
	public ArrayList SelectCourseList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        CourseData data = null;

        String v_coursenm = box.getString("p_coursenm");

        try {
            sql = "select a.course, a.coursenm, a.inuserid, a.indate, ";
            sql+= "    a.gradscore, a.gradfailcnt, a.luserid, a.ldate, ";
            sql+= "    a.subjcnt, a.biyong ";
            sql+= "  from TZ_COURSE  a ";
//            sql+= "    ,(select course, count(*) subjcnt ";
//            sql+= "       from tz_COURSESUBJ ";
//            sql+= "      group by course) b ";
//            sql+= " where a.course = b.course ";

            if (!v_coursenm.equals(""))
                sql+= "  where a.coursenm like " + SQLString.Format("%" + v_coursenm + "%");

            sql+= " order by a.course ";

            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data=new CourseData();
                data.setCourse(ls.getString("course"));
                data.setCoursenm(ls.getString("coursenm"));
                data.setInuserid(ls.getString("inuserid"));
                data.setIndate(ls.getString("indate"));
                data.setGradscore(ls.getInt("gradscore"));
                data.setGradfailcnt(ls.getInt("gradfailcnt"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
                data.setSubjcnt(ls.getInt("subjcnt"));
				data.setBiyong(ls.getInt("biyong"));

                list1.add(data);
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


    /**
    코스데이타 조회
    @param box          receive from the form object and session
    @return CourseData   코스데이
    */
	public CourseData SelectCourseData(RequestBox box) throws Exception {
        String v_coursenm = box.getString("p_coursenm");
        CourseData data = null;

        if (!v_coursenm.equals("")) {
            data=new CourseData();
            data.setCourse(box.getString("p_course"));
            data.setCoursenm(box.getString("p_coursenm"));
            data.setGradfailcnt(box.getInt("p_gradfailcnt"));
            data.setGradscore(box.getInt("p_gradscore"));
            data.setSubjcnt(box.getInt("p_subjcnt"));
			data.setBiyong(box.getInt("p_biyong"));						
        } else {
            DBConnectionManager connMgr = null;
            ListSet ls = null;
            String sql  = "";

            String v_course = box.getString("p_course");

            try {
                sql = "select a.course, a.coursenm, a.inuserid, a.indate, ";
                sql+= "    a.gradscore, a.gradfailcnt, a.luserid, a.ldate, ";
                sql+= "    a.subjcnt, a.biyong, a.upperclass ";
                sql+= "  from TZ_COURSE  a ";
//                sql+= "   , (select course, count(*) subjcnt ";
//                sql+= "       from tz_COURSESUBJ ";
//                sql+= "      group by course) b ";
//                sql+= " where a.course = b.course ";
                sql+= "   where a.course = " + SQLString.Format(v_course);

                connMgr = new DBConnectionManager();
                ls = connMgr.executeQuery(sql);

                if (ls.next()) {
                    data=new CourseData();
                    data.setCourse(ls.getString("course"));
                    data.setCoursenm(ls.getString("coursenm"));
                    data.setInuserid(ls.getString("inuserid"));
                    data.setIndate(ls.getString("indate"));
                    data.setGradscore(ls.getInt("gradscore"));
                    data.setGradfailcnt(ls.getInt("gradfailcnt"));
                    data.setLuserid(ls.getString("luserid"));
                    data.setLdate(ls.getString("ldate"));
	                data.setSubjcnt(ls.getInt("subjcnt"));
					data.setBiyong(ls.getInt("biyong"));
					data.setUpperclass(ls.getString("upperclass"));
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
        }
        return data;
    }

    /**
    집합,사이버/대분류별/중분류/소분류 과정리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   코스코드 리스트
    */
    @SuppressWarnings("unchecked")
	public ArrayList TargetSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        SubjectInfoData data = null;

        String v_gubun       = box.getString("p_gubun");
        String v_upperclass  = box.getString("s_upperclass");
        String v_middleclass = box.getStringDefault("s_middleclass","ALL");   //과정분류
        String v_lowerclass  = box.getStringDefault("s_lowerclass","ALL");    //과정분류

        if (v_gubun.equals("")) v_gubun = "ALL";
        if (v_upperclass.equals("")) v_upperclass = "ALL";

        try {
            sql = "select a.subj, a.subjnm, a.isonoff, b.upperclass, b.classname, c.codenm  isonoffnm";
            sql+= "  from tz_subj     a, ";
            sql+= "       tz_subjatt  b, ";
            sql+= "       tz_code     c  ";
            sql+= " where a.subjclass = b.subjclass ";
            sql+= "   and a.isonoff   = c.code   ";
            sql+= "   and c.gubun     = " + SQLString.Format(ONOFF_GUBUN);
            if (!v_gubun.equals("ALL"))
                sql+= " and a.isonoff = " + SQLString.Format(v_gubun);

            if (!v_upperclass.equals("ALL"))
                sql+= " and a.upperclass = " + SQLString.Format(v_upperclass);
            if (!v_middleclass.equals("ALL"))
                sql += " and a.middleclass = "+SQLString.Format(v_middleclass);
            if (!v_lowerclass.equals("ALL"))
                sql += " and a.lowerclass = "+SQLString.Format(v_lowerclass);
            sql+= " order by a.subj ";

            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data=new SubjectInfoData();

                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setIsonoff(ls.getString("isonoff"));
                data.setUpperclass(ls.getString("upperclass"));
                data.setClassname(ls.getString("classname"));
                data.setIsonoffnm(ls.getString("isonoffnm"));

                list1.add(data);
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

    /**
    집합,사이버/대분류별 과정리스트 조
    @param box          receive from the form object and session
    @return ArrayList   코스코드 리스트
    */
    @SuppressWarnings("unchecked")
	public ArrayList SelectedSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";

        ArrayList list1 = null;
        list1 = new ArrayList();
        SubjectInfoData data = null;

        String v_subjectcodes    = box.getString("p_selectedsubjcodes");
        String v_subjecttexts    = box.getString("p_selectedsubjtexts");
        String v_course   = box.getString("p_course");

        try {

            if (!v_subjectcodes.equals("")) {
                StringTokenizer v_tokencode = new StringTokenizer(v_subjectcodes, ";");
                StringTokenizer v_tokentext = new StringTokenizer(v_subjecttexts, ";");

                String v_code = "";
                String v_text = "";

                while (v_tokencode.hasMoreTokens() && v_tokentext.hasMoreTokens()) {
                    v_code = v_tokencode.nextToken();
                    v_text = v_tokentext.nextToken();

                    data=new SubjectInfoData();

                    data.setSubj(v_code);
                    data.setDisplayname(v_text);

                    list1.add(data);
                }
            } else {
                if (!v_course.equals("")) {

                    sql = "select a.subj, a.subjnm, a.isonoff, b.upperclass, b.classname, d.codenm isonoffnm ";
                    sql+= "  from tz_subj         a, ";
                    sql+= "       tz_subjatt      b, ";
                    sql+= "       tz_coursesubj   c, ";
                    sql+= "       tz_code         d  ";
                    sql+= " where a.subjclass = b.subjclass ";
                    sql+= "   and a.subj      = c.subj ";
                    sql+= "   and a.isonoff   = d.code   ";
                    sql+= "   and d.gubun     = " + SQLString.Format(ONOFF_GUBUN);
                    sql+= "   and c.course    = " + SQLString.Format(v_course);
                    sql+= " order by c.subj ";

                    connMgr = new DBConnectionManager();
                    list1 = new ArrayList();
                    ls = connMgr.executeQuery(sql);

                    while (ls.next()) {
                        data=new SubjectInfoData();

                        data.setSubj(ls.getString("subj"));
                        data.setSubjnm(ls.getString("subjnm"));
                        data.setIsonoff(ls.getString("isonoff"));
                        data.setUpperclass(ls.getString("upperclass"));
                        data.setClassname(ls.getString("classname"));
                        data.setIsonoffnm(ls.getString("isonoffnm"));

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

    /**
    새로운 코스코드 등록
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
     public int InsertCourse(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_course      = "";
        String v_coursenm    = box.getString("p_coursenm");
        String v_upperclass  = box.getString("p_upperclass_i");
        String v_middleclass = box.getString("p_middleclass");
        String v_lowerclass  = box.getString("p_lowerclass");
        String v_courseclass = v_upperclass + v_middleclass + v_lowerclass;
        int    v_gradscore   = box.getInt("p_gradscore");
        int    v_gradfailcnt = box.getInt("p_gradfailcnt");
		int    v_biyong      = box.getInt("p_biyong");
		int    v_subjcnt     = box.getInt("p_subjcnt");		
        String v_luserid     = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            v_course = getMaxCoursecode(connMgr);

            //insert TZ_COURSE table
            sql =  "insert into TZ_COURSE(course, coursenm, courseclass, upperclass, middleclass, lowerclass, inuserid, indate, gradscore, gradfailcnt, biyong, subjcnt, luserid, ldate) ";
            sql+=  " values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'))";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_course);
            pstmt.setString(2, v_coursenm);
            pstmt.setString(3, v_courseclass);
            pstmt.setString(4, v_upperclass);
            pstmt.setString(5, v_middleclass);
            pstmt.setString(6, v_lowerclass);
            pstmt.setString(7, v_luserid);
            pstmt.setInt   (8, v_gradscore);
            pstmt.setInt   (9, v_gradfailcnt);
            pstmt.setInt   (10, v_biyong);
            pstmt.setInt   (11, v_subjcnt);			
            pstmt.setString(12, v_luserid);

            isOk = pstmt.executeUpdate();

            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }

            String v_subj        = "";
            String v_isrequired  = "Y";

            String v_subjectcodes    = box.getString("p_selectedsubjcodes");
            StringTokenizer v_token = new StringTokenizer(v_subjectcodes, ";");

            //insert TZ_COURSESUBJ table
            sql =  "insert into TZ_COURSESUBJ(course, subj, isrequired, luserid, ldate) ";
            sql+=  " values (?, ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'))";
            pstmt = connMgr.prepareStatement(sql);

            while (v_token.hasMoreTokens()) {
                v_subj = v_token.nextToken();

                pstmt.setString(1, v_course);
                pstmt.setString(2, v_subj);
                pstmt.setString(3, v_isrequired);
                pstmt.setString(4, v_luserid);
                isOk = pstmt.executeUpdate();
            }
        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    public String getMaxCoursecode(DBConnectionManager connMgr) throws Exception {
        String v_coursecode = "";
        String v_maxcourse  = "";
        int    v_maxno   = 0;

        ListSet ls = null;
        String sql  = "";
        try {
            sql = " select max(course) maxcourse";
            sql+= "   from tz_course ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                 v_maxcourse = ls.getString("maxcourse");
            }

            if (v_maxcourse.equals("")) {
                v_coursecode = "C00001";
            } else {
                v_maxno = Integer.valueOf(v_maxcourse.substring(1)).intValue();
                v_coursecode = "C" + new DecimalFormat("00000").format(v_maxno+1);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_coursecode;
    }

    /**
    선택된 코스코드 수정
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
    */
     public int UpdateCourse(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_course      = box.getString("p_course");
        String v_coursenm    = box.getString("p_coursenm");
        String v_upperclass  = box.getString("p_upperclass_i");
        String v_middleclass = box.getString("p_middleclass");
        String v_lowerclass  = box.getString("p_lowerclass");
        String v_courseclass = v_upperclass + v_middleclass + v_lowerclass;
        int    v_gradscore   = box.getInt("p_gradscore");
        int    v_gradfailcnt = box.getInt("p_gradfailcnt");
		int    v_biyong      = box.getInt("p_biyong");
		int    v_subjcnt     = box.getInt("p_subjcnt");
        String v_luserid     = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //insert TZ_COURSE table
            sql = "update TZ_COURSE  ";
			sql+= "   set coursenm = ?, courseclass = ?, upperclass = ?, middleclass = ?, lowerclass = ?, ";
			sql+= "       gradscore = ?, gradfailcnt = ?, biyong = ?, subjcnt =?, ";
			sql+= "       luserid = ?, ldate = to_char(sysdate,'YYYYMMDDHH24MISS') ";
            sql+= " where course = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_coursenm);
            pstmt.setString(2, v_courseclass);
            pstmt.setString(3, v_upperclass);
            pstmt.setString(4, v_middleclass);
            pstmt.setString(5, v_lowerclass);
            pstmt.setInt   (6, v_gradscore);
            pstmt.setInt   (7, v_gradfailcnt);
            pstmt.setInt   (8, v_biyong);
            pstmt.setInt   (9, v_subjcnt);	
            pstmt.setString(10, v_luserid);
            pstmt.setString(11, v_course);
            isOk = pstmt.executeUpdate();

            sql ="select count(*) CNTS from tz_courseseq where course="+StringManager.makeSQL(v_course);
            ls = connMgr.executeQuery(sql);
            ls.next();
            if(ls.getInt("CNTS")==0) {
                if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }

                //delete TZ_COURSESUBJ table
                sql = "delete from TZ_COURSESUBJ where course = ? ";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, v_course);
                isOk = pstmt.executeUpdate();

                if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }

                String v_subj        = "";
                String v_isrequired  = "Y";
                String v_subjects    = box.getString("p_selectedsubjcodes");
                StringTokenizer v_token = new StringTokenizer(v_subjects, ";");

                //insert TZ_COURSESUBJ table
                sql =  "insert into TZ_COURSESUBJ(course, subj, isrequired, luserid, ldate) ";
                sql+=  " values (?, ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'))";
                pstmt = connMgr.prepareStatement(sql);

                while (v_token.hasMoreTokens()) {
                    v_subj = v_token.nextToken();

                    pstmt.setString(1, v_course);
                    pstmt.setString(2, v_subj);
                    pstmt.setString(3, v_isrequired);
                    pstmt.setString(4, v_luserid);
                    isOk = pstmt.executeUpdate();
                }
            }else isOk=-99;
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    선택된 코스코드 삭제
    @param box      receive from the form object and session
    @return isOk    1:delete success,0:delete fail
    */
    public int DeleteCourse(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int    isOk1 = 0;
        PreparedStatement pstmt2 = null;
        String sql2 = "";
        int    isOk2 = 0;
        PreparedStatement pstmt3 = null;
        String sql3 = "";
        String sql = "";
        ListSet ls=null;

        String v_course  = box.getString("p_course");
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql ="select count(*) CNTS from tz_courseseq where course="+StringManager.makeSQL(v_course);
            ls = connMgr.executeQuery(sql);
            ls.next();
            if(ls.getInt("CNTS")==0) {
                //delete TZ_COURSE table
                sql1 = "delete from TZ_COURSE where course = ? ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_course);
                isOk1 = pstmt1.executeUpdate();

                //delete TZ_COURSESUBJ table
                sql2 = "delete from TZ_COURSESUBJ where course = ? ";
                pstmt2 = connMgr.prepareStatement(sql2);
                pstmt2.setString(1, v_course);
                isOk2 = pstmt2.executeUpdate();
				
                //delete TZ_COURSESUBJ table
                sql3 = "delete from TZ_GRSUBJ where subjcourse = ? ";
                pstmt3 = connMgr.prepareStatement(sql3);
                pstmt3.setString(1, v_course);
                //isOk3 = 
                	pstmt3.executeUpdate();
				
                if(isOk1 > 0 && isOk2 >0) connMgr.commit();         //      2가지 sql 이 꼭 같이 delete 되어야 하는 경우이므로
                else connMgr.rollback();
            }else   return -99;
        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql2 = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e1) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1*isOk2;
    }
    /*
       코스차수리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   코스차수리스트
    */      
    @SuppressWarnings("unchecked")
	public ArrayList SelectCourseseqList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list1 = null;
        String sql  = "";
        CourseData data = null;

        String s_course = box.getString("s_course");            
        String s_cyear  = box.getString("s_cyear");
        String p_order  = box.getString("p_order");
        if (p_order==null || p_order.equals(""))    p_order = "cyear";

        try {
            sql = "select course,cyear,courseseq,a.grcode,b.grcodenm,gyear,grseq            "
                + "       ,coursenm,gradscore,gradfailcnt,propstart,propend   "
                + "       ,edustart,eduend,a.indate,a.luserid,a.ldate               "
                + "  from tz_courseseq a, tz_grcode b"
                + " where a.grcode=b.grcode  and a.cyear != '0000' ";
            if (!s_course.equals("ALL"))    sql+="  and course ="+StringManager.makeSQL(s_course);
            if (!s_cyear.equals("ALL")) sql+="  and cyear='"+s_cyear+"'";   
            sql+= " order by " + p_order ;

            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                data=new CourseData();                   
                data.setCourse(ls.getString("course"));
                data.setCyear(ls.getString("cyear"));
                data.setCourseseq(ls.getString("courseseq"));
                data.setCoursenm(ls.getString("coursenm"));
                data.setLdate(ls.getString("ldate"));
                data.setIndate(ls.getString("indate"));
                data.setGradscore(ls.getInt("gradscore"));
                data.setGradfailcnt(ls.getInt("gradfailcnt"));
                data.setLuserid(ls.getString("luserid"));
                data.setGrcode(ls.getString("grcode"));
                data.setGrcodenm(ls.getString("grcodenm"));
                data.setGyear(ls.getString("gyear"));
                data.setGrseq(ls.getString("grseq"));
            
                list1.add(data);
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

    /**
    코스차수년도 Select-box
    @param 
    @return
    */      
	public static String getCyearSelect(RequestBox box, String p_cyear) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
        String results="<select name='s_cyear'><option value='ALL'>ALL</option>";
        String  v_cyear=""; 

        try {
            sql = "select distinct cyear "
                + "  from tz_courseseq where cyear != '0000' order by cyear";

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                v_cyear = ls.getString("cyear");
                results += "<option value='"+v_cyear+"'";
                if(p_cyear.equals(v_cyear)) results += " selected ";
                results += ">"+v_cyear+"</option>";                 
            }
            results += "</select>";
        }            
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return results;
    }

    /**
    과정차수리스트 조회
    @param box          receive from the form object and session
    @return ArrayList   과정차수리스트
    */      
    @SuppressWarnings("unchecked")
	public ArrayList SelectSubjseqList(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls1=null;
        ArrayList list1 = null;
        String sql  = "";
        SubjseqData data = null;

        String s_subj = box.getString("s_subj");            
        String s_year  = box.getString("s_year");
        String p_order  = box.getString("p_order");
        if (p_order==null || p_order.equals(""))    p_order = "year";

        try {
            sql = "select a.subj,year,subjseq,a.grcode,b.grcodenm,gyear,grseq           "
                + "       ,a.subjnm,a.isgoyong,propstart,propend   "
                + "       ,edustart,eduend,a.luserid,a.ldate, c.isonoff   "
                + "  from tz_subjseq a, tz_grcode b, tz_subj c"
                + " where a.grcode=b.grcode "
                + "   and a.subj=c.subj ";
            if (!s_subj.equals("ALL"))  sql+="  and a.subj ="+StringManager.makeSQL(s_subj);
            if (!s_year.equals("ALL"))  sql+="  and year='"+s_year+"'"; 
            sql+= " order by " + p_order ;
//stem.out.println(sql);
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                data=new SubjseqData();                   
                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setLdate(ls.getString("ldate"));
                data.setIsgoyong(ls.getString("isgoyong"));
                data.setPropstart(ls.getString("propstart"));
                data.setPropend(ls.getString("propend"));
                data.setEdustart(ls.getString("edustart"));
                data.setEduend(ls.getString("eduend"));
                data.setLuserid(ls.getString("luserid"));
                data.setGrcode(ls.getString("grcode"));
                data.setGrcodenm(ls.getString("grcodenm"));
                data.setGyear(ls.getString("gyear"));
                data.setGrseq(ls.getString("grseq"));
                data.setIsonoff(ls.getString("isonoff"));
/*				
                if(data.getIsonoff().equals("OFF")){
                    sql = "select count(*) CNTS from tz_offsubjlecture "
                        + " where subj="+StringManager.makeSQL(data.getSubj())
                        + "   and year="+StringManager.makeSQL(data.getYear())
                        + "   and subjseq="+StringManager.makeSQL(data.getSubjseq());
                    if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }  
                    ls1 = connMgr.executeQuery(sql);
                    ls1.next();
                    data.setCnt_offsubjlecture(ls1.getInt("CNTS"));
                }
*/                
                list1.add(data);
            }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }

    /**
    과정차수년도 Select-box
    @param 
    @return
    */      
	public static String getYearSelect(RequestBox box, String p_year) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
        String results="<select name='s_year'><option value='ALL'>ALL</option>";
        String  v_year=""; 

        try {
            sql = "select distinct year "
                + "  from tz_subjseq order by year";

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                v_year = ls.getString("year");
                results += "<option value='"+v_year+"'";
                if(p_year.equals(v_year))   results += " selected ";
                results += ">"+v_year+"</option>";                  
            }
            results += "</select>";
        }            
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return results;
    }
    
    /**
    코스차수데이타 조회
    @param box          receive from the form object and session
    @return CourseData   코스차수데이타
    */      
    public CourseData SelectCourseseqData(RequestBox box) throws Exception {               

        CourseData data = null;
        
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
    
        try {
            sql = "select a.course, a.cyear, a.courseseq, a.coursenm, a.propstart, a.propend,    ";
            sql+= "       a.edustart, a.eduend, a.gradscore, a.gradfailcnt, a.biyong, a.subjcnt, ";
            sql+= "       a.canceldays, a.grcode, a.gyear, a.grseq, b.grcodenm, c.grseqnm        ";
            sql+= "  from TZ_COURSESEQ  a, TZ_GRCODE b, TZ_GRSEQ c                               ";
            sql+= " where a.course = "   + SQLString.Format(box.getString("p_course"));
            sql+= "   and a.cyear = "    + SQLString.Format(box.getString("p_cyear"));
            sql+= "   and a.courseseq = "+ SQLString.Format(box.getString("p_courseseq"));
            sql+= "   and a.grcode=b.grcode ";
            sql+= "   and a.grcode=c.grcode and a.gyear=c.gyear and a.grseq=c.grseq";
    
            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);
               
            if (ls.next()) {
                data=new CourseData();                   
                data.setCourse(ls.getString("course"));
                data.setCyear(ls.getString("cyear"));
                data.setCourseseq(ls.getString("courseseq"));
                data.setCoursenm(ls.getString("coursenm"));
                data.setGradscore(ls.getInt("gradscore"));
                data.setGradfailcnt(ls.getInt("gradfailcnt"));
                data.setBiyong(ls.getInt("biyong"));
				data.setSubjcnt(ls.getInt("subjcnt"));
				data.setCanceldays(ls.getInt("canceldays"));				
                data.setGrcode(ls.getString("grcode"));
                data.setGyear(ls.getString("gyear"));
                data.setGrseq(ls.getString("grseq"));
                data.setPropstart(ls.getString("propstart"));
                data.setPropend(ls.getString("propend"));
                data.setEdustart(ls.getString("edustart"));
                data.setEduend(ls.getString("eduend"));				
                data.setGrcodenm(ls.getString("grcodenm"));
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
    코스차수정보 수정-저장
    @param box      receive from the form object and session
    @return isOk    
    */              
    public int UpdateCourseseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;        
        String sql = "";
        int    isOk = 0;   

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);  
            
            //added by LeeSuMin 2003.10.30 -- 코스차수 존재시 삭제 불가.
            sql ="update tz_courseseq set  propstart  =" + SQLString.Format(box.getString("p_propstart"));
            sql+="                        ,propend    =" + SQLString.Format(box.getString("p_propend"));
            sql+="                        ,edustart   =" + SQLString.Format(box.getString("p_edustart"));
            sql+="                        ,eduend     =" + SQLString.Format(box.getString("p_eduend"));
            sql+="                        ,canceldays =" + SQLString.Format(box.getString("p_canceldays"));	
            sql+="                        ,gradscore  =" + SQLString.Format(box.getString("p_gradscore"));	
            sql+="                        ,biyong     =" + SQLString.Format(box.getString("p_biyong"));				
            sql+= " where course = "   + SQLString.Format(box.getString("p_course"));
            sql+= "   and cyear = "    + SQLString.Format(box.getString("p_cyear"));
            sql+= "   and courseseq = "+ SQLString.Format(box.getString("p_courseseq"));

            isOk = connMgr.executeUpdate(sql); 
            
            if(isOk>0){
                sql ="update tz_subjseq set  propstart =" + SQLString.Format(box.getString("p_propstart"));
                sql+="                      ,propend   =" + SQLString.Format(box.getString("p_propend"));
	            sql+="                      ,edustart  =" + SQLString.Format(box.getString("p_edustart"));
	            sql+="                      ,eduend    =" + SQLString.Format(box.getString("p_eduend"));	
	            sql+="                      ,canceldays =" + SQLString.Format(box.getString("p_canceldays"));
                sql+= " where course = "   + SQLString.Format(box.getString("p_course"));
                sql+= "   and cyear = "    + SQLString.Format(box.getString("p_cyear"));
                sql+= "   and courseseq = "+ SQLString.Format(box.getString("p_courseseq"));
                
                isOk = connMgr.executeUpdate(sql); 
            }
            
            connMgr.commit();
        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }           
}