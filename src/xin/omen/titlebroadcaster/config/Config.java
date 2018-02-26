package xin.omen.titlebroadcaster.config;

import xin.omen.titlebroadcaster.TitleBroadcaster;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Config {
	
	private static Config confinstance;
	public static Config getInstance(){
		if (confinstance == null){
			confinstance = new Config();
		}
		return confinstance;
	}
	
	Path ConfigFile = Paths.get(TitleBroadcaster.getInstance().getConfigDir() + "/config.conf");
	
	
	ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(ConfigFile).build();
	private CommentedConfigurationNode configNode;
	
	public void loadConfig() {
		if (!Files.exists(ConfigFile)){
			try{
				Files.createFile(ConfigFile);
				load();
				setup();
				save();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		else{
			load();
		}
	}
	
	public CommentedConfigurationNode get() {
		return configNode;
	}
	
	public void load(){
		try{
			configNode = loader.load();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void setup(){
		get().getNode("Subtitle", "server").setValue("&4&l - From Server");
		get().getNode("Subtitle", "server").setComment("set subtitle sent from console");
		get().getNode("Subtitle", "player").setValue("&9&l - From {player_name}");
		get().getNode("Subtitle", "player").setComment("set subtitle sent from player, you can use placeholders such as {player_name} and {player_displayname}");
		get().getNode("Duration", "fadein").setValue(10);
		get().getNode("Duration", "fadein").setComment("set fadein duration");
		get().getNode("Duration", "stay").setValue(60);
		get().getNode("Duration", "stay").setComment("set stay duration");
		get().getNode("Duration", "fadeout").setValue(10);
		get().getNode("Duration", "fadeout").setComment("set fadeout duration");
		get().getNode("permissioncheck").setValue("You do not have permission set use this command");
	}
	
	public void save(){
		try{
			loader.save(configNode);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public String getserver() {
		return get().getNode("Subtitle", "server").getString();
	}
	
	public String getplayer() {
		return get().getNode("Subtitle", "player").getString();
	}
	
	public int getfadein(){
		return get().getNode("Duration", "fadein").getInt();
	}
	
	public int getstay(){
		return get().getNode("Duration", "stay").getInt();
	}
	
	public int getfadeout(){
		return get().getNode("Duration", "fadeout").getInt();
	}
	
	public String getpermissioncheck(){
		return get().getNode("permissioncheck").getString();
	}
}
