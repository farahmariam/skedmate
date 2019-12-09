package trilane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trilane.CommonDatabase;
import trilane.Status;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class PopulateStatusBox
 */
@WebServlet("/PopulateStatusBox")
public class PopulateStatusBox extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PopulateStatusBox() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		populateStatus(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		populateStatus(request,response);
	}
	
	
	protected void populateStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");

		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		
		
		
		 Connection connection = null;		
		 Statement stGetStatus = null;		 
		 ResultSet rsGetStatus  = null;
		 
		
		 
		
		
		 ArrayList<StatusObject> statusList = new ArrayList();
		 
		
		 
		 try
			{
			 		
			 
					Class.forName("com.mysql.jdbc.Driver");
					String databaseUrl = (String)session.getAttribute("data-url-schedule");
					String databaseUser = context.getInitParameter("data-user");
					String databasePwd = context.getInitParameter("data-pwd");
					connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
					
					
					//add status list to arraylist
					String idQuery = "select * from status"; 
					stGetStatus = connection.createStatement();
					rsGetStatus = stGetStatus.executeQuery(idQuery);
					
					int statusId=-1;
					String statusName="";
					int is_working=-1;
					String colour = "";
					while(rsGetStatus.next())
					{
						statusId = rsGetStatus.getInt("status_id");
						statusName = rsGetStatus.getString("status_name");
						is_working = rsGetStatus.getInt("is_working");
						colour =  rsGetStatus.getString("status_colour");
						
						//System.out.println("statusname: " + statusName + ", status id: "  + statusId);
						
						StatusObject statusObj = new StatusObject(statusId,statusName,is_working,colour);
						statusList.add(statusObj);
						
					}
					
					Gson gson = new Gson();
					
					String jsonList = gson.toJson(statusList);
				        

					
					response.getWriter().write(jsonList);


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
				if (stGetStatus != null) try { stGetStatus.close(); } catch (SQLException e) {e.printStackTrace();}
				if (rsGetStatus != null) try { rsGetStatus.close(); } catch (SQLException e) {e.printStackTrace();}
				
		   	 	
				if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
				

				
			}
	}

}
