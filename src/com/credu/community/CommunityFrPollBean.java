//**********************************************************
//1. ��      ��:
//2. ���α׷���: CommunityFrPollBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��:
//
//**********************************************************

package com.credu.community;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
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
public class CommunityFrPollBean {
	private ConfigSet config;
	private int row;

	public CommunityFrPollBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.manage.row"));  //�� ����� �������� row ���� �����Ѵ�
			row = 10; //������ ����
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ʿ����ں��̱�
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public String LPAD(String str, int len, char pad) {
		String result = str;
		int templen = len - result.getBytes().length;

		for (int i = 0; i < templen; i++)
			result = pad + result;

		return result;
	}

	/**
	 * �������
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int insertFrPoll(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		ListSet     		ls    = null;
		StringBuffer      sql   = new StringBuffer();
		int         isOk        = 1;

		String v_cmuno         = box.getString("p_cmuno");
		String v_pollno        = box.getString("p_pollno");
		String v_questno       = box.getString("p_questno");
		String v_background    = box.getString("p_background");

		String v_title 	     = box.getString("p_title");
		String v_fdte			 = box.getString("p_fdte").replaceAll("-","");
		String v_tdte          = box.getString("p_tdte").replaceAll("-","");

		String v_need_question	= box.getString("p_need_question");
		String v_need_fg			= box.getString("p_need_fg");
		String v_re_gen_fg		= box.getString("p_re_gen_fg");
		String v_re_spe_fg		= box.getString("p_re_spe_fg"); //�Ⱦ��̰� �ֽ� , ������ ��

		Vector v_field_name    = box.getVector("p_field_name");
		Vector v_fieldno       = box.getVector("p_fieldno");

		String s_userid        = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			// ����Ÿ ���
			if(v_pollno.length() <1){
				sql.append(" select nvl(max(pollno), 0)  from tz_cmupollmst where cmuno ='").append(v_cmuno).append("'");
				ls = connMgr.executeQuery(sql.toString());

				sql.setLength(0);

				while (ls.next()) v_pollno= String.valueOf(ls.getInt(1)+1);

				sql.append(" insert into tz_cmupollmst\n ");
				sql.append(" (\n ");
				sql.append("     cmuno, pollno, title, fdte\n ");
				sql.append("     , tdte, tot_poll_cnt, background\n ");
				sql.append("     , register_userid, register_dte\n ");
				sql.append("     , modifier_userid, modifier_dte\n ");
				sql.append("     , del_fg\n ");
				sql.append(" )\n ");
				sql.append(" values\n ");
				sql.append(" (\n ");
				sql.append("     ?, ?, ?,?, ?, 0, ?, ?\n ");
				sql.append("     ,to_char(sysdate, 'YYYYMMDDHH24MISS'), ?\n ");
				sql.append("     ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N'\n ");
				sql.append(" )\n ");

				pstmt = connMgr.prepareStatement(sql.toString());
				int index = 1;
				pstmt.setString(index++ , v_cmuno                 );
				pstmt.setString(index++ , v_pollno                );
				pstmt.setString(index++ , v_title);
				pstmt.setString(index++ , v_fdte);
				pstmt.setString(index++ , v_tdte);
				pstmt.setCharacterStream(index++, new StringReader(v_background), v_background.length());
				pstmt.setString(index++ , s_userid                );
				pstmt.setString(index++ , s_userid                );

				isOk=pstmt.executeUpdate();

				if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }


			} else {
				sql.append(" update tz_cmupollmst set\n ");
				sql.append("     title             =?, fdte            =?\n ");
				sql.append("     , tdte            =?, background      =?\n ");
				sql.append("     , modifier_userid =?\n ");
				sql.append("     , modifier_dte    =to_char(sysdate, 'YYYYMMDDHH24MISS')\n ");
				sql.append(" where  cmuno   =?\n ");
				sql.append("   and  pollno  =?\n ");

				int index = 1;
				pstmt = connMgr.prepareStatement(sql.toString());
				pstmt.setString(index++ , v_title);
				pstmt.setString(index++ , v_fdte);
				pstmt.setString(index++ , v_tdte);
				pstmt.setString(index++ , v_background);
				//pstmt.setCharacterStream(index++, new StringReader(v_background), v_background.length());
				pstmt.setString(index++ , s_userid);
				pstmt.setString(index++ , v_cmuno);
				pstmt.setString(index++ , v_pollno);
				isOk=pstmt.executeUpdate();

				if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			}

			sql.setLength(0);

			//�����׸����� ���
			if(v_questno.length() <1){
				sql.append(" select  max(questno)\n ");
				sql.append(" from    tz_cmupollquest\n ");
				sql.append(" where   cmuno    = '").append(v_cmuno ).append("'\n ");
				sql.append(" and     pollno   = '").append(v_pollno).append("'\n ");

				ls = connMgr.executeQuery(sql.toString());


				while (ls.next()) v_questno= String.valueOf(ls.getInt(1)+1);

				sql.setLength(0);

				sql.append(" insert into tz_cmupollquest\n ");
				sql.append(" (\n ");
				sql.append("     cmuno, pollno, questno, need_question\n ");
				sql.append("     , need_fg, re_gen_fg, re_spe_fg, item_cnt\n ");
				sql.append("     , register_userid , register_dte\n ");
				sql.append("     , modifier_userid , modifier_dte\n ");
				sql.append(" )\n ");
				sql.append(" values\n ");
				sql.append(" (\n ");
				sql.append("     ?, ?, ?,?, ?, ?, ?,?\n ");
				sql.append("     , ?,to_char(sysdate, 'YYYYMMDDHH24MISS')\n ");
				sql.append("     , ?,to_char(sysdate, 'YYYYMMDDHH24MISS')\n ");
				sql.append(" )\n ");

				pstmt = connMgr.prepareStatement(sql.toString());
				pstmt.setString(1 , v_cmuno);
				pstmt.setString(2 , v_pollno);
				pstmt.setString(3 , v_questno);
				pstmt.setString(4 , v_need_question);
				pstmt.setString(5 , v_need_fg);
				pstmt.setString(6 , v_re_gen_fg);
				pstmt.setString(7 , v_re_spe_fg);
				pstmt.setString(8 , "0");
				pstmt.setString(9 , s_userid);
				pstmt.setString(10 , s_userid);
				isOk=pstmt.executeUpdate();
			} else {

				sql.append(" update tz_cmupollquest set\n ");
				sql.append("     need_question     =?, need_fg         =?\n ");
				sql.append("     , re_gen_fg       =?, re_spe_fg       =?\n ");
				sql.append("     , item_cnt        =?, modifier_userid =?\n ");
				sql.append("     , modifier_dte    =to_char(sysdate, 'YYYYMMDDHH24MISS')\n ");
				sql.append(" where  cmuno    =?\n ");
				sql.append("   and  pollno   =?\n ");
				sql.append("   and  questno  =?\n ");

				pstmt = connMgr.prepareStatement(sql.toString());
				pstmt.setString(1 , v_need_question);
				pstmt.setString(2 , v_need_fg);
				pstmt.setString(3 , v_re_gen_fg);
				pstmt.setString(4 , v_re_spe_fg);
				pstmt.setString(5 , "0");
				pstmt.setString(6 , s_userid);
				pstmt.setString(7 , v_cmuno);
				pstmt.setString(8 , v_pollno);
				pstmt.setString(9 , v_questno);
				isOk=pstmt.executeUpdate();
			}
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			sql.setLength(0);

			//�������׸� ���

			sql.append(" delete from tz_cmupollfield\n ");
			sql.append(" where   cmuno    = '").append(v_cmuno  ).append("'\n ");
			sql.append(" and     pollno   = '").append(v_pollno ).append("'\n ");

			pstmt = connMgr.prepareStatement(sql.toString());

			pstmt.executeUpdate();

			for(int i=0; i < v_field_name.size();i++){

				String v_tmp_fieldno    =(String)v_fieldno.elementAt(i);
				String v_tmp_field_name =(String)v_field_name.elementAt(i);

				sql.setLength(0);

				sql.append(" select  max(fieldno)\n ");
				sql.append(" from    tz_cmupollfield\n ");
				sql.append(" where   cmuno    = '").append(v_cmuno  ).append("'\n ");
				sql.append(" and     pollno   = '").append(v_pollno ).append("'\n ");
				sql.append(" and     questno  = '").append(v_questno).append("'\n ");

				ls = connMgr.executeQuery(sql.toString());
				while (ls.next()) v_tmp_fieldno= String.valueOf(ls.getInt(1)+1);

				sql.setLength(0);

				sql.append(" insert into tz_cmupollfield\n ");
				sql.append(" (\n ");
				sql.append("     cmuno, pollno, questno, fieldno\n ");
				sql.append("     , field_name, poll_cnt, etc_fg, etc_nm\n ");
				sql.append("     , register_userid , register_dte\n ");
				sql.append("     , modifier_userid , modifier_dte\n ");
				sql.append(" )\n ");
				sql.append(" values\n ");
				sql.append(" (\n ");
				sql.append("     ?, ?, ?, ?, ?, ?, ?,?\n ");
				sql.append("     , ?,to_char(sysdate, 'YYYYMMDDHH24MISS')\n ");
				sql.append("     , ?,to_char(sysdate, 'YYYYMMDDHH24MISS')\n ");
				sql.append(" )\n ");

				pstmt = connMgr.prepareStatement(sql.toString());
				pstmt.setString(1 , v_cmuno                 );
				pstmt.setString(2 , v_pollno                );
				pstmt.setString(3 , v_questno                );
				pstmt.setString(4 , v_tmp_fieldno                );
				pstmt.setString(5 , v_tmp_field_name);
				pstmt.setString(6 , "0" );
				pstmt.setString(7 , "N" );
				pstmt.setString(8 , "" );
				pstmt.setString(9  , s_userid                );
				pstmt.setString(10 , s_userid                );
				isOk=pstmt.executeUpdate();

			}


			if(isOk > 0 ) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}



	/**
	 * ��������
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int deleteFrPoll(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";
		int         isOk        = 1;

		String v_cmuno         = box.getString("p_cmuno");
		String v_pollno        = box.getString("p_pollno");

		String s_userid        = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql  =" update tz_cmupollmst set  del_fg           ='Y'"
				+"                         , modifier_userid =?"
				+"                         , modifier_dte    =to_char(sysdate, 'YYYYMMDDHH24MISS')"
				+"                  where  cmuno   =?"
				+"                    and  pollno  =?";
			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(1 , s_userid                );
			pstmt.setString(2 , v_cmuno                 );
			pstmt.setString(3 , v_pollno                );
			isOk=pstmt.executeUpdate();

			if(isOk > 0 ) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


	/**
	 * ���׻���
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int deleteFrPollField(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		ListSet     ls          = null;
		StringBuffer  sql       = new StringBuffer();
		int         isOk        = 1;
		int         isOk2       = 1;
		int         isOk3       = 1;
		int         isOk4       = 1;

		String v_cmuno         = box.getString("p_cmuno");
		String v_pollno        = box.getString("p_pollno");
		// String v_questno        = box.getString("p_questno");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql.append(" delete from  tz_cmupollquest");
			sql.append(" where  cmuno    =?");
			sql.append(" and  pollno   =?");

			pstmt = connMgr.prepareStatement(sql.toString());

			pstmt.setString(1 , v_cmuno                 );
			pstmt.setString(2 , v_pollno                );

			isOk = pstmt.executeUpdate();

			pstmt.close();
			sql.setLength(0);



			sql.append(" delete from  tz_cmupollticket");
			sql.append(" where  cmuno    =?");
			sql.append(" and  pollno   =?");

			pstmt = connMgr.prepareStatement(sql.toString());

			pstmt.setString(1 , v_cmuno                 );
			pstmt.setString(2 , v_pollno                );

			isOk2 = pstmt.executeUpdate();

			pstmt.close();
			sql.setLength(0);

			sql.append(" delete from  tz_cmupollfield");
			sql.append(" where  cmuno    =?");
			sql.append(" and  pollno   =?");

			pstmt = connMgr.prepareStatement(sql.toString());

			pstmt.setString(1 , v_cmuno                 );
			pstmt.setString(2 , v_pollno                );

			isOk3 = pstmt.executeUpdate();

			pstmt.close();
			sql.setLength(0);

			sql.append(" delete from  TZ_CMUPOLLMST");
			sql.append(" where  cmuno    =?");
			sql.append(" and  pollno   =?");

			pstmt = connMgr.prepareStatement(sql.toString());

			pstmt.setString(1 , v_cmuno                 );
			pstmt.setString(2 , v_pollno                );

			isOk4 = pstmt.executeUpdate();



			if(isOk > 0 && isOk2 > 0 && isOk3 > 0 && isOk4 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
				isOk = 1;
			} else isOk = 0;

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}
	/**
	 * �亯���
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int replyFrPoll(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";
		int         isOk        = 1;

		String v_cmuno         = box.getString("p_cmuno");
		String v_pollno        = box.getString("p_pollno");

		Vector v_questno       = box.getVector("p_questno");
		Vector v_re_gen_fg     = box.getVector("p_re_gen_fg");

		String s_userid        = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//����ǥ�ڼ� ����
			sql  =" update tz_cmupollmst set  tot_poll_cnt    =tot_poll_cnt+1"
				+"                  where  cmuno   =?"
				+"                    and  pollno  =?";
			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(1 , v_cmuno                 );
			pstmt.setString(2 , v_pollno                );

			isOk=pstmt.executeUpdate();

			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }

			for(int i=0;i<v_questno.size();i++){
				String v_tmp_fquestno     =(String)v_questno.elementAt(i);
				String v_tmp_re_gen_fg    =(String)v_re_gen_fg.elementAt(i);
				if("1".equals(v_tmp_re_gen_fg)){//�����������ΰ��
					String v_tmp_fieldno       = box.getString("p_fieldno"+v_tmp_fquestno+"a");
					String v_tmp_etc_nm        = box.getString("p_etc_nm"+v_tmp_fquestno+"a");
					sql  =" update tz_cmupollfield set   poll_cnt   =poll_cnt +1"
						+"                  where  cmuno    =?"
						+"                    and  pollno   =?"
						+"                    and  questno  =?"
						+"                    and  fieldno  =?";

					pstmt = connMgr.prepareStatement(sql);
					pstmt.setString(1 , v_cmuno                 );
					pstmt.setString(2 , v_pollno                );
					pstmt.setString(3 , v_tmp_fquestno          );
					pstmt.setString(4 , v_tmp_fieldno           );
					isOk=pstmt.executeUpdate();

					if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
					sql  =" insert into tz_cmupollticket (  cmuno, pollno, questno, userid "
						+"                              , sel_text, question, shot_text, register_dte) "
						+"             values (       ?                 , ?           , ?         ,?"
						+"                          , ?                 , ?           , ? "
						+"                          , to_char(sysdate, 'YYYYMMDDHH24MISS'))";
					pstmt = connMgr.prepareStatement(sql);
					pstmt.setString(1 , v_cmuno                 );
					pstmt.setString(2 , v_pollno                );
					pstmt.setString(3 , v_tmp_fquestno                );
					pstmt.setString(4 , s_userid               );
					pstmt.setString(5 , v_tmp_fieldno);
					pstmt.setString(6 , "" );
					pstmt.setString(7 , v_tmp_etc_nm );
					isOk=pstmt.executeUpdate();
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
				} else if("2".equals(v_tmp_re_gen_fg)||"3".equals(v_tmp_re_gen_fg)){//�����ΰ��
					Vector vv_tmp_fieldno       = box.getVector("p_fieldno"+v_tmp_fquestno+"a");
					String v_tmp_etc_nm        = box.getString("p_etc_nm"+v_tmp_fquestno+"a");
					String v_tmp_set_text="";
					for(int j=0;j<vv_tmp_fieldno.size();j++){
						String v_sub_tt_fieldno=(String)vv_tmp_fieldno.elementAt(j);
						v_tmp_set_text +=v_sub_tt_fieldno+",";
						sql  =" update tz_cmupollfield set   poll_cnt   =poll_cnt +1"
							+"                  where  cmuno    =?"
							+"                    and  pollno   =?"
							+"                    and  questno  =?"
							+"                    and  fieldno  =?";

						pstmt = connMgr.prepareStatement(sql);
						pstmt.setString(1 , v_cmuno                 );
						pstmt.setString(2 , v_pollno                );
						pstmt.setString(3 , v_tmp_fquestno          );
						pstmt.setString(4 , v_sub_tt_fieldno           );
						isOk=pstmt.executeUpdate();
						if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
					}
					sql  =" insert into tz_cmupollticket (  cmuno, pollno, questno, userid "
						+"                              , sel_text, question, shot_text, register_dte) "
						+"             values (       ?                 , ?           , ?         ,?"
						+"                          , ?                 , ?           , ? "
						+"                          , to_char(sysdate, 'YYYYMMDDHH24MISS'))";
					pstmt = connMgr.prepareStatement(sql);
					pstmt.setString(1 , v_cmuno                 );
					pstmt.setString(2 , v_pollno                );
					pstmt.setString(3 , v_tmp_fquestno                );
					pstmt.setString(4 , s_userid               );
					pstmt.setString(5 , v_tmp_set_text);
					pstmt.setString(6 , "" );
					pstmt.setString(7 , v_tmp_etc_nm );
					isOk=pstmt.executeUpdate();
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
				} else if("4".equals(v_tmp_re_gen_fg)||"5".equals(v_tmp_re_gen_fg)){//�����ΰ��
					String v_tmp_reply       = box.getString("p_reply"+v_tmp_fquestno+"a");
					sql  =" insert into tz_cmupollticket (  cmuno, pollno, questno, userid "
						+"                              , sel_text, question, shot_text, register_dte) "
						+"             values (       ?                 , ?           , ?         ,?"
						+"                          , ?                 , ?           , ? "
						+"                          , to_char(sysdate, 'YYYYMMDDHH24MISS'))";
					pstmt = connMgr.prepareStatement(sql);
					pstmt.setString(1 , v_cmuno                 );
					pstmt.setString(2 , v_pollno                );
					pstmt.setString(3 , v_tmp_fquestno                );
					pstmt.setString(4 , s_userid               );
					pstmt.setString(5 , "");
					pstmt.setString(6 , "" );
					pstmt.setString(7 , v_tmp_reply );
					isOk=pstmt.executeUpdate();
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
				}
			}

			if(isOk > 0 ) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/**
	 * ����Ʈ
	 * @param box          receive from the form object and session
	 * @return ArrayList   ����Ʈ
	 * @throws Exception
	 */
	public ArrayList selectPollList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();

