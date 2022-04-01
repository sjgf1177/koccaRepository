//**********************************************************
//1. 제      목: TEMPLET SERVLET
//2. 프로그램명: TempletServlet.java
//3. 개      요: 템플릿 관리
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 정 상 진
//7. 수      정:
//
//**********************************************************
package controller.templet;

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
import com.credu.system.AdminUtil;
import com.credu.templet.TempletBean;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.templet.TempletServlet")    
public class TempletServlet extends HttpServlet implements Serializable {

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
            v_process = box.getStringDefault("p_process","listPage");
            
            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            
            ///////////////////////////////////////////////////////////////////
            // 권한 check 루틴 VER 0.2 - 2003.08.10
            if (!AdminUtil.getInstance().checkRWRight("TempletServlet", v_process, out, box)) {
                return;
            }
            ///////////////////////////////////////////////////////////////////
            if (v_process.equals("listPage")) {                             //템플릿관리 리스트 조회 화면
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("updateManagerPage")) {             //템플릿관리 수정 화면
                this.performUpdateManagerPage(request, response, box, out);
            } else if (v_process.equals("updateManager")) {                 //템플릿관리 등록(저장)
                this.performUpdateManager(request, response, box, out);
            } else if (v_process.equals("updateMainPage")) {                //템플릿관리 메인 수정 화면
                this.performUpdateMainPage(request, response, box, out);
            } else if (v_process.equals("updateMain")) {                    //템플릿관리 메인 등록(저장)
                this.performUpdateMain(request, response, box, out);
            } else if (v_process.equals("contentsPopup")) {                 //템플릿관리 메인 컨텐츠 등록페이지 (팝업)
                this.performContentsPopup(request, response, box, out);
            } else if (v_process.equals("contentsUpdate")) {                 //템플릿관리 메인 컨텐츠 등록 (저장)
                this.performContentsUpdate(request, response, box, out);
            } else if (v_process.equals("updateSubPage")) {                 //템플릿관리 서브 수정 화면
                this.performUpdateSubPage(request, response, box, out);
            } else if (v_process.equals("updateSub")) {                     //템플릿관리 서브 수정(저장)
                this.performUpdateSub(request, response, box, out);
            } else if (v_process.equals("menuPopup")) {                     //템플릿관리 서브 메뉴 등록페이지 (팝업)
                this.performMenuPopup(request, response, box, out);
            } else if (v_process.equals("menuUpdate")) {                     //템플릿관리 서브 메뉴 등록 (저장)
                this.performMenuUpdate(request, response, box, out);
            } else if (v_process.equals("modeform")) {						// 템플릿관리 서브 수정 form 내용 가져오기
            	this.performModeForm(request, response, box, out);
            } else if (v_process.equals("insertSubTemplet")) {
            	this.performInsertSub(request, response, box, out);
            } else if (v_process.equals("updateMainMenuPage")) {             // 템플릿 관리 메인 메뉴 수정 페이지 (팝업)
            	this.performUpdateMainMenuPage(request, response, box, out);
            } else if (v_process.equals("updateMainMenu")) {             // 템플릿 관리 메인 메뉴 수정 
            	this.performUpdateMainMenu(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
	
	
	   /*템플릿관리 서브 메뉴 등록페이지 (팝업)
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performUpdateMainMenuPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	        throws Exception {
	            request.setAttribute("requestbox", box);
	            String v_url = "/learn/admin/templet/za_MainMenu_P.jsp";

	            TempletBean bean = new TempletBean();
	            // 마스터 메뉴 리스트
	            ArrayList list1 = bean.SelectMainMenuMaster(box);
	            request.setAttribute("MenuMaster", list1);
	            // 선택된 메뉴 리스트
	            ArrayList list2 = bean.SelectMainMenuList(box);
	            request.setAttribute("MenuList", list2);

	            ServletContext sc = getServletContext();
	            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
	            rd.forward(request, response);
	    }


	    /**
	    템플릿관리 서브 메뉴 등록 (저장)
	    @param request  encapsulates the request to the servlet
	    @param response encapsulates the response from the servlet
	    @param box      receive from the form object
	    @param out      printwriter object
	    @return void
	    */
	    public void performUpdateMainMenu(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	    throws Exception {
		    try{
		        String v_url = "/servlet/controller.templet.TempletServlet";
		
		        TempletBean bean = new TempletBean();
		        int isOk = bean.InsertMainMenu(box);
		
		        String v_msg = "";
		        box.put("p_process", "updateSubPage");
		
		        AlertManager alert = new AlertManager();
		        if(isOk > 0) {
		            v_msg = "update.ok";
		            alert.alertOkMessage(out, v_msg, v_url , box, true, true);
		        }else {
		            v_msg = "update.fail";
		            alert.alertFailMessage(out, v_msg);
		        }
		    }catch (Exception ex) {
		        ErrorManager.getErrorStackTrace(ex, out);
		        throw new Exception("performMenuUpdate()\r\n" + ex.getMessage());
		    }
	    }

    /**
    템플릿관리 리스트 조회 화면
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/templet/za_Templet_L.jsp";

            TempletBean bean = new TempletBean();
            ArrayList list1 = bean.SelectEduGroupList(box);
            request.setAttribute("EduGroupList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }


    /**
    템플릿관리 수정 화면
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateManagerPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/templet/za_Templet_U.jsp";

            TempletBean bean = new TempletBean();
            String result = bean.getGrcodeType(box);
            request.setAttribute("Grcodetype", result);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateManagerPage()\r\n" + ex.getMessage());
        }
    }

    /**
    템플릿관리 등록(저장)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateManager(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.templet.TempletServlet";

            TempletBean bean = new TempletBean();
            int isOk = bean.InsertGrcodeType(box);

            String v_msg = "";
            box.put("p_process", "listPage");

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
            throw new Exception("performUpdateManager()\r\n" + ex.getMessage());
        }
    }

    /**
    템플릿관리 메인 수정 화면
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateMainPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/templet/za_TempletMain_U.jsp";

            TempletBean bean = new TempletBean();
            DataBox dbox = bean.selectTemplet(box);
            request.setAttribute("TempletData", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMainPage()\r\n" + ex.getMessage());
        }
    }

    /**
    템플릿관리 메인 수정(저장)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateMain(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.templet.TempletServlet";

            TempletBean bean = new TempletBean();
            int isOk = bean.updateTempletMain(box);

            String v_msg = "";
            box.put("p_process", "listPage");

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
            throw new Exception("performUpdateMain()\r\n" + ex.getMessage());
        }
    }


    /**
    템플릿관리 메인 컨텐츠 등록페이지 (팝업)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performContentsPopup(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/templet/za_Menu_P.jsp";

            TempletBean bean = new TempletBean();
            // 마스터 메뉴 리스트
            ArrayList list1 = bean.SelectMenuMaster(box);
            request.setAttribute("MenuMaster", list1);
            // 선택된 메뉴 리스트
            ArrayList list2 = bean.SelectMenuList(box);
            request.setAttribute("MenuList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
    }

    /**
    템플릿관리 메인 컨텐츠 등록 (저장)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performContentsUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url = "/servlet/controller.templet.TempletServlet";

            TempletBean bean = new TempletBean();
            int isOk = bean.InsertMenu(box);

            String v_msg = "";
            box.put("p_process", "updateMainPage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, true, true);
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performContentsUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
    템플릿관리 서브 수정 화면
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateSubPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/templet/za_TempletSub_U.jsp";

            TempletBean bean = new TempletBean();
            ArrayList list = bean.selectMasterTemplet(box);
            request.setAttribute("TempletMasterList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateSubPage()\r\n" + ex.getMessage());
        }
    }

    /**
    템플릿관리 서브 수정(저장)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateSub(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.templet.TempletServlet";
            TempletBean bean = new TempletBean();
            int isOk = bean.updateTempletSub(box);

            String v_msg = "";
            box.put("p_process", "listPage");

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
            throw new Exception("performUpdateSub()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performInsertSub(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		String v_url  = "/servlet/controller.templet.TempletServlet";
            TempletBean bean = new TempletBean();
            
            int isOk = bean.updateSubTemplet(box);
            
            String v_msg = "";
            box.put("p_process", "updateSubPage");
            box.put("p_grcode", box.getString("p_grtype"));
    		
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateSubPage()\r\n" + ex.getMessage());
    	}
    }

    /**
    템플릿관리 서브 메뉴 등록페이지 (팝업)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMenuPopup(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/templet/za_Menu_P.jsp";

            TempletBean bean = new TempletBean();
            // 마스터 메뉴 리스트
            ArrayList list1 = bean.SelectMenuMaster(box);
            request.setAttribute("MenuMaster", list1);
            // 선택된 메뉴 리스트
            ArrayList list2 = bean.SelectMenuList(box);
            request.setAttribute("MenuList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
    }


    /**
    템플릿관리 서브 메뉴 등록 (저장)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMenuUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
	    try{
	        String v_url = "/servlet/controller.templet.TempletServlet";
	
	        TempletBean bean = new TempletBean();
	        int isOk = bean.InsertMenu(box);
	
	        String v_msg = "";
	        box.put("p_process", "updateSubPage");
	
	        AlertManager alert = new AlertManager();
	        if(isOk > 0) {
	            v_msg = "update.ok";
	            alert.alertOkMessage(out, v_msg, v_url , box, true, true);
	        }else {
	            v_msg = "update.fail";
	            alert.alertFailMessage(out, v_msg);
	        }
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performMenuUpdate()\r\n" + ex.getMessage());
	    }
    }
   
    /**
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performModeForm(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
	    try{
	        request.setAttribute("requestbox", box);
	        String v_url = "/learn/admin/templet/za_TempletSub_C.jsp";
	
	        TempletBean bean = new TempletBean();
	        DataBox dbox = bean.selectSubMainTemplet(box);
	        if(dbox == null) {
	        	dbox = bean.selectMasterOneTemplet(box);
	        }
	        request.setAttribute("SubMainTemplet", dbox);
	
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher(v_url);
	        rd.forward(request, response);
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performUpdateSubPage()\r\n" + ex.getMessage());
	    }
    }
}
