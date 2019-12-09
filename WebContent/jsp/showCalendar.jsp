<%@ page  language="java" import="java.util.*,java.text.*"%>
<%@ page import ="java.sql.Connection" %>
<%@ page import ="java.sql.SQLException" %>
<%@ page import ="java.sql.ResultSet" %>
<%@ page import ="java.sql.Statement" %>
<%@ page import ="java.sql.DriverManager" %>
<%@ page import ="trilane.CommonDatabase" %>
<%@ page import ="trilane.StatusObject" %>

 <%@ include file="common.jsp" %>

<%
int iYear=0;
int iMonth=-1;

if(request.getParameter("iYear")!=null && request.getParameter("iMonth")!=null)
{	if(request.getParameter("iYear")!="" && request.getParameter("iMonth")!="")
	{ 		
			try
			{
				iYear=nullIntconv(request.getParameter("iYear"));
				iMonth=nullIntconv(request.getParameter("iMonth"));
			}
			catch(Exception ex)
			{
			ex.printStackTrace();
			
			}
	}
}

if(session.getAttribute("month")!=null && session.getAttribute("year")!=null)
{
	iMonth = (Integer)session.getAttribute("month");
	iYear = (Integer)session.getAttribute("year");
	
	session.setAttribute("month", null);
	session.setAttribute("year", null);
}


//Get current day, month, year
 Calendar ca = new GregorianCalendar();
 int iTDay=ca.get(Calendar.DATE);
 int iTYear=ca.get(Calendar.YEAR);
 int iTMonth=ca.get(Calendar.MONTH);

 //if no input, show todays date in calendar
 if(iYear==0)
 {
	  iYear=iTYear;
	  iMonth=iTMonth;
 }

 
 //getting no of days, weekstartday and total weeks of selected month, year
 GregorianCalendar cal = new GregorianCalendar (iYear, iMonth, 1); 

 int days=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
 int weekStartDay=cal.get(Calendar.DAY_OF_WEEK);
 
 cal = new GregorianCalendar (iYear, iMonth, days); 
 int iTotalweeks=cal.get(Calendar.WEEK_OF_MONTH);
  
%>




<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to the shift schedule!</title>
<link rel="stylesheet" href="../css/scheduleStyles.css">
<script>
function goTo()
{
	
	
	document.forms["frm"].submit();
}
</script>
<style>
table.fixed {table-layout:fixed; width:190px;}/*Setting the table width is important!*/
table.fixed td {overflow:hidden;}/*Hide text outside the cell.*/
table.fixed td:nth-of-type(1) {width:20px;}/*Setting the width of column 1.*/
table.fixed td:nth-of-type(2) {width:30px;}/*Setting the width of column 2.*/
table.fixed td:nth-of-type(3) {width:40px;}/*Setting the width of column 3.*/

</style>



</head>



<%

ServletContext context = request.getServletContext();
String loginUrl = context.getInitParameter("login-url");

int userId = -1;
String userFullNameFromSession = "";
boolean isAdmin=false;
String loginName = "";
String companyLoginId = "";

