package trilane;

public class TeamObject {
	int teamId;
	String teamName;
   
    
    public TeamObject(int teamId,String teamName)
	{
    	this.teamId = teamId;
		this.teamName = teamName;
		
	}
	
	public String getTeamName()
	{
		return this.teamName;
	}
	
	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}
	

	public int getTeamId()
	{
		return this.teamId;
	}
	
	public void setTeamId(int teamId)
	{
		this.teamId = teamId;
	}

}
