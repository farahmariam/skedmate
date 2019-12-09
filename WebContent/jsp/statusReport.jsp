<%@page import="java.util.*, java.text.*,  java.sql.* , trilane.CommonDatabase, trilane.StatusObject"%> 
<%@ include file="common.jsp" %>
  
<html> 
<head>
<style type="text/css">

h3 {
    color: red;
	background: white;
    font-family: verdana;
    font-size: 150%;
}

</style>
<title>ScheduleIT</title>
<link rel="stylesheet" href="../css/report.css">
</head>

<body bgcolor="white"> 
<%

int cntMonth = 2;
java.util.Date tableStartDate=null;
java.util.Date tableEndDate=null;


String countString = request.getParameter("month");
String strStartDate = request.getParameter("startdate");
String strEndDate = request.getParameter("enddate");
String strInputAction = request.getParameter("action");
SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

ArrayList<StatusObject> statusList=null;


if (countString != null && countString.length( ) > 0)
{
	try 
	{
		cntMonth = Integer.parseInt(countString);
	} 
	catch (NumberFormatException e) 
	{
		out.println(" No of Months" + countString + " invalid");
	}
}

if (strStartDate != null && strStartDate.length( ) > 0)
{	
	
	try
	{
		tableStartDate = (java.util.Date)formatter.parse(strStartDate);
			
	}
	catch(Exception ex)
	{
		out.println("Exception: "  + ex.toString());
	}

}




if (strEndDate != null && strEndDate.length( ) > 0)
{	
	try
	{
		tableEndDate = (java.util.Date)formatter.parse(strEndDate);
	}
	catch(Exception ex)
	{
		out.println("Exception: "  + ex.toString());
	}

}



%>

<form name="report" method="post" action="statusReport.jsp">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
	  	 <td width="10%" align="right" > <input type="button"  name="back" value="Previous" onClick="goBack()"  />
	    
	     </td>
	  
	  
	    <td align="center" class="textStyle" >Enter number of months : <input type="text" size="5" name="month" value="<%= cntMonth %>" />
	   	 <input type=submit value="Display">
	    </td>
	     
	    <td align="left" > <input type="button"  name="Next" value="Next" onClick="goForward()"  />
    
     	</td>
	</tr>
	</table>
	<input type="hidden" name="startdate" />
	<input type="hidden" name="enddate" />
	<input type="hidden" name="action" />
</form>


<%
Calendar start=null;
Calendar endCal = null;
java.util.Date endDate = null;
java.util.Date startDate = null;

Calendar startCal = null;

Calendar endCalendar = null;

if(tableStartDate==null && tableEndDate==null )
{
	start = Calendar.getInstance( );
	
	
	//add 3months for end date
	start.add(Calendar.MONTH, cntMonth);
	endDate = start.getTime();
	endCal = Calendar.getInstance();
	endCal.setTime(endDate);
	tableEndDate = endDate;
	
	
	
	//set start as it has changed
	start = Calendar.getInstance();
	startDate = start.getTime();
	tableStartDate = startDate;
	
	
	//values for second loop
	startCal = Calendar.getInstance();
	startCal.setTime(startDate);
	
	endCalendar = Calendar.getInstance();
	endCalendar.setTime(endDate);
	
	
	
	
	
}
else if(tableStartDate!=null && tableEndDate!=null)
{
	if (strInputAction != null && strInputAction.length( ) > 0)
	{

		if(strInputAction.equalsIgnoreCase("back"))
		{
			//start becomes end date
			endCal = Calendar.getInstance();
			endCal.setTime(tableStartDate);
			endDate = endCal.getTime();
			tableEndDate = endDate;
			
			
			
			//for start date: Go back the text field input no of months
			start = Calendar.getInstance();
			start.setTime(tableStartDate);
			start.add(Calendar.MONTH, -cntMonth);
			startDate = start.getTime();
			tableStartDate = startDate;
			
			
			
			

			
		}
		else if(strInputAction.equalsIgnoreCase("forward"))
		{
			//end date becomes start date
			start = Calendar.getInstance();
			start.setTime(tableEndDate);
			startDate = start.getTime();
			tableStartDate = startDate;
			
			//end date: go forward by text field input no of months
			endCal = Calendar.getInstance();
			endCal.setTime(tableEndDate);
			endCal.add(Calendar.MONTH, cntMonth);
			endDate = endCal.getTime();
			tableEndDate = endDate;
			
		}
		

	}

		//values for second loop
		startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		
		endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
	
	
	
}
%>

