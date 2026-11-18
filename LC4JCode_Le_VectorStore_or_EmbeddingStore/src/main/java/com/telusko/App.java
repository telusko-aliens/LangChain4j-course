package com.telusko;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public class App {



    public static void main(String[] args) {

         EmbeddingModel embeddingModel = OpenAiEmbeddingModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("text-embedding-3-small")
                .build();

        // EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
        //         .baseUrl("http://localhost:11434")
        //         .modelName("nomic-embed-text")
        //         .build();

         // 1. Create Vector Store (Swap with PgVectorEmbeddingStore / Qdrant in production)
        EmbeddingStore<TextSegment> vectorStore = new InMemoryEmbeddingStore<>();

         // 2. Store 3 private company facts as (Embedding, TextSegment) pairs
        saveFact(vectorStore, embeddingModel, "At Telusko, customers can request a full refund within 19 days of course purchase.");
        saveFact(vectorStore, embeddingModel, "Full-time employees receive 42 days of paid Alien Leave every year.");
        saveFact(vectorStore, embeddingModel, "Our Pune studio records courses using Canon EOS R6 Mark II cameras.");


        // 3. Search by meaning ("cash back" matches "refund" with ZERO shared keywords!)
        Embedding queryVector = embeddingModel.embed("which mic we use in our recordings?").content();

        EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
            .queryEmbedding(queryVector)
            .maxResults(1)
            .minScore(0.7)
            .build();

        EmbeddingSearchResult<TextSegment> result = vectorStore.search(searchRequest);

        for (EmbeddingMatch<TextSegment> match : result.matches()) {
            System.out.println("Match Score : " + match.score());
            System.out.println("Found Fact  : " + match.embedded().text());
        }

    }

        static void saveFact(EmbeddingStore<TextSegment> store, EmbeddingModel model, String text) {
        TextSegment segment = TextSegment.from(text);
        Embedding vector = model.embed(segment).content();
        store.add(vector, segment);
    }
}
