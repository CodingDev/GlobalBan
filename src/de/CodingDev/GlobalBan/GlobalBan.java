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
	
	public void onEnable(){
		globalBanServer = new GlobalBanServer(this);
		globalBanServer.checkAPIKeys();
		playerChecker = new PlayerChecker(this);
		getServer().getPluginManager().registerEvents(new GlobalBanEvents(this), this);
		playerChecker.startupCheck();
		Updater updater = new Updater(this, 45061, this.getFile(), UpdateType.NO_DOWNLOAD, true);
		if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
		    getLogger().info("New version available! " + updater.getLatestName());
		}else if (updater.getResult() == UpdateResult.NO_UPDATE) {
		    getLogger().info("No new version available");
		}else{
		    getLogger().info("Updater: " + updater.getResult());
		}
		try{
			Metrics metrics = new Metrics(this);
			metrics.start();
		}catch (IOException localIOException) {}
		getLogger().info("GlobalBan enabled.");
	}
	
	public void onDisable(){
		getLogger().info("GlobalBan disabled.");
	}
}
