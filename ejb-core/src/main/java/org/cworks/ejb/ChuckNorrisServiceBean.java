/**
 * Created with love by corbett.
 * User: corbett
 * Date: 3/28/14
 * Time: 10:17 PM
 */
package org.cworks.ejb;

import com.google.gson.Gson;
import org.apache.commons.lang.StringEscapeUtils;
import org.cworks.ejb.client.ChuckNorrisService;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


@Stateless(name = "ChuckNorrisService", mappedName = "ChuckNorrisService")
@Local(ChuckNorrisService.class)
public class ChuckNorrisServiceBean implements ChuckNorrisService {

	/**
	 * The root uri of the chuck norris database
	 */
	private static final String cndb = "http://api.icndb.com/jokes/random";

    private static class ChuckDoesntLikeException extends RuntimeException {
        public ChuckDoesntLikeException(Exception ex) {
            super(ex);
        }
        public ChuckDoesntLikeException(String message, Exception ex) {
            super(message, ex);
        }
    }

    @Override
    public String randomJoke() {

        URL url = null;
        try {
            url = new URL(cndb);
        } catch (MalformedURLException ex) {
            throw new ChuckDoesntLikeException("Chuck hates malformed URLs", ex);
        }
        BufferedReader reader = null;
        StringBuffer response = null;
        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            response = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException ex) {
            throw new ChuckDoesntLikeException(
                "Chuck can stop IOExceptions but computers can't", ex);
        } finally {
            close(reader);
        }

        return parseJoke(response);
    }

    @Override
    public String randomJoke(String firstName, String lastName) {
        return null;
    }

    private static void close(Closeable c) {
        if(c != null) {
            try {
                c.close();
            } catch (IOException e) { }
        }
    }

    private static String parseJoke(StringBuffer response) {
        ChuckNorrisResponse json;
        String joke = "Chuck don't play games";
        try {
            Gson gson = new Gson();
            json = gson.fromJson(response.toString(), ChuckNorrisResponse.class);
            if(json.isSuccess()) {
                joke = StringEscapeUtils.unescapeHtml(json.getJoke());
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return joke;
    }

    private static class ChuckNorrisResponse {
        String type;
        Map<String, Object> value;
        void setType(String type) {
            this.type = type;
        }
        void setValue(Map<String, Object> value) {
            this.value = value;
        }
        String getJoke() {
            String joke = (String)this.value.get("joke");
            return joke;
        }
        boolean isSuccess() {
            return "success".equalsIgnoreCase(type) ? true : false;
        }
    }
}

