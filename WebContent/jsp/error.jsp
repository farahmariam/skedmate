<html>
<head>
<link rel="stylesheet" href="../css/scheduleStyles.css">
</head>
<body>
<%

String errormessage = "";
if(session.getAttribute("errormessage")!=null && session.getAttribute("errormessage")!="")
{
	errormessage = (String)session.getAttribute("errormessage");	
}


%>


 <div class="header">
    <p class="settingHeader"> Error Occurred!</p>
    <p><%=errormessage %> </p>
	<p >Please Try again Here -  <a href='index.jsp' class="three">Go to Login</a></p>
 </div>
	
</body>
</html>
