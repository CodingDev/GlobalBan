package de.CodingDev.GlobalBan;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class GlobalBanEvents implements Listener {
	GlobalBan globalBan;
	
	public GlobalBanEvents(GlobalBan globalBan){
		this.globalBan = globalBan;
	}
	
	@EventHandler
	public void onPreLofin(PlayerLoginEvent e){
		if(globalBan.globalBanServer.registerPlayer(e.getPlayer())){
			CheckResult cr = globalBan.playerChecker.checkPlayerByPlayer(e.getPlayer());
			if(!cr.containsErrors()){
				if(cr.getReult() != BanTypes.CLEAN){
					e.setKickMessage(cr.getReason());
					e.setResult(Result.KICK_OTHER);
				}else if(cr.getPoints() >= globalBan.getConfig().getInt("Ban.GlobalBan.MaxPoints")){
					e.setKickMessage(globalBan.getConfig().getString("Messages.Ban.GlobalBan.MaxPoints").replaceAll("{ban_count}", globalBan.getConfig().getString("Ban.GlobalBan.MaxPoints")));
					e.setResult(Result.KICK_OTHER);
				}
			}else{
				e.setKickMessage("Can not get CheckResult... GlobalBan Offline? Error: " + cr.getErrors());
				e.setResult(Result.KICK_OTHER);
			}
		}else{
			e.setKickMessage("Can not Register/Login on GlobalBan... GlobalBan Offline?");
			e.setResult(Result.KICK_OTHER);
		}
	}
}
