package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ChatController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField inputField;

    @FXML
    protected void sendMessage(ActionEvent event) {
        String message = inputField.getText();
        displayMessage("You: " + message);

        // Send message to Flask chatbot
        String response = sendMessageToFlaskChatbot(message);
        displayMessage("Chatbot: " + response);

        inputField.clear();
    }

    private String sendMessageToFlaskChatbot(String message) {
        try {
            URL url = new URL("http://localhost:5000/send_message"); // Make sure this URL matches your Flask app's URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("message", message);

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }
            reader.close();

            // Assuming the response from Flask is a JSON object with a "response" field
            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getString("response");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with Flask chatbot";
        }
    }

    private void displayMessage(String message) {
        chatArea.appendText(message + "\n");
    }
}
