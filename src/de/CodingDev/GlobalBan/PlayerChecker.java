package de.CodingDev.GlobalBan;

import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerChecker {
	private GlobalBan globalBan;
	
	public PlayerChecker(GlobalBan globalBan){
		this.globalBan = globalBan;
	}
	
	public CheckResult checkPlayerByName(String playerName){
		return new CheckResult(globalBan, null, playerName);
	}
	
	public CheckResult checkPlayerByUID(UUID uuid){
		return new CheckResult(globalBan, uuid, null);
	}
	
	public CheckResult checkPlayerByPlayer(Player player){
		return new CheckResult(globalBan, player.getUniqueId(), null);
	}
	
	public void startupCheck(){
		for(Player player : globalBan.getServer().getOnlinePlayers()){
			if(globalBan.globalBanServer.registerPlayer(player)){
				CheckResult cr = checkPlayerByPlayer(player);
				if(!cr.containsErrors()){
					if(cr.getReult() != BanTypes.CLEAN){
						player.kickPlayer(cr.getReason()); 
					}else if(cr.getPoints() >= globalBan.getConfig().getInt("Ban.GlobalBan.MaxPoints")){
						player.kickPlayer(globalBan.getConfig().getString("Messages.Ban.GlobalBan.MaxPoints").replaceAll("{ban_count}", globalBan.getConfig().getString("Ban.GlobalBan.MaxPoints")));
					}
				}else{
					globalBan.getLogger().warning("Can not get CheckResult... GlobalBan Offline? Error: " + cr.getErrors());
				}
			}else{
				globalBan.getLogger().warning("Can not Register/Login on GlobalBan... GlobalBan Offline?");
			}
		}
	}
	
}
