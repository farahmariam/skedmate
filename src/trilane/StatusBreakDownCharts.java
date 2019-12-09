package trilane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.*;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import trilane.CommonDatabase;

/**
 * Servlet implementation class StatusBreakDownCharts
 */
@WebServlet("/StatusBreakDownCharts")
public class StatusBreakDownCharts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatusBreakDownCharts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request,response);
	}
	
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		
		
		String databaseUrl = (String)session.getAttribute("data-url-schedule");
		String databaseUser = context.getInitParameter("data-user");
		String databasePwd = context.getInitParameter("data-pwd");
		Connection connection = null;
		
		
		DefaultPieDataset pieWorkOrNotDataset= null;
		DefaultPieDataset pieWorkingStatusDataset= null;
		DefaultPieDataset pieNotWorkingStatusDataset= null;
		
		Statement stUsers = null;
		ResultSet rsUsers = null;
		Statement stEvent = null;
		ResultSet rsEvent = null;
		
		OutputStream out = null;
		
		
		
		int totalResWorkingCounter=0;
		int totalResNotWorkingCounter=0;
		
		
		
		try
		{
			int fromDay = Integer.parseInt(request.getParameter("fromDay"));
			int fromMonth = Integer.parseInt(request.getParameter("fromMonth"));
			int fromYear = Integer.parseInt(request.getParameter("fromYear"));
			
			int toDay = Integer.parseInt(request.getParameter("toDay"));
			int toMonth = Integer.parseInt(request.getParameter("toMonth"));
			int toYear =Integer.parseInt( request.getParameter("toYear"));
			
			
			
			String typeChart = request.getParameter("type");
			
			//start calendar
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.set(fromYear, fromMonth, fromDay);
			
			//end calendar
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.set(toYear, toMonth, toDay);
		
			
			Class.forName("com.mysql.jdbc.Driver");
			//connection obj
			connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			
			pieWorkOrNotDataset = new DefaultPieDataset();
			pieWorkingStatusDataset = new DefaultPieDataset();
			pieNotWorkingStatusDataset =  new DefaultPieDataset();
			
			CommonDatabase objCommon = new CommonDatabase();
			ArrayList<StatusObject> statusList = objCommon.getAllStatus(context, session);
			
			ArrayList<ArrayList<Integer>> workinglists = new ArrayList<ArrayList<Integer>>();
			ArrayList<ArrayList<Integer>> notworkinglists = new ArrayList<ArrayList<Integer>>();
			for(int j=0;j<statusList.size();j++)
			{
				
				
				
				ArrayList<Integer> workingstatus =  new ArrayList<Integer>();
				workinglists.add(j, workingstatus);
				
				ArrayList<Integer> notworkingstatus =  new ArrayList<Integer>();
				notworkinglists.add(j, workingstatus);
				
			}
			
			
			for (java.util.Date date = startCalendar.getTime(); !startCalendar.after(endCalendar); startCalendar.add(Calendar.DATE, 1), date = startCalendar.getTime())
			{
			
				int day=startCalendar.get(Calendar.DATE);
				int month=startCalendar.get(Calendar.MONTH);
				int year=startCalendar.get(Calendar.YEAR);
				int dayOfWeek = startCalendar.get(Calendar.DAY_OF_WEEK);
				
				
				String monthName = objCommon.getMonth(month);
				String dayName = objCommon.getDay(dayOfWeek);
				
				String userQuery = "select * from users";
				stUsers =connection.createStatement();
				
				rsUsers = stUsers.executeQuery(userQuery);
				
				int status_id=-1;
				int user_id=-1;
				int tech_id=-1;
				
				
				while (rsUsers.next())
				{
					user_id = rsUsers.getInt("user_id"); 
					tech_id = rsUsers.getInt("tech_id"); 
					String eventQuery = "select * from events where day=" + day + " and month=" + month + " and year =" + year + " and user_id = " + user_id ;
					stEvent = connection.createStatement();
					rsEvent = stEvent.executeQuery(eventQuery);
					if(rsEvent.next())
					{
						
							status_id = rsEvent.getInt("status_id");
							for(int i=0;i<statusList.size();i++)
							{
								StatusObject statusObj = statusList.get(i);
								int statusIdLoop = statusObj.getStatusId();
								
								int isWorking = statusObj.getStatusWorking();
								
								if(status_id==statusIdLoop)
								{
									if(isWorking==1)
									{
										totalResWorkingCounter++;
										workinglists.get(i).add(1);
									}
									else if(isWorking==0)
									{
										totalResNotWorkingCounter++;
										notworkinglists.get(i).add(1);
									}
									
									
								}
								
								
							}
					}
					else
					{
						//if no entry in events table, then default working status is assumed.
						totalResWorkingCounter++;
						
						workinglists.get(0).add(1);
					}
					
				}
				
				
			 }
			
			//System.out.println("working counter: " + totalResWorkingCounter);
			//System.out.println("not working counter: " + totalResNotWorkingCounter);
			
			
			int workingHours = totalResWorkingCounter * 8;
			int notWorkingHours = totalResNotWorkingCounter * 8;
			pieWorkOrNotDataset.setValue("Working Hours" , workingHours);
			pieWorkOrNotDataset.setValue("Not Working Hours" , notWorkingHours);
			
			for(int j=0;j<statusList.size();j++)
			{
				StatusObject statusObject = statusList.get(j);
				int statusIdLoop = statusObject.getStatusId();
				String statusName=statusObject.getStatusName();
				int is_working=statusObject.getStatusWorking();
				
				ArrayList<Integer> workingarray = workinglists.get(j);
				ArrayList<Integer> notworkingarray = notworkinglists.get(j);
				
				int hours =0;
				if(is_working==1)
				{
					hours = workingarray.size() * 8;
					pieWorkingStatusDataset.setValue(statusName + " hours ", hours);
					//System.out.println("status name: " +statusName + ", no of ppl : "  + workingarray.size());
					
				}
				else if(is_working==0)
				{
					hours = notworkingarray.size() * 8;
					pieNotWorkingStatusDataset.setValue(statusName + " hours ", hours);
					//System.out.println("status name: " +statusName + ", no of ppl : "  + notworkingarray.size());
					
				}
				
				
				
			}
			
			
			String header =objCommon.getMonth(fromMonth).substring(0, 3) + " " +  fromDay  + ", " + fromYear + " to " + objCommon.getMonth(toMonth).substring(0, 3) + " " + toDay + ", " + toYear;
			
			if(typeChart.equalsIgnoreCase("pieTotal"))
			{
				//working or not chart      
					JFreeChart workOrNotChart = ChartFactory.createPieChart3D("Resource Hours (working / not working) -  " + header , pieWorkOrNotDataset, true, true, false);
					workOrNotChart.setBorderPaint(Color.black);
					workOrNotChart.setBorderStroke(new BasicStroke(10.0f));
					workOrNotChart.setBorderVisible(true);
				 
					PiePlot workOrNotPlot = (PiePlot) workOrNotChart.getPlot();
					workOrNotPlot.setSectionOutlinesVisible(false);
					workOrNotPlot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
					workOrNotPlot.setNoDataMessage("No data available");
					//xrPlot.setCircular(false);
					workOrNotPlot.setLabelGap(0.08);
					workOrNotPlot.setToolTipGenerator(new StandardPieToolTipGenerator());
					workOrNotPlot.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);
					
					workOrNotPlot.setSectionPaint("Working Hours", new Color(153, 255, 153));
					workOrNotPlot.setSectionPaint("Not Working Hours", new Color(255, 51, 51));
					
					workOrNotPlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1}"));
					
					
					ChartPanel chartWorkOrNotPanel = new ChartPanel(workOrNotChart);  
					
					PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0} = {1},  {2}");
					//PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{2}");
					
					workOrNotPlot.setLabelGenerator(generator);
					
					 if(workOrNotChart!=null)
					 {
						 int width=800;
						 int height=500;
						 final ChartRenderingInfo info =  new ChartRenderingInfo(new StandardEntityCollection());
						 response.setContentType("image/png");
						 out = response.getOutputStream();
						 ChartUtilities.writeChartAsPNG(out, workOrNotChart, width, height, info);
						 
					 }
					
			}		
			else if(typeChart.equalsIgnoreCase("pieWork"))
			
			{
			
					//working status chart
					JFreeChart workStatusChart = ChartFactory.createPieChart3D("Working Hours - Status Breakdown for " + header , pieWorkingStatusDataset, true, true, false);
					workStatusChart.setBorderPaint(Color.black);
					workStatusChart.setBorderStroke(new BasicStroke(10.0f));
					workStatusChart.setBorderVisible(true);
				 
					PiePlot workStatusPlot = (PiePlot) workStatusChart.getPlot();
					workStatusPlot.setSectionOutlinesVisible(false);
					workStatusPlot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
					workStatusPlot.setNoDataMessage("No data available");
					//xrPlot.setCircular(false);
					workStatusPlot.setLabelGap(0.08);
					workStatusPlot.setToolTipGenerator(new StandardPieToolTipGenerator());
					//PieSectionLabelGenerator workStatusGenerator = new StandardPieSectionLabelGenerator("{0} = {1},  {2}");
					PieSectionLabelGenerator workStatusGenerator = new StandardPieSectionLabelGenerator("{0} = {1}");
					
					workStatusPlot.setLabelGenerator(workStatusGenerator);
					
					
					workStatusPlot.setSectionPaint("ONSHIFT Hours", new Color(153, 255, 153));
					workStatusPlot.setSectionPaint("BIC Hours", new Color(236, 227, 231));
					workStatusPlot.setSectionPaint("WOP Hours", new Color(255, 255, 128));
					
					workStatusPlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1}"));
					
					
					
					ChartPanel chartWorkStatusPanel = new ChartPanel(workStatusChart);
					
					
					 if(workStatusChart!=null)
					 {
						 int width=800;
						 int height=500;
						 final ChartRenderingInfo info =  new ChartRenderingInfo(new StandardEntityCollection());
						 response.setContentType("image/png");
						 out = response.getOutputStream();
						 ChartUtilities.writeChartAsPNG(out, workStatusChart, width, height, info);
						 
					 }
					
			}
			else if(typeChart.equalsIgnoreCase("pieNotWork"))
			{
					
					
					//not working status breakdown chart
					JFreeChart NotWorkingStatusChart = ChartFactory.createPieChart3D("Off Shift Hours -  Status Breakdown for " + header , pieNotWorkingStatusDataset, true, true, false);
					NotWorkingStatusChart.setBorderPaint(Color.black);
					NotWorkingStatusChart.setBorderStroke(new BasicStroke(10.0f));
					NotWorkingStatusChart.setBorderVisible(true);
					 
					PiePlot notWorkingStatusPlot = (PiePlot) NotWorkingStatusChart.getPlot();
					notWorkingStatusPlot.setSectionOutlinesVisible(false);
					notWorkingStatusPlot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
					notWorkingStatusPlot.setNoDataMessage("No data available");
					//catPlot.setCircular(false);
					notWorkingStatusPlot.setLabelGap(0.08);
					notWorkingStatusPlot.setToolTipGenerator(new StandardPieToolTipGenerator());
					
					//PieSectionLabelGenerator notWorkStatusGenerator = new StandardPieSectionLabelGenerator("{0} = {1},  {2}");
					PieSectionLabelGenerator notWorkStatusGenerator = new StandardPieSectionLabelGenerator("{0} = {1}");
					
					notWorkingStatusPlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1}"));
					
					
					notWorkingStatusPlot.setLabelGenerator(notWorkStatusGenerator);
					
					notWorkingStatusPlot.setSectionPaint("TRAINING Hours", new Color(77, 77, 255));
					notWorkingStatusPlot.setSectionPaint("OFFSHIFT Hours", new Color(255, 51, 51));
					notWorkingStatusPlot.setSectionPaint("NOT AVAILABLE Hours", new Color(236, 227, 231));
					notWorkingStatusPlot.setSectionPaint("WOP_LIEU Hours", new Color(230, 153, 255));
					
					
					ChartPanel chartNotWorkingStatusPanel = new ChartPanel(NotWorkingStatusChart);
					

					 if(NotWorkingStatusChart!=null)
					 {
						 int width=800;
						 int height=500;
						 final ChartRenderingInfo info =  new ChartRenderingInfo(new StandardEntityCollection());
						 response.setContentType("image/png");
						 out = response.getOutputStream();
						 ChartUtilities.writeChartAsPNG(out, NotWorkingStatusChart, width, height, info);
						 
					 }
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
        	if (stUsers != null) try { stUsers.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsUsers != null) try { rsUsers.close(); } catch (SQLException e) {e.printStackTrace();}
			
			if (stEvent != null) try { stEvent.close(); } catch (SQLException e) {e.printStackTrace();}
			if (rsEvent != null) try { rsEvent.close(); } catch (SQLException e) {e.printStackTrace();}
			
			
			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}

			
		
			
         }
		
		
	}
	

}
