package trilane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

/**
 * Servlet implementation class CheckIfHoliday
 */
@WebServlet("/CheckIfHoliday")
public class CheckIfHoliday extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckIfHoliday() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkIfHoliday(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkIfHoliday(request,response);
	}
	protected void checkIfHoliday(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    
	    ServletContext context = request.getServletContext();
	    HttpSession session = request.getSession();
		
		int date = Integer.parseInt(request.getParameter("date"));
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		
		
		Connection con = null;
		Statement ps = null;
		ResultSet res = null;
		
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				String databaseUrl =(String) session.getAttribute("data-url-schedule");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				con =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				String query="SELECT  * FROM public_holiday WHERE  day = " + date + " and month = " + month + " and year = " + year;
				
				ps = con.createStatement();
				
				
				
				
				res = ps.executeQuery(query);
				if(res.first())
				{
					
					response.getWriter().write("true");
					
					
				}else
				{
				
					response.getWriter().write("false");
					
				}
			
				
		}
		catch (SQLException e)
		{
			System.out.println(e.toString());  
		}
		catch (Exception e)
		{
			System.out.println(e.toString());  
		}
		finally
		{
			if (res != null) try { res.close(); } catch (SQLException e) {e.printStackTrace();}
			if (ps != null) try { ps.close(); } catch (SQLException e) {e.printStackTrace();}

	   	 	if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
			
			
		}
		
		
	}
	

}
