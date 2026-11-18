package com.telusko;

import java.util.Arrays;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;

public class App {



    public static void main(String[] args) {

        //  EmbeddingModel embeddingModel = OpenAiEmbeddingModel.builder()
        //         .apiKey(System.getenv("OPENAI_API_KEY"))
        //         .modelName("text-embedding-3-small")
        //         .build();

        EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("nomic-embed-text")
                .build();

        Embedding v1 = embeddingModel.embed("What is the refund policy?").content();
        Embedding v2 = embeddingModel.embed("How can I get my money back?").content();
        Embedding v3 = embeddingModel.embed("Java garbage collection frees heap memory.").content();

         // 2. Inspect the raw vector inside Java
        System.out.println("Vector Dimensions : " + v1.dimension());
        System.out.println("First 5 floats    : " + Arrays.toString(Arrays.copyOf(v1.vector(), 5)));


        double refundVsMoney = CosineSimilarity.between(v1, v2);
        double refundVsJava  = CosineSimilarity.between(v1, v3);

        System.out.println("Refund vs Money Back: " + refundVsMoney);
        System.out.println("Refund vs Java: " + refundVsJava);

    }
}
