// // package professorpi;

// // import java.io.*;
// // import java.net.*;
// // import com.google.gson.*;

// // public class context {
// //     private static final String API_KEY = "AIzaSyDoAnIBzGir0VK27tLJKVT7okU3i4tpZUg";  // 🔑 put your Gemini API key here
// //     private static final String MODEL = "gemini-1.5-flash";
// //     private static final String ENDPOINT =
// //         "https://generativelanguage.googleapis.com/v1beta/models/" + MODEL + ":generateContent?key=" + API_KEY;

// //     // ---------------- Gemini Call ----------------
// //     @SuppressWarnings({ "resource", "deprecation" })
// //     public static String callLLM(String prompt) throws IOException {
// //         // Context to force JSON responses
// //         String systemContext = """
// //         You are Professor Pi, a math agent.
// //         Interpret user requests in ANY language, slang, or phrasing.
// //         You must ALWAYS respond ONLY in pure JSON with this schema:
// //         {"function":"<add|subtract|multiply|divide>","arguments":{"a":int,"b":int}}

// //         Examples:
// //         User: "bhai 4 guna 2 bta"
// //         Assistant: {"function":"multiply","arguments":{"a":4,"b":2}}

// //         User: "7 minus 3"
// //         Assistant: {"function":"subtract","arguments":{"a":7,"b":3}}
// //         Respond ONLY with pure JSON. Do not add explanations, text, or markdown.
// //         If the request is not a math operation, respond with {"function":"add","arguments":{"a":0,"b":0}}

// //         """;

// //         String finalPrompt = systemContext + "\nUser: " + prompt;

// //         // ✅ Correct request JSON format
// //         JsonObject textPart = new JsonObject();
// //         textPart.addProperty("text", finalPrompt);

// //         JsonArray parts = new JsonArray();
// //         parts.add(textPart);

// //         JsonObject content = new JsonObject();
// //         content.add("parts", parts);

// //         JsonArray contents = new JsonArray();
// //         contents.add(content);

// //         JsonObject request = new JsonObject();
// //         request.add("contents", contents);

// //         // Send POST request
// //         URL url = new URL(ENDPOINT);
// //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
// //         conn.setRequestMethod("POST");
// //         conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
// //         conn.setDoOutput(true);

// //         try (OutputStream os = conn.getOutputStream()) {
// //             os.write(request.toString().getBytes("UTF-8"));
// //         }

// //         InputStream is;
// //         if (conn.getResponseCode() >= 400) {
// //             is = conn.getErrorStream();
// //             String errorResponse = new BufferedReader(new InputStreamReader(is))
// //                     .lines().reduce("", (acc, line) -> acc + line);
// //             throw new IOException("Gemini API Error (" + conn.getResponseCode() + "): " + errorResponse);
// //         } else {
// //             is = conn.getInputStream();
// //         }

// //         // Read response
// //         String response = new BufferedReader(new InputStreamReader(is))
// //                 .lines().reduce("", (acc, line) -> acc + line);

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

// //     // ---------------- Dispatcher ----------------
// //     public static double executeFunction(String jsonResponse) {
// //         JsonObject obj = JsonParser.parseString(jsonResponse).getAsJsonObject();
// //         String function = obj.get("function").getAsString();
// //         JsonObject args = obj.get("arguments").getAsJsonObject();

// //         int a = args.get("a").getAsInt();
// //         int b = args.get("b").getAsInt();

// //         switch (function.toLowerCase()) {
// //             case "add": return add(a, b);
// //             case "subtract": return subtract(a, b);
// //             case "multiply": return multiply(a, b);
// //             case "divide": return divide(a, b);
// //             default: throw new IllegalArgumentException("Unknown function: " + function);
// //         }
// //     }

// //     // ---------------- Math Functions ----------------
// //     public static int add(int a, int b) { return a + b; }
// //     public static int subtract(int a, int b) { return a - b; }
// //     public static int multiply(int a, int b) { return a * b; }
// //     public static double divide(int a, int b) {
// //         if (b == 0) throw new ArithmeticException("Division by zero");
// //         return (double) a / b;
// //     }

// //     // ---------------- Main ----------------
// //     public static void main(String[] args) throws IOException {
// //         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
// //         System.out.print("pucho mjhse pucho => ");
// //         String question = br.readLine();

// //         if (question == null || question.trim().isEmpty()) {
// //             question = "add 2 and 3"; // default
// //         }

// //         try {
// //             String response = callLLM(question);
// //             double result = executeFunction(response);
// //             System.out.println("Professor Pi: " + result);
// //         } catch (Exception e) {
// //             System.out.println("Professor Pi (error): " + e.getMessage());
// //         }
// //     }
// // }


// package professorpi;

// import com.google.gson.*;
// import java.util.Scanner;

// public class ProfessorPi {
//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);

//         System.out.print("pucho mjhse pucho => ");
//         String query = sc.nextLine();

//         // Call LLM
//         String llmResponse = llm.askLLM(query);

//         try {
//             JsonObject obj = JsonParser.parseString(llmResponse).getAsJsonObject();
//             String function = obj.get("function").getAsString();
//             JsonObject argsObj = obj.getAsJsonObject("args");

//             switch (function) {
//                 case "sum":
//                     int a = argsObj.get("a").getAsInt();
//                     int b = argsObj.get("b").getAsInt();
//                     System.out.println("Ans: " + basicCalculation.sum(a, b));
//                     break;

//                 case "diff":
//                     int n1 = argsObj.get("n").getAsInt();
//                     int n2 = argsObj.get("n").getAsInt();
//                     System.out.println("Ans: " + basicCalculation.diff(n2 , n1));
//                     break;

//                 // case "encrypt":
//                 //     String text = argsObj.get("text").getAsString();
//                 //     System.out.println("Ans: " + basicCalculation.encrypt(text));
//                 //     break;

//                 default:
//                     System.out.println("Sorry, mujhe samajh nahi aaya 😅");
//             }

//         } catch (Exception e) {
//             System.out.println("Error parsing LLM response: " + e.getMessage());
//         }
//     }
// }
