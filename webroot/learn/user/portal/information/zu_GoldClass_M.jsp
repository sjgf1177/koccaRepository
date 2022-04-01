<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    
    String v_subTit = "";
    String lectureCls = box.getStringDefault("p_lecture_cls","ALL");
    String lectureClsNm = "";

    if (lectureCls.equals("GC01")) {
        lectureClsNm = "(방송영상)";
        v_subTit = lectureClsNm;

    } else if (lectureCls.equals("GC02")) {
        lectureClsNm = "(게임)";
        v_subTit = lectureClsNm;

    } else if (lectureCls.equals("GC03")) {
        lectureClsNm = "(만화애니캐릭터)";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("GC04")) {
        lectureClsNm = "(음악공연)";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("GC05")) {
        lectureClsNm = "(기타)";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("GC06")) {
        lectureClsNm = "(콘텐츠 인사이트)";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("GC07")) {
        lectureClsNm = "(마스터 클래스)";
        v_subTit = lectureClsNm;

    } else if(lectureCls.equals("GC08")) {
        lectureClsNm = "(휴넷열린강좌)";
        v_subTit = lectureClsNm;
    }

    int v_seq = 0;
    int i = 0;
    int j = 0;
    int v_viewcnt = 0;
    float v_checkpoin = 0;

    String v_vodimg = "";
    String v_lecnm = "";
    String v_tutornm = "";
    String v_lectime = "";
    String v_creator = "";
    String v_intro = "";
    String v_width_s = "";
    String v_height_s = "";
    String v_vodurl = "";
    String v_tutorimg = "";

    int v_seq2 = 0;
    String v_vodimg2 = "";
    String v_lecnm2 = "";
    String v_tutornm2 = "";
    String v_lectime2 = "";
    String v_creator2 = "";
    String v_intro2 = "";
    String v_width_s2 = "";
    String v_height_s2 = "";
    int v_viewcnt2 = 0;
    float v_checkpoin2 = 0;
    String v_vodurl2 = "";
    String v_tutorimg2 = "";

    String v_genre = box.getString("p_genre");

    ArrayList openClassList = (ArrayList)request.getAttribute("openClassList");
    ArrayList PreopenClassList = (ArrayList)request.getAttribute("PreopenClassList");

    String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

    String v_classname1 = "";
    String v_classname2 = "";

    String s_userid = box.getSession("userid");

    int totalPage = 0;
    int	totalRowCount = 0;
    int pageNo = box.getInt("pageNo"); 
    int pageSize = box.getInt("pageSize");
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

    //검색내용에 맞는 리스트 페이지로 이동
    function goPreGoldClass(){
        document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        document.form1.target="_self";
        document.form1.p_process.value = "selectPreopenClassList";
        document.form1.submit();
    }

    //상세화면 페이지로 이동
    function viewContent(seq) {
        document.form1.p_prePage.value = "Main";
        document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        document.form1.target="_self";
        document.form1.p_process.value = "selectView";
        document.form1.p_seq.value = seq;
        document.form1.submit();
    }


    function openGoldClass(seq,w,h) {

        if (w.length>0) {
            window.open("", "ViewVod", "top=0, left=0, width="+w+", height="+h+", status=no, resizable=no");

        } else {
            window.open("", "ViewVod", "top=0, left=0, width=1008, height=570, status=no, resizable=no");
        }
        document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
        document.form1.p_process.value = "popUpVod";
        document.form1.p_seq.value = seq;
        document.form1.target="ViewVod";
        document.form1.submit();
        document.form1.target="_self";
    }

    function goGoldClassMain(){
        document.form1.target="_self";
        document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        document.form1.p_process.value = "mainPage";
        document.form1.submit();
    }

    function goReply(seq) {

        window.open("about:blank", "replyList", "top=0, left=0, width=637, height=530, status=no, resizable=no");

        document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
        document.form1.p_process.value = "insertReplyPage";
        document.form1.p_seq.value = seq;
        document.form1.target="replyList";
        document.form1.submit();

        document.form1.target="_self";
    }

    function goGoldClassMain(){
        document.form1.target="_self";
        document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
        document.form1.p_process.value = "mainPage";
        document.form1.submit();
    }

    /**
     * 페이지 이동
     */
    function goPage(pageNum) {
         document.form1.pageNo.value = pageNum;
         document.form1.target="_self";
         document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
         document.form1.p_process.value = "mainPage";
         document.form1.submit();
    }

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="form1" method="post">
    <input type="hidden" name="p_process"   value="" />
    <input type="hidden" name="p_seq"       value="" />
    <input type="hidden" name="p_prePage"   value="" />
    <input type="hidden" name="pageNo"      value="<%= pageNo %>" />
    <input type="hidden" name="p_lecture_cls" value="<%= lectureCls %>" />

