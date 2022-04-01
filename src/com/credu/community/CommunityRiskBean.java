package com.credu.community;

import com.credu.library.*;
import com.dunet.common.util.StringUtil;
import com.dunet.common.util.UploadUtil;
import com.credu.library.RequestBox;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010. 7. 8
 * Time: 오후 1:52:16
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unchecked")
public class CommunityRiskBean {
    private ConfigSet config;
    private int row;

    public CommunityRiskBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
	}

    @SuppressWarnings("unchecked")
    public ArrayList selectList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls      = null;
		ArrayList           list    = new ArrayList();
		String sql    	   		= "";
		StringBuffer headSql  	= new StringBuffer();
		StringBuffer bodySql   	= new StringBuffer();
		String count_sql  = "";
        String order_sql  = "";

		DataBox dbox    = null;

		String 			v_search     = box.getString("p_search");
		String 			v_searchtext = box.getString("p_searchtext");
        int v_pageno = box.getInt("p_pageno");

		try {
			connMgr = new DBConnectionManager();

			headSql.append(" select  a.*,b.name,(select count(*) from tz_riskmanagerment z where a.seq=z.parentseq and z.gubun!=0) as reply   \n ");
			bodySql.append("   from tz_riskmanagerment a,tz_member b                   \n ");
			bodySql.append("  where a.userid          = b.userid                      \n ");
            bodySql.append("  and  b.grcode='"+box.getSession("tem_grcode")+"'");
            bodySql.append("  and  a.gubun=0 ");

            if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				if (v_search.equals("title")) {                          //    제목으로 검색할때
					bodySql.append(" AND TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
				} else if (v_search.equals("contents")) {                //    내용으로 검색할때
					bodySql.append(" AND CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
				}
			}

            order_sql=" order by seq desc,gubun";
			sql= headSql.toString()+ bodySql.toString()+order_sql;

			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ bodySql;
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(row);          				   // 		페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.
			int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(total_page_count));
				dbox.put("d_rowcount", new Integer(row));

				list.add(dbox);

			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

    @SuppressWarnings("unchecked")
    public DataBox view(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls      = null;
		ArrayList           list    = new ArrayList();
		String sql    	   		= "";
		StringBuffer headSql  	= new StringBuffer();
		StringBuffer bodySql   	= new StringBuffer();
        String sessionId=box.getSession("userid");
		String orderSql  = "";
		DataBox dbox    = null;
		String v_seq         = box.getString("p_seq");
        Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();

		try {
			connMgr = new DBConnectionManager();

			headSql.append(" select  a.*,b.name,c.REALFILE, c.SAVEFILE, c.FILESEQ   \n ");
			bodySql.append("   from tz_riskmanagerment a,tz_member b,tz_riskmanagermentfile c \n ");
			bodySql.append("  where a.userid = b.userid \n ");
            bodySql.append("  and  a.seq=c.seq(+)  \n ");
            bodySql.append("  and  b.grcode='"+box.getSession("tem_grcode")+"'");
            bodySql.append("  and  a.seq='"+v_seq+"' \n");
            bodySql.append("  and  a.gubun=0 \n");

			sql= headSql.toString()+ bodySql.toString()+ orderSql;
			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();
//				list.add(dbox);
                if(!dbox.getString("d_realfile").equals(""))
                {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }
            }

			if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);

            if(!sessionId.equals(dbox.getString("d_userid")))
            {
                sql=" UPDATE tz_riskmanagerment SET hitnum = hitnum + 1 WHERE seq = " + v_seq;
                connMgr.executeUpdate(sql);
            }
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return dbox;
	}

     @SuppressWarnings("unchecked")
    public ArrayList viewRePly(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls      = null;
		ArrayList           list    = new ArrayList();
		String sql    	   		= "";
		StringBuffer headSql  	= new StringBuffer();
		StringBuffer bodySql   	= new StringBuffer();
		String orderSql  = "";
		DataBox dbox    = null;

		try {
			connMgr = new DBConnectionManager();

			headSql.append(" select  a.*,b.name,c.REALFILE, c.SAVEFILE, c.FILESEQ   \n ");
			bodySql.append("   from tz_riskmanagerment a,tz_member b,tz_riskmanagermentfile c \n ");
			bodySql.append("  where a.userid = b.userid \n ");
            bodySql.append("  and  a.seq=c.seq(+)  \n ");
            bodySql.append("  and  b.grcode='"+box.getSession("tem_grcode")+"' \n");
            bodySql.append("  and  a.parentseq='"+box.getString("p_seq")+"' \n");
            bodySql.append("  and  a.gubun!=0 \n");
            bodySql.append("  order by a.ldate \n");

			sql= headSql.toString()+ bodySql.toString()+ orderSql;
			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

    @SuppressWarnings("unchecked")
	public int update(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls 					= null;
		PreparedStatement pstmt1 	= null;

		StringBuffer sql  			= new StringBuffer();

		int isOk  = 1;
		int isOk1 = 1;
		int isOk2 = 1;
		int isOk3 = 1;
		int v_cnt = 0;

		String v_title 		= StringUtil.removeTag(box.getString("p_title"));
		String v_contents 	=  StringUtil.removeTag(box.getString("p_content"));
		String s_userid 	= box.getSession("userid");
		String v_isopen  	= box.getStringDefault("p_isopen", "Y");
        int v_gubun        =  box.getInt("p_gubun");
        int v_parentseq    =  box.getInt("p_parentseq");
        int v_seq    =  box.getInt("p_seq");

		try {

			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
			sql.setLength(0);

            sql.append(" update tz_riskmanagerment set title=?,contents=? where seq=?\n");

			pstmt1 = connMgr.prepareStatement(sql.toString());

			pstmt1.setString   (1, v_title);
            pstmt1.setCharacterStream(2,  new StringReader(v_contents), v_contents.length());
            pstmt1.setInt   (3, v_seq);

			isOk1 = pstmt1.executeUpdate();     			//      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.
			isOk2 = UploadUtil.fnRegisterAttachFile(box);
			isOk3 = this.insertUpFile(connMgr,v_seq,  box);

			if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
				isOk = 1;
			} else {
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
				isOk = 0;
			}

		} catch (Exception ex) {
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}

		return isOk;
	}

    @SuppressWarnings("unchecked")
	public int updateRePly(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls 					= null;
		PreparedStatement pstmt1 	= null;

		StringBuffer sql  			= new StringBuffer();

		int isOk  = 1;
		int isOk1 = 1;
		int isOk2 = 1;
		int isOk3 = 1;
		int v_cnt = 0;

		String v_title 		= box.getString("p_title");
		String v_contents 	=  box.getString("p_replycontents");
		String s_userid 	= box.getSession("userid");
		String v_isopen  	= box.getStringDefault("p_isopen", "Y");
        int v_gubun        =  box.getInt("p_gubun");
        int v_parentseq    =  box.getInt("p_replyseq");
        int v_seq    =  box.getInt("p_seq");

		try {

			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

            sql.append(" update tz_riskmanagerment set title=?,contents=? where seq=?\n");

			pstmt1 = connMgr.prepareStatement(sql.toString());

			pstmt1.setString   (1, v_title);
            pstmt1.setCharacterStream(2,  new StringReader(v_contents), v_contents.length());
            pstmt1.setInt   (3, v_parentseq);

			isOk1 = pstmt1.executeUpdate();     			//      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.
			isOk2 = UploadUtil.fnRegisterAttachFile(box);
			isOk3 = this.insertUpFile(connMgr,v_seq,  box);

			if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
				isOk = 1;
			} else {
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
				isOk = 0;
			}

		} catch (Exception ex) {
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}

		return isOk;
	}

    @SuppressWarnings("unchecked")
	public int delete(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt=null;
		int isOk =0;
		int i = 0;
		ListSet ls = null;
		String sql  = "";
		String v_userid		= box.getSession("userid");
		String v_seq		= box.getString("p_seq");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = " delete from tz_riskmanagerment ";
			sql += " Where seq = ? or parentseq=?";

			pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_seq);
            pstmt.setString(2, v_seq);

            isOk = pstmt.executeUpdate();

            if(isOk>0)
                deleteFile(connMgr,Integer.parseInt(v_seq),box);
            
		}catch(Exception ex) {
			isOk = 0;
			connMgr.rollback();
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if (isOk > 0) {connMgr.commit();}
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

    @SuppressWarnings("unchecked")
	public int deleteRePly(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt=null;
		int isOk =0;
		int i = 0;
		ListSet ls = null;
		String sql  = "";
		String v_userid		= box.getSession("userid");
		String v_seq		= box.getString("p_replyseq");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = " delete from tz_riskmanagerment ";
			sql += " Where seq = ? ";

			pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_seq);

            isOk = pstmt.executeUpdate();

		}catch(Exception ex) {
			isOk = 0;
			connMgr.rollback();
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if (isOk > 0) {connMgr.commit();}
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

    @SuppressWarnings("unchecked")
	public int insert(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls 					= null;
		PreparedStatement pstmt1 	= null;

		StringBuffer sql  			= new StringBuffer();

		int isOk  = 1;
		int isOk1 = 1;
		int isOk2 = 1;
		int isOk3 = 1;
		int v_cnt = 0;

		String v_title 		= StringUtil.removeTag(box.getString("p_title"));
		String v_contents 	=  StringUtil.removeTag(box.getString("p_content"));
		String s_userid 	= box.getSession("userid");
		String v_isopen  	= box.getStringDefault("p_isopen", "Y");
        int v_gubun        =  box.getInt("p_gubun");
        int v_parentseq    =  box.getInt("p_parentseq");

		try {

			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql.append(" SELECT NVL(MAX(SEQ), 0)+1 FROM tz_riskmanagerment");

			ls = connMgr.executeQuery(sql.toString());

			ls.next();
			int v_seq = ls.getInt(1);
			ls.close();

			//////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
			sql.setLength(0);

			sql.append(" INSERT INTO tz_riskmanagerment(SEQ, GUBUN, PARENTSEQ,USERID, TITLE, CONTENTS, LDATE, SDATE, EDATE, HITNUM) \n");
			sql.append(" VALUES (?, ?, ?,?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), ?, ?, ?) ");

			pstmt1 = connMgr.prepareStatement(sql.toString());

			pstmt1.setInt   (1, v_seq);
            pstmt1.setInt   (2, v_gubun);
            pstmt1.setInt   (3, v_parentseq);
			pstmt1.setString(4, s_userid);
			pstmt1.setString(5, v_title);
			pstmt1.setCharacterStream(6,  new StringReader(v_contents), v_contents.length());
			pstmt1.setString(7, "");
			pstmt1.setString(8, "");
            pstmt1.setInt   (9, 0);

			isOk1 = pstmt1.executeUpdate();     			//      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.
			isOk2 = UploadUtil.fnRegisterAttachFile(box);
			isOk3 = this.insertUpFile(connMgr,v_seq,  box);

			if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
				isOk = 1;
			} else {
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
				isOk = 0;
			}

		} catch (Exception ex) {
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}

		return isOk;
    }

    @SuppressWarnings("unchecked")
	public int insertRePly(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls 					= null;
		PreparedStatement pstmt1 	= null;

		StringBuffer sql  			= new StringBuffer();

		int isOk  = 1;
		int isOk1 = 1;

		String v_contents 	=  box.getString("p_replycontents");
		String s_userid 	= box.getSession("userid");
        int v_gubun        =  1;
        int v_parentseq    =  box.getInt("p_seq");

		try {
            connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
            
            sql.append(" SELECT NVL(MAX(SEQ), 0)+1 FROM tz_riskmanagerment");

			ls = connMgr.executeQuery(sql.toString());

			ls.next();
			int v_seq = ls.getInt(1);
			ls.close();

			//////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
			sql.setLength(0);

			sql.append(" INSERT INTO tz_riskmanagerment (SEQ, GUBUN, PARENTSEQ,USERID,  CONTENTS, LDATE, SDATE, EDATE, HITNUM) ");
			sql.append(" VALUES (?, ?,?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), ?, ?, ?) ");

			pstmt1 = connMgr.prepareStatement(sql.toString());

            pstmt1.setInt   (1, v_seq);
            pstmt1.setInt   (2, v_gubun);
            pstmt1.setInt   (3, v_parentseq);
			pstmt1.setString(4, s_userid);
			pstmt1.setCharacterStream(5,  new StringReader(v_contents), v_contents.length());
			pstmt1.setString(6, "");
			pstmt1.setString(7, "");
            pstmt1.setInt   (8, 0);

			isOk1 = pstmt1.executeUpdate();     

			if(isOk1 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
				isOk = 1;
			} else {
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
				isOk = 0;
			}

		} catch (Exception ex) {
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}

		return isOk;
    }

    public	int	insertUpFile(DBConnectionManager connMgr, int p_seq,  RequestBox	box) throws	Exception {
		ListSet	ls = null;
		PreparedStatement pstmt2 = null;
		String sql = "";
		String sql2	= "";
		int	isOk2 =	1;
        DataBox dbox    = null;

		ArrayList<String> arySaveFileName  = (ArrayList)box.getObject("arySaveFileName");
		ArrayList<String> aryRealFileName = (ArrayList)box.getObject("aryRealFileName");

		try	{
            //----------------------	자료 삭제 하기 ----------------------------
            String [] v_savefile=box.getStringArray("p_savefile");
            sql	= "select * from	tz_riskmanagermentfile where seq = "+p_seq;
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
				dbox = ls.getDataBox();
                int p_fileseq=dbox.getInt("D_FILESEQ");
                String savefile=dbox.getString("d_savefile");
                int sw=0;
                for(int j=0;j<v_savefile.length;j++)
                {
                    if(savefile.equals(v_savefile[j]))
                        sw++;
                }
                if(sw==0)
                {
                    FileManager.deleteFile(savefile);
                    sql="delete from tz_riskmanagermentfile  where seq = ? and fileseq=? ";
                    pstmt2 = connMgr.prepareStatement(sql);
                    pstmt2.setInt   (1, p_seq);
                    pstmt2.setInt   (2, p_fileseq);
                    isOk2 =	pstmt2.executeUpdate();
                }
			}
            ls.close();
            //------------------------------------------------------------------------------------


            //----------------------	자료 번호 가져온다 ----------------------------
            sql	= "select NVL(max(fileseq),	0)+1 from	tz_riskmanagermentfile where seq = "+p_seq;
            ls = connMgr.executeQuery(sql);
            ls.next();
            int	v_fileseq =	ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------

			//////////////////////////////////	 파일 table	에 입력	 ///////////////////////////////////////////////////////////////////
			sql2 =	"insert	into tz_riskmanagermentfile(seq, fileseq, realfile, savefile,ldate)";
			sql2 +=	" values (?, ?, ?, ?,to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt2 = connMgr.prepareStatement(sql2);

			for(int	i =	0; i < arySaveFileName.size(); i++)	{
				if(	!arySaveFileName.get(i).equals(""))	{		//		실제 업로드	되는 파일만	체크해서 db에 입력한다
					pstmt2.setInt   (1, p_seq);
					pstmt2.setInt   (2, v_fileseq);
					pstmt2.setString(3,	aryRealFileName.get(i));
					pstmt2.setString(4,	arySaveFileName.get(i));
					isOk2 =	pstmt2.executeUpdate();
					v_fileseq++;
				}
			}
		}
		catch (Exception ex) {
			FileManager.deleteFile(arySaveFileName);		//	일반파일, 첨부파일 있으면 삭제..
			ErrorManager.getErrorStackTrace(ex,	box, sql2);
			throw new Exception("sql = " + sql2	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(ls != null) { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
		}
		return isOk2;
	}
    
    public	int	insertEventUpFile(DBConnectionManager connMgr, int p_seq,  RequestBox	box) throws	Exception {
		ListSet	ls = null;
		PreparedStatement pstmt2 = null;
		String sql = "";
		String sql2	= "";
		int	isOk2 =	1;
        DataBox dbox    = null;

		ArrayList<String> arySaveFileName  = (ArrayList)box.getObject("arySaveFileName");
		ArrayList<String> aryRealFileName = (ArrayList)box.getObject("aryRealFileName");

		try	{
            //----------------------	자료 삭제 하기 ----------------------------
            String [] v_savefile=box.getStringArray("p_savefile");
            sql	= "select * from	tz_riskmanagermentfile where seq = "+p_seq;
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
				dbox = ls.getDataBox();
                int p_fileseq=dbox.getInt("D_FILESEQ");
                String savefile=dbox.getString("d_savefile");
                int sw=0;
                for(int j=0;j<v_savefile.length;j++)
                {
                    if(savefile.equals(v_savefile[j]))
                        sw++;
                }
                if(sw==0)
                {
                    FileManager.deleteFile(savefile);
                    sql="delete from tz_riskmanagermentfile  where seq = ? and fileseq=? ";
                    pstmt2 = connMgr.prepareStatement(sql);
                    pstmt2.setInt   (1, p_seq);
                    pstmt2.setInt   (2, p_fileseq);
                    isOk2 =	pstmt2.executeUpdate();
                }
			}
            ls.close();
            //------------------------------------------------------------------------------------

			//////////////////////////////////	 파일 table	에 입력	 ///////////////////////////////////////////////////////////////////
			sql2 =	"insert	into tz_riskmanagermentfile(seq, fileseq, realfile, savefile,ldate)";
			sql2 +=	" values (?, ?, ?, ?,to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt2 = connMgr.prepareStatement(sql2);

			for(int	i =	0; i < arySaveFileName.size(); i++)	{
				if(	!arySaveFileName.get(i).equals(""))	{		//		실제 업로드	되는 파일만	체크해서 db에 입력한다
					pstmt2.setInt   (1, p_seq);
					pstmt2.setInt   (2, 77777);
					pstmt2.setString(3,	aryRealFileName.get(i));
					pstmt2.setString(4,	arySaveFileName.get(i));
					isOk2 =	pstmt2.executeUpdate();
				}
			}
		}
		catch (Exception ex) {
			FileManager.deleteFile(arySaveFileName);		//	일반파일, 첨부파일 있으면 삭제..
			ErrorManager.getErrorStackTrace(ex,	box, sql2);
			throw new Exception("sql = " + sql2	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(ls != null) { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
		}
		return isOk2;
	}

    public	int	deleteFile(DBConnectionManager connMgr, int p_seq,  RequestBox	box) throws	Exception {
		ListSet	ls = null;
		String sql = "";
		int	isOk2 =	1;
        DataBox dbox    = null;

		try	{
            //----------------------	자료 번호 가져온다 ----------------------------
            sql	= "select * from	tz_riskmanagermentfile where seq = "+p_seq;
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
				dbox = ls.getDataBox();
                String savefile=dbox.getString("d_savefile");
                
                FileManager.deleteFile(savefile);		//	일반파일, 첨부파일 있으면 삭제..
			}
		}
		catch (Exception ex) {
            isOk2=0;
			ErrorManager.getErrorStackTrace(ex,	box, sql);
			throw new Exception("sql = " + sql	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(ls != null) { try { ls.close(); } catch (Exception e) {}	}
		}
		return isOk2;
	}

    @SuppressWarnings("unchecked")
	public int eventInsert(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls 					= null;
		PreparedStatement pstmt1 	= null;
		String sql  			= "";

		int isOk  = 0;

        String gubun    	= box.getString("p_gubun_inja");
		String s_userid 	= box.getString("p_userid");
        String [] s_ans     =  box.getString("p_ans").split(",");

		try {

			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

            sql=(" INSERT INTO TZ_TMP201007_EVENT_ANS (gubun,userid, question, ans,ldate) \n");
            sql+=(" VALUES (?,?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')) ");

            for(int i=0;i<s_ans.length;i++)
            {
                pstmt1 = connMgr.prepareStatement(sql);

                pstmt1.setString(1, gubun);
                pstmt1.setString(2, s_userid);
                pstmt1.setString(3, Integer.toString(i+1));
                pstmt1.setString(4, s_ans[i]);

                isOk =	pstmt1.executeUpdate();
            }

			if(isOk > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			} else {
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}

		} catch (Exception ex) {
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}

		return isOk;
    }

    @SuppressWarnings("unchecked")
    public DataBox login(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls      = null;
		ArrayList           list    = new ArrayList();
		String sql    	   		= "";
        //String id=box.getString("id");
        String s_userid = box.getSession("s_userid");
        //String pwd=box.getString("pwd");
        String gubun=box.getString("p_gubun_inja");
		String orderSql  = "";
		DataBox dbox    = null;

		try {
			connMgr = new DBConnectionManager();

            sql="select a.*,nvl(b.userid,'') as val from tz_member a \n";
            sql+="left join  (select distinct userid from TZ_TMP201007_EVENT_ANS where gubun='"+gubun+"') b on a.userid=b.userid \n";
            sql+="where a.userid='"+s_userid+"'";

            if(s_userid.equals("lee1"))
                sql+=" and grcode='N000001' ";
            
			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();
            }
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return dbox;
	}

    @SuppressWarnings("unchecked")
	public int eventPresent(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls 					= null;
		PreparedStatement pstmt1 	= null;
		String sql  			= "";

		int isOk  = 0;

		String s_userid 	= box.getString("p_userid");
        String s_ans     =  box.getString("p_ans");

		try {

			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

            sql=(" INSERT INTO TZ_TMP201007_EVENT_ANS (userid, question, ans,ldate) \n");
            sql+=(" VALUES (?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')) ");

            pstmt1 = connMgr.prepareStatement(sql);

            pstmt1.setString(1, s_userid);
            pstmt1.setString(2, "99");
            pstmt1.setString(3, s_ans);

            isOk =	pstmt1.executeUpdate();

			if(isOk > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			} else {
				if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			}

		} catch (Exception ex) {
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}

		return isOk;
    }

    @SuppressWarnings("unchecked")
	public int eventReg20100716(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls 					= null;
		PreparedStatement pstmt 	= null;
		StringBuffer sql  			= new StringBuffer();
        DataBox dbox    = null;
        int isOk  = 0;
		int isOk1 = 0;
		int isOk2 = 0;
		int isOk3 = 0;
        int queryValue=-1;

		String s_userid 	= box.getString("id");        //아이디
        String s_job 	    = box.getString("job");       //취업기관
        String s_jobdate 	= box.getString("jobdate");   //취업일
        String s_people 	= box.getString("people");    //인사담당자
        String s_st1 	    = box.getString("st1");  // 휴대폰 회사
        String s_st2 	    = box.getString("st2");  // 휴대폰 번호
        String s_st3 	    = box.getString("st3");  // 휴대폰 국
        String s_st4 	    = box.getString("st4");        // 휴대폰 4자리번호
        String s_smsok 	    = box.getString("p_smsok");    // 개인정보 수집및 sms동의
        String s_department = box.getString("department"); // 부서
        String s_position 	= box.getString("position");   // 직급

		try {

			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
            ////////////////////////////////// 일련 번호를 구해온다 ///////////////////////////////////////////////////////////////////
			sql.append(" SELECT NVL(MAX(SEQ), 0)+1 FROM TZ_TMP201007_EVENT_REGJOB");

			ls = connMgr.executeQuery(sql.toString());

			ls.next();
			int v_seq = ls.getInt(1);
			ls.close();
			
            //////////////////////////////////응시 중복 여부를 체크한다 ///////////////////////////////////////////////////////////////////
			sql.setLength(0);
			
			sql.append("select count(*) from TZ_TMP201007_EVENT_REGJOB where userid='"+s_userid+"'");

			ls = connMgr.executeQuery(sql.toString());

			ls.next();
			int v_duplicateCheck = ls.getInt(1);
			ls.close();

            //////////////////////////////////회원 테이블에 아이디 존재 여부 확인 ///////////////////////////////////////////////////////////////////
			sql.setLength(0);
			
			sql.append(" SELECT COUNT(USERID) FROM TZ_MEMBER WHERE USERID = '"+s_userid+"' AND GRCODE = 'N000001' ");

			ls = connMgr.executeQuery(sql.toString());

			ls.next();
			int v_idCheck = ls.getInt(1);
			ls.close();

			//////////////////////////////////성공취업 이벤트 table 에 입력  ///////////////////////////////////////////////////////////////////
			sql.setLength(0);
			
			if(v_idCheck > 0 && v_duplicateCheck < 1){ //중복 응시를 안 했으며 회원인 사람
				
                sql.append(" INSERT INTO TZ_TMP201007_EVENT_REGJOB \n");
                sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'),?,?,?) ");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setString(1, s_userid);
                pstmt.setString(2, s_job);
                pstmt.setString(3, s_jobdate);
                pstmt.setString(4, s_people);
                pstmt.setString(5, s_st1);
                pstmt.setString(6, s_st2);
                pstmt.setString(7, s_st3);
                pstmt.setString(8, s_st4);
                pstmt.setString(9, s_smsok);
                pstmt.setString(10, s_department);
                pstmt.setString(11, s_position);
                pstmt.setInt(12, v_seq);

                isOk1 =	pstmt.executeUpdate();
                
                //파일 업로드
                isOk2 = UploadUtil.fnRegisterAttachFile(box);
    			isOk3 = this.insertEventUpFile(connMgr,v_seq,  box);
             
	            if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
					if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
					isOk = 1;
				} else {
					if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
					isOk = 0;
				}
            } else if (v_idCheck == 0){ // 회원이 아닌사람
            	isOk = 2;
            }

		} catch (Exception ex) {
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}

		return isOk;
    }
    
    @SuppressWarnings("unchecked")
	public int eventRegJob(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls 					= null;
		PreparedStatement pstmt 	= null;
		StringBuffer sql  			= new StringBuffer();
        DataBox dbox    = null;
        int isOk  = 0;
		int isOk1 = 0;
		int isOk2 = 0;
		int isOk3 = 0;
        int queryValue=-1;

		String s_email 	= box.getString("mailid");        //아이디
        String s_job 	    = box.getString("job");       //형태
        String s_sirname 	    = box.getString("sirname");       //형태
        String s_handphone 	    = box.getString("handphone");       //형태
        String s_company 	= box.getString("company");   //회사명
        String s_category 	= box.getString("category");    //업종
        String s_department = box.getString("department");  // 부서
        String s_position 	= box.getString("position");  // 직위
        String s_wish 	    = box.getString("wish");  // 직위
        String s_memo = "";

		try {

			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
            ////////////////////////////////// 일련 번호를 구해온다 ///////////////////////////////////////////////////////////////////
			sql.append(" SELECT NVL(MAX(SEQ), 0)+1 FROM TZ_TMP201205_REGJOB");

			ls = connMgr.executeQuery(sql.toString());

			ls.next();
			int v_seq = ls.getInt(1);
			ls.close();
			
            //////////////////////////////////응시 중복 여부를 체크한다 ///////////////////////////////////////////////////////////////////
			sql.setLength(0);
			
			sql.append("select count(*) from TZ_TMP201205_REGJOB where EAMIL ='"+s_email+"'");

			ls = connMgr.executeQuery(sql.toString());

			ls.next();
			int v_duplicateCheck = ls.getInt(1);
			ls.close();

           

			//////////////////////////////////성공취업 이벤트 table 에 입력  ///////////////////////////////////////////////////////////////////
			sql.setLength(0);
			
			if(v_duplicateCheck < 1){ //중복 응시를 안 했으며 회원인 사람
				
                sql.append(" INSERT INTO TZ_TMP201205_REGJOB \n");
                sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?,  TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'),?,?,?,?) ");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setString(1, s_email);
                pstmt.setString(2, s_job);
                pstmt.setString(3, s_company);
                pstmt.setString(4, s_category);
                pstmt.setString(5, s_department);
                pstmt.setString(6, s_memo);
                pstmt.setString(7, s_position);
                pstmt.setString(8, s_wish);
                pstmt.setInt(9, v_seq);
                pstmt.setString(10, s_sirname);
                pstmt.setString(11, s_handphone);

                isOk1 =	pstmt.executeUpdate();               
             
	            if(isOk1 > 0 ) {
					if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
					isOk = 1;
				} else {
					if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
					isOk = 0;
				}
            } 

		} catch (Exception ex) {
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}

		return isOk;
    }
}
