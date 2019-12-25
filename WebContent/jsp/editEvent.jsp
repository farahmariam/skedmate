<%@ page import ="java.sql.*" %>
<%@ page import ="java.util.*" %>
<%@ page import ="javax.servlet.http.HttpSession" %>
<%@ page import ="trilane.CommonDatabase" %>

<%@ page import ="com.google.gson.Gson" %>



 <%@ include file="common.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Schedule</title>
<link rel="stylesheet" href="../css/scheduleStyles.css">
</head>


<%
	

	
	ServletContext context = request.getServletContext();

	
	String loginUrl = context.getInitParameter("login-url");
	
	boolean isSaturday=false;;
	boolean isSunday=false;
	boolean isHoliday=false;
	
	int totalResources = -1;
	int day = -1;
	int month = -1;
	int year= -1; 
	String monthName = "";
	String dayName = "";
	int offShiftCount = 0;
	int dayOfWeek=-1;
	int onlineCount = -1;
	int userTechId = -1;
	int statusId = -1;
	int eventId = -1;
	
	int is_working=1;
	int not_working=0;
	
	int totalResourcesNotWorkingFromTeam = 0;
	int totalResourcesWorkingFromTeam = 0;
	int minimumResourceLimit = 0;
	
	
	
	if ((session.getAttribute("loginname") == null) || (session.getAttribute("loginname") == ""))
	{
	%>
       
	<h3 class="settingText">You are not logged in!</h3> <br/>
	<a href="<%=loginUrl %>" class="three" >Please Login..</a>
	<%
	}
	else 
	{
		eventId =  Integer.parseInt(request.getParameter("eventid"));
		int userId = Integer.parseInt(request.getParameter("userid"));
		String userFullName = request.getParameter("fullname");
		statusId = Integer.parseInt(request.getParameter("statusid"));
		String statusName = request.getParameter("statusname");
		isSaturday = Boolean.parseBoolean(request.getParameter("isSat"));
		isSunday = Boolean.parseBoolean(request.getParameter("isSun"));
		dayOfWeek = Integer.parseInt(request.getParameter("dayofweek"));
		String comment = request.getParameter("comment");
		day = Integer.parseInt(request.getParameter("day"));
		month = Integer.parseInt(request.getParameter("month"));
		year = Integer.parseInt(request.getParameter("year"));
		
		
		//to find out  options of status
		int statusForDay = -1;
		String statusNameForDay = "";
			
		String teamName="";
		
				
				
				
		
		
		Connection connection = null;
		Statement stMinUsers = null;
		ResultSet rsMinUsers = null;
		
		Statement stStatus = null;
		ResultSet rsStatus = null;
		
		Statement stStatusName = null;
		ResultSet rsStatusName = null;
		
		Statement stTotalUsers = null;
		ResultSet rsTotalUsers = null;
		
		Statement stUserTechnology = null;
		ResultSet rsUserTechnology = null;
		
		//new
		Statement stEvent = null;
		ResultSet rsEvent = null;
		
		Statement stCheckStatusWorking = null; 
		ResultSet rsCheckStatusWorking = null;
		
		Statement stTeam = null; 
		ResultSet rsTeam = null; 
		
		Statement stTeamName = null; 
		ResultSet rsTeamName = null; 
		
		
		ArrayList<trilane.StatusObject> statusList=null;
		
		
		
		
		
		
		//the day_id to query limitcatxr
		int day_id = -1;
		
		if(dayOfWeek ==1)
		{
			day_id = 1;
			dayName = "Sunday";
		}
		else if(dayOfWeek ==2)
		{
			day_id = 2;
			dayName = "Monday";
		}
		else if(dayOfWeek ==3)
		{
			day_id = 3;
			dayName = "Tuesday";
		}
		else if(dayOfWeek ==4)
		{
			day_id = 4;
			dayName = "Wednesday";
		}
		else if(dayOfWeek ==5)
		{
			day_id = 5;
			dayName = "Thursday";
		}
		else if(dayOfWeek ==6)
		{
			day_id = 6;
			dayName = "Friday";
		}
		else if(dayOfWeek ==7)
		{
			day_id = 7;
			dayName = "Saturday";
		}
		
		
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			//String databaseUrl = context.getInitParameter("data-url");
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			
			
			
			//find which technology the user works in
			stUserTechnology = connection.createStatement();
			String userTechQuery = "select tech_id from users where user_id = " + userId;
			rsUserTechnology = stUserTechnology.executeQuery(userTechQuery);
			if(rsUserTechnology.next())
			{
				userTechId = rsUserTechnology.getInt(1);
			}
			
			
			
			
			//Get Team Column name to query public_holiday or team_day limits table to get min limits
			stTeamName = connection.createStatement();
			String teamNameQuery = "select tech_name from technology where tech_id=" + userTechId;
			rsTeamName = stTeamName.executeQuery(teamNameQuery);
			if(rsTeamName.next())
			{
				teamName = rsTeamName.getString(1);
			}
			System.out.println("team name:  " + teamName);

			CommonDatabase obj = new CommonDatabase();
			String colName = obj.getTeamColumnForTable(teamName);
			//String teamWithoutSpaces = teamName.replaceAll("\\s+","");
			//String colName = teamWithoutSpaces.trim().toLowerCase() + "_limit";
			
			
			
			
			
			
			
			//check if it is a public holiday. If so, get the limits from holiday table.
			//If not holiday, then get limits from limitcatxr table.
			
			
			isHoliday = checkPublicHoliday(day,month,year,context,session);
			if(isHoliday)
			{
				day_id=8;
				dayName = "Public Holiday";
			}
			String minLimitsQuery="";
			
			if(isHoliday)
			{
				//if it is a public holiday, get min limits from public_holiday table 
				
				minLimitsQuery = "select * from public_holiday where day=" + day + " and month=" + month + " and year=" + year;
			
				
			}
			else
			{
				
				
				//getting the minimum resources required for the day from team_day_limits table
				minLimitsQuery = "select * from team_day_limits where day_id=" + day_id;
				
				
			}
			stMinUsers= connection.createStatement();
			rsMinUsers = stMinUsers.executeQuery(minLimitsQuery );
			
			
			if (rsMinUsers.next()) 
			{
				//get the min resource limit
				minimumResourceLimit = 	rsMinUsers.getInt(colName);
				
			}
			System.out.println("the min limit:" + minimumResourceLimit );
			
			
			
			
			
			//Find out total resources in the selected resources's team
			stTotalUsers = connection.createStatement();
			rsTotalUsers = stTotalUsers.executeQuery("select count(*) from users where tech_id=" +  userTechId);
			if (rsTotalUsers.next())
			{
				totalResources =  rsTotalUsers.getInt(1);
			}
			
		
			//Find out number of resources from the selected person's team not working on that day
			
			String eventQuery = "select * from events where day=" + day + " and month=" + month + " and year =" + year ;
			stEvent = connection.createStatement();
			rsEvent = stEvent.executeQuery(eventQuery );
			int eventStatusId = -1;
			int checkStatus=-1;
			int eventUserId=-1;
			int eventUserTechId=-1;
			 
			while (rsEvent.next())
			{
				eventStatusId =rsEvent.getInt("status_id");
				eventUserId = rsEvent.getInt("user_id");
				//check if this status is working or not
				stCheckStatusWorking  = connection.createStatement();
				String checkStatusWorking = "select is_working from status where status_id=" + eventStatusId;
				rsCheckStatusWorking = stCheckStatusWorking.executeQuery(checkStatusWorking);
				if(rsCheckStatusWorking.next())
				{
					checkStatus = rsCheckStatusWorking.getInt("is_working");
					if (checkStatus==not_working)
					{
						//find out which team this particular resource of event belongs to
						stTeam = connection.createStatement();
						String teamQuery = "select tech_id from users where user_id=" + eventUserId;
						rsTeam = stTeam.executeQuery(teamQuery);
						if(rsTeam.next())
						{
							eventUserTechId = rsTeam.getInt(1);
						}
						//if this team is same as the selected users team, then increse the counter of people not working from that team by 1
						if(eventUserTechId==userTechId)
						{
							totalResourcesNotWorkingFromTeam++;
						}
						
					}
				}
				
			}
			
			//find total resources working from the team = total resources from team- total resources from team not working
			totalResourcesWorkingFromTeam = totalResources - totalResourcesNotWorkingFromTeam;
			

			
			//Set month name
			if(month==0)
			{
				monthName = "January";	
			}
			else if(month==1)
			{
				monthName = "February";	
			}
			else if(month==2)
			{
				monthName = "March";	
			}
			else if(month==3)
			{
				monthName = "April";	
			}
			else if(month==4)
			{
				monthName = "May";	
			}
			else if(month==5)
			{
				monthName = "June";	
			}
			else if(month==6)
			{
				monthName = "July";	
			}
			else if(month==7)
			{
				monthName = "August";	
			}
			else if(month==8)
			{
				monthName = "September";	
			}
			else if(month==9)
			{
				monthName = "October";	
			}
			else if(month==10)
			{
				monthName = "November";	
			}
			else if(month==11)
			{
				monthName = "December";	
			}
			
			
			
			
			
			%>
			<body style="background-color:#f7f0f7">
			<img src="../images/skedmate3.jpg"  style="width:1500px;height:180px;">
			<form name="edit" method="post" action="${pageContext.request.contextPath}/SaveEditedEvent"   >
            <center>
            <table style="background-color:white" border="1" width="50%" cellpadding="5">
                <thead>
                    <tr>
                        <th class="backcolourpurple bold8" colspan="2">Edit Status of <%= userFullName %> on <%=monthName %>  <%=day %> , <%= year %> (<%=dayName %>)</th>
                    </tr>
                </thead>
                <tbody>
					 <tr>
                        <td class="index">Resource Name</td>
                        <td><input type="text" name="resourcename" value="<%=userFullName %>" readonly/></td>
                    </tr>
					<tr>
                        <td class="index">Status</td>
                        <td><select name="status" onchange="checkConditions()" ">
			<% 
			CommonDatabase objCommon  = new CommonDatabase();
			statusList = objCommon.getAllStatus(context, session);
			
			
			
			//Get the status combo options for the particular day
			for(int loop=0; loop<statusList.size(); loop++)
			{
				trilane.StatusObject statusObject = statusList.get(loop);
				statusForDay = statusObject.getStatusId();
				statusNameForDay = statusObject.getStatusName();
				
				
				if(statusId == statusForDay)
				{
						
					
					
		%>
					<option value="<%=statusForDay  %>" selected><%=statusNameForDay  %> </option>
		
		<% 			
				}
				else
				{
						
						
		%>
		
					<option value="<%=statusForDay  %>" ><%=statusNameForDay  %> </option>
		
		<% 
				}
					
			}
			
			
		%>
						</select>
						<span id="statusmsg"></span>
						</td>
						</tr>
						
						<tr>
                        <td class="index">Comment</td>
                        <td><textarea name="comment" rows="10" cols="30"><%=comment %></textarea></td>
                    	</tr>

						<tr>
						<td class="index"><input type="submit" value="Save  Status"  />&nbsp <input type="button" value="Go back to Calendar!" onClick="goToCalendar()" class="button"/></td>
						<td class="index"><input type="reset" value="Reset" /> &nbsp <input type="button" value="Clear" onClick="clearFields()" class="button"/>  </td>
						
						</tr>
						<input type="hidden" name="eventId" value="<%=eventId%>"/>
						<input type="hidden" name="month" value="<%=month%>"/>
						<input type="hidden" name="year" value="<%=year%>"/>
						<input type="hidden" name="username" value="<%=userFullName%>"/>
						<input type="hidden" name="day" value="<%=day%>"/>
		
		
		
		<% 
			
			
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
			 if (rsUserTechnology != null) try { rsUserTechnology.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (stUserTechnology != null) try { stUserTechnology.close(); } catch (SQLException e) {e.printStackTrace();}
			
			 if (stEvent != null) try { stEvent.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (rsEvent != null) try { rsEvent.close(); } catch (SQLException e) {e.printStackTrace();}
			
			
			 if (stCheckStatusWorking != null) try { stCheckStatusWorking.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (rsCheckStatusWorking != null) try { rsCheckStatusWorking.close(); } catch (SQLException e) {e.printStackTrace();}
			
			 if (stTeam != null) try { stTeam.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (rsTeam != null) try { rsTeam.close(); } catch (SQLException e) {e.printStackTrace();}
			 
			 if (stTeamName != null) try { stTeamName.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (rsTeamName != null) try { rsTeamName.close(); } catch (SQLException e) {e.printStackTrace();}
			 
			 if (rsTotalUsers != null) try { rsTotalUsers.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (stTotalUsers != null) try { stTotalUsers.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (rsStatusName != null) try { rsStatusName.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (stStatusName != null) try { stStatusName.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (rsStatus != null) try { rsStatus.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (stStatus != null) try { stStatus.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (rsMinUsers != null) try { rsMinUsers.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (stMinUsers != null) try { stMinUsers.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}

		}
		
		
	
%>

	
</body>

<%
	}

%>


<Script> 

function checkConditions()
{
	var selValue= document.forms["edit"]["status"].value;
	clearSpan('statusmsg');
	
	
	
	//get if the selected status is a working/non working status
	//ajax method to get working or not 
	
	var NewStatusWorking="";
	var xmlhttp = new XMLHttpRequest();
	   
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
	        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();
	        	NewStatusWorking=trimmedResponse;
	        	
	        }
	        
	    };
	    try
	    {
	    	var posturl = "status_id=" + selValue ;
		    
			 
			 xmlhttp.open("POST", "${pageContext.request.contextPath}/GetStatusWorkingOrNot", false);
			
			 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			 xmlhttp.send(posturl);
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }
	
	
	
	
	
	
	
	
	//min limitof resources required for the day
	var minLimit = <%= minimumResourceLimit  %> ;
	
	var NumResourcesWorking = <%= totalResourcesWorkingFromTeam %>;
	
	var isSaturday = <%=isSaturday %>;
	var isSunday = <%=isSunday %>;
	var isPublicHoliday = <%= isHoliday %>;
	
	//id denoting which day of week
	var dayOfWeek = <%=dayOfWeek %>
	
	//day, month, year
	var day = <%= day %>;
	var month = <%= month %>;
	var monthName = "<%=monthName %>" ;
	var year = <%= year %>;
	
	//original status id
	var currentStatus = <%= statusId %>;
	
	//The user's technology team
	var userTechnology = <%= userTechId%>;
	
	//if the new selected status is a working one, let the change be made without any checking.
	//if not working status, then check if min limits condition is satisfied
	
	if(NewStatusWorking=="0")
	{
		
		if(!(NumResourcesWorking>minLimit))
		{
			document.getElementById("statusmsg").style.color = "red";
	        document.getElementById("statusmsg").innerHTML = "Cannot change status!No of people working in this team will not satisfy the minimum resource limits for the day";
	        document.forms["edit"]["status"].value = currentStatus;
	       
		}
	
	}
	
	
}






function clearFields()
{ 
	
	document.forms["edit"]["comment"].value="";
	
	
	
return false; 
} 

function goToCalendar()
{	
	
	window.open('showCalendar.jsp', '_self');
	
}





function clearSpan(elementId)
{
	document.getElementById(elementId).innerHTML ="";	
}




</script>
</html>