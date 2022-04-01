//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ ���������� �����ϴ� ����
//  2. ���α׷��� : HomePageBoardServlet.java
//  3. ��      ��: Ŀ�´�Ƽ�� �������� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.1
//  7. ��      ��: 2005.7.1
//**********************************************************

package controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityQnABean;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@SuppressWarnings({ "unchecked", "serial" })
public class CommunityDirectServlet extends javax.servlet.http.HttpServlet {

	/**
	 * DoGet
	 * Pass get requests through to PerformTask
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter      out          = null;
		RequestBox       box          = null;
		String           v_process    = "";

		try {
			response.setContentType("text/html;charset=euc-kr");

			out       = response.getWriter();
			box       = RequestManager.getBox(request);
			v_process = box.getStringDefault("p_process","selectlist");

			if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

			// �α� check ��ƾ VER 0.2 - 2003.09.9
			//if (!AdminUtil.getInstance().checkLogin(out, box)) {
			// return;
			//}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

			/////////////////////////////////////////////////////////////////////////////
			if(v_process.equals("selectList")) {          //  ����Ʈ �������� �̵��Ҷ�
				this.performListPage(request, response, box, out);
			} else if(v_process.equals("viewPage")) {           //  ����
				this.performViewPage(request, response, box, out);

			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}


	/**
    ����Ʈ�������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			CommunityQnABean bean = new CommunityQnABean();
			ArrayList list = bean.selectListQna(box);

			request.setAttribute("list", list);



			//����Ʈ ȸ����ü�� �α�����ȸ������
			//CommunityIndexBean beanAllMem = new CommunityIndexBean();
			//ArrayList listbeanAllMem = beanAllMem.selectTz_Member(box);
			//request.setAttribute("listAllUser", listbeanAllMem);


			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuDirect_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuDirect_L.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}


	/**
    ���������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);
			CommunityQnABean bean = new CommunityQnABean();
			ArrayList list = bean.selectViewQna(box,"VIEW");

			request.setAttribute("list", list);

			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_CmuDirect_R.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/community/zu_CmuDirect_R.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performViewPage()\r\n" + ex.getMessage());
		}
	}

}

