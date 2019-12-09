package trilane;

public class StatusObject 
{
	
	String statusName;
	int statusId;
	int is_working;
	String colour;
	
	public StatusObject()
	{
		
	}
	
	public StatusObject(int id,String name,int is_working,String colour)
	{
		this.statusName = name;
		this.statusId = id;
		this.is_working  = is_working;
		this.colour = colour;
	}
	
	public String getStatusName()
	{
		return this.statusName;
	}
	
	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
		
	}
	public int getStatusId()
	{
		return this.statusId;
	}
	
	public void setStatusId(int id)
	{
		this.statusId = id;
		
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
		return this.is_working;
	}
	
	public void setStatusWorking(int working)
	{
		this.is_working = working;
		
	}

}
