package com.telusko;

import java.util.List;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

public class App {


        record Event(String name, String date, List<String> participants) {}


     interface EventExtractor {
        @SystemMessage("Extract the event information")
        Event extractEvent(String text);
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


        EventExtractor eventExtractor = AiServices.builder(EventExtractor.class)
                .chatModel(model)
                .build();

        Event event = eventExtractor.extractEvent("Navin and Kiran going for science fair on 12th Nov");
        System.out.println("Event Name: " + event.name());
        System.out.println("Event Date: " + event.date());
        System.out.println("Event Participants: " + event.participants());

    }
}
