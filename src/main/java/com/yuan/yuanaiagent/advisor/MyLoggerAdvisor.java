
package com.yuan.yuanaiagent.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

@Slf4j
public class MyLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public int getOrder() {
        return 0;
    }

    private AdvisedRequest before(AdvisedRequest request) {
        log.info("AI Request: {}",request.userText());
        return request;
    }

    private void observeAfter(AdvisedResponse advisedResponse) {
       log.info("AI Response: {}",advisedResponse.response().getResult().getOutput().getText());
    }


    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        // 1. 处理请求（前置处理）
        advisedRequest = this.before(advisedRequest);
        // 2. 调用链中的下一个Advisor
        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
        // 3. 处理响应（后置处理）
        this.observeAfter(advisedResponse);

        return advisedResponse;
    }

    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);
        return (new MessageAggregator()).aggregateAdvisedResponse(advisedResponses, this::observeAfter);
    }
}
