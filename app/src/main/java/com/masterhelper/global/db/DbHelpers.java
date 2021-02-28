package com.masterhelper.global.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.masterhelper.locations.repository.LocationRepository;
import com.masterhelper.journeys.repository.JourneyRepository;
import com.masterhelper.goals.repository.GoalRepository;
import com.masterhelper.media.repository.MediaRepository;
import com.masterhelper.plotLine.repository.PlotLineRepository;

public class DbHelpers extends SQLiteOpenHelper {
  public JourneyRepository journeyRepository;
  public GoalRepository goalRepository;
  public LocationRepository locationRepository;
  public MediaRepository mediaRepository;
  public PlotLineRepository plotLineRepository;

  /**
   * Имя файла базы данных
   */
  private static final String DATABASE_NAME = "global.db";
  /**
   * Версия базы данных. При изменении схемы увеличить на единицу
   */
  private static final int DATABASE_VERSION = 62;

  SQLiteDatabase db;


  public DbHelpers(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    journeyRepository = new JourneyRepository(this);
    goalRepository = new GoalRepository(this);
    locationRepository = new LocationRepository(this);
    mediaRepository = new MediaRepository(this);
    plotLineRepository = new PlotLineRepository(this);
    db = getWritableDatabase();
  }

  private void initTables(){
    journeyRepository.createTable();
    goalRepository.createTable();
    locationRepository.createTable();
    mediaRepository.createTable();
    plotLineRepository.createTable();
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
    goalRepository.createTable();
    plotLineRepository.createTable();
    //initTables();
  }

  public Cursor read(String query){
    return db.rawQuery(query, null);
  }

  public void write(String query){
    db.execSQL(query);
  }

}
