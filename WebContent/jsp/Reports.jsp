<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View data and reports</title>
<link rel="stylesheet" href="../css/scheduleStyles.css">
</head>
<body onload="hideReportAndDateDiv()" >
<img src="../images/skedmate3.jpg"  style="width:1500px;height:180px;">

<div class="container">

    <div class="header  ">
      <h3 class="settingHeader" >SELECT REPORT TYPE</h3> 
    </div>
    
  	<div class="mainbody" id="formdiv"> 
  	
  	<form name="report" method="post" action="#"  onsubmit="showReport();return false;" >
  	 <tbody>
  	
    <table border="0" width="100%" cellpadding="5"> 
    
		  <tr>
			 <td  class="index">Report type </td>
		 		
	       	 <td><select name="reporttype" id ="reporttype" onchange="showDateDiv()"> 
							<option value="" selected > <b> Select report type</b> </option>
							<option value="totalResStatusCharts"  > <b> Total Resources Working/NotWorking Status breakdown </b> </option>
							<option value="totalRes"  > <b> Total members in each team</b> </option>
							
							
				</select>
			</td>
				
			
					
		</tr>
		 
		</table> 
		
		<div id="charttype">
		
	 <table border="0" width="100%" cellpadding="5">   
		 <tr>
			 <td  class="index">Chart Type </td>
		 		
	       	 <td><select name="graphType" id ="graphType" > 
							
							<option value="pie" selected  > <b> Pie Chart</b> </option>
							<option value="bar"  > <b> Bar Graph</b> </option>
				</select>
			</td>
			
		</tr>
		</table>
		</div>
		
		<div id="fromdateinputs">
		 <table border="0" width="100%" cellpadding="5"> 
			 <tr>
			    <td   class="index">From:</td>
			    <td><input type="date" name="fromdate" id="fromdatepicker" onchange="hideReportDiv()" /> </td>
			 </tr>	
		</table>
		</div>	
		<div id="todateinputs">
		 <table border="0" width="100%" cellpadding="5">  
			  <tr>
			    <td   class="index">To:</td>
			    <td><input type="date" name="todate" id="todatepicker"  onchange="hideReportDiv()" /> </td>
			 </tr>	
			 </table>
		</div>
		
		
		<div id="divnumdays">
		
		 <table border="0" width="100%" cellpadding="5">   
			 <tr>
				 <td  class="index">Number of days </td>
			 		
		       	 <td><select name="numdays" id ="numdays" > 
								
								<option value="0" selected  > <b> 1</b> </option>
								<option value="1"  > <b>2</b> </option>
								<option value="2"  > <b>3</b> </option>
								<option value="3"  > <b>4</b> </option>
								<option value="4"  > <b>5</b> </option>
								<option value="5"  > <b>6</b> </option>
								<option value="6"  > <b>7</b> </option>
					</select>
				</td>
				
			</tr>
			</table>
		</div>
		
		 <table border="0" width="100%" cellpadding="5"> 
			 <tr>
			    

			    <td colspan="2" ><input class="admin" type="submit" name="Submit" value="Submit" /> </td>
			 </tr>	
		</table>
		</tbody>
	
		    
	</form>   
	</div>
	</div>

<div class="header"> </div>


<div class="container" id="reportdiv">

    <div class="header">
      <h3 class="settingHeader" >REPORTS</h3> 
    </div>
    
  	<div class="imagemainbody" id="bodyreportdiv" > 
  	
  	<div class="header"> </div>
  	
  	<div id="divimage1"> <img src="" width="800" height="500" border="0" id="image1"/></div>
  	
	<div id="divimage2"><img src="" width="800" height="500" border="0" id="image2"/></div>
  	
  	
	<div id="divimage3"><img src="" width="800" height="500" border="0" id="image3"/></div>
 
  	
  	
  	
  	
  	</div>




 

</body>


<script type="text/javascript">



function hideReportAndDateDiv()
{
	document.getElementById('reportdiv').style.display='none';
	document.getElementById('fromdateinputs').style.display='none';
	document.getElementById('todateinputs').style.display='none';
	document.getElementById('divnumdays').style.display='none';
}

function showDateDiv()
{
	var reportTypeBoxValue = document.getElementById("reporttype").value; 
	if(reportTypeBoxValue=="totalResStatusCharts")
	{
		document.getElementById('fromdateinputs').style.display='block';
		document.getElementById('todateinputs').style.display='block';
		document.getElementById('charttype').style.display='none';
		document.getElementById('divnumdays').style.display='none';
	}
	else if(reportTypeBoxValue=="" || reportTypeBoxValue=="totalRes")
	{
		document.getElementById('charttype').style.display='block';
		document.getElementById('fromdateinputs').style.display='none';
		document.getElementById('todateinputs').style.display='none';
		document.getElementById('divnumdays').style.display='none';
	}
	else
	{
		document.getElementById('charttype').style.display='block';
		document.getElementById('fromdateinputs').style.display='block';
		document.getElementById('todateinputs').style.display='none';
		document.getElementById('divnumdays').style.display='block';
	}
	
	hideReportDiv();
	
}

