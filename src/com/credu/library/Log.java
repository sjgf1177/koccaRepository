
package com.credu.library;


 /**
 * <p>����: �α� ���� ���̺귯��</p>
 * <p>����: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author ������
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
    * ������ Load �ɶ� LogSysWriter ��ü�� �����Ѵ�.
    @return LogSysWriter �� ��ȯ�Ѵ�.
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
    * ������ Load �ɶ� LogErrWriter ��ü�� �����Ѵ�.
    @return LogErrWriter �� ��ȯ�Ѵ�.
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
    * ������ Load �ɶ� LogInfoWriter ��ü�� �����Ѵ�.
    @return LogInfoWriter �� ��ȯ�Ѵ�.
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
    * ������ Load �ɶ� LogMailWriter ��ü�� �����Ѵ�.
    @return LogMailWriter �� ��ȯ�Ѵ�.
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
