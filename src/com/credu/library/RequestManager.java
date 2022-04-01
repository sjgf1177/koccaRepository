package com.credu.library;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 제목: request/multipart 와 session 관련 라이브러리
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
public class RequestManager {
    /**
     * 인자없는 생성자
     */
    public RequestManager() {
    }

    /**
     *request 객체를 받아 세션시간 설정, 멀티파트 폼처리, box hashtable 에 session , parameters 를
     * 담아 반환한다.
     * 
     * @param request
     *            HttpServletRequest 의 request 객체를 인자로 받음
     * @return RequestBox request 객체에서 받은 파라미터 name 과 value, session 객체를 담은
     *         hashtable 객체를 반환함
     */
    @SuppressWarnings("unchecked")
    public static RequestBox getBox(HttpServletRequest request) {
        ConfigSet conf = null;
        HttpSession session = null;
        RequestBox box = null;
        MultipartRequest multi = null;
        // String filetype = "";
        // int typenum = 0;
        String v_userid = "";
        String uploadName = "";

        try {
            conf = new ConfigSet();

            session = request.getSession(true);

            // 첨부파일이 존재하는 경우 (새로 바뀐 업로드 방식에서는 아래 부분을 참고하지 않습니다. 10.01.01 ~ )
            if (isMultipartForm(request)) {
                String v_servletName = getServletName(request.getRequestURI());

                uploadName = (String) request.getAttribute("uploadName");
                
                uploadName = (uploadName == null || uploadName.equals("")) ? "default" : uploadName;

                // String v_currentDate = FormatDate.getDate("yyyyMMddHHmmss");

                if (session.getAttribute("userid") != null) {
                    v_userid = session.getAttribute("userid").toString();
                }

                String v_dirKey = conf.getDir(conf.getProperty("dir.upload"), uploadName);

                String v_relativePath = conf.getProperty("dir.upload." + v_dirKey);

                String v_updir = conf.getProperty("dir.home") + v_relativePath;

                String v_subj = StringManager.chkNull(request.getParameter("g_subj"));
                String v_year = StringManager.chkNull(request.getParameter("g_year"));

                if (!v_subj.equals("") && !v_year.equals("")) {
                    // 리포트 제출 경우만 해당됨

                    // '연도' 디렉토리
                    File yearDir = new File(v_updir + File.separator + v_year);

                    if (!yearDir.isDirectory()) { // 연도 디렉토리 없으면 새로 생성
                        yearDir.mkdir();
                    }

                    // '과정' 디렉토리
                    File subjDir = new File(yearDir, v_subj);

                    if (!subjDir.isDirectory()) { // 과정 디렉토리 없으면 새로 생성
                        subjDir.mkdir();
                    }

                    // 리포트 파일이 저장되는 경로
                    v_updir = v_updir + v_year + File.separator + v_subj;
                    v_relativePath = v_relativePath + v_year + File.separator + v_subj + File.separator;
                } else {

                    String currYear = FormatDate.getDate("yyyy");

                    File targetDir = new File(v_updir + currYear);
                    if (!targetDir.isDirectory()) {
                        targetDir.mkdir();
                    }

                    v_updir = v_updir + File.separator + currYear;
                    v_relativePath = v_relativePath + currYear + File.separator;

                }

                multi = new MultipartRequest(request, v_updir, v_servletName, v_userid);

                multi.readRequest(); // 파일 및 request가 업로드된다
                box = multi.getBox(request);

                // box.put("p_fullupdir", v_updir+ File.separator);
                box.put("p_updir", v_relativePath);

            } else { // 첨부파일이 없는 경우
                box = new RequestBox("requestbox");

                // Request로 넘어온 정보들을 DataBox에 등록한다.
                Enumeration e1 = request.getParameterNames();
                while (e1.hasMoreElements()) {
                    String key = (String) e1.nextElement();
                    box.put(key, request.getParameterValues(key));
                }
            }

            String userIp = request.getHeader("X-Forwarded-For");
            if (userIp == null || userIp.length() == 0 || userIp.equals("") || userIp.equals("unknown")) {
                userIp = request.getRemoteAddr();
            }

            box.put("session", session);
            box.put("userip", userIp);
            box.put("hostip", request.getRemoteHost());
            box.put("request.serverName", request.getServerName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // if(box.getString("s_grcode").equals("")) box.put("s_grcode",
        // "N000001");
        return box;
    }

    /**
     * ContentType 에서 Multipart form 인가 여부를 확인한다
     * 
     * @param request
     *            HttpServletRequest 의 request 객체를 인자로 받음
     * @return Multipart form 이면 true 를 반환함
     */
    public static boolean isMultipartForm(HttpServletRequest request) {
        String v_contentType = StringManager.chkNull(request.getContentType());

        return v_contentType.indexOf("multipart/form-data") >= 0; // Multipart 로
        // 넘어왔는지 여부
    }

    /**
     * 서블릿경로애서 서블릿명을 추출하여 반환함
     * 
     * @param servletPath
     *            서블릿 경로를 인자로 받음
     * @return 서블릿명을 받환함
     */
    public static String getServletName(String servletPath) {
        return servletPath.substring(servletPath.lastIndexOf(".") + 1, servletPath.lastIndexOf("Servlet"));
    }

    /**
     * 쿠키를 담은 box 객체를 얻는다.
     * 
     * @param request
     *            HttpServletRequest 의 request 객체를 인자로 받음
     * @return RequestBox request 객체에서 쿠키의 name 과 value 를 담은 hashtable 객체를 반환함
     */
    @SuppressWarnings("unchecked")
    public static RequestBox getBoxFromCookie(HttpServletRequest request) {
        RequestBox cookiebox = new RequestBox("cookiebox");
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return cookiebox;

        for (int i = 0; cookies != null && i < cookies.length; i++) {
            String key = cookies[i].getName();
            String value = cookies[i].getValue();
            if (value == null)
                value = "";
            String[] values = new String[1];
            values[0] = value;
            cookiebox.put(key, values);
        }
        return cookiebox;
    }
}
