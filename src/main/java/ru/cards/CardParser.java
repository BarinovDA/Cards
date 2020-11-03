package main.java.ru.cards;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static main.java.ru.cards.ColorConstant.DEEP_BLACK;
import static main.java.ru.cards.ColorConstant.DEEP_GRAY;
import static main.java.ru.cards.ColorConstant.LIGHT_GRAY;
import static main.java.ru.cards.ColorConstant.MY_PINK;
import static main.java.ru.cards.ColorConstant.MY_RED;

public class CardParser {
    /**
     * координаты отбора пкселей для определения значения карты
     */
    private static final int[] ARRAY_PIXELS_COORDINATE = new int[]{4, 14, 2, 2, 2, 15, 0, 12, 7, 3, 7, 16, 14, 7, 14, 14, 21, 20, 21, 2, 14, 6, 8, 14, 12, 10, 23, 11, 15, 19, 15, 11, 1, 21, 14, 2, 0, 1, 17, 1, 2, 10, 10, 6};
    /**
     * Количество точек для определения значения карты, зависит от количества координат
     */
    private int[] arrayPixelsProbe = new int[22];
    private Map<String, String> cardValues = new HashMap<String, String>();
    private String path;

    public CardParser(String path) {
        this.path = path;
        initCardValues();
    }

    public void run() {
        try {
            File folder = new File(path); //путь к директории с картинками
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.print(file.getName() + "- ");
                    BufferedImage img = ImageIO.read(file);
                    int xCoordinateSuit = 170; //начальная координата для определения масти
                    int xCoordinateValue = 152; //начальная координата для определения значения карты
                    for (int i = 1; i < 6; i++) { //перебор все карт на столе
                        printCardValue(img.getSubimage(xCoordinateValue, 591, 30, 24)); //взятие суб картинки по координатам х,у и размером 30 на 40
                        printCardSuit(img.getSubimage(xCoordinateSuit, 635, 30, 30)); //взятие суб картинки по координатам х,у и размером 30 на 40
                        //сдвиг координаты из-за несимметричости отрисовки карт
                        switch (xCoordinateSuit) {
                            case 170:
                            case 313:
                                xCoordinateSuit += 71;
                                xCoordinateValue += 71;
                                break;
                            case 384:
                                xCoordinateSuit += 71;
                                xCoordinateValue += 72;
                                break;
                            case 241:
                                xCoordinateValue += 72;
                                xCoordinateSuit += 72;
                                break;
                        }
                    }
                }
                System.out.println("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Инициализация набора карт для сравнения
    private void initCardValues() {
        cardValues.put(Arrays.toString(new int[]{0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1}), "2");
        cardValues.put(Arrays.toString(new int[]{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1}), "3");
        cardValues.put(Arrays.toString(new int[]{1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1}), "4");
        cardValues.put(Arrays.toString(new int[]{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0}), "5");
        cardValues.put(Arrays.toString(new int[]{1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0}), "6");
        cardValues.put(Arrays.toString(new int[]{0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1}), "7");
        cardValues.put(Arrays.toString(new int[]{1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0}), "8");
        cardValues.put(Arrays.toString(new int[]{1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0}), "9");
        cardValues.put(Arrays.toString(new int[]{1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1}), "10");
        cardValues.put(Arrays.toString(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1}), "J");
        cardValues.put(Arrays.toString(new int[]{1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0}), "Q");
        cardValues.put(Arrays.toString(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1}), "K");
        cardValues.put(Arrays.toString(new int[]{1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1}), "A");
    }
    /**
     * Принимает изображение с матстью и выводит в консоль сокращенное название
     */
    private void printCardSuit(BufferedImage subImg) {
        //карта есть если цвет белый (255.255.255) или серый (120.120.120)
        if (isCard(subImg)) {
            //определение карты по различию пикселей
            if (new Color(subImg.getRGB(22, 10)).equals(DEEP_GRAY) || new Color(subImg.getRGB(22, 10)).equals(DEEP_BLACK)) {
                System.out.print("s"); //S - spade (пики)
            } else if (new Color(subImg.getRGB(15, 3)).equals(DEEP_GRAY) || new Color(subImg.getRGB(15, 3)).equals(DEEP_BLACK)) {
                System.out.print("c"); //С - club (трефы)
            }
            if (new Color(subImg.getRGB(15, 3)).equals(MY_PINK) || new Color(subImg.getRGB(15, 3)).equals(MY_RED)) {
                System.out.print("d");//D - diamond (бубны)
            }
            if (new Color(subImg.getRGB(15, 3)).equals(Color.WHITE) || new Color(subImg.getRGB(22, 10)).equals(LIGHT_GRAY)) {
                System.out.print("h"); //H -heart (червы)
            }
        }
    }
    /**
     * Принимает изображение со значением карты и выводит в консоль
     */
    private void printCardValue(BufferedImage subImg) throws IOException {
        //карта есть если цвет белый (255.255.255) или серый (120.120.120)
        if (isCard(subImg)) {
            IntStream.iterate(0, x -> x + 2)
                .limit(ARRAY_PIXELS_COORDINATE.length/2)
                .forEach(value -> {
                   if (new Color(subImg.getRGB(ARRAY_PIXELS_COORDINATE[value], ARRAY_PIXELS_COORDINATE[value + 1])).equals(Color.WHITE)
                || (new Color(subImg.getRGB(ARRAY_PIXELS_COORDINATE[value], ARRAY_PIXELS_COORDINATE[value + 1])).equals(LIGHT_GRAY))) {
                       arrayPixelsProbe[value / 2] = 0;
                   } else {
                       arrayPixelsProbe[value / 2] = 1;
                   }
                });

            for (Map.Entry<String, String> entry : cardValues.entrySet()) {
                String key = entry.getKey().replaceAll(", ", "");
                String keyWithoutReplace = entry.getKey();
                String stringPixelsProbe = Arrays.toString(arrayPixelsProbe).replaceAll(", ", "");
                int countOccurrence = 0;
                for (int i = 1; i < key.length(); i++) {
                    if (key.charAt(i) == stringPixelsProbe.charAt(i)) {
                        countOccurrence++;
                    }
                    if (countOccurrence == 21) {//отрисовка карт с погрешностью в 1 пиксел, поэтому для сравнения нужно arrayPixelsProbe.length -1
                        System.out.print(cardValues.get(keyWithoutReplace));
                        break;
                    }
                }
            }
        }
    }
    /**
     * Проверяет, является ли изображение частью карты
     */
    private boolean isCard(BufferedImage subImg) {
        // x-29 y-0 координата верхнего правого угла картинки (у карты пиксель всегда белый или серый)
        return new Color(subImg.getRGB(29, 0)).equals(Color.WHITE) || new Color(subImg.getRGB(29, 0)).equals(LIGHT_GRAY);
    }
}