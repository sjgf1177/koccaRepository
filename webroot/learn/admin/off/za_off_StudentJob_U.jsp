<SCRIPT LANGUAGE="JavaScript">              

function update() {
    
    var changedate = make_date(document.form1.p_date.value);

    if (blankCheck(document.form1.p_changereason.value)) {
        alert("변경사유를 입력하세요");
        document.form1.p_changereason.focus();
        return;
    }

    if (blankCheck(document.form1.p_date.value)) {
        alert("변경일자를 입력하세요");
        document.form1.p_date.focus();
        return;
    }

    if(document.form1.p_stustate.value == "ALL" || document.form1.p_stustate.value == ""){
        alert("학적을 선택하세요");
        document.form1.p_stustate.focus();
        return;
    }

    document.form1.p_changedate.value  = changedate;
    document.form1.action = "/servlet/controller.off.OffSchRegAdminServlet";
    document.form1.p_process.value = "update";
    document.form1.submit();
}

//회사 검색
function searchGrpComp() {
  var url = "/servlet/controller.library.SearchServlet?p_process=grpcomp&p_gubun=companynm&p_key1=&p_key2=";
  open_window("",url,"0","0","100","100");
}
//회사 검색 후 처리
function receiveGrpComp(comp , compnm, comptype, groupnm, companynm, tmp1, tmp2, tmp3){
  var compObj = document.form1.p_comp;
  var divTelno
  var divFaxno
  var 
  
  
  for (i = 0; i < Length; i++) {
    if (document.form1.p_comp.options[i].value == comp) {
        return;
    }
  }
}

</SCRIPT>                  
<div id="view_div" style="display:none">
<table width="97%" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr> 
    <td align="left"> 
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr> 
          <td>
          <div id="ajax_div"></div>
            
            <table cellspacing="0" cellpadding="0" class="open_table1">
              <tr> 
                <td align="right" style="padding-top=10">
                <a href="javascript:update()"><img src="/images/admin/button/btn_add.gif" border="0"></a>
                <a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a>
                </td>
              </tr>
            </table>
            <!----------------- 닫기 버튼 끝 ----------------->
              <br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' 
ondragstart='return false' onselectstart='return false' 
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
