
<%@ page import ="java.sql.*" %>
<%@ page import ="java.util.*" %>
<%@ page import ="trilane.CommonDatabase" %>
<%@ page import ="trilane.TeamObject" %>





<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Resource details</title>
<link rel="stylesheet" href="../css/scheduleStyles.css">
</head>
<body >

<%
	int userId = -1;
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
		userId = Integer.parseInt(request.getParameter("userid"));
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		Connection connection = null;
		Statement stUser = null;
		ResultSet rsUser = null;
		
		String firstName = "";
		String lastName="";
		String email="";
		String userName="";
		String password = "";
		int techId = -1;
		int isAdmin = -1;
			
		try
		{
			
			session.setAttribute("month", month);
			session.setAttribute("year", year);
			Class.forName("com.mysql.jdbc.Driver");
			
			String databaseUrl = (String) session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			
			//find which technology the user works in
			stUser = connection.createStatement();
			String userQuery = "select * from users where user_id = " + userId;
			rsUser = stUser.executeQuery(userQuery);
			if(rsUser.next())
					{
						firstName = rsUser.getString("fname");
						lastName = rsUser.getString("lname");
						email = rsUser.getString("email");
						userName  = rsUser.getString("uname");
						password = rsUser.getString("pwd");
						techId = rsUser.getInt("tech_id");
						isAdmin = rsUser.getInt("is_admin");
					}
			
			
		}
		catch(SQLException se)
		{
	   		//Handle errors for JDBC
	  		 se.printStackTrace();
		 }
		catch(Exception e)
		{
	  		 //Handle errors for Class.forName
	   			e.printStackTrace();
		}
		finally
		{
			 if (rsUser != null) try { rsUser.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (stUser != null) try { stUser.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		
		
		if(firstName==null) firstName="";
		if(lastName==null) lastName="";
		if(email==null) email="";	
	

%>

<form name="editresource" method="post" action="${pageContext.request.contextPath}/SaveResourceDetails" onsubmit="return validateForm()" onreset="clearSpans()">
            <center>
            <table border="1" width="50%" cellpadding="5">
                <thead>
                    <tr>
                        <th class="backcolourpurple bold8" colspan="2">Update resource details here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="index">First Name</td>
                        <td><input type="text" name="fname" value="<%= firstName %>" /></td>
                    </tr>
                    <tr>
                        <td class="index">Last Name</td>
                        <td><input type="text" name="lname" value="<%= lastName %>" /></td>
                    </tr>
                    <tr>
                        <td class="index">Email</td>
                        <td><input type="text" name="email" value="<%= email %>" onBlur="checkEmail()" onClick="clearSpan('isEmail')" /><span id="isEmail"></span></td>
                    </tr>
                    <tr>
                        <td class="index">User Name</td>
                        <td><input type="text" name="uname" value="<%=userName %>" readonly  /></td>
                    </tr>
                    <tr>
                        <td class="index">Password</td>
                        <td><input type="password" name="pass" value="<%=password %>" onBlur="checkPasswordMatch()" onClick="changePwdBoxStyle()"/></td>
                    </tr>
					<tr>
                        <td class="index">Confirm Password</td>
                        <td><input type="password" name="confirmpass" value="<%=password %>" onBlur="checkPasswordMatch()" onClick="changePwdBoxStyle()"/><span id="pwdMatch"></span></td>
                    </tr>
					<tr>
					
						<td class="index"> Team </td>
						<td> 
           					<select name="technology" id="technology">
           					<%
           						CommonDatabase objCommon = new CommonDatabase();
           					    ArrayList<TeamObject> teamList = null;
           					    teamList = objCommon.getAllTeams(context, session);
           					    int team_Id=-1;
           					    String team_name="";
           					    
           						//Get the status combo options for the particular day
           						for(int loop=0; loop<teamList.size(); loop++)
           						{
           							TeamObject teamObject = teamList.get(loop);
           							team_Id = teamObject.getTeamId();
           							team_name = teamObject.getTeamName();
           							
           							
           							if(techId == team_Id)
           							{
           									
           								
           								
           					%>
           								<option value="<%=team_Id  %>" selected><%=team_name  %> </option>
           					
           					<% 			
           							}
           							else
           							{
           									
           									
           					%>
           					
           								<option value="<%=team_Id  %>" ><%=team_name  %> </option>
           					
           					<% 
           							}
           								
           						}
           						
           						
           					%>
           					
           					
           					
           					
	           					
    						</select>
    					</td>
					
                    </tr>
					
                    <tr>
                        <td class="index" ><input type="submit" value="Submit" />&nbsp <input type="button" value="Go back to Calendar!" onClick="goToCalendar()" class="button"/></td>
                        <td class="index"><input type="reset" value="Reset" /> &nbsp <input type="button" value="Clear" onClick="clearFields()" class="button"/> </td>
                    </tr>

					<input type="hidden" name="userid" value="<%=userId%>"/>
					<input type="hidden" name="isadmin" value="<%=isAdmin%>"/>
					<input type="hidden" name="month" value="<%=month%>"/>
					<input type="hidden" name="year" value="<%=year%>"/>
                   
                </tbody>
            </table>
            </center>
        </form>

<%
	}
%>

</body>

<script type="text/javascript">
function validateForm() 
{
	    	var x = document.forms["editresource"]["fname"].value;
	   		 if (x == null || x == "")
			{
	       		 alert(" First name must be filled out");
	        	return false;
	   		 }
			
			var z = document.forms["editresource"]["pass"].value;
	    	if (z == null || z == "")
			{
	        	alert(" Password must be filled out");
	        	return false;
	    	}
			var cpass = document.forms["editresource"]["confirmpass"].value;
	    	if (cpass == null || cpass == "")
			{
	        	alert(" Confirm Password must be filled out");
	        	return false;
	    	}
	
			var email =  document.forms["editresource"]["email"].value;
			var reEmail = /^(?:[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+\.)*[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+@(?:(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!\.)){0,61}[a-zA-Z0-9]?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!$)){0,61}[a-zA-Z0-9]?)|(?:\[(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\]))$/;

		  	if(!email.match(reEmail)) {
		   	 	alert("Invalid email address");
		    	return false;
		  	}
	 	 	
			
		 	if (z != cpass) 
		 	{
		        alert("Passwords do not match");
		        document.forms["editresource"]["pass"].style.borderColor = "#E34234";
		        document.forms["editresource"]["confirmpass"].style.borderColor = "#E34234";
		        return false;
		    }
	  
	  
	  return true;

}
function checkEmail()
{
	clearSpan("isEmail");
	
	var email =  document.forms["editresource"]["email"].value;
	if(email!="")
		{
		var reEmail = /^(?:[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+\.)*[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+@(?:(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!\.)){0,61}[a-zA-Z0-9]?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!$)){0,61}[a-zA-Z0-9]?)|(?:\[(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\]))$/;

	  	if(!email.match(reEmail)) 
	  	{
	  		document.getElementById("isEmail").style.color = "red";
	        document.getElementById("isEmail").innerHTML = "INVALID EMAIL";
	        document.forms["editresource"]["email"].value = "";
	  	}
		
		}
	
	
	
	
}




