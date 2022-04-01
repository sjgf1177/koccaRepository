<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_cmuno      = box.getString("p_cmuno");
    String  v_pollno     = box.getString("p_pollno");
    String  v_brd_fg      = box.getString("p_brd_fg");  

    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    int     v_pageno     = box.getInt("p_pageno");

    int v_userpollcnt    = Integer.parseInt(String.valueOf(request.getAttribute("userpollcnt")));
    ArrayList list       = (ArrayList)request.getAttribute("list");
    ArrayList list1    =new ArrayList();
    ArrayList list2=new ArrayList();
    ArrayList list3=new ArrayList();
    ArrayList list4=new ArrayList();

    String  v_title      = "";
    String  v_fdte       = "";
    String  v_tdte       = "";
    String  v_background = "";
    String  v_str_fg     = "";
    String  v_make_nm    = "";
    String  v_email      = "";
    String v_pool_status = "";
    String v_apply_yn    = "";
    int    v_tot_poll_cnt = 0;
    
    if(list.size() != 0){
       list1       = (ArrayList)list.get(0);  //리스트
       list2       = (ArrayList)list.get(1);  //항목
       list3       = (ArrayList)list.get(2);  //상세필드
       list4       = (ArrayList)list.get(3);  //응답결과

    }
    if(list1.size() != 0){
      DataBox dbox1 = (DataBox)list1.get(0);
	    v_title      = dbox1.getString("d_title");
	    v_fdte       = dbox1.getString("d_fdte");
	    v_tdte       = dbox1.getString("d_tdte");
	    v_background = dbox1.getString("d_background");
	    v_make_nm    = dbox1.getString("d_name");
	    v_str_fg     = dbox1.getString("d_str_fg");
	    v_email      = dbox1.getString("d_email");
	    v_pool_status = dbox1.getString("d_poll_status");
	    v_apply_yn    = dbox1.getString("d_apply_yn");
	    v_tot_poll_cnt = dbox1.getInt("d_tot_poll_cnt");
    }
    
    ArrayList replyList       = (ArrayList)request.getAttribute("replyList");
    
    // 질문항목
    DataBox dbox2 = null;
    String v_questno            = "";
    String v_need_question      = "";
    String v_need_fg            = "";
    String v_re_gen_fg          = "";
    String v_item_cnt           = "";
    String v_register_userid    = "";
    String v_register_dte       = "";
    String v_modifier_userid    = "";
    String v_modifier_dte       = "";
    
    if(list2.size() != 0 ){
    	dbox2 = (DataBox)list2.get(0);
    	
    	v_questno            = dbox2.getString("d_questno");
   		v_need_question      = dbox2.getString("d_need_question");
   		v_need_fg            = dbox2.getString("d_need_fg");
   		v_re_gen_fg          = dbox2.getString("d_re_gen_fg");
   		v_item_cnt           = dbox2.getString("d_item_cnt");
    }
    
    
    // 답변항목(선택형)
    String v_fieldno            = "";
    String v_field_name         = "";
    String v_poll_cnt           = "";
    String v_etc_fg             = "";
    String v_etc_nm             = "";
    
    // 답변항목(단답형)
    String v_userid             = "";
    String v_sel_text           = "";
    String v_question           = "";
    String v_shot_text          = "";


%>  
  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//등록페이지로이동
function uf_moveInsertPage() {

    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "movewirtePage";
    document.form1.submit();
}

//답변등록
function uf_replyData() {
	<% if(v_apply_yn.equals("Y")){%>
    alert("이미 투표에 참여해주셨으므로\r\n다시 투표하실수 없습니다.");
    return;
	<% } else { %>
    if(uf_validcheck() == -1) return;

    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "replyData";
    document.form1.submit();

    <% } %>
    
}

function uf_validcheck(){

	<%  if(!v_re_gen_fg.equals("4")){ %>
    
     var checkObjAry = document.getElementsByName("p_fieldno<%=dbox2.getString("d_questno")%>a");
     var checkObjLen = checkObjAry.length;
     var checkCnt = 0;
     for(var i = 0 ; i < checkObjLen ; i ++) {
    	    if(checkObjAry[i].checked){
        	    checkCnt++;
    	    }
     }

    if(checkCnt == 0) {
    	<%  if(v_re_gen_fg.equals("1")){  %>
        alert("반드시 하나의 항목을 선택하셔야 합니다.");
        <%  } else if(v_re_gen_fg.equals("2")){ %>
        alert("반드시 하나 이상의 항목을 선택하셔야 합니다.");
        return false;
        <%  }  %>
    }
    <%  } else { %>

    <%  }  %>

    return true;
}

