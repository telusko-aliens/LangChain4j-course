package com.telusko;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;


public class App {


    public static void main(String[] args) {
        // OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        OpenAIClient client = OpenAIOkHttpClient.builder()
                .baseUrl("http://localhost:11434/v1")
                .apiKey("ollama")
                .build();

        ResponseCreateParams params = ResponseCreateParams.builder()
            .input("in 1 line explain llm, which model are you?")
            .model("gemma4:e4b")
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
