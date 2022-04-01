<%
//**********************************************************
//  1. 제      목: 지식공유 인기지식
//  2. 프로그램명 : gu_KnowCom_L.jsp
//  3. 개      요: 지식공유 인기지식- 최다댓글 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.03
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","05");
    String v_process= box.getString("p_process");
    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_select      = box.getString("p_select");

    int v_seq = 0, v_readcnt = 0,  i = 0,  v_cnt = 0;
    String  v_inuserid    = "", v_position = "", v_indate = "", v_title = "", tLink = "",  v_types = "" ,v_name="";
    String  v_categorycd     = box.getString("p_categorycd");
    String  v_cateSelect     = box.getString("p_cateSelect");

    String s_userid          = box.getSession("userid");
    String s_usernm          = box.getSession("name");
    String v_classname1      = "";
    String v_classname2      = "";

    ArrayList list = (ArrayList)request.getAttribute("ComListPage");
        if((request.getAttribute("SelectListCate"))!=null){
        list = (ArrayList)request.getAttribute("SelectListCate");
    }else{}

        // 카테고리 가져오기
    ArrayList knowlist2 = KnowBoardUserBean.SelectMenuCategoryTreeList(box);
    String v_viewname  = "";
    String v_subjclass = "";
    String v_level     = "";
    int    v_subcnt    = 0;

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMystudy.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
//입력페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "InsertPage";
    //document.form1.p_pageno.value = "<%= v_pageno %>";
    document.form1.submit();
}

//상세화면 페이지로 이동
function select(seq, upfilecnt, userid, types) {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.p_userid.value    = userid;
    document.form1.p_types.value     = types;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.submit();
}

//인기지식 페이지 이동
function MoveList(gubun) {
    document.form1.p_pageno.value = "1";
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = gubun;
    document.form1.submit();
}

//검색내용에 맞는 리스트 페이지로 이동
function selectList(tab) {

    if (form1.p_searchtext.value == ""){    // 조회하기
        alert("검색 할 내용을 입력해 주세요");
    }else{
        document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
        document.form1.p_process.value = "ComListPage";
        document.form1.p_pageno.value = "1";
        document.form1.submit();
        showTab(tab);
    }
}

// 카테고리별 보기
function cateSelectList(tab){

    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_gubun.value = tab;
    document.form1.p_pageno.value = "1";
    document.form1.p_process.value = "selectPage";
    document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ComListPage";
    document.form1.submit();
}

// Tab 선택시 동작
function showTab(clickTab)
{
   //alert(clickTab);
    g_showTab(clickTab, 2);
}

function g_showTab(tabNo, count)
{

    var clickTabNo = new Number(tabNo);
    var tabCount   = new Number(count);

    if( clickTabNo == 0 || tabCount == 0 ) return;

    for(i=1; i<=tabCount; i++)
    {
        div = document.all["tab_"+i];

        if(!div) return;

        if( clickTabNo == i ) {
            // 활성화된 tab 클릭시 return
            if(div.style.display == "") return;
            div.style.display = ""
        }
        else {
            // 선택되지 않는 탭들은 깜빡임 방지.
            if(div.style.display != "none") {
                div.style.display = "none";
            }
        }
    }
}
//-->
</script>

          <!-- 게시판 시작 -->
        <form name = "form1" method = "post">
          <input type="hidden" name="p_process"    value = "<%= v_process %>">
          <input type="hidden" name="p_pageno"     value = "<%= v_pageno %>">
          <input type = "hidden" name = "p_seq"         value = "">
          <input type = "hidden" name = "p_userid"      value = "">
          <input type = "hidden" name = "p_upfilecnt"   value = "">
          <input type = "hidden" name = "p_types"       value = "">
          <input type = "hidden" name = "p_categorycd"  value = "">
          <input type = "hidden" name = "p_gubun"       value = "3">

