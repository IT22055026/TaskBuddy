package com.example.taskbuddy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.taskbuddy.model.Note

@Database (entities = [Note::class], version = 2)
abstract class NoteDatabase: RoomDatabase(){

    abstract fun getNoteDeo() : NoteDao

    companion object{
        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).addMigrations(MIGRATION_1_2)
                .build()

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add the 'noteDate' column to the 'notes' table and set it as non-null with a default value
                database.execSQL("ALTER TABLE notes ADD COLUMN noteDate TEXT NOT NULL DEFAULT ''")
            }
        }

    }
}