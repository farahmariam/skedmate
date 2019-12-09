<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="../css/scheduleStyles.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recover login details</title>
</head>
<body>

<form name="forgotpassword" method="post" action="${pageContext.request.contextPath}/EmailAdminLoginCredentials" onsubmit="return checkEmail()" >
            <center>
            <table border="1" width="50%" cellpadding="5">
                <thead>
                    <tr>
                        <th class="backcolourpurple bold8" colspan="2">If you are an administrator, please enter the email id that you provided when setting up the project.If you are not an administrator, please contact the skedmate administrator to recover login details.</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="index">Administrator Email Id</td>
                        <td><input type="text" name="email" value="" onBlur="checkEmail()" onClick="clearSpan('divadminmail')" /><span id="divadminmail"></span></td>
                    </tr>
 					<tr>
                        <td class="index" ><input type="submit" value="Submit" /></td>
                        <td class="index"><input type="reset" value="Reset" /> </td>
                    </tr>
				</tbody>
				</table>
			</center>
			</form>

</body>


<script type='text/javascript'>

function clearSpan(elementId)
{
	document.getElementById(elementId).innerHTML ="";	
}


function checkEmail()
{
	clearSpan("divadminmail");
	
	var email =  document.forms["forgotpassword"]["email"].value;
	if(email!="")
	{
		var reEmail = /^(?:[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+\.)*[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+@(?:(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!\.)){0,61}[a-zA-Z0-9]?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!$)){0,61}[a-zA-Z0-9]?)|(?:\[(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\]))$/;

	  	if(!email.match(reEmail)) 
	  	{
	  		document.getElementById("divadminmail").style.color = "red";
	        document.getElementById("divadminmail").innerHTML = "INVALID EMAIL";
	        //document.forms["settingform"]["adminemail"].value = "";
	        return false;
	  	}
	  	
	  	
	  	
	  	//ajax to check if this email id is present in global table
		if (window.XMLHttpRequest) 
		{
			   xmlhttp = new XMLHttpRequest();
		} else 
		{
			    // code for IE6, IE5
			    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		
		
		
		
		//ajax tocheck if event already over or within 2 months
		
		
			var returnValue = "0";
			
			 xmlhttp.onreadystatechange = function()
		    {
		        if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
		        {
		        	var trimmedResponse = xmlhttp.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();

		        	
		        	var emailExists  = "exists";
			    	var emailNotExists = "not exists";
			    	
					//alert(trimmedResponse);
			    	if(trimmedResponse.toLowerCase()  == emailNotExists.toLowerCase())
			    	{
			    		document.getElementById("divadminmail").style.color = "red";
				        document.getElementById("divadminmail").innerHTML = "This is not the administrator email id provided to the setup!";
				        returnValue="1";
					}
			    	else if(trimmedResponse.toLowerCase()  == emailExists.toLowerCase())
			   		{
			    		returnValue="0";
			   		}
		            
		           
		        }
			};
			
			try
		    {
			 
			 var posturl = "email=" + email;
		    
			 
			 xmlhttp.open("POST", "${pageContext.request.contextPath}/checkAdminEmailExists", true);
			
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
		    else
	    	{
		    	return true;
	    	}
		
	  	
	  	
	  	
	  	
		
     }
	else
	{
		
		document.getElementById("divadminmail").style.color = "red";
        document.getElementById("divadminmail").innerHTML = "Please enter the administrator email id.";
        
        return false;
	
	}
	
	
	
	
}

</script>
</html>