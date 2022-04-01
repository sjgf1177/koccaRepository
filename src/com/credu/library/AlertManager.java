package com.credu.library;

import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * ����: Alert �޽������� ���̺귯�� ����: Copyright: Copyright (c) 2004 Company: Credu
 *
 * @author ������
 * @date 2003. 12
 * @version 1.0
 */
public class AlertManager {

    /**
     * �޽����� JavaScript�� alert â���� �����ش�. history.back(-1); �̺�Ʈ �߻�
     *
     * @param out PrintWriter class
     * @param msg �޽���
     */
    public static void historyBack(PrintWriter out, String msg) {
        out.println("<html>");
        out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'><script language = 'javascript'>");
        out.println("alert('" + msg + "')");
        out.println("history.back(-1);");
        out.println("</script>");
        out.println("</head>");
        out.println("</html>");
        out.flush();
    }

    /**
     * ���� or ������ ó���� �������� ��쿡 ������ �޽����� JavaScript�� alert â���� �����ش�.
     * history.back(-1); �̺�Ʈ �߻�
     *
     * @param out PrintWriter class
     * @param msg �޽���
     */
    public void alertFailMessage(PrintWriter out, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n");
        sb.append("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>\n");
        sb.append("<script type='text/javascript'>\n");

        if (msg.equals("insert.fail")) {
            sb.append("alert('��Ͽ� �����߽��ϴ�.');\n");

        } else if (msg.equals("insert.failDupe")) {
            sb.append("alert('�ߺ��� Ű������ ��Ͽ� �����߽��ϴ�.');");
            sb.append("self.close();\n");

        } else if (msg.equals("save.fail")) {
            sb.append("alert('���忡 �����߽��ϴ�.');");

        } else if (msg.equals("update.fail")) {
            sb.append("alert('������ �����߽��ϴ�.');");

        } else if (msg.equals("delete.fail")) {
            sb.append("alert('������ �����߽��ϴ�.');");

        } else if (msg.equals("reply.fail")) {
            sb.append("alert('��Ͽ� �����߽��ϴ�.');");

        } else if (msg.equals("confirm.fail")) {
            sb.append("alert('��û��� ���忡 �����߽��ϴ�!');");

        } else if (msg.equals("propose.fail")) {
            sb.append("alert('��û�� �����߽��ϴ�!');");

        } else if (msg.equals("propcancel.fail")) {
            sb.append("alert('��ҽ�û�� �����߽��ϴ�!');");

        } else if (msg.equals("approvalreport.fail")) {
            sb.append("alert('�����ſ� �����߽��ϴ�!');");

        } else if (msg.equals("approval.fail")) {
            sb.append("alert('����ó���� �����߽��ϴ�!');");

        } else if (msg.equals("appcancel.fail")) {
            sb.append("alert('�������ó���� �����߽��ϴ�!');");

        } else if (msg.equals("forceprop.fail")) {
            sb.append("alert('�����԰�ó���� �����߽��ϴ�!');");

        } else if (msg.equals("changeseq.fail")) {
            sb.append("alert('���������� ���ο� �����߽��ϴ�!');");

        } else if (msg.equals("autoassign.fail")) {
            sb.append("alert('������п� �����߽��ϴ�!');");

        } else if (msg.equals("savechangeseq.fail")) {
            sb.append("alert('�������� ó���� �����߽��ϴ�!');");

        } else if (msg.equals("studentinsert.fail")) {
            sb.append("alert('������ �߰��� �����߽��ϴ�!');");

        } else if (msg.equals("reject.fail")) {
            sb.append("alert('�ݷ�ó���� �����߽��ϴ�!');");

        } else if (msg.equals("studentapproval.fail")) {
            sb.append("alert('����ó���� �����߽��ϴ�.');");

        } else if (msg.equals("studentvnogoyong.fail")) {
            sb.append("alert('����ó���� �����߽��ϴ�.');");

        } else if (msg.equals("mail.fail")) {
            sb.append("alert('���Ϲ߼ۿ� �����߽��ϴ�.');");

        } else if (msg.equals("scomodify.fail")) {
            sb.append("alert('������ ���� �������� �־� ������ �� �����ϴ�.');");

        } else if (msg.equals("scodelete.fail")) {
            sb.append("alert('������ ���εǾ� �־� ������ �� �����ϴ�.');");

        } else if (msg.equals("scodeletejindo.fail")) {
            sb.append("alert('������ ���� �������� �־� ������ �� �����ϴ�.');");

        } else if (msg.equals("compcondition.fail")) {
            sb.append("alert('�����Ͻ� ȸ��� �̹� ��ϵǾ��ֽ��ϴ�.');");

        } else if (msg.equals("blackcondition.fail")) {
            sb.append("alert('�Է��Ͻ� ������ �̹� ��ϵǾ��ֽ��ϴ�.');");

        } else if (msg.equals("opinioninsert.fail")) {
            sb.append("alert('�Է��Ͻ� ������ �̹� ��ϵǾ��ֽ��ϴ�. �ǰ� ������ [�����н��� �ǰߺ���]�� �̵��Ͽ� �����Ͻñ� �ٶ��ϴ�.');");
            sb.append("self.close();");

        } else if (msg.equals("subjApplyTimeOver.no")) {
            sb.append("alert('���� ��û �Ⱓ�� �ƴմϴ�.');");
            sb.append("self.close();");

        } else if (!msg.equals("")) {
            sb.append("alert(\"" + msg + "\");\n");
        }

        sb.append("history.back(-1);");
        sb.append("</script>");
        sb.append("</head>");
        sb.append("</html>");

        out.println(sb.toString());
        out.flush();

        // out.println("history.back(-1);");
        // out.println("");
        // out.println("</head>");
        // out.println("</html>");
        // out.flush();
    }

