package edu.ap.spring.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

@Controller
@Scope("session")
public class JokeController {

    private HttpURLConnection con = null;
    public static final String REQUEST_METHOD = "POST";


    public JokeController() {
   }
       
   @RequestMapping("/joke")
   public String joke(Map<String, Object > model, @RequestParam("firstName") String firstname,
                      @RequestParam("lastName") String lastname) {
        String responseString = "";
        try {
            URL conUrl = new URL("http://api.icndb.com/jokes/random?firstName=" + firstname + "&lastName=" + lastname );
            System.out.println(conUrl);
            con = (HttpURLConnection) conUrl.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            int responsecode = con.getResponseCode();
            System.out.println(responsecode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

            JSONObject obj = new JSONObject(response.toString());
            JSONObject obj2 = (JSONObject) obj.get("value");
            System.out.println(obj2);
            responseString = obj2.get("joke").toString();

        }
        catch(IOException ex){
            System.out.println("http error");
       }


       model.put("response",responseString);


       return "joke" ;
    }

		   
   @RequestMapping("/joke_post")
   public String joke_post() {
	   return "";
   }
   
   @RequestMapping("/")
   public String root() {
	   return "redirect:/joke";
   }
}