if ((session.getAttribute("loginname") == null) || (session.getAttribute("loginname") == ""))
{
	
	
%>

	<h3 class="settingText">You are not logged in!</h3> <br/>
	<a href="<%=loginUrl %>" class="three" >Please Login..</a>


<%
}
else 
{
	userId =(Integer)session.getAttribute("userid");
	userFullNameFromSession = (String)session.getAttribute("username"); 
	companyLoginId = (String)session.getAttribute("companyLoginId"); 
	
	loginName = (String)session.getAttribute("loginname");
	Connection conn = null;
	Statement eventStatement =null;
	Statement userStatement = null;
	Statement statusStatement = null;
	ResultSet eventRs  = null;
	ResultSet userRs  = null;
	ResultSet statusRs  = null;
	
	Statement stColour = null;
	ResultSet rsColour = null;
	String legendStyleClass = "";
	
	isAdmin = checkIfAdmin(userId,context,session);
	
	
	if(userFullNameFromSession.trim().isEmpty())
	{
		userFullNameFromSession=loginName;
		
	}
	

%>
<body  >
<form name="frm" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	    <td width="90%" class="two">
	     <h3 class="indexSettings">Welcome, <%=userFullNameFromSession  %>.</h3>
	    </td>
	    
	    
	    
	    <td>
	    <select name="adminmenu" id ="adminmenu" onchange="goToNewPage()" class="options">
	    
	    <option value=""  selected> <h4 > Actions</h4> </option>
	    <option value="resourceDetails.jsp?userid=<%= userId %>&month=<%=iMonth %>&year=<%=iYear %>"> Manage Account </option>
	    <option value="LongStatusEntryResource.jsp?userid=<%= userId %>&&userName=<%=userFullNameFromSession%>&month=<%=iMonth %>&year=<%=iYear %>"> Edit Multiple days </option>
	  
	    
	    <option value="statusReport.jsp">ScheduleIT View </option>
	    <option value="Reports.jsp">View Reports </option>
	    
	    
	    <%
	    if(isAdmin)
	    {
	    	
	    %>
	     <option value="settings.jsp?month=<%=iMonth %>&year=<%=iYear %>"> Settings </option>
	     <option value="showLog.jsp">Show Log </option>
	    <% 
	    }
	    	    	
	    %>
	    <option value="logout.jsp"> Log Out </option>
	    
	    </select>
	    </td>
    </tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
    
      <tr>
        <td class="settingText" width="4%" >Year</td>
        <td width="20%">
		<select name="iYear" id ="iYear" onchange="goTo()" >
        <%
		// start year and end year in combo box to change year in calendar
	    for(int iy=iTYear-2;iy<=iTYear+70;iy++)
		{
		  if(iy==iYear)
		  {
		    %>
          <option value="<%=iy%>" selected="selected"><%=iy%></option>
          <%
		  }
		  else
		  {
		    %>
          <option value="<%=iy%>"><%=iy%></option>
          <%
		  }
		}
	   %>
        </select></td>
        <td align="right"> <input type="button" class="button1" value="Previous" onClick="previous()" /></td>
        <td width="50%" class="settingText" align="center"><h3><%=new SimpleDateFormat("MMMM").format(new java.util.Date(2008,iMonth,01))%> <%=iYear%></h3></td>
        <td width="20%" align="left"> <input  type="button" class="button1" value="Next" onClick="next()" /></td>
        <td class="settingText" width="4%">Month</td>
        <td width="20%">
		<select name="iMonth" onchange="goTo()" >
        <%
		// print month in combo box to change month in calendar
	    for(int im=0;im<=11;im++)
		{
		  if(im==iMonth)
		  {
	     %>
          <option value="<%=im%>" selected="selected"><%=new SimpleDateFormat("MMMM").format(new java.util.Date(2008,im,01))%></option>
          <%
		  }
		  else
		  {
		    %>
          <option value="<%=im%>"><%=new SimpleDateFormat("MMMM").format(new java.util.Date(2008,im,01))%></option>
          <%
		  }
		}
	   %>
        </select></td>
      </tr>
    </table></td>
  </tr>
  <tr>
  <div id="calendarId" >
    <td><table align="center" border="1" cellpadding="3" cellspacing="0" width="100%">
      <tbody>
        <tr>
          <th bgcolor="#c6bfc2">Sun</th>
          <th bgcolor="#c6bfc2">Mon</th>
          <th bgcolor="#c6bfc2">Tue</th>
          <th bgcolor="#c6bfc2">Wed</th>
          <th bgcolor="#c6bfc2">Thu</th>
          <th bgcolor="#c6bfc2">Fri</th>
          <th bgcolor="#c6bfc2">Sat</th>
        </tr>
        <%
        int cnt =1;
        
        
        
      	
        
        
        
        
        for(int i=1;i<=iTotalweeks;i++)
        {
		%>
        <tr>
          <% 
		      	
		        for(int j=1;j<=7;j++)
		        {
		        	cal.set(iYear, iMonth, cnt-weekStartDay+1);

		          	boolean isSaturday = false;
		          	boolean isSunday = false;
		          	boolean weekday = false;
		          	boolean weekend = false;
		          	String dayType="";
		          	int selectedDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		          	
		          	 if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) 
		          	 {
		          		 
		          		 isSunday = true;
		          		 weekend = true;
		          		
		          	 }
		          	 else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
		          	 {
		          		 isSaturday = true;
		          		 weekend = true;
		          		
		          	 }
		          	 else
		          	 {
		          		 weekday = true;
		          		
		          	 }
		          	
		          	String cellColour = "";
		          	
		          	if(weekend)
		          	{
		          		cellColour = "cellWeekend";
		          		dayType="Weekend!";
		          	}
		        	else
		          	{
		          		cellColour = "";
		          		dayType="Weekday!";
		          	}
		          	
		          	
		          	//if public holiday, give another colour
		          	if(checkPublicHoliday(cnt-weekStartDay+1,iMonth,iYear,context,session))
		          	{
		          		cellColour = "cellPublicHoliday";
		          		dayType="Public Holiday";
		          	}
	        	
		        if(cnt<weekStartDay || (cnt-weekStartDay+1)>days)
		        {
		         %>
                <td align="center" height="110" >&nbsp;</td>
               <% 
		        }
		        else
		        {
		        	
		         %>
		         
		         <td title="<%=dayType %>" align="center" height="110" class="<%=cellColour %>" >
                 
			         <table class="fixed" id="table_<%=(cnt-weekStartDay+1)%>" border="0" cellpadding="0" cellspacing="0" width="100%" >
			         
				         <th  class="addth" title="Click here to add new entry.."  valign="top" id="<%=(cnt-weekStartDay+1)%>" onclick="addNew(this.id,<%=isSaturday%>, <%=isSunday%>,<%= selectedDayOfWeek %>)">
				         	<%=(cnt-weekStartDay+1)%>
				         </th>
			         <%
			         try 
			 			{	
			        	int day = cnt-weekStartDay+1;
			        	Class.forName("com.mysql.jdbc.Driver");
			        	
			        	//String databaseUrl = context.getInitParameter("data-url");
			        	String databaseUrl =(String) session.getAttribute("data-url-schedule");
			    		String databaseUser = context.getInitParameter("data-user");
			    		String databasePwd = context.getInitParameter("data-pwd");
			        	
						conn = DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
						
			 		
			 			
			 			
			 			
			 			eventStatement = conn.createStatement();
			 			
			 			String eventQuery = "select * from events where day=" + day + " and month=" + iMonth + " and year =" + iYear ;
						eventRs = eventStatement.executeQuery(eventQuery);
						
						int eventId = -1;
						int userScheduleId = -1;
						int statusId = -1;
						String comment = "";
						String styleclass="";
						String tdStyleClass="";
						String statusColour = "";
						
						while(eventRs.next()) 
						{
							
							eventId = eventRs.getInt("event_id");
							userScheduleId = eventRs.getInt("user_id");
			         		statusId = eventRs.getInt("status_id");
			         		comment = eventRs.getString("comment");
			         		
			         		//find the colour for the status from the status table
			         		stColour = conn.createStatement();
			         		String colourQuery = "select status_colour from status where status_id= " + statusId;
			         		rsColour = stColour.executeQuery(colourQuery);
			         		if(rsColour.next())
			         		{
			         			statusColour = 	rsColour.getString(1);
			         			
			         		}
			         		//styles
			         		styleclass="color:black;font-size:8pt; font-family:verdana,arial,serif;background:" + statusColour + ";" ;
			         		tdStyleClass = "background:" + statusColour + ";";
			         		
			         		
			         		String userFirstName ="";
			         		String userLastName ="";
			         		String userEventLoginName = "";
			         		userStatement = conn.createStatement();
			         		userRs = userStatement.executeQuery("select fname,lname,uname from users where user_id=" + userScheduleId);
			         		if(userRs.next())
							{
								userFirstName = userRs.getString("fname");
								userLastName = userRs.getString("lname");
								userEventLoginName = userRs.getString("uname");
							}
			         		
							String status = "";
							statusStatement = conn.createStatement();
							statusRs = statusStatement.executeQuery("select status_name from status where status_id=" + statusId);
							if(statusRs.next())
							{
								status = statusRs.getString("status_name");
								
							}
							String userFullName="";
							if(userFirstName!=null && userLastName!=null)
							{
			         			userFullName = userFirstName + " " + userLastName;
							}
							else
							{
								//userFullName= loginName;
								userFullName= userEventLoginName;
							}
							if(comment==null)
							{
								comment="";
							}
							
							
						
			         %>
			         	<tr>
			         		<td  width="100%" style="<%= tdStyleClass %>">
			         		
			         		<%
			         		if(userId==userScheduleId)
			         		{
			         			
			         				
			         			
			         		
			         		%>
			         			
			         			<a href="#" style="<%=styleclass %>" onClick="edit('<%= eventId %>', '<%= userScheduleId %>',' <%= userFullName %>', '<%= statusId %>', '<%= status %>', '<%= isSaturday %>', '<%= isSunday %>', '<%= selectedDayOfWeek %>', '<%= comment %>', '<%= day %>', '<%= iMonth %>', '<%= iYear %>');return false;"  title="Click here to edit <%=userFullName %>:<%= status %>   <%=comment %>" > <%=userFullName %> </a> 
			         		 <%
			         			
			         		
			         			
			         		}
			         		else
			         		{
			         		
			         		%>
			         			<a href="#" style="<%=styleclass %>"  title="<%= userFullName %>:<%= status %>    <%=comment %>"> <%=userFullName %> </a>
			         		<%
			         		}
			         		%>
			         		
			         		
			         		</td>
			         		
			         		
			         	</tr>
			         
			         <%
			         
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
			        	 if (statusRs != null) try { statusRs.close(); } catch (SQLException e) {e.printStackTrace();}
						if (userRs != null) try { userRs.close(); } catch (SQLException e) {e.printStackTrace();}
						if (eventRs != null) try { eventRs.close(); } catch (SQLException e) {e.printStackTrace();}
						if (statusStatement != null) try { statusStatement.close(); } catch (SQLException e) {e.printStackTrace();}
						if (userStatement != null) try { userStatement.close(); } catch (SQLException e) {e.printStackTrace();}
						if (eventStatement != null) try { eventStatement.close(); } catch (SQLException e) {e.printStackTrace();}
						if (conn != null) try { conn.close(); } catch (SQLException e) {e.printStackTrace();}
 	
			         }
			         
			         
			       
			         %>
			                       
	                </table>
                
                </td>
           <%
           
           	
		        }
		        cnt++;
		      }
	        %>
        </tr>
        <%
        
        
	    }
       
     
        
	    %>
      </tbody>
    </table></td>
   </div>
  </tr>
</table>



<table   align="center" border="1" cellpadding="3" cellspacing="0" width="100%">
<tr>
<%
	
	ArrayList<StatusObject> statusList= null;

	CommonDatabase objCommon  = new CommonDatabase();
	statusList = objCommon.getAllStatus(context, session);
	
	//int width = (int)Math.ceil(100 / statusList.size());
	String statusName="";
	String statusColour="";


	//Get the status combo options for the particular day
	for(int loop=0; loop<statusList.size(); loop++)
	{
		StatusObject statusObject = statusList.get(loop);
		
		statusName = statusObject.getStatusName();
		statusColour = statusObject.getStatusColor();
	
		legendStyleClass = "background:" + statusColour + ";" ;
		
%>

		 <th width="15%" style=<%= legendStyleClass %> ><%=statusName.toUpperCase() %></th>

<% 
		
	}

%>
</tr>


</table>
</form>
</body>
<%

}
%>










