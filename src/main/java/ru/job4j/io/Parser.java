package ru.job4j.io;

import java.io.File;

public class Parser {
    private final File file;

    public Parser(final File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("Wrong file name!");
        }
        this.file = file;
    }

    public File getFile() {
        return new File(String.valueOf(file));
    }
}
