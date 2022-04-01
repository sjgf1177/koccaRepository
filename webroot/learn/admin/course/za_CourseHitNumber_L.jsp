<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");    //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  s_grseq   = box.getString("s_grseq");          //과정 차수
    String  s_subjsearchkey   = box.getString("s_subjsearchkey");          //과정명

    String s_biyong=box.getString("s_biyong");          //유무료
    String s_area=box.getString("s_area");          //교육구분
    //DEFINED in relation to select END

    String  v_gyear ="", v_grseq="", v_course="", v_cyear="", v_courseseq="", v_courseStr="";
    String  v_subj="",v_year="",v_subjseq="";
    String  v_subjnmlink = "";
    String  v_precourse = "";
    String s_gadmin = box.getSession("gadmin");
    String v_gadmin = StringManager.substring(s_gadmin, 0, 1);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language="JavaScript">

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}

// 셀렉트 선택후 검색
function whenSelection(ss_action) {
    document.form1.p_process.value="select";
    document.form1.p_action.value = ss_action;

	if (ss_action=="go")    {
		if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----' || document.form1.s_grcode.value == '') {
			alert("교육그룹을 선택하세요.");
			return ;
		}
		if (document.form1.s_gyear.value == '') {
			alert("연도를 선택하세요.");
			return ;
		}
		top.ftop.setPam();
	}

    document.form1.submit();
}

function elementForm() {
  var form = document.form1;
  var s_url = "";
  s_url += "&s_upperclass="+form.s_upperclass.value;
  s_url += "&s_middleclass="+form.s_middleclass.value;
  s_url += "&s_lowerclass="+form.s_lowerclass.value;
  s_url += "&s_subjsearchkey="+form.s_subjsearchkey.value;
  s_url += "&s_subjcourse="+form.s_subjcourse.value;
  return s_url;
}

