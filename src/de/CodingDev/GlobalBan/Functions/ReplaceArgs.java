package de.CodingDev.GlobalBan.Functions;

public class ReplaceArgs {
	private String key;
	private String value;

	public ReplaceArgs(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String replaceAll(String input){
		return input.replaceAll("\\{"+key+"\\}", String.valueOf(value));
	}
}
