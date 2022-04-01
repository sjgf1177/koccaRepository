//**********************************************************
//1. ��      ��: ��������
//2. ���α׷���: SulmunSubjUserServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: lyh
//**********************************************************

package controller.research;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunCommonUserBean;
import com.credu.research.SulmunRegistUserBean;
import com.credu.research.SulmunSubjPaperBean;
import com.credu.research.SulmunSubjUserBean;
import com.credu.research.SulmunTargetUserBean;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.research.SulmunSubjUserServlet")
public class SulmunSubjUserServlet extends HttpServlet implements Serializable {
    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox  box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");
            
            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // �α� check ��ƾ VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }

            /*
            if(box.getSession("userid").equals("")){
                request.setAttribute("tUrl",request.getRequestURI());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request,response);
                return;
            }
             */

            if (v_process.equals("SulmunUserListPage")) {                      //�������� ����� ������ ����Ʈ
                this.performSulmunUserListPage(request, response, box, out);
            }
            else if (v_process.equals("SulmunUserPaperListPage")) {                 //�������� ����� ��������
                this.performSulmunUserPaperListPage(request, response, box, out);
            }
            else if (v_process.equals("SulmunUserResultInsert")) {                 //�������� ������ �Է°�� ����Ҷ�
                this.performSulmunUserResultInsert(request, response, box, out);
            }
            else if (v_process.equals("SulmunUserPaperResult")) {                 //�������� ���κ� �򰡰�� ����
                this.performSulmunUserPaperResult(request, response, box, out);
            }
            else if (v_process.equals("SulmunNew")) {                 // ���ǰ��ǽ� ����������(2005.07.20)
                this.performSulmunNew(request, response, box, out);
            }
            else if (v_process.equals("SulmunGen")) {                 // ���ǰ��ǽ�  �Ϲ� ����������
                this.performSulmunGen(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    �������� ����� ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSulmunUserListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/study/zu_SulmunSubjPaper_L.jsp";

            SulmunRegistUserBean bean2 = new SulmunRegistUserBean();
            SulmunSubjUserBean bean1     = new SulmunSubjUserBean();

            ArrayList list1 = bean1.selectEducationSubjectList(box);
            request.setAttribute("EducationSubjectList1", list1);

            ArrayList list2 = bean2.selectEducationSubjectList(box);
            request.setAttribute("EducationSubjectList2", list2);

            ArrayList list4 = bean2.selectGraduationSubjectList(box);
            request.setAttribute("GraduationSubjectList2", list4);

            /*         SulmunSubjUserBean bean = new SulmunSubjUserBean();
            ArrayList list1 = bean.SelectUserList(box);
            request.setAttribute("SulmunUserList", list1);

            ArrayList list2 = bean.SelectUserResultList(box);
            request.setAttribute("SulmunUserResultList", list2);
             */
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    ���� ���ǽ� �޴�  ���� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSulmunNew(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            SulmunSubjUserBean     bean1 = new SulmunSubjUserBean();     // ����
            String v_url  = "";
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
                //v_url = "/learn/user/2012/portal/study/gu_SulmunNew_L.jsp";
                v_url = "/learn/user/2013/portal/study/gu_SulmunNew_L.jsp";
            } else {
                v_url = "/learn/user/portal/study/gu_SulmunNew_L.jsp";
            SulmunCommonUserBean   bean3 = new SulmunCommonUserBean();   // �Ϲ�
            SulmunRegistUserBean   bean4 = new SulmunRegistUserBean();   // ���԰��
            SulmunTargetUserBean   bean5 = new SulmunTargetUserBean();   // �����

            ArrayList list_t = bean5.selectSulmunTargetList(box);   // ����ڼ�������Ʈ
            request.setAttribute("SulmunTarget", list_t);

            ArrayList list_cm = bean3.SelectUserList(box);          // �Ϲݼ�������Ʈ
            request.setAttribute("SulmunCommon", list_cm);

            ArrayList list_r = bean4.SelectUserList(box);           // ���԰�μ�������Ʈ
            request.setAttribute("SulmunRegist", list_r);
            }

            ArrayList list1 = bean1.selectEducationSubjectList(box);    //��������
            request.setAttribute("EducationSubjectList1", list1);


            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunNew()\r\n" + ex.getMessage());
        }
    }

