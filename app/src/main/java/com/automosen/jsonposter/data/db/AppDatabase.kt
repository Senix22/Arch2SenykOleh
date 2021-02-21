package com.automosen.jsonposter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.automosen.jsonposter.data.db.entities.Post

@Database(
    entities = [Post::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getPostDao() : PostDao


    companion object{
        @Volatile
        private var instance : AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AppDatabase.db"
            ).build()
    }
}