    /**
     * �������� ������ ���� ���� �� Ȯ�� �޼��� �����ְ� â�� �ݴ´�.
     *
     * @param out PrintWriter class
     * @param msg �޽���
     */
    public void alertEduServiceSul(PrintWriter out, String msg) {
        out.println("<html>");
        out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'><script language = 'javascript'>");

        if (!msg.equals("")) {
            out.println("alert(\"" + msg + "\");");
        }

        out.println("self.close();");
        out.println("</script>");
        out.println("</head>");
        out.println("</html>");
        out.flush();
    }

    /**
     * ���� or ������ ����� ó���Ǿ��� ��쿡 ������ �޽����� JavaScript�� alert â���� �����ش�.
     * isOpenWin/isClosed/isHomepage �� ��� false �� ���õȴ�.
     *
     * @param out PrintWriter class
     * @param msg �޽���
     * @param url �̵��� ������ URL
     * @param box RequestBox class
     */
    public void alertOkMessage(PrintWriter out, String msg, String url, RequestBox box) {
        this.alertOkMessage(out, msg, url, box, false, false, false, false);
    }

    /**
     * ���� or ������ ����� ó���Ǿ��� ��쿡 ������ �޽����� JavaScript�� alert â���� �����ش�. isHomepage ��
     * ��� false �� ���õȴ�.
     *
     * @param out PrintWriter class
     * @param msg �޽���
     * @param url �̵��� ������ URL
     * @param box RequestBox class
     */
    public void alertOkMessage(PrintWriter out, String msg, String url, RequestBox box, boolean isOpenWin, boolean isClosed) {
        this.alertOkMessage(out, msg, url, box, isOpenWin, isClosed, false, false);
    }

    /**
     * ���� or ������ ����� ó���Ǿ��� ��쿡 ������ �޽����� JavaScript�� alert â���� �����ش�. isHomepage ��
     * ��� false �� ���õȴ�.
     *
     * @param out PrintWriter class
     * @param msg �޽���
     * @param url �̵��� ������ URL
     * @param box RequestBox class
     */
    public void alertOkMessage(PrintWriter out, String msg, String url, RequestBox box, boolean isOpenWin, boolean isClosed, boolean isHomepage) {
        this.alertOkMessage(out, msg, url, box, isOpenWin, isClosed, isHomepage, false);
    }

