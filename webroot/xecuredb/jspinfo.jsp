<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.reflect.Method" %>
<%@ page import = "java.util.jar.*" %>
<%//@ page import = "xecure.servlet.XecureConfig" %>
<%//@ page import = "xei.conf.XecureConfig" %>
<%//@ page import = "xecure.exp.XecureExpConfig" %>
<%//@ page import = "com.sf.XecurePDF.XecPDFConfig" %>
<%//@ page import = "SafeSignOn.SsoConfig" %>
<%//@ page import = "eamldap.EAMConf" %>
<%//@ page import = "sso30ldap.SsoConf" %>
<%//@ page import = "com.test.test" %>

<%!
	boolean boldName(String key)
	{
		boolean isBold = false ;
		if(key.equals("java.vm.version"))					isBold = true ;
		else if(key.equals("java.specification.version"))	isBold = true ;
		
		else if(key.equals("sun.boot.library.path"))		isBold = true ;
		else if(key.equals("java.library.path"))			isBold = true ;
		
		else if(key.equals("sun.boot.class.path"))			isBold = true ;
		else if(key.equals("java.class.path"))				isBold = true ;
		else if(key.equals("java.endorsed.dirs"))			isBold = true ;
		else if(key.equals("java.ext.dirs"))				isBold = true ;
		
		else if(key.equals("java.home"))					isBold = true ;
		else if(key.equals("sun.arch.data.model"))			isBold = true ;
		
		else if(key.equals("file.separator"))				isBold = true ;
		else if(key.equals("path.separator"))				isBold = true ;
		else if(key.equals("os.name"))						isBold = true ;
		else if(key.equals("os.arch"))						isBold = true ;
		else if(key.equals("os.version"))					isBold = true ;
		
		else if(key.equals("user.home"))					isBold = true ;
		else if(key.equals("user.dir"))						isBold = true ;
		else if(key.equals("user.language"))				isBold = true ;
		
		else if(key.equals("common.loader"))				isBold = true ;
		else if(key.equals("shared.loader"))				isBold = true ;
		
		else if(key.startsWith("weblogic."))				isBold = true ;
		else if(key.startsWith("tomcat."))					isBold = true ;
		else if(key.startsWith("catalina."))				isBold = true ;
		else if(key.startsWith("jeus."))					isBold = true ;
		else if(key.startsWith("jrun."))					isBold = true ;

		return isBold ;
	}
	
	boolean redName(String key)
	{
		boolean isRed = false ;

		if(key.startsWith("weblogic."))				isRed = true ;
		else if(key.startsWith("tomcat."))			isRed = true ;
		else if(key.startsWith("catalina."))		isRed = true ;
		else if(key.startsWith("jeus."))			isRed = true ;
		else if(key.startsWith("jrun."))			isRed = true ;
		
		return isRed ;
	}
	
	String printString(java.util.Set set)
	{
		if(set == null)	return "" ;
		StringBuffer sb = new StringBuffer() ;
		String[] temp = new String[set.size()] ;
		temp =(String[])set.toArray(temp) ;
		for(int i = 0;i < temp.length; i++)
		{
			sb.append(temp[i]) ;
			sb.append("<br>") ;
		}
		
		return sb.toString() ;
	}
	
	String printString(java.util.Properties prop)
	{
		String ret = "" ;
		Enumeration e = prop.propertyNames() ;
		
		if(e.hasMoreElements() == false)
		{
			ret += "<table valign=middle border='0' width=100% bgcolor=#A2C2FC cellspacing='1' cellpadding='0'>" ;
			ret += "<tr><td>없습니다.</td></tr>" ;
			ret += "</table>" ;
			
			return ret ;
		}
		else
		{
			
			ret += "<table valign=middle border='0' width=100% bgcolor=#A2C2FC cellspacing='1' cellpadding='0'>" ;
			for(;e.hasMoreElements();)
			{
				String key = (String)e.nextElement() ;
				boolean isBold = boldName(key) ;
				boolean isRed = redName(key) ;
				ret += "<tr bgcolor=#E4EDFE valign=middle><td width='20%'>"+(isRed ? "<font color=#FF0000>" : "") +(isBold ? "<b>": "") + key + (isBold ? "</b>": "")+(isRed ? "</font>" : "")+"</td><td>"+ prop.getProperty(key)+"</td></tr>";
    		}
    		ret += "</table>" ;
    		
    		return ret ;
		}
	}
	
	String getSystemPath(String dir, String path)
	{
		char file_separator = System.getProperty("file.separator").charAt(0) ;
		String current_directory = System.getProperty("user.dir") ;
		
		String ret = "" ;
		
		// 맨끝 file_separator 제거
		ret = ret + ((dir.endsWith(""+ file_separator)) ? dir.substring(0,dir.length()-1) : dir) ;
		
		// 맨앞 ..를 절대경로바꾸기
		if(ret.startsWith(".."))
		{
			if((ret.length() > 3) && (ret.charAt(2) == '/' || ret.charAt(2) == file_separator))
				ret = current_directory.substring(0, current_directory.lastIndexOf(file_separator)) + ret.substring(2) ;
			else
				ret = current_directory.substring(0, current_directory.lastIndexOf(file_separator)) ;
		}

		if(path == null)		return ret.replace('/', file_separator) ;
		if(path.length() == 0)	return ret.replace('/', file_separator) ;
		
		if(path.charAt(0) == '/' || path.charAt(0) == file_separator)
			ret = ret + path ;
		else
			ret = ret + file_separator + path ;
			
		return ret.replace('/', file_separator) ;
	}
	
	java.util.Properties findClass(java.io.File file, String dir, String path, java.util.Properties prop)
	{
		
		File[] temp = file.listFiles() ;
		for(int i = 0; i < temp.length; i++)
		{
			if(temp[i].isDirectory())
				prop = findClass(temp[i], dir, path + "/" + temp[i].getName() , prop) ;
			
			
			if(temp[i].isFile() && temp[i].getName().endsWith(".class"))
			{
				String classname = temp[i].getName() ;
					
				if(prop.containsKey(classname))
					prop.setProperty(classname, prop.getProperty(classname,"") + dir+System.getProperty("file.separator")+"<b>"+getSystemPath(path,classname).substring(1)+"</b><br>") ;
				
				if(path.length() > 0)
				{
					classname = path.substring(1).replace('\\','/')+"/"+classname ;
					if(prop.containsKey(classname))
						prop.setProperty(classname, prop.getProperty(classname,"")+dir+System.getProperty("file.separator")+"<b>"+getSystemPath("",classname).substring(1)+"</b><br>") ;
				}
			}
		}
		
		return prop ;
	}
	
	java.util.Properties findLibPath(java.util.Vector paths, java.util.Properties prop)
	{
		for (Enumeration p = paths.elements(); p.hasMoreElements() ;)
		{
			String path = (String)p.nextElement() ;
			File dir = new File(path) ;
			if(dir.isDirectory() == false)	continue ;
			
			for (Enumeration e = prop.propertyNames(); e.hasMoreElements() ;)
			{
				File temp = new File(path+System.getProperty("file.separator")+(String)e.nextElement()) ;
				if(temp.isDirectory())	continue ;
				
				if(prop.containsKey(temp.getName()) && temp.exists())
					prop.setProperty(temp.getName(), prop.getProperty(temp.getName(),"")+path+System.getProperty("file.separator")+"<b>"+temp.getName()+(temp.canRead() ? "" : "<font color=#FF0000>...Can`t Read</font>")+"</b><br>") ;
			}
		}
		
		return prop ;
	}
	
	java.util.Properties findClassPath(java.util.Vector paths, java.util.Properties prop)
	{
		for (Enumeration p = paths.elements(); p.hasMoreElements() ;)
		{
			String path = (String)p.nextElement() ;
			File file = new File(path) ;
			
			if(file.isFile())
			{
				if((path.endsWith(".jar") || path.endsWith(".zip")) == false)	continue ;
				
				try
				{
					JarFile jar = new JarFile(file) ;
					
					//getEntry(String name) ;
					
					for (Enumeration e = jar.entries() ; e.hasMoreElements() ;)
					{
						JarEntry jarentry = (JarEntry)e.nextElement();
						
						
						if(jarentry.isDirectory())							continue ;
						if(jarentry.getName().endsWith(".class") == false)	continue ;
						
						String classname = jarentry.getName() ;
						if(prop.containsKey(classname))
							prop.setProperty(classname,prop.getProperty(classname,"") + path + "...<b>"+getSystemPath("",jarentry.getName()).substring(1) + "</b><br>") ;

						classname = classname.substring(classname.lastIndexOf("/")+1) ;
						if(prop.containsKey(classname))
							prop.setProperty(classname,prop.getProperty(classname,"") + path + "...<b>"+getSystemPath("",jarentry.getName()).substring(1) + "</b><br>") ;
    				}
				}				
				catch(Exception e)
				{
				}
			}
			else if(file.isDirectory())
			{
				prop = findClass(file, path, "", prop) ;
			}
		}
		
		return prop ;
	}	
	
	java.util.Properties getProperties(String name, Object obj)
	{
		Properties prop = new Properties() ;
		Method[] methods = obj.getClass().getMethods() ;		
		for(int i = 0; i < methods.length; i++)
		{
			if(methods[i].getName().startsWith("get"))
			{
				if(	methods[i].getReturnType().getName().startsWith("java.lang.String")
				||	methods[i].getReturnType().getName().startsWith("java.lang.StringBuffer")
				||	methods[i].getReturnType().getName().startsWith("int")
				||	methods[i].getReturnType().getName().startsWith("long"))
				{
					if(methods[i].getParameterTypes().length == 0)
					{
						try
						{
							prop.setProperty(name+"."+methods[i].getName()+"()" , (methods[i].invoke(obj, new Object[0])).toString()	) ;
						}
						catch(NullPointerException e)
						{
							prop.setProperty(name+"."+methods[i].getName()+"()" , "<null>" ) ;
						}
						catch(Exception e)
						{
						}
					}
				}	
			}
    	}
    	return prop ;
    }
