package com.codepath.apps.restclienttemplate;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import com.codepath.apps.restclienttemplate.models.SampleModelDao;
import com.codepath.apps.restclienttemplate.models.SampleModelDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.HashSet;

public class MyDatabase_Impl extends MyDatabase {
  private volatile SampleModelDao _sampleModelDao;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `SampleModel` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"6e67c6322d1cd4b011c8b19c13634a4c\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `SampleModel`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsSampleModel = new HashMap<String, TableInfo.Column>(2);
        _columnsSampleModel.put("id", new TableInfo.Column("id", "INTEGER", false, 1));
        _columnsSampleModel.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSampleModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSampleModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSampleModel = new TableInfo("SampleModel", _columnsSampleModel, _foreignKeysSampleModel, _indicesSampleModel);
        final TableInfo _existingSampleModel = TableInfo.read(_db, "SampleModel");
        if (! _infoSampleModel.equals(_existingSampleModel)) {
          throw new IllegalStateException("Migration didn't properly handle SampleModel(com.codepath.apps.restclienttemplate.models.SampleModel).\n"
                  + " Expected:\n" + _infoSampleModel + "\n"
                  + " Found:\n" + _existingSampleModel);
        }
      }
    }, "6e67c6322d1cd4b011c8b19c13634a4c");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "SampleModel");
  }

  @Override
  public SampleModelDao sampleModelDao() {
    if (_sampleModelDao != null) {
      return _sampleModelDao;
    } else {
      synchronized(this) {
        if(_sampleModelDao == null) {
          _sampleModelDao = new SampleModelDao_Impl(this);
        }
        return _sampleModelDao;
      }
    }
  }
}
