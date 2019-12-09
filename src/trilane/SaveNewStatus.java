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
 * Servlet implementation class SaveNewStatus
 */
@WebServlet("/SaveNewStatus")
public class SaveNewStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveNewStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveNewStatus(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveNewStatus(request,response);
	}
	
	
	protected void saveNewStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 BufferedWriter objWriter=  null;
		 HttpSession session = request.getSession();
		    
		 String adminName =(String)session.getAttribute("loginname");
		
		 ServletContext context = request.getServletContext();
		 Connection connection = null;		
		 Statement stSaveStatus = null;	
		 
		 Statement stCheckEventExist = null;
		 ResultSet rsCheckEventExist = null;
		 
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
				 
			int userId = Integer.parseInt(request.getParameter("userid"));
			int statusId = Integer.parseInt(request.getParameter("statusid"));
			int month = Integer.parseInt(request.getParameter("month"));
			int year = Integer.parseInt(request.getParameter("year"));
			int day = Integer.parseInt(request.getParameter("date"));
			String resourceName= request.getParameter("resourcename");
			String statusName = request.getParameter("statusname");
			
			//System.out.println("statusid:  " + statusId);
			
			Class.forName("com.mysql.jdbc.Driver");
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
			connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
			
			//first check if events table has a record on the selected day for the user. If it exists, update it . Else, insert a new record.
			stCheckEventExist = connection.createStatement();
			String sqlQueryCheckExist = "select * from events where day = " + day + " and month = " + month + " and year= " + year + " and user_id = " + userId;
			rsCheckEventExist = stCheckEventExist.executeQuery(sqlQueryCheckExist);
			if(rsCheckEventExist.next())
			{
				
				//event exists. Now update status.
				String sqlQueryUpdate = "update events set status_id=" + statusId + " where day = " + day + " and month = " + month + " and year= " + year + " and user_id = " + userId;
				stSaveStatus = connection.createStatement();
				
				int i = stSaveStatus.executeUpdate(sqlQueryUpdate);
				 
				if (i > 0)
				{
					response.getWriter().write("success");
					//System.out.println("Event updated");
					String successMessage =dateFormat.format(calendar.getTime()) + " -  Status of: " +  resourceName + " on " + day +"/" + objCommon.getMonth(month) + "/" + year + " changed to:  " + statusName + ". Status changed by admin: " + adminName +"." ;
					
					
					objWriter.write(successMessage);
					objWriter.newLine();
					objWriter.newLine();
				}
				else
				{
					response.getWriter().write("error");
					//System.out.println("error in updation");
					String errorMessage =  dateFormat.format(calendar.getTime()) + " - Error occured while trying to change status of: " +  resourceName + " on " + day +"/" + objCommon.getMonth(month) + "/" + year + " to:  " + statusName + ". Edit tried by admin:" + adminName + "." ;
					
					
					objWriter.write(errorMessage);
					objWriter.newLine();
					objWriter.newLine();
				}
				
				
			}
			else
			{
				//Event does not exist. Insert new record.
				stInsertEvent = connection.createStatement();
				
				String sqlQuery = "insert into events(user_id, day, month, year, status_id, event_datetime) values (" + userId + " , " + day + "," + month + "," + year + "," + statusId + ",CURRENT_TIMESTAMP())";
				int i = stInsertEvent.executeUpdate(sqlQuery);
				 
				if (i > 0)
					{
					
					response.getWriter().write("success");
					//System.out.println("Event inserted");
					String successMessage =dateFormat.format(calendar.getTime()) + " -  Status of: " +  resourceName + " on " + day +"/" + objCommon.getMonth(month) + "/" + year + " added as:  " + statusName + ". Status added by admin: " + adminName +"." ;
					
					
					objWriter.write(successMessage);
					objWriter.newLine();
					objWriter.newLine();
					
					} 
				else 
					{
					response.getWriter().write("error");
					//System.out.println("error in insertion");
					String errorMessage =  dateFormat.format(calendar.getTime()) + " - Error occured while trying to add status of: " +  resourceName + " on " + day +"/" + objCommon.getMonth(month) + "/" + year + " , as:  " + statusName + ". Status addition tried by admin:" + adminName + "." ;
					
					
					objWriter.write(errorMessage);
					objWriter.newLine();
					objWriter.newLine();
					}
				
			}
			
			objWriter.flush();
		}
		 catch(SQLException se)
		{
		      //Handle errors for JDBC
	      se.printStackTrace();
	      System.out.println("error:" + se.getMessage());
		}
		
		catch(Exception e)
		{
	      //Handle errors for Class.forName
	      e.printStackTrace();
	      System.out.println("error:" + e.getMessage());
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
