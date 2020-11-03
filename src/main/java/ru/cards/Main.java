package main.java.ru.cards;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Path to images is not specified");
        }
            String path = args[0];

            CardParser cardParser = new CardParser(path);
            cardParser.run();

    }
}
