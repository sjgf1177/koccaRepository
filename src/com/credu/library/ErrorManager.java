package com.credu.library;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 제목: Error 관련 라이브러리
 * 설명: 
 * Copyright: Copyright (c) 2004
 * Company: Credu
 * 
 * @author 이정한
 * @date 2003. 12
 * @version 1.0
 */
public class ErrorManager {
    // private static String trace_delim = "\r\n\tat ";
    //private static String msg_delim = ": ";

    /**
     * stackTrace 를 Html 형식으로 정렬한다
     * 
     * @param stackTrace stackTrace String을 인자로 받는다
     * @return result Html 형식으로 정렬된 stackTrace 를 반환한다
     */
    public static String getHtmlLineup(String stackTrace) {
        String result = "";

        if (stackTrace != null) {
            result = StringManager.replace(stackTrace, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            result = StringManager.replace(stackTrace, "\r\n", "<br>");
        }
        return result;
    }

    /**
     * Error 메시지(sql 포함)와 stackTrace 를 웹화면에서 보여준다
     * 
     * @param ex Throwable 를 인자로 받는다
     * @param out PrintWriter 를 인자로 받는다
     * @param str sql 구문을 인자로 받는다
     */
    @SuppressWarnings("null")
    public static void getErrorStackTrace(Throwable ex, RequestBox box, String str) {
        ByteArrayOutputStream baos = null;
        PrintStream ps = null;
        String sql = str;
        PrintWriter out = null;

        try {
            baos = new ByteArrayOutputStream();
            ps = new PrintStream(baos);
            ex.printStackTrace(ps);
            String error_msg = baos.toString();

            if (isErrorMessageView()) {
                out = (PrintWriter) box.getObject("errorout");
                if (out != null) {
                    out.println("<HTML>");
                    out.println("<HEAD><TITLE>Compound JSPs</TITLE></HEAD>");
                    out.println("<BODY BGCOLOR=#C0C0C0>");
                    out.println("<H2>Exception Occurred</H2>");
                    out.println("<FONT SIZE=2>");
                    out.println(sql + "<br><br>");
                    out.println(getHtmlLineup(error_msg));
                    out.println("</FONT>");
                    out.println("</BODY></HTML>");
                }
            } else {
                out.println("<html><head>");
                out.println("<script language = 'javascript'>");
                out.println("alert('잘못된 접근입니다. 관리자에게 문의하시기 바랍니다.')");
                out.println("history.back(-1);");
                out.println("</script>");
                out.println("</head>");

                out.println("<body onload='javascript:document.errform.submit()'>");
                //                out.println("<form name=errform action='/learn/library/include/printSystemErrorMessage.jsp' method=post>");
                out.println("<form name=errform action='/servlet/controller.homepage.MainServlet' method=post>");
                out.println("</body>");

                out.println("</html>");
                Log.err.println("StackTrace : " + error_msg);
            }
        } catch (Exception e) {
            Log.sys.println(box, "ErrorManager.getErrorStackTrace(Throwable ex, RequestBox box, String str) is critical error\r\n" + e.getMessage());
            //   e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e1) {
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e1) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e1) {
                }
            }
        }
    }

    /**
     * Error 메시지와 stackTrace 를 웹화면에서 보여준다
     * 
     * @param ex Throwable 를 인자로 받는다
     * @param out PrintWriter 를 인자로 받는다
     */
    public static void getErrorStackTrace(Throwable ex, PrintWriter out) {
        ByteArrayOutputStream baos = null;
        PrintStream ps = null;

        try {
            baos = new ByteArrayOutputStream();
            ps = new PrintStream(baos);
            ex.printStackTrace(ps);
            String error_msg = baos.toString();

            if (out != null) {
                if (isErrorMessageView()) {
                    out.println("<HTML>");
                    out.println("<HEAD><TITLE>Compound JSPs</TITLE></HEAD>");
                    out.println("<BODY BGCOLOR=#C0C0C0>");
                    out.println("<H2>Exception Occurred</H2>");
                    out.println("<FONT SIZE=2>");
                    out.println(getHtmlLineup(error_msg));
                    out.println("</FONT>");
                    out.println("</BODY></HTML>");
                } else {
                    out.println("<html><head>");
                    out.println("<script language = 'javascript'>");
                    out.println("alert('잘못된 접근입니다. 관리자에게 문의하시기 바랍니다.')");
                    out.println("history.back(-1);");
                    out.println("</script>");
                    out.println("</head>");
                    out.println("<body onload='javascript:document.errform.submit()'>");
                    //                    out.println("<form name=errform action='/learn/library/include/printSystemErrorMessage.jsp' method=post>");
                    out.println("<form name=errform action='/servlet/controller.homepage.MainServlet' method=post>");
                    out.println("</body>");

                    out.println("</html>");
                    Log.err.println("StackTrace : " + error_msg);
                }
            }
        } catch (Exception e) {
            Log.sys.println("ErrorManager.getErrorStackTrace(Throwable ex, PrintWriter out) is critical error\r\n" + e.getMessage());
            //   e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e1) {
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e1) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e1) {
                }
            }
        }
    }

    /**
     * Error 메시지와 stackTrace 를 웹화면 or 콘솔에서 보여준다
     * 
     * @param ex Throwable 를 인자로 받는다
     * @param isHtml 웹화면에서 보여줄지 여부를 설정한다.
     * @return error_msg 에러메시지를 리턴한다.
     */
    public static String getErrorStackTrace(Throwable ex, boolean isHtml) {
        ByteArrayOutputStream baos = null;
        PrintStream ps = null;
        String error_msg = "";

        try {
            baos = new ByteArrayOutputStream();
            ps = new PrintStream(baos);
            ex.printStackTrace(ps);
            error_msg = baos.toString();

            Log.err.println("StackTrace : " + error_msg);

            if (isHtml)
                error_msg = getHtmlLineup(error_msg);
        } catch (Exception e) {
            Log.sys.println("ErrorManager.getErrorStackTrace(Throwable ex, boolean isHtml) is critical error\r\n" + e.getMessage());
            //   e.printStackTrace();
        }
        return error_msg;
    }

    /**
     * Error 메시지와 stackTrace 를 보여준다
     * 
     * @param ex Throwable 를 인자로 받는다
     */
    public static void getErrorStackTrace(Throwable ex) {
        ByteArrayOutputStream baos = null;
        PrintStream ps = null;
        String error_msg = "";

        try {
            baos = new ByteArrayOutputStream();
            ps = new PrintStream(baos);
            ex.printStackTrace(ps);
            error_msg = baos.toString();
            Log.err.println("StackTrace : " + error_msg);
        } catch (Exception e) {
            Log.sys.println("ErrorManager.getErrorStackTrace(Throwable ex) is critical error\r\n" + e.getMessage());
            //   e.printStackTrace();
        }
    }

    /**
     * RequestBox 를 인자로 받아 box 안에 담긴 key, value 를 console 에 찍는다.
     * 
     * @param box RequestBox
     */
    @SuppressWarnings("unchecked")
    public static void systemOutPrintln(RequestBox box) throws Exception {
        try {
            if (isErrorMessageView()) {
                Enumeration e = box.keys();

                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();

                    String value = box.get(key);

                    System.out.println(key + " : " + value);
                }
            }
        } catch (Exception e) {
            Log.sys.println("ErrorManager.systemOutPrintln(RequestBox box) is critical error\r\n" + e.getMessage());
            //   e.printStackTrace();
        }
    }

    public static boolean isErrorMessageView() throws Exception {
        boolean result = false;
        try {
            ConfigSet conf = new ConfigSet();
            String isMessage = conf.getProperty("error.message.view");
            if (isMessage.equals("true"))
                result = true;
            else
                result = false;
        } catch (Exception ex) {
            throw new Exception("ErrorManager.isErrorMessageView()\r\n" + ex.getMessage());
        }
        return result;
    }

    public static void isMobileReturnUrl(PrintWriter out, HttpServletRequest request, RequestBox box, String v_power) throws Exception {
        String v_url = request.getRequestURI();
        String param = request.getQueryString();

        AlertManager alert = new AlertManager();

        //post방식으로 들어온 경우 
        if (param == "null" || param == null) {
            v_url = "/servlet/controller.mobile.main.MainServlet";
            param = "p_process=mainPage";
        }

        String url = "/servlet/controller.mobile.member.LoginServlet?p_process=loginpage&p_frmURL=";
        String v_msg = "";

        String s_userid = box.getSession("userid");

        if (s_userid == null || "".equals(s_userid)) {
            v_msg = "로그인이 필요합니다.";

            v_url = new Base64().encode(v_url + "?" + param);

            url += v_url;
            alert.alertOkMessage(out, v_msg, url, box);

            return;
        }

    }
}