<script type="text/javascript">

function edit(eventid, userid, fullname, statusid, statusname,isSat, isSun, dayofweek, comment, day, month, year)
{
	
	var xmlhttp;
	var response="";
	
	if (window.XMLHttpRequest) 
	{
		   xmlhttp = new XMLHttpRequest();
	} else 
	{
		    // code for IE6, IE5
		    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	
		
		 xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
	        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	
	        	response = trimmedResponse;
	            
	           
	        }
		};
		
		try
	    {
		 
		 var posturl = "year=" + year + "&&month=" + month + "&&day=" + day;
	    
		 
		 xmlhttp.open("POST", "${pageContext.request.contextPath}/CheckEditPossible", false);
		
		 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xmlhttp.send(posturl);
		   
		   
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }	
	    
	    var editPossible  = "Can Edit";
    	var editNotPossible2Months = "Within freeze period";
    	var editNotPossibleEventOver="Event Over";
	
    	if(response.toLowerCase()  == editNotPossible2Months.toLowerCase())
    	{
    		alert("The selected date is within the freeze period. You cannot edit status of this event. ");
			return false;
		}
    	else if(response.toLowerCase()  == editNotPossibleEventOver.toLowerCase())
   		{
    		alert("The selected event is Over! It cannot be edited. ");
			return false;
   		}
		else if(response.toLowerCase()  == editPossible.toLowerCase())
		{
			 window.open('editEvent.jsp?eventid=' + eventid + '&userid='+userid + '&fullname=' + fullname + '&statusid=' + statusid + '&statusname=' + statusname + '&isSat=' + isSat + '&isSun=' + isSun + '&dayofweek=' + dayofweek + '&comment=' + comment + '&day=' + day + '&month=' + month + '&year=' + year,  "_self" , width="300", height="250");  

		}
	
	
	
	
}