// 조건체크
function chkSelected() {
  var selectedcnt = 0;
  var form = document.form1;
  if(form.all['p_checks'] == '[object]') {
    if (form.p_checks.length > 0) {
      for (i=0; i<form.p_checks.length; i++) {
        if (form.p_checks[i].checked == true) {
          selectedcnt++;
        }
      }
    } else {
      if (form.p_checks.checked == true) {
        selectedcnt++;
      }
    }
  }

  return selectedcnt;
}

    function fnSave()
    {
        var r=$("#tbl1");
        var str="";

        var count=$("tr",r).length;
        for(var i=2;i<count;i++)
        {
            var r1=$("tr:eq("+i+")",r);
            var tmp=$("input:eq(0)",r1).val();
            var tmp1=$("input:eq(1)",r1).val();
            if(tmp!="")
                str+=tmp+"@"+tmp1+",";
        }

        str=str.substr(0,str.length-1);
        
        document.form1.p_process.value="update";
        $("[name='p_update']").val(str);
        document.form1.submit();
    }
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.course.HitNumberAdminServlet">
        <input type="hidden" name="p_process" value="listPage">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_grcode" value="<%=ss_grcode%>">
        <input type="hidden" name="p_mgyear" value="">
        <input type="hidden" name="p_mgrseq" value="">
        <input type="hidden" name="p_msubjcourse" value="">
        <input type="hidden" name="p_myear" value="">
        <input type="hidden" name="p_mseq" value="">

        <input type="hidden" name="p_subj" value="">
        <input type="hidden" name="p_subjnm" value="">
        <input type="hidden" name="p_update" value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/course/c_title0314.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>
        <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">

            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">

                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="27%" colspan="2" align="left" valign="middle">
                      	<table border="0" cellspacing="0" cellpadding="0" width="99%" class="_srchT">
                        <!------------------- 조건검색 시작 ------------------------->
						<script type="text/javascript">
							var startup = 0;
							function Main_s_subjseq(){
								if (startup > 1)
									changes_grseq(document.all.s_grcode.value, document.all.s_gyear.value);
							}
							function Main_subjcourse(){
								if(startup > 1)
									changes_subjcourse(document.all.s_grcode.value, document.all.s_upperclass.value, document.all.s_middleclass.value, document.all.s_lowerclass.value, document.all.s_grseq.value,document.all.s_subjsearchkey.value );
								startup++;
							}
						</script>
					<tr>
                        <td width="8%" class="_tdT"><font color="red">★</font>교육그룹</td>
						<td width="17%" class="_tdS"><kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "
							onChange="changes_gyear(this.value);" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
						</td>
                      	<td width="5%" class="_tdT">연도</td>
                      	<td width="13%" class="_tdS"><kocca_select:select name="s_gyear" sqlNum="course.year"  param="<%= ss_grcode %>"
		onChange="changes_grseq(s_grcode.value, this.value);" afterScript="Main_subjcourse" attr=" " selectedValue="<%= ss_gyear %>" isLoad="true" all="false" />
						</td>
						<td width="8%" class="_tdT">교육차수</td>
						<td width="24%" class="_tdS"><kocca_select:select name="s_grseq" sqlNum="course.subjseq"
		param="<%=ss_grcode%>" param2="<%=ss_gyear%>"
		attr=" style='width:95%;'" onChange="" afterScript="Main_subjcourse" selectedValue="<%= s_grseq %>" isLoad="true" all="true" />
						</td>
					</tr>
					<tr>
						<td width="8%" class="_tdT">대분류</td>
						<td width="17%" class="_tdS"><kocca_select:select name="s_upperclass" sqlNum="course.0002"  param=" "
		onChange="changes_middleclass(this.value);Main_subjcourse();" attr=" "  selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
						</td>
						<td class="_tdT">중분류</td>
						<td class="_tdS"><kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>"
		onChange="changes_lowerclass(s_upperclass.value, this.value);Main_subjcourse();" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
						</td>
						<td class="_tdT">소분류</td>
						<td class="_tdS"><kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
		onChange="whenSelection('go')" attr=" " selectedValue="0000" isLoad="true" all="true" />
						</td>
                    </tr>
                    <tr>
                    	<td width="8%" class="_tdT">과정검색</td>
                        <td class="_tdS">
                        	<input type="text" name="s_subjsearchkey" size="17" onkeypress="searchSubjnmKeyEvent(event)" value="<%=box.getString("s_subjsearchkey")%>">
							<a href="javascript:goSearchSubjnm()" ><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>
                      	</td>
                      	<input type="hidden" name="p_returnurlkey" value="<%=request.getRequestURI()%>">
						<script>
							document.form1.s_subjsearchkey.focus();
							function searchSubjnmKeyEvent(e){
								if (e.keyCode =='13'){  goSearchSubjnm();  }
							}
							function goSearchSubjnm(){
								if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
									alert("교육그룹을 선택하세요.");
									return;
								}
								document.form1.action='/servlet/controller.study.SearchSubjNameServlet';
								document.form1.p_process.value = 'SearchSubjnmKey';
								document.form1.submit();
							}
						</script>
                      	<td width="8%" class="_tdT">과정</td>
                      	<td colspan="4" class="_tdS"><kocca_select:select name="s_subjcourse" sqlNum="subjlist1|subjlist2|subjlist3|subjlist4|subjlist5|subjlist6|subjlist7|subjlist8|subjlist9"
				param="<%=ss_grcode%>"param4="<%=ss_upperclass%>"param5="<%=ss_middleclass%>"param6="<%=ss_lowerclass%>"
				param7="<%=s_grseq%>" param9="<%=s_subjsearchkey%>"
				onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_subjcourse %>" isLoad="true" all='<%= box.getSession("isSubjAll") %>' />
						</td>
					</tr>
                    <tr>
                        <td width="8%" class="_tdT">유/무료</td>
                        <td width="17%" class="_tdS">
                            <select name="s_biyong">
                                <option value="A" <% if(s_biyong.equals("A")) out.print("selected");%>>전체</option>
                                <option value="Y" <% if(s_biyong.equals("Y")) out.print("selected");%>>유료</option>
                                <option value="N" <% if(s_biyong.equals("N")) out.print("selected");%>>무료</option>
                            </select> 
                        </td>
                        <td class="_tdT">교육구분</td>
                        <td class="_tdS" colspan="4">
                            <select name="s_area">
                                <option value="T" <% if(s_area.equals("T")) out.print("selected"); %> >전체</option>
                                <option value="G0" <% if(s_area.equals("G0")) out.print("selected"); %> >게임</option>
                                <option value="B0" <% if(s_area.equals("B0")) out.print("selected"); %> >방송</option>
                                <option value="K0" <% if(s_area.equals("K0")) out.print("selected"); %> >문콘</option>
							</select>
                        </td>
                    </tr>
                        <!-------------------- 조건검색 끝 ---------------------------->
                    <tr>
                    	<td colspan="8" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
                    </tR>
                  </table>
                  </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>

      <table border="0" cellpadding="0" cellspacing="0" width="97%">
          <tr>
              <td  align="right"><img src="/images/admin/button/btn_apply.gif" style="CURSOR:POINTER" onclick="fnSave()"></td>
          </tr>
      </table>
      <table width="970" class="table_out" cellspacing="1" cellpadding="5" id="tbl1">
        <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title"><b>과정명</b></td>
          <td width="40px" class="table_title"><b>정원</b></td>
          <td width="50px"  class="table_title"><b>유/무료</b></td>
          <td width="60px" class="table_title"><b>교육구분</b></td>
          <td width="60px"  class="table_title"><b>사용유무</b></td>
          <td width="70px" class="table_title"><b>현재추천수</b></td>
          <td width="70px" class="table_title"><b>추천수수정</b></td>
        </tr>
<%
        if (box.getString("p_action").equals("go")) {
            ArrayList  list = (ArrayList)request.getAttribute("selectList");

            for (int i=0; i<list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);
%>
        <tr class="table_02_1">
            <td class="table_02_1" style="text-align:left"><%=dbox.getString("d_subjnm")%></td>
            <td class="table_02_1"><%=dbox.getString("d_studentlimit")%></td>
            <td class="table_02_1"><%=dbox.getString("d_biyong")%></td>
            <td class="table_02_1"><%=dbox.getString("d_area")%></td>
            <td class="table_02_1"><%=dbox.getString("d_ISUSE")%></td>
            <td class="table_02_1"><%=dbox.getString("d_hitnumber")%></td>
            <td class="table_02_1">
                <input type="text" name="hitnumber" size="8" maxlength="5">
                <input type="hidden" name="subj" value="<%=dbox.getString("d_subj")%>">
            </td>
                    <%--<td  class="table_02_1" colspan="12" align="center">등록된 과정이 없습니다.</td>--%>
          </tr>
<%}}%>
        </table>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
