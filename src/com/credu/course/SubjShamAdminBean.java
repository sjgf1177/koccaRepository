//**********************************************************
//  1. ��      ��: ���������н�
//  2. ���α׷��� : SubjShamAdminBean.java
//  3. ��      ��: ���������н� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 
//  7. ��      ��:
//**********************************************************

package com.credu.course;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;

public class SubjShamAdminBean {

    private ConfigSet config;
    private int row;

	public SubjShamAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }
	}

    /**
     * ���������н�ȭ�� ����Ʈ
     * @param box          receive from the form object and session
     * @return ArrayList   ���� ����Ʈ
     * @throws Exception
     */
    public ArrayList selectListSubjSham(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        SubjectData data = null;
        DataBox dbox   = null;

		// ����¡
		int    v_pageno    = box.getInt("p_pageno");
		if (v_pageno == 0) v_pageno = 1;


        String v_search			= box.getString("p_search");
		String  ss_upperclass   = box.getStringDefault("s_upperclass","ALL");    //�����з�
        String  ss_middleclass  = box.getStringDefault("s_middleclass","ALL");   //�����з�
        String  ss_lowerclass   = box.getStringDefault("s_lowerclass","ALL");    //�����з�
        String  ss_subjcourse   = box.getStringDefault("s_subjcourse","ALL");    //����&�ڽ�
        
        String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");           		//������ ����

        String v_subjnm = box.getString("s_inpubox");   //������

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select /*a.aescontentid aescontentid,*/ b.upperclass upperclass, b.classname classname, ";
            sql += "        a.subj subj, a.subjnm subjnm, a.contenttype contenttype, a.isonoff isonoff,ISUSE  ";
            sql += "   from TZ_SUBJ a, TZ_SUBJATT b                         ";
            sql += "  where a.upperclass = b.upperclass                       ";
            sql += "    and b.middleclass = '000'";
            sql += "    and b.lowerclass = '000'";

            if (!ss_subjcourse.equals("ALL")) {
                sql+= "   and a.subj = " + SQLString.Format(ss_subjcourse);
            } else {
                if (!ss_upperclass.equals("ALL")) {
                    if (!ss_upperclass.equals("ALL")) {
                        sql += " and a.upperclass = "+SQLString.Format(ss_upperclass);
                    }
                    if (!ss_middleclass.equals("ALL")) {
                        sql += " and a.middleclass = "+SQLString.Format(ss_middleclass);
                    }
                    if (!ss_lowerclass.equals("ALL")) {
                        sql += " and a.lowerclass = "+SQLString.Format(ss_lowerclass);
                    }
                }
            }
            
			if (!v_search.equals("")) {
				sql += "  and a.subjnm like '%"+v_search+"%'";
			}

            if (!v_subjnm.equals("")) {
				sql += "  and a.subjnm like '%"+v_subjnm+"%'";
			}

			if(v_orderColumn.equals("")) {
				sql+= " order by b.subjclass asc, a.subj asc ";
			} else {
			    sql+= " order by " + v_orderColumn + v_orderType + ", a.subj asc";
			}
            //sql += " order by b.subjclass asc, a.subj asc";
			//Log.info.println("................."+sql);
            ls = connMgr.executeQuery(sql);

            //����¡
            ls.setPageSize(row);             			//  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno);				//     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage();	//     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount();	//     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
            	/*
                data = new SubjectData();

                data.setUpperclass(ls.getString("upperclass"));
                data.setClassname(ls.getString("classname"));
                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setContenttype(ls.getString("contenttype"));
                data.setIsonoff(ls.getString("isonoff"));
                
                
                list.add(data);
                */
                dbox = ls.getDataBox();
				 //����¡
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
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
     * ���������������н�ȭ�� ����Ʈ
     * @param box          receive from the form object and session
     * @return ArrayList   ���������� ����Ʈ
     * @throws Exception
     */
    public ArrayList selectViewSubjSham(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        SubjseqData data = null;

        String v_subj       = box.getString("p_subj");
        String v_grcode       = box.getSession("tem_grcode");
        String v_auth       = box.getSession("gadmin");
        
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            
            sql = "";
            sql += "SELECT \n";
            sql += "	A.SUBJ, \n";
            sql += "	A.YEAR, \n";
            sql += "	A.SUBJSEQ, \n";
            sql += "	A.GRCODE, \n";
            sql += "	A.GYEAR, \n";
            sql += "	A.GRSEQ, \n";
            sql += "	A.ISBELONGCOURSE, \n";
            sql += "	A.COURSE, \n";
            sql += "	A.CYEAR, \n";
            sql += "	A.COURSESEQ, \n";
            sql += "	A.PROPSTART, \n";
            sql += "	A.PROPEND, \n";
            sql += "	A.EDUSTART, \n";
            sql += "	A.EDUEND, \n";
            sql += "	A.ISCLOSED, \n";
            sql += "	A.SUBJNM, \n";
            sql += "	A.ISOUTSOURCING, \n";
            sql += "	(SELECT GRCODENM FROM TZ_GRCODE WHERE GRCODE = A.GRCODE) AS GRCODENM \n";
            sql += "FROM \n";
            sql += "	TZ_SUBJSEQ A \n";
            sql += "WHERE \n";
            sql += "	A.SUBJ = " + StringManager.makeSQL(v_subj) + "\n";
            if(!v_auth.equals("A1"))
                sql += "  AND  A.GRCODE = " + StringManager.makeSQL(v_grcode) + "\n";
            sql += "ORDER BY A.GYEAR DESC, A.GRSEQ DESC, A.YEAR DESC, A.SUBJSEQ DESC";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new SubjseqData();


                data.setSubj(ls.getString("subj"));
                data.setYear(ls.getString("year"));
                data.setSubjseq(ls.getString("subjseq"));
                data.setGrcode(ls.getString("grcode"));
                data.setGyear(ls.getString("gyear"));
                data.setGrseq(ls.getString("grseq"));
                data.setIsbelongcourse(ls.getString("isbelongcourse"));
                data.setCourse(ls.getString("course"));
                data.setCyear(ls.getString("cyear"));
                data.setCourseseq(ls.getString("courseseq"));
                data.setPropstart(ls.getString("propstart"));
                data.setPropend(ls.getString("propend"));
                data.setEdustart(ls.getString("edustart"));
                data.setEduend(ls.getString("eduend"));
                data.setIsclosed(ls.getString("isclosed"));
                data.setSubjnm(ls.getString("subjnm"));
				data.setIsoutsourcing(ls.getString("isoutsourcing"));
				data.setGrcodenm(ls.getString("grcodenm"));

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

}
