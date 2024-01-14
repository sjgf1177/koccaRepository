package com.credu.course;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import org.apache.commons.lang.StringUtils;

public class SubjectClassifyBean {

    /**
     * ���԰��� �з��� ����� ��ȸ�Ѵ�.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList selectOnlineClassifyList() throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append(" SELECT GUBUN_1 AS GUBUN1, GET_CODENM('0110', GUBUN_1) AS GUBUNNM1,	\n");
		    sql.append("        GUBUN_2 AS GUBUN2, GET_CODENM('0110', GUBUN_2) AS GUBUNNM2, \n");
		    sql.append("        GUBUN_3 AS GUBUN3, GET_CODENM('0110', GUBUN_3) AS GUBUNNM3, \n");
		    sql.append("        (SELECT COUNT(GUBUN_1)                                    	\n");
		    sql.append("           FROM TZ_SUBJHOMEGUBUN_MASTER M                    		\n");
		    sql.append("          WHERE M.GUBUN_1 = A.GUBUN_1) AS GUBUNCNT1,         		\n");
		    sql.append("        CASE WHEN GET_CODENM('0110', GUBUN_2) IS NOT NULL THEN 		\n");
		    sql.append(" 					(SELECT COUNT(GUBUN_1)                          \n");
		    sql.append("           			   FROM TZ_SUBJHOMEGUBUN_MASTER M               \n");
		    sql.append("          			  WHERE M.GUBUN_1 = A.GUBUN_1                   \n");
		    sql.append("           			    AND M.GUBUN_2 = A.GUBUN_2)					\n");
		    sql.append("			 ELSE 1 END AS GUBUNCNT2,         						\n");
		    sql.append("        (SELECT COUNT(GUBUN_1)                                    	\n");
		    sql.append("           FROM TZ_SUBJHOMEGUBUN M                           		\n");
		    sql.append("          WHERE M.GUBUN = 'GS'                                		\n");
		    sql.append("            AND M.GUBUN_1 = A.GUBUN_1                         		\n");
		    sql.append("            AND M.GUBUN_2 = A.GUBUN_2                         		\n");
		    sql.append("            AND M.GUBUN_3 = A.GUBUN_3) AS SUBJCNT,             		\n");
		    sql.append("        (SELECT COUNT(GUBUN_1)                                    	\n");
		    sql.append("           FROM TZ_GOLDHOMEGUBUN M                           		\n");
		    sql.append("          WHERE M.GUBUN = 'GC'                                		\n");
		    sql.append("            AND M.GUBUN_1 = A.GUBUN_1                         		\n");
		    sql.append("            AND M.GUBUN_2 = A.GUBUN_2                         		\n");
		    sql.append("            AND M.GUBUN_3 = A.GUBUN_3) AS GOLDCNT,             		\n");		    
		    sql.append("		(SELECT X.LEVEL_CODE 										\n"); 
		    sql.append("		   FROM TZ_CODE X 											\n");
		    sql.append("		  WHERE X.GUBUN = '0110' 									\n");
		    sql.append("			AND X.CODE  = A.GUBUN_1) AS LVCODE,						\n");            		
		    sql.append("		(SELECT CODENM 												\n");		                                    	
		    sql.append("		   FROM TZ_CODE M  											\n");                         				
		    sql.append("		  WHERE M.GUBUN  = '0121' 									\n");                              		
		    sql.append("			AND M.LEVELS = 1 										\n");
		    sql.append("			AND M.CODE   = (SELECT X.LEVEL_CODE 					\n");
		    sql.append("							  FROM TZ_CODE X 						\n");
		    sql.append("							 WHERE X.GUBUN = '0110' 				\n");
		    sql.append("							   AND X.CODE  = A.GUBUN_1)) AS LVCDNM  \n");	
		    sql.append("  FROM TZ_SUBJHOMEGUBUN_MASTER A                            		\n");
		    sql.append(" WHERE 1 = 1							                     		\n");
		    sql.append(" ORDER BY A.GUBUN_1, A.GUBUN_2, A.GUBUN_3                     		\n");
            
            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
     * ���԰��� �з��� ����� ��ȸ�Ѵ�.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectSubjClassifyList(String clsCd) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	ListSet ls = null;
    	ArrayList list = null;
    	StringBuffer sql = new StringBuffer();
    	DataBox dbox = null;
    	
    	try {
    		connMgr = new DBConnectionManager();
    		
    		list = new ArrayList();
    		
    		sql.append("/* com.credu.course.SubjectClassifyBean() selectSubjClassifyList (���԰��� �з��� ��� ��ȸ) */ \n");
    		sql.append("SELECT  A.CLS_CD                            \n");
    		sql.append("    ,   A.UPPER_CLS_CD                      \n");
    		sql.append("    ,   A.CLS_NM                            \n");
    		sql.append("    ,   A.USE_YN                            \n");
    		sql.append("    ,   A.SORT_ORDER                        \n");
    		sql.append("    ,   (                                   \n");
    		sql.append("        SELECT  COUNT(SUBJ)                 \n");
    		sql.append("          FROM  TZ_SUBJ_CLS_MNG B           \n");
    		sql.append("         WHERE  A.CLS_CD = B.CLS_CD(+)      \n");
    		sql.append("        ) AS CNT                            \n");
    		sql.append("  FROM  TZ_SUBJ_CLS_MASTER A                \n");
    		sql.append(" WHERE  A.UPPER_CLS_CD = '").append(clsCd).append("'  \n");
    		sql.append(" ORDER  BY A.UPPER_CLS_CD, A.SORT_ORDER     \n");
    		
