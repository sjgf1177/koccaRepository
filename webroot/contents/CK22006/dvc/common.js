var txtDeco =new Array()
txtDeco[1] = new Array()
txtDeco[2] = new Array()




txtDeco[1][0] =" <tr height=30 style='height:16.5pt'>  <td height=20 bgcolor ='#E2E2E2' class=xl65 style='font-size:12px;color:black;font-weight:400;  text-decoration:none;text-underline-style:none;text-line-through:none;  font-family:'돋움';border-top:none;border-right:.5pt solid white;border-bottom:  .5pt solid white;border-left:none;background:#E2E2E2;mso-pattern:#E2E2E2 none'  align = 'center'>"
txtDeco[1][1] ="</td><td bgcolor ='#E2E2E2' class=xl66 style='font-size:12px;color:black;font-weight:400;  text-decoration:none;text-underline-style:none;text-line-through:none;  font-family:'돋움';border-top:none;border-right:none;border-bottom:.5pt solid white;  border-left:none;background:#E2E2E2;mso-pattern:#E2E2E2 none;padding-left:20px' >"
txtDeco[1][2] ="</td> </tr>"

txtDeco[2][0] ="<tr height=30 style='height:16.5pt'><td height=20 class=xl65 bgcolor ='#FFFFFF' style='font-size:12px;color:black;font-weight:400;  text-decoration:none;text-underline-style:none;text-line-through:none;  font-family:'돋움';border-top:none;border-right:.5pt solid white;border-bottom:  .5pt solid white;border-left:none;background:#FFFFFF;mso-pattern:#FFFFFF none'  align = 'center'>"
txtDeco[2][1] ="</td> <td bgcolor ='#FFFFFF' class=xl66 style='font-size:12px;color:black;font-weight:400;  text-decoration:none;text-underline-style:none;text-line-through:none;  font-family:'돋움';border-top:none;border-right:none;border-bottom:.5pt solid white;  border-left:none;background:#FFFFFF;mso-pattern:#FFFFFF none;padding-left:20px'>"
txtDeco[2][2] ='</td></tr>'
function titleWrite(){
	var tmpstr = " <table border=0 cellpadding=1 cellspacing=1 width=580 style='border-collapse: collapse;table-layout:fixed;width:398pt'>   <tr eight=25 style='height:16.5pt'>  <td bgcolor ='#7F7F7F' class=xl65 width=100 style='width:54pt;font-size:12px;color:white;  ont-weight:700;text-decoration:none;text-underline-style:none;text-line-through:  none;font-family:'돋움';border-top:none;border-right:.5pt solid white;  border-bottom:1.5pt solid white;border-left:none;background:#7F7F7F;  mso-pattern:#7F7F7F none' align = 'center'><strong>차시</strong></td>  <td bgcolor ='#7F7F7F' class=xl65 width=485 style='width:289pt;font-size:12px;color:white;  font-weight:700;text-decoration:none;text-underline-style:none;text-line-through:  none;font-family:'돋움';border-top:none;border-right:none;border-bottom:  1.5pt solid white;border-left:none;background:#7F7F7F;mso-pattern:#7F7F7F none'  align = 'center'>차 시 명</td> </tr>"

	for(i=1;i<chapNameSet.length;i++){
		sp =i%2==0?1: 2		
		tmpstr+=txtDeco[sp][0]
		tmpstr+=i+" 차시"

		tmpstr+=txtDeco[sp][1]
		
		tmpstr+="<a href='javascript:openWin("+i+")' >"
		tmpstr+=chapNameSet[i]
		tmpstr+="</a>"
		tmpstr+=txtDeco[sp][2]

	}
	tmpstr +=" </table>"
tableXp.innerHTML=(tmpstr)
 
}
function openWindow(vURL){
	window.open(vURL, "","scrollbars=no,resizable=0,status=0, width=1280px, height=754px ");
}

function security(){
  w = window.open("device/security/trust.htm","","width=400,height=300,left=0,top=0");
  w.focus();
 }
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
function openWin(a){
   
	url = itostr(a)+"/010101.html"
//	url = "35899/01/01.html";
	authwin = window.open(url, "study", "width=1290,height=764,menubar=no,toolbar=no,location=no,status=no,scrollbars=no,resizable=yes,top=0,left=0");
	authwin.focus()
}
function itostr(num){ // 숫자열을 문자열로 
	var tmpstr =  num>9?num:"0"+num
	return tmpstr
}
function view(){
	settingWin="setting/open.html";
	authwin = window.open(settingWin, "setting", "menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,top=0,left=0,width=900,height=600");
}
function itostr(inum){
	return inum<10?"0"+inum:inum;
}

