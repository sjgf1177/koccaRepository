//**********************************************************
//1. 제      목: 온라인테스트 마스터관리
//2. 프로그램명: ETestMasterServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package controller.etest;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.etest.ETestMasterBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

@WebServlet("/servlet/controller.etest.ETestMasterServlet")
public class ETestMasterServlet extends HttpServlet implements Serializable {
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
            v_process = box.getStringDefault("p_process", "ETestMasterListPage");

            if(ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ETestMasterServlet", v_process, out, box)) {
                    return;
            }

            if (v_process.equals("ETestMasterListPage")) {                      //온라인테스트 마스터 리스트
                this.performETestMasterListPage(request, response, box, out);
            } else if (v_process.equals("ETestMasterInsertPage")) {             //온라인테스트 마스터 등록 페이지로 이동
                this.performETestMasterInsertPage(request, response, box, out);
            } else if (v_process.equals("ETestMasterUpdatePage")) {             //온라인테스트 마스터 수정 페이지로 이동
                this.performETestMasterUpdatePage(request, response, box, out);
            } else if (v_process.equals("ETestMasterInsert")) {                 //온라인테스트 마스터 등록할때
                this.performETestMasterInsert(request, response, box, out);
            } else if (v_process.equals("ETestMasterUpdate")) {                 //온라인테스트 마스터 수정할때
                this.performETestMasterUpdate(request, response, box, out);
            } else if (v_process.equals("ETestMasterDelete")) {                 //온라인테스트 마스터 삭제할때
                this.performETestMasterDelete(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    온라인테스트 마스터 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMasterListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestMaster_L.jsp";

            ETestMasterBean bean = new ETestMasterBean();
            ArrayList list1 = bean.selectETestMasterList(box);
            request.setAttribute("ETestMasterList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMasterListPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 마스터 등록 페이지로 이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMasterInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/etest/za_ETestMaster_I.jsp";
            
            ETestMasterBean bean = new ETestMasterBean();
            ArrayList list = bean.selectETestLevels(box);
            request.setAttribute("ETestLevelsData", list);
          
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMasterInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 마스터 수정 페이지로 이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
  public void performETestMasterUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
      try {
          request.setAttribute("requestbox", box);
          String v_return_url = "/learn/admin/etest/za_ETestMaster_U.jsp";

          ETestMasterBean bean = new ETestMasterBean();
            ArrayList list = bean.selectETestLevels(box);
            request.setAttribute("ETestLevelsData", list);

          DataBox dbox = bean.selectETestMasterData(box);
          request.setAttribute("ETestMasterData", dbox);

          ServletContext sc = getServletContext();
          RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
          rd.forward(request, response);
      }catch (Exception ex) {
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performETestMasterUpdatePage()\r\n" + ex.getMessage());
      }
  }

    /**
    온라인테스트 마스터 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMasterInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.etest.ETestMasterServlet";

            ETestMasterBean bean = new ETestMasterBean();
            int isOk = bean.insertETestMaster(box);
            //문제지 수 만큼 jsp 파일 생성

			String v_msg = "";
            box.put("p_process", "ETestMasterInsertPage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
              v_msg = "insert.ok";
             alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
              v_msg = "insert.fail";
             alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMasterInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 마스터 수정할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performETestMasterUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.etest.ETestMasterServlet";

            ETestMasterBean bean = new ETestMasterBean();
            int isOk = bean.updateETestMaster(box);
            //문제지 수 만큼 jsp 파일 생성

            String v_msg = "";
            box.put("p_process", "ETestMasterUpdatePage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMasterUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
    온라인테스트 마스터 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
   public void performETestMasterDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.etest.ETestMasterServlet";

            ETestMasterBean bean = new ETestMasterBean();
            int isOk = bean.deleteETestMaster(box);
            //문제지 jsp 파일 삭제

            String v_msg = "";
            box.put("p_process", "ETestMasterInsertPage");
            box.put("p_end", "0");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
              v_msg = "delete.ok";
              alert.alertOkMessage(out, v_msg, v_url , box);
			}else if(isOk==-2){                
				v_msg = "해당 마스터에 문제지가 있습니다.";   
				alert.alertFailMessage(out, v_msg);	              
            }else {
              v_msg = "delete.fail";
              alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performETestMasterDelete()\r\n" + ex.getMessage());
        }
    }

}