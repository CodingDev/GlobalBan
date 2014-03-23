package de.CodingDev.GlobalBan;

public class GlobalBanServer {
	GlobalBan globalBan;
	
	public GlobalBanServer(GlobalBan globalBan){
		this.globalBan = globalBan;
	}
	
	public void checkAPIKeys(){
		globalBan.getLogger().info("All Keys are Ok.");
	}
	
	public void sendPostRequest(){
		//TODO: Create Method...
	}
}
