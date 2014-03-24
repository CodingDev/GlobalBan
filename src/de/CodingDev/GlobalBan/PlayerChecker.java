package de.CodingDev.GlobalBan;

import org.bukkit.entity.Player;

public class PlayerChecker {
	private GlobalBan globalBan;
	
	public PlayerChecker(GlobalBan globalBan){
		this.globalBan = globalBan;
	}
	
	public CheckResult checkPlayer(String playerName){
		return new CheckResult(globalBan, playerName);
	}
	
	public void startupCheck(){
		for(Player player : globalBan.getServer().getOnlinePlayers()){
			CheckResult cr = checkPlayer(player.getName());
			if(cr != null){
				if(cr.getReult() != BanTypes.CLEAN){
					player.kickPlayer(cr.getReason()); 
				}else if(cr.getPoints() >= globalBan.getConfig().getInt("Ban.GlobalBan.MaxPoints")){
					player.kickPlayer(globalBan.getConfig().getString("Messages.Ban.GlobalBan.MaxPoints").replaceAll("{ban_count}", globalBan.getConfig().getString("Ban.GlobalBan.MaxPoints")));
				}
			}else{
				globalBan.getLogger().warning("Can´t get CheckResult... GlobalBan Offline?");
			}
		}
	}
	
}
