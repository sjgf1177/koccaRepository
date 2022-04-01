package controller.mobile.subj;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.subj.SubjectBean;
import com.credu.mobile.user.LoginBean;

/**
 * 과정 찜하기 기능을 수행한다.
 * 열린 강좌 / 정규 과정을 모두 담당한다.
 * @author saderaser
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.subj.SubjFavorServlet")
public class SubjFavorServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        try {
            // request.setCharacterEncoding("euc-kr");
            response.setContentType("text/html;charset=euc-kr");

            out = response.getWriter();
            box = RequestManager.getBox(request);

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            this.pefromManageSubjFavor(request, response, box, out);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 과정 찜하기 기능을 담당하는 메서드이다.
     * 등록 및 취소 기능을 수행한다.
     * 열린 강좌 / 정규 과정을 모두 해당된다.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void pefromManageSubjFavor(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            
            StringBuffer sb = new StringBuffer();

            String jobType = box.getString("jobType"); // 작업 유형 (register: 등록 / cancel: 취소)
            
            LoginBean loginBean = new com.credu.mobile.user.LoginBean();
            boolean isLogin = loginBean.isUserLogin(box);
            
            if ( isLogin ) {

                SubjectBean bean = new SubjectBean();
    
                int resultCnt = 0;
                if (jobType.equals("register")) {
                    resultCnt = bean.registerSubjFavor(box);
                } else {
                    resultCnt = bean.cancelSubjFavor(box);
                }
                
                sb.append("{\"isLogin\" : true, \n");
                sb.append(" \"resultCnt\" : ").append(resultCnt).append("}");
            } else {
                sb.append("{\"isLogin\" : false, \n");
                sb.append(" \"resultCnt\" : 0 }");
            }
            
            out.write(sb.toString());
            out.flush();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception(this.getClass().getName() + " peformB2BLoginPage()\r\n" + ex.getMessage());
        }

    }
}