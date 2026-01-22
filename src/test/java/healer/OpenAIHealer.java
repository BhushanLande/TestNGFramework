package healer;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;

public class OpenAIHealer {
    private final String apiKey;

    public OpenAIHealer(String apiKey) {
        this.apiKey = apiKey;
    }
    public By heal(String brokenXpath, String pageSource) {

        // Escape HTML for JSON
        String jsonSafeHTML = pageSource
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r\n", "\\n")
                .replace("\n", "\\n");

        // Build JSON payload
        JSONObject payload = new JSONObject();
        payload.put("model", "gpt-5-nano");
        payload.put("input",
                "You are a senior Selenium automation expert.\n\n" +
                        "Broken XPath:\n1" + brokenXpath + "\n\n" +
                        "HTML Page Source:\n" + jsonSafeHTML + "\n\n" +
                        "Rules:\n- Identify the intended element\n" +
                        "- Generate the most stable XPath\n" +
                        "- Return ONLY a valid XPath\n" +
                        "- No explanation");
        payload.put("store", true);

        // Send POST request
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .body(payload.toString())
                .post("https://api.openai.com/v1/responses")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Extract corrected XPath
        String correctedXPath = extractCorrectedXPath(response.asString());
        System.out.println("Corrected XPath: " + correctedXPath);

        // Return as Selenium By
        return By.xpath(correctedXPath);
    }

    /**
     * Parses GPT response JSON to get the corrected XPath
     */
    private static String extractCorrectedXPath(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);
        String correctedXpath = "";

        if (json.has("output")) {
            JSONArray outputArray = json.getJSONArray("output");

            for (int i = 0; i < outputArray.length(); i++) {
                JSONObject item = outputArray.getJSONObject(i);

                if (item.has("type") && item.getString("type").equals("message")
                        && item.has("content")) {

                    JSONArray contentArray = item.getJSONArray("content");

                    for (int j = 0; j < contentArray.length(); j++) {
                        JSONObject contentItem = contentArray.getJSONObject(j);

                        if (contentItem.has("type") && contentItem.getString("type").equals("output_text")
                                && contentItem.has("text")) {
                            correctedXpath = contentItem.getString("text").trim();
                        }
                    }
                }
            }
        }

        return correctedXpath;
    }
}
