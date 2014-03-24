package de.CodingDev.GlobalBan;

import org.json.simple.JSONObject;

public class CheckResult {
	private String playerName;
	private BanTypes banResult = BanTypes.CLEAN;
	private JSONObject serverResult;
	private boolean containsErrors;
	private int globalBanPoints;
	private String banReason;
	private long timeLeft;
	GlobalBan globalBan;
	
	public CheckResult(GlobalBan globalBan, String playerName){
		this.globalBan = globalBan;
		JSONObject obj=new JSONObject();
		obj.put("method","checkPlayer");
		
		serverResult = globalBan.globalBanServer.sendPostRequest(obj);
		if(serverResult.containsKey("playerResult")){
			banResult = BanTypes.valueOf(serverResult.get("banResult").toString());
			globalBanPoints = Integer.parseInt(serverResult.get("globalBanPoints").toString());
			banReason = serverResult.get("banReason").toString();
			timeLeft = Long.parseLong(serverResult.get("timeLeft").toString());
		}else{
			containsErrors = true;
		}
	}
	
	public boolean containsErrors(){
		return containsErrors;
	}
	
	public BanTypes getReult(){
		return banResult;
	}
	
	public String getReason(){
		return banReason;
	}
	
	public long getTimeLeft(){
		return timeLeft;
	}

	public int getPoints() {
		return globalBanPoints;
	}
}
