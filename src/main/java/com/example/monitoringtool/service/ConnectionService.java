package com.example.monitoringtool.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ConnectionService {

    /**
     * Opens an HTTP connection to the specified URL.
     *
     * @param urlString The URL to which the connection should be opened.
     * @return An HttpURLConnection object representing the opened connection.
     * @throws IOException If an I/O error occurs while opening the connection.
     */
    public HttpURLConnection openHttpConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    /**
     * Closes an HTTP connection.
     *
     * @param connection The HttpURLConnection object representing the connection to be closed.
     * @throws IOException If an I/O error occurs while closing the connection.
     */
    public void closeHttpConnection(HttpURLConnection connection) throws IOException {
        connection.disconnect();
    }
}

