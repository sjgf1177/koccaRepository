package com.credu.library;

import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * 제목: Alert 메시지관련 라이브러리 설명: Copyright: Copyright (c) 2004 Company: Credu
 *
 * @author 이정한
 * @date 2003. 12
 * @version 1.0
 */
public class AlertManager {

    /**
     * 메시지를 JavaScript의 alert 창으로 보여준다. history.back(-1); 이벤트 발생
     *
     * @param out PrintWriter class
     * @param msg 메시지
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
     * 저장 or 삭제시 처리가 실패했을 경우에 나오는 메시지를 JavaScript의 alert 창으로 보여준다.
     * history.back(-1); 이벤트 발생
     *
     * @param out PrintWriter class
     * @param msg 메시지
     */
    public void alertFailMessage(PrintWriter out, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n");
        sb.append("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>\n");
        sb.append("<script type='text/javascript'>\n");

        if (msg.equals("insert.fail")) {
            sb.append("alert('등록에 실패했습니다.');\n");

        } else if (msg.equals("insert.failDupe")) {
            sb.append("alert('중복된 키값으로 등록에 실패했습니다.');");
            sb.append("self.close();\n");

        } else if (msg.equals("save.fail")) {
            sb.append("alert('저장에 실패했습니다.');");

        } else if (msg.equals("update.fail")) {
            sb.append("alert('수정에 실패했습니다.');");

        } else if (msg.equals("delete.fail")) {
            sb.append("alert('삭제에 실패했습니다.');");

        } else if (msg.equals("reply.fail")) {
            sb.append("alert('등록에 실패했습니다.');");

        } else if (msg.equals("confirm.fail")) {
            sb.append("alert('신청결과 저장에 실패했습니다!');");

        } else if (msg.equals("propose.fail")) {
            sb.append("alert('신청에 실패했습니다!');");

        } else if (msg.equals("propcancel.fail")) {
            sb.append("alert('취소신청에 실패했습니다!');");

        } else if (msg.equals("approvalreport.fail")) {
            sb.append("alert('결재상신에 실패했습니다!');");

        } else if (msg.equals("approval.fail")) {
            sb.append("alert('결재처리에 실패했습니다!');");

        } else if (msg.equals("appcancel.fail")) {
            sb.append("alert('결재취소처리에 실패했습니다!');");

        } else if (msg.equals("forceprop.fail")) {
            sb.append("alert('직접입과처리에 실패했습니다!');");

        } else if (msg.equals("changeseq.fail")) {
            sb.append("alert('차수변경이 승인에 실패했습니다!');");

        } else if (msg.equals("autoassign.fail")) {
            sb.append("alert('차수배분에 실패했습니다!');");

        } else if (msg.equals("savechangeseq.fail")) {
            sb.append("alert('차수변경 처리에 실패했습니다!');");

        } else if (msg.equals("studentinsert.fail")) {
            sb.append("alert('교육생 추가에 실패했습니다!');");

        } else if (msg.equals("reject.fail")) {
            sb.append("alert('반려처리에 실패했습니다!');");

        } else if (msg.equals("studentapproval.fail")) {
            sb.append("alert('승인처리에 실패했습니다.');");

        } else if (msg.equals("studentvnogoyong.fail")) {
            sb.append("alert('변경처리에 실패했습니다.');");

        } else if (msg.equals("mail.fail")) {
            sb.append("alert('메일발송에 실패했습니다.');");

        } else if (msg.equals("scomodify.fail")) {
            sb.append("alert('진도를 나간 수강생이 있어 수정할 수 없습니다.');");

        } else if (msg.equals("scodelete.fail")) {
            sb.append("alert('과정에 매핑되어 있어 삭제할 수 없습니다.');");

        } else if (msg.equals("scodeletejindo.fail")) {
            sb.append("alert('진도를 나간 수강생이 있어 삭제할 수 없습니다.');");

        } else if (msg.equals("compcondition.fail")) {
            sb.append("alert('선택하신 회사는 이미 등록되어있습니다.');");

        } else if (msg.equals("blackcondition.fail")) {
            sb.append("alert('입력하신 내용은 이미 등록되어있습니다.');");

        } else if (msg.equals("opinioninsert.fail")) {
            sb.append("alert('입력하신 내용은 이미 등록되어있습니다. 의견 수정은 [동료학습자 의견보기]로 이동하여 수정하시기 바랍니다.');");
            sb.append("self.close();");

        } else if (msg.equals("subjApplyTimeOver.no")) {
            sb.append("alert('수강 신청 기간이 아닙니다.');");
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
     * 교육서비스 만족도 조사 성공 시 확인 메세지 보여주고 창을 닫는다.
     *
     * @param out PrintWriter class
     * @param msg 메시지
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
     * 저장 or 삭제시 제대로 처리되었을 경우에 나오는 메시지를 JavaScript의 alert 창으로 보여준다.
     * isOpenWin/isClosed/isHomepage 가 모두 false 로 세팅된다.
     *
     * @param out PrintWriter class
     * @param msg 메시지
     * @param url 이동할 페이지 URL
     * @param box RequestBox class
     */
    public void alertOkMessage(PrintWriter out, String msg, String url, RequestBox box) {
        this.alertOkMessage(out, msg, url, box, false, false, false, false);
    }

    /**
     * 저장 or 삭제시 제대로 처리되었을 경우에 나오는 메시지를 JavaScript의 alert 창으로 보여준다. isHomepage 가
     * 모두 false 로 세팅된다.
     *
     * @param out PrintWriter class
     * @param msg 메시지
     * @param url 이동할 페이지 URL
     * @param box RequestBox class
     */
    public void alertOkMessage(PrintWriter out, String msg, String url, RequestBox box, boolean isOpenWin, boolean isClosed) {
        this.alertOkMessage(out, msg, url, box, isOpenWin, isClosed, false, false);
    }

    /**
     * 저장 or 삭제시 제대로 처리되었을 경우에 나오는 메시지를 JavaScript의 alert 창으로 보여준다. isHomepage 가
     * 모두 false 로 세팅된다.
     *
     * @param out PrintWriter class
     * @param msg 메시지
     * @param url 이동할 페이지 URL
     * @param box RequestBox class
     */
    public void alertOkMessage(PrintWriter out, String msg, String url, RequestBox box, boolean isOpenWin, boolean isClosed, boolean isHomepage) {
        this.alertOkMessage(out, msg, url, box, isOpenWin, isClosed, isHomepage, false);
    }

    /**
     * 저장 or 삭제시 제대로 처리되었을 경우에 나오는 메시지를 JavaScript의 alert 창으로 보여준다.
     *
     * @param out PrintWriter class
     * @param msg 메시지
     * @param url 이동할 페이지 URL
     * @param box RequestBox class
     * @param isOpenWin 현재 창이 Openwindow 인가 여부
     * @param isClosed Openwindow 창이 close 될지 여부
     * @param isHomepage Homepage에서 운영본부를 열었는지 여부
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
            out.println("alert('등록 되었습니다.');");

        } else if (msg.equals("save.ok")) {
            out.println("alert('저장 되었습니다.');");

        } else if (msg.equals("update.ok")) {
            out.println("alert('수정 되었습니다.');");

        } else if (msg.equals("delete.ok")) {
            out.println("alert('삭제 되었습니다.');");

        } else if (msg.equals("reply.ok")) {
            out.println("alert('등록 되었습니다.');");

        } else if (msg.equals("scomodify.ok")) {
            out.println("alert('수정되었습니다.');");

        } else if (msg.equals("confirm.ok")) {
            out.println("alert('신청결과가 저장되었습니다.');");

        } else if (msg.equals("propose.ok")) {
            out.println("alert('장바구니에 저장 되었습니다. 나의강의실->장바구니에서 결제하기를 진행하셔야 수강이 완료됩니다.');");

        } else if (msg.equals("propose.ok.zero")) {
            out.println("alert('수강신청이 완료 되었습니다.');");

        } else if (msg.equals("offpropose.ok")) {
            //out.println("alert('신청이 완료 되었습니다. \\n1차 승인후 나의 강의실->수강료결제 조회/납부에서 결제하기를 진행하셔야 수강이 완료됩니다');");
            out.println("alert('신청이 완료 되었습니다. ');");

        } else if (msg.equals("propcancel.ok")) {
            out.println("alert('취소신청 되었습니다.');");

        } else if (msg.equals("propcancel.delete.ok")) {
            out.println("alert('취소 되었습니다.');");

        } else if (msg.equals("approvalreport.ok")) {
            out.println("alert('결재상신 되었습니다.');");

        } else if (msg.equals("approval.ok")) {
            out.println("alert('결재처리 되었습니다.');");

        } else if (msg.equals("appcancel.ok")) {
            out.println("alert('결재취소처리 되었습니다.');");

        } else if (msg.equals("forceprop.ok")) {
            out.println("alert('입과처리 되었습니다.');");

        } else if (msg.equals("changeseq.ok")) {
            out.println("alert('차수변경이 승인되었습니다.');");

        } else if (msg.equals("autoassign.ok")) {
            out.println("alert('차수배분에 성공하였습니다.');");

        } else if (msg.equals("savechangeseq.ok")) {
            out.println("alert('차수변경 처리 되었습니다.');");

        } else if (msg.equals("studentinsert.ok")) {
            out.println("alert('교육생 추가 되었습니다.');");

        } else if (msg.equals("reject.ok")) {
            out.println("alert('반려처리 되었습니다.');");

        } else if (msg.equals("studentapproval.ok")) {
            out.println("alert('승인처리 되었습니다.');");

        } else if (msg.equals("studentvnogoyong.ok")) {
            out.println("alert('변경처리 되었습니다.');");

        } else if (msg.equals("pregrseq.no")) {
            out.println("alert('이전 차수에 무료과정을 미수료하여 수강 신청 할 수 없습니다.');");

        } else if (msg.equals("nowgrseq.no")) {
            out.println("alert('현재 5개 과정 신청하셔서 추가 수강신청이 불가능합니다.\\n(차수별 1인 최대 5개 과정까지 신청 가능)\\n\\n※ 다음 차수에 수강신청해주세요. (매월 1일, 15일 차수 오픈)');");

            // out.println("alert('해당 차수에 신청 가능한 무료과정 수를 초과하였습니다.\\n(1인 3과목 신청 가능)');");
        } else if (msg.equals("nowgrseq2.no")) {
            out.println("alert('해당 차수에 신청 가능한 무료과정 수를 초과하였습니다.');");

        } else if (msg.equals("nowgrseq3.no")) {
            out.println("alert('해당 차수에 신청 가능한 정원수를 초과하였습니다.');");

        } else if (msg.equals("subjApplyTimeOver.no")) {
            out.println("alert('수강 신청 기간이 아닙니다.');");

        } else if (msg.equals("mail.ok")) {
            String v_mailcnt = box.getString("p_mailcnt");
            out.println("alert('" + v_mailcnt + "명에게 메일이 발송되었습니다.');");

        } else if (!msg.equals("")) {
            out.println("alert('" + msg + "');");
        }

        if (!isOpenWin) { //      openwindow 가 아닌경우
            //  System.out.println(box);
            out.println("document.form1.action = '" + url + "'");
            out.println("document.form1.submit()");
        } else if (isOpenWin && isClosed && !isHomepage && !isReload) { //      openwindow 가 close 되는 경우
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
        } else if (isOpenWin && !isClosed) { //      openwindow 가 계속열려져있는 경우
            out.println("document.form1.target = window.opener.name");
            out.println("document.form1.action = '" + url + "'");
            out.println("document.form1.submit()");
        } else if (isOpenWin && isClosed && isHomepage) { //      Homepage에서 운영본부를 열은 경우
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
     * 메시지를 JavaScript의 alert 창으로 보여준다. self.close(); 이벤트 발생
     *
     * @param out PrintWriter class
     * @param msg 메시지
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
     * 메시지를 JavaScript의 alert 창으로 보여준다. self.close(); 이벤트 발생
     *
     * @param out PrintWriter class
     * @param msg 메시지
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
