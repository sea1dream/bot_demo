package org.example.bot_demo;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;

@Component
public class TestPlugin extends BotPlugin {


    @Override
    public int onPrivateMessage(Bot bot, PrivateMessageEvent event) {
        if ("hello".equals(event.getMessage())) {

            byte[] bytes;
            try (InputStream in = new ClassPathResource("/Pic/8.jpg").getInputStream()) {
                bytes = in.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String sendMsg = MsgUtils.builder()
                    .at(event.getUserId())
                    .text("测试")
                    .img(bytes)
                    .build();
            bot.sendPrivateMsg(event.getUserId(),sendMsg,false);


        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupMessage(Bot bot, GroupMessageEvent event) {
        if ("KeepCoding".equals(event.getMessage())) {

            byte[] bytes;
            try (InputStream in = new ClassPathResource(Path).getInputStream()) {
                bytes = in.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String sendMsg = MsgUtils.builder()
                    .at(event.getUserId())
                    .img(bytes)
                    .build();
            bot.sendGroupMsg(event.getGroupId(), sendMsg, false);


        }

        return MESSAGE_IGNORE;
    }

}