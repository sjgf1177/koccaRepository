//**********************************************************
//  1. 제      목:  제어하는 서블릿
//  2. 프로그램명 : ____Servlet.java
//  3. 개      요:  제어 프로그램
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수     정1:
//**********************************************************
package controller.off;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.off.OffBatchRecordBean;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffBatchRecordsAdminServlet")
public class OffBatchRecordsAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

	/**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}

	/**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;
		RequestBox box = null;
		String v_process = "";

		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);
			//v_process = box.getString("p_process");// 원본
			v_process = StringManager.convertHtmlchars(box.getString("p_process")) ;			

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}

			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("OffBatchRecordsAdminServlet", v_process, out, box)) {
				return;
			}
			box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////

			if(v_process.equals("FileToDBPage")) {                     // 입력 페이지
				this.performFileToDBPage(request, response, box, out);
			} else if (v_process.equals("insertFileToDB")) {                //in case of 입과FileToDB 입력
				this.performInsertFileToDB(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
    입력페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
	 */
	public void performFileToDBPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			String v_return_url = "/learn/admin/off/za_off_Batch_Records.jsp";
			if (box.getBoolean("isExcel")) {
				box.sync("s_subjterm");
				OffBatchRecordBean bean = new OffBatchRecordBean();
				request.setAttribute("resultList", bean.getReportScoreList(box));
				v_return_url = "/learn/admin/off/za_excel.jsp";//필수
				if (box.getBoolean("isTerm")) {//학기별(과목별)
					box.put("title", "오프라인과정성적 - 학기별(과목별) : " +box.get("s_year") +"년도 "+box.get("s_subjterm") +"학기");//엑셀 제목
					box.put("tname", "ID|이름|과정명|과목명|과정코드|차수|학기|과목코드|점수");//컬럼명
					box.put("tcode", "userid|name|subjnm|lecturenm|subj|subjseq|TERM|lecture|score");//데이터이름
					box.put("bgcolumn", "score");
				}
				else {//과정별
					box.put("title", "오프라인과정성적 - 과정별");//엑셀 제목
					box.put("tname", "ID|과정명|과정코드|차수|수료여부(1:수료)|출석률점수(진도율)|중간평가|형성평가|최종평가|과제|참여도|기타");//컬럼명
					box.put("tcode", "userid|subjnm|subj|subjseq|TERM|TSTEP|MTEST|HTEST|FTEST|REPORT|ETC1|ETC2");//데이터이름
					box.put("bgcolumn", "TSTEP|MTEST|HTEST|FTEST|REPORT|ETC1|ETC2");
				}
				box.put("resultListName", "resultList");//결과 목록
			}
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
		}
	}
	public void performInsertFileToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
		try {
			request.setAttribute("requestbox", box);
			String v_return_url = "/servlet/controller.off.OffBatchRecordsAdminServlet?p_process=FileToDBPage";

			OffBatchRecordBean bean = new OffBatchRecordBean();
			String result = bean.insert(box);
			AlertManager alert = new AlertManager();
			String v_msg = null;

			if(result==null) {
				request.setAttribute("requestbox", box);
				v_msg = "insert.ok";
				alert.alertOkMessage(out, v_msg, v_return_url , box);
			}
			else {
				v_msg = "insert.fail";
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
		}
	}

}
