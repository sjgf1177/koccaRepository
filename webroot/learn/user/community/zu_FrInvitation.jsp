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
    String  v_process    = box.getStringDefault("p_process","gomsnoticePop");
    String  v_static_cmuno = box.getString("p_static_cmuno");
    String  v_cmuno = box.getString("p_cmuno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");

    String  content      = box.getString("p_content");

    Vector  v_p_leftcbx          = box.getVector("leftcbx"  );
    Vector  v_p_left_userid      = box.getVector("p_left_userid"  );
    Vector  v_p_left_name        = box.getVector("p_left_name"    );
    Vector  v_p_left_email       = box.getVector("p_left_email"   );

    Vector  v_p_rightcbx          = box.getVector("rightcbx"  );
    Vector  v_p_right_userid      = box.getVector("p_right_userid"  );
    Vector  v_p_right_name        = box.getVector("p_right_name"    );
    Vector  v_p_right_email       = box.getVector("p_right_email"   );

    ArrayList list       = (ArrayList)request.getAttribute("list");
    if("Y".equals(box.getString("p_saveact"))){
     v_p_rightcbx          = new Vector();
     v_p_right_userid      = new Vector();
     v_p_right_name        = new Vector();
     v_p_right_email       = new Vector();


    }
%>  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//회원선택
function uf_msPopMember() {
   if(document.form1.p_allselect.checked == true){
       alert("전체회원이 선택되었습니다.");return;
   }
   open_window('msPopMember', '/servlet/controller.community.CommunityPopUpServlet?p_process=gomsmemberPop&p_static_cmuno='+document.form1.p_static_cmuno.value, 100, 100, 610, 480, 'no', 'no', 'no', 'no', 'no');
  
}
//등록
function uf_cfmOK() {
   if(document.form1.rightcnt.value==0){
      alert('전송할 회원을 선택해주세요');return;
   }

    if(document.form1.p_title.value ==''){
       alert('제목을 입력하여야합니다.');document.form1.p_title.focus();return;
    }

    

    document.form1.p_saveact.value ="Y";
    document.form1.p_blank.value ="";
    document.form1.action = "/servlet/controller.community.CommunityFrInvitationServlet";
    document.form1.p_process.value = "insertData";
    document.form1.submit();
}
//조회
function uf_searchOK() {
    if(document.form1.p_searchtext.value ==''){
       alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
    }

    document.form1.action = "/servlet/controller.community.CommunityFrInvitationServlet";
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}

//좌측 데이터 전체체크
function uf_leftAll(){
   var rowcnt = document.form1.leftcnt.value;

   if(rowcnt>0){
      if(document.form1.leftallcbx.checked == true){
          if(rowcnt ==1){
             document.form1.leftcbx.checked=true;
          } else {
             for(var i=0;i<rowcnt;i++){
                document.form1.leftcbx[i].checked=true;
             }  
          }
      }else {
          if(rowcnt ==1){
             document.form1.leftcbx.checked=false;
          } else {
             for(var i=0;i<rowcnt;i++){
                document.form1.leftcbx[i].checked=false;
             }  
          }

      }
   }
}

