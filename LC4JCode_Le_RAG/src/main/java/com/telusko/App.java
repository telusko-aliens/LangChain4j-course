package com.telusko;

import java.util.List;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public class App {

      interface PolicyAssistant {
        @SystemMessage("Answer strictly using the provided company policy context. If unknown, say so.")
        String chat(String userMessage);
    }


    public static void main(String[] args) {


        //  ChatModel chatModel = OpenAiChatModel.builder()
        //         .apiKey(System.getenv("OPENAI_API_KEY"))
        //         .modelName("gpt-6-sol")
        //         .build();

        // EmbeddingModel embeddingModel = OpenAiEmbeddingModel.builder()
        //         .apiKey(System.getenv("OPENAI_API_KEY"))
        //         .modelName("text-embedding-3-small")
        //         .build();

        ChatModel chatModel = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gemma4:e4b") // or "gemma4:12b"
                .build();
        EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("nomic-embed-text")
                .build();


         // 1. Private company document (can also load .txt / .pdf via FileSystemDocumentLoader)
        Document companyDoc = Document.from("""
                Telusko Company Policy:
                1. Refund Policy: Customers can request a full refund within 19 days of course purchase.
                2. Leave Policy: Full-time team members receive 42 days of paid Alien Leave per year.
                3. Support Policy: WhatsApp support is strictly for enrollment queries, not doubt solving.
                """);

       // 2. PHASE A (Ingestion): Split into 300-char chunks (30 overlap) -> Embed -> Store
        EmbeddingStore<TextSegment> vectorStore = new InMemoryEmbeddingStore<>();

        EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 30))
                .embeddingModel(embeddingModel)
                .embeddingStore(vectorStore)
                .build()
                .ingest(List.of(companyDoc));


        // 3. PHASE B (Retrieval): Connect Vector Store to AiServices via ContentRetriever
        ContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(vectorStore)
                .embeddingModel(embeddingModel)
                .maxResults(2)
                .minScore(0.6)
                .build();


          PolicyAssistant assistant = AiServices.builder(PolicyAssistant.class)
                .chatModel(chatModel)
                .contentRetriever(retriever)
                .build();



        String answer = assistant.chat(
                "I bought a course 2 weeks ago. Can I get my money back? Also, can I ask Java doubts on WhatsApp?");
        System.out.println("AI: " + answer);

    }


}
