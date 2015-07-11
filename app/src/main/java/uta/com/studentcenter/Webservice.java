package uta.com.studentcenter;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

/**
 * Created by vivekraveendran on 7/10/2015.
 */
public class Webservice {

    public static int login(String userName, String password){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/login.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + userName + "&password=" + password;
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            urlConnection.setRequestProperty("Content-Language", "en-US");

            urlConnection.setUseCaches (false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream ( urlConnection.getOutputStream ());
            wr.writeBytes(urlParameters);

            wr.flush ();
            wr.close ();

            //Get Response
            InputStream is = urlConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                //response.append('\r');
            }
            rd.close();
            String output = response.toString();
            JSONObject jsonObject = new JSONObject(output);
            System.out.println(output);

        }catch(Exception e){

            e.printStackTrace();
        }
        return 1;
    }



    public static int Search(String department, String term, String courseNumber, String restriction){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/search.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "dept_name=" + "CSE" + "&course_num=" + "5000" + "&restriction=" + ">" +"&term=" + "Fall 2015" ;
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            urlConnection.setRequestProperty("Content-Language", "en-US");

            urlConnection.setUseCaches (false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream ( urlConnection.getOutputStream ());
            wr.writeBytes(urlParameters);

            wr.flush ();
            wr.close ();

            //Get Response
            InputStream is = urlConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                //response.append('\r');
            }
            rd.close();
            String output = response.toString();
            JSONObject jsonObject = new JSONObject(output);
            System.out.println(output);

        }catch(Exception e){

            e.printStackTrace();
        }
        return 1;
    }
}
