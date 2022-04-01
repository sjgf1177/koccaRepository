package com.credu.library;

import java.io.*;
import java.util.*;

/**
 * <p>
 * 제목: File 관리 라이브러리
 * </p>
 * <p>
 * 설명:
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
public class FileManager {

    public FileManager() {
    }

    /**
     * 첨부파일이 있는경우 List화면에서 Icon으로 표시한다.
     * 
     * @param filename 업로드되는 파일명
     * @return result 파일확장자에 따른 이미지아이콘 경로를 반환함
     */
    public static String getFileTypeImage(String filename) throws Exception {
        String fvalue = "";

        if (!filename.equals("")) {
            fvalue = "<img border = 0 src = '/images/common/";
            if (StringManager.rightstring(filename, 4).toLowerCase().equals(".htm"))
                fvalue = fvalue + "etc_html.gif'>";
            else if (StringManager.rightstring(filename, 4).toLowerCase().equals(".jpg"))
                fvalue = fvalue + "etc_html.gif'>";
            else if (StringManager.rightstring(filename, 4).toLowerCase().equals(".gif"))
                fvalue = fvalue + "etc_html.gif'>";
            else if (StringManager.rightstring(filename, 4).toLowerCase().equals(".txt"))
                fvalue = fvalue + "etc_txt.gif'>";
            else if (StringManager.rightstring(filename, 4).toLowerCase().equals(".zip"))
                fvalue = fvalue + "etc_zip.gif'>";
            else if (StringManager.rightstring(filename, 4).toLowerCase().equals(".hwp"))
                fvalue = fvalue + "etc_hwp.gif'>";
            else if (StringManager.rightstring(filename, 4).toLowerCase().equals(".doc"))
                fvalue = fvalue + "etc_doc.gif'>";
            else if (StringManager.rightstring(filename, 4).toLowerCase().equals(".ppt"))
                fvalue = fvalue + "etc_ppt.gif'>";
            else
                fvalue = fvalue + "etc_file.gif'>";
        }
        return fvalue;
    }

    /**
     * 첨부파일을 삭제한다.(배열로 저장된 다수의 파일)
     * 
     * @param savenm 저장되있는 파일명(배열)
     * @param upSeq 삭제될 파일갯수
     */
    public static void deleteFile(String[] savenm, int upSeq) throws Exception {
        try {
            ConfigSet conf = new ConfigSet();
            /**
             * 2009.11.27 DB의 save_file 컬럼에 상대경로와 파일명이 들어가게 수정 dir.home :
             * webroot 까지의 절대 경로 v_savenm : /upload/'해당디렉토리'/저장파일명
             * 
             * new File(경로, 파일명) => new File(파일경로) 로 변경
             */
            //String v_updir = conf.getProperty("dir.upload");
            String v_updir = conf.getProperty("dir.home");

            for (int i = 0; i < upSeq; i++) {
                if (!savenm[i].equals("")) {
                    //File f = new File(v_updir, v_savenm[i]);
                    File f = new File(v_updir + savenm[i]);
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 첨부파일을 삭제한다.(Vector로 저장된 다수의 파일)
     * 
     * @param savenm 저장되있는 파일명(Vector)
     */
    public static void deleteFile(Vector<String> savefile) throws Exception {
        try {
            ConfigSet conf = new ConfigSet();
            if (savefile != null) {
                for (int i = 0; i < savefile.size(); i++) {
                    String v_savenm = (String) savefile.elementAt(i);
                    if (!v_savenm.equals("")) {
                        /**
                         * 2009.11.27 DB의 save_file 컬럼에 상대경로와 파일명이 들어가게 수정
                         * dir.home : webroot 까지의 절대 경로 v_savenm :
                         * /upload/'해당디렉토리'/저장파일명
                         * 
                         * new File(경로, 파일명) => new File(파일경로) 로 변경
                         */

                        //String v_dirKey = conf.getDir(conf.getProperty("dir.upload"), getServletName(v_savenm));
                        //String v_updir = conf.getProperty("dir.upload." + v_dirKey);
                        String v_updir = conf.getProperty("dir.home");
                        //File f = new File(v_updir, v_savenm);
                        File f = new File(v_updir + v_savenm);
                        System.out.println("file : " + f);
                        System.out.println("file exist : " + f.exists());
                        if (f.exists()) {
                            f.delete();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.sys.println(ex, "Happen to FileManager.deleteFile(Vector savefile)");
        }
    }

    /**
     * 첨부파일을 삭제한다.(ArrayList로 저장된 다수의 파일)
     * 
     * @param savenm 저장되있는 파일명(ArrayList)
     */
    public static void deleteFile(ArrayList<String> savefile) throws Exception {
        try {
            ConfigSet conf = new ConfigSet();
            if (savefile != null) {
                for (int i = 0; i < savefile.size(); i++) {
                    String v_savenm = (String) savefile.get(i);
                    if (!v_savenm.equals("")) {
                        /**
                         * 2009.11.27 DB의 save_file 컬럼에 상대경로와 파일명이 들어가게 수정
                         * dir.home : webroot 까지의 절대 경로 v_savenm :
                         * /upload/'해당디렉토리'/저장파일명
                         * 
                         * new File(경로, 파일명) => new File(파일경로) 로 변경
                         */

                        //String v_dirKey = conf.getDir(conf.getProperty("dir.upload"), getServletName(v_savenm));
                        //String v_updir = conf.getProperty("dir.upload." + v_dirKey);
                        String v_updir = conf.getProperty("dir.home");
                        //File f = new File(v_updir, v_savenm);
                        File f = new File(v_updir + v_savenm);
                        System.out.println("file : " + f);
                        System.out.println("file exist : " + f.exists());
                        if (f.exists()) {
                            f.delete();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.sys.println(ex, "Happen to FileManager.deleteFile(Vector savefile)");
        }
    }

    /**
     * 첨부파일을 삭제한다.
     * 
     * @param savenm 저장되있는 파일명
     */
    public static void deleteFile(String p_savenm) throws Exception {
        // boolean result = true;
        try {
            if (!p_savenm.equals("")) {
                ConfigSet conf = new ConfigSet();
                /**
                 * 2009.11.27 DB의 save_file 컬럼에 상대경로와 파일명이 들어가게 수정 dir.home :
                 * webroot 까지의 절대 경로 v_savenm : /upload/'해당디렉토리'/저장파일명
                 * 
                 * new File(경로, 파일명) => new File(파일경로) 로 변경
                 */
                //String v_dirKey = conf.getDir(conf.getProperty("dir.upload"), getServletName(p_savenm));
                //String v_updir = conf.getProperty("dir.upload." + v_dirKey);
                String v_updir = conf.getProperty("dir.home");

                //File f = new File(v_updir, p_savenm);
                File f = new File(v_updir + p_savenm);
                if (f.exists()) {
                    f.delete();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.sys.println(ex, "Happen to FileManager.deleteFile(String " + p_savenm + ")");
        }
    }

    /**
     * 지정폴더에서 첨부파일을 삭제한다.
     * 
     * @param savenm 저장되있는 파일명
     * @param p_dirKey 저장되있는 지정폴더
     */
    public static void deleteFile(String p_savenm, String p_dirKey) throws Exception {
        // boolean result = true;
        try {
            if (!p_savenm.equals("")) {
                ConfigSet conf = new ConfigSet();
                /**
                 * 2009.11.27 DB의 save_file 컬럼에 상대경로와 파일명이 들어가게 수정 dir.home :
                 * webroot 까지의 절대 경로 v_savenm : /upload/'해당디렉토리'/저장파일명
                 * 
                 * new File(경로, 파일명) => new File(파일경로) 로 변경
                 */
                //String v_updir = conf.getProperty("dir.upload." + p_dirKey);
                String v_updir = conf.getProperty("dir.home");
                //File f = new File(v_updir, p_savenm);
                File f = new File(v_updir + p_savenm);
                if (f.exists()) {
                    f.delete();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.sys.println(ex, "Happen to FileManager.deleteFile(String " + p_savenm + ")");
        }
    }

    public static String getServletName(String p_savenm) {
        String v_servletName = p_savenm;
        StringTokenizer st = new StringTokenizer(p_savenm, "_");
        if (st.hasMoreElements()) {
            v_servletName = st.nextToken().toLowerCase();
        }
        return v_servletName;
    }
}
