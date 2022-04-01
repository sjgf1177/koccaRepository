package com.credu.course;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class SubjectRandomBean {

	/**
     * 전체과정 목록을 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList selectAllList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        
        String gubun = box.getStringDefault("gubun", "S");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            
            if(gubun.equals("S")){
            	sql.append("SELECT  A.SUBJ AS COURSE,          	\n");
            	sql.append("        A.SUBJNM AS COURSENM       	\n");
            	sql.append("  FROM  TZ_SUBJ A               	\n");
            	sql.append("    ,   TZ_GRSUBJ B             	\n");
            	sql.append(" WHERE  B.GRCODE= 'N000001'     	\n");
            	sql.append("   AND  A.ISUSE = 'Y'           	\n");
            	sql.append("   AND  A.SUBJ = B.SUBJCOURSE   	\n");
            	sql.append("   AND  A.SUBJ NOT IN (         	\n");
            	sql.append("        SELECT  COURSE          	\n");
            	sql.append("          FROM  TZ_SUBJECT_RANDOM	\n");
            	sql.append("         WHERE  GUBUN = '"+gubun+"' \n");
            	sql.append("        )							\n");
            	sql.append(" ORDER  BY A.SUBJNM             	\n");
            }else{
            	sql.append("SELECT A.SEQ AS COURSE, 			\n");
            	sql.append("       A.LECNM AS COURSENM			\n");
            	sql.append("  FROM TZ_GOLDCLASS A				\n");
            	sql.append(" WHERE USEYN = 'Y'					\n");
            	sql.append("   AND SEQ NOT IN (					\n");
            	sql.append("	   SELECT TO_NUMBER(COURSE)		\n");
            	sql.append("	     FROM TZ_SUBJECT_RANDOM		\n");
            	sql.append("	    WHERE GUBUN = '"+gubun+"'	\n");
            	sql.append("	   )							\n");
            	sql.append(" ORDER BY A.LECNM 					\n");
            }
            
            
            ls = connMgr.executeQuery(sql.toString());
            while (ls.next()) {
                list.add(ls.getDataBox());
            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }
    
    /**
     * 선택된과정 목록을 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList selectSubjectList(RequestBox box) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	ListSet ls = null;
    	ArrayList list = null;
    	StringBuffer sql = new StringBuffer();
    	
    	String gubun = box.getStringDefault("gubun", "S");
    	
    	try {
    		connMgr = new DBConnectionManager();
    		
    		list = new ArrayList();
    		
    		if(gubun.equals("S")){
    			sql.append("SELECT A.COURSE, B.SUBJNM AS COURSENM	\n");
    			sql.append("  FROM TZ_SUBJECT_RANDOM A				\n");
    			sql.append(" INNER JOIN TZ_SUBJ B					\n");
    			sql.append("    ON A.COURSE = B.SUBJ				\n");
    			sql.append(" WHERE A.GUBUN = '"+gubun+"'			\n");
    			sql.append(" ORDER BY B.SUBJNM						\n");
    		}else{
    			sql.append("SELECT A.COURSE, B.LECNM AS COURSENM	\n");
    			sql.append("  FROM TZ_SUBJECT_RANDOM A				\n");
    			sql.append(" INNER JOIN TZ_GOLDCLASS B				\n");
    			sql.append("    ON A.COURSE = TO_CHAR(B.SEQ)		\n");
    			sql.append(" WHERE A.GUBUN = '"+gubun+"'			\n");
    			sql.append(" ORDER BY B.LECNM						\n");
    		}
    		
    		ls = connMgr.executeQuery(sql.toString());
    		while (ls.next()) {
    			list.add(ls.getDataBox());
    		}
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		ErrorManager.getErrorStackTrace(ex);
    		throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
    	} finally {
    		if (ls != null) {
    			try {
    				ls.close();
    			} catch (Exception e) {
    			}
    		}
    		if (connMgr != null) {
    			try {
    				connMgr.freeConnection();
    			} catch (Exception e10) {
    			}
    		}
    	}
    	return list;
    }
    
    
    /**
     * 랜덤과정 목록을 저장한다. 삭제 후 등록
     * @param box
     * @return
     * @throws Exception
     */
    public int saveSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int resultCnt = 0;
        int index = 1;

        String gubun = box.getString("gubun");
        String subjArr[] = box.getStringArray("randomSubject");
        
        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            
            sql.append("DELETE  \n");
            sql.append("  FROM  TZ_SUBJECT_RANDOM \n");
            sql.append(" WHERE  GUBUN = ?  \n");
            
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(1, gubun);
            pstmt.executeUpdate();
            
            pstmt.close();
            pstmt = null;

            sql.setLength(0);
            sql.append("/* com.credu.course.SubjectRandomBean saveSubjectList (랜덤 과정목록 등록) */\n");
            sql.append("INSERT INTO  TZ_SUBJECT_RANDOM (   	\n");
            sql.append("        GUBUN      					\n");
            sql.append("    ,   COURSE      				\n");
            sql.append(") VALUES (          				\n");
            sql.append("        ?           				\n");
            sql.append("    ,   ?           				\n");
            sql.append(")                   				\n");
            
            pstmt = connMgr.prepareStatement(sql.toString());

            for ( int i = 0; i < subjArr.length; i++ ) {
                index = 1;
                pstmt.setString(index++, gubun);
                pstmt.setString(index++, subjArr[i]);
                
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            resultCnt = 1;
            connMgr.commit();
        } catch (Exception ex) {
        	resultCnt = 0;
        	connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return resultCnt;
    }
}
