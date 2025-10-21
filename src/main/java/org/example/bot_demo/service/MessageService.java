package org.example.bot_demo.service;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MessageService {

    // 本地图片私发
    public static void localPrivate(Bot bot, PrivateMessageEvent event, String text, String imgClasspath)throws IOException {
       byte[] imgBytes = StreamUtils.copyToByteArray(new ClassPathResource(imgClasspath).getInputStream());

        String sendMsg = MsgUtils.builder()
                .at(event.getUserId())
                .text(text)
                .img(imgBytes)
                .build();
        bot.sendPrivateMsg(event.getUserId(),sendMsg,false);
    }

    // 本地图片群发
    public static void localGroup(Bot bot, GroupMessageEvent event, String text, String imgClasspath)throws IOException {
        byte[] imgBytes = StreamUtils.copyToByteArray(new ClassPathResource(imgClasspath).getInputStream());

        String sendMsg = MsgUtils.builder()
                .at(event.getUserId())
                .text(text)
                .img(imgBytes)
                .build();
        bot.sendGroupMsg(event.getGroupId(),sendMsg,false);
    }

    // url图片私发
    public static void remotePrivate(Bot bot, PrivateMessageEvent event, String text, String imgUrl)throws IOException {
        String sendMsg = MsgUtils.builder()
                .at(event.getUserId())
                .text(text)
                .img(imgUrl)
                .build();
        bot.sendPrivateMsg(event.getUserId(),sendMsg,false);
    }

    //url图片群发
    public static void remoteGroup(Bot bot, GroupMessageEvent event, String text, String imgUrl)throws IOException {
        String sendMsg = MsgUtils.builder()
                .at(event.getUserId())
                .text(text)
                .img(imgUrl)
                .build();
        bot.sendGroupMsg(event.getGroupId(),sendMsg,false);
    }

    // 扫描整个目录，随机抽取一张图片
    private static final AtomicReference<List<String>> ALL_IMGS =
            new AtomicReference<>(loadAllImgPaths());
    private static final ThreadLocalRandom R = ThreadLocalRandom.current();

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void autoRefresh() {          // 必须是非 static、Spring Bean 里的方法
        ALL_IMGS.set(loadAllImgPaths()); // 用 set 替换内容
    }

    private static List<String> loadAllImgPaths() {
        try {
            var resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:/Pic/*.{jpg,png}");
            return Arrays.stream(resources)
                    .map(r -> "/Pic/" + r.getFilename())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("扫描图片失败", e);
        }
    }

    public static void localGroupRandom(Bot bot, GroupMessageEvent event, String text) throws IOException {
        if (ALL_IMGS.get().isEmpty()) {
            bot.sendGroupMsg(event.getGroupId(), "图库为空", false);
            return;
        }
        String path = ALL_IMGS.get().get(R.nextInt(ALL_IMGS.get().size()));
        localGroup(bot, event, text, path);
    }

}
