package org.nai.model;

import com.toshiba.mwcloud.gs.RowKey;
import lombok.Data;

@Data
public class Ratings {
    @RowKey
    String userName;
    String movie;
    String rating;
}
