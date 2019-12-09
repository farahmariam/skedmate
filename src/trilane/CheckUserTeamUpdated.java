package trilane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckUserTeamUpdated
 */
@WebServlet("/CheckUserTeamUpdated")
public class CheckUserTeamUpdated extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUserTeamUpdated() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkTeamUpdated(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkTeamUpdated(request,response);
	}
	
	protected void checkTeamUpdated(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			Integer tech_id=-1;
			
			//find out the lock time from database
			Class.forName("com.mysql.jdbc.Driver");
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			con = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			st = con.createStatement();
			
			rs = st.executeQuery("select tech_id from users where user_id=" + user_id) ;
			if (rs.next()) 
			{
				tech_id = rs.getInt(1);
				
			}
			//System.out.println("the team id: " + tech_id);
			if(tech_id==0 || tech_id==-1 || tech_id==null)
			{
				response.getWriter().write("No team");
			}
			else
			{
				response.getWriter().write("Team updated");
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
