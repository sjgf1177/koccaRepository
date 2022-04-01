//**********************************************************
//  1. ��      ��: �ǰߴޱ�
//  2. ���α׷��� : OpinionBean.java
//  3. ��      ��: �ǰߴޱ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7.  2
//  7. ��      ��:
//**********************************************************

package com.credu.beta;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;

/**
 * �ǰ� �ޱ�(ADMIN)
 *
 * @date   : 2003. 7
 * @author : s.j Jung
 */

/*
subj, year, subjseq, lesson, lessonseq, seq, userid, answer, luserid, ldate
*/

public class BetaOpinionBean {

    public BetaOpinionBean() {}


    /**
    �ǰߴޱ�  ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   �ǰߴޱ�   ����Ʈ
    */
    public ArrayList selectListOpinion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
//		2005.11.15_�ϰ��� : TotalCount ���� ���� ���� 
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
        BetaOpinionData data = null;

        String v_subj    = box.getSession("s_subj");
        String v_year    = box.getSession("s_year");
        String v_subjseq = box.getSession("s_subjseq");
        String v_lesson  = box.getString("p_lesson");
        int v_lessonseq  = box.getInt("p_lessonseq");
        int v_pageno     = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            head_sql  = " select a.subj subj, a.year year, a.subjseq subjseq, a.lesson lesson, a.lessonseq lessonseq,       ";
            head_sql += "        a.seq seq, a.userid userid, b.name name, a.answer answer, a.luserid luserid, a.ldate ldate ";
            body_sql += "   from TZ_OPINION a, TZ_MEMBER b                                                                  ";
			body_sql += "  where a.userid = b.userid                                                                        ";
			body_sql += "    and a.subj = " + StringManager.makeSQL(v_subj);
			body_sql += "    and a.year = " + StringManager.makeSQL(v_year);
			body_sql += "    and a.subjseq = " + StringManager.makeSQL(v_subjseq);
			body_sql += "    and a.lesson = " + StringManager.makeSQL(v_lesson);
			body_sql += "    and a.lessonseq = " + v_lessonseq;
			body_sql += " order by seq desc";

			sql= head_sql+ body_sql+ order_sql;
			
			ls = connMgr.executeQuery(sql);		
			
			count_sql= "select count(*) "+ body_sql;
			
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(10);                         //  �������� row ������  �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);	// ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage();   	// ��ü ������ ���� ��ȯ�Ѵ�
            //int total_row_count = ls.getTotalCount();   	// ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
                data = new BetaOpinionData();

                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));
                data.setLesson(ls.getString("lesson"));
                data.setLessonseq(ls.getInt("lessonseq"));
                data.setSeq(ls.getInt("seq"));
                data.setUserid(ls.getString("userid"));
                data.setName(ls.getString("name"));
                data.setAnswer(ls.getString("answer"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));

                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

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
    �ǰ�  �ۼ�
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
     public int insertOpinion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;

        String v_subj    = box.getSession("s_subj");
        String v_year    = box.getSession("s_year");
        String v_subjseq = box.getSession("s_subjseq");
        String v_lesson  = box.getString("p_lesson");
        int v_lessonseq  = box.getInt("p_lessonseq");
        int v_seq        = 0;
        String v_answer  = box.getString("p_content");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql  = "select max(seq) from TZ_OPINION  ";
            sql += "    where  subj = " + StringManager.makeSQL(v_subj);
            sql += "    and year = " + StringManager.makeSQL(v_year);
            sql += "    and subjseq = " + StringManager.makeSQL(v_subjseq);
            sql += "    and lesson = " + StringManager.makeSQL(v_lesson);
            sql += "    and lessonseq = " + v_lessonseq;

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_seq = ls.getInt(1) + 1;
            } else {
                v_seq = 1;
            }

            sql1  = " insert into TZ_OPINION(subj, year, subjseq, lesson, lessonseq, seq, userid, answer, luserid, ldate)  ";
            sql1 += " 	 values (? ,? ,? ,? ,? ,? ,? ,? ,? , to_char(sysdate, 'YYYYMMDDHH24MISS'))"; 
            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);
            pstmt.setString(4, v_lesson);
            pstmt.setInt(5, v_lessonseq);
            pstmt.setInt(6, v_seq);
            pstmt.setString(7, s_userid);
            pstmt.setString(8, v_answer);
            pstmt.setString(9, s_userid);

            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
}
