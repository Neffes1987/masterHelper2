package com.masterhelper.global.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelpers extends SQLiteOpenHelper {
  //public JourneyRepository journeyRepository;
  //public GoalRepository goalRepository;
  //public LocationRepository locationRepository;
  //public MediaRepository mediaRepository;
  //public PlotLineRepository plotLineRepository;
  //public NPCRepository npcRepository;

  /**
   * Имя файла базы данных
   */
  private static final String DATABASE_NAME = "global.db";
  /**
   * Версия базы данных. При изменении схемы увеличить на единицу
   */
  private static final int DATABASE_VERSION = 70;

  SQLiteDatabase db;


  public DbHelpers(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    // journeyRepository = new JourneyRepository(this);
    //goalRepository = new GoalRepository(this);
    //locationRepository = new LocationRepository(this);
    //mediaRepository = new MediaRepository(this);
    //plotLineRepository = new PlotLineRepository(this);
    //npcRepository = new NPCRepository(this);
    db = getWritableDatabase();
  }

  private void initTables() {
    //journeyRepository.createTable();
    //goalRepository.createTable();
    // locationRepository.createTable();
    //mediaRepository.createTable();
    //plotLineRepository.createTable();
    // npcRepository.createTable();
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
    //npcRepository.createTable();
  }

  public Cursor read(String query) {
    return db.rawQuery(query, null);
  }

  public void write(String query) {
    db.execSQL(query);
  }

}
