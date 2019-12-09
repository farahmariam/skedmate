package trilane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.servlet.http.HttpSession;

import trilane.CommonDatabase;

/**
 * Servlet implementation class SaveNewUser
 */
@WebServlet("/SaveNewUser")
public class SaveNewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveNewUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveNewUser(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveNewUser(request,response);
	}
	
	protected void saveNewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    BufferedWriter objWriter=  null;
	    HttpSession session = request.getSession();
	    
	    ServletContext context = request.getServletContext();
	    
		String user = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		
		String technology = request.getParameter("tech");
		
		int techId = Integer.parseInt(technology);
		
		int isAdmin =  Integer.parseInt(request.getParameter("admin"));
		
		String email = "";
		email = request.getParameter("email");
		
		String adminName =(String)session.getAttribute("loginname");
		
		Connection conSchedule = null;
		Statement st = null;
		
		
		
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
			
			String scheduleDatabaseUrl = (String)session.getAttribute("data-url-schedule");
			String databaseUser = context.getInitParameter("data-user");
			String databasePwd = context.getInitParameter("data-pwd");
		
			conSchedule = DriverManager.getConnection(scheduleDatabaseUrl,databaseUser, databasePwd);
			
			
			
			st = conSchedule.createStatement();
			
			 String sqlQuery = "insert into users(email,uname, pwd, tech_id, is_admin) values ('" + email + "','" +  user + "' , '" + pwd + "'," + techId + "," + isAdmin + ")";
			 int i = st.executeUpdate(sqlQuery);
			 
			if (i > 0)
			{
				//if admin, then save entry in global_admin
				
				if(isAdmin==1)
				{
					Administrator adminObject = new Administrator(user,pwd,email);
					
					int updated = objCommon.insertIntoGlobalAdmin(context, session, adminObject);
					
					
					
					
				}
				
				
				
				response.getWriter().write("New User added");
				String successMessage =dateFormat.format(cal.getTime()) + " -  New Resource : '" + user + "' added by admin: " + adminName +"." ;
				
				
				objWriter.write(successMessage);
				objWriter.newLine();
				objWriter.newLine();
				
				} 
			else 
				{
				response.getWriter().write("Error");
				String errorMessage = dateFormat.format(cal.getTime()) +  " - Error occured while trying to add new resource:" + user + ". Add resource tried by admin: " + adminName + "." ;
				objWriter.write(errorMessage);
				objWriter.newLine();
				objWriter.newLine();
				
				
				}
				objWriter.flush();
			
		}
		catch(SQLException se)
		{
	      //Handle errors for JDBC
	      System.out.println(se.toString());
	    }
		catch(Exception e)
		{
	      //Handle errors for Class.forName
			System.out.println(e.toString());
	   	}
		finally
		{
			if (st != null) try { st.close(); } catch (SQLException e) {e.printStackTrace();}
			if (conSchedule != null) try { conSchedule.close(); } catch (SQLException e) {e.printStackTrace();}

			if(objWriter!=null) try { objWriter.close(); } catch(Exception ex) {ex.printStackTrace();}
			
			
			
		}
	    
	    
	}

}
