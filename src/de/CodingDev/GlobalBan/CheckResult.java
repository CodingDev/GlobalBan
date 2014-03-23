package de.CodingDev.GlobalBan;

public class CheckResult {
	private String playerName;
	private BanTypes banResult = BanTypes.CLEAN;
	
	public CheckResult(String playerName){
		
	}
	
	public BanTypes getReult(){
		return banResult;
	}
	
	public String getReason(){
		return null;
	}
	
	public int getTimeLeft(){
		return 0;
	}
}
