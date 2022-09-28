package com.example.contacttext.di
import android.content.Context
import androidx.room.Room
import com.example.contacttext.room.ContactDao
import com.example.contacttext.room.ContactDatabase
import com.example.contacttext.room.Repository
import com.example.contacttext.utials.XmlParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
       ContactDatabase::class.java,
        "contact.db"
    ).build()

    @Singleton
    @Provides
    fun provideDao(db: ContactDatabase) = db.contactDao()
    @Singleton
    @Provides
    fun provideRepository(dao: ContactDao)=Repository(dao)

    @Singleton
    @Provides
    fun provideXml( @ApplicationContext app: Context,repository: Repository) = XmlParser(app,repository)
}