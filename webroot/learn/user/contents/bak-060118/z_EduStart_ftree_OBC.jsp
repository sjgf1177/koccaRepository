<%
//**********************************************************
//  1. 제      목: OBC MasterForm Tree Frame - Use Applet Tree
//  2. 프로그램명: z_EduStart_ftree_OBC.jsp
//  3. 개      요: OBC MasterForm Tree Frame - Use Applet Tree
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 06
//  7. 수      정:
//*********************************************************** 
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.common.*" %>

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  s_userid    = box.getSession("userid");    
    String  s_name      = box.getSession("name");
    String  s_subj      = box.getSession("s_subj");
    String  s_year      = box.getSession("s_year");
    String  s_subjseq   = box.getSession("s_subjseq");
    String  s_gadmin    = box.getSession("gadmin");
	
    int     p_branch    = 0;
    if (!box.getString("p_branch").equals(""))  p_branch    = Integer.parseInt(box.getString("p_branch"));
    String  p_tmp1      = box.getString("p_tmp1");
   

    MasterFormData  da  =(MasterFormData)request.getAttribute("MasterFormData");
    String  v_otbgcolor     = da.getOtbgcolor();
    if(v_otbgcolor == null || v_otbgcolor.equals(""))   v_otbgcolor = "FFFFFF";
    String  v_fontobj       = da.getFontobj();
    String  v_dir           = da.getDir();
    String  v_contenttype   = da.getContenttype();
    String  v_isconfirmed   = "N";

    int     v_curbranch     = 99;
    int     v_mybranch      = 99;
    int     v_firstBranchObjectIndex = 0;
    String  v_mybrname      = "";

    if (v_mybranch!=99) v_isconfirmed = "Y";
    
    String  v_base_url  = "/servlet/controller.contents.EduStart?p_process=";
    String  v_eduChkURL = v_base_url + "eduCheck";  //진도체크URL
    String  v_examURL   = "/servlet/controller.exam.ExamServlet?p_process=UserResultListPage&p_lesson=";        //평가URL
    String  v_sulURL    = "/servlet/controller.research.SulmunServlet?p_process=UserResultListPage&p_f_gubun=C";//설문URL
    String  v_brCntlURL = v_base_url + "branchControl";         //분기제어URL
    String  v_eduMemURL = v_base_url + "eduMemList";            //동료학습자보기URL
    String  v_url_gong  = "/servlet/controller.study.SubjGongStudyServlet?p_process=select&p_subj="
                        + s_subj+"&p_year="+s_year+"&p_subjseq="+s_subjseq;
    String  v_branchurl = v_base_url + "tree&p_branch=";                        
    
    String  v_imgURL    = "/images/user/contents/"; //image Base URL
    
    ArrayList   td  = (ArrayList)request.getAttribute("TreeData");  // Tree Data
    OBCTreeData ot  = null;                                         // Tree Data Beans
    
//    System.out.println("getRequestURI " + request.getRequestURI());
//    System.out.println("getRemoteUser " + request.getRemoteUser());
//    System.out.println("getRemoteAddr " + request.getRemoteAddr());
//    System.out.println("getRemoteHost " + request.getRemoteHost()); 
//    System.out.println("getProtocol() " + request.getProtocol()); 

    
    String v_appletImgUrl = "";
    String userip = request.getRemoteAddr();
    
    int idx = userip.indexOf(".");
    String firstip = StringManager.substring(userip, 0, idx);
    if(firstip.equals("150")) {
        v_appletImgUrl = "http://210.118.65.137/images/object/otree/";
    }
    else {
        v_appletImgUrl = "http://210.118.65.137/images/object/otree/";
    }
        System.out.println("v_appletImgUrl " + v_appletImgUrl);
%>


