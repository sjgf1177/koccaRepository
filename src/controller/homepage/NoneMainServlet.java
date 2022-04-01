package controller.homepage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.LoginBean;
import com.credu.homepage.NoticeAdminBean;
import com.credu.homepage.TutorLoginBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.templet.TempletBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.NoneMainServlet")
public class NoneMainServlet extends HttpServlet
    implements Serializable
{

    public NoneMainServlet()
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";
        String hostname = "";
        String s_grcode = "";
        String v_grcode = "";
        String v_url = "";
        try
        {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");
            s_grcode = box.getSession("tem_grcode");
            LoginBean bean = new LoginBean();
            hostname = request.getHeader("Host");
            box.put("p_hostname", hostname);

            if(s_grcode.equals(""))
            {
                if(hostname.equals("autoever.eibis.co.kr"))
                {
                    v_url = "indexN000001.jsp";
                } else
                {
                    v_grcode = LoginBean.getCompanyUrl(box);
                    v_url = "/index" + v_grcode + ".jsp";
                }

                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(v_url);
                rd.forward(request, response);
            }

            if(ErrorManager.isErrorMessageView())
            {
                System.out.println("Error true");
                System.out.println("out = " + out);
                box.put("errorout", out);
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            if(v_process.equals("authChange"))
            {
                String v_auth = box.getString("p_auth");
                box.setSession("gadmin", v_auth);
                box.setSession("grtype", bean.getGrtype(box));
                String v_serno = box.getSession("serno");
                int v_serno1 = 0;
                if(v_auth.equals("P1") && v_serno.equals(""))
                {
                    TutorLoginBean tbean = new TutorLoginBean();
                    v_serno1 = tbean.tutorLogin(box);
                    box.setSession("serno", v_serno1);
                }
                performMainList(request, response, box, out);
            } else
            if(v_process.equals("popupview"))
                performPopupView(request, response, box, out);
            else
            if(v_process.equals("selectNoticeList"))
                performSelectNoticeList(request, response, box, out);
            else
            if(v_process.equals("selectNoticeView"))
                performSelectNoticeView(request, response, box, out);
            else
            if(v_process.equals("usermail"))
                performUsermail(request, response, box, out);
            else
            if(v_process.equals("usermailsend"))
                performUsermailSend(request, response, box, out);
            else
                performMainList(request, response, box, out);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    @SuppressWarnings("unchecked")
    public void performMainList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_userip = request.getRemoteAddr();
            box.put("p_userip", v_userip);
            NoticeAdminBean nbean = new NoticeAdminBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if(tabseq == 0)
            {
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                tabseq = nbean.selectTableseq(box);
                if(tabseq == 0)
                {
                    String msg = "\uAC8C\uC2DC\uD310\uC815\uBCF4\uAC00 \uC5C6\uC2B5\uB2C8\uB2E4.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }
            ArrayList pnlist = nbean.selectListNoticePopupHome(box);
            request.setAttribute("noticePopup", pnlist);
            ArrayList tnlist = nbean.selectListNoticeTop(box);
            request.setAttribute("noticeListTop", tnlist);

            String v_url = "";
            String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
            TempletBean templetbean = new TempletBean();
            ArrayList mainl_list = templetbean.SelectMenuList(tem_grcode, "0", "L");
            ArrayList mainr_list = templetbean.SelectMenuList(tem_grcode, "0", "R");
            ArrayList mainc_list = templetbean.SelectMenuList(tem_grcode, "0", "C");
            request.setAttribute("mainl_list", mainl_list);
            request.setAttribute("mainr_list", mainr_list);
            request.setAttribute("mainc_list", mainc_list);
            /*
            if(tem_type.equals("GA"))
            {
                if(tem_type_sub.equals("wsu"))
                    v_url = "/learn/user/game/homepage/gu_MainWsu.jsp";
                else
                    v_url = "/learn/user/game/homepage/gu_MainA.jsp";
            } else
            if(tem_type.equals("GB"))
                v_url = "/learn/user/game/homepage/gu_MainB.jsp";
            else
            if(tem_type.equals("KA"))
                v_url = "/learn/user/kocca/homepage/ku_MainA.jsp";
            else
            if(tem_type.equals("KB"))
                v_url = "/learn/user/kocca/homepage/ku_MainB.jsp";
            else
                v_url = "/";
            */
            v_url = "/learn/user/game/homepage/gu_MainNone.jsp";
            if(box.getSession("isnew").equals("Y"))
                v_url = "/learn/user/kocca/homepage/new_Main.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void performPopupView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_userip = request.getRemoteAddr();
            box.put("p_userip", v_userip);
            NoticeAdminBean nbean = new NoticeAdminBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if(tabseq == 0)
            {
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                tabseq = nbean.selectTableseq(box);
                if(tabseq == 0)
                {
                    String msg = "\uAC8C\uC2DC\uD310\uC815\uBCF4\uAC00 \uC5C6\uC2B5\uB2C8\uB2E4.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }
            com.credu.library.DataBox dbox = nbean.selectViewNotice(box);
            request.setAttribute("selectNotice", dbox);
            String v_url = "";
            if(box.getString("p_useframe").equals("Y"))
                v_url = "/learn/user/homepage/zu_Notice_popOnlycontY.jsp";
            else
                v_url = "/learn/user/homepage/zu_Notice_popOnlycontN.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void performSelectNoticeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_userip = request.getRemoteAddr();
            box.put("p_userip", v_userip);
            NoticeAdminBean nbean = new NoticeAdminBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if(tabseq == 0)
            {
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                tabseq = nbean.selectTableseq(box);
                if(tabseq == 0)
                {
                    String msg = "\uAC8C\uC2DC\uD310\uC815\uBCF4\uAC00 \uC5C6\uC2B5\uB2C8\uB2E4.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }
            ArrayList list1 = nbean.selectListNoticeAllHome(box);
            request.setAttribute("selectNoticeListAll", list1);
            ArrayList list2 = nbean.selectListNoticeHome(box);
            request.setAttribute("selectNoticeList", list2);
            String v_url = "";
            v_url = "/learn/user/homepage/zu_Notice_L.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void performSelectNoticeView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_userip = request.getRemoteAddr();
            box.put("p_userip", v_userip);
            NoticeAdminBean nbean = new NoticeAdminBean();
            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if(tabseq == 0)
            {
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                tabseq = nbean.selectTableseq(box);
                if(tabseq == 0)
                {
                    String msg = "\uAC8C\uC2DC\uD310\uC815\uBCF4\uAC00 \uC5C6\uC2B5\uB2C8\uB2E4.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }
            com.credu.library.DataBox dbox = nbean.selectViewNotice(box);
            request.setAttribute("selectNotice", dbox);
            String v_url = "";
            v_url = "/learn/user/homepage/zu_Notice_R.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    public void performUsermail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            request.setAttribute("requestbox", box);
            String v_url = "/learn/user/homepage/zu_Usermail.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    public void performUsermailSend(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception
    {
        try
        {
            LoginBean bean = new LoginBean();
            int isOk = bean.insertUserMail(box);
            String v_msg = "";
            AlertManager alert = new AlertManager();
            if(isOk > 0)
            {
                v_msg = "\uC804\uC1A1\uB418\uC5C8\uC2B5\uB2C8\uB2E4.";
                alert.selfClose(out, v_msg);
            } else
            {
                v_msg = "\uC804\uC1A1\uC5D0 \uC2E4\uD328\uD588\uC2B5\uB2C8\uB2E4.";
                alert.alertFailMessage(out, v_msg);
            }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUsermailSend()\r\n" + ex.getMessage());
        }
    }
}