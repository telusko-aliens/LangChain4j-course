package com.telusko;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiResponsesChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

public class App {


         // 1. Give the AI "Hands" by annotating normal Java methods with @Tool
    static class SmartTools {

        @Tool("Gets the current weather temperature for a given city")
        String getWeather(String city) {
            System.out.println(">>> [JAVA TOOL EXECUTED] Fetching live weather for: " + city);
            return "The weather in " + city + " is 28°C and sunny.";
        }

        @Tool("Calculates the exact product of two large numbers")
        long multiply(long a, long b) {
            System.out.println(">>> [JAVA TOOL EXECUTED] Multiplying " + a + " * " + b);
            return a * b;
        }
    }

        interface Agent {
                @SystemMessage("You are a helpful AI agent. Use your available tools whenever needed.")
                String chat(String userMessage);
    }



           public static void main(String[] args) {
        // === OPTION A: OpenAI Cloud (gpt-6-sol) ===
        ChatModel model = OpenAiResponsesChatModel.builder()
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
                .tools(new SmartTools())
                .build();

        String answer = agent.chat("What is the weather in Pune right now, and what is 4582 * 9123?");
        System.out.println("\nAI Agent Final Answer: " + answer);

    }
}
