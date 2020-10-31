package com.masterhelper.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.masterhelper.db.repositories.journeys.JourneyRepository;
import com.masterhelper.db.repositories.scenes.SceneRepository;

public class DbHelpers extends SQLiteOpenHelper {
  public JourneyRepository journeyRepository;
  public SceneRepository sceneRepository;

  /**
   * Имя файла базы данных
   */
  private static final String DATABASE_NAME = "global.db";
  /**
   * Версия базы данных. При изменении схемы увеличить на единицу
   */
  private static final int DATABASE_VERSION = 48;

  SQLiteDatabase db;


  public DbHelpers(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    journeyRepository = new JourneyRepository(this);
    sceneRepository = new SceneRepository(this);
    db = getWritableDatabase();
  }

  private void initTables(){
    journeyRepository.createTable();
    sceneRepository.createTable();
  }

  /**
   * Вызывается при создании базы данных
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    this.db = db;
    initTables();
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
    initTables();
  }

  public Cursor read(String query){
    return db.rawQuery(query, null);
  }

  public void write(String query){
    db.execSQL(query);
  }

}
