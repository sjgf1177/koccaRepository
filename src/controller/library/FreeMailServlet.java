//**********************************************************
//  1. ��      ��: FreeMail �����ϴ� ������
//  2. ���α׷��� : FreeMailServlet.java
//  3. ��      ��: FreeMail �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 12. 23
//  7. ��      ��:
//**********************************************************

package controller.library;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.FreeMailBean;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.library.FreeMailServlet")
public class FreeMailServlet extends javax.servlet.http.HttpServlet {

    /**
     * DoGet Pass get requests through to PerformTask
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("insertPage")) { // freeMail �ۼ� ������ �̵��Ҷ�
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) { // freeMail �ۼ�
                this.performInsert(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * FREE MAIL ����������� �̵��Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      ���������� box ��ü�� �Ѱ��ش�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/library/freeMailForm.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/libary/freeMailForm.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * FREE MAIL ����Ҷ�
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            FreeMailBean bean = new FreeMailBean();
            int isOk = bean.sendFreeMail(box);

            AlertManager alert = new AlertManager();
            String v_msg = "";

            if (isOk > 0) {
                if (box.getString("p_isMailing").equals("1")) {
                    v_msg = isOk + "���� ������ �߼۵Ǿ����ϴ�.";
                } else if (box.getString("p_isMailing").equals("2")) {
                    v_msg = isOk + "���� SMS�� �߼۵Ǿ����ϴ�.";
                }
            } else {
                if (box.getString("p_isMailing").equals("1")) {
                    v_msg = "���Ϲ߼ۿ� �����߽��ϴ�.";
                } else if (box.getString("p_isMailing").equals("2")) {
                    v_msg = "SMS �߼ۿ� �����߽��ϴ�.";
                }
            }
            alert.selfClose(out, v_msg);

            Log.info.println(this, box, v_msg + " on FreeMailServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

}