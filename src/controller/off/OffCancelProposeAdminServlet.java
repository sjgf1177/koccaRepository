//**********************************************************
//  1. 제     목:  오프라인신청승인 : 신청승인 제어하는 서블릿
//  2. 프로그램명 : OffApprovalAdminServlet.java
//  3. 개     요:  오프라인신청승인 : 신청승인 제어 프로그램
//  4. 환     경: JDK 1.5
//  5. 버     젼: 1.0
//  6. 작     성: swchoi 2009. 11. 12
//  7. 수    정1:
//**********************************************************
package controller.off;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OffCancelProposeBean;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffCancelProposeAdminServlet")
public class OffCancelProposeAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        //      MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
        //      int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("OffApprovalAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("listPage")) {                             //in case of 신청승인 목록 화면
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("insert")) {                        //in case of 신청승인 등록
                this.performInsert(request, response, box, out);
            } else if(v_process.equals("delete")) {               //  삭제할때
                this.performDelete(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    //  삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            // OffCancelProposeBean bean = new OffCancelProposeBean();

            int isOk = 0; //bean.delete(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffApprovalAdminServlet";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                request.setAttribute("requestbox", box);
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            // OffCancelProposeBean bean = new OffCancelProposeBean();

            int isOk = 0; //bean.runAccept(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffApprovalAdminServlet";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                request.setAttribute("requestbox", box);
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    신청승인코드리스트 VIEW
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/za_off_CancelPropose_L.jsp";
            box.sync("s_subjsearchkey");
            if(box.getString("s_lowerclass").length()>0) {
                OffCancelProposeBean bean = new OffCancelProposeBean();
                request.setAttribute("resultList", bean.listPage(box));
                //request.setAttribute("acceptResultList", bean.acceptResultList(box));
            }
            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/off/za_excel.jsp";//필수
                box.put("title", "Off 취소명단");//엑셀 제목
                box.put("tname", "과정명|차수|ID|성명|회원구분|결제상태|결제액|결제방법|취소일|취소요청일");//컬럼명
                box.put("tcode", "d_subjnm|d_subjseq|d_userid|d_name|d_membergubunnm|d_resultcode|d_price|d_paymethod|d_refunddate|d_canceldate");//데이터이름
                box.put("resultListName", "resultList");//결과 목록
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }
}

