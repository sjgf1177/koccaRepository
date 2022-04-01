//**********************************************************
//  1. ��      ��: ���������� �����ϴ� ����
//  2. ���α׷��� : SubjGongStudyServlet.java
//  3. ��      ��: �������� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2005. 1. 31
//  7. ��      ��:
//**********************************************************

package controller.study;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.contents.EduStartBean;
import com.credu.course.SubjGongAdminBean;
import com.credu.course.SubjGongData;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunRegistUserBean;
import com.credu.research.SulmunSubjUserBean;
import com.credu.study.ToronBean;
import com.credu.system.AdminUtil;
import com.credu.system.StudyCountBean;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.study.SubjGongStudyServlet")
public class SubjGongStudyServlet extends javax.servlet.http.HttpServlet {

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

			System.out.println("==================== v_process123 : " + v_process);
			// �α� check ��ƾ VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

			if(v_process.equals("selectView")) {                      // �󼼺����Ҷ�
				this.performSelectView(request, response, box, out);
			}
			else if(v_process.equals("insertPage")) {            // ����������� �̵��Ҷ�
				this.performInsertPage(request, response, box, out);
			}
			else if(v_process.equals("insert")) {                // ����Ҷ�
				this.performInsert(request, response, box, out);
			}
			else if(v_process.equals("updatePage")) {            // ������������ �̵��Ҷ�
				this.performUpdatePage(request, response, box, out);
			}
			else if(v_process.equals("update")) {                // �����Ͽ� �����Ҷ�
				this.performUpdate(request, response, box, out);
			}
			else if(v_process.equals("delete")) {                // �����Ҷ�
				this.performDelete(request, response, box, out);
			}
			else if(v_process.equals("select")) {                // �ش������ ���� ��ȸ�Ҷ�(ù ����ȭ��)
				this.performSelectList(request, response, box, out);
			}
			else if(v_process.equals("OffselectView")) {         // �󼼺����Ҷ�(��������)
				this.performOffSelectView(request, response, box, out);
			}
			else if(v_process.equals("Offselect")) {             // �ش������ ���� ��ȸ�Ҷ�(��������)
				this.performOffSelectList(request, response, box, out);
			}
			else if(v_process.equals("NoticeGoyong")) {			 // ��뺸�� �ȳ����� ��ȸ
				this.performNoticeGoyong(request, response, box, out);
			}
			else if(v_process.equals("yeslist")) {             // yeslearn
				this.performYesSelectList(request, response, box, out);
			}
			else if(v_process.equals("yesview")) {			 // yeslearn
				this.performYesSelectView(request, response, box, out);
			}
			else if(v_process.equals("gongList")) {			 // �������� 2009.12.03
				this.performSubjGongList(request, response, box, out);
			}


		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
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
	public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
			SubjGongAdminBean bean = new SubjGongAdminBean();

			//SubjGongData data = bean.selectViewGong(box);
			DataBox dbox = bean.selectViewGongNew(box);
			request.setAttribute("selectGong", dbox);

