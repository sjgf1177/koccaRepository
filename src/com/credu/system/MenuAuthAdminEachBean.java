//**********************************************************
//  1. ��      ��: �޴� ����(��ں�)
//  2. ���α׷��� : MenuAuthAdminEachBean.java
//  3. ��      ��: �޴� ����(��ں�)
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 11.  15
//  7. ��      ��:
//**********************************************************

package com.credu.system;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;

/**
 * �޴� ����(ADMIN)
 *
 * @date   : 2004. 11
 * @author : S.W.Kang
 */
public class MenuAuthAdminEachBean {
	private static final String CONFIG_NAME = "cur_nrm_grcode";
	
    public MenuAuthAdminEachBean() {}

    /**
    ���ȭ�� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ��� ����Ʈ
    */
    public ArrayList selectListAdminAuth (RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";

        String v_searchtext  = box.getString("p_searchtext");
        String v_search      = box.getString("p_search");
        String ss_gadmin     = box.getStringDefault("s_gadmin","ALL");      //gadmin
        String v_orderColumn = box.getString("p_orderColumn");           	//������ �÷���
        String v_orderType   = box.getString("p_orderType");           		//������ ����

        StringTokenizer st = new StringTokenizer(ss_gadmin,",");
        if (st.hasMoreElements()) {
            ss_gadmin        = (String)st.nextToken();
		}

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

			sql  =  " select distinct a.userid, get_jikwinm(b.jikwi,b.comp) jikwinm,				";
			sql +=	"        b.name, b.comp, get_compnm(b.comp,2,2) compnm, b.jikwi, b.cono, b.name	";
			sql +=  "   from tz_manager a,tz_member b 											";
			sql +=  "  where a.userid = b.userid																									";
            if ( !v_searchtext.equals("")) {                             //    �˻�� ������
                if (v_search.equals("name")) {                          //    �������� �˻��Ҷ�
                    sql += " and b.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("cono")) {                //    ������� �˻��Ҷ�
                    sql += " and b.userid like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            if (!ss_gadmin.equals("ALL")) {
                sql+= " and c.gadmin = " + StringManager.makeSQL(ss_gadmin);
            }
            
            //if(!v_orderColumn.equals("")) {
            //    if(v_orderColumn.equals("userid")){
    		//	    sql+= " order by a." + v_orderColumn + v_orderType;
    		//	}
	    	//	else {
		    //	    sql+= " order by b." + v_orderColumn + v_orderType;
			//    }
			//}
            
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
    ���� ����
    @param box          receive from the form object and session
    @return result      ���� ����
    */  
    public int selectCountGadmin(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        int result = 0;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select count(*) cnt from TZ_GADMIN  ";

            ls = connMgr.executeQuery(sql);

            if  (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
    ��ں� ���Ѹ�
    @param box          receive from the form object and session
    @return result      ���� ����
    */  
    public String gadminPermission(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String v_userid = box.getString("p_userid");
		MenuAuthAdminData data = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        String v_value = "";
        int result = 0;
System.out.println("v_userid������="+v_userid);
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select a.gadminnm  							";
            sql += "   from TZ_GADMIN a,TZ_MANAGER b 	";
            sql += "  where a.gadmin = b.gadmin 				";
            sql += "    and b.userid = '"+v_userid+"'		";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
				data = new MenuAuthAdminData();
				
				data.setGadminnm(ls.getString("gadminnm"));
				if (v_value.equals("")){
					v_value = data.getGadminnm();
				}else{
					v_value = v_value+","+data.getGadminnm();
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
        return v_value;
    }


   /**
    ���� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ����Ʈ
    */  
    public ArrayList selectListGadmin(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        GadminData data = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select gadmin, gadminnm from TZ_GADMIN  ";
            sql += "  order by gadmin asc                    ";

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
    ���� ���Ѹ�
    @param box          receive from the form object and session
    @return ArrayList   ����Ʈ
    */  
    public MenuAuthAdminData selectGadmin(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        MenuAuthAdminData data = null;
        String v_gadminview = box.getString("p_gadminview");
        String p_gadminnm = "";

        if (v_gadminview == "") {
        	v_gadminview="A1";
        }

        try {
            connMgr = new DBConnectionManager();

            sql  = " select gadmin,gadminnm from TZ_GADMIN  ";
            sql += "  where gadmin='"+v_gadminview+"'        ";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new MenuAuthAdminData();

				data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));

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
    �޴����Ѽ���ȭ�� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ����Ʈ
    */  
    public ArrayList selectListMenuAuth(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        String sql = "";
        ArrayList list2 = null;
        MenuAuthAdminData data = null;

		String v_grcode  	= CodeConfigBean.getConfigValue(CONFIG_NAME);
		String v_gadminview = box.getString("p_gadminview");
		String v_systemgubun = "";
		
        String sql_add1 = "";                                         // �������� ���� �߰� ����(�����ڵ�)
        String sql_add2 = "";                                         // �������� ���� �߰� ����(�ش����(�б�/����))

        data  	= this.selectGadmin(box);
        String v_gadmin = data.getGadmin();

        int i = 0;
        
        if (v_gadminview.equals("R1")){
        	v_systemgubun="2";
        }
        else if(v_gadminview.equals("S1")){
        	v_systemgubun="2";
        }
        else if(v_gadminview.equals("T1")){
        	v_systemgubun="2";
        }
        else if(v_gadminview.equals("U1")){
        	v_systemgubun="2";
        }

        try {
            connMgr = new DBConnectionManager();

            list2 = new ArrayList();

            sql  = " select grcode, menu, menunm, levels, seq, control                                  ";
            sql += "   from                                                                     ";
            sql += "      (                                                                     ";
            sql += "        select a.grcode grcode, a.menu menu, a.modulenm menunm, a.seq seq,  ";
            sql += "               b.levels levels, c.gadmin gadmin, c.control control          ";
            sql += "          from tz_menusub a, tz_menu b, tz_menuauth c                       ";
            sql += "          where a.grcode = b.grcode                                         ";
            sql += "           and a.menu   = b.menu                                            ";
            sql += "           and a.grcode = c.grcode(+)                                       ";
            sql += "           and a.menu   = c.menu(+)                                        ";
            sql += "           and a.menu   = c.menu(+)                                         ";
            sql += "           and a.seq    = c.menusubseq(+)                                   ";
            sql += "           and a.grcode = " + StringManager.makeSQL(v_grcode);
            sql += "           and c.gadmin = " + StringManager.makeSQL(v_gadmin);
            if (v_gadminview.equals("R1") || v_gadminview.equals("S1") || v_gadminview.equals("T1") || v_gadminview.equals("U1")){
            	sql += "           and a.systemgubun='"+v_systemgubun+"'";
            }
            sql += "       )                                                                    ";
            sql += " group by grcode, menu, menunm, seq, levels, control                        ";
            sql += " order by grcode asc, menu asc, seq asc                      				";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
            	dbox = ls.getDataBox();

                list2.add(dbox);
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

        return list2;
    }
    
    /**
    �޴����Ѽ���ȭ�� ����Ʈ(��� ������ ���õǾ����� �޴����� �����ֱ�)
    @param box          receive from the form object and session
    @return ArrayList   ����Ʈ
    */  
    public ArrayList selectListAdminMenuAuth(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        String sql = "";
        ArrayList list2 = null;
        String v_userid = box.getString("p_userid");
		String v_grcode  = CodeConfigBean.getConfigValue(CONFIG_NAME);
		
        int i = 0;

        try {
            connMgr = new DBConnectionManager();

            list2 = new ArrayList();

            sql  = " select grcode, menu, menunm, levels, seq, control                          ";
            sql += "   from                                                                     ";
            sql += "      (                                                                     ";
            sql += "        select a.grcode grcode, a.menu menu, a.modulenm menunm, a.seq seq,  ";
            sql += "               b.levels levels, c.userid userid, c.control control          ";
            sql += "          from tz_menusub a, tz_menu b, tz_adminmenuauth c                  ";
            sql += "          where a.grcode = b.grcode                                         ";
            sql += "           and a.menu   = b.menu                                            ";
            sql += "           and a.grcode = c.grcode(+)                                       ";
            sql += "           and a.menu   = c.menu(+)                                         ";
            sql += "           and a.menu   = c.menu(+)                                         ";
            sql += "           and a.seq    = c.menusubseq(+)                                   ";
            sql += "           and a.grcode = " + StringManager.makeSQL(v_grcode);
            sql += "           and c.userid = " + StringManager.makeSQL(v_userid);
            sql += "       )                                                                    ";
            sql += " group by grcode, menu, menunm, seq, levels, control                        ";
            sql += " order by grcode asc, menu asc, seq asc                      				";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
            	dbox = ls.getDataBox();

                list2.add(dbox);
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

        return list2;
    }

    /**
    ������� �󼼺���
    @param box              receive from the form object and session
    @return ManagerData     ��ȸ�� ������
    */
   public MenuAuthAdminData selectViewManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        MenuAuthAdminData data = null;

        String v_userid  = box.getString("p_userid");
        String v_gadmin  = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();

			sql  = " select distinct get_jikwinm(b.jikwi,b.comp) jikwinm, b.cono cono, 		";
            sql += "        b.name name, get_compnm(b.comp,2,2) compnm						";
            sql += "   from TZ_MANAGER a, TZ_MEMBER b, TZ_GADMIN c                          ";
            sql += "  where a.userid = b.userid                                             ";
            sql += "    and a.gadmin = c.gadmin                                             ";
            sql += "    and a.userid  = " + StringManager.makeSQL(v_userid);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
            	//dbox = ls.getDataBox();
            		
                data=new MenuAuthAdminData();
                
                data.setJikwinm(ls.getString("jikwinm"));
                data.setCono(ls.getString("cono"));
                data.setName(ls.getString("name"));
                data.setCompnm(ls.getString("compnm"));
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
    �޴����Ѽ����Ͽ� �����Ҷ�
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
    */      
    public int updateMenuAuth(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk2_check = 0;

		String v_grcode = CodeConfigBean.getConfigValue(CONFIG_NAME);        
        String s_userid = box.getSession("userid");
        String v_userid	= box.getString("p_userid");

        ArrayList list1 = (ArrayList)selectListGadmin(box);           // ������������Ʈ
        GadminData data1 = null;
        String v_gadmin_org    = "";
        //int    v_gadmincnt = list1.size();                            // ������������
				int    v_gadmincnt = 1;                            // ������������

        String v_menu    = "";
        String v_gadmin  = "";
        String v_control = "";
        int v_menusubseq = 0;

        Vector v_vecmenu[]       = new Vector[v_gadmincnt];
        Vector v_vecmenusubseq[] = new Vector[v_gadmincnt];
        Vector v_vecgadmin[]     = new Vector[v_gadmincnt];

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            // ���� ����Ÿ ����
            //sql1  = " delete from TZ_MENUAUTH   ";
            //sql1 += "   where grcode = ?       ";
            //pstmt1 = connMgr.prepareStatement(sql1);
            //
            //pstmt1.setString(1, v_grcode);
            //isOk1 = pstmt1.executeUpdate();
            
            sql1 	= " delete from TZ_ADMINMENUAUTH	";
            sql1 += "  where userid = ?							";
            pstmt1 = connMgr.prepareStatement(sql1);
            
            pstmt1.setString(1, v_userid);
            isOk1	= pstmt1.executeUpdate();

            // ����� �ڷ� ���
            sql2  = " insert into TZ_ADMINMENUAUTH  (grcode, userid, menusubseq, menu, control, luserid, ldate)       ";
            sql2 += "                  values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
            pstmt2 = connMgr.prepareStatement(sql2);

            isOk2 = 1;
            //   ���� ������ŭ ����
            for(int i = 0; i < v_gadmincnt; i++) {
            	DataBox dbox = (DataBox)list1.get(i);
            	
                //data1   = (GadminData)list1.get(i);
                v_gadmin_org   	   = dbox.getString("d_gadmin");
				v_vecmenu[i]       = box.getVector("p_menu");
                v_vecmenusubseq[i] = box.getVector("p_menusubseq");
                //v_vecgadmin[i]     = box.getVector("p_gadmin");

               // �޴� ������ŭ ����
               for(int j = 0; j < v_vecmenu[i].size() ; j++){
                   v_menu       = (String)v_vecmenu[i].elementAt(j);
                   v_menusubseq = StringManager.toInt((String)v_vecmenusubseq[i].elementAt(j));
					StringManager.chkNull(box.getString("p_" + v_gadmin_org + "W" + j + i));
                   v_control = StringManager.chkNull(box.getString("p_" + v_userid + "R" + j)) + StringManager.chkNull(box.getString("p_" + v_userid + "W" + j));
//                   System.out.println("=" + i + "��° ���� : " + v_menu + ", "+j + "��° �޴� : " + v_gadmin + "RW : " + v_control + "\n");
//System.out.println(i+":"+j+": /" + v_grcode + "/" + v_gadmin  + "/" +  v_menusubseq+ "/" +  v_menu+ "/" + v_control);
                   // DB INSERT
                   pstmt2.setString(1, v_grcode);
                   pstmt2.setString(2, v_userid);
                   pstmt2.setInt(3, v_menusubseq);
                   pstmt2.setString(4, v_menu);
                   pstmt2.setString(5, v_control);
                   pstmt2.setString(6, s_userid);

                   isOk2_check = pstmt2.executeUpdate();
                   if (isOk2_check == 0) isOk2 = 0;

               }
            }

            // ���� ��ϵ� �ڷᰡ ���� �� �����Ƿ� isOk1 üũ ����
            if ( isOk2 > 0) connMgr.commit();
            else connMgr.rollback();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk2;
    }
}
