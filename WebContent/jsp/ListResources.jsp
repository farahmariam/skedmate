<%@page import="java.util.*, java.text.*,  java.sql.* , trilane.CommonDatabase, trilane.user"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of all resources</title>
<link rel="stylesheet" href="../css/report.css">
</head>
<%
ServletContext context = request.getServletContext();

ArrayList<user> userList = null;
CommonDatabase objCommon = new CommonDatabase();
userList = objCommon.getAllResources(context, session);





%>
<body>

<div class="scrollableContainer">

  <div class="scrollingArea">

 <table class="cruises scrollable"  >
	<thead>
		<tr>
		
			<th class="name"><div class="th-inner">First Name</div></th>
			<th class="name"><div class="th-inner">Last Name</div></th>
			<th class="name"><div class="th-inner">Login Name</div></th>
			<th class="name"><div class="th-inner">Email</div></th>
			<th class="name"><div class="th-inner">Team</div></th>
			<th class="name"><div class="th-inner">Administrator Status</div></th>
		</tr>
	</thead>
	<tbody>
	
	<%
	CommonDatabase obj = new CommonDatabase();
	for(int i=0;i< userList.size();i++)
	{
		user userObject = userList.get(i);
		int userId = userObject.getUserId();
		String fName = userObject.getfName();
		String lName = userObject.getlName();
		String email = userObject.getEmail();
		String loginName = userObject.getUserName();
		int techId = userObject.getUserTechId();
		int isAdmin = userObject.getUserIsAdmin();
		String teamName="";
		String AdminStatus="";
		
		if(fName==null)
		{
			fName="Not saved.";
		}
		
		if(lName==null)
		{
			lName="Not saved.";
		}
		
		
		if(email==null)
		{
			email="Not saved.";
		}
		
		if((Integer)techId==null)
		{
			teamName="Team not saved.";
		}
		else
		{
			teamName = 	obj.getTechnologyName(techId, context, session);
		}
		
		if((Integer)isAdmin==null)
		{
			AdminStatus="Admin status not saved.";
		}
		else
		{
			boolean boolIsAdmin = obj.checkIfAdmin(userId, context, session);
			if(boolIsAdmin)
			{
				AdminStatus = "Admin";
			}
			else
			{
				AdminStatus = "Not Admin";
			}
		}
	%>
	
	<tr>
		<td ><div><%=fName %></div></td>
		<td ><div><%=lName %></div></td>
		<td ><div><%=loginName %></div></td>
		<td ><div><%=email %></div></td>
		<td ><div><%=teamName %></div></td>
		<td ><div><%=AdminStatus %></div></td>
	
	</tr>
	
	<%
	}
	
	%>	
		
	</tbody>
	</table>
</div>
</div>
	
	
	

</body>
</html>