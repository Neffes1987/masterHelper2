package com.masterhelper.global.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.masterhelper.screens.journey.JourneyRepository;
import com.masterhelper.screens.journey.JourneyProgressRepository;
import com.masterhelper.screens.plotTwist.PlotTwistRepository;
import com.masterhelper.screens.plotTwist.point.PointRepository;

public class DbHelpers extends SQLiteOpenHelper {
  public JourneyRepository journeyRepository;
  public PlotTwistRepository plotRepository;
  public JourneyProgressRepository journeyProgressRepository;
  public PointRepository pointRepository;

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

    db = getWritableDatabase();
    setupRepositories(db);
  }

  public void setupRepositories(SQLiteDatabase db) {
    if (db == null) {
      return;
    }

    journeyRepository = new JourneyRepository(db);
    plotRepository = new PlotTwistRepository(db);
    journeyProgressRepository = new JourneyProgressRepository(db);
    pointRepository = new PointRepository(db);
  }


  private void initTables() {
    setupRepositories(db);

    journeyRepository.createTable();
    plotRepository.createTable();
    journeyProgressRepository.createTable();
    pointRepository.createTable();
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
  }
}
