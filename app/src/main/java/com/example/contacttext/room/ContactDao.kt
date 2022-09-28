package com.example.contacttext.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.contacttext.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert
   suspend  fun insertCustomer(contact: Contact):Long
   @Query("SELECT * FROM Contact")
    fun  getAllContacts(): PagingSource<Int, Contact>
    @Query("SELECT * FROM Contact WHERE id=:id")
    fun getContactDetails(id: Int): Flow<Contact>
    @Update
    fun updateContact(contact: Contact)
    @Delete
    fun deleteContact(contact: Contact?)
    @Query("SELECT * FROM Contact")
    fun getAllContactsAsList(): List<Contact>

}