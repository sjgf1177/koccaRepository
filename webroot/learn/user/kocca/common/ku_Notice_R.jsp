<%
//**********************************************************
//  1. 제      목: 공지사항 상세보기
//  2. 프로그램명 : ku_Notice_R.jsp
//  3. 개      요: 공지사항 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 05.12.22 이나연
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process    = box.getString("p_process");
    int     v_pageno     = box.getInt   ("p_pageno");
    String  v_userid     = box.getSession("userid");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int v_tabseq   = box.getInt("p_tabseq");
    int v_seq      = box.getInt("p_seq");

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
    String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자
    String v_luserid   = "";
    String v_gubun_view = "";
    String v_loginyn    = "";           // 로그인유무
    String v_useyn      = "";           // 사용유무
    String v_grcodecd   = "";           // 대상 교육그룹
    int v_popwidth   = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치

    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용

    String v_upfile    ="";
    //String v_realfile  ="";

    Vector v_realfileVector = null;
    Vector v_savefileVector = null;
    Vector v_fileseqVector  = null;

    DataBox dbox = (DataBox)request.getAttribute("selectNotice");

    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");
        v_startdate = dbox.getString("d_startdate");
        v_enddate   = dbox.getString("d_enddate");
        v_adtitle   = dbox.getString("d_adtitle");
        v_adcontent = dbox.getString("d_adcontent");
        v_addate    = dbox.getString("d_addate");
        v_adname    = dbox.getString("d_adname");
        v_luserid   = dbox.getString("d_luserid");
        v_cnt       = dbox.getInt("d_cnt");
        v_loginyn   = dbox.getString("d_loginyn");
        v_useyn     = dbox.getString("d_useyn");
        v_grcodecd  = dbox.getString("d_grcodecd");
        v_popwidth  = dbox.getInt("d_popwidth");
        v_popheight = dbox.getInt("d_popheight");
        v_popxpos   = dbox.getInt("d_popxpos");
        v_popypos   = dbox.getInt("d_popypos");
        v_popup     = dbox.getString("d_popup");
        v_upfile    = dbox.getString("d_upfile");
        //v_realfile  = dbox.getString("d_realfile");
        v_uselist   = dbox.getString("d_uselist");
        v_useframe  = dbox.getString("d_useframe");

        //v_adcontent  = StringManager.replace(data.getAdcontent(),"\n","<br>");


        v_realfileVector    = (Vector)dbox.getObject("d_realfile");
        v_savefileVector    = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector     = (Vector)dbox.getObject("d_fileseq");
     }

    if (v_gubun.equals("Y")) v_gubun_view = "전체";
    else if (v_gubun.equals("N")) v_gubun_view = "일반";
    else if (v_gubun.equals("P")) v_gubun_view = "팝업";

    if (v_uselist.equals("")) v_uselist = "N";
    if (v_useframe.equals("")) v_useframe = "N";

    if(v_loginyn.equals("Y")) v_loginyn = "로그인후";
    else if(v_loginyn.equals("N")) v_loginyn = "로그인전";
    else v_loginyn = "전체";

    if(v_useyn.equals("Y")) v_useyn = "사용";
    else if(v_useyn.equals("N")) v_useyn = "사용안함";


%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topCommon.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">

    function listnotice(){
        document.nform1.action = "/servlet/controller.homepage.KHomeNoticeServlet";
        document.nform1.submit();
    }

</SCRIPT>

<form name="nform1" method="post">
    <input type="hidden" name="p_process">
    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">

    <input type="hidden" name="p_searchtext" value="<%=v_searchtext%>">
    <input type="hidden" name="p_search"	 value="<%=v_search%>">


