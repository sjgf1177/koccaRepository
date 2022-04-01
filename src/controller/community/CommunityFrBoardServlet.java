//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ �޴����� ����
//  2. ���α׷��� : HomePageBoardServlet.java
//  3. ��      ��: Ŀ�´�Ƽ�� �޴� �������� �����Ѵ�
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

import com.credu.community.CommunityFrBoardBean;
import com.credu.community.CommunityMsMenuBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
public class CommunityFrBoardServlet extends javax.servlet.http.HttpServlet {

	/**
	 * DoGet
	 * Pass get requests through to PerformTask
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}
	@Override
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter      out          = null;
		//        MultipartRequest multi        = null;
		RequestBox       box          = null;
		String           v_process    = "";
		//        int              fileupstatus = 0;

		try {
			response.setContentType("text/html;charset=euc-kr");
			//            String path = request.getServletPath();

			out       = response.getWriter();
			box       = RequestManager.getBox(request);
			v_process = box.getStringDefault("p_process","selectlist");

			if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

			// �α� check ��ƾ VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

			/////////////////////////////////////////////////////////////////////////////
			if(v_process.equals("selectlist")) {                      //  ����Ʈ �������� �̵��Ҷ�
				this.performListPage(request, response, box, out);
			} else if(v_process.equals("insertPage")) {               //  ��� �������� �̵��Ҷ�
				this.performInsertPage(request, response, box, out) ;
			} else if(v_process.equals("insertData")) {               //  �űԵ��
				this.performInsertData(request, response, box, out);
			} else if(v_process.equals("viewPage")) {                 //  ����
				this.performViewPage(request, response, box, out);
			} else if(v_process.equals("insertMemoData")) {           //  ��۵��
				this.performInsertMemoData(request, response, box, out);
			} else if(v_process.equals("deleteMemoData")) {           //  ��ۻ���
				this.performDeleteMemoData(request, response, box, out);
			} else if(v_process.equals("deleteData")) {               //  ��ۻ���
				this.performDeleteData(request, response, box, out);
			} else if(v_process.equals("updatePage")) {               //  ���� �������� �̵��Ҷ�
				this.performUpdatePage(request, response, box, out);
			} else if(v_process.equals("updateData")) {               //  ����
				this.performUpdateData(request, response, box, out);
			} else if(v_process.equals("replyPage")) {                //  �亯��� �������� �̵��Ҷ�
				this.performReplyPage(request, response, box, out);
			} else if(v_process.equals("replyData")) {                //  �亯���
				this.performReplyData(request, response, box, out);

			} else if(v_process.equals("totalsearchPage")) {                //  ���հ˻� �������� �̵��Ҷ�
				this.performTotalSearchPage(request, response, box, out);

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
	@SuppressWarnings("unchecked")
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);

			String v_url = "";
			System.out.println("box.getString(\"p_brd_fg\") : "+ box.getString("p_brd_fg"));
			//�Խ�������
			CommunityFrBoardBean bean = new CommunityFrBoardBean();
			if("3".equals(box.getString("p_brd_fg"))){
				ArrayList list = bean.selectAlbumListBrd(box);
				request.setAttribute("list", list);
				v_url = "/learn/user/community/zu_FrBrdPhoto_L.jsp";
			} else {
				ArrayList list = bean.selectListBrd(box);
				request.setAttribute("list", list);
				v_url = "/learn/user/community/zu_FrBrd_L.jsp";
			}
			System.out.println("box.getString(\"p_brd_fg\")2 : "+ box.getString("p_brd_fg"));
			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to "+v_url);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}


	/**
    ���հ˻� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	@SuppressWarnings("unchecked")
	public void performTotalSearchPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);

			//�Խ�������
			CommunityFrBoardBean bean = new CommunityFrBoardBean();
			ArrayList list = bean.selectRoomTotalListBrd(box);
			request.setAttribute("list", list);

			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrTotalSearch_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrTotalSearch_L.jsp");
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

			CommunityFrBoardBean bean = new CommunityFrBoardBean();
			DataBox selectView = bean.selectViewBrd(box,"VIEW");

			request.setAttribute("selectView", selectView);

			String v_url = "";

			//�Խ�������
			if("3".equals(box.getString("p_brd_fg"))){
				v_url = "/learn/user/community/zu_FrBrdPhoto_R.jsp";
			} else {
				v_url = "/learn/user/community/zu_FrBrd_R.jsp";
			}

			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to "+v_url);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performViewPage()\r\n" + ex.getMessage());
		}
	}

	/**
    ��� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	@SuppressWarnings("unchecked")
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
			CommunityMsMenuBean beanFileCnt = new CommunityMsMenuBean();
			String    v_filecnt         = beanFileCnt.getSingleColumn(box,box.getString("p_cmuno"),box.getString("p_menuno"),"filecnt");

			box.put("fielcnt", v_filecnt.equals("") ? 0 : Integer.parseInt(v_filecnt));

			String v_url = "";

			//�Խ�������
			if("3".equals(box.getString("p_brd_fg"))){

				v_url = "/learn/user/community/zu_FrBrdPhoto_I.jsp";
			} else {
				v_url = "/learn/user/community/zu_FrBrd_I.jsp";
			}

			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to "+v_url);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}


	/**
    �亯��� �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performReplyPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
			CommunityMsMenuBean beanFileCnt = new CommunityMsMenuBean();
			String    v_filecnt         = beanFileCnt.getSingleColumn(box,box.getString("p_cmuno"),box.getString("p_menuno"),"filecnt");
			request.setAttribute("fielcnt", v_filecnt);

			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_FrBrd_A.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/community/zu_FrBrd_A.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}

	/**
    ������������ �������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	@SuppressWarnings("unchecked")
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			CommunityMsMenuBean beanFileCnt = new CommunityMsMenuBean();
			String    v_filecnt         = beanFileCnt.getSingleColumn(box,box.getString("p_cmuno"),box.getString("p_menuno"),"filecnt");

			box.put("fielcnt", v_filecnt.equals("") ? 0 : Integer.parseInt(v_filecnt));

			CommunityFrBoardBean bean = new CommunityFrBoardBean();
			DataBox selectView = bean.selectViewBrd(box,"UPDATE");

			request.setAttribute("selectView", selectView);

			String v_url = "";

			//�Խ�������
			if("3".equals(box.getString("p_brd_fg"))){
				v_url = "/learn/user/community/zu_FrBrdPhoto_U.jsp";
			} else {
				v_url = "/learn/user/community/zu_FrBrd_U.jsp";
			}

			ServletContext    sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to "+v_url);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}

	/**
    �űԵ�� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */

