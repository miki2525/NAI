package org.nai;

import com.toshiba.mwcloud.gs.GSException;
import org.nai.db.DataLoader;

public class Main {
    public static void main(String[] args) {
        try {
            DataLoader.initData();
        } catch (GSException e) {
            throw new RuntimeException("Problem occured with data initialization");
        }
    }
}