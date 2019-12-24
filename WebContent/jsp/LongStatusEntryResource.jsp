<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="java.util.*, java.text.*,  java.sql.*, trilane.StatusObject, trilane.CommonDatabase" %> 
<%@ include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Long Status Entry by User</title>
<link rel="stylesheet" href="../css/scheduleStyles.css">

</head>


<body style="background-color:#f7f0f7">

<img src="../images/skedmate3.jpg"  style="width:1500px;height:180px;">


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
	
int	userId = Integer.parseInt(request.getParameter("userid"));

String userName = request.getParameter("userName");
int month = Integer.parseInt(request.getParameter("month"));
int year = Integer.parseInt(request.getParameter("year"));


session.setAttribute("month", month);
session.setAttribute("year", year);



%>






<div class="containerwhite">

    <div class="header  ">
      <h3 class="settingHeader" >LONG STATUS ENTRY</h3> 
    </div>
    
    <tbody>
    
  	<div  id="formdiv"> 
  	
  	<form name="resourceform" method="post" action="#"  onsubmit="saveStatus();return false;" >
  	
  	
    <table style="background-color:white" border="0" width="100%" cellpadding="5"> 
     
		
		  
		<tr>
						
				<td class="index"> Status </td>
				<td><select name="resourcestatus" id ="resourcestatus" onchange="clearSpan('statusspan')"> 
				<option value="" selected > <b> Select Status</b> </option>
				
			<% 
				ArrayList<StatusObject> statusList=null;
				CommonDatabase objCommon  = new CommonDatabase();
				statusList = objCommon.getAllStatus(context, session);
				int statusForDay=-1;
				String statusNameForDay="";
				
				
				//Get the status combo options for the particular day
				for(int loop=0; loop<statusList.size(); loop++)
				{
					StatusObject statusObject = statusList.get(loop);
					statusForDay = statusObject.getStatusId();
					statusNameForDay = statusObject.getStatusName();
				%>
				
				<option value="<%=statusForDay  %>" ><%=statusNameForDay  %> </option>
			
		
			<% 
				}
				
			%>
			
				</select>
				</td>
					
					
		</tr>
		
		<tr>
                        <td class="index">Comment</td>
                        <td><textarea id="comment" name="comment" rows="10" cols="30"></textarea></td>
       </tr>
		</table>
		
		<div id="dateinputs">
			<div id="fromdate">
			
				 <table style="background-color:white" border="0" width="100%" cellpadding="5"> 
					 <tr>
					    <td   class="index">From:</td>
					    <td><input type="date" name="fromdate" id="fromdatepicker"  /> </td>
					 </tr>	
				 </table>
			
			</div>
			<div id="todate">
				<table style="background-color:white" border="0" width="100%" cellpadding="5"> 
				 <tr>
				    <td   class="index">To:</td>
				    <td><input type="date" name="todate" id="todatepicker"   /> </td>
				 </tr>	
				 </table>
			
			</div>
			 
		</div>
		
		 <table style="background-color:white" border="0" width="60%" cellpadding="5"> 
			 <tr>
			    

			    <td colspan="2" ><input class="admin" type="submit" name="Submit" value="Submit" /> &nbsp<span id="statusspan"></span></td>
			    <td><input type="button" value="Go back to Calendar!" onClick="goToCalendar()" class="button1"/> </td>
			 </tr>	
			
		</table>
	
	
		    
	</form>   
 </div>
 </tbody>
</div>


</body>


<script type="text/javascript">

function goToCalendar()
{	
	
	window.open('showCalendar.jsp', '_self');
	
}


function clearSpan(elementId)
{
	document.getElementById(elementId).innerHTML ="";	
}

function saveStatus()
{
	try
	{
		var user_id= "<%= userId %>";
		var user_name="<%= userName %>";
		var comment = document.getElementById("comment").value;
		
		
		var statusSelectBox = document.getElementById("resourcestatus");
		var selectedStatusValue = statusSelectBox.value;
		var statusName = statusSelectBox.options[statusSelectBox.selectedIndex].text;
	
		
		//get the dates
		var selectedfromDate = document.getElementById("fromdatepicker").value;
		var selectedtoDate = document.getElementById("todatepicker").value;
	
		var fromDate = new Date(selectedfromDate);
		var fromDay=fromDate.getDate();
		var fromMonth = fromDate.getMonth();
		var fromYear = fromDate.getFullYear();	
		
		var toDate = new Date(selectedtoDate);
		var toDay=toDate.getDate();
		var toMonth = toDate.getMonth();
		var toYear = toDate.getFullYear();	
		
		//form checking
		if(selectedStatusValue=="")
		{
			alert("Select the status.");
			return false;
		}
		if(selectedfromDate=="")
		{
			alert("Select from date!");
			return false;
		}
		if(selectedtoDate=="")
		{
			alert("Select to date!");
			return false;
		}
		
		if((toDate < fromDate) && (toDate != fromDate))
		{
			alert("To date should be after from date!");
			return false;
		}
		
		
		//ajax to check if the user's team has been updated
		var xmlhttp;
		
		
		if (window.XMLHttpRequest) 
		{
			   xmlhttp = new XMLHttpRequest();
		} else 
		{
			    // code for IE6, IE5
			    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		
		
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
	    	
		
		
		
		
		//check if edit is possible. ie, date not within 2 months from 2day.
		
		
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
		 
		 var posturl = "year=" + fromYear + "&&month=" + fromMonth + "&&day=" + fromDay;
	    
		 
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
	
	
	
		
	
		
		
		
		
			
		
		
		
		//ajax to call class to save status on multiple days.
		if (window.XMLHttpRequest) 
		{
			xmlhttp = new XMLHttpRequest();
		} 
		else 
		{
			    // code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		
			
			 xmlhttp.onreadystatechange = function()
		    {
		        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
		        {
		        	
		        	
		        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();
		        	var savedStatus  = "success";
		        	var errorMsg = "error;"
		            if(trimmedResponse.toLowerCase()  == savedStatus.toLowerCase())
	            	{
	                	//alert("Saved status!");
	                	document.getElementById("statusspan").style.color = "red";
		                document.getElementById("statusspan").innerHTML = trimmedResponse.toUpperCase();
		                document.forms["resourceform"]["resourcestatus"].value = "";
		                document.forms["resourceform"]["comment"].value = "";
		                
	            	}
		            else if(trimmedResponse.toLowerCase()  == errorMsg.toLowerCase())
	            	{
		            	alert("Error!");
	            	}
		        	
		        }
			};
	
		 try
		    {
			 var url="${pageContext.request.contextPath}/SaveLongStatusSettings?user_id=" + user_id + "&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&toDay="+ toDay + "&&toMonth=" + toMonth + "&&toYear=" + toYear + "&&status_id=" + selectedStatusValue + "&&status_name=" + statusName + "&&resource_name=" + user_name  + "&&comment=" + comment;
			
			
			 xmlhttp.open("POST", url, true);
			
			 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			 xmlhttp.send(url);
			   
			   
			}
		    catch(e)
		    {
		    	alert("unable to connect to server");
		    	alert("error:" + e.message);
		    }	
		
		
	}
	catch(e)
	{
		alert("unable to connect to server");
		//alert("error: " + e.message);
	}
	
}







</script>

<%
}
%>
</html>