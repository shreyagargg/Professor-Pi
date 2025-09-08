// // // package professorpi;

// // // import java.io.*;
// // // import java.net.HttpURLConnection;
// // // import java.net.URL;
// // // import java.util.Scanner;
// // // import com.google.gson.*;

// // // public class llm {

// // //     // Replace with your actual API key
// // //     private static final String API_KEY = "AIzaSyDoAnIBzGir0VK27tLJKVT7okU3i4tpZUg";

// // //     public static void main(String[] args) throws Exception {
// // //         Scanner scanner = new Scanner(System.in);

// // //         System.out.println("Ask me anything => ");
// // //         String query = scanner.nextLine();
// // //         scanner.close();

// // //         // Send query to LLM
// // //         String response = callLLM(query);

// // //         // Parse LLM response
// // //         JsonObject json = JsonParser.parseString(response).getAsJsonObject();
// // //         JsonObject choice = json.getAsJsonArray("choices").get(0).getAsJsonObject();
// // //         JsonObject message = choice.getAsJsonObject("message");

// // //         if (message.has("tool_calls")) {
// // //             JsonObject toolCall = message.getAsJsonArray("tool_calls").get(0).getAsJsonObject();
// // //             String functionName = toolCall.getAsJsonObject("function").get("name").getAsString();
// // //             JsonObject argsObj = JsonParser.parseString(
// // //                     toolCall.getAsJsonObject("function").get("arguments").getAsString()
// // //             ).getAsJsonObject();

// // //             int a = argsObj.get("a").getAsInt();
// // //             int b = argsObj.get("b").getAsInt();

// // //             // Call existing methods from basicCalculation class
// // //             int result = 0;
// // //             switch (functionName) {
// // //                 case "sum":
// // //                     result = basicCalculation.sum(a, b);
// // //                     break;
// // //                 case "diff":
// // //                     result = basicCalculation.diff(a, b);
// // //                     break;
// // //                 case "prod":
// // //                     result = basicCalculation.prod(a, b);
// // //                     break;
// // //                 case "div":
// // //                     result = basicCalculation.div(a, b);
// // //                     break;
// // //                 default:
// // //                     System.out.println("Unknown function: " + functionName);
// // //                     return;
// // //             }

// // //             System.out.println("Professor Pi says: " + result);
// // //         } else {
// // //             System.out.println("No tool call returned. LLM said: " + message.toString());
// // //         }
// // //     }

// // //     // Call OpenAI API
// // //     private static String callLLM(String query) throws Exception {
// // //         URL url = new URL("https://api.openai.com/v1/chat/completions");
// // //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
// // //         conn.setRequestMethod("POST");
// // //         conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
// // //         conn.setRequestProperty("Content-Type", "application/json");
// // //         conn.setDoOutput(true);

// // //         String body = """
// // //         {
// // //           "model": "gpt-4.1-mini",
// // //           "messages": [
// // //             {"role": "system", "content": "You are Professor Pi, a math assistant who must always call functions."},
// // //             {"role": "user", "content": "%s"}
// // //           ],
// // //           "tools": [
// // //             {
// // //               "type": "function",
// // //               "function": {
// // //                 "name": "sum",
// // //                 "description": "Adds two integers",
// // //                 "parameters": {
// // //                   "type": "object",
// // //                   "properties": {
// // //                     "a": {"type": "integer"},
// // //                     "b": {"type": "integer"}
// // //                   },
// // //                   "required": ["a","b"]
// // //                 }
// // //               }
// // //             },
// // //             {
// // //               "type": "function",
// // //               "function": {
// // //                 "name": "diff",
// // //                 "description": "Subtracts two integers",
// // //                 "parameters": {
// // //                   "type": "object",
// // //                   "properties": {
// // //                     "a": {"type": "integer"},
// // //                     "b": {"type": "integer"}
// // //                   },
// // //                   "required": ["a","b"]
// // //                 }
// // //               }
// // //             },
// // //             {
// // //               "type": "function",
// // //               "function": {
// // //                 "name": "prod",
// // //                 "description": "Multiplies two integers",
// // //                 "parameters": {
// // //                   "type": "object",
// // //                   "properties": {
// // //                     "a": {"type": "integer"},
// // //                     "b": {"type": "integer"}
// // //                   },
// // //                   "required": ["a","b"]
// // //                 }
// // //               }
// // //             },
// // //             {
// // //               "type": "function",
// // //               "function": {
// // //                 "name": "div",
// // //                 "description": "Divides two integers",
// // //                 "parameters": {
// // //                   "type": "object",
// // //                   "properties": {
// // //                     "a": {"type": "integer"},
// // //                     "b": {"type": "integer"}
// // //                   },
// // //                   "required": ["a","b"]
// // //                 }
// // //               }
// // //             }
// // //           ],
// // //           "tool_choice": "auto"
// // //         }
// // //         """.formatted(query);