%>
<html>
    <head>
    <title><%= application.getServerInfo() %></title>
    <style type="text/css">
      <!--
		body, ul, td, th {
			font-family: verdana,arial,helvetica,sans-serif;
			font-size: 75%;
		}

		.tableTitle {
			font-family: verdana,arial,helvetica,sans-serif;
			font-weight: bold;
		}

		.tableExtras {
			font-family: verdana,arial,helvetica,sans-serif;
			font-size: 85%;
			color: #FFFFFF;
		}
      -->
    </style>
</head>

<body>

Version:<b>2006年01月17日12:00</b> Author:zhang@softforum.com
<b>WAS:<font color=#0000FF>
<%
if(System.getProperty("tomcat.home") != null)			out.println("Tomcat 3.x") ;
else if(System.getProperty("catalina.home") != null)
{
	if(application.getMinorVersion() == 3)				out.println("Tomcat 4.x") ;
	if(application.getMinorVersion() == 4)				out.println("Tomcat 5.x") ;
	if(application.getMinorVersion() == 5)				out.println("Tomcat 6.x") ;
	if(application.getMajorVersion() == 3 && application.getMinorVersion() == 0 )   out.println("Tomcat 7.x") ;
}
else if(System.getProperty("jeus.home") != null)
{
	if(application.getMinorVersion() == 2)				out.println("Jeus 3.x") ;
	else if(application.getMinorVersion() == 3)			out.println("Jeus 4.x") ;
	else if(application.getMinorVersion() == 4)			out.println("Jeus 5.x") ;
	else												out.println("Jeus") ;
}
else if(System.getProperty("weblogic.Name") != null)
{
	if(application.getMinorVersion() == 2)				out.println("WebLogic 6.x") ;
	else if(application.getMinorVersion() == 3)			out.println("WebLogic 7.x,8.x") ;
	else if(application.getMinorVersion() == 4)			out.println("WebLogic 9.x") ;
	else												out.println("WebLogic") ;
}
else													out.println("모름") ;