<!-- title -->

            <table width="680" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td  class="location" > HOME > 공지사항</td>
              </tr>
            </table>
            <table width="680" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="/images/user/kocca/common/<%=tem_subimgpath%>/tit_notice.gif"></td>
              </tr>
              <tr>
                <td height="20"></td>
              </tr>
            </table>
            <table width="680" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="88" rowspan="3"><img src="/images/user/kocca/common/i_notice.gif"></td>
                <td width="592" height="10"></td>
              </tr>
              <tr>
                <td height="11"><img src="/images/user/kocca/common/text_botice.gif"></td>
              </tr>
              <tr>
                <td height="12"></td>
              </tr>
              <tr >
                <td height="1" colspan="2" background="/images/user/kocca/common/dot_bg_9.gif"></td>
              </tr>
              <tr >
                <td height="7" colspan="2" ></td>
              </tr>
            </table>
            <!--게시판목록 -->
            <table width="680" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="6"><img src="/images/user/kocca/common/bar_noticeview_top.gif" ></td>
              </tr>
              <tr>
                <td height="2" colspan="6"></td>
              </tr>
              <tr>
                <td width="75" class="linecolor_notice01">제목</td>
                <td width="605" colspan="5" class="tbl_gleft01"><%=v_adtitle%></td>
              </tr>
              <tr>
                <td colspan="6" height="1"></td>
              </tr>
              <tr>
                <td colspan="6" height="1" class="linecolor_main01"></td>
              </tr>
              <tr>
                <td colspan="6" height="1"></td>
              </tr>
              <tr>
                <td class="linecolor_notice01">작성자</td>
                <td width="220" class="tbl_gleft01"><%=v_adname%></td>
                <td width="75" class="linecolor_notice01">작성일</td>
                <td width="110" class="tbl_gleft01"><%=FormatDate.getFormatDate(v_addate,"yyyy-MM-dd")%></td>
                <td width="75" class="linecolor_notice01" >조회수</td>
                <td width="125" class="tbl_gleft01"><%=v_cnt%></td>
              </tr>
              <tr>
                <td colspan="6" height="1" class="linecolor_main01"></td>
              </tr>
              <tr>
                <td colspan="6" height="2"></td>
              </tr>
              <tr>
                <td colspan="6" height="1" class="linecolor_main01"></td>
              </tr>
              <tr>
                <td colspan="6"><table width="680" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="24" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                      <td width="40">&nbsp;</td>
                      <td><%=v_adcontent%></td>
                    </tr>
                    <tr>
                      <td height="20" colspan="2">&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td colspan="6" height="1" class="linecolor_main01"></td>
              </tr>
              <tr>
                <td class="linecolor_notice01">첨부파일</td>
                <td>
                    <table colspan="5" border="0" valign="center" >
    <%
        if( v_realfileVector.size() > 1 ) {
            System.out.println("v_realfileVector : "+v_realfileVector);
            for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
                String v_realfile = (String)v_realfileVector.elementAt(i);
                String v_savefile = (String)v_savefileVector.elementAt(i);
                String v_fileseq = (String)v_fileseqVector.elementAt(i);

                if(v_realfile != null && !v_realfile.equals("")) {
    %>

                            <tr colspan="5" class="tbl_gleft01" ><a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>&nbsp;&nbsp;&nbsp;<%= v_realfile%></a></tr>
    <%          }
            }
             }else{  %>
                            <tr colspan="5" class="tbl_gleft02" ><td> 첨부된 파일이 없습니다 </td></tr>
    <%     }
    %>
                        </tr>
                      </table>
                    <td>
                <!--td >첨부파일명.doc</td-->
              </tr>
              <tr>
                <td height="3" colspan="6" class="tbl_bgcolor_notice"></td>
              </tr>
            </table>
            <table width="680" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="2"></td>
              </tr>
              <tr>
                <td height="27" class="linecolor_notice"><table width="52" border="0" align="right" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <!--a href="javascript:listnotice(<%%>)"><img src="/images/user/kocca/button/btn_list.gif" border="0"></a-->
<%//  if( v_userid == null || v_userid ==""){ %>
    <!--a href="/servlet/controller.homepage.KHomeNoticeServlet?p_process=moreNotice"><img src="/images/user/kocca/button/btn_list.gif" border="0"></a></td-->
<%//}else{%>
    <a href="javascript:listnotice()"><img src="/images/user/kocca/button/btn_list.gif" border="0"></a></td>
<%//}%>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td height="1" bgcolor="#cccccc"></td>
              </tr>
            </table>

</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->