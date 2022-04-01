//*********************************************************
//1. 제      목: 오프라인 수강료 납부등록
//2. 프로그램명: OffBillRegistAdminServlet.java
//3. 개      요: 오프라인 servlet
//4. 환      경: JDK 1.5
//5. 버      젼: 1.0
//6. 작      성: 2009.12.22
//7. 수      정:
//**********************************************************
package controller.off;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OffBillRegistAdminBean;

//public class OutClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffBillRegistAdminServlet")
public class OffBillRegistAdminServlet extends javax.servlet.http.HttpServlet  implements Serializable {

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
      RequestBox box = null;
      String v_process = "";

      try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getStringDefault("p_process","selectList");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("selectList")) {                        //  조회화면
                this.performSelectList(request, response, box, out);
            }else if(v_process.equals("ExcelDown")) {                   //  엑셀 저장
                this.performExcelList(request, response, box, out);
            }else if(v_process.equals("offbillInsertPage")) {                   //  수강료납부 등록 페이지
                this.performOffBillInsertPage(request, response, box, out);
            }else if(v_process.equals("SearchStudentOpenPage")){   //발급대상자 조회
                this.performSearchStudentOpenPage(request, response, box, out);
            }else if(v_process.equals("offbillInsert")){   //수강료납부 등록
                this.performInsert(request, response, box, out);
            }else if(v_process.equals("offbillUpdatePage")){   //수강료 납부세부정보
                this.performSelectOffBillRegistInfo(request, response, box, out);
            }else if(v_process.equals("offbillUpdate")){   //수강료 납부 결제정보 수정
                this.performUpdate(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    리스트(관리자)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            OffBillRegistAdminBean bean = new OffBillRegistAdminBean();

            ArrayList list = bean.selectList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_OffBillRegistAdmin_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/off/za_OffBillRegistAdmin_L.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
      Excel 다운
      @param request  encapsulates the request to the servlet
      @param response encapsulates the response from the servlet
      @param box      receive from the form object
      @param out      printwriter object
      @return void
      */
      public void performExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
          try{
              request.setAttribute("requestbox", box);
              OffBillRegistAdminBean bean = new OffBillRegistAdminBean();

              if (box.getString("p_action").equals("go")) {
                  ArrayList list = bean.selectExcelList(box);
                  request.setAttribute("selectList", list);
              }

              ServletContext sc = getServletContext();
              RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_OffBillRegistAdmin_E.jsp");
              rd.forward(request, response);
          }catch (Exception ex) {
              ErrorManager.getErrorStackTrace(ex, out);
              throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
          }
      }

    /**
    수강료납부 등록 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performOffBillInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            OffBillRegistAdminBean bean = new OffBillRegistAdminBean();

            DataBox dbox = bean.selectSubjseqInfo(box);
            request.setAttribute("subjseqInfo", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_OffBillRegistAdmin_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/off/za_OffBillRegistAdmin_I.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTaxRegistPage()\r\n" + ex.getMessage());
        }
    }



    /**
    발급대상자 검색 PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSearchStudentOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다
            OffBillRegistAdminBean bean = new OffBillRegistAdminBean();
            ArrayList list = bean.selectSearchStudent(box);

            request.setAttribute("searchStudent", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_SearchStudent.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {

            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("searchTutorOpenPage()\r\n" + ex.getMessage());
        }
    }

    /**
    수강료납부 등록
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url  = "/servlet/controller.off.OffBillRegistAdminServlet";

            OffBillRegistAdminBean bean = new OffBillRegistAdminBean();

            int isOk = bean.insert(box);
            box.put("p_process","selectList");

            String v_msg = "";

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "save.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, true, true);
            }else {
                v_msg = "save.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on performInsert");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }

    }

    /**
    수강료 납부등록 내용 정보 수정 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectOffBillRegistInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            OffBillRegistAdminBean bean = new OffBillRegistAdminBean();

            DataBox dbox = bean.selectOffBillRegistInfo(box);
            request.setAttribute("OffBillRegistInfo", dbox);

            ArrayList list = bean.selectOffBillStudent(box);
            request.setAttribute("searchOffBillStudentList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_OffBillRegistAdmin_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/off/za_OffBillRegistAdmin_U.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTaxRegistPage()\r\n" + ex.getMessage());
        }
    }

    /**
    수강료 납부등록 내용 정보 수정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url  = "/servlet/controller.off.OffBillRegistAdminServlet";

            OffBillRegistAdminBean bean = new OffBillRegistAdminBean();

            int isOk = bean.update(box);
            box.put("p_process","selectList");

            String v_msg = "";

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "save.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, true, true);
            }else {
                v_msg = "save.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on performUpdate");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }

    }

}