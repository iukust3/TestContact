package com.example.contacttext.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contacttext.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase:RoomDatabase() {
    abstract  fun contactDao():ContactDao
}