package de.CodingDev.GlobalBan;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import de.CodingDev.GlobalBan.Metrics.Metrics;
import de.CodingDev.GlobalBan.UpdateChecker.Updater;
import de.CodingDev.GlobalBan.UpdateChecker.Updater.UpdateResult;
import de.CodingDev.GlobalBan.UpdateChecker.Updater.UpdateType;

public class GlobalBan extends JavaPlugin{
	
	/*
	 * Code Cleanup from		: 23.03.2014 19:00 
	 * Authors					: R3N3PDE
	 * Website from R3N3PDE 	: http://r3n3p.de
	 * Website from CodingDev	: http://codingdev.de
	 * Website from GlobalBan 	: http://globalban.net 
	 * 
	 * */
	
	public PlayerChecker playerChecker;
	public GlobalBanServer globalBanServer;
	public GlobalBanCommandExecutor globalBanCommandExecutor;
	
	public void onEnable(){
		syncConfig();
		globalBanServer = new GlobalBanServer(this);
		if(globalBanServer.checkAPIKeys()){
			playerChecker = new PlayerChecker(this);
			getServer().getPluginManager().registerEvents(new GlobalBanEvents(this), this);
			playerChecker.startupCheck();
			globalBanCommandExecutor = new GlobalBanCommandExecutor(this);
			getCommand("globalban").setExecutor(globalBanCommandExecutor);
			
			//Updater
			Updater updater = new Updater(this, 45061, this.getFile(), UpdateType.NO_DOWNLOAD, true);
			if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
			    getLogger().info("New version available! " + updater.getLatestName());
			}else if (updater.getResult() == UpdateResult.NO_UPDATE) {
			    getLogger().info("No new version available");
			}else{
			    getLogger().info("Updater: " + updater.getResult());
			}
			//Metrics
			try{
				Metrics metrics = new Metrics(this);
				metrics.start();
			}catch (IOException localIOException) {}
			getLogger().info("GlobalBan enabled.");
		}
	}
	
	public void syncConfig(){
		getConfig().addDefault("Basic.ServerKey", "");
		getConfig().addDefault("Basic.BungeeCord", false);
		getConfig().addDefault("Ban.GlobalBan.MaxPoints", 10);
		
		getConfig().addDefault("Messages.Ban.GlobalBan.MaxPoints", "&cYou GlobalBan Account has too many Bans! (We allow max {ban_count} GlobalBans on our Server)");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable(){
		getLogger().info("GlobalBan disabled.");
	}
}