//저장
function uf_updateData(questno) {

    document.form1.p_questnotmp.value = questno;
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "moveupdatePage";
    document.form1.submit();
}


//리스트페이지로이동
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "movelistPage";
    document.form1.submit();
}

//삭제
function wf_deleteOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "deleteData";
    document.form1.submit();
}

//설문결과페이지
function uf_resultviewOK(pollno,rowseq) {
    document.form1.p_pollno.value = pollno;
    document.form1.p_rowseq.value = rowseq;
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "moveresultviewPage";
    document.form1.submit();
}

//예시 페이지로 이동
function uf_msSamplePage() {

   open_window('movesamplePage', '/servlet/controller.community.CommunityFrPollServlet?p_process=movesamplePage', 100, 100, 670, 480, 'no', 'no', 'no', 'no', 'no');
  
}

//댓글등록하기
function uf_insertMemoOK() {
    if(document.form1.p_rep_content.value ==''){
       alert('댓글 내용을 입력하여야합니다.');document.form1.p_rep_content.focus();return;
    }

    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "insertMemoData";
    document.form1.submit();
}

//댓글삭제하기
function uf_deleteMemoOK(rplno) {
    document.form1.p_rplno.value = rplno;
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "deleteMemoData";
    document.form1.submit();
}

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    enctype = "multipart/form-data"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_cmuno"       value="<%=v_cmuno%>">
<input type = "hidden" name = "p_pollno"      value="<%=v_pollno%>">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_questnotmp"      value = "">
<input type = "hidden" name = "p_rowseq"      value = "">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"      value = "<%=v_select%>">
<input type = "hidden" name = "p_brd_fg"       value = "<%=box.getString("p_brd_fg")%>">
<input type = "hidden" name = "p_rplno"       value = "">
<input type = "hidden" name = "p_return_process"       value = "<%=v_process %>">

            <h1><img src="/images/portal/community/tit_05.gif" alt="설문조사" /></h1>

            <table class="study_view">
            <!--[if ie]><colgroup><col width="40px" /><col /><col width="40px" /><col width="80px" /><col width="40px" /><col width="80px" /><col width="50px" /><col width="80px" /></colgroup><![endif]-->
            <colgroup><col width="60px" /><col /><col width="60px" /><col width="80px" /><col width="60px" /><col width="80px" /><col width="70px" /><col width="80px" /></colgroup>
            <tr>
                <th>제목</th>
                <td colspan="7" class="tit"><%=v_title %></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%=v_make_nm%></td>
                <th>시작일</th>
                <td class="num"><%=FormatDate.getFormatDate(v_fdte, "yyyy.MM.dd")%></td>
                <th>종료일</th>
                <td class="num"><%=FormatDate.getFormatDate(v_tdte, "yyyy.MM.dd")%></td>
                <th>응답자수</th>
                <td class="num"><%=v_tot_poll_cnt %></td>
            </tr>
            <tr>
                <td colspan="8" class="con"><%=v_background%></td>
            </tr>
            </table>

            <div class="poll_box">
			    <dl class="q">
                    <dt>
	                    <%=dbox2.getString("d_need_question")%>
	                    <input type=hidden name="p_re_gen_fg" value="<%=dbox2.getString("d_re_gen_fg")%>" />
	                    <input type=hidden name=p_questno value="<%=dbox2.getString("d_questno")%>" />
                    </dt>
                    <%
						    for(int j=0;j<list3.size();j++){
				                DataBox dbox3 = (DataBox)list3.get(j);
					%>
                    <dd><p class="txt"><input type="radio" name="p_fieldno<%=dbox2.getString("d_questno")%>a" id="p_fieldno<%=dbox2.getString("d_questno")%>a" value="<%=dbox3.getString("d_fieldno")%>" /><%=dbox3.getString("d_field_name")%></p></dd>
					<%      } %>
                </dl>
                <p class="cboth board_btn"><a href="javascript:uf_replyData();" class="btn_gr"><span>투표하기</span></a><a href="javascript:uf_resultviewOK(<%=v_pollno%>,1);" class="btn_gr"><span>결과보기</span></a></p>
            </div>
            
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->