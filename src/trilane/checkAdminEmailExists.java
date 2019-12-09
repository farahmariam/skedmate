package trilane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class checkAdminEmailExists
 */
@WebServlet("/checkAdminEmailExists")
public class checkAdminEmailExists extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkAdminEmailExists() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkEmailExists(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		checkEmailExists(request,response);
	}

	protected void checkEmailExists(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    
	    ServletContext context = request.getServletContext();
		
		String emailId =request.getParameter("email");
		
		//System.out.println("the email id " + emailId);
		
		Connection con = null;
		Statement statement = null;
		ResultSet res = null;
		
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				String databaseUrl = "jdbc:mysql://localhost:3306/Company?useSSL=false";
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				con =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				statement = con.createStatement();
				String sqlQuery = "select * from global_admin where admin_email like '%" + emailId  + "%'";
				
				
				res = statement.executeQuery(sqlQuery);
				if(res.first())
				{
					//System.out.println("exists");
					response.getWriter().write("exists");
					
					
				}else
				{
					//System.out.println("not exists");
					response.getWriter().write("not exists");
					
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
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}

	   	 	if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
			
			
		}
		
		
		
		
		
		
	}

}
