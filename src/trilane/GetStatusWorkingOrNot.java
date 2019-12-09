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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GetStatusWorkingOrNot
 */
@WebServlet("/GetStatusWorkingOrNot")
public class GetStatusWorkingOrNot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStatusWorkingOrNot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getWorking(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getWorking(request,response);
	}
	
	protected void getWorking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession();
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet res = null;
	    
		try
		{
				
			
				ServletContext context = request.getServletContext();
				Class.forName("com.mysql.jdbc.Driver");
				String databaseUrl = (String) session.getAttribute("data-url-schedule");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				con =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				
				
				ps = con.prepareStatement("SELECT  is_working FROM status WHERE " + "status_id = ?");
				ps.setString(1,request.getParameter("status_id"));
				res = ps.executeQuery();
				if(res.next())
				{
					int is_working = res.getInt("is_working");
					//System.out.println("the is_working= " + is_working);
					
					response.getWriter().write( Integer.toString(is_working));
					
					
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
