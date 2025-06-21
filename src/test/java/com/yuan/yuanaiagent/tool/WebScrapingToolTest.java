package com.yuan.yuanaiagent.tool;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebScrapingToolTest {

    @Test
    void scrapeWebPage() {
        WebScrapingTool tool = new WebScrapingTool();
        String url = "https://blog.csdn.net/cui_hao_nan?spm=1000.2115.3001.5343";
        String result = tool.scrapeWebPage(url);
        assertNotNull(result);
    }
}