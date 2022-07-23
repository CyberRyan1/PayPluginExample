package com.github.cyberryan1.paypluginexample.commands;

import com.github.cyberryan1.cybercore.helpers.command.CyberCommand;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import com.github.cyberryan1.paypluginexample.utils.yml.YMLUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class EarnCommand extends CyberCommand {

    public EarnCommand() {
        super( "earn", "&8/&7earn" );
        register( false );

        setDemandPlayer( true );
        setAsync( true );
    }

    @Override
    public List<String> tabComplete( CommandSender sender, String args[] ) {
        return List.of();
    }

    @Override
    public boolean execute( CommandSender sender, String args[] ) {
        final Player player = ( Player ) sender;

        int amount = ( int ) ( Math.random() * 10 + 1 );
        double current = YMLUtils.getData().getDouble( "balance." + player.getUniqueId() );
        double newBal = current + amount;

        CoreUtils.sendMsg( player, "&7You earned &2$&a" + amount + "&7!" );

        YMLUtils.getData().set( "balance." + player.getUniqueId(), newBal );
        YMLUtils.saveData();
        return true;
    }
}