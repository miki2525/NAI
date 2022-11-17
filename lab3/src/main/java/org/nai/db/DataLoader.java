package org.nai.db;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import org.apache.hadoop.mapreduce.ID;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DataLoader class loads data from xlsx and process it, fills the IDsHolder with IDs,
 * converts the xlsx to csv using IDs assigned to userNames and movies
 * @author Mikołaj Kalata
 * @author Adam Lichy
 */
public class DataLoader {

    private static Map<Integer, List<String>> data;
    private static final int USER_NAME = 0;
    private static final int MOVIE = 1;
    private static final int RATE = 2;

    private DataLoader() {
    }

    /**
     * loads data from xlsx and convert it to csv
     * @return csv File
     */
    public static File initData() {
        loadData();
        return createCSVFile();
    }

    /**
     * loads data from xlsx, fills IDsHolder
     */
    public static void loadData() {
        Workbook workbook = null;
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream("data.xlsx");
            workbook = new XSSFWorkbook(Objects.requireNonNull(inputStream));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        if (workbook == null) {
            throw new RuntimeException("File Lodaing problems occured");
        }

        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, List<String>> dataMap = new LinkedHashMap<>();
        int i = 0;

        for (Row row : sheet) {
            if (row.getCell(0).getStringCellValue().startsWith("#")) {
                continue; //skip column names
            }
            dataMap.put(i, new ArrayList<>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        String cellValue = cell.getRichStringCellValue().getString().trim();
                        assignID(cell, cellValue);
                        dataMap.get(i).add(cellValue);
                        break;
                    case NUMERIC:
                        cellValue = cell.getNumericCellValue() + "".trim();
                        assignID(cell, cellValue);
                        dataMap.get(i).add(cellValue);
                        break;
                }
            }
            i++;
        }
        data = dataMap;
    }

    private static void assignID(Cell cell, String cellValue) {
        Set<String> setTemp;
        if (cell.getColumnIndex() == USER_NAME) {
            setTemp = IDsHolder.userMap
                    .keySet()
                    .stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
            if (!setTemp.contains(cellValue.toLowerCase())) {
                IDsHolder.assignUserId(cellValue);
            }
        } else if (cell.getColumnIndex() == MOVIE) {
            setTemp = IDsHolder.movieMap
                    .keySet()
                    .stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
            if (!setTemp.contains(cellValue.toLowerCase())) {
                IDsHolder.assignMovieId(cellValue);
            }
        }
    }

    private static File createCSVFile() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File csvFile = new File(Objects.requireNonNull(classloader.getClass().getResource("/"))
                .getPath().replace("%20", " "),
                "data.csv");

        try (FileWriter outputfile = new FileWriter(csvFile);
             CSVWriter writer = new CSVWriter(outputfile, ICSVWriter.DEFAULT_SEPARATOR,
                     '\0', ICSVWriter.NO_ESCAPE_CHARACTER, ICSVWriter.DEFAULT_LINE_END)) {

            data.forEach((key, value) -> {
                String userName = value.get(USER_NAME).toLowerCase();
                String userId = IDsHolder.userMap.get(userName) + "";
                String movie = value.get(MOVIE).toLowerCase();
                String movieId = IDsHolder.movieMap.get(movie) + "";
                String rate = value.get(RATE);
                String[] line = {userId, movieId, rate};
                writer.writeNext(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvFile;
    }
}
