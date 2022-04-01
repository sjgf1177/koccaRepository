/*
 * @(#)PostScriptBean.java
 *
 * Copyright(c) 2006, Jin-pil Chung
 * All rights reserved.
 */

package com.credu.course;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

public class PostScriptBean 
{
    private ConfigSet config;
    private int row;

    public PostScriptBean() 
    {
        try
        {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 교육후기 나의후기 목록  (2007-0070 0075 0076 0079차수 후기 이벤트용)
     * 
     * @param box
     * @return ArrayList
     * @throws Exception
     */
	public List selectMyList(RequestBox box) throws Exception 
	{
		DBConnectionManager connMgr = null;
        ListSet ls = null;

        ArrayList list = null;
        String sql = "";
	        
        try 
        {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            String v_grcode = box.getStringDefault("p_grcode", "N000002");
            String v_gyear = box.getStringDefault("p_gyear", "2007");
            String s_userid = box.getSession("userid");
            
            sql =
            	"\n  SELECT  a.subj, c.userid, NVL( d.seq, '') seq,  " +
            	"\n  	( select classname from tz_subjatt x  " +
            	"\n  	  where x.upperclass = a.upperclass and x.middleclass = a.middleclass and x.lowerclass = '000' ) classnm,  " +
            	"\n  	a.subjnm as subjnm,  " +
            	"\n  	case when d.seq is null then 'N' else 'Y' end as is_postscript  " +
            	"\n  FROM  " +
            	"\n  	tz_subj a,  " +
            	"\n  	tz_subjseq b,  " +
            	"\n  	tz_student c left outer join tz_postscript d on d.grcode = " + SQLString.Format(v_grcode) + " and c.subj = d.subj and c.userid = d.userid  " +
            	"\n  WHERE 1=1  " +
            	"\n  	AND a.subj=b.subj  " +
            	"\n  	AND b.subj=c.subj  " +
            	"\n  	AND b.year=c.year  " +
            	"\n  	AND b.subjseq=c.subjseq  " +
            	"\n  	AND b.grcode = " + SQLString.Format(v_grcode) +
            	"\n  	AND b.gyear = " + SQLString.Format(v_gyear) +
            	"\n  	AND b.grseq in ( '0070', '0075', '0076', '0079' ) " +
            	"\n  	AND c.userid = " + SQLString.Format(s_userid) +
            	"\n     AND a.subj not in ( 'T000099', 'T000100' )  ";

            ls = connMgr.executeQuery( sql );

            while ( ls.next() )
            {
                list.add( ls.getDataBox() );
            }
        }
        catch (Exception ex) 
        {
            ErrorManager.getErrorStackTrace( ex, box, sql );
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally 
        {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return list;
	}    

    /**
     * 교육후기 전체후기 목록  (2007-0070 0075 0076 0079차수 후기 이벤트용)
     * 
     * @param box
     * @return ArrayList
     * @throws Exception
     */
	public List selectAllList(RequestBox box)  throws Exception 
	{
		DBConnectionManager connMgr = null;
        ListSet ls = null;

        ArrayList list = null;
        String sql = "";
	        
        try 
        {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            String v_grcode = box.getStringDefault("p_grcode", "N000002");
            String v_gyear = box.getStringDefault("p_gyear", "2007");

            int v_pageno = box.getInt("p_pageno");
            v_pageno = v_pageno==0?1:v_pageno;

            sql =
            	"\n  SELECT  d.seq, a.subj, c.userid, m.name usernm,  " +
            	"\n  	( select classname from tz_subjatt x  " +
            	"\n  	  where x.upperclass = a.upperclass and x.middleclass = a.middleclass and x.lowerclass = '000' ) classnm,  " +
            	"\n  	a.subjnm as subjnm,  " +
            	"\n  	d.title  " +
            	"\n  FROM  " +
            	"\n  	tz_member m,  " +
            	"\n  	tz_subj a,  " +
            	"\n  	tz_subjseq b,  " +
            	"\n  	tz_student c,  " +
            	"\n                tz_postscript d  " +
            	"\n  WHERE 1=1  " +
            	"\n  	AND a.subj=b.subj  " +
            	"\n  	AND b.subj=c.subj  " +
            	"\n  	AND b.year=c.year  " +
            	"\n  	AND b.subjseq=c.subjseq  " +
            	"\n     AND d.grcode = b.grcode  " +
            	"\n     AND c.subj = d.subj   " +
            	"\n     AND c.userid = d.userid  " +
            	"\n     AND m.userid = c.userid  " +
            	"\n  	AND b.grcode = " + SQLString.Format(v_grcode) +
            	"\n  	AND b.gyear = " + SQLString.Format(v_gyear) +
            	"\n  	AND b.grseq in ( '0070', '0075', '0076', '0079' ) " +
            	"\n  	AND a.subj not in ( 'T000099', 'T000100' )  " +
            	"\n  ORDER BY seq desc  ";

            ls = connMgr.executeQuery( sql );

            String sql2 =
            	"\n  SELECT  count(*) cnt  " +
            	"\n  FROM    " +
            	"\n    	tz_subjseq b,  " +
            	"\n    	tz_student c,    " +
            	"\n                tz_postscript d    " +
            	"\n    WHERE 1=1    " +
            	"\n    	AND b.subj=c.subj    " +
            	"\n    	AND b.year=c.year    " +
            	"\n    	AND b.subjseq=c.subjseq    " +
            	"\n     AND d.grcode = b.grcode    " +
            	"\n     AND c.subj = d.subj     " +
            	"\n     AND c.userid = d.userid    " +
            	"\n  	AND b.grcode = " + SQLString.Format(v_grcode) +
            	"\n  	AND b.gyear = " + SQLString.Format(v_gyear) +
            	"\n  	AND b.grseq in ( '0070', '0075', '0076', '0079' ) ";
            	
			int total_row_count = BoardPaging.getTotalRow(connMgr, sql2);

            ls.setPageSize(row);          				    //     페이지당 row 갯수를 세팅한다
		    ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            
            DataBox dbox = null;
            while ( ls.next() )
            {
            	dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                
                list.add( dbox );
            }
        }
        catch (Exception ex) 
        {
            ErrorManager.getErrorStackTrace( ex, box, sql );
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally 
        {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return list;
	}

	public ArrayList selectList(RequestBox box) throws Exception
    {
		DBConnectionManager connMgr = null;
        ListSet ls = null;

        ArrayList list = null;
        String sql = "";
	        
        try 
        {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            String v_subj = box.getString("p_subj");

            int v_pageno = box.getInt("p_pageno");
            v_pageno = v_pageno==0?1:v_pageno;

            sql =
            	"\n  SELECT a.seq, a.title, a.userid,  " +
            	"\n         (SELECT name FROM TZ_MEMBER WHERE userid = a.userid ) usernm  " +
            	"\n  FROM TZ_POSTSCRIPT A  " +
            	"\n  WHERE SUBJ = " + SQLString.Format(v_subj);  
            	
            ls = connMgr.executeQuery( sql );

            String sql2 =
            	"\n  SELECT count(seq)    " +
            	"\n  FROM TZ_POSTSCRIPT   " +
            	"\n  WHERE SUBJ = " + SQLString.Format(v_subj);

            int total_row_count = BoardPaging.getTotalRow(connMgr, sql2);

            ls.setPageSize(row);          				    //     페이지당 row 갯수를 세팅한다
		    ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            
            DataBox dbox = null;
            while ( ls.next() )
            {
            	dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                
                list.add( dbox );
            }
        }
        catch (Exception ex) 
        {
            ErrorManager.getErrorStackTrace( ex, box, sql );
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally 
        {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return list;		
    }

	public DataBox select(RequestBox box) throws Exception
	{
		DBConnectionManager connMgr = null;
        ListSet ls = null;

        DataBox dbox = null;
        String sql = "";
	        
        try 
        {
            connMgr = new DBConnectionManager();

            String v_grcode = box.getString("p_grcode");
            String v_seq = box.getString("p_seq");
            String s_userid = box.getSession("userid");
            
            sql =
            	"\n  SELECT A.SEQ, A.GRCODE, A.SUBJ, B.SUBJNM, A.USERID, A.TITLE, A.CONTENTS, A.CNT, A.POINT, A.INDATE, A.LDATE  " +
            	"\n  FROM TZ_POSTSCRIPT A, TZ_SUBJ B  " +
            	"\n  WHERE A.SUBJ = B.SUBJ  " +
            	"\n  	AND A.GRCODE = " + SQLString.Format(v_grcode) +
            	"\n  	AND A.SEQ = " + SQLString.Format(v_seq);
            
            if ( box.getString("idCheck").equals("Y") ) {
            	sql += "\n  	AND USERID = " + SQLString.Format(s_userid);
            }

            ls = connMgr.executeQuery( sql );

            if ( ls.next() )
            {
                dbox = ls.getDataBox();
            }
        }
        catch (Exception ex) 
        {
            ErrorManager.getErrorStackTrace( ex, box, sql );
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally 
        {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return dbox;
	}

	public int insert(RequestBox box) throws Exception 
	{
		DBConnectionManager connMgr = null;
	    PreparedStatement pstmt = null;
	        
        String sql = "";
        int isOk = 0;

        try 
        { 
            connMgr = new DBConnectionManager();
            int v_nextSeq = getNextSeq( connMgr );
            
            int v_seq = box.getInt("p_seq");
            String v_grcode = box.getString("p_grcode");
            String v_subj = box.getString("p_subj");
            String v_title = box.getString("p_title");
            String v_contents = box.getString("p_contents");
            String v_point = box.getString("p_point");
            String s_userid = box.getSession("userid");

            String v_type = box.getString("p_process");
            
            if ( "insert".equals(v_type) ) {
	            // insert
	            sql =
	                "\n  insert into tz_postscript                                    				" +
	                "\n  ( seq, grcode, subj, userid, title, contents, cnt, point, indate, ldate )	" +
	                "\n  values ( ?, ?, ?, ?, ?, ?, 0, ?, to_char(sysdate,'YYYYMMDDHH24MISS'), to_char(sysdate,'YYYYMMDDHH24MISS') )";
	            
	            pstmt = connMgr.prepareStatement(sql);
	            
	            pstmt.setInt( 1, v_nextSeq );
	            pstmt.setString( 2, v_grcode );
	            pstmt.setString( 3, v_subj );
	            pstmt.setString( 4, s_userid );
	            pstmt.setString( 5, v_title );
	            pstmt.setString( 6, v_contents );
	            pstmt.setString( 7, v_point );
	                
	            isOk = pstmt.executeUpdate();
            } else if ( "update".equals(v_type) ) {
	            // update
	            sql =
	                "\n  update tz_postscript set title = ?, contents = ?, point = ?, ldate = to_char(sysdate,'YYYYMMDDHH24MISS')		" +
	                "\n  where grcode = ? and seq = ? and userid = ?  ";
	            
	            pstmt = connMgr.prepareStatement(sql);
	            
	            pstmt.setString( 1, v_title );
	            pstmt.setString( 2, v_contents );
	            pstmt.setString( 3, v_point );
	            pstmt.setString( 4, v_grcode );
	            pstmt.setInt( 5, v_seq );
	            pstmt.setString( 6, s_userid );

	            isOk = pstmt.executeUpdate();            	
	        } else if ( "adminUpdate".equals(v_type) ) {
	        	String s_gadmin = box.getSession("gadmin");
	        	
	        	if ( s_gadmin.equals("A") ) {
		        	// update
		        	sql =
		        		"\n  update tz_postscript set title = ?, contents = ?, point = ?, ldate = to_char(sysdate,'YYYYMMDDHH24MISS')		" +
		        		"\n  where grcode = ? and seq = ?  ";
		        	
		        	pstmt = connMgr.prepareStatement(sql);
		        	
		            pstmt.setString( 1, v_title );
		            pstmt.setString( 2, v_contents );
		            pstmt.setString( 3, v_point );
		            pstmt.setString( 4, v_grcode );
		            pstmt.setInt( 5, v_seq );
		        	
		        	isOk = pstmt.executeUpdate();
	        	}
	        }
            
            
        }
        catch ( Exception ex ) 
        { 
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage() );
        }
        finally 
        { 
            if ( pstmt != null ) { try { pstmt.close(); } catch ( Exception e ) { } }
            if ( connMgr != null ) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }

        return isOk; 
	}

	private int getNextSeq(DBConnectionManager connMgr) throws Exception {

		int nextSeq = 0;
		
        String sql =
        		"\n  SELECT NVL(MAX(SEQ)+1,0) nextSeq  " +
        		"\n  FROM TZ_POSTSCRIPT  ";
        
        ListSet ls = connMgr.executeQuery( sql );

        if ( ls.next() )
        {
            nextSeq = ls.getInt("nextSeq");
        }		
		
		return nextSeq;
	}

	public int delete(RequestBox box) throws Exception
	{
		DBConnectionManager connMgr = null;
	    PreparedStatement pstmt = null;
	        
        String sql = "";
        int isOk = 0;

        try 
        { 
            connMgr = new DBConnectionManager();
            
            String v_grcode = box.getString("p_grcode");
            int v_seq = box.getInt("p_seq");
            String s_userid = box.getSession("userid");
            
            String v_type = box.getString("p_process");

            if ( "delete".equals(v_type) ) {
	            // delete
	            sql =
	                "\n  delete from tz_postscript                    " +
	                "\n  where grcode = ? and seq = ? and userid = ?  ";
	            
	            pstmt = connMgr.prepareStatement(sql);
	            
	            pstmt.setString( 1, v_grcode );
	            pstmt.setInt( 2, v_seq );
	            pstmt.setString( 3, s_userid );
	                
	            isOk = pstmt.executeUpdate();
            } else if ( "adminDelete".equals(v_type) ) {
	        	String s_gadmin = box.getSession("gadmin");
	        	
	        	if ( s_gadmin.equals("A") ) {

	        		sql =
		                "\n  delete from tz_postscript     " +
		                "\n  where grcode = ? and seq = ?  ";

		            pstmt = connMgr.prepareStatement(sql);

		            pstmt.setString( 1, v_grcode );
		            pstmt.setInt( 2, v_seq );

		            isOk = pstmt.executeUpdate();
	        	}
            }
        }
        catch ( Exception ex ) 
        { 
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage() );
        }
        finally 
        { 
            if ( pstmt != null ) { try { pstmt.close(); } catch ( Exception e ) { } }
            if ( connMgr != null ) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }

        return isOk; 		
	}

	public boolean isPostscript(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
        ListSet ls = null;

        boolean isPostscript = false;
        String sql = "";
	        
        try 
        {
            connMgr = new DBConnectionManager();

            String v_subj = box.getString("p_subj");
            String s_userid = box.getSession("userid");
            
            sql =
            	
        		"\n  SELECT COUNT(*) cnt  " +
        		"\n  FROM TZ_STUDENT  " +
        		"\n  WHERE SUBJ = " + SQLString.Format(v_subj) +
        		"\n  	AND USERID = " + SQLString.Format(s_userid);

            ls = connMgr.executeQuery( sql );
            
            if ( ls.next() )
            {
                if ( ls.getInt("cnt") > 0 ) {
                	isPostscript = true;
                }
            }
        }
        catch (Exception ex) 
        {
            ErrorManager.getErrorStackTrace( ex, box, sql );
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally 
        {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

		return isPostscript;
	}
}