<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="720" height="35" align="right"  background="/images/user/game/mystudy/<%=tem_subimgpath%>/tit_joint.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> HOME > 나의 공부방 > 지식공유게시판</td>
  </tr>
  <tr>
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<!-- 과정조회 -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="7"><img src="/images/user/game/mystudy/st_ingi.gif"></td>
  </tr>
  <tr>
    <td colspan="7" height="15"></td>
  </tr>
  <tr>
    <td width="1">&nbsp;</td>
    <td width="104">
        <a href="javascript:MoveList('PopListPage');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','/images/user/game/mystudy/tab_qu01_on.gif',1)"><img src="/../images/user/game/mystudy/tab_qu01.gif" name="Image31" border="0"></a>
    </td>
    <td width="104">
        <a href="javascript:MoveList('ComListPage');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image32','','/images/user/game/mystudy/tab_qu02_on.gif',1)"><img src="/images/user/game/mystudy/tab_qu02.gif" name="Image32" width="104" height="31" border="0"></a>
    </td>
    <td width="104">
        <img src="/images/user/game/mystudy/tab_qu03_on.gif" width="104" height="31">
    </td>

    <td width="178"></td>
    <td width="91"><img src="/images/user/game/mystudy/text_catagory_01.gif"></td>
    <td width="138" align="center">
        <select name="p_cateSelect" onchange="cateSelectList(3);">
        <%  for (int jj=0; jj<knowlist2.size(); jj++){
              DataBox kdbox = (DataBox)knowlist2.get(jj);
              v_viewname    = kdbox.getString("d_viewname");
              v_subjclass   = kdbox.getString("d_subjclass");
              v_level       = kdbox.getString("d_levels");
              v_subcnt      = kdbox.getInt("d_subcnt");
              if( v_subcnt == 0 ){

        %>
            <option value="<%=v_subjclass%>" <%if(v_cateSelect.equals(v_subjclass)){out.println("selected");}%>><%=v_viewname%></option>
        <%      }
            }

        %>
        </select>
    </td>
  </tr>
  <tr>
    <td height="5" colspan="7"></td>
  </tr>
  <tr>
    <Td colspan="7"><img src="/images/user/game/mystudy/g_back.jpg"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <%
  if(list.size() != 0){
    DataBox dbox = (DataBox)list.get(0);

  %>
    <tr>
        <td width="15" rowspan="3"></td>
        <Td width="87" rowspan="3"><img src="/images/user/game/mystudy/photo_best_search.gif"></td>
        <td width="13" rowspan="3"></td>

    <td width="525" class="tbl_gleft05">[최다댓글지식] <%=dbox.getString("d_title")%></td>
        <td width="80"><div align="right">
            <a href="javascript:select('<%=dbox.getInt("d_seq")%>','<%=dbox.getInt("d_filecnt")%>','<%=dbox.getString("d_inuserid")%>','<%=dbox.getString("d_types")%>')"><img src="/images/user/game/button/btn_moreview.gif"></a>
        </div></td>
    </tr>
    <tr>
        <td height="1" colspan="2" background="/images/user/game/mystudy/bg_dotline_3.gif"></td>
    </tr>
    <tr>
        <td colspan="2" class="tbl_gleft"><%=dbox.getString("d_contents")%></td>
    </tr>
    <tr>
        <Td height="20" colspan="5"></td>
    </tr>
</table>
  <%}%>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="4" height="27" valign="top" background="/images/user/game/mystudy/ba_bg02.gif"><img src="/images/user/game/mystudy/ba_f.gif"></td>
    <td width="712" align="center" valign="bottom" background="/images/user/game/mystudy/ba_bg02.gif">
      <table width="710" height="23" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="38" align="center"  ><img src="/images/user/game/mystudy/text_t_num02.gif"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="75" align="center"  ><img src="/images/user/game/mystudy/text_t_bun.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="350" align="center" ><img src="/images/user/game/mystudy/text_t_subject.gif"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="77" align="center"  ><img src="/images/user/game/mystudy/text_t_writer.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="80" align="center"  ><img src="/images/user/game/mystudy/text_t_openday.gif"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="49" align="center"  ><img src="/images/user/game/mystudy/text_add_file.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="35" align="center"  ><img src="/images/user/game/mystudy/text_t_rec.gif"></td>
        </tr>
      </table></td>
    <td width="4" align="right" valign="top" background="/images/user/game/mystudy/ba_bg02.gif"><img src="/images/user/game/mystudy/ba_tail.gif" ></td>
  </tr>
  <tr>
    <td height="5" colspan="3"></td>
  </tr>
  <tr class="linecolor_board3">
    <td height="1" colspan="3"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="10" colspan="7"></td>
  </tr>
