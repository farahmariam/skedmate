<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="../css/scheduleStyles.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Setup your Scheduling project</title>
</head>
<body onload="setPreviousTeamCount()">
<img src="../images/skedmate3.jpg"  style="width:1500px;height:180px;">

<form name="settingform" method="post" action="${pageContext.request.contextPath}/CreateDatabase" onsubmit="return validate()" onkeypress="stopSubmitOnEnter(window.event);">
        
<div class="container">
    <div class="header  ">
     <h3 class="settingHeader">COMPANY SETUP </h3> 
    </div>
    <div class="imagemainbody">
    <center>
	  <tbody>
	  	 
            <table class="settingstable" border="1" width="80%" cellpadding="5" id="tableDatabase">
               		<tr>
                        <td class="index">Company Name</td>
                        <td><input type="text" name="companyname" id="companyname" value=""  onClick="clearSpan('divcompanyname')" onBlur="checkCompanyName()" /><span id="divcompanyname"></span></td>
                    </tr>
					<tr>
                        <td class="index">Company Address</td>
                        <td><input type="text" name="companyadd" id="companyadd" value=""  onClick="clearSpan('divcompanyaddress')"  /><span id="divcompanyaddress"></span></td>
                    </tr>
					<tr>
                        <td class="index">Company Phone Number</td>
                        <td><input type="number" name="companyphone" id="companyphone" value=""  onClick="clearSpan('divcompanyphone')" /><span id="divcompanyphone"></span></td>
                    </tr>

					
			</table>
	
	
		 <div class="header  ">
	     	<h3 class="settingBoxHeaderSmall">ADMINISTRATOR DETAILS</h3> 
	    </div>
   
     	<table class="settingstable" border="1" width="80%" cellpadding="5" id="tableDatabase">
            
					<tr>
                        <td class="index">Admin User Id/Name</td>
                        <td><input type="text" name="adminname" id="adminname" value=""  onClick="clearSpan('divadminname')"  /><span id="divadminname"></span></td>
                    </tr>
					<tr>
                        <td class="index">Admin User Password</td>
                        <td><input type="password" name="adminpass" id="adminpass" value=""  onBlur="checkPasswordMatch()" onClick="changePwdBoxStyle()"  /><span id="divadminpass"></span></td>
                    </tr>
					<tr>
                        <td class="index">Confirm Password</td>
                        <td><input type="password" name="confirmpass" id="confirmpass" value="" onBlur="checkPasswordMatch()" onClick="changePwdBoxStyle()"/><span id="pwdMatch"></span></td>
                    </tr>

					<tr>
                        <td class="index">Admin Email Id</td>
                        <td><input type="text" name="adminemail" id="adminemail"  value="" onBlur="checkEmail()" onClick="clearSpan('divadminmail')" /><span id="divadminmail"></span></td>
                    </tr>
              
	       </table>
	
	
	
			<div class="footer">
			</div>
			
			
			 <div class="header  ">
			
     			<h3 class="settingBoxHeaderSmall">WORKGROUP SETUP </h3> 
     		
   			 </div>
			
			 <table class="settingstable" border="1" width="80%" cellpadding="5" id="teamsnumtable">
					<tr >
                        <td title="Total number of teams in your company. If you dont have separate teams, enter 0 " class="index">Enter number of teams or groups in your work</td>
                        <td><input type="number" name="numteams" id="numteams" value="1" onBlur="createInputsForTeams()"  onClick="clearSpan('divnumteams')" /><span id="divnumteams"></span></td>

                    </tr>
                 
           </table>
		 <div id="divteams">
		 <table class="settingstable" border="1" width="80%" cellpadding="5" id="teamstable">
			<tr>
                        <td class="index">Team Name</td>
                        <td><input type="text" name="inputteam1" id="inputteam1"  value="WORKGROUP 1" onBlur="checkTeamName(this.value,'spanteam1','divteams')" onClick="clearSpan('spanteam1')" /><span id="spanteam1"></span></td>
            </tr>	
			
			<tr>
						<td > </td>
                        <td class="index">
							<table class="settingstable" border="1" cellpadding="5" width=80%"  id="limitstable">
							 <th colspan="8" title="Enter the minimum number of resources from this team ,required to work on weekdays, weekends and public holidays" class="index">Minimum Resources for day </th>
                       
							<tr>
							
                       			 <td >Mon:<input style="width: 4em" type="number" name="monlimitteam1" id="monlimitteam1"   value="1"  /></td>
                        		
                       			 <td >Tue:<input style="width: 4em" type="number" name="tuelimitteam1" id="tuelimitteam1"  value="1"  /></td>
                        		 <td >Wed:<input style="width: 4em" type="number" name="wedlimitteam1" id="wedlimitteam1"  value="1"  /></td>
                        		 <td >Thu:<input style="width: 4em" type="number" name="thulimitteam1" id="thulimitteam1"  value="1"  /></td>
                        		 
                       			 <td >Fri:<input style="width: 4em" type="number" name="frilimitteam1" id="frilimitteam1"  value="1"  /></td>
                        		 
                       			 <td>Sat:<input style="width: 4em" type="number" name="satlimitteam1" id="satlimitteam1"  value="1"  /></td>
                        		
							
                       			 <td >Sun:<input style="width: 4em" type="number" name="sunlimitteam1" id="sunlimitteam1"  value="1"  /></td>
                        		
                       			 <td >Pub Hol:<input style="width: 4em" type="number" name="hollimitteam1" id="hollimitteam1"  value="1"  /></td>
                        		 
							
							</tr>
						</td>
           			</table>

             </tr>     
           </table>
		</div>
		
		
		<div class="footer">
		</div>
			
			
		 <div class="header  ">
		
    			<h3 class="settingBoxHeaderSmall">RESOURCE STATUS </h3> 
    		
  		 </div>
  		 
  		 
  		 <div id="divstatus">
		 <table class="settingstable" border="1" width="80%" cellpadding="5" id="statustable">
			<tr>
                        <td class="index" title="This will be the default status of the resource">Default Working Status:</td>
                        <td><input type="text" name="inputstatus1" id="inputstatus1"  value="Onshift" onBlur="checkStatusName(this.value,'spanstatus1','divstatus')" onClick="clearSpan('spanstatus1')" /><span id="spanstatus1"></span></td>
						<td><input type="color" id="color1" name="color1" value="#bbfed7"> Display Colour </td>
						<td> <input title="Check the box if the status is considered as 'working or at work'." type="checkbox" id="working1" name="working1" value="1" checked onclick="alert('This is the default working status');return false;" onkeydown="alert('This is the default working status');return false;"/> Working Status?</td>
            </tr>
			<tr>
                        <td class="index">Status:</td>
                        <td><input type="text" name="inputstatus2" id="inputstatus2"  value="Offshift" onBlur="checkStatusName(this.value,'spanstatus2','divstatus')" onClick="clearSpan('spanstatus2')"   /><input type="button" id="btnDelete1" value="Delete Status" onClick="deleteStatus(this,'statustable')" class="button"/><span id="spanstatus2"></span></td>
						<td><input type="color" id="color2" name="color2" value="#e0b3ac"> Display Colour </td>
						<td> <input title="Check the box if the status is considered as 'working or at work'." type="checkbox" id="working2" name="working2" onclick="setCheckBoxValue(this.id)" > Working Status?</td>
            </tr>
			<tr>
                        <td class="index">Status:</td>
                        <td><input type="text" name="inputstatus3" id="inputstatus3"  value="Sick Leave" onBlur="checkStatusName(this.value,'spanstatus3','divstatus')" onClick="clearSpan('spanstatus3')"   /><input type="button" id="btnDelete2" value="Delete Status" onClick="deleteStatus(this,'statustable')" class="button"/><span id="spanstatus3"></span></td>
						<td><input type="color" id="color3" name="color3" value="#F8FEB1"> Display Colour </td>
						<td> <input title="Check the box if the status is considered as 'working or at work'." type="checkbox"id="working3"  name="working3" onclick="setCheckBoxValue(this.id)"> Working Status?</td>
            </tr>

			<tr>
                        <td class="index">Status:</td>
                        <td><input type="text" name="inputstatus4" id="inputstatus4"  value="Training" onBlur="checkStatusName(this.value,'spanstatus4','divstatus')" onClick="clearSpan('spanstatus4')"   /><input type="button" id="btnDelete3" value="Delete Status" onClick="deleteStatus(this,'statustable')" class="button"/><span id="spanstatus4"></span></td>
						<td><input type="color" id="color4" name="color4" value="#dcf5f3"> Display Colour </td>
						<td> <input title="Check the box if the status is considered as 'working or at work'." type="checkbox" id="working4" name="working4" onclick="setCheckBoxValue(this.id)"> Working Status?</td>
            </tr>

			<tr>
                        <td class="index">Status:</td>
                        <td><input type="text" name="inputstatus5" id="inputstatus5"  value="P.T.O" onBlur="checkStatusName(this.value,'spanstatus5','divstatus')" onClick="clearSpan('spanstatus5')"   /><input type="button" id="btnDelete4" value="Delete Status" onClick="deleteStatus(this,'statustable')" class="button"/><span id="spanstatus5"></span></td>
						<td><input type="color" id="color5" name="color5" value="#FEBAA1"> Display Colour </td>
						<td> <input title="Check the box if the status is considered as 'working or at work'." type="checkbox" id="working5" name="working5" onclick="setCheckBoxValue(this.id)"> Working Status?</td>


            </tr>
		
						
			
  		 </table>
  		 
  		  <table   width="80%"  id="addStatusBtnTable">
  		 	<tr>
                        <td  colspan="2"><input type="button" id="btnAddStatus" value="Add Status" onClick="addStatus()" class="button2"/></td>
           </tr>
  		 
  		 </table>
  		 </div>
  		 
  		 
  		 <div class="footer">
		</div>
			
			
		 <div class="header  ">
		
    			<h3 class="settingBoxHeaderSmall">DATA EDITING</h3> 
    		
  		 </div>
  		 
  	<table  class="settingstable" border="1" width="80%" cellpadding="5" id="freezetable">
		
		<tr>
                  <td class="index">Freeze Period</td>
                  <td title="During the freeze period, only the admin can change or edit the schedules. The freeze period is calculated from the current day"><input type="number" name="freezeperiod" id="freezeperiod" value="7"  onClick="clearSpan('divfreezeperiod')" onBlur="checkFreezePeriod()" />days. <span id="divfreezeperiod"></span></td>
        </tr>
	</table>
  		 
  		 <div class="footer">
		</div> 
  		 
			
	
		
		 <table  border="0" width="80%" cellpadding="5">
		
		  <tr>
             <td class="bold10"><input type="Submit" value="Save settings" class="skedSetup"/></td>
          </tr>
		</table>
		
		</tbody>
         </center>
