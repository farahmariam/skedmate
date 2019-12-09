package trilane;

public class Team 
{
	String teamName;
    int monResLimit;
    int tueResLimit;
    int wedResLimit;
    int thurResLimit;
    int friResLimit;
    int satResLimit;
    int sunResLimit;
    int pubHolResLimit;

	public Team()
	{
		
	}
	
	public Team(String teamName,int monResLimit,int tueResLimit,int wedResLimit,int thurResLimit,int friResLimit,int satResLimit,int sunResLimit,int pubHolResLimit)
	{
		this.teamName = teamName;
		this.monResLimit = monResLimit;
		this.tueResLimit = tueResLimit;
		this.wedResLimit = wedResLimit;
		this.thurResLimit = thurResLimit;
		this.friResLimit = friResLimit;
		this.satResLimit = satResLimit;
		this.sunResLimit = sunResLimit;
		this.pubHolResLimit = pubHolResLimit;
	}
	
	public String getTeamName()
	{
		return this.teamName;
	}
	
	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}
	
	public int getMonLimit()
	{
		return this.monResLimit;
	}
	
	public void setMonLimit(int monResLimit)
	{
		this.monResLimit = monResLimit;
	}
	
	public int getTueLimit()
	{
		return this.tueResLimit;
	}
	
	public void setTueLimit(int tueResLimit)
	{
		this.tueResLimit = tueResLimit;
	}
	
	public int getWedLimit()
	{
		return this.wedResLimit;
	}
	
	public void setWedLimit(int wedResLimit)
	{
		this.wedResLimit = wedResLimit;
	}
	
	public int getThurLimit()
	{
		return this.thurResLimit;
	}
	
	public void setThurLimit(int thurResLimit)
	{
		this.thurResLimit = thurResLimit;
	}
	
	public int getFriLimit()
	{
		return this.friResLimit;
	}
	
	public void setFriLimit(int friResLimit)
	{
		this.friResLimit = friResLimit;
	}
	
	public int getSatLimit()
	{
		return this.satResLimit;
	}
	
	public void setSatLimit(int satResLimit)
	{
		this.satResLimit = satResLimit;
	}
	
	public int getSunLimit()
	{
		return this.sunResLimit;
	}
	
	public void setSunLimit(int sunResLimit)
	{
		this.sunResLimit = sunResLimit;
	}
	
	public int getPubHolLimit()
	{
		return this.pubHolResLimit;
	}
	
	public void setPubHolLimit(int pubHolResLimit)
	{
		this.pubHolResLimit = pubHolResLimit;
	}
	
	

}
