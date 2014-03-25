package de.CodingDev.GlobalBan;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import de.CodingDev.GlobalBan.Functions.ReplaceArgs;
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
	public String prefix = "§6[GlobalBan] ";
	public String newVersionName;
	public boolean newVersion;
	
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
			    newVersion = true;
			    newVersionName = updater.getLatestName();
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
	
	public String getMessage(String path, ReplaceArgs... args){
		String baseMessage = prefix + getConfig().getString("Messages." + getConfig().getString("Basic.Language") + "." + path);
		for(ReplaceArgs arg : args){
			baseMessage = arg.replaceAll(baseMessage);
		}
		return ChatColor.translateAlternateColorCodes('&', baseMessage);
	}
	
	public void syncConfig(){
		//Test Key for Debug
		//Reset by Boot
		getConfig().set("Basic.ServerKey", "88ea39439e74fa27c09a4fc0bc8ebe6d00978392");
		getConfig().set("Basic.BungeeCord", false);
		getConfig().set("Basic.ShowProtectInfo", true);
		getConfig().set("Basic.Debug", true);
		getConfig().set("Basic.Language", "EN");
		getConfig().set("Ban.GlobalBan.MaxPoints", 10);
		
		getConfig().set("Messages.EN.Ban.GlobalBan.MaxPoints", "&cYou GlobalBan Account has too many Bans! (We allow max {ban_count} GlobalBans on our Server)");
		getConfig().set("Messages.EN.Basic.ShowProtectInfo", "This Server is GlobalBan protected.");
		getConfig().set("Messages.EN.Basic.NewVersion", "A new Version is available! (&c{new_version}&6)");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable(){
		getLogger().info("GlobalBan disabled.");
	}

	public void debug(String message, boolean global) {
		if(global){
			getServer().broadcastMessage(prefix + "[DEBUG] " + message);
		}else{
			getServer().getLogger().info(prefix + "[DEBUG] " + message);
		}
	}
}
