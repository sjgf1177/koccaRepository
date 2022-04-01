package com.credu.infomation;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.scorm.StringUtil;

public class GoldClassGroupBean {

	
	/**
	 * 교육그룹별 열린강좌 사용 리스트
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectGroupList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        StringBuilder sb = new StringBuilder();
        ArrayList<DataBox> list = null;
        ListSet ls = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            sb.append("/* com.credu.infomation.GoldClassGroupBean selectGroupList(열린강좌 목록 조회) */\n");
            sb.append(" SELECT A.GRCODE, A.GRCODENM, A.DOMAIN,				\n");
		    sb.append("         (SELECT COUNT(0)							\n");
		    sb.append("            FROM TZ_GOLDCLASS_GRMNG					\n");
		    sb.append("           WHERE GRCODE = A.GRCODE) AS GOLDCLASS_CNT	\n");
		    sb.append("   FROM TZ_GRCODE A									\n");
		    sb.append("  WHERE A.GRCODE NOT IN ('N000001')					\n");
		    sb.append("    AND A.USEYN = 'Y'								\n");
		    sb.append("    AND DOMAIN IS NOT NULL							\n");
		    sb.append("  ORDER BY A.GRCODE									\n");
            

            ls = connMgr.executeQuery(sb.toString());


            while (ls.next()) {
                list.add(ls.getDataBox());
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
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
	 * 선택 교육그룹 정보 조회
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public DataBox selectGrcodeInfo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		StringBuilder sb = new StringBuilder();
		DataBox dbox = null;
		ListSet ls = null;
		
		String v_grcode = box.getString("p_grcode");
		
		try {
			connMgr = new DBConnectionManager();
			
			sb.append("/* com.credu.infomation.GoldClassGroupBean selectGrcodeInfo(선택 교육그룹 정보 조회) */\n");
			sb.append(" SELECT A.GRCODE, A.GRCODENM, A.DOMAIN				\n");
			sb.append("   FROM TZ_GRCODE A									\n");
			sb.append("  WHERE A.GRCODE = ("+SQLString.Format(v_grcode)+")	\n");
			
			
			ls = connMgr.executeQuery(sb.toString());
			while (ls.next()) {
				dbox = ls.getDataBox();
			}
			
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sb.toString());
			throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
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
	 * 등록 열린강좌 제외 목록
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectGoldclassList(RequestBox box) throws Exception {
		
		DBConnectionManager connMgr = null;
		StringBuilder sb = new StringBuilder();
		ArrayList<DataBox> list = null;
		ListSet ls = null;
		
		String v_grcode = box.getString("p_grcode");
		
		try {
			connMgr = new DBConnectionManager();
			
			list = new ArrayList<DataBox>();
			sb.append("/* com.credu.infomation.GoldClassGroupBean selectGoldclassList(등록 열린강좌 제외 목록) */\n");
			sb.append(" SELECT A.SEQ, A.LECNM								\n");
			sb.append("   FROM TZ_GOLDCLASS A								\n");
			sb.append("  WHERE A.USEYN = 'Y'								\n");
			sb.append("    AND NOT EXISTS (SELECT 1							\n");
			sb.append("                      FROM TZ_GOLDCLASS_GRMNG B		\n");
			sb.append("                     WHERE B.GRCODE = "+SQLString.Format(v_grcode)+"		\n");
			sb.append("                       AND B.SEQ = A.SEQ)			\n");
			sb.append("  ORDER BY A.SEQ										\n");
			
			
			
			ls = connMgr.executeQuery(sb.toString());
			
			
			while (ls.next()) {
				list.add(ls.getDataBox());
			}
			
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sb.toString());
			throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
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
	 * 등록 열린강좌 목록
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectGoldclassGroupList(RequestBox box) throws Exception {
		
		DBConnectionManager connMgr = null;
		StringBuilder sb = new StringBuilder();
		ArrayList<DataBox> list = null;
		ListSet ls = null;
		
		String v_grcode = box.getString("p_grcode");
		
		try {
			connMgr = new DBConnectionManager();
			
			list = new ArrayList<DataBox>();
			sb.append("/* com.credu.infomation.GoldClassGroupBean selectGoldclassGroupList(등록 열린강좌 목록) */\n");
			sb.append(" SELECT A.SEQ, B.LECNM							\n");
			sb.append("   FROM TZ_GOLDCLASS_GRMNG A						\n");
			sb.append("  INNER JOIN TZ_GOLDCLASS B						\n");
			sb.append("     ON A.SEQ = B.SEQ							\n");
			sb.append("  WHERE A.GRCODE = "+SQLString.Format(v_grcode)+"\n");
			sb.append("  ORDER BY A.SEQ									\n");
			
			ls = connMgr.executeQuery(sb.toString());
			
			
			while (ls.next()) {
				list.add(ls.getDataBox());
			}
			
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sb.toString());
			throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
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
	 * 교육그룹별 열린강좌 등록
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public int saveGoldclassList(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	StringBuffer sql = new StringBuffer();
    	
    	int isOk = 0;
    	int index = 1;
    	
    	String v_grcode = box.getString("p_grcode");
    	String seqArr[] = box.getStringArray("p_seqs");
    	String regId = box.getSession("userid");
    	
    	try {
    		
    		connMgr = new DBConnectionManager();
    		connMgr.setAutoCommit(false);
    		
    		sql.append("DELETE  TZ_GOLDCLASS_GRMNG 	\n");
    		sql.append(" WHERE  GRCODE = ?			\n");
    		
    		pstmt = connMgr.prepareStatement(sql.toString());
    		pstmt.setString(1, v_grcode);
    		pstmt.executeUpdate();
    		
    		pstmt.close();
    		pstmt = null;
    		
    		if(null != seqArr && seqArr.length > 0){
    			sql.setLength(0);
    			sql.append("/* com.credu.infomation.GoldClassGroupBean saveGoldclassList (교육그룹별 열린강좌 등록) */\n");
    			sql.append("INSERT INTO  TZ_GOLDCLASS_GRMNG (   \n");
    			sql.append("        SEQ      	\n");
    			sql.append("    ,   GRCODE     	\n");
    			sql.append("    ,   INP_DT     	\n");
    			sql.append("    ,   INP_USERID  \n");
    			sql.append("    ,   MOD_DT     	\n");
    			sql.append("    ,   MOD_USERID  \n");
    			sql.append(") VALUES (          \n");
    			sql.append("        ?           \n");
    			sql.append("    ,   ?           \n");
    			sql.append("    ,   SYSDATE     \n");
    			sql.append("    ,   ?           \n");
    			sql.append("    ,   SYSDATE     \n");
    			sql.append("    ,   ?           \n");
    			sql.append(")                   \n");
    			
    			pstmt = connMgr.prepareStatement(sql.toString());
    			
    			for ( int i = 0; i < seqArr.length; i++ ) {
    				index = 1;
    				pstmt.setString(index++, seqArr[i]);
    				pstmt.setString(index++, v_grcode);
    				pstmt.setString(index++, regId);
    				pstmt.setString(index++, regId);
    				
    				pstmt.addBatch();
    			}
    			
    			pstmt.executeBatch();
    		}
    		
    		isOk = 1;
			connMgr.commit();
    	} catch (Exception ex) {
    		isOk = 0;
    		if(connMgr != null){
    			connMgr.rollback();
    		}
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
    	return isOk;
    }
	
	
	public ArrayList insertGoldclassExcel(RequestBox box) throws Exception {
        Sheet 				sheet 	 = null;
        Workbook 			workbook = null;
        ListSet 			ls 		 = null;
        ConfigSet 			conf 	 = null;
        ArrayList 			list 	 = null;
        StringBuffer 		query 	 = null;
        DBConnectionManager connMgr  = null;
        PreparedStatement	pstmt	 = null;
        DataBox				dbox	 = null;
    	
    	String v_newFileName  = box.getNewFileName("p_file");
    	
        String v_type		= box.getString("p_type");		//등록구분
        
        String v_luserid 	= box.getSession("userid");
        
        int isOk[] = null;
        int failCnt = 0;
        int celIndex = 0;
        int pstmtIndex = 1;
        
		String v_grcode			= "";
		String v_seq	 		= "";
    	
    	try{
    		conf = new ConfigSet();
    		list = new ArrayList();
    		query = new StringBuffer();
    		connMgr = new DBConnectionManager();
    		connMgr.setAutoCommit(false);
    		
            String fullName=conf.getProperty("dir.home")+v_newFileName;
            
            workbook = Workbook.getWorkbook(new File(fullName));            
            sheet = workbook.getSheet(0);
            
            
            //insert TZ_Grseq table
            query.setLength(0);
            query.append("\n MERGE INTO TZ_GOLDCLASS_GRMNG A									");
            query.append("\n USING ( SELECT ? AS GRCODE, ? AS SEQ, ? AS REG_ID FROM DUAL ) B	");
            query.append("\n    ON (A.GRCODE = B.GRCODE AND A.SEQ = B.SEQ)						");
            query.append("\n  WHEN MATCHED THEN													");
            query.append("\n         UPDATE 													");
            query.append("\n            SET MOD_DT = SYSDATE,									");
            query.append("\n                MOD_USERID = B.REG_ID								");
            query.append("\n  WHEN NOT MATCHED THEN												");
            query.append("\n         INSERT (													");
            query.append("\n             SEQ,													");
            query.append("\n             GRCODE,												");
            query.append("\n             INP_DT,												");
            query.append("\n             INP_USERID,											");
            query.append("\n             MOD_DT,												");
            query.append("\n             MOD_USERID												");
            query.append("\n         )															");
            query.append("\n         VALUES (													");
            query.append("\n             B.SEQ,													");
            query.append("\n             B.GRCODE,												");
            query.append("\n             SYSDATE,												");
            query.append("\n             B.REG_ID,												");
            query.append("\n             SYSDATE,												");
            query.append("\n             B.REG_ID												");
            query.append("\n         )															");
            
            pstmt = connMgr.prepareStatement(query.toString());
            
            
            for (int i=1; i < sheet.getRows(); i++ ) {
            	celIndex = 0;
				v_grcode	= sheet.getCell(celIndex++,i).getContents();
				v_seq		= sheet.getCell(celIndex++,i).getContents();
                
				dbox = new DataBox(null);
				dbox.put("d_line", 		i + 1);
				dbox.put("d_grcode", 	v_grcode);
				dbox.put("d_seq", 		v_seq);
				
				
	            if("".equals(v_grcode)){
	            	dbox.put("d_msg",	"ERROR01");
	            	list.add(dbox);
	            	failCnt++;
	            	continue;
	            }
	            
	            if("".equals(v_seq)){
	            	dbox.put("d_msg",	"ERROR02");
	            	list.add(dbox);
	            	failCnt++;
	            	continue;
	            }
	            	
	            
	            pstmtIndex = 1;
    			pstmt.setString(pstmtIndex++, v_grcode);
    			pstmt.setString(pstmtIndex++, v_seq);
    			pstmt.setString(pstmtIndex++, v_luserid);
	    			
    			pstmt.addBatch();
    			pstmt.clearParameters();
	            
        		dbox.put("d_msg",	"SUCCESS");
        		
        		
        		
				list.add(dbox);
            }
            isOk = pstmt.executeBatch();
            
            
            if(v_type.equals("Ins") && isOk.length > 0 && failCnt == 0){
            	connMgr.commit();
            }else{
            	connMgr.rollback();
            }
    	}catch(Exception ex){
    		connMgr.rollback();
    		ex.printStackTrace();
    		ErrorManager.getErrorStackTrace(ex, box, query.toString());
            throw new Exception("sql = " + query.toString() + "\r\n" + ex.getMessage());
    	}finally{
    		if(v_newFileName != null && !v_newFileName.equals("")){
    			try{
    				FileManager.deleteFile(v_newFileName);
    			}catch(Exception e){}
    		}
    		if(pstmt != null){
    			try{
    				pstmt.close();
    			}catch(Exception e){}
    		}
    		if(ls != null){
    			try{
    				ls.close();
    			}catch(Exception e){}
    		}
    		if(workbook != null){
    			try{
    				workbook.close();
    			}catch(Exception e){}
    		}
    		if(connMgr != null){
    			try{
    				connMgr.setAutoCommit(true);
    				connMgr.freeConnection();
    			}catch(Exception e){}
    		}
    	}
    	return list;
    }
}
