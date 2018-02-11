package xin.omen.titlebroadcaster;

import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.command.args.GenericArguments;
import org.slf4j.Logger;
import com.google.inject.Inject;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import xin.omen.titlebroadcaster.commands.CommandMain;
import org.spongepowered.api.Game;

@Plugin(id="titlebroadcaster", name="TitleBroadcaster", version="1.0.0-SNAPSHOT", authors="NipoCN")
public class TitleBroadcaster {
	
	@Inject
	private Game game;
	
	@Inject
	private Logger logger;
	
	private static TitleBroadcaster instance;
	
	@Listener
	public void onGameAboutToStartServerEvent(GameAboutToStartServerEvent event){
		instance = this;
		CommandSpec mainCommand = CommandSpec.builder()
				.description(Text.of("TitleBroadcaster Main Command"))
				.arguments(
						GenericArguments.remainingJoinedStrings(Text.of("message"))
				)
				.executor(new CommandMain())
				.build();
		game.getCommandManager().register(this, mainCommand, "titlebroadcaster", "tb");
		logger.info("Plugin Initialized, thanks for using TitleBroadcaster by NipoCN");
	}
}