function addNew(id, issaturday, issunday, dayofweek)
{
	
	
	var year = <%=iYear%>;
	var month = <%=iMonth%>;
	var date = id;
	var xmlhttp;
	
	if (window.XMLHttpRequest) 
	{
		   xmlhttp = new XMLHttpRequest();
	} else 
	{
		    // code for IE6, IE5
		    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	//ajax to check if the user's team has been updated
	
	
	var teamResponse="";
	var user_id= <%=userId %>;
	 xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
	        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	
	        	teamResponse = trimmedResponse;
	            
	           
	        }
		};
		
		try
	    {
		 
		 var posturl = "user_id=" + user_id ;
	    
		 
		 xmlhttp.open("POST", "${pageContext.request.contextPath}/CheckUserTeamUpdated", false);
		
		 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xmlhttp.send(posturl);
		   
		   
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }	
	    
	    var teamUpdated  = "Team updated";
    	var teamNotUpdated = "No team";
    	
	
    	if(teamResponse.toLowerCase()  == teamNotUpdated.toLowerCase())
    	{
    		alert("Please update your team details before you can start using the calendar. ");
			return false;
			teamNotUpdated="";
		}
    	
	
	
	
	
	
	
	//ajax tocheck if event already over or within 2 months
	
	
	
		var response="";
		 xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
	        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	
	        	response = trimmedResponse;
	            
	           
	        }
		};
		
		try
	    {
		 
		 var posturl = "year=" + year + "&&month=" + month + "&&day=" + date;
	    
		 
		 xmlhttp.open("POST", "${pageContext.request.contextPath}/CheckEditPossible", false);
		
		 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xmlhttp.send(posturl);
		   
		   
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }	
	    
	    var editPossible  = "Can Edit";
    	var editNotPossibleInFreezePeriod = "Within freeze period";
    	var editNotPossibleEventOver="Event Over";
	
    	if(response.toLowerCase()  == editNotPossibleInFreezePeriod.toLowerCase())
    	{
    		alert("The selected date is within the freeze period. You cannot add this event. ");
			return false;
		}
    	else if(response.toLowerCase()  == editNotPossibleEventOver.toLowerCase())
   		{
    		alert("The selected day is Over! It cannot be edited. ");
			return false;
   		}
	
	
	
		
	
	
	//ajax to add new user with default status
		var url = "add.jsp?userid=<%=userId %>&&sat=" + issaturday + "&&sun=" + issunday + "&&year=" + year + "&&month=" + month + "&&date=" + date;
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
	        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	var added  = "New User added";
	        	var exists = "User event exists!";
	            if(trimmedResponse.toLowerCase()  == added.toLowerCase())
	            	{
	               		goTo();
	            	}
	            else if(trimmedResponse.toLowerCase()  == exists.toLowerCase())
	            	{
	            		alert("Event exists!")
	            	}
	            
	           
	        }
		};
		
		//if username from session null or empty string , set name to login name
		var username ="<%=userFullNameFromSession%>";
		
		if(username.trim()=="" || username==null)
			{
			username ="<%= loginName %>" ;
			}
		 
		
	
	
	 try
	    {
		 
		 var posturl = "userid=<%=userId %>&&username=" + username + "&&sat=" + issaturday + "&&sun=" + issunday + "&&year=" + year + "&&month=" + month + "&&date=" + date;
	    
		 
		 xmlhttp.open("POST", "${pageContext.request.contextPath}/AddResource", true);
		// xmlhttp.open("POST", "add.jsp", true);
		 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xmlhttp.send(posturl);
		   
		   
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }	
	
	
}
	
	




