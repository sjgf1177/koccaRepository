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

    String  s_userid    = box.getSession("userid");
    String  s_name  = box.getSession("name");
    String  s_subj      = box.getSession("s_subj");
    String  s_year      = box.getSession("s_year");
    String  s_subjseq   = box.getSession("s_subjseq");
    String  s_gadmin    = box.getSession("gadmin");


    MasterFormData  da  =(MasterFormData)request.getAttribute("MasterFormData");
    String  v_ismfbranch    = da.getIsmfbranch();
    String  v_dir           = da.getDir();
    String  v_contenttype   = da.getContenttype();
    String  v_isconfirmed   = "N";
    int     v_mybranch      = EduEtc1Bean.get_mybranch(s_subj, s_year, s_subjseq, s_userid);
    if (v_mybranch!=99) v_isconfirmed = "Y";

    if(v_mybranch != 99)    v_isconfirmed = "Y";

    String  v_base_url      = "/servlet/controller.contents.EduStart?p_process=";
    String  v_eduChkURL = v_base_url + "eduCheck&p_lesson=";    //진도체크URL
    String  v_examURL   = "/servlet/controller.exam.ExamServlet?p_process=UserResultListPage&p_lesson=";        //평가URL
    //String    v_sulURL    = "/servlet/controller.research.SulmunServlet?p_process=UserResultListPage&p_f_gubun=C";//설문URL
    //String    v_sulURL    = "/servlet/controller.research.SulmunSubjUserServlet/p_process=SulmunUserPaperListPage";//설문
    String  v_sulURL    = "/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunUserPaperFinalListPage&p_subj=" + s_subj + "&p_year=" + s_year + "&p_subjseq=" + s_subjseq;
    String  v_brCntlURL = v_base_url + "branchControl";         //분기제어URL
    String  v_eduMemURL = v_base_url + "eduMemList";            //동료학습자보기URL

    String  v_imgURL    = EduEtc1Bean.make_eduURL(s_subj,"DOC")+"docs/menuimg"; //image Base URL

    String  v_isshowsel = "N";                  //차시선택Selection 조회여부
    ArrayList  list = (ArrayList)request.getAttribute("MfLessonList");
    MfLessonData x = null;
%>
<html>
<head>
<title>Education...</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<!-- CopyRights. Credu.Corp.  written by LeeSuMin -->
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

    var isChecked = false;

	function popup_window(url) {

//dhgo alert(url);

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
		alert(url);
	}

//	var url = '/servlet/controller.contents.EduStart?p_process=eduList';
//	var w = '800';
//	var h = '600';
//	window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width="+w+",height="+h+" ").focus();

	}

	function upWin(url,w,h){
		window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width="+w+",height="+h+" ").focus();
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
//dhgo alert('eduChk2_'+url);
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

//dhgo alert('eduChkStart='+url);

        return;
    }

    function startContents(gubun,types,urls){

//dhgo alert('startContents='+urls);

        parent.ebody.location =urls;
        isChecked = false;

        eduChkStart('START');
    }
    function goNext(){
        chkDates();

        var  strCurrent = "";
        var  current = parseInt(document.next.now.value,10);
        if (curbranch==mybranch && !isChecked){
            var  url = '<%=v_eduChkURL%>'+document.next.now.value;
            alert("url="+url);
            parent.echk.location = url;
        }

        var nowdate = document.next.now.value;

        //for(var i=0;i<document.sel.p_lesson.length;i++){
        //    arrStr = document.sel.p_lesson.options[i].value.split(",");
        //    if (arrStr[0]==nowdate){
        //        if (arrStr[2]=="Y"){
        //          //url = '<%=v_examURL%>'+nowdate;
        //            //parent.ebody.location = url;
        //            return;
        //        }else{
        //            break;
        //        }
        //    }
        //}

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

//alert('arrStr[0]='+arrStr[0]+', vals='+vals);

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
           //   parent.ebody.location ='<%=v_sulURL%>';
                return;
            }else{
                alert(vals+"차시는 존재하지 않습니다");
            }
            document.sel.p_lesson.options[seli].selected = true;
