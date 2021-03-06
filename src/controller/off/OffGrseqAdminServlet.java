//**********************************************************
//  1. 제     목:  오프라인차수 : 차수 제어하는 서블릿
//  2. 프로그램명 : OffGrseqAdminServlet.java
//  3. 개     요:  오프라인차수 : 차수 제어 프로그램
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
import com.credu.off.OffGrseqBean;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffGrseqAdminServlet")
public class OffGrseqAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

            if (!AdminUtil.getInstance().checkRWRight("OffGrseqAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("listPage")) {                             //in case of 차수 목록 화면
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("insertPage")) {                    //in case of 차수 등록 화면
                this.performInsertPage(request, response, box, out);
            } else if (v_process.equals("insert")) {                        //in case of 차수 등록
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("addSeq")) {                        //in case of 차수 추가 모집 등록
                this.performAddSeq(request, response, box, out);
            } else if (v_process.equals("addSubjSeq")) {                  //in case of 차수 복사 등록
                this.performAddSubjSeq(request, response, box, out);
            } else if (v_process.equals("updatePage")) {                    //in case of 차수 수정 화면
                this.performUpdatePage(request, response, box, out);
            } else if (v_process.equals("update")) {                        //in case of 차수 수정
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {               //  삭제할때
                this.performDelete(request, response, box, out);
            } else if(v_process.equals("detailListPage")) {               //  학기 목록
                this.performDetailListPage(request, response, box, out);
            } else if(v_process.equals("detailInsertPage")) {                 //  학기 입력
                this.performDetailInsertPage(request, response, box, out);
            } else if(v_process.equals("detailInsert")) {                 //  학기 저장
                this.performDetailInsert(request, response, box, out);
            }  else if(v_process.equals("teachDetailPage")) {                 //  강좌정보 저장 페이지
                this.performTeachDetailPage(request, response, box, out);
            } else if (v_process.equals("teachDetailUpdate")) {               // 강좌정보 저장
                this.performTeachDetailUpdate(request, response, box, out);
            } else if(v_process.equals("teachDetailDelete")) {               //  강좌정보 삭제
                this.performTeachDetailDelete(request, response, box, out);
            } else if(v_process.equals("teachDetailSelect")) {               //  강좌정보 가져오기
                this.performTeachDetailSelect(request, response, box, out);
            } else if(v_process.equals("teachDailyPrint")) {                         //  훈련일지 출력
                this.performTeachDailyPrint(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    public void performAddSeq(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffGrseqBean bean = new OffGrseqBean();

            box.put("p_goyongpricemajor", box.getStringDefault("p_goyongpricemajor", "0"));
            box.put("p_goyongpriceminor", box.getStringDefault("p_goyongpriceminor", "0"));
            box.put("p_lowerclass", box.getStringDefault("p_lowerclass", "000"));
            int isOk = bean.insertSeq(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffGrseqAdminServlet";
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

    public void performAddSubjSeq(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffGrseqBean bean = new OffGrseqBean();

            box.put("p_goyongpricemajor", box.getStringDefault("p_goyongpricemajor", "0"));
            box.put("p_goyongpriceminor", box.getStringDefault("p_goyongpriceminor", "0"));
            box.put("p_lowerclass", box.getStringDefault("p_lowerclass", "000"));
            int isOk = bean.InsertSubject(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffGrseqAdminServlet";
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
    //  삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffGrseqBean bean = new OffGrseqBean();

            int isOk = bean.delete(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffGrseqAdminServlet";
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

    public void performDetailInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffGrseqBean bean = new OffGrseqBean();

            box.put("p_goyongpricemajor", box.getStringDefault("p_goyongpricemajor", "0"));
            box.put("p_goyongpriceminor", box.getStringDefault("p_goyongpriceminor", "0"));
            box.put("p_lowerclass", box.getStringDefault("p_lowerclass", "000"));
            int isOk = bean.detailInsert(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffGrseqAdminServlet";
            box.put("p_process", "detailListPage");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                request.setAttribute("requestbox", box);
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, true, true);
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
    public void performDetailInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //    명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();

            OffGrseqBean bean = new OffGrseqBean();
            request.setAttribute("resultData", bean.detailInsertPage(box));

            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_grseqDetail_I.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    public void performDetailListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/za_off_grseqDetail_L.jsp";
            box.put("s_subjsearchkey", box.getString("s_subjsearchkey"));
            if(box.getString("s_lowerclass").length()>0) {
                OffGrseqBean bean = new OffGrseqBean();
                request.setAttribute("resultList", bean.listPage(box));
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    public void performTeachDetailPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/za_off_grseqteachdetail_I.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
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
            OffGrseqBean bean = new OffGrseqBean();

            box.put("p_goyongpricemajor", box.getStringDefault("p_goyongpricemajor", "0"));
            box.put("p_goyongpriceminor", box.getStringDefault("p_goyongpriceminor", "0"));
            box.put("p_term", box.getStringDefault("p_term", "1"));
            box.put("p_lowerclass", box.getStringDefault("p_lowerclass", "000"));
            int isOk = bean.InsertSubject(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffGrseqAdminServlet";
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
            request.setAttribute("requestbox", box);        //    명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_grseq_I.jsp");
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }
    /**
    차수코드리스트 VIEW
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box    receive from the form object
    @param out    printwriter object
    @return void
     */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/za_off_grseq_L.jsp";
            box.put("s_subjsearchkey", box.getString("s_subjsearchkey"));
            if(box.getString("s_lowerclass").length()>0) {
                OffGrseqBean bean = new OffGrseqBean();
                request.setAttribute("resultList", bean.listPage(box));
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
            OffGrseqBean bean = new OffGrseqBean();

            box.put("p_goyongpricemajor", box.getStringDefault("p_goyongpricemajor", "0"));
            box.put("p_goyongpriceminor", box.getStringDefault("p_goyongpriceminor", "0"));
            box.put("p_lowerclass", box.getStringDefault("p_lowerclass", "000"));
            int isOk = bean.update(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffGrseqAdminServlet";
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

            OffGrseqBean bean = new OffGrseqBean();

            box.put("u_year", box.getString("u_year"));
            box.put("u_subjseq", box.getString("u_subjseq"));
            box.put("u_seq", box.getString("u_seq"));
            request.setAttribute("resultData", bean.selectPage(box));

            request.setAttribute("detail", bean.selectsubjseq_detail(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_grseq_U.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    public void performTeachDetailSelect(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //    명시적으로 box 객체를 넘겨준다

            OffGrseqBean bean = new OffGrseqBean();
            request.setAttribute("resultData", bean.selectTeachDetail(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_grseqteachdetail_I.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    public void performTeachDetailDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffGrseqBean bean = new OffGrseqBean();

            int isOk = bean.teachDetailDelete(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffGrseqAdminServlet";
            box.put("p_process", "updatePage");

            box.put("u_year",box.getString("p_year"));
            box.put("u_subjseq",box.getString("p_subjseq"));

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

    public void performTeachDetailUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
        try {
            OffGrseqBean bean = new OffGrseqBean();

            box.put("p_goyongpricemajor", box.getStringDefault("p_goyongpricemajor", "0"));
            box.put("p_goyongpriceminor", box.getStringDefault("p_goyongpriceminor", "0"));
            box.put("p_lowerclass", box.getStringDefault("p_lowerclass", "000"));
            int isOk = bean.updateTeachDetail(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffGrseqAdminServlet";
            box.put("p_process", "updatePage");
            //    수정 후 해당 리스트 페이지로 돌아가기 위해
            box.put("u_year",box.getString("p_year"));
            box.put("u_subjseq",box.getString("p_subjseq"));

            if(box.getString("p_seq1").equals(""))
                v_msg = "insert";
            else
                v_msg = "update";

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                request.setAttribute("requestbox", box);
                v_msg += ".ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg += ".fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

     public void performTeachDailyPrint(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //    명시적으로 box 객체를 넘겨준다

            OffGrseqBean bean = new OffGrseqBean();
            request.setAttribute("resultData", bean.selectTeachDailyPrint(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_off_teachdailyprint_P.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performTeachDailyPrint()\r\n" + ex.getMessage());
        }
    }
}

