package com.credu.library;

import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * <p>
 * ����: �ڷ�� ���Ѱ��� ���̺귯��
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
public class BulletinManager {

    /**
     * �ش� �Խ����� �б�/�߰�/����/����/�亯/���Ͼ��ε忩��/�亯��ɿ���/Ŀ�´�Ƽ���� �� ���Ѱ��� RequestBox �� �����Ѵ�.
     * 
     * @param name Servlet Name
     * @param box RequestBox class
     * @param out PrintWriter class
     * @return RequestBox box �� ��ȯ�Ѵ�.
     */
    @SuppressWarnings("unchecked")
    public synchronized static RequestBox getState(String name, RequestBox box, PrintWriter out) throws Exception {
        try {
            ConfigSet conf = new ConfigSet();
            String canRead = conf.getProperty(name + ".can.read");
            String canAppend = conf.getProperty(name + ".can.append");
            String canModify = conf.getProperty(name + ".can.modify");
            String canDelete = conf.getProperty(name + ".can.delete");
            String canReply = conf.getProperty(name + ".can.reply");
            boolean isUpload = new Boolean(conf.getProperty(name + ".is.upload")).booleanValue();
            boolean isReply = new Boolean(conf.getProperty(name + ".is.reply")).booleanValue();
            boolean isCommunity = new Boolean(conf.getProperty(name + ".is.community")).booleanValue();

            box.put("p_canRead", canRead);
            box.put("p_canAppend", canAppend);
            box.put("p_canModify", canModify);
            box.put("p_canDelete", canDelete);
            box.put("p_canReply", canReply);
            box.put("p_isUpload", String.valueOf(isUpload));
            box.put("p_isReply", String.valueOf(isReply));
            box.put("p_isCommunity", String.valueOf(isCommunity));

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            Log.sys.println(ex, "Happen to BulletinManager.getState(\"" + name + "\")");
            throw new Exception("BulletinManager.getState()\r\n" + ex.getMessage());
        }
        return box;
    }

    /**
     * �α����� ����ڰ� �� �Խ��ǿ��� �б�/�߰�/����/����/�亯/���Ͼ��ε忩��/�亯��ɿ���/Ŀ�´�Ƽ����, �����ۼ��� ������ ����
     * ���ѿ��θ� �����Ѵ�.
     * 
     * @param name Servlet Name
     * @param box RequestBox class
     * @param authority �ش� �Խ����� �б�/�߰�/����.. �� ���� ����Ҽ� �ִ� �����ڵ��
     * @return boolean isAuth ������ �ִ��� ���θ� ��ȯ�Ѵ�.
     */
    public synchronized static boolean isAuthority(RequestBox box, String authority) throws Exception {
        boolean isAuth = false;

        try {
            String s_userid = box.getSession("userid"); //      �α����� ���
            String gadmin = box.getSession("gadmin"); //      �����ڵ�

            String writer = box.getString("p_userid"); //      �ش� �Խñ� �ۼ���

            if (authority != null && gadmin != null) {
                if (gadmin.equals("")) {
                    gadmin = "ZZ"; //      ������������ �л����� �����
                }

                StringTokenizer st = new StringTokenizer(authority, ",");

                int auth = 0;
                while (st.hasMoreTokens()) {
                    String str = st.nextToken();
                    gadmin = StringManager.substring(gadmin, 0, 1);
                    str = StringManager.substring(str, 0, 1);
                    auth = gadmin.compareTo(str); //	�α����ѻ���� ���Ѱ� �Խ��� ���� ��,   
                    if (auth == 0)
                        break;
                }

                if (auth == 0) {
                    isAuth = true; //      ���� ������ ���
                } else if (s_userid.equals(writer)) {
                    isAuth = true; //      ������ ���(�α����� ��� = �Խñ� �ۼ���)
                } else if (box.getBoolean("p_isCommunity") && box.getString("p_isleader").equals("Y")) {
                    isAuth = true; //      Ŀ�´�Ƽ���� �û��� ���
                } else {
                    isAuth = false;
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            Log.err.println (ex.getStackTrace().toString());
            throw new Exception("BulletinManager.isAuthority()\r\n" + ex.getMessage());
        }
        return isAuth;
    }
}