		String sql    	  	= "";
		String countSql  		= "";
		StringBuffer headSql  = new StringBuffer();
		StringBuffer bodySql  = new StringBuffer();
		StringBuffer bodySql2 = new StringBuffer();
		DataBox             dbox    = null;

		String v_cmuno         = box.getString("p_cmuno");

		String v_searchtext = box.getString("p_searchtext");
		String v_select     = box.getString("p_select");
		int v_pageno        = box.getInt("p_pageno");

		try {
			connMgr = new DBConnectionManager();

			headSql.append(" SELECT  A.*\n ");
			headSql.append(" FROM    (\n ");
			headSql.append("         SELECT  A.CMUNO, A.POLLNO, A.TITLE, A.FDTE FDTE, A.TDTE, A.TOT_POLL_CNT\n ");
			headSql.append("                 , A.BACKGROUND, A.STR_FG, A.REGISTER_USERID, A.REGISTER_DTE\n ");
			headSql.append("                 , B.USERID,B.RESNO, C.KOR_NAME NAME, C.EMAIL, B.DEPTNAM, B.JIKUPNM\n ");
			headSql.append("                 , B.JIKWINM, C.TEL, C.MOBILE, C.OFFICE_TEL, C.WK_AREA, C.GRADE, E.KOR_NM GRADE_NM\n ");
			headSql.append("                 , CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') < A.FDTE THEN 'PRE'\n ");
			headSql.append("                        WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.FDTE AND A.TDTE THEN 'ING'\n ");
			headSql.append("                        ELSE 'END'\n ");
			headSql.append("                 END POLL_STATUS\n ");
			bodySql.append("        FROM    TZ_CMUPOLLMST A, TZ_MEMBER B, TZ_CMUUSERMST C\n ");
			bodySql.append("                , TZ_CMUGRDCODE E\n ");
			bodySql.append("        WHERE   A.REGISTER_USERID  = B.USERID\n ");
			bodySql.append("    and b.grcode  = ").append(StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n ");
			bodySql.append("        AND A.CMUNO            = C.CMUNO\n ");
			bodySql.append("        AND A.REGISTER_USERID  = C.USERID\n ");
			bodySql.append("        AND C.CMUNO            = E.CMUNO\n ");
			bodySql.append("        AND C.GRADE            = E.GRCODE\n ");
			bodySql.append("        AND A.CMUNO            = "+StringManager.makeSQL(v_cmuno)+"\n ");
			bodySql.append("        AND A.DEL_FG           = 'N'\n ");
			bodySql2.append("        ) A\n ");
			bodySql2.append(" ORDER BY A.POLLNO DESC");

			if ( !v_searchtext.equals("")) {      // �˻�� ������
				if (v_select.equals("title"))   bodySql.append("  and lower(a.title)    like lower (" + StringManager.makeSQL("%" + v_searchtext + "%")+")\n");
				if (v_select.equals("name"))    bodySql.append(" and lower(c.kor_name) like lower (" + StringManager.makeSQL("%" + v_searchtext + "%")+")\n");
			}

			sql= headSql.toString()+ bodySql.toString()+ bodySql2.toString();
			ls = connMgr.executeQuery(sql);

			countSql= "select count(*) "+ bodySql.toString();
			int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);

			ls.setPageSize(row);                         // �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno, total_row_count);                 // ������������ȣ�� �����Ѵ�.
			int total_page_count = ls.getTotalPage();    // ��ü ������ ���� ��ȯ�Ѵ�
			//int total_row_count = ls.getTotalCount();    // ��ü row ���� ��ȯ�Ѵ�

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
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
	 * ����Ÿ��ȸ
	 * @param box          receive from the form object and session
	 * @return ArrayList   ����Ÿ��ȸ
	 * @throws Exception
	 */
	public ArrayList selectPollMst(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		StringBuffer        sql     = new StringBuffer();
		DataBox             dbox    = null;

		String v_cmuno         = box.getString("p_cmuno");
		String v_pollno         = box.getString("p_pollno");

		try {
			connMgr = new DBConnectionManager();

			sql.append(" SELECT  A.CMUNO, A.POLLNO, A.TITLE, A.FDTE FDTE, A.TDTE, A.TOT_POLL_CNT\n ");
			sql.append("         , A.BACKGROUND, A.STR_FG, A.REGISTER_USERID, A.REGISTER_DTE\n ");
			sql.append("         , B.USERID,B.RESNO, C.KOR_NAME NAME, C.EMAIL, B.DEPTNAM, B.JIKUPNM\n ");
			sql.append("         , B.JIKWINM, C.TEL, C.MOBILE, C.OFFICE_TEL, C.WK_AREA, C.GRADE, E.KOR_NM GRADE_NM\n ");
			sql.append("         , CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') < A.FDTE THEN 'PRE'\n ");
			sql.append("                WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.FDTE AND A.TDTE THEN 'ING'\n ");
			sql.append("                ELSE 'END'\n ");
			sql.append("         END POLL_STATUS\n ");
			sql.append(" FROM    TZ_CMUPOLLMST A, TZ_MEMBER B, TZ_CMUUSERMST C\n ");
			sql.append("         , TZ_CMUGRDCODE E\n ");
			sql.append(" WHERE   A.REGISTER_USERID  = B.USERID\n ");
			sql.append(" AND A.CMUNO            = C.CMUNO\n ");
			sql.append("    and b.grcode  = ").append(StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n ");
			sql.append(" AND A.REGISTER_USERID  = C.USERID\n ");
			sql.append(" AND C.CMUNO            = E.CMUNO\n ");
			sql.append(" AND C.GRADE            = E.GRCODE\n ");
			sql.append(" AND A.CMUNO            = "+StringManager.makeSQL(v_cmuno)+"\n ");
			sql.append(" AND A.POLLNO           = "+StringManager.makeSQL(v_pollno)+"\n ");
			sql.append(" AND A.DEL_FG           = 'N'\n ");

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
	 * ��ȸ
	 * @param box          receive from the form object and session
	 * @return ArrayList   ��ȸ
	 * @throws Exception
	 */
	public ArrayList selectPollView(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		ArrayList           list1    = new ArrayList(); // ���� MST
		ArrayList           list2    = new ArrayList(); // ���� LIST
		ArrayList           list3    = new ArrayList(); // �亯 �׸� (������ �亯 ���)
		ArrayList           list4    = new ArrayList(); // �亯 �׸�(�ְ��� �亯 ���)

		StringBuffer        sql     = new StringBuffer();
		DataBox             dbox    = null;

		String v_cmuno         = box.getString("p_cmuno");
		String v_pollno        = box.getString("p_pollno");
		String s_userid        = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			sql.append(" SELECT  A.CMUNO, A.POLLNO, A.TITLE, A.FDTE, A.TDTE, A.TOT_POLL_CNT\n ");
			sql.append("         , A.BACKGROUND, A.STR_FG, A.REGISTER_USERID, A.REGISTER_DTE\n ");
			sql.append("         , B.USERID,B.RESNO, C.KOR_NAME NAME, C.EMAIL, B.DEPTNAM, B.JIKUPNM\n ");
			sql.append("         , B.JIKWINM, C.TEL, C.MOBILE, C.OFFICE_TEL, C.WK_AREA, C.GRADE, E.KOR_NM GRADE_NM\n ");
			sql.append("         , CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') < A.FDTE THEN 'PRE'\n ");
			sql.append("                WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.FDTE AND A.TDTE THEN 'ING'\n ");
			sql.append("                ELSE 'END'\n ");
			sql.append("         END POLL_STATUS\n ");
			sql.append("         , DECODE(F.USERID, NULL, 'N', 'Y')  APPLY_YN\n ");
			sql.append(" FROM    TZ_CMUPOLLMST A, TZ_MEMBER B, TZ_CMUUSERMST C\n ");
			sql.append("         , TZ_CMUGRDCODE E, TZ_CMUPOLLTICKET F\n ");
			sql.append(" WHERE   A.REGISTER_USERID  = B.USERID\n ");
			sql.append(" AND A.CMUNO            = C.CMUNO\n ");
			sql.append("    and b.grcode  = ").append(StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n ");
			sql.append(" AND A.REGISTER_USERID  = C.USERID\n ");
			sql.append(" AND C.CMUNO            = E.CMUNO\n ");
			sql.append(" AND C.GRADE            = E.GRCODE\n ");
			sql.append(" AND A.CMUNO            = F.CMUNO(+)\n ");
			sql.append(" AND A.POLLNO           = F.POLLNO(+)\n ");
			sql.append(" AND F.USERID(+)        = "+StringManager.makeSQL(s_userid)+"\n ");
			sql.append(" AND A.CMUNO            = "+StringManager.makeSQL(v_cmuno)+"\n ");
			sql.append(" AND A.POLLNO           = "+v_pollno+"\n ");
			sql.append(" AND A.DEL_FG           = 'N'\n ");

			ls = connMgr.executeQuery(sql.toString());
			while (ls.next()) {
				dbox = ls.getDataBox();
				list1.add(dbox);
			}
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			sql.setLength(0);

			//�����׸�
			sql.append(" SELECT  CMUNO, POLLNO, QUESTNO, NEED_QUESTION\n ");
			sql.append("         , NEED_FG, RE_GEN_FG, RE_SPE_FG, ITEM_CNT\n ");
			sql.append("         , REGISTER_USERID , REGISTER_DTE\n ");
			sql.append("         , MODIFIER_USERID , MODIFIER_DTE\n ");
			sql.append(" FROM    TZ_CMUPOLLQUEST\n ");
			sql.append(" WHERE   CMUNO   = '"+v_cmuno+"'\n ");
			sql.append(" AND     POLLNO  = '"+v_pollno+"'\n ");


			ls = connMgr.executeQuery(sql.toString());
			while (ls.next()) {
				dbox = ls.getDataBox();
				list2.add(dbox);
			}
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			sql.setLength(0);

			//���׸�
			sql.append(" SELECT  CMUNO, POLLNO, QUESTNO, FIELDNO, FIELD_NAME\n ");
			sql.append("         , POLL_CNT, ETC_FG, ETC_NM, REGISTER_USERID\n ");
			sql.append("         , REGISTER_DTE, MODIFIER_USERID, MODIFIER_DTE\n ");
			sql.append(" FROM    TZ_CMUPOLLFIELD\n ");
			sql.append(" WHERE   CMUNO   = '"+v_cmuno+"'\n ");
			sql.append(" AND     POLLNO  = '"+v_pollno+"'\n ");

			ls = connMgr.executeQuery(sql.toString());
			while (ls.next()) {
				dbox = ls.getDataBox();
				list3.add(dbox);
			}
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			sql.setLength(0);

			//��ǥ�׸�
			sql.append(" SELECT  CMUNO, POLLNO, QUESTNO, USERID, SEL_TEXT\n ");
			sql.append("         , QUESTION, SHOT_TEXT, REGISTER_DTE\n ");
			sql.append(" FROM    TZ_CMUPOLLTICKET\n ");
			sql.append(" WHERE   CMUNO   = '"+v_cmuno+"'\n ");
			sql.append(" AND     POLLNO  = '"+v_pollno+"'\n ");

			ls = connMgr.executeQuery(sql.toString());
			while (ls.next()) {
				dbox = ls.getDataBox();
				list4.add(dbox);
			}
			list.add(list1);
			list.add(list2);
			list.add(list3);
			list.add(list4);

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
	 * Ŀ�´�Ƽ Ȩ���� ��ȸ
	 * @param box          receive from the form object and session
	 * @return ArrayList   Ŀ�´�Ƽ Ȩ���� ��ȸ
	 * @throws Exception
	 */
	public ArrayList selectPollIndexView(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();

		ArrayList           list3   = new ArrayList();
		ArrayList           list4   = new ArrayList();

		String              sql     = "";
		DataBox             dbox    = null;

		String v_pollno     = "";
		String v_cmuno = box.getString("p_cmuno");
		String s_userid = box.getSession("userid");


		try {
			connMgr = new DBConnectionManager();

			sql ="\n  select * from ( select rownum rnum,  a.pollno    pollno"
				+"\n    from tz_cmupollmst a,tz_member b,tz_cmuusermst c "
				+"\n         ,tz_cmugrdcode  e  "
				+"\n   where a.register_userid  = b.userid "
				+"\n     and a.cmuno            = c.cmuno "
				+"\n    and b.grcode  = "+StringManager.makeSQL(box.getSession("tem_grcode"))+"\n "
				+"\n     and a.register_userid  = c.userid "
				+"\n     and c.cmuno            = e.cmuno  "
				+"\n     and c.grade            = e.grcode "
				+"\n     and a.cmuno            = '"+v_cmuno+"'"
				//+"\n     and a.fdte             <= '"+FormatDate.getDate("yyyyMMdd")+"'"
				//+"\n     and a.tdte             >= '"+FormatDate.getDate("yyyyMMdd")+"'"
				+"\n     and a.del_fg           = 'N'"
				+"\n   order by a.tdte desc, a.pollno desc ) where rnum < 2";


			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				v_pollno = ls.getString("pollno");
			}

			if(v_pollno == null || v_pollno.equals("")) {
				if(ls != null) { try { ls.close(); }catch (Exception e) {} }
				if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
				return null;
			}

			//����Ÿ
			sql ="\n  select   a.cmuno   cmuno  , a.pollno    pollno   , a.title   title  , a.fdte fdte"
				+"\n         , a.tdte tdte, a.tot_poll_cnt tot_poll_cnt, a.background background,a.str_fg str_fg "
				+"\n         , a.register_userid register_userid, a.register_dte register_dte"
				+"\n         ,b.userid          userid                 ,b.resno        resno "
				+"\n         ,c.kor_name        name                   ,c.email        email "
				+"\n         ,b.deptnam         deptnam                ,b.jikupnm      jikupnm "
				+"\n         ,b.jikwinm         jikwinm                ,c.tel          tel"
				+"\n         ,c.mobile          mobile                 ,c.office_tel   office_tel "
				+"\n         ,c.wk_area         wk_area                ,c.grade        grade "
				+"\n         ,e.kor_nm          grade_nm               "
				+"\n         , (select count(*) from tz_cmupollticket where cmuno = a.cmuno and pollno = a.pollno and userid = '"+s_userid+"') apply_fg"
				+"\n    from tz_cmupollmst a,tz_member b,tz_cmuusermst c "
				+"\n         ,tz_cmugrdcode  e  "
				+"\n   where a.register_userid  = b.userid "
				+"\n    and b.grcode  = "+StringManager.makeSQL(box.getSession("tem_grcode"))
				+"\n     and a.cmuno            = c.cmuno "
				+"\n     and a.register_userid  = c.userid "
				+"\n     and c.cmuno            = e.cmuno  "
				+"\n     and c.grade            = e.grcode "
				+"\n     and a.cmuno            = '"+v_cmuno+"'"
				+"\n     and a.pollno            = '"+v_pollno+"'"
				+"\n     and a.del_fg           = 'N'"
				;

			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);
			}


			//�����׸�
			sql ="\n  select   cmuno, pollno, questno, need_question "
				+"\n        , need_fg, re_gen_fg, re_spe_fg, item_cnt "
				+"\n        , register_userid , register_dte  "
				+"\n        , modifier_userid , modifier_dte"
				+"\n    from tz_cmupollquest "
				+"\n   where cmuno            = '"+v_cmuno+"'"
				+"\n     and pollno           = '"+v_pollno+"'"
				;
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				dbox = ls.getDataBox();
			}
			list.add(dbox);


			//���׸�
			sql ="\n  select   cmuno, pollno, questno, fieldno, field_name, poll_cnt "
				+"\n         , etc_fg, etc_nm, register_userid, register_dte, modifier_userid, modifier_dte "
				+"\n    from tz_cmupollfield "
				+"\n   where cmuno            = '"+v_cmuno+"'"
				+"\n     and pollno           = '"+v_pollno+"'"
				;
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				dbox = ls.getDataBox();
				list3.add(dbox);
			}
			list.add(list3);

			//��ǥ�׸�
			sql ="\n  select   cmuno, pollno, questno, userid, sel_text, question, shot_text, register_dte "
				+"\n    from tz_cmupollticket "
				+"\n   where cmuno            = '"+v_cmuno+"'"
				+"\n     and pollno           = '"+v_pollno+"'"
				;
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				dbox = ls.getDataBox();
				list4.add(dbox);
			}
			list.add(list4);

		}
		catch (Exception ex) {
			//             ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}


	/**
	 * ��ǥ������ȸ
	 * @param box          receive from the form object and session
	 * @return ArrayList   ��ǥ������ȸ
	 * @throws Exception
	 */
	public int selectPollTicketCnt(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;

		String              sql     = "";

		String v_cmuno         = box.getString("p_cmuno");
		String v_pollno        = box.getString("p_pollno");
		String s_userid             = box.getSession("userid");

		int    v_rowcnt        =0;
		try {
			connMgr = new DBConnectionManager();

			//��ǥ�׸�
			sql ="\n  select    count(*) cnt"
				+"\n    from tz_cmupollticket "
				+"\n   where cmuno            = '"+v_cmuno+"'"
				+"\n     and pollno           = '"+v_pollno+"'"
				+"\n     and userid           = '"+s_userid+"'"
				;

			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				v_rowcnt = ls.getInt(1);
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
		return v_rowcnt;
	}


	/**
	 * ��ǥ������ȸ
	 * @param box          receive from the form object and session
	 * @return ArrayList   ��ǥ������ȸ
	 * @throws Exception
	 */
	public int selectPollTickeIndextCnt(String v_cmuno,String v_pollno,String s_userid) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;

		String              sql     = "";

		int    v_rowcnt        =0;
		try {
			connMgr = new DBConnectionManager();

			//��ǥ�׸�
			sql ="\n  select    count(*) cnt"
				+"\n    from tz_cmupollticket "
				+"\n   where cmuno            = '"+v_cmuno+"'"
				+"\n     and pollno           = '"+v_pollno+"'"
				+"\n     and userid           = '"+s_userid+"'"
				;
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				v_rowcnt = ls.getInt(1);
			}

		}
		catch (Exception ex) {

			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return v_rowcnt;
	}



	/**
	 * ��ǥ��ȣ��ȸ
	 * @param box          receive from the form object and session
	 * @return ArrayList   ��ǥ��ȣ��ȸ
	 * @throws Exception
	 */
	public int getPollno(String v_cmuno) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;

		String              sql     = "";

		int    v_pollno        =0;
		try {
			connMgr = new DBConnectionManager();

			sql  = " select max(pollno)  from tz_cmupollmst where cmuno ='"+v_cmuno+"'";
			ls = connMgr.executeQuery(sql);
			while (ls.next()) v_pollno= ls.getInt(1)+1;


		}
		catch (Exception ex) {

			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return v_pollno;
	}

	/**
	 * ����Ʈ
	 * @param box          receive from the form object and session
	 * @return ArrayList   ����Ʈ
	 * @throws Exception
	 */
	public ArrayList selectPollReplyList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();

		StringBuffer sql    	  	= new StringBuffer();
		DataBox             dbox    = null;

		String v_cmuno         = box.getString("p_cmuno");
		int    v_menuno        = box.getInt("p_menuno");
		String v_brdno         = box.getString("p_pollno");

		try {
			connMgr = new DBConnectionManager();

			sql.append(" select a.cmuno          ,a.menuno\n ");
			sql.append("       ,a.brdno          ,a.rplno\n ");
			sql.append("       ,a.content        ,a.userid\n ");
			sql.append("       ,a.register_dte   ,a.modifier_dte\n ");
			sql.append("       ,b.resno          ,b.name\n ");
			sql.append("       ,c.email          ,b.deptnam\n ");
			sql.append("       ,b.jikupnm        ,b.jikwinm\n ");
			sql.append("   from tz_cmuboardreplay a,tz_member b,tz_cmuusermst c\n ");
			sql.append("  where a.userid          = b.userid\n ");
			sql.append("    and a.cmuno           = c.cmuno\n ");
			sql.append("    and b.grcode  = ").append(StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n ");
			sql.append("    and a.userid          = c.userid\n ");
			sql.append("    and a.cmuno           = '").append(v_cmuno).append("'\n ");
			sql.append("    and a.menuno          = '").append(v_menuno).append("'\n ");
			sql.append("    and a.brdno           = '").append(v_brdno).append("'\n ");
			sql.append("  order by a.rplno desc");

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
	 * ��۵���ϱ�
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int insertBrdMemo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";

		int         isOk1       = 1;
		int         isOk2       = 1;
		int         v_rplno     = 0;

		String v_cmuno              = box.getString("p_cmuno");
		int    v_menuno             = box.getInt("p_menuno");
		int    v_brdno              = box.getInt("p_pollno");
		String s_userid             = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = "select isnull(max(rplno), 0) from tz_cmuboardreplay where cmuno = '"+v_cmuno+"' and menuno = '"+v_menuno+"' and brdno ="+v_brdno;
			ls = connMgr.executeQuery(sql);
			while (ls.next()) v_rplno = ls.getInt(1) + 1;

			sql  =" insert into tz_cmuboardreplay ( cmuno     , menuno,brdno          , rplno       , content   , userid"
				+"                       , register_dte , modifier_dte, del_fg)"
				+"               values  (?,?,?,?,?,?"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS')"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')"
				;
			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(1 , v_cmuno          );
			pstmt.setInt (2 , v_menuno      );
			pstmt.setInt   (3 , v_brdno       );
			pstmt.setInt   (4,  v_rplno        	);
			pstmt.setString(5, box.getString("p_rep_content" ));
			pstmt.setString(6, s_userid);
			isOk1 = pstmt.executeUpdate();

			if(isOk1 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk1*isOk2;
	}

	/**
	 * ��ۻ����ϱ�
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int deleteBrdMemo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";

		int         isOk1       = 1;
		int         isOk2       = 1;

		String v_cmuno              = box.getString("p_cmuno");
		int    v_menuno             = box.getInt("p_menuno");
		int    v_brdno              = box.getInt("p_pollno");
		int    v_rplno              = box.getInt("p_rplno");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = "delete  from tz_cmuboardreplay where cmuno = '"+v_cmuno+"' and menuno = '"+v_menuno+"' and brdno ="+v_brdno+" and rplno="+v_rplno;
			pstmt = connMgr.prepareStatement(sql);
			isOk1 = pstmt.executeUpdate();
			System.out.println("isOk1:"+isOk1);
			if(isOk1 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk1*isOk2;
	}
}

