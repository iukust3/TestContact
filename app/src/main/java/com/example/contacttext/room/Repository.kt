package com.example.contacttext.room

import androidx.paging.PagingSource
import com.example.contacttext.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository  @Inject constructor(
    private val contactDao: ContactDao
) {
    suspend fun insertContact(contact: Contact):Long {
     return   withContext(Dispatchers.IO) {
            contactDao.insertCustomer(contact)
        }
    }

    fun getAllContacts(): PagingSource<Int, Contact> {

        return contactDao.getAllContacts()
    }

   suspend fun getContactDetails(id: Int): Flow<Contact> {
        return  contactDao.getContactDetails(id)
    }

    fun saveContact(contact: Contact) {
        contactDao.updateContact(contact)
    }

    fun deleteContact(contact: Contact?) {
        contactDao.deleteContact(contact)
    }

    fun getAllContactsAsList(): List<Contact> {
return contactDao.getAllContactsAsList()
    }


}
sealed class Response<out T> {
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ): Response<T>()

    data class Failure(
        val e: Exception?
    ): Response<Nothing>()
}