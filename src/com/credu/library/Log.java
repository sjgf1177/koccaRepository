
package com.credu.library;


 /**
 * <p>제목: 로그 관련 라이브러리</p>
 * <p>설명: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author 이정한
 *@date 2003. 12
 *@version 1.0
 */
public final class Log {
    public final static LogSysWriter sys = (LogSysWriter)getLogSysWriter();
    
    public final static LogErrWriter err = (LogErrWriter)getLogErrWriter();
    
    public final static LogInfoWriter info = (LogInfoWriter)getLogInfoWriter();
    
    public final static LogMailWriter mail = (LogMailWriter)getLogMailWriter();

    /**
    * Don't let anyone instantiate this class
    */
    private Log() {}

    /**
    * 서버가 Load 될때 LogSysWriter 객체를 생성한다.
    @return LogSysWriter 를 반환한다.
    */
    private static Object getLogSysWriter() {       
        LogSysWriter  logger = null;
        try {
            logger = new LogSysWriter();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return logger;
    }

    /**
    * 서버가 Load 될때 LogErrWriter 객체를 생성한다.
    @return LogErrWriter 를 반환한다.
    */
    private static Object getLogErrWriter() {       
        LogErrWriter  logger = null;  
        try {
            logger = new LogErrWriter();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return logger;
    }

    /**
    * 서버가 Load 될때 LogInfoWriter 객체를 생성한다.
    @return LogInfoWriter 를 반환한다.
    */
    private static Object getLogInfoWriter() {       
        LogInfoWriter  logger = null; 
        try {
            logger = new LogInfoWriter(); 
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return logger;
    }

    /**
    * 서버가 Load 될때 LogMailWriter 객체를 생성한다.
    @return LogMailWriter 를 반환한다.
    */
    private static Object getLogMailWriter() {       
        LogMailWriter  logger = null;
        try {
            logger = new LogMailWriter();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return logger;
    }
}
