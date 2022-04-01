<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 용어사전(과정)
//  2. 프로그램명 : zu_DicSubjStudy_L.jsp
//  3. 개      요: 용어사전(과정) 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process         = box.getString("p_process");
    String  v_total_row_count = box.getStringDefault("total_row_count","0");  // 빈에서 box.put 
    //2005.12.06_하경태 : 과정 -> 교육그룹으로 변경.
	//String  v_subj            = box.getString("p_grcode");    // 교육그룹코드
    String  v_subj       = box.getString("p_subj");
    String  v_year       = box.getString("p_year");
    String  v_subjseq    = box.getString("p_subjseq");
    String  v_grcode     = box.getString("p_grcode");
    String  v_flagpreview= box.getString("p_flagpreview");

	String s_grcode		= box.getSession("tem_grcode");
	if(!s_grcode.equals(""))
	{
		v_grcode = s_grcode;
	}


    int v_pageno = box.getInt("p_pageno"); 
    if (v_pageno == 0) v_pageno = 1;

    String  v_searchtext    = box.getString("p_searchtext");
    String  v_group         = box.getString("p_group");

    String  v_search         = "";
    if (v_searchtext.equals(""))  v_search = v_group;
    else                          v_search = v_searchtext;


    int     v_seq        = 0;
    String  v_subjcode   = "";
    String  v_subjcodenm = "";
    String  v_words      = "";
    String  v_descs      = "";
    String  v_groups     = "";
    int     v_totalpage  = 0;
    int     v_rowcount = 1;

    String	v_subjnm	= "";
    String	v_edustart	= "";
    String	v_eduend	= "";
    
    ArrayList list = (ArrayList)request.getAttribute("selectList");

    if (!"Y".equals(v_flagpreview)){
	    SubjectBean		subjbean = new SubjectBean();
		SubjectData		subjdata = new SubjectData();
						subjdata = subjbean.SelectSubjectData(box);
	
	    EduStartBean	bean		= new EduStartBean();
	    EduScoreData    scoredata	= new EduScoreData();
	    				scoredata	= bean.SelectEduScore(box);
	    				
		v_subjnm	= subjdata.getSubjnm();
		v_edustart	= FormatDate.getFormatDate(scoredata.getEdustart(),"yyyy.MM.dd");
		v_eduend	= FormatDate.getFormatDate(scoredata.getEduend(),"yyyy.MM.dd");
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>용어사전</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript">
<!--
    obj_old_dic = null;
	obj_old_li  = null;
    function show_list(obj_dic, obj_li) {

		document.getElementById("dicitonarystart").style.display = "none";
        
        if(obj_old_dic != null)
        {
            if(obj_dic.id != obj_old_dic.id)
            {
                change_old()
            }
        }

        if (obj_dic.style.display == "none")
        {
            obj_dic.style.display = "";
            obj_li.className = "on";
        }
        obj_old_dic = obj_dic;
        obj_old_li  = obj_li;
    }

    function change_old() {
        obj_old_dic.style.display = "none";
        obj_old_li.className          = "";
    }

    function goSearch() {
        document.form1.p_pageno.value = 1;
        document.form1.target = "_self"
        document.form1.p_process.value   = "selectList";
        document.form1.action            = "/servlet/controller.study.DicSubjStudyServlet";
        document.form1.submit();

    }

    function goGroup(group) {
        document.form1.p_pageno.value = 1;
        document.form1.p_group.value      = group;
        document.form1.p_searchtext.value = "";

        document.form1.target = "_self"
        document.form1.p_process.value   = "selectList";
        document.form1.action            = "/servlet/controller.study.DicSubjStudyServlet";
        document.form1.submit();

        document.form1.target = window.self.name;

    }
//-->
</script>
</head>
<body id="pop_study"><!-- popup size : 890* -->
<form name ='form1' method='post' action='javascript:goSearch();'>
    <input type='hidden' name = 'p_process'    value = '<%=v_process%>'/>
    <input type='hidden' name = 'p_pageno'     value = '<%=v_pageno%>'/>
    <input type='hidden' name = 'p_group'      value = '<%=v_group%>'/>
    <input type="hidden" name = "p_subj"       value = "<%= v_subj %>"/>
    <input type="hidden" name = "p_year"       value = "<%= v_year %>"/>
    <input type="hidden" name = "p_subjseq"    value = "<%= v_subjseq %>"/>
    <input type="hidden" name = "p_grcode"     value = "<%= v_grcode %>"/>
    <input type="hidden" name = "p_flagpreview"     value = "<%= v_flagpreview %>"/>

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit9.gif" alt="용어사전" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap">
			<div id="con_scoll">


				<div id="btn_close"><a href="javascript:top.window.close()"><img src="/images/portal/btn/btn_close.gif" alt="닫기" /></a></div>
<%
	if (!"Y".equals(v_flagpreview)){
%>			
				<div class="study_tltle study_tltle_sub"><div class="study_tltle_left"><div class="study_tltle_right">
					<dl class="class_info"> 
						<dt><span>과정명</span></dt>
						<dd><%= v_subjnm %></dd>
					</dl>
					<dl class="class_info">
						<dt><span>교육기간</span></dt>
						<dd class="class_day"><%= v_edustart %> ~<%= v_eduend %></dd>
					</dl>
				</div></div></div>
<%
	}
%>			
				
				<div class="content">
					<div class="dic_search_box">
						<div class="left"><div class="right">
						<dl>
							<dt><img src="/images/portal/myclass/study/dictionary_stit1.gif" alt="색인으로 찾기" /></dt>
							<dd>
								<ul>
									<li><a href="javascript:goGroup('ㄱ')"><img src="/images/portal/btn/btn_kor1.gif" alt="ㄱ" /></a></li>
									<li><a href="javascript:goGroup('ㄴ')"><img src="/images/portal/btn/btn_kor2.gif" alt="ㄴ" /></a></li>
									<li><a href="javascript:goGroup('ㄷ')"><img src="/images/portal/btn/btn_kor3.gif" alt="ㄷ" /></a></li>
									<li><a href="javascript:goGroup('ㄹ')"><img src="/images/portal/btn/btn_kor4.gif" alt="ㄹ" /></a></li>
									<li><a href="javascript:goGroup('ㅁ')"><img src="/images/portal/btn/btn_kor5.gif" alt="ㅁ" /></a></li>
									<li><a href="javascript:goGroup('ㅂ')"><img src="/images/portal/btn/btn_kor6.gif" alt="ㅂ" /></a></li>
									<li><a href="javascript:goGroup('ㅅ')"><img src="/images/portal/btn/btn_kor7.gif" alt="ㅅ" /></a></li>
									<li><a href="javascript:goGroup('ㅇ')"><img src="/images/portal/btn/btn_kor8.gif" alt="ㅇ" /></a></li>
									<li><a href="javascript:goGroup('ㅈ')"><img src="/images/portal/btn/btn_kor9.gif" alt="ㅈ" /></a></li>
									<li><a href="javascript:goGroup('ㅊ')"><img src="/images/portal/btn/btn_kor10.gif" alt="ㅊ" /></a></li>
									<li><a href="javascript:goGroup('ㅋ')"><img src="/images/portal/btn/btn_kor11.gif" alt="ㅋ" /></a></li>
									<li><a href="javascript:goGroup('ㅌ')"><img src="/images/portal/btn/btn_kor12.gif" alt="ㅌ" /></a></li>
									<li><a href="javascript:goGroup('ㅍ')"><img src="/images/portal/btn/btn_kor13.gif" alt="ㅍ" /></a></li>
									<li><a href="javascript:goGroup('ㅎ')"><img src="/images/portal/btn/btn_kor14.gif" alt="ㅎ" /></a></li>
									<li><a href="javascript:goGroup('기타')"><img src="/images/portal/btn/btn_kor15.gif" alt="1~9" /></a></li>
								</ul>
								<ul>
									<li><a href="javascript:goGroup('A')"><img src="/images/portal/btn/btn_eng1.gif" alt="a" /></a></li>
									<li><a href="javascript:goGroup('B')"><img src="/images/portal/btn/btn_eng2.gif" alt="b" /></a></li>
									<li><a href="javascript:goGroup('C')"><img src="/images/portal/btn/btn_eng3.gif" alt="c" /></a></li>
									<li><a href="javascript:goGroup('D')"><img src="/images/portal/btn/btn_eng4.gif" alt="d" /></a></li>
									<li><a href="javascript:goGroup('E')"><img src="/images/portal/btn/btn_eng5.gif" alt="e" /></a></li>
									<li><a href="javascript:goGroup('F')"><img src="/images/portal/btn/btn_eng6.gif" alt="f" /></a></li>
									<li><a href="javascript:goGroup('G')"><img src="/images/portal/btn/btn_eng7.gif" alt="g" /></a></li>
									<li><a href="javascript:goGroup('H')"><img src="/images/portal/btn/btn_eng8.gif" alt="h" /></a></li>
									<li><a href="javascript:goGroup('I')"><img src="/images/portal/btn/btn_eng9.gif" alt="i" /></a></li>
									<li><a href="javascript:goGroup('J')"><img src="/images/portal/btn/btn_eng10.gif" alt="j" /></a></li>
									<li><a href="javascript:goGroup('K')"><img src="/images/portal/btn/btn_eng11.gif" alt="k" /></a></li>
									<li><a href="javascript:goGroup('L')"><img src="/images/portal/btn/btn_eng12.gif" alt="l" /></a></li>
									<li><a href="javascript:goGroup('M')"><img src="/images/portal/btn/btn_eng13.gif" alt="m" /></a></li>
									<li><a href="javascript:goGroup('N')"><img src="/images/portal/btn/btn_eng14.gif" alt="n" /></a></li>
									<li><a href="javascript:goGroup('O')"><img src="/images/portal/btn/btn_eng15.gif" alt="o" /></a></li>
									<li><a href="javascript:goGroup('P')"><img src="/images/portal/btn/btn_eng16.gif" alt="p" /></a></li>
									<li><a href="javascript:goGroup('Q')"><img src="/images/portal/btn/btn_eng17.gif" alt="q" /></a></li>
									<li><a href="javascript:goGroup('R')"><img src="/images/portal/btn/btn_eng18.gif" alt="r" /></a></li>
									<li><a href="javascript:goGroup('S')"><img src="/images/portal/btn/btn_eng19.gif" alt="s" /></a></li>
									<li><a href="javascript:goGroup('T')"><img src="/images/portal/btn/btn_eng20.gif" alt="t" /></a></li>
									<li><a href="javascript:goGroup('U')"><img src="/images/portal/btn/btn_eng21.gif" alt="u" /></a></li>
									<li><a href="javascript:goGroup('V')"><img src="/images/portal/btn/btn_eng22.gif" alt="v" /></a></li>
									<li><a href="javascript:goGroup('W')"><img src="/images/portal/btn/btn_eng23.gif" alt="w" /></a></li>
									<li><a href="javascript:goGroup('X')"><img src="/images/portal/btn/btn_eng24.gif" alt="x" /></a></li>
									<li><a href="javascript:goGroup('Y')"><img src="/images/portal/btn/btn_eng25.gif" alt="y" /></a></li>
									<li><a href="javascript:goGroup('Z')"><img src="/images/portal/btn/btn_eng26.gif" alt="z" /></a></li>
								</ul>
							</dd>
						</dl>
						
						<dl>
							<dt class="search"><img src="/images/portal/myclass/study/dictionary_stit2.gif" alt="검색어입력" /></dt>
							<dd><input type="text" name="p_searchtext" class="inbox" style="width:330px;" value="<%= v_searchtext %>" onKeyPress="fnKeyPressEnter(event,'goSearch');"/><a href="javascript:goSearch()"><img src="/images/portal/btn/btn_bsearch.gif" alt="검색" /></a></dd>
						</dl>
						</div></div>
					</div>
					
					<p class="board_bcomment mg_t30">총 <strong><%= list.size() %></strong> 개의 용어가 검색되었습니다.</p>
					<div class="search_result">
						<div class="left_word">
							<ul>
<%
        for(int i = 0; i < list.size(); i++) {
            DataBox dbox   = (DataBox)list.get(i);
            v_seq    = dbox.getInt   ("d_seq");
            v_words  = dbox.getString("d_words");
            v_descs  = dbox.getString("d_descs");
            v_groups = dbox.getString("d_groups");
            v_descs  = StringManager.replace(v_descs,"\n","<br>");
%>
								<li id="li<%= v_seq %>"><a href="javascript:show_list(dicitonary<%=v_seq%>,li<%= v_seq %>);"><%= v_words %></a></li>
<%
        }
%>
							</ul>
						</div>
						<div id="dicitonarystart" class="right_info">
							<dl>
								<dt></dt>
								<dd></dd>
							</dl>
						</div>	

<%
        for(int i = 0; i < list.size(); i++) {
            DataBox dbox   = (DataBox)list.get(i);
            v_seq    = dbox.getInt   ("d_seq");
            v_words  = dbox.getString("d_words");
            v_descs  = dbox.getString("d_descs");
            v_groups = dbox.getString("d_groups");
            v_descs  = StringManager.replace(v_descs,"\n","<br>");
%>
						<div id="dicitonary<%=v_seq%>" class="right_info" style="display: none">
							<dl>
								<dt><%= v_words %></dt>
								<dd><%= v_descs %></dd>
							</dl>
						</div>	
<%
        }
%>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>