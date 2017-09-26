package com.seoul.hanokmania.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by student on 2015-09-15.
 */
public class NetworkUtility {
    /**
     * Retrieve source from url(Customized for class)
     * @param url
     * @return
     * @throws Exception
     */
    public static String getReturnString(String url) throws Exception {
        return getReturnString(getUrlConnection(url));
    }

    /**
     * getUrlConnection
     * @Note : url connection
     * @return
     * @throws Exception
     *
     *
     */
    public static URLConnection getUrlConnection(String urlString)
                                                    throws Exception {

        URL url = new URL( urlString );                 // Given url information
        URLConnection connection = url.openConnection();// Connection
        connection.setDoInput(true);// must be default
        return connection;
    }

    /**
     * getReturnString
     * @Note : Result connected
     * @param connection
     * @return
     * @throws IOException
     *
     *
     */
    public static String getReturnString(URLConnection connection)
                                                    throws IOException {

        InputStream is = null;
        try {
            is = connection.getInputStream();
        } catch (IOException ioe) {
            if (connection instanceof HttpURLConnection) {
                HttpURLConnection httpConn = (HttpURLConnection) connection;
                int statusCode = httpConn.getResponseCode();
                if (statusCode != 200) {
                    is = httpConn.getErrorStream();
                }
            }
        }

        InputStreamReader reader= new InputStreamReader(is, "UTF-8" );
        BufferedReader in = new BufferedReader(reader);

        StringBuilder builder= new StringBuilder();

        String decodedString;

        while( ( decodedString = in.readLine() ) != null ) {
            builder.append( decodedString );
        }

        in.close();

        return builder.toString();
    }


}
