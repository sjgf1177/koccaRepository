<%
//********************************************************************
//  1. 제      목: OBC마스터폼 Lesson 수정화면
//  2. 프로그램명 : za_MasterFormLesson_U.jsp
//  3. 개      요: OBC마스터폼 Lesson 수정화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 3
//  7. 수      정:
//********************************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.beta.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  p_subj      	= box.getString("p_subj");
    String  c_gadmin    	= box.getSession("s_gadmin");
    if(c_gadmin==null || c_gadmin.equals(""))   c_gadmin="A1";

    String  p_process   	= box.getString("p_process");
    String  ss_contenttype  = box.getString("s_contenttype");   //마스터폼 리스트 ContentType
    String  ss_upperclass 	= box.getString("s_upperclass");	//마스터폼 리스트 화면 대분류
    String  ss_middleclass 	= box.getString("s_middleclass");	//마스터폼 리스트 화면 중분류
    String  ss_lowerclass  	= box.getString("s_lowerclass");	//마스터폼 리스트 화면 소분류
    int     p_cnt_lesson	= box.getInt("p_cnt_lesson");

    BetaMasterFormData  da  =(BetaMasterFormData)request.getAttribute("BetaMasterFormData");
    String  v_ismfbranch    = da.getIsmfbranch();
    String  v_subjnm        = da.getSubjnm();
    String  v_dir           = da.getDir();
    String  v_contenttypetxt= da.getContenttypetxt();
    String  v_unchangableMaxLesson = da.getUnchangableMaxLesson();
    int     v_cnt_lesson    = da.getCnt_lesson();

    if(p_cnt_lesson>v_cnt_lesson)   v_cnt_lesson = p_cnt_lesson;

    ArrayList       list1 = (ArrayList)request.getAttribute("BetaMfModuleData");
    ArrayList       list2 = (ArrayList)request.getAttribute("BetaMfLessonData");
    BetaMfLessonData    data2 = null;

    SelectParam param = new SelectParam("p_isbranch", "", false, 1, box.getHttpSession());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
</script>
</head>
<script language="javascript">
function whenLessonCnt(){
    document.f2.p_process.value="updateLessonPage";
    document.f2.submit();
}
function whenSubmit(){
    if(!checkOK())  return;
    document.f2.p_process.value="updateLesson";
    document.f2.submit();
}
function checkOK(){
    var isExist=false;

    for(var i=0;i<document.f2.length;i++){
        if(document.f2.elements[i].name=="p_sdesc" && document.f2.elements[i].value.length > 0){
            if(document.f2.elements[(i-1)].value == '<%=v_unchangableMaxLesson%>'){
                isExist = true;
                break;
            }
        }
    }

    if(!isExist){
        if ("<%=v_unchangableMaxLesson%>" == "000")
                alert("1개 이상을 등록하셔야 합니다");
        else    alert("<%=v_unchangableMaxLesson%> Lesson까지 입력하셔야 합니다.");
    }

    return isExist;
}
function whenDelete(lesson){
    var f_del = false;
    var f_len = 0;
	var v_add = 0;
	f_len =8;

    for(var i=0;i<document.f2.length;i++){
        if(document.f2.elements[i].name=="p_lesson" && document.f2.elements[i].value==lesson){
            document.f2.elements[(i+1)].value="";
            f_del = true;
        }
        if(f_del && (document.f2.elements[i].name=="p_sdesc") ){

            if( i < (document.f2.length-f_len) ){
                document.f2.elements[i].value=document.f2.elements[(i+5+v_add)].value;
                document.f2.elements[(i+1)].selectedIndex=document.f2.elements[(i+6+v_add)].selectedIndex;
                document.f2.elements[(i+2)].value=document.f2.elements[(i+7+v_add)].value;
                document.f2.elements[(i+3)].value=document.f2.elements[(i+8+v_add)].value;
            }else{
                document.f2.elements[i].value="";
                document.f2.elements[(i+2)].value="";
                document.f2.elements[(i+3)].value="";
            }
        }
    }
}


