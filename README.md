# LangChain4j Course

Source code for the LangChain4j course by Telusko, available on Udemy and the Telusko platform.

Each lecture has its own folder containing the complete project exactly as it looks at the end of that lecture. You can open any folder independently in VS Code (or any IDE) and run it, no need to go through the folders in order.

## What you will learn

- Calling OpenAI models from Java using the official OpenAI Java SDK
- Running local models with Ollama
- Reasoning effort, instructions and structured output (JSON Schema)
- LangChain4j: ChatModel, AiServices, structured output with records
- Chain of Thought prompting
- Chat Memory with MessageWindowChatMemory
- Tools and AI Agents with @Tool
- Embeddings, cosine similarity and vector stores (InMemoryEmbeddingStore)
- RAG with EmbeddingStoreIngestor and EmbeddingStoreContentRetriever

## Folder structure

```
LangChain4jCourse/
├── LC4JCode_Le_Java_Project_with_OpenAI_in_VS_Code/         Java + OpenAI SDK setup, first API call
├── LC4JCode_Le_with_Ollama/                                 Same project pointed to a local Ollama model
├── LC4JCode_Le_Reasoning_Effort_and_Instructions/           Reasoning effort (LOW/HIGH) and instructions
├── LC4JCode_Le_Structured_Output_for_Json/                  JSON Schema structured output with the OpenAI SDK
├── LC4JCode_Le_Langchain4j_ChatModel/                       First LangChain4j code: ChatModel
├── LC4JCode_Le_Langchain4j_AiServices/                      AiServices and @SystemMessage
├── LC4JCode_Le_Langchain4j_Structured_Output/               Structured output with Java records
├── LC4JCode_Le_Chain_of_Thoughts/                           Chain of Thought with step-by-step records
├── LC4JCode_Le_Chat_Memory/                                 Chat loop with MessageWindowChatMemory
├── LC4JCode_Le_Tools_and_AI_Agents_1/                       AI Agent setup (part 1)
├── LC4JCode_Le_Tools_and_AI_Agents_2/                       @Tool methods and tool calling (part 2)
├── LC4JCode_Le_Embedding_Demo_and_Cosine_Similarity_Part_1/ Embeddings and cosine similarity
├── LC4JCode_Le_VectorStore_or_EmbeddingStore/               InMemoryEmbeddingStore and similarity search
└── LC4JCode_Le_RAG/                                         Full RAG pipeline: ingest, retrieve, answer
```

Every folder is a standalone Maven project:

```
LC4JCode_Le_<Lecture_Name>/
├── pom.xml
├── .env
├── .vscode/
│   └── launch.json
└── src/
    └── main/
        └── java/
            └── com/
                └── telusko/
                    └── App.java
```

## Prerequisites

- Java (JDK 25, as used in the course; the pom targets release 25)
- Maven
- VS Code with the Extension Pack for Java (or any Java IDE)
- An OpenAI API key, for the OpenAI lectures
- [Ollama](https://ollama.com) installed, for the local model lectures

## Setup

### 1. OpenAI API key

Open the `.env` file in the lecture folder and replace the placeholder with your own key:

```
OPENAI_API_KEY=your_api_key_here
```

The included `.vscode/launch.json` already loads this file when you run the app. You can also set `OPENAI_API_KEY` as a regular environment variable instead.

Never commit your real key to Git.

### 2. Ollama models

For the lectures that use local models, pull them once:

```
ollama run gemma4:e4b
ollama pull nomic-embed-text
```

Check what you have installed with `ollama list`.

### 3. Run

Open the lecture folder in VS Code, let Maven import finish, then run `App.java`.

Many lectures contain two blocks in `App.java`: an OpenAI block and an Ollama block, with one of them commented out. Uncomment whichever one you want to use, exactly as shown in the lecture.

## Dependencies

| Library | Version |
| --- | --- |
| com.openai : openai-java | 4.91.0 |
| dev.langchain4j : langchain4j | 1.22.0 |
| dev.langchain4j : langchain4j-open-ai | 1.22.0 |
| dev.langchain4j : langchain4j-ollama | 1.22.0 |

The first four lectures use only the OpenAI SDK. The LangChain4j dependencies are added from the ChatModel lecture onwards.

## Related

- Spring AI course source code: [SpringAIUdemy](https://github.com/navinreddy20/SpringAIUdemy)
- Telusko on YouTube: [youtube.com/@Telusko](https://www.youtube.com/@Telusko)