<div class="scrollableContainer">

  <div class="scrollingArea">

 <table class="cruises scrollable"  >
	<thead>
		<tr>
		
			<th class="name"><div class="th-inner">Name</div></th>
					
					<% 
					for (java.util.Date date = start.getTime(); !(start.after(endCal)); start.add(Calendar.DATE, 1), date = start.getTime())
					{
						Calendar cal = Calendar.getInstance();
					    cal.setTime(date);
					
						int day=cal.get(Calendar.DATE);
						int month=cal.get(Calendar.MONTH);
						int year=cal.get(Calendar.YEAR);
						int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
						String monthName = getMonth(month);
					%>
				

					<th class="name"><div  class="th-inner"> <%= day %> <%= monthName %> (<%=year %>)</div></th>
	
<% 

}


%>
			</tr>
		</thead>
		
	<tbody>
	<%
	
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection = null;
	Statement stUsers =null;
	ResultSet rsUsers = null;
	
	
	Statement stEvent = null;
	ResultSet rsEvent = null;
	
	
	
	String tdStyleClass="";
	
	
	try
	{
		
		ServletContext context = request.getServletContext();
		String databaseUrl = (String)session.getAttribute("data-url-schedule");
		String databaseUser = context.getInitParameter("data-user");
		String databasePwd = context.getInitParameter("data-pwd");
		
		String fName="";
		String lName="";
		String uName="";
		String resorceFullName="";
		int user_id=-1;
	
		connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
		stUsers = connection.createStatement();
		
		rsUsers = stUsers.executeQuery("select * from users order by uname");
		while (rsUsers.next())
		{
			user_id = rsUsers.getInt("user_id");
			fName = rsUsers.getString("fname");
			lName = rsUsers.getString("lname");
			uName = rsUsers.getString("uname");
			
			if(fName!=null && lName!=null)
			{
				resorceFullName = fName + " " + lName;
			}
			else
			{
				resorceFullName= uName;
			}
			
	%>
	
	<tr>
		<td class="name"><div><%=resorceFullName %></div></td>
			 
							<%
								String styleColour="";
								
								for (java.util.Date newdate = startCal.getTime(); !(startCal.after(endCalendar)); startCal.add(Calendar.DATE, 1), newdate = startCal.getTime())
									
								{
									boolean isPublicHoliday = false;
									boolean isWeekend = false;
									String mouseOverDay = "";
									
									Calendar newCal = Calendar.getInstance();
									newCal.setTime(newdate);
								
									int date=newCal.get(Calendar.DATE);
									int month=newCal.get(Calendar.MONTH);
									int year=newCal.get(Calendar.YEAR);
									int dayOfWeek = newCal.get(Calendar.DAY_OF_WEEK);
									String monthNm = getMonth(month);
									
									
									
									stEvent = connection.createStatement();
									String eventQuery = "select * from events where day=" + date + " and month=" + month + " and year =" + year + " and user_id=" + user_id ;
									rsEvent = stEvent.executeQuery(eventQuery);
									
									int status_id=-1;
									String status_name="";
									
									//check if weekend
									isWeekend = checkWeekend(dayOfWeek);
									
									//check if public holiday
									isPublicHoliday = checkPublicHoliday(date, month, year,context,session);
									
									CommonDatabase objCommon  = new CommonDatabase();
									statusList = objCommon.getAllStatus(context, session);
									
									//check if event exists. else set default vaues
									if(rsEvent.next())
									{
										status_id = rsEvent.getInt("status_id");	
										
										
									}
									else
									{
										//if event does not exist,  set status id to the first status id which is default working id
										
										
										
										
										StatusObject statusObject = statusList.get(0);
										int default_id = statusObject.getStatusId();
										
										status_id = default_id;
										
										
									}
									
									
									//setting style
									for(int loop=0; loop<statusList.size(); loop++)
									{
										StatusObject statusObj = statusList.get(loop);
										String loopStatusName = statusObj.getStatusName();
										int loopStatusId = statusObj.getStatusId();
										String status_colour = statusObj.getStatusColor();
										
										
										
										//if the status is equal to the statusid of the day
										if(loopStatusId==status_id)
										{
											styleColour=status_colour;
											status_name = loopStatusName;
											
											
										}
										
									}
									
									//set the style
									if(isPublicHoliday || isWeekend)
				         			{
				         				if(isPublicHoliday && isWeekend)
				         				{
				         					tdStyleClass = "tdOnShiftPublicHoliday";
				         					tdStyleClass="background:" + styleColour + ";border-width:2px; border-spacing:0px; border-style:solid; border-color:#8e44ad;";
				         				}
				         				else if(isPublicHoliday)
				         				{
				         					tdStyleClass ="background:" + styleColour + ";border-width:2px; border-spacing:0px; border-style:solid; border-color:#8e44ad;";
				         				}
				         				else 
				         				{
				         					tdStyleClass = "background:" + styleColour + ";border-width:1px; border-spacing:0px; border-style:solid; border-color:black;";
				         				}
				         				
				         			}
				         			else
				         			{
				         				tdStyleClass = "background:" + styleColour +";";
				         			}
									
									
									/*
									stStatus = connection.createStatement();
									String statusQuery = "select * from status where status_id=" + status_id;
									rsStatus = stStatus.executeQuery(statusQuery);
									if(rsStatus.next())
									{
										status_name = rsStatus.getString("status_name");
									}
									*/
									
									
									//mouse over text
									if(isWeekend || isPublicHoliday)
									{
										if(isWeekend && isPublicHoliday)
										{
											mouseOverDay = resorceFullName + " - " + status_name + " on " + date + "/" + monthNm + "/" + year  + ". (Weekend and Public Holiday)";
										}
										else if(isPublicHoliday)
										{
											mouseOverDay = resorceFullName + " - " + status_name + " on " + date + "/" + monthNm + "/" + year  + ".(Public Holiday)";
										}
										else if(isWeekend)
										{
											mouseOverDay =  resorceFullName + " - " + status_name + " on " + date + "/" + monthNm + "/" + year  + ".(Weekend)";
										}
										
									}
									else
									{
										mouseOverDay = resorceFullName + " - " + status_name + " on " + date + "/" + monthNm + "/" + year  + ".(Weekday)";
									}
									
									
									
									
							%>
									<td style="<%= tdStyleClass %>" title="<%= mouseOverDay  %>"><div><%=status_name %></div></td>
									
		
							<%
								

								}
							
							startCal = Calendar.getInstance();
							startCal.setTime(startDate);
							
							endCalendar = Calendar.getInstance();
							endCalendar.setTime(endDate);

							%>
	
												

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
		if (stUsers != null) try { stUsers.close(); } catch (SQLException e) {e.printStackTrace();}
		if (rsUsers != null) try { rsUsers.close(); } catch (SQLException e) {e.printStackTrace();}
		
		if (stEvent != null) try { stEvent.close(); } catch (SQLException e) {e.printStackTrace();}
		if (rsEvent != null) try { rsEvent.close(); } catch (SQLException e) {e.printStackTrace();}
		
		
	
		if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
		 
	}
		
		
		
		%>
			</tbody>
			</div>
		</div>

	
</table>
 
	
</body>



<script type="text/javascript">

function goBack()
{
	

	
	document.forms["report"]["startdate"].value = "<%=tableStartDate  %>";
	document.forms["report"]["enddate"].value = "<%=tableEndDate  %>";
	document.forms["report"]["action"].value = "back";
	goTo();
	
}

function goForward()
{
	
	
	document.forms["report"]["startdate"].value = "<%=tableStartDate  %>";
	document.forms["report"]["enddate"].value = "<%=tableEndDate  %>";
	document.forms["report"]["action"].value = "forward";
	goTo();
	
}

function goTo()
{
	
	
	document.forms["report"].submit();
}


</script>
</html>