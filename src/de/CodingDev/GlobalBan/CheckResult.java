package de.CodingDev.GlobalBan;

import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.json.simple.JSONObject;

public class CheckResult {
	private String playerName;
	private BanTypes banResult = BanTypes.CLEAN;
	private JSONObject serverResult;
	private boolean containsErrors = false;
	private int globalBanPoints;
	private String banReason;
	private long timeLeft;
	private String errors;
	private String userID;
	GlobalBan globalBan;
	
	public CheckResult(GlobalBan globalBan, UUID uuid, String playerName){
		this.globalBan = globalBan;
		JSONObject obj=new JSONObject();
		obj.put("method", "checkPlayer");
		if(uuid != null){
			obj.put("uid", uuid.toString());
		}else if(playerName != null){
			obj.put("username", playerName);
		}
		
		serverResult = globalBan.globalBanServer.sendPostRequest(obj);
		if(serverResult != null){ 
			if(serverResult.containsKey("playerResult")){
				banResult = BanTypes.valueOf(serverResult.get("banResult").toString());
				globalBanPoints = Integer.parseInt(serverResult.get("globalBanPoints").toString());
				banReason = serverResult.get("banReason").toString();
				timeLeft = Long.parseLong(serverResult.get("timeLeft").toString());
				userID = serverResult.get("userID").toString();
				this.playerName = serverResult.get("playerName").toString();
			}else{
				containsErrors = true;
				errors = "Cant send API request.";
			}
		}else{
			containsErrors = true;
			errors = "The GlobalBan Auth server is Offline, try again later.";
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
	
	public String getName() {
		return playerName;
	}
	
	public String getUID() {
		return userID;
	}

	public void getProfile(CommandSender sender) {
		sender.sendMessage("§6~~~~~~~~~~ GlobalBan Result ~~~~~~~~~~");
		sender.sendMessage("§6Playername: §c" + getName());
		sender.sendMessage("§6UID: §c" + getUID());
		sender.sendMessage("§6GlobalBans Points: §c" + getPoints());
		sender.sendMessage("§6~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	public String getErrors() {
		return errors;
	}
}
