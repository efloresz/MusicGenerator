/*
 * program which generates similar reccomendations based on user input
 * similar artist, music type, song name
 * scans internet -- implementing API key
 * return the artists as a sorted list
 * API web scraping
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MusicGenerator {
    public static void main(String[] args){
        System.out.println("Welcome to the MusicGenerator! A program which generates artist reccomenations based on user input.");

        System.out.print("Enter the artist name: ");
        Scanner input = new Scanner(System.in);
        String artist = input.nextLine();
        System.out.println("The similar artists to " + artist + " include: ");

        try{
            // user input implemented in the Last.fm API request
            String formattedArtist = artist.replace(" " , "%20");
            String apiKey = "774abda2ee6a5f81e7c1c1a28c3c8e5a";
            String apiUrl = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist="
                    + formattedArtist + "&api_keys" + apiKey + "&format = json";

            URL url = new URL(apiUrl); // creates url object with Last.fm API url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // open a connection to url
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            // checks if response was successful
            if(responseCode == HttpURLConnection_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while((line = reader.readLine()) != null){
                    response.append(line);
                }
                reader.close();

                // process the response as JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject similarArtistObject = jsonResponse.getResponseObject("similarartists");
                JSONArray aristArray = similarArtistObject.getJSONArray("artist");

                // extract and print similar artists
                for(int i = 0; i < artistsArray.length(); i++){
                    JSONObject artistObject = aristArray.getJSONObject(i);
                    String similarArtistName = artistObject.getSTring("name");
                    System.out.println(similarArtistName);

                }
            } else{
                System.out.println("API Request failed. Response Code: " + responseCode);
            }
            connection.disconnect(); // disconnects the connections
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}





