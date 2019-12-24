<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="trilane.CommonDatabase" %>
<%@ page import ="java.util.*" %>
<%@ page import ="trilane.TeamObject" %>
<%@ page import ="java.util.*" %>
<%@ page import ="trilane.user" %>
<%@ page import ="trilane.StatusObject" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Management</title>
<link rel="stylesheet" href="../css/scheduleStyles.css">
</head>

<%

ServletContext context = request.getServletContext();
String loginUrl = context.getInitParameter("login-url");
ArrayList<TeamObject> teamList = null;


if ((session.getAttribute("loginname") == null) || (session.getAttribute("loginname") == ""))
{
	
	
%>

	<h3 class="settingText">You are not logged in!</h3> <br/>
	<a href="<%=loginUrl %>" class="three" >Please Login..</a>


<%
}
else 
{
	if((request.getParameter("month")!="" && request.getParameter("month")!=null) && (request.getParameter("year")!="" && request.getParameter("year")!=null))
	{
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		session.setAttribute("month", month);
		session.setAttribute("year", year);
		
	}
	

%>

<body onload="hideElements()" >
<img src="../images/skedmate3.jpg"  style="width:1500px;height:180px;">
<div class="header">
</div>
 <div class="header">
     <a href="showCalendar.jsp" class="settingBoxHeader"> Go to Calendar</a>
    </div>
<div class="container">
    <div class="header  ">
     <h3 class="settingHeader">USER MANAGEMENT </h3> 
    </div>
    <div class="mainbody">
    	<form name="userform" method="post" action="#" onsubmit="saveUser();return false;" >
            <center>
			
            <table class="settingstable" border="1" width="80%" cellpadding="5">
               
                <tbody>
                    <tr>
                        <td class="index">Login Name</td>
                        <td><input type="text" name="uname" id="uname" value=""  onClick="clearallSpans()" onChange="populateUserDetails()" onBlur="populateUserDetails()" /><span id="isE"></span></td>
                    </tr>
                    <tr>
                        <td class="index">Password</td>
                        <td><input type="password" name="pass" id="pass" value="" onClick="clearSpan('savemessage');changePwdBoxStyle();" onBlur="checkPasswordMatch()"/> </td>
                    </tr>
					<tr>
                        <td class="index">Confirm Password</td>
                        <td><input type="password" name="confirmpass" id="confirmpass" value="" onBlur="checkPasswordMatch()" onClick="changePwdBoxStyle()"/><span id="pwdMatch"></span></td>
                    </tr>
                   <tr>
						<td class="index"> Administrator Privilege </td>
						<td> 
           					<select name="admin"  id="admin" onChange="clearSpan('savemessage')">
	           					<option value="0" selected>User</option>
	           					<option value="1" >Administrator</option>
    						</select>
    					</td>
    				</tr>
                   <tr>
						<td class="index"> Team </td>
						<td> 
           					<select name="technology" id="technology" onChange="clearSpan('savemessage')">
	           				<%
           						CommonDatabase objCommon = new CommonDatabase();
           					   
           					    teamList = objCommon.getAllTeams(context, session);
           					    int team_Id=-1;
           					    String team_name="";
           					    
           						//Get the status combo options for the particular day
           						for(int loop=0; loop<teamList.size(); loop++)
           						{
           							TeamObject teamObject = teamList.get(loop);
           							team_Id = teamObject.getTeamId();
           							team_name = teamObject.getTeamName();
           							
           							
           							if(loop == 0)
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
                        <td class="index">Email</td>
                        <td><input type="text" name="email" id="email" value="" onBlur="checkEmail()" onClick="clearSpan('isEmail')" /><span id="isEmail"></span></td>
                    </tr>
    				 <tr>
                        <td class="index"><input type="Submit" value="Create Resource" class="admin"/>&nbsp<span id="savemessage"></span></td>
                        <td class="index"><input type="button" value="Delete Resource" class="button1" onClick="deleteUser()"/>&nbsp&nbsp <input type="button" value="Edit Resource" class="button1" onClick="editUser()"/> &nbsp&nbsp <input type="button" value="View all Resources" class="button1" onClick="viewUsers()"/> </td>
                    </tr>
   			 </tbody>
           </table>
         </center>
        </form>
      
     </div>
   
</div> 

<div class="footer">
</div>



<div class="container">

    <div class="header  ">
      <h3 class="settingHeader" >CALENDAR MANAGEMENT</h3> 
    </div>
    
  	<div class="mainbody"> 
  	
    <table border="0" width="100%" cellpadding="0"> 
    
	    <tr>
		    <td colspan="6" align="center">
		    	<div  id="datediv"> 
		  				<table border="0" width="60%" cellpadding="0"> 
					     	<td><h3 class="settingText">Select date</h3></td>
			                <td><input type="date" name="date" id="datepicker" onChange="datefunction()" /> </td>
			
							  <td >
								    <a href='LongStatusEntryAdmin.jsp' class="three" title="Click this link to save status of resource over multiple days">Edit multiple days.</a>
								    
								    </td>
						</table>
				</div>
		    </td>
		  
	    </tr>
	    <tr>
	    	<td>
		    	
			    <div class="divstyle" id="holidaydiv">
			    	<table border="0" width="60%" cellpadding="5">
			    		<tr>
						<td class="settingText">Public Holiday? </td>
						 <td><input type="checkbox" class="squaredOne" name="holiday" id="holiday" value="Public Holiday" onclick="saveHoliday();" /> </td>
							
						</tr> 
				    	
			    	</table>
			    </div>
				   
			 </td>
		    <td>
		    	<div class="divstyle" id="minmaxdiv">
		    	<form name="limitform" action="#"  onsubmit="saveLimits();return false;">
			    	<table class="settingstable" border="1" width="90%" cellpadding="5">
			    		<thead >
	                   		 <tr>
		                        
								<th  class="tableSettingsHeader"  colspan="2"><label  name="day" id="day" value="" /> </th>
		                    </tr>
	               		 </thead>
				  <%
					//Get theteam names
						for(int loop=0; loop<teamList.size(); loop++)
						{
							TeamObject teamObj = teamList.get(loop);
							team_Id = teamObj.getTeamId();
							team_name = teamObj.getTeamName();
							int counter=loop+1;
							
				  
				  
				  
				  %>
				    
			   			 <tr>
			    			<td class="indexSettings"> Team : <%= team_name.toUpperCase()   %> </td>
			       			<td><input type="number" name="team<%=counter %>" id="team<%=counter %>" value=""  onchange="clearSpan('limitspan')" onClick="clearSpan('limitspan')"/> </td>
					
						</tr>
						
				<%
						}
				
				%>
						
						<tr>
					
					
								<td colspan="2"> <input class="admin" type="submit" id="savelimits" name="savelimits" value="Save" />&nbsp<span id="limitspan"></span></td>
					 
						</tr>
					</table>
	    			</form>
	    		</div>
	    	</td>
	    	
	    	
	    	 <td>
		    	<div class="divstyle" id="resourcediv">
		    	<form name="resourceform" action="#"  onsubmit="modifyResourceStatus();return false;">
			    	<table class="settingstable" border="1" width="90%" cellpadding="5">
			    		<thead >
	                   		 <tr>
		                        <th class="tableSettingsHeader"  colspan="2"> Resources for the day </th>
								
		                    </tr>
	               		 </thead>
				  
				    
			   			 <tr>
			    			<td class="indexSettings"> Name </td>
			       			<td><select name="resourcename" id ="resourcename" onchange="loadResourceStatus()"> 
							<option value="" selected > <b> Select Resource Name</b> </option>
							</select>
							</td>
					
						</tr>
						
				
						<tr>
						
							<td class="indexSettings"> Status </td>
							<td><select name="resourcestatus" id ="resourcestatus" onchange="clearSpan('statusspan')"> 
							<option value="" selected > <b> Select Status</b> </option>
							</select>
							</td>
					
					
						</tr>
				
						
						<tr>
					
					
								<td colspan="2" > <input class="admin" type="submit" id="modifystatus" name="modifystatus" value="Modify" />&nbsp<span id="statusspan"></span></td>
					 			
						</tr>
					</table>
	    			</form>
	    		</div>
	    	</td>
	    	
	    </tr>
    </table>
   </div>
</div>


<div class="footer">
</div>



<div class="container">

    <div class="header  ">
      <h3 class="settingHeader" >DISPLAY AND INTERNAL SETTINGS</h3> 
    </div>
    
  	<div class="mainbody1"> 
  	<center>
  	<table class="settingstable" border="1" width="80%" cellpadding="5">
  	
  	<tbody>
  	
  	<%
  		
		ArrayList<StatusObject> statusList = objCommon.getAllStatus(context, session);	
		for(int i=0;i<statusList.size();i++)
		{
			StatusObject status = statusList.get(i);
			String statusName = status.getStatusName();
			String colour = status.getStatusColor();
		
  	
  	
  	%>
  	
  	
  	
  	 
               
                
                    <tr>
                        <td class="index"><%=statusName.toUpperCase() %></td>
						<td><input type="color" id="color<%=(i+1) %>" name="color<%=(i+1) %>" value="<%= colour  %>"  onClick="clearSpan('spancolour<%=(i+1) %>');clearSpan('colourmsgspan')"> Display Colour <span id="spancolour<%=(i+1) %>" ></span></td>
						
                    </tr>
  	
  	
  	
  	
  	<%
		}
  	%>
  					<tr>
                        <td  colspan="2"><input type="button" id="btnSaveColour" value="Save Status Colours" onClick="saveColours()" class="button2"/>&nbsp<span id="colourmsgspan"></span></td>
           			</tr>
  		</tbody>
  		</table>
  		
  		
  		<div class="footer">
		</div>
  		<table class="settingstable" border="1" width="80%" cellpadding="5">
  		
  		<tbody>
  		 <tr>
                        <td title="During the freeze period, only the admin can change or edit the schedules. The freeze period is calculated from the current day" class="index">Freeze Period</td>
						<td title="During the freeze period, only the admin can change or edit the schedules. The freeze period is calculated from the current day"><input type="number" name="freezeperiod" id="freezeperiod" value="<%=objCommon.getFreezePeriod(context, session) %>"  onClick="clearSpan('divfreezeperiod');clearSpan('freezeperiodmsgspan')" onBlur="checkFreezePeriod()" />days. <span id="divfreezeperiod"></span></td>
        </tr>
  		
  		<tr>
                        <td  colspan="2"><input type="button" id="btnSaveFreezePeriod" value="Save Freeze Period" onClick="saveFreezePeriod()" class="button2"/>&nbsp<span id="freezeperiodmsgspan"></span></td>
       </tr>
  		</tbody>
  		</table>
  		
  		</center>
	</div>
</div>

</body>









<script type="text/javascript">

function checkFreezePeriod()
{
	var freezePeriod = document.getElementById("freezeperiod").value;
	if(freezePeriod==null || freezePeriod=="")
	{
		document.getElementById("divfreezeperiod").style.color = "red";
		document.getElementById("divfreezeperiod").innerHTML = "Please enter the freeze period.";
		return false;
	}
	if(freezePeriod<0)
	{
		document.getElementById("divfreezeperiod").style.color = "red";
		document.getElementById("divfreezeperiod").innerHTML = "Freeze period should be 0 or more days!";
		document.getElementById("freezeperiod").value="";
		return false;
	}
	
	
}

function saveFreezePeriod()
{
	var freezePeriod = document.getElementById("freezeperiod").value;
	if(freezePeriod==null || freezePeriod=="")
	{
		document.getElementById("divfreezeperiod").style.color = "red";
		document.getElementById("divfreezeperiod").innerHTML = "Please enter the freeze period.";
		return false;
	}
	if(freezePeriod<0)
	{
		document.getElementById("divfreezeperiod").style.color = "red";
		document.getElementById("divfreezeperiod").innerHTML = "Freeze period should be 0 or more days!";
		document.getElementById("freezeperiod").value="";
		return false;
	}
	
	//ajax to save freeze period
	
	var xmlhttp = new XMLHttpRequest();
    
    xmlhttp.onreadystatechange = function()
    {
        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
    		var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

        	var savedColour  = "Saved freeze period!";
        	if(trimmedResponse.toLowerCase()  == savedColour.toLowerCase())
             {
        		document.getElementById("freezeperiodmsgspan").style.color = "red";
            	document.getElementById("freezeperiodmsgspan").innerHTML = trimmedResponse.toUpperCase();
           	 }
           
        
       
    	}
    
	};
    try
    {
		var posturl = "freezeperiod=" + freezePeriod  ;
    
	 
		xmlhttp.open("POST", "${pageContext.request.contextPath}/SaveFreezePeriod", true);
	
	 	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	 	xmlhttp.send(posturl);
	}
    catch(e)
    {
    	alert("unable to connect to server");
    }

	
	
	
	}

function saveColours()
{
	var NumStatus= <%=statusList.size()  %>;
	
	var statusColourtArray=[];
	
	//get the limits
	for(var i=1;i<=NumStatus;i++)
	{
		var colourInputId = "color" + (i);
		
		var colour = document.getElementById(colourInputId).value;
		
		if(colour==""||colour==null)
		{
			var spanId="spancolour" + i;
			document.getElementById(spanId).innerHTML="Please choose a colour!";
			return false;
		}
		statusColourtArray.push(colour );
		
	}
	
	var jString = JSON.stringify(statusColourtArray);
	
	//ajax to save colours
	
	var xmlhttp = new XMLHttpRequest();
    
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
        		var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	var savedColour  = "Saved status display colours!";
	        	if(trimmedResponse.toLowerCase()  == savedColour.toLowerCase())
	             {
	        		document.getElementById("colourmsgspan").style.color = "red";
                	document.getElementById("colourmsgspan").innerHTML = trimmedResponse.toUpperCase();
	           	 }
	           
            
           
        	}
        
    	};
	    try
	    {
    		var posturl = "jsonString=" + jString  ;
	    
		 
			xmlhttp.open("POST", "${pageContext.request.contextPath}/SaveStatusColours", true);
		
		 	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 	xmlhttp.send(posturl);
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }
	
	
	
	
	
}

