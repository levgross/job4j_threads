package ru.job4j.thread;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class Wget implements Runnable {

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(Paths.get(new URL(url).getPath()).getFileName().toString())) {
            long start = System.currentTimeMillis();
            long time;
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, 1024);
                downloadData += bytesRead;
                if (downloadData > speed) {
                    time = System.currentTimeMillis() - start;
                    System.out.println(time);
                    if (time < 1000) {
                        Thread.sleep(1000 - time);
                    }
                    start = System.currentTimeMillis();
                    downloadData = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong number of arguments."
            + " Usage java -jar wget.jar URL DOWNLOAD_SPEED");
        }
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(args[0])) {
            throw new IllegalArgumentException(String.format("Url not exist %s", args[0]));
        }
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("Speed cannot be zero or negative.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
