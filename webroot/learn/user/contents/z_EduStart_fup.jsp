<%
/**
 * file name : z_EduStart_fup.jsp
 * date      : 2003/08/21
 * programmer: LeeSuMin
 * function  : 마스터폼 Upper Frame
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    if("Y".equals(box.getSession("tobeyn"))){
		response.setHeader("P3P", "CP='CAO PSA CONi OTR OUR DEM ONL'");
	}

    String  s_userid    = box.getSession("userid");
    String  s_name  	= box.getSession("name");
    String  s_subj      = box.getSession("s_subj");
    String  s_year      = box.getSession("s_year");
    String  s_subjseq   = box.getSession("s_subjseq");
    String  s_gadmin    = box.getSession("gadmin");
    String  s_mftype    = box.getSession("s_mftype");
    String	s_tem_type	= box.getSession("tem_type");
    System.out.println("================================== z_EduStart_fup.jsp_0000000");
    
    //tobe를 위해 추가 차수, tobe여부 2015.11.14
    String  s_lesson    = box.getSession("lesson");
    String  s_tobeyn    = box.getSession("tobeyn");
    //TOBE일 경우 레슨 정보가 없을 시 강제로 추가 해줌
    if("Y".equals(s_tobeyn)){
    	if("".equals(s_lesson)){
    		s_lesson = "001";
    		box.setSession("lesson", s_lesson);
    	}
    }
    System.out.println("================================== s_tobeyn : " + s_tobeyn);
    String  v_ismfbranch    = box.getSession("s_ismfbranch");
    String  v_contenttype   = box.getSession("s_contenttype");
	int		v_width			= Integer.parseInt( box.getSession("s_width") ) - 10;
	int		v_height		= Integer.parseInt( box.getSession("s_height") ) - 55;

    String  v_isconfirmed   = "N";
    
    int     v_mybranch      = EduEtc1Bean.get_mybranch(s_subj, s_year, s_subjseq, s_userid);
    if(v_mybranch != 99) v_isconfirmed = "Y";

	String  v_base_url      = "/servlet/controller.contents.EduStart?p_process=";
	String  v_eduChkURL 	= v_base_url + "eduCheck&p_lesson="; //진도체크URL
	String  v_examURL   	= "/servlet/controller.exam.ExamServlet?p_process=UserResultListPage&p_lesson="; //평가URL
	String  v_sulURL    	= "/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunUserPaperFinalListPage&p_subj=" + s_subj + "&p_year=" + s_year + "&p_subjseq=" + s_subjseq;
	String  v_brCntlURL 	= v_base_url + "branchControl"; //분기제어URL
	String  v_eduMemURL 	= v_base_url + "eduMemList"; //동료학습자보기URL
	String  v_creduURL		= "/contents/credu/start.html";

    String  v_imgURL    	= EduEtc1Bean.make_eduURL(s_subj,"DOC")+"docs/menuimg"; //image Base URL

    String  v_isshowsel 	= "N"; //차시선택Selection 조회여부
    ArrayList  list 		= (ArrayList)request.getAttribute("MfLessonList");
    
    MfLessonData x 			= null;
    System.out.println("================================== z_EduStart_fup.jsp_11111111");
%>
<html>
<head>
<title>Education...</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script type="text/javascript" src="/script/portal/backspaceN.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language=javascript>

    var f_branch = '<%=v_ismfbranch%>';
    var mybranch = '<%=v_mybranch%>';
    var curbranch = '<%=v_mybranch%>';
    var f_confirmed = 'N';
    var f_branchset = '<%=v_isconfirmed%>';
    var tempbranch = "0";

	var v_subj = '<%=s_subj%>';
	var v_year = '<%=s_year%>';
	var v_subjseq = '<%=s_subjseq%>';
	var v_mftype = '<%=s_mftype%>';

    var isChecked = false;
    
    var creduGateUrl = "";


	function popup_window(url) {
		if (url.indexOf("LecHisServlet") > -1){
			upWin('/servlet/controller.contents.EduStart?p_process=eduList',800,600);
		}else if (url.indexOf("RptStdServlet") > -1){
			upWin('/servlet/controller.study.ProjectServlet?p_process=choicePage&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq,800,600);
		}else if (url.indexOf("ExmMngServlet") > -1){
			upWin('/servlet/controller.study.StudyExamServlet?&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq,800,600);
		}else if (url.indexOf("bbsalgn=1") > -1){
			parent.ebody.location = '/servlet/controller.study.SubjGongStudyServlet?p_sitegubun=&p_ispreview=&p_process=select&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq+'&p_ispreview=';
		}else if (url.indexOf("bbsalgn=4") > -1){
			upWin('/servlet/controller.study.StudyDataServlet?&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq,800,600);
		}else if (url.indexOf("bbsalgn=3") > -1 || url.indexOf("BbsBrdServlet") > -1){
			upWin('/servlet/controller.study.SubjQnaStudyServlet?p_process=SubjQnaFrame&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq,800,600);
		}else if (url.indexOf("ChtStdServlet") > -1){
			upWin('/servlet/controller.study.ToronServlet?&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq,800,600);
		}else{
			alert('지원되지 않는 기능입니다.');
		}
	}

	//학습자 의견보기
	function openOpinion(v_lesson, v_lessonnm, v_pageno, v_opinseq, v_question) {
        upWin('/servlet/controller.study.SubjOpinionServlet?p_process=SubjOpinionList&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq+'&p_lesson='+v_lesson+'&p_lessonnm='+v_lessonnm+'&p_pageno='+v_pageno+'&p_opinseq='+v_opinseq+'&p_question='+v_question,900,600);
	}

	//학습자 의견저장하기
	function saveOpinion(v_lesson, v_lessonnm, v_pageno, v_opinseq, v_question, v_contents) {
		upWin('/servlet/controller.study.SubjOpinionServlet?p_process=SubjOpinionInsert&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq+'&p_lesson='+v_lesson+'&p_lessonnm='+v_lessonnm+'&p_pageno='+v_pageno+'&p_opinseq='+v_opinseq+'&p_question='+v_question+'&p_contents='+v_contents ,900,600);
	}

	//커뮤니티 게시판
	function openBoard(gubun) {
			upWin('/servlet/controller.study.SubjBulletinServlet?p_process=SubjBulletinFrame&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq+'&p_gubun='+gubun,817,604);
	}

	function upWin(url,w,h){
		window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width="+w+",height="+h+" ").focus();
	}
	
	function goMain(){

		if (v_mftype=='ND') {
			eduChk();
		}

	    var urlStr = '/servlet/controller.study.SubjGongStudyServlet?p_sitegubun=&p_ispreview=&p_process=select&p_subj='+v_subj+'&p_year='+v_year+'&p_subjseq='+v_subjseq+'&p_ispreview=&p_mftype='+v_mftype;
		parent.ebody.location = urlStr;
	}
	
	function winClose(){
		top.window.close();
	}
	
    function eduChkEnd(){
		if (v_mftype=='ND') {
			eduChk();
		}
    }

    function out(){
        parent.out();
    }

    function chkDates(){
		return;
		var t = top.ebody.location;
		var tmp = t.toString().split("/docs/");
		var t_date = tmp[tmp.length-1].substring(0,2);
		
		var selUrl = document.sel.p_lesson.options[document.sel.p_lesson.selectedIndex].value.split(",");
		var nowUrl = selUrl[1];
		var ntmp = nowUrl.toString().split("/docs/");
		var n_date = ntmp[ntmp.length-1].substring(0,2);
		
		if (t_date != n_date){
		    for(var i=0;i<document.sel.p_lesson.length;i++){
		       arrStr = document.sel.p_lesson.options[i].value.split(",");
		
		        if (arrStr[0]==t_date){
		            document.sel.p_lesson.options[i].selected = true;
		            document.next.now.value=arrStr[0];
		            break;
		        }
		    }
		}
    }
    
    function uWin(url){
        window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=700,height=600").focus();
    }
    
    function eduChk(){

        chkDates();

        var  strCurrent = "";

        var  current = document.next.now.value;

        if (curbranch==mybranch){
            var  url = '<%=v_eduChkURL%>'+current;

            parent.echk.location = url;
            isChecked = true;
        }

        return;
    }
    
    function eduChkStart(gubun){
        chkDates();
        var  strCurrent = "";
        var  current = document.next.now.value;
        if (curbranch==mybranch){
            var  url = '<%=v_eduChkURL%>'+current+'&p_gubun='+gubun;
            //alert("url="+url);
            parent.echk.location = url;

        }

        return;
    }

    function startContents(gubun,types,urls){
//		if(true) {
//		    window.open('/learn/user/contents/z_EduStart_content_popup.jsp?p_url='+url, name, 'left=0,top=0,width=<%=v_width%>,height=<%=v_height%>,toolbar=no,menubar=no,status=yes,scrollbars=yes,resizable=yes').focus();
//			open_window('openContent',urls,0,0,<%=v_width%>,<%=v_height%>,false,false,'yes','yes','yes');
//		}
//		else if (v_subj=='cpl003' || v_subj=='cpl004' || v_subj=='cpl010' || v_subj=='sws006'
//		    || v_subj=='spr001' || v_subj=='cpl1031' || v_subj=='cpl1032' || v_subj=='cpl1033') {
//			w = '<%=v_width%>';
//			h = '<%=v_height%>';
//			open_window('openContent',urls,0,0,w,h,false,false,'yes','yes','yes');
//		}else{
//       	parent.ebody.location =urls;
//      }
//        isChecked = false;

//		setTimeout("eduChkStart('START')",1000);

		if(urls.indexOf("ksp.credu.com") > 0){
			creduGateUrl = urls;
			urls = "<%=v_creduURL%>";
		}

		if (v_subj=='cpl003' || v_subj=='cpl004' || v_subj=='cpl010' || v_subj=='sws006'
		    || v_subj=='spr001' || v_subj=='cpl1031' || v_subj=='cpl1032' || v_subj=='cpl1033') {
			w = '<%=v_width%>';
			h = '<%=v_height%>';
			open_window('openContent',urls,0,0,w,h,false,false,'yes','yes','yes');
		}else{
        	if('<%=s_tem_type%>' == 'B'){
	        	open_window('openContent',urls,0,0,<%=v_width%>,<%=v_height%>,false,false,'yes','yes','yes');
        	}else{
	        	parent.ebody.location =urls;
        	}
        }
        isChecked = false;

        
		// 학습시작(학습시작시간 체크위해 진도반영: 진도율적용안됨)
		eduChkStart('START');

		// 학습완료 프로세스 시작
		 if(v_subj=='CK15031' || v_subj =='CK15032' || v_subj == 'CK16002'){
		   setTimeout("eduChkStart('COMPLETE')",600000);
			 
		 }else{
		   setTimeout("eduChkStart('COMPLETE')",10000);
		 }

    }
    
    // 크레듀 제휴과정 링크정보 가져오기
    function creduURL(){
    	$.ajax({
	            type : "post"
	        ,   url : "/servlet/controller.contents.EduStart"
	        ,   dataType : "json"
	        ,   data : {
	        	url : creduGateUrl,
	        	p_process : "creduSubj"
	        }
	        ,   success : function(ajaxData) {
	        		parent.ebody.loadswf(ajaxData.videoURL, 945, 540);
	        	}
	        ,   error :  function(arg1, arg2) {
	                alert("오류가 발생하여 플레이 할 수 없습니다.");
	            }
	    });
    }

	function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable) {
	
	    toolbar_str   = ((toolbar   == 'yes') ? 'yes' : 'no') ;
	    menubar_str   = ((menubar   == 'yes') ? 'yes' : 'no') ;
	    statusbar_str = ((statusbar == 'yes') ? 'yes' : 'no') ;
	    scrollbar_str = ((scrollbar == 'yes') ? 'yes' : 'no') ;
	    resizable_str = ((resizable == 'yes') ? 'yes' : 'no') ;
	
	    window.open('/learn/user/contents/z_EduStart_content_popup.jsp?p_url='+url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
	}

    function goNext(){
        chkDates();

        var  strCurrent = "";
        var  current = parseInt(document.next.now.value,10);
        if (curbranch==mybranch && !isChecked){
            var  url = '<%=v_eduChkURL%>'+document.next.now.value;
            parent.echk.location = url;
        }

        var nowdate = document.next.now.value;

        current -= 1;
        current += 1;

        if ( current >= 99){
            current += 1;
        }else if( current >= 9){
            current += 1;
            strCurrent = "0"+current;
        }else{
            current += 1;
            strCurrent = "00"+current;
        }

 		goMain();
    }

    function goExam(lesson,ptype){
        url = '<%=v_examURL%>'+lesson+'&p_ptype='+ptype;
        parent.ebody.location = url;
    }

    function goExNext(){
        var  strCurrent = "";
        var  current = document.next.now.value;
        var nowdate = document.next.now.value;
        for(var i=0;i<document.sel.p_lesson.length;i++){
            arrStr = document.sel.p_lesson.options[i].value.split(",");
            if (arrStr[0]==nowdate){
                if (arrStr[2]=="Y"){
                    if (f_branch == "Y")    document.sel.p_lesson.options[i].value=arrStr[0]+","+arrStr[1]+",Y,"+arrStr[2];
                    else                    document.sel.p_lesson.options[i].value=arrStr[0]+","+arrStr[1]+",Y";
                }
                break;
            }
        }

        current -= 1;
        current += 1;

        if ( current >= 99){
            current += 1;
        }else if( current >= 9){
            current += 1;
            strCurrent = "0"+current;
        }else{
            current += 1;
            strCurrent = "00"+current;
        }

        document.next.now.value = strCurrent;
        jumpTo(strCurrent);
    }

    function jumpTo(vals){
        var strVal = "";
        var nvals = 0;
        var seli = 0;
        var arrStr;
        var f_exist = "N";

        if (vals <10){
            nvals = vals - 1;
            nvals += 1;
            strVal= "00" + nvals;
        } else if (vals <100){
            nvals = vals - 1;
            nvals += 1;
            strVal= "0" + nvals;
        }else if (vals >= 1000){
            alert("ERROR: ["+vals+"]value exceeds");
            return;
        }else{
            strVal = vals;
        }
        
        document.next.now.value=strVal;

        for(var i=0;i<document.sel.p_lesson.length;i++){
            arrStr = document.sel.p_lesson.options[i].value.split(",");

            if (arrStr[0]==vals){
                f_exist = "Y";
                seli = i;
                break;
            }
        }
        
        if (f_exist=="N"){
            var tmps = document.sel.p_lesson.length;
            arrStr = document.sel.p_lesson.options[0].value.split(",");
            if( i== tmps ){
                alert("수고하셨습니다. 교육내용의 마지막부분을 학습하셨습니다.\n\n설문, 평가, 과제물의 응시여부를 다시한번 확인해 주시기바랍니다.");
                document.sel.p_lesson.options[seli].selected = true;
             // parent.ebody.location ='<%=v_sulURL%>';
                return;
            }else{
                alert(vals+"차시는 존재하지 않습니다");
            }
            document.sel.p_lesson.options[seli].selected = true;

            startContents('','',arrStr[1]);
            return;

        } else {

            document.sel.p_lesson.options[seli].selected = true;
            startContents('','',arrStr[1]);
            return;

        }
    }

    function whenSelect(ival){
        var url=document.sel.p_lesson.options[ival].value;
        var arrStr = url.split(",");
        document.sel.p_lesson.options[ival].selected = true;
        document.next.now.value = arrStr[0];
        //zpack     parent.ebody.location = arrStr[1];
        var stdUrl = arrStr[1];
        stdUrl = stdUrl.replace('http://game.connect.or.kr','');
        stdUrl = stdUrl.replace('http://contents.connect.or.kr','');
        startContents('','',stdUrl);
    }

    function memList(){
        uWin("<%=v_eduMemURL%>");
    }

    function starting(lesson){
        // 학습창 다시띄우기 셋팅
    	//window.parent.opener.document.form1.lessonRepopup.value = "2";
        jumpTo(lesson);
    }
    
    function getVodURL(){
    	return "http://onexpert.hvod.skcdn.com";
    }

	//새창방지
    function KeyEventHandle(){
      if((event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82)) ||(event.keyCode >= 112 && event.keyCode <= 123)) {
           event.keyCode                = 0;
           event.cancelBubble        = true;
           event.returnValue        = false;
      }
    }
	
    document.onkeydown=KeyEventHandle;
    document.onkeyup=KeyEventHandle;
    
    
    window.onbeforeunload = function() {
        try {
        	alert("123123123");
            // 교육시간 체크
            eduChk();
            // 학습창 다시띄우기

            if( opener.document.form1.lessonRepopup.value == "2" ) {
                opener.studyOpenRe();
            }

        } catch (e) {
        } finally {
        }
    };
    
</script>

<script type="text/javascript">

  var _gaq = _gaq || [];  
  _gaq.push(['_setAccount', 'UA-33104445-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>
<style>
body,form { margin:0; padding:0; }
div.contentnavi { width:100%; height:100%; }
</style>
<body oncontextmenu="return false" ondragstart="return false">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
    <FORM name="sel">
<%  

System.out.println("================================== v_isshowsel : " + v_isshowsel );
if  (v_isshowsel.equals("Y")){  // Lesson Select 보여줄 경우%>
    <TR>
        <TD width="320" align="LEFT" valign=top background="<%=v_imgURL%>/title1.gif">
            <img src="<%=v_imgURL%>/title1.gif" alt="A00015" valign=top border=0 height="46">
        </TD><!-- 2009.12.04 선택상자변경시  학습시간 반영 top.etop.eduChk(); -->
        <TD width="*" align="CENTER" valign=middle background="<%=v_imgURL%>/title2.gif">&#160;
            <SELECT name="p_lesson" onChange="top.etop.eduChk();whenSelect(document.sel.p_lesson.selectedIndex);">
            
        <%  for (int i=0; i<list.size(); i++) {
                x  = (MfLessonData)list.get(i);     %>
            <option value="<%=x.getLesson()%>,<%=x.getStarting()%>,<%=x.getIsexam()%>,<%=x.getIsbranch()%>"><%=x.getLesson()%>-<%=x.getSdesc()%></option>
        <%  }   %>
            </SELECT>
            <img src="<%=v_imgURL%>/title2.gif" valign="top" border="0" height="46">
        </TD>
        <TD width=80 valign=middle align="CENTER">
            <img src="<%=v_imgURL%>/title3.gif" valign=top border=0>
        </TD>
    </TR>
<%  }else{  %>
    <TR>
        <td align="right" valign="top"><!-- 2009.12.04 선택상자변경시  학습시간 반영 top.etop.eduChk(); -->
            <SELECT name="p_lesson" style="margin-top:13px;" onChange="top.etop.eduChk();whenSelect(document.sel.p_lesson.selectedIndex);">
        <%  for (int i=0; i<list.size(); i++) {
                x  = (MfLessonData)list.get(i); %>
                <option style="font-family:'gulim','dotum','sans-serif'; font-size:12px;'" value="<%=x.getLesson()%>,<%=x.getStarting()%>,<%=x.getIsexam()%>,<%=x.getIsbranch()%>"><%=x.getLesson()%>-<%=x.getSdesc()%></option>
        <%  }   %>
            </SELECT>
        </td>
    </tr>
<%  }   %>
    </FORM>
    <FORM name="next">
        <input type=hidden  name="now"  value="000">
    </FORM>
</TABLE>


<% //tobe 여부를 확인하여 tobe일 경우 차시가 바로 넘어오므로 컨텐츠를 재생시킴

System.out.println("================================== s_tobeyn : " + s_tobeyn );
if(s_tobeyn.equals("Y")){%>
	<script language=javascript>
		<%if(s_lesson.equals("")){%>
			alert("차시가 존재하지 않습니다.");
		<%}else{%>
			setTimeout("starting('<%=s_lesson%>')",2000);
		<%}%>
	</script>
	
	
<%//asis 일 경우 기존대로 진행함
}else{ %>
	<!-- as-is 는 기존대로 수행 -->	
	<%	
	System.out.println("================================== v_contenttype : " + v_contenttype );
	if (v_contenttype.equals("M")){ %>
	<script language=javascript>
	//	var urlStr = 'http://contents.connect.or.kr/contents/setcookie.asp';
	//	top.ebody.location = urlStr;
	
	//	setTimeout("top.ebody.location='http://contents.connect.or.kr/contents/setCookie.asp'",1000);
		setTimeout("top.ebody.location = top.ebodyLoc",2000);
	</script>
	
	<%	}else if (v_contenttype.equals("N")){ %>
	<script language=javascript>
	//	var urlStr = 'http://contents.connect.or.kr/contents/setcookie.asp';
	//	top.ebody.location = urlStr;
		setTimeout("top.ebody.location = top.ebodyLoc",1000);
	</script>
	
	<%  }   %>
<%} %>
</body></html>
