package com.credu.library;

import java.io.File;
import java.io.FileInputStream;
import java.util.StringTokenizer;

/**
 * <p>
 * ����: config ���� ����
 * </p>
 * <p>
 * ����: �ý��� ȯ�漳�� config ����(cresys.properties)�� �����ϴ� Ŭ����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Credu
 * </p>
 * 
 * @author ������
 *@date 2003. 12
 *@version 1.0
 */
public class ConfigSet {
    protected static Object lock = new Object();
    protected static java.util.Properties props = null;
    private static long cresys_last_modified = 0;
    private static long bulletin_last_modified = 0;
    private static long mail_last_modified = 0;
    private static long sqlList_last_modified = 0;
    protected static long lastModified = 0;

    /**
     *�ý��� ȯ�漳�� config ����(cresys.properties)�� ��ġ�� �����η� �����Ѵ�.
     */
    // Real Server
    //private static String cresys_file_name = "/data/kocca/lmswebapp/WEB-INF/classes/conf/cresys.properties";
    // dev
    private static String cresys_file_name = "C:/kocca2021_aa/workspace/kocca/webroot/WEB-INF/classes/conf/cresys.properties";

    // Local Server
    // private static String cresys_file_name =
    // "C:/work/academy/KOCCA_DEV/webroot/WEB-INF/classes/conf/cresys.properties";
    // private static String cresys_file_name =
    // "d:/bea6.1/wlserver6.1/config/SmotorDomain/applications/DefaultWebApp/WEB-INF/classes/conf/cresys.properties";

    /**
     *ConfigSet class�� �ʱ�ȭ�Ѵ�. �ý��� ȯ�漳�� config ����(cresys.properties)�� �޸𸮿� �÷�
     * �ǽð����� �д´�. config ���� ����� �ٷ� ����ȴ�.
     */
    public ConfigSet() throws Exception {
        initialize();
    }

    /**
     * �ý��� ȯ�漳�� config ����(cresys.properties)�� �޸𸮿� �÷� �ǽð����� �д´�. config ���� �����
     * �ٷ� ����ȴ�.
     */
    protected void initialize() throws Exception {
        synchronized (lock) {
            try {
                boolean needUpdate = false;

                File cresys_file = new File(cresys_file_name);
                if (!cresys_file.canRead())
                    throw new Exception(this.getClass().getName() + " - Can't open cresys.properties : " + cresys_file_name);

                if ((cresys_last_modified != cresys_file.lastModified()) || props == null) {
                    needUpdate = true;
                } else {
                    String bulletin_file_name = props.getProperty("bulletin.file.name");
                    if (bulletin_file_name != null) {
                        File bulletin_file = new File(bulletin_file_name);
                        if (!bulletin_file.canRead())
                            throw new Exception(this.getClass().getName() + " - Can't open bulletin.properties : " + bulletin_file_name);

                        if (bulletin_last_modified != bulletin_file.lastModified()) {
                            needUpdate = true;
                        }
                    }

                    String mail_file_name = props.getProperty("mail.file.name");
                    if (mail_file_name != null) {
                        File mail_file = new File(mail_file_name);
                        if (!mail_file.canRead())
                            throw new Exception(this.getClass().getName() + " - Can't open mail.properties : " + mail_file_name);

                        if (mail_last_modified != mail_file.lastModified()) {
                            needUpdate = true;
                        }
                    }

                    String sqlList_file_name = props.getProperty("sqlList.file.name");
                    if (sqlList_file_name != null) {
                        File sqlList_file = new File(sqlList_file_name);
                        if (!sqlList_file.canRead())
                            throw new Exception(this.getClass().getName() + " - Can't open mail.properties : " + sqlList_file_name);

                        if (sqlList_last_modified != sqlList_file.lastModified()) {
                            needUpdate = true;
                        }
                    }
                }

                if (needUpdate) {
                    props = new java.util.Properties();

                    FileInputStream cresys_fin = new FileInputStream(cresys_file);
                    props.load(new java.io.BufferedInputStream(cresys_fin));
                    cresys_fin.close();
                    cresys_last_modified = cresys_file.lastModified();

                    String bulletin_file_name = props.getProperty("bulletin.file.name");
                    if (bulletin_file_name != null) {
                        File bulletin_file = new File(bulletin_file_name);
                        if (!bulletin_file.canRead())
                            throw new Exception(this.getClass().getName() + " - Can't open bulletin.properties : " + bulletin_file_name);

                        FileInputStream bulletin_fin = new FileInputStream(bulletin_file);
                        props.load(new java.io.BufferedInputStream(bulletin_fin));
                        bulletin_fin.close();
                        bulletin_last_modified = bulletin_file.lastModified();
                    }

                    String mail_file_name = props.getProperty("mail.file.name");
                    if (mail_file_name != null) {
                        File mail_file = new File(mail_file_name);
                        if (!mail_file.canRead())
                            throw new Exception(this.getClass().getName() + " - Can't open mail.properties : " + mail_file_name);

                        FileInputStream mail_fin = new FileInputStream(mail_file);
                        props.load(new java.io.BufferedInputStream(mail_fin));
                        mail_fin.close();
                        mail_last_modified = mail_file.lastModified();
                    }
                    System.out.println(props.getProperty("sqlList.file.name"));
                    String sqlList_file_name = props.getProperty("sqlList.file.name");
                    if (sqlList_file_name != null) {
                        File sqlList = new File(sqlList_file_name);
                        if (!sqlList.canRead())
                            throw new Exception(this.getClass().getName() + " - Can't open mail.properties : " + sqlList_file_name);

                        FileInputStream sqlList_fin = new FileInputStream(sqlList);
                        props.load(new java.io.BufferedInputStream(sqlList_fin));
                        sqlList_fin.close();
                        sqlList_last_modified = sqlList.lastModified();
                    }
                    ConfigSet.lastModified = System.currentTimeMillis();
                } // end if
            } catch (Exception ex) {
                ex.printStackTrace();
                ConfigSet.lastModified = 0;
                cresys_last_modified = 0;
                bulletin_last_modified = 0;
                mail_last_modified = 0;
                Log.sys.println(this, ex, "Happen to ConfigSet.initialize()");
                throw new Exception(this.getClass().getName() + " - Can't open properties: " + ex.getMessage());
            }
        } // end of sunchronized(lock);
    }

