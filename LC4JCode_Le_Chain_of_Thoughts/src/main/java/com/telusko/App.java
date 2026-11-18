package com.telusko;

import java.util.List;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

public class App {


        record Step(String explanation, String output) {}

        // 2. Put List BEFORE finalAnswer so the LLM reasons step-by-step first!
        record MathSolution(List<Step> steps, String finalAnswer) {}

      interface MathTutor {
        @SystemMessage("You are a helpful math tutor")
        MathSolution solve(String equation);
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


        MathTutor mathTutor = AiServices.builder(MathTutor.class)
                .chatModel(model)
                .build();

        MathSolution solution = mathTutor.solve("How can I solve 8x + 7 = -23?");


        System.out.println("Steps:");
        for (Step step : solution.steps()) {
            System.out.println("Explanation: " + step.explanation());
            System.out.println("Output: " + step.output());
            System.out.println();
        }

        System.out.println("Final Answer: " + solution.finalAnswer());

    }
}
