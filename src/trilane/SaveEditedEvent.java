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
 * Servlet implementation class SaveEditedEvent
 */
@WebServlet("/SaveEditedEvent")
public class SaveEditedEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveEditedEvent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveEvent(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveEvent(request,response);
	}
	
	
	
	protected void saveEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		
		
		BufferedWriter objWriter=  null;
		ServletContext context = request.getServletContext();
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		String comment = request.getParameter("comment");
		int statusId = Integer.parseInt(request.getParameter("status"));
		
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		int day = Integer.parseInt(request.getParameter("day"));
		String userName = request.getParameter("username");
		String statusName = "";
		
		
		
		Statement st = null;
		Connection connection = null;
		Statement stStatus = null;
		ResultSet rsStatus = null;
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
			Calendar cal = Calendar.getInstance();
			
	    	
			Class.forName("com.mysql.jdbc.Driver");
			
			
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			
			stStatus = connection.createStatement();
			String statusQuery = "select * from status where status_id=" + statusId ;
			rsStatus = stStatus.executeQuery(statusQuery);
			if(rsStatus.next())
			{
				statusName = rsStatus.getString("status_name");
			}
			
			st = connection.createStatement();
			
			
			String sqlQuery = "update events set comment='" + comment + "',status_id=" + statusId + " where event_id=" + eventId;
			int i = st.executeUpdate(sqlQuery);
			CommonDatabase objCommon = new CommonDatabase();
			
			 
			if (i > 0)
			{
				session.setAttribute("month", month);
				session.setAttribute("year", year);
				
				//String successMessage = "Resource Status Edited:" + userName + ".Status changed to:'" + statusName + "' on date: " + day +"/" + objCommon.getMonth(month) + "/" + year + ". Info added on: " + dateFormat.format(cal.getTime()) ;
				String successMessage = dateFormat.format(cal.getTime()) + " - Resource Status of " + userName  + " changed to:'" + statusName + "' for the date: " + day +"/" + objCommon.getMonth(month) + "/" + year + ". Info edited by: " + userName +"."; 
				
				
				objWriter.write(successMessage);
				objWriter.newLine();
				objWriter.newLine();
				
				response.sendRedirect(getServletContext().getContextPath() +  "/jsp/showCalendar.jsp");
				
			} 
			else
			{
				//String errorMessage = "Error occured while trying to edit user:" + userName + " on date: " + day +"/" + objCommon.getMonth(month) + "/" + year + ". Date and time: " + dateFormat.format(cal.getTime()) ;
				String errorMessage =  dateFormat.format(cal.getTime()) + " - Error occured while trying to edit user:" + userName + " on date: " + day +"/" + objCommon.getMonth(month) + "/" + year + ". Edit tried by:" + userName + "." ;
				
				
				objWriter.write(errorMessage);
				objWriter.newLine();
				objWriter.newLine();
				
				response.sendRedirect(getServletContext().getContextPath() + "/jsp/error.jsp");
			
			
			}
			objWriter.flush();
			
			
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
			if (stStatus != null) try { stStatus.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsStatus != null) try { rsStatus.close(); } catch (SQLException e) {e.printStackTrace();}
			 if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			if(objWriter!=null) try { objWriter.close(); } catch(Exception ex) {ex.printStackTrace();}
			 
		}
		
		
		
	}

}