    		ls = connMgr.executeQuery(sql.toString());
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			
    			list.add(dbox);
    		}
    		
    	} catch (Exception ex) {
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
     * �����з� �׸��� ����Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    public int registerSubjectClassify(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int resultCnt = 0;
        int index = 1;
        
        String upperClsCd = box.getString("upperClsCd");
        String clsNm = box.getString("clsNm");
        String useYn = box.getString("useYn");
        String regId = box.getSession("userid");
        int sortOrder = box.getInt("sortOrder");
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.course.SubjectClassifyBean registerSubjectClassify (�����з����� ���) */\n");
            sql.append("INSERT  INTO    TZ_SUBJ_CLS_MASTER (    \n");
            sql.append("        CLS_CD          \n");
            sql.append("    ,   UPPER_CLS_CD    \n");
            sql.append("    ,   CLS_NM          \n");
            sql.append("    ,   USE_YN          \n");
            sql.append("    ,   SORT_ORDER      \n");
            sql.append("    ,   REG_DT          \n");
            sql.append("    ,   REG_ID          \n");
            sql.append("    ,   MOD_DT          \n");
            sql.append("    ,   MOD_ID          \n");
            sql.append(") VALUES (              \n");
            sql.append("        (SELECT  NVL(MAX(CLS_CD), ?) + 1  FROM  TZ_SUBJ_CLS_MASTER WHERE  UPPER_CLS_CD = ?) \n");
            sql.append("    ,   ?               \n");
            sql.append("    ,   ?               \n");
            sql.append("    ,   ?               \n");
            sql.append("    ,   ?               \n");
            sql.append("    ,   SYSDATE         \n");
            sql.append("    ,   ?               \n");
            sql.append("    ,   SYSDATE         \n");
            sql.append("    ,   ?               \n");
            sql.append(")                       \n");
            
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, upperClsCd);
            pstmt.setString(index++, upperClsCd);
            pstmt.setString(index++, upperClsCd);
            pstmt.setString(index++, clsNm);
            pstmt.setString(index++, useYn);
            pstmt.setInt(index++, sortOrder);
            pstmt.setString(index++, regId);
            pstmt.setString(index++, regId);

            resultCnt = pstmt.executeUpdate();
            
            if ( resultCnt > 0 ) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
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
    
    /**
     * �����з� �׸��� ����Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    public int registerOnlineClassify(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	StringBuffer sql = new StringBuffer();
    	
    	int resultCnt = 0;
    	int index = 1;
    	
    	String upperClsCd = box.getString("upperClsCd");
    	String gubun1 = box.getString("gubun1");
    	String gubun2 = box.getStringDefault("gubun2", "S00");
    	String gubun3 = box.getStringDefault("gubun3", gubun2 + "00");
    	try {
    		connMgr = new DBConnectionManager();
    		connMgr.setAutoCommit(false);
    		
    		
    		sql.append("/* com.credu.course.SubjectClassifyBean registerOnlineClassify (�����з����� ���) */\n");
    		sql.append(" INSERT INTO TZ_SUBJHOMEGUBUN_MASTER (	\n");
			sql.append("     GUBUN_1, GUBUN_2, GUBUN_3, GUBUN_4	\n");
			sql.append(" )										\n");
    		sql.append(" VALUES(								\n");
    		sql.append(" 	?, ?, ?, 'F01'						\n");
    		sql.append(" )										\n");
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		
    		pstmt.setString(index++, gubun1);
    		pstmt.setString(index++, gubun2);
    		pstmt.setString(index++, gubun3);
    		
    		pstmt.executeUpdate();
    		
			connMgr.commit();
			resultCnt = 1;
    	} catch (Exception ex) {
    		connMgr.rollback();
    		ex.printStackTrace();
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

    /**
     * ���� �з� �׸��� �����Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    public int deleteSubjectClassify(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int resultCnt = 0;
        
        String clsCd = box.getString("clsCd");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.course.SubjectClassifyBean deleteSubjectClassify (�����з� ����) */\n");
            sql.append("DELETE  \n");
            sql.append(" FROM   TZ_SUBJ_CLS_MNG \n");
            sql.append(" WHERE  CLS_CD = ?          \n");
            
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(1, clsCd);

            pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;

            sql.setLength(0);
            sql.append("/* com.credu.course.SubjectClassifyBean deleteSubjectClassify (�����з� ����) */\n");
            sql.append("DELETE  \n");
            sql.append("  FROM  TZ_SUBJ_CLS_MASTER  \n");
            sql.append(" WHERE  CLS_CD = ?          \n");
            
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(1, clsCd);

            resultCnt = pstmt.executeUpdate();
            
            if ( resultCnt > 0 ) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
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
    /**
     * ���� �з� �׸��� �����Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    public int deleteOnlineClassify(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	StringBuffer sql = new StringBuffer();
    	
    	int resultCnt = 0;
    	
    	String gubun1 = box.getString("gubun1");
    	String gubun2 = box.getString("gubun2");
    	String gubun3 = box.getString("gubun3");
    	
    	try {
    		connMgr = new DBConnectionManager();
    		connMgr.setAutoCommit(false);
    		
    		sql.setLength(0);
    		sql.append("/* com.credu.course.SubjectClassifyBean deleteOnlineClassify (���԰��� ����) */\n");
    		sql.append("DELETE  						\n");
    		sql.append("  FROM TZ_SUBJHOMEGUBUN 		\n");
    		sql.append(" WHERE GUBUN   = 'GS'       	\n");
    		sql.append("   AND GUBUN_1 = ?          	\n");
    		sql.append("   AND GUBUN_2 = ?          	\n");
    		sql.append("   AND GUBUN_3 = ?          	\n");
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		
    		pstmt.setString(1, gubun1);
    		pstmt.setString(2, gubun2);
    		pstmt.setString(3, gubun3);
    		
    		pstmt.executeUpdate();
    		pstmt.close();
    		pstmt = null;
    		
    		sql.setLength(0);
    		sql.append("/* com.credu.course.SubjectClassifyBean deleteOnlineClassify (�������� ����) */\n");
    		sql.append("DELETE  						\n");
    		sql.append("  FROM TZ_GOLDHOMEGUBUN 		\n");
    		sql.append(" WHERE GUBUN   = 'GC'       	\n");
    		sql.append("   AND GUBUN_1 = ?          	\n");
    		sql.append("   AND GUBUN_2 = ?          	\n");
    		sql.append("   AND GUBUN_3 = ?          	\n");
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		
    		pstmt.setString(1, gubun1);
    		pstmt.setString(2, gubun2);
    		pstmt.setString(3, gubun3);
    		
    		pstmt.executeUpdate();
    		pstmt.close();
    		pstmt = null;
    		
    		sql.setLength(0);
    		sql.append("/* com.credu.course.SubjectClassifyBean deleteOnlineClassify (�����з� ����) */\n");
    		sql.append("DELETE  							\n");
    		sql.append("  FROM  TZ_SUBJHOMEGUBUN_MASTER  	\n");
    		sql.append(" WHERE  GUBUN_1 = ?          		\n");
    		sql.append("   AND  GUBUN_2 = ?          		\n");
    		sql.append("   AND  GUBUN_3 = ?          		\n");
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		
    		pstmt.setString(1, gubun1);
    		pstmt.setString(2, gubun2);
    		pstmt.setString(3, gubun3);
    		
    		pstmt.executeUpdate();
    		
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

    /**
     * ���� �з� �׸��� �����Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    public int updateSubjectClassify(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int resultCnt = 0;
        int index = 1;
        
        String clsNm = box.getString("clsNm");
        String clsCd = box.getString("clsCd");
        String useYn = box.getString("useYn");
        String sortOrder = box.getString("sortOrder");
        String regid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.course.SubjectClassifyBean updateSubjectClassify (�����з� ����) */\n");
            sql.append("UPDATE  TZ_SUBJ_CLS_MASTER  \n");
            sql.append("   SET  CLS_NM = ?          \n");
            sql.append("    ,   USE_YN = ?          \n");
            sql.append("    ,   SORT_ORDER = ?      \n");
            sql.append("    ,   MOD_DT = SYSDATE    \n");
            sql.append("    ,   MOD_ID = ?          \n");
            sql.append(" WHERE  CLS_CD = ?          \n");
            
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, clsNm);
            pstmt.setString(index++, useYn);
            pstmt.setString(index++, sortOrder);
            pstmt.setString(index++, regid);
            pstmt.setString(index++, clsCd);

            resultCnt = pstmt.executeUpdate();
            
            if ( resultCnt > 0 ) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
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
    
    /**
     * ���� �з� �׸��� �����Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    public int updateOnlineClassify(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	StringBuffer sql = new StringBuffer();
    	ListSet ls = null;
    	
    	int resultCnt = 0;
    	int index = 1;
    	int checkCount = 0;
    	
    	String gubun1 = box.getString("gubun1");
    	String gubun2 = box.getStringDefault("gubun2", "S00");
    	String gubun3 = box.getStringDefault("gubun3", gubun2 + "00");
    	String v_gubun1 = box.getString("p_gubun1");
    	String v_gubun2 = box.getString("p_gubun2");
    	String v_gubun3 = box.getString("p_gubun3");
    	
    	try {
    		connMgr = new DBConnectionManager();
    		connMgr.setAutoCommit(false);
    		
    		sql.setLength(0);
    		sql.append("SELECT COUNT(0) as CNT FROM TZ_SUBJHOMEGUBUN 	\n");
    		sql.append(" WHERE GUBUN   = 'GS'					\n");
    		sql.append("   AND GUBUN_1 = '"+v_gubun1+"'			\n");
    		sql.append("   AND GUBUN_2 = '"+v_gubun2+"'			\n");
    		sql.append("   AND GUBUN_3 = '"+v_gubun3+"'			\n");
    		
    		ls = connMgr.executeQuery(sql.toString());
    		while(ls.next()){
    			checkCount = ls.getInt("cnt");
    		}
    		
    		if(checkCount == 0 ){
    			
    			sql.setLength(0);
    			sql.append("/* com.credu.course.SubjectClassifyBean updateSubjectClassify (�����з� ����) */\n");
    			sql.append("UPDATE  TZ_SUBJHOMEGUBUN_MASTER  \n");
    			sql.append("   SET  GUBUN_1 = ?          \n");
    			sql.append("    ,   GUBUN_2 = ?          \n");
    			sql.append("    ,   GUBUN_3 = ?          \n");
    			sql.append(" WHERE  GUBUN_1 = ?          \n");
    			sql.append("   AND  GUBUN_2 = ?          \n");
    			sql.append("   AND  GUBUN_3 = ?          \n");
    			
    			pstmt = connMgr.prepareStatement(sql.toString());
    			
    			pstmt.setString(index++, gubun1);
    			pstmt.setString(index++, gubun2);
    			pstmt.setString(index++, gubun3);
    			pstmt.setString(index++, v_gubun1);
    			pstmt.setString(index++, v_gubun2);
    			pstmt.setString(index++, v_gubun3);
    			
    			pstmt.executeUpdate();
    			
    			resultCnt = 1;
    		}
    		
			connMgr.commit();
    		
    	} catch (Exception ex) {
    		resultCnt = 0;
    		connMgr.rollback();
    		ErrorManager.getErrorStackTrace(ex);
    		throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
    	} finally {
    		if (ls != null) {
    			try {
    				ls.close();
    			} catch (Exception e) {
    			}
    		}
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
    
    /**
     * ���԰��� �з��� ������ ��ȸ�Ѵ�.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public DataBox selectSubjClassify(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        
        String clsCd = box.getString("clsCd");

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.course.SubjectClassifyBean() selectSubjClassify (���԰��� �з��� ���� ��ȸ) */ \n");
            sql.append("SELECT  CLS_CD                  \n");
            sql.append("    ,   UPPER_CLS_CD            \n");
            sql.append("    ,   CLS_NM                  \n");
            sql.append("    ,   USE_YN                  \n");
            sql.append("    ,   SORT_ORDER              \n");
            sql.append("  FROM  TZ_SUBJ_CLS_MASTER      \n");
            sql.append(" WHERE  CLS_CD = '").append(clsCd).append("'  \n");


            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
            }

        } catch (Exception ex) {
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
        return dbox;
    }

    /**
     * B2C ��ü ���� ����� ��ȸ�Ѵ�. �ش� �з��� ��ϵ� ������ ��Ͽ��� ���ܵȴ�.
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectAllSubjectList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        String clsCd = box.getString("clsCd");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.course.SubjectClassifyBean() selectSubjClassify (B2C ��ü ���� ��� ��ȸ) */ \n");
            sql.append("SELECT  A.SUBJ                  \n");
            sql.append("    ,   A.SUBJNM                \n");
            sql.append("  FROM  TZ_SUBJ A               \n");
            sql.append("    ,   TZ_GRSUBJ B             \n");
            sql.append(" WHERE  B.GRCODE= 'N000001'     \n");
            sql.append("   AND  A.ISUSE = 'Y'           \n");
            sql.append("   AND  A.SUBJ = B.SUBJCOURSE   \n");
            sql.append("   AND  A.SUBJ NOT IN (         \n");
            sql.append("        SELECT  SUBJ            \n");
            sql.append("          FROM  TZ_SUBJ_CLS_MNG \n");
            sql.append("         WHERE  CLS_CD = ?      \n");
            sql.append("        )                       \n");
            sql.append(" ORDER  BY A.SUBJNM             \n");
            
            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setString(1, clsCd);

            ls = new ListSet(pstmt);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception ex) {
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
     * B2C ��ü ���� ����� ��ȸ�Ѵ�. �ش� �з��� ��ϵ� ������ ��Ͽ��� ���ܵȴ�.
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList onlineAllSubjectList(RequestBox box) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	ListSet ls = null;
    	ArrayList list = null;
    	StringBuffer sql = new StringBuffer();
    	DataBox dbox = null;
    	
    	try {
    		connMgr = new DBConnectionManager();
    		
    		list = new ArrayList();
    		
    		sql.append("/* com.credu.course.SubjectClassifyBean() selectSubjClassify (B2C ��ü ���� ��� ��ȸ) */ \n");
    		sql.append("SELECT  A.SUBJ                  \n");
    		sql.append("     ,  A.SUBJNM                \n");
    		sql.append("  FROM  TZ_SUBJ A               \n");
    		sql.append("     ,  TZ_GRSUBJ B             \n");
    		sql.append(" WHERE  B.GRCODE= 'N000001'     \n");
    		sql.append("   AND  A.ISUSE = 'Y'           \n");
    		sql.append("   AND  A.SUBJ = B.SUBJCOURSE   \n");
    		sql.append("   AND  A.SUBJ NOT IN (         \n");
    		sql.append("        SELECT  SUBJ            \n");
    		sql.append("          FROM  TZ_SUBJHOMEGUBUN\n");
    		sql.append("         WHERE  GUBUN = 'GS'    \n");	
    		sql.append("        )                       \n");
    		sql.append(" ORDER  BY A.SUBJNM             \n");
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		
    		ls = new ListSet(pstmt);
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			
    			list.add(dbox);
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
     * �з��� ��ϵ� ���� ����� ��ȸ�Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList classifiedSubjectList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        String clsCd = box.getString("clsCd");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.course.SubjectClassifyBean() classifiedSubjectList (�з��� ��ϵ� ���� ��� ��ȸ) */ \n");
            sql.append("SELECT  A.SUBJ                  \n");
            sql.append("    ,   A.SUBJNM                \n");
            sql.append("  FROM  TZ_SUBJ A               \n");
            sql.append("    ,   TZ_GRSUBJ B             \n");
            sql.append("    ,   TZ_SUBJ_CLS_MNG C       \n");
            sql.append(" WHERE  B.GRCODE= 'N000001'     \n");
            sql.append("   AND  A.ISUSE = 'Y'           \n");
            sql.append("   AND  C.CLS_CD = ?            \n");
            sql.append("   AND  A.SUBJ = B.SUBJCOURSE   \n");
            sql.append("   AND  A.SUBJ = C.SUBJ         \n");
            sql.append(" ORDER  BY A.SUBJNM             \n");
            
            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setString(1, clsCd);
            
            ls = new ListSet(pstmt);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
            

        } catch (Exception ex) {
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
     * �з��� ��ϵ� ���� ����� ��ȸ�Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList onlineSubjectList(RequestBox box) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	ListSet ls = null;
    	ArrayList list = null;
    	StringBuffer sql = new StringBuffer();
    	DataBox dbox = null;
    	String gubun1 = box.getString("gubun1");
    	String gubun2 = box.getString("gubun2");
    	String gubun3 = box.getString("gubun3");
    	
    	try {
    		connMgr = new DBConnectionManager();
    		
    		list = new ArrayList();
    		
    		sql.append("/* com.credu.course.SubjectClassifyBean() onlineSubjectList (�з��� ��ϵ� ���� ��� ��ȸ) */ \n");
    		sql.append("SELECT A.SUBJ, B.SUBJNM								\n");
    		sql.append("     , (SELECT C.LVCODE 							\n");
    		sql.append("		  FROM TZ_SUBJHOMEGUBUN_LEVEL C 			\n");
    		sql.append("		 WHERE C.SUBJ      = A.SUBJ 				\n");
    		sql.append("		   AND C.GUBUNCODE = A.GUBUN_3) AS LVCODE	\n");
    		sql.append("  FROM TZ_SUBJHOMEGUBUN A							\n");
    		sql.append(" INNER JOIN TZ_SUBJ B ON A.SUBJ = B.SUBJ			\n");
    		sql.append(" WHERE A.GUBUN   = 'GS'								\n");
    		sql.append("   AND A.GUBUN_1 = ?								\n");
    		sql.append("   AND A.GUBUN_2 = ?								\n");
    		sql.append("   AND A.GUBUN_3 = ?								\n");
    		sql.append(" ORDER BY B.SUBJNM									\n");
    		
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		pstmt.setString(1, gubun1);
    		pstmt.setString(2, gubun2);
    		pstmt.setString(3, gubun3);
    		
    		ls = new ListSet(pstmt);
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			
    			list.add(dbox);
    		}
    		
    		
    	} catch (Exception ex) {
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
     * �з��� ���� ����� �����Ѵ�. ���� �� ���
     * @param box
     * @return
     * @throws Exception
     */
    public int saveSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int resultCnt[] = null;
        int index = 1;

        String clsCd = box.getString("clsCd");
        String subjArr[] = box.getStringArray("classifedSubject");
        String regId = box.getSession("userid");
        
        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            
            sql.append("DELETE  \n");
            sql.append("  FROM  TZ_SUBJ_CLS_MNG \n");
            sql.append(" WHERE  CLS_CD = ?  \n");
            
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(1, clsCd);
            pstmt.executeUpdate();
            
            pstmt.close();
            pstmt = null;

            sql.setLength(0);
            sql.append("/* com.credu.course.SubjectClassifyBean saveSubjectList (�з��� ������� ���) */\n");
            sql.append("INSERT              \n");
            sql.append("  INTO  TZ_SUBJ_CLS_MNG (   \n");
            sql.append("        CLS_CD      \n");
            sql.append("    ,   SUBJ        \n");
            sql.append("    ,   REG_DT      \n");
            sql.append("    ,   REG_ID      \n");
            sql.append("    ,   MOD_DT      \n");
            sql.append("    ,   MOD_ID      \n");
            sql.append(") VALUES (          \n");
            sql.append("        ?           \n");
            sql.append("    ,   ?           \n");
            sql.append("    ,   SYSDATE     \n");
            sql.append("    ,   ?           \n");
            sql.append("    ,   SYSDATE     \n");
            sql.append("    ,   ?           \n");
            sql.append(")                   \n");
            
            pstmt = connMgr.prepareStatement(sql.toString());

            for ( int i = 0; i < subjArr.length; i++ ) {
                index = 1;
                pstmt.setString(index++, clsCd);
                pstmt.setString(index++, subjArr[i]);
                pstmt.setString(index++, regId);
                pstmt.setString(index++, regId);
                
                pstmt.addBatch();
            }

            resultCnt = pstmt.executeBatch();
            
            if ( resultCnt.length > 0 ) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
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
        return resultCnt.length;
    }
    
    /**
     * �з��� ���� ����� �����Ѵ�. ���� �� ���
     * @param box
     * @return
     * @throws Exception
     */
    public int saveOnlineList(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
    	PreparedStatement sPstmt = null;
    	PreparedStatement sPstmt2 = null;
    	PreparedStatement gPstmt = null;
    	PreparedStatement gPstmt2 = null;    	
    	StringBuffer sql = new StringBuffer();
    	
    	int sResultCnt[] = null;
    	int sResult = 0;
    	int gResultCnt[] = null;
    	int gResult = 0;
    	int index = 1;
    	
    	String gubun1 = box.getString("gubun1");
    	String gubun2 = box.getString("gubun2");
    	String gubun3 = box.getString("gubun3");
    	String subjArr[] = box.getStringArray("classifedSubject");
    	String goldArr[] = box.getStringArray("classifedGoldClass");
    	String regId = box.getSession("userid");
    	
    	try {
    		
    		connMgr = new DBConnectionManager();
    		connMgr.setAutoCommit(false);
    		
    		sql.append("DELETE  					\n");
    		sql.append("  FROM  TZ_SUBJHOMEGUBUN 	\n");
    		sql.append(" WHERE  GUBUN = 'GS'  		\n");
    		sql.append("   AND  GUBUN_1 = ?  		\n");
    		sql.append("   AND  GUBUN_2 = ?  		\n");
    		sql.append("   AND  GUBUN_3 = ?  		\n");
    		
    		sPstmt = connMgr.prepareStatement(sql.toString());
    		
    		sPstmt.setString(1, gubun1);
    		sPstmt.setString(2, gubun2);
    		sPstmt.setString(3, gubun3);
    		
    		sResult = sPstmt.executeUpdate();
    		
    		if(subjArr != null){
    			sPstmt.close();
    			sPstmt = null;
	    		
	    		sql.setLength(0);
	    		sql.append("/* com.credu.course.SubjectClassifyBean saveOnlineList (�з��� ������� ���) */\n");
	    		sql.append("INSERT INTO TZ_SUBJHOMEGUBUN (   	\n");
	    		sql.append("        GUBUN      					\n");
	    		sql.append("    ,   GUBUN_1     				\n");
	    		sql.append("    ,   GUBUN_2     				\n");
	    		sql.append("    ,   GUBUN_3     				\n");
	    		sql.append("    ,   GUBUN_4     				\n");
	    		sql.append("    ,   SUBJ      					\n");
	    		sql.append(") VALUES (          				\n");
	    		sql.append("        'GS'        				\n");
	    		sql.append("    ,   ?           				\n");
	    		sql.append("    ,   ?          		 			\n");
	    		sql.append("    ,   ?           				\n");
	    		sql.append("    ,   'F01'     					\n");
	    		sql.append("    ,   ?           				\n");
	    		sql.append(")                   				\n");
	    		
	    		sPstmt2 = connMgr.prepareStatement(sql.toString());
	    		
	    		for ( int i = 0; i < subjArr.length; i++ ) {
	    			sPstmt2.setString(1, gubun1);
	    			sPstmt2.setString(2, gubun2);
	    			sPstmt2.setString(3, gubun3);
	    			sPstmt2.setString(4, subjArr[i]);
	    			
	    			sPstmt2.addBatch();
	    			sPstmt2.clearParameters();
	    		}
	    		
	    		sResultCnt = sPstmt2.executeBatch();
    		}else{
    			sResultCnt = new int[1];
    			sResultCnt[0] = 1;
    		}
    		
    		sql.setLength(0);
    		
    		sql.append("DELETE  					\n");
    		sql.append("  FROM  TZ_GOLDHOMEGUBUN 	\n");
    		sql.append(" WHERE  GUBUN = 'GC'  		\n");
    		sql.append("   AND  GUBUN_1 = ?  		\n");
    		sql.append("   AND  GUBUN_2 = ?  		\n");
    		sql.append("   AND  GUBUN_3 = ?  		\n");
    		
    		gPstmt = connMgr.prepareStatement(sql.toString());
    		
    		gPstmt.setString(1, gubun1);
    		gPstmt.setString(2, gubun2);
    		gPstmt.setString(3, gubun3);
    		
    		gResult = gPstmt.executeUpdate();
    		
    		if(goldArr != null){
    			gPstmt.close();
    			gPstmt = null;
	    		
	    		sql.setLength(0);
	    		sql.append("/* com.credu.course.SubjectClassifyBean saveOnlineList (�з��� ������� ���) */\n");
	    		sql.append("INSERT INTO TZ_GOLDHOMEGUBUN (   	\n");
	    		sql.append("        GUBUN      					\n");
	    		sql.append("    ,   GUBUN_1    		 			\n");
	    		sql.append("    ,   GUBUN_2     				\n");
	    		sql.append("    ,   GUBUN_3     				\n");
	    		sql.append("    ,   GUBUN_4     				\n");
	    		sql.append("    ,   SEQ      					\n");
	    		sql.append(") VALUES (          				\n");
	    		sql.append("        'GC'        				\n");
	    		sql.append("    ,   ?           				\n");
	    		sql.append("    ,   ?           				\n");
	    		sql.append("    ,   ?           				\n");
	    		sql.append("    ,   'F01'     					\n");
	    		sql.append("    ,   ?           				\n");
	    		sql.append(")                   				\n");
	    		
	    		gPstmt2 = connMgr.prepareStatement(sql.toString());
	    		
	    		for ( int i = 0; i < goldArr.length; i++ ) {
	    			gPstmt2.setString(1, gubun1);
	    			gPstmt2.setString(2, gubun2);
	    			gPstmt2.setString(3, gubun3);
	    			gPstmt2.setString(4, goldArr[i]);
	    			
	    			gPstmt2.addBatch();
	    			gPstmt2.clearParameters();
	    		}
	    		
	    		gResultCnt = gPstmt2.executeBatch();
    		}else{
    			gResultCnt = new int[1];
    			gResultCnt[0] = 1;
    		}
    		
    		if ( sResultCnt.length > 0 && gResultCnt.length > 0) {
    			connMgr.commit();
    		} else {
    			connMgr.rollback();
    		}
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		ErrorManager.getErrorStackTrace(ex);
    		throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
    	} finally {
    		if (sPstmt != null) {
    			try {
    				sPstmt.close();
    			} catch (Exception e) {
    			}
    		}
    		if (sPstmt2 != null) {
    			try {
    				sPstmt2.close();
    			} catch (Exception e) {
    			}
    		}
    		if (gPstmt != null) {
    			try {
    				gPstmt.close();
    			} catch (Exception e) {
    			}
    		}
    		if (gPstmt2 != null) {
    			try {
    				gPstmt2.close();
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
    	
    	return (sResultCnt.length + gResultCnt.length);
    }

    
    @SuppressWarnings("unchecked")
    public ArrayList selectCodeList(String gubun, int lv, String ucd) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	ListSet ls = null;
    	ArrayList list = null;
    	StringBuffer sql = new StringBuffer();
    	DataBox dbox = null;
    	
    	try {
    		connMgr = new DBConnectionManager();
    		
    		list = new ArrayList();
    		
    		sql.append("/* com.credu.course.SubjectClassifyBean() selectCodeList  */ \n");
    		sql.append("SELECT  CODE, CODENM, LEVEL_CODE    \n");
    		sql.append("  FROM  TZ_CODE         			\n");
    		sql.append(" WHERE  GUBUN  = ?     				\n");
    		sql.append("   AND  LEVELS = ?     				\n");
    		
    		if(lv > 1){
    			sql.append(" AND UPPER = ?					\n");
    		}
    		
    		sql.append(" ORDER  BY CODE						\n");
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		pstmt.setString(1, gubun);
    		pstmt.setInt(2, lv);
    		
    		if(lv > 1){
    			pstmt.setString(3, ucd);
    		}
    		
    		ls = new ListSet(pstmt);
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			
    			list.add(dbox);
    		}
    		
    		
    	} catch (Exception ex) {
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
     * ���̵� ���� �ڵ带 �����Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    public int updateLevelCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int resultCnt = 0;
        int index = 1;
        
        String type = box.getString("type");
        String lvcd = box.getString("lvcd");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.course.SubjectClassifyBean updateLevelCode (���̵� ���� �ڵ� ����) */\n");
            sql.append("UPDATE TZ_CODE				\n");
            sql.append("   SET LEVEL_CODE = ?		\n");
            sql.append(" WHERE GUBUN 	  = '0110'	\n");
            sql.append("   AND LEVELS 	  = 1		\n");
            sql.append("   AND CODE 	  = ?		\n");
            
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, lvcd);
            pstmt.setString(index++, type);

            resultCnt = pstmt.executeUpdate();
            
            if ( resultCnt > 0 ) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
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
    
    /**
     * �����ڵ���� ��ȸ�Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectGubunNmList(RequestBox box) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	ListSet ls = null;
    	ArrayList list = null;
    	StringBuffer sql = new StringBuffer();
    	DataBox dbox = null;
    	String gubun1 = box.getString("gubun1");
    	String gubun2 = box.getString("gubun2");
    	String gubun3 = box.getString("gubun3");
    	
    	try {
    		connMgr = new DBConnectionManager();
    		
    		list = new ArrayList();
    		
    		sql.append("/* com.credu.course.SubjectClassifyBean() selectGubunNmList (�����ڵ��  ��ȸ) */ \n");
    		sql.append("SELECT  A.CODENM                \n");
    		sql.append("  FROM  TZ_CODE A               \n");
    		sql.append(" WHERE  A.GUBUN = '0110'        \n");
    		sql.append("   AND  A.CODE  IN (?, ?, ?)   	\n");
    		sql.append(" ORDER  BY A.LEVELS, A.CODE		\n");
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		pstmt.setString(1, gubun1);
    		pstmt.setString(2, gubun2);
    		pstmt.setString(3, gubun3);
    		
    		ls = new ListSet(pstmt);
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			
    			list.add(dbox);
    		}
    		
    	} catch (Exception ex) {
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
     * B2C ��ü �������� ����� ��ȸ�Ѵ�. �ش� �з��� ��ϵ� ������ ��Ͽ��� ���ܵȴ�.
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList onlineAllGoldClassList(RequestBox box) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	ListSet ls = null;
    	ArrayList list = null;
    	StringBuffer sql = new StringBuffer();
    	DataBox dbox = null;
    	
    	try {
    		connMgr = new DBConnectionManager();
    		
    		list = new ArrayList();
    		
    		sql.append("/* com.credu.course.SubjectClassifyBean() onlineAllGoldClassList (B2C ��ü �������� ��� ��ȸ) */ \n");
    		sql.append("SELECT A.SEQ                              				\n");
    		sql.append("     , A.LECNM                                			\n");
    		sql.append("  FROM TZ_GOLDCLASS A                 					\n");
    		sql.append("  	 , TZ_GOLDCLASS_GRMNG B           					\n");
    		sql.append(" WHERE A.SEQ 		  = B.SEQ(+)                		\n");
    		sql.append("   AND B.GRCODE 	  = 'N000001'                		\n");
    		sql.append("   AND A.USEYN  	  = 'Y'            	        		\n");
    		sql.append("   AND A.LECTURE_CLS != 'GC99' 							\n");
    		sql.append("   AND A.SEQ NOT IN (SELECT SEQ 						\n");
    		sql.append("					   FROM TZ_GOLDHOMEGUBUN			\n");
    		sql.append("					  WHERE GUBUN = 'GC') 				\n");			
    		sql.append(" ORDER BY A.LECNM	                  					\n");
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		
    		ls = new ListSet(pstmt);
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			
    			list.add(dbox);
    		}
    		
    	} catch (Exception ex) {
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
     * �з��� ��ϵ� �������� ����� ��ȸ�Ѵ�.
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList onlineGoldClassList(RequestBox box) throws Exception {
    	
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	ListSet ls = null;
    	ArrayList list = null;
    	StringBuffer sql = new StringBuffer();
    	DataBox dbox = null;
    	String gubun1 = box.getString("gubun1");
    	String gubun2 = box.getString("gubun2");
    	String gubun3 = box.getString("gubun3");
    	
    	try {
    		connMgr = new DBConnectionManager();
    		
    		list = new ArrayList();
    		
    		sql.append("/* com.credu.course.SubjectClassifyBean() onlineGoldClassList (�з��� ��ϵ� �������� ��� ��ȸ) */ \n");
    		sql.append("SELECT A.SEQ										\n");
    		sql.append("	 , B.LECNM										\n");
    		sql.append("     , (SELECT C.LVCODE 							\n");
    		sql.append("		  FROM TZ_GOLDHOMEGUBUN_LEVEL C 			\n");
    		sql.append("		 WHERE C.SEQ       = A.SEQ 					\n");
    		sql.append("		   AND C.GUBUNCODE = A.GUBUN_3) AS LVCODE	\n");    		
    		sql.append("  FROM TZ_GOLDHOMEGUBUN  A							\n");
    		sql.append(" INNER JOIN TZ_GOLDCLASS B							\n");
    		sql.append("    ON A.SEQ = B.SEQ								\n");
    		sql.append(" WHERE A.GUBUN   = 'GC'								\n");
    		sql.append("   AND A.GUBUN_1 = ?								\n");
    		sql.append("   AND A.GUBUN_2 = ?								\n");
    		sql.append("   AND A.GUBUN_3 = ?								\n");
    		sql.append(" ORDER BY B.LECNM									\n");
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		
    		pstmt.setString(1, gubun1);
    		pstmt.setString(2, gubun2);
    		pstmt.setString(3, gubun3);
    		
    		ls = new ListSet(pstmt);
    		
    		while (ls.next()) {
    			dbox = ls.getDataBox();
    			
    			list.add(dbox);
    		}
    		
    	} catch (Exception ex) {
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
	 * ���԰��� ���̵� ����
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public int courseSLvSave(RequestBox box) throws Exception { 
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer strQuery = null;
		
		int index = 1;
		int result = 0;
		
    	String gubun1 = box.getString("gubun1");
    	String gubun2 = box.getString("gubun2");
    	String gubun3 = box.getString("gubun3");
		
		try { 
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			strQuery = new StringBuffer();
			
			strQuery.append("DELETE 						\n");
			strQuery.append("  FROM TZ_SUBJHOMEGUBUN_LEVEL	\n");	
			strQuery.append(" WHERE GUBUNCODE IN (?)		\n");
			
			pstmt = connMgr.prepareStatement(strQuery.toString());
			index = 1;
			pstmt.setString(index++, gubun3);
			
			result = pstmt.executeUpdate();
			
			if(result > -1){
				Vector<String> v_subjSelect = box.getVector("p_scd");
				
				if(v_subjSelect != null && v_subjSelect.size() > 0){
					for(String subj : v_subjSelect){
						strQuery.setLength(0);
						strQuery.append("INSERT INTO TZ_SUBJHOMEGUBUN_LEVEL			\n");
						strQuery.append("(											\n");
						strQuery.append("	SUBJ,									\n");
						strQuery.append("	GUBUNCODE,								\n");
						strQuery.append("	LUSERID,								\n");
						strQuery.append("	LDATE,									\n");
						strQuery.append("	LVCODE									\n");
						strQuery.append(")											\n");
						strQuery.append("VALUES 									\n");
						strQuery.append("(		 									\n");
						strQuery.append("	?,										\n");
						strQuery.append("	?,										\n");
						strQuery.append("	?,										\n");
						strQuery.append("	TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),	\n");
						strQuery.append("	?										\n");
						strQuery.append(")											\n");
						
						pstmt = connMgr.prepareStatement(strQuery.toString());
						
						index = 1;
						pstmt.setString(index++, subj);
						pstmt.setString(index++, gubun3);
						pstmt.setString(index++, box.getSession("userid"));
						pstmt.setString(index++, box.getString(subj + "_lv"));
				
						result = pstmt.executeUpdate();
						
						if(result < 0){
							break;
						}
						
						if(pstmt != null){
							pstmt.close();
						}
					}
				}
			}
			
			if(result > 0) { 
				connMgr.commit();
			} else { 
				connMgr.rollback();
			}
			
		} catch(Exception ex ) { 
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
			throw new Exception("SQL = " + strQuery.toString() + "\r\n" + ex.getMessage() );
		} finally { 
			if(connMgr != null ) { try { connMgr.freeConnection(); connMgr.setAutoCommit(true); } catch(Exception e10 ) { } }
			if(pstmt != null ) { try { pstmt.close(); } catch(Exception e1 ) { } }
		}
		
		return result;
	}
	
	/**
	 * ����ü�赵 �������� ���̵� ����
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public int courseGLvSave(RequestBox box) throws Exception { 
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer strQuery = null;
		
		int index = 1;
		int result = 0;
		
    	String gubun1 = box.getString("gubun1");
    	String gubun2 = box.getString("gubun2");
    	String gubun3 = box.getString("gubun3"); 
		
		try { 
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			strQuery = new StringBuffer();
			
			strQuery.append("DELETE 						\n");
			strQuery.append("  FROM TZ_GOLDHOMEGUBUN_LEVEL	\n");	
			strQuery.append(" WHERE GUBUNCODE IN (?)		\n");
			
			pstmt = connMgr.prepareStatement(strQuery.toString());
			index = 1;
			pstmt.setString(index++, gubun3);
			
			result = pstmt.executeUpdate();
			
			if(result > -1){
				Vector<String> v_goldSelect = box.getVector("p_gcd");
				
				if(v_goldSelect != null && v_goldSelect.size() > 0){
					for(String gold : v_goldSelect){
						strQuery.setLength(0);
						strQuery.append("INSERT INTO TZ_GOLDHOMEGUBUN_LEVEL			\n");
						strQuery.append("(											\n");
						strQuery.append("	SEQ,									\n");
						strQuery.append("	GUBUNCODE,								\n");
						strQuery.append("	LUSERID,								\n");
						strQuery.append("	LDATE,									\n");
						strQuery.append("	LVCODE									\n");
						strQuery.append(")											\n");
						strQuery.append("VALUES 									\n");
						strQuery.append("(		 									\n");
						strQuery.append("	?,										\n");
						strQuery.append("	?,										\n");
						strQuery.append("	?,										\n");
						strQuery.append("	TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),	\n");
						strQuery.append("	?										\n");
						strQuery.append(")											\n");
						
						pstmt = connMgr.prepareStatement(strQuery.toString());
						
						index = 1;
						pstmt.setString(index++, gold);
						pstmt.setString(index++, gubun3);
						pstmt.setString(index++, box.getSession("userid"));
						pstmt.setString(index++, box.getString(gold + "_lv"));
				
						result = pstmt.executeUpdate();
						
						if(result < 0){
							break;
						}
						
						if(pstmt != null){
							pstmt.close();
						}
					}
				}
			}
			
			if(result > 0) { 
				connMgr.commit();
			} else { 
				connMgr.rollback();
			}
			
		} catch(Exception ex ) { 
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
			throw new Exception("SQL = " + strQuery.toString() + "\r\n" + ex.getMessage() );
		} finally { 
			if(connMgr != null ) { try { connMgr.freeConnection(); connMgr.setAutoCommit(true); } catch(Exception e10 ) { } }
			if(pstmt != null ) { try { pstmt.close(); } catch(Exception e1 ) { } }
		}
		
		return result;
	}

    /**
     * �н��ε�� 1���� ����� ��ȸ�Ѵ�.
     *
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList selectLoadMapClassifyList1(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String lm_type = box.getStringDefault("lmType", "B0");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.course.SubjectClassifyBean() selectLoadMapClassifyList1 (�н��ε�� 1���� ��� ��ȸ) */  \n");
            sql.append(" SELECT DISTINCT M.GUBUN_2                                                              G2C \n");
            sql.append("      , (SELECT X.CODENM FROM TZ_CODE X WHERE X.GUBUN = '0110' AND X.CODE = M.GUBUN_2)  G2N \n");
            sql.append("   FROM (                                                                                   \n");
            sql.append("            SELECT A.GUBUN_2                                                                \n");
            sql.append("                 , A.GUBUN_3                                                                \n");
            sql.append("                 , (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0101', 'L0201')) X1    \n");
            sql.append("                 , (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0102', 'L0203')) X2    \n");
            sql.append("                 , (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0103', 'L0203')) X3    \n");
            sql.append("                 , NULL Z1  \n");
            sql.append("                 , NULL Z2  \n");
            sql.append("                 , NULL Z3  \n");
            sql.append("                 , CASE WHEN (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0101', 'L0201')) IS NOT NULL THEN '1'   \n");
            sql.append("                        WHEN (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0102', 'L0202')) IS NOT NULL THEN '3'   \n");
            sql.append("                        WHEN (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0103', 'L0203')) IS NOT NULL THEN '5'   \n");
            sql.append("                        ELSE '99'                   \n");
            sql.append("                    END TYPE                        \n");
            sql.append("              FROM TZ_SUBJHOMEGUBUN A               \n");
            sql.append("             WHERE A.GUBUN   = 'GS'                 \n");
            sql.append("               AND A.GUBUN_1 = '" + lm_type + "'    \n");

            sql.append("             UNION ALL                  \n");

            sql.append("            SELECT A.GUBUN_2            \n");
            sql.append("                 , A.GUBUN_3            \n");
            sql.append("                 , NULL                 \n");
            sql.append("                 , NULL                 \n");
            sql.append("                 , NULL                 \n");
            sql.append("                 , (SELECT Z.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL Z WHERE A.SEQ = Z.SEQ AND Z.LVCODE IN ('L0101', 'L0201'))  \n");
            sql.append("                 , (SELECT Z.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL Z WHERE A.SEQ = Z.SEQ AND Z.LVCODE IN ('L0102', 'L0203'))  \n");
            sql.append("                 , (SELECT Z.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL Z WHERE A.SEQ = Z.SEQ AND Z.LVCODE IN ('L0103', 'L0203'))  \n");
            sql.append("                 , CASE WHEN (SELECT X.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL X WHERE A.SEQ = X.SEQ AND X.LVCODE IN ('L0101', 'L0201')) IS NOT NULL THEN '2'   \n");
            sql.append("                        WHEN (SELECT X.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL X WHERE A.SEQ = X.SEQ AND X.LVCODE IN ('L0102', 'L0202')) IS NOT NULL THEN '4'   \n");
            sql.append("                        WHEN (SELECT X.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL X WHERE A.SEQ = X.SEQ AND X.LVCODE IN ('L0103', 'L0203')) IS NOT NULL THEN '6'   \n");
            sql.append("                        ELSE '99'                   \n");
            sql.append("                    END TYPE                        \n");
            sql.append("              FROM TZ_GOLDHOMEGUBUN A               \n");
            sql.append("             WHERE A.GUBUN   = 'GC'                 \n");
            sql.append("               AND A.GUBUN_1 = '" + lm_type + "'    \n");
            sql.append("        ) M                                         \n");
            sql.append("    ORDER BY M.GUBUN_2                              \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
     * �н��ε�� 2���� ����� ��ȸ�Ѵ�.
     *
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList selectLoadMapClassifyList2(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String lm_type = box.getStringDefault("lmType", "B0");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.course.SubjectClassifyBean() selectLoadMapClassifyList2 (�н��ε�� 2���� ��� ��ȸ) */  \n");
            sql.append(" SELECT DISTINCT M.GUBUN_2                                                              G2C \n");
            sql.append("      , M.GUBUN_3                                                                       G3C \n");
            sql.append("      , (SELECT X.CODENM FROM TZ_CODE X WHERE X.GUBUN = '0110' AND X.CODE = M.GUBUN_3)  G3N \n");
            sql.append("   FROM (                                                                                   \n");
            sql.append("            SELECT A.GUBUN_2                                                                \n");
            sql.append("                 , A.GUBUN_3                                                                \n");
            sql.append("                 , (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0101', 'L0201')) X1    \n");
            sql.append("                 , (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0102', 'L0203')) X2    \n");
            sql.append("                 , (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0103', 'L0203')) X3    \n");
            sql.append("                 , NULL Z1  \n");
            sql.append("                 , NULL Z2  \n");
            sql.append("                 , NULL Z3  \n");
            sql.append("                 , CASE WHEN (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0101', 'L0201')) IS NOT NULL THEN '1'   \n");
            sql.append("                        WHEN (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0102', 'L0202')) IS NOT NULL THEN '3'   \n");
            sql.append("                        WHEN (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0103', 'L0203')) IS NOT NULL THEN '5'   \n");
            sql.append("                        ELSE '99'                   \n");
            sql.append("                    END TYPE                        \n");
            sql.append("              FROM TZ_SUBJHOMEGUBUN A               \n");
            sql.append("             WHERE A.GUBUN   = 'GS'                 \n");
            sql.append("               AND A.GUBUN_1 = '" + lm_type + "'    \n");

            sql.append("             UNION ALL                  \n");

            sql.append("            SELECT A.GUBUN_2            \n");
            sql.append("                 , A.GUBUN_3            \n");
            sql.append("                 , NULL                 \n");
            sql.append("                 , NULL                 \n");
            sql.append("                 , NULL                 \n");
            sql.append("                 , (SELECT Z.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL Z WHERE A.SEQ = Z.SEQ AND Z.LVCODE IN ('L0101', 'L0201'))  \n");
            sql.append("                 , (SELECT Z.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL Z WHERE A.SEQ = Z.SEQ AND Z.LVCODE IN ('L0102', 'L0203'))  \n");
            sql.append("                 , (SELECT Z.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL Z WHERE A.SEQ = Z.SEQ AND Z.LVCODE IN ('L0103', 'L0203'))  \n");
            sql.append("                 , CASE WHEN (SELECT X.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL X WHERE A.SEQ = X.SEQ AND X.LVCODE IN ('L0101', 'L0201')) IS NOT NULL THEN '2'   \n");
            sql.append("                        WHEN (SELECT X.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL X WHERE A.SEQ = X.SEQ AND X.LVCODE IN ('L0102', 'L0202')) IS NOT NULL THEN '4'   \n");
            sql.append("                        WHEN (SELECT X.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL X WHERE A.SEQ = X.SEQ AND X.LVCODE IN ('L0103', 'L0203')) IS NOT NULL THEN '6'   \n");
            sql.append("                        ELSE '99'                   \n");
            sql.append("                    END TYPE                        \n");
            sql.append("              FROM TZ_GOLDHOMEGUBUN A               \n");
            sql.append("             WHERE A.GUBUN   = 'GC'                 \n");
            sql.append("               AND A.GUBUN_1 = '" + lm_type + "'    \n");
            sql.append("        ) M                                         \n");
            sql.append("    ORDER BY M.GUBUN_2, M.GUBUN_3                   \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
     * �н��ε�� 3���� ����� ��ȸ�Ѵ�.
     *
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList selectLoadMapClassifyList3(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String lm_type = box.getStringDefault("lmType", "B0");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.course.SubjectClassifyBean() selectLoadMapClassifyList3 (�н��ε�� 3���� ��� ��ȸ) */                                                                    \n");
            sql.append(" SELECT M.GUBUN_2                                                                       G2C                                                                         \n");
            sql.append("      , (SELECT X.CODENM FROM TZ_CODE X WHERE X.GUBUN = '0110' AND X.CODE = M.GUBUN_2)  G2N                                                                         \n");
            sql.append("      , M.GUBUN_3                                                                       G3C                                                                         \n");
            sql.append("      , (SELECT X.CODENM FROM TZ_CODE X WHERE X.GUBUN = '0110' AND X.CODE = M.GUBUN_3)  G3N                                                                         \n");
            sql.append("      , M.GUBUN_3                                                                       G3C                                                                         \n");
            sql.append("      , (SELECT X.CODENM FROM TZ_CODE X WHERE X.GUBUN = '0110' AND X.CODE = M.GUBUN_3)  G3N                                                                         \n");
            sql.append("      , M.TYPE                                                                                                                                                      \n");
            sql.append("      , CASE M.TYPE                                                                                                                                                 \n");
            sql.append("            WHEN '1' THEN M.X1                                                                                                                                      \n");
            sql.append("            WHEN '2' THEN TO_CHAR(M.Z1)                                                                                                                             \n");
            sql.append("            WHEN '3' THEN M.X2                                                                                                                                      \n");
            sql.append("            WHEN '4' THEN TO_CHAR(M.Z2)                                                                                                                             \n");
            sql.append("            WHEN '5' THEN M.X3                                                                                                                                      \n");
            sql.append("            WHEN '6' THEN TO_CHAR(M.Z3)                                                                                                                             \n");
            sql.append("        END CD                                                                                                                                                      \n");
            sql.append("      , CASE M.TYPE                                                                                                                                                 \n");
            sql.append("            WHEN '1' THEN (SELECT X.SUBJNM FROM TZ_SUBJ      X WHERE X.SUBJ = M.X1)                                                                                 \n");
            sql.append("            WHEN '2' THEN (SELECT X.LECNM  FROM TZ_GOLDCLASS X WHERE X.SEQ  = M.Z1)                                                                                 \n");
            sql.append("            WHEN '3' THEN (SELECT X.SUBJNM FROM TZ_SUBJ      X WHERE X.SUBJ = M.X2)                                                                                 \n");
            sql.append("            WHEN '4' THEN (SELECT X.LECNM  FROM TZ_GOLDCLASS X WHERE X.SEQ  = M.Z2)                                                                                 \n");
            sql.append("            WHEN '5' THEN (SELECT X.SUBJNM FROM TZ_SUBJ      X WHERE X.SUBJ = M.X3)                                                                                 \n");
            sql.append("            WHEN '6' THEN (SELECT X.LECNM  FROM TZ_GOLDCLASS X WHERE X.SEQ  = M.Z3)                                                                                 \n");
            sql.append("        END NM                                                                                                                                                      \n");
            sql.append("      , M.CRDATE                                                                                                                                                    \n");
            sql.append("      , M.SYEAR                                                                                                                                                     \n");
            sql.append("      , M.SUBJSEQ                                                                                                                                                   \n");
            sql.append("      , M.GYEAR                                                                                                                                                     \n");
            sql.append("      , M.GRSEQ                                                                                                                                                     \n");
            sql.append("      , M.L_CNT                                                                                                                                                     \n");
            sql.append("      , M.STS                                                                                                                                                       \n");
            sql.append("   FROM (                                                                                                                                                           \n");
            sql.append("            SELECT A.GUBUN_2                                                                                                                                        \n");
            sql.append("                 , A.GUBUN_3                                                                                                                                        \n");
            sql.append("                 , (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0101', 'L0201')) X1                                        \n");
            sql.append("                 , (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0102', 'L0203')) X2                                        \n");
            sql.append("                 , (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0103', 'L0203')) X3                                        \n");
            sql.append("                 , NULL Z1                                                                                                                                          \n");
            sql.append("                 , NULL Z2                                                                                                                                          \n");
            sql.append("                 , NULL Z3                                                                                                                                          \n");
            sql.append("                 , CASE WHEN (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0101', 'L0201')) IS NOT NULL THEN '1'            \n");
            sql.append("                        WHEN (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0102', 'L0202')) IS NOT NULL THEN '3'            \n");
            sql.append("                        WHEN (SELECT X.SUBJ FROM TZ_SUBJHOMEGUBUN_LEVEL X WHERE A.SUBJ = X.SUBJ AND X.LVCODE IN ('L0103', 'L0203')) IS NOT NULL THEN '5'            \n");
            sql.append("                        ELSE '99'                                                                                                                                   \n");
            sql.append("                    END TYPE                                                                                                                                        \n");
            sql.append("                 , (SELECT SUBSTR(X.CRDATE, 1, 4)                                                                                                                   \n");
            sql.append("                      FROM VZ_SCSUBJSEQIMGMOBILE X                                                                                                                  \n");
            sql.append("                     WHERE X.SUBJ   = A.SUBJ                                                                                                                        \n");
            sql.append("                       AND X.GRCODE = 'N000001'                                                                                                                     \n");
            sql.append("                       AND X.PROPSTART NOT IN (' ')                                                                                                                 \n");
            sql.append("                       AND TO_CHAR(SYSDATE,'YYYYMM') BETWEEN REPLACE(SUBSTR(X.PROPSTART,1,6), '.', '') AND REPLACE(SUBSTR(X.PROPEND,1,6), '.', '')) CRDATE          \n");
            sql.append("                 , (SELECT X.YEAR                                                                                                                                   \n");
            sql.append("                      FROM VZ_SCSUBJSEQIMGMOBILE X                                                                                                                  \n");
            sql.append("                     WHERE X.SUBJ   = A.SUBJ                                                                                                                        \n");
            sql.append("                       AND X.GRCODE = 'N000001'                                                                                                                     \n");
            sql.append("                       AND X.PROPSTART NOT IN (' ')                                                                                                                 \n");
            sql.append("                       AND TO_CHAR(SYSDATE,'YYYYMM') BETWEEN REPLACE(SUBSTR(X.PROPSTART,1,6), '.', '') AND REPLACE(SUBSTR(X.PROPEND,1,6), '.', '')) SYEAR           \n");
            sql.append("                 , (SELECT X.SUBJSEQ                                                                                                                                \n");
            sql.append("                      FROM VZ_SCSUBJSEQIMGMOBILE X                                                                                                                  \n");
            sql.append("                     WHERE X.SUBJ   = A.SUBJ                                                                                                                        \n");
            sql.append("                       AND X.GRCODE = 'N000001'                                                                                                                     \n");
            sql.append("                       AND X.PROPSTART NOT IN (' ')                                                                                                                 \n");
            sql.append("                       AND TO_CHAR(SYSDATE,'YYYYMM') BETWEEN REPLACE(SUBSTR(X.PROPSTART,1,6), '.', '') AND REPLACE(SUBSTR(X.PROPEND,1,6), '.', '')) SUBJSEQ         \n");
            sql.append("                 , (SELECT X.GYEAR                                                                                                                                  \n");
            sql.append("                      FROM VZ_SCSUBJSEQIMGMOBILE X                                                                                                                  \n");
            sql.append("                     WHERE X.SUBJ   = A.SUBJ                                                                                                                        \n");
            sql.append("                       AND X.GRCODE = 'N000001'                                                                                                                     \n");
            sql.append("                       AND X.PROPSTART NOT IN (' ')                                                                                                                 \n");
            sql.append("                       AND TO_CHAR(SYSDATE,'YYYYMM') BETWEEN REPLACE(SUBSTR(X.PROPSTART,1,6), '.', '') AND REPLACE(SUBSTR(X.PROPEND,1,6), '.', '')) GYEAR           \n");
            sql.append("                 , (SELECT X.GRSEQ                                                                                                                                  \n");
            sql.append("                      FROM VZ_SCSUBJSEQIMGMOBILE X                                                                                                                  \n");
            sql.append("                     WHERE X.SUBJ   = A.SUBJ                                                                                                                        \n");
            sql.append("                       AND X.GRCODE = 'N000001'                                                                                                                     \n");
            sql.append("                       AND X.PROPSTART NOT IN (' ')                                                                                                                 \n");
            sql.append("                       AND TO_CHAR(SYSDATE,'YYYYMM') BETWEEN REPLACE(SUBSTR(X.PROPSTART,1,6), '.', '') AND REPLACE(SUBSTR(X.PROPEND,1,6), '.', '')) GRSEQ           \n");
            sql.append("                 , (SELECT COUNT(*) FROM TZ_SUBJLESSON X WHERE X.SUBJ = A.SUBJ) L_CNT                                                                               \n");
            sql.append("                 , NVL((SELECT 'OOO'                                                                                                                                \n");
            sql.append("                          FROM VZ_SCSUBJSEQIMGMOBILE X                                                                                                              \n");
            sql.append("                         WHERE X.SUBJ   = A.SUBJ                                                                                                                    \n");
            sql.append("                           AND X.GRCODE = 'N000001'                                                                                                                 \n");
            sql.append("                           AND X.PROPSTART NOT IN (' ')                                                                                                             \n");
            sql.append("                           AND TO_CHAR(SYSDATE,'YYYYMM') BETWEEN REPLACE(SUBSTR(X.PROPSTART,1,6), '.', '') AND REPLACE(SUBSTR(X.PROPEND,1,6), '.', '')), 'XXX') STS \n");
            sql.append("              FROM TZ_SUBJHOMEGUBUN A                                                                                                                               \n");
            sql.append("             WHERE A.GUBUN   = 'GS'                                                                                                                                 \n");
            sql.append("               AND A.GUBUN_1 = '" + lm_type + "'                                                                                                                    \n");

            sql.append("             UNION ALL                                                                                                                                              \n");

            sql.append("            SELECT A.GUBUN_2                                                                                                                                        \n");
            sql.append("                 , A.GUBUN_3                                                                                                                                        \n");
            sql.append("                 , NULL                                                                                                                                             \n");
            sql.append("                 , NULL                                                                                                                                             \n");
            sql.append("                 , NULL                                                                                                                                             \n");
            sql.append("                 , (SELECT Z.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL Z WHERE A.SEQ = Z.SEQ AND Z.LVCODE IN ('L0101', 'L0201'))                                              \n");
            sql.append("                 , (SELECT Z.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL Z WHERE A.SEQ = Z.SEQ AND Z.LVCODE IN ('L0102', 'L0203'))                                              \n");
            sql.append("                 , (SELECT Z.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL Z WHERE A.SEQ = Z.SEQ AND Z.LVCODE IN ('L0103', 'L0203'))                                              \n");
            sql.append("                 , CASE WHEN (SELECT X.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL X WHERE A.SEQ = X.SEQ AND X.LVCODE IN ('L0101', 'L0201')) IS NOT NULL THEN '2'               \n");
            sql.append("                        WHEN (SELECT X.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL X WHERE A.SEQ = X.SEQ AND X.LVCODE IN ('L0102', 'L0202')) IS NOT NULL THEN '4'               \n");
            sql.append("                        WHEN (SELECT X.SEQ FROM TZ_GOLDHOMEGUBUN_LEVEL X WHERE A.SEQ = X.SEQ AND X.LVCODE IN ('L0103', 'L0203')) IS NOT NULL THEN '6'               \n");
            sql.append("                        ELSE '99'                                                                                                                                   \n");
            sql.append("                   END TYPE                                                                                                                                         \n");
            sql.append("                 , (SELECT X.CREATYEAR FROM TZ_GOLDCLASS X WHERE X.SEQ = A.SEQ)                                                                                     \n");
            sql.append("                 , NULL                                                                                                                                             \n");
            sql.append("                 , NULL                                                                                                                                             \n");
            sql.append("                 , NULL                                                                                                                                             \n");
            sql.append("                 , NULL                                                                                                                                             \n");
            sql.append("                 , NULL                                                                                                                                             \n");
            sql.append("                 , 'OOO'                                                                                                                                            \n");
            sql.append("              FROM TZ_GOLDHOMEGUBUN A                                                                                                                               \n");
            sql.append("             WHERE A.GUBUN   = 'GC'                                                                                                                                 \n");
            sql.append("               AND A.GUBUN_1 = '" + lm_type + "'                                                                                                                    \n");
            sql.append("        ) M                                                                                                                                                         \n");
            sql.append("    WHERE M.STS = 'OOO'                                                                                                                                             \n");
            sql.append("    ORDER BY M.GUBUN_2, M.GUBUN_3, M.CRDATE DESC, M.TYPE, M.X1, M.X2, M.X3, M.Z1, M.Z2, M.Z3                                                                        \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
     * ���� ���� ��ȸ.
     *
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList selectCourseInfo(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String lm_type = box.getStringDefault("p_type", "B0");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.course.SubjectClassifyBean() selectCourseInfo (���� ���� ��ȸ) */                                                                \n");

            if ("S".equals(box.getString("p_gubun"))) {
                sql.append(" SELECT S1.SUBJ COURSE_ID                                                                                                               \n");
                sql.append("      , S1.YEAR                                                                                                                         \n");
                sql.append("      , S1.SUBJSEQ                                                                                                                      \n");
                sql.append("      , S1.GRCODE                                                                                                                       \n");
                sql.append("      , S1.GYEAR                                                                                                                        \n");
                sql.append("      , S1.GRSEQ                                                                                                                        \n");
                sql.append("      , S1.SUBJNM COURSE_NM                                                                                                             \n");
                sql.append("      , SUBSTR(S1.CRDATE, 1, 4) CRDATE                                                                                                  \n");
                sql.append("      , (SELECT X.CODENM                                                                                                                \n");
                sql.append("           FROM TZ_CODE X                                                                                                               \n");
                sql.append("          WHERE X.GUBUN = '0110'                                                                                                        \n");
                sql.append("            AND X.CODE  = (SELECT X.GUBUN_2                                                                                             \n");
                sql.append("                             FROM TZ_SUBJHOMEGUBUN X                                                                                    \n");
                sql.append("                            WHERE X.GUBUN   = 'GS'                                                                                      \n");
                sql.append("                              AND X.GUBUN_1 = '" + lm_type + "'                                                                         \n");
                sql.append("                              AND X.SUBJ    = S1.SUBJ)) D1                                                                              \n");
                sql.append("      , (SELECT X.CODENM                                                                                                                \n");
                sql.append("           FROM TZ_CODE X                                                                                                               \n");
                sql.append("          WHERE X.GUBUN = '0110'                                                                                                        \n");
                sql.append("            AND X.CODE  = (SELECT X.GUBUN_3                                                                                             \n");
                sql.append("                             FROM TZ_SUBJHOMEGUBUN X                                                                                    \n");
                sql.append("                            WHERE X.GUBUN   = 'GS'                                                                                      \n");
                sql.append("                              AND X.GUBUN_1 = '" + lm_type + "'                                                                         \n");
                sql.append("                              AND X.SUBJ    = S1.SUBJ)) D2                                                                              \n");
                sql.append("      , (SELECT X.CODENM                                                                                                                \n");
                sql.append("           FROM TZ_CODE X                                                                                                               \n");
                sql.append("          WHERE X.GUBUN = '0121'                                                                                                        \n");
                sql.append("            AND X.CODE  = (SELECT X.LVCODE                                                                                              \n");
                sql.append("                             FROM TZ_SUBJHOMEGUBUN_LEVEL X                                                                              \n");
                sql.append("                            WHERE S1.SUBJ = X.SUBJ)) LV                                                                                 \n");
                sql.append("      , S1.INTRODUCEFILENAMENEW IMG                                                                                                     \n");
                sql.append("      , S1.SUBJ VOD_URL                                                                                                                 \n");
                sql.append("      , 0 WIDTH_S                                                                                                                       \n");
                sql.append("      , 0 HEIGHT_S                                                                                                                      \n");
                sql.append("   FROM VZ_SCSUBJSEQIMGMOBILE S1                                                                                                        \n");
                sql.append("  WHERE S1.PROPSTART NOT IN (' ')                                                                                                       \n");
                sql.append("    AND TO_CHAR(SYSDATE, 'YYYYMM') BETWEEN REPLACE(SUBSTR(S1.PROPSTART, 1, 6), '.', '') AND REPLACE(SUBSTR(S1.PROPEND,1,6), '.', '')    \n");
                sql.append("    AND S1.SUBJ   = '" + box.getString("p_course_id") + "'                                                                         \n");
                sql.append("    AND S1.GRCODE = 'N000001'                                                                                                           \n");
            } else if ("G".equals(box.getString("p_gubun"))) {
                sql.append(" SELECT A.SEQ COURSE_ID                                                                                                                 \n");
                sql.append("      , NULL YEAR                                                                                                                       \n");
                sql.append("      , NULL SUBJSEQ                                                                                                                    \n");
                sql.append("      , NULL GRCODE                                                                                                                     \n");
                sql.append("      , NULL GYEAR                                                                                                                      \n");
                sql.append("      , NULL GRSEQ                                                                                                                      \n");
                sql.append("      , A.LECNM     COURSE_NM                                                                                                           \n");
                sql.append("      , A.CREATYEAR CRDATE                                                                                                              \n");
                sql.append("      , (SELECT X.CODENM                                                                                                                \n");
                sql.append("           FROM TZ_CODE X                                                                                                               \n");
                sql.append("          WHERE X.GUBUN = '0110'                                                                                                        \n");
                sql.append("            AND X.CODE  = (SELECT X.GUBUN_2                                                                                             \n");
                sql.append("                             FROM TZ_GOLDHOMEGUBUN X                                                                                    \n");
                sql.append("                            WHERE X.GUBUN   = 'GC'                                                                                      \n");
                sql.append("                              AND X.GUBUN_1 = '" + lm_type + "'                                                                         \n");
                sql.append("                              AND X.SEQ     = A.SEQ)) D1                                                                                \n");
                sql.append("      , (SELECT X.CODENM                                                                                                                \n");
                sql.append("           FROM TZ_CODE X                                                                                                               \n");
                sql.append("          WHERE X.GUBUN = '0110'                                                                                                        \n");
                sql.append("            AND X.CODE  = (SELECT X.GUBUN_3                                                                                             \n");
                sql.append("                             FROM TZ_GOLDHOMEGUBUN X                                                                                    \n");
                sql.append("                            WHERE X.GUBUN   = 'GC'                                                                                      \n");
                sql.append("                              AND X.GUBUN_1 = '" + lm_type + "'                                                                         \n");
                sql.append("                              AND X.SEQ     = A.SEQ)) D2                                                                                \n");
                sql.append("      , (SELECT X.CODENM                                                                                                                \n");
                sql.append("           FROM TZ_CODE X                                                                                                               \n");
                sql.append("          WHERE X.GUBUN = '0121'                                                                                                        \n");
                sql.append("            AND X.CODE  = (SELECT X.LVCODE                                                                                              \n");
                sql.append("                             FROM TZ_GOLDHOMEGUBUN_LEVEL X                                                                              \n");
                sql.append("                            WHERE A.SEQ = X.SEQ)) LV                                                                                    \n");
                sql.append("      , A.VODIMG  IMG                                                                                                                   \n");
                sql.append("      , A.VODURL  VOD_URL                                                                                                               \n");
                sql.append("      , A.WIDTH_S                                                                                                                       \n");
                sql.append("      , A.HEIGHT_S                                                                                                                      \n");
                sql.append("   FROM TZ_GOLDCLASS A                                                                                                                  \n");
                sql.append("  WHERE A.SEQ = '" + box.getString("p_course_id") + "'                                                                              \n");
            }

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
     * ���� ���� ��� ��ȸ.
     *
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList selectCourseNextList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;
        String tags = "";
        String v_str = "";
        String token = ",";
        String []arr = null;
        int rnkLimit = 0;
        int rcnt = 0;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.setLength(0);
            sql.append("/* com.credu.course.SubjectClassifyBean() selectCourseNextList (���� �±� ��ȸ) */                                                            \n");

            if ("S".equals(box.getString("p_gubun"))) {
                sql.append(" SELECT A.SEARCH_NM TAGS                                                                                                                \n");
                sql.append("   FROM TZ_SUBJ A                                                                                                                       \n");
                sql.append("  WHERE A.SUBJ = '" + box.getString("p_course_id") + "'                                                                            \n");
            } else if ("G".equals(box.getString("p_gubun"))) {
                sql.append(" SELECT A.TAGS                                                                                                                          \n");
                sql.append("   FROM TZ_GOLDCLASS A                                                                                                                  \n");
                sql.append("  WHERE A.SEQ = '" + box.getString("p_course_id") + "'                                                                              \n");
            }

            ls = connMgr.executeQuery(sql.toString());
            while(ls.next()){
                tags = ls.getString("tags");

                arr = StringUtils.split(tags, token.trim());

                TreeSet ts = new TreeSet();
                for(int i = 0; i < arr.length; i++){
                    ts.add(arr[i]);
                }

                Iterator it = ts.iterator();
                while(it.hasNext()){
                    v_str += (String)it.next()+token.trim();
                }

                v_str = v_str.substring(0, v_str.lastIndexOf(token.trim()));

                tags = v_str;

                arr = StringUtils.split(tags, token.trim());
            }

            rnkLimit = (arr.length < 3) ? arr.length - 1 : 2;

            sql.setLength(0);
            sql.append("/* com.credu.course.SubjectClassifyBean() selectCourseNextList (���� ���� ��� ��ȸ) */                                                        \n");

            if ("S".equals(box.getString("p_gubun"))) {
                sql.append(" SELECT *                                                                                                                               \n");
                sql.append("   FROM (SELECT TYPE                                                                                                                    \n");
                sql.append("              , COURSE_ID                                                                                                               \n");
                sql.append("              , YEAR                                                                                                                    \n");
                sql.append("              , SUBJSEQ                                                                                                                 \n");
                sql.append("              , COURSE_NAME                                                                                                             \n");
                sql.append("              , IMG_PATH                                                                                                                \n");
                sql.append("              , G2CD                                                                                                                    \n");
                sql.append("              , G2NM                                                                                                                    \n");
                sql.append("              , G3CD                                                                                                                    \n");
                sql.append("              , G3NM                                                                                                                    \n");
                sql.append("              , LVCD                                                                                                                    \n");
                sql.append("              , LVNM                                                                                                                    \n");
                sql.append("              , WIDTH_S                                                                                                                 \n");
                sql.append("              , HEIGHT_S                                                                                                                \n");
                sql.append("              , VOD_URL                                                                                                                 \n");
                sql.append("              , CRDATE                                                                                                                  \n");
                sql.append("              , GRSEQ                                                                                                                   \n");
                sql.append("              , GYEAR                                                                                                                   \n");

                if (arr.length > 0) {
                    for (String str : arr) {
                        str = str.replaceAll(" ", "");
                        sql.append("              , CASE WHEN INSTR (LINK_TEXT, '" + str + "') > 0 THEN 1 ELSE 0 END AS RNK" + str + "                              \n");
                    }
                }

                sql.append("           FROM (SELECT 'S'                         AS TYPE                                                                             \n");
                sql.append("		              , A.SUBJ 	                    AS COURSE_ID                                                                        \n");
                sql.append("                      , A.YEAR 	                    AS YEAR                                                                             \n");
                sql.append("		              , A.SUBJSEQ 	                AS SUBJSEQ                                                                          \n");
                sql.append("                      , C.SUBJNM 	                AS COURSE_NAME                                                                      \n");
                sql.append("		              , C.INTRODUCEFILENAMENEW      AS IMG_PATH                                                                         \n");
                sql.append("		              , C.SEARCH_NM                 AS LINK_TEXT                                                                        \n");
                sql.append("                      , (SELECT X.GUBUN_2                                                                                               \n");
                sql.append("                           FROM TZ_SUBJHOMEGUBUN X                                                                                      \n");
                sql.append("                          WHERE X.GUBUN = 'GS'                                                                                          \n");
                sql.append("                            AND X.SUBJ  = A.SUBJ)   AS G2CD                                                                             \n");
                sql.append("	                  , (SELECT Z.CODENM                                                                                                \n");
                sql.append("                           FROM TZ_CODE Z                                                                                               \n");
                sql.append("                          WHERE Z.GUBUN  = '0110'                                                                                       \n");
                sql.append("                            AND Z.LEVELS = 2                                                                                            \n");
                sql.append("                            AND Z.CODE   = (SELECT X.GUBUN_2                                                                            \n");
                sql.append("                                              FROM TZ_SUBJHOMEGUBUN X                                                                   \n");
                sql.append("                                             WHERE X.GUBUN = 'GS'                                                                       \n");
                sql.append("                                               AND X.SUBJ  = A.SUBJ)) AS G2NM                                                           \n");
                sql.append("	                  , (SELECT X.GUBUN_3                                                                                               \n");
                sql.append("                           FROM TZ_SUBJHOMEGUBUN X                                                                                      \n");
                sql.append("                          WHERE X.GUBUN = 'GS'                                                                                          \n");
                sql.append("                            AND X.SUBJ  = A.SUBJ)   AS G3CD                                                                             \n");
                sql.append("	                  , (SELECT Z.CODENM                                                                                                \n");
                sql.append("                           FROM TZ_CODE Z                                                                                               \n");
                sql.append("                          WHERE Z.GUBUN  = '0110'                                                                                       \n");
                sql.append("                            AND Z.LEVELS = 3                                                                                            \n");
                sql.append("                            AND Z.CODE   = (SELECT X.GUBUN_3                                                                            \n");
                sql.append("                                              FROM TZ_SUBJHOMEGUBUN X                                                                   \n");
                sql.append("                                             WHERE X.GUBUN = 'GS'                                                                       \n");
                sql.append("                                               AND X.SUBJ  = A.SUBJ)) AS G3NM                                                           \n");
                sql.append("            		  , (SELECT X.LVCODE                                                                                                \n");
                sql.append("                           FROM TZ_SUBJHOMEGUBUN_LEVEL X                                                                                \n");
                sql.append("                          WHERE X.SUBJ = A.SUBJ)    AS LVCD                                                                             \n");
                sql.append("		              , (SELECT Z.CODENM                                                                                                \n");
                sql.append("                           FROM TZ_CODE Z                                                                                               \n");
                sql.append("                          WHERE Z.GUBUN  = '0121'                                                                                       \n");
                sql.append("                            AND Z.LEVELS = 2                                                                                            \n");
                sql.append("                            AND Z.CODE   = (SELECT X.LVCODE                                                                             \n");
                sql.append("                                              FROM TZ_SUBJHOMEGUBUN_LEVEL X                                                             \n");
                sql.append("                                             WHERE X.SUBJ = A.SUBJ)) AS LVNM                                                            \n");
                sql.append("		              , 0  AS WIDTH_S                                                                                                   \n");
                sql.append("		              , 0  AS HEIGHT_S                                                                                                  \n");
                sql.append("		              , '' AS VOD_URL                                                                                                   \n");
                sql.append("		              , SUBSTR(C.CRDATE, 1, 4) CRDATE                                                                                   \n");
                sql.append("		              , A.GYEAR                                                                                                         \n");
                sql.append("		              , A.GRSEQ                                                                                                         \n");
                sql.append("                   FROM TZ_SUBJSEQ   A                                                                                                  \n");
                sql.append("                  INNER JOIN TZ_SUBJ C                                                                                                  \n");
                sql.append("                          ON A.SUBJ = C.SUBJ                                                                                            \n");
                sql.append("                  WHERE C.SUBJ NOT IN (SELECT DISTINCT SUBJ                                                                             \n");
                sql.append("                                         FROM TZ_SUBJHOMEGUBUN                                                                          \n");
                sql.append("                                        WHERE GUBUN = 'GS'                                                                              \n");
                sql.append("                                          AND GUBUN_1 IN 'T0')                                                                          \n");
                sql.append("                    AND TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN A.PROPSTART AND A.PROPEND                                              \n");
                sql.append("                    AND A.GRCODE = 'N000001')                                                                                           \n");
                sql.append("          WHERE 1 = 1                                                                                                                   \n");
                sql.append("            AND COURSE_ID <> '" + box.getString("p_course_id") + "'                                                                \n");

                if (arr.length > 0) {
                    sql.append("            AND (                                                                                                                   \n");

                    rcnt = 0;
                    for (String str : arr) {
                        rcnt++;
                        str = str.replaceAll(" ", "");

                        if (rcnt == arr.length) {
                            sql.append("                    LINK_TEXT LIKE '%" + str + "%'                                                                         \n");
                        } else {
                            sql.append("                    LINK_TEXT LIKE '%" + str + "%' OR                                                                       \n");
                        }
                    }

                    sql.append("                )                                                                                                                   \n");
                }

                sql.append("        )                                                                                                                               \n");
                sql.append("  WHERE 1 = 1                                                                                                                           \n");

                if (arr.length > 0) {
                    sql.append("    AND (                                                                                                                           \n");

                    rcnt = 0;
                    for (String str : arr) {
                        rcnt++;
                        str = str.replaceAll(" ", "");

                        if (rcnt == arr.length) {
                            sql.append("                RNK" + str + "                                                                                              \n");
                        } else {
                            sql.append("                RNK" + str + " +                                                                                            \n");
                        }
                    }

                    sql.append("        ) > " + rnkLimit + "                                                                                                        \n");
                }

                sql.append("  ORDER BY CRDATE DESC, COURSE_NAME DESC                                                                                                \n");

                if (arr.length > 0) {
                    for (String str : arr) {
                        str = str.replaceAll(" ", "");
                        sql.append("         , RNK" + str + " DESC                                                                                                  \n");
                    }
                }
            } else if ("G".equals(box.getString("p_gubun"))) {
                sql.append(" SELECT *                                                                                                                               \n");
                sql.append("   FROM (SELECT A.TYPE                                                                                                                  \n");
                sql.append("              , A.COURSE_ID                                                                                                             \n");
                sql.append("              , A.YEAR                                                                                                                  \n");
                sql.append("              , A.SUBJSEQ                                                                                                               \n");
                sql.append("              , A.COURSE_NAME                                                                                                           \n");
                sql.append("              , A.IMGFILE AS IMG_PATH                                                                                                   \n");
                sql.append("              , B.GUBUN_2 AS G2CD                                                                                                       \n");
                sql.append("              , B.GUBUN_3 AS G3CD                                                                                                       \n");
                sql.append("	          , (SELECT X.CODENM                                                                                                        \n");
                sql.append("                   FROM TZ_CODE X                                                                                                       \n");
                sql.append("                  WHERE X.GUBUN  = '0110'                                                                                               \n");
                sql.append("                    AND X.LEVELS = 2                                                                                                    \n");
                sql.append("                    AND X.CODE   = B.GUBUN_2) AS G2NM                                                                                   \n");
                sql.append("	          , (SELECT X.CODENM                                                                                                        \n");
                sql.append("                   FROM TZ_CODE X                                                                                                       \n");
                sql.append("                  WHERE X.GUBUN  = '0110'                                                                                               \n");
                sql.append("                    AND X.LEVELS = 3                                                                                                    \n");
                sql.append("                    AND X.CODE   = B.GUBUN_3) AS G3NM                                                                                   \n");
                sql.append("	          , C.LVCODE AS LVCD                                                                                                        \n");
                sql.append("              , (SELECT X.CODENM                                                                                                        \n");
                sql.append("                   FROM TZ_CODE X                                                                                                       \n");
                sql.append("                  WHERE X.GUBUN  = '0121'                                                                                               \n");
                sql.append("                    AND X.LEVELS = 2                                                                                                    \n");
                sql.append("                    AND X.CODE   = C.LVCODE) AS LVNM                                                                                    \n");
                sql.append("              , D.VODURL AS VOD_URL                                                                                                     \n");
                sql.append("              , D.HEIGHT_S                                                                                                              \n");
                sql.append("              , D.WIDTH_S                                                                                                               \n");
                sql.append("              , D.CREATYEAR AS CRDATE                                                                                                   \n");
                sql.append("              , NULL AS GRSEQ                                                                                                           \n");
                sql.append("              , NULL AS GYEAR                                                                                                           \n");

                if (arr.length > 0) {
                    for (String str : arr) {
                        str = str.replaceAll(" ", "");
                        sql.append("              , CASE WHEN INSTR (A.LINK_TEXT, '" + str + "') > 0 THEN 1 ELSE 0 END AS RNK" + str + "                            \n");
                    }
                }

                sql.append("           FROM VZ_LINK_COURSE              A                                                                                           \n");
                sql.append("           LEFT JOIN TZ_GOLDHOMEGUBUN       B                                                                                           \n");
                sql.append("                  ON A.COURSE_ID = TO_CHAR(B.SEQ)                                                                                       \n");
                sql.append("                 AND B.GUBUN     = 'GC'                                                                                                 \n");
                sql.append("           LEFT JOIN TZ_GOLDHOMEGUBUN_LEVEL C                                                                                           \n");
                sql.append("                  ON A.COURSE_ID = TO_CHAR(C.SEQ)                                                                                       \n");
                sql.append("           LEFT JOIN TZ_GOLDCLASS           D                                                                                           \n");
                sql.append("                  ON A.COURSE_ID = TO_CHAR(D.SEQ)                                                                                       \n");
                sql.append("          WHERE A.COURSE_ID <> '" + box.getString("p_course_id") + "'                                                              \n");
                sql.append("            AND A.COURSE_ID NOT IN (SELECT DISTINCT X.SUBJ                                                                              \n");
                sql.append("                                      FROM TZ_SUBJHOMEGUBUN X                                                                           \n");
                sql.append("                                     WHERE X.GUBUN    = 'GS'                                                                            \n");
                sql.append("                                       AND X.GUBUN_1 IN 'T0')                                                                           \n");

                if (arr.length > 0) {
                    sql.append("            AND (                                                                                                                   \n");

                    rcnt = 0;
                    for (String str : arr) {
                        rcnt++;
                        str = str.replaceAll(" ", "");

                        if (rcnt == arr.length) {
                            sql.append("                        LINK_TEXT LIKE '%" + str + "%'                                                                      \n");
                        } else {
                            sql.append("                        LINK_TEXT LIKE '%" + str + "%' OR                                                                   \n");
                        }
                    }

                    sql.append("                )                                                                                                                   \n");
                }

                sql.append("        )                                                                                                                               \n");
                sql.append("  WHERE 1 = 1                                                                                                                           \n");

                if (arr.length > 0) {
                    sql.append("    AND (                                                                                                                           \n");

                    rcnt = 0;
                    for (String str : arr) {
                        rcnt++;
                        str = str.replaceAll(" ", "");

                        if (rcnt == arr.length) {
                            sql.append("             RNK" + str + "                                                                                                 \n");
                        } else {
                            sql.append("             RNK" + str + " +                                                                                               \n");
                        }
                    }

                    sql.append("        ) > " + rnkLimit + "                                                                                                        \n");
                }

                sql.append("  ORDER BY CRDATE DESC, COURSE_NAME DESC                                                                                                \n");

                if (arr.length > 0) {
                    for (String str : arr) {
                        str = str.replaceAll(" ", "");
                        sql.append("         , RNK" + str + " DESC                                                                                                  \n");
                    }
                }
            }

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
}
