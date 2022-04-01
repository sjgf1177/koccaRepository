//**********************************************************
//  1. 제      목: 코드관리 제어하는 서블릿
//  2. 프로그램명 : CodeAdminServlet.java
//  3. 개      요: 코드관리 제어 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 2
//  7. 수     정1:
//**********************************************************
package controller.system;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import com.credu.system.CodeAdminBean;
import com.credu.system.CodeData;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.system.CodeAdminServlet")
public class CodeAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
//        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
//        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("CodeAdminServlet", v_process, out, box)) {
				return; 
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////
            if(v_process.equals("selectView"))  {                       // 대분류코드 상세보기로 이동할때
                this.performSelectView(request, response, box, out);
            } else if(v_process.equals("insertPage")) {                 // 대분류코드 등록페이지로 이동할때
                this.performInsertPage(request, response, box, out);
            } else if(v_process.equals("insert")) {                     // 대분류코드 등록할때
                this.performInsert(request, response, box, out);
            } else if(v_process.equals("updatePage")) {                 // 대분류코드 수정페이지로 이동할때
                this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                     // 대분류코드 수정하여 저장할때
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {                     // 대분류코드 삭제할때
                this.performDelete(request, response, box, out);
            } else if(v_process.equals("selectSubView")){               // 소분류코드 상세보기로 이동할때
                this.performSelectSubView(request, response, box, out);
            } else if(v_process.equals("insertSubPage")) {              // 소분류코드 등록페이지로 이동할때
                this.performInsertSubPage(request, response, box, out);
            } else if(v_process.equals("insertSub")) {                  // 소분류코드 등록할때
                this.performInsertSub(request, response, box, out);
            } else if(v_process.equals("updateSubPage")) {              // 소분류코드 수정페이지로 이동할때
                this.performUpdateSubPage(request, response, box, out);
            } else if(v_process.equals("updateSub")) {                  // 소분류코드 수정하여 저장할때
                this.performUpdateSub(request, response, box, out);
            } else if(v_process.equals("deleteSub")) {                  // 소분류코드 삭제할때
                this.performDeleteSub(request, response, box, out);
            } else if(v_process.equals("selectSubList")) {              // 소분류코드 리스트
                this.performSelectSubList(request, response, box, out);
            } else if(v_process.equals("select")) {                     // 대분류코드 리스트
                this.performSelectList(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
 
    /**
    대분류코드 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            CodeAdminBean bean = new CodeAdminBean();
            CodeData data = bean.selectViewCode(box);

            request.setAttribute("selectCode", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Code_R.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Code_R.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

     /**
    대분류코드 등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            /*-------- 하단 리스트 출력 시작 -------------*/
            CodeAdminBean bean = new CodeAdminBean();
            ArrayList list = bean.selectListCode(box);

            request.setAttribute("selectList", list);
            /*-------- 하단 리스트 출력 끝   -------------*/

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Code_I.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Code_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

     /**
    대분류코드 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.insertCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

     /**
    대분류코드 수정페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            /*-------- 하단 리스트 출력 시작 -------------*/
            CodeAdminBean bean = new CodeAdminBean();
            ArrayList list = bean.selectListCode(box);

            request.setAttribute("selectList", list);
            /*-------- 하단 리스트 출력 끝   -------------*/

            CodeData data = bean.selectViewCode(box);

            request.setAttribute("selectCode", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Code_U.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    대분류코드 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.updateCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "select");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

     /**
    대분류코드 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.deleteCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
    소분류코드 상세보기로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectSubView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            CodeAdminBean bean = new CodeAdminBean();
            CodeData data = bean.selectSubViewCode(box);

            request.setAttribute("selectSubCode", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CodeSub_R.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CodeSub_R.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSubView()\r\n" + ex.getMessage());
        }
    }

     /**
    소분류코드 등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertSubPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            /*-------- 하단 리스트 출력 시작 -------------*/
            CodeAdminBean bean = new CodeAdminBean();
            ArrayList List = bean.selectSubListCode(box);

            request.setAttribute("selectSubList", List);
            /*-------- 하단 리스트 출력 시작 -------------*/

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CodeSub_I.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CodeSub_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertSubPage()\r\n" + ex.getMessage());
        }
    }

     /**
    소분류코드 등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertSub(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.insertSubCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "selectSubList");

            AlertManager alert = new AlertManager();

            if(isOk == 3){
            	v_msg = "insert.duplication";
                alert.alertFailMessage(out, v_msg);
            }
            else if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertSub()\r\n" + ex.getMessage());
        }
    }


     /**
    소분류코드 수정페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateSubPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            /*-------- 하단 리스트 출력 시작 -------------*/
            CodeAdminBean bean = new CodeAdminBean();
            ArrayList list = bean.selectSubListCode(box);

            request.setAttribute("selectSubList", list);
            /*-------- 하단 리스트 출력 끝   -------------*/

            CodeData data = bean.selectSubViewCode(box);

            request.setAttribute("selectSubCode", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CodeSub_U.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CodeSub_U.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateSubPage()\r\n" + ex.getMessage());
        }
    }

     /**
    // 소분류코드 수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateSub(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.updateSubCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "selectSubList");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateSub()\r\n" + ex.getMessage());
        }
    }

     /**
    // 소분류코드 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDeleteSub(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           CodeAdminBean bean = new CodeAdminBean();

            int isOk = bean.deleteSubCode(box);

            String v_msg = "";
            String v_url = "/servlet/controller.system.CodeAdminServlet";
            box.put("p_process", "selectSubList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CodeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteSub()\r\n" + ex.getMessage());
        }
    }

    /**
    소분류코드 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectSubList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            CodeAdminBean bean = new CodeAdminBean();
            ArrayList List = bean.selectSubListCode(box);

            request.setAttribute("selectSubList", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_CodeSub_L.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_CodeSub_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSubList()\r\n" + ex.getMessage());
        }
    }

    /**
    대분류코드 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            CodeAdminBean bean = new CodeAdminBean();
            ArrayList list = bean.selectListCode(box);

            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/system/za_Code_L.jsp");
            rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/system/za_Code_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

}

