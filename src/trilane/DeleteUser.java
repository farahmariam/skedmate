package trilane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import trilane.CommonDatabase;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		deleteUser(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		deleteUser(request,response);
	}
	
	protected void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    
	    BufferedWriter objWriter=  null;
	    HttpSession session = request.getSession();
	    
	    String adminName =(String)session.getAttribute("loginname");
		
	    
	    
	    ServletContext context = request.getServletContext();
		String user = request.getParameter("username");
		
		
		
		 Connection con = null;
		 PreparedStatement preparedStmt = null;
		 ResultSet res = null;
		    
		try
		{
				String companyLoginId = (String)session.getAttribute("companyLoginId");
				String strLogPath = context.getInitParameter("logfile-path");
				String fileName = companyLoginId + ".txt";
				strLogPath = strLogPath.concat(fileName);
				File strFile = new File(strLogPath);
				if (!strFile.exists())
				{
					strFile.createNewFile();
				}
				
				
				 //File appending
				objWriter = new BufferedWriter(new FileWriter(strFile,true));
				
				
				//get current date time
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				CommonDatabase objCommon = new CommonDatabase();
			
				Class.forName("com.mysql.jdbc.Driver");
				String databaseUrl = (String)session.getAttribute("data-url-schedule");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				con =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				//first check if the resource to be deleted was an admin
				//if so delete his email from globaladmin table
				
				boolean wasAdmin = objCommon.checkIfAdmin(user, context, session);
				
				if(wasAdmin)
				{
					//delete from global_admin
					//delete his email id from global_admin
					int companyId = objCommon.getCompanyIdFromLoginId(companyLoginId, context);
					String email = objCommon.getEmailIdOfResource(user, session, context);
					objCommon.deleteAdminMailFromGlobal(companyId,email , context);
				
				}
				
				String query = "delete from users where uname = ?";
			    preparedStmt = con.prepareStatement(query);
			    preparedStmt.setString(1,user);

			    int i = preparedStmt.executeUpdate();
			   
			    if (i > 0)
				{
					response.getWriter().write("User deleted");
					
					String successMessage =dateFormat.format(cal.getTime()) + " -  Resource : '" + user + "' deleted by admin: " + adminName +"." ;
					
					
					objWriter.write(successMessage);
					objWriter.newLine();
					objWriter.newLine();
					
				}else
				{
					response.getWriter().write("Error");
					String errorMessage = dateFormat.format(cal.getTime()) +  " - Error occured while trying to delete resource:" + user + ". Delete resource tried by admin: " + adminName + "." ;
					objWriter.write(errorMessage);
					objWriter.newLine();
					objWriter.newLine();
					
				}
			    objWriter.flush();
					
			}
			catch (SQLException e)
			{
				System.out.println(e.toString());  
				response.getWriter().write("Error");
			}
			catch (Exception e)
			{
				System.out.println(e.toString()); 
				response.getWriter().write("Error");
			}
			finally
			{
				if (preparedStmt != null) try { preparedStmt.close(); } catch (SQLException e) {e.printStackTrace();}

		   	 	if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
		   	    if(objWriter!=null) try { objWriter.close(); } catch(Exception ex) {ex.printStackTrace();}
				
			}
	}

}
