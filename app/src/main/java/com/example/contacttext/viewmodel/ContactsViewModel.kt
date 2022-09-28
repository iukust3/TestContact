package com.example.contacttext.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.contacttext.model.Contact
import com.example.contacttext.room.Repository
import com.example.contacttext.room.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class ContactsViewModel  @Inject constructor(
    private val repository:Repository
) :ViewModel(){

    private val _contatctState = mutableStateOf<Response<Contact>>(Response.Loading)
    val contactState: State<Response<Contact>> = _contatctState

    val contactPagging = Pager(PagingConfig(
        pageSize = 10,
        enablePlaceholders = false,
        maxSize = 30
    )) {
        repository.getAllContacts()
    }.flow.cachedIn(viewModelScope)
   fun refresh(){
       contactPagging.retry {true }
   }


    fun getDetails(id:Int){
        viewModelScope.launch {
                repository.getContactDetails(id).collect { response ->
                    _contatctState.value = Response.Success(response)
                }

        }
    }

    fun saveContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {

            repository.saveContact(contact)
        }
    }

    suspend fun insertContact(value: Contact): Long {
     return repository.insertContact(value)
    }

    fun deleteContact(contact: Contact?) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteContact(contact)
        }
    }

    suspend fun getAllContacts(): List<Contact> {
return withContext(Dispatchers.IO){
    repository.getAllContactsAsList()
}
    }
}