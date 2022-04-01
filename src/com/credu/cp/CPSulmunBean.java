//**********************************************************
//  1. ��      ��: �������� ��
//  2. ���α׷���: CPSulmunBean.java
//  3. ��      ��: ������������ ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ��â�� 2005. 8. 21
//  7. ��      ��:
//**********************************************************

package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

/**
*���ְ�������
*<p>����:CPSulmunBean.java</p>
*<p>����:���ְ������� ��</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author ��â��
*@version 1.0
*/

public class CPSulmunBean {
    private ConfigSet config;
    private int row;
	    	
    public CPSulmunBean() {
        try{
            config = new ConfigSet();  
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }	
    }

    /**
    ���־�ü ��� ���� ����Ʈ
    @param	box			receive from the form object and session
    @return	ArrayList	���־�ü ��� ���� ����Ʈ
    */
    public ArrayList selectCourseList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //BulletinData data = null;
        DataBox dbox = null;
        
        String v_searchtext = box.getString("p_searchtext");
        String v_cp = box.getString("p_cp");
        String v_year = box.getString("p_year");
        int v_sulpapernum = box.getInt("s_sulpapernum");
        //String v_select = box.getString("p_select");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();

            String s_gadmin = box.getSession("gadmin");
            String s_userid = box.getSession("userid");
            
            
            //���־�ü ����Ʈ
            if(s_gadmin.equals("S1") || s_gadmin.equals("T1") || s_gadmin.equals("M1") ){
            	//���־�ü ������ϰ��(�ش��ü�� ������������)
	            sql = "select cpseq, cpnm ";
	            sql += " from tz_cpinfo where userid = " + SQLString.Format(s_userid);	
	            sql += " order by cpnm";	            
	                        
            	ls = connMgr.executeQuery(sql);
            	
            	if(ls.next())
            		v_cp = ls.getString("cpseq");
            	else
            		v_cp = "";
           	}

            sql = " select                                  \n";
            sql+= "   a.subj,                               \n";
            sql+= "   a.grcode,                             \n";
            sql+= "   a.subjseq,                            \n";
            sql+= "   a.year,                               \n";
            sql+= "   a.sulpapernum,                        \n";
            sql+= "   a.cpseq,                              \n";
            sql+= "   b.totcnt,                             \n";
            sql+= "   b.sulnums,                            \n";
            sql+= "   b.sulpapernm,                         \n";
            sql+= "   c.cpnm,                               \n";
            sql+= "   (                                     \n";
            sql+= "     select count(*) from tz_suleach x   \n";
            sql+= "     where                               \n";
            sql+= "       x.subj = a.subj                   \n";
            sql+= "       and x.subjseq = a.subjseq         \n";
            sql+= "       and x.year = a.year               \n";
            sql+= "       and x.grcode = a.grcode           \n";
            sql+= "       and x.sulpapernum = a.sulpapernum \n";
            sql+= "       and x.userid = a.cpseq            \n";
            sql+= "   ) replycnt,                           \n";
            sql+= "   (                                     \n";
            sql+= "     select cpstatus from tz_suleach x   \n";
            sql+= "     where                               \n";
            sql+= "       x.subj = a.subj                   \n";
            sql+= "       and x.subjseq = a.subjseq         \n";
            sql+= "       and x.year = a.year               \n";
            sql+= "       and x.grcode = a.grcode           \n";
            sql+= "       and x.sulpapernum = a.sulpapernum \n";
            sql+= "       and x.userid = a.cpseq            \n";
            sql+= "   ) cpstatus                            \n";
            sql+= " from                                    \n";
            sql+= "   tz_sulpapercp a,                      \n";
            sql+= "   tz_sulpaper b,                        \n";
            sql+= "   tz_cpinfo c                           \n";
            sql+= " where                                   \n";
            sql+= "   a.subj = b.subj                       \n";
            sql+= "   and a.grcode = b.grcode               \n";
            sql+= "   and a.subjseq = b.subjseq             \n";
            sql+= "   and a.year = b.year                   \n";
            sql+= "   and a.sulpapernum = b.sulpapernum     \n";
            sql+= "   and c.cpseq = a.cpseq                 \n";
            
            if(v_sulpapernum != 0){
              sql+= "   and b.sulpapernum = "+v_sulpapernum+"\n";
            }

            //�⵵ �˻�
            if(!v_year.equals("") && !v_year.equals("ALL")){
            	sql += " and a.year = " + StringManager.makeSQL(v_year);
            }
            else if(v_year.equals("")){
            	sql += " and a.year = " + StringManager.makeSQL(FormatDate.getDate("yyyy"));
            }
            
            //���־�ü �˻�
            if(!v_cp.equals("") && !v_cp.equals("ALL")){
                sql += " and c.cpseq = " + StringManager.makeSQL(v_cp);
            }
            sql+= " order by c.cpnm\n";
            System.out.println(sql);

			
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) ��ü����

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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      �� �ݾ��ش�
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  
    
    
    /**
    ���־�ü ���� ���ø���Ʈ
    @param	box			receive from the form object and session
    @return	ArrayList	���־�ü ���� ���ø���Ʈ
    */
    public ArrayList SelectSulmunUserList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_year = box.getString("p_year");
        int v_sulpapernum = box.getInt("s_sulpapernum");

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();
            
            sql+= " select                 \n";
            sql+= "   a.userid,            \n";
            sql+= "   a.sulnums,           \n";
            sql+= "   a.answers,           \n";
            sql+= "   b.cpnm               \n";
            sql+= " from                   \n";
            sql+= "   tz_suleach a,        \n";
            sql+= "   tz_cpinfo b          \n";
            sql+= " where                  \n";
            sql+= "   a.userid = b.cpseq   \n";
            sql+= "   and a.subj = 'CP'    \n";
            sql+= "   and a.grcode = 'ALL' \n";
            sql+= "   and a.cpstatus = 'Y' \n";

            if(v_sulpapernum != 0){
              sql+= "   and a.sulpapernum = "+v_sulpapernum+"\n";
            }

            //�⵵ �˻�
            if(!v_year.equals("") && !v_year.equals("ALL")){
            	sql += " and a.year = " + StringManager.makeSQL(v_year);
            }
            sql+= " order by b.cpnm\n";
            System.out.println(sql);
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
    ���־�ü ���� �亯����
    @param	box			receive from the form object and session
    @return	ArrayList	���־�ü ���� �亯����
    */
    public DataBox SelectSulAnswerInfo(DBConnectionManager connMgr, String year, String sulnum, String selnum) throws Exception {
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            
            sql+= " select                         \n";
            sql+= "   a.sultext, b.selpoint,  \n";
            sql+= "   (select codenm from tz_code where gubun = '0059' and a.distcode = code) gubuntxt \n";
            sql+= " from                           \n";
            sql+= "   tz_sul a, tz_sulsel b        \n";
            sql+= " where                          \n";
            sql+= "   a.subj = b.subj              \n";
            sql+= "   and a.grcode = b.grcode      \n";
            sql+= "   and a.sulnum = b.sulnum      \n";
            //sql+= "   and a.year   = b.year        \n";
            sql+= "   and a.subj = 'CP'            \n";
            sql+= "   and a.grcode  = 'ALL'        \n";
            //sql+= "   and a.year    = '"+year+"'   \n";
            sql+= "   and a.sulnum  = "+sulnum+"   \n";
            sql+= "   and b.selnum  = "+selnum+"   \n";
            System.out.println(sql);
            ls = connMgr.executeQuery(sql);

            if(ls.next()) {
                dbox = ls.getDataBox();
            }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return dbox;
    }
    
}
