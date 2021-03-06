package de.CodingDev.GlobalBan;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class GlobalBanServer {
	private GlobalBan globalBan;
	private String serverKey;
	
	public GlobalBanServer(GlobalBan globalBan){
		this.globalBan = globalBan;
	}
	
	public boolean checkAPIKeys(){
		if(globalBan.getConfig().getString("Basic.ServerKey").length() > 0){
			JSONObject obj=new JSONObject();
			obj.put("method","checkServerKey");
			obj.put("serverKey", globalBan.getConfig().getString("Basic.ServerKey"));
			JSONObject serverResult = sendPostRequest(obj);
			if(serverResult != null){
				if(serverResult.get("validKey").equals("true")){
					serverKey = globalBan.getConfig().getString("Basic.ServerKey");
					globalBan.getLogger().info("The ServerKey is Valid.");
					return true;
				}else{
					globalBan.getLogger().warning("The ServerKey is Invalid! Create a Key on https://globalban.net/serverKey");
					globalBan.getLogger().warning("Disable GlobalBan...");
					globalBan.getPluginLoader().disablePlugin(globalBan);
				}
			}else{
				globalBan.getLogger().warning("Can not connect to http://globalban.net. Server Offline?");
				globalBan.getLogger().warning("Disable GlobalBan...");
				globalBan.getPluginLoader().disablePlugin(globalBan);
			}
		}else{
			globalBan.getLogger().warning("The ServerKey is Invalid! Create a Key on https://globalban.net/serverKey");
			globalBan.getLogger().warning("Disable GlobalBan...");
			globalBan.getPluginLoader().disablePlugin(globalBan);
		}
		return false;
	}
	
	public boolean registerPlayer(Player player) {
		JSONObject serverResult;
		JSONObject obj=new JSONObject();
		obj.put("method", "checkPlayer");
		obj.put("uid", player.getUniqueId().toString());
		
		serverResult = globalBan.globalBanServer.sendPostRequest(obj);
		if(serverResult != null){ 
			if(serverResult.containsKey("registerResult")){
				return true;
			}
		}
		return false;
	}
	
	public JSONObject sendPostRequest(JSONObject args){
		try{
			if(!args.containsKey("serverKey")){
				args.put("serverKey", serverKey);
			}
			String urlArgs = urlEncodeUTF8(args);
			if(globalBan.getConfig().getBoolean("Basic.Debug")){
				globalBan.debug("[SERVER] [SEND]" + urlArgs, false);
			}
			URL oracle = new URL("http://127.0.0.1/server_api/v1/?"+urlArgs);
	        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
	        String inputLine = in.readLine();
	        Object obj = JSONValue.parse(inputLine);
	        in.close();
	        if(globalBan.getConfig().getBoolean("Basic.Debug")){
				globalBan.debug("[SERVER] [READ]" + inputLine, false);
			}
	        
			return (JSONObject) obj;
		}catch(Exception e){
			globalBan.getLogger().warning("sendPostRequest Error: " + e);
			return null;
		}
	}
	
	String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                urlEncodeUTF8(entry.getKey().toString()),
                urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();       
    }
}
