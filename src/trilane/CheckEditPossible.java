package trilane;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckEditPossible
 */
@WebServlet("/CheckEditPossible")
public class CheckEditPossible extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckEditPossible() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkEditPossible(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkEditPossible(request, response);
	}
	
	protected void checkEditPossible(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try
		{
			
			
			response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		    response.setCharacterEncoding("UTF-8");
		    
		    ServletContext context = request.getServletContext();
		    HttpSession session = request.getSession();
			
			int day = Integer.parseInt(request.getParameter("day"));
			int month = Integer.parseInt(request.getParameter("month"));
			int year = Integer.parseInt(request.getParameter("year"));
			
			String companyLoginId = (String)session.getAttribute("companyLoginId");
			
			int freezePeriod = 0;
			
			
			//find out the lock time from database
			Class.forName("com.mysql.jdbc.Driver");
			String databaseUrl = (String)session.getAttribute("data-url-company");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			con = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			st = con.createStatement();
			
			rs = st.executeQuery("select freeze_period from company_details where company_loginid='" + companyLoginId + "'" );
			if (rs.next()) 
			{
				freezePeriod = rs.getInt(1);
				
			}
			
			
			
			
			
			
			GregorianCalendar selectedCalendar = new GregorianCalendar (year, month, day);
			//selected date
			java.util.Date selectedDate = selectedCalendar.getTime();
			
			// find current date
			Calendar start = Calendar.getInstance();
	
	
			//add freeze period for end date
			start.add(Calendar.DATE, freezePeriod);
			java.util.Date endDate = start.getTime();
			Calendar endCal = start.getInstance();
			endCal.setTime(endDate);
			
	
	      	//set start as it has changed
			start = Calendar.getInstance();
			java.util.Date startDate = start.getTime();
	      	
	      	boolean canEdit = true;
	      	boolean beforeDate = false;
	      	
	      	SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy/MM/dd");
	      	
	      	String StrSelectedDate = dateFormatter.format(selectedDate);
	      	//System.out.println("Selected date: " + StrSelectedDate);
	      	
	      
	      
	       //loop through next two months from current date to see if the selected calendar date faklls in that time period
	      	
			for (java.util.Date date = start.getTime(); !start.after(endCal); start.add(Calendar.DATE, 1), date = start.getTime())
			{
	
				String strDate = dateFormatter.format(date);
				//System.out.println("Loop date: " + strDate);
				
	
				if(strDate.equals(StrSelectedDate))
				{
					canEdit = false;
				}
				
				
												
			}
			
			if (selectedDate.before(startDate)) 
	      	{
	      		canEdit = false;
	      		beforeDate = true;
			
	      	}
	
			if(canEdit)
			{
				response.getWriter().write("Can Edit");
			}
			else 
			{
				if(beforeDate)
				{
					response.getWriter().write("Event Over");
				}
				else
				{
					response.getWriter().write("Within freeze period");
				}
				
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
			if (rs != null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
			 
		}
					
		
		
	}

}
