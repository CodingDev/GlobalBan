package de.CodingDev.GlobalBan;

import org.bukkit.plugin.java.JavaPlugin;

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
		getLogger().info("GlobalBan enabled.");
	}
	
	public void onDisable(){
		getLogger().info("GlobalBan disabled.");
	}
}
