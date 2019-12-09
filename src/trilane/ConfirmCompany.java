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

/**
 * Servlet implementation class ConfirmCompany
 */
@WebServlet("/ConfirmCompany")
public class ConfirmCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmCompany() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		confirmCompany(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		confirmCompany(request,response);
	}
	
	protected void confirmCompany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = request.getServletContext();
		String companyLogin = request.getParameter("companyid");
		Connection connection = null;
		Statement statement = null;
		int is_confirmed = 1;
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			
			String databaseUrl = "jdbc:mysql://localhost:3306/company?useSSL=false";
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
			connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
			
			statement = connection.createStatement();
			String sqlUpdateQuery = "Update company_details set is_confirmed =  " + is_confirmed + " where company_loginid = '" + companyLogin + "'";
			int i = statement.executeUpdate(sqlUpdateQuery);
			if(i>0)
			{
				response.sendRedirect(getServletContext().getContextPath() +  "/jsp/index.jsp");
			}
			else
			{
				response.sendRedirect(getServletContext().getContextPath() +  "/jsp/error.jsp");
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
			
			if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}

	   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
			
		}
	}

}
