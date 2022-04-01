/*
 * @(#)SSOServlet.java
 *
 * Copyright(c) 2007, Jin-pil Chung
 * All rights reserved.
 */

package controller.homepage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.LoginBean;
import com.credu.homepage.SSOBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

/**
 * Servlet implementation class for Servlet: ScormContentServlet
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.SSOServlet")
public class SSOServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        response.setContentType("text/html;charset=euc-kr");
        request.setCharacterEncoding("euc-kr");

        PrintWriter out = response.getWriter();
        RequestBox box = RequestManager.getBox(request);

        String v_process = box.getStringDefault("p_process", "ssoStudy");

        try {
            if (ErrorManager.isErrorMessageView()) {
                box.put("error.out", out);
            }

            if (v_process.equals("ssoStudy")) {
                this.performSSOStudy(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }

    }

    @SuppressWarnings("unchecked")
    private void performSSOStudy(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        SSOBean sb = new SSOBean();
        LoginBean lb = new LoginBean();
        AlertManager alertMng = new AlertManager();

        int loginResult = 0;

        DataBox studentStatus = null;
        boolean isApprovalStudent = false;

        String studyYN = "";

        try {
            studentStatus = sb.getStudentStatus(box);

            isApprovalStudent = (studentStatus == null) ? false : true;

            if (isApprovalStudent) {
                studyYN = studentStatus.getString("d_study_yn");

                // default 처리
                box.setSession("cmu_grtype", "KGDI");
                box.setSession("tem_grcode", box.getStringDefault("p_grcode", "N000001"));

                // 로그인 처리
                //box.put("p_userid", box.getString("id"));
                // todo
                box.put("p_userid", "kbs_m_10262");
                box.put("p_grcode", "N000085");
                box.setSession("tem_grcode", "N000085");
                box.put("p_userip", box.getString("userip"));
                // box.put("p_userip", request.getRemoteAddr());
                // System.out.println(box.getSession("tem_grcode"));
                
                System.out.println("============================== box : " + box);

                loginResult = lb.loginForSSO(box);

                if (loginResult != 1) {
                    alertMng.selfClose(out, "사용자 정보가 존재하지 않습니다.\\n관리자에게 문의하시기 바랍니다.");
                    return;
                } else {

                    // 수강과정 Parameter Select
                    Map param = sb.selectEduStartParameter(box);

                    StringBuffer eduStartUrl = new StringBuffer();

                    eduStartUrl.append("/servlet/controller.contents.EduStart?");
                    eduStartUrl.append("p_subj=");
                    eduStartUrl.append(param.get("p_subj"));
                    eduStartUrl.append("&p_year=");
                    eduStartUrl.append(param.get("p_year"));
                    eduStartUrl.append("&p_subjseq=");
                    eduStartUrl.append(param.get("p_subjseq"));
                    eduStartUrl.append("&contenttype=");
                    eduStartUrl.append(param.get("p_contenttype"));
                    eduStartUrl.append("&p_process=main");

                    if (studyYN.equals("N")) {
                        eduStartUrl.append("&p_review=Y");
                    }

                    // 학습창 Forward
                    forwardUrl(request, response, eduStartUrl.toString());
                }
            } else {
                alertMng.selfClose(out, "등록되지 않은 사용자이거나, 수강하지 않은 과정입니다.\\n관리자에게 문의하시기 바랍니다.");
                return;
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSSOStudy()\r\n" + ex.getMessage());
        }
    }

    private void forwardUrl(HttpServletRequest request, HttpServletResponse response, String forwardUrl) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(forwardUrl);
        rd.forward(request, response);
    }
}