function hideReportDiv()
{
	document.getElementById('reportdiv').style.display='none';
}



function showReport()
{
	try
	{
	//set source of images as blank
	document.getElementById("image1").src="#";
	document.getElementById("image2").src="#";
	document.getElementById("image3").src="#";
	
	
	//get report type
	var reportTypeBoxValue = document.getElementById("reporttype").value;
	
	//var chartTypeBoxValue = document.getElementById("charttype").value;
	
	var chartTypeBox = document.getElementById("graphType");
	var chartTypeBoxValue = chartTypeBox.options[chartTypeBox.selectedIndex].value;
	
	var numDaysBox = document.getElementById("numdays");
	var numDaysBoxValue = numDaysBox.options[numDaysBox.selectedIndex].value;
	
	//get the dates
	var selectedfromDate = document.getElementById("fromdatepicker").value;
	var selectedtoDate = document.getElementById("todatepicker").value;
	
	
	var fromDate = new Date(document.getElementById("fromdatepicker").value);
	
	var fromDay=fromDate.getDate();
	var fromMonth = fromDate.getMonth();
	var fromYear = fromDate.getFullYear();
	
	
	var toDate = new Date(document.getElementById("todatepicker").value);
	
	var toDay=toDate.getDate();
	var toMonth = toDate.getMonth();
	var toYear = toDate.getFullYear();
	
	if(reportTypeBoxValue=="")
	{
		alert("Please select the type of report to be shown!");
		return false;
	}
	if(reportTypeBoxValue=="totalResStatusCharts" )
	{
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
		if((new Date(selectedtoDate) < new Date(selectedfromDate)) && (new Date(selectedtoDate) != new Date(selectedfromDate)))
		{
			alert("To date should be after from date!");
			return false;
		}
	
	}
	else if(reportTypeBoxValue=="minmax" || reportTypeBoxValue=="totalResWorkingTeamsOnDay" || reportTypeBoxValue=="totalResNotWorkingOnDay" || reportTypeBoxValue=="resTrainingTeamOnDay")
	{
		if(selectedfromDate=="")
		{
			alert("Select date!");
			return false;
		}
		
	}
	
	
	
	
	document.getElementById('reportdiv').style.display='block';
	
	//handle each report
	if(reportTypeBoxValue=="totalRes")
	{
		if(chartTypeBoxValue=="pie")
		{
			
			document.getElementById("image1").src="${pageContext.request.contextPath}/TotalResouceInEachTeamChart?type=pie";
			document.getElementById("image1").style.width = '600px';
			document.getElementById("image1").style.height = '400px';
			
			
			
			document.getElementById("image2").src="#";
			document.getElementById('divimage2').style.display='none';
			
			document.getElementById("image3").src="#";
			document.getElementById('divimage3').style.display='none';
		}
		else if(chartTypeBoxValue=="bar")
		{
			document.getElementById("image1").src = "${pageContext.request.contextPath}/TotalResouceInEachTeamChart?type=bar";
			
			document.getElementById("image1").style.width = '600px';
			document.getElementById("image1").style.height = '400px';
			
			document.getElementById("image2").src="#";
			document.getElementById('divimage2').style.display='none';
			document.getElementById("image3").src="#";
			document.getElementById('divimage3').style.display='none';

		}
		
	
	}
	else if(reportTypeBoxValue=="totalResStatusCharts")
	{
		
		document.getElementById("image1").src="${pageContext.request.contextPath}/StatusBreakDownCharts?type=pieTotal&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&toDay="+ toDay + "&&toMonth=" + toMonth + "&&toYear=" + toYear;
		document.getElementById("image1").style.width = '600px';
		document.getElementById("image1").style.height = '400px';
		
		
		
		document.getElementById("image2").src="${pageContext.request.contextPath}/StatusBreakDownCharts?type=pieWork&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&toDay="+ toDay + "&&toMonth=" + toMonth + "&&toYear=" + toYear;
		document.getElementById("image2").style.width = '600px';
		document.getElementById("image2").style.height = '400px';
		document.getElementById('divimage2').style.display='block';
		
		document.getElementById("image3").src="${pageContext.request.contextPath}/StatusBreakDownCharts?type=pieNotWork&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&toDay="+ toDay + "&&toMonth=" + toMonth + "&&toYear=" + toYear;
		document.getElementById("image3").style.width = '600px';
		document.getElementById("image3").style.height = '400px';
		document.getElementById('divimage3').style.display='block';
	
		
	}
	else if(reportTypeBoxValue=="minmax")
	{
		if(chartTypeBoxValue=="pie")
		{
			
			document.getElementById("image1").src="${pageContext.request.contextPath}/LimitsCharts?type=xrpie&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image1").style.width = '600px';
			document.getElementById("image1").style.height = '400px';
			
			
			
			document.getElementById("image2").src="${pageContext.request.contextPath}/LimitsCharts?type=catpie&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image2").style.width = '600px';
			document.getElementById("image2").style.height = '400px';
			document.getElementById('divimage2').style.display='block';
			
			document.getElementById("image3").src="#";
			document.getElementById('divimage3').style.display='none';
		
		
		}
		else if(chartTypeBoxValue=="bar")
		{
			document.getElementById("image1").src="${pageContext.request.contextPath}/LimitsCharts?type=bar&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image1").style.width = '800px';
			document.getElementById("image1").style.height = '550px';
			
			document.getElementById("image2").src="#";
			document.getElementById('divimage2').style.display='none';
			document.getElementById("image3").src="#";
			document.getElementById('divimage3').style.display='none';
		
		}
		
	
	}
	else if(reportTypeBoxValue=="totalResWorkingTeamsOnDay")
	{
		if(chartTypeBoxValue=="pie")
		{
			
			document.getElementById("image1").src="${pageContext.request.contextPath}/WorkingCharts?type=xrpie&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image1").style.width = '600px';
			document.getElementById("image1").style.height = '400px';
			
			
			
			document.getElementById("image2").src="${pageContext.request.contextPath}/WorkingCharts?type=catpie&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image2").style.width = '600px';
			document.getElementById("image2").style.height = '400px';
			document.getElementById('divimage2').style.display='block';
			
			document.getElementById("image3").src="#";
			document.getElementById('divimage3').style.display='none';
		
		
		}
		else if(chartTypeBoxValue=="bar")
		{
			document.getElementById("image1").src="${pageContext.request.contextPath}/WorkingCharts?type=bar&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image1").style.width = '800px';
			document.getElementById("image1").style.height = '550px';
			
			document.getElementById("image2").src="#";
			document.getElementById('divimage2').style.display='none';
			document.getElementById("image3").src="#";
			document.getElementById('divimage3').style.display='none';
		
		}
	
	}
	else if(reportTypeBoxValue=="totalResNotWorkingOnDay")
	{
		
		if(chartTypeBoxValue=="pie")
		{
			
			document.getElementById("image1").src="${pageContext.request.contextPath}/NotWorkingCharts?type=xrpie&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image1").style.width = '600px';
			document.getElementById("image1").style.height = '400px';
			
			
			
			document.getElementById("image2").src="${pageContext.request.contextPath}/NotWorkingCharts?type=catpie&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image2").style.width = '600px';
			document.getElementById("image2").style.height = '400px';
			document.getElementById('divimage2').style.display='block';
			
			document.getElementById("image3").src="#";
			document.getElementById('divimage3').style.display='none';
		
		
		}
		else if(chartTypeBoxValue=="bar")
		{
			document.getElementById("image1").src="${pageContext.request.contextPath}/NotWorkingCharts?type=bar&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image1").style.width = '800px';
			document.getElementById("image1").style.height = '550px';
			
			document.getElementById("image2").src="#";
			document.getElementById('divimage2').style.display='none';
			document.getElementById("image3").src="#";
			document.getElementById('divimage3').style.display='none';
		
		}
	
	
	
	
	
	}
	else if(reportTypeBoxValue=="resTrainingTeamOnDay")
	{
		
		

		if(chartTypeBoxValue=="pie")
		{
			
			document.getElementById("image1").src="${pageContext.request.contextPath}/TrainingCharts?type=xrpie&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image1").style.width = '600px';
			document.getElementById("image1").style.height = '400px';
			
			
			
			document.getElementById("image2").src="${pageContext.request.contextPath}/TrainingCharts?type=catpie&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image2").style.width = '600px';
			document.getElementById("image2").style.height = '400px';
			document.getElementById('divimage2').style.display='block';
			
			document.getElementById("image3").src="#";
			document.getElementById('divimage3').style.display='none';
		
		
		}
		else if(chartTypeBoxValue=="bar")
		{
			document.getElementById("image1").src="${pageContext.request.contextPath}/TrainingCharts?type=bar&&fromDay=" + fromDay + "&&fromMonth=" + fromMonth + "&&fromYear=" + fromYear + "&&numdays=" + numDaysBoxValue;
			document.getElementById("image1").style.width = '800px';
			document.getElementById("image1").style.height = '550px';
			
			document.getElementById("image2").src="#";
			document.getElementById('divimage2').style.display='none';
			document.getElementById("image3").src="#";
			document.getElementById('divimage3').style.display='none';
		
		}
	
		
		
		
		
		
		
	}
	
	
	
	}
	catch(e)
	{
		alert(e.message);	
	}
	
	
	
	
}
</script>
</html>