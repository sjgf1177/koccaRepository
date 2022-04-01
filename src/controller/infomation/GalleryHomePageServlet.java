package controller.infomation;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.infomation.GalleryHomePageBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.infomation.GalleryHomePageServlet")
public class GalleryHomePageServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out 		= null;
        RequestBox 	box 		= null;
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
//			if (!AdminUtil.getInstance().checkLogin(out, box)) {
//				return; 
//			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           v_canRead   = BulletinManager.isAuthority(box,box.getString("p_canRead"));
           v_canAppend = BulletinManager.isAuthority(box,box.getString("p_canAppend"));
           v_canModify = BulletinManager.isAuthority(box,box.getString("p_canModify"));
           v_canDelete = BulletinManager.isAuthority(box,box.getString("p_canDelete"));
           v_canReply  = BulletinManager.isAuthority(box,box.getString("p_canReply"));
			
            if(v_process.equals("selectList")) {     			 		     //  갤러리 리스트
            	if(v_canRead) this.performSelectList(request, response, box, out);
            } else if(v_process.equals("selectView")) {                      //  갤러리 상세
            	if(v_canRead) this.performSelectView(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
    
    
    /**
    //  상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

 			GalleryHomePageBean bean = new GalleryHomePageBean();
             
             DataBox dbox = bean.selectView(box);
             request.setAttribute("selectView", dbox);

             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/information/zu_Gallery_R.jsp");
             rd.forward(request, response);

             Log.info.println(this, box, "Dispatch to /learn/user/portal/information/zu_Gallery_R.jsp");

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }


    /**
          워크샵 리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            GalleryHomePageBean bean = new GalleryHomePageBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));

            if (tabseq == 0) {
	          	/*------- 게시판 분류에 대한 부분 세팅 -----*/
				box.put("p_type", "PG");
				box.put("p_grcode", "0000000");
				box.put("p_comp", "0000000000");
				box.put("p_subj", "0000000000");
				box.put("p_year", "0000");
				box.put("p_subjseq", "0000");
				/*----------------------------------------*/

            	tabseq = bean.selectTableseq(box);
            	
                if (tabseq == 0) {
				  String msg = "게시판정보가 없습니다.";
				  AlertManager.historyBack(out, msg);
                }
                
            	box.put("p_tabseq", String.valueOf(tabseq));
            }

            //일반 리스트
            ArrayList List = bean.selectList(box);
            request.setAttribute("selectList", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/information/zu_Gallery_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/portal/information/zu_Gallery_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
	public void errorPage(RequestBox box, PrintWriter out)
        throws Exception {
        try {
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "이 프로세스로 진행할 권한이 없습니다.");
            //  Log.sys.println();

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
    
}

