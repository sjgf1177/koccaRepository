
package com.credu.library;

 /**
 * <p>제목: SQL 변수처리 관련 라이브러리</p>
 * <p>설명: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author 이정한
 *@date 2003. 12
 *@version 1.0
 */
public class SQLString {
    /**
    * SQL 에서 변수에 '   ' 을 붙여 리턴한다.
    @param str  변수
    @return 'str'
    */
    public static String Format(String str) {
        return 	("'" + str + "'");
    }
    
    public static String Format(int i) {
        return	String.valueOf(i);
    }
    
    public static String Format(double d) {
        return	String.valueOf(d);
    }  
}
