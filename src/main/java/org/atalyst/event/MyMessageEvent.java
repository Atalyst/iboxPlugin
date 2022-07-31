package org.atalyst.event;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.atalyst.function.MyServerQuery;
import org.jetbrains.annotations.NotNull;
import org.atalyst.config.MyConfig;

/*
 * 消息事件处理
 */
public class MyMessageEvent {
    public static final MyMessageEvent INSTANCE = new MyMessageEvent();

    public void messageEventHandle(@NotNull GroupMessageEvent event) {
        if(messageIsConfigGroup(event)) {
                MyServerQuery myServerQuery = new MyServerQuery(event);     //服务器查询
        }
    }

    private boolean messageIsConfigGroup(@NotNull GroupMessageEvent event){
        return MyConfig.INSTANCE.getQqGroup().contains(event.getSubject().getId());
    }
}