package com.credu.library;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * <p>
 * ����: System ���� �αװ��� ���̺귯��
 * </p>
 * <p>
 * ����:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Credu
 * </p>
 * 
 * @author ������
 * @date 2003. 12
 * @version 1.0
 */
public class LogSysWriter {
    private static PrintWriter writer = null;
    private final static Object lock = new Object();
    private static String today = null;
    private static boolean newLined = true;

    public LogSysWriter() {
        synchronized (lock) {
            checkDate();
        }
    }

    /**
     * ���糯¥�� �������� ���θ� Ȯ���Ͽ� ������ ���糯¥�� �α� ������ �����Ѵ�.
     */
    private static void checkDate() {
        try {
            String day = FormatDate.getDate("yyyyMMdd");
            if (day.equals(today))
                return;

            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
                writer = null;
            }
            today = day;
            String logname = today + ".log";
            ConfigSet conf = new ConfigSet();
            String directory = conf.getProperty("log.dir.sys");

            File logDir = new File(directory);
            if (!logDir.isDirectory()) {
                logDir.mkdirs();
            }

            File file = new File(directory, logname);
            String filename = file.getAbsolutePath();
            FileWriter fw = new FileWriter(filename, true);// APPEND MODE
            writer = new PrintWriter(new java.io.BufferedWriter(fw), new Boolean(conf.getProperty("log.autoflush")).booleanValue() // AUTO Flush
            );
        } catch (Exception e) {
            e.printStackTrace();
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
            writer.println("Can't open log file : " + e.getMessage());
            writer.println("Log will be printed into System.out");
        }
    }

    /**
     * ȯ�漳�����Ͽ��� �ý��۷α׸� �����Ұ����� ���θ� Ȯ���Ѵ�.
     * 
     * @return isPrintable �α׻�������
     */
    private boolean isPrintMode() {
        boolean isPrintable = true;
        try {
            ConfigSet conf = new ConfigSet();
            isPrintable = new Boolean(conf.getProperty("log.sys.trace")).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isPrintable;
    }

    /**
     * �α׻������� �ð�
     */
    private void printTime() {
        try {
            checkDate();
            writer.write(FormatDate.getDate("HH:mm:ss") + ' ');
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �α׸� �ش� �α����Ͽ� ���´�.
     * 
     * @param Object servletName �������� ���� ������Ʈ
     * @param Exception e
     * @param String msg �޽��� �α�
     */
    public void println(Object servletName, Exception e, String msg) {
        if (!isPrintMode())
            return;
        synchronized (lock) {
            if (newLined)
                printTime();
            writer.println(getPrefixInfo(servletName));
            writer.print(ErrorManager.getErrorStackTrace(e, false));
            writer.println(msg);
            writer.println("");
            newLined = true;
        }
    }

    /**
     * �α׸� �ش� �α����Ͽ� ���´�.
     * 
     * @param Object servletName �������� ���� ������Ʈ
     * @param Exception e
     */
    public void println(Object servletName, Exception e) {
        if (!isPrintMode())
            return;
        synchronized (lock) {
            if (newLined)
                printTime();
            writer.println(getPrefixInfo(servletName));
            writer.println(ErrorManager.getErrorStackTrace(e, false));
            newLined = true;
        }
    }

    /**
     * �α׸� �ش� �α����Ͽ� ���´�.
     * 
     * @param Object userid Userid�� ���� ������Ʈ
     * @param String msg �޽��� �α�
     */
    public void println(Object userid, String msg) {
        if (!isPrintMode())
            return;
        synchronized (lock) {
            if (newLined)
                printTime();
            writer.println(getValueInfo(userid));
            writer.println("  " + msg);
            newLined = true;
        }
    }

    /**
     * �α׸� �ش� �α����Ͽ� ���´�.
     * 
     * @param Exception e
     * @param String msg �޽��� �α�
     */
    public void println(Exception q, String msg) {
        if (!isPrintMode())
            return;
        synchronized (lock) {
            if (newLined)
                printTime();
            writer.println("");
            writer.print(ErrorManager.getErrorStackTrace(q, false));
            writer.println(msg);
            writer.println("");
            newLined = true;
        }
    }

    /**
     * �α׸� �ش� �α����Ͽ� ���´�.
     * 
     * @param String msg �޽��� �α�
     */
    public void println(String msg) {
        if (!isPrintMode())
            return;
        synchronized (lock) {
            if (newLined)
                printTime();
            writer.println(msg);
            newLined = true;
        }
    }

    @SuppressWarnings("unchecked")
    protected String getPrefixInfo(Object o) {
        StringBuffer info = new StringBuffer();
        info.append('[');

        if (o == null) {
            info.append("null");
        } else {
            Class c = o.getClass();
            String fullname = c.getName();
            info.append(fullname + "] ");
        }

        return info.toString();
    }

    @SuppressWarnings("unchecked")
    protected String getValueInfo(Object o) {
        // int interval = 0;
        StringBuffer info = new StringBuffer();

        try {

            if (o == null) {
                info.append("null");
            } else if (o instanceof RequestBox) {
                RequestBox box = (RequestBox) o;

                String user = box.getSession("userid");
                if (user != null)
                    info.append(user + "] ");
            } else {
                Class c = o.getClass();
                String fullname = c.getName();
                String name = null;
                int index = fullname.lastIndexOf('.');
                if (index == -1)
                    name = fullname;
                else
                    name = fullname.substring(index + 1);
                info.append(name);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return info.toString();
    }
}
