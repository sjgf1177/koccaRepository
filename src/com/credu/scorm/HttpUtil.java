package com.credu.scorm;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import com.credu.scorm.*;


public class HttpUtil{

	/** HttpUtil.getParameter() ����� */
	public static String getParam(String name, ServletRequest request) {
		return  HttpUtil.getParameter(name, request);
	}
	
	/** HttpUtil.getParameter() ����� */
	public static String getParam(String name, ServletRequest request, String defaultValue) {
		return  HttpUtil.getParameter(name, request, defaultValue);
	}
	
	/** HttpUtil.getParameterValues() ����� */
	public static String[] getParamValues(String name, ServletRequest request) {
		return  HttpUtil.getParameterValues(name, request);
	}
	
	/** HttpUtil.getParameterValues() ����� */
	public static String[] getParamValues(String name, ServletRequest request, String[] defaultValue) {
		return  HttpUtil.getParameterValues(name, request, defaultValue);
	}

	


	/**
	 * �־��� name�� �Ķ������ ���� �޴´�.
	 */
	public static String getParameter(String name, ServletRequest request){
		return StringUtil.getEUC_KR(request.getParameter(name));
	}

	/**
	 * �־��� name�� �Ķ������ ���� �޴´�. ���� null�� ��� default ���� �����Ѵ�.
	 */
	public static String getParameter(String name, ServletRequest request, String defaultValue ){
		String ret = StringUtil.getEUC_KR(request.getParameter(name));
		return ret==null ? defaultValue : ret;
	}

	/**
	 * �־��� name�� �Ķ������ ���� �޴´�. ���� null�� ��� default ����, ������ ��� default2���� �����Ѵ�.
	 */
	public static String getParameter(String name, ServletRequest request, String defaultValue, String defaultValue2){
		String ret = StringUtil.getEUC_KR(request.getParameter(name));
		String value;
	    
		if(ret == null)
			value	= defaultValue;
		else if(ret.equals(""))
			value	= defaultValue2;
		else
			value	= ret;

		return value;
	}

	public static String getParameter(String name, ServletRequest request, int defaultValue){
		String ret = StringUtil.getEUC_KR(request.getParameter(name));
		String value;
		if(ret == null || ret.equals(""))
			value	= defaultValue+"";
		else
			value	= ret;

		return value;
	}
	/**
	 * �־��� name���� �� ��� �Ķ������ ���� �޴´�.
	 */
	public static String[] getParameterValues(String name, ServletRequest request){
		String[] ret	= request.getParameterValues(name);
		if(ret != null){
			return StringUtil.getEUC_KR(ret);
		}else{
			return null;
		}
	}
	
	
	/**
	 * �־��� name���� �� ��� �Ķ������ ���� �޴´�. ���� null�� ��� default ���� �����Ѵ�.
	 */
	public static String[] getParameterValues(String name, ServletRequest request, String[] defaultValue ){
		String ret[]	= request.getParameterValues(name);
		if(ret != null){
			return StringUtil.getEUC_KR(ret);
		}else{
			return defaultValue;
		}
	}


	/**
	 * �־��� name�� �Ķ������ ���� �޴´�. ���� null�� ��� default ���� �����Ѵ�.
	 */
	public static String getParameter(String name, MultipartRequest request, String defaultValue ){
		String ret = StringUtil.getEUC_KR(request.getParameter(name));
		return ret==null ? defaultValue : ret;
	}
	
	/**
	 * �־��� name�� �Ķ������ ���� �޴´�. ���� null�� ��� default ���� �����Ѵ�.
	 */
	public static String getParameter2(String name, MultipartRequest request, String defaultValue ){
		String ret = request.getParameter(name);
		return ret==null ? defaultValue : ret;
	}
	
	/**
	 * �־��� name�� �Ķ������ ���� �޴´�. ���� null�� ��� default ���� �����Ѵ�.
	 */
	public static String[] getParameterValues2(String name, MultipartRequest request){
		String[] ret = request.getParameterValues(name);
		return ret;
	}

