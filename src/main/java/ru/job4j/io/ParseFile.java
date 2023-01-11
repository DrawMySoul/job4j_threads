package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private synchronized String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getContent() {
        return content(c -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(c -> c < 0x80);
    }

}
