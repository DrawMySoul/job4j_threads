package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long delta = System.currentTimeMillis() - start;
                    if (delta < 1000) {
                        Thread.sleep(1000 - delta);
                    }
                    start = System.currentTimeMillis();
                    downloadData = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void urlValidate(String url) {
        if (!url.matches("^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_+.~#?&/=]*)$")) {
            throw new IllegalArgumentException("Invalid URL");
        }
    }

    private static void speedValidate(String speed) {
        if (Integer.parseInt(speed) <= 0) {
            throw new IllegalArgumentException("Invalid speed! Value must be more than zero");
        }
    }

    private static void argsValidate(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Args are empty! Parameters: first is url, second is speed limit");
        } else if (args.length == 1) {
            throw new IllegalArgumentException("Args contains only one parameter! Parameters: first is url, second is speed limit ");
        }
        urlValidate(args[0]);
        speedValidate(args[1]);
    }

    public static void main(String[] args) throws InterruptedException {
        argsValidate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
