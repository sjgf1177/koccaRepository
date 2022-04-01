//**********************************************************
//  1. ��      ��: �ڵ� ����
//  2. ���α׷��� : CodeConfigBean.java
//  3. ��      ��: �ڵ� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7.  14
//  7. ��      ��:
//**********************************************************

package com.credu.system;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.StringManager;

/**
 * �ڵ� ����(ADMIN)
 *
 * @date   : 2003. 7
 * @author : s.j Jung
 */
public class CodeConfigBean {

    public CodeConfigBean() {}



    /**
    * ���ھտ� 0 ���� ä���,  (����,����)
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
    *  ���Ǳ� ����Ÿ�� (����Ÿ��)
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
     *  �ڵ� üũ�ڽ� (config��,����Ʈ�ڽ���,��ü����)
     */
     public static String getCodeCheck(String config, String name, boolean allcheck) throws Exception {
         return getCodeCheck (config, name, "", allcheck);
         /**(config��(0),�ڵ�,����,üũ�ڽ���(0),���ð�(0),�̺�Ʈ��,��ü����(false)) **/
     }

     /**
      *  �ڵ� üũ�ڽ� (config��,����Ʈ�ڽ���,���ð�,��ü����)
      */
      public static String getCodeCheck(String config, String name, String selected, boolean allcheck) throws Exception {
          return getCodeCheck (config, "", 1, name, selected, "", allcheck);
          /**(config��(0),�ڵ�,����,üũ�ڽ���(0),���ð�(0),�̺�Ʈ��,��ü����(false)) **/
      }


    /**
    *  �ڵ� ����Ʈ�ڽ� (config��,����Ʈ�ڽ���,���ð�) 3
    */
    public static String getCodeSelect (String config, String name, String selected) throws Exception {
        return getCodeSelect (config, "", 1, name, selected, "", 0, "");
    }

    /**
    *  �ڵ� ����Ʈ�ڽ� (config��,����Ʈ�ڽ���,���ð�,��ü����) 4
    */
    public static String getCodeSelect (String config, String name, String selected, int allcheck) throws Exception {
        return getCodeSelect (config, "", 1, name, selected, "", allcheck, "");
        /**(config��(0),�ڵ�,����,����Ʈ�ڽ���(0),���ð�(0),�̺�Ʈ��,��ü����(0)) **/
    }

    /**
    *  �ڵ� ����Ʈ�ڽ� (config��,�ڵ�,����,����Ʈ�ڽ���,�̺�Ʈ��,���ð�) 5
    */
    public static String getCodeSelect (String config, String code, int levels, String name, String selected) throws Exception {
        return getCodeSelect (config, code, levels, name, selected, "",0, "");
    }

    /**
    *  �ڵ� ����Ʈ�ڽ� (config��,�ڵ�,����,����Ʈ�ڽ���,���ð�,�̺�Ʈ��,��ü����) 6
    */
    public static String getCodeSelect (String config, String code, int levels, String name, String selected, int allcheck) throws Exception {
        return getCodeSelect (config, code, levels, name, selected, "", allcheck, "");
    }

    /**
    *  �ڵ� ����Ʈ�ڽ� (config��,�ڵ�,����Ʈ�ڽ���,���ð�,�̺�Ʈ��) 4
    */
    public static String getCodeSelect (String config, String name, String selected, String event) throws Exception {
        return getCodeSelect (config, "", 1, name, selected, event, 0, "");
    }

    /**
    *  �ڵ� ����Ʈ�ڽ� (config��,�ڵ�,����Ʈ�ڽ���,���ð�,�̺�Ʈ��,��ü����) 5
    */
    public static String getCodeSelect (String config, String name, String selected, String event, int allcheck) throws Exception {
        return getCodeSelect (config, "", 1, name, selected, event, allcheck, "");
    }

    /**
    *  �ڵ� ����Ʈ�ڽ� (config��, ����Ʈ�ڽ���, ���ð�, �̺�Ʈ��, ��ü����, �κ����)6
    */
    public static String getCodeSelect (String config, String name, String selected, String event, int allcheck, String aPart) throws Exception {
        return getCodeSelect (config, "", 1, name, selected, event, allcheck, aPart);
    }

    /**
    *  �ڵ� ����Ʈ�ڽ� (config��,�ڵ�,����,����Ʈ�ڽ���,���ð�,�̺�Ʈ��,�Ϻ����) 7
      *  TZ_CONFIG �� �̿�
    *  allcheck�� ��ü���� : 0 -��ü����, 1 - ��ü����, 2 - ALL
    *  aPart��(2�ڸ�) --> ���ڸ��� ���̿� �ִ� �ڵ尪�� return�Ѵ�.
    */

    public static String getCodeSelect (String config, String code, int levels, String name, String selected, String event, int allcheck, String aPart) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        CodeData data = null;
        String gubun = getConfigValue(config);   //TZ_CONFIG ���̺��� ���� ���´�.
        String fChar = "";
        String sChar = "";

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
          result += " <option value=''>=== ��ü ===</option> \n";
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
    *  �ڵ� ����Ʈ�ڽ� (GUBUN,�ڵ�,����,����Ʈ�ڽ���,���ð�,�̺�Ʈ��,��ü����)
      *  TZ_CODEGUBUN �� �̿�
    *  ��ü���� : 0 -��ü����, 1 - ��ü����, 2 - ALL
    */
    public static String getCodeGubunSelect (String gubun, String code, int levels, String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;

        StringBuilder sql = new StringBuilder();
        CodeData data = null;

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
          result += " <option value=''>=== ��ü ===</option> \n";
        } else if (allcheck == 2) {
            result += " <option value='ALL'>ALL</option> \n";
        } else if( allcheck == 3 ){
			result += " <option value=''>=== ���� ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* CodeConfigBean.getCodeGubunSelect (�ڵ尪 ��ȸ �� selectbox �����) */ \n");
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
     *  �ڵ� üũ�ڽ� (GUBUN,�ڵ�,����,����Ʈ�ڽ���,���ð�,�̺�Ʈ��,��ü����)
       *  TZ_CODEGUBUN �� �̿�
     *  ��ü���� : true
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
         gubun = getConfigValue(gubun);   //TZ_CONFIG ���̺��� ���� ���´�.


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
    *  �ڵ屸�и� (config��)
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
    *  �ڵ�� (config��,�ڵ�)
    */
    public static String getCodeName (String config, String code) throws Exception {
        return getCodeName (config, code, 1);
    }

    /**
    *  �ڵ�� (config��,�ڵ�,����)
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
     *  �ڵ� ���� (config��,�����ڵ�,����)
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
     *  �ڵ� ����Ʈ (config��,�����ڵ�,����)
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
     * �ڵ����Ʈ��(ù��° �ڵ尪)
     * @param config     config��
     * @param code       �����ڵ�
     * @param levels     ����
     * @return result    �ڵ尪
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
