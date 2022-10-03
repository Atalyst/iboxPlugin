package org.atalyst.command;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.MemberCommandSenderOnMessage;
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
        if(commandSenderIsConfigGroup((MemberCommandSenderOnMessage) sender)){
            sender.sendMessage("test");
        }
    }

    private boolean commandSenderIsConfigGroup(MemberCommandSenderOnMessage sender){
        return MyConfig.INSTANCE.getQqGroup().contains(sender);
    }
}
