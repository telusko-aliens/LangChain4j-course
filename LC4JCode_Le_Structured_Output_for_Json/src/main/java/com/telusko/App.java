package com.telusko;

import java.util.List;
import java.util.Map;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonValue;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseFormatTextJsonSchemaConfig;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.responses.ResponseTextConfig;


public class App {


    public static void main(String[] args) {
        //  OpenAIClient client = OpenAIOkHttpClient.fromEnv();
        //  String model = "gpt-6-sol";
        String model = "gemma4:e4b";

        OpenAIClient client = OpenAIOkHttpClient.builder()
                .baseUrl("http://localhost:11434/v1")
                .apiKey("ollama")
                .build();

        Map<String, Object> schema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "name", Map.of("type", "string"),
                        "date", Map.of("type", "string"),
                        "participants", Map.of("type", "array", "items", Map.of("type", "string"))),
                "required", List.of("name", "date", "participants"),
                "additionalProperties", false);

        ResponseCreateParams params = ResponseCreateParams.builder()
            .model(model)

            .inputOfResponse(
                        List.of(
                                ResponseInputItem.ofEasyInputMessage(
                                        EasyInputMessage.builder()
                                                .role(EasyInputMessage.Role.SYSTEM)
                                                .content("Extract the event information.")
                                                .build()),
                                ResponseInputItem.ofEasyInputMessage(
                                        EasyInputMessage.builder()
                                                .role(EasyInputMessage.Role.USER)
                                                .content("Alice and Bob are going to a science fair on Friday.")
                                                .build())))
                .text(
                        ResponseTextConfig.builder()
                                .format(
                                        ResponseFormatTextJsonSchemaConfig.builder()
                                                .name("event")
                                                .strict(true)
                                                .schema(
                                                        JsonValue.from(schema)
                                                                .convert(ResponseFormatTextJsonSchemaConfig.Schema.class))
                                                .build())
                                .build())
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
