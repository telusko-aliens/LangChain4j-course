package com.telusko;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.Reasoning;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;


public class App {


    public static void main(String[] args) {
        //  OpenAIClient client = OpenAIOkHttpClient.fromEnv();
        //  String model = "gpt-6-sol";
         String model = "gemma4:e4b";

        OpenAIClient client = OpenAIOkHttpClient.builder()
                .baseUrl("http://localhost:11434/v1")
                .apiKey("ollama")
                .build();

        String code = """
            def display_name(user):
                return user.profile.name

            print(display_name(None))
            """;

        ResponseCreateParams params = ResponseCreateParams.builder()
            .model(model)
            .input("find the error in the following code:\n" + code)
            .instructions("you are a senior software engineer, please find the error in the code and provide a solution")
            .reasoning(Reasoning.builder().effort(ReasoningEffort.HIGH).build())
            .build();

        Response response = client.responses().create(params);

        // System.out.println(response.output().get(1).message());
        response.output().stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .forEach(outputText -> System.out.println(outputText.text()));

    }
}
