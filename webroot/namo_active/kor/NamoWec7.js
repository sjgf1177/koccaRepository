document.onreadystatechange=function()
{
 if (document.readyState == 'complete')
 {
      if (document.all['divShowInstall'])
        document.all['divShowInstall'].style.visibility = 'hidden';
  }
}

var strScripts ="<OBJECT ID='Wec' CLASSID='CLSID:43088818-69F9-4852-8ED5-9C4FA514D995' WIDTH='741' HEIGHT='525' CodeBase='../NamoWec.cab#Version=7,0,0,97'>";
strScripts +="<PARAM NAME='UserLang' VALUE=kor>";
strScripts +="<PARAM NAME='InitFileURL' VALUE='./As7Init.xml'>";
strScripts +="<PARAM NAME='InitFileVer' VALUE='1.0'>";
strScripts +="<PARAM NAME='InitFileWaitTime' VALUE='3000'>";
strScripts +="<PARAM NAME='InstallSourceURL' VALUE='http://help.namo.co.kr/activesquare/techlist/help/AS7_update'>";
strScripts +="</OBJECT>";

document.write(strScripts);