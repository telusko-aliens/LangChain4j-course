package com.telusko;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

public class App {

        interface Agent {
                @SystemMessage("You are a helpful AI agent. Use your available tools whenever needed.")
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
        //         .modelName("gemma4:e4b") // or "gemma4:12b"
        //         .build();


        Agent agent = AiServices.builder(Agent.class)
                .chatModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();

        String answer = agent.chat("What is the weather in Pune right now, and what is 4582 * 9123?");
        System.out.println("\nAI Agent Final Answer: " + answer);

    }
}
