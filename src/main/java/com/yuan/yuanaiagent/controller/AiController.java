package com.yuan.yuanaiagent.controller;

import com.yuan.yuanaiagent.agent.YuAnManus;
import com.yuan.yuanaiagent.app.LoveApp;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private LoveApp loveApp;

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;

    @GetMapping("/love_app/chat/sync")
    public String doChatWithloveAppSync(String message, String chatId){
        return loveApp.doChat(message,chatId);
    }

    /**
     * 返回Flux 响应式؜对象，并且添加 SSE 对应的 MediaType：
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithLoveAppSSE(String message, String chatId) {
        return loveApp.doChatByStream(message, chatId);
    }

    /**
     * 使用 SSEEmiter，؜通过 send 方法持续向 SseEmitter 发送消息
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/love_app/chat/sse/emitter")
    public SseEmitter doChatWithLoveAppSSEEmitter(String message, String chatId) {
        // 3 分钟超时
        SseEmitter emitter = new SseEmitter(180000L);
        //获取 Flux 数据流并直接订阅
        loveApp.doChatByStream(message, chatId)
                .subscribe(
                        //处理每条消息
                        chunk->{
                            try {
                                emitter.send(chunk);
                            } catch (IOException e) {
                                emitter.completeWithError(e);
                            }
                        },
                        //处理错误
                        emitter::completeWithError,
                        //处理完成
                        emitter::complete
                );
        //返回 emitter
        return emitter;

    }

    /**
     * 流式调用 Manus 超级智能体
     *
     * @param message
     * @return
     */
    @GetMapping("/manus/chat")
    public SseEmitter doChatWithManus(String message) {
        YuAnManus yuAnManus = new YuAnManus(allTools, dashscopeChatModel);
        return yuAnManus.run(message);
    }


}