</div>

				<input type="hidden" name="databasename" id="databasename" value=""/>
				<input type="hidden" name="statusCount" id="statusCount" value=""/>
				<input type="hidden" name="teamPreviousCount" id="teamPreviousCount" value=""/>
       
    

 </form>

</body>


<script type='text/javascript' >

function setCheckBoxValue(checkBoxId)
{
	var checkBox = document.getElementById(checkBoxId);
	if(checkBox.checked)
	{
		document.getElementById(checkBoxId).value="1";
	}
	else
	{
		document.getElementById(checkBoxId).value="0";
	}
	//alert("the value is: " + checkBox.value);
}

function hideDiv()
{
	//document.getElementById('divteams').style.display='none';
	//document.getElementById('divstatus').style.display='none';
}

function clearSpan(elementId)
{
	document.getElementById(elementId).innerHTML ="";	
}

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

function addStatus()
{
	var statusTable = document.getElementById("statustable");
	var numRows = statusTable.rows.length;
	
	var idCount = numRows+1;
	
	 var newRow = document.createElement('tr');   
	 newRow.appendChild( document.createElement('td') );
	 newRow.appendChild( document.createElement('td') );
	 newRow.appendChild( document.createElement('td') );
	 newRow.appendChild( document.createElement('td') );
	 
	 //text node (team name)
	 var statusName = document.createTextNode("Status:");
	
	 //input element for team name and its attributes
	 
	 var inputStatus = document.createElement('input');
	 inputStatus.setAttribute('type', 'text');
	 inputStatus.setAttribute('id',   'inputstatus'+idCount);
	 inputStatus.setAttribute('name', 'inputstatus'+idCount);
	 inputStatus.setAttribute('value', 'New Status');
	 
	
	  
	 var spanStatus = document.createElement('span');
	 spanStatus.id = "spanstatus" + idCount;
	 
	 
	 var btnDelete = document.createElement('input');
	 btnDelete.setAttribute('type',   'button');
	 btnDelete.setAttribute('id',   'btnDelete'+idCount);
	 btnDelete.setAttribute('name', 'btnDelete'+idCount);
	 btnDelete.setAttribute('value', 'Delete Status');
	 btnDelete.setAttribute('class', 'button');
	 btnDelete.onclick = function(){deleteStatus(this,'statustable')};  
	 
	 //create the color picker
	 
	 var colorPicker = document.createElement("INPUT");
	 colorPicker.setAttribute("type", "color");
	 colorPicker.setAttribute("name", "color"+idCount );
	 colorPicker.setAttribute("id", "color"+idCount);
	 colorPicker.setAttribute("value", getRandomColor());
	 
	 var colourText = document.createTextNode("Display Colour");
	 
	 //create the checkbox
	 var checkbox = document.createElement('input'); 
	 checkbox.setAttribute("type", "checkbox");
	 checkbox.setAttribute("name", "working"+idCount );
	 checkbox.setAttribute("id", "working"+idCount);
	 checkbox.setAttribute("title", "Check the box if the status is considered as 'working or at work'.");
	 
	 checkbox.onclick = function() { 
		 setCheckBoxValue("working"+idCount); 
     };
	 
	 var checkText = document.createTextNode("Working Status?");

	 
	
	
	
	
	 //appending text node to first col
	 newRow.cells[0].appendChild(statusName);
	 newRow.cells[0].className="index";
	 
	 //appending input and span to second col
	 newRow.cells[1].appendChild(inputStatus);
	 newRow.cells[1].appendChild(btnDelete);
	 newRow.cells[1].appendChild(spanStatus);
	 
	 
	 newRow.cells[2].appendChild(colorPicker);
	 newRow.cells[2].appendChild(colourText);
	 
	 newRow.cells[3].appendChild(checkbox);
	 newRow.cells[3].appendChild(checkText);
	 
	 statusTable.appendChild(newRow);
	 
	 
	 var container=document.getElementById("statustable");
	 var inputStatusArray=container.getElementsByTagName('input');
	    
		var spanCount =0;
		
		for(var s=0;s<inputStatusArray.length;s++)
		{
			
			var inputContainer = inputStatusArray[s];
			
			if(inputContainer.type=="text")	
			{
				spanCount=spanCount*1 +1;
				var inputValue = inputStatusArray[s].value;
				var inputId = inputStatusArray[s].id;
				
				var spanId = "spanstatus" + spanCount;
				inputContainer.onclick = (function(opt) {
				    return function() {
				    	clearSpan(opt);
					    };
					})(spanId);
				
				
				
				inputContainer.onblur = (function(value,span,div) {
				    return function() {
				    	checkStatusName(value,span,div);
					    };
					})(inputContainer.value,spanId,"divstatus");
				
				
				
				
			}
			
			
			
	   	}
	
	
	
	
}


