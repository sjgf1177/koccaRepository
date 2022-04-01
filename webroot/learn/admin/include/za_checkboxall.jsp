<script language='javascript'>
function chkeckall(){
    if(document.form1.p_chkeckall.checked){
      whenAllSelect();
    }
    else{
      whenAllSelectCancel();
    }
}
</script>
<input type="checkbox" name="p_chkeckall" onClick="javascript:chkeckall()">
