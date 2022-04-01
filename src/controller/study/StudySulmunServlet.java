//**********************************************************
//  1. 제      목:  과정 설문 서블릿
//  2. 프로그램명 : StudySulmunServlet.java
//  3. 개      요: 과정 설문 서블릿
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 조성용 2004. 12. 20
//  7. 수     정1:
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

            if (v_process.equals("StudySulmunListPage")) {                      //과정 설문, 컨텐츠 평가  리스트
                this.performStudySulmunListPage(request, response, box, out);
            } 

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    설문 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performStudySulmunListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
			String v_subj = box.getString("p_subj"); //원 과정 코드 
            String v_return_url = "/learn/user/study/zu_StudySulmun_L.jsp";
      
            // 과정별 메뉴 접속 정보 추가
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

            /* ==========   과정설문 응시여부 ==========*/     
            box.put("p_subj","ALL"); 	// 과정 코드가 아닌 설문 유형 분류 (수정 해야 할지...)       
            int suldata = bean.getUserData(box);    
            box.put("p_suldata",String.valueOf(suldata));         
            /* ==========   과정설문 응시여부 ==========*/   

            /* ==========   컨텐츠평가 응시여부 ==========*/     
            box.put("p_subj","REGIST");       // 과정 코드가 아닌 설문 유형 분류 (수정 해야 할지...)       
            int contentsdata = bean1.getUserData(box);      
            box.put("p_contentsdata",String.valueOf(contentsdata));   
            box.put("p_subj",v_subj);        // 원 과정 코드를 다시 가져옴.         
            /* ==========   컨텐츠평가 응시여부 ==========*/ 

            if(!box.getSession("s_subjseq").equals("0000")){    // 베타테스트일경우
                /* ==========   권장진도율, 자기진도율 시작 ==========*/
                SubjGongAdminBean sbean = new SubjGongAdminBean();

                String promotion  = sbean.getPromotion(box);
                request.setAttribute("promotion", promotion);
                String progress = sbean.getProgress(box);
                request.setAttribute("progress", progress);
                /* ==========   권장진도율, 자기진도율 끝  ==========*/
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

