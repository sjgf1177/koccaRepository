<script type="text/javascript">
function initMoving(target, position, topLimit, btmLimit) {
if (!target)
return false;

var obj = target;
obj.initTop = position;
obj.topLimit = topLimit;
obj.bottomLimit = document.documentElement.scrollHeight - btmLimit;

obj.style.position = "absolute";
obj.top = obj.initTop;
obj.left = obj.initLeft;

if (typeof(window.pageYOffset) == "number") {
obj.getTop = function() {
return window.pageYOffset;
}
} else if (typeof(document.documentElement.scrollTop) == "number") {
obj.getTop = function() {
return document.documentElement.scrollTop;
}
} else {
obj.getTop = function() {
return 0;
}
}

if (self.innerHeight) {
obj.getHeight = function() {
return self.innerHeight;
}
} else if(document.documentElement.clientHeight) {
obj.getHeight = function() {
return document.documentElement.clientHeight;
}
} else {
obj.getHeight = function() {
return 500;
}
}

obj.move = setInterval(function() {
if (obj.initTop > 0) {
pos = obj.getTop() + obj.initTop;
} else {
pos = obj.getTop() + obj.getHeight() + obj.initTop;
//pos = obj.getTop() + obj.getHeight() / 2 - 15;
}

if (pos > obj.bottomLimit)
pos = obj.bottomLimit;
if (pos < obj.topLimit)
pos = obj.topLimit;

interval = obj.top - pos;
obj.top = obj.top - interval / 3;
obj.style.top = obj.top + "px";
}, 30)
}
</script>

<div id="gotop" style="margin-left: 26px;">
<table border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="10">&nbsp;</td>
    </tr>
    <tr>
      <td><img src="/images/portal/homepage_renewal/common/title_quickmu.png" alt="QUICK MEMU" /></td>
    </tr>
    <tr>
      <td><a href="javascript:menuMainForward('3','/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image781','','/images/portal/homepage_renewal/common/btn_quickmu_on1.gif',1)"><img src="/images/portal/homepage_renewal/common/btn_quickmu_off1.gif" alt="나의강의실" name="Image781" id="Image781" /></a></td>
    </tr>
    <tr>
      <td><a href="javascript:menuForward('3', '02');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image21','','/images/portal/homepage_renewal/common/btn_quickmu_on2.gif',1)"><img src="/images/portal/homepage_renewal/common/btn_quickmu_off2.gif" alt="수료증출력" name="Image21" id="Image21" /></a></td>
    </tr>
    <tr>
      <td height="80" valign="bottom" background="/images/portal/homepage_renewal/common/btn_quickmu_on3.gif"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="26" align="center"><a href="javascript:menuMainForward('1','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList')"><img src="/images/portal/homepage_renewal/common/qu_on.gif" width="29" height="23" id="Image2" onmouseover="MM_swapImage('Image2','','/images/portal/homepage_renewal/common/qu_on2.gif',1)" onmouseout="MM_swapImgRestore()" /></a></td>
          <td align="center"><a href="javascript:menuMainForward('2','/servlet/controller.off.ProposeOffServlet?p_process=SubjectList')"><img src="/images/portal/homepage_renewal/common/qu_off2.gif" width="30" height="23" id="Image3" onmouseover="MM_swapImage('Image3','','/images/portal/homepage_renewal/common/qu_off.gif',1)" onmouseout="MM_swapImgRestore()" /></a></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td><a href="javascript:menuMainForward2('3','19','/servlet/controller.study.MyClassBillServlet?p_process=MyOffClassBillListPage');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image4','','/images/portal/homepage_renewal/common/btn_quickmu_on4.gif',1)"><img src="/images/portal/homepage_renewal/common/btn_quickmu_off4.gif" alt="오프라인결제" name="Image4" id="Image4" /></a></td>
    </tr>
  </table>
 </div>

<script type="text/javascript">initMoving(document.getElementById("gotop"), 200, 1, 0);</script>


 
 
 
   
