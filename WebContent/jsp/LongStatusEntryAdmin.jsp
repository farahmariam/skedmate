<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="java.util.*, java.text.*,  java.sql.*, trilane.StatusObject, trilane.CommonDatabase" %> 
<%@ include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Long Status Entry by Admin</title>
<link rel="stylesheet" href="../css/scheduleStyles.css">

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




<body onload="populateResourceSelectBox()">

<div class="container">

    <div class="header  ">
      <h3 class="settingHeader" >LONG STATUS ENTRY</h3> 
    </div>
    
  	<div class="mainbody1" id="formdiv"> 
  	
  	<form name="resourceform" method="post" action="#"  onsubmit="saveStatus();return false;" >
  	
  	
    <table border="0" width="100%" cellpadding="5"> 
     <tbody>
    
    
	   
		 
		  <tr>
				<td  class="index">Resource Name </td>
		 		
	       	 	<td><select name="resourcename" id ="resourcename" onchange="resourceOnchange()"> 
							<option value="" selected > <b> Select Resource Name</b> </option>
				 </select>
				</td>
				
			
					
		</tr>
		 
		 
		
		  
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
				
				<option value="<%=statusForDay  %>" >  <b><%=statusNameForDay  %> </b></option>
			
		
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
			
				 <table border="0" width="100%" cellpadding="5"> 
					 <tr>
					    <td   class="index">From:</td>
					    <td><input type="date" name="fromdate" id="fromdatepicker"  /> </td>
					 </tr>	
				 </table>
			
			</div>
			<div id="todate">
				<table border="0" width="100%" cellpadding="5"> 
				 <tr>
				    <td   class="index">To:</td>
				    <td><input type="date" name="todate" id="todatepicker"   /> </td>
				 </tr>	
				 </table>
			
			</div>
			 
		</div>
		
		 <table border="0" width="100%" cellpadding="5"> 
			 <tr>
			    

			    <td colspan="2" ><input class="admin" type="submit" name="Submit" value="Submit" /> &nbsp<span id="statusspan"></span></td>
			    <td><input type="button" value="Go back to Settings!" onClick="goToSettings()" class="button1"/> </td>
			 </tr>	
		</table>
	</tbody>
	
		    
	</form>   
 </div>
</div>


</body>


<script type="text/javascript">

function goToSettings()
{	
	
	window.open('settings.jsp', '_self');
	
}



function resourceOnchange()
{
	
	clearSpan('statusspan');
	
	
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

function clearSpan(elementId)
{
	document.getElementById(elementId).innerHTML ="";	
}

function saveStatus()
{
	var comment = document.getElementById("comment").value;
	
	
	var resourceSelectBox = document.getElementById("resourcename");
	var selectedResourceValue = resourceSelectBox.value;
	var resourceName=resourceSelectBox.options[resourceSelectBox.selectedIndex].text;
	
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
	                resetModifyStatusValues();
	                
            	}
	            else if(trimmedResponse.toLowerCase()  == errorMsg.toLowerCase())
            	{
	            	alert("Error!");
            	}
	        	
	        }
		};

	 try
	    {
		 var url="${pageContext.request.contextPath}/SaveLongStatusSettings?user_id=" + selectedResourceValue + "&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&toDay="+ toDay + "&&toMonth=" + toMonth + "&&toYear=" + toYear + "&&status_id=" + selectedStatusValue + "&&status_name=" + statusName + "&&resource_name=" + resourceName + "&&comment=" + comment ;
		
		 xmlhttp.open("POST", url, true);
		
		 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xmlhttp.send(url);
		   
		   
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    }	
	
	
	
	
}

function resetModifyStatusValues()
{
	document.forms["resourceform"]["resourcename"].value = "";
	document.forms["resourceform"]["resourcestatus"].value = "";
	document.forms["resourceform"]["comment"].value = "";
	
}



</script>

<%
}
%>
</html>