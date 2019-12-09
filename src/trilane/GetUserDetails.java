package trilane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import trilane.user;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetUserDetails
 */
@WebServlet("/GetUserDetails")
public class GetUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserDetails() {
        super();
        // TODO Auto-generated constructor stub
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		getUserDetails(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 getUserDetails(request,response);
	}
	
	protected void getUserDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");

		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		
		
		 Connection connection = null;		
		 Statement stGetResource= null;		 
		 ResultSet rsGetResource  = null;
		
		String userName= request.getParameter("userName");
		 
		
		 
		 try
			{
				
					Class.forName("com.mysql.jdbc.Driver");
					String databaseUrl = (String)session.getAttribute("data-url-schedule");
					String databaseUser = context.getInitParameter("data-user");
					String databasePwd = context.getInitParameter("data-pwd");
					connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);

					String query = "select * from users where uname='" + userName + "'"; 
					stGetResource = connection.createStatement();
					rsGetResource = stGetResource.executeQuery(query);
					
					
					int user_id = -1;
					
					String password="";
					
					String fName="";
					String lName="";
					String email="";
					
					int tech_id=-1;
					int is_admin=-1;
					
					user userObj=null;
					
					if(rsGetResource.next())
					{
						userName = rsGetResource.getString("uname");						
						user_id = rsGetResource.getInt("user_id");
						
						fName = rsGetResource.getString("fname");
						if(fName==null)
						{
							fName="";
						}
						lName = rsGetResource.getString("lname");
						if(lName==null)
						{
							lName="";
						}
						password=rsGetResource.getString("pwd");
						
						email=rsGetResource.getString("email");
						if(email==null)
						{
							email="";
						}
						tech_id=rsGetResource.getInt("tech_id");
						if((Integer)tech_id==null)
						{
							tech_id=1;
						}
						is_admin = rsGetResource.getInt("is_admin");
						if((Integer)is_admin==null)
						{
							is_admin=0;
						}
						
						//System.out.println("login name:" + userName + ",user id :" + user_id + ",password: " + password  + ",email: " + email + ", techid:" + tech_id + ", is_admin: " + is_admin);
						userObj = new user(user_id,userName,password,fName,lName,email, tech_id,is_admin);
						
						
					}
					
					Gson gson = new Gson();
				
					String jsonList = gson.toJson(userObj);
				        

					
					response.getWriter().write(jsonList);


					//new Gson().toJson(list, response.getWriter());
					
					
					
			}
		 catch(SQLException se)
			{
		      //Handle errors for JDBC
		      se.printStackTrace();
		      System.out.println("error:" + se.getMessage());
		    }
			catch(IOException e){
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
				if (rsGetResource != null) try { rsGetResource.close(); } catch (SQLException e) {e.printStackTrace();}
				if (stGetResource != null) try { stGetResource.close(); } catch (SQLException e) {e.printStackTrace();}

		   	 	
				if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				
				  

				
			}
	}

}
