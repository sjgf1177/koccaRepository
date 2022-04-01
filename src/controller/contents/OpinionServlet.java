//**********************************************************
//  1. ��      ��: �ǰߴޱ� �����ϴ� ����(�����)
//  2. ���α׷��� : OpinionServlet.java
//  3. ��      ��: �ǰߴޱ� ���� ���α׷�(�����)
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 13
//  7. ��     ��1:
//**********************************************************
package controller.contents;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.contents.OpinionBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.contents.OpinionServlet")
public class OpinionServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

//            System.out.println("v_process : " + v_process);
			// �α� check ��ƾ VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("selectList")) {                     // �ǰ� ����Ʈ
				this.performSelectList(request, response, box, out);                
            } else if(v_process.equals("insert")) {                  // �ǰ� �ۼ�
                this.performInsert(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    �ǰߴޱ� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

			OpinionBean bean = new OpinionBean();

            //�Ϲݰ��� ����Ʈ
            ArrayList list = bean.selectListOpinion(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/jsp/user/contents/zu_Opinion_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /jsp/user/contents/zu_Opinion_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
    �ǰߴޱ� �ۼ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            OpinionBean bean = new OpinionBean();
            int isOk = bean.insertOpinion(box);

            String v_msg = "";
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "��� �Ǿ����ϴ�.";
            } else {
                v_msg = "��Ͽ� �����߽��ϴ�.";
            }
            alert.historyBack(out, v_msg);

            Log.info.println(this, box, v_msg + " on ContactServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

}

