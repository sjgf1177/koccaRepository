//**********************************************************
//1. 제      목: 관련사이트 SERVLET
//2. 프로그램명: LinkSiteServlet.java
//3. 개      요: 관련사이트 SERVLET
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 정경진 2005. 08. 03
//7. 수      정: 
//                 
//**********************************************************
package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.LinkSiteBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.LinkSiteServlet")
public class LinkSiteServlet extends HttpServlet implements Serializable {

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
			if (!AdminUtil.getInstance().checkRWRight("LinkSiteServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if (v_process.equals("selectList")) {		//관련사이트 리스트 페이지
				this.performSelectListPage(request, response, box, out);
			} else if (v_process.equals("updateSort")) {	//관련사이트 순위 변경 처리
				this.performUpdateSort(request, response, box, out);
			} else if (v_process.equals("viewPage")) {	//관련사이트 상세 조회 페이지
				this.performSelectViewPage(request, response, box, out);
			} else if (v_process.equals("insertPage")) {	//관련사이트 등록 페이지
				this.performInsertPage(request, response, box, out);
			} else if (v_process.equals("insert")) {		//관련사이트 등록 처리
				this.performInsert(request, response, box, out);
			} else if (v_process.equals("updatePage")) {	//관련사이트 수정 페이지
				this.performUpdatePage(request, response, box, out);
			} else if (v_process.equals("update")) {		//관련사이트 수정
				this.performUpdate(request, response, box, out);
			} else if (v_process.equals("delete")) {		//관련사이트 삭제
				this.performDelete(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

    
	/**
    관련사이트 리스트 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/homepage/za_LinkSite_L.jsp";
            
            LinkSiteBean bean = new LinkSiteBean();
            
            ArrayList list = bean.SelectList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectListPage()\r\n" + ex.getMessage());
        }
    }


	/**
    관련사이트 순위 변경
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performUpdateSort(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.homepage.LinkSiteServlet";

            LinkSiteBean bean = new LinkSiteBean();

            int isOk = bean.updateSort(box);

            String v_msg = "";
            box.put("p_process", "selectList");

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
            throw new Exception("performUpdateSort()\r\n" + ex.getMessage());
        }
    }
	

	/**
    관련사이트 내용 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/homepage/za_LinkSite_R.jsp";
            
            LinkSiteBean bean = new LinkSiteBean();            

            ArrayList list = bean.SelectView(box);
            request.setAttribute("selectInfo", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectViewPage()\r\n" + ex.getMessage());
        }
    }


	/**
    관련사이트 등록 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/homepage/za_LinkSite_I.jsp";
                        
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }


	/**
    관련사이트 등록 처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.homepage.LinkSiteServlet";

            LinkSiteBean bean = new LinkSiteBean();

            int isOk = bean.insert(box);

            String v_msg = "";
            box.put("p_process", "selectList");

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
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }
    

	/**
    관련사이트 수정 페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/homepage/za_LinkSite_U.jsp";
                        
            LinkSiteBean bean = new LinkSiteBean();

            ArrayList list = bean.SelectView(box);
            request.setAttribute("selectInfo", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }


	/**
    관련사이트 수정 처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.homepage.LinkSiteServlet";

            LinkSiteBean bean = new LinkSiteBean();

            int isOk = bean.update(box);

            String v_msg = "";
            box.put("p_process", "selectList");

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
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    
	/**
    관련사이트 삭제
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try{
            String v_url  = "/servlet/controller.homepage.LinkSiteServlet";

            LinkSiteBean bean = new LinkSiteBean();

            int isOk = bean.delete(box);

            String v_msg = "";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }
	
}
