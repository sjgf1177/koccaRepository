//**********************************************************
//  1. 제      목: 과정공지를 제어하는 서블릿
//  2. 프로그램명 : SubjGongStudyServlet.java
//  3. 개      요: 과정공지 페이지을 제어한다
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2005. 1. 31
//  7. 수      정:
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
			// 로긴 check 루틴 VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

			if(v_process.equals("selectView")) {                      // 상세보기할때
				this.performSelectView(request, response, box, out);
			}
			else if(v_process.equals("insertPage")) {            // 등록페이지로 이동할때
				this.performInsertPage(request, response, box, out);
			}
			else if(v_process.equals("insert")) {                // 등록할때
				this.performInsert(request, response, box, out);
			}
			else if(v_process.equals("updatePage")) {            // 수정페이지로 이동할때
				this.performUpdatePage(request, response, box, out);
			}
			else if(v_process.equals("update")) {                // 수정하여 저장할때
				this.performUpdate(request, response, box, out);
			}
			else if(v_process.equals("delete")) {                // 삭제할때
				this.performDelete(request, response, box, out);
			}
			else if(v_process.equals("select")) {                // 해당과정에 대한 조회할때(첫 공지화면)
				this.performSelectList(request, response, box, out);
			}
			else if(v_process.equals("OffselectView")) {         // 상세보기할때(오프라인)
				this.performOffSelectView(request, response, box, out);
			}
			else if(v_process.equals("Offselect")) {             // 해당과정에 대한 조회할때(오프라인)
				this.performOffSelectList(request, response, box, out);
			}
			else if(v_process.equals("NoticeGoyong")) {			 // 고용보험 안내정보 조회
				this.performNoticeGoyong(request, response, box, out);
			}
			else if(v_process.equals("yeslist")) {             // yeslearn
				this.performYesSelectList(request, response, box, out);
			}
			else if(v_process.equals("yesview")) {			 // yeslearn
				this.performYesSelectView(request, response, box, out);
			}
			else if(v_process.equals("gongList")) {			 // 공지사항 2009.12.03
				this.performSubjGongList(request, response, box, out);
			}


		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}


	/**
    상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
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
    등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

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
    등록할때
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
    수정페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

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
    수정하여 저장할때
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
			//      수정 후 해당 리스트 페이지로 돌아가기 위해

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
    삭제할때
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
    해당과정에 대한 리스트
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

			// 권장진도율
			String promotion  = bean.getPromotion(box);
			request.setAttribute("promotion", promotion);

			// 자기진도율
			String progress = bean.getProgress(box);
			request.setAttribute("progress", progress);

			// 공지
			ArrayList list = bean.selectListGong(box);
			request.setAttribute("selectList", list);

			// 전체공지
			ArrayList listall = bean.selectListGongAll_H(box);
			request.setAttribute("selectListAll", listall);

			//수료기준
            ArrayList list2 = bean.selectCompleteBasic(box);
			request.setAttribute("selectCompleteBasic", list2);

			// 토론
			ToronBean ToronBean = new ToronBean();
			ArrayList ToronList = ToronBean.selectTopicList(box);
			request.setAttribute("TopicList", ToronList);
			
			// 060118 진도추가
			EduStartBean  bean1 = new EduStartBean();
			ArrayList data1 = null;

			String contenttype = box.getSession("s_contenttype");
			String v_ispreview = "";
			v_ispreview = box.getString("p_ispreview");

			if (contenttype.equals("N")){					//Normal MasterForm
				data1= bean1.SelectEduList(box);						//진도데이터
			}
			else if (contenttype.equals("M")){			//Normal MasterForm(Old)
				data1= bean1.SelectEduList(box);							//진도데이터
			}
			else if (contenttype.equals("O") || contenttype.equals("S")){          //OBC,SCORM MasterForm
				if (v_ispreview.equals("Y")) {
					data1= bean1.SelectEduListOBCPreview(box);					//진도데이터
				} else {
					data1= bean1.SelectEduListOBC(box);                        //진도데이터
				}
			}
			request.setAttribute("EduList", data1);
			
			
			

			// 상시학습여부
			String isalways  = bean.getIsalways(box);
			request.setAttribute("isalways", isalways);
			
			//정규학습창
			String returnUrl = "/learn/user/study/zu_SubjGongStudy_L.jsp";
			if("Y".equals(isalways)){
				
				
				// 학습시작일
				String edustart  = bean.getStartEdu(box);
				request.setAttribute("edustart", edustart);
				

				//설문정보
	            SulmunSubjUserBean sbean = new SulmunSubjUserBean();
	            ArrayList slist = sbean.SelectUserList(box);
	            request.setAttribute("SulmunSubjUserList", slist);
	            
	            //과정설문
				SulmunRegistUserBean sbean1 = new SulmunRegistUserBean();
	            ArrayList slist1 = sbean1.SelectUserList(box);
	            request.setAttribute("SulmunContentsUserList", slist1);
	            
	            //일반설문
	            String v_subj = box.getString("p_subj");
				box.put("s_subj", v_subj); 
	            box.put("p_subj","REGIST");        
	            int contentsdata = sbean1.getUserData(box);      
	            box.put("p_contentsdata",String.valueOf(contentsdata)); 
	            box.put("p_subj",v_subj);
				
	            //상시학습창
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
    상세보기(오프)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performOffSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
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
    해당과정에 대한 리스트(오프)
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
    고용보험에 대한 정보 조회
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
    예스런 공지 리스트
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

			ArrayList list = bean.selectListGong(box);       // 공지
			request.setAttribute("selectList", list);

			ArrayList listall = bean.selectListGongAll_H(box); // 전체공지
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
    예스런 상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performYesSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
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
	공지사항
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	 */
	public void performSubjGongList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
	throws Exception {
		try{

			// 과정별 메뉴 접속 정보 추가
			box.put("p_menu","05");
			StudyCountBean scBean = new StudyCountBean();
			scBean.writeLog(box);

			request.setAttribute("requestbox", box);
			SubjGongAdminBean bean = new SubjGongAdminBean();

			ArrayList list = bean.selectListPageGong(box);       // 공지
			request.setAttribute("selectList", list);

			ArrayList listall = bean.selectListGongAll_H(box); // 전체공지
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

