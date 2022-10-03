package org.atalyst;

import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.atalyst.command.QueryCommand;
import org.atalyst.config.MyConfig;
import org.atalyst.event.MyMessageEvent;


public final class Ibox extends JavaPlugin {
    //插件信息
    public static final Ibox INSTANCE = new Ibox();

    private Ibox() {
        super(new JvmPluginDescriptionBuilder("org.atalyst.ibox", "0.2")
                .name("IboxServerPlugin")
                .author("Atalyst，Feu_d'artifice")
                .build());
    }

    @Override
    public void onEnable() {
        //加载config
        reloadPluginConfig(MyConfig.INSTANCE);
        getLogger().info("IboxPlugins已加载!");

        //群消息事件监听
        EventChannel<GroupMessageEvent> messageEvent =
                GlobalEventChannel.INSTANCE.filterIsInstance(GroupMessageEvent.class);

        //注册指令
        CommandManager.INSTANCE.registerCommand(QueryCommand.INSTANCE,false);

        //群消息事件
        messageEvent.subscribeAlways(GroupMessageEvent.class, MyMessageEvent.INSTANCE::messageEventHandle);
    }
}