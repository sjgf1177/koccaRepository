package com.credu.library;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <p>
 * 제목: File Move 라이브러리
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
public class FileMove {

    public FileMove() {
    }

    /**
     * 파일을 p_thisPath 에서 p_thatPath 로 이동한다.
     * 
     * @param p_thatPath 이동할 경로
     * @param p_thisPath 이동하기전 경로
     * @param p_fileName 파일명
     * @return boolean 파일이동 성공여부
     */
    public boolean move(String p_thatPath, String p_thisPath, String p_fileName) {
        boolean move_success = false;
        // boolean delete_success = false;
        int length = 0;

        String system_slash = File.separator; //   해당 시스템의 분리자를 얻는다

        try {
            File tempFile = new File(p_thisPath + system_slash + p_fileName); //   임시폴더에서 이동할 파일명의 File 객체 생성한다

            FileInputStream fis = new FileInputStream(tempFile);
            //  tempFile에서 내용을 읽기 준비중		
            BufferedInputStream bis = new BufferedInputStream(fis);
            //  읽은 데이터를 bufferedInputStream 에 임시저장

            byte[] buf = new byte[1024];

            File realFile = new File(p_thatPath + system_slash + p_fileName); //   임시폴더에서 이동할 파일명의 File 객체 생성한다

            FileOutputStream fos = new FileOutputStream(realFile);
            //  realFile에 내용을 쓰기 준비중
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            while ((length = bis.read(buf)) > 0) {
                //  한줄씩 읽기 시작 - 읽을 줄이 있을때까지 무한루프를 돈다
                bos.write(buf, 0, length); //  buf 만큼 쓴다				
            }
            bos.flush();

            fis.close();
            bis.close();
            fos.close();
            bos.close();
            move_success = true;
            if (move_success) {
                // boolean b = tempFile.delete();			//  파일이동이 제대로 되었다면 해당 임시파일 삭제한다
                // System.out.println(" move delete is ok ======? " + b+ " /// p_thisPath + system_slash + p_fileName==>" + p_thisPath + system_slash + p_fileName);
            }
        } catch (Exception ie) {
            move_success = false;
            ie.printStackTrace();
        }
        return move_success;
    }

    /**
     * 파일을 p_thisPath 에서 p_thatPath 로 이동한다.
     * 
     * @param p_thatPath 이동할 경로
     * @param p_thisPath 이동하기전 경로
     * @param p_fileName 파일명
     * @return boolean 파일이동 성공여부
     */
    public boolean copy(String p_thatPath, String p_thisPath, String p_fileName) {
        boolean move_success = false;
        // boolean delete_success = false;
        int length = 0;

        String system_slash = File.separator; //   해당 시스템의 분리자를 얻는다

        try {
            File tempFile = new File(p_thisPath + system_slash + p_fileName); //   임시폴더에서 이동할 파일명의 File 객체 생성한다

            FileInputStream fis = new FileInputStream(tempFile);
            //  tempFile에서 내용을 읽기 준비중		
            BufferedInputStream bis = new BufferedInputStream(fis);
            //  읽은 데이터를 bufferedInputStream 에 임시저장

            byte[] buf = new byte[1024];

            File realFile = new File(p_thatPath + system_slash + p_fileName); //   임시폴더에서 이동할 파일명의 File 객체 생성한다

            FileOutputStream fos = new FileOutputStream(realFile);
            //  realFile에 내용을 쓰기 준비중
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            while ((length = bis.read(buf)) > 0) {
                //  한줄씩 읽기 시작 - 읽을 줄이 있을때까지 무한루프를 돈다
                bos.write(buf, 0, length); //  buf 만큼 쓴다				
            }
            bos.flush();

            fis.close();
            bis.close();
            fos.close();
            bos.close();
            move_success = true;
        } catch (Exception ie) {
            move_success = false;
            ie.printStackTrace();
        }
        return move_success;
    }

    /**
     * 파일을 p_thisPath 에서 p_thatPath 로 이동한다.
     * 
     * @param p_thatPath 이동할 경로
     * @param p_thisPath 이동하기전 경로
     * @param p_oldFileName 이동하기전 파일명
     * @param p_newFileName 이동한 후 새로운 파일명
     * @return boolean 파일이동 성공여부
     */
    public boolean move(String p_thatPath, String p_thisPath, String p_oldFileName, String p_newFileName) {
        boolean move_success = false;
        // boolean delete_success = false;
        int length = 0;

        String system_slash = File.separator; //   해당 시스템의 분리자를 얻는다

        try {
            File tempFile = new File(p_thisPath + system_slash + p_oldFileName); //   임시폴더에서 이동할 파일명의 File 객체 생성한다

            FileInputStream fis = new FileInputStream(tempFile);
            //  tempFile에서 내용을 읽기 준비중		
            BufferedInputStream bis = new BufferedInputStream(fis);
            //  읽은 데이터를 bufferedInputStream 에 임시저장

            byte[] buf = new byte[1024];

            File realFile = new File(p_thatPath + system_slash + p_newFileName); //   임시폴더에서 이동할 파일명의 File 객체 생성한다

            FileOutputStream fos = new FileOutputStream(realFile);
            //  realFile에 내용을 쓰기 준비중
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            while ((length = bis.read(buf)) > 0) {
                //  한줄씩 읽기 시작 - 읽을 줄이 있을때까지 무한루프를 돈다
                bos.write(buf, 0, length); //  buf 만큼 쓴다				
            }
            bos.flush();

            fis.close();
            bis.close();
            fos.close();
            bos.close();
            move_success = true;
            if (move_success) {
                // boolean b = tempFile.delete(); //  파일이동이 제대로 되었다면 해당 임시파일 삭제한다
            }
        } catch (Exception ie) {
            System.out.println(ie);
            move_success = false;
            ie.printStackTrace();
        }
        return move_success;
    }
}