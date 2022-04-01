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

import com.credu.homepage.MemberInfoBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OffApprovalBean;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OffApprovalAdminServlet")
public class OffApprovalAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
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

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("OffApprovalAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("listPage")) { //in case of 신청승인 목록 화면
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("insert")) { //in case of 신청승인 등록
                this.performInsert(request, response, box, out);
            } else if (v_process.equals("delete")) { //  삭제할때
                this.performDelete(request, response, box, out);
            } else if (v_process.equals("viewPropose")) { // 개인 수강신청정보 popup
                this.performViewPropose(request, response, box, out);
            } else if (v_process.equals("personalSelectPrint")) { // 수강신청자료 출력
                this.performViewPrint(request, response, box, out);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * // 삭제할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffApprovalBean bean = new OffApprovalBean();

            int isOk = bean.delete(box);

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffApprovalAdminServlet";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                request.setAttribute("requestbox", box);
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록할때
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            OffApprovalBean bean = new OffApprovalBean();

            int isOk = bean.runAccept(box);
            String s_subjcode = box.getVector("p_subj").elementAt(0).toString();
            String s_year = box.getVector("p_year").elementAt(0).toString();
            String s_subjseq = box.getVector("p_subjseq").elementAt(0).toString();

            box.put("s_subjcode", s_subjcode);
            box.put("s_year", s_year);
            box.put("s_subjseq", s_subjseq);
            box.put("s_action", "go");
            box.sync("s_lowerclass", "p_lowerclass");
            box.sync("s_middleclass", "p_middleclass");
            box.sync("s_upperclass", "p_upperclass");

            String v_msg = "";
            String v_url = "/servlet/controller.off.OffApprovalAdminServlet";
            box.put("p_process", "listPage");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                request.setAttribute("requestbox", box);
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }

    /**
     * 신청승인코드리스트 VIEW
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/za_off_Approval_L.jsp";
            box.sync("s_subjsearchkey");
            
            String subj = box.getString("ss_subj");
            String year = box.getString("ss_year");
            String subjseq = box.getString("ss_subjseq");
            
            String nameStr = "ID|성명|성별|생년월일|직장명|소속부서|직책명|연락처|E-Mail|신청일|총경력년수|총경력개월|자기소개|지원동기|참가정보 이름| 참가세션 | 소속 및 직함";
            String codeStr = "d_userid|d_name|d_sex|d_memberreg|d_comptext|d_deptnam|d_jikchaeknm|d_handphone|d_email|d_appdate|d_tcareeryear|d_tcareermonth|d_intro|d_motive|d_apply_name|d_apply_session|d_apply_belong_title";
            
            OffApprovalBean bean = new OffApprovalBean();
            
            if ( subj != null && !subj.equals("")) {
                DataBox dbox = bean.selectNeedInput(subj, year, subjseq); 
                char[]token = dbox.getString("d_needinput").toCharArray();
                
                if ( token[10] == '1') {
                    nameStr += "|닉네임|개인온라인채널";
                    codeStr += "|d_nickname|d_private_sns";
                }
            }
            
            if (box.getString("s_lowerclass").length() > 0 && !box.getBoolean("isAllExcel")) {
                request.setAttribute("resultList", bean.listPage(box));
                request.setAttribute("acceptResultList", bean.acceptResultList(box));
            }

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/off/za_excel.jsp";//필수
                box.put("title", "Off 신청명단");//엑셀 제목

                box.put("tname", nameStr);//컬럼명
                box.put("tcode", codeStr);//데이터이름
                box.put("resultListName", "resultList");//결과 목록
            }

            if (box.getBoolean("isAllExcel")) {
                request.setAttribute("resultList", bean.listAllPage(box));
                v_return_url = "/learn/admin/off/za_excel.jsp";//필수
                box.put("title", box.get("s_year") + "년도 전체 Off 신청명단");//엑셀 제목

                box.put("tname", "과정명|차수|ID|성명|성별|생년월일|직장명|소속부서|직책명|연락처|E-Mail|결제방법|결제일|결제시간|결제상태|결제액|승인상태|신청일|취소일|취소요청일|총경력년수|총경력개월");//컬럼명
                box.put("tcode", "d_subjnm|d_subjseq|d_userid|d_name|d_memberreg|d_sex|d_comptext|d_deptnam|d_jikchaeknm|d_handphone|d_email|d_paymethod|d_pgauthdate|d_pgauthtime|d_resultcode|d_price|chk_name|d_appdate|d_refunddate|d_canceldate|d_tcareeryear|d_tcareermonth");//데이터이름
                box.put("resultListName", "resultList");//결과 목록
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    public void performViewPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {

            request.setAttribute("requestbox", box);

            // 개인정보
            MemberInfoBean bean = new MemberInfoBean();
            box.put("onOff", 2);
            request.setAttribute("resultbox", bean.memberInfoViewNew(box));
            request.setAttribute("offApplyInfo", bean.selectOffApplyInfo(box));

            // 개인 수강신청 이력 정보
            OffApprovalBean bean2 = new OffApprovalBean();
            request.setAttribute("resultList", bean2.listPageMember(box));
            request.setAttribute("acceptResultList", bean2.acceptResultList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_PersonalSelect_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    public void performViewPrint(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // 개인정보
            MemberInfoBean bean = new MemberInfoBean();
            box.put("onOff", 2);
            request.setAttribute("resultbox", bean.memberInfoViewNew(box));

            // 개인 수강신청 이력 정보
            OffApprovalBean bean2 = new OffApprovalBean();
            request.setAttribute("resultList", bean2.listPageMember(box));
            request.setAttribute("acceptResultList", bean2.acceptResultList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/off/za_personalSelect_P.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("personalSelectPrint()\r\n" + ex.getMessage());
        }
    }
}
