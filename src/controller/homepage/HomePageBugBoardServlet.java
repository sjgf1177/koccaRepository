//**********************************************************
//1. 제      목: 버그 신고 게시판을 제어하는 서블릿
//2. 프로그램명: HomePageBugBoardServlet.java
//3. 개      요: 버그 신고 게시판을 제어한다
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 06.01.12 이나연
//7. 수      정:
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.BoardBean;
import com.credu.homepage.HomePageQnaBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomePageBugBoardServlet")
public class HomePageBugBoardServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";
        boolean v_canAppend = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            box = BulletinManager.getState("HomePageBoard", box, out);

            v_process = box.getStringDefault("p_process","insertPage");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }



            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLogin(out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            v_canAppend = BulletinManager.isAuthority(box,box.getString("p_canAppend"));

            if(v_process.equals("insertPage")) {          //  등록페이지로 이동할때
                if(v_canAppend)
                    this.performInsertPage(request, response, box, out);
                else this.errorPage(box, out);
            }
            else if(v_process.equals("insert")) {         //  등록할때
                if(v_canAppend)
                    this.performInsert(request, response, box, out);
                else this.errorPage(box, out);
            }else{
                this.errorPage(box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


     /**
    등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            BoardBean bean = new BoardBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));
              if (tabseq == 0) {
                    /*------- 게시판 분류에 대한 부분 세팅 -----*/
                    box.put("p_type", "BU");
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

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/game/service/gu_Bug_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/game/service/gu_Bug_I.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
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
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            HomePageQnaBean bean = new HomePageQnaBean("BU");

            int isOk = bean.insertQnaQue(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {

                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on HomePageBoardServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
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

