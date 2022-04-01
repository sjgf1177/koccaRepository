package controller.homepage;

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
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ProposeCourseBean;
import com.credu.study.MyClassBean;
import com.credu.system.AdminUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010. 8. 12
 * Time: 오전 9:38:51
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.homepage.AspMenuMainServlet")
public class AspMenuMainServlet extends javax.servlet.http.HttpServlet implements Serializable {
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}

	@SuppressWarnings("unused")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out 		= null;
        RequestBox box 		= null;
        String 		v_process 	= "";

        boolean     v_canRead 	= false;
        boolean     v_canAppend = false;
        boolean     v_canModify = false;
        boolean     v_canDelete = false;
        boolean     v_canReply  = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if(v_process.equals("IDENTIFY")){
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return;
			}}

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("GUIDE")) {     			 		     //  수강신청안내
            	this.performGuide(request, response, box, out);
            } else if(v_process.equals("REQUEST")) {                        //  수강신청
            	this.performRequest(request, response, box, out);
            } else if(v_process.equals("IDENTIFY")) {                   //  수강신청확인/취소
            	this.performIdentify(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    public void performGuide(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            String v_url = "/learn/user/portal/online/zu_subjectInfomation_R.jsp";
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/online/zu_subjectInfomation_R.jsp";
            }

    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher(v_url);
    		rd.forward(request, response);

         } catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }

    @SuppressWarnings("rawtypes")
	public void performRequest(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
    		 box.put("p_area", box.getStringDefault("p_area", ""));
    		 box.put("p_searchtext", box.getStringDefault("p_searchtext", ""));

			ProposeCourseBean bean = new ProposeCourseBean();
			ArrayList list1 = null;
			String v_url = "";
System.out.println("=================================== box : " + box);
			v_url = "/learn/user/portal/propose/zu_Asp_Subject_L.jsp";
			if(box.getSession("tem_type").equals("B")){
				v_url = "/learn/user/typeB/propose/zu_Asp_Subject_L.jsp";
			}
			list1 = bean.selectSubjectList(box);
			
			
			request.setAttribute("SubjectList", list1);
			request.setAttribute("requestbox", box);
			
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);

         } catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }

    @SuppressWarnings("rawtypes")
	public void performIdentify(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             String v_url = "/learn/user/portal/course/gu_Asp_ProposeHistory_L.jsp";
             if(box.getSession("tem_type").equals("B")){
            	 v_url = "/learn/user/typeB/course/gu_Asp_ProposeHistory_L.jsp";
             }
             
			 MyClassBean bean = new MyClassBean();
			 ArrayList list = bean.selectProposeHistoryList(box);
			 
			 request.setAttribute("requestbox", box);
			 request.setAttribute("ProposeHistoryList", list);
			 
			 ServletContext sc = getServletContext();
			 RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			 rd.forward(request, response);
         } catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }
}
