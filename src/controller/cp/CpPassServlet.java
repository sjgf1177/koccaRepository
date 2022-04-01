/**
*외주관리시스템의 비밀번호를 찾기를 하는 서블릿
*<p>제목:CpPassServlet.java</p>
*<p>설명:외주관리시스템Pass서블릿</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 박준현
*@version 1.0
*/
package controller.cp;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.cp.CpPassBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
@WebServlet("/servlet/controller.cp.CpPassServlet")
public class CpPassServlet extends javax.servlet.http.HttpServlet {
    
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
    비밀번호찾기페이지이동
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
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpSearch_Pw.jsp");
            rd.forward(request, response);
            
           // Log.info.println(this, box, "Dispatch to /learn/user/homepage/za_Faq_I.jsp");
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
	/**
    주민번호와 id를 검색하여 메일을 발송
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
	public void performjuminSearch(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            
            CpPassBean pass = new CpPassBean();
            boolean isMailed = false;
			boolean isPwd = false;
			boolean isId = false;
			boolean isName = false;
			String str = "";
			String v_msg = "";
            
            AlertManager alert = new AlertManager();
           
            isId = pass.selectid(box);
			System.out.println("회원가입한 ID여부 확인 : " + isId);
			isPwd = pass.selectpwd(box);
			System.out.println("회원가입한 주민번호여부 확인 : " + isPwd);
			isName = pass.selectname(box);
		System.out.println("isName:::"+isName);
			if (isId == true){
				if(isName ==true){
					if (isPwd == true){
						DataBox dbox = pass.selectPds(box);
						request.setAttribute("selectQna", dbox);
					}else{
						v_msg = "주민번호가 틀립니다";
						alert.historyBack(out, v_msg);
					}
				}else{
					v_msg = "이름이 틀립니다.";
					alert.historyBack(out, v_msg);
				}
			}else{
				v_msg = "ID를 잘 못 입력하셨습니다";
				alert.historyBack(out, v_msg);	
			}
      
          // request.setAttribute("selectPds",dbox);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/cp/user/zu_CpPw_Find.jsp");
            rd.forward(request, response);
            
            Log.info.println(this, box, "Dispatch to /cp/user/zu_CpPw_Find.jsp");
                    
        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelect()\r\n" + ex.getMessage());
        }
    }


}

