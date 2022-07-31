package org.atalyst.function;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.SteamPlayer;
import com.github.koraktor.steamcondenser.steam.servers.SourceServer;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.jetbrains.annotations.NotNull;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public class MyServerQuery {
    public MyServerQuery(@NotNull GroupMessageEvent event) {
        MessageChain getChain = event.getMessage();          //  获取qq消息链
        this.sourceServerQuery(event,getChain,"s1.iboxgame.cn",27120,"oc");//  oc服务器
        this.sourceServerQuery(event,getChain,"s1.iboxgame.cn",12450,"l4d2","l8d");//  l4d2服务器
        this.svQuery(event,getChain,"!sv");
    }

    private void svQuery(GroupMessageEvent event, MessageChain getChain,String command){
        try {
            if (getChain.contentToString().contains(command)) {
                StringBuilder sb = new StringBuilder(getChain.contentToString());
                String query;
                query = String.valueOf(sb.delete(0, command.length()+1));
                String[] arr = query.split(":");
                SourceServer sourceServer = new SourceServer(arr[0], Integer.valueOf(arr[1]));
                sourceServer.initialize();
                event.getSubject().sendMessage(this.sendServerMessage(sourceServer,event));
            }
        }catch(SteamCondenserException | TimeoutException e){
            event.getSubject().sendMessage("无法获取服务器信息");
            }
    }

    private void sourceServerQuery(GroupMessageEvent event, MessageChain getChain, String address, Integer port,String command) {
        if (getChain.contentToString().equals(command)) {
            try {
                SourceServer sourceServer = new SourceServer(address, port);
                sourceServer.initialize();
                event.getSubject().sendMessage(this.sendServerMessage(sourceServer,event));
            } catch (SteamCondenserException | TimeoutException e) {
                event.getSubject().sendMessage("无法获取服务器信息");
            }
        }
    }

    private void sourceServerQuery(GroupMessageEvent event, MessageChain getChain, String address, Integer port,String command,String command2) {
        if (getChain.contentToString().equals(command) || getChain.contentToString().equals(command2)) {
            try {
                SourceServer sourceServer = new SourceServer(address, port);
                sourceServer.initialize();
                event.getSubject().sendMessage(this.sendServerMessage(sourceServer,event));
            } catch (SteamCondenserException | TimeoutException e) {
                event.getSubject().sendMessage("无法获取服务器信息");
            }
        }
    }

    private MessageChain sendServerMessage(SourceServer server,GroupMessageEvent event) {
        StringBuilder sb = new StringBuilder("\n");
        MessageChain sendChain = null;
        
        try {
            Set<Entry<String, SteamPlayer>> serverSet = server.getPlayers().entrySet();
            if (serverIsEmpty(server,event)) {
                sb.append("暂无玩家……");
            } else {
                for (Entry<String, SteamPlayer> sf : serverSet) {          //    玩家信息查询
                    if (!(sf.getValue().getName().length() == 0) && !(sf.getValue().getScore() == 0)) {
                        sb.append("玩家：");
                        sb.append(sf.getValue().getName());
                        sb.append("\t得分：");
                        sb.append(sf.getValue().getScore());
                        sb.append("\n");
                    }
                }
            }

                    sendChain = new MessageChainBuilder()          //    服务器信息查询
                    .append(new At(event.getSender().getId()))
                    .append("\n名称：")
                    .append(server.getServerInfo().get("serverName").toString())
                    .append("\n地址：")
                    .append(server.getIpAddresses().toString()).append(":").append(server.getServerInfo().get("serverPort").toString())
                    .append("\n地图：")
                    .append(server.getServerInfo().get("mapName").toString())
                    .append("\n游戏：")
                    .append(server.getServerInfo().get("gameDir").toString())
                    .append("\n标签：")
                    .append(server.getServerInfo().get("serverTags").toString())
                    .append("\n人数：")
                    .append(server.getServerInfo().get("numberOfPlayers").toString()).append("|").append(server.getServerInfo().get("maxPlayers").toString()).append(" (")
                    .append(server.getServerInfo().get("numberOfBots").toString()).append(" Bot)")
                    .append("\n密码保护：")
                    .append(server.getServerInfo().get("passwordProtected").toString())
                    .append("\nVAC状态：")
                    .append(server.getServerInfo().get("secure").toString())
                    .append("\n——————————————")
                    .append(sb)
                    .build();
        } catch (SteamCondenserException | TimeoutException e) {
            event.getSubject().sendMessage("无法获取服务器信息");
        }
        return sendChain;
    }

    private boolean serverIsEmpty(SourceServer server,GroupMessageEvent event) {
        boolean b = false;
            try {
                b = server.getPlayers().isEmpty();
            } catch (SteamCondenserException |TimeoutException e) {
                event.getSubject().sendMessage("无法获取服务器信息");
            }
        return b;
    }
}

