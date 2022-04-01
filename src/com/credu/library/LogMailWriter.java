package com.credu.library;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * <p>
 * ����: ���� �αװ��� ���̺귯��
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
public class LogMailWriter {
    private static PrintWriter writer = null;
    private final static Object lock = new Object();
    private static String today = null;
    private static boolean newLined = true;

    public LogMailWriter() {
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
            String directory = conf.getProperty("log.dir.mail");

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
     * ȯ�漳�����Ͽ��� ���Ϸα׸� �����Ұ����� ���θ� Ȯ���Ѵ�.
     * 
     * @return isPrintable �α׻�������
     */
    private boolean isPrintMode() {
        boolean isPrintable = true;
        try {
            ConfigSet conf = new ConfigSet();
            isPrintable = new Boolean(conf.getProperty("log.mail.trace")).booleanValue();
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
     * �α׸� �ش� �α����Ͽ� ���´�. msg@param Object servletName ���������� ���� ������Ʈ
     * 
     * @param Object userid Userid �� ���� ������Ʈ
     * @param String msg �޽��� �α�
     * @param Vector v ���� �޴� �н����� �߼ۼ�������
     */
    @SuppressWarnings("unchecked")
    public void println(Object servletName, Object userid, Vector v, String msg) {
        if (!isPrintMode())
            return;
        synchronized (lock) {
            if (newLined)
                printTime();
            writer.print(getPrefixInfo(servletName));
            writer.print(getValueInfo(userid));
            writer.println(msg);
            for (int i = 0; i < v.size(); i++) {
                writer.println((String) v.elementAt(i));
            }
            writer.println("");
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
            info.append(fullname + " : ");
        }
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
                info.append("elapsed=" + interval + "ms, ");

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