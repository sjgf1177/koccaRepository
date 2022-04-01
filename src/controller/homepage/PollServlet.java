//**********************************************************
//  1. ��      ��: Poll�� �����ϴ� ����
//  2. ���α׷��� : PollServlet.java
//  3. ��      ��: Poll�� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 13
//  7. ��      ��: 
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

@WebServlet("/servlet/controller.homepage.PollServlet")
public class PollServlet extends javax.servlet.http.HttpServlet {
    
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

           if(v_process.equals("selectPoll")) {          // POLL ����
                this.performSelectPoll(request, response, box, out);
            }
            else if(v_process.equals("resultPoll")) {    // POLL �������
                this.performResultPoll(request, response, box, out);
            }
            else if(v_process.equals("insertPoll")) {    // POLL �Ҷ�
                this.performInsertPoll(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
    

    /**
    POLL ����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectPoll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {       
        try {            
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            PollBean bean = new PollBean();

            int v_seq = bean.getPollSeq(box);
            box.put("p_seq", String.valueOf(v_seq));
            request.setAttribute("seq", String.valueOf(v_seq));            

            // ���� ����Ǵ¼����� �������
            if (v_seq != 0 ) {
                PollData data1 = bean.selectPoll(box);
                request.setAttribute("selectPoll", data1);

                // ������ 
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
    POLL �������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performResultPoll(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            PollBean bean = new PollBean();

            PollData data1 = bean.selectPoll(box);
            request.setAttribute("selectPoll", data1);

            // ������ (���� ��ǥ ��� ������ ��)
            ArrayList list = bean.selectPollSel(box);
            request.setAttribute("selectPollSel", list);

            ServletContext sc    = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/homepage/pop_CyberPoll_R.jsp");
            rd.forward(request, response);
                    
            Log.info.println(this, box, "Dispatch to /learn/user/game/homepage/pop_CyberPoll_R.jsp");

        }catch (Exception ex) {           
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performResultPoll()\r\n" + ex.getMessage());
        }
    }


     /**
    ����Ҷ�
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
            String v_url = "/servlet/controller.homepage.PollServlet";
            //String v_url = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "resultPoll");
            
            String v_ip = request.getRemoteAddr();
            box.put("p_ip", v_ip);          
            
            AlertManager alert = new AlertManager();

            PollBean bean = new PollBean();
            int isOk1 = bean.getPollVoteCheck(box);

            // �̹� ���Ḧ �� ���
            if (isOk1 > 0) {
                v_msg = "�̹� ��ǥ �� �����Դϴ�.";
                alert.alertOkMessage(out, v_msg, v_url , box);   
            }

            else {
                int isOk2 = bean.insertPollResult(box);

                if(isOk2 > 0) {
                /* ================ ���ϸ��� ��� ����  ============================*/
                //String v_code   = "00000000000000000006";                      // ������ ���ϸ����ڵ�
                //String s_userid = box.getSession("userid");
                //int isOk3 = MileageManager.insertMileage(v_code, s_userid);    // ���ϸ��� �ۼ�
                /* ================ ���ϸ��� ��� ����  ============================*/

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
