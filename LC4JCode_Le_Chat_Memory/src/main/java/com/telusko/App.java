package com.telusko;

import java.util.Scanner;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

public class App {


        interface Assistant {
                String chat(String userMessage);
        }

    public static void main(String[] args) {
        // === OPTION A: OpenAI Cloud (gpt-6-sol) ===
        // ChatModel model = OpenAiChatModel.builder()
        //         .apiKey(System.getenv("OPENAI_API_KEY"))
        //         .modelName("gpt-6-sol")
        //         .build();

        // === OPTION B: Local Ollama (Uncomment for gemma4:e4b or gemma4:12b) ===
        ChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gemma4:e4b") // or "gemma4:12b"
                .build();


        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Chat started! Type 'exit' to stop.");

        while (true) {
            System.out.print("\nYou: ");
            String userInput = scanner.nextLine();
            if ("exit".equalsIgnoreCase(userInput)) break;

            // Look how clean the loop is now — no manual List management!
            String reply = assistant.chat(userInput);
            System.out.println("AI: " + reply);
        }

    }
}
