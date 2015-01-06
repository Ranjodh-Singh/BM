package edu.rs.budgetmanager.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranjodh_singh on 12/23/2014.
 */
public class BudgetManagerDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "budget.db";
    private static final String TEXT_TYPE = " REAL";
    private static final String REAL_TYPE = " ";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_LABEL_ENTRIES =
            "CREATE TABLE " + DBMetaData.LabelEntry.TABLE_NAME + " (" +
                    DBMetaData.LabelEntry._ID + " INTEGER PRIMARY KEY," +
                    DBMetaData.LabelEntry.COLUMN_NAME_ENTRY_ID + REAL_TYPE + COMMA_SEP +
                    DBMetaData.LabelEntry.COLUMN_NAME_TITLE + TEXT_TYPE +

                    " )";
    private static final String SQL_CREATE_RECORD_ENTRIES =
            "CREATE TABLE " + DBMetaData.RecordsEntry.TABLE_NAME + " (" +
                    DBMetaData.RecordsEntry._ID + " INTEGER PRIMARY KEY," +
                    DBMetaData.RecordsEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    DBMetaData.RecordsEntry.COLUMN_NAME_AMOUNT + REAL_TYPE + COMMA_SEP +
                    DBMetaData.RecordsEntry.COLUMN_NAME_LABEL_ID + REAL_TYPE + COMMA_SEP +
                    DBMetaData.RecordsEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    DBMetaData.RecordsEntry.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
                    DBMetaData.RecordsEntry.COLUMN_NAME_DESC + TEXT_TYPE +

                    " )";
    private static final String SQL_DELETE_LABEL_ENTRIES =
            "DROP TABLE IF EXISTS " + DBMetaData.LabelEntry.TABLE_NAME;
    private static final String SQL_DELETE_RECORD_ENTRIES =
            "DROP TABLE IF EXISTS " + DBMetaData.RecordsEntry.TABLE_NAME;
    public BudgetManagerDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
 }
    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LABEL_ENTRIES);
        db.execSQL(SQL_CREATE_RECORD_ENTRIES);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_LABEL_ENTRIES);
        db.execSQL(SQL_DELETE_RECORD_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     *
     * @return get all the labels stored by the user.
     */
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  labelId FROM " + DBMetaData.RecordsEntry.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

}
