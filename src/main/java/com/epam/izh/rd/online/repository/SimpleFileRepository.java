package com.epam.izh.rd.online.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class SimpleFileRepository implements FileRepository {
    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        long cnt = 0L;
        File dir = new File("src\\main\\resources\\" + path);
        File[] files = dir.listFiles();
        for (File f : Objects.requireNonNull(files)) {
            if (f.isDirectory()) {
                cnt += countFilesInDirectory(path + "\\" + f.getName());
            } else {
                cnt++;
            }
        }
        return cnt;
//
//        long c = 0L;
//        try {
//            c = Files.walk(Paths.get("src\\main\\resources\\" + path))
//                    .filter(Files::isRegularFile)
//                    .count();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return c;

    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        long cnt = 1L;
        File dir = new File("src\\main\\resources\\" + path);
        File[] files = dir.listFiles();
        for (File f : Objects.requireNonNull(files)) {
            if (f.isDirectory()) {
                cnt += countDirsInDirectory(path + "\\" + f.getName());
            }
        }
        return cnt;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File dir = new File("src\\main\\resources\\" + from);
        File dirC = new File("src\\main\\resources\\" + to);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.toString().endsWith(".txt")) {
                try {
                    Files.copy(dir.toPath(), dirC.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        try {
            Path pathAbsolut = Paths.get(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            path = pathAbsolut.toString() + "\\" + path;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File dir = new File(path);
        File file = new File(path + "\\" + name);

        try {
            dir.mkdir();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.exists();
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        Scanner in = null;
        String s;
        try {
            in = new Scanner(new FileReader("src\\main\\resources\\" + fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        while(Objects.requireNonNull(in).hasNext()) {
            sb.append(in.next());
        }
        in.close();
        return(sb.toString());
    }

}