    /**
    ���� ���ǽ� �޴�  ���� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSulmunGen(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url  = "";
            if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
                //v_url = "/learn/user/2012/portal/study/gu_SulmunGen_L.jsp";
                v_url = "/learn/user/2013/portal/study/gu_SulmunGen_L.jsp";
            } else {
                v_url = "/learn/user/portal/study/gu_SulmunNew_L.jsp";
            }

            SulmunCommonUserBean   bean3 = new SulmunCommonUserBean();   // �Ϲ�

            ArrayList list_cm = bean3.SelectUserList(box);          // �Ϲݼ�������Ʈ
            request.setAttribute("SulmunCommon", list_cm);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunNew()\r\n" + ex.getMessage());
        }
    }

    /**
    �������� ����� ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSulmunUserPaperListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/research/zu_SulmunSubjUserPaper_I.jsp";

            SulmunSubjPaperBean bean = new SulmunSubjPaperBean();
            ArrayList list1 = bean.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);

            box.put("p_subjsel",box.getString("p_subj"));

            box.put("p_upperclass","ALL");

            DataBox dbox1 = bean.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox1);  //2005.08.22 by������ jsp���� �� �� �޾ƿ�.

            box.remove("p_subjsel");
            box.remove("p_subjsel");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserPaperListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    �������� ������ �Է°�� ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSulmunUserResultInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.research.SulmunSubjUserServlet";

            SulmunSubjUserBean bean = new SulmunSubjUserBean();
            
            int isOk = bean.InsertSulmunUserResult(box);

            String v_msg = "";

            box.put("p_end","0");
            box.put("p_process", "SulmunNew");
            box.put("p_tab", "1");

            AlertManager alert = new AlertManager();
            if(isOk == 2) {
                v_msg = "������ ������ �ּż� �����մϴ�.";
                box.put("openercount", 2);
                
                //����н��� ��� ����â ���ε�
                if(box.getString("p_isalways").equals("Y")){
                	alert.alertOkMessage(out, v_msg, v_url , box, true, true, false, true);
                }else{
                	alert.alertOkMessage(out, v_msg, v_url , box, true, true);
                }
            }else if(isOk == 1){
                v_msg = "���� �Ⱓ �����Դϴ�.";
                alert.alertFailMessage(out, v_msg);
            }else if (isOk == 3){
                v_msg = "���� �Ⱓ�� �Ϸ�Ǿ����ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }else{
                v_msg = "�̹� �ش� ������ �����ϼ̽��ϴ�.";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserResultInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    �������� ����� ������ ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSulmunUserPaperResult(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/user/game/research/gu_SulmunSubjUserResultList.jsp";

            SulmunSubjUserBean bean = new SulmunSubjUserBean();

            DataBox dbox = bean.SelectUserPaperResult(box);
            request.setAttribute("UserPaperResult", dbox);

            //DataBox dbox1 = bean.selectSulmunUser(box);
            //request.setAttribute("SulmunUserData", dbox1);

            SulmunSubjPaperBean bean1 = new SulmunSubjPaperBean();
            ArrayList list1 = bean1.selectPaperQuestionExampleList(box);
            request.setAttribute("PaperQuestionExampleList", list1);

            box.put("p_subjsel",box.getString("p_subj"));
            box.put("p_upperclass","ALL");
            DataBox dbox2 = bean1.getPaperData(box);
            request.setAttribute("SulmunPaperData", dbox2);
            box.remove("p_subjsel");
            box.remove("p_subjsel");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSulmunUserPaperResult()\r\n" + ex.getMessage());
        }
    }
}