function previous()
{
	var currentYear = <%=iYear %>;
	var currentMonth = <%=iMonth %>;
	
	
	if(currentMonth==0)
		{
		currentMonth = 11;
		currentYear = currentYear-1;
		
		
		
		}
	else
		{
		currentMonth = currentMonth-1;
		}
	
	
	document.forms["frm"]["iMonth"].value = currentMonth;
	document.forms["frm"]["iYear"].value = currentYear;
	
	goTo();
}

function next()
{
	
	var currentYear = <%=iYear %>;
	
	
	var currentMonth = <%=iMonth %>;
	
	
	if(currentMonth==11)
		{
		currentMonth = 0;
		currentYear = currentYear + 1;
		}
	else
		{
		currentMonth = currentMonth + 1;
		}
	document.forms["frm"]["iMonth"].value = currentMonth;
	document.forms["frm"]["iYear"].value = currentYear;
	
	goTo();
	
	
}

function checkAdmin()
{
	var settingLink =  document.getElementById['settings'];

	if(<%=isAdmin %>)
		{
		return true;

		}
	else
		{
		return false;
		}
	
}

function goToNewPage()
{
	var dropdown = document.forms["frm"]["adminmenu"];
	var url = dropdown.value;
	 if (url != "")
	 {
		 if(url=="statusReport.jsp" || url=="Reports.jsp" || url=="showLog.jsp")
			 {
			 window.open(url,'_blank');
			 dropdown.value="";
			 }
		 else
			 {
			 	window.open(url,'_self');
			 }
	 	
	 }
	

	
	}

function selectoption()
{
	document.forms["frm"]["adminmenu"].value="";
	
}


</script>





</html>