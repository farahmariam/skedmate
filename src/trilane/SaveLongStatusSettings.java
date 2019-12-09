package trilane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import trilane.CommonDatabase;

/**
 * Servlet implementation class SaveLongStatusSettings
 */
@WebServlet("/SaveLongStatusSettings")
public class SaveLongStatusSettings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveLongStatusSettings() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveSettings(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveSettings(request,response);
	}
	
	protected void saveSettings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 	BufferedWriter objWriter=  null;
		    HttpSession session = request.getSession();
			    
			String adminName =(String)session.getAttribute("loginname");
			
			
			ServletContext context = request.getServletContext();
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
			Connection connection = null;
			
			Statement stCheckEventExist = null;
			ResultSet rsCheckEventExist = null;
			Statement stSaveStatus = null;
			Statement stInsertEvent = null;
		
			
			try
			{
				String companyLoginId = (String)session.getAttribute("companyLoginId");
				String strLogPath = context.getInitParameter("logfile-path");
				String fileName = companyLoginId + ".txt";
				strLogPath = strLogPath.concat(fileName);
				File strFile = new File(strLogPath);
				if (!strFile.exists())
				{
					strFile.createNewFile();
				}
				
				
				 //File appending
				objWriter = new BufferedWriter(new FileWriter(strFile,true));
				
				
				//get current date time
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Calendar calendar = Calendar.getInstance();
				CommonDatabase objCommon = new CommonDatabase();
				
				
				int fromDay = Integer.parseInt(request.getParameter("fromDay"));
				int fromMonth = Integer.parseInt(request.getParameter("fromMonth"));
				int fromYear = Integer.parseInt(request.getParameter("fromYear"));
				
				int toDay = Integer.parseInt(request.getParameter("toDay"));
				int toMonth = Integer.parseInt(request.getParameter("toMonth"));
				int toYear =Integer.parseInt( request.getParameter("toYear"));
				
				int user_id=Integer.parseInt(request.getParameter("user_id"));
				int status_id=Integer.parseInt(request.getParameter("status_id"));
				String status_name=request.getParameter("status_name");
				String resource_name=request.getParameter("resource_name");
				String strComment = "";
				strComment = request.getParameter("comment");
				

				//start calendar
				Calendar startCalendar = Calendar.getInstance();
				startCalendar.set(fromYear, fromMonth, fromDay);
				
				//end calendar
				Calendar endCalendar = Calendar.getInstance();
				endCalendar.set(toYear, toMonth, toDay);
			
				
				Class.forName("com.mysql.jdbc.Driver");
				//connection obj
				connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
				

				//loop between dates
				for (java.util.Date date = startCalendar.getTime(); !startCalendar.after(endCalendar); startCalendar.add(Calendar.DATE, 1), date = startCalendar.getTime())
				{
					Calendar cal = Calendar.getInstance();
				    cal.setTime(date);
		
					
					int day=cal.get(Calendar.DATE);
					int month=cal.get(Calendar.MONTH);
					int year=cal.get(Calendar.YEAR);
					
					
					
					String monthName = objCommon.getMonth(month);
					
					
					
					//first check if events table has a record on the selected day for the user. If it exists, update it . Else, insert a new record.
					stCheckEventExist = connection.createStatement();
					String sqlQueryCheckExist = "select * from events where day = " + day + " and month = " + month + " and year= " + year + " and user_id = " + user_id;
					rsCheckEventExist = stCheckEventExist.executeQuery(sqlQueryCheckExist);
					if(rsCheckEventExist.next())
					{
						
						//event exists. Now update status.
						String sqlQueryUpdate = "update events set status_id=" + status_id + ", comment='" + strComment + "' where day = " + day + " and month = " + month + " and year= " + year + " and user_id = " + user_id;
						stSaveStatus = connection.createStatement();
						
						int i = stSaveStatus.executeUpdate(sqlQueryUpdate);
						 
						if (i > 0)
						{
							//response.getWriter().write("success");
							//System.out.println("Event updated");
							String successMessage =dateFormat.format(calendar.getTime()) + " -  Status of: " +  resource_name + " on " + day +"/" + monthName + "/" + year + " changed to:  " + status_name + ". Status changed by admin: " + adminName +"." ;
							
							
							objWriter.write(successMessage);
							objWriter.newLine();
							objWriter.newLine();
						}
						else
						{
							//response.getWriter().write("error");
							//System.out.println("error in updation");
							String errorMessage =  dateFormat.format(calendar.getTime()) + " - Error occured while trying to change status of: " +  resource_name + " on " + day +"/" + monthName + "/" + year + " to:  " + status_name + ". Edit tried by admin:" + adminName + "." ;
							
							
							objWriter.write(errorMessage);
							objWriter.newLine();
							objWriter.newLine();
						}
						
						
					}
					else
					{
						//Event does not exist. Insert new record.
						stInsertEvent = connection.createStatement();
						
						String sqlQuery = "insert into events(user_id, day, month, year, status_id,comment, event_datetime) values (" + user_id + " , " + day + "," + month + "," + year + "," + status_id + ",' " + strComment + "',CURRENT_TIMESTAMP())";
						int i = stInsertEvent.executeUpdate(sqlQuery);
						 
						if (i > 0)
							{
							
							//response.getWriter().write("success");
							//System.out.println("Event inserted");
							String successMessage =dateFormat.format(calendar.getTime()) + " -  Status of: " +  resource_name + " on " + day +"/" + monthName + "/" + year + " added as:  " + status_name + ". Status added by admin: " + adminName +"." ;
							
							
							objWriter.write(successMessage);
							objWriter.newLine();
							objWriter.newLine();
							
							} 
						else 
							{
							//response.getWriter().write("error");
							//System.out.println("error in insertion");
							String errorMessage =  dateFormat.format(calendar.getTime()) + " - Error occured while trying to add status of: " +  resource_name + " on " + day +"/" + monthName + "/" + year + " , as:  " + status_name + ". Status addition tried by admin:" + adminName + "." ;
							
							
							objWriter.write(errorMessage);
							objWriter.newLine();
							objWriter.newLine();
							}
						
					}
					
					
		
				}//end of for loop
				
				response.getWriter().write("success");
				
			}
			catch(SQLException se)
	 		{
	 	   		//Handle errors for JDBC
	 	  		 se.printStackTrace();
	 	  		response.getWriter().write("error");
	 		 }
	 		catch(Exception e)
	 		{
	 	  		 //Handle errors for Class.forName
	 	   			e.printStackTrace();
	 	   		response.getWriter().write("error");
	 		}
	         finally
	         {
	        	 if (stCheckEventExist != null) try { stCheckEventExist.close(); } catch (SQLException e) {e.printStackTrace();}
	 			if (rsCheckEventExist != null) try { rsCheckEventExist.close(); } catch (SQLException e) {e.printStackTrace();}
	 			if (stInsertEvent != null) try { stInsertEvent.close(); } catch (SQLException e) {e.printStackTrace();}
	 			if (stSaveStatus != null) try { stSaveStatus.close(); } catch (SQLException e) {e.printStackTrace();}
	 			
	 			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
	 			if(objWriter!=null) try { objWriter.close(); } catch(Exception ex) {ex.printStackTrace();}
	 			 

				
	         }	
			
			
			
			
	}

}
