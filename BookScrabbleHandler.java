package test;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler {
    private DictionaryManager dictionaryManager;
    private PrintWriter out;
    private Scanner in;

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        out = new PrintWriter(outToClient);
        in = new Scanner(inFromClient);
        dictionaryManager = DictionaryManager.get();

        String inputFromClient = in.nextLine();

        boolean isWordExists;
        if (inputFromClient.startsWith("Q,")) {
            String[] words = inputFromClient.substring(2).split(",");
            isWordExists = dictionaryManager.query(words);
        } else {
            String[] words = inputFromClient.substring(2).split(",");
            isWordExists = dictionaryManager.challenge(words);
        }

        out.println(isWordExists ? "true" : "false");
        out.flush();
    }

    @Override
    public void close() {
        out.close();
        in.close();
    }
}