// // //         try (OutputStream os = conn.getOutputStream()) {
// // //             os.write(body.getBytes());
// // //         }

// // //         BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
// // //         StringBuilder response = new StringBuilder();
// // //         String line;
// // //         while ((line = in.readLine()) != null) {
// // //             response.append(line);
// // //         }
// // //         in.close();

// // //         return response.toString();
// // //     }
// // // }

// // package professorpi;

// // import java.io.*;
// // import java.net.*;
// // import com.google.gson.*;

// // public class llm {
// //     private static final String API_KEY = "AIzaSyDoAnIBzGir0VK27tLJKVT7okU3i4tpZUg";  // <-- put your key here
// //     private static final String MODEL = "gemini-1.5-flash";
// //     private static final String ENDPOINT =
// //         "https://generativelanguage.googleapis.com/v1beta/models/" + MODEL + ":generateContent?key=" + API_KEY;

    

// //     @SuppressWarnings({ "resource", "deprecation" })
// //     public static String callLLM(String prompt) throws IOException {
// //         // ✅ Correct request JSON format
// //         JsonObject textPart = new JsonObject();
// //         textPart.addProperty("text", prompt);

// //         JsonArray parts = new JsonArray();
// //         parts.add(textPart);

// //         JsonObject content = new JsonObject();
// //         content.add("parts", parts);

// //         JsonArray contents = new JsonArray();
// //         contents.add(content);

// //         JsonObject request = new JsonObject();
// //         request.add("contents", contents);

// //         // Debug print (to verify JSON)
// //         System.out.println("Sending JSON: " + request.toString());

// //         // Send POST request
// //         URL url = new URL(ENDPOINT);
// //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
// //         conn.setRequestMethod("POST");
// //         conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
// //         conn.setDoOutput(true);

// //         try (OutputStream os = conn.getOutputStream()) {
// //             os.write(request.toString().getBytes("UTF-8"));
// //         }

// //         // ✅ Handle errors
// //         InputStream is;
// //         if (conn.getResponseCode() >= 400) {
// //             is = conn.getErrorStream();
// //             String errorResponse = new BufferedReader(new InputStreamReader(is))
// //                     .lines()
// //                     .reduce("", (acc, line) -> acc + line);
// //             throw new IOException("Gemini API Error (" + conn.getResponseCode() + "): " + errorResponse);
// //         } else {
// //             is = conn.getInputStream();
// //         }

// //         // Read response
// //         String response = new BufferedReader(new InputStreamReader(is))
// //                 .lines()
// //                 .reduce("", (acc, line) -> acc + line);

// //         JsonObject json = JsonParser.parseString(response).getAsJsonObject();

// //         String output = json
// //                 .getAsJsonArray("candidates")
// //                 .get(0).getAsJsonObject()
// //                 .getAsJsonObject("content")
// //                 .getAsJsonArray("parts")
// //                 .get(0).getAsJsonObject()
// //                 .get("text").getAsString();

// //         return output;
// //     }

// //     public static void main(String[] args) throws IOException {
// //         System.out.print("Ask me anything => ");
// //         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
// //         String question = br.readLine();

// //         if (question == null || question.trim().isEmpty()) {
// //             question = "Hello Gemini!";  // default prompt if user enters nothing
// //         }

// //         String answer = callLLM(question);
// //         System.out.println("Professor Pi: " + answer);
// //     }
// // }

// package professorpi;

// import com.google.gson.*;

// public class llm {

//     // Mock response for demo — replace with actual Gemini call
//     public static String askLLM(String query) {
//         if (query.toLowerCase().contains("sum")) {
//             return "{ \"function\": \"sum\", \"args\": {\"a\": 7, \"b\": 5} }";
//         } else if (query.toLowerCase().contains("prime")) {
//             return "{ \"function\": \"isPrime\", \"args\": {\"n\": 11} }";
//         } else if (query.toLowerCase().contains("encrypt")) {
//             return "{ \"function\": \"encrypt\", \"args\": {\"text\": \"hello\"} }";
//         }
//         return "{ \"function\": \"unknown\", \"args\": {} }";
//     }
// }
