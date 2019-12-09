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
import java.util.*;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TotalResouceInEachTeamChart
 */
@WebServlet("/TotalResouceInEachTeamChart")
public class TotalResouceInEachTeamChart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TotalResouceInEachTeamChart() {
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
		String databaseUrl =(String) session.getAttribute("data-url-schedule");
		String databaseUser = context.getInitParameter("data-user");
		String databasePwd = context.getInitParameter("data-pwd");
		Connection connection = null;
		DefaultPieDataset pieDataset= null;
		
		DefaultCategoryDataset barDataset = null;
				
		OutputStream out = null;
		Statement stUsers = null;
		ResultSet rsUsers = null;
		
		
		
		//int xrCounter = 0;
		//int catCounter = 0;
		
		
		String typeChart = request.getParameter("type");
		int user_id=-1;
		int tech_id=-1;
		//System.out.println("equals xrpie");
		try
		{
			
			pieDataset = new DefaultPieDataset();
			
			barDataset = new DefaultCategoryDataset();
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(databaseUrl,databaseUser, databasePwd);
			
			CommonDatabase obj = new CommonDatabase();
			ArrayList<TeamObject> teamList = obj.getAllTeams(context, session);
			
			
			for(int count=0;count<teamList.size();count++)
			{
				TeamObject team = teamList.get(count);
				int loopTechId = team.getTeamId();
				String teamName = team.getTeamName();
				String userQuery = "select count(*) from users where tech_id=" + loopTechId;
				stUsers =connection.createStatement();
				int totalCount = 0;
				rsUsers = stUsers.executeQuery(userQuery);
				if(rsUsers.next())
				{
					totalCount = rsUsers.getInt(1);
				}
				pieDataset.setValue("Total Resources in " + teamName , totalCount);
				barDataset.addValue(totalCount, "Total Resources" , teamName.toUpperCase());
				
			}

			
			
			 
			 if(typeChart.equalsIgnoreCase("pie"))
			 {
				 JFreeChart chart = ChartFactory.createPieChart3D("Total Resources in Teams", pieDataset, true, true, false);
				  chart.setBorderPaint(Color.black);
				 chart.setBorderStroke(new BasicStroke(10.0f));
				 chart.setBorderVisible(true);
				 PiePlot plot = (PiePlot) chart.getPlot();
			     plot.setSectionOutlinesVisible(false);
			     plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
			     plot.setNoDataMessage("No data available");
			     //plot.setCircular(false);
			     plot.setLabelGap(0.08);
			     plot.setToolTipGenerator(new StandardPieToolTipGenerator());
			     
			     PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0} = {1}");
					
			     plot.setLabelGenerator(generator);
			     plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1}"));
					
			     
			    ChartPanel chartPanel = new ChartPanel(chart);  
			    
			    if(chart!=null)
				 {
					 int width=600;
					 int height=400;
					 final ChartRenderingInfo info =  new ChartRenderingInfo(new StandardEntityCollection());
					 response.setContentType("image/png");
					 out = response.getOutputStream();
					 ChartUtilities.writeChartAsPNG(out, chart, width, height, info);
					 
				 }
				
				
			 }
			 
			 else if (typeChart.equalsIgnoreCase("bar"))
			 {
				 
				
				 JFreeChart barChart = ChartFactory.createBarChart3D("Total Resources in each team.","Team","Total Resources",barDataset,PlotOrientation.VERTICAL,true, true, false);
				 ValueAxis yAxis = new NumberAxis3D("Total Resources in each team");
				 yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				 CategoryPlot plot = (CategoryPlot) barChart.getPlot();
				 plot.setRangeAxis(yAxis);
				 
				 
				 barChart.setBorderPaint(Color.black);
				 barChart.setBorderStroke(new BasicStroke(10.0f));
				 barChart.setBorderVisible(true);
				 if(barChart!=null)
				 {
					 int width=600;
					 int height=400;
					 final ChartRenderingInfo info =  new ChartRenderingInfo(new StandardEntityCollection());
					 response.setContentType("image/png");
					 out = response.getOutputStream();
					 ChartUtilities.writeChartAsPNG(out, barChart, width, height, info);
					 
				 }
				 
			 }
			 
			 
			 
			 
			 
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
			if (out != null) try { out.close(); } catch (Exception e) {e.printStackTrace();}
			
			if (stUsers != null) try { stUsers.close(); } catch (Exception e) {e.printStackTrace();}
			if (rsUsers != null) try { rsUsers.close(); } catch (Exception e) {e.printStackTrace();}
			
			
			if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
			
			
		}
	}

	

}