function viewUsers()
{
	
	 window.open("ListResources.jsp",'_blank');	
	
}
function checkPasswordMatch()
{
			clearSpan("pwdMatch");
			var z = document.forms["userform"]["pass"].value;
			var cpass = document.forms["userform"]["confirmpass"].value;
	    	if (z != null && z != "" && cpass != null && cpass != "" )
			{
	        	if (z != cpass) 
				 	{
						document.getElementById("pwdMatch").style.color = "red";
        				document.getElementById("pwdMatch").innerHTML = "PASSWORDS DO NOT MATCH";
        				document.forms["userform"]["pass"].value = "";
        				document.forms["userform"]["confirmpass"].value = "";
				        
				        document.forms["userform"]["pass"].style.borderColor = "#E34234";
				        document.forms["userform"]["confirmpass"].style.borderColor = "#E34234";
				        
				    }

	    	}
			
	    	
	
	
	
}

function changePwdBoxStyle()
{
	
	document.forms["userform"]["pass"].style.borderColor = "";
    document.forms["userform"]["confirmpass"].style.borderColor = "";
    clearSpan("pwdMatch");
	
	}


function populateUserDetails()
{
	var userName = document.forms["userform"]["uname"].value;
	
	if(userName!="")
	{
		//check if the username exists
		//check if user exists
		var xmlhttp = new XMLHttpRequest();
		var returnValue = "0";
	    
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
	        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();
	
	        	var exists  = "User already exists";
	        	var notExists = "User name does not exist";
	        	
	            if(trimmedResponse.toLowerCase()  == notExists.toLowerCase())
	            	{
		               
		                
		                returnValue = "1";
	            	}
	            
	           
	        }
	        
	    };
	    try
	    {
	    	var posturl = "username=" + userName ;
		    
			 
			 xmlhttp.open("POST", "${pageContext.request.contextPath}/UserNameExists", false);
			
			 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			 xmlhttp.send(posturl);
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }
		if(returnValue=="1")
		{
			
			return false;
		}
		
		//get the user details and populate the form
		
		
		var xmlhttp = new XMLHttpRequest();
	    
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
        		var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();
				
				var user = JSON.parse(trimmedResponse);
				//alert(trimmedResponse);
			
				document.getElementById("pass").value=user.loginpassword;
				document.getElementById("confirmpass").value=user.loginpassword;
				document.getElementById("email").value=user.email;
				document.getElementById("admin").value = user.isadmin;
				
				document.getElementById("technology").value = user.techid;
				
				
				
	        }
        
    	};
	    try
	    {
	    	var posturl = "userName=" + userName ;
		    
			 
			 xmlhttp.open("POST", "${pageContext.request.contextPath}/GetUserDetails", false);
			
			 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			 xmlhttp.send(posturl);

		}
	    catch(e)
	    {
	    	alert("unable to connect to server"); 
	    }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
}

