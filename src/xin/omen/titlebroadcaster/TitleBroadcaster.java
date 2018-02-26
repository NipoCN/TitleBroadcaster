package xin.omen.titlebroadcaster;

import xin.omen.titlebroadcaster.config.Config;

import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.command.args.GenericArguments;

import java.nio.file.Path;
import java.nio.file.Files;

import org.slf4j.Logger;
import com.google.inject.Inject;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.text.Text;

import xin.omen.titlebroadcaster.commands.CommandMain;
import org.spongepowered.api.Game;

@Plugin(id="titlebroadcaster", name="TitleBroadcaster", version="1.2.0-SNAPSHOT", authors="NipoCN")
public class TitleBroadcaster {
	
	@Inject
	private Game game;
	
	@Inject
	private Logger logger;
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path ConfigDir;
	
	private static TitleBroadcaster instance;
	
	@Listener
	public void onGameAboutToStartServerEvent(GameAboutToStartServerEvent event){
		instance = this;
		if (!Files.exists(ConfigDir)){
			try {
				Files.createDirectories(ConfigDir);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		Config.getInstance().loadConfig();
		CommandSpec mainCommand = CommandSpec.builder()
				.description(Text.of("TitleBroadcaster Main Command"))
				.arguments(
						GenericArguments.remainingJoinedStrings(Text.of("message"))
				)
				.executor(new CommandMain())
				.build();
		game.getCommandManager().register(this, mainCommand, "titlebroadcaster", "tb");
		logger.info("--------------------------------------------------------------");
		logger.info("Thank you for using TitleBroadcaster made by NipoCN");
		logger.info("--------------------------------------------------------------");
	}
	
	public Path getConfigDir(){
		return ConfigDir;
	}
	
	public static TitleBroadcaster getInstance(){
		if (instance == null){
			instance = new TitleBroadcaster();
		}
		return instance;
	}
	
}