out.println("[JSDK(Servlet API Version)"+application.getMajorVersion()+"."+application.getMinorVersion()+"]") ;
%>
</font></b><br>
주요WAS별 Servlet API Version(JSDK)은 <a href=http://java.sun.com/products/servlet/industry.html>http://java.sun.com/products/servlet/industry.html</a>에서 확인할 수 있습니다.<br>
<%
	String action = request.getParameter("action") ;
	action = (action == null) ? "system" : action ;
	
	String[][] menu =	{ 
							{	"System.getProperties()" , "system"			}	,
							{	"class.path    정보얻기" , "classpath"		}	,
							{	"libray.path   정보얻기" , "librarypath"	}	,
							{	"Applicaton    정보얻기" , "application"	}	,
							{	"request       정보얻기" , "request"		}	
						} ;
%>
<table valign=middle border='0' bgcolor=#A2C2FC cellspacing='1' cellpadding='1'>
	<tr bgcolor=#E4EDFE>
<%
	for(int i = 0; i < menu.length; i++)
	{
%>
		<td width='1420' valign=middle align=center <%=(action.equals(menu[i][1])) ? "bgcolor=#A2C2FC" : "" %> >
			<b>
<%
		if(action.equals(menu[i][1]))
			out.println(menu[i][0]) ;
		else
			out.println("<a href='"+request.getRequestURI()+"?action="+menu[i][1]+"'>"+menu[i][0]+"</a>") ;
%>
			</b>
		</td>
<%
	}
%>
	</tr>
</table>

<table valign=middle border='0' bgcolor=#A2C2FC cellspacing='1' cellpadding='1'>
	<tr bgcolor=#E4EDFE>
		<td>
