package com.epam.izh.rd.online.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        FileReader fr = null;
        try {
            fr = new FileReader("src\\main\\resources\\sensitive_data.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scan = new Scanner(Objects.requireNonNull(fr));
        String input = scan.nextLine();
        Pattern pattern = Pattern.compile("\\d{4}(\\s\\d{4}){3}");
        Matcher matcher = pattern.matcher(input);
        String [] arr = input.split(String.valueOf(pattern));
        StringBuilder x = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            x.append(arr[i]);
            x.append(input.substring(matcher.start(), matcher.end()).
                    replaceAll("(\\s\\d{4}){2}\\s", " **** **** "));
            ++i;
        }
        x.append(arr[arr.length - 1]);
        try {
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return x.toString();
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        FileReader fr = null;
        try {
            fr = new FileReader("src\\main\\resources\\sensitive_data.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scan = new Scanner(Objects.requireNonNull(fr));
        String input = scan.nextLine();
        Pattern pattern = Pattern.compile("\\$\\{balance\\}");
        Matcher matcher = pattern.matcher(input);
        String newStr = matcher.replaceAll(String.valueOf((int) balance));
        Pattern patternP = Pattern.compile("\\$\\{payment_amount\\}");
        Matcher matcherP = patternP.matcher(newStr);
        String newStrP = matcherP.replaceAll(String.valueOf((int) paymentAmount));
        return newStrP;
    }
}
