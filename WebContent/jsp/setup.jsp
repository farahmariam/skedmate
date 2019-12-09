<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="../css/scheduleStyles.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Setup your Scheduling project</title>
</head>
<body onload="hideDiv();">

<div class="container">
    <div class="header  ">
     <h3 class="settingHeader">COMPANY SETUP </h3> 
    </div>
    <div class="mainbody1">
    	<form name="settingform" method="post" action="${pageContext.request.contextPath}/CreateDatabase" onsubmit="return validate()" >
            <center>
			 <tbody>
            <table class="settingstable" border="1" width="80%" cellpadding="5" id="tableDatabase">
               		<tr>
                        <td class="index">Company Name</td>
                        <td><input type="text" name="companyname" id="companyname" value=""  onClick="clearSpan('divcompanyname')" onBlur="checkCompanyName()" /><span id="divcompanyname"></span></td>
                    </tr>
					<tr>
                        <td class="index">Admin User Id/Name</td>
                        <td><input type="text" name="adminname" id="adminname" value=""  onClick="clearSpan('divadminname')"  /><span id="divadminname"></span></td>
                    </tr>
					<tr>
                        <td class="index">Admin User Password</td>
                        <td><input type="password" name="adminpass" id="adminpass" value=""  onClick="clearSpan('divadminpass')"  /><span id="divadminpass"></span></td>
                    </tr>

					<tr>
                        <td class="index">Admin Email Id</td>
                        <td><input type="text" name="adminemail" id="adminemail"  value="" onBlur="checkEmail()" onClick="clearSpan('divadminmail')" /><span id="divadminmail"></span></td>
                    </tr>
               <!--  
                    <tr>
                        <td class="index">Database Name</td>
                        <td><input type="text" name="databasename" id="databasename" value=""  onClick="clearSpan('divdatabasename')" onBlur="checkDatabaseName()" /><span id="divdatabasename"></span></td>
                    </tr> -->
			</table>
			<div class="footer">
			</div>
			
			
			 <div class="header  ">
			
     			<h3 class="settingBoxHeaderSmall">Team Setup </h3> 
     		
   			 </div>
			
			 <table class="settingstable" border="1" width="80%" cellpadding="5" id="teamsnumtable">
					<tr >
                        <td title="Total number of teams in your company. If you dont have separate teams, enter 0 " class="index">Enter number of teams or groups in your work</td>
                        <td><input type="number" name="numteams" id="numteams" value="" onBlur="createInputsForTeams()"  onClick="clearSpan('divnumteams')" /><span id="divnumteams"></span></td>

                    </tr>
                 
           </table>
		 <div id="divteams">
		 <table class="settingstable" border="1" width="80%" cellpadding="5" id="teamstable">
					
                 
           </table>
		</div>
	
		 <div class="header  ">
     			
     			<h3 class="settingBoxHeaderSmall">Work Status Setup </h3> 
   			 </div>
		 <table class="settingstable" border="1" width="80%" cellpadding="5" id="statussnumtable">
					<tr >
                        <td title="Total number of work Status in your company. For example: onshift and offshift"  class="index">Enter number of work status</td>
                        <td><input type="number" name="numstatus" id="numstatus" value="" onBlur="createInputsForStatus()" onClick="clearSpan('divnumstatus')" /><span id="divnumstatus"></span></td>

                    </tr>
                 
           </table>
 		<div id="divstatus">
		 <table class="settingstable" border="1" width="80%" cellpadding="5" id="statustable">
					
                 
           </table>
		</div>
		
		 <table  border="0" width="80%" cellpadding="5">
		
		  <tr>
             <td class="index"><input type="Submit" value="Save settings" class="admin"/></td>
          </tr>
		</table>
		
		</tbody>
         </center>

				<input type="hidden" name="databasename" id="databasename" value=""/>
        </form>
      
     </div>
   
</div> 

<div class="footer">
</div>



</body>


<script type='text/javascript' >
function hideDiv()
{
	document.getElementById('divteams').style.display='none';
	document.getElementById('divstatus').style.display='none';
}

