package controller.mobile.subj;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.subj.SubjectBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.subj.SearchSubjectServlet")
public class SearchSubjectServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;

        String process = "";

        try {
            res.setContentType("text/html;charset=euc-kr");
            out = res.getWriter();
            box = RequestManager.getBox(req);
            process = box.getStringDefault("process", "searchSubect");

            if (process.equals("searchSubect")) { 
                this.performSearchSubjectList(req, res, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    private void performSearchSubjectList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            req.setCharacterEncoding("utf-8");
            
            String dispatcherUrl = "/mobile/jsp/subj/searchSubjectResult.jsp";
            
            System.out.println("keyword in servlet : " + box.getString("keyword"));
            /*
            DataBox dbox = null;
            String subjFlag = "";

            SubjectBean bean = new SubjectBean();

            ArrayList<DataBox> subjectList = bean.searchSubjectList(box, "");
            ArrayList<DataBox> onlineSubjList = new ArrayList<DataBox>();
            ArrayList<DataBox> openclassSubjList = new ArrayList<DataBox>();

            for (int i = 0; i < subjectList.size(); i++) {
                dbox = ((DataBox) subjectList.get(i));
                subjFlag = dbox.getString("d_subj_flag");

                if (subjFlag.equals("ONLINE")) {
                    onlineSubjList.add(dbox);
                } else if (subjFlag.equals("OPENCLASS")) {
                    openclassSubjList.add(dbox);
                }
            }

            req.setAttribute("onlineSubjList", onlineSubjList);
            req.setAttribute("openclassSubjList", openclassSubjList);
            */
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectFAQList()\r\n" + ex.getMessage());
        }

    }

}
