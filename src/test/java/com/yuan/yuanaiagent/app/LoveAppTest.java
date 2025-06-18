package com.yuan.yuanaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void doChat() {
        String charId = UUID.randomUUID().toString();
        //第一轮对话
        String message = "你好，我是于安";
        String answer = loveApp.doChat(message, charId);
        Assertions.assertNotNull(answer);
        //第二轮对话
        message = "我不希望站在上帝视角说教我的另一半(鱼安)，我该怎么做";
        answer = loveApp.doChat(message,charId);
        Assertions.assertNotNull(answer);
        //第三轮对话
        message = "我的对象叫什么？帮我回忆一下";
        answer = loveApp.doChat(message,charId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String charId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是程序员于安，我想染另一半（鱼安）更爱我，但我不知道怎么办";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, charId);
        Assertions.assertNotNull(loveReport);
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
        String answer =  loveApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithRagAdvisor() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
        String answer =  loveApp.doChatWithRagAdvisor(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithRagAdvisorPGVector() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
        String answer =  loveApp.doChatWithRagAdvisorPGVector(message, chatId);
        Assertions.assertNotNull(answer);
    }
}