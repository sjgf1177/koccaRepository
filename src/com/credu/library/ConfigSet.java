package com.credu.library;

import java.io.File;
import java.io.FileInputStream;
import java.util.StringTokenizer;

/**
 * <p>
 * 제목: config 파일 제어
 * </p>
 * <p>
 * 설명: 시스템 환경설정 config 파일(cresys.properties)을 제어하는 클래스
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Credu
 * </p>
 * 
 * @author 이정한
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
     *시스템 환경설정 config 파일(cresys.properties)의 위치를 절대경로로 설정한다.
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
     *ConfigSet class를 초기화한다. 시스템 환경설정 config 파일(cresys.properties)을 메모리에 올려
     * 실시간으로 읽는다. config 파일 변경시 바로 적용된다.
     */
    public ConfigSet() throws Exception {
        initialize();
    }

    /**
     * 시스템 환경설정 config 파일(cresys.properties)을 메모리에 올려 실시간으로 읽는다. config 파일 변경시
     * 바로 적용된다.
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
     * 시스템 환경설정 config 파일(cresys.properties)의 설정값을 boolean type 으로 리턴한다.
     * 
     * @param key
     *            cresys.properties 의 각 옵션별 설정명
     * @return boolean value 를 반환한다.
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
     * 시스템 환경설정 config 파일(cresys.properties)의 설정값을 int type 으로 리턴한다.
     * 
     * @param key
     *            cresys.properties 의 각 옵션별 설정명
     * @return int value 를 반환한다.
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
     * 시스템 환경설정 config 파일(cresys.properties)의 설정값을 String type 으로 리턴한다.
     * 
     * @param key
     *            cresys.properties 의 각 옵션별 설정명
     * @return String value 를 반환한다.
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
     * 시스템 환경설정 config 파일(cresys.properties)의 설정값중에서 파일이 업로드되는 디렉토리명을 String
     * type 으로 리턴한다.
     * 
     * @param key
     *            cresys.properties 의 각 옵션별 설정명
     * @param p_servletName
     *            파일을 업로드하는 Servlet Name
     * @return String 파일업로드될 디렉토리명을 반환한다.
     */
    public String getDir(String key, String p_servletName) {
        String v_dirKey = "";
        StringTokenizer st = new StringTokenizer(key, ";");
        while (st.hasMoreElements()) {
            String token = st.nextToken();
            int isDir = p_servletName.toLowerCase().indexOf(token);

            if (isDir > -1) {
                // v_dirKey = (token.equals("etest")?"exam":token);
                v_dirKey = token; // jkh 0224 etest 자체dir적용
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
