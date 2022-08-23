package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
       try {
           char[] chars = {'\\', '|', '/'};
           int i = 1;
           while (!Thread.currentThread().isInterrupted()) {
               System.out.print("\r loading ... " + chars[i]);
               Thread.sleep(500);
               i = i == chars.length - 1 ? 0 : i + 1;
           }
       } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
       }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }
}