function clearSpan(elementId)
{
	document.getElementById(elementId).innerHTML ="";	
}

function checkCompanyName()
{
	var companyName = document.getElementById("companyname").value;
	
	if (companyName.trim()=="")
	{
		
		document.getElementById("divcompanyname").style.color = "red";
		document.getElementById("divcompanyname").innerHTML = "Please enter the company name.";
		return false;
		
	}
	var compNameTrimmed = companyName.replace(/\s+/g, '');
	if(compNameTrimmed.length<4)
	{
		document.getElementById("divcompanyname").style.color = "red";
        document.getElementById("divcompanyname").innerHTML = "Company name should not be less than 4 letters!";
		return false;
	}
	
	
	
}

function clearSpanTeamDiv(divId,divName)
{
	var container=document.getElementById(divName);
	var spanArray=container.getElementsByTagName('span');
	for(var s=0;s<spanArray.length;s++)
	{
		  spanText=spanArray[s].innerHTML;
		  if(spanArray[s].id==divId)
			  {
			  
			  spanArray[s].innerHTML =  "";
			  }
	}
		
		
		
	
	
}



function deletepreviousRows(tableName)
{
	var table = document.getElementById(tableName);
	while(table.rows.length > 0) 
	{
		  table.deleteRow(0);
	}
	
}

function createInputsForStatus()
{
	
	deletepreviousRows("statustable");
    var numberRows = document.getElementById('numstatus').value;
    
    if(numberRows<0)
   	{
    	document.getElementById('divstatus').style.display='none';
    	document.getElementById("divnumstatus").style.color = "red";
 		document.getElementById("divnumstatus").innerHTML = "Entry should not be less than 0!";
 		document.getElementById('numstatus').value="";
        return false;
    	
   	}
    
    if(numberRows==0)
   	{
    	document.getElementById('divstatus').style.display='none';
   	}
    else
   	{
    	document.getElementById('divstatus').style.display='block';
   	
   	}
   
   
    var table = document.getElementById('statustable');
    
    var tr = [];
    for(var i=0; i<numberRows;i++)
   	{
    	 tr[i] = document.createElement('tr');   
    	 tr[i].appendChild( document.createElement('td') );
    	 tr[i].appendChild( document.createElement('td') );
    	 
    	 var inputStatusName = document.createTextNode("Status Name:");
    	 
    	 var spanStatus = document.createElement('span');
    	 spanStatus.id = "spanstatus" + i;
    	
    	

    	 
    	 var inputStatus= document.createElement('input');
    	 inputStatus.setAttribute('type', 'text');
    	 inputStatus.setAttribute('id',   'inputstatus'+i);
    	 inputStatus.setAttribute('name', 'inputstatus'+i);
    	 
    	 var spanid="spanstatus"+i;
    	// inputStatus.onblur = function onblur(event){checkTeamName(this.value, spanid,"divstatus");}
    	// inputStatus.onclick = function onclick(event){clearSpanTeamDiv(spanTeam.id,"divstatus");}
    	
    	 tr[i].cells[0].appendChild(inputStatusName);
    	
    	 tr[i].cells[0].className="index";
    	 tr[i].cells[1].appendChild(inputStatus);
    	 tr[i].cells[1].appendChild(spanStatus);
    	 
    	 table.appendChild(tr[i]);
    	 
   	}
    
    var container=document.getElementById("statustable");
	var inputTeamsArray=container.getElementsByTagName('input');
    
	for(var s=0;s<inputTeamsArray.length;s++)
	{
		var inputContainer = inputTeamsArray[s];
		var spanid="spanstatus"+s;
		//inputContainer.onclick = function onclick(event){clearSpan(spanid);}
	
		inputContainer.onclick = (function(opt) {
		    return function() {
		    	clearSpan(opt);
			    };
			})(spanid);
	}
	
}
function createInputsForTeams()
{
   	deletepreviousRows("teamstable");
    var numberRows = document.getElementById('numteams').value;
    if(numberRows<0)
   	{
    	document.getElementById('divteams').style.display='none';
    	document.getElementById("divnumteams").style.color = "red";
 		document.getElementById("divnumteams").innerHTML = "Entry should not be less than 0!";
 		document.getElementById('numteams').value="";
         return false;
    	
   	}
    
    if(numberRows==0)
   	{
    	document.getElementById('divteams').style.display='none';
   	}
    else
   	{
    	document.getElementById('divteams').style.display='block';
   	
   	}
   
   
    var table = document.getElementById('teamstable');
    
    var tr = [];
    for(var i=0; i<numberRows;i++)
   	{
    	 tr[i] = document.createElement('tr');   
    	 tr[i].appendChild( document.createElement('td') );
    	 tr[i].appendChild( document.createElement('td') );
    	 
    	 var inputTeamName = document.createTextNode("Team Name:");
    	 
    	
    	
    	

    	 
    	 var inputTeam = document.createElement('input');
    	 inputTeam.setAttribute('type', 'text');
    	 inputTeam.setAttribute('id',   'inputteam'+i);
    	 inputTeam.setAttribute('name', 'inputteam'+i);
    	 
    	 
    	 var spanTeam = document.createElement('span');
    	 spanTeam.id = "spanteam" + i;
    	
    	 
    	 var spanid="spanteam"+i;
    	// inputTeam.onblur = function onblur(event){checkTeamName(this.value, spanTeam.id,"divteams");}
    	// inputTeam.onclick = function onclick(event){clearSpanTeamDiv(spanTeam.id,"divteams");}
    	
    	 tr[i].cells[0].appendChild(inputTeamName);
    	
    	 tr[i].cells[0].className="index";
    	 tr[i].cells[1].appendChild(inputTeam);
    	 tr[i].cells[1].appendChild(spanTeam);
    	 
    	 table.appendChild(tr[i]);
    	 
   	}
    
    var container=document.getElementById("teamstable");
	var inputTeamsArray=container.getElementsByTagName('input');
    
	for(var s=0;s<inputTeamsArray.length;s++)
	{
		var inputContainer = inputTeamsArray[s];
		var spanid="spanteam"+s;
		//inputContainer.onclick = function onclick(event){clearSpan(spanid);}
	
		inputContainer.onclick = (function(opt) {
		    return function() {
		    	clearSpan(opt);
			    };
			})(spanid);
		
		
		
   	}
    
	
}

