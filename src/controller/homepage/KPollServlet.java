//**********************************************************
//1. 제      목: Poll를 제어하는 서블릿
//2. 프로그램명: KPollServlet.java
//3. 개      요: Poll의 페이지을 제어한다
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 이나연 06.01.12
//7. 수      정: 
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.PollBean;
import com.credu.homepage.PollData;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.MultipartRequest;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

@WebServlet("/servlet/controller.homepage.KPollServlet")
public class KPollServlet extends javax.servlet.http.HttpServlet {

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

       if(v_process.equals("selectPoll")) {          // POLL 보기
            this.performSelectPoll(request, response, box, out);
        }
        else if(v_process.equals("resultPoll")) {    // POLL 결과보기
            this.performResultPoll(request, response, box, out);
        }
        else if(v_process.equals("insertPoll")) {    // POLL 할때
            this.performInsertPoll(request, response, box, out);
        }
    }catch(Exception ex) {
        ErrorManager.getErrorStackTrace(ex, out);  
    }
}


/**
POLL 보기
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performSelectPoll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
    try {            
        request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
        PollBean bean = new PollBean();

        int v_seq = bean.getPollSeq(box);
        box.put("p_seq", String.valueOf(v_seq));
        request.setAttribute("seq", String.valueOf(v_seq));            

        // 현재 진행되는설문이 있을경우
        if (v_seq != 0 ) {
            PollData data1 = bean.selectPoll(box);
            request.setAttribute("selectPoll", data1);

            // 폴지문 
            ArrayList list = bean.selectPollSel(box);
            request.setAttribute("selectPollSel", list);
        }

        ServletContext sc    = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/homepage/zu_Poll_I.jsp");
        rd.forward(request, response);
                
        Log.info.println(this, box, "Dispatch to /learn/user/homepage/zu_Poll_I.jsp");

    }catch (Exception ex) {           
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performSelectPoll()\r\n" + ex.getMessage());
    }
}


/**
POLL 결과보기
@param request  encapsulates the request to the servlet
@param response encapsulates the response from the servlet
@param box      receive from the form object
@param out      printwriter object
@return void
*/
public void performResultPoll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
    try {
        request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
        PollBean bean = new PollBean();

        PollData data1 = bean.selectPoll(box);
        request.setAttribute("selectPoll", data1);

        // 폴지문 (유저 투표 결과 가지고 옴)
        ArrayList list = bean.selectPollSel(box);
        request.setAttribute("selectPollSel", list);

        ServletContext sc    = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/kocca/homepage/pop_LivePoll_R.jsp");
        rd.forward(request, response);
                
        Log.info.println(this, box, "Dispatch to /learn/user/kocca/homepage/pop_LivePoll_R.jsp");

    }catch (Exception ex) {           
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performResultPoll()\r\n" + ex.getMessage());
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
public void performInsertPoll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) 
    throws Exception {       
    try {             

        String v_msg = "";
        String v_url = "/servlet/controller.homepage.KPollServlet";
        //String v_url = "/servlet/controller.homepage.MainServlet";
        box.put("p_process", "resultPoll");
        
        String v_ip = request.getRemoteAddr();
        box.put("p_ip", v_ip);          
        
        AlertManager alert = new AlertManager();

        PollBean bean = new PollBean();
        int isOk1 = bean.getPollVoteCheck(box);

        // 이미 투료를 한 경우
        if (isOk1 > 0) {
            v_msg = "이미 투표 한 설문입니다.";
            alert.alertOkMessage(out, v_msg, v_url , box);   
        }

        else {
            int isOk2 = bean.insertPollResult(box);

            if(isOk2 > 0) {
            /* ================ 마일리지 등록 시작  ============================*/
            //String v_code   = "00000000000000000006";                      // 폴응답 마일리지코드
            //String s_userid = box.getSession("userid");
            //int isOk3 = MileageManager.insertMileage(v_code, s_userid);    // 마일리지 작성
            /* ================ 마일리지 등록 시작  ============================*/

                v_msg = "insert.ok";       
                alert.alertOkMessage(out, v_msg, v_url , box);   
            }
            else {                
                v_msg = "insert.fail";   
                alert.alertFailMessage(out, v_msg); 
            }
        }

        Log.info.println(this, box, v_msg + " on PollServlet");
    }catch (Exception ex) {           
        ErrorManager.getErrorStackTrace(ex, out);
        throw new Exception("performInsertPoll()\r\n" + ex.getMessage());
    }
}

}