    /**
     * �ý��� ȯ�漳�� config ����(cresys.properties)�� �������� boolean type ���� �����Ѵ�.
     * 
     * @param key
     *            cresys.properties �� �� �ɼǺ� ������
     * @return boolean value �� ��ȯ�Ѵ�.
     */
    public boolean getBoolean(String key) {
        boolean value = false;
        try {
            value = (new Boolean(props.getProperty(key))).booleanValue();
        } catch (Exception ex) {
            Log.sys.println(this, ex, "Happen to ConfigSet.getBoolean(\"" + key + "\")");
            throw new IllegalArgumentException("Illegal Boolean Key : " + key);
        }
        return value;
    }

    /**
     * �ý��� ȯ�漳�� config ����(cresys.properties)�� �������� int type ���� �����Ѵ�.
     * 
     * @param key
     *            cresys.properties �� �� �ɼǺ� ������
     * @return int value �� ��ȯ�Ѵ�.
     */
    public int getInt(String key) {
        int value = -1;
        try {
            value = Integer.parseInt(props.getProperty(key));
        } catch (Exception ex) {
            Log.sys.println(this, ex, "Happen to ConfigSet.getInt(\"" + key + "\")");
            throw new IllegalArgumentException("Illegal Integer Key : " + key);
        }
        return value;
    }

    /**
     * �ý��� ȯ�漳�� config ����(cresys.properties)�� �������� String type ���� �����Ѵ�.
     * 
     * @param key
     *            cresys.properties �� �� �ɼǺ� ������
     * @return String value �� ��ȯ�Ѵ�.
     */
    public String getProperty(String key) {
        String value = null;
        try {
            String tmp = props.getProperty(key);
            if (tmp == null)
                throw new Exception("value of key(\"" + key + "\")  is null");
            value = StringManager.korEncode(tmp);
        } catch (Exception ex) {
            Log.sys.println(this, ex, "Happen to ConfigSet.getProperty(\"" + key + "\")");
            throw new IllegalArgumentException("Illegal String Key : " + key);
        }
        return value;
    }

    public java.util.Properties getProperties() {
        return props;
    }

    public long lastModified() {
        return lastModified;
    }

    /**
     * �ý��� ȯ�漳�� config ����(cresys.properties)�� �������߿��� ������ ���ε�Ǵ� ���丮���� String
     * type ���� �����Ѵ�.
     * 
     * @param key
     *            cresys.properties �� �� �ɼǺ� ������
     * @param p_servletName
     *            ������ ���ε��ϴ� Servlet Name
     * @return String ���Ͼ��ε�� ���丮���� ��ȯ�Ѵ�.
     */
    public String getDir(String key, String p_servletName) {
        String v_dirKey = "";
        StringTokenizer st = new StringTokenizer(key, ";");
        while (st.hasMoreElements()) {
            String token = st.nextToken();
            int isDir = p_servletName.toLowerCase().indexOf(token);

            if (isDir > -1) {
                // v_dirKey = (token.equals("etest")?"exam":token);
                v_dirKey = token; // jkh 0224 etest ��üdir����
                return v_dirKey;
            } else {
                v_dirKey = "default";
            }
        }
        // System.out.println("v_dirKeypre==");
        // System.out.println("v_dirKey=="+v_dirKey);
        return v_dirKey;
    }
}
