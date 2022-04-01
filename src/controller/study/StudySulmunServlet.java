//**********************************************************
//  1. ��      ��:  ���� ���� ����
//  2. ���α׷��� : StudySulmunServlet.java
//  3. ��      ��: ���� ���� ����
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 12. 20
//  7. ��     ��1:
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

import com.credu.course.SubjGongAdminBean;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunRegistUserBean;
import com.credu.research.SulmunSubjUserBean;
import com.credu.system.StudyCountBean;


@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.study.StudySulmunServlet")
public class StudySulmunServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
        RequestBox  box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "StudySulmunListPage");

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if(box.getSession("userid").equals("")){
                request.setAttribute("tUrl",request.getRequestURI());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request,response);
                return;
            }

            if (v_process.equals("StudySulmunListPage")) {                      //���� ����, ������ ��  ����Ʈ
                this.performStudySulmunListPage(request, response, box, out);
            } 

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    ���� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performStudySulmunListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
			String v_subj = box.getString("p_subj"); //�� ���� �ڵ� 
            String v_return_url = "/learn/user/study/zu_StudySulmun_L.jsp";
      
            // ������ �޴� ���� ���� �߰�
            box.put("p_menu","95");
            StudyCountBean scBean = new StudyCountBean();
            scBean.writeLog(box);

            SulmunSubjUserBean bean = new SulmunSubjUserBean();
			box.put("s_subj", v_subj); 
            ArrayList list = bean.SelectUserList(box);
            request.setAttribute("SulmunSubjUserList", list);

			SulmunRegistUserBean bean1 = new SulmunRegistUserBean();
            ArrayList list1 = bean1.SelectUserList(box);
            request.setAttribute("SulmunContentsUserList", list1);

            /* ==========   �������� ���ÿ��� ==========*/     
            box.put("p_subj","ALL"); 	// ���� �ڵ尡 �ƴ� ���� ���� �з� (���� �ؾ� ����...)       
            int suldata = bean.getUserData(box);    
            box.put("p_suldata",String.valueOf(suldata));         
            /* ==========   �������� ���ÿ��� ==========*/   

            /* ==========   �������� ���ÿ��� ==========*/     
            box.put("p_subj","REGIST");       // ���� �ڵ尡 �ƴ� ���� ���� �з� (���� �ؾ� ����...)       
            int contentsdata = bean1.getUserData(box);      
            box.put("p_contentsdata",String.valueOf(contentsdata));   
            box.put("p_subj",v_subj);        // �� ���� �ڵ带 �ٽ� ������.         
            /* ==========   �������� ���ÿ��� ==========*/ 

            if(!box.getSession("s_subjseq").equals("0000")){    // ��Ÿ�׽�Ʈ�ϰ��
                /* ==========   ����������, �ڱ������� ���� ==========*/
                SubjGongAdminBean sbean = new SubjGongAdminBean();

                String promotion  = sbean.getPromotion(box);
                request.setAttribute("promotion", promotion);
                String progress = sbean.getProgress(box);
                request.setAttribute("progress", progress);
                /* ==========   ����������, �ڱ������� ��  ==========*/
            } 

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performStudySulmunListPage()\r\n" + ex.getMessage());
        }
    }


}

