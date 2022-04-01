//**********************************************************
//  1. 제     목:  오프라인과정-과목 제어하는 서블릿
//  2. 프로그램명 : OffSubjectAdminServlet
//  3. 개     요:  오프라인과정-과목 제어 프로그램
//  4. 환     경: JDK 1.5
//  5. 버     젼: 1.0
//  6. 작     성: swchoi 2009. 11. 24
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
import com.credu.off.OffSubjectBean;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffSubjectAdminServlet")
public class OffSubjectAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

            if (!AdminUtil.getInstance().checkRWRight("OffSubjectAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("listPage")) {                             //in case of 목록 화면
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("insertPage")) {                    //in case of  등록 화면
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) {                        //in case of  등록
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("updatePage")) {                    //in case of  수정 화면
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) {                        //in case of  수정
                this.performUpdate(request, response, box, out);
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
            OffSubjectBean bean = new OffSubjectBean();

            int isOk = bean.delete(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffSubjectAdminServlet";
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
            OffSubjectBean bean = new OffSubjectBean();

            int isOk = bean.InsertSubject(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffSubjectAdminServlet";
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
     등록페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            /**
             * 객체 초기화
             */
            box.put("p_subj", box.get("p_subj"));
            box.put("p_year", box.get("p_year"));
            box.put("p_subjseq", box.get("p_subjseq"));
            box.put("p_term", box.get("p_term"));
            box.put("p_lecture", box.get("p_lecture"));
            request.setAttribute("requestbox", box);        //    명시적으로 box 객체를 넘겨준다

            OffSubjectBean bean = new OffSubjectBean();
            request.setAttribute("resultData", bean.selectPage(box));
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_Subject_I.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    /**
    코드리스트 VIEW
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/za_off_Subject_L.jsp";
            box.put("s_subjsearchkey", box.getString("s_subjsearchkey"));
            if(box.getString("s_lowerclass").length()>0) {
                OffSubjectBean bean = new OffSubjectBean();
                request.setAttribute("resultList", bean.listPage(box));
            }
            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/off/za_excel.jsp";//필수
                box.put("title", "Offline 과목관리");//엑셀 제목
                box.put("tname", "과정명|차수|학기|과목명|학점|담당교수|사용여부");//컬럼명
                box.put("tcode", "d_subjnm|d_subjseq|d_term|d_lecturenm|d_point|d_tutornm|d_isuse");//데이터이름
                box.put("resultListName", "resultList");//결과 목록
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }   /**
    //  수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            OffSubjectBean bean = new OffSubjectBean();

            box.put("p_goyongpricemajor", box.getStringDefault("p_goyongpricemajor", "0"));
            box.put("p_goyongpriceminor", box.getStringDefault("p_goyongpriceminor", "0"));
            box.put("p_lowerclass", box.getStringDefault("p_lowerclass", "000"));
            int isOk = bean.update(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffSubjectAdminServlet";
            box.put("p_process", "listPage");
            //    수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                request.setAttribute("requestbox", box);
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }
    /**
     수정페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //    명시적으로 box 객체를 넘겨준다

            OffSubjectBean bean = new OffSubjectBean();

            box.put("u_year", box.getString("u_year"));
            box.put("u_subjseq", box.getString("u_subjseq"));
            box.put("u_seq", box.getString("u_seq"));
            request.setAttribute("resultData", bean.selectPage(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_Subject_U.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }
}

