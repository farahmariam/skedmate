package trilane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trilane.CommonDatabase;

import com.google.gson.Gson;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Servlet implementation class GetStatusForResource
 */
@WebServlet("/GetStatusForResource")
public class GetStatusForResource extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStatusForResource() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getStatusForResource(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getStatusForResource(request,response);
	}
	
	
	
	
	protected void getStatusForResource(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	    
	    ServletContext context = request.getServletContext();
	    HttpSession session = request.getSession();
	   
		//First find out if the resource is entered as an event on the selected day. If yes, then return his status. If not, then return as "on shift" if weekday and "offshift" if weekend.
		

		 Connection connection = null;		
		 Statement stGetEvent = null;		 
		 ResultSet rsGetEvent  = null;
		 
		
		 
		 
		 int statusIdOfResource=-1;
		 String statusNameOfResource="";
		 
		 try
		{
			 
				int userId = Integer.parseInt(request.getParameter("userid"));
				
				int month = Integer.parseInt(request.getParameter("month"));
				int year = Integer.parseInt(request.getParameter("year"));
				int day = Integer.parseInt(request.getParameter("date"));
				
				GregorianCalendar cal = new GregorianCalendar (year, month, day);
				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
				
				//int OFF_SHIFT=Constants.OFFSHIFT_ID;
				//int ON_SHIFT=Constants.ONSHIFT_ID;
				
				
			Class.forName("com.mysql.jdbc.Driver");
			String databaseUrl =(String) session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
			connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
			
			

			String query = "select * from events where day = " + day + " and month = " + month + " and year= " + year + " and user_id = " + userId; 
			stGetEvent = connection.createStatement();
			rsGetEvent = stGetEvent.executeQuery(query);
			
			
			if(rsGetEvent.next())
			{
				statusIdOfResource = rsGetEvent.getInt("status_id");
				
				
			}
			else
			{
				//if event does not exist,  set default status
				CommonDatabase commonObj = new CommonDatabase();
				ArrayList<StatusObject> statusList = commonObj.getAllStatus(context, session);
				StatusObject statusObj = statusList.get(0);
				statusIdOfResource = statusObj.getStatusId();
				
				
				
			}
			
			Gson gson = new Gson();
			
			response.getWriter().write(Integer.toString(statusIdOfResource));
	
			
			
		}
		catch(SQLException se)
		{
	      //Handle errors for JDBC
	      se.printStackTrace();
	      System.out.println("error:" + se.getMessage());
	    }
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("error:" + e.getMessage());
		}
		catch(Exception e)
		{
	      //Handle errors for Class.forName
	      e.printStackTrace();
	      System.out.println("error:" + e.getMessage());
	   	}
			

		finally
		{
			if (stGetEvent != null) try { stGetEvent.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsGetEvent != null) try { rsGetEvent.close(); } catch (SQLException e) {e.printStackTrace();}
			
	   	 	
			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
			}

	}


}
