package professorpi;

import java.io.IOException;
import java.util.Scanner;

import com.google.gson.*;
import okhttp3.*;

public class trial {

    private static final String API_KEY = ""; // <-- put your API key
    private static final String MODEL = "gemini-1.5-flash";
    private static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/"
                                           + MODEL + ":generateContent?key=";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type 'exit' to quit.");
        while (true) {
            System.out.print("Ask me anything => ");
            String query = scanner.nextLine();
            if (query.equalsIgnoreCase("exit")) break;

            try {
                String response = askGemini(query);
                handleFunctionCall(response);
            } catch (IOException e) {
                System.out.println("Network/API error: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Bye! 👋");
    }

    private static String askGemini(String query) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String requestBodyJson =
            "{\n" +
            "  \"contents\": [\n" +
            "    {\n" +
            "      \"role\": \"user\",\n" +
            "      \"parts\": [\n" +
            "        { \"text\": \"You are a function router. ONLY call sum, diff, prod, or div if the user asks a math question with two numbers. If the query is unrelated (like rainbow colors), call default_fallback instead.\" }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"role\": \"user\",\n" +
            "      \"parts\": [\n" +
            "        { \"text\": \"" + query + "\" }\n" +
            "      ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"tools\": [{\n" +
            "    \"function_declarations\": [\n" +
            "      { \"name\": \"sum\", \"description\": \"add two numbers\", \"parameters\": {\"type\": \"object\", \"properties\": {\"a\": {\"type\": \"integer\"}, \"b\": {\"type\": \"integer\"}}, \"required\": [\"a\",\"b\"] } },\n" +
            "      { \"name\": \"diff\", \"description\": \"subtract b from a\", \"parameters\": {\"type\": \"object\", \"properties\": {\"a\": {\"type\": \"integer\"}, \"b\": {\"type\": \"integer\"}}, \"required\": [\"a\",\"b\"] } },\n" +
            "      { \"name\": \"prod\", \"description\": \"multiply two numbers\", \"parameters\": {\"type\": \"object\", \"properties\": {\"a\": {\"type\": \"integer\"}, \"b\": {\"type\": \"integer\"}}, \"required\": [\"a\",\"b\"] } },\n" +
            "      { \"name\": \"div\", \"description\": \"divide a by b\", \"parameters\": {\"type\": \"object\", \"properties\": {\"a\": {\"type\": \"integer\"}, \"b\": {\"type\": \"integer\"}}, \"required\": [\"a\",\"b\"] } },\n" +
            "      { \"name\": \"default_fallback\", \"description\": \"return idk when no math operation applies\", \"parameters\": { \"type\": \"object\", \"properties\": {} } }\n" +
            "    ]\n" +
            "  }],\n" +
            "  \"tool_config\": {\n" +
            "    \"function_calling_config\": {\n" +
            "      \"mode\": \"ANY\"\n" +
            "    }\n" +
            "  }\n" +
            "}";

        Request request = new Request.Builder()
                .url(BASE_URL + API_KEY)
                .post(RequestBody.create(requestBodyJson, MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String body = response.body() != null ? response.body().string() : "";
            // System.out.println("DEBUG: Gemini raw response = " + body);
            return body;
        } catch (IOException e) {
            System.out.println("Gemini is tired rn 😴, I’ll just say: idk bro");
            return "{ \"error\": \"network_error\" }";
        }
    }

    private static void handleFunctionCall(String json) {
        try {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

            if (!obj.has("candidates") || obj.getAsJsonArray("candidates").size() == 0) {
                System.out.println("No response from Gemini");
                return;
            }

            JsonObject candidate = obj.getAsJsonArray("candidates").get(0).getAsJsonObject();
            JsonObject content = candidate.getAsJsonObject("content");

            if (!content.has("parts") || content.getAsJsonArray("parts").size() == 0) {
                System.out.println("Empty response");
                return;
            }

            JsonArray parts = content.getAsJsonArray("parts");

            for (JsonElement partElem : parts) {
                JsonObject part = partElem.getAsJsonObject();

                if (part.has("functionCall")) {
                    JsonObject functionCall = part.getAsJsonObject("functionCall");
                    String name = functionCall.get("name").getAsString();
                    JsonObject args = functionCall.getAsJsonObject("args");

                    switch (name) {
                        case "sum": {
                            int a = args.get("a").getAsInt();
                            int b = args.get("b").getAsInt();
                            System.out.println("Result of sum(" + a + ", " + b + ") = " + sum(a, b));
                            break;
                        }
                        case "diff": {
                            int a = args.get("a").getAsInt();
                            int b = args.get("b").getAsInt();
                            System.out.println("Result of diff(" + a + ", " + b + ") = " + diff(a, b));
                            break;
                        }
                        case "prod": {
                            int a = args.get("a").getAsInt();
                            int b = args.get("b").getAsInt();
                            System.out.println("Result of prod(" + a + ", " + b + ") = " + prod(a, b));
                            break;
                        }
                        case "div": {
                            int a = args.get("a").getAsInt();
                            int b = args.get("b").getAsInt();
                            if (b == 0) {
                                System.out.println("Cannot divide by zero");
                            } else {
                                System.out.println("Result of div(" + a + ", " + b + ") = " + div(a, b));
                            }
                            break;
                        }
                        case "default_fallback": {
                            System.out.println(def());
                            break;
                        }
                        default:
                            System.out.println("Unknown function: " + name);
                    }

                } else if (part.has("text")) {
                    System.out.println(part.get("text").getAsString());
                } else {
                    System.out.println("No actionable content in this part");
                }
            }

        } catch (Exception e) {
            System.out.println("Error parsing function call: " + e.getMessage());
        }
    }

    // Math functions
    public static int sum(int a, int b) { return a + b; }
    public static int diff(int a, int b) { return a - b; }
    public static int prod(int a, int b) { return a * b; }
    public static int div(int a, int b) { return a / b; }
    public static String def() { return "Sorry, I don’t know that."; }
}
