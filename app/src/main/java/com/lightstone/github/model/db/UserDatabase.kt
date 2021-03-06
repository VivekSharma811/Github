package com.lightstone.github.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lightstone.github.model.db.dao.RepoDao
import com.lightstone.github.model.db.dao.UserDao
import com.lightstone.github.model.response.GithubRepository
import com.lightstone.github.model.response.UserItem


@Database(
    entities = arrayOf(UserItem::class, GithubRepository::class),
    version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun repoDao() : RepoDao

    companion object {
        @Volatile private var instance : UserDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UserDatabase::class.java,
                "user.db")
                .build()
    }

}