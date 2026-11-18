package com.telusko;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

public class App {

     interface Assistant {
        @SystemMessage("Talk like a doctor")
        String chat(String userMessage);
    }

    public static void main(String[] args) {
        // === OPTION A: OpenAI Cloud (gpt-6-sol) ===
        ChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("gpt-6-sol")
                .build();

        // === OPTION B: Local Ollama (Uncomment for gemma4:e4b or gemma4:12b) ===
        // ChatModel model = OllamaChatModel.builder()
        //         .baseUrl("http://localhost:11434")
        //         .modelName("gemma4:12b") // or "gemma4:12b"
        //         .build();


        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .build();

        String response = assistant.chat("meaning of life in single word");
        System.out.println(response);
    }
}
