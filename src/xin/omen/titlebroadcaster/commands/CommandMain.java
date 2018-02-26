package xin.omen.titlebroadcaster.commands;

import org.spongepowered.api.command.*;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.*;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.entity.living.player.*;
import org.spongepowered.api.text.title.*;
import org.spongepowered.api.Sponge;
import xin.omen.titlebroadcaster.config.Config;

public class CommandMain implements CommandExecutor {
	
	@Override
	public CommandResult execute (CommandSource Sender, CommandContext args) throws CommandException {
		if (Sender instanceof Player == false){
			String message = args.<String>getOne("message").get();
			if (message.equals("reload")){
				Config.getInstance().load();
				Sender.sendMessage(Text.of(TextColors.RED, "Successfully reloaded plugin"));
				return CommandResult.success();
			}
			Text messagetext = TextSerializers.FORMATTING_CODE.deserialize(message);
			Text serversubmessagetext = TextSerializers.FORMATTING_CODE.deserialize(Config.getInstance().getserver());
			Title send = Title.builder()
					.title(messagetext)
					.subtitle(serversubmessagetext)
					.fadeIn(Config.getInstance().getfadein())
					.stay(Config.getInstance().getstay())
					.fadeOut(Config.getInstance().getstay())
					.build();
			for (Player OnlinePlayer : Sponge.getServer().getOnlinePlayers()){
				OnlinePlayer.sendTitle(send);
			}
			Sender.sendMessage(Text.of(TextColors.RED, "Successfully Broadcasted from console"));
			return CommandResult.success();
		}
		if (!Sender.hasPermission("titlebroadcaster.use")){
			Sender.sendMessage(Text.of(TextColors.RED, Config.getInstance().getpermissioncheck()));
			return CommandResult.success();
		}
		Player player = (Player) Sender;
		String message = args.<String>getOne("message").get();
		if (message.equals("reload")){
			if (player.hasPermission("titlebroadcaster.reload")){
				Config.getInstance().load();
				Sender.sendMessage(Text.of(TextColors.RED, "Successfully reloaded plugin"));
				return CommandResult.success();
			}
			else{
				Sender.sendMessage(Text.of(TextColors.RED, Config.getInstance().getpermissioncheck()));
				return CommandResult.success();
			}
		}
		Text messagetext = TextSerializers.FORMATTING_CODE.deserialize(message);
		Text playersubmessagetext = TextSerializers.FORMATTING_CODE.deserialize(Config.getInstance().getplayer()
				.replace("{player_name}", player.getName())
				.replace("{player_displayname}", player.getDisplayNameData().displayName().toString()));
		Title send = Title.builder()
				.title(messagetext)
				.subtitle(playersubmessagetext)
				.fadeIn(Config.getInstance().getfadein())
				.stay(Config.getInstance().getstay())
				.fadeOut(Config.getInstance().getstay())
				.build();
		for (Player OnlinePlayer : Sponge.getServer().getOnlinePlayers()){
			OnlinePlayer.sendTitle(send);
		}
		return CommandResult.success();
	}
}
