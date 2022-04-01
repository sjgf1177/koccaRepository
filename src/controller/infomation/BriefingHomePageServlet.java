package controller.infomation;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.infomation.EduReviewHomePageBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.infomation.BriefingHomePageServlet")
public class BriefingHomePageServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
            
            if (box.getSession("tem_grcode") == "") {        		
	             box.setSession("tem_grcode","N000001");
	     	}	   

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            
            // �α� check ��ƾ VER 0.2 - 2003.09.9
//			if (!AdminUtil.getInstance().checkLogin(out, box)) {
//				return; 
//			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           /* 
           v_canRead   = BulletinManager.isAuthority(box,box.getString("p_canRead"));
           v_canAppend = BulletinManager.isAuthority(box,box.getString("p_canAppend"));
           v_canModify = BulletinManager.isAuthority(box,box.getString("p_canModify"));
           v_canDelete = BulletinManager.isAuthority(box,box.getString("p_canDelete"));
           v_canReply  = BulletinManager.isAuthority(box,box.getString("p_canReply"));
		    */	
            if(v_process.equals("selectList")) {     			 		     //  ȫ�� ��� ����Ʈ
            	 this.performSelectList(request, response, box, out);
            } else if(v_process.equals("selectView")) {                      //  ȫ�� ��� ��
            	 this.performSelectView(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
    
    
    /**
    //  �󼼺���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
             
             String v_url = "";

			EduReviewHomePageBean bean = new EduReviewHomePageBean();
             DataBox dbox = bean.selectView(box);
             request.setAttribute("selectView", dbox);

             //v_url = "/learn/user/2012/portal/information/zu_PrArticles_R.jsp";
             v_url = "/learn/user/2013/portal/information/zu_PrArticles_R.jsp";
             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher(v_url);
             rd.forward(request, response);

             Log.info.println(this, box, "Dispatch to "+v_url);

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }


    /**
           ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�
            
            String v_url = "";

            EduReviewHomePageBean bean = new EduReviewHomePageBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));

            if (tabseq == 0) {
	          	/*------- �Խ��� �з��� ���� �κ� ���� -----*/
				box.put("p_type", "PR");
				box.put("p_grcode", "0000000");
				box.put("p_comp", "0000000000");
				box.put("p_subj", "0000000000");
				box.put("p_year", "0000");
				box.put("p_subjseq", "0000");
				/*----------------------------------------*/

            	tabseq = bean.selectTableseq(box);
            	
                if (tabseq == 0) {
				  String msg = "�Խ��������� �����ϴ�.";
				  AlertManager.historyBack(out, msg);
                }
                
            	box.put("p_tabseq", String.valueOf(tabseq));
            }

            //�Ϲ� ����Ʈ
            ArrayList List = bean.selectList(box);
            request.setAttribute("selectList", List);

            //v_url = "/learn/user/2012/portal/information/zu_PrArticles_L.jsp";
            v_url = "/learn/user/2013/portal/information/zu_PrArticles_L.jsp";
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to "+v_url);
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

            alert.alertFailMessage(out, "�� ���μ����� ������ ������ �����ϴ�.");
            //  Log.sys.println();

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
    
}

