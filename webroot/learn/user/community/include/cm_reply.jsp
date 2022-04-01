<script type='text/javascript' src='/script/portal/prototype-1.6.0.2.js'></script>
<script language="JavaScript">
<!--
	//ajax 실행시 로딩 상태 만들기
	function cmMakeLoading()
	{
	    var inHtml  = "";
	
	    inHtml = "<table class=\"answer bo_bn\">"
	           + "  <tr><td align='center' valign='middle'><img src='/image/portal/common/ajax-loader1.gif'/></td></tr>"
	           + "</table>";
	
	    return inHtml;
	}   
	
	// ajax 실행시 로딩 상태 만들기
	function cmTargetMakeLoading( obj_name )
	{
	    var obj = document.getElementById(obj_name);
	
	    obj.innerHTML = cmMakeLoading();
	}

    function replyInit() {
        var listDiv = document.getElementById("replay_div");
        
        cmTargetMakeLoading("replay_div");
        
        var url     = "./include/cm_reply_ajax.jsp";
        var pars    = "p_cmuno=<%=v_cmuno%>"
                    + "&p_menuno=<%=v_menuno%>"
                    + "&p_brdno=<%=v_brdno%>";
                    
        new Ajax.Updater({success: 'reply_div'}, url, {method : 'post', parameters : pars, onFailure: reportError});

        fnTypingCheck();
    }


    function uf_insertMemoOK(){
       
        if(document.form1.p_rep_content.value ==''){
        	alert('댓글 내용을 입력하여야합니다.');document.form1.p_rep_content.focus();return;
        }    
                      
        if (confirm("댓글을 등록하시겠습니까?")) {           

            var url   = "/servlet/controller.community.CommunityReplyServlet";
            var pars  = "p_process=insertReply"
                     + "&p_rep_content=" + document.getElementById("p_rep_content").value 
                     + "&p_cmuno=" + encodeURIComponent("<%=v_cmuno%>")
                     + "&p_menuno=" + encodeURIComponent("<%=v_menuno%>")
                     + "&p_brdno=" + encodeURIComponent("<%=v_brdno%>");

            new Ajax.Request (
                    url,
                    {
                        method: 'post',
                        parameters: pars,
                        onComplete:
                        function(xmlHttp){
        	                resultInsertMemo(xmlHttp);
                        }
                    }
            );
        }
    }
	              
	function resultInsertMemo(xmlHttp) {   
	      var serverData    = xmlHttp.responseText;         // JSon Data 값을 읽는다.
	      var evalData      = eval('(' + serverData + ')');
	      var sList_obj     = evalData.scfList;
	      
	      var reqState     = evalData.result[0].STATE;

	      if (reqState.toLowerCase() != "succ")
	      {
	          alert("Ajax 통신중 오류가 발생했습니다.");
	          return;
	      }else{
	    	  alert("댓글이 등록되었습니다.");
	    	  replyInit();
	      }
	} 

    function uf_deleteMemoOK(rplno){
        
        if (confirm("댓글을 삭제하시겠습니까?")) {           

            var url   = "/servlet/controller.community.CommunityReplyServlet";
            var pars  = "p_process=deleteReply"
                     + "&p_cmuno=" + encodeURIComponent("<%=v_cmuno%>")
                     + "&p_menuno=" + encodeURIComponent("<%=v_menuno%>")
                     + "&p_brdno=" + encodeURIComponent("<%=v_brdno%>")
                     + "&p_rplno=" + encodeURIComponent(rplno);

            new Ajax.Request (
                    url,
                    {
                        method: 'post',
                        parameters: pars,
                        onComplete:
                        function(xmlHttp){
                            resultDeleteMemo(xmlHttp);
                        }
                    }
            );
        }
    }
                  
    function resultDeleteMemo(xmlHttp) {   
          var serverData    = xmlHttp.responseText;         // JSon Data 값을 읽는다.
          var evalData      = eval('(' + serverData + ')');
          var sList_obj     = evalData.scfList;
          
          var reqState     = evalData.result[0].STATE;

          if (reqState.toLowerCase() != "succ")
          {
              alert("Ajax 통신중 오류가 발생했습니다.");
              return;
          }else{
              alert("댓글이 삭제되었습니다.");
              replyInit();
          }
    }              

	function replyInit(){
		document.getElementById("p_rep_content").value = "";
		//document.getElementById("div_cnt").innerHTML = "400";
		
	    var url     = "/servlet/controller.community.CommunityReplyServlet";
	    var pars  = "p_process=selectListReply"
	    	      + "&p_cmuno=" + encodeURIComponent("<%=v_cmuno%>")
                  + "&p_menuno=" + encodeURIComponent("<%=v_menuno%>")
                  + "&p_brdno=" + encodeURIComponent("<%=v_brdno%>");
    
        new Ajax.Updater({success: 'reply_div'}, url, {method : 'post', parameters : pars, onFailure: reportError});	
	}

	   function reportError(e) {
	        alert(e);
	    }  

	window.onload = function () { 
		replyInit();
	}

//-->
</script>

            <div id="reply_div"></div>
            <table class="answer mg_tn bo_tn">
            <colgroup><col width="126px" /><col /></colgroup>
            <tr>
                <th>댓글 입력</th>
                <!--td><textarea name="p_rep_content" id="p_rep_content" style="width:427px;" class="mg_r4" onchange="getObjectLength('p_rep_content', 400);" onkeydown="getObjectLength('p_rep_content', 400);"></textarea> <a href="javascript:uf_insertMemoOK()" class="btn_gr va_t"><span>댓글등록</span><div id="p_rep_content_length">400 Bytes</div></a></td-->
                <td><textarea name="p_rep_content" id="p_rep_content" style="width:427px;ime-mode:active;padding-left:10;padding-left:10;" class="mg_r4" onkeydown="fnTypingCheck('p_rep_content', 400, 'p_rep_content_length')" onchange="fnTypingCheck('p_rep_content', 400, 'p_rep_content_length')"></textarea> <a href="javascript:uf_insertMemoOK()" class="btn_gr va_t"><span>댓글등록</span><div id="p_rep_content_length">400 Bytes</div></a></td>
            </tr>
            </table>
