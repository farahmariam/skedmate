<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Help for using Skedmate</title>
<link rel="stylesheet" href="../css/scheduleStyles.css">
</head>
<body style="background-color:#f7f0f7">

<img src="../images/skedmate3.jpg"  style="width:1500px;height:180px;">
<div class="header">
   
 </div>
<div class="header">
     <a href="index.jsp" class="settingBoxHeader"> Go to login page</a>
 </div>
 
<div class="containerwhite1">
<form name="helpform">
<h1 class="indexSettings"> Welcome to Skedmate help!!</h1> 
<p> <b> Skedmate </b> is an online rostering tool that will help your company to organise your teams and resources and their working status every day.
<ul>
	<li>The Administrator can set the minimum resources (on-shift members) required per day for each team. </li>
	<li>The admin can set/change the shift status of employees on multiple days.</li>
	<li>Employees/team members can login and mark & edit their work status on the days of a calendar. They can also change their shift status over multiple days if the minimum resource requirement allows the change.</li>
	<li>The application shows logs of all the changes made by team members.</li>
	<li>The tool also generates reports in the form of graphs. </li>

</ul>

<h><b>How to Start : </b> </h>

<ul>
<li>Create a company account (using the link :First Time User? Please Setup Project - link).</li>
<li>An email will be sent to you with the login id, password and company id. Please click on the link to confirm and start using the app.</li>
<li>Login to the app using the login id, password and company id. </li>
<li>After logging in for the first time, you have to update your details like : name, team etc using the link 'Edit personal details' under the actions tab at the top right corner. </li>
<li>Now you can start using the app. The admin can add resources to different teams and provide them with their login details. </li>
<li>The team resources can now login to the app and start using it as well. </li>
</ul>


<table style="background-color:white" cellspacing="2" cellpadding="5" border="2" width="100%">
				
                <tbody>
                    <tr>
                        <td class="index"> Update your personal details!</td>
					
					 <td><img src="../images/editmenu.jpg"  style="width:500px;height:200px;"></td>
					
					 <td> <img src="../images/editPersonal.jpg"  style="width:500px;height:200px;"> </td>
                    </tr>
                   
					
            </tbody>
</table>
<div class="header">
</div>

<table style="background-color:white" cellspacing="2" cellpadding="5" border="2" width="100%">
				
                <tbody>
                    <tr>
                        <td class="index"> Click on the calendar to show your name on that day with default working status.! In the next step, you can change this status.
						Note: There is no need to click on calendar,if the status is to be set as working, which is the default status.
						</td>
					
					 <td><img src="../images/calendar_click.jpg"  style="width:500px;height:200px;"></td>
					 <td></td>
					 </tr>
					 <tr>
                        <td class="index">After that, you can click on your name to edit your status!</td>
					
					 <td><img src="../images/clickheretoedit.jpg"  style="width:500px;height:200px;"></td>
					<td><img src="../images/editstatusform.jpg"  style="width:500px;height:200px;"></td>
					 </tr>
                   
					
            </tbody>
</table>
<div class="header">
</div>

<table style="background-color:white" cellspacing="2" cellpadding="5" border="2" width="100%" >
				
                <tbody>
				<thead class="backcolourpurple bold8">
                    <tr>
                        <th colspan="3">Help for the Actions dropdown items</th>
                    </tr>
                </thead>
                   
					 <tr>
                        <td class="index"> Update your status for multiple days using a form!</td>
					
					 <td><img src="../images/multipledaysmenu.jpg"  style="width:500px;height:200px;"></td>
					
					 <td> <img src="../images/editmultipledays.jpg"  style="width:500px;height:200px;"> </td>
                    </tr>

 					<tr>
                     <td class="index"> If you are an admin, you can access the settings menu to: add/delete users , edit status of users and also do calendar management.</td>
					
					 <td><img src="../images/settingmenu.jpg"  style="width:500px;height:200px;"></td>
					
					 <td> <img src="../images/skedsettings.jpg"  style="width:500px;height:200px;"> </td>
                    </tr>

					<tr>
                     <td class="index"> If you are an admin, you can access the log records to see all edits made by users.</td>
					
					 <td><img src="../images/logmenu.jpg"  style="width:500px;height:200px;"></td>
					
					 <td> <img src="../images/log.jpg"  style="width:500px;height:200px;"> </td>
                    </tr>
					<tr>
                     <td class="index"> Select 'Worker Scheduled Status View' to see the status of all team resources for next required number of months.</td>
					
					 <td><img src="../images/sheduleviewmenu.jpg"  style="width:500px;height:200px;"></td>
					
					 <td> <img src="../images/sheduleview.jpg"  style="width:500px;height:200px;"> </td>
                    </tr>

					<tr>
                     <td class="index"> Select 'Worker Scheduled Status View' to see the status of all team resources for next required number of months.</td>
					
					 <td><img src="../images/reportmenu.jpg"  style="width:500px;height:200px;"></td>
					
					 <td> <img src="../images/report.jpg"  style="width:500px;height:200px;"> </td>
                    </tr>

                   
                   
					
            </tbody>
</table>


 
<div class="header">
</div>


<input type="button" value="Login Page" onClick="goToLogin()" class="button1"/>
</form>
</div>

</body>
<script type="text/javascript">

function goToLogin()
{	
	
	window.open('index.jsp', '_self');
	
}
</script>
</html>