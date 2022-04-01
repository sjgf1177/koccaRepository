package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.OpenEventBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.homepage.OpenEventServlet")
public class OpenEventServlet extends javax.servlet.http.HttpServlet implements Serializable {

	
	/**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        // int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            // String path = request.getServletPath();

            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "eventList");

            if (ErrorManager.isErrorMessageView())
                box.put("errorout", out);

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
            	return;
            }

            /////////////////////////////////////////////////////////////////////////////
            if (v_process.equals("eventList")) { 
                this.performListPage(request, response, box, out);		// 이벤트 리스트 페이지
            } else if (v_process.equals("detailPage")) {
            	this.performDetailPage(request, response, box, out);	// 이벤트 대상자리스트 페이지
            } else if (v_process.equals("excel")) {
            	this.performExcel(request, response, box, out);			// 이벤트 대상자리스트 엑셀다운로드
            } else if (v_process.equals("insertPage")) {
            	this.performInsertPage(request, response, box, out);	// 이벤트 등록페이지
            } else if (v_process.equals("insert")) {
            	this.performInsert(request, response, box, out);		// 이벤트 등록
            } else if (v_process.equals("updatePage")) {
            	this.performUpdatePage(request, response, box, out);	// 이벤트 수정페이지
            } else if (v_process.equals("update")) {
            	this.performUpdate(request, response, box, out);		// 이벤트 수정
            } else if (v_process.equals("delete")) {
            	this.performDelete(request, response, box, out);		// 이벤트 삭제
            }
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
    
    /**
     * 열린강좌 이벤트 리스트페이지
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/homepage/openclass/za_OpenEvent_L.jsp";
            OpenEventBean bean = new OpenEventBean();
            ArrayList<DataBox> list = bean.selectEventList(box);
            request.setAttribute("list", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/openclass/za_OpenEvent_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMoreListPage()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * 이벤트 대상자리스트 페이지
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performDetailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		request.setAttribute("requestbox", box);
    		String v_return_url = "/learn/admin/homepage/openclass/za_OpenEvent_V.jsp";
    		OpenEventBean bean = new OpenEventBean();
    		
    		DataBox info = bean.selectEventInfo(box);
    		request.setAttribute("info", info);
    		
    		ArrayList<DataBox> countList = bean.selectEventCountList(box, info);
    		request.setAttribute("countList", countList);
    		
    		ArrayList<DataBox> list = bean.selectEventTargetList(box);
    		request.setAttribute("list", list);
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
    		rd.forward(request, response);
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/homepage/openclass/za_OpenEvent_V.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performDetailPage()\r\n" + ex.getMessage());
    	}
    }
    
    /**
     * 이벤트 대상자리스트 페이지
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		request.setAttribute("requestbox", box);
    		String v_return_url = "/learn/admin/homepage/openclass/za_OpenEvent_E.jsp";
    		OpenEventBean bean = new OpenEventBean();
    		
    		ArrayList<DataBox> list = bean.selectEventTargetList(box);
    		request.setAttribute("list", list);
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
    		rd.forward(request, response);
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/homepage/openclass/za_OpenEvent_E.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performExcel()\r\n" + ex.getMessage());
    	}
    }
    
    /**
     * 이벤트 등록 페이지
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		request.setAttribute("requestbox", box);
    		String v_return_url = "/learn/admin/homepage/openclass/za_OpenEvent_I.jsp";
    		
    		OpenEventBean bean = new OpenEventBean();
    		
    		ArrayList<DataBox> list = bean.selectOpenclassList(box);
    		request.setAttribute("list", list);
    		
    		ArrayList<DataBox> checkList = bean.selectOpenclassCheckList(box);
    		request.setAttribute("checkList", checkList);
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
    		rd.forward(request, response);
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/homepage/openclass/za_OpenEvent_I.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performInsertPage()\r\n" + ex.getMessage());
    	}
    }
    
    /**
     * 이벤트 등록
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		OpenEventBean bean = new OpenEventBean();
    		int isOk = bean.insertEvent(box);

			String v_msg = "";
			String v_url = "/servlet/controller.homepage.OpenEventServlet";
			box.put("p_process", "eventList");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/homepage/openclass/za_OpenEvent_I.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performInsert()\r\n" + ex.getMessage());
    	}
    }
    
    /**
     * 이벤트 수정페이지
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		request.setAttribute("requestbox", box);
    		String v_return_url = "/learn/admin/homepage/openclass/za_OpenEvent_U.jsp";
    		
    		OpenEventBean bean = new OpenEventBean();
    		
    		DataBox info = bean.selectEventInfo(box);
    		request.setAttribute("info", info);
    		
    		ArrayList<DataBox> list = bean.selectOpenclassList(box);
    		request.setAttribute("list", list);
    		
    		ArrayList<DataBox> checkList = bean.selectOpenclassCheckList(box);
    		request.setAttribute("checkList", checkList);
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
    		rd.forward(request, response);
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/homepage/openclass/za_OpenEvent_U.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
    	}
    }
    
    /**
     * 이벤트 수정
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		OpenEventBean bean = new OpenEventBean();
    		int isOk = bean.updateEvent(box);
    		
    		String v_msg = "";
    		String v_url = "/servlet/controller.homepage.OpenEventServlet";
    		box.put("p_process", "eventList");
    		
    		AlertManager alert = new AlertManager();
    		
    		if(isOk > 0) {
    			v_msg = "update.ok";
    			alert.alertOkMessage(out, v_msg, v_url , box);
    		}
    		else {
    			v_msg = "update.fail";
    			alert.alertFailMessage(out, v_msg);
    		}
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/homepage/openclass/za_OpenEvent_U.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performUpdate()\r\n" + ex.getMessage());
    	}
    }
    
    
    /**
     * 이벤트 삭제
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		OpenEventBean bean = new OpenEventBean();
    		int isOk = bean.deleteEvent(box);
    		
    		String v_msg = "";
    		String v_url = "/servlet/controller.homepage.OpenEventServlet";
    		box.put("p_process", "eventList");
    		
    		AlertManager alert = new AlertManager();
    		
    		if(isOk > 0) {
    			v_msg = "delete.ok";
    			alert.alertOkMessage(out, v_msg, v_url , box);
    		}
    		else {
    			v_msg = "delete.fail";
    			alert.alertFailMessage(out, v_msg);
    		}
    		
    		Log.info.println(this, box, "Dispatch to /learn/admin/homepage/openclass/za_OpenEvent_U.jsp");
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performDelete()\r\n" + ex.getMessage());
    	}
    }
}
