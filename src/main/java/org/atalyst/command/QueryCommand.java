package org.atalyst.command;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.message.data.MessageChain;
import org.atalyst.Ibox;
import org.atalyst.config.MyConfig;
import org.jetbrains.annotations.NotNull;

public final class QueryCommand extends JRawCommand {
    public static final QueryCommand INSTANCE = new QueryCommand();
    private QueryCommand(){
        super(Ibox.INSTANCE,"test");
        setUsage("/test");
        setDescription("test测试");
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args){
        if(commandSenderIsConsole(sender)) {                                       //指令发送者为控制台
            Ibox.INSTANCE.getLogger().info("请在群内使用该指令");
        } else {                                                                   //指令发送者为群成员
            if(commandSenderIsConfigGroup(sender)){
                sender.sendMessage("test");
            }
        }
    }

    private boolean commandSenderIsConsole(CommandSender sender){
        return (sender.getSubject() == null);
    }

    private boolean commandSenderIsConfigGroup(CommandSender sender){
        return MyConfig.INSTANCE.getQqGroup().contains(sender.getSubject().getId());
    }
}
