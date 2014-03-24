package de.CodingDev.GlobalBan;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GlobalBanCommandExecutor implements CommandExecutor {
	private GlobalBan globalBan;
	public GlobalBanCommandExecutor(GlobalBan globalBan) {
		this.globalBan = globalBan;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg1, String[] args) {
		if(cmd.getName().equalsIgnoreCase("globalban")){
			if(args.length == 2){
				if(args[0].equalsIgnoreCase("profile")){
					CheckResult cr = globalBan.playerChecker.checkPlayer(args[1]);
					if(cr.containsErrors()){
						sender.sendMessage(cr.getErrors());
					}else{
						cr.getProfile(sender);
					}
				}
			}
		}
		
		return true;
	}

}