function uf_blankOK(blank) {
    document.form1.p_blank.value =blank;
    document.form1.action = "/servlet/controller.community.CommunityFrInvitationServlet";
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_static_cmuno"      value = "<%=v_static_cmuno%>">
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>">
<input type = "hidden" name = "p_blank"      value = "">
<input type = "hidden" name = "p_saveact"      value = "N">

            <h1><img src="/images/portal/community/tit_16.gif" alt="친구 초대하기" /></h1>

            <div class="invite_box">
                <p class="search_box"><img src="/images/portal/community/search_box_tit.gif" alt="회원명 검색" class="va_m" /> <input name="p_searchtext" type="text" class="inbox" style="width:225px"  value="<%=v_searchtext%>" onkeypress="javascript:fnKeyPressEnter(event, 'uf_searchOK');"/> <a href="javascript:uf_searchOK();"><img src="/images/portal/btn/btn_serbox_search.gif" class="va_m" alt="검색" /></a></p>
                
                <div class="invite_warp">
                    <div class="search_result">
                        <p class="tit"><span class="tit">회원검색결과</span><span class="fl_r"><input  name=leftallcbx type="checkbox"  onclick="uf_leftAll();"/> 전체선택</span></p>
                        <div class="scrolling">
                            <div>
                            <ul>
                            <% //DB에서 읽어오는경우
							   int i_leftcnt=0;
							   if ("".equals(box.getString("p_blank"))){//DB에서 읽어오는경우
							       for(int i=0;i<list.size();i++){     
							              DataBox dbox = (DataBox)list.get(i);
							              i_leftcnt++;
							%>
							    <input type=hidden name="p_left_userid"  value="<%=dbox.getString("d_userid")%>">
		                        <input type=hidden name="p_left_name"    value="<%=dbox.getString("d_name")%>">
		                        <input type=hidden name="p_left_email"   value="<%=dbox.getString("d_email")%>">
                                <li><input type=checkbox name="leftcbx" value="<%=dbox.getString("d_userid")%>"> <%=dbox.getString("d_name")%>(<%=dbox.getString("d_userid")%>)</li>
                             <%       }
							   } else if ("RIGHT".equals(box.getString("p_blank"))){//오른쪽으로 이동하는경우.
							       for(int i=0;i<v_p_left_userid.size();i++){
							           int v_eqcnt=0;
							           String vtmp1=(String)v_p_left_userid.elementAt(i);
							           for(int j=0;j<v_p_leftcbx.size();j++){
							               String vtmp2=(String)v_p_leftcbx.elementAt(j);
							               System.out.println(vtmp1+"   :  "+vtmp2);
							               if(vtmp1.equals(vtmp2))v_eqcnt++;
							           }
							           if(v_eqcnt==0){
							              i_leftcnt++;
							%>
							    <input type=hidden name="p_left_userid"  value="<%=(String)v_p_left_userid.elementAt(i)%>">
			                    <input type=hidden name="p_left_name"    value="<%=(String)v_p_left_name.elementAt(i)%>">
			                    <input type=hidden name="p_left_email"   value="<%=(String)v_p_left_email.elementAt(i)%>">
			                    <li><input type=checkbox name="leftcbx" value="<%=(String)v_p_left_userid.elementAt(i)%>"> <%=(String)v_p_left_name.elementAt(i)%>(<%=(String)v_p_left_userid.elementAt(i)%>)</li>
			                <%         } %>
							<%     }%>
							<%   } else if ("LEFT".equals(box.getString("p_blank"))){//왼쪽으로 이동하는경우.
							
							       for(int i=0;i<v_p_right_userid.size();i++){
							           int v_eqcnt=0;
							           String vtmp1=(String)v_p_right_userid.elementAt(i);
							           for(int j=0;j<v_p_rightcbx.size();j++){
							               String vtmp2=(String)v_p_rightcbx.elementAt(j);
							               if(vtmp1.equals(vtmp2))v_eqcnt++;
							           }
							           if(v_eqcnt>0){
							             v_p_leftcbx.addElement     ((String)v_p_right_userid.elementAt(i));
							             v_p_left_userid.addElement ((String)v_p_right_userid.elementAt(i));
							             v_p_left_name.addElement   ((String)v_p_right_name.elementAt(i));
							             v_p_left_email.addElement  ((String)v_p_right_email.elementAt(i));
							           }
							       }
							       for(int i=0;i<v_p_left_userid.size();i++){
							        i_leftcnt++;
							%>  
							    <input type=hidden name="p_left_userid"  value="<%=(String)v_p_left_userid.elementAt(i)%>">
			                    <input type=hidden name="p_left_name"    value="<%=(String)v_p_left_name.elementAt(i)%>">
			                    <input type=hidden name="p_left_email"   value="<%=(String)v_p_left_email.elementAt(i)%>">
			                    <li><input type=checkbox name="leftcbx" value="<%=(String)v_p_left_userid.elementAt(i)%>"> <%=(String)v_p_left_name.elementAt(i)%>(<%=(String)v_p_left_userid.elementAt(i)%>)</li>
			                <%
							      } 
							%>
							<%}%>
                            </ul>
                            </div>
                        </div>
                    </div>
      
                    <p class="btn"><a href="javascript:uf_blankOK('RIGHT')"><img src="/images/portal/btn/btn_arrow_right.gif" alt="오른쪽으로 이동" /></a><br /><a href="javascript:uf_blankOK('LEFT')"><img src="/images/portal/btn/btn_arrow_left.gif" alt="왼쪽으로 이동" /></a></p>
      
                    <div class="delivery">
                        <p class="tit"><span class="tit">발송명단</span></p>
                        <div class="scrolling">
                            <div>
                            <ul>      
							<% 
							   int i_rightcnt=0;
							   if ("".equals(box.getString("p_blank"))){//DB에서 읽어오는경우
							      for(int i=0;i<v_p_right_userid.size();i++){ 
							         i_rightcnt++;
							%>
			                    <input type=hidden name="p_right_userid"  value="<%=v_p_right_userid.elementAt(i)%>">
			                    <input type=hidden name="p_right_name"    value="<%=v_p_right_name.elementAt(i)%>">
			                    <input type=hidden name="p_right_email"   value="<%=v_p_right_email.elementAt(i)%>">
			                    <li><input type=checkbox name=rightcbx value="<%=v_p_right_userid.elementAt(i)%>"><%=v_p_right_name.elementAt(i)%>(<%=v_p_right_userid.elementAt(i)%>)</li>
							<%    }
							   } else if ("RIGHT".equals(box.getString("p_blank"))){//오른쪽으로 이동하는경우.
							       for(int i=0;i<v_p_left_userid.size();i++){    
							          int iEqual=0;
							          String v_t_userid =(String)v_p_left_userid.elementAt(i);
							          System.out.println("v_p_leftcbx.size()  ...:"+v_p_leftcbx.size());
							          for (int j=0;j<v_p_leftcbx.size();j++){
							              String v_t_userid1 =(String)v_p_leftcbx.elementAt(j);
							              if(v_t_userid.equals(v_t_userid1))iEqual++;
							          }
							          if(iEqual>0){
							             v_p_rightcbx.addElement(v_p_left_userid.elementAt(i));
							             v_p_right_userid.addElement(v_p_left_userid.elementAt(i));
							             v_p_right_name.addElement(v_p_left_name.elementAt(i));
							             v_p_right_email.addElement(v_p_left_email.elementAt(i));
							          }
							      }
							      for(int i=0;i<v_p_right_userid.size();i++){  
							       i_rightcnt++;
							%>
			                    <input type=hidden name="p_right_userid"  value="<%=v_p_right_userid.elementAt(i)%>">
			                    <input type=hidden name="p_right_name"    value="<%=v_p_right_name.elementAt(i)%>">
			                    <input type=hidden name="p_right_email"   value="<%=v_p_right_email.elementAt(i)%>">
                                <li><input type=checkbox name=rightcbx value="<%=v_p_right_userid.elementAt(i)%>"><%=v_p_right_name.elementAt(i)%>(<%=v_p_right_userid.elementAt(i)%>)</li>
							<%     }%>
							<%   } else if ("LEFT".equals(box.getString("p_blank"))){//오른쪽으로 이동하는경우.
							
							       for(int i=0;i<v_p_right_userid.size();i++){
							           int v_eqcnt=0;
							           String vtmp1=(String)v_p_right_userid.elementAt(i);
							           for(int j=0;j<v_p_rightcbx.size();j++){
							               String vtmp2=(String)v_p_rightcbx.elementAt(j);
							               System.out.println(vtmp1+"   :  "+vtmp2);
							               if(vtmp1.equals(vtmp2))v_eqcnt++;
							           }
							           System.out.println("..........  v_eqcnt:"+v_eqcnt);
							           if(v_eqcnt==0){ i_rightcnt++;
						    %>
				                <input type=hidden name="p_right_userid"  value="<%=v_p_right_userid.elementAt(i)%>">
				                <input type=hidden name="p_right_name"    value="<%=v_p_right_name.elementAt(i)%>">
				                <input type=hidden name="p_right_email"   value="<%=v_p_right_email.elementAt(i)%>">
				                <li><input type=checkbox name=rightcbx value="<%=v_p_right_userid.elementAt(i)%>"><%=v_p_right_name.elementAt(i)%>(<%=v_p_right_userid.elementAt(i)%>)</li>
							<%    
							          }
							     }    
							}%>
                            </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
			<input type=hidden name=leftcnt value="<%=i_leftcnt%>">
			<input type=hidden name=rightcnt value="<%=i_rightcnt%>">

            <table class="study_write">
            <!--[if ie]><colgroup><col width="40px" /><col /></colgroup><![endif]-->
            <colgroup><col width="60px" /><col /></colgroup>
            <tr>
                <th class="th"><label for="subject">제목</label></th>
                <td class="td in"><input type="text" name="p_title" id="subject" class="inbox" style="width:97%;" /></td>
            </tr>
            <tr>
                <td colspan="2" class="td con"><textarea name="p_content" style="height:120px;" title="내용 입력란"><%=content %></textarea></td>
            </tr>
            </table> 
            
            <p class="board_btn"><a href="javascript:uf_cfmOK();" class="btn_gr"><span>보내기</span></a></p>

</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->
