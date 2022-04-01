package com.credu.library;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * <p>
 * ����: ���μ��� ���� �αװ��� ���̺귯��
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
public class LogInfoWriter {
    private static PrintWriter writer = null;
    private final static Object lock = new Object();
    private static String today = null;
    private static boolean newLined = true;

    public LogInfoWriter() {
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
            String directory = conf.getProperty("log.dir.info");

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
     * ȯ�漳�����Ͽ��� �����α׸� �����Ұ����� ���θ� Ȯ���Ѵ�.
     * 
     * @return isPrintable �α׻�������
     */
    private boolean isPrintMode() {
        boolean isPrintable = true;
        try {
            ConfigSet conf = new ConfigSet();
            isPrintable = new Boolean(conf.getProperty("log.info.trace")).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isPrintable;
    }

    /**
     *�α׻������� �ð�
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
     * @param Object userid Userid�� ���� ������Ʈ
     * @param String msg �޽��� �α�
     */
    public void println(Object servletName, Object userid, String msg) {
        if (!isPrintMode())
            return;
        synchronized (lock) {
            if (newLined)
                printTime();
            writer.print(getPrefixInfo(servletName));
            writer.print(getValueInfo(userid));
            writer.println(msg);
            newLined = true;
        }
    }

    /**
     * �α׸� �ش� �α����Ͽ� ���´�.
     * 
     * @param Object servletName �������� ���� ������Ʈ
     * @param Object userid userid �� ���� ������Ʈ
     */
    public void println(Object servletName, Object userid) {
        if (!isPrintMode())
            return;
        synchronized (lock) {
            if (newLined)
                printTime();
            writer.print(getPrefixInfo(servletName));
            writer.println(getValueInfo(userid));
            newLined = true;
        }
    }

    /**
     * �α׸� �ش� �α����Ͽ� ���´�.
     * 
     * @param Object servletName �������� ���� ������Ʈ
     *@param String msg �޽��� �α�
     */
    public void println(Object servletName, String msg) {
        if (!isPrintMode())
            return;
        synchronized (lock) {
            if (newLined)
                printTime();
            writer.print(getPrefixInfo(servletName));
            writer.println("]  " + msg);
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

    /**
     * �α׸� �ش� �α����Ͽ� ���´�.
     * 
     * @param Object servletName �������� ���� ������Ʈ
     * @param Object userid userid �� ���� ������Ʈ
     */
    public void println(Object servletName, Object userid, boolean isEtest) {
        if (isEtest) {
            if (!isPrintMode())
                return;
            synchronized (lock) {
                if (newLined)
                    printTime();
                writer.print(getPrefixInfo(servletName));
                writer.println(getValueInfo(userid));
                writer.println(getEtestInfo(userid));
                newLined = true;
            }
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
            info.append(fullname);
        }
        //   info.append("] ");

        return info.toString();
    }

    @SuppressWarnings("unchecked")
    protected String getValueInfo(Object o) {
        int interval = 0;
        StringBuffer info = new StringBuffer();

        try {

            if (o == null) {
                info.append("null");
            } else if (o instanceof RequestBox) {
                RequestBox box = (RequestBox) o;

                String start = box.getString("starttime");
                if (!start.equals("")) {
                    interval = FormatDate.getMilliSecDifference(start, FormatDate.getDate("yyyyMMddHHmmssSSS"));
                }
                info.append(" : elapsed=" + interval + "ms, ");

                String user = box.getSession("userid");
                String userip = box.getString("userip");

                if (user != null && userip != null)
                    info.append(user + ", " + userip + "] ");
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

    protected String getEtestInfo(Object o) {
        // int interval = 0;
        StringBuffer info = new StringBuffer();

        try {
            if (o instanceof RequestBox) {
                RequestBox box = (RequestBox) o;

                String v_etestsubj = box.getString("p_etestsubj");
                String v_year = box.getString("p_gyear");
                String v_etestcode = box.getString("p_etestcode");
                String v_userid = box.getSession("userid");
                int v_etestnum = box.getInt("p_etestnum");
                String v_answer = box.getString("p_answer");
                String v_etest = box.getString("p_etest");
                String v_started = box.getString("p_started");
                String v_ended = box.getString("p_ended");

                info.append("   " + v_etestsubj + v_year + v_etestcode + "." + v_userid + ".txt,   Etestnum : " + v_etestnum + ",    Start : " + v_started + ",    End : " + v_ended + "  \r\n");
                info.append("    Etest : " + v_etest + "   \r\n");
                info.append("    Answer : " + v_answer + "   \r\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return info.toString();
    }
}