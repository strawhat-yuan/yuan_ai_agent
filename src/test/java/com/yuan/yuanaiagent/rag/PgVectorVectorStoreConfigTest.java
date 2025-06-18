package com.yuan.yuanaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PgVectorVectorStoreConfigTest {

    @Resource
    private VectorStore pgVectorVectorStore;

    @Test
    void pgVectorVectorStore() {
        //自己声明不同文档，并写入元信息
//        List<Document> documents = List.of(
//                new Document("Spring AI 改变了后端开发的思维方式。", Map.of("标签", "技术")),
//                new Document("生活就像一盒巧克力，你永远不知道下一颗是什么味道。"),
//                new Document("知足者常乐，善待自己，热爱生活。", Map.of("标签", "哲学")));
//        // 添加文档到向量数据库中
//        pgVectorVectorStore.add(documents);
        // 相似度查询
        List<Document> results = pgVectorVectorStore.similaritySearch(SearchRequest.builder().query("巧克力").topK(3).build());
        Assertions.assertNotNull(results);
    }
}