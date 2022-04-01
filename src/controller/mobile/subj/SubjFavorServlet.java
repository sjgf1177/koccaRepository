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
 * ���� ���ϱ� ����� �����Ѵ�.
 * ���� ���� / ���� ������ ��� ����Ѵ�.
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
     * ���� ���ϱ� ����� ����ϴ� �޼����̴�.
     * ��� �� ��� ����� �����Ѵ�.
     * ���� ���� / ���� ������ ��� �ش�ȴ�.
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

            String jobType = box.getString("jobType"); // �۾� ���� (register: ��� / cancel: ���)
            
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