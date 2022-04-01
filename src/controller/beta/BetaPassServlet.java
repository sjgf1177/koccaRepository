/**
*베타테스트시스템의 Pass찾기를 제어하는 서블릿
*<p>제목:BetaPassServlet.java</p>
*<p>설명:베타테스트시스템Pass찾기서블릿</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 박준현
*@version 1.0
*/
package controller.beta;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.BetaPassBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
@WebServlet("/servlet/controller.beta.BetaPassServlet")
public class BetaPassServlet extends javax.servlet.http.HttpServlet {
    
    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        int fileupstatus = 0;
        
        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");
            
            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            
			/*/ 로긴 check 루틴 VER 0.2 - 2003.09.9
			if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}*/
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           if(v_process.equals("pass")) {                                // 리스트
                this.performPassPage(request, response, box, out);
            }else if(v_process.equals("jumin")) {                                // 리스트
				System.out.println("주민번호검색을 시작합니다");
                this.performjuminSearch(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }

    /**
    비밀번호찾기 페이지이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
     public void performPassPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
        throws Exception {       
        try {             
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다      

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaSearch_PW.jsp");
            rd.forward(request, response);
            
           // Log.info.println(this, box, "Dispatch to /learn/user/homepage/za_Faq_I.jsp");
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
	/**
    사용자의 id와 주민번호체크 후 정상이면 메일발송
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performjuminSearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            BetaPassBean pass = new BetaPassBean();
            boolean isMailed = false;
			boolean isPwd = false;
			boolean isId = false;
			String str = "";
			String v_msg = "";
            
            AlertManager alert = new AlertManager();
           
            isId = pass.selectid(box);
			System.out.println("회원가입한 ID여부 확인 : " + isId);
			isPwd = pass.selectpwd(box);
			System.out.println("회원가입한 주민번호여부 확인 : " + isPwd);
			

			if (isId == true){ //id가 존재하면
				if (isPwd == true){ // 주민번호가 맞으면
					 isMailed = pass.selectPds(box);
					 if (isMailed == true){
						 str = "Ok";
					}else{
						 str = "fail";
					}
				}else{
					v_msg = "주민번호가 틀립니다";
					alert.historyBack(out, v_msg);
				}
			}else{
				v_msg = "ID를 잘 못 입력하셨습니다";
				alert.historyBack(out, v_msg);	
			}
      
           request.setAttribute("selectPds",str);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/beta/user/zu_BetaPw_Find.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /beta/user/zu_pw_find.jsp");
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }


}