	/**
	 * �־��� name�� �Ķ������ ���� �޴´�. ���� null�� ��� default ����, ������ ��� default2���� �����Ѵ�.
	 */
	public static String getParameter(String name, MultipartRequest request, String defaultValue, String defaultValue2){
		String ret = StringUtil.getEUC_KR(request.getParameter(name));
		String value;
		if(ret == null)
			value	= defaultValue;
		else if(ret.equals(""))
			value	= defaultValue2;
		else
			value	= ret;

		return value;
	}

	/**
	 * �־��� name�� �Ķ������ ���� �޴´�. ���� null�� ��� default ����, ������ ��� default2���� �����Ѵ�.
	 */
	public static String getParameter2(String name, MultipartRequest request, String defaultValue, String defaultValue2){
		String ret = request.getParameter(name);
		String value;
		if(ret == null)
			value	= defaultValue;
		else if(ret.equals(""))
			value	= defaultValue2;
		else
			value	= ret;

		return value;
	}

	public static String getParameter(String name, MultipartRequest request, int defaultValue){
		String ret = StringUtil.getEUC_KR(request.getParameter(name));
		String value;
		if(ret == null || ret.equals(""))
			value	= defaultValue+"";
		else
			value	= ret;

		return value;
	}


	/**
	 * null ���ڸ� ���鹮�ڿ��� ��ȯ   null => ""
	 */
   public static  String nullToBlank ( String str)
   {
      return ( str == null ) ? "" : str.trim();
   }
	/**
	 * null ���ڸ� "0"���ڿ��� ��ȯ   null => "0"
	 */
   public static  String nullToZero ( String str)
   {
      return ( str == null ) ? "0" : str.trim();
   }
   public static  int nullToZeroInt ( String str)
   {
      return ( str == null ) ? 0 : Integer.parseInt(str.trim());
   }
	/**
	 * �ι��ڸ� ���� ���̸� checked�� ��ȯ
	 */
   public static  String trueToChecked(String ck1,String ck2)
   {
	 	String Re_turn = "";
		if(ck1.equals(ck2)){
			Re_turn = "checked";
		}
		return Re_turn;
   }
	/**
	 * �ι��ڸ� ���� ���̸� selected�� ��ȯ
	 */
   public static  String trueToSelected(String ck1,String ck2)
   {
	 	String Re_turn = "";
		if(ck1.equals(ck2)){
			Re_turn = "selected";
		}
		return Re_turn;
   }

	/**
	 * html tag => ���ڷ� ��ȯ
	 */
   public static  String convertHtml( String content, int html) {
      String temp = content;
      int pos;
      if( html==0 ) {
         pos = -1;
         while((pos = temp.indexOf("<", pos + 1)) != -1) {
            String left = temp.substring(0, pos);
            String right = temp.substring(pos + 1, temp.length());
            temp = left + "&lt;" + right;
         }
      }

      pos = 0;
      while((pos = temp.indexOf("\n")) != -1) {
         String left = temp.substring(0, pos);
         String right = temp.substring(pos + 1, temp.length());
         temp = left + "<br>" + right;
      }

      pos = 0;
      while((pos = temp.indexOf("  ", pos)) != -1) {
         String left = temp.substring(0, pos);
         String right = temp.substring(pos + 1, temp.length());
         temp = left + "&nbsp;&nbsp;" + right;
         pos += 4;
      }

      return temp;
   }
	/**
	 * ��Ʈ���� �����ڱ������� ���ͷ� ��ȯ ex) getGuToVector("��,��,��,��,��",",") => [��,��,��,��,��]
	 */

   public static Vector getGuToVector( String param1, String gubun )
   {
		Vector vr_return = new Vector();
		String contents =  new String(param1);
		int pos1 = 0;
		int pos2 = 0;
		int pos3 = 0;
		pos1 = contents.length();
			while(pos1 >= gubun.length()){
				pos2 = contents.indexOf(gubun);
				if (pos2 != -1){
					pos3 = pos2+gubun.length();
					vr_return.add(contents.substring(0,pos2));
					contents = contents.substring(pos3,pos1);
					pos1 = contents.length();
				}else{
					vr_return.add(contents);
					pos1 = 0;
				}
			}
		return 	vr_return;
	}
} 