//alert("["+document.sel.p_lesson.options.selectedIndex+"]document.sel.p_lesson.options[seli].selected = "+document.sel.p_lesson.options[seli].selected);
            //zpack     parent.ebody.location = arrStr[1];
            startContents('','',arrStr[1]);
            return;

        } else {

            document.sel.p_lesson.options[seli].selected = true;
//alert("["+document.sel.p_lesson.options.selectedIndex+"]document.sel.p_lesson.options[seli].selected = "+document.sel.p_lesson.options[seli].selected);


                startContents('','',arrStr[1]);
                return;

        }
    }

    function whenSelect(ival){
//alert("inWhenSelect ival="+ival);
        var url=document.sel.p_lesson.options[ival].value;
        var arrStr = url.split(",");
        document.sel.p_lesson.options[ival].selected = true;
        document.next.now.value = arrStr[0];
        //zpack     parent.ebody.location = arrStr[1];
        startContents('','',arrStr[1]);
    }


    function memList(){
        uWin("<%=v_eduMemURL%>");
    }
    function starting(lesson){
        jumpTo('001');
    }
</script>
</head>

<body background="<%=v_imgURL%>/bar_title.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table  border="0" cellpadding="0" cellspacing="0" width="1010" align="LEFT" valign="TOP" marginwidth="0" marginheight="0" leftmargin="0" topmargin="0">
    <FORM name="sel">
<%  if  (v_isshowsel.equals("Y")){  // Lesson Select 보여줄 경우%>
    <TR>
        <TD height=46 width=320 align="LEFT" valign=top background="<%=v_imgURL%>/title1.gif">
            <img src="<%=v_imgURL%>/title1.gif" alt="A00015" valign=top border=0 height="46">
        </TD>
        <TD height=46 width=610 align="CENTER" valign=middle background="<%=v_imgURL%>/title2.gif">&#160;
            <SELECT name="p_lesson" onChange="whenSelect(document.sel.p_lesson.selectedIndex);">
        <%  for (int i=0; i<list.size(); i++) {
                x  = (MfLessonData)list.get(i);     %>
            <option value="<%=x.getLesson()%>,<%=x.getStarting()%>,<%=x.getIsexam()%>,<%=x.getIsbranch()%>"><%=x.getLesson()%>-<%=x.getSdesc()%></option>
        <%  }   %>

            </SELECT>
            <img src="<%=v_imgURL%>/title2.gif" valign="top" border="0" height="46">
        </TD>
        <TD height=46 width=80 valign=middle align="CENTER">
            <!--<a href="javascript:memList()">
            <img src="../../images/MasterForm/go.gif" alt="View learner/in-box" border=0></a>
            -->
            <img src="<%=v_imgURL%>/title3.gif" valign=top border=0>
        </TD>
    </TR>
    <TR><td colspan=3><br><br><br><br></td></TR>
<%  }else{  %>
<%		if (v_contenttype.equals("N")){ %>
    <TR>
        <TD height=29 width=320 align="LEFT" valign=top>
            <img src="<%=v_imgURL%>/title4.gif" alt="A00015" valign=top border=0 height=29>
        </TD>
        <TD height=29 width=400 align="CENTER" valign=middle>&#160;
        </TD>
        <TD height=29 width=80 valign=middle align="RIGHT"><a href="javascript:memList()">
            <img src="../../images/MasterForm/go.gif" alt="View learner/in-box" border=0></a>
        </TD>
    </TR>
<%		}	%>
    <TR>
        <td colspan=3>
        <!--<br><br><br><br>-->
            <SELECT name="p_lesson" onChange="whenSelect(document.sel.p_lesson.selectedIndex);">
        <%  for (int i=0; i<list.size(); i++) {
                x  = (MfLessonData)list.get(i); %>
                <option value="<%=x.getLesson()%>,<%=x.getStarting()%>,<%=x.getIsexam()%>,<%=x.getIsbranch()%>"><%=x.getLesson()%>-<%=x.getSdesc()%></option>
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
</body></html>
