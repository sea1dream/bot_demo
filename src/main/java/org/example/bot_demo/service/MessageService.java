package org.example.bot_demo.service;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import java.io.IOException;

public class MessageService {

    public static void localPrivate(Bot bot, PrivateMessageEvent event, String text, String imgClasspath)throws IOException {
       byte[] imgBytes = StreamUtils.copyToByteArray(new ClassPathResource(imgClasspath).getInputStream());

        String sendMsg = MsgUtils.builder()
                .at(event.getUserId())
                .text(text)
                .img(imgBytes)
                .build();
        bot.sendPrivateMsg(event.getUserId(),sendMsg,false);
    }

    public static void localGroup(Bot bot, GroupMessageEvent event, String text, String imgClasspath)throws IOException {
        byte[] imgBytes = StreamUtils.copyToByteArray(new ClassPathResource(imgClasspath).getInputStream());

        String sendMsg = MsgUtils.builder()
                .at(event.getUserId())
                .text(text)
                .img(imgBytes)
                .build();
        bot.sendGroupMsg(event.getGroupId(),sendMsg,false);
    }

    public static void remotePrivate(Bot bot, PrivateMessageEvent event, String text, String imgUrl)throws IOException {
        String sendMsg = MsgUtils.builder()
                .at(event.getUserId())
                .text(text)
                .img(imgUrl)
                .build();
        bot.sendPrivateMsg(event.getUserId(),sendMsg,false);
    }

    public static void remoteGroup(Bot bot, GroupMessageEvent event, String text, String imgUrl)throws IOException {
        String sendMsg = MsgUtils.builder()
                .at(event.getUserId())
                .text(text)
                .img(imgUrl)
                .build();
        bot.sendGroupMsg(event.getGroupId(),sendMsg,false);
    }

}