			ServletContext sc    = getServletContext();
			//RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjGongStudy_R.jsp");
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjGong_R.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/study/zu_SubjGong_R.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}


	/**
    ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			SubjGongAdminBean bean = new SubjGongAdminBean();
			SubjGongData data = bean.selectGongSample(box);
			request.setAttribute("selectGong", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjGongStudy_I.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/study/zu_SubjGongStudy_I.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertPage()\r\n" + ex.getMessage());
		}
	}

	/**
    ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			SubjGongAdminBean bean = new SubjGongAdminBean();
			int isOk = bean.insertGong(box);

			String v_msg = "";
			String v_url = "/servlet/controller.course.SubjGongStudyServlet";
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

			Log.info.println(this, box, v_msg + " on SubjGongStudyServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsert()\r\n" + ex.getMessage());
		}
	}

	/**
    ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			SubjGongAdminBean bean = new SubjGongAdminBean();
			SubjGongData data = bean.selectViewGong(box);
			request.setAttribute("selectGong", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjGongStudy_U.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/study/zu_SubjGongStudy_U.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}
	}

	/**
    �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			SubjGongAdminBean bean = new SubjGongAdminBean();
			int isOk = bean.updateGong(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.course.SubjGongStudyServlet";
			box.put("p_process", "select");
			//      ���� �� �ش� ����Ʈ �������� ���ư��� ����

			AlertManager alert = new AlertManager();

			if(isOk > 0) {
				v_msg = "update.ok";
				alert.alertOkMessage(out, v_msg, v_url , box);
			}
			else {
				v_msg = "update.fail";
				alert.alertFailMessage(out, v_msg);
			}

			Log.info.println(this, box, v_msg + " on SubjGongStudyServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdate()\r\n" + ex.getMessage());
		}
	}

	/**
    �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			SubjGongAdminBean bean = new SubjGongAdminBean();
			int isOk = bean.deleteGong(box);

			String v_msg = "";
			String v_url  = "/servlet/controller.course.SubjGongStudyServlet";
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

			Log.info.println(this, box, v_msg + " on SubjGongStudyServlet");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performDelete()\r\n" + ex.getMessage());
		}
	}

	/**
    �ش������ ���� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {

			request.setAttribute("requestbox", box);
			SubjGongAdminBean bean = new SubjGongAdminBean();

			// ����������
			String promotion  = bean.getPromotion(box);
			request.setAttribute("promotion", promotion);

			// �ڱ�������
			String progress = bean.getProgress(box);
			request.setAttribute("progress", progress);

			// ����
			ArrayList list = bean.selectListGong(box);
			request.setAttribute("selectList", list);

			// ��ü����
			ArrayList listall = bean.selectListGongAll_H(box);
			request.setAttribute("selectListAll", listall);

			//�������
            ArrayList list2 = bean.selectCompleteBasic(box);
			request.setAttribute("selectCompleteBasic", list2);

			// ���
			ToronBean ToronBean = new ToronBean();
			ArrayList ToronList = ToronBean.selectTopicList(box);
			request.setAttribute("TopicList", ToronList);
			
			// 060118 �����߰�
			EduStartBean  bean1 = new EduStartBean();
			ArrayList data1 = null;

			String contenttype = box.getSession("s_contenttype");
			String v_ispreview = "";
			v_ispreview = box.getString("p_ispreview");

			if (contenttype.equals("N")){					//Normal MasterForm
				data1= bean1.SelectEduList(box);						//����������
			}
			else if (contenttype.equals("M")){			//Normal MasterForm(Old)
				data1= bean1.SelectEduList(box);							//����������
			}
			else if (contenttype.equals("O") || contenttype.equals("S")){          //OBC,SCORM MasterForm
				if (v_ispreview.equals("Y")) {
					data1= bean1.SelectEduListOBCPreview(box);					//����������
				} else {
					data1= bean1.SelectEduListOBC(box);                        //����������
				}
			}
			request.setAttribute("EduList", data1);
			
			
			

			// ����н�����
			String isalways  = bean.getIsalways(box);
			request.setAttribute("isalways", isalways);
			
			//�����н�â
			String returnUrl = "/learn/user/study/zu_SubjGongStudy_L.jsp";
			if("Y".equals(isalways)){
				
				
				// �н�������
				String edustart  = bean.getStartEdu(box);
				request.setAttribute("edustart", edustart);
				

				//��������
	            SulmunSubjUserBean sbean = new SulmunSubjUserBean();
	            ArrayList slist = sbean.SelectUserList(box);
	            request.setAttribute("SulmunSubjUserList", slist);
	            
	            //��������
				SulmunRegistUserBean sbean1 = new SulmunRegistUserBean();
	            ArrayList slist1 = sbean1.SelectUserList(box);
	            request.setAttribute("SulmunContentsUserList", slist1);
	            
	            //�Ϲݼ���
	            String v_subj = box.getString("p_subj");
				box.put("s_subj", v_subj); 
	            box.put("p_subj","REGIST");        
	            int contentsdata = sbean1.getUserData(box);      
	            box.put("p_contentsdata",String.valueOf(contentsdata)); 
	            box.put("p_subj",v_subj);
				
	            //����н�â
	            returnUrl = "/learn/user/study/zu_SubjGongStudyAll_L.jsp";
			}


			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(returnUrl);
			rd.forward(request, response);

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectList()\r\n" + ex.getMessage());
		}
	}


	/**
    �󼼺���(����)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performOffSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
			SubjGongAdminBean bean = new SubjGongAdminBean();

			SubjGongData data = bean.selectViewGong(box);
			request.setAttribute("selectGong", data);

			ServletContext sc    = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjGongStudyOff_R.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/study/zu_SubjGongStudyOff_R.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performOffSelectView()\r\n" + ex.getMessage());
		}
	}

	/**
    �ش������ ���� ����Ʈ(����)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performOffSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 	{
		try {
			request.setAttribute("requestbox", box);
			SubjGongAdminBean bean = new SubjGongAdminBean();

			ArrayList list = bean.selectListGong(box);
			request.setAttribute("selectList", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjGongStudyOff_L.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/study/zu_SubjGongStudyOff_L.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performOffSelectList()\r\n" + ex.getMessage());
		}
	}

	/**
    ��뺸�迡 ���� ���� ��ȸ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performNoticeGoyong(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 	{
		try {
			request.setAttribute("requestbox", box);

			SubjGongAdminBean bean = new SubjGongAdminBean();

			SubjGongData data = bean.selectViewGoyong(box);
			request.setAttribute("selectGoyong", data);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_GoyongInfo.jsp");
			rd.forward(request, response);

			Log.info.println(this, box, "Dispatch to /learn/user/study/zu_GoyongInfo.jsp");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performNoticeGoyong()\r\n" + ex.getMessage());
		}
	}

	/**
    ������ ���� ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performYesSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			SubjGongAdminBean bean = new SubjGongAdminBean();

			ArrayList list = bean.selectListGong(box);       // ����
			request.setAttribute("selectList", list);

			ArrayList listall = bean.selectListGongAll_H(box); // ��ü����
			request.setAttribute("selectListAll", listall);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjGongStudyPop_L.jsp");
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performYesSelectList()\r\n" + ex.getMessage());
		}
	}

	/**
    ������ �󼼺���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performYesSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
			SubjGongAdminBean bean = new SubjGongAdminBean();

			SubjGongData data = bean.selectViewGong(box);
			request.setAttribute("selectGong", data);

			ServletContext sc    = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjGongStudyPop_R.jsp");
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}

	/**
	��������
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performSubjGongList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try{

			// ������ �޴� ���� ���� �߰�
			box.put("p_menu","05");
			StudyCountBean scBean = new StudyCountBean();
			scBean.writeLog(box);

			request.setAttribute("requestbox", box);
			SubjGongAdminBean bean = new SubjGongAdminBean();

			ArrayList list = bean.selectListPageGong(box);       // ����
			request.setAttribute("selectList", list);

			ArrayList listall = bean.selectListGongAll_H(box); // ��ü����
			request.setAttribute("selectListAll", listall);


			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_SubjGong_L.jsp");
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSubjGongList()\r\n" + ex.getMessage());
		}
	}

}

