package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    private String getData(Predicate<Integer> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = in.read()) != -1) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public String getContent() {
        return getData(f -> true);
    }

    public String getContentWithoutUnicode() {
        return getData(f -> f < 0x80);
    }

    public static void main(String[] args) {
        ParseFile pf = new ParseFile(new File("text.txt"));
        SaveData sd = new SaveData(new File("destFile.txt"));
        sd.saveContent(pf.getContent());
    }
}
