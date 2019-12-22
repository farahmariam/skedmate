package trilane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckDatabaseName
 */
@WebServlet("/CheckDatabaseName")
public class CheckDatabaseName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckDatabaseName() {
        super();
        // TODO Auto-generated constructor stub
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    
	    ServletContext context = request.getServletContext();
		
		String databaseName = request.getParameter("databasename");
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet res = null;
		
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				String databasePath = context.getInitParameter("data-url");
				String databaseUrl = databasePath.concat("?useSSL=false") ;
				System.out.println("database url in check databasename: " + databaseUrl);
				
				//String databaseUrl = "jdbc:mysql://scheduler:3306/?useSSL=false";
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				con =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				
				
				ps = con.prepareStatement("SHOW DATABASES LIKE ?");
				ps.setString(1, databaseName);
				
				res = ps.executeQuery();
				if(res.first())
				{
					response.getWriter().write("exists");
					
				}
				else
				{
					response.getWriter().write("not exists");
					
				}
				
				
		}
		catch (SQLException e)
		{
			response.getWriter().write("error");
			System.out.println(e.toString());  
		}
		catch (Exception e)
		{
			response.getWriter().write("error");
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