function saveUser() 
{
	clearSpan("savemessage");
	
	var userName = document.forms["userform"]["uname"].value;
	var password = document.forms["userform"]["pass"].value;
	var confirmpassword = document.forms["userform"]["confirmpass"].value;
	var isAdmin = document.forms["userform"]["admin"].value;
	var tech = document.forms["userform"]["technology"].value;
	var email =  document.forms["userform"]["email"].value;
	
	if(userName=="")
	{
		alert("Please enter a user name!");
		return false;
	
	}
	
	if(password=="")
	{
		alert("Please enter the password!");
		return false;
	
	}
	
	if(confirmpassword=="")
	{
		alert("Please confirm the password!");
		return false;
	
	}
	
	if (password != confirmpassword) 
 	{
        alert("Passwords do not match");
        document.forms["userform"]["pass"].style.borderColor = "#E34234";
        document.forms["userform"]["confirmpass"].style.borderColor = "#E34234";
        return false;
    }
	if(isAdmin==1)
	{
		if(email=="")
		{
			alert("Please enter the email! If new User being added is an administrator, you need to enter the email id!");
			return false;
		
		}
		
		var reEmail = /^(?:[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+\.)*[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+@(?:(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!\.)){0,61}[a-zA-Z0-9]?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!$)){0,61}[a-zA-Z0-9]?)|(?:\[(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\]))$/;

	  	if(!email.match(reEmail)) {
	   	 	alert("Invalid email address");
	    	return false;
	  	}
		 	
		
	
	}
	
	
	//check if user exists
	var xmlhttp = new XMLHttpRequest();
	var returnValue = "0";
    
    xmlhttp.onreadystatechange = function()
    {
        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

        	var exists  = "User already exists";
        	
            if(trimmedResponse.toLowerCase()  == exists.toLowerCase())
            	{
	                document.getElementById("isE").style.color = "red";
	                document.getElementById("isE").innerHTML = trimmedResponse.toUpperCase();
	               // document.forms["userform"]["uname"].value = "";
	                resetValues();
	                
	                returnValue = "1";
            	}
            
           
        }
        
    };
    try
    {
    	var posturl = "username=" + userName ;
	    
		 
		 xmlhttp.open("POST", "${pageContext.request.contextPath}/UserNameExists", false);
		
		 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xmlhttp.send(posturl);
	}
    catch(e)
    {
    	alert("unable to connect to server");
    }
	if(returnValue=="1")
	{
		
		return false;
	}
	
	
	//if the new user is an admin, check if the email entered is already present in the global_admin table
	
	if (window.XMLHttpRequest) 
	{
		   xmlhttpobj = new XMLHttpRequest();
	} else 
	{
		    // code for IE6, IE5
		    xmlhttpobj = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	var emailValue = "0";
    
	xmlhttpobj.onreadystatechange = function()
    {
        if(xmlhttpobj.readyState == 4 && xmlhttpobj.status == 200)
        {
        	var emailResponse = xmlhttpobj.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

        	var emailexists  = "email exists";
        	
        	
        	
            if(emailResponse.toLowerCase()  == emailexists.toLowerCase())
            	{
	                document.getElementById("isEmail").style.color = "red";
	                document.getElementById("isEmail").innerHTML = "This email id is already associated with the company as an administrator!";
	                document.forms["userform"]["email"].value = "";
	                
	                emailValue = "1";
            	}
            
           
        }
        
    };
    try
    {
    	var url = "${pageContext.request.contextPath}/EmailExists?email=" + email;
    	xmlhttpobj.open("GET", url, false);
    	xmlhttpobj.send();
	}
    catch(e)
    {
    	alert("unable to connect to server");
    }
   
	if(emailValue=="1")
	{
		
		return false;
	}
	
	
	
	
	
	
	
	//ajax to add user
	
	if (window.XMLHttpRequest) 
	{
		   xmlhttpobj = new XMLHttpRequest();
	} else 
	{
		    // code for IE6, IE5
		    xmlhttpobj = new ActiveXObject("Microsoft.XMLHTTP");
	}
		
		
			
			xmlhttpobj.onreadystatechange = function()
		    {
		        if(xmlhttpobj.readyState == 4 && xmlhttpobj.status == 200)
		        {
		        	var trimmedResponse = xmlhttpobj.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();
	
		        	var added  = "New User added";
		        	var error = "Error";
		        	
		        	if(trimmedResponse.toLowerCase()==added.toLowerCase())
	            	{
	            		
	            		document.getElementById("savemessage").style.color = "red";
	            		document.getElementById("savemessage").innerHTML = "Added User!";
	            		resetValues();
	            		
	            		
	            		
	            	}
		        	else if(trimmedResponse.toLowerCase()==error.toLowerCase())
	        		{
		        		document.getElementById("savemessage").style.color = "red";
	           		 	document.getElementById("savemessage").innerHTML = "Error!";
	           		 	resetValues();
	        		}
	           		
		           
		            
		           
		        }
			};
		
		
		 try
		    {
			  
			 var posturl = "uname=" + userName + "&&pwd=" + password + "&&tech=" + tech + "&&admin=" + isAdmin + "&&email=" + email;
		    
			 
			 xmlhttpobj.open("POST", "${pageContext.request.contextPath}/SaveNewUser", true);
			
			 xmlhttpobj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			 xmlhttpobj.send(posturl);
			   
			   
			}
		    catch(e)
		    {
		    	alert("unable to connect to server");
		    }	
	  
	    
	
}

