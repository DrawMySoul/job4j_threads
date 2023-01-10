package ru.job4j.io;

import java.io.*;

public final class WriteContent {
    private final File file;

    public WriteContent(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
