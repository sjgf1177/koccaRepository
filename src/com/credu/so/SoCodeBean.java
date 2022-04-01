//**********************************************************
//1. 제      목: so평가코드관리 
//2. 프로그램명: SoCodeBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 정은년 2005.8.22
//7. 수      정:
//
//**********************************************************

package com.credu.so;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.so.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoCodeBean {

    public SoCodeBean() {}

    /**
    평가코드 리스트
    @param box          receive from the form object and session
    @return ArrayList   리스트
    */
    public ArrayList SelectCodeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = new ArrayList();
        ListSet ls     = null;
        DataBox dbox   = null;
        String sql     = "";
        String v_sogubun = box.getString("p_sogubun");    // so구분     
        
        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT distcode, distnm, levels, upcode, pcode, notice, ordering "
                + " from tz_socode     "
                + " where sogubun = "+SQLString.Format(v_sogubun) 
                + " order by distcode  ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }        
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }


    /**
    평가코드 등록
    @param box          receive from the form object and session
    @return int
    */
    public int InsertCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls     = null;   
        String sql  = "";
        String wsql = "";        
        int isOk = 0;

        String v_distcode = "";
        String v_sogubun  = box.getString("p_sogubun");    // so구분           
        String v_distnm   = box.getString("p_distnm");
        int    v_levels   = box.getInt("p_levels");
        String v_upcode   = box.getString("p_upcode");
        String v_pcode    = box.getString("p_pcode");
        String v_notice   = box.getString("p_notice");
        int    v_ordering = 0;
        String v_luserid  = box.getSession("userid");
          
        
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            int v_substr = ((v_levels-1)*3)+1;  // 레벨별 분류코드 위치
            
            if(v_levels!=1) wsql = " and upcode='"+v_upcode+"' and levels="+v_levels+" ";

            sql = " SELECT NVL(substr(max(distcode),"+v_substr+",3)+1, 101) discode,  NVL(max(ordering)+1, 1) ordering FROM tz_socode "
                + " WHERE sogubun = "+SQLString.Format(v_sogubun)+" "
                + wsql;
                
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_distcode = ls.getString("discode");
                v_ordering = ls.getInt("ordering");
            }

            if (!v_upcode.equals("0")&&!v_upcode.equals("")) {
                if (v_levels==2) v_distcode = v_upcode.substring(0,3)+v_distcode;
                else if (v_levels==3) v_distcode = v_upcode.substring(0,6)+v_distcode;
                else if (v_levels==4) v_distcode = v_upcode.substring(0,9)+v_distcode;
                else if (v_levels==5) v_distcode = v_upcode.substring(0,12)+v_distcode;               
                
            }else{
                isOk = 0;
            }                                 
                        
            for(int i=5; i>v_levels; i--) {
                v_distcode += "000";    
            }
                  
            sql = " insert into TZ_SOCODE  values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )  ";
                
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString( 1, v_distcode);
            pstmt.setString( 2, v_sogubun);  // sogubun                     
            pstmt.setString( 3, v_distnm);
            pstmt.setInt   ( 4, v_levels);
            pstmt.setString( 5, v_upcode);
            pstmt.setString( 6, v_pcode);
            pstmt.setString( 7, v_notice);
            pstmt.setInt   ( 8, v_ordering);   
            pstmt.setString( 9, v_luserid);
            pstmt.setString(10, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();

        }
        catch(Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (isOk > 0) {connMgr.commit();}
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }               
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
    
    /**
    평가코드 수정
    @param box          receive from the form object and session
    @return int
    */
    public int UpdateCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls     = null;      
        String sql  = "";    
        int isOk = 0;
        String v_sogubun  = box.getString("p_sogubun");    // so구분   
        String v_distcode = box.getString("p_distcode");
        String v_distnm   = box.getString("p_distnm");
        String v_notice   = box.getString("p_notice");
        int    v_ordering = box.getInt("p_ordering");
        String v_luserid  = box.getSession("userid");
        
        try {
            connMgr = new DBConnectionManager();
 
            sql = " update  TZ_SOCODE set distnm=?, notice=?, ordering=?, luserid=?, ldate=? where distcode = ?  and sogubun = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_distnm);
            pstmt.setString(2, v_notice);
            pstmt.setInt   (3, v_ordering);
            pstmt.setString(4, v_luserid);
            pstmt.setString(5, FormatDate.getDate("yyyyMMddHHmmss"));                                                                        
            pstmt.setString(6, v_distcode);
            pstmt.setString(7, v_sogubun);
            
            isOk = pstmt.executeUpdate();

        }
        catch(Exception ex) {
            isOk = 0;
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {            
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }    
    
    /**
    평가코드 삭제
    @param box          receive from the form object and session
    @return int
    */
    public int DeleteCode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet             ls      = null;          
        String sql  = "";    
        int isOk = 0;
        int cnt  = 0;
        String v_sogubun  = box.getString("p_sogubun");    // so구분  
        String v_distcode = box.getString("p_distcode");
        
        try {
            connMgr = new DBConnectionManager();
            
            // 하위코드가 있는경우 예외처리
            sql = " select count(distcode) cnt from tz_socode where upcode="+SQLString.Format(v_distcode)+"  and sogubun="+SQLString.Format(v_sogubun)+" ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                cnt = ls.getInt("cnt");
            } 
            
            if (cnt > 0) {
                isOk = -2;
            }else{
                // 삭제            
                sql = " delete from  TZ_SOCODE where distcode = ?  and sogubun = ? ";
                pstmt = connMgr.prepareStatement(sql);                                                                      
                pstmt.setString(1, v_distcode);
                pstmt.setString(2, v_sogubun);
                isOk = pstmt.executeUpdate();
            }                
        }
        catch(Exception ex) {
            isOk = 0;
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {            
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }    

    /**
    코드레벨 뷰 select box
    @param selectname       select box name
    @param selected         selected valiable
    @param allcheck         all check Y(1),all check N(0)
    @return String select box
    */
    public static String getLevels(RequestBox box, int p_levels, boolean isChange, boolean isALL) throws Exception {
        String result = "";        
        String  v_upcode     = "";     // 상위코드
        String  v_distcode   = "";     // onchange 발생하는 레벨코드          
        String  v_sogubun    = box.getString("p_sogubun");    // so구분        
        int     v_reqlevels  = box.getInt("p_reqlevels");     // onchange 발생하는 레벨          
        String  v_levels1    = box.getString("p_levels1");  
        String  v_levels2    = box.getString("p_levels2");  
        String  v_levels3    = box.getString("p_levels3");  
        String  v_levels4    = box.getString("p_levels4");  
        String  v_levels5    = box.getString("p_levels5");  
        String  v_selectnm   = "levels"+String.valueOf(p_levels);  
        System.out.println("v_sogubun====>>>>"+v_sogubun);      

        result = "  <SELECT name='"+v_selectnm+"' ";

        if(isChange) result += " onChange = \"javascript:whenSelection("+p_levels+")\" ";  
        result += " >\n";
            
        if (isALL) {
          result += " <option value=''>=== ALL ===</option> \n";
        }

        try {
            for(int i=1; i<=v_reqlevels+1; i++){
                
                if(p_levels!=1) v_upcode = box.getString("p_levels"+String.valueOf(p_levels-1));    //상위코드 
                
                if(i==p_levels){

                    v_distcode = box.getString("p_levels"+String.valueOf(p_levels));     // onchange 발생하는 레벨코드   

                    result += getLevelsOptionTag(p_levels, v_distcode, v_upcode, v_sogubun);
                }                    
            }
            result += " </SELECT>\r\n ";
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return result;
    }       

    /**
    코드레벨 뷰 select box
    @param selectname       select box name
    @param selected         selected valiable
    @param allcheck         all check Y(1),all check N(0)
    @return String select box
    */
    public static String getLevels(RequestBox box, int p_levels, boolean isChange, boolean isALL, boolean isUpdate) throws Exception {
        String result = "";        
        String  v_upcode     = "";     // 상위코드
        String  v_distcode   = "";     // onchange 발생하는 레벨코드          
        String  v_sogubun    = box.getString("p_sogubun");    // so구분        
        int     v_reqlevels  = box.getInt("p_reqlevels");     // onchange 발생하는 레벨          
        String  v_levels1    = box.getString("p_levels1");  
        String  v_levels2    = box.getString("p_levels2");  
        String  v_levels3    = box.getString("p_levels3");  
        String  v_levels4    = box.getString("p_levels4");  
        String  v_levels5    = box.getString("p_levels5");  
        String  v_selectnm   = "levels"+String.valueOf(p_levels);        


        String  p_distcode   = box.getString("p_distcode");  // isUpdate
        
        if(isUpdate) {

            v_reqlevels = 3;
            
            for(int i=1; i<=v_reqlevels; i++){
            
                v_distcode = PutZero(p_distcode.substring(0, 3*i), 15);    
                box.put("p_levels"+String.valueOf(i),v_distcode);
            }

            v_reqlevels = v_reqlevels-1;            
        }


        result = "  <SELECT name='"+v_selectnm+"' ";

        if(isChange) result += " onChange = \"javascript:whenSelection("+p_levels+")\" ";  
        result += " >\n";
            
        if (isALL) {
          result += " <option value=''>=== ALL ===</option> \n";
        }
                        
        try {
            for(int i=1; i<=v_reqlevels+1; i++){           
                
                if(p_levels!=1) v_upcode = box.getString("p_levels"+String.valueOf(p_levels-1));    //상위코드 
                
                if(i==p_levels){

                    v_distcode = box.getString("p_levels"+String.valueOf(p_levels));     // onchange 발생하는 레벨코드   

                    result += getLevelsOptionTag(p_levels, v_distcode, v_upcode, v_sogubun);
                }                    
            }
            result += " </SELECT>\r\n ";
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return result;
    }  
    
    
    /**
    코드레벨 가져오기 option box 
    @return String
    */
    public static String getLevelsOptionTag(int p_levels, String v_distcode, String v_upcode, String v_sogubun) throws Exception {           
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt   = null;        
        ListSet             ls      = null;        
        String sql = "";
        String result = "";
        
        try {
            connMgr = new DBConnectionManager();
                        
            sql = " select distcode, distnm from tz_socode ";
            sql+= " where levels = "+p_levels+" ";   
            if(p_levels!=1){
                sql+= "   and upcode ="+SQLString.Format(v_upcode)+" ";
            }
            sql+= "   and sogubun="+SQLString.Format(v_sogubun)+" ";
            sql+= " order by ordering ";
            //System.out.println("so____level===>>>"+sql);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                result += " <option value=" + ls.getString("distcode");
                if (v_distcode.equals(ls.getString("distcode"))) { result += " selected "; }
                                                                                                                  
                result += ">" + ls.getString("distnm") + "</option> \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }    
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }       
    

    /**
    속성 레벨 갯수 가져오기
    @return int
    */
    public int getLevelsCount(String sogubun) throws Exception {           
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;        
        String  sql  = ""; 
        int     lcnt = 0;
        
        try {
            connMgr = new DBConnectionManager();
            sql = " select max(levels) lcnt from tz_socode where sogubun="+SQLString.Format(sogubun)+"  ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                lcnt = ls.getInt("lcnt");
            }           
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return lcnt;
    } 
    

    /**
    속성 이름 가져오기
    @return String
    */
    public static String getLevelsName(String p_distcode, int levels, String p_sogubun) throws Exception {           
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;        
        String sql    = ""; 
        String wsql   = ""; 
        String result = "";
        String v_distcode = "";
                
        try {
            connMgr = new DBConnectionManager();
            
            for(int i=1; i<=levels; i++){
                v_distcode = PutZero(p_distcode.substring(0, 3*i), 15);    
            
                wsql +=  " distcode = '"+v_distcode+"' " ;
                if(i!=levels) wsql+=" or ";
            }
            
            sql = " select distnm from tz_socode where sogubun="+SQLString.Format(p_sogubun)+"  ";
            sql+= "                               and ( " + wsql + " ) ";
            sql+= " order by levels "; 
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                result += ls.getString("distnm").trim()+"/";
            }
            
            result = result.substring(0,result.length()-1);           
            
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }    


    /**
     * "0" 채워넣는 Method
     * @return String
     */
    public static String PutZero(String sinput, int ilength)
    {
        char[] ch = new char[ilength];

        for(int i = 0; i < sinput.length(); i++)
        {
            ch[i] = sinput.charAt(i);
        }

        for(int i = sinput.length(); i < ilength; i++)
        {
            ch[i] = '0';
        }

        return String.valueOf(ch);
    }
  
}
