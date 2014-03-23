package de.CodingDev.GlobalBan;

import org.bukkit.entity.Player;

public class PlayerChecker {
	GlobalBan globalBan;
	
	public PlayerChecker(GlobalBan globalBan){
		this.globalBan = globalBan;
	}
	
	public CheckResult checkPlayer(String playerName){
		return new CheckResult(playerName);
	}
	
	public void startupCheck(){
		for(Player player : globalBan.getServer().getOnlinePlayers()){
			CheckResult cr = checkPlayer(player.getName());
			if(cr.getReult() != BanTypes.CLEAN){
				player.kickPlayer(cr.getReason());
			}
		}
	}
	
}