	@SuppressWarnings("unchecked")
	public void performInsertData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			CommunityFrBoardBean bean = new CommunityFrBoardBean();

			int isOk = bean.insertBrd(box);

			String v_msg = "";
			String v_url = "/servlet/controller.community.CommunityFrBoardServlet";
			box.put("p_process", "selectlist");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on CommunityFrBoardServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertData()\r\n" + ex.getMessage());
		}
	}

	/**
    �亯��� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */

	@SuppressWarnings("unchecked")
	public void performReplyData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			CommunityFrBoardBean bean = new CommunityFrBoardBean();

			int isOk = bean.replyBrd(box);

			String v_msg = "";
			String v_url = "/servlet/controller.community.CommunityFrBoardServlet";
			box.put("p_process", "selectlist");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on CommunityFrBoardServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertData()\r\n" + ex.getMessage());
		}
	}

	/**
    ���� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */

	@SuppressWarnings("unchecked")
	public void performUpdateData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			CommunityFrBoardBean bean = new CommunityFrBoardBean();

			int isOk = bean.updateBrd(box);

			String v_msg = "";
			String v_url = "/servlet/controller.community.CommunityFrBoardServlet";
			box.put("p_process", "selectlist");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "update.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "update.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on CommunityFrBoardServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertData()\r\n" + ex.getMessage());
		}
	}
	/**
    ��۵�� �Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */

	@SuppressWarnings("unchecked")
	public void performInsertMemoData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			CommunityFrBoardBean bean = new CommunityFrBoardBean();

			int isOk = bean.insertBrdMemo(box);

			String v_msg = "";
			String v_url = "/servlet/controller.community.CommunityFrBoardServlet";
			box.put("p_process", "viewPage");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on CommunityFrBoardServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertData()\r\n" + ex.getMessage());
		}
	}


	/**
    ��� ������ ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */

	@SuppressWarnings("unchecked")
	public void performDeleteMemoData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			CommunityFrBoardBean bean = new CommunityFrBoardBean();

			int isOk = bean.deleteBrdMemo(box);

			String v_msg = "";
			String v_url = "/servlet/controller.community.CommunityFrBoardServlet";
			box.put("p_process", "viewPage");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "delete.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "delete.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on CommunityFrBoardServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertData()\r\n" + ex.getMessage());
		}
	}


	/**
    �� ������ ��
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */

	@SuppressWarnings("unchecked")
	public void performDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			CommunityFrBoardBean bean = new CommunityFrBoardBean();

			int isOk = bean.deleteBrdData(box);

			String v_msg = "";
			String v_url = "/servlet/controller.community.CommunityFrBoardServlet";
			box.put("p_process", "selectlist");

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "delete.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "delete.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on CommunityFrBoardServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertData()\r\n" + ex.getMessage());
		}
	}

}

