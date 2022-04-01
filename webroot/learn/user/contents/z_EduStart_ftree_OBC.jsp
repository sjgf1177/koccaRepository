<%
//**********************************************************
//  1. 제      목: OBC MasterForm Tree Frame - Use Applet Tree
//  2. 프로그램명: z_EduStart_ftree_OBC.jsp
//  3. 개      요: OBC MasterForm Tree Frame - Use Applet Tree
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 06
//  7. 수      정: 정진필  2006/10/12 - Applet Tree를 Javascript dTree로 변환
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/website/common/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null)
    {
        box = RequestManager.getBox(request);
    }

	String treeTitle = (String) request.getAttribute("treeTitle");
	Map progressDataMap = (HashMap) request.getAttribute("progressDataMap");

    String s_subj = box.getSession("s_subj");
    String s_year = box.getSession("s_year");
    String s_subjseq = box.getSession("s_subjseq");

    String v_ispreview = box.getString("p_ispreview"); //맛보기
    String v_review = box.getString("p_review");       // 복습

    if( v_ispreview.equals("Y") || v_review.equals("Y") || s_year.equals("2000") ) {
      s_year = "PREV";   //진도를 쌓지 않습니다.
    }

    int p_branch = 0;
    if (!box.getString("p_branch").equals(""))
        p_branch = Integer.parseInt(box.getString("p_branch"));

    String p_tmp1 = box.getString("p_tmp1");

    MasterFormData da =(MasterFormData)request.getAttribute("MasterFormData");

    String v_otbgcolor = da.getOtbgcolor();
    if ( v_otbgcolor == null || v_otbgcolor.equals("") )
        v_otbgcolor = "FFFFFF";
    String v_fontobj = da.getFontobj();
    String v_dir = da.getDir();
    String v_contenttype = da.getContenttype();
    String v_isconfirmed = "N";

    int v_curbranch = 99;
    int v_mybranch = 99;
    int v_firstBranchObjectIndex = 0;
    String v_mybrname = "";

    if (v_mybranch!=99) v_isconfirmed = "Y";

    String  v_base_url  = "/servlet/controller.contents.EduStart?p_process=";
    String  v_eduChkURL = v_base_url + "eduCheck";  //진도체크URL
    String  v_examURL   = "/servlet/controller.exam.ExamServlet?p_process=UserResultListPage&p_lesson=";        //평가URL
    String  v_sulURL    = "/servlet/controller.research.SulmunServlet?p_process=UserResultListPage&p_f_gubun=C";//설문URL
    String  v_brCntlURL = v_base_url + "branchControl";         //분기제어URL
    String  v_eduMemURL = v_base_url + "eduMemList";            //동료학습자보기URL
    String  v_url_gong  = v_base_url + "fmain&p_ispreview=" + v_ispreview
							+ "&p_subj=" + s_subj + "&p_year=" + s_year + "&p_subjseq=" + s_subjseq;
    //String  v_url_gong  = "/servlet/controller.study.SubjGongStudyServlet?p_process=select&p_subj="
    //                    + s_subj+"&p_year="+s_year+"&p_subjseq="+s_subjseq;
    String  v_branchurl = v_base_url + "tree&p_branch=";

    String  v_imgURL    = "/images/user/contents/"; //image Base URL

    ArrayList   td  = (ArrayList)request.getAttribute("TreeData");  // Tree Data
    OBCTreeData ot  = null;                                         // Tree Data Beans
