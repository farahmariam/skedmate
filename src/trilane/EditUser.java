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
 * Servlet implementation class EditUser
 */
@WebServlet("/EditUser")
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		editUser(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		editUser(request,response);
	}
	
	
	protected void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    
	    BufferedWriter objWriter=  null;
	    HttpSession session = request.getSession();
	    
	    String adminName =(String)session.getAttribute("loginname");
		
	    
	    
	    ServletContext context = request.getServletContext();
	    
		String user = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		
		String technology = request.getParameter("tech");
		
		int techId = Integer.parseInt(technology);
		
		int isAdmin =  Integer.parseInt(request.getParameter("admin"));
		String newEmail = request.getParameter("email");
		
		
		
		
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
				String databaseUrl =(String) session.getAttribute("data-url-schedule");
				String databaseUser = context.getInitParameter("data-user");
				String databasePwd = context.getInitParameter("data-pwd");
				con =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
				
				//first check if the user was an admin before. If so, see if his status has changed. if so, then delete his email from global admin.
				//if he wasnt an admin before, check if he is admin now,..if so, insert his email in global admin.
				
				boolean wasAdmin = objCommon.checkIfAdmin(user, context, session);
				
				if(wasAdmin)
				{
					if(isAdmin==0)
					{
						//delete his email id from global_admin
						int companyId = objCommon.getCompanyIdFromLoginId(companyLoginId, context);
						String email = objCommon.getEmailIdOfResource(user, session, context); //get previous email id
						objCommon.deleteAdminMailFromGlobal(companyId,email , context);
						
						
					}
					else if(isAdmin==1)
					{
						Administrator adminObj = new Administrator(user,pwd,newEmail);
						
						//check if email id has changed. If it has, then update the global_admin table
						String oldEmail = objCommon.getEmailIdOfResource(user, session, context); 
						
						if(oldEmail.equals(newEmail))
						{
							//do nothing
						}
						else
						{
							//update new email in the global_admin table
							
							objCommon.UpdateEmailGlobalAdmin(context, session, adminObj);
						}
						
						//check if password has changed. If so update in global_admin table
						String oldPassword = objCommon.getPasswordOfResource(user, session, context);
						if(oldPassword.equals(pwd))
						{
							//do nothing
						}
						else
						{
							//update new password in the global_admin table
							
							objCommon.UpdatePasswordGlobalAdmin(context, session, adminObj);
						}
						
						
						
						
						
						
						
					}
				}
				else
				{
					if(isAdmin==1)
					{
						//insert his new email id into globaladmin table
						Administrator adminObject = new Administrator(user,pwd,newEmail);
						
						objCommon.insertIntoGlobalAdmin(context, session, adminObject);
						
					}
					
				}
				
				
				String query = "update users set email = ?, pwd = ?, tech_id = ?, is_admin = ? where uname=?";
			    preparedStmt = con.prepareStatement(query);
			    preparedStmt.setString(1,newEmail);
			    preparedStmt.setString(2,pwd);
			    preparedStmt.setInt(3,techId);
			    preparedStmt.setInt(4,isAdmin);
			    preparedStmt.setString(5,user);
			    
			    

			    int i = preparedStmt.executeUpdate();
			   
			    if (i > 0)
				{
					response.getWriter().write("User edited");
					
					String successMessage =dateFormat.format(cal.getTime()) + " -  Resource : '" + user + "' edited by admin: " + adminName +"." ;
					
					
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
