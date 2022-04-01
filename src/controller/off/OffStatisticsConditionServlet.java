package controller.off;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OffStatisticsConditionBean;
import com.credu.system.AdminUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010. 6. 17
 * Time: ���� 9:33:17
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffStatisticsConditionServlet")
public class OffStatisticsConditionServlet extends javax.servlet.http.HttpServlet implements Serializable {
    //@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}
    //@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;
		RequestBox box = null;
		String v_process = "";

		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);
			v_process = box.getString("p_process");
			//v_process = box.getString("p_process").trim();
			
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("OffStatisticsConditionServlet", v_process, out, box)) {
			return; 
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////
			
			if (v_process.equals("listPage")) {                            //in case of ���� ��� ȭ��
				this.performListPage(request, response, box, out);
			} else if(v_process.equals("jqueryList")) { //���������� ����� �Ѹ��� ��
                this.performJqueryList(request, response, box, out);
            }else if (v_process.equals("listPage1")) {                            //in case of ���� ��� ȭ��
				this.performListPage1(request, response, box, out);

			// �߰� 	
            }else if(v_process.equals("selectCourseStat")) {   // ������ ��Ȳ
	             this.performSelectCourseStat(request, response, box, out);
	        }else if(v_process.equals("selectAgeStat")) {   // ���� ��Ȳ
	             this.performSelectAgeStat(request, response, box, out);
	        }else if(v_process.equals("selectDetail")) {   // �󼼺���
	             this.performSelectDetailStat(request, response, box, out);
	        }
			
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

    //��� ������ ȣ��
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_return_url = "/learn/admin/off/za_off_StaticsNew_L.jsp";
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}
    
  //��� ������ ȣ��
    public void performListPage1(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
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

    //����Ÿ �ҷ���
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
    
    /**
	������ ��Ȳ
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelectCourseStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);
	        String v_return_url ="/learn/admin/off/za_CourseStatAjax_L.jsp";
	        
	        OffStatisticsConditionBean bean = new OffStatisticsConditionBean();
	        ArrayList list = bean.selectOffCourseStatList(box);
	        request.setAttribute("selectList", list);
	        
	        /*if (box.getBoolean("isExcel")) {
				v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
				box.put("title", "������ �˻�");//���� ����
                box.put("tname", "�о�|������|�����ο�|����|�̼���|������");//�÷���
				box.put("tcode", "d_area|d_subjnm|d_user_cnt|d_grad_cnt|d_ngrad_cnt|d_sul_rate");//�������̸�
				box.put("resultListName", "selectList");//��� ���
			}*/
	
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
	        rd.forward(request, response);
	
	        Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CousreStatAjax_L.jsp");
	
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
	    }
	}
	/**
	���ɺ� ����Ʈ(�˻���)
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelectAgeStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);
	        
	        String v_return_url = "/learn/admin/off/za_AgeStatAjax_L.jsp";
	        
	        OffStatisticsConditionBean bean = new OffStatisticsConditionBean();
	        ArrayList list = bean.selectAgeList(box);
	        request.setAttribute("selectList", list);
	        
	        if (box.getBoolean("isExcel")) {
				v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
				box.put("title", "���ɺ� �˻�");//���� ����
                box.put("tname", "������|�з�|�о�|��ü|10��|20��|30��|40��|50��|60��|70��|��Ȯ��");//�÷���
				box.put("tcode", "d_grseqnm|d_areaname|d_cate|d_tot|d_teens|d_twenty|d_thirty|d_fourty|d_fifty|d_sixty|d_seventy|d_johndoe");//�������̸�
				box.put("resultListName", "selectList");//��� ���
			}
	
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
	        rd.forward(request, response);
	
	        Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_AgeStatAjax_L.jsp");
	
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
	    }
	}
	/**
	�󼼺���
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelectDetailStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	    try {
	        request.setAttribute("requestbox", box);
	        
	        
	        OffStatisticsConditionBean bean = new OffStatisticsConditionBean();
	        ArrayList list = bean.selectDetailList(box);
	        request.setAttribute("selectList", list);
	        
	        String v_return_url = "/learn/admin/off/za_DetailStatAjax_L.jsp";
	        
	        ServletContext sc = getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
	        rd.forward(request, response);
	
	        Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_AgeStatAjax_L.jsp");
	
	    }catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, out);
	        throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
	    }
	}
	
	
}
