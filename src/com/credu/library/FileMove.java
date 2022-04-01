package com.credu.library;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <p>
 * ����: File Move ���̺귯��
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
 *@date 2003. 12
 *@version 1.0
 */
public class FileMove {

    public FileMove() {
    }

    /**
     * ������ p_thisPath ���� p_thatPath �� �̵��Ѵ�.
     * 
     * @param p_thatPath �̵��� ���
     * @param p_thisPath �̵��ϱ��� ���
     * @param p_fileName ���ϸ�
     * @return boolean �����̵� ��������
     */
    public boolean move(String p_thatPath, String p_thisPath, String p_fileName) {
        boolean move_success = false;
        // boolean delete_success = false;
        int length = 0;

        String system_slash = File.separator; //   �ش� �ý����� �и��ڸ� ��´�

        try {
            File tempFile = new File(p_thisPath + system_slash + p_fileName); //   �ӽ��������� �̵��� ���ϸ��� File ��ü �����Ѵ�

            FileInputStream fis = new FileInputStream(tempFile);
            //  tempFile���� ������ �б� �غ���		
            BufferedInputStream bis = new BufferedInputStream(fis);
            //  ���� �����͸� bufferedInputStream �� �ӽ�����

            byte[] buf = new byte[1024];

            File realFile = new File(p_thatPath + system_slash + p_fileName); //   �ӽ��������� �̵��� ���ϸ��� File ��ü �����Ѵ�

            FileOutputStream fos = new FileOutputStream(realFile);
            //  realFile�� ������ ���� �غ���
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            while ((length = bis.read(buf)) > 0) {
                //  ���پ� �б� ���� - ���� ���� ���������� ���ѷ����� ����
                bos.write(buf, 0, length); //  buf ��ŭ ����				
            }
            bos.flush();

            fis.close();
            bis.close();
            fos.close();
            bos.close();
            move_success = true;
            if (move_success) {
                // boolean b = tempFile.delete();			//  �����̵��� ����� �Ǿ��ٸ� �ش� �ӽ����� �����Ѵ�
                // System.out.println(" move delete is ok ======? " + b+ " /// p_thisPath + system_slash + p_fileName==>" + p_thisPath + system_slash + p_fileName);
            }
        } catch (Exception ie) {
            move_success = false;
            ie.printStackTrace();
        }
        return move_success;
    }

    /**
     * ������ p_thisPath ���� p_thatPath �� �̵��Ѵ�.
     * 
     * @param p_thatPath �̵��� ���
     * @param p_thisPath �̵��ϱ��� ���
     * @param p_fileName ���ϸ�
     * @return boolean �����̵� ��������
     */
    public boolean copy(String p_thatPath, String p_thisPath, String p_fileName) {
        boolean move_success = false;
        // boolean delete_success = false;
        int length = 0;

        String system_slash = File.separator; //   �ش� �ý����� �и��ڸ� ��´�

        try {
            File tempFile = new File(p_thisPath + system_slash + p_fileName); //   �ӽ��������� �̵��� ���ϸ��� File ��ü �����Ѵ�

            FileInputStream fis = new FileInputStream(tempFile);
            //  tempFile���� ������ �б� �غ���		
            BufferedInputStream bis = new BufferedInputStream(fis);
            //  ���� �����͸� bufferedInputStream �� �ӽ�����

            byte[] buf = new byte[1024];

            File realFile = new File(p_thatPath + system_slash + p_fileName); //   �ӽ��������� �̵��� ���ϸ��� File ��ü �����Ѵ�

            FileOutputStream fos = new FileOutputStream(realFile);
            //  realFile�� ������ ���� �غ���
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            while ((length = bis.read(buf)) > 0) {
                //  ���پ� �б� ���� - ���� ���� ���������� ���ѷ����� ����
                bos.write(buf, 0, length); //  buf ��ŭ ����				
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
     * ������ p_thisPath ���� p_thatPath �� �̵��Ѵ�.
     * 
     * @param p_thatPath �̵��� ���
     * @param p_thisPath �̵��ϱ��� ���
     * @param p_oldFileName �̵��ϱ��� ���ϸ�
     * @param p_newFileName �̵��� �� ���ο� ���ϸ�
     * @return boolean �����̵� ��������
     */
    public boolean move(String p_thatPath, String p_thisPath, String p_oldFileName, String p_newFileName) {
        boolean move_success = false;
        // boolean delete_success = false;
        int length = 0;

        String system_slash = File.separator; //   �ش� �ý����� �и��ڸ� ��´�

        try {
            File tempFile = new File(p_thisPath + system_slash + p_oldFileName); //   �ӽ��������� �̵��� ���ϸ��� File ��ü �����Ѵ�

            FileInputStream fis = new FileInputStream(tempFile);
            //  tempFile���� ������ �б� �غ���		
            BufferedInputStream bis = new BufferedInputStream(fis);
            //  ���� �����͸� bufferedInputStream �� �ӽ�����

            byte[] buf = new byte[1024];

            File realFile = new File(p_thatPath + system_slash + p_newFileName); //   �ӽ��������� �̵��� ���ϸ��� File ��ü �����Ѵ�

            FileOutputStream fos = new FileOutputStream(realFile);
            //  realFile�� ������ ���� �غ���
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            while ((length = bis.read(buf)) > 0) {
                //  ���پ� �б� ���� - ���� ���� ���������� ���ѷ����� ����
                bos.write(buf, 0, length); //  buf ��ŭ ����				
            }
            bos.flush();

            fis.close();
            bis.close();
            fos.close();
            bos.close();
            move_success = true;
            if (move_success) {
                // boolean b = tempFile.delete(); //  �����̵��� ����� �Ǿ��ٸ� �ش� �ӽ����� �����Ѵ�
            }
        } catch (Exception ie) {
            System.out.println(ie);
            move_success = false;
            ie.printStackTrace();
        }
        return move_success;
    }
}