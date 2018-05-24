import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionTest {

    private static final String REST_OF_API_CALL = "activityType=MAKE_PAYMENT&brand=testQa";
    private static final String AMP = "&";
    private static final String CUSTOMER_ID = "customerID=";
    private static final String ACTION = "action=";
    private static final String UUID = "uuid=";
    private static final String CUSTOMER_SESSION_ID = "customerSessionID=";

    public static void main(String[] args) throws Exception {

        HttpURLConnectionTest http = new HttpURLConnectionTest();
        System.out.println("Testing - Send API call request");
        String customerId = "bcqa";
        String action = "getScore";
        String uuid = "QA_AndroidSDKTP93D6LR";
        String customerSessionID = "csid_qa_1516617094.58";
        String response;
        //send valid api call
        response = http.sendApiRequest(customerId, action, uuid, customerSessionID);
        //check the score field
        checkScoreNumber(response);
        customerSessionID = "csid_qa_1516617094";
        //send not valid api call
        response = http.sendApiRequest(customerId, action, uuid, customerSessionID);
        //check the score field
        checkScoreNumber(response);
    }

    private static void checkScoreNumber(String response) {
        int score;
        try {
            JSONObject jsonObject = new JSONObject(response);
            score = jsonObject.getInt("score");
            if (score > 800){
                System.out.println("The score field is bigger than 800");
            }else{
                System.out.println("The score field is smaller than 800");

            }
        } catch (JSONException e) {
            System.out.println("Error parsing data " + e.toString());
        }
    }

    /**
     * sending API call request within provided inputs
     *
     * @param customerId        - customer ID
     * @param action            - action ID
     * @param uuid              - uuid
     * @param customerSessionID - customer Session ID
     * @throws Exception - throw exception in case of it
     */
    private String sendApiRequest(final String customerId, final String action, final String uuid, final String customerSessionID) throws Exception {

        String url = "https://api.bcqa.bc2.customers.biocatch.com/api/v6/score?";
        //build the url to send
        StringBuilder urlToSend = new StringBuilder();
        urlToSend.append(url);
        urlToSend.append(CUSTOMER_ID);
        urlToSend.append(customerId);
        urlToSend.append(AMP);
        urlToSend.append(ACTION);
        urlToSend.append(action);
        urlToSend.append(AMP);
        urlToSend.append(UUID);
        urlToSend.append(uuid);
        urlToSend.append(AMP);
        urlToSend.append(CUSTOMER_SESSION_ID);
        urlToSend.append(customerSessionID);
        urlToSend.append(AMP);
        urlToSend.append(REST_OF_API_CALL);
        System.out.println("URL to send : " + urlToSend.toString());
        URL obj = new URL(urlToSend.toString());
        HttpURLConnection con;
        //here we are going to send the request
        con = (HttpURLConnection) obj.openConnection();
        //checking the response code
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        //converting the response to BufferedReader
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        //convert the response to StringBuilder
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        //returning the response in string type
        return response.toString();
    }
}