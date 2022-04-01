<%

    String group   = "";    // 그룹코드
    String company = "";    // 회사코드
    String gpm     = "";    // 사업부코드
    String dept    = "";    // 부서코드
    String jikup   = "";    // 직위코드
    String jikun   = "";    // 직군코드

    group   = "01";
    company = box.getString("company");
    gpm     = box.getString("gpm");
    dept    = box.getString("dept");
    jikup   = box.getString("jikup");
    jikun   = box.getString("jikun");

%>

                    <table class="form_table" width="99%" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td>
                          <input type='hidden' name = 'group' value='<%=group%>'>
                          회사
                          <%=CompAdminBean.getCompanySelect (group, company)%>
                          &nbsp;&nbsp;사업부
                          <%=CompAdminBean.getGpmSelect (group, company, gpm)%>
                          &nbsp;&nbsp;부서
                          <%=CompAdminBean.getDeptSelect (group, company, gpm, dept)%>
                          &nbsp;&nbsp;직위
                          <%=CompAdminBean.getJikupSelect (jikup) %>
                          &nbsp;&nbsp;직군
                          <%=CompAdminBean.getJikunSelect (jikun) %>
                        </td>
                      </tr>
                    </table>

<SCRIPT LANGUAGE="JavaScript">
<!--

    function companyChange() {

         document.<%=v_form%>.action = "<%=v_action%>";
         document.<%=v_form%>.gpm.value = '';
         document.<%=v_form%>.p_process.value = "selectPre";
         document.<%=v_form%>.submit();
    }

    function gpmChange() {

         document.<%=v_form%>.action = "<%=v_action%>";
         document.<%=v_form%>.p_process.value = "selectPre";
         document.<%=v_form%>.submit();
    }


//-->
</SCRIPT>

