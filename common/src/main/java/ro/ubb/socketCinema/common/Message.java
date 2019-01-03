package ro.ubb.socketCinema.common;

import java.io.*;

/**
 * author: liviu
 */
public class Message {
    public static final String OK = "ok";
    public static final String ERROR = "error";

    private static final String LINE_SEPARATOR = System.getProperty("line" +
            ".separator");

    private String header;
    private String body;

    public Message() {
    }

    public Message(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }


    @Override
    public String toString() {
        return "Message{" +
                "header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public void writeTo(OutputStream os) throws IOException {
        os.write((header + LINE_SEPARATOR + body + LINE_SEPARATOR).getBytes());
    }

    public void readFrom(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        header = br.readLine();
        body = br.readLine();
    }
}
