// Author: Emily Flores
// Purpose: Program generates similar artists based on user input.
// Date Modified: 05/19/23

/* NOTES:
 * program which generates similar recommendations based on user input
 * similar artist, music type, song name
 * scans internet -- implementing API key
 * return the artists as a sorted list
 * API web scraping
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;

public class MusicGenerator {
    public static void main(String[] args) {
        System.out.println("Welcome to the MusicGenerator! A program which generates artist recommendations based on user input.");

        System.out.print("Enter the artist name: ");
        Scanner input = new Scanner(System.in);
        String artist = input.nextLine();

        System.out.println("The similar artists to " + artist + " include: ");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");

        try {
            // user input implemented in the Last.fm API request
            String formattedArtist = artist.replace(" ", "%20");
            String apiKey = "774abda2ee6a5f81e7c1c1a28c3c8e5a";
            String apiUrl = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist="
                    + formattedArtist + "&api_key=" + apiKey + "&format=json";

            URL url = new URL(apiUrl); // creates url object with Last.fm API url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            // checks if response was successful
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // process the response as JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject similarArtistObject = jsonResponse.getJSONObject("similarartists");
                JSONArray artistsArray = similarArtistObject.getJSONArray("artist");

                // extract and print similar artists
                for (int i = 0; i < artistsArray.length(); i++) {
                    JSONObject artistObject = artistsArray.getJSONObject(i);
                    String similarArtistName = artistObject.getString("name");
                    System.out.println(similarArtistName);
                }
                System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
            } else {
                System.out.println("API Request failed. Response Code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