    /**
     * ���� or ������ ����� ó���Ǿ��� ��쿡 ������ �޽����� JavaScript�� alert â���� �����ش�.
     *
     * @param out PrintWriter class
     * @param msg �޽���
     * @param url �̵��� ������ URL
     * @param box RequestBox class
     * @param isOpenWin ���� â�� Openwindow �ΰ� ����
     * @param isClosed Openwindow â�� close ���� ����
     * @param isHomepage Homepage���� ����θ� �������� ����
     */
    @SuppressWarnings("unchecked")
    public void alertOkMessage(PrintWriter out, String msg, String url, RequestBox box, boolean isOpenWin, boolean isClosed, boolean isHomepage,
            boolean isReload) {
        out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'></head>");
        //out.println("<DIV id='tmp' style='visibility:hidden; display:none'>");
        out.println("<body>");

        out.println("<form name = 'form1' method='post'>");

        Enumeration e1 = box.keys();
        while (e1.hasMoreElements()) {
            String v_key = (String) e1.nextElement();
            String v_value = box.get(v_key).toString();
            
            if ( !v_key.equals("p_pwd") && !v_key.equals("p_topuserid") && !v_key.equals("p_userid") && !v_key.equals("p_toppwd")) {
                out.println("<input type = 'hidden' name = '" + v_key + "' value = '" + v_value + "'>");
            }
        }
        out.println("</form>");
        //out.println("</DIV>");
        out.println("</body>");

        out.println("<script language = 'javascript'>");
        if (msg.equals("insert.ok")) {
            out.println("alert('��� �Ǿ����ϴ�.');");

        } else if (msg.equals("save.ok")) {
            out.println("alert('���� �Ǿ����ϴ�.');");

        } else if (msg.equals("update.ok")) {
            out.println("alert('���� �Ǿ����ϴ�.');");

        } else if (msg.equals("delete.ok")) {
            out.println("alert('���� �Ǿ����ϴ�.');");

        } else if (msg.equals("reply.ok")) {
            out.println("alert('��� �Ǿ����ϴ�.');");

        } else if (msg.equals("scomodify.ok")) {
            out.println("alert('�����Ǿ����ϴ�.');");

        } else if (msg.equals("confirm.ok")) {
            out.println("alert('��û����� ����Ǿ����ϴ�.');");

        } else if (msg.equals("propose.ok")) {
            out.println("alert('��ٱ��Ͽ� ���� �Ǿ����ϴ�. ���ǰ��ǽ�->��ٱ��Ͽ��� �����ϱ⸦ �����ϼž� ������ �Ϸ�˴ϴ�.');");

        } else if (msg.equals("propose.ok.zero")) {
            out.println("alert('������û�� �Ϸ� �Ǿ����ϴ�.');");

        } else if (msg.equals("offpropose.ok")) {
            //out.println("alert('��û�� �Ϸ� �Ǿ����ϴ�. \\n1�� ������ ���� ���ǽ�->��������� ��ȸ/���ο��� �����ϱ⸦ �����ϼž� ������ �Ϸ�˴ϴ�');");
            out.println("alert('��û�� �Ϸ� �Ǿ����ϴ�. ');");

        } else if (msg.equals("propcancel.ok")) {
            out.println("alert('��ҽ�û �Ǿ����ϴ�.');");

        } else if (msg.equals("propcancel.delete.ok")) {
            out.println("alert('��� �Ǿ����ϴ�.');");

        } else if (msg.equals("approvalreport.ok")) {
            out.println("alert('������ �Ǿ����ϴ�.');");

        } else if (msg.equals("approval.ok")) {
            out.println("alert('����ó�� �Ǿ����ϴ�.');");

        } else if (msg.equals("appcancel.ok")) {
            out.println("alert('�������ó�� �Ǿ����ϴ�.');");

        } else if (msg.equals("forceprop.ok")) {
            out.println("alert('�԰�ó�� �Ǿ����ϴ�.');");

        } else if (msg.equals("changeseq.ok")) {
            out.println("alert('���������� ���εǾ����ϴ�.');");

        } else if (msg.equals("autoassign.ok")) {
            out.println("alert('������п� �����Ͽ����ϴ�.');");

        } else if (msg.equals("savechangeseq.ok")) {
            out.println("alert('�������� ó�� �Ǿ����ϴ�.');");

        } else if (msg.equals("studentinsert.ok")) {
            out.println("alert('������ �߰� �Ǿ����ϴ�.');");

        } else if (msg.equals("reject.ok")) {
            out.println("alert('�ݷ�ó�� �Ǿ����ϴ�.');");

        } else if (msg.equals("studentapproval.ok")) {
            out.println("alert('����ó�� �Ǿ����ϴ�.');");

        } else if (msg.equals("studentvnogoyong.ok")) {
            out.println("alert('����ó�� �Ǿ����ϴ�.');");

        } else if (msg.equals("pregrseq.no")) {
            out.println("alert('���� ������ ��������� �̼����Ͽ� ���� ��û �� �� �����ϴ�.');");

        } else if (msg.equals("nowgrseq.no")) {
            out.println("alert('���� 5�� ���� ��û�ϼż� �߰� ������û�� �Ұ����մϴ�.\\n(������ 1�� �ִ� 5�� �������� ��û ����)\\n\\n�� ���� ������ ������û���ּ���. (�ſ� 1��, 15�� ���� ����)');");

            // out.println("alert('�ش� ������ ��û ������ ������� ���� �ʰ��Ͽ����ϴ�.\\n(1�� 3���� ��û ����)');");
        } else if (msg.equals("nowgrseq2.no")) {
            out.println("alert('�ش� ������ ��û ������ ������� ���� �ʰ��Ͽ����ϴ�.');");

        } else if (msg.equals("nowgrseq3.no")) {
            out.println("alert('�ش� ������ ��û ������ �������� �ʰ��Ͽ����ϴ�.');");

        } else if (msg.equals("subjApplyTimeOver.no")) {
            out.println("alert('���� ��û �Ⱓ�� �ƴմϴ�.');");

        } else if (msg.equals("mail.ok")) {
            String v_mailcnt = box.getString("p_mailcnt");
            out.println("alert('" + v_mailcnt + "���� ������ �߼۵Ǿ����ϴ�.');");

        } else if (!msg.equals("")) {
            out.println("alert('" + msg + "');");
        }

        if (!isOpenWin) { //      openwindow �� �ƴѰ��
            //  System.out.println(box);
            out.println("document.form1.action = '" + url + "'");
            out.println("document.form1.submit()");
        } else if (isOpenWin && isClosed && !isHomepage && !isReload) { //      openwindow �� close �Ǵ� ���
            if (box.get("openercount").equals("3")) {
                out.println("self.close();");
            } else {
                if (box.get("openercount").equals("2"))
                    out.println("document.form1.target = top.window.opener.top.window.opener.name");
                else
                    out.println("document.form1.target = top.window.opener.name");
                out.println("document.form1.action = '" + url + "';");
                out.println("document.form1.submit();");
                out.println("self.close();");
            }
        } else if (isOpenWin && isClosed && !isHomepage && isReload) {
            out.println("window.opener.location.reload()");
            out.println("self.close()");
        } else if (isOpenWin && !isClosed) { //      openwindow �� ��ӿ������ִ� ���
            out.println("document.form1.target = window.opener.name");
            out.println("document.form1.action = '" + url + "'");
            out.println("document.form1.submit()");
        } else if (isOpenWin && isClosed && isHomepage) { //      Homepage���� ����θ� ���� ���
            if (box.get("openercount").equals("2"))
                out.println("document.form1.target = top.window.opener.top.window.opener.name");
            else
                out.println("document.form1.target = top.window.opener.name");
            out.println("document.form1.action = '" + url + "'");
            out.println("document.form1.submit()");
            out.println("top.self.close()");
        }
        out.println("</script>");
        out.println("</html>");
        out.flush();
    }