function checkPasswordMatch()
{
			clearSpan("pwdMatch");
			var z = document.forms["editresource"]["pass"].value;
			var cpass = document.forms["editresource"]["confirmpass"].value;
	    	if (z != null && z != "" && cpass != null && cpass != "" )
			{
	        	if (z != cpass) 
				 	{
						document.getElementById("pwdMatch").style.color = "red";
        				document.getElementById("pwdMatch").innerHTML = "PASSWORDS DO NOT MATCH";
        				document.forms["editresource"]["pass"].value = "";
        				document.forms["editresource"]["confirmpass"].value = "";
				        
				        document.forms["editresource"]["pass"].style.borderColor = "#E34234";
				        document.forms["editresource"]["confirmpass"].style.borderColor = "#E34234";
				        
				    }

	    	}
			
	    	
	
	
	
}
function changePwdBoxStyle()
{
	
	document.forms["editresource"]["pass"].style.borderColor = "";
    document.forms["editresource"]["confirmpass"].style.borderColor = "";
    clearSpan("pwdMatch");
	
	}




function clearSpan(elementId)
{
	document.getElementById(elementId).innerHTML ="";	
}

function clearSpans()
{
	clearSpan("isEmail");
	
	clearSpan("pwdMatch");
	document.forms["editresource"]["pass"].style.borderColor = "";
    document.forms["editresource"]["confirmpass"].style.borderColor = "";
	
}
function goToCalendar()
{	
	
	window.open('showCalendar.jsp', '_self');
	
}

function clearFields()
{ 
	
	document.forms["editresource"]["fname"].value="";
	document.forms["editresource"]["lname"].value="";
	document.forms["editresource"]["email"].value="";
	document.forms["editresource"]["uname"].value="";
	document.forms["editresource"]["pass"].value="";
	document.forms["editresource"]["confirmpass"].value="";

	
	
	
return false; 
} 

</script>
</html>