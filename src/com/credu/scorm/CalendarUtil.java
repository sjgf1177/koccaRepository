package com.credu.scorm;

import java.util.*;

public class CalendarUtil{

	//'==============|| ��¥ ���ÿ� ���̾� �� �⺻ ���� (�ٸ� Form ��ü �ܺο� ���ԵǾ�������) ||==============
	public static String getCalendarForm(){
		String str_ReturnCAL	= "";

		str_ReturnCAL	= "	<link rel='stylesheet' type='text/css' href='/common/calendar/calendar.css'>\n"
					+ "	<script language='JavaScript1.2' src='/common/calendar/calendar.js'></script>\n"
					+ "	<script language='JavaScript1.2' src='/common/calendar/calmove.js'></script>\n"
					+ "	\n"
					+ "	<!-- �޷� ���� -->\n"
					+ "	<div id='CalDiv' class='caldivstyle'>\n"
					+ "	<table cellpadding=0 cellspacing=0 width=180 border=2>\n"
					+ "	<tr><td class=bgtitle><a name='calendar_top'></a>\n"
					+ "		<table cellpadding=1 cellspacing=1 width='100%'>\n"
					+ "		<tr><td class=bgtitle style='cursor:hand' onMousedown='initializedragie()'>\n"
					+ "			<ilayer width='100%'>\n"
					+ "				<layer width='100%' onMouseover='dragswitch=1;drag_dropns(CalDiv)' onMouseout='dragswitch=0'>\n"
					+ "					<table width='100%' bgcolor=000000  cellpadding=0 cellspacing=0>\n"
					+ "					<tr>\n"
					+ "						<td class=bgtitle id=ititle><img src='/common/calendar/img/calendar.gif' border=0></td>\n"
					+ "						<td class=bgtitle align=right><a class='callink' href='javascript:void(HideWindow(CalDiv))' onfocus=this.blur()><img src='/common/calendar/img/close_o.gif' border=0></a></td>\n"
					+ "					</tr>\n"
					+ "					</table>\n"
					+ "				</layer>\n"
					+ "			</ilayer>\n"
					+ "		</td></tr>\n"
					+ "		<tr><td width='100%' class=bgtext>\n"
					+ "			<table width='100%' bgcolor=808080 cellpadding=1 cellspacing=0>\n"
					+ "			<form name=CalForm method=post>\n"
					+ "			<tr>\n"
					+ "				<td class=bgcaltitle><font color=#000000 style='font-size:4pt'>&nbsp;</font><input type=text name=year size=4 maxlength=4 class=itext onChange='selectDate()'>\n"
					+ "					<SELECT NAME=month onChange='selectDate()' class=itext>\n"
					+ "						<OPTION> 1��</OPTION>\n"
					+ "						<OPTION> 2��</OPTION>\n"
					+ "						<OPTION> 3��</OPTION>\n"
					+ "						<OPTION> 4��</OPTION>\n"
					+ "						<OPTION> 5��</OPTION>\n"
					+ "						<OPTION> 6��</OPTION>\n"
					+ "						<OPTION> 7��</OPTION>\n"
					+ "						<OPTION> 8��</OPTION>\n"
					+ "						<OPTION> 9��</OPTION>\n"
					+ "						<OPTION> 10��</OPTION>\n"
					+ "						<OPTION> 11��</OPTION>\n"
					+ "						<OPTION> 12��</OPTION>\n"
					+ "					</SELECT>\n"
					+ "				</td>\n"
					+ "				<td class=bgcaltitle><a class='callink' href='#alexit' onClick='setPreviousYear()' title='Previous(Year)'><img src='/common/calendar/img/pre.gif' border=0><img src='/common/calendar/img/pre.gif' border=0></a>&nbsp; <a class='callink' href='#alexit' onClick='setPreviousMonth()' title='Previous(Month)'><img src='/common/calendar/img/pre.gif' border=0></a>&nbsp; <a class='callink' href='#alexit' onClick='setToday()' title='Today'>Today</a>&nbsp; <a class='callink' href='#alexit' onClick='setNextMonth()' title='Next(Month)'><img src='/common/calendar/img/next.gif' border=0></a>&nbsp; <a class='callink' href='#alexit' onClick='setNextYear()' title='Next(Year)'><img src='/common/calendar/img/next.gif' border=0><img src='/common/calendar/img/next.gif' border=0></a></td>\n"
					+ "				<td class=bgcaltitle align='right'><a class='callink' href='#alexit' onClick='setClear()' title='Clear'>[Clear]</a>&nbsp;</td>"
					+ "			</tr>\n"
					+ "			</form>\n"
					+ "			</table>\n"
					+ "		</td></tr>\n"
					+ "		<tr><td  class=bgtext>\n"
					+ "			<table cellpadding=2 cellspacing=0 width='100%' bgcolor=#ffffff id='CalTable'>\n"
					+ "			<form name=CalView>\n"
					+ "			<tr height=24>\n"
					+ "				<td align=center class=bgtext1>��</td>\n"
					+ "				<td align=center class=bgtext1>��</td>\n"
					+ "				<td align=center class=bgtext1>ȭ</td>\n"
					+ "				<td align=center class=bgtext1>��</td>\n"
					+ "				<td align=center class=bgtext1>��</td>\n"
					+ "				<td align=center class=bgtext1>��</td>\n"
					+ "				<td align=center class=bgtext1>��</td>\n"
					+ "			</tr>\n"
					+ "			<script language='javascript'>\n"
					+ "				CalDay_Print();	\n"
					+ "				setDate('');		\n"
					+ "			</script>\n"
					+ "			</form>\n"
					+ "			</table>\n"
					+ "		</td></tr>\n"
					+ "		</table>\n"
					+ "	</td></tr>\n"
					+ "	</table>\n"
					+ "	</div>\n"
					+ "	<!-- �޷� �� -->";

		return str_ReturnCAL;
	}


