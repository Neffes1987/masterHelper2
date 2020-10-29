package com.masterhelper.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.masterhelper.db.contracts.journeys.JourneyContract;

public class DbHelpers extends SQLiteOpenHelper {
  private String SQLCreateTemplate ="DROP TABLE IF EXISTS ";
  public JourneyContract journeyContract;


  /**
   * Имя файла базы данных
   */
  private static final String DATABASE_NAME = "global.db";
  /**
   * Версия базы данных. При изменении схемы увеличить на единицу
   */
  private static final int DATABASE_VERSION = 45;

  SQLiteDatabase db;


  public DbHelpers(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    journeyContract = new JourneyContract(this);
  }

  /**
   * Вызывается при создании базы данных
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    this.db = db;
  }

  @Override
  public void onOpen(SQLiteDatabase db) {
    super.onOpen(db);
    if (!db.isReadOnly()) {
      // Enable foreign key constraints
      db.execSQL("PRAGMA foreign_keys=ON;");
    }
  }

  /**
   * Вызывается при обновлении схемы базы данных
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    this.db = db;
  }

  public Cursor read(String query){
    // Создадим и откроем для чтения базу данных
    SQLiteDatabase db = getReadableDatabase();
    return db.rawQuery(query, null);
  }

  public void write(String query){
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(query);
  }

}
