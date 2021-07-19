package com.amarpreet.thread;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThroughputHttpServer {
    private static final String INPUT_FILE = "src/main/resources/throughput/war_and_peace.txt";
    public static void main(String[] args) throws IOException {
        URL res = ThroughputHttpServer.class.getClassLoader().getResource(INPUT_FILE);
        String text = Files.readString(Paths.get(INPUT_FILE).toAbsolutePath());
///       System.out.println(text.substring(0,10)); // Testing Path is fine and File reader works
        startServer(text);
    }

    private static void startServer(String text) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(9090),0);
        httpServer.createContext("/search", new WordCountHandler(text));
//        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        httpServer.setExecutor(executorService);
        httpServer.start();
        System.out.println("HttpServer is started at: " + httpServer.getAddress());
    }

    private static class WordCountHandler implements HttpHandler {
        private String text;
        public WordCountHandler(String text) {
            this.text = text;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String[] keyValue = query.split("=");
            String action = keyValue[0];
            String word = keyValue[1];
            if(!action.equalsIgnoreCase("word")){
                exchange.sendResponseHeaders(400,0);
                return;
            }
            long count = countWord(word);

            byte[] response = Long.toString(count).getBytes();
            exchange.sendResponseHeaders(200,response.length);
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response);
            outputStream.close();
        }

        private long countWord(String word) {
            long count = 0;
            int index = 0;
            while(index >= 0){
                index = text.indexOf(word, index);
                if(index >= 0) {
                    count++;
                    index++;
                }
            }
            return count;
        }
    }
}
