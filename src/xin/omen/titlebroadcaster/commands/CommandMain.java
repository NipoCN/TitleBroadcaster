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

public class CommandMain implements CommandExecutor {
	
	@Override
	public CommandResult execute (CommandSource Sender, CommandContext args) throws CommandException {
		if (Sender instanceof Player == false){
			String message = args.<String>getOne("message").get();
			Text messagetext = TextSerializers.FORMATTING_CODE.deserialize(message);
			Title send = Title.builder()
					.title(messagetext)
					.subtitle(Text.of(TextColors.RED, "- Server"))
					.fadeIn(10)
					.stay(60)
					.fadeOut(10)
					.build();
			for (Player OnlinePlayer : Sponge.getServer().getOnlinePlayers()){
				OnlinePlayer.sendTitle(send);
			}
			return CommandResult.success();
		}
		if (!Sender.hasPermission("xin.omen.titlebroadcaster.use")){
			Sender.sendMessage(Text.of(TextColors.RED, "You don't have permission set use this command"));
			return CommandResult.success();
		}
		Player player = (Player) Sender;
		String message = args.<String>getOne("message").get();
		Text messagetext = TextSerializers.FORMATTING_CODE.deserialize(message);
		Title send = Title.builder()
				.title(messagetext)
				.subtitle(Text.of(TextColors.BLUE, "- From ", player.getName()))
				.fadeIn(10)
				.stay(60)
				.fadeOut(10)
				.build();
		for (Player OnlinePlayer : Sponge.getServer().getOnlinePlayers()){
			OnlinePlayer.sendTitle(send);
		}
		return CommandResult.success();
	}
}
