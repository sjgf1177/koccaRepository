<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    String  v_process   = box.getString("p_process");
    String  v_userid     = box.getString("p_userid");

    String v_subj         = "";
    String v_subjnm       = "";
    String v_year         = "";
    String v_subjseq      = "";
    String v_name         = "";
    String v_studentno    = "";
    String v_state        = "";
    String v_statenm      = "";
    
    String v_comp         = "";
    String v_compnm       = "";
    String v_telno        = "";
    String v_faxno        = "";
    String v_email        = "";
    String v_employdate   = "";
    String v_employgubun  = "";
    String v_employgubunnm= "";
    
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
        v_subj            = dbox.getString("d_subj");
        v_subjnm          = dbox.getString("d_subjnm");
        v_year            = dbox.getString("d_year");
        v_subjseq         = dbox.getString("d_subjseq");
        v_name            = dbox.getString("d_name");
        v_studentno       = dbox.getString("d_studentno");
        v_state           = dbox.getString("d_stustatus");
        
        v_comp         = dbox.getString("d_comp");       
        v_compnm       = dbox.getString("d_compnm");     
        v_telno        = dbox.getString("d_telno");      
        v_faxno        = dbox.getString("d_faxno");      
        v_email        = dbox.getString("d_email");      
        v_employdate   = dbox.getString("d_employdate");
        v_employgubun  = dbox.getString("d_employgubun");
        v_employgubunnm= dbox.getString("d_employgubunnm");
        
    }
%>

<input type="hidden" name="p_process"/>
<input type="hidden" name="p_subj"      id="p_subj"         value="<%=v_subj %>">
<input type="hidden" name="p_changedate"id="p_changedate"   value="">
<input type="hidden" name="p_subjnm"    value="">
<input type="hidden" name="p_year"      id="p_year"         value="<%=v_year %>">
<input type="hidden" name="p_subjseq"   id="p_subjseq"      value="<%=v_subjseq %>">
<input type="hidden" name="p_userid"   id="p_userid"      value="<%=v_userid %>">

              <table cellspacing="1" cellpadding="5" class="open_table_out">
              <tr>
                <td colspan="2" class="table_top_line"></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >과정명</td>
                <td class="table_02_2"  width="80%" colspan=3><%=v_subjnm %></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >성명</td>
                <td class="table_02_2"  width="30%" ><%=v_name %></td>
                <td class="table_title" width="20%" >학번</td>
                <td class="table_02_2"  width="30%" ><%=v_studentno %></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >차수</td>
                <td class="table_02_2"  width="30%" ><%=Integer.parseInt(v_subjseq) %>기</td>
                <td class="table_title" width="20%" >학적상태</td>
                <td class="table_02_2"  width="30%" ><%=v_statenm%></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >취업일자</td>
                <td class="table_02_2"  width="80%" colspan=3>
                  <input name="p_fdate" class="input" type="text" size="10" maxlength="10" OnClick="MiniCal(this);" readonly value='<%=FormatDate.getFormatDate(v_employdate, "yyyy/MM/dd") %>' >
                </td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >취업구분</td>
                <td class="table_02_2"  width="80%" colspan=3>
                <select name="p_employgubun">
                <%
                List codeList = CodeAdminBean.selectListCode("0099");
                
                if(codeList != null && codeList.size() > 0 ){
                	String sCode   = "";
                	String sCodeNm = "";
                	for(int i = 0 ; i < codeList.size() ; i++){
                		DataBox codeBox = (DataBox)codeList.get(i);
                		
                		sCode   = codeBox.getString("d_code");
                		sCodeNm = codeBox.getString("d_codenm");
                		
                %>
                    <option value="<%=sCode %>" <%=sCode.equals(v_employgubun) ? "selected" : ""%>><%=sCodeNm %></option>
                <%
                		
                	}
                }
                %>
                </select>
                </td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >전화</td>
                <td class="table_02_2"  width="30%" ><%=v_telno %></td>
                <td class="table_title" width="20%" >팩스</td>
                <td class="table_02_2"  width="30%" ><%=v_faxno%></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >담당자</td>
                <td class="table_02_2"  width="30%" ><%=v_compnm %></td>
                <td class="table_title" width="20%" >이메일</td>
                <td class="table_02_2"  width="30%" ><%=v_email%></td>
              </tr>
            </table>
