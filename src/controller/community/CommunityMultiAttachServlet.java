//**********************************************************
//1. 제      목: Tag관련 SERVLET


//2. 프로그램명: TagServlet
//3. 개      요:
//4. 환      경: JDK 1.6
//5. 버      젼: 0.1
//6. 작      성: swchoi 2009. 7. 10.
//7. 수      정:
//
//**********************************************************
package controller.community;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityIndexBean;
import com.credu.community.CommunityMsMangeBean;
import com.credu.community.CommunityMultiAttachBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@SuppressWarnings("unchecked")
public class CommunityMultiAttachServlet extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 8236546677697884692L;

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
			
			out = response.getWriter();
			box	= RequestManager.getBox(request);
			
			v_process = box.getString("p_process");
			
			if(v_process.equals("selectList")) {                       //  첨부파일 리스트
	        	this.performSelectList(request, response, box, out);
	        } else if(v_process.equals("updatePage")) {                    //  첨부파일 리스트(수정페이지)
	        	this.performUpdatePage(request, response, box, out);
	        } else if(v_process.equals("deleteAttachment")) {                    //  첨부 삭제
	        	this.performDelete(request, response, box, out);
	        }
			
			
		} catch(Exception ex) {
			System.out.println("AjaxServlet Error  "+ex);
			ErrorManager.getErrorStackTrace(ex, out);
		}
	}
	
    /**
     * 댓글 리스트
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            
            CommunityMultiAttachBean bean = new CommunityMultiAttachBean();
            DataBox fileBox =	bean.selectList(box);
            
            request.setAttribute("fileBox", fileBox);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/include/cm_MultiAttach_R_Ajax.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/include/cm_MultiAttach_R_Ajax.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }	
    
    /**
     * 댓글 리스트
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            System.out.println("p_maxFileCnt1 :"+request.getParameter("p_maxFileCnt"));
            CommunityMultiAttachBean bean = new CommunityMultiAttachBean();
            DataBox fileBox =	bean.selectList(box);
            
            request.setAttribute("fileBox", fileBox);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/include/cm_MultiAttach_U_Ajax.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/include/cm_MultiAttach_U_Ajax.jsp");
            
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }	
    
    @SuppressWarnings("unchecked")
	public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
        	CommunityMultiAttachBean bean = new CommunityMultiAttachBean();
			int isOk = bean.delete(box);
			
			if(isOk > 0 ) {
				out.println("{                   ");
				out.println("'result': [{        ");
				out.println("    'STATE': 'SUCC' ");
				out.println("    }]              ");
				out.println("}                   ");

			} else {
				out.println("{                   ");
				out.println("'result': [{        ");
				out.println("    'STATE': 'SUCC' ");
				out.println("    }]              ");
				out.println("}                   ");
			}
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsCommunityCloseData()\r\n" + ex.getMessage());
        }
    }

}
