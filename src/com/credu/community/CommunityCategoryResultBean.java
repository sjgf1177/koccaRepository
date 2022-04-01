//**********************************************************
//1. ��      ��:
//2. ���α׷���: CommunityCategoryResultBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��:
//
//**********************************************************

package com.credu.community;

import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("unchecked")
public class CommunityCategoryResultBean {
	private ConfigSet config;
	private static int row=10;

	public CommunityCategoryResultBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //�� ����� �������� row ���� �����Ѵ�
			row = 10; //������ ����
			//System.out.println("....... row.....:"+row);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ŀ�´�Ƽ ��ȸ����Ʈ
	 * @param box          receive from the form object and session
	 * @return ArrayList   Ŀ�´�Ƽ ��ȸ����Ʈ
	 * @throws Exception
	 */
	public ArrayList selectCateGoryList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		// ������ : 05.11.15 ������ : �̳��� _ rownum ����
		String              sql     = "";
		StringBuffer        headSql     = new StringBuffer();
		StringBuffer        bodySql     = new StringBuffer();
		String              orderSql     = "";
		String              countSql     = "";

		DataBox             dbox    = null;

		String v_searchtext    = box.getString("p_searchtext");
		String v_select        = box.getString("p_select");
		String v_orderby       = box.getString("p_orderby");
		String v_type_m        = box.getStringDefault("p_type_m", "ALL");
		String v_type_l        = box.getStringDefault("p_type_l", "ALL");
		String v_loc_fg        = box.getStringDefault("p_loc_fg","ALL");
		int    v_pageno        = box.getInt("p_pageno");

		try {
			connMgr = new DBConnectionManager();

			headSql.append(" SELECT  A.CMUNO, A.CMU_NM, A.MEMBER_CNT, A.CLOSE_FG       \n ");
			headSql.append("         , A.ACCEPT_DTE, A.REGISTER_DTE, A.REGISTER_USERID \n ");
			headSql.append("         , A.MODIFIER_DTE, A.MODIFIER_USERID, B.KOR_NAME   \n ");
			headSql.append("         , C.KOR_NM GRADE_NM, INTRO                        \n ");
			headSql.append("         , ISNULL(D.SAVEFILE,'') HONGBO_SAVEFILE           \n ");
			headSql.append("         , ISNULL(D.CONTENTS,'??????')  HONGBO_CONTENTS    \n ");
			bodySql.append(" FROM    TZ_CMUBASEMST A                                   \n ");
			bodySql.append("         , (                                               \n ");
			bodySql.append("             SELECT  CMUNO,USERID,KOR_NAME,GRADE           \n ");
			bodySql.append("             FROM    TZ_CMUUSERMST                         \n ");
			bodySql.append("             WHERE   GRADE='01'                            \n ");
			bodySql.append("             AND     CLOSE_FG='1'                          \n ");
			bodySql.append("         ) B                                               \n ");
			bodySql.append("         , TZ_CMUGRDCODE C                                 \n ");
			bodySql.append("         , TZ_CMUHONGBO D                                  \n ");
			bodySql.append(" WHERE   A.CMUNO     = B.CMUNO                             \n ");
			bodySql.append(" AND     B.CMUNO     = C.CMUNO                             \n ");
			bodySql.append(" AND     B.GRADE     = C.GRCODE                            \n ");
			bodySql.append(" AND     B.CMUNO     = D.CMUNO(+)                          \n ");
			bodySql.append(" AND     A.CLOSE_FG  = '1'                                 \n ");


			if("1".equals(v_loc_fg)){//�α�Ŀ�´�Ƽ
				bodySql.append(" AND A.HOLD_FG  = 1                                    \n ");
			}

			if("2".equals(v_loc_fg)){//�ű�Ŀ�´�Ƽ
				bodySql.append(" AND    SYSDATE - TO_DATE(SUBSTRING(A.ACCEPT_DTE, 1, 8)) < 30  \n ");
			}

			if(!v_type_m.equals("ALL")){
				bodySql.append(" AND A.TYPE_M  = '").append(v_type_m).append("'        \n ");
			} else if(!v_type_l.equals("ALL")){
				bodySql.append(" AND A.TYPE_L  = '").append(v_type_l).append("'        \n ");
			}

			if ( !v_searchtext.equals("")) {      // �˻�� ������
				if (v_select.equals("cmu_nm"))     bodySql.append(" AND LOWER(A.CMU_NM)    LIKE LOWEr ( ").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(") \n");;
				if (v_select.equals("intro"))      bodySql.append(" AND LOWER(A.INTRO)     LIKE LOWER ( ").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(") \n");
				if (v_select.equals("kor_name"))   bodySql.append(" AND LOWER(B.KOR_NAME)  LIKE LOWER ( ").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(") \n");
			}



			if (v_orderby.equals("cmu_nm"))    orderSql = " order by a.cmu_nm asc";
			else if (v_orderby.equals("kor_name"))  orderSql = " order by b.kor_name asc";
			else if (v_orderby.equals("accept_dte"))orderSql = " order by a.accept_dte asc";
			else orderSql = " order by a.accept_dte asc";

			sql= headSql.toString()+ bodySql.toString()+ orderSql;

			ls = connMgr.executeQuery(sql);
			countSql= "select count(*) " + bodySql.toString();
			int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql);

