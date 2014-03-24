package de.CodingDev.GlobalBan.Functions;

public class ReplaceArgs {
	private String key;
	private Object value;

	public ReplaceArgs(String key, Object value){
		this.key = key;
		this.value = value;
	}
	
	public String replaceAll(String input){
		input.replaceAll("{"+key+"}", String.valueOf(value));
		return input;
	}
}
