package com.credu.library;

import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * <p>
 * 제목: 자료실 권한관련 라이브러리
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
public class BulletinManager {

    /**
     * 해당 게시판의 읽기/추가/수정/삭제/답변/파일업로드여부/답변기능여부/커뮤니티여부 의 권한값을 RequestBox 로 리턴한다.
     * 
     * @param name Servlet Name
     * @param box RequestBox class
     * @param out PrintWriter class
     * @return RequestBox box 를 반환한다.
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
     * 로그인한 사용자가 이 게시판에서 읽기/추가/수정/삭제/답변/파일업로드여부/답변기능여부/커뮤니티여부, 본인작성글 수정과 같은
     * 권한여부를 리턴한다.
     * 
     * @param name Servlet Name
     * @param box RequestBox class
     * @param authority 해당 게시판의 읽기/추가/수정.. 등 에서 사용할수 있는 권한코드들
     * @return boolean isAuth 권한이 있는지 여부를 반환한다.
     */
    public synchronized static boolean isAuthority(RequestBox box, String authority) throws Exception {
        boolean isAuth = false;

        try {
            String s_userid = box.getSession("userid"); //      로그인한 사람
            String gadmin = box.getSession("gadmin"); //      권한코드

            String writer = box.getString("p_userid"); //      해당 게시글 작성자

            if (authority != null && gadmin != null) {
                if (gadmin.equals("")) {
                    gadmin = "ZZ"; //      최하위권한인 학생으로 만든다
                }

                StringTokenizer st = new StringTokenizer(authority, ",");

                int auth = 0;
                while (st.hasMoreTokens()) {
                    String str = st.nextToken();
                    gadmin = StringManager.substring(gadmin, 0, 1);
                    str = StringManager.substring(str, 0, 1);
                    auth = gadmin.compareTo(str); //	로그인한사람의 권한과 게시판 권한 비교,   
                    if (auth == 0)
                        break;
                }

                if (auth == 0) {
                    isAuth = true; //      같은 권한인 경우
                } else if (s_userid.equals(writer)) {
                    isAuth = true; //      본인인 경우(로그인한 사람 = 게시글 작성자)
                } else if (box.getBoolean("p_isCommunity") && box.getString("p_isleader").equals("Y")) {
                    isAuth = true; //      커뮤니티에서 시삽인 경우
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
