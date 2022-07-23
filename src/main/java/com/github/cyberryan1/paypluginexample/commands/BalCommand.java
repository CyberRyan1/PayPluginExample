package com.github.cyberryan1.paypluginexample.commands;

import com.github.cyberryan1.cybercore.helpers.command.CyberCommand;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import com.github.cyberryan1.paypluginexample.utils.Utils;
import com.github.cyberryan1.paypluginexample.utils.yml.YMLUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BalCommand extends CyberCommand {

    public BalCommand() {
        super( "bal", "&8/&7bal &b[player]" );
        register( true );

        setAsync( true );
    }

    @Override
    public List<String> tabComplete( CommandSender sender, String args[] ) {
        if ( args.length == 0 || args[0].length() == 0 ) { return getOnlinePlayerNames(); }
        if ( args.length == 1 ) { return matchOnlinePlayers( args[0] ); }
        return List.of();
    }

    @Override
    public boolean execute( CommandSender sender, String args[] ) {

        if ( args.length == 0 ) {
            if ( sender instanceof Player ) {
                final Player player = ( Player ) sender;
                double balance = YMLUtils.getData().getDouble( "balance." + player.getUniqueId() );
                String format = Utils.getFormattedNumber( balance );
                CoreUtils.sendMsg( player, "&7Your balance is &2$&a" + format );
            }

            else {
                CoreUtils.sendMsg( sender, "&7This command can only be ran by a player" );
            }
        }

        else {
            if ( CoreUtils.isValidUsername( args[0] ) == false ) {
                sendInvalidPlayerArg( sender, args[0] );
                return true;
            }

            final OfflinePlayer target = Bukkit.getOfflinePlayer( args[0] );
            if ( target != null ) {
                double balance = YMLUtils.getData().getDouble( "balance." + target.getUniqueId() );
                String format = Utils.getFormattedNumber( balance );
                CoreUtils.sendMsg( sender, "&7The balance of &b" + target.getName() + "&7 is &2$&a" + format );
            }

            else {
                sendInvalidPlayerArg( sender, args[0] );
            }
        }

        return true;
    }
}