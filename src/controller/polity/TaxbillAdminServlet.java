//*********************************************************
//1. 제      목: 계산서 관리
//2. 프로그램명: TaxbillAdminServlet.java
//3. 개      요: 행정서비스 servlet
//4. 환      경: JDK 1.5
//5. 버      젼: 1.0
//6. 작      성: 2009.12.17
//7. 수      정:
//**********************************************************
package controller.polity;

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
import com.credu.polity.TaxbillAdminBean;

//public class OutClassServlet extends javax.servlet.http.HttpServlet implements Serializable {
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.polity.TaxbillAdminServlet")
public class TaxbillAdminServlet extends javax.servlet.http.HttpServlet  implements Serializable {

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
            }else if(v_process.equals("taxInsertPage")) {                   //  계산서 등록 페이지
                this.performTaxInsertPage(request, response, box, out);
            }else if(v_process.equals("SearchMemberOpenPage")){   //발급대상자 조회
                this.performSearchMemberOpenPage(request, response, box, out);
            }else if(v_process.equals("taxInsert")){   //계산서 등록
                this.performInsert(request, response, box, out);
            }else if(v_process.equals("SearchStudentPage")){   //입과인원
                this.performSearchStudentPage(request, response, box, out);
            }else if(v_process.equals("taxbillInfo")){   //계산서세부정보
                this.performSelectTaxBillInfo(request, response, box, out);
            }else if(v_process.equals("taxUpdate")){   //계산서 결제정보 수정
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

            TaxbillAdminBean bean = new TaxbillAdminBean();

            ArrayList list = bean.selectList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_TaxbillAdmin_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_TaxbillAdmin_L.jsp");

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
            //            String v_return_url = "/learn/admin/polity/za_TaxbillAdmin_E.jsp";
            TaxbillAdminBean bean = new TaxbillAdminBean();

            if (box.getString("p_action").equals("go"))
            {
                ArrayList list = bean.selectExcelList(box);
                request.setAttribute("selectList", list);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_TaxbillAdmin_E.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSearch()\r\n" + ex.getMessage());
        }
    }

    /**
    계산서 등록 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performTaxInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            TaxbillAdminBean bean = new TaxbillAdminBean();

            DataBox dbox = bean.selectGroupInfo(box);
            request.setAttribute("groupInfo", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_TaxbillAdmin_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_TaxbillAdmin_I.jsp");

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
    public void performSearchMemberOpenPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다
            TaxbillAdminBean bean = new TaxbillAdminBean();
            ArrayList list = bean.selectSearchMember(box);

            request.setAttribute("searchMember", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_SearchMember.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("searchTutorOpenPage()\r\n" + ex.getMessage());
        }
    }

    /**
    계산서 등록
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url  = "/servlet/controller.polity.TaxbillAdminServlet";

            TaxbillAdminBean bean = new TaxbillAdminBean();

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
   입과인원
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSearchStudentPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다
            TaxbillAdminBean bean = new TaxbillAdminBean();

            ArrayList list1 = bean.selectGrInfo(box);
            request.setAttribute("searchGrInfo", list1);

            ArrayList list2 = bean.selectSearchStudent(box);
            request.setAttribute("searchStudent", list2);


            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_SearchStudent.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {

            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("searchTutorOpenPage()\r\n" + ex.getMessage());
        }
    }

    /**
    계산서 등록 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performSelectTaxBillInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            TaxbillAdminBean bean = new TaxbillAdminBean();

            DataBox dbox = bean.selectTaxBillInfo(box);
            request.setAttribute("taxbillInfo", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/polity/za_TaxbillAdmin_U.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/polity/za_TaxbillAdmin_U.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTaxRegistPage()\r\n" + ex.getMessage());
        }
    }

    /**
    계산서 결제정보 수정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
     */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url  = "/servlet/controller.polity.TaxbillAdminServlet";

            TaxbillAdminBean bean = new TaxbillAdminBean();

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