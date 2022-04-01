package controller.infomation;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.infomation.GoldClassGroupBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

/**
 * �������� �ϰ����� ����
 * 
 * @author saderaser
 * @since ?
 */
@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.infomation.GoldClassGroupServlet")
public class GoldClassGroupServlet extends javax.servlet.http.HttpServlet implements Serializable {

	/**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        // MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        // int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "list");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("GoldClassGroupServlet", v_process, out, box)) {
                return;
            }

            // �����׷캰 �������� ��� ����Ʈ
            if (v_process.equals("list")) { 
                this.performList(request, response, box, out);
                
            // �����׷캰 �������� ��� ������
            } else if (v_process.equals("insertPage")) {
            	this.performInsertPage(request, response, box, out);
            	
            // �����׷캰 �������� ���
            } else if (v_process.equals("insert")) {
	        	this.performInsert(request, response, box, out);
	        	
        	// �ϰ��������ε� ������
	        } else if (v_process.equals("uploadPage")) {
	        	this.performUploadPage(request, response, box, out);

        	// �ϰ��������ε�
	        } else if (v_process.equals("upload")) {
	        	this.performUpload(request, response, box, out);
	        }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
    
    /**
     * �����׷캰 �������� ��� ����Ʈ
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //��������� box ��ü�� �Ѱ��ش�

            GoldClassGroupBean bean = new GoldClassGroupBean();
            ArrayList list = bean.selectGroupList(box);
            request.setAttribute("list", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_GoldClassGroup_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performList()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * �������� �ϰ���� ������
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		request.setAttribute("requestbox", box); //��������� box ��ü�� �Ѱ��ش�
    		
    		GoldClassGroupBean bean = new GoldClassGroupBean();
    		/* ���� �����׷����� ��ȸ */
    		DataBox groupInfo = bean.selectGrcodeInfo(box);
    		request.setAttribute("groupInfo", groupInfo);
    		
    		/* ��� �������� ���� ��� */
    		ArrayList goldclassList = bean.selectGoldclassList(box);
    		request.setAttribute("goldclassList", goldclassList);
    		
    		/* ��� �������� ��� */
    		ArrayList groupList = bean.selectGoldclassGroupList(box);
    		request.setAttribute("groupList", groupList);
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_GoldClassGroup_I.jsp");
    		rd.forward(request, response);
    		
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performInsertPage()\r\n" + ex.getMessage());
    	}
    }
    
    /**
     * �����׷캰 �������� ���
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    public void performInsert(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {

        	GoldClassGroupBean bean = new GoldClassGroupBean();
            int isOk = bean.saveGoldclassList(box);
            StringBuilder sb = new StringBuilder();

            sb.append("{\"isOk\": " + isOk + "}");

            out.write(sb.toString());
            out.flush();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * �ϰ� �������ε� ������
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performUploadPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		request.setAttribute("requestbox", box); //��������� box ��ü�� �Ѱ��ش�
    		
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_GoldClassGroup_E.jsp");
    		rd.forward(request, response);
    		
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performUploadPage()\r\n" + ex.getMessage());
    	}
    }
    
    /**
     * �ϰ� �������ε�
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performUpload(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    	try {
    		String v_url = "/servlet/controller.course.GrseqServlet";

    		GoldClassGroupBean bean = new GoldClassGroupBean();
            ArrayList list = bean.insertGoldclassExcel(box);
            request.setAttribute("list", list);

            AlertManager alert = new AlertManager();
            if (list != null) {
            	request.setAttribute("requestbox", box);
            	v_url = "/learn/admin/infomation/za_GoldClassGroup_R.jsp";
            	ServletContext sc = getServletContext();
        		RequestDispatcher rd = sc.getRequestDispatcher(v_url);
        		rd.forward(request, response);
            } else {
                alert.alertFailMessage(out, "insert.fail");
            }
    	} catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performUpload()\r\n" + ex.getMessage());
    	}
    }
}