function getRandomColor() {
    var letters = '0123456789ABCDEF';
    var color = '#';
    for (var i = 0; i < 6; i++ ) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

function deleteStatus( obj , tableName)
{   
	var row= obj.parentNode.parentNode;
	
	
	 var table = document.getElementById(tableName);
	
	 table.deleteRow(row.rowIndex);
	
}

function deletepreviousRowsCount(tableName, numRowsToDelete)
{
	var table = document.getElementById(tableName);
	
	if(table.rows.length >=numRowsToDelete)
	{
	
		
		for (var i=numRowsToDelete-1; i >=0; i--) {
		    table.deleteRow(i);
		}
	
	}
	
	
	
	
}



function setPreviousTeamCount()
{
	var numberRows = document.getElementById('numteams').value;
	document.getElementById('teamPreviousCount').value = numberRows;
	
}


function createInputsForTeams()
{
	
    var numberRows = document.getElementById('numteams').value;
   
    if(numberRows<1)
   	{
    	//document.getElementById('divteams').style.display='none';
    	document.getElementById("divnumteams").style.color = "red";
 		document.getElementById("divnumteams").innerHTML = "Number of teams should not be less than 1!";
 		document.getElementById('numteams').value="1";
 		createInputsForTeams();
 		 document.getElementById('divteams').style.display='block';
         return false;
    	
   	}
    
    /*
    var previousCount = document.getElementById('teamPreviousCount').value;
    
    
    var numDelRows;
    var numAddRows;
    if(numberRows < previousCount)
   	{
    	numDelRows = (previousCount - numberRows)*2;
    	deletepreviousRowsCount("teamstable",numDelRows);
    	return false;
   	}
    else if(numberRows > previousCount)
   	{
    	numAddRows = numberRows - previousCount;
   	}
    else if(numberRows = previousCount)
   	{
   		return false;
   	}
   */ 	
   deletepreviousRows("teamstable");
   	
   	
   
   //get the table teamstable and add required number of inputs for: team names and the min res requirements for each day and pub hol.
    var table = document.getElementById('teamstable');
    
    var tr = [];
    
    //var nameCount  = (previousCount*1 + 1);
    
    
	//  for(var i=0; i<numAddRows;i++)
	for(var i=1; i<=numberRows;i++)
   	 {
		 
    	 tr[i] = document.createElement('tr');   
    	 tr[i].appendChild( document.createElement('td') );
    	 tr[i].appendChild( document.createElement('td') );
    	 
    	 //text node (team name)
    	 var inputTeamName = document.createTextNode("Team Name");
    	
    	 //input element for team name and its attributes
    	 
    	 var inputTeam = document.createElement('input');
    	 inputTeam.setAttribute('type', 'text');
    	 inputTeam.setAttribute('id',   'inputteam'+i);
    	 inputTeam.setAttribute('name', 'inputteam'+i);
    	 inputTeam.setAttribute('value', 'WORKGROUP '+(i));
    	 /*
    	 inputTeam.setAttribute('id',   'inputteam'+nameCount);
    	 inputTeam.setAttribute('name', 'inputteam'+nameCount);
    	 inputTeam.setAttribute('value', 'WORKGROUP '+(nameCount));
    	 
    	 */
    	 //span element after the input element for error messages
    	 var spanTeam = document.createElement('span');
    	 spanTeam.id = "spanteam" + i;
    	
    	 
    	 var spanid="spanteam"+i;
    	 
    	
    	
    	 //appending text node to first col
    	 tr[i].cells[0].appendChild(inputTeamName);
    	 tr[i].cells[0].className="index";
    	 
    	 //appending input and span to second col
    	 tr[i].cells[1].appendChild(inputTeam);
    	 tr[i].cells[1].appendChild(spanTeam);
    	 
    	 //appending the row to the table
    	 table.appendChild(tr[i]);
    	 
    	 
    	 //Now create the limits table in new row.
    	 tr[i] = document.createElement('tr');   
    	 tr[i].appendChild( document.createElement('td') );
    	 tr[i].appendChild( document.createElement('td') );
    	 
    	 
    	 //text node (first col: empty)
    	 var firstColLimits = document.createTextNode("");
    	//appending text node to first col
    	 tr[i].cells[0].appendChild(firstColLimits);
    	 tr[i].cells[0].className="index";
    	 
    	
    	 var limitsTable = document.createElement("table");
    
    	 limitsTable.setAttribute('width', '80%');
    	 limitsTable.setAttribute('border', '1');
    	 limitsTable.setAttribute('cellpadding', '5');
    	 limitsTable.setAttribute('class', 'settingstable');
    	 limitsTable.setAttribute('id', 'limitstable' + i);
    	 
    	 var header = document.createElement("th");
    	 header.appendChild(document.createTextNode("Minimum Resources for day"));
    	 header.setAttribute('title', 'Enter the minimum number of resources from this team ,required to work on weekdays, weekends and public holidays');
    	 header.setAttribute('class','index');
    	 header.setAttribute('colspan','8');
    	 
    	
    	 
    	 limitsTable.appendChild(header);
    	 
    	 var newRow = document.createElement("tr");
    	 newRow.appendChild( document.createElement('td') );
    	 newRow.appendChild( document.createElement('td') );
    	 newRow.appendChild( document.createElement('td') );
    	 newRow.appendChild( document.createElement('td') );
    	 newRow.appendChild( document.createElement('td') );
    	 newRow.appendChild( document.createElement('td') );
    	 newRow.appendChild( document.createElement('td') );
    	 newRow.appendChild( document.createElement('td') );
    	 
    	 
    	 //monday
    	  var monNode = document.createTextNode("Mon:");
    	
    	 //input element for day limits
    	 var monInput = document.createElement('input');
    	 monInput.setAttribute('type', 'number');
    	 monInput.setAttribute('id',   'monlimitteam' + i);
    	 monInput.setAttribute('name', 'monlimitteam' + i);
    	 monInput.setAttribute('value', '1');
    	 monInput.setAttribute('style', 'width: 4em');
    	 
    	 //tuesday
   	     var tueNode = document.createTextNode("Tue:");
    	 
    	 var tueInput = document.createElement('input');
    	 tueInput.setAttribute('type', 'number');
    	 tueInput.setAttribute('id',   'tuelimitteam' + i);
    	 tueInput.setAttribute('name', 'tuelimitteam' + i);
    	 tueInput.setAttribute('value', '1');
    	 tueInput.setAttribute('style', 'width: 4em');
    	 
    	//wednesday
   	     var wedNode = document.createTextNode("Wed:");
    	 
    	 var wedInput = document.createElement('input');
    	 wedInput.setAttribute('type', 'number');
    	 wedInput.setAttribute('id',   'wedlimitteam' + i);
    	 wedInput.setAttribute('name', 'wedlimitteam' + i);
    	 wedInput.setAttribute('value', '1');
    	 wedInput.setAttribute('style', 'width: 4em');
    	 
    	//Thursday
   	     var thurNode = document.createTextNode("Thu:");
    	 
    	 var thurInput = document.createElement('input');
    	 thurInput.setAttribute('type', 'number');
    	 thurInput.setAttribute('id',   'thulimitteam' + i);
    	 thurInput.setAttribute('name', 'thulimitteam'+ i);
    	 thurInput.setAttribute('value', '1');
    	 thurInput.setAttribute('style', 'width: 4em');
    	 
    	//Friday
   	     var friNode = document.createTextNode("Fri:");
    	 
    	 var friInput = document.createElement('input');
    	 friInput.setAttribute('type', 'number');
    	 friInput.setAttribute('id',   'frilimitteam' + i);
    	 friInput.setAttribute('name', 'frilimitteam' + i);
    	 friInput.setAttribute('value', '1');
    	 friInput.setAttribute('style', 'width: 4em');
    	 
    	//Saturday
   	     var satNode = document.createTextNode("Sat:");
    	 
    	 var satInput = document.createElement('input');
    	 satInput.setAttribute('type', 'number');
    	 satInput.setAttribute('id',   'satlimitteam' + i);
    	 satInput.setAttribute('name', 'satlimitteam' + i);
    	 satInput.setAttribute('value', '1');
    	 satInput.setAttribute('style', 'width: 4em');
    	 
    	//Sunday
   	     var sunNode = document.createTextNode("Sun:");
    	 
    	 var sunInput = document.createElement('input');
    	 sunInput.setAttribute('type', 'number');
    	 sunInput.setAttribute('id',   'sunlimitteam' + i);
    	 sunInput.setAttribute('name', 'sunlimitteam' + i);
    	 sunInput.setAttribute('value', '1');
    	 sunInput.setAttribute('style', 'width: 4em');
    	 
    	//Public Holiday
   	     var holNode = document.createTextNode("Pub Hol:");
    	 
    	 var holInput = document.createElement('input');
    	 holInput.setAttribute('type', 'number');
    	 holInput.setAttribute('id',   'hollimitteam' + i);
    	 holInput.setAttribute('name', 'hollimitteam' + i);
    	 holInput.setAttribute('value', '1');
    	 holInput.setAttribute('style', 'width: 4em');
    	 
    	 
    	 newRow.cells[0].appendChild(monNode);
    	 newRow.cells[0].appendChild(monInput);
    	 
    	 newRow.cells[1].appendChild(tueNode);
    	 newRow.cells[1].appendChild(tueInput);
    	 
    	 newRow.cells[2].appendChild(wedNode);
    	 newRow.cells[2].appendChild(wedInput);
    	 
    	 newRow.cells[3].appendChild(thurNode);
    	 newRow.cells[3].appendChild(thurInput);
    	 
    	 newRow.cells[4].appendChild(friNode);
    	 newRow.cells[4].appendChild(friInput);
    	 
    	 newRow.cells[5].appendChild(satNode);
    	 newRow.cells[5].appendChild(satInput);
    	 
    	 newRow.cells[6].appendChild(sunNode);
    	 newRow.cells[6].appendChild(sunInput);
    	 
    	 newRow.cells[7].appendChild(holNode);
    	 newRow.cells[7].appendChild(holInput);
    	 
    	 
    	 //add this row to limitstable
    	 limitsTable.appendChild(newRow);
    	 
    	 
    	 
    	 
    	 
    	 //add the limitstable to second col of row
    	 tr[i].cells[1].appendChild(limitsTable);
    	 tr[i].cells[1].className="index";
    	 table.appendChild(tr[i]);
    	 
    	 
    	 //nameCount = (nameCount*1 + 1);
    	 setPreviousTeamCount();
    	 
    	 
    	 
    	 
   	}
    
    var container=document.getElementById("teamstable");
	var inputTeamsArray=container.getElementsByTagName('input');
    
	var spanidCnt=1;
	var spanidValue;
	for(var s=0;s<inputTeamsArray.length;s++)
	{
		var inputContainer = inputTeamsArray[s];
		
		var inputValue = inputTeamsArray[s].value;
		var inputId = inputTeamsArray[s].id;
		var teamString = "inputteam";
	    
		
		
		if(inputId.includes(teamString))
		{
			spanidValue="spanteam"+spanidCnt;
			inputContainer.onclick = (function(opt) {
			    return function() {
			    	clearSpan(opt);
				    };
				})(spanidValue);
			
			
			
			inputContainer.onblur = (function(value,span,div) {
			    return function() {
			    	checkTeamName(value,span,div);
				    };
				})(inputContainer.value,spanidValue,"divteams");
			
			spanidCnt = spanidCnt*1 + 1;
		
		}
		
		
		
   	}
   
	
}

function checkStatusName(strStatus, divId, divName)
{
	
	var container=document.getElementById(divName);
	var spanArray=container.getElementsByTagName('span');
	

	if (strStatus=="")
	{
		for(var s=0;s<spanArray.length;s++)
		{
			 
			  if(spanArray[s].id==divId)
				  {
				  spanArray[s].style.color = "red";
				  spanArray[s].innerHTML =  "Please enter the status name.";
				  }
		}
		
		
		return false;
		
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
	var cpass = document.getElementById("confirmpass").value;

	
	var numTeams = document.getElementById('numteams').value;
	
	var databaseName="";
	
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
	
	
    if (cpass == null || cpass == "")
	{
        document.getElementById("pwdMatch").style.color = "red";
		document.getElementById("pwdMatch").innerHTML = "Please confirm password.";
		return false;
    }

	if (adminPass != cpass) 
	{
		document.getElementById("pwdMatch").innerHTML = "Passwords do not match!";
	    document.forms["editresource"]["pass"].style.borderColor = "#E34234";
	    document.forms["editresource"]["confirmpass"].style.borderColor = "#E34234";
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
        document.getElementById("divnumteams").innerHTML = "Please enter the number of teams (1 or more)";
		return false;
	}
	
	
	if(numTeams>=1)
	{
		var container=document.getElementById("divteams");
		var inputTeamsArray=container.getElementsByTagName('input');
		var spanArray = container.getElementsByTagName('span');
		
		var spanidCnt=0;
		
		
		for(var s=0;s<inputTeamsArray.length;s++)
		{
				
				 var inputContainer = inputTeamsArray[s];
				 var inputContainerValue = inputContainer.value;
				 var inputId = inputTeamsArray[s].id;
				 var teamString = "inputteam";
				    
				if(inputId.includes(teamString))
				{
					 if(inputContainerValue==null || inputContainerValue.trim()=="")
					 {
						  
						  spanArray[spanidCnt].style.color = "red";
						  spanArray[spanidCnt].innerHTML =  "Please enter the team name.";
						  return false;
				     }
					 spanidCnt=spanidCnt*1 + 1;
					
				}
				else
				{
					if(inputContainerValue==null || inputContainerValue.trim()=="")
					 {
						  
						  alert("Please make sure all the limits are filled in.")
						  return false;
				     }
					else if(inputContainerValue<1)
					{
						alert("Please make sure all the limits are not less than 1.")
						 return false;
					}
					 
				}
			 
		}
	
	}
	else
	{
		document.getElementById("divnumteams").style.color = "red";
        document.getElementById("divnumteams").innerHTML = "Please enter the number of teams (1 or more)";
		return false;
	
	
	}
	
	//validate status
	
	var statusTable = document.getElementById('statustable');
	var numStatusCount = statusTable.rows.length;
	
	for(var f=0;f<numStatusCount;f++)
	{
		
		if(document.getElementById("working" + (f*1+1)).checked)
		{
			document.getElementById("working" + (f*1+1)).value="1";
		}
		else
		{
			document.getElementById("working" + (f*1+1)).value="0";
		}
		
	}
	
	
	//loop through the rows..get the status names and check if blank
	
	 
	 var inputStatusArray=statusTable.getElementsByTagName('input');
	 var spanArray = statusTable.getElementsByTagName('span');
	    
	 var spanCount =0;
	 
	 //setting the checkbox values
	 for(var count=0; count<numStatusCount;count++)
	 {
		var checkBoxId = "working" + (count*1+1);
		var checkBox = document.getElementById(checkBoxId);
		//alert(checkBox.checked);
		
		
	 }
		
	 for(var s=0;s<inputStatusArray.length;s++)
	 {
		 
			
			var inputContainer = inputStatusArray[s];
			
			if(inputContainer.type=="text")	
			{
				
				var inputValue = inputStatusArray[s].value;
				
				var inputId = inputStatusArray[s].id;
				
				
				
				if(inputValue==null || inputValue.trim()=="")
				 {
					
					spanArray[spanCount].style.color = "red";
					spanArray[spanCount].innerHTML =  "Please enter the status name.";
					return false;
					
					
				 }
				spanCount=spanCount*1 +1;
			}
			
	 }
	
	//set the number of statuses to hidden input
	document.getElementById("statusCount").value=numStatusCount;
	
	
	
	var freeze = checkFreezePeriod();
	if(freeze ==false)
	{
		return false;
	}
	
	
	
	
	
	
	//setup the database name
	var currentTime = new Date();
	var year = currentTime.getFullYear();
	var compNameNoSpace = compName.replace(/\s+/g, '');
	var subCompName = compNameNoSpace.substring(0, 4).toLowerCase();
	
	databaseName = subCompName + year;
	//alert(databaseName);
	
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

function checkPasswordMatch()
{
			clearSpan("pwdMatch");
			var z = document.forms["settingform"]["adminpass"].value;
			var cpass = document.forms["settingform"]["confirmpass"].value;
	    	if (z != null && z != "" && cpass != null && cpass != "" )
			{
	        	if (z != cpass) 
				 	{
						document.getElementById("pwdMatch").style.color = "red";
        				document.getElementById("pwdMatch").innerHTML = "PASSWORDS DO NOT MATCH";
        				document.forms["settingform"]["adminpass"].value = "";
        				document.forms["settingform"]["confirmpass"].value = "";
				        
				        document.forms["settingform"]["adminpass"].style.borderColor = "#E34234";
				        document.forms["settingform"]["confirmpass"].style.borderColor = "#E34234";
				        
				    }

	    	}
			
	    	
	
	
	
}


function changePwdBoxStyle()
{
	
	document.forms["settingform"]["adminpass"].style.borderColor = "";
    document.forms["settingform"]["confirmpass"].style.borderColor = "";
    clearSpan("pwdMatch");
    clearSpan("divadminpass");
	
	}

function stopSubmitOnEnter (e) {
	  var eve = e || window.event;
	  var keycode = eve.keyCode || eve.which || eve.charCode;

	  if (keycode == 13) {
	    eve.cancelBubble = true;
	    eve.returnValue = false;

	    if (eve.stopPropagation) {   
	      eve.stopPropagation();
	      eve.preventDefault();
	    }

	    return false;
	  }
	}

</script>
</html>