%>
<html>
<head>
	<title>학습 목차</title>
	<style>
		body
		{
			scrollbar-3dlight-color:#FFFFFF;
			scrollbar-arrow-color:#666666;
			scrollbar-track-color:#FFFFFF;
			scrollbar-darkshadow-color:#FFFFFF;
			scrollbar-face-color:#FFFFFF;
			scrollbar-highlight-color:#C9C9C9;
			scrollbar-shadow-color:#C9C9C9;
		}
	</style>
	<link rel="StyleSheet" href="/website/user/common/dtree/dtree.css" type="text/css" />
	<script type="text/javascript" src="/website/user/common/dtree/dtree.js"></script>
	<script language="javascript" src="/website/user/common/control.js"></script>
    <SCRIPT LANGUAGE="JavaScript">
        var cModule ="";
        var cLesson ="";
        var cObject ="";
        var cIndex  =0;
        var cAR     =null;
        var vFrom   ="justChk";
        var myBranch = <%=v_mybranch%>;
        var curBranch= <%=v_curbranch%>;

		function nodeCheck() {
			//if ( top.echk.document.form1.p_check.value == "true" ) {
				d.setStatus( cIndex, d.icon.nodeCheck );
			//}
		}

		// 진도 체크
        function eduCheck( gubun ){
		<%
			if (!s_year.equals("PREV")){
		%>

            var  url = '<%=v_eduChkURL%>&p_module='+cModule+'&p_lesson='+cLesson+'&p_oid='+cObject
                     + '&p_curidx='+cIndex+'&p_from='+vFrom+'&p_branch='+curBranch+'&p_gubun='+gubun;

            top.echk.location = url;
            vFrom = "justChk";

			if ( gubun == "END" ){
				setTimeout("nodeCheck()",1000);
			}
		<%
			}
		%>
			return "OK";
        }

        function eduChk(){
        	eduCheck( "END" );
        }

        function eduChkStart(){
        	eduCheck( "START" );
        }

		// 목차에서 Object 선택
        function whenObj(idx){
            var rs = getMLO(idx);
            var as = rs.split(",");

            if (as[3]!="SC"){
                if (as[3] == "LE"){
                    if (as[9]=="BRANCH" && myBranch ==99){
                        gotoBranchPage();
                        return;
                    }
                }
                nodeStatusChange(idx);
                return;
            }

            jumpTo(idx);
        }

		// 다음 Object로 이동
        function goNext(){
            setTimeout("jumpNext()",1500);
        }

        // 다음 Object 로 이동
        function jumpNext(){
            setNextMLO();
            jumpTo(cIndex);
        }

		// 이전 Object로 이동
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

		// 해당 idx의 Object로 이동
        function jumpTo(idx){
            var rs = null;

			rs = getMLO(idx);

            if(rs=="NOTEXIST"){
                alert("Object가 존재하지 않습니다.");
                return;
            }

            setCurrentByIndex(idx);

            //if(isBranchSubj=="Y" && cAR[9]=="BRANCH" && myBranch==99){
            //    alert("HEY2");
            //    gotoBranchPage();
            //    return;
            //}

            if (cAR[3]!="SC"){
                cIndex = getNextSCO(idx);
                if (cIndex==9999)
                   return;
                setCurrentByIndex(cIndex);
            }

			// 목차의 해당 Object 상위 노드를 Open함
            d.openTo( cIndex-1, true );
			d.refresh();
			d.changeNodeIcon( cIndex, d.icon.nodeIng );

            top.etree_fset.ebott.changeObj(cObject,cAR[6]);
            top.etree_fset.ebott.changeUrl(cAR[8]);

            if (cAR[4].substring(0,1)=="T") {
                whenExam();
            }else if (cAR[4]=="RP"){
                whenReport();
            }else{
            	eduChkStart();

				// 1/1 페이지 일 경우, 강제로 진도끝 체크 함
            	var v_mypn = parseInt(top.etree_fset.ebott.document.fobj.p_mypage.value,10);
				var v_topn = parseInt(top.etree_fset.ebott.document.fobj.p_pages.value,10);

				if ( v_mypn == 1 && v_topn == 1 ) {
					setTimeout( "eduChk()", 1500);
				}

                /* 분기과정 사용않함
                if (curBranch==myBranch){
                    isOk = eduChk2('START');
                    top.echk.location = '<%=v_eduChkURL%>&p_module='+cModule+'&p_lesson='+cLesson+'&p_oid='+cObject
                                       + '&p_curidx='+cIndex+'&p_from='+vFrom+'&p_branch='+curBranch +'&p_gubun=START';
                }
                */
                top.etree_fset.ebody.location=cAR[8];
                top.etree_fset.ebott.setCompleted( true );
            }
        }

		// 다음 Object에 대해 Module, Lesson, Object를 Setting
        function setNextMLO(){
            cIndex++;
            setCurrentByIndex(cIndex);
        }

		// 다음 Object에 대해 Module, Lesson, Object를 Setting
        function setCurrentByIndex(idx){
            cIndex  = idx;
            var rs = getMLO(cIndex);
            if(rs =="NOTEXIST"){
            	alert( "학습의 마지막 부분을 학습하셨습니다. 처음으로 이동하겠습니다." );
                setCurrentByIndex(1);
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

        // Form idx에 대한 Object 정보를 가져온다
        function getMLO(idx){
            for(var i=0;i<document.ff.length;i++){
                if (parseInt(document.ff.elements[i].name,10) == idx){
                    return  document.ff.elements[i].value;
                }
            }
            return "NOTEXIST";
        }

        //
        function getNextSCO(idx){
            var as;
            for(var i=idx;i<document.ff.length;i++){
                as = document.ff.elements[i].value.split(",");
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
      		// alert("Loading Failed.. Ask to Administrator please..");
            return 9999;
        }

		// 평가로 이동
        function whenExam(){
            //var url = "zeduobj_gong.exjumps?p_subj=<%=s_subj%>&p_year=<%=s_year%>&p_subjseq=<%=s_subjseq%>&p_module="+cModule;
            //url+= "&p_lesson="+cLesson+"&'p_oid="+cObject;
            url = '<%=v_examURL%>'+cLesson+"&p_oid="+cObject;

            top.etree_fset.ebody.location = url;
        }

		// 레포트로 이동
        function whenReport(){
            var url = "";
            top.etree_fset.ebody.location = url;
        }

		// 공지사항으로 이동
        function whenGong(){
            top.etree_fset.ebody.location="<%=v_url_gong%>&p_contenttype=<%=v_contenttype%>";
        }

		// 시작 페이지로 이동
        function whenStart(mo,le,obj){

            for(var i=0;i<document.ff.length;i++){
                as = document.ff.elements[i].value.split(",");

                if (as[0]==mo && as[1]==le && as[2]==obj){
                    jumpTo(parseInt(document.ff.elements[i].name,10));
                    return;
                }
            }
            jumpTo(1);
        }

		// Tree 영역 사이즈 변경
        function resizeEtree(){
            top.switchTreeFrameSize()
            if(top.f_min){
                document.resizeImg.src="/image/contents/show.gif";
            }else{
                document.resizeImg.src="/image/contents/hide.gif";
            }
        }

		/**
		 * 아마도 사용 하지 않는 함수들 : 삭제 예정
		// 해당 Page로 이동
        function jumpToPage(mod,les,obj,cpage,npage,urls){alert("5555goonica");

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

        //분기과목만 쓰는 함수로 현대기아자동차에서는 쓰이지 않는 함수이다.
        function eduChk2(gubun){
		<%
			if (!s_year.equals("PREV")){
		%>
            var  url = '<%=v_eduChkURL%>&p_module='+cModule+'&p_lesson='+cLesson+'&p_oid='+cObject
                     + '&p_curidx='+cIndex+'&p_from='+vFrom+'&p_branch='+curBranch+'&p_gubun='+gubun;
            top.echk.location = url;
            setTimeout("goOnIcarus()",2000);
		<%
			}
		%>
            return "OK";
        }

        function goOnIcarus(gubun){
		<%
			if (!s_year.equals("PREV")){
		%>
            vFrom = "justChk";
            return "OK";
		<%
			}
		%>
        }

		// Brach
        function selBranchOption(){
            var br = document.f1.p_branch.options[document.f1.p_branch.selectedIndex].value;
            document.location ="<%=v_branchurl%>"+br;
        }

		// Brach
        function calledFromBranchPage(branchId){
            document.location="<%=v_branchurl%>"+branchId+"&p_tmp1=first";
        }
		*/
    </SCRIPT>
    </head>

    <body bgcolor="<%=v_otbgcolor%>" topmargin="0" leftmargin="5" marginwidth="0" marginheight="0">
	<!-- 트리 Show/Hide -->
	<div id="resize" style="text-align:right; width:100%; margin-bottom:6px; background:#F4F3F3">
	  <a href="javascript:resizeEtree();"><img name="resizeImg" src="/image/contents/hide.gif" alt="Resize" border="0" width="60"></a>
	</div>
    <!-- 공지사항 -->
    <div style="margin-bottom:5px">
      <a href="#" onclick="javascript:whenGong();"><img src="/image/contents/notice_icon.gif" border="0"/></a>
    </div>
    <!-- dtree 시작 -->
    <div id="dtree" class="dtree">
	  <script type="text/javascript">
        <!--
		d = new dTree('d');

		d.config.useSelection = false;
		d.config.useStatusText = false;

		d.add(0,-1,'');

<%
	//Paramter Rule = Index||유형(MODULE,LESSON,SC)||목차명||Type(1001,1002,1003)||Mode(SC,TM,TT)||진도여부(Lesson_OID)
	String param[] = null;

	int id = 0;
	String objType = "";
	String title = "";
	String iconType = "";
	String iconMode = "";
	String lessonOid = "";;

	int pid = 0;
	int moduleId = 0;
	int lessonId = 0;

   	for ( int i=0; i<td.size(); i++ )
   	{
       	ot = (OBCTreeData)td.get(i);
		param = ot.getAp().split("\\|\\|", -1);

		id = Integer.parseInt(param[0]);
		objType = param[1];
		title = param[2];
		iconType = param[3];
		iconMode = param[4];
		lessonOid = param[5];

		// 특수문자 처리
		title = title.replaceAll("\'", "\\\\\'");
		title = title.replaceAll("\"", "\\\\\"");

		// pid set
		if ( objType.equals("MODULE") )
		{
		    pid = 0;
		    moduleId = id;
		}
		else if ( objType.equals("LESSON") )
		{
		    pid = moduleId;
		    lessonId = id;
		}
		else if ( objType.equals("SC") )
		{
		    pid = lessonId;
		}

		// tree node add
		String iconPrefix = "d.icon.";
		String iconName = "";
		if ( objType.equals("MODULE") )
		{
		    iconName = "module" + iconType;
%>
		d.add( <%= id %>, <%= pid %>, '<%= title %>', '', '<%= title %>', '', <%= iconPrefix+iconName %> );
<%
		}
		else if ( objType.equals("LESSON") )
		{
		    iconName = "lesson" + iconType;
%>
		d.add( <%= id %>, <%= pid %>, '<%= title %>', '', '<%= title %>', '', <%= iconPrefix+iconName %> );
<%
		}
		else
		{
		    
%>
		
<%

        if ( iconMode.equals("SC") )
        {
	        iconName = "node" + iconType;
        }
        else if ( iconMode.equals("TM") )
        {
	        iconName = "nodeTM";
        }
        else if ( iconMode.equals("TT") )
        {
	        iconName = "nodeTT";
        }
%>
		d.add( <%= id %>, <%= pid %>, '<%= title %>',
				'javascript:whenObj(\'<%=id%>\')', '<%= title %>', '', <%= iconPrefix+iconName %> );
<%
		}
%>
		d.setStatus( <%= id %>, <%= iconPrefix+iconName %> );
<%
   	}
%>
    	document.write(d);
		d.closeAll();
		//-->
	  </script>
    </div>
    <!-- dtree 끝 -->

    <form name="ff">
<%
	for(int i=0; i<td.size(); i++)
	{
        ot = (OBCTreeData)td.get(i);
%>
        <input type=hidden name="<%=i+1%>" value="<%=ot.getAh()%>">
<%
	}
%>
    </form>
</body></html>
