package com.credu.library;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * ����: request/multipart �� session ���� ���̺귯��
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
public class RequestManager {
    /**
     * ���ھ��� ������
     */
    public RequestManager() {
    }

    /**
     *request ��ü�� �޾� ���ǽð� ����, ��Ƽ��Ʈ ��ó��, box hashtable �� session , parameters ��
     * ��� ��ȯ�Ѵ�.
     * 
     * @param request
     *            HttpServletRequest �� request ��ü�� ���ڷ� ����
     * @return RequestBox request ��ü���� ���� �Ķ���� name �� value, session ��ü�� ����
     *         hashtable ��ü�� ��ȯ��
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

            // ÷�������� �����ϴ� ��� (���� �ٲ� ���ε� ��Ŀ����� �Ʒ� �κ��� �������� �ʽ��ϴ�. 10.01.01 ~ )
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
                    // ����Ʈ ���� ��츸 �ش��

                    // '����' ���丮
                    File yearDir = new File(v_updir + File.separator + v_year);

                    if (!yearDir.isDirectory()) { // ���� ���丮 ������ ���� ����
                        yearDir.mkdir();
                    }

                    // '����' ���丮
                    File subjDir = new File(yearDir, v_subj);

                    if (!subjDir.isDirectory()) { // ���� ���丮 ������ ���� ����
                        subjDir.mkdir();
                    }

                    // ����Ʈ ������ ����Ǵ� ���
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

                multi.readRequest(); // ���� �� request�� ���ε�ȴ�
                box = multi.getBox(request);

                // box.put("p_fullupdir", v_updir+ File.separator);
                box.put("p_updir", v_relativePath);

            } else { // ÷�������� ���� ���
                box = new RequestBox("requestbox");

                // Request�� �Ѿ�� �������� DataBox�� ����Ѵ�.
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
     * ContentType ���� Multipart form �ΰ� ���θ� Ȯ���Ѵ�
     * 
     * @param request
     *            HttpServletRequest �� request ��ü�� ���ڷ� ����
     * @return Multipart form �̸� true �� ��ȯ��
     */
    public static boolean isMultipartForm(HttpServletRequest request) {
        String v_contentType = StringManager.chkNull(request.getContentType());

        return v_contentType.indexOf("multipart/form-data") >= 0; // Multipart ��
        // �Ѿ�Դ��� ����
    }

    /**
     * ������ξּ� �������� �����Ͽ� ��ȯ��
     * 
     * @param servletPath
     *            ���� ��θ� ���ڷ� ����
     * @return �������� ��ȯ��
     */
    public static String getServletName(String servletPath) {
        return servletPath.substring(servletPath.lastIndexOf(".") + 1, servletPath.lastIndexOf("Servlet"));
    }

    /**
     * ��Ű�� ���� box ��ü�� ��´�.
     * 
     * @param request
     *            HttpServletRequest �� request ��ü�� ���ڷ� ����
     * @return RequestBox request ��ü���� ��Ű�� name �� value �� ���� hashtable ��ü�� ��ȯ��
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
