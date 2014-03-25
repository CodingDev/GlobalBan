package de.CodingDev.GlobalBan;

import org.bukkit.entity.Player;

public class PlayerPing extends Thread{
	private GlobalBan globalBan;
	public boolean running;
	
	public PlayerPing(GlobalBan globalBan){
		this.globalBan = globalBan;
	}
	
	public void run(){
		while(running){
			try{
				//Mark all players as Online.
				for(Player player : globalBan.getServer().getOnlinePlayers()){
					globalBan.playerChecker.checkPlayerByUID(player.getUniqueId());
				}
				
				Thread.sleep(30000);
			}catch(Exception e){
				globalBan.getLogger().warning("");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) { }
			}
		}
	}
}
