import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CryptoAPI {

    public static JSONObject getCryptoData(String cryptoSymbol) {

        try {

            //asking API for price, MC, 24hr Vol
            String apiUrl = "https://api.coingecko.com/api/v3/simple/price?ids=" + cryptoSymbol + "&vs_currencies=usd&include_market_cap=true&include_24hr_vol=true";


            //connects to website and GET data
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            //starts reading the info that comes back from the website
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );


            //Reads the website’s response line by line, and saves it into one big string.
            //loops reads a line as long as there is content to read, does not return null
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();



            // Convert String response to JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getJSONObject(cryptoSymbol); // get the specific coin info


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

