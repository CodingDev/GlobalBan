package de.CodingDev.GlobalBan;

import org.bukkit.command.CommandSender;
import org.json.simple.JSONObject;

public class CheckResult {
	private String playerName;
	private BanTypes banResult = BanTypes.CLEAN;
	private JSONObject serverResult;
	private boolean containsErrors;
	private int globalBanPoints;
	private String banReason;
	private long timeLeft;
	private String errors;
	GlobalBan globalBan;
	
	public CheckResult(GlobalBan globalBan, String playerName){
		this.globalBan = globalBan;
		JSONObject obj=new JSONObject();
		obj.put("method", "checkPlayer");
		obj.put("username", playerName);
		
		serverResult = globalBan.globalBanServer.sendPostRequest(obj);
		if(serverResult.containsKey("playerResult")){
			banResult = BanTypes.valueOf(serverResult.get("banResult").toString());
			globalBanPoints = Integer.parseInt(serverResult.get("globalBanPoints").toString());
			banReason = serverResult.get("banReason").toString();
			timeLeft = Long.parseLong(serverResult.get("timeLeft").toString());
		}else{
			containsErrors = true;
			errors = "Cant send API request.";
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

	public void getProfile(CommandSender sender) {
		// TODO Auto-generated method stub
		
	}

	public String getErrors() {
		return errors;
	}
}
