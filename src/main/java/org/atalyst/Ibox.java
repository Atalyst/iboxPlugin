package org.atalyst;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.data.AutoSavePluginConfig;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;

public final class Ibox extends JavaPlugin {
    public static final Ibox INSTANCE = new Ibox();

        private Ibox() {
                        super(new JvmPluginDescriptionBuilder("org.atalyst.ibox", "0.1")
            .name("IboxServerPlugin")
            .author("Atalyst，Feu d'artifice")
                    .build());    }

    @Override
    public void onEnable() {
        AutoSavePluginConfig("test");

        getLogger().info("IboxPlugins已加载!");
        // 创建监听
        Listener listener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, a->{
            MessageChain chain=a.getMessage(); // 可获取到消息内容等, 详细查阅 `GroupMessageEvent`
            a.getSubject().sendMessage("Hello!"); // 回复消息
        });
    }
}