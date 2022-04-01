//*********************************************************
//1. ��      ��: USER SERVLET
//2. ���α׷���: KRecommendServlet.java
//3. ��      ��: ������õ���� servlet
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 1.0
//6. ��      ��: lyh
//**********************************************************
package controller.study;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.KRecommandBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.study.KRecommendServlet")
public class KRecommendServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 898329821802527780L;

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        // MultipartRequest multi = null;
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

            // �α� check ��ƾ VER 0.2
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            if (v_process.equals("RecommandList")) { // ������õ���� ����Ʈ ���
                this.performInerestList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ������õ���� ��ȸ
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInerestList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            KRecommandBean bean = new KRecommandBean();
            String v_url = "/learn/user/kocca/study/ku_RecommandList_L.jsp";

            //�������� ��ȸ
            ArrayList<DataBox> list = bean.selectRecommandList(box);
            request.setAttribute("UserInfoList", list);

            //�˻��� ���� select��Ͽ� �� ����
            ArrayList<ArrayList<DataBox>> selectboxList = bean.selectRecommandSelectbox(box);
            request.setAttribute("RecommandSelectbox", selectboxList);

            //��õ���� ��ȸ
            ArrayList<DataBox> recommandSubjList = bean.selectRecommandSubj(box);
            request.setAttribute("RecommandSubj", recommandSubjList);

            //�˻���õ����
            ArrayList<DataBox> recommandSearchList = bean.selectRecommandSearch(box);
            request.setAttribute("RecommandSearch", recommandSearchList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ProposeCancel()\r\n" + ex.getMessage());
        }
    }

}