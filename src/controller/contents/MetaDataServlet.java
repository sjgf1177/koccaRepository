//**********************************************************
//  1. 제      목: MetaData Operation Servlet
//  2. 프로그램명: MetaDataServlet.java
//  3. 개      요: SCO관리에 관련된 Servlet
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.29
//  7. 수      정: 박미복 2004. 11.29
//**********************************************************

package controller.contents;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.contents.MetaDataMainBean;
import com.credu.contents.MetaDataMainData;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.contents.MetaDataServlet")
public class MetaDataServlet extends HttpServlet implements Serializable {

	/**
	Pass get requests through to PerformTask
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	*/
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		this.doPost(request, response);
	}

	/**
	doPost
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		PrintWriter out = null;
		RequestBox  box = null;
		String v_process = "";

		try {
			response.setContentType("text/html;charset=euc-kr");
			out = response.getWriter();
			box = RequestManager.getBox(request);
			v_process = box.getStringDefault("p_process","listPage");
			//System.out.println("=====================================================v_process : " + v_process );

			if(ErrorManager.isErrorMessageView()) {
				box.put("errorout", out);
			}
			///////////////////////////////////////////////////////////////////
			// 권한 check 루틴 VER 0.2 - 2003.08.10
			if (!AdminUtil.getInstance().checkRWRight("MetaDataServlet", v_process, out, box)) {
				return; 
			}
			///////////////////////////////////////////////////////////////////
			if (v_process.equals("listPage")) {      					// MetaData List
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("searchList")) {    		  	// MetaData 검색화면
				this.performListPage(request, response, box, out);
			} else if (v_process.equals("insertSave")) {      			//MetaData 등록저장
				this.performInsertSave(request, response, box, out);
			} else if (v_process.equals("updateSave")) {     	 		//MetaData 수정저장
				this.performUpdateSave(request, response, box, out);
			} else if (v_process.equals("insertPage")) {    		  	// MetaData 등록화면
				//this.performInsertPage(request, response, box, out);
			} else if (v_process.equals("updatePage")) {      			//MetaData 수정화면
				//this.performUpdatePage(request, response, box, out);
			} else if (v_process.equals("viewPage")) {      			//MetaData 조회화면
				//this.performUpdatePage(request, response, box, out);
			} else if (v_process.equals("deleteSave")) {      			//MetaData 삭제
				//this.performDeleteSave(request, response, box, out);
			}
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}

	/**
	METADATA 등록
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsertSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try{
			String v_url = "/learn/admin/contents/za_MetadataFrame.jsp";
			String v_metalocation = box.getString("p_metalocation");
			System.out.println("=========================================================performInsertSave box");
			ErrorManager.systemOutPrintln(box);
			System.out.println("=========================================================performInsertSave box");

			MetaDataMainBean bean = new MetaDataMainBean();
			boolean exist = bean.getMetaDataByOid(box);
            

			System.out.print("ddddddddddddddddddddddddddd");
			//Metadata 보기 처음 클릭 시, XML 파싱
			if ( !exist ) {
				if ( !"".equals(v_metalocation) ){
					System.out.print("aaaaaaaaaaaaaaaaaaaaaa");
					bean.readXMLwriteDB(box, out);
					System.out.print("bbbbbbbbbbbbbbbbbbbbbbbbb");
				}
				else{
					bean.insertEmptyObject(box);		// metalocation 값이 없을 때(XML없을 때) Empty MetaDataMain을 만든다.
				}
			}
            System.out.println("=========================================================aaaaaaaaaaaaaa");

			MetaDataMainData data = bean.getMetaDataMainDataByOid(box);
			request.setAttribute("metadata_idx", ""+data.getMetadata_idx());

			System.out.println("=========================================================bbbbbbbbb");

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url); 
			rd.forward(request, response);

			System.out.println("=========================================================ccccccccccccc");
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performInsertSave()\r\n" + ex.getMessage());
		}
	}

	/**
	METADATA PAGE
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try{
			String v_url = "/learn/admin/contents/za_Metadata_U.jsp";
/*			//System.out.println("=========================================================performListPage box");
			ErrorManager.systemOutPrintln(box);
			//System.out.println("=========================================================performListPage box");
*/
			MetaDataMainBean bean = new MetaDataMainBean();
			ArrayList metaDataAll = bean.getMetaDataAllByOid(box, out);

			request.setAttribute("MetaDataAll", metaDataAll);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(v_url);
			rd.forward(request, response);
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performListPage()\r\n" + ex.getMessage());
		}
	}

	/**
	METADATA 수정
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performUpdateSave(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
		throws Exception {
		try{
			String v_url = "/servlet/controller.contents.MetaDataServlet";
			String v_oid = box.getString("p_oid");
			String v_metadata_idx = box.getString("p_metadata_idx");
/*			//System.out.println("=========================================================performUpdateSave box");
			ErrorManager.systemOutPrintln(box);
			//System.out.println("=========================================================performUpdateSave box");
*/
			MetaDataMainBean bean = new MetaDataMainBean();
			String results = bean.updateObject(box, out);

			String v_msg ="";
			box.put("p_process", "listPage");
			box.put("p_oid", v_oid);
			box.put("p_metadata_idx", v_metadata_idx);

			AlertManager alert = new AlertManager();
			if(results.equals("OK")) {
				v_msg ="update.ok";
				alert.alertOkMessage(out, v_msg, v_url, box);
			}else {
				v_msg = results;
				alert.alertFailMessage(out, v_msg);
			}
		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performUpdateSave()\r\n" + ex.getMessage());
		}
	}
}