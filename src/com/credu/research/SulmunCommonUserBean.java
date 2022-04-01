//**********************************************************
//1. ��      ��:
//2. ���α׷���: SulmunCommonUserBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: lyh
//**********************************************************

package com.credu.research;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunCommonUserBean {


    public SulmunCommonUserBean() {}


    public int InsertSulmunUserResult(RequestBox box) throws Exception {
  
        DBConnectionManager connMgr = null;        
        PreparedStatement pstmt1 = null;
        // PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        String sql1 = "";
        // String sql2 = "";
        int isOk = 0;
        int isOk1 = 0;

		String v_grcode     = box.getString("p_grcode");
        String v_subj       = box.getString("p_subj");
        String v_gyear       = box.getString("p_gyear");
        String v_subjseq    = box.getString("p_subjseq");
        int    v_sulpapernum= box.getInt("p_sulpapernum");
        String v_userid     = box.getString("p_userid");
        String v_sulnums    = box.getString("p_sulnums");
		String v_answers    = box.getString("p_answers");
        String v_luserid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

			//�����Ⱓ Ȯ��
            isOk = getSulmunGigan(box);
			

			if (isOk == 2) {		//������ 1 //���� �Ϸ�3 //���� ��2

            sql1 = "select userid from TZ_SULEACH   \n";
            sql1+=  " 	where subj = ? and grcode = ? and year = ? and subjseq = ?  \n";
			sql1+=	"	and gubun ='COMMON' and sulpapernum = ?  and  userid = ? \n";
						
            pstmt1 = connMgr.prepareStatement(sql1);   
            
            pstmt1.setString( 1, v_subj);      
            pstmt1.setString( 2, v_grcode);       
            pstmt1.setString( 3, v_gyear);    
            pstmt1.setString( 4, v_subjseq);
            pstmt1.setInt( 5, v_sulpapernum);
            pstmt1.setString( 6, v_userid);
			
                try {
                    rs = pstmt1.executeQuery();
                    
                    if(!rs.next()) {     //     ���ſ� ��ϵ� userid �� Ȯ���ϰ� ���� ��쿡�� ���
											//�����Ͻ� subj����  ������ ���Ѱ��� �ƴϹǷ� ALL�� ����. 
                        //isOk1 = InsertTZ_suleach(connMgr, "ALL" , v_grcode, v_gyear,    v_subjseq, v_sulpapernum, v_userid, v_sulnums, v_answers, v_luserid);
                        isOk1 = InsertTZ_suleach(connMgr, v_subj , v_grcode, v_gyear,    v_subjseq, v_sulpapernum, v_userid, v_sulnums, v_answers, v_luserid);
                    } 

                }catch(Exception e) {}
                finally { if (rs != null) { try { rs.close(); } catch (Exception e) {} }}           

			}
			if (isOk > 0){
						connMgr.commit();
					} else {
						connMgr.rollback();
					}

       }
        catch(Exception ex) {
            isOk1 = 0;
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }      
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return isOk*isOk1;
    }

    @SuppressWarnings("deprecation")
    public int getSulmunGigan(RequestBox box) throws Exception {

		SulmunCommonPaperBean bean = null;
		DataBox  dbox0 = null; 

        // String v_sulstart = "";
        // String v_sulend = "";
		int v_update = 0;

        try {
			bean = new SulmunCommonPaperBean();
			dbox0 = bean.getPaperData(box);

            // v_sulstart = FormatDate.getFormatDate(dbox0.getString("d_sulstart"),"yyyy-MM-dd");
            // v_sulend = FormatDate.getFormatDate(dbox0.getString("d_sulend"),"yyyy-MM-dd");
		    
			if(dbox0.getInt("d_sulpapernum") > 0){
          
		      long v_fstart = Long.parseLong(dbox0.getString("d_sulstart"));
              long v_fend = Long.parseLong(dbox0.getString("d_sulend"));
              
                 java.util.Date d_now = new java.util.Date();
                 String d_year = String.valueOf(d_now.getYear()+1900);
				 String d_month = String.valueOf(d_now.getMonth()+1);
				 String d_day = String.valueOf(d_now.getDate());

                 if(d_month.length() == 1){
				        d_month = "0" + d_month; 
				 }
				 if (d_day.length() == 1){
				        d_day = "0" + d_day; 				 
				 }
		         long v_now = Long.parseLong(d_year+d_month+d_day); 

				 //System.out.println("v_fstart:"+v_fstart+"/v_now:"+v_now);
		         if (v_fstart > v_now){
					v_update = 1;                                         //���� ��
		         } else if (v_now > v_fend){
					v_update = 3;                                         //���� �Ϸ�
		         } else if (v_fstart <= v_now && v_now <= v_fend){
					v_update = 2;                                          //���� ��
		         }
			}
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return v_update;

	}


    /**
    �Ϲݼ���  ��� ��� 
    @param box          receive from the form object and session
    @return int   
    */
    public int InsertTZ_suleach(DBConnectionManager connMgr, String p_subj,  String p_grcode,  String p_gyear,    String p_subjseq, int p_sulpapernum,
                            String p_userid, String  p_sulnums, String p_answers, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SULEACH table
            sql =  "insert into TZ_SULEACH 	\n";
            sql+=  " (subj,  grcode, gubun,   year,    subjseq, sulpapernum, \n";
            sql+=  "  userid,  sulnums, answers,  luserid, ldate) 			\n";
            sql+=  " values ";
            sql+=  " (?,       ?,      'COMMON',       ?,      ?,     ?,   \n";
            sql+=  "  ?,       ?,       ?,   	?,       ?) ";
			
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString( 1, p_subj);
            pstmt.setString( 2, p_grcode);
			pstmt.setString( 3, p_gyear);
            pstmt.setString( 4, p_subjseq);
            pstmt.setInt   ( 5, p_sulpapernum);
            pstmt.setString( 6, p_userid);
            pstmt.setString( 7, p_sulnums);
            pstmt.setString( 8, p_answers);
            pstmt.setString( 9, p_luserid);
            pstmt.setString(10, FormatDate.getDate("yyyyMMddHHmmss"));
			
            isOk = pstmt.executeUpdate();
			

       }
       catch(Exception ex) {
           ErrorManager.getErrorStackTrace(ex);
           throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
       }
       finally {
           if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
       }
       return isOk;
    }

    /**
    �Ϲݼ���  ��� ���� 
    @param box          receive from the form object and session
    @return DataBox   
    */
    @SuppressWarnings("deprecation")
    public DataBox SelectUserPaperResult(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        

        String v_grcode    = box.getString("p_grcode");
        String v_subj      = box.getStringDefault("p_subj", "TARGET");
        java.util.Date d_now = new java.util.Date();
        String v_gyear    = box.getStringDefault("p_gyear", String.valueOf(d_now.getYear()+1900));
        String v_subjseq   = box.getStringDefault("p_subjseq","0001");
        int    v_sulpapernum = box.getInt("p_sulpapernum");
        String v_userid  = box.getString("p_userid");		
        try {      
                connMgr = new DBConnectionManager();
                
			sql = "select sulnums, answers    ";
            sql+= "  from tz_suleach ";
            sql+= " where grcode = " + SQLString.Format(v_grcode);
            sql+= "   and subj   = " + SQLString.Format(v_subj);
            sql+= "   and year   = " + SQLString.Format(v_gyear);
            sql+= "   and subjseq   = " + SQLString.Format(v_subjseq);
            sql+= "   and sulpapernum   = " + SQLString.Format(v_sulpapernum);
            sql+= "   and userid   = " + SQLString.Format(v_userid);
			
                ls = connMgr.executeQuery(sql);
                
                while (ls.next()) {
                    dbox = ls.getDataBox();
                }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }

    /**
    ����  ����� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   �¶����׽�Ʈ �����
    */
    public DataBox selectSulmunUser(RequestBox box) throws Exception {               
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";        

        String v_userid  = box.getString("p_userid");		
        try {      
                connMgr = new DBConnectionManager();
                
                sql+= "select       b.comp  asgn,  get_compnm(b.comp,2,4)       asgnnm, ";
                sql+= "	   	  b.jikup,       get_jikupnm(b.jikup, b.comp) jikupnm, ";
                sql+= "	   	  b.cono,     b.name ";
                sql+= "  from tz_member   b ";
                sql+= "   where b.userid    = " + SQLString.Format(v_userid);
     
                ls = connMgr.executeQuery(sql);
                
                while (ls.next()) {
                    dbox = ls.getDataBox();
                }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }

    public String getGrcode(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq)  throws Exception {
        String v_grcode = "";
        ListSet ls = null;
        String sql  = "";
        try {
            sql = "select grcode ";
            sql+= "  from tz_subjseq  ";
            sql+= " where subj    = " + SQLString.Format(p_subj);
            sql+= "   and year    = " + SQLString.Format(p_year);
            sql+= "   and subjseq = " + SQLString.Format(p_subjseq);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_grcode = ls.getString("grcode");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_grcode;
    }

    /**
    ����� �ش��������Ʈ
    @param box          receive from the form object and session
    @return ArrayList   �ش��������Ʈ
    */
    public ArrayList<DataBox> SelectUserList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String sql = "";
                        
        try {
        String s_userid     = box.getSession("userid");
        String v_subj     = box.getStringDefault("s_subjcourse", SulmunCommonBean.DEFAULT_SUBJ);
        String v_grcode = box.getSession("tem_grcode");
		
			connMgr = new DBConnectionManager();
            
            list = new ArrayList<DataBox>();

			sql = "select a.grcode,       a.subj,   a.subjseq,      \n";
            sql+= "       a.sulpapernum,  a.sulpapernm, a.year, \n";
            sql+= "       a.totcnt,       a.sulnums, a.sulmailing, a.sulstart, a.sulend, \n";
            sql+= "       'COMMON'      subjnm , \n";
            sql+= " ( select count(userid) from tz_suleach where grcode = a.grcode   \n";
			sql+= " and gubun = a.subj and sulpapernum = a.sulpapernum and userid = "+SQLString.Format(s_userid)+" ) issul "; 
            sql+= "  from tz_sulpaper a \n";
			sql+= "   where a.grcode = " + SQLString.Format(v_grcode);
            sql+= "   and a.subj   = " + SQLString.Format(v_subj);
            sql += "  and substring(a.sulstart,1,8) <= to_char(sysdate, 'yyyyMMdd')         \n";
            sql += "  and substring(a.sulend,1,8) >= to_char(sysdate, 'yyyyMMdd')         \n";
            //sql += "  and 0 = ( select count(userid) from tz_suleach where grcode =  " + SQLString.Format(v_grcode);
            //sql+= "   and subj   = " + SQLString.Format(v_subj);
            //sql+= "   and userid   = " + SQLString.Format(s_userid)  + " ) ";
            sql+= " order by a.subj, a.sulpapernum \n";
			System.out.println("SelectUserList sql: "+sql);			
			// Log.info.println("common>>>"+sql);
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