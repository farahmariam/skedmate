package trilane;

public class Status 
{
	String statusName;
	String colour;
	int working;
	
	public Status()
	{
		
	}
	
	public Status(String name,String colour, int working)
	{
		this.statusName = name;
		this.colour = colour;
		this.working = working;
	}
	
	public String getStatusName()
	{
		return this.statusName;
	}
	
	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
		
	}
	public String getStatusColor()
	{
		return this.colour;
	}
	
	public void setStatusColour(String colour)
	{
		this.colour = colour;
		
	}
	public int getStatusWorking()
	{
		return this.working;
	}
	
	public void setStatusWorking(int working)
	{
		this.working = working;
		
	}

}