	//==============|| ��¥ �Է��� Ŭ���� �̺�Ʈ �ڵ� ���� (1) ||==============
	public static String setEventOnclick(String str_FormName, String str_InputName, String str_BlurFlag){
		return setEventOnclick(str_FormName, str_InputName, str_BlurFlag, "PARENT", 0, 0);
	/*	String str_ReturnEvent	= "";
		if(str_BlurFlag.equals("1")){
			str_ReturnEvent	= " onfocus='this.blur();'";
		}

		str_ReturnEvent	+= " onclick=\"ShowCal(document."+ str_FormName +"."+ str_InputName +", '0');\"";

		return str_ReturnEvent;*/
	}
	public static String setEventOnclick(String str_FormName, String str_InputName, String str_BlurFlag, String str_WinType, int int_Xadd, int int_Yadd){
		String str_ReturnEvent	= "";
		if(str_BlurFlag.equals("1")){
			str_ReturnEvent	= " onfocus='this.blur();'";
		}
		if(str_WinType.equals("")) str_WinType = "PARENT";

		str_ReturnEvent	+= " onclick=\"ShowCal(document."+ str_FormName +"."+ str_InputName +", '0', '"+ str_WinType +"', "+ int_Xadd +", "+ int_Yadd +");\"";

		return str_ReturnEvent;
	}

	//==============|| ��¥ �Է��� Ŭ���� �̺�Ʈ �ڵ� ���� (2) -- �ֺ� �޺��ڽ� ���� �Ӽ� ���� ||==============
	public static String setEventOnclickHide(String str_FormName, String str_InputName, String str_BlurFlag){
		String str_ReturnEvent	= "";
		if(str_BlurFlag.equals("1")){
			str_ReturnEvent	= " onfocus='this.blur();'";
		}

		str_ReturnEvent	+= " onclick=\"ShowCal(document."+ str_FormName +"."+ str_InputName +", '1');\"";

		return str_ReturnEvent;
	}
}