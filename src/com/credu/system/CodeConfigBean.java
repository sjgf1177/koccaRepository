//**********************************************************
//  1. 제      목: 코드 관리
//  2. 프로그램명 : CodeConfigBean.java
//  3. 개      요: 코드 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7.  14
//  7. 수      정:
//**********************************************************

package com.credu.system;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.StringManager;

/**
 * 코드 관리(ADMIN)
 *
 * @date   : 2003. 7
 * @author : s.j Jung
 */
public class CodeConfigBean {

    public CodeConfigBean() {}



    /**
    * 숫자앞에 0 으로 채우기,  (숫자,길이)
    */
    public static String addZero (int chkNumber, int chkLen){
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
    *  컨피그 데이타값 (데이타명)
    */
    public static String getConfigValue (String name) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        try {
            connMgr = new DBConnectionManager();

            sql  = " select vals from TZ_CONFIG        ";
            sql += "  where name = " + StringManager.makeSQL(name);
            //System.out.println(sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getString("vals");
            }
            //System.out.println("result"=result);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return result;
    }

    /**
     *  코드 체크박스 (config명,셀렉트박스명,전체유무)
     */
     public static String getCodeCheck(String config, String name, boolean allcheck) throws Exception {
         return getCodeCheck (config, name, "", allcheck);
         /**(config명(0),코드,레벨,체크박스명(0),선택값(0),이벤트명,전체유무(false)) **/
     }

     /**
      *  코드 체크박스 (config명,셀렉트박스명,선택값,전체유무)
      */
      public static String getCodeCheck(String config, String name, String selected, boolean allcheck) throws Exception {
          return getCodeCheck (config, "", 1, name, selected, "", allcheck);
          /**(config명(0),코드,레벨,체크박스명(0),선택값(0),이벤트명,전체유무(false)) **/
      }


    /**
    *  코드 셀렉트박스 (config명,셀렉트박스명,선택값) 3
    */
    public static String getCodeSelect (String config, String name, String selected) throws Exception {
        return getCodeSelect (config, "", 1, name, selected, "", 0, "");
    }

    /**
    *  코드 셀렉트박스 (config명,셀렉트박스명,선택값,전체유무) 4
    */
    public static String getCodeSelect (String config, String name, String selected, int allcheck) throws Exception {
        return getCodeSelect (config, "", 1, name, selected, "", allcheck, "");
        /**(config명(0),코드,레벨,셀렉트박스명(0),선택값(0),이벤트명,전체유무(0)) **/
    }

    /**
    *  코드 셀렉트박스 (config명,코드,레벨,셀렉트박스명,이벤트명,선택값) 5
    */
    public static String getCodeSelect (String config, String code, int levels, String name, String selected) throws Exception {
        return getCodeSelect (config, code, levels, name, selected, "",0, "");
    }

    /**
    *  코드 셀렉트박스 (config명,코드,레벨,셀렉트박스명,선택값,이벤트명,전체유무) 6
    */
    public static String getCodeSelect (String config, String code, int levels, String name, String selected, int allcheck) throws Exception {
        return getCodeSelect (config, code, levels, name, selected, "", allcheck, "");
    }

    /**
    *  코드 셀렉트박스 (config명,코드,셀렉트박스명,선택값,이벤트명) 4
    */
    public static String getCodeSelect (String config, String name, String selected, String event) throws Exception {
        return getCodeSelect (config, "", 1, name, selected, event, 0, "");
    }

    /**
    *  코드 셀렉트박스 (config명,코드,셀렉트박스명,선택값,이벤트명,전체유무) 5
    */
    public static String getCodeSelect (String config, String name, String selected, String event, int allcheck) throws Exception {
        return getCodeSelect (config, "", 1, name, selected, event, allcheck, "");
    }

    /**
    *  코드 셀렉트박스 (config명, 셀렉트박스명, 선택값, 이벤트명, 전체유무, 부분출력)6
    */
    public static String getCodeSelect (String config, String name, String selected, String event, int allcheck, String aPart) throws Exception {
        return getCodeSelect (config, "", 1, name, selected, event, allcheck, aPart);
    }

    /**
    *  코드 셀렉트박스 (config명,코드,레벨,셀렉트박스명,선택값,이벤트명,일부출력) 7
      *  TZ_CONFIG 값 이용
    *  allcheck값 전체여부 : 0 -전체없음, 1 - 전체있음, 2 - ALL
    *  aPart값(2자리) --> 두자리수 사이에 있는 코드값을 return한다.
    */

