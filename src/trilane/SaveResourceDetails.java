package trilane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SaveResourceDetails
 */
@WebServlet("/SaveResourceDetails")
public class SaveResourceDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveResourceDetails() {
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
		
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		String user = request.getParameter("uname");
		String pwd = request.getParameter("pass");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		int tech_id=Integer.parseInt(request.getParameter("technology"));
		
		int isAdmin = Integer.parseInt(request.getParameter("isadmin"));
		int userId = Integer.parseInt(request.getParameter("userid"));
		
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		
		
		
		Connection connection = null;
		Statement st = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			String databaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			
			st = connection.createStatement();
			
			 
			 String sqlQuery = "update users set fname='" + fname + "',lname='" + lname + "',email='" + email  + "',pwd='" + pwd + "',tech_id=" + tech_id  + ",is_admin=" + isAdmin + " where user_id=" + userId;
				
			 int i = st.executeUpdate(sqlQuery);
			 
			if (i > 0)
			{
				//If this user is an admin and his email has been changed, then the global_admin table also has to be updated
				if(isAdmin==1)
				{
					Administrator adminObject = new Administrator(user,pwd,email);
					CommonDatabase obj = new CommonDatabase();
					obj.UpdateEmailGlobalAdmin(context, session, adminObject);
				}
				
				session.setAttribute("month", month);
				session.setAttribute("year", year);
				
				response.sendRedirect(getServletContext().getContextPath() + "/jsp/showCalendar.jsp");
				
				} 
			else 
				{
					session.setAttribute("errormessage", "Error occured when saving resource details!");
					response.sendRedirect(getServletContext().getContextPath() + "/jsp/error.jsp");
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
			if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}

	   	 
		}
		
		
	}

}
