package com.telusko;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class App {

    public static void main(String[] args) {
        // === OPTION A: OpenAI Cloud (gpt-6-sol) ===
        // ChatModel model = OpenAiChatModel.builder()
        //         .apiKey(System.getenv("OPENAI_API_KEY"))
        //         .modelName("gpt-6-sol")
        //         .build();

        // === OPTION B: Local Ollama (Uncomment for gemma4:e4b or gemma4:12b) ===
        ChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gemma4:12b") // or "gemma4:12b"
                .build();

        String response = model.chat("Write a poem about the beauty of nature.");
        System.out.println(response);
    }
}
