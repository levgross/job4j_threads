package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final Parser parser;

    public ParseFile(Parser parser) {
        this.parser = parser;
    }

    public String getContent(Predicate<Integer> filter) {
        StringBuilder output = new StringBuilder("1");
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(parser.getFile()));
             BufferedOutputStream out = new BufferedOutputStream(new OutputStream() {
                 @Override
                 public void write(int b) {
                     output.append((char) b);
                 }
             })) {

            int data;
            while ((data = in.read()) != -1) {
                if (filter.test(data)) {
                    out.write(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public static void main(String[] args) {
        Parser parser = new Parser(new File("text.txt"));
        ParseFile parseFile = new ParseFile(parser);
        String text = parseFile.getContent(data -> data < 0x80);
        System.out.println(text);
    }
}
