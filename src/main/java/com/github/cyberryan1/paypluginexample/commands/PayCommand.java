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

public class PayCommand extends CyberCommand {

    public PayCommand() {
        super( "pay", "&8/&7pay &b(player) (amount)" );
        register( true );

        setMinArgs( 2 );
        setDemandPlayer( true );
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
        final Player player = ( Player ) sender;
        final double currentBal = YMLUtils.getData().getDouble( "balance." + player.getUniqueId() );

        if ( CoreUtils.isValidUsername( args[0] ) == false ) {
            sendInvalidPlayerArg( player, args[0] );
            return true;
        }

        final OfflinePlayer target = Bukkit.getOfflinePlayer( args[0] );
        if ( target == null ) {
            sendInvalidPlayerArg( player, args[0] );
            return true;
        }

        if ( target.hasPlayedBefore() == false ) {
            CoreUtils.sendMsg( player, "&b" + target.getName() + " &7has not joined the server, therefore they cannot be paid" );
            return true;
        }

        double paying;
        try {
            paying = Double.parseDouble( args[1] );
        } catch ( NumberFormatException e ) {
            CoreUtils.sendMsg( player, "&b" + args[1] + " &7is not a number!" );
            return true;
        }

        if ( paying < 0.01 ) {
            CoreUtils.sendMsg( player, "&7You must make a payment of over zero dollars!" );
        }

        else if ( currentBal - paying < 0 ) {
            CoreUtils.sendMsg( player, "&7Sending this much money would leave you in a negative balance, which isn't allowed" );
        }

        else {
            String format = Utils.getFormattedNumber( paying );
            CoreUtils.sendMsg( player, "&7You sent &2$&a" + format + " &7to &b" + target.getName() );
            if ( target.isOnline() ) {
                final Player targetOnline = target.getPlayer();
                CoreUtils.sendMsg( targetOnline, "&b" + player.getName() + "&7 sent you &2$&a" + format );
            }

            double newPlayerBal = currentBal - paying;
            double targetBal = YMLUtils.getData().getDouble( "balance." + target.getUniqueId() );
            double newTargetBal = targetBal + paying;
            YMLUtils.getData().set( "balance." + player.getUniqueId(), newPlayerBal );
            YMLUtils.getData().set( "balance." + target.getUniqueId(), newTargetBal );
            YMLUtils.saveData();
        }

        return true;
    }
}