package trilane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trilane.Team;
import com.google.gson.Gson;
import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateDatabase
 */
@WebServlet("/CreateDatabase")
public class CreateDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateDatabase() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		createDatabase(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		createDatabase(request,response);
	}
	
	protected void createDatabase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
		try
		{

				ServletContext context = request.getServletContext();
				HttpSession session = request.getSession();
				
				//Initialise variables
				String company_name="";
				String company_address = "";
				int company_phone = 0;
				String admin_id = "";
				String admin_password="";
				String admin_email="";
				int intFreezePeriod = -1;
				int numTeams = -1;
				int numStatus=-1;
				String databaseName="";
				String companyLoginId ="";
				int is_confirmed=0;
				
				
				ArrayList<Team> teamList = new ArrayList();
				ArrayList<Status> statusList = new ArrayList();
				
				
				
				//company name
				if(request.getParameter("companyname")!=null && request.getParameter("companyname")!="")
				{
					company_name = request.getParameter("companyname");
					
				}
				
				
				//company_address
				if(request.getParameter("companyadd")!=null && request.getParameter("companyadd")!="")
				{
					company_address = request.getParameter("companyadd");
				}
				
				//company phone
				if(request.getParameter("companyphone")!=null && request.getParameter("companyphone")!="")
				{
					company_phone = Integer.parseInt(request.getParameter("companyphone"));
				}
				
				//admin_id
				if(request.getParameter("adminname")!=null && request.getParameter("adminname")!="")
				{
					admin_id= request.getParameter("adminname");
				}
				
				//admin_password
				if(request.getParameter("adminpass")!=null && request.getParameter("adminpass")!="")
				{
					admin_password= request.getParameter("adminpass");
				}
				
				//admin_email
				if(request.getParameter("adminemail")!=null && request.getParameter("adminemail")!="")
				{
					admin_email= request.getParameter("adminemail");
				}
				
				//freeze period
				if(request.getParameter("freezeperiod")!=null && request.getParameter("freezeperiod")!="")
				{
					intFreezePeriod = Integer.parseInt(request.getParameter("freezeperiod"));
				}
				
				//number of teams
				if(request.getParameter("numteams")!=null && request.getParameter("numteams")!="")
				{
					numTeams = Integer.parseInt(request.getParameter("numteams"));
				}
				
				//System.out.println(" no of teams: " + numTeams );
						
				
				//get team details
				for (int i=1; i<=numTeams;i++)
				{
					String team  = request.getParameter("inputteam" + i);
					
					int monLimit = Integer.parseInt(request.getParameter("monlimitteam"+i));
					
					int tueLimit = Integer.parseInt(request.getParameter("tuelimitteam"+i));
					
					int wedLimit = Integer.parseInt(request.getParameter("wedlimitteam"+i));
					
					int thuLimit = Integer.parseInt(request.getParameter("thulimitteam"+i));
					
					int friLimit = Integer.parseInt(request.getParameter("frilimitteam"+i));
					
					int satLimit = Integer.parseInt(request.getParameter("satlimitteam"+i));
					
					int sunLimit = Integer.parseInt(request.getParameter("sunlimitteam"+i));
					
					int pubHolLimit = Integer.parseInt(request.getParameter("hollimitteam"+i));
					
					
					//System.out.println("Team Details: " + team + " , " + monLimit + " , " + tueLimit + " , " + wedLimit + " , "  + thuLimit + " , " + friLimit +  " , " + satLimit + " , " + sunLimit + " , " + pubHolLimit ) ; 
					
					Team newTeam = new Team(team,monLimit,tueLimit,wedLimit,thuLimit,friLimit,satLimit,sunLimit,pubHolLimit);
					teamList.add(i-1,newTeam);
				}
				
				
				//Getting Status details
				//num of statuses
				if(request.getParameter("statusCount")!=null && request.getParameter("statusCount")!=null)
				{
					numStatus = Integer.parseInt(request.getParameter("statusCount"));
					//System.out.println("numbr of status: " + numStatus);
				}
				
				for(int j=1;j<=numStatus;j++)
				{
					int is_working_status=-1;
					String statusName  = request.getParameter("inputstatus" + j);
					String statusColour = request.getParameter("color" + j);
					//System.out.println("the value of checkbox :" + request.getParameter("working" + j));
					if(request.getParameter("working" + j)=="on"   || request.getParameter("working" + j)==null)
					{
						is_working_status=0;
					}
					else
					{
						is_working_status = Integer.parseInt(request.getParameter("working" + j));
					}
					
					//System.out.println("status: " + statusName);
					Status statusObject = new Status(statusName,statusColour,is_working_status);
					statusList.add(j-1,statusObject);
					
					
				}
				
				//database name
				if(request.getParameter("databasename")!=null && request.getParameter("databasename")!=null)
				{
					databaseName = request.getParameter("databasename");
					companyLoginId = databaseName;
				}
				
				//setup Administrator object
				Administrator objAdmin = new Administrator(admin_id, admin_password, admin_email);
				
				//setup CompanyObject
				
				Company companyObj = new Company(company_name,company_address,company_phone,companyLoginId,databaseName,intFreezePeriod,is_confirmed);
				
				CommonDatabase objCommonDatabase = new CommonDatabase();
				
				//save company details
				boolean isCompanySaved = objCommonDatabase.saveCompanyDetails(companyObj, context, objAdmin, session);
				
				//if company details saved, save rest of the details, else forward to error page
				if(isCompanySaved)
				{
					//create new database
					boolean isDatabaseCreated = objCommonDatabase.createCompanyDatabase(companyObj, context, session,objAdmin);
					if(isDatabaseCreated)
					{
						//create tables
						boolean TablesCreated = objCommonDatabase.createTables(companyObj,teamList,context,session,objAdmin);
						if(TablesCreated)
						{
							boolean dataEntered  = objCommonDatabase.enterDataInTables(companyObj, teamList, statusList, objAdmin, context,session);
							
							if(dataEntered)
							{
								//create log file
								
								String strLogPath = context.getInitParameter("logfile-path");
								String fileName = companyObj.companyLoginId + ".txt";
								strLogPath = strLogPath.concat(fileName);
								File strFile = new File(strLogPath);
								if (!strFile.exists())
								{
									strFile.createNewFile();
								}
								
								Gson gson = new Gson();
								String jsonStringCompany = gson.toJson(companyObj);
								String jsonStringAdmin = gson.toJson(objAdmin);
								//response.sendRedirect(getServletContext().getContextPath() + "/email?company=" + jsonStringCompany  + "&admin=" + jsonStringAdmin );
								request.setAttribute("company", jsonStringCompany);
								request.setAttribute("admin", jsonStringAdmin);
								RequestDispatcher rd = request.getRequestDispatcher("/email");
								rd.forward(request,response);
							}
							else
							{
								response.sendRedirect(getServletContext().getContextPath() +  "/jsp/error.jsp");
							}
						}
						else
						{
							response.sendRedirect(getServletContext().getContextPath() +  "/jsp/error.jsp");
						}
						
					}
					else
					{
						response.sendRedirect(getServletContext().getContextPath() +  "/jsp/error.jsp");
					}
					
				}
				else
				{
					response.sendRedirect(getServletContext().getContextPath() +  "/jsp/error.jsp");
				}
		
				
				
				
				
		}
		catch(Exception ex)
		{
			System.out.println("Error occured in createDatabase.java: " + ex.toString() + ex.getStackTrace());
		}
		
	}

}
