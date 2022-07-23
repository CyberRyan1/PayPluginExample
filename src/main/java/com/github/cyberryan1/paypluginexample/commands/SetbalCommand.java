package com.github.cyberryan1.paypluginexample.commands;

import com.github.cyberryan1.cybercore.helpers.command.CyberCommand;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import com.github.cyberryan1.paypluginexample.utils.Utils;
import com.github.cyberryan1.paypluginexample.utils.yml.YMLUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SetbalCommand extends CyberCommand {

    public SetbalCommand() {
        super( "setbal", "&8/&7setbal &b(player) (amount)" );
        register( true );

        setMinArgs( 2 );
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

        if ( CoreUtils.isValidUsername( args[0] ) == false ) {
            sendInvalidPlayerArg( sender, args[0] );
            return true;
        }

        final OfflinePlayer target = Bukkit.getOfflinePlayer( args[0] );
        if ( target == null ) {
            sendInvalidPlayerArg( sender, args[0] );
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble( args[1] );
        } catch ( NumberFormatException e ) {
            CoreUtils.sendMsg( sender, "&b" + args[1] + " &7is not a number!" );
            return true;
        }

        String format = Utils.getFormattedNumber( amount );
        CoreUtils.sendMsg( sender, "&7Set &b" + target.getName() + "&7's balance to &2$&a" + format );
        if ( target.isOnline() ) {
            CoreUtils.sendMsg( target.getPlayer(), "&7Your balance has been set to &2$&a" + format );
        }

        YMLUtils.getData().set( "balance." + target.getUniqueId(), amount );
        YMLUtils.saveData();

        return true;
    }

}