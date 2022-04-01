package com.credu.course;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class EduSystemChartBean {

	public EduSystemChartBean() { }
	
	
	/**
	 * 교육체계도 목록 
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList list(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuffer strQuery = null;
		ArrayList list = null;
		ListSet ls = null;
		
		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();
			
			strQuery = new StringBuffer();
			strQuery.append(" /* EduSystemChartBean.list 교육체계도 목록  */ \n");
			strQuery.append("SELECT                                                                          \n");
			strQuery.append("    A.CHARTCODE AS CODEA,                                                       \n");
			strQuery.append("    B.CHARTCODE AS CODEB,                                                       \n");
/*			strQuery.append("    C.CHARTCODE AS CODEC,                                                       \n");
			strQuery.append("    D.CHARTCODE AS CODED,                                                       \n");
			strQuery.append("    E.CHARTCODE AS CODEE,                                                       \n");*/
			strQuery.append("    A.CHARTNAME AS CODENAMEA,                                                   \n");
			strQuery.append("    B.CHARTNAME AS CODENAMEB,                                                   \n");
/*			strQuery.append("    C.CHARTNAME AS CODENAMEC,                                                   \n");
			strQuery.append("    D.CHARTNAME AS CODENAMED,                                                   \n");
			strQuery.append("    E.CHARTNAME AS CODENAMEE,                                                   \n");*/
			strQuery.append("    (SELECT                                                                     \n");
			strQuery.append("        ISNULL(COUNT(*), 0)                                                     \n");
			strQuery.append("    FROM                                                                        \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE IN ('0')) T1      \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T2 \n");
			strQuery.append("        ON T1.CHARTCODE = T2.UPPERCHARTCODE                                     \n");
/*			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T3 \n");
			strQuery.append("        ON T2.CHARTCODE = T3.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T4 \n");
			strQuery.append("        ON T3.CHARTCODE = T4.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T5 \n");
			strQuery.append("        ON T4.CHARTCODE = T5.UPPERCHARTCODE                                     \n");*/
			strQuery.append("    WHERE                                                                       \n");
			strQuery.append("        T1.CHARTCODE = A.CHARTCODE) AS ACNT,                                    \n");
			strQuery.append("    (SELECT                                                                     \n");
			strQuery.append("        ISNULL(COUNT(*), 0)                                                     \n");
			strQuery.append("    FROM                                                                        \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE IN ('0')) T1      \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T2 \n");
			strQuery.append("        ON T1.CHARTCODE = T2.UPPERCHARTCODE                                     \n");
/*			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T3 \n");
			strQuery.append("        ON T2.CHARTCODE = T3.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T4 \n");
			strQuery.append("        ON T3.CHARTCODE = T4.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T5 \n");
			strQuery.append("        ON T4.CHARTCODE = T5.UPPERCHARTCODE                                     \n");*/
			strQuery.append("    WHERE                                                                       \n");
			strQuery.append("        T2.CHARTCODE = B.CHARTCODE) AS BCNT                                     \n");
/*			strQuery.append("    (SELECT                                                                     \n");
			strQuery.append("        ISNULL(COUNT(*), 0)                                                     \n");
			strQuery.append("    FROM                                                                        \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE IN ('0')) T1      \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T2 \n");
			strQuery.append("        ON T1.CHARTCODE = T2.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T3 \n");
			strQuery.append("        ON T2.CHARTCODE = T3.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T4 \n");
			strQuery.append("        ON T3.CHARTCODE = T4.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T5 \n");
			strQuery.append("        ON T4.CHARTCODE = T5.UPPERCHARTCODE                                     \n");
			strQuery.append("    WHERE                                                                       \n");
			strQuery.append("        T3.CHARTCODE = C.CHARTCODE) AS CCNT,                                    \n");
			strQuery.append("    (SELECT                                                                     \n");
			strQuery.append("        ISNULL(COUNT(*), 0)                                                     \n");
			strQuery.append("    FROM                                                                        \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE IN ('0')) T1      \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T2 \n");
			strQuery.append("        ON T1.CHARTCODE = T2.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T3 \n");
			strQuery.append("        ON T2.CHARTCODE = T3.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T4 \n");
			strQuery.append("        ON T3.CHARTCODE = T4.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T5 \n");
			strQuery.append("        ON T4.CHARTCODE = T5.UPPERCHARTCODE                                     \n");
			strQuery.append("    WHERE                                                                       \n");
			strQuery.append("        T4.CHARTCODE = D.CHARTCODE) AS DCNT,                                    \n");
			strQuery.append("    (SELECT                                                                     \n");
			strQuery.append("        ISNULL(COUNT(*), 0)                                                     \n");
			strQuery.append("    FROM                                                                        \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE IN ('0')) T1      \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T2 \n");
			strQuery.append("        ON T1.CHARTCODE = T2.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T3 \n");
			strQuery.append("        ON T2.CHARTCODE = T3.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T4 \n");
			strQuery.append("        ON T3.CHARTCODE = T4.UPPERCHARTCODE                                     \n");
			strQuery.append("        LEFT OUTER JOIN                                                         \n");
			strQuery.append("        (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) T5 \n");
			strQuery.append("        ON T4.CHARTCODE = T5.UPPERCHARTCODE                                     \n");
			strQuery.append("    WHERE                                                                       \n");
			strQuery.append("        T5.CHARTCODE = E.CHARTCODE) AS ECNT                                     \n");*/
			strQuery.append("    FROM                                                                        \n");
			strQuery.append("    (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE IN ('0')) A           \n");
			strQuery.append("    LEFT OUTER JOIN                                                             \n");
			strQuery.append("    (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) B       \n");
			strQuery.append("    ON A.CHARTCODE = B.UPPERCHARTCODE                                           \n");
/*			strQuery.append("    LEFT OUTER JOIN                                                             \n");
			strQuery.append("    (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) C       \n");
			strQuery.append("    ON B.CHARTCODE = C.UPPERCHARTCODE                                           \n");
			strQuery.append("    LEFT OUTER JOIN                                                             \n");
			strQuery.append("    (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) D       \n");
			strQuery.append("    ON C.CHARTCODE = D.UPPERCHARTCODE                                           \n");
			strQuery.append("    LEFT OUTER JOIN                                                             \n");
			strQuery.append("    (SELECT * FROM TZ_EDUSYSTEMCHART WHERE UPPERCHARTCODE NOT IN ('0')) E       \n");
			strQuery.append("    ON D.CHARTCODE = E.UPPERCHARTCODE                                           \n");
			strQuery.append("ORDER BY A.MAXUPPERCHARTCODE ASC, A.UPPERCHARTCODE ASC, A.CHARTORDER ASC, B.CHARTORDER ASC, C.CHARTORDER ASC, D.CHARTORDER ASC, E.CHARTORDER ASC        \n");*/
			strQuery.append("ORDER BY A.MAXUPPERCHARTCODE ASC, A.UPPERCHARTCODE ASC, A.CHARTORDER ASC, B.CHARTORDER ASC       \n");

			ls = connMgr.executeQuery(strQuery.toString());

			while (ls.next()) {
				list.add(ls.getDataBox());
			}
			
		
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
			throw new Exception("SQL = " + strQuery.toString() + "\r\n" + ex.getMessage());
		} finally {
			if (ls != null) { try { ls.close(); } catch (Exception e) { } }
			if (connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) { } }
		}
		return list;
	}
	
	
	/**
	 * 하위 교육체계분류코드 목록 
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList uLChartCodeList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuffer strQuery = null;
		ArrayList list = null;
		ListSet ls = null;
		
		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();
			
			strQuery = new StringBuffer();
			strQuery.append(" /* EduSystemChartBean.upperChartCodeList 상,하위 교육체계분류코드 목록 */ \n");
			strQuery.append("SELECT								\n");
			strQuery.append("    A.CHARTCODE,					\n");
			strQuery.append("    A.CHARTNAME,					\n");
			strQuery.append("    A.LUSERID,						\n");
			strQuery.append("    A.LDATE,						\n");
			strQuery.append("    A.UPPERCHARTCODE,				\n");
			strQuery.append("    A.CHARTORDER,					\n");
			strQuery.append("    A.USEYN,						\n");
			strQuery.append("    A.MAXUPPERCHARTCODE			\n");
			strQuery.append("FROM								\n");
			strQuery.append("    TZ_EDUSYSTEMCHART A			\n");
			strQuery.append("WHERE								\n");
			strQuery.append("    A.USEYN IN ('Y')				\n");
			
			//하위분류일 경우
			if(box.getInt("p_codeselect") > 1){
				strQuery.append("    AND A.UPPERCHARTCODE IN ("+StringManager.makeSQL(box.getString("p_upperchartcode"))+")	\n");
				
			//상위분류일 경우
			}else{
				strQuery.append("    AND A.UPPERCHARTCODE IN ('0')	\n");
				
			}
			strQuery.append("ORDER BY A.CHARTORDER ASC			\n");
			
			ls = connMgr.executeQuery(strQuery.toString());
			
			while (ls.next()) {
				list.add(ls.getDataBox());
			}
			
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
			throw new Exception("SQL = " + strQuery + "\r\n" + ex.getMessage());
		} finally {
			if (ls != null) { try { ls.close(); } catch (Exception e) { } }
			if (connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) { } }
		}
		return list;
	}
	
	
	
	/**
	 * 교육체계도 분류 등록
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public int insert(RequestBox box) throws Exception { 
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer strQuery = null;
		ListSet ls = null;
		
		int index = 1;
		int result = 0;
		
		int v_codegubun = 0;
		String v_maxcode = "";
		v_codegubun = box.getInt("p_codegubun");
		
		try { 
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			strQuery = new StringBuffer();
			strQuery.append("SELECT ISNULL(MAX(A.CHARTCODE), '100000') AS MAXCODE	\n");
			strQuery.append("FROM   TZ_EDUSYSTEMCHART A							    \n");

			ls = connMgr.executeQuery(strQuery.toString());
			if(ls.next()){
				v_maxcode = ls.getString("maxcode");
			}
			
			if(v_maxcode != null && !"".equals(v_maxcode)){
				v_maxcode = this.addZero((Integer.parseInt(v_maxcode) + 1), 6);
			}
			
			//자원 등록
			//상위코드 등록인 경우(UPPER)
			if(1 == v_codegubun){
				box.put("p_maxupperchartcode", v_maxcode);
				box.put("p_upperchartcode", "0");
				
			}else if(2 == v_codegubun){
				box.put("p_maxupperchartcode", box.getString("p_maxupperchart").split("_")[1]);
				box.put("p_upperchartcode", box.getString("p_maxupperchart").split("_")[1]);
				
			}else if(2 < v_codegubun){
				box.put("p_maxupperchartcode", box.getString("p_upperchartcode_"+box.getString("p_lowchartgubun")).split("_")[0]);
				box.put("p_upperchartcode", box.getString("p_upperchartcode_"+box.getString("p_lowchartgubun")).split("_")[1]);
				
			}
			box.put("p_chartname", box.getString("p_chartname").trim());
			box.put("p_chartorder", box.getString("p_chartorder").trim());
			
			//교육체계분류 코드 등록
			strQuery.setLength(0);
			strQuery.append("INSERT INTO TZ_EDUSYSTEMCHART   	\n");
			strQuery.append("(								  	\n");
			strQuery.append("    CHARTCODE,						\n");
			strQuery.append("    CHARTNAME,						\n");
			strQuery.append("    LUSERID,						\n");
			strQuery.append("    LDATE,							\n");
			strQuery.append("    UPPERCHARTCODE,				\n");
			strQuery.append("    MAXUPPERCHARTCODE,				\n");
			strQuery.append("    CHARTORDER,					\n");
			strQuery.append("    USEYN,							\n");
			strQuery.append("    LEVELS,						\n");
			strQuery.append("    SUMMARY,						\n");
			strQuery.append("    JOBAREA,						\n");
			strQuery.append("    CAPACITY						\n");
			strQuery.append(")									\n");
			strQuery.append("VALUES 							\n");
			strQuery.append("(		 							\n");
			strQuery.append("    ?,								\n");
			strQuery.append("    ?,								\n");
			strQuery.append("    ?,								\n");
			strQuery.append("    TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),\n");
			strQuery.append("    ?,								\n");
			strQuery.append("    ?,								\n");
			strQuery.append("    ?,								\n");
			strQuery.append("    ?,								\n");
			strQuery.append("    ?,								\n");
			strQuery.append("    ?,								\n");
			strQuery.append("    ?,								\n");
			strQuery.append("    ?								\n");
			strQuery.append(")									\n");

			pstmt = connMgr.prepareStatement(strQuery.toString());
			
			index = 1;
			pstmt.setString(index++, v_maxcode);
			pstmt.setString(index++, box.getString("p_chartname"));
			pstmt.setString(index++, box.getSession("userid"));
			pstmt.setString(index++, box.getString("p_upperchartcode"));
			pstmt.setString(index++, box.getString("p_maxupperchartcode"));
			pstmt.setString(index++, box.getString("p_chartorder"));
			pstmt.setString(index++, box.getString("p_useyn"));
			pstmt.setInt(index++, v_codegubun);
			pstmt.setString(index++, box.getString("p_summary"));
			pstmt.setString(index++, box.getString("p_jobarea"));
			pstmt.setString(index++, box.getString("p_capacity"));
	
			result = pstmt.executeUpdate();
			
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
	 * 교육체계도 분류 수정
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public int update(RequestBox box) throws Exception { 
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer strQuery = null;
		
		int index = 1;
		int result = 0;
		
		try { 
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			//자원 등록
			box.put("p_chartname", box.getString("p_chartname").trim());
			box.put("p_chartorder", box.getString("p_chartorder").trim());
			
			//교육체계분류 코드 등록
			strQuery = new StringBuffer();
			strQuery.append("UPDATE TZ_EDUSYSTEMCHART SET						\n");
			strQuery.append("    CHARTNAME = ?,									\n");
			strQuery.append("    LUSERID = ?,									\n");
			strQuery.append("    LDATE = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),	\n");
			strQuery.append("    CHARTORDER = ?,								\n");
			strQuery.append("    USEYN = ?,										\n");
			strQuery.append("    SUMMARY = ?,									\n");
			strQuery.append("    JOBAREA = ?,									\n");
			strQuery.append("    CAPACITY = ?									\n");
			strQuery.append("WHERE												\n");
			strQuery.append("    CHARTCODE IN (?)								\n");
			
			pstmt = connMgr.prepareStatement(strQuery.toString());
			index = 1;
			pstmt.setString(index++, box.getString("p_chartname"));
			pstmt.setString(index++, box.getSession("userid"));
			pstmt.setString(index++, box.getString("p_chartorder"));
			pstmt.setString(index++, box.getString("p_useyn"));
			pstmt.setString(index++, box.getString("p_summary"));
			pstmt.setString(index++, box.getString("p_jobarea"));
			pstmt.setString(index++, box.getString("p_capacity"));
			pstmt.setString(index++, box.getString("p_chartcode"));
			
			result = pstmt.executeUpdate();
			
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
	 * 교육체계도 분류 삭제
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public int delete(RequestBox box) throws Exception { 
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer strQuery = null;
		ListSet ls = null;
		
		int index = 1;
		int result = 0;
		
		int v_chartcodecnt = 0;
		String v_chartcode = "";
		
		v_chartcode = box.getString("p_chartcode"); 
		
		try { 
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			strQuery = new StringBuffer();
			strQuery.append("SELECT ISNULL(COUNT(CHARTCODE), 0) AS CHARTCODECNT					\n");
			strQuery.append("FROM TZ_EDUSYSTEMCHART												\n");
			strQuery.append("WHERE UPPERCHARTCODE IN ("+StringManager.makeSQL(v_chartcode)+")	\n");
			
			ls = connMgr.executeQuery(strQuery.toString());
			if(ls.next()){
				v_chartcodecnt = ls.getInt("chartcodecnt");
			}
			
			//하위분류코드가 존재하는 경우 삭제할 수 없도록 함
			if(v_chartcodecnt > 0){
				
				box.put("system_msg", "해당 분류코드의 하위분류코드가 존재하여 삭제할 수 없습니다!");
				result = -1;
				return result;
				
				
			}else{
				
				//교육체계분류 코드 등록
				strQuery.setLength(0);
				strQuery.append("DELETE FROM TZ_EDUSYSTEMCHART \n");
				strQuery.append("WHERE CHARTCODE IN (?)        \n");

				pstmt = connMgr.prepareStatement(strQuery.toString());
				index = 1;
				pstmt.setString(index++, v_chartcode);
				
				result = pstmt.executeUpdate();
				
				
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
	 * 자리수 설정
	 * @param chkNumber
	 * @param chkLen
	 * @return
	 */
	public String addZero (int chkNumber, int chkLen){
		String temp = null;
		temp = String.valueOf(chkNumber);
		int len = temp.length();

		if (len < chkLen){
			for(int i=1; i<=(chkLen-len); i++) {
				temp = "0" + temp;
			}
		}
		return temp;
	}
	
	
	
	
	/**
	 * 교육체계분류코드 정보
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public DataBox view(RequestBox box) throws Exception { 
		DBConnectionManager connMgr = null;
		StringBuffer strQuery = null;
		DataBox data = null;
		ListSet ls = null;

		try { 
		connMgr = new DBConnectionManager();
		
		strQuery = new StringBuffer();
		strQuery.append("SELECT                         \n");
		strQuery.append("    A.CHARTCODE,               \n");
		strQuery.append("    A.CHARTNAME,               \n");
		strQuery.append("    A.LUSERID,                 \n");
		strQuery.append("    A.LDATE,                   \n");
		strQuery.append("    A.UPPERCHARTCODE,          \n");
		strQuery.append("    A.CHARTORDER,              \n");
		strQuery.append("    A.USEYN,                   \n");
		strQuery.append("    A.SUMMARY,					\n");
		strQuery.append("    A.JOBAREA,					\n");
		strQuery.append("    A.CAPACITY,				\n");
		strQuery.append("    A.MAXUPPERCHARTCODE,       \n");
		strQuery.append("    (SELECT CHARTNAME FROM TZ_EDUSYSTEMCHART WHERE CHARTCODE = A.UPPERCHARTCODE) AS UPPERCHARTNAME	\n");
		strQuery.append("FROM                           \n");
		strQuery.append("    TZ_EDUSYSTEMCHART A        \n");
		strQuery.append("WHERE                          \n");
		strQuery.append("    A.CHARTCODE IN ("+StringManager.makeSQL(box.getString("p_chartcode"))+") \n");

		ls = connMgr.executeQuery(strQuery.toString());

		if(ls.next()){
			data = ls.getDataBox();
		}

		} catch(Exception ex ) { 
			ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
			throw new Exception("SQL = " + strQuery.toString() + "\r\n" + ex.getMessage() );

		} finally { 
			if(ls != null ) { try { ls.close(); } catch(Exception e ) { }}
			if(connMgr != null ) { try { connMgr.freeConnection(); } catch(Exception e10 ) { } }
		}
		return data;
	}
	
	
	
	/**
	 * B2C 전체과정 목록 
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList subjAll(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuffer strQuery = null;
		ArrayList list = null;
		ListSet ls = null;
		
		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();
			
			strQuery = new StringBuffer();
			strQuery.append(" /* EduSystemChartBean.subjAll B2C 전체과정 목록 */	\n");
			strQuery.append("SELECT                                                                                        \n");
			strQuery.append("    A.SUBJ,                                                                                   \n");
			strQuery.append("    A.SUBJNM                                                                                  \n");
			strQuery.append("FROM                                                                                          \n");
			strQuery.append("    VZ_SCSUBJSEQIMGMOBILE A                                                                   \n");
			strQuery.append("WHERE                                                                                         \n");
			strQuery.append("    A.GRCODE IN ('N000001')                                                                   \n");
			strQuery.append("    AND A.ISUSE IN ('Y')                                                                      \n");
			strQuery.append("    AND TO_CHAR(SYSDATE,'YYYYMM') BETWEEN REPLACE(SUBSTR(A.PROPSTART,1,6),'.','') AND REPLACE(SUBSTR(A.PROPEND,1,6),'.','') \n");
			strQuery.append("    AND A.SUBJ NOT IN ( SELECT SUBJ FROM TZ_SUBJ WHERE SUBJ BETWEEN 'CK14014' AND 'CK14048' OR ( A.SUBJ BETWEEN 'CK15003' AND 'CK15030' )) \n");
			strQuery.append("    AND A.SUBJ NOT IN (SELECT SUBJ FROM TZ_EDUSYSTEMCHARTSUBJ WHERE CHARTCODE IN ("+StringManager.makeSQL(box.getString("p_chartcode"))+")) \n");
			strQuery.append("ORDER BY A.SUBJ ASC                                                                        \n");

			ls = connMgr.executeQuery(strQuery.toString());
			
			while (ls.next()) {
				list.add(ls.getDataBox());
			}
			
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
			throw new Exception("SQL = " + strQuery + "\r\n" + ex.getMessage());
		} finally {
			if (ls != null) { try { ls.close(); } catch (Exception e) { } }
			if (connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) { } }
		}
		return list;
	}
	
	
	/**
	 * 교육체계도와 연결된 과정 목록 
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList subjSelect(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuffer strQuery = null;
		ArrayList list = null;
		ListSet ls = null;
		
		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();
			
			strQuery = new StringBuffer();
			strQuery.append(" /* EduSystemChartBean.subjSelect 교육체계도와 연결된 과정 목록 */	\n");
			strQuery.append("SELECT                                            \n");
			strQuery.append("    A.CHARTCODE,                                  \n");
			strQuery.append("    A.SUBJ,                                       \n");
			strQuery.append("    B.SUBJNM                                      \n");
			strQuery.append("FROM                                              \n");
			strQuery.append("    TZ_EDUSYSTEMCHARTSUBJ A INNER JOIN TZ_SUBJ B \n");
			strQuery.append("    ON A.SUBJ = B.SUBJ                            \n");
			strQuery.append("WHERE                                             \n");
			strQuery.append("    A.CHARTCODE IN ("+StringManager.makeSQL(box.getString("p_chartcode"))+")                     \n");
			strQuery.append("ORDER BY B.SUBJNM ASC, B.SUBJ ASC                 \n");

			
			ls = connMgr.executeQuery(strQuery.toString());
			
			while (ls.next()) {
				list.add(ls.getDataBox());
			}
			
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, strQuery.toString());
			throw new Exception("SQL = " + strQuery + "\r\n" + ex.getMessage());
		} finally {
			if (ls != null) { try { ls.close(); } catch (Exception e) { } }
			if (connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) { } }
		}
		return list;
	}
	
	
	
	
	
	/**
	 * 교육체계도 과정연결
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public int subjCourseUpdate(RequestBox box) throws Exception { 
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer strQuery = null;
		
		int index = 1;
		int result = 0;
		
		String v_chartcode = "";
		v_chartcode = box.getString("p_chartcode"); 
		
		try { 
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			strQuery = new StringBuffer();
			strQuery.append("DELETE FROM TZ_EDUSYSTEMCHARTSUBJ	\n");
			strQuery.append("WHERE CHARTCODE IN (?)			\n");
			
			pstmt = connMgr.prepareStatement(strQuery.toString());
			index = 1;
			pstmt.setString(index++, v_chartcode);
			
			result = pstmt.executeUpdate();
			
			if(result > -1){
				
				Vector<String> v_subjSelect = box.getVector("subjSelect");
				if(v_subjSelect != null && v_subjSelect.size() > 0){
					
					//2.선택된 과정  추가
					for(String subj : v_subjSelect){
						
						strQuery.setLength(0);
						strQuery.append("INSERT INTO TZ_EDUSYSTEMCHARTSUBJ (	\n");
						strQuery.append("    SUBJ,		\n");
						strQuery.append("    CHARTCODE,	\n");
						strQuery.append("    LUSERID,	\n");
						strQuery.append("    LDATE		\n");
						strQuery.append(")				\n");
						strQuery.append("VALUES (		\n");
						strQuery.append("    ?,			\n");
						strQuery.append("    ?,			\n");
						strQuery.append("    ?,			\n");
						strQuery.append("    TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')	\n");
						strQuery.append(")											\n");
						
						pstmt = connMgr.prepareStatement(strQuery.toString());
						
						index = 1;
						pstmt.setString(index++, subj);
						pstmt.setString(index++, v_chartcode);
						pstmt.setString(index++, box.getSession("userid"));
				
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
	
	
	
	
	
	
	
	
	
	
	
	
	


}
