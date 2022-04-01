<%@ page contentType = "text/html;charset=MS949" %>
        <!----------------- 마스터폼 정보수정 시작 ----------------->
<script language="javascript">
    function changeMode(process){
        document.f2.p_process.value=process;
        document.f2.submit();
    }
    function whenList(){
        document.f2.p_process.value='listPage';
        document.f2.p_action.value='go';
        document.f2.submit();
    }
</script>
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="16%" height="25" class="table_title"><b>과정코드/명</b></td>
            <td bgcolor="#F7F7F7" width="81%"><b>[<%=p_subj%>] <%=v_subjnm%></b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>콘텐츠 위치</b></td>
            <td bgcolor="#F7F7F7"><%=v_dir%></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>콘텐츠 타입</b></td>
            <td bgcolor="#F7F7F7"><%=v_contenttypetxt%></td>
          </tr>
        </table>

<%
//    String  [] v_img    = {"black","black","black","black"};            //Tag Image file Name Array
//    String  [] v_txt    = {"MasterForm","Module","Lesson","Branch"};    //Tag Text Array
//    String  [] v_proc   = {"","Module","Lesson","Branch"};  //Tag p_process Array
    String  [] v_img    = {"black","black","black","black"};           //Tag Image file Name Array
    String  [] v_txt    = {"MasterForm","Module","Lesson","날짜지정"};  //Tag Text Array
    String  [] v_proc   = {"","Module","Lesson","Date"};               //Tag p_process Array

    if(p_process.equals("updatePage"))              v_img[0] = "blue";
    else if(p_process.equals("updateModulePage"))   v_img[1] = "blue";
    else if(p_process.equals("updateLessonPage"))   v_img[2] = "blue";
    else if(p_process.equals("updateDatePage"))     v_img[3] = "blue";
%>


        <br>
        <br>

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
<%
    int loop_cnt = 4;

    for(int i=0;i<loop_cnt;i++){
        %>
            <td width="85" height="23" align="center" valign="middle">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td background="/images/admin/contents/contents_<%=v_img[i]%>_img1.gif" rowspan="3" width="5" height="23"></td>
                  <td background="/images/admin/contents/contents_<%=v_img[i]%>_img3.gif" height="5"></td>
                  <td rowspan="3" background="/images/admin/contents/contents_<%=v_img[i]%>_img2.gif" width="5" height="23"></td>
                </tr>
                <tr>
                  <td background="/images/admin/contents/contents_<%=v_img[i]%>_img5.gif" align="center" height="16" valign="middle">
                    <b><font color="#FFFFFF"><a href="javascript:changeMode('update<%=v_proc[i]%>Page')"><%=v_txt[i]%></a></font></b></td>
                </tr>
                <tr>
                  <td background="/images/admin/contents/contents_<%=v_img[i]%>_img4.gif" height="2"></td>
                </tr>
              </table>
            </td>
            <td width="2"></td>
        <%
    }

%>
            <td>&nbsp;</td>
          </tr>
        </table>

