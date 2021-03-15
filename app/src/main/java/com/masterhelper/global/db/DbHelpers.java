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
  private static final int DATABASE_VERSION = 69;

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
  }

  void goalMigration() {
    this.db.execSQL("PRAGMA foreign_keys=off;");
    this.db.execSQL("BEGIN TRANSACTION;");

    this.db.execSQL("ALTER TABLE goals RENAME TO _goals_old;");
    goalRepository.createTable();


    this.db.execSQL("INSERT INTO goals (id, plotId, title, description, progress, act, assigned_location) SELECT id, plotId, title, description, progress, act, assigned_location FROM _goals_old;");


    this.db.execSQL("DROP TABLE _goals_old;");

    this.db.execSQL("COMMIT;");
    this.db.execSQL("PRAGMA foreign_keys=on;");
  }

  public Cursor read(String query) {
    return db.rawQuery(query, null);
  }

  public void write(String query) {
    db.execSQL(query);
  }

}