function checkTeamName(strTeam, divId, divName)
{
	var container=document.getElementById(divName);
	var spanArray=container.getElementsByTagName('span');
	

	if (strTeam=="")
	{
		for(var s=0;s<spanArray.length;s++)
		{
			 
			  if(spanArray[s].id==divId)
				  {
				  spanArray[s].style.color = "red";
				  spanArray[s].innerHTML =  "Please enter the team name.";
				  }
		}
		
		
		return false;
		
	}
		
	
}



function checkDatabaseName()
{
	
	var databaseName = document.getElementById("databasename").value;
	
	if (databaseName=="")
	{
		
		document.getElementById("divdatabasename").style.color = "red";
		document.getElementById("divdatabasename").innerHTML = "Please enter the database name.";
		return false;
		
	}
	
	if (databaseName.indexOf(' ') !== -1)
	{
		
		document.getElementById("divdatabasename").style.color = "red";
		document.getElementById("divdatabasename").innerHTML = "Name should not contain space!";
		return false;
		
	}
	var isnum = /^\d+$/.test(databaseName);
	if(isnum)
	{
		
		document.getElementById("divdatabasename").style.color = "red";
		document.getElementById("divdatabasename").innerHTML = "Database Name should not be only numbers!";
		return false;
	}
	
	var pattern = new RegExp(/[@~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/); //unacceptable chars
    if (pattern.test(databaseName)) {
       
        document.getElementById("divdatabasename").style.color = "red";
		document.getElementById("divdatabasename").innerHTML = "Please only use standard alphanumerics.";
        return false;
    }

	
	//ajax to check if the databasename exists!
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

	        	var exists  = "exists";
	        	var error = "error";
	        	var notexists = "not exists";
	        	
	        	if(trimmedResponse.toLowerCase()==exists.toLowerCase())
            	{
            		
            		document.getElementById("divdatabasename").style.color = "red";
            		document.getElementById("divdatabasename").innerHTML = "Database Exists! Choose another name!";
            		document.getElementById("databasename").value = "";
            		return false;
            		
            		
            	}
	        	else if(trimmedResponse.toLowerCase()==notexists.toLowerCase())
        		{
	        		document.getElementById("divdatabasename").style.color = "black";
           		 	document.getElementById("divdatabasename").innerHTML = "Valid Name!";
           		 	return true;
           		 	
        		}
           		
	        	else if(trimmedResponse.toLowerCase()==error.toLowerCase())
        		{
	        		document.getElementById("divdatabasename").style.color = "red";
           		 	document.getElementById("divdatabasename").innerHTML = "Error!";
           		 	return false;
        		}
           		
	           
	            
	           
	        }
		};
	
	
	 try
	    {
		  
		 var posturl = "databasename=" + databaseName ;
	    
		 
		 xmlhttpobj.open("POST", "${pageContext.request.contextPath}/CheckDatabaseName", true);
		
		 xmlhttpobj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xmlhttpobj.send(posturl);
		   
		   
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    	return false;
	    }
	    
}
function validate()
{
	var compName=document.getElementById("companyname").value;
	var adminName = document.getElementById("adminname").value;
	var adminPass = document.getElementById("adminpass").value;
	var adminEmail = document.getElementById("adminemail").value;
	
	var numTeams = document.getElementById('numteams').value;
	var numStatus = document.getElementById('numstatus').value;
	var databaseName="";
	
	/*
	if(compName==null || compName.trim()=="")
	{
		
		document.getElementById("divcompanyname").style.color = "red";
        document.getElementById("divcompanyname").innerHTML = "Please enter your company name.";
		return false;
	}
	
	
	var compNameTrimmed = compName.replace(/\s+/g, '');
	if(compNameTrimmed<4)
	{
		document.getElementById("divcompanyname").style.color = "red";
        document.getElementById("divcompanyname").innerHTML = "Company name should not be less than 4 letters!";
		return false;
	}
	*/
	var validCompanyName=checkCompanyName();
	if(validCompanyName==false)
	{
		return false;
	}
	if(adminName==null || adminName.trim()=="")
	{
		document.getElementById("divadminname").style.color = "red";
        document.getElementById("divadminname").innerHTML = "Please enter admin id.";
		return false;
	}
	if(adminPass==null || adminPass.trim()=="")
	{
		document.getElementById("divadminpass").style.color = "red";
        document.getElementById("divadminpass").innerHTML = "Please enter admin password.";
		return false;
	}
	
	//emailchecking
	if(adminEmail==null || adminEmail.trim()=="")
	{
		document.getElementById("divadminmail").style.color = "red";
        document.getElementById("divadminmail").innerHTML = "Please enter admin email id.";
		return false;
	}
	
	var isEmail = checkEmail();
	if(isEmail==false)
	{
		return false;
	}
	
	
	//check teams
	if(numTeams==null || numTeams.trim()=="")
	{
		document.getElementById("divnumteams").style.color = "red";
        document.getElementById("divnumteams").innerHTML = "Please enter the number of teams (0 or more)";
		return false;
	}

	//check teams
	if(numStatus==null || numStatus.trim()=="")
	{
		document.getElementById("divnumstatus").style.color = "red";
        document.getElementById("divnumstatus").innerHTML = "Please enter the number of work status options (0 or more)";
		return false;
	}
	
	
	
	if(numTeams>0)
	{
		var container=document.getElementById("divteams");
		var inputTeamsArray=container.getElementsByTagName('input');
		var spanArray = container.getElementsByTagName('span');
		for(var s=0;s<inputTeamsArray.length;s++)
		{
			 var inputContainer = inputTeamsArray[s];
			 var inputContainerValue = inputContainer.value;
			
			 
			 if(inputContainerValue==null || inputContainerValue.trim()=="")
			 {
				  spanArray[s].style.color = "red";
				  spanArray[s].innerHTML =  "Please enter the team name.";
				  return false;
		     }
			 
		}
			
	
	}
	
	if(numStatus>0)
	{
		var container=document.getElementById("divstatus");
		var inputStatusArray=container.getElementsByTagName('input');
		var spanArray = container.getElementsByTagName('span');
		for(var s=0;s<inputStatusArray.length;s++)
		{
			 var inputContainer = inputStatusArray[s];
			 var inputContainerValue = inputContainer.value;
			
			 
			 if(inputContainerValue==null || inputContainerValue.trim()=="")
			 {
				  spanArray[s].style.color = "red";
				  spanArray[s].innerHTML =  "Please enter the status name.";
				  return false;
		     }
			 
		}
			
	
	}
	
	//setup the database name
	var currentTime = new Date();
	var year = currentTime.getFullYear();
	var compNameNoSpace = compName.replace(/\s+/g, '');
	var subCompName = compNameNoSpace.substring(0, 4).toLowerCase();
	
	databaseName = subCompName + year;
	alert(databaseName);
	
	var result = validateDatabaseName(databaseName);
	
	if(result==true)
	{
		//document.forms[settingform][databasename].value = databaseName;
		//alert("valid");
		document.getElementById("databasename").value=databaseName;
	}
	else if(result==false)
	{
		//alert("not valid. already exists");
		
		
		do
		{
			databaseName="";
			var random = Math.floor((Math.random() * 100) + 1);
			databaseName = subCompName + random;
			
			}while(validateDatabaseName(databaseName)!=true)
		//alert(databaseName);
		
		document.getElementById("databasename").value=databaseName;
		
	}
	else if(result=="error")
	{
		alert("error occured when validating details. Could not connect to database.");
		return false;
	}
	
	
	
	
}





