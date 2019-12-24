<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import ="java.io.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log</title>
</head>
<%

ServletContext context = request.getServletContext();
String loginUrl = context.getInitParameter("login-url");


if ((session.getAttribute("loginname") == null) || (session.getAttribute("loginname") == ""))
{
	
	
%>

	<h3 class="settingText">You are not logged in!</h3> <br/>
	<a href="<%=loginUrl %>" class="three" >Please Login..</a>


<%
}
else 
{
	
%>
<body style="background-color:#f7f0f7">
<img src="../images/skedmate3.jpg"  style="width:1500px;height:180px;">

<%

	try
	{
		
		String companyLoginId = (String)session.getAttribute("companyLoginId");
		String strLogPath = context.getInitParameter("logfile-path");
		String fileName = companyLoginId + ".txt";
		strLogPath = strLogPath.concat(fileName);
		//File strFile = new File(strLogPath);
		BufferedReader reader = new BufferedReader(new FileReader(strLogPath));
        StringBuilder sb = new StringBuilder();
        
        String line = reader.readLine();
        while(line!=null){
   %>
   
   <p> <%= line %> </p>
   
   <% 
       
         line = reader.readLine();
        }
        
       

		
		
	}
	catch(Exception ex)
	{
		
		out.println("Error: " + ex.toString());
	}

}
%>

</body>
</html>