    public static String getCodeSelect (String config, String code, int levels, String name, String selected, String event, int allcheck, String aPart) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        CodeData data = null;
        String gubun = getConfigValue(config);   //TZ_CONFIG 테이블에서 값을 얻어온다.
        String fChar = "";
        String sChar = "";

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
          result += " <option value=''>=== 전체 ===</option> \n";
        } else if (allcheck == 2) {
              result += " <option value='ALL'>ALL</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql  = " select code, codenm from tz_code            ";
            sql += "  where gubun  = " + StringManager.makeSQL(gubun);
            sql += "    and levels = " + levels;
            if (levels > 1) {
              sql += "    and upper = " + StringManager.makeSQL(code);
            }

            if (aPart.length() > 0 && aPart.length()<=2) {
                  fChar = aPart.substring(0,1);
                  sChar = aPart.substring(1,2);
                  boolean check = fChar.charAt(0) <= sChar.charAt(0);

                  //System.out.println("check==>"+ check);
                  if(check){
                    //sql += "  and substring(code,"+fChar+","+sChar+") = "+StringManager.makeSQL(tChar);
                    sql += "  and code >="+StringManager.makeSQL(fChar)+" and code <= "+StringManager.makeSQL(sChar);
                }

            }

            sql += " order by code asc";
            ls = connMgr.executeQuery(sql);

              //System.out.println("code_sql==>"+ sql);
            while (ls.next()) {
                data = new CodeData();

                data.setCode(ls.getString("code"));
                data.setCodenm(ls.getString("codenm"));

                result += " <option value=" + data.getCode();
                if (selected.equals(data.getCode())) {
                    result += " selected ";
                }

                result += ">" + data.getCodenm() + "</option> \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result += "  </SELECT> \n";
        return result;
    }



    /**
    *  코드 셀렉트박스 (GUBUN,코드,레벨,셀렉트박스명,선택값,이벤트명,전체유무)
      *  TZ_CODEGUBUN 값 이용
    *  전체여부 : 0 -전체없음, 1 - 전체있음, 2 - ALL
    */
    public static String getCodeGubunSelect (String gubun, String code, int levels, String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;

        StringBuilder sql = new StringBuilder();
        CodeData data = null;

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
          result += " <option value=''>=== 전체 ===</option> \n";
        } else if (allcheck == 2) {
            result += " <option value='ALL'>ALL</option> \n";
        } else if( allcheck == 3 ){
			result += " <option value=''>=== 선택 ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* CodeConfigBean.getCodeGubunSelect (코드값 조회 후 selectbox 만들기) */ \n");
            sql.append("SELECT  CODE    \n");
            sql.append("    ,   CODENM  \n");
            sql.append("  FROM  TZ_CODE \n");
            sql.append(" WHERE  GUBUN = '").append(gubun).append("' \n");
            sql.append("   AND  LEVELS = ").append(levels).append(" \n");
            if (levels > 1) {
                sql.append("   AND UPPER = '").append(code).append("'   \n");
            }
            sql.append(" ORDER BY CODE ASC  \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                data = new CodeData();

                data.setCode(ls.getString("code"));
                data.setCodenm(ls.getString("codenm"));

                result += " <option value=" + data.getCode();
                if (selected.equals(data.getCode())) {
                    result += " selected ";
                }

                result += ">" + data.getCodenm() + "</option> \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result += "  </SELECT> \n";
        return result;
    }

    /**
     *  코드 체크박스 (GUBUN,코드,레벨,셀렉트박스명,선택값,이벤트명,전체유무)
       *  TZ_CODEGUBUN 값 이용
     *  전체여부 : true
     */
     public static String getCodeCheck (String gubun, String code, int levels, String name, String selected, String event, boolean allcheck) throws Exception {
         DBConnectionManager connMgr = null;
         ListSet ls = null;
         StringBuffer result = new StringBuffer();
         final String CHECKBOX = "\n<input TYPE='CHECKBOX' name='" + name + "' " + event + " value='";
         final String COT = "'";
         final String CLOSE = ">";
         final String CODE = "code";
         final String CODENM = "codenm";
         final String CHECK = " checked";
         String sql = "";
         gubun = getConfigValue(gubun);   //TZ_CONFIG 테이블에서 값을 얻어온다.


         try {
             connMgr = new DBConnectionManager();

             sql  = " select code, codenm from tz_code            ";
             sql += "  where gubun  = " + StringManager.makeSQL(gubun);
             sql += "    and levels = " + levels;
             if (levels > 1) {
               sql += "    and upper = " + StringManager.makeSQL(code);
             }
             sql += " order by code asc";
             ls = connMgr.executeQuery(sql);

             while (ls.next()) {
                 result.append(CHECKBOX);
                 result.append(ls.getString(CODE));
                 result.append(COT);

                 if (allcheck || selected.indexOf(ls.getString(CODE))!=-1) {
                     result.append(CHECK);
                 }

                 result.append(CLOSE);
                 result.append(ls.getString(CODENM));
             }
         }
         catch (Exception ex) {
//        	 System.out.println(ex);
             ErrorManager.getErrorStackTrace(ex);
             throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
         }
         finally {
             if(ls != null) { try { ls.close(); }catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }

         return result.toString();
     }


    /**
    *  코드구분명 (config명)
    */
    public static String getCodeName (String config) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        CodeData data = null;
        String gubun = getConfigValue(config);

        try {
            connMgr = new DBConnectionManager();

            sql  = " select gubunnm, maxlevel from TZ_CODEGUBUN        ";
            sql += "  where gubun = " + StringManager.makeSQL(gubun);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data=new CodeData();
                data.setGubunnm(ls.getString("gubunnm"));
                result = data.getGubunnm();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return result;
    }


    /**
    *  코드명 (config명,코드)
    */
    public static String getCodeName (String config, String code) throws Exception {
        return getCodeName (config, code, 1);
    }

    /**
    *  코드명 (config명,코드,레벨)
    */
    public static String getCodeName (String config, String code, int levels) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        CodeData data = null;
        String gubun = getConfigValue(config);
        if(gubun.equals("")) gubun = config;
//System.out.println(gubun+"gubun");
//System.out.println(code+"code");
//System.out.println(levels+"levels");

        try {
            connMgr = new DBConnectionManager();

            sql  = " select codenm from TZ_CODE        ";
            sql += "  where gubun  = " + StringManager.makeSQL(gubun);
            sql += "    and levels = " + levels;
            sql += "    and code   = " + StringManager.makeSQL(code);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                data=new CodeData();
                data.setCodenm(ls.getString("codenm"));
                result = data.getCodenm();
            }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return result;
    }


    /**
     *  코드 갯수 (config명,상위코드,레벨)
     */
	public static int getCodeCnt(String config, String code, int levels) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
//        ArrayList list = null;
        String sql = "";
        int result = 0;
        String gubun = getConfigValue(config);

        try {
            connMgr = new DBConnectionManager();

//            list = new ArrayList();

            sql  = " select count(*) cnt from tz_code            ";
            sql += "  where gubun  = " + StringManager.makeSQL(gubun);
            sql += "    and levels = " + levels;
            if (levels > 1) {
              sql += "    and upper = " + StringManager.makeSQL(code);
            }

            ls = connMgr.executeQuery(sql);

            if  (ls.next()) {
                result = ls.getInt("cnt");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }


    /**
     *  코드 리스트 (config명,상위코드,레벨)
     */
     @SuppressWarnings("unchecked")
	public static ArrayList getCodeList (String config, String code, int levels) throws Exception {
         DBConnectionManager connMgr = null;
         ListSet ls     = null;
         ArrayList list = null;
         String sql = "";
         CodeData data = null;
         String gubun = getConfigValue(config);

         try {
             connMgr = new DBConnectionManager();
             list = new ArrayList();

             sql  = " select code, codenm from tz_code            ";
             sql += "  where gubun  = " + StringManager.makeSQL(gubun);
             sql += "    and levels = " + levels;
             if (levels > 1) {
               sql += "    and upper = " + StringManager.makeSQL(code);
             }
             sql += " order by code asc";
             //System.out.println(sql);

             ls = connMgr.executeQuery(sql);

             while (ls.next()) {
                 data = new CodeData();

                 data.setCode(ls.getString("code"));
                 data.setCodenm(ls.getString("codenm"));
                 list.add(data);
             }
         }
         catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex);
             throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
         }
         finally {
             if(ls != null) { try { ls.close(); }catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }

         return list;
     }


    /**
     * 코드디폴트값(첫번째 코드값)
     * @param config     config명
     * @param code       상위코드
     * @param levels     레벨
     * @return result    코드값
     * @throws Exception
     */
    public static String getCodeDefault (String config, String code, int levels) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        CodeData data = null;
        String gubun = getConfigValue(config);

        try {
            connMgr = new DBConnectionManager();

            sql  = " select code from TZ_CODE        ";
            sql += "  where gubun  = " + StringManager.makeSQL(gubun);
            sql += "    and levels = " + levels;
            if (levels > 1) {
              sql += "    and upper = " + StringManager.makeSQL(code);
            }
Log.err.println("sql11111111111111111111111::::"+sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data=new CodeData();
                data.setCode(ls.getString("code"));
                result = data.getCode();
            }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return result;
    }

}
