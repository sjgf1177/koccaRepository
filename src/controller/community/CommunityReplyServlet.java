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
import com.credu.community.CommunityReplyBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("unchecked")
public class CommunityReplyServlet extends HttpServlet implements Serializable {

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
			
			// 로긴 check 루틴 VER 0.2 
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return; 
            }
			
			if(v_process.equals("selectListReply")) {                       //  댓글 리스트
	        	this.performSelectListReply(request, response, box, out);
	        } else if(v_process.equals("insertReply")) {                    //  댓글 추가
	        	this.performInsertReply(request, response, box, out);
	        } else if(v_process.equals("deleteReply")) {                    //  댓글 삭제
	        	this.performDeleteReply(request, response, box, out);
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
	public void performSelectListReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  
            
            CommunityReplyBean bean = new CommunityReplyBean();
            ArrayList selectList =	bean.selectList(box);
            
            request.setAttribute("selectList", selectList);


            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/include/cm_reply_list_ajax.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/include/cm_reply_list_ajax.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }	
    
    @SuppressWarnings("unchecked")
	public void performInsertReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
        	CommunityReplyBean bean = new CommunityReplyBean();
			int isOk = bean.insertReply(box);
			
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
    
    @SuppressWarnings("unchecked")
	public void performDeleteReply(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
        	CommunityReplyBean bean = new CommunityReplyBean();
			int isOk = bean.deleteReply(box);
			
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