    @SuppressWarnings("unchecked")
    public void confirmMessage(PrintWriter out, String msg, String url, RequestBox box) {
        out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'></head>");
        out.println("<DIV id='tmp' style='visibility:hidden; display:none'>");

        out.println("<form name = 'form1' method='post'>");

        Enumeration e1 = box.keys();
        while (e1.hasMoreElements()) {
            String v_key = (String) e1.nextElement();
            String v_value = box.get(v_key).toString();
            out.println("<input type = 'hidden' name = '" + v_key + "' value = '" + v_value + "'>");
        }
        out.println("</form>");
        out.println("</DIV>");

        out.println("<script language = 'javascript'>");
        out.println("if(confirm('" + msg + "')) {");
        out.println("    document.form1.action = '" + url + "';");
        out.println("    document.form1.submit();");
        out.println("}    ");
        out.println("else {");
        out.println("    self.close();");
        out.println("}    ");
        out.println("</script>");
        out.println("</head>");
        out.println("</html>");
        out.flush();
    }

    /**
     * �޽����� JavaScript�� alert â���� �����ش�. self.close(); �̺�Ʈ �߻�
     *
     * @param out PrintWriter class
     * @param msg �޽���
     */
    public void selfClose(PrintWriter out, String msg) {
        out.println("<html>");
        out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'><script language = 'javascript'>");
        System.out.println("msg = > '" + msg + "'");
        if (!msg.equals("")) {
            out.println("alert('" + msg + "');");
        }
        out.println("self.close();");
        out.println("</script>");
        out.println("</head>");
        out.println("</html>");
        out.flush();
    }

    /**
     * �޽����� JavaScript�� alert â���� �����ش�. self.close(); �̺�Ʈ �߻�
     *
     * @param out PrintWriter class
     * @param msg �޽���
     */
    public void selfClose_kocsc(PrintWriter out, String msg) {
        out.println("<html>");
        out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'><script language = 'javascript'>");
        System.out.println("msg = > '" + msg + "'");
        if (!msg.equals("")) {
            out.println("alert('" + msg + "')");
        }
        out.println("function win_close(){");
        out.println("   top.window.opener = top;");
        out.println("   top.window.open('','_parent','');");
        out.println("   top.window.close();");
        out.println("}");
        out.println("</script>");
        out.println("</head>");
        out.println("</html>");
        out.flush();
    }

}