<%if(list.size() != 0){%>
<%
            for(i = 0; i < list.size(); i++) {
                   DataBox dbox = (DataBox)list.get(i);
                   v_dispnum    = dbox.getInt("d_dispnum");
                   v_inuserid   = dbox.getString("d_inuserid");
                   v_name       = dbox.getString("d_name");
                   v_cnt        = dbox.getInt("d_cnt");
                   v_indate     = dbox.getString("d_indate");
                   v_title      = dbox.getString("d_title");
                   v_seq        = dbox.getInt("d_seq");
                   v_types      = dbox.getString("d_types");
                   v_readcnt    = dbox.getInt("d_readcnt");
                   v_upfilecnt  = dbox.getInt("d_filecnt");
                   v_totalpage  = dbox.getInt("d_totalpage");
                   v_rowcount   = dbox.getInt("d_rowcount");


                    // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.

                    if (!v_searchtext.equals("")&&v_select.equals("title")) {
                        v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                    } else if(!v_searchtext.equals("")&&v_select.equals("name")){
                        v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                    }

%>
  <tr>
    <td width="44" class="tbl_grc"><%=v_dispnum%></td>
    <td width="76" class="tbl_grc"><%=dbox.getString("d_categorynm")%></td>
    <td width="350" class="tbl_bleft">
        <%if (Integer.parseInt(v_types) > 0) {%>
            &nbsp;&nbsp;&nbsp;&nbsp;<IMG src="/images/user/support/ico_re.gif" border="0">
        <% } %>

        <a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_inuserid%>','<%=v_types%>')"><%=v_title%></a>

        <%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){%>
            <IMG src="/images/user/game/button/btn_new.gif" border="0">
        <% } %></td>
    <td width="78" class="tbl_grc"><%=v_name%></td>
    <td width="82" class="tbl_grc"><%=FormatDate.getFormatDate(v_indate, "yyyy/MM/dd")%></td>
    <td width="50" class="tbl_grc">
        <% if( v_upfilecnt>0){ %>
        <img src="/images/user/game/mystudy/i_add.gif" width="13" height="13">
        <%}else{%> - <%}%>
    </td>
    <td width="40" class="tbl_grc"><%=v_cnt%></td>
  </tr>
  <tr>
    <td height="1" colspan="7" class="linecolor_board4"></td>
  </tr>
<%}%>
<%}else{%>

  <tr>
    <td colspan="13" align=center valign=top>등록 된 질문이 없습니다.
    </td>
  </tr>
<%}%>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="2" class="lcolor"></td>
  </tr>
  <tr>
    <td height="2" class="linecolor_board3"></td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
  <tr>
    <td align="center" valign="bottom">
    <table width="720" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="650" align="center">
              <table width="540" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td>
                    <%=PageUtil.printPageListGame(v_totalpage, v_pageno, row) %>
                    </td>
                  </tr>
              </table>
            </td>
            <td width="70"><img src="/images/user/game/button/btn_edu_input.gif" ></td>
        </tr>
      </table>
      <!-- page -->
    </td>
  </tr>
  <tr>
    <td height="5" ></td>
  </tr>
  <tr>
    <td height="2"class="linecolor_board3"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="10" colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td width="238">&nbsp;</td>
    <td width="90"> <div align="center">
        <select name="p_select" class="input2">
          <option value="title"  >:: 제 목 </option>
          <option value="content">:: 내 용 </option>
          <option value="name"   >:: 이 름 </option>
        </select>
      </div></td>
    <td width="105"> <div align="left">
        <input name="p_searchtext" type="text" class="input2" size="15" maxlength="30">
      </div></td>
    <td width="48"><a href="javascript:selectList()"><img src="/images/user/game/button/btn_j.gif" width="48" height="21"></td>
    <td width="273">&nbsp;</td>
  </tr>
</table>
</form>



<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->