function checkUserExist()
{
	clearSpan("isE");
	
    var xmlhttp = new XMLHttpRequest();
    var userName = document.forms["userform"]["uname"].value;
   
    xmlhttp.onreadystatechange = function()
    {
        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

        	var exists  = "User already exists";
            if(trimmedResponse.toLowerCase()  == exists.toLowerCase())
            	{
                document.getElementById("isE").style.color = "red";
                document.getElementById("isE").innerHTML = trimmedResponse.toUpperCase();
                document.forms["userform"]["uname"].value = "";
            	}
            
           
        }
        
    };
    try
    {
    	var posturl = "username=" + userName ;
	    
		 
		 xmlhttp.open("POST", "${pageContext.request.contextPath}/UserNameExists", true);
		
		 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xmlhttp.send(posturl);
	}
    catch(e)
    {
    	alert("unable to connect to server");
    }
}

function clearSpan(elementId)
{
	document.getElementById(elementId).innerHTML ="";	
}

function resetValues()
{
	document.forms["userform"]["uname"].value = "";
	document.forms["userform"]["pass"].value = "";
	document.forms["userform"]["confirmpass"].value = "";
	document.forms["userform"]["email"].value = "";
	document.forms["userform"]["admin"].value = "0";
	document.forms["userform"]["technology"].value = "1";
	
	
	
	}
	
	function resetModifyStatausValues()
	{
		document.forms["resourceform"]["resourcename"].value = "";
		document.forms["resourceform"]["resourcestatus"].value = "";
		
	}
	
	
	
	function clearallSpans()
	{
		clearSpan("savemessage");
		clearSpan("isE");
		clearSpan("pwdMatch");
		clearSpan("colourmsgspan");
		
		
		
	}
	
	
	function editUser()
	{
		var userName = document.forms["userform"]["uname"].value;
		if (userName=="")
		{
			alert("Please enter the existing username of resource to be edited.");
			return false;
			
		}
		
		var password = document.forms["userform"]["pass"].value;
		var confirmpassword= document.forms["userform"]["confirmpass"].value;
		if (password=="")
		{
			alert("Please enter the new password.");
			return false;
			
		}
		
		if(confirmpassword=="")
		{
			alert("Please confirm the password!");
			return false;
		
		}
		
		if (password != confirmpassword) 
	 	{
	        alert("Passwords do not match");
	        document.forms["userform"]["pass"].style.borderColor = "#E34234";
	        document.forms["userform"]["confirmpass"].style.borderColor = "#E34234";
	        return false;
	    }
		
		var isAdmin = document.forms["userform"]["admin"].value;
		var tech = document.forms["userform"]["technology"].value;
		
		var email = document.forms["userform"]["email"].value;
		
		if(isAdmin==1)
		{
			if(email=="")
			{
				alert("Please enter the email! If new User being added is an administrator, you need to enter the email id!");
				return false;
			
			}
			
			var reEmail = /^(?:[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+\.)*[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+@(?:(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!\.)){0,61}[a-zA-Z0-9]?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!$)){0,61}[a-zA-Z0-9]?)|(?:\[(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\]))$/;

		  	if(!email.match(reEmail)) {
		   	 	alert("Invalid email address");
		    	return false;
		  	}
			 	
			
		
		}
		
		
	//ajax to check if user exists. then only edit
		
		var returnValue="0";
	    var xmlhttp = new XMLHttpRequest();
	   
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
	        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	
	        	var notexist = "User name does not exist";
	            if(trimmedResponse.toLowerCase()  == notexist.toLowerCase())
	            	{
		                document.getElementById("isE").style.color = "red";
		                document.getElementById("isE").innerHTML = trimmedResponse.toUpperCase();
		                document.forms["userform"]["uname"].value = "";
		                returnValue="1";
	            	}
	            
	           
	        }
	        
	    };
	    try
	    {
	    	var posturl = "username=" + userName ;
		    
			 
			 xmlhttp.open("POST", "${pageContext.request.contextPath}/UserNameExists", false);
			
			 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			 xmlhttp.send(posturl);
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }
	    
	    if(returnValue=="1")
		{
		return false;
		}
	
	
	
	//ajax to edit user
	var xmlhttp = new XMLHttpRequest();
    
    xmlhttp.onreadystatechange = function()
    {
        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

        	var edited  = "User edited";
        	
    	
    		if(trimmedResponse.toLowerCase()  == edited.toLowerCase())
        	{
                document.getElementById("savemessage").style.color = "red";
                document.getElementById("savemessage").innerHTML = trimmedResponse.toUpperCase();
                resetValues();
        	}
          
        }
        
    };
    try
    {
    	var posturl = "username=" + userName  + "&&pwd=" + password + "&&tech=" + tech + "&&admin=" + isAdmin + "&&email=" + email ;
	    
		 
		 xmlhttp.open("POST", "${pageContext.request.contextPath}/EditUser", true);
		
		 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xmlhttp.send(posturl);
	}
    catch(e)
    {
    	alert("unable to connect to server");
    }
	
		
		
		
		
		
	}
	
	
	
	
	
	
	
	function deleteUser()
	{
		var userName = document.forms["userform"]["uname"].value;
		if (userName=="")
		{
			alert("Please enter the existing username of resource to be deleted.");
			return false;
			
		}
		
		
		
		
		//ajax to check if user exists. then only delete
		
		var returnValue="0";
	    var xmlhttp = new XMLHttpRequest();
	   
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
	        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	
	        	var notexist = "User name does not exist";
	            if(trimmedResponse.toLowerCase()  == notexist.toLowerCase())
	            	{
		                document.getElementById("isE").style.color = "red";
		                document.getElementById("isE").innerHTML = trimmedResponse.toUpperCase();
		                document.forms["userform"]["uname"].value = "";
		                returnValue="1";
	            	}
	            
	           
	        }
	        
	    };
	    try
	    {
	    	var posturl = "username=" + userName ;
		    
			 
			 xmlhttp.open("POST", "${pageContext.request.contextPath}/UserNameExists", false);
			
			 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			 xmlhttp.send(posturl);
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }
		
		
		if(returnValue=="1")
			{
			return false;
			}
		
		
		
		//ajax to delete user
		var xmlhttp = new XMLHttpRequest();
	    
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
	        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	var deleted  = "User deleted";
	        	
        	
        		if(trimmedResponse.toLowerCase()  == deleted.toLowerCase())
            	{
	                document.getElementById("savemessage").style.color = "red";
	                document.getElementById("savemessage").innerHTML = trimmedResponse.toUpperCase();
	                document.forms["userform"]["uname"].value = "";
	                resetValues();
            	}
	          
	        }
	        
	    };
	    try
	    {
	    	var posturl = "username=" + userName ;
		    
			 
			 xmlhttp.open("POST", "${pageContext.request.contextPath}/DeleteUser", true);
			
			 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			 xmlhttp.send(posturl);
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }
		
		
		
		
	}
	
	
	
	//Onchange function of date
	function datefunction()
	{
		clearSpan('statusspan');
		clearSpan('limitspan');
		
		
		//show the hidden divs
		document.getElementById('holidaydiv').style.display='block';
		document.getElementById('minmaxdiv').style.display='block';
		document.getElementById('resourcediv').style.display='block';
		
		//remove options from resource and status select boxes
		clearOptions("resourcename");
		clearOptions("resourcestatus");
		
		
		//if holiday, show as checked, else uncheck it.
		fillHolidayDiv(document.getElementById("datepicker").value);
		
		//populate the minmaxdiv
		fillMinMaxDiv(document.getElementById("datepicker").value);
		
		//populate the resource name select box
		populateResourceSelectBox();
		
		//populate the status name select box
		populateStatusSelectBox(document.getElementById("datepicker").value)
	}
	
	//function to clear selectbox
	function clearOptions(selectname)
	{
		var selectbox = document.getElementById(selectname);
		var i;
	    for(i = selectbox.options.length - 1 ; i > 0 ; i--)
	    {
	        selectbox.remove(i);
	    }

		
		
	}
	
	
	//populate the resource name select box
	function populateResourceSelectBox()
	{
		
		var xmlhttp = new XMLHttpRequest();
	    
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
        		var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();
				
				var myArr = JSON.parse(trimmedResponse);
				var resourceSelect = document.getElementById("resourcename"); 

				for(var i=0; i<myArr.length ; i++)
				{	
					var resFullName="";
					
					//get the resouce object name and id
					var resource =myArr[i]; 
					var resLoginName = resource.loginname;
					var resId =  resource.id;
					resFullName = resource.fullname;
					
					//create an option element and add to select box
					var element = document.createElement("option");
					var resTextContent = "";
					if(resFullName.trim()=="")
					{
						resTextContent=resLoginName;
					}
					else
					{
						resTextContent = resLoginName + " [ " + resFullName + " ] ";
					
					}
					element.textContent = resTextContent;
					element.value = resId;
					resourceSelect.appendChild(element);
					
					
				}
				
        	}
        
    	};
	    try
	    {
		 	var url = "${pageContext.request.contextPath}/PopulateResourceBox";
		 	xmlhttp.open("GET", url, true);
		 	xmlhttp.send();

		}
	    catch(e)
	    {
	    	alert("unable to connect to server"); 
	    }
		
		
		
	}
	
	//populate the status box
	function populateStatusSelectBox(selecteddate)
	{
		
		
		var xmlhttp = new XMLHttpRequest();
	    
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
        		var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();
				
				var myArr = JSON.parse(trimmedResponse);
				var statusSelect = document.getElementById("resourcestatus"); 

				for(var i=0; i<myArr.length ; i++)
				{
					//get the resouce object name and id
					var status =myArr[i]; 
					var statusName = status.statusname;
					var statusId =  status.statusid;
					
					//create an option element and add to select box
					var element = document.createElement("option");
					element.textContent = statusName;
					element.value = statusId;
					statusSelect.appendChild(element);

				
				}
				
				
        	}
        
    	};
	    try
	    {
    		
		 	
		 	var url = "${pageContext.request.contextPath}/PopulateStatusBox";
		 	xmlhttp.open("GET", url, true);
		 	xmlhttp.send();

		}
	    catch(e)
	    {
	    	alert("unable to connect to server"); 
	    }
		
		
		
		
	}
	
	
	
	// function to check/uncheck holiday check box if it is/is not a holiday on the date change function.
	function fillHolidayDiv(selecteddate)
	{
		
		var date = new Date(selecteddate);
		var day=date.getDate();
		var month = date.getMonth();
		var year = date.getFullYear();
		
		
		
		//ajax to check if selected date has been saved as a holiday.
		var xmlhttp = new XMLHttpRequest();
    
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
        		var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	var isHoliday  = "true";
	        	var notHoliday = "false";
	            if(trimmedResponse.toLowerCase()  == isHoliday.toLowerCase())
	            {
	            	document.getElementById('holiday').checked = true;
	            }
	            else if(trimmedResponse.toLowerCase()  == notHoliday.toLowerCase())
            	{
	            	document.getElementById('holiday').checked = false;
            	}
            
           
        	}
        
    	};
	    try
	    {
    		var posturl = "date=" + day + "&&month=" + month + "&&year=" + year ;
	    
		 
			xmlhttp.open("POST", "${pageContext.request.contextPath}/CheckIfHoliday", true);
		
		 	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 	xmlhttp.send(posturl);
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }
		
	}
	
	
	
	
	
	//Function to set the day and the xr and cat limits on date change.
	function fillMinMaxDiv(selecteddate)
	{
		var date = new Date(selecteddate);
		var day=date.getDate();
		var month = date.getMonth();
		var year = date.getFullYear();
		var counter=<%= teamList.size() %>;
		
		
		
		var xmlhttp = new XMLHttpRequest();
	    
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
        		var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();
				
				var myArr = JSON.parse(trimmedResponse);
				
				document.getElementById("day").innerHTML = getDay(myArr[0]);
				for(var i=1;i<=counter;i++)
				{
					elementName="team" + i;
					document.getElementById(elementName).value= myArr[i];
				}
				
			
        	}
        
    	};
	    try
	    {
    		
		 	
		 	var url = "${pageContext.request.contextPath}/GetMinMaxResource?date=" + day + "&&month=" + month + "&&year=" + year;
		 	xmlhttp.open("GET", url, true);
		 	xmlhttp.send();

		}
	    catch(e)
	    {
	    	alert("unable to connect to server"); 
	    }
		
		
		
		
		
		
	}
	
	
	
	function hideElements()
	{	
		
		document.getElementById('holidaydiv').style.display='none';
		document.getElementById('minmaxdiv').style.display='none';
		document.getElementById('resourcediv').style.display='none';
	}
	
	
	//Function to save as a holiday if checked and delete holiday if unchecked
	function saveHoliday()
	{
		
		var checked = document.getElementById('holiday').checked;
		
		var NumTeams = <%=teamList.size()  %>;
		
		var teamLimitArray=[];
		
		//get the limits
		for(var i=1;i<=NumTeams;i++)
		{
			var teamName = "team" + (i);
			
			var limit = document.getElementById(teamName).value;
			teamLimitArray.push(limit );
			
		}
		
		var jString = JSON.stringify(teamLimitArray);
		
		var date = new Date(document.getElementById("datepicker").value);
		var day=date.getDate();
		var month = date.getMonth();
		var year = date.getFullYear();
		
		//ajax to save the date as a holiday if checked and to delete if unchecked.
		var xmlhttp = new XMLHttpRequest();
    
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
        		var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	var savedHoliday  = "Saved as holiday!";
	        	var deletedHoliday = "Deleted holiday!";
	            if(trimmedResponse.toLowerCase()  == savedHoliday.toLowerCase())
	            	{
	                	alert("Saved as a public Holiday!");
	            	}
	            else if(trimmedResponse.toLowerCase()  == deletedHoliday.toLowerCase())
	            	{
	            		alert("Removed public holiday!")
	            	}
            
           
        	}
        
    	};
	    try
	    {
    		var posturl = "checked=" + checked + "&&date=" + day + "&&month=" + month + "&&year=" + year + "&&jsonString=" + jString ;
	    
		 
			xmlhttp.open("POST", "${pageContext.request.contextPath}/SaveHoliday", false);
		
		 	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 	xmlhttp.send(posturl);
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }
	    
	    //if unchecked then retrieve limits from team_day_limits and refresh .If checked retrieve values from public_holiday table
	    
	    
	    fillMinMaxDiv(document.getElementById("datepicker").value);
	    
	    
	    
	    
	    
	}
	
	function saveLimits()
	{
		var checked = document.getElementById('holiday').checked;
		
		
		
		//get limits
		var NumTeams = <%=teamList.size()  %>;
		
		var teamLimitArray=[];
		
		//get the limits
		for(var i=1;i<=NumTeams;i++)
		{
			var teamName = "team" + (i);
			
			var limit = document.getElementById(teamName).value;
			
			if(limit<0)
			{
				alert("Limit should not be less than 0!");
				document.getElementById(teamName).value="";
				document.getElementById(teamName).focus();
				return false;
			}
			teamLimitArray.push(limit );
			
		}
		
		var jString = JSON.stringify(teamLimitArray);
		
		//ajax to save max and min in xr and cat
		
	
		var date = new Date(document.getElementById("datepicker").value);
		var day=date.getDate();
		var month = date.getMonth();
		var year = date.getFullYear();
		
		
		var xmlhttp = new XMLHttpRequest();
    
	    xmlhttp.onreadystatechange = function()
	    {
	        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	        {
        		var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

	        	var savedLimits  = "Saved limits!";
	        	var holidayLimits = "Saved Public Holiday limits!";
	           // if(trimmedResponse.toLowerCase()  == savedLimits.toLowerCase() || trimmedResponse.toLowerCase()  == holidayLimits.toLowerCase())
	            //	{
	                	//alert(trimmedResponse);
	            		document.getElementById("limitspan").style.color = "red";
	                	document.getElementById("limitspan").innerHTML = trimmedResponse.toUpperCase();
	                	
	            //	}
            
           
        	}
        
    	};
	    try
	    {
    		var posturl = "jsonString=" + jString  + "&&date=" + day + "&&month=" + month + "&&year=" + year  + "&&checked=" + checked;
	    
		 
			xmlhttp.open("POST", "${pageContext.request.contextPath}/SaveLimits", true);
		
		 	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 	xmlhttp.send(posturl);
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }
		
		
	}
	
	function getDay(dayindex)
	{
		var day="";
		
		switch (dayindex) {
		    case 1:
		        day = "Sunday";
		        break;
		    case 2:
		        day = "Monday";
		        break;
		    case 3:
		        day = "Tuesday";
		        break;
		    case 4:
		        day = "Wednesday";
		        break;
		    case 5:
		        day = "Thursday";
		        break;
		    case 6:
		        day = "Friday";
		        break;
		    case 7:
		        day = "Saturday";
		        break;
		    default:
		        day = "Unknown Day";
		}
		return day;
		
	}
	
	function loadResourceStatus()
	{
		clearSpan('statusspan');
		//document.getElementById("resourcestatus").value="";
		
		var resourceSelectBox = document.getElementById("resourcename");
		var selectedResourceValue = resourceSelectBox.value;
		var selectedResourceName = resourceSelectBox.options[resourceSelectBox.selectedIndex].text;
		
		if(selectedResourceValue=="")
		{
			document.getElementById("resourcestatus").value="";
			return false;
		}
		else
		{
			//ajax to get the status of the resource for the selected day
			
			var date = new Date(document.getElementById("datepicker").value);
			var day=date.getDate();
			var month = date.getMonth();
			var year = date.getFullYear();
		
		
			var xmlhttp = new XMLHttpRequest();
	    
		    xmlhttp.onreadystatechange = function()
		    {
		        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
		        {
	        		var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();
	
					var statusSelect = document.getElementById("resourcestatus"); 
					
					for(var i = 0; i < statusSelect.length; i++) 
					{
						
					       if(statusSelect.options[i].value == trimmedResponse)
					    	{
					    	   	statusSelect.options[i].selected=true;
					    	 }
					 }

					
	           
	        	}
	        
	    	};
		    try
		    {
			 	var url = "${pageContext.request.contextPath}/GetStatusForResource?date=" + day + "&&month=" + month + "&&year=" + year + "&&userid=" + selectedResourceValue;
			 	xmlhttp.open("GET", url, true);
			 	xmlhttp.send();
			}
		    catch(e)
		    {
		    	alert("unable to connect to server");
		    }
		
		
		}
		
	}
	
	function modifyResourceStatus()
	{
		var resourceSelectBox = document.getElementById("resourcename");
		var selectedResourceValue = resourceSelectBox.value;
		var resourceName=resourceSelectBox.options[resourceSelectBox.selectedIndex].text;
		
		var statusSelectBox = document.getElementById("resourcestatus");
		var selectedStatusValue = statusSelectBox.value;
		var statusName = statusSelectBox.options[statusSelectBox.selectedIndex].text;
		
		if(selectedResourceValue=="")
		{
			alert("Select the resource.");
			return false;
		}
		
		if(selectedStatusValue=="")
		{
			alert("Select the status.");
			return false;
		}
		
		//ajax to save user event on the selected day with new status.
		
		var date = new Date(document.getElementById("datepicker").value);
		var day=date.getDate();
		var month = date.getMonth();
		var year = date.getFullYear();
	
	
		var xmlhttp = new XMLHttpRequest();
    
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
	                resetModifyStatausValues();
	                
            	}
	            else if(trimmedResponse.toLowerCase()  == errorMsg.toLowerCase())
            	{
	            	alert("Error!");
            	}
           
        	}
        
    	};
	    try
	    {
		 	var url = "${pageContext.request.contextPath}/SaveNewStatus?date=" + day + "&&month=" + month + "&&year=" + year + "&&userid=" + selectedResourceValue + "&&statusid=" + selectedStatusValue + "&&resourcename=" +resourceName + "&&statusname=" + statusName ;
		 	
		 	
		 	xmlhttp.open("GET", url, true);
		 	xmlhttp.send();
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }
	
	
	}
		
	function checkEmail()
	{
		clearSpan("isEmail");
		
		var email =  document.forms["userform"]["email"].value;
		if(email!="")
			{
			var reEmail = /^(?:[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+\.)*[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+@(?:(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!\.)){0,61}[a-zA-Z0-9]?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!$)){0,61}[a-zA-Z0-9]?)|(?:\[(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\]))$/;

		  	if(!email.match(reEmail)) 
		  	{
		  		document.getElementById("isEmail").style.color = "red";
		        document.getElementById("isEmail").innerHTML = "INVALID EMAIL";
		        document.forms["userform"]["email"].value = "";
		  	}
			
			}
		
		
		
		
	}
	
		
		
		
	
	
	
	

</script>

<%
}
%>


</html>

