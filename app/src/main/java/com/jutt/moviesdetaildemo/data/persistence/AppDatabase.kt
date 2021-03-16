
package com.jutt.moviesdetaildemo.data.persistence

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.persistence.daos.MoviesDao
import java.util.concurrent.Executors

@Database(
    version = 1,
    entities = [
        Movie::class
    ]
)
@TypeConverters(TypeConversions::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        private const val DATABASE_NAME = "movies-db"

        fun buildDatabase(
            context: Context,
        ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .addCallback(object : Callback() {
                override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                    super.onDestructiveMigration(db)
                }
            })
            .build()
    }
}