<%
    if( box.getSession("tem_grcode").equals("N000001")) {
        String openClassImg = "";
        String openClassAltTxt = "";
%>
<table>
    <tr>
        <td>

            <!--타이틀부분-->
            <table width="672" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="30" colspan="2" class="h_road">&nbsp;</td>
                </tr>
                <tr>
                    <td>
<%
        if(v_genre.equals("G01")) {
%>
                        <img src="/images/portal/homepage_renewal/gold/stitle_01.gif" alt="방송영상" />
<%
        } else if(v_genre.equals("G02")) {
%>
                        <img src="/images/portal/homepage_renewal/gold/stitle_02.gif" alt="영화" />
<%
        } else if(v_genre.equals("G03")) {
%>
                        <img src="/images/portal/homepage_renewal/gold/stitle_03.gif" alt="다큐멘터리" />
<%
        } else if(v_genre.equals("G04")) {
%>
                        <img src="/images/portal/homepage_renewal/gold/stitle_04.gif" alt="문화예술" />
<%
        } else if(v_genre.equals("G05")) {
%>
                        <img src="/images/portal/homepage_renewal/gold/stitle_05.gif" alt="기타" />
<%
        } else if(v_genre.equals("")) {
%>
                        <img src="/images/portal/homepage_renewal/gold/stitle_00.gif" alt="전체과정" />
<%
        }
%>
                    </td>
                    <td class="h_road">
<%
        if(v_genre.equals("G01")) {
%>
                        Home &gt; 열린강좌  &gt; <strong>방송영상</strong>
<%
        } else if(v_genre.equals("G02")) {
%>
                        Home &gt; 열린강좌  &gt; <strong>영화</strong>
<%
        } else if(v_genre.equals("G03")) {
%>
                        Home &gt; 열린강좌  &gt; <strong>다큐멘터리</strong>
<%
        } else if(v_genre.equals("G04")) {
%>
                        Home &gt; 열린강좌  &gt; <strong>문화예술</strong>
<%
        } else if(v_genre.equals("G05")) {
%>
                        Home &gt; 열린강좌  &gt; <strong>기타</strong>
<%
        } else if(v_genre.equals("")) {
%>
                        Home &gt; 열린강좌  &gt; <strong>전체과정</strong>
<%
        }
%>
                    </td>
                </tr>
                <tr>
                    <td height="12" colspan="2"></td>
                </tr>
                <tr>
                    <td height="1" colspan="2" bgcolor="E5E5E5"></td>
                </tr>
            </table>
    <!--<table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><a href="javascript:goGoldClassMain();" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image47','','/images/portal/homepage_renewal/library/tab_on_03.gif',1)"><img src="/images/portal/homepage_renewal/library/tab_on_03.gif" name="Image47" id="Image47" /></a></td>
        <td><a href="javascript:goPreGoldClass();" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image48','','/images/portal/homepage_renewal/library/tab_on_04.gif',1)"><img src="/images/portal/homepage_renewal/library/tab_off_04.gif" name="Image48" id="Image48" /></a></td>
      </tr>
    </table>
    --><!--탭부분//-->
    <!--테이블부분-->
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td height="2" colspan="3" bgcolor="#7692CB"></td>
                </tr>
                <tr>
                    <td colspan="3">&nbsp;</td>
                </tr>
<%
        if(openClassList.size() != 0){
            for(i = 0; i < openClassList.size(); i++) {
                DataBox dbox = (DataBox)openClassList.get(i);

                v_seq = dbox.getInt("d_seq");
                v_vodimg = dbox.getString("d_vodimg");
                v_lecnm = dbox.getString("d_lecnm");
                v_tutornm = dbox.getString("d_tutornm");
                v_lectime = dbox.getString("d_lectime");
                v_creator = dbox.getString("d_creator");
                v_intro = dbox.getString("d_intro");
                v_width_s = dbox.getString("d_width_s");
                v_height_s = dbox.getString("d_height_s");
                v_viewcnt = dbox.getInt("d_viewcnt");
                v_checkpoin = dbox.getFloat("d_checkpoin");
                v_vodurl = dbox.getString("d_vodurl");
                v_tutorimg = dbox.getString("d_tutorimg");
                v_intro = StringUtil.getByteString(v_intro, 200);

                if(i+1 < openClassList.size()){
                    j = i+1 ;
                    i++;
                    DataBox dbox2 = (DataBox)openClassList.get(j);

                    v_seq2 = dbox2.getInt("d_seq");
                    v_vodimg2 = dbox2.getString("d_vodimg");
                    v_lecnm2 = dbox2.getString("d_lecnm");
                    v_tutornm2 = dbox2.getString("d_tutornm");
                    v_lectime2 = dbox2.getString("d_lectime");
                    v_creator2 = dbox2.getString("d_creator");
                    v_intro2 = dbox2.getString("d_intro");
                    v_width_s2 = dbox2.getString("d_width_s");
                    v_height_s2 = dbox2.getString("d_height_s");
                    v_viewcnt2 = dbox2.getInt("d_viewcnt");
                    v_checkpoin2 = dbox2.getFloat("d_checkpoin");
                    v_vodurl2 = dbox2.getString("d_vodurl");
                    v_tutorimg2 = dbox2.getString("d_tutorimg");

                    v_intro2      = StringUtil.getByteString(v_intro, 200);
                }
%>

                <tr>
                    <td class="ct_tit_table_lright">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table width="320" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="100px">
                                                <table border="0" cellpadding="0" cellspacing="1">
                                                    <tr>
                                                        <td width="80" height="80"><img src="http://edu.kocca.or.kr/servlet/controller.library.DownloadServlet?p_savefile=<%=v_tutorimg %>" alt="강사사진" width="80px" height="100px"/></td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td align="left"><span class="btxt"><%=v_tutornm %></span><br />
                                                <a href="javascript:viewContent( <%=v_seq%>);"><strong><%=v_lecnm %></strong></a>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table width="320" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="100px">
                                                <table border="0" cellpadding="0" cellspacing="1">
                                                    <tr>
                                                        <td width="80" height="80">
<%
                if(i+1 < openClassList.size()){%>
                                                            <img src="http://edu.kocca.or.kr/servlet/controller.library.DownloadServlet?p_savefile=<%=v_tutorimg2 %>" alt="강사사진"  width="80px" height="100px"/>
<%
                } else {
%>
                                                            &nbsp;
<%
                }
%>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td align="left">
                                                <span class="btxt">
<%
                if(i+1 < openClassList.size()) {
%>
                                                <%=v_tutornm2 %></span>
                                                <br />
                                                <a href="javascript:viewContent( <%=v_seq2%>);"><strong><%=v_lecnm2 %></strong></a>
<%
                } else {
%>
                                                &nbsp;
<%
                }
%>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
<%
            }

        } else {
%>
                <tr>
                    <td>열린강좌가 없습니다.</td>
                </tr>
<%
        }
%>
            </table>

            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td height="1" bgcolor="CACACA"></td>
                </tr>
                <tr>
                    <td height="4" bgcolor="F7F7F7"></td>
                </tr>
            </table>
            <!--페이징부분--><!--타이틀부분//-->
        </td>
        <td width="20">&nbsp;</td>
    </tr>
</table>

<%
    } else {
%>

<h2><img src="/images/portal/information/h2_tit1.gif" alt="열린강좌" class="fl_l" /><p class="category">Home > <strong>열린강좌</strong><%= v_subTit %></p></h2>
<p><img src="/images/portal/information/gold_borimg.gif" alt="열린강좌는 무료로 제공되는 공개강좌로 재미와 깊이가 함께 존재하는 열린 강의입니다. 매월 훌륭한 명사의 새로운 강좌를 만나보실 수 있으며, 누구나 참여할 수 있는 열린 교육공간입니다. " /></p>
<!--
<ul class="tabwrap mg_t30">
    <li><a href="javascript:goGoldClassMain();" class="tab_information tab_information_on"><span>이달의 열린강좌</span></a></li>
    <li><a href="javascript:goPreGoldClass();" class="tab_information"><span>지난 열린강좌</span></a></li>
</ul>
//-->
<div class="info_photo_list">

<%
        if(openClassList.size() != 0){
            for(i = 0; i < openClassList.size(); i++) {
                DataBox dbox = (DataBox)openClassList.get(i);

                v_seq = dbox.getInt("d_seq");
                v_vodimg = dbox.getString("d_vodimg");
                v_lecnm = dbox.getString("d_lecnm");
                v_tutornm = dbox.getString("d_tutornm");
                v_lectime = dbox.getString("d_lectime");
                v_creator = dbox.getString("d_creator");
                v_intro = dbox.getString("d_intro");
                v_width_s = dbox.getString("d_width_s");
                v_height_s = dbox.getString("d_height_s");
                v_viewcnt = dbox.getInt("d_viewcnt");
                v_checkpoin = dbox.getFloat("d_checkpoin");
                v_vodurl = dbox.getString("d_vodurl");
                v_intro = StringUtil.getByteString(v_intro, 200);

                if ( v_lecnm.length() > 26 ) {
                    v_lecnm = v_lecnm.substring(0, 26) + "<br/>" + v_lecnm.substring(26, v_lecnm.length());
                }

               totalPage = dbox.getInt("d_totalPage");
               totalRowCount = dbox.getInt("d_totalRowCount");

%>
    <dl class="photo_list">
        <dt><a href="javascript:openGoldClass(<%=v_seq %>, '<%=v_width_s %>','<%=v_height_s %>')" class="btn_violet"><img src="/servlet/controller.library.DownloadServlet?p_savefile=<%= v_vodimg %>" width="152" height="114" alt="" /></a></dt>
        <dd class="tit_btn">
            <a href="javascript:viewContent( <%=v_seq%>);"><span class="fl_l"><%= v_lecnm %></span></a>
            <span class="fl_r">
                <a href="javascript:openGoldClass(<%=v_seq %>, '<%=v_width_s %>','<%=v_height_s %>')" class="btn_violet"><span>강좌보기</span></a>
                <a href="javascript:viewContent( <%=v_seq%>);" class="btn_gr"><span>상세보기</span></a>
            </span>
        </dd>
        <dd><span class="tit">강사명</span> <span class="con"><%=v_tutornm %></span></dd>
        <dd><span class="le"><span class="tit">강의시간</span><span class="con"><%=v_lectime %></span></span><span class="ri"><span class="tit">제작</span><span class="con"><%= v_creator %></span></span></dd>
        <dd class="ex"><span class="tit">개 요</span> <span class="con"><%=v_intro %></span></dd>
        <dd><span class="le"><span class="tit">조회수 </span> <span class="con"><%=v_viewcnt %></span></span><span class="ri"><span class="tit">평가</span><span class="con"><span class="fl_l"><%=StringUtil.getStarPoint(v_checkpoin) %></span> <a href="javascript:goReply(<%=v_seq %>)" class="board_btn2 fl_r"><span>의견달기</span></a></span></span></dd>
    </dl>
<%
            }
        } else {
%>
    <dl class="photo_list">
        <dd>이달의 열린강좌가 없습니다.</dd>
    </dl>
<%
        }
%>
    <%= PageUtil.printPageSizeListDivNoPagesize(totalPage, pageNo, 0, pageSize, totalRowCount) %>
</div>
<%
    }
%>
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->