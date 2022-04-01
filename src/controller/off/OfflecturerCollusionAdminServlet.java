//**********************************************************
//  1. 제	  목:  오프라인차수 : 차수 제어하는 서블릿
//  2. 프로그램명 : OffGrseqAdminServlet.java
//  3. 개	  요:  오프라인차수 : 차수 제어 프로그램
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: swchoi 2009. 11. 12
//  7. 수	 정1:
//**********************************************************
package controller.off;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.off.OfflecturerCollusionAdminBean;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.off.OfflecturerCollusionAdminServlet")
public class OfflecturerCollusionAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "listPage");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("listPage")) { //in case of 차수 목록 화면
                this.performListPage(request, response, box, out);
            } else if (v_process.equals("detailView")) {
                this.performDetailView(request, response, box, out);
            } else if (v_process.equals("selectPrint")) {
                this.performSelectPrint(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/lectCollusion/za_lectCollusion_L.jsp";

            OfflecturerCollusionAdminBean bean = new OfflecturerCollusionAdminBean();

            ArrayList list = bean.listPage(box);
            request.setAttribute("infoList", list);

            //ArrayList tlist = bean.tlistPage(box);
            //request.setAttribute("tList", tlist);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    public void performDetailView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/lectCollusion/za_lectCollusion_R_Popup.jsp";

            OfflecturerCollusionAdminBean bean = new OfflecturerCollusionAdminBean();

            //기본정보
            request.setAttribute("resultbox", bean.lectInfoView(box));

            //파일
            ArrayList fileList = bean.lectFileList(box);
            request.setAttribute("fileList", fileList);

            //추가정보 학력
            ArrayList eduList = bean.lectCareerList(box, "H");
            request.setAttribute("eduList", eduList);

            //추가정보 주요경력
            ArrayList careerList = bean.lectCareerList(box, "C");
            request.setAttribute("careerList", careerList);

            //추가정보 교수기록
            ArrayList professorList = bean.lectCareerList(box, "P");
            request.setAttribute("professorList", professorList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDetailView()\r\n" + ex.getMessage());
        }
    }

    public void performSelectPrint(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/off/lectCollusion/za_lectCollusion_P_Popup.jsp";

            OfflecturerCollusionAdminBean bean = new OfflecturerCollusionAdminBean();

            //기본정보
            request.setAttribute("resultbox", bean.lectInfoView(box));

            //파일
            ArrayList fileList = bean.lectFileList(box);
            request.setAttribute("fileList", fileList);

            //추가정보 학력
            ArrayList eduList = bean.lectCareerList(box, "H");
            request.setAttribute("eduList", eduList);

            //추가정보 주요경력
            ArrayList careerList = bean.lectCareerList(box, "C");
            request.setAttribute("careerList", careerList);

            //추가정보 교수기록
            ArrayList professorList = bean.lectCareerList(box, "P");
            request.setAttribute("professorList", professorList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPrint()\r\n" + ex.getMessage());
        }
    }
}