<%	
	if(action.equals("system"))
	{
		out.println(printString(System.getProperties())) ;
	}
	
	if(action.equals("classpath"))
	{
%>
<font color=#0000FF><b>WAS:Tocmat 3.x, Tocmat 4.x, Tomcat 5.x, Tomcat 6.x, Tomcat 7.x, Jeus 4.x, WebLogic, Jrun 4.x</b></font><br>
<font color=#0000FF><b>PKG:XecureWeb, XecureExpSign, XecureExpressⓘ, SafeSignOn, XecureYSSNC, XecureConnect, XecureDB, XecurePDF</b></font><br>
<table width=100% valign=middle border='0' bgcolor=#A2C2FC cellspacing='1' cellpadding='0'>
	<tr bgcolor=#E4EDFE valign=middle>
	<td width='250'><b>Class Loader 순서</b></td>
<%		
    	Vector classpath = new Vector() ;

		if(System.getProperty("tomcat.home") != null)	// Tomcat 3.x
		{
%>
	<td><b>Tomcat 3.3.2기준 Class Loader</b></td>
	<tr><td bgcolor=#E4EDFE>System.getProperty("sun.boot.class.path")					</td><td bgcolor=#E4EDFE>Bootstrap classes of your JVM<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.jar					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.zip					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.zip<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.class.path")						</td><td bgcolor=#E4EDFE>System class loader classses (described above)<td></tr>
	
	<tr><td bgcolor=#E4EDFE>System.getProperty("tomcat.home")+/lib/common/*.jar			</td><td bgcolor=#E4EDFE>$TOMCAT_HOME/lib/common/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("tomcat.home")+/lib/apps/*.jar</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$TOMCAT_HOME/lib/apps/*.jar</b></font><td></tr>
	
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/classes				</td><td bgcolor=#E4EDFE>/WEB-INF/classes of your web application<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/lib/*.jar				</td><td bgcolor=#E4EDFE>/WEB-INF/lib/*.jar of your web application<td></tr>
	
	<tr><td bgcolor=#E4EDFE>System.getProperty("tomcat.home")+/lib/endorsed/*.jar		</td><td bgcolor=#E4EDFE>$TOMCAT_HOME/lib/endorsed/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("tomcat.home")+/lib/endorsed/*.zip		</td><td bgcolor=#E4EDFE>$TOMCAT_HOME/lib/endorsed/*.zip<td></tr>
<%
    		{
    			StringTokenizer st = new StringTokenizer(System.getProperty("sun.boot.class.path",""),System.getProperty("path.separator"));
    			while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
   			}
   			
   			classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.jar")) ;
   			classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.zip")) ;
   			
   			{
				StringTokenizer st = new StringTokenizer(System.getProperty("java.class.path",""),System.getProperty("path.separator"));	// 확인못함.
				while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
			}
   			
   			classpath.add(getSystemPath(System.getProperty("tomcat.home"),"/lib/common/*.jar")) ;
   			classpath.add(getSystemPath(System.getProperty("tomcat.home"),"/lib/apps/*.jar")) ;
   			
 			classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/classes")) ;
 			classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/lib/*.jar")) ;
			
			classpath.add(getSystemPath(System.getProperty("tomcat.home"),"/lib/endorsed/*.jar")) ;
			classpath.add(getSystemPath(System.getProperty("tomcat.home"),"/lib/endorsed/*.zip")) ;	// zip됨..
		}
		else if(System.getProperty("catalina.home") != null)
		{

			if(application.getMinorVersion() == 3)	// Tomcat 4.x
			{
%>
	<td><b>Tomcat 4.1.40기준 Class Loader</b></td>
	<tr><td bgcolor=#E4EDFE>System.getProperty("sun.boot.class.path")					</td><td bgcolor=#E4EDFE>Bootstrap classes of your JVM<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.jar					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.zip					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.zip<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.class.path")						</td><td bgcolor=#E4EDFE>System class loader classses (described above)<td></tr>
	
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/classes				</td><td bgcolor=#E4EDFE>/WEB-INF/classes of your web application<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/lib/*.jar				</td><td bgcolor=#E4EDFE>/WEB-INF/lib/*.jar of your web application<td></tr>

	<tr><td bgcolor=#E4EDFE>System.getProperty("catalina.home")+/common/classes			</td><td bgcolor=#E4EDFE>$CATALINA_HOME/common/classes<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("catalina.home")+/common/endorsed/*.jar	</td><td bgcolor=#E4EDFE>$CATALINA_HOME/common/endorsed/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("catalina.home")+/common/endorsed/*.zip	</td><td bgcolor=#E4EDFE>$CATALINA_HOME/common/endorsed/*.zip<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("catalina.home")+/common/lib/*.jar		</td><td bgcolor=#E4EDFE>$CATALINA_HOME/common/lib/*.jar<td></tr>
	
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("catalina.home")+/shared/classes	</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$CATALINA_HOME/shared/classes</b></font><td></tr>
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("catalina.home")+/shared/lib/*.jar</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$CATALINA_HOME/shared/lib/*.jar</b></font><td></tr>
<%
    			{
    			StringTokenizer st = new StringTokenizer(System.getProperty("sun.boot.class.path",""),System.getProperty("path.separator"));
    			while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
   				}
   				
   				classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.jar")) ;
   				classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.zip")) ;

   				{
				StringTokenizer st = new StringTokenizer(System.getProperty("java.class.path",""),System.getProperty("path.separator"));	// 확인못함.
				while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
				}
				   				
 				classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/classes")) ;
 				classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/lib/*.jar")) ;
				
				classpath.add(getSystemPath(System.getProperty("catalina.home"),"/common/classes")) ;
				classpath.add(getSystemPath(System.getProperty("catalina.home"),"/common/endorsed/*.jar")) ;
				classpath.add(getSystemPath(System.getProperty("catalina.home"),"/common/endorsed/*.zip")) ;	// zip됨
 				classpath.add(getSystemPath(System.getProperty("catalina.home"),"/common/lib/*.jar")) ;
 			 	classpath.add(getSystemPath(System.getProperty("catalina.home"),"/shared/classes")) ;
 				classpath.add(getSystemPath(System.getProperty("catalina.home"),"/shared/lib/*.jar")) ;
			}
			if(application.getMinorVersion() == 4)	// Tomcat 5.x
			{
%>
	<td><b>Tomcat 5.5.34기준 Class Loader</b></td>
	<tr><td bgcolor=#E4EDFE>System.getProperty("sun.boot.class.path")					</td><td bgcolor=#E4EDFE>Bootstrap classes of your JVM<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.jar					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.zip					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.zip<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.class.path")						</td><td bgcolor=#E4EDFE>System class loader classses (described above)<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/classes				</td><td bgcolor=#E4EDFE>/WEB-INF/classes of your web application<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/lib/*.jar				</td><td bgcolor=#E4EDFE>/WEB-INF/lib/*.jar of your web application<td></tr>
	
	<tr><td bgcolor=#E4EDFE>System.getProperty("catalina.home")+/common/classes			</td><td bgcolor=#E4EDFE>$CATALINA_HOME/common/classes<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("catalina.home")+/common/endorsed/*.jar	</td><td bgcolor=#E4EDFE>$CATALINA_HOME/common/endorsed/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("catalina.home")+/common/endorsed/*.zip	</td><td bgcolor=#E4EDFE>$CATALINA_HOME/common/endorsed/*.zip<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("catalina.home")+/common/i18n/*.jar		</td><td bgcolor=#E4EDFE>$CATALINA_HOME/common/i18n/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("catalina.home")+/common/lib/*.jar		</td><td bgcolor=#E4EDFE>$CATALINA_HOME/common/lib/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("catalina.base")+/shared/classes	</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$CATALINA_BASE/shared/classes</b></font><td></tr>
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("catalina.base")+/shared/lib/*.jar</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$CATALINA_BASE/shared/lib/*.jar</b></font><td></tr>
<%
    			{
    			StringTokenizer st = new StringTokenizer(System.getProperty("sun.boot.class.path",""),System.getProperty("path.separator"));
    			while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
   				}
   			
				classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.jar")) ;
				classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.zip")) ;					// zip됨.
				
   				{
				StringTokenizer st = new StringTokenizer(System.getProperty("java.class.path",""),System.getProperty("path.separator"));
				while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
				}
				
 				classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/classes")) ;
 				classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/lib/*.jar")) ;
 				
				classpath.add(getSystemPath(System.getProperty("catalina.home"),"/common/classes")) ;
				classpath.add(getSystemPath(System.getProperty("catalina.home"),"/common/endorsed/*.jar")) ;
				classpath.add(getSystemPath(System.getProperty("catalina.home"),"/common/endorsed/*.zip")) ;	// zip됨.
				classpath.add(getSystemPath(System.getProperty("catalina.home"),"/common/i18n/*.jar")) ;;
 				classpath.add(getSystemPath(System.getProperty("catalina.home"),"/common/lib/*.jar")) ;

 			 	classpath.add(getSystemPath(System.getProperty("catalina.base"),"/shared/classes")) ;
 				classpath.add(getSystemPath(System.getProperty("catalina.base"),"/shared/lib/*.jar")) ;
			}
			if(application.getMinorVersion() == 5)	// Tomcat 6.x
			{
%>
	<td><b>Tomcat 6.0.35기준 Class Loader</b></td>
	<tr><td bgcolor=#E4EDFE>System.getProperty("sun.boot.class.path")					</td><td bgcolor=#E4EDFE>Bootstrap classes of your JVM<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.jar					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.zip					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.zip<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.class.path")						</td><td bgcolor=#E4EDFE>System class loader classses (described above)<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/classes				</td><td bgcolor=#E4EDFE>/WEB-INF/classes of your web application<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/lib/*.jar				</td><td bgcolor=#E4EDFE>/WEB-INF/lib/*.jar of your web application<td></tr>
	
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("catalina.base")+/shared/classes	</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$CATALINA_BASE/shared/classes</b></font><td></tr>
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("catalina.base")+/shared/lib/*.jar</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$CATALINA_BASE/shared/lib/*.jar</b></font><td></tr>
<%
    			{
    			StringTokenizer st = new StringTokenizer(System.getProperty("sun.boot.class.path",""),System.getProperty("path.separator"));
    			while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
   				}
   			
				classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.jar")) ;
				classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.zip")) ;					// zip됨.
				
   				{
				StringTokenizer st = new StringTokenizer(System.getProperty("java.class.path",""),System.getProperty("path.separator"));
				while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
				}
				
 				classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/classes")) ;
 				classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/lib/*.jar")) ;
 				
 			 	classpath.add(getSystemPath(System.getProperty("catalina.base"),"/shared/classes")) ;
 				classpath.add(getSystemPath(System.getProperty("catalina.base"),"/shared/lib/*.jar")) ;
			}	
			if(application.getMinorVersion() == 0 && application.getMajorVersion() == 3)	// Tomcat 7.x
			{
%>
	<td><b>Tomcat 7.0.23기준 Class Loader</b></td>
	<tr><td bgcolor=#E4EDFE>System.getProperty("sun.boot.class.path")					</td><td bgcolor=#E4EDFE>Bootstrap classes of your JVM<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.jar					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.jar<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.zip					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.zip<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.class.path")						</td><td bgcolor=#E4EDFE>System class loader classses (described above)<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/classes				</td><td bgcolor=#E4EDFE>/WEB-INF/classes of your web application<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/lib/*.jar				</td><td bgcolor=#E4EDFE>/WEB-INF/lib/*.jar of your web application<td></tr>
	
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("catalina.base")+/shared/classes	</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$CATALINA_BASE/shared/classes</b></font><td></tr>
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("catalina.base")+/shared/lib/*.jar</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$CATALINA_BASE/shared/lib/*.jar</b></font><td></tr>
<%
    			{
    			StringTokenizer st = new StringTokenizer(System.getProperty("sun.boot.class.path",""),System.getProperty("path.separator"));
    			while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
   				}
   			
				classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.jar")) ;
				classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.zip")) ;					// zip됨.
				
   				{
				StringTokenizer st = new StringTokenizer(System.getProperty("java.class.path",""),System.getProperty("path.separator"));
				while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
				}
				
 				classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/classes")) ;
 				classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/lib/*.jar")) ;
 				
 			 	classpath.add(getSystemPath(System.getProperty("catalina.base"),"/shared/classes")) ;
 				classpath.add(getSystemPath(System.getProperty("catalina.base"),"/shared/lib/*.jar")) ;
			}
   		}
		else if(System.getProperty("jeus.home") != null)
		{
%>
	<td><b>Jeus 4.2 Class Loader</b></td>

	<tr><td bgcolor=#E4EDFE>System.getProperty("sun.boot.class.path")					</td><td bgcolor=#E4EDFE>Bootstrap classes of your JVM<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.jar					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.jar(확인요망)<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.zip					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.zip(확인요망)<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.class.path")						</td><td bgcolor=#E4EDFE>System class loader classses (described above)<td></tr>
	
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/classes				</td><td bgcolor=#E4EDFE>/WEB-INF/classes of your web application<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/lib/*.jar				</td><td bgcolor=#E4EDFE>/WEB-INF/lib/*.jar of your web application<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/lib/*.zip				</td><td bgcolor=#E4EDFE>/WEB-INF/lib/*.zip of your web application(확인요망)<td></tr>
	
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("jeus.home")+/lib/application			</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$JEUS_HOME/lib/application</b></font><td></tr>
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("jeus.home")+/lib/application/*.jar	</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$JEUS_HOME/lib/application/*.jar</b></font><td></tr>
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("jeus.home")+/lib/application/*.zip	</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$JEUS_HOME/lib/application/*.zip</b></font><td></tr>
	<tr><td bgcolor=#E4EDFE><font color=#0000FF><b>System.getProperty("jeus.home")+/lib/system/*.jar		</b></font></td><td bgcolor=#E4EDFE><font color=#0000FF><b>$JEUS_HOME/lib/system/*.jar</b></font><td></tr>
<%
    		{
    			StringTokenizer st = new StringTokenizer(System.getProperty("sun.boot.class.path",""),System.getProperty("path.separator"));
    			while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
   			}
   			
			classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.jar")) ;	// 확인요망
			classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.zip")) ;	// 확인요망
				
			{
				StringTokenizer st = new StringTokenizer(System.getProperty("java.class.path",""),System.getProperty("path.separator"));
				while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
			}
			
 			classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/classes")) ;
 			classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/lib/*.jar")) ;
 			classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/lib/*.zip")) ;			// 확인요망!
			
			classpath.add(getSystemPath(System.getProperty("jeus.home"),"/lib/application")) ;
			classpath.add(getSystemPath(System.getProperty("jeus.home"),"/lib/application/*.jar")) ;
			classpath.add(getSystemPath(System.getProperty("jeus.home"),"/lib/application/*.zip")) ;
			classpath.add(getSystemPath(System.getProperty("jeus.home"),"/lib/system/*.jar")) ;
 		}
		else if(System.getProperty("jrun.home") != null)
		{
%>
	<td><b>Jrun 4.x Class Loader</b></td>

	<tr><td bgcolor=#E4EDFE>System.getProperty("sun.boot.class.path")					</td><td bgcolor=#E4EDFE>Bootstrap classes of your JVM<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.jar					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.jar(확인요망)<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.zip					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.zip(확인요망)<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.class.path")						</td><td bgcolor=#E4EDFE>System class loader classses (described above)<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("jrun.home")+/servers/lib/*.jar			</td><td bgcolor=#E4EDFE><font color=#0000FF><b>$JRUN_HOME/servers/lib/*.jar</b></font><td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/classes				</td><td bgcolor=#E4EDFE>/WEB-INF/classes of your web application<td></tr>
<%
    		{
    			StringTokenizer st = new StringTokenizer(System.getProperty("sun.boot.class.path",""),System.getProperty("path.separator"));
    			while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
   			}
   			
			classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.jar")) ;	// 확인요망
			classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.zip")) ;	// 확인요망
				
			{
				StringTokenizer st = new StringTokenizer(System.getProperty("java.class.path",""),System.getProperty("path.separator"));
				while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
			}
			
			classpath.add(getSystemPath(System.getProperty("jrun.home"),"/servers/lib/*.jar")) ;
 			classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/classes")) ;
		}
 		else
 		{
%>
	<td><b>기타 WAS Class Loader</b></td>

	<tr><td bgcolor=#E4EDFE>System.getProperty("sun.boot.class.path")					</td><td bgcolor=#E4EDFE>Bootstrap classes of your JVM<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.jar					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.jar(확인요망)<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.ext.dirs")+/*.zip					</td><td bgcolor=#E4EDFE>$JAVA_HOME/jre/lib/ext/*.zip(확인요망)<td></tr>
	<tr><td bgcolor=#E4EDFE>System.getProperty("java.class.path")						</td><td bgcolor=#E4EDFE>System class loader classses (described above)<td></tr>
	
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/classes				</td><td bgcolor=#E4EDFE>/WEB-INF/classes of your web application<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/lib/*.jar				</td><td bgcolor=#E4EDFE>/WEB-INF/lib/*.jar of your web application<td></tr>
	<tr><td bgcolor=#E4EDFE>application.getRealPath("/")+/WEB-INF/lib/*.zip				</td><td bgcolor=#E4EDFE>/WEB-INF/lib/*.zip of your web application(확인요망)<td></tr>
	
<%
    		{
    			StringTokenizer st = new StringTokenizer(System.getProperty("sun.boot.class.path",""),System.getProperty("path.separator"));
    			while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
   			}
   			
			classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.jar")) ;	// 확인요망
			classpath.add(getSystemPath(System.getProperty("java.ext.dirs"),"/*.zip")) ;	// 확인요망
				
			{
				StringTokenizer st = new StringTokenizer(System.getProperty("java.class.path",""),System.getProperty("path.separator"));
				while (st.hasMoreTokens())	classpath.add(getSystemPath((String)st.nextToken(),""));
			}
			
 			classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/classes")) ;
 			classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/lib/*.jar")) ;
 			classpath.add(getSystemPath(application.getRealPath("/"),"/WEB-INF/lib/*.zip")) ;			// 확인요망!

 		}		
%>
</table>
		<br><b><font color=#0000FF>-- class.path실제경로 --</font></b><br>
<%
 		Vector realclasspath = new Vector() ;		
		for (Enumeration e = classpath.elements() ; e.hasMoreElements() ;)
		{			
			String path = (String)e.nextElement() ;
			File file = null;
			
			if(path.endsWith("*.jar") || path.endsWith("*.zip"))
			{
				file = new File(path.substring(0,path.length()-6)) ;
				
				out.println("<b>"+path+"</b>"+((file.exists()) ? "" : "<font color=#FF0000>...Not Exists</font>")+"<br>");
				if(file.exists())
				{
					String extname = path.substring(path.length()-4) ;
					File[] temp = file.listFiles() ;
					for(int i = 0; i < temp.length; i++)
					{
						if(temp[i].isFile() && temp[i].getName().endsWith(extname))
							if(!realclasspath.contains(temp[i].getPath()))
								realclasspath.add(temp[i].getPath()) ;
					}
				}
			}
			else
			{
				file = new File(path) ;
					
				if(file.isDirectory())
				{
					out.println("<b>"+path+"</b>"+((file.exists()) ? "" : "<font color=#FF0000>...Not Exists</font>")+"<br>");
					if(file.exists())
						if(!realclasspath.contains(file.getPath()))
							realclasspath.add(file.getPath()) ;
				}
				else
				{
					if(file.exists() == false)
						out.println("<b>"+path+"</b>"+((file.exists()) ? "" : "<font color=#FF0000>...Not Exists</font>")+"<br>");
					else
						out.println("<b>"+path+"</b>"+((file.canRead()) ? "" : "<font color=#FF0000>...Can`t Read</font>")+"<br>");
						
					if(file.exists() && file.canRead())
						if(!realclasspath.contains(file.getPath()))	
							realclasspath.add(file.getPath()) ;
				}
			}
		}

	    out.println("<br>") ;
	    	    
		Properties prop = new Properties() ;
		
		prop.setProperty("xecure.servlet.XecureConfig"		, "") ;	// XecureWeb
		prop.setProperty("xecure.exp.XecureExpConfig"		, "") ;	// XecureExpSign
		prop.setProperty("xei.conf.XecureConfig"			, "") ;	// XecureExpressⓘ
		prop.setProperty("xecure.yessign.XecureCheckerV2"	, "") ;	// XecureYSSNC 2.0
		prop.setProperty("xecure.yessign.XecureChecker"		, "") ;	// XecureYSSNC 1.0
		prop.setProperty("com.sf.xc3.XC_v3"					, "") ;	// XecureConnect3
		prop.setProperty("com.softforum.xdbe.XdspNative"	, "") ;	// XecureDB
		prop.setProperty("com.sf.XecurePDF.XecPDFConfig"	, "") ;	// XecurePDF
		prop.setProperty("SafeSignOn.SsoConfig"				, "") ;	// SafeSignOn
		prop.setProperty("eamldap.EAMConf"					, "") ;	// EAMLDAP
		prop.setProperty("sso30ldap.SsoConf"				, "") ;	// SSOLDAP
		prop.setProperty("com.test.test"					, "") ;	// TEST
	    
		prop.setProperty("com.softforum.xdbe.XdbHelper"		, "") ;	// XecureDB XdbHelper
		prop.setProperty("com.softforum.xdbe.xCrypto"		, "") ;	// XecureDB xCrypto
		
		for (Enumeration e = prop.propertyNames() ; e.hasMoreElements() ;)
		{
			String classname = (String)e.nextElement() ;	
			if(classname.endsWith(".class") == false)	// .class없으면
			{
				prop.remove(classname) ;
				prop.setProperty(classname.replace('.','/')+".class","") ;
			}
			else
			{
				prop.remove(classname) ;
				prop.setProperty(classname.substring(0,classname.length()-6).replace('.','/')+".class","") ;
			}
		}
			    
	    prop = findClassPath(realclasspath, prop) ;
	    
	    out.println(printString(prop)) ;
	    
	    if(prop.getProperty("xecure/servlet/XecureConfig.class","").length() != 0)
		{
			out.println("<b><font color=#FF0000>本Page에서 xecure.servlet.XecureConfig를 찾아 UnComment하시요!!..그럼 getXXX()로 되는 정보을 얻을 수 있습니다.</font></b><br>") ;
	    	//out.println(printString(getProperties("xecure.servlet.XecureConfig",new xecure.servlet.XecureConfig()))) ;
	    }
	    
	    if(prop.getProperty("xecure/exp/XecureExpConfig.class","").length() != 0)
		{
			out.println("<b><font color=#FF0000>本Page에서 xecure.exp.XecureExpConfig 찾아 UnComment하시요!!..그럼 getXXX()로 되는 정보을 얻을 수 있습니다.</font></b><br>") ;
	    	//out.println(printString(getProperties("xecure.exp.XecureExpConfig",new xecure.exp.XecureExpConfig()))) ;
	    }
	    
	    if(prop.getProperty("xei/conf/XecureConfig.class","").length() != 0)
		{
			out.println("<b><font color=#FF0000>本Page에서 xei.conf.XecureConfig를 찾아 UnComment하시요!!..그럼 getXXX()로 되는 정보을 얻을 수 있습니다.</font></b><br>") ;
	    	//out.println(printString(getProperties("xei.conf.XecureConfig",new xei.conf.XecureConfig()))) ;
	    }
	    
	    if(prop.getProperty("com/sf/XecurePDF/XecureConfig.class","").length() != 0)
		{
			out.println("<b><font color=#FF0000>本Page에서 com.sf.XecurePDF.XecPDFConfig 찾아 UnComment하시요!!..그럼 getXXX()로 되는 정보을 얻을 수 있습니다.</font></b><br>") ;
	    	//out.println(printString(getProperties("com.sf.XecurePDF.XecPDFConfig",new com.sf.XecurePDF.XecPDFConfig()))) ;
	    }
	    
	    if(prop.getProperty("SafeSignOn/SsoConfig.class","").length() != 0)
	    {
			out.println("<b><font color=#FF0000>本Page에서 SafeSignOn.SsoConfig를 찾아 UnComment하시요!!..그럼 getXXX()로 되는 정보을 얻을 수 있습니다.</font></b><br>") ;
	    	//out.println(printString(getProperties("SafeSignOn.SsoConfig",new SafeSignOn.SsoConfig()))) ;	    
	    }
	    
	    if(prop.getProperty("eamldap/EAMConf.class","").length() != 0)
	    {
			out.println("<b><font color=#FF0000>本Page에서 eamldap.EAMConf 찾아 UnComment하시요!!..그럼 getXXX()로 되는 정보을 얻을 수 있습니다.</font></b><br>") ;
	    	//out.println(printString(getProperties("eamldap.EAMConf",new eamldap.EAMConf()))) ;	    
	    }
	    
	    if(prop.getProperty("sso30ldap/SsoConf.class","").length() != 0)
	    {
			out.println("<b><font color=#FF0000>本Page에서 sso30ldap.SsoConf 찾아 UnComment하시요!!..그럼 getXXX()로 되는 정보을 얻을 수 있습니다.</font></b><br>") ;
	    	//out.println(printString(getProperties("sso30ldap.SsoConf",new sso30ldap.SsoConf()))) ;	    
	    }
	    
	    if(prop.getProperty("com/test/test.class","").length() != 0)
	    {
			out.println("<b><font color=#FF0000>本Page에서 com.test.test 찾아 UnComment하시요!!..그럼 getXXX()로 되는 정보을 얻을 수 있습니다.</font></b><br>") ;
	    	//out.println(printString(getProperties("com.test.test",new com.test.test()))) ;	    
	    }
	    
%>
		<br><b><font color=#0000FF>-- class.path실제경로[*.jar,*.zip 찾고 Not Exists, Can`t Read는 제외] --</font></b><br>
<%
		for (Enumeration e = realclasspath.elements() ; e.hasMoreElements() ;)
			out.println((String)e.nextElement()+"<br>");
	}
	
	if(action.equals("librarypath"))
	{
%>
<table width=100% valign=middle border='0' bgcolor=#A2C2FC cellspacing='1' cellpadding='0'>
	<tr bgcolor=#E4EDFE valign=middle>
		<td width=25%><b>System.getProperty("os.name")</b></td><td><%=System.getProperty("os.name")%><td>
	</tr>
	<tr bgcolor=#E4EDFE valign=middle>
		<td width=25%><b>System.getProperty("os.version")</b></td><td><%=System.getProperty("os.version")%><td>
	</tr>
	<tr bgcolor=#E4EDFE valign=middle>
		<td width=25%><b>System.getProperty("os.arch")</b></td><td><%=System.getProperty("os.arch")%><td>
	</tr>
	<tr bgcolor=#E4EDFE valign=middle>
		<td width=25%><b>System.getProperty("sun.arch.data.model")</b></td><td><%=System.getProperty("sun.arch.data.model")%><td>
	</tr>
	<tr bgcolor=#E4EDFE valign=middle>
		<td width=25%><b>System.getProperty("sun.boot.library.path")</b></td><td><%=System.getProperty("sun.boot.library.path")%><td>
	</tr>
	<tr bgcolor=#E4EDFE valign=middle>
		<td width=25%><b>System.getProperty("java.library.path")</b></td><td><%=System.getProperty("java.library.path")%><td>
	</tr>
</table>
<%
		Vector librarypath = new Vector() ;
		
		{
    		StringTokenizer st = new StringTokenizer(System.getProperty("sun.boot.library.path",""),System.getProperty("path.separator"));
    		while (st.hasMoreTokens())	librarypath.add((String)st.nextToken());
    	}
    	
		{
    		StringTokenizer st = new StringTokenizer(System.getProperty("java.library.path",""),System.getProperty("path.separator"));
    		while (st.hasMoreTokens())	librarypath.add((String)st.nextToken());
    	}
 
 %>
		<br><b><font color=#0000FF>-- lib.path실제경로 --</font></b><br>
<%   	
    	Vector reallibpath = new Vector() ;
    	
		for (Enumeration e = librarypath.elements() ; e.hasMoreElements() ;)
		{
			String path = (String)e.nextElement() ;
		
			if(path.startsWith(".."))	path = System.getProperty("user.dir","") + path.substring(2) ;
			if(path.startsWith("."))	path = System.getProperty("user.dir","") + path.substring(1) ;
			if(path.endsWith(System.getProperty("file.separator")))
				path = path.substring(0,path.length()-System.getProperty("file.separator").length()) ;
				
			if(reallibpath.contains(path) == false)
			{
				reallibpath.add(path) ;
				out.println("<b>"+path+"</b>"+"<br>");
			}
			else
			{
				out.println(path+"<br>");
			}
    	}
    	
		out.println("<br>") ;
    	
		Properties prop = new Properties() ;
		
		prop.setProperty("XWservlet"			, "") ;		// XecureWeb
		prop.setProperty("XecureExpSignJNIv2"	, "") ;		// XecureExpSign
		prop.setProperty("XEI_jni"				, "") ;		// XecureExpressⓘ
		prop.setProperty("XecurePDF"			, "") ;		// XecurePDF
		prop.setProperty("XC_v3_jni"			, "") ;		// XecureConnect3
		prop.setProperty("xdsp_jni"				, "") ;		// XecureDB
		
		prop.setProperty("jcrypto"				, "") ;		// XecureDB jcrypto
		prop.setProperty("xdbhelper"			, "") ;		// XecureDB xdbhelper
		prop.setProperty("xCrypto"				, "") ;		// XecureDB xCrypto
		
		for (Enumeration e = prop.propertyNames() ; e.hasMoreElements() ;)
		{
			String libname = (String)e.nextElement() ;

			if(System.getProperty("os.name").startsWith("Window"))
			{
				if(libname.endsWith(".dll") == false)	// .dll없으면
				{
					prop.remove(libname) ;
					prop.setProperty(libname+".dll","") ;
				} 

			}
			else
			{				
				String librarypostfix = (System.getProperty("os.name").startsWith("HP")) ? ".sl" : ".so" ;
				if(libname.startsWith("lib") == false || libname.endsWith(librarypostfix) == false)
				{
					prop.remove(libname) ;
					libname = libname.startsWith("lib") ? "" : "lib"+libname ;
					libname = libname.endsWith(librarypostfix) ? "" : libname+librarypostfix ;
					prop.setProperty(libname,"") ;
				}
			}
		}
		
	    prop = findLibPath(reallibpath, prop) ;
	    
	    out.println(printString(prop)) ;
%>
<%
    }
    
    if(action.equals("application"))
    {
    	out.println("<br><b>JSDK Version:"+ application.getMajorVersion()+"."+application.getMinorVersion()+"</b>"+"<br>") ;
    	out.println("<b>DocumentRoot(application.getRealPath(/))</b>:"+application.getRealPath("/")+"<br>") ;

    	out.println(printString(getProperties("application",application))) ;
    	
    	//out.println("<b>application.getResourcePaths(/WEB-INF/classes)</b>:<br>"+printString(application.getResourcePaths("/WEB-INF/classes"))+"</b>"+"<br>") ;
    	//out.println("<b>application.getResourcePaths(/WEB-INF/lib)    </b>:<br>"+printString(application.getResourcePaths("/WEB-INF/lib"))+"</b>"+"<br>") ;
    }
    
    if(action.equals("request"))
    {
    	out.println(printString(getProperties("request",request))) ;
    	
    	Cookie[] cookies = request.getCookies() ;
    	for(int i = 0; i < cookies.length; i++)
    		out.println(printString(getProperties("cookies["+i+"]",cookies[i]))) ;
    	
    }
    
%>
		</td>
	</tr>
</table>
</body>
</html>