package com.telusko;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;


public class App {


    public static void main(String[] args) {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();
        ResponseCreateParams params = ResponseCreateParams.builder()
            .input("who is telusko")
            .model("gpt-6-sol")
            .build();
        Response response = client.responses().create(params);

        System.out.println(response.output());
    }
}