			ls.setPageSize(5);                         // �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno,totalrowcount );  // ������������ȣ�� �����Ѵ�.
			int total_page_count = ls.getTotalPage();    // ��ü ������ ���� ��ȯ�Ѵ�
			//int total_row_count = ls.getTotalCount();  // ��ü row ���� ��ȯ�Ѵ�

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum"  , new Integer(totalrowcount - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(total_page_count));
				dbox.put("d_rowcount" , new Integer(row));
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
	 * Ŀ�´�Ƽ ��ȸ����Ʈ
	 * @param box          receive from the form object and session
	 * @return ArrayList   Ŀ�´�Ƽ ��ȸ����Ʈ
	 * @throws Exception
	 */
	public ArrayList selectCateGoryGroup(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		StringBuffer         sql     = new StringBuffer();

		DataBox             dbox    = null;

		try {
			connMgr = new DBConnectionManager();

			//Ŀ�´�Ƽ ��з�
			sql.append("SELECT  'ALL' CODE, '��ü' CODENM, COUNT(B.CMUNO) CNT\n");
			sql.append("FROM    TZ_CODE A, TZ_CMUBASEMST B            \n");
			sql.append("WHERE   A.CODE     =  B.TYPE_L(+)             \n");
			sql.append("AND     A.GUBUN    = '0052'                   \n");
			sql.append("AND     A.LEVELS   = 1                        \n");
			sql.append("AND     B.CLOSE_FG(+) = '1'                   \n");
			sql.append(" UNION ALL                                    \n");
			sql.append(" SELECT  A.CODE, A.CODENM, COUNT(B.CMUNO) CNT \n");
			sql.append(" FROM    TZ_CODE A, TZ_CMUBASEMST B           \n");
			sql.append(" WHERE   A.CODE     =  B.TYPE_L(+)            \n");
			sql.append(" AND     A.GUBUN    = '0052'                  \n");
			sql.append(" AND     A.LEVELS   = 1                       \n");
			sql.append(" AND	 B.CLOSE_FG(+) = '1'                  \n");
			sql.append(" GROUP BY  CODE, CODENM                       \n");

			ls = connMgr.executeQuery(sql.toString());

			while (ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}


	/**
	 * ȫ������ ��ȸ
	 * @param box          receive from the form object and session
	 * @return ArrayList   ȫ�� ������
	 * @throws Exception
	 */
	public ArrayList selectHongbo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();

		String              sql     = "";
		DataBox             dbox    = null;

		String  v_cmuno = box.getString("p_cmuno");




		try {
			connMgr = new DBConnectionManager();
			sql  = " select a.cmuno           cmuno           , a.cmu_nm        cmu_nm      , a.in_method_fg     in_method_fg   , a.search_fg search_fg"
				+"              , a.data_passwd_fg  data_passwd_fg  , a.display_fg    display_fg  , a.type_l           type_l         ,a.type_m type_m"
				+ "             , a.intro           intro           , a.img_path      img_path    , a.layout_fg        layout_fg      , a.html_skin_fg html_skin_fg"
				+"              , a.read_cnt        read_cnt        , a.member_cnt    member_cnt  , a.close_fg         close_fg"
				+ "             , a.close_reason    close_reason    , a.close_dte     close_dte   , a.close_userid     close_userid"
				+"              , a.hold_fg         hold_fg         , a.accept_dte    accept_dte  , a.accept_userid    accept_userid  , a.register_dte  register_dte"
				+ "             , a.register_userid register_userid , a.modifier_dte  modifier_dte, a.modifier_userid  modifier_userid "
				+"              , b.kor_name        kor_name        , c.codenm      grade_nm"
				+"              , isnull(d.savefile,'')        hongbo_savefile , isnull(d.contents,'')      hongbo_contents"
				+"    from tz_cmubasemst a "
				+"        ,(select cmuno,userid,kor_name,grade from tz_cmuusermst where grade='01' and close_fg='1') b"
				+"        ,(select cmuno cmuno,grcode grcode,kor_nm codenm from tz_cmugrdcode) c"
				+"        ,tz_cmuhongbo d"
				+ "  where a.cmuno  = b.cmuno"
				+ "    and b.cmuno  = c.cmuno"
				// ������ : 05.11.09 ������ : �̳��� _(+)  ����
				//                   + "    and b.cmuno  = d.cmuno(+)"
				+ "    and b.cmuno   =  d.cmuno(+) "
				+ "    and b.grade  = c.grcode"
				+"    and a.close_fg  = '1'"
				+ "  and a.cmuno        = '"+v_cmuno+"'"
				;
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

}
