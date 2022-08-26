package ru.job4j.io;

import java.io.*;

public final class SaveData {
    private final File file;

    public SaveData(final File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
