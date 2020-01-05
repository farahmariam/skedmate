<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login to access Schedule</title>
<link rel="stylesheet" href="../css/scheduleStyles.css">
</head>
<body  style="background-color:#f7f0f7">
  
<form name="login" method="post" action="${pageContext.request.contextPath}/Login" onsubmit="return validateForm()">
            <center>
<img src="../images/skedmate3.jpg"  style="width:1500px;height:180px;">

 <h3 class="settingBoxHeaderSmall"><a href='HelpForIndex.jsp' title='Click here for help'> Info about Skedmate! </a></h3>

<table style="background-color:white" cellspacing="2" cellpadding="5" border="2" width="500">
				<thead class="backcolourpurple bold8">
                    <tr>
                        <th colspan="2">Login Here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="index">User Name </td>
                        <td><input type="text" name="uname" value=""   /></td>
                    </tr>
                    <tr>
                        <td class="index">Password</td>
                        <td><input type="password" name="pass" value="" /></td>
                    </tr>
 					<tr>
                        <td class="index">Company Id</td>
                        <td><input type="text" name="compid" value="" /></td>
                    </tr>
					
                    <tr>
                        <td ><input type="submit" value="Login" /></td>
                        <td ><input type="reset" value="Reset" /></td>
                    </tr>
					
                   
                </tbody>
            </table>

			<table cellspacing="2" cellpadding="5" border="2" width="500">
					<tr>
                        <td ><a href='forgotPassword.jsp' class="three">Forgot Password and Id?</a></td>
                        <td ><a href='skedSetup.jsp' class="three">First Time User? Please Setup Project.</a></td>
                    </tr>
			</table>
			
            </center>
 <h3 class="settingBoxHeaderSmall"><a href='http://trilanetech.tk' title='Click here to go TriLane technologies home page'> By TriLane Technologies </a></h3>

        </form>

</body>
<script type="text/javascript">
function validateForm() 
{
	    var x = document.forms["login"]["uname"].value;
	    if (x == null || x == "")
			{
	        alert(" User name must be filled out");
	        return false;
	   		 }
		var y = document.forms["login"]["pass"].value;
	    if (y == null || y == "")
			{
	        alert(" Password must be filled out");
	        return false;
	    	}
		var z = document.forms["login"]["compid"].value;
	    if (z == null || z == "")
			{
	        alert(" Company Id must be filled out");
	        return false;
	    	}
	
		

}
function checkEmpty(name) {
	  var x = document.forms["login"][name].value;
	    if (x == null || x == "")
			{
	        alert("This field cannot be empty! ");  
	document.forms["login"][name].focus();
	
	        return false;
	   		 }
}


</script>


</html>