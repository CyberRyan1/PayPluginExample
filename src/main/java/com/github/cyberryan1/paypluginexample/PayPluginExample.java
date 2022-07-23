package com.github.cyberryan1.paypluginexample;

import com.github.cyberryan1.cybercore.CyberCore;
import com.github.cyberryan1.paypluginexample.commands.BalCommand;
import com.github.cyberryan1.paypluginexample.commands.EarnCommand;
import com.github.cyberryan1.paypluginexample.commands.PayCommand;
import com.github.cyberryan1.paypluginexample.commands.SetbalCommand;
import com.github.cyberryan1.paypluginexample.utils.yml.YMLUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class PayPluginExample extends JavaPlugin {

    @Override
    public void onEnable() {
        // Initialize things
        CyberCore.setPlugin( this );

        // Update/load data files
        YMLUtils.getData().getYMLManager().initialize();
        YMLUtils.getData().sendPathNotFoundWarns( false );

        // Initialize commands
        initializeCommands();
    }

    private void initializeCommands() {
        new PayCommand();
        new EarnCommand();
        new BalCommand();
        new SetbalCommand();
    }
}