function whenChangeModule(){
    var maxIdx = <%=v_cnt_lesson%>;
    var curIdx = maxIdx;
    var bakIdx = maxIdx;

    for(var i=document.f2.p_module.length; i >0; i--){
        j = i-1;
        curIdx =document.f2.p_module[j].selectedIndex;

        if (curIdx != bakIdx){
            if (curIdx == maxIdx || curIdx > bakIdx){
                document.f2.p_module[j].options[bakIdx].selected = true;
            }else
                bakIdx = curIdx;
        }

    }
}
function whenBranch(lesson){
    var url=document.f2.action;
    url +="?p_process=updateLessonBranchPage&p_subj=<%=p_subj%>&p_lesson="+lesson;
    window.open(url,"branchWin","width=800,height=600");
}
</script>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">


  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
     <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/tit_masterform.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table> 
	   <!----------------- title 끝 ----------------->
        <br>
		        <!-------------소제목 시작-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>차시정보수정</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->
    <%@ include file="/beta/admin/include_BetaMasterFormAdmin.jsp" %>

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td bgcolor="#636563">
              <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <FORM name = "f2" method = "post" action="/servlet/controller.beta.BetaMasterFormServlet">
                    <input type=hidden name=p_process   	value="">
                    <input type=hidden name=p_action    	value="">
                    <input type=hidden name=p_subj      	value="<%=p_subj%>">
                    <input type=hidden name=p_contenttype   value="<%=da.getContenttype()%>">
                    <input type=hidden name=s_upperclass 	value="<%=ss_upperclass%>">
                    <input type=hidden name=s_middleclass 	value="<%=ss_middleclass%>">
                    <input type=hidden name=s_lowerclass 	value="<%=ss_lowerclass%>">
                    <input type=hidden name=s_contenttype   value="<%=ss_contenttype%>">
                <tr>
                  <td bgcolor="#FFFFFF" align="center" valign="top">
                  <br>
                    <!----------------- 구성할 차시수 시작 ----------------->
                    <table width="99%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td valign="bottom" height="20" width="195">구성할 차시수 :
                          <input name="p_cnt_lesson" type="text" class="input" size="2" value="<%=v_cnt_lesson%>">
                        </td>
                        <td><a href="javascript:whenLessonCnt()"><img src="/images/admin/button/b_go.gif" border="0"><a></td>
                      </tr>
                      <tr>
                        <td height="3"></td>
                      </tr>
                    </table>
                    <!----------------- 구성할 차시수 끝 ----------------->

                    <table width="99%" border="0" cellspacing="1" cellpadding="5">
                      <tr>
                        <td colspan="7" class="table_top_line"></td>
                      </tr>
                      <tr>
                        <td width="5%" height="25" class="table_title"><b>Lesson</b></td>
                        <td class="table_title"><b>차시(Lesson)명</b></td>
                        <td class="table_title"><b>Module</b></td>
                        <td class="table_title"><b>소유과정</b></td>
                        <td class="table_title"><b>콘텐츠위치</b></td>
                        <td class="table_title"><b>삭제</b></td>
                      </tr>
<%          for (int i=0; i<list2.size(); i++) {
                data2  = (BetaMfLessonData)list2.get(i);
%>
                      <tr>
                        <td align="center" bgcolor="#EEEEEE" height="25"><%=data2.getLesson()%></td>
                        <td class="table_02_1">
                          <input name="p_lesson" type="hidden" value="<%=data2.getLesson()%>">
                          <input name="p_sdesc" type="text" class="input" value="<%=data2.getSdesc()%>" size="30">
                        </td>
                        <td class="table_02_1">
                          <!--<select name="p_module" onChange="whenChangeModule('<%//=data2.getModule()%>')">-->
                          <select name="p_module">
                          <%=BetaMasterFormBean.make_moduleSelect(list1,data2.getModule())%>
                          </select>
                        </td>
                        <td class="table_02_1">
                          <input name="p_owner" type="text" class="input" size="10" value="<%=data2.getOwner()%>">
                        </td>
                        <td class="table_02_1">
                          <input name="p_starting" type="text" class="input" size="50" value="<%=data2.getStarting()%>">
                        </td>
                        <td class="table_02_1">

                <%  if(Integer.parseInt(data2.getLesson()) > Integer.parseInt(v_unchangableMaxLesson.trim())){  %>
                            <a href="javascript:whenDelete('<%=data2.getLesson()%>')"><img src="/images/admin/button/btn_del.gif" border=0></a></td>
                <%  }   %>
                      </tr>
<%          }

            int list_size = 1;
            if (list2 != null)  list_size = list2.size() + 1;

            String  v_lesson = "";
            for (int i=list_size;i<=v_cnt_lesson;i++){
                v_lesson = new DecimalFormat("000").format(i);
%>
                      <tr>
                        <td align="center" bgcolor="#EEEEEE" height="25"><%=v_lesson%></td>
                        <td class="table_02_1">
                          <input name="p_lesson" type="hidden" value="<%=v_lesson%>">
                          <input name="p_sdesc" type="text" class="input" value="" size="30">
                        </td>
                        <td class="table_02_1">
                          <!--<select name="p_module" onChange="whenChangeModule('<%=v_lesson%>')">-->
                          <select name="p_module">
                          <%=BetaMasterFormBean.make_moduleSelect(list1, "")%>
                          </select>
                        </td>
                        <td class="table_02_1">
                          <input name="p_owner" type="text" class="input" size="10" value="<%=p_subj%>">
                        </td>
                        <td class="table_02_1">
                          <input name="p_starting" type="text" class="input" size="50" value="<%=EduEtc1Bean.make_startURL("1",p_subj,da.getServer(),da.getPort(),da.getDir(),v_lesson)%>">
                        </td>
                        <td class="table_02_1">&nbsp;</td>
                      </tr>
<%
            }
%>

                    </table>
                    <!----------------- 차시정보수정 끝 ----------------->
                    <br>
                    <!----------------- 저장버튼 시작 ----------------->
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td> <a href="javascript:whenSubmit()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
						<td width=8></td>
						<td><a href="javascript:whenList()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
                      </tr>
                      <tr>
                        <td height="8"></td>
						<td height="8"></td>
						<td height="8"></td>
                      </tr>
                    </table>
                    <!----------------- 저장버튼 끝 ----------------->
                  </td>
                </tr>
                </FORM>
              </table>
            </td>
          </tr>
        </table>
        <br>

      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
