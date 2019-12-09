package trilane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import trilane.CommonDatabase;
import com.google.gson.Gson;


/**
 * Servlet implementation class SaveHoliday
 */
@WebServlet("/SaveHoliday")
public class SaveHoliday extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveHoliday() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveHoliday(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveHoliday(request,response);
	}
	
	protected void saveHoliday(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");
	    
	    BufferedWriter objWriter=  null;
	    HttpSession session = request.getSession();
	    
	    String adminName =(String)session.getAttribute("loginname");
		
	    
	    
	    ServletContext context = request.getServletContext();
		boolean checked = Boolean.parseBoolean(request.getParameter("checked"));
		int date = Integer.parseInt(request.getParameter("date"));
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		
		//System.out.println("team limit array " + request.getParameterValues("jsonString"));
		
		 Gson gson = new Gson();
		 String[] teamLimits = gson.fromJson(request.getParameter("jsonString"), String[].class);

		
		
		
		//System.out.println("team limit array: " + teamLimits.toString());
		
		ArrayList<Integer> limitList = new ArrayList();
		
		for( int i = 0; i < teamLimits.length ; i++)
		{
			limitList.add((Integer.parseInt(teamLimits[i])));
		    
		}
		
		
		 Connection connection = null;
		
		 Statement stSaveAsHoliday = null;
		
		 PreparedStatement preparedStmt = null;
		 
		 
		 
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
					connection =(Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePwd);
					int holiday = -1;
					
					ArrayList<TeamObject> teamList = null;
					teamList = objCommon.getAllTeams(context, session);
					
					
					if(checked)
					{
						
							
							int holidayUpdate = 1;
							String insertHoliday = "insert into public_holiday (day, month, year, holiday"; // xr_limit, cat_limit) values (" + date + " , " + month + "," + year + "," + holidayUpdate + "," + xrLimit + "," +catLimit + ")";
							//get limit column names
							for(int loop=0; loop<teamList.size(); loop++)
       						{
       							TeamObject teamObject = teamList.get(loop);
       							
       							String teamName = teamObject.getTeamName();
       							String teamWithoutSpaces = teamName.replaceAll("\\s+","");
								String colName = teamWithoutSpaces.toLowerCase().trim() + "_limit";
								insertHoliday = insertHoliday.concat("," + colName);
       						}
							insertHoliday = insertHoliday.concat(") values (" + date + " , " + month + "," + year + "," + holidayUpdate );
							
							for(int loop=0;loop<limitList.size();loop++)
							{
								int limit = limitList.get(loop);
								insertHoliday = insertHoliday.concat( "," + limit);
							}
							insertHoliday = insertHoliday.concat( ")" );
							
							//System.out.println("insert query= " + insertHoliday);
							
							stSaveAsHoliday = connection.createStatement();
							int i = stSaveAsHoliday.executeUpdate(insertHoliday);
							
							if (i > 0)
							{
								response.getWriter().write("Saved as holiday!");
								String successMessage =dateFormat.format(cal.getTime()) + " -  Public Holiday :  Date: " + date +"/" + objCommon.getMonth(month) + "/" + year + ". Public Holiday added by admin: " + adminName +"." ;
								
								
								objWriter.write(successMessage);
								objWriter.newLine();
								objWriter.newLine();
							}
							else
							{
								response.getWriter().write("Error");
								String errorMessage =  dateFormat.format(cal.getTime()) + " - Error occured while trying to add public holiday on date: " + date +"/" + objCommon.getMonth(month) + "/" + year + ". Delete tried by admin:" + adminName + "." ;
								
								
								objWriter.write(errorMessage);
								objWriter.newLine();
								objWriter.newLine();
							}
						
							objWriter.flush();
					}
					else
					{
						//If unchecked then delete holiday
						String query = "delete from public_holiday where day = ? and month = ? and year=? ";
					    preparedStmt = connection.prepareStatement(query);
					    preparedStmt.setInt(1, date);
					    preparedStmt.setInt(2, month);
					    preparedStmt.setInt(3, year);

					    int i = preparedStmt.executeUpdate();
					   
					    if (i > 0)
						{
					    	response.getWriter().write("Deleted holiday!");
					    	String successMessage =dateFormat.format(cal.getTime()) + " -  Public Holiday deleted on :  Date: " + date +"/" + objCommon.getMonth(month) + "/" + year + ". Public Holiday deleted by admin: " + adminName +"." ;
							
							
							objWriter.write(successMessage);
							objWriter.newLine();
							objWriter.newLine();
							
						}
					    else
						{
							response.getWriter().write("Error");
							String errorMessage =  dateFormat.format(cal.getTime()) + " - Error occured while trying to delete public holiday on date: " + date +"/" + objCommon.getMonth(month) + "/" + year + ". Delete tried by admin:" + adminName + "." ;
							
							
							objWriter.write(errorMessage);
							objWriter.newLine();
							objWriter.newLine();
							
						}
						
						
					}
					
				
						
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
					if (stSaveAsHoliday != null) try { stSaveAsHoliday.close(); } catch (SQLException e) {e.printStackTrace();}
					if (preparedStmt != null) try { preparedStmt.close(); } catch (SQLException e) {e.printStackTrace();}

			   	 	if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			   	 	if(objWriter!=null) try { objWriter.close(); } catch(Exception ex) {ex.printStackTrace();}
				 
					
				}
	}


}
