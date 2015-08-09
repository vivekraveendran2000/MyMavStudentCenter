package uta.com.studentcenter;


import android.util.Log;

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

    public static JSONObject login(String userName, String password){

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
            return  jsonObject;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }



    public static String Search(String department, String term, String courseNumber, String restriction){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/search.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "dept_name=" + department + "&course_num=" + courseNumber + "&restriction=" + restriction +"&term=" + term ;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String getApplications(String netId){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/check_application_status.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId ;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String getToDoList(String netId){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/view_todo_list.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId ;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String addToCart(String netId, String uniqueId, String term){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/add_to_cart.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId  + "&unique_code=" + uniqueId + "&term=" + term ;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }


    public static String viewCart(String netId, String term){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/view_cart.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId  + "&term=" + term ;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String removeFromCart(String netId, String term, String uniqueCode){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/remove_cart.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId  + "&term=" + term + "&unique_code=" + uniqueCode;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String enroll(String netId, String term, String uniqueCode){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/enroll_course.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId  + "&term=" + term + "&unique_code=" + uniqueCode;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String viewSchedule(String netId, String term){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/view_schedule.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId  + "&term=" + term ;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String drop(String netId, String term, String uniqueCode){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/drop_course.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId  + "&term=" + term  + "&unique_code=" + uniqueCode;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String swap(String netId, String term, String dropCourseUniqueCode, String swapCourseUniqueCode){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/swap_course.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId  + "&term=" + term + "&unique_code_drop=" + dropCourseUniqueCode + "&unique_code_add=" + swapCourseUniqueCode;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String viewHold(String netId){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/view_holds.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String getFinancialData(String netId, String term){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/view_financial.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId  + "&term=" + term;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String getPersonalInfo(String netId){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/view_personal.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public static String updatePersonalInfo(String netId, String mailingAddress, String homeAddress, String email, String contact_number){

        try {

            URL url = new URL("http://omega.uta.edu/~sxa6933/StudentCenter/update_info.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "netid=" + netId + "&mailing_address=" + mailingAddress + "&home_address=" + homeAddress + "&email=" + email + "&contact_no=" + contact_number;
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
            return  output;

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }
}
