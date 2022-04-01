//**********************************************************
//  1. 제      목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개      요:  관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수      정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.tutor;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.UploadUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class OffExpertHomePageBean {

	private ConfigSet config;
    private int row;

	public OffExpertHomePageBean() {
	    try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	}
	

	
	/**
	*  전문가 등록
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/
	 public int insert(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement 	pstmt 	= null;
		ListSet 			ls 		= null;
		StringBuffer 		sql 	= new StringBuffer();
		String 				seqSql	= "";
		int isOk = 0;
		int isOk2 = 0;
		
		int		v_seq               =  0;  
		String  v_gubun             = box.getString("p_gubun");  
		String  v_eng_name          = box.getString("p_eng_name");  
		String  v_comp              = box.getString("p_comp");  
		String  v_dept              = box.getString("p_dept");  
		String  v_comp_post1        = box.getString("p_comp_post1");  
		String  v_comp_post2        = box.getString("p_comp_post2");  
		String  v_comp_addr1        = box.getString("p_comp_addr1");  
		String  v_comp_addr2        = box.getString("p_comp_addr2");  
		String  v_comp_tel          = box.getString("p_comp_tel");  
		String  v_comp_fax          = box.getString("p_comp_fax");  
		String  v_mobile_phone      = box.getString("p_mobile_phone");  
		String  v_email             = box.getString("p_email");  
		String  v_living_place      = box.getString("p_living_place");  
		String  v_post1             = box.getString("p_post1");  
		String  v_post2             = box.getString("p_post2");  
		String  v_addr1             = box.getString("p_addr1");  
		String  v_addr2             = box.getString("p_addr2");  
		String  v_home_tel          = box.getString("p_home_tel");  
		String  v_education         = box.getString("p_education").equals("ALL") ? "" : box.getString("p_education") ;  
		Vector  v_special_field     = box.getVector("p_special_field");
		String  v_introduce         = box.getString("p_introduce");  
		String  v_approve_yn        = box.getStringDefault("p_approve_yn", "A");  // A : 신청대기  
		String  v_reg_yn            = box.getStringDefault("p_reg_yn", "N");  
		
		StringBuffer  v_special_fields	= new StringBuffer();
		
		String  s_userid            = box.getSession("userid");
		String  s_usernm            = box.getSession("name");
		String  s_resno            = box.getSession("resno");
		
		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			for(int i = 0 ; i < v_special_field.size() ; i++) {
				v_special_fields.append((String)v_special_field.get(i));
				v_special_fields.append("|");
			}
			
			seqSql  = "SELECT MAX(SEQ) FROM TZ_PROFESSIONAL";
			   ls = connMgr.executeQuery(seqSql);
			   if (ls.next()) {
				   v_seq = ls.getInt(1) + 1;
			   } else {
				   v_seq = 1;
			   }
			   
			isOk2 = UploadUtil.fnRegisterAttachFileForSingular(box); // 각 단일 첨부파일 업로드
			
			String v_photo_save_file    = box.getString("p_photo_file_savefile");	
			String v_photo_real_file    = box.getString("p_photo_file_realfile");
			String v_intro_save_file    = box.getString("p_intro_file_savefile");
			String v_intro_real_file	= box.getString("p_intro_file_realfile");

			sql.append(" INSERT INTO TZ_PROFESSIONAL                               \n ");
			sql.append(" (                                                         \n ");
			sql.append("     SEQ, GUBUN, NAME, ENG_NAME, COMP, DEPT                \n ");
			sql.append("     , RESNO, COMP_POST1, COMP_POST2, COMP_ADDR1           \n ");
			sql.append("     , COMP_ADDR2, COMP_TEL, COMP_FAX, MOBILE_PHONE        \n ");
			sql.append("     , EMAIL, LIVING_PLACE, POST1, POST2, ADDR1            \n ");
			sql.append("     , ADDR2, HOME_TEL, EDUCATION, SPECIAL_FIELD           \n ");
			sql.append("     , INTRODUCE, APPROVE_YN, REG_YN, PHOTO_REAL_FILE      \n ");
			sql.append("     , PHOTO_SAVE_FILE, INTRO_REAL_FILE, INTRO_SAVE_FILE   \n ");
			sql.append("     , LUSERID, INDATE, LDATE                              \n ");
			sql.append(" )                                                         \n ");
			sql.append(" VALUES                                                    \n ");
			sql.append(" (                                                         \n ");
			sql.append("     ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                       \n ");
			sql.append("     , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                        \n ");
			sql.append("     , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                        \n ");
			sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')                \n ");
			sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')                \n ");
			sql.append(" )                                                         \n ");

			pstmt = connMgr.prepareStatement(sql.toString());
			
			int index = 1;
			
			pstmt.setInt   (index++,  v_seq);              
			pstmt.setString(index++,  v_gubun          );
			pstmt.setString(index++,  s_usernm         );
			pstmt.setString(index++,  v_eng_name       );
			pstmt.setString(index++,  v_comp           );
			pstmt.setString(index++,  v_dept           );
			pstmt.setString(index++,  s_resno          );
			pstmt.setString(index++,  v_comp_post1     );
			pstmt.setString(index++,  v_comp_post2     );
			pstmt.setString(index++,  v_comp_addr1     );
			pstmt.setString(index++,  v_comp_addr2     );
			pstmt.setString(index++,  v_comp_tel       );
			pstmt.setString(index++,  v_comp_fax       );
			pstmt.setString(index++,  v_mobile_phone   );
			pstmt.setString(index++,  v_email          );
			pstmt.setString(index++,  v_living_place   );
			pstmt.setString(index++,  v_post1          );
			pstmt.setString(index++,  v_post2          );
			pstmt.setString(index++,  v_addr1          );
			pstmt.setString(index++,  v_addr2          );
			pstmt.setString(index++,  v_home_tel       );
			pstmt.setString(index++,  v_education      );
			pstmt.setString(index++,  v_special_fields.toString());
			pstmt.setString(index++,  v_introduce      );
			pstmt.setString(index++,  v_approve_yn     );
			pstmt.setString(index++,  v_reg_yn         );
			pstmt.setString(index++,  v_photo_real_file);
			pstmt.setString(index++,  v_photo_save_file);
			pstmt.setString(index++,  v_intro_real_file);
			pstmt.setString(index++,  v_intro_save_file);
			pstmt.setString(index++,  s_userid);
			
			isOk = pstmt.executeUpdate();
			
			if(isOk > 0 ){
				connMgr.commit();
				isOk =1;
			}
			else{
				connMgr.rollback();
				isOk =0;
			}
		}
		catch(Exception ex) {connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try {connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}
}
