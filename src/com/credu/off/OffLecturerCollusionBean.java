//**********************************************************
//  1. 제      목: HomePage 운영자에게 관리
//  2. 프로그램명: HomePageQnaBean.java
//  3. 개      요: 운영자에게 관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 6.  23
//  7. 수      정: 이나연 05.11.16 _ connMgr.setOracleCLOB 수정
//**********************************************************
package com.credu.off;

import java.io.StringReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.PageList;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.CodeData;
import com.dunet.common.util.StringUtil;
import com.dunet.common.util.UploadUtil; 
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OffLecturerCollusionBean {
    private ConfigSet config;
    private int row;
    private String v_type = "MM";
	private	static final String	FILE_TYPE =	"p_file1";			//		파일업로드되는 프로필사진
	private	static final String	FILE_TYPE2 = "p_file2";			//		파일업로드되는 강의계획서
	private	static final String	FILE_TYPE3 = "p_file3";
	private	static final int FILE_LIMIT	= 3;					//	  페이지에 세팅된 파일첨부 갯수
    public static int getFILE_LIMIT() {
    	return FILE_LIMIT;
    }
    
    public int insert(RequestBox box) throws Exception {
     	  DBConnectionManager connMgr 	= null;
          ListSet ls 					= null;
          PreparedStatement pstmt1 		= null;
          PreparedStatement pstmt2 		= null;
          StringBuffer sql 				= new StringBuffer();
          StringBuffer sql2 			= new StringBuffer();
          
          int isOk = 0;
          int isOk1 = 0;
          int isOk2 = 0;
          int isOk3 = 0;
          
          // 기본정보
          String v_name		 = box.getString("name");
          String v_reg7		 = box.getString("reg7");
          String v_compnm	 = box.getString("compnm");
          String v_deptnm	 = box.getString("deptnm");
          String v_compaddr	 = box.getString("compaddr");
          String v_homeaddr	 = box.getString("homeaddr");
          String v_comptel	 = box.getString("comptel");
          String v_fax		 = box.getString("fax");
          String v_hometel	 = box.getString("hometel");
          String v_handphone = box.getString("handphone");
          String v_email	 = box.getString("email");
          String v_worknbook = box.getString("worknbook");
          String v_subjgubun = box.getString("p_subjgubun");
          String v_etc		 = box.getString("etc");
          String v_lectgubun = box.getString("lectgubun");
          
          String [] v_gubun = new String [9];
          int [] v_gubunseq = new int [9];
          String [] v_startdate = new String [9];
          String [] v_enddate = new String [9];
          String [] v_contents = new String [9];
          String [] v_position = new String [9];
          
          v_gubun[0] = "H";v_gubun[1] = "H";v_gubun[2] = "H";
          v_gubun[3] = "C";v_gubun[4] = "C";v_gubun[5] = "C";
          v_gubun[6] = "P";v_gubun[7] = "P";v_gubun[8] = "P";
          
          v_gubunseq[0] = 1;v_gubunseq[1] = 2;v_gubunseq[2] = 3;
          v_gubunseq[3] = 1;v_gubunseq[4] = 2;v_gubunseq[5] = 3;
          v_gubunseq[6] = 1;v_gubunseq[7] = 2;v_gubunseq[8] = 3;
          
          v_startdate[0] = box.getString("h_edustart1");
          v_startdate[1] = box.getString("h_edustart2");
          v_startdate[2] = box.getString("h_edustart3");
          v_startdate[3] = box.getString("c_edustart1");
          v_startdate[4] = box.getString("c_edustart2");
          v_startdate[5] = box.getString("c_edustart3");
          v_startdate[6] = box.getString("p_edustart1");
          v_startdate[7] = box.getString("p_edustart2");
          v_startdate[8] = box.getString("p_edustart3");
          
          v_enddate[0] = box.getString("h_eduend1");
          v_enddate[1] = box.getString("h_eduend2");
          v_enddate[2] = box.getString("h_eduend3");
          v_enddate[3] = box.getString("c_eduend1");
          v_enddate[4] = box.getString("c_eduend2");
          v_enddate[5] = box.getString("c_eduend3");
          v_enddate[6] = box.getString("p_eduend1");
          v_enddate[7] = box.getString("p_eduend2");
          v_enddate[8] = box.getString("p_eduend3");
          
          v_contents[0] = box.getString("h_contents1");
          v_contents[1] = box.getString("h_contents2");
          v_contents[2] = box.getString("h_contents3");
          v_contents[3] = box.getString("c_contents1");
          v_contents[4] = box.getString("c_contents2");
          v_contents[5] = box.getString("c_contents3");
          v_contents[6] = box.getString("p_contents1");
          v_contents[7] = box.getString("p_contents2");
          v_contents[8] = box.getString("p_contents3");
          
          v_position[0] = box.getString("h_position1");
          v_position[1] = box.getString("h_position2");
          v_position[2] = box.getString("h_position3");
          v_position[3] = box.getString("c_position1");
          v_position[4] = box.getString("c_position2");
          v_position[5] = box.getString("c_position3");
          v_position[6] = box.getString("p_position1");
          v_position[7] = box.getString("p_position2");
          v_position[8] = box.getString("p_position3");
                     
          try {
         	 
              connMgr = new DBConnectionManager();
              
              connMgr.setAutoCommit(false);
              
              sql.append(" SELECT NVL(MAX(SEQ), 0) FROM TZ_OFFLECTCARDINFO ");
              ls = connMgr.executeQuery(sql.toString());
              ls.next();
              int v_seq = ls.getInt(1) + 1;
              ls.close();

              //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
              sql.setLength(0);
              
              sql.append(" INSERT INTO TZ_OFFLECTCARDINFO(SEQ, NAME, REG7, COMPNM, COMPADDR, COMPTEL, HANDPHONE, EMAIL, WORKNBOOK, INDATE, ETC, SUBJGUBUN, LECTGUBUN) ");
              sql.append(" VALUES (?,?,crypto.enc('normal',?),?,crypto.enc('normal',?),crypto.enc('normal',?),crypto.enc('normal',?),crypto.enc('normal',?),?,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'),?,?,?) ");

              pstmt1 = connMgr.prepareStatement(sql.toString());
              
              pstmt1.setInt   (1, v_seq);
              pstmt1.setString(2, v_name);
              pstmt1.setString(3, v_reg7);
              pstmt1.setString(4, v_compnm);
              pstmt1.setString(5, v_compaddr);
              pstmt1.setString(6, v_comptel);
              pstmt1.setString(7, v_handphone);
              pstmt1.setString(8, v_email);
              pstmt1.setString(9, v_worknbook);
              pstmt1.setString(10, v_etc);
              pstmt1.setString(11, v_subjgubun);
              pstmt1.setString(12, v_lectgubun);

              isOk1 = pstmt1.executeUpdate();
              
              sql2.setLength(0);
              
              sql2.append(" INSERT INTO TZ_OFFLECTCARDADDINFO(SEQ, GUBUN, GUBUNSEQ, STARTDATE, ENDDATE, CONTENTS, POSITION, INDATE) ");
              sql2.append(" VALUES (?,?,?,?,?,?,?,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')) ");
              pstmt2 = connMgr.prepareStatement(sql2.toString());
              for(int i=0; i < 9; i++){
            	  pstmt2.setInt   (1, v_seq);
            	  pstmt2.setString(2, v_gubun[i]);
            	  pstmt2.setInt	  (3, v_gubunseq[i]);
            	  pstmt2.setString(4, v_startdate[i]);
            	  pstmt2.setString(5, v_enddate[i]);
            	  pstmt2.setString(6, v_contents[i]);
            	  pstmt2.setString(7, v_position[i]);
            	  isOk2 = pstmt2.executeUpdate();
              }
              

              isOk3 = this.insertUpFile(connMgr, box, v_seq);

  			 if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                  if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
                  isOk = 1;
              }else{
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
              if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
              if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
          }
         
          return isOk;
     }
    
    public	int	insertUpFile(DBConnectionManager connMgr, RequestBox box, int p_seq) throws	Exception {
		ListSet	ls = null;
		PreparedStatement pstmt2 = null;
		String sql = "";
		String sql2	= "";
		int	isOk2 =	1;

		String [] v_realFileName = new String [FILE_LIMIT];
		String [] v_newFileName	= new String [FILE_LIMIT];
		System.out.println("FILE_LIMIT :  " + FILE_LIMIT);
		v_realFileName[0] = box.getRealFileName(FILE_TYPE);
		v_newFileName[0] =	box.getNewFileName(FILE_TYPE);
		v_realFileName[1] = box.getRealFileName(FILE_TYPE2);
		v_newFileName[1] =	box.getNewFileName(FILE_TYPE2);
		v_realFileName[2] = box.getRealFileName(FILE_TYPE3);
		v_newFileName[2] =	box.getNewFileName(FILE_TYPE3);

		try	{
			sql	= "select NVL(max(fileseq),	0) from	TZ_OFFLECTCARDFILE where seq = " +	p_seq ;
			System.out.println("sql :  " + sql);
			ls = connMgr.executeQuery(sql);
			ls.next();
			int	v_fileseq =	ls.getInt(1) + 1;
			
			System.out.println("v_fileseq : " + v_fileseq);
			
			ls.close();
			sql2 =	"insert	into TZ_OFFLECTCARDFILE(seq, fileseq, filegubun, realfile, savefile, indate)";
			sql2 +=	" values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt2 = connMgr.prepareStatement(sql2);
			System.out.println("v_realFileSIze : " + v_realFileName.length);
			for(int	i =	0; i < FILE_LIMIT; i++)	{
				if(!v_realFileName[i].equals(""))	{		//		실제 업로드	되는 파일만	체크해서 db에 입력한다
					pstmt2.setInt(1, p_seq);
					pstmt2.setInt(2, v_fileseq);
					if(i == 0){
						pstmt2.setString(3, "P");
					}else if(i==1){
						pstmt2.setString(3, "F");
					}else{
						pstmt2.setString(3, "A");
					}
					pstmt2.setString(4,	v_realFileName[i]);
					pstmt2.setString(5,	v_newFileName[i]);
					isOk2 =	pstmt2.executeUpdate();
					System.out.println("i : "+i);
					v_fileseq++;
				}
			}
		}
		catch (Exception ex) {
			FileManager.deleteFile(v_newFileName, FILE_LIMIT);		//	일반파일, 첨부파일 있으면 삭제..
			ErrorManager.getErrorStackTrace(ex,	box, sql2);
			throw new Exception("sql = " + sql2	+ "\r\n" + ex.getMessage());
		}
		finally	{
		    if(ls != null) { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
		}
		return isOk2;
	}

}
