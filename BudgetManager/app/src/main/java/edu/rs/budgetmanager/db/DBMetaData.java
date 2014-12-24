package edu.rs.budgetmanager.db;

import android.provider.BaseColumns;

/**
 * Created by ranjodh_singh on 12/23/2014.
 */
public final class DBMetaData {
public DBMetaData(){

}
    /* Inner class that defines the table contents */
    public static abstract class LabelEntry implements BaseColumns {
        public static final String TABLE_NAME = "labels";
        public static final String COLUMN_NAME_ENTRY_ID = "labelId";
        public static final String COLUMN_NAME_TITLE = "labelName";


    }
    public static abstract class RecordsEntry implements BaseColumns {
        public static final String TABLE_NAME = "records";
        public static final String COLUMN_NAME_ENTRY_ID = "recordId";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_LABEL_ID = "labelId";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
