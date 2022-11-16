package org.nai.db;

import com.toshiba.mwcloud.gs.GSException;
import com.toshiba.mwcloud.gs.RowSet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nai.model.Ratings;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataLoader {
    private static RowSet<Ratings> loadedData;
    private static final int USER_NAME = 0;
    private static final int MOVIE = 1;
    private static final int RATE = 2;

    private DataLoader() {
    }

    public static RowSet<Ratings> initData() throws GSException {
        if (loadedData == null) {
            Workbook workbook = null;
            try {
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                InputStream inputStream = classloader.getResourceAsStream("data.xlsx");
//            Path temp = Files.createTempFile("data-", ".xlsx");
//            Files.copy(Objects.requireNonNull(classloader.getResourceAsStream("data.xlsx")), temp, StandardCopyOption.REPLACE_EXISTING);
//            FileInputStream file = new FileInputStream(temp.toFile());
                workbook = new XSSFWorkbook(Objects.requireNonNull(inputStream));
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
            if (workbook == null) {
                throw new RuntimeException("File Lodaing problems occured");
            }

            Sheet sheet = workbook.getSheetAt(0);
            Map<Integer, List<String>> data = new HashMap<>();
            Set<String> moviesSet = new HashSet<>();
            int i = 0;

            for (Row row : sheet) {
                data.put(i, new ArrayList<>());
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            String cellValue = cell.getRichStringCellValue().getString().trim();
                            if (cell.getColumnIndex() == 1) {
                                Set<String> setTemp = moviesSet
                                        .stream()
                                        .map(String::toLowerCase)
                                        .collect(Collectors.toSet());
                                if (!setTemp.contains(cellValue.toLowerCase())) {
                                    moviesSet.add(cellValue);
                                }
                            }
                            data.get(i).add(cellValue);
                            break;
                        case NUMERIC:
                             cellValue = cell.getNumericCellValue() + "".trim();
                            if (cell.getColumnIndex() == 1) {
                                Set<String> setTemp = moviesSet
                                        .stream()
                                        .map(String::toLowerCase)
                                        .collect(Collectors.toSet());
                                if (!setTemp.contains(cellValue.toLowerCase())) {
                                    moviesSet.add(cellValue);
                                }
                            }
                            data.get(i).add(cellValue);
                            break;
                    }
                }
                i++;
            }
            //TODO init coll + db

            final List<String> movieList = new ArrayList<>(moviesSet);
            data.forEach((key, value) -> {
                if (key != 0) { //skip column names
                    Ratings ratings = new Ratings();
                    ratings.setUserName(value.get(USER_NAME));
                    int index = movieList
                            .stream()
                            .map(String::toLowerCase)
                            .collect(Collectors.toList())
                            .indexOf(value.get(MOVIE).toLowerCase());
                    ratings.setMovie(movieList.get(index));
                    ratings.setRating(value.get(RATE));
                    System.out.println(key + " ; " + ratings);
                    //TODO add to gridDB
                    //coll.append(ratings);
                }
            });

//TODO            Query<Ratings> query = coll.query("select *");
//            RowSet<Ratings> res = query.fetch();
//            loadedData = res;
        }
        return loadedData;
    }
}