function checkEmail()
{
	clearSpan("divadminmail");
	
	var email =  document.forms["settingform"]["adminemail"].value;
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
		
     }
	
	
	
	
}


function validateDatabaseName(databaseName)
{
	//ajax to check if the databasename exists!
	if (window.XMLHttpRequest) 
	{
		   xmlhttpobj = new XMLHttpRequest();
	} else 
	{
		    // code for IE6, IE5
		    xmlhttpobj = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	
		var result="";
		
		xmlhttpobj.onreadystatechange = function()
	    {
	        if(xmlhttpobj.readyState == 4 && xmlhttpobj.status == 200)
	        {
	        	var trimmedResponse = xmlhttpobj.responseText.replace(/^\s*/,'').replace(/\s*$/,'').toLowerCase();
	        	 var exists  = "exists";
	         	var error = "error";
	         	var notexists = "not exists";
	         	
	         	if(trimmedResponse.toLowerCase()==exists.toLowerCase())
	         	{
	         		
	         		
	         		result = false;
	         		
	         		
	         	}
	         	else if(trimmedResponse.toLowerCase()==notexists.toLowerCase())
	     		{
	         		
	        		 	result =  true;
	        		 	
	     		}
	        		
	         	else if(trimmedResponse.toLowerCase()==error.toLowerCase())
	     		{
	         		
	         		result =  error;
	     		}
   
	        }
		};
	
	
	 try
	    {
		  
		 var posturl = "databasename=" + databaseName ;
	    
		 
		 xmlhttpobj.open("POST", "${pageContext.request.contextPath}/CheckDatabaseName", false);
		
		 xmlhttpobj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		 xmlhttpobj.send(posturl);
		   
		   
		}
	    catch(e)
	    {
	    	alert("unable to connect to server");
	    	return false;
	    }
	    
	 return result;  
   		
	    
}




</script>
</html>