<HTML><HEAD><TITLE></TITLE>
    <style>
    <!--
        A:link    {font: 9pt GulimChe,돋움체; color: 000000; text-decoration: none}
        A:active  {font: 9pt GulimChe,돋움체; color: 000000; text-decoration: none}
        A:visited {font: 9pt GulimChe,돋움체; color: 000000; text-decoration: none}
        A:hover   {text-decoration: underline;}
        td        {font: 9pt GulimChe,돋움체;}
        .tdw      {font: bold 9pt GulimChe,돋움체;}
        .font     {font: bold 9pt GulimChe,돋움체;}
        .netist   {font: bold 9pt GulimChe,돋움체; color: gray;}
    -->
    </style>
    <!-- CopyRight. Credu.Corp.  written by LeeSuMin -->
    <script language="javascript" src="/Contents2/Object/common/script/control.js"></script>
    <SCRIPT LANGUAGE="JavaScript">
        var cModule ="";
        var cLesson ="";
        var cObject ="";
        var cIndex  =0;
        var cAR     =null;
        var vFrom   ="justChk";
        var isLoaded = false;
        var myBranch = <%=v_mybranch%>;
        var curBranch= <%=v_curbranch%>;

        function whenObj(idx){
            var rs = getMLO(idx);
            var as = rs.split(",");
            if (as[3]!="SC"){
                if (as[3] == "LE"){
                    if (as[9]=="BRANCH" && myBranch ==99){
//alert("HEY");
                        gotoBranchPage();
                        return;
                    }
                }
                nodeStatusChange(idx);
                return;
            }
            jumpTo(idx);

        }

        function eduChk(){

<%  if (!s_year.equals("PREV") ){   %>
            var  url = '<%=v_eduChkURL%>&p_module='+cModule+'&p_lesson='+cLesson+'&p_oid='+cObject
                     + '&p_curidx='+cIndex+'&p_from='+vFrom+'&p_branch='+curBranch+'&p_gubun=END';
//alert("EduChechk="+url);
            top.echk.location = url;
            vFrom = "justChk";
<%  }   %>
        }
        //분기과정만 쓰는 함수로 현대기아자동차에서는 쓰이지 않는 함수이다.
        function eduChk2(gubun){

<%         if (!s_year.equals("PREV")){ %>
            var  url = '<%=v_eduChkURL%>&p_module='+cModule+'&p_lesson='+cLesson+'&p_oid='+cObject
                     + '&p_curidx='+cIndex+'&p_from='+vFrom+'&p_branch='+curBranch+'&p_gubun='+gubun;
            top.echk.location = url;
            setTimeout("goOnIcarus()",2000);
<%         }    %>
            return "OK";
        }

        function goOnIcarus(gubun){
<%  if (!s_year.equals("PREV")){    %>
alert("goOnIcarus ="+gubun);
            vFrom = "justChk";
            return "OK";
<%  }   %>
        }
        
        function changeEduChk(idx){
            document.ICARUS.setObjStatus(idx,"COMPLETE");
        }
/*****
        function goNext(){
            vFrom = "normal";
            isOk = eduChk2('END');      //(7일 진도제한 Alert는 eduChk 프로그램에서 출력하도록 한다.)
            //document.ICARUS.setObjStatus(cIndex,"COMPLETE"); --> changeEduChk()에서 담당토록한다.
            setNextMLO();
            jumpTo(cIndex);
        }
*****/
        function goNext(){
            <% if (!s_year.equals("PREV")){ %>
            var  url = '<%=v_eduChkURL%>&p_module='+cModule+'&p_lesson='+cLesson+'&p_oid='+cObject
                     + '&p_curidx='+cIndex+'&p_from=normal&p_branch='+curBranch+'&p_gubun=END';
            top.echk.location = url;
            <% }    %>
            alert("url="+url);
            setTimeout("goOnIcarus2()",2000);
        }
        function goOnIcarus2(){
            document.ICARUS.setObjStatus(cIndex,"COMPLETE"); 
            setNextMLO();
            jumpTo(cIndex);
        }
        function jumpTo(idx){
            var rs = null;

			rs = getMLO(idx);
            if(rs=="NOTEXIST"){
                alert("NOT-EXISTED OBJECT");
                return;
            }
            if("<%=s_year%>"=="PREV"){
                as = rs.split(",");
                if(as[0]!="PR"){
                    alert("[과정맛보기] 부분만 검토하실수 있습니다.");
                    cModule="PR";
                    return;
                }
            }

            setCurrentByIndex(idx);

            //alert(idx+"."+cAR[3]+"."+cAR[4]+"."+cAR[5]+"."+cAR[9]);
            
            //if(isBranchSubj=="Y" && cAR[9]=="BRANCH" && myBranch==99){
            //    alert("HEY2");
            //    gotoBranchPage();
            //    return;
            //}

            if (cAR[3]!="SC"){
                cIndex = getNextSCO(idx);
                if (cIndex==9999)   return;
                setCurrentByIndex(cIndex);
            }

            openUppers(cIndex);
            //top.etree_fset.ebody.location=cAR[8];
            top.etree_fset.ebott.changeObj(cObject,cAR[6])
            top.etree_fset.ebott.changeUrl(cAR[8])
            document.ICARUS.setObjStatus(cIndex,"ING");

            if (cAR[4].substring(0,1)=="T") {
                whenExam();
            }else if (cAR[4]=="RP"){
                whenReport();
            }else{
//alert("Branch="+curBranch+":"+myBranch);
                //if (curBranch==myBranch){
                //    isOk = eduChk2('START');
                    //top.echk.location = '<%=v_eduChkURL%>&p_module='+cModule+'&p_lesson='+cLesson+'&p_oid='+cObject
                    //                   + '&p_curidx='+cIndex+'&p_from='+vFrom+'&p_branch='+curBranch +'&p_gubun=START';
                //}           
                top.etree_fset.ebody.location=cAR[8];
            }
        }
        function whenExam(){
            //var url = "zeduobj_gong.exjumps?p_subj=<%=s_subj%>&p_year=<%=s_year%>&p_subjseq=<%=s_subjseq%>&p_module="+cModule;
            //url+= "&p_lesson="+cLesson+"&'p_oid="+cObject;
            url = '<%=v_examURL%>'+cLesson+"&p_oid="+cObject;
//alert(url);           
            top.etree_fset.ebody.location = url;

        }
        function whenReport(){
            var url = "";
            top.etree_fset.ebody.location = url;

        }
        function goPrev(){
            var bModule,bLesson,bObject;
            var bidx = 0;
            for(var i=0;i<document.ff.length;i++){
                as = document.ff.elements[i].value.split(",");
                if (as[3]=="SC"){
                    bModule = as[0];
                    bLesson = as[1];
                    bObject = as[2];
                    bidx    = i;
                }
                if (as[2]==cObject && as[0]==cModule && as[1]==cLesson){
                    jumpTo(bidx);
                }
            }
        }
        function whenStart(mo,le,obj){

            for(var i=0;i<document.ff.length;i++){
                as = document.ff.elements[i].value.split(",");

                if (as[0]==mo && as[1]==le && as[2]==obj){
                    jumpTo(parseInt(document.ff.elements[i].name,10));
                    return;
                }
            }
            jumpTo(0);
        }
        function setNextMLO(){
            cIndex++;
            setCurrentByIndex(cIndex);
        }
        function setCurrentByIndex(idx){
            cIndex  = idx;
            var rs = getMLO(cIndex);
            if(rs =="NOTEXIST"){
                setCurrentByIndex(0);
                return;
            }
            cAR    = null;
            cAR    = rs.split(",");
            cModule = cAR[0];
            cLesson = cAR[1];
            cObject = cAR[2];

            top.etop.setVals(cModule,cLesson,cObject,cIndex);

        }
        function setCurrent(idx,mod,les,oid){
            setCurrentByIndex(idx);
        }
        function getMLO(idx){
            for(var i=0;i<document.ff.length;i++){
                if (parseInt(document.ff.elements[i].name,10) == idx){
                    return  document.ff.elements[i].value;
                }
            }
            return "NOTEXIST";
        }
        function getNextSCO(idx){
            var as;
            for(var i=idx;i<document.ff.length;i++){
                as = document.ff.elements[i].value.split(",");
        //        alert(as[3]+"."+as[9]+".  "+document.ff.elements[i].value)
                if (as[3]=="SC"){
                    return parseInt(document.ff.elements[i].name,10);
                }
           //     if (as[3]=="LE" && isBranchSubj=="Y" && as[9]=="BRANCH" && myBranch==99){
           //         alert("HEY3");
           //         gotoBranchPage();
           //         return 9999;
           //     }
            }
            for(var i=0;i<idx;i++){
                as = document.ff.elements[i].value.split(",");
                if (as[3]=="SC"){
                    return parseInt(document.ff.elements[i].name,10);
                }
            }
      //     alert("Loading Failed.. Ask to Administrator please..");
            return 9999;
        }
        function openUppers(idx){
            for(var i=0;i<idx;i++){
                as = document.ff.elements[i].value.split(",");
                if (as[3]!="SC" && as[0]==cModule && as[1]==cLesson){
                    nodeOpen(i);
                }
            }
        }

        function whenGong(){
            top.etree_fset.ebody.location="<%=v_url_gong%>&p_contenttype=<%=v_contenttype%>";
        }

        // Applet Handling
        function nodeOpen(idx){
            document.ICARUS.nodeOpen(idx);
        }
        function nodeClose(idx){
            document.ICARUS.nodeClose(idx);
        }
        function nodeStatusChange(idx){
            document.ICARUS.nodeStatusChange(idx);
        }
        function setAppletCurIdx(idx){
            document.ICARUS.setCurrent(idx);
        }
        function wideUp(w){
            document.ICARUS.width=w;
            document.ICARUS.setResize(w,650);
        }

        //Window XP일 경우 JVM 설치하기 위한 함수모음(startOK, ifXP, excuteJVMInstall).
        //Applet이 정상적으로 로딩되면 applet에서 startOK를 호출한다. 5초동안 startOK가 미호출되면
        //Applet기동이 불가한 것으로 판단하여 JVM설치하도록 한다.
        function startOK(ii){
            isLoaded = true;
<%  if (p_tmp1.equals("first")){    %>
            //alert(<%=v_firstBranchObjectIndex%>);
            setTimeout("jumpTo(<%=v_firstBranchObjectIndex%>)",1000);
            //jumpTo(<%=v_firstBranchObjectIndex%>);

<%  }   %>
        }
        function ifXP(){
            var isXP = false;
            if (navigator.appVersion.indexOf("Windows NT 5.1")!=-1){
                 isXP=true;
            }
            if(isXP){
                setTimeout("excuteJVMInstall()",10000);
            }
        }
        function excuteJVMInstall(){
            if (isLoaded==false){
                    window.open("zeduobj_start_applet.get_plugin","플러그인다운로드","width=500, height=500");
            }
        }
        function excuteJVMInstall1(){
            if (isLoaded==false){

                var msg ="학습자께서는 Windows XP 운영체제를 사용하고 있으며, 본 학습을 진행하기 위해서는 Plug-in을 설치하셔야 합니다.\n\n"
                msg += "자동설치를 원하시면 [OK]버튼을 누르시고, \n\n다운로드설치를 원하시면 [취소]를 눌러주십시오\n\n";
                if (confirm(msg)){
                    msg = "잠시후 Plug-in설치 화면이 나오면 설치를 진행하여 주십시오.\n\n";
                    msg += "(네트웍 환경에 따라 설치완료시까지 10분~20분가량 소요)\n\n";
                    msg += "5분이상 지나도 설치화면이 나오지 않으면 학습창을 재기동하신 후\n\n";
                    msg += "[다운로드설치]를 선택하여 주시기 바랍니다.\n\n";
                    
             //       alert(msg);
                    top.echk.location="http://java.sun.com/webapps/getjava/BrowserCheck";
                }else{
                    window.open("zeduobj_start_applet.get_plugin","Plugin","width=500, height=500");
                }
            }
        }

        function jumpToPage(mod,les,obj,cpage,npage,urls){

            for(var i=0;i<document.ff.length;i++){
                as = document.ff.elements[i].value.split(",");
                if (as[3]=="SC"){
                    bModule = as[0];
                    bLesson = as[1];
                    bObject = as[2];
                    bidx    = i;
                }
                if (as[2]==obj && as[0]==mod && as[1]==les){
                    jumpTo(bidx);
                    break;
                }
            }

            top.etree_fset.ebody.location = urls;
            top.etree_fset.ebott.fobj.p_mypage.value=parseInt(cpage,10,10);
            //Added 20020805. LeeSuMin(for use Multi-Server)
            top.etree_fset.ebott.changeUrl(urls);

        }
        ifXP();

        function resizeEtree(){
            top.switchTreeFrameSize()
            if(top.f_min){
                document.resizeImg.src="/images/object/show.gif";
            }else{
                document.resizeImg.src="/images/object/hide.gif";
            }
        }
        function selBranchOption(){
            var br = document.f1.p_branch.options[document.f1.p_branch.selectedIndex].value;
            document.location ="<%=v_branchurl%>"+br;
        }
        /* Branch */
        function calledFromBranchPage(branchId){
   //         alert(branchId);
    //        alert("<%=v_branchurl%>"+branchId+"&p_tmp1=first");
            document.location="<%=v_branchurl%>"+branchId+"&p_tmp1=first";
        }

    </SCRIPT>
    </head>

    <body bgcolor="<%=v_otbgcolor%>" leftmargin=0>
    <FORM name="f1">
    <TABLE border=0 cellpadding=0 cellspacing=0 align=right valign=top width=100% bgcolor=F4F3F3>
        <TR><td align=right height=22>
            <a href="javascript:resizeEtree()"><img name="resizeImg" src="/images/object/hide.gif" alt="Resize" border=0 width=60></a>
        </td></TR>
    </TABLE><br><br>
    <TABLE border=0 align=center valign=top width=100%>
        <TR><td>
    
        <a href="javascript:whenGong()"><img src="/images/object/applet_gong.gif" border=0></a>


           </td>
    </TR></TABLE>
        <applet NAME="ICARUS" CODEBASE="/myclasses/OBC" CODE="creTreeApplet.class" WIDTH=170 HEIGHT=500 MAYSCRIPT> 
        <param NAME="baseImgURL"  VALUE="<%=v_appletImgUrl%>">
        <param NAME="AUTHOR"      VALUE="KangSeongWoog(Credu)">
        <param NAME="TEXTCOLOR"   VALUE="000000">
        <param NAME="ACTIVECOLOR" VALUE="ff0000">
        <param NAME="SCROLLCOLOR" VALUE="ffffee">
        <param NAME="MAYSCRIPT"   VALUE="true">
        <param NAME="BGCOLOR"     VALUE="<%=v_otbgcolor%>">
        <param NAME="FONT"        VALUE="굴림,0,12">


<%  for(int i=0;i<td.size();i++){   
        ot = (OBCTreeData)td.get(i);    %>   
        <PARAM name="DATA<%=i%>"   value="<%=ot.getAp()%>">
<%  }   %>
        <param NAME="objCnt"     VALUE="<%=td.size()%>">
    </applet>
    </FORM>
    <br>

    <form name="ff">
<%  for(int i=0;i<td.size();i++){   
        ot = (OBCTreeData)td.get(i);    %>  
        <input type=hidden name="<%=i%>"   value="<%=ot.getAh()%>">
<%  }   %>  
    </form>
</body></html>
