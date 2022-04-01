package controller.off;

import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OffGrseqBean;
import com.credu.off.OffStatisticsConditionBean;
import com.credu.system.AdminUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010. 6. 17
 * Time: 오전 9:33:17
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffStatisticsCondition")
public class OffStatisticsCondition extends javax.servlet.http.HttpServlet implements Serializable {
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}

    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;
		RequestBox box = null;
		String v_process = "";

		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);
			v_process = box.getString("p_process").trim();

			if (v_process.equals("listPage")) {                             //in case of 차수 목록 화면
				this.performListPage(request, response, box, out);
			} else if(v_process.equals("jqueryList")) { //실제적으로 목록을 뿌리는 곳
                this.performJqueryList(request, response, box, out);
            }
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

    //목록 페이지 호출
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/off/za_off_Statics_L.jsp";
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}

    //데이타 불러옴
    public void performJqueryList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/off/za_jquery_statics.jsp";

            OffStatisticsConditionBean bean = new OffStatisticsConditionBean();
            request.setAttribute("resultList", bean.listPage(box));

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
}
