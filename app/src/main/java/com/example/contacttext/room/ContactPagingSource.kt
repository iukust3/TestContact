package com.example.contacttext.room

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.contacttext.model.Contact

class ContactPagingSource(  private val response: suspend (Int) -> List<Contact>,) : PagingSource<Int, Contact>() {
    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> {
        return try {

            val nextPage = params.key ?: 1
            val result = response.invoke(nextPage)
            LoadResult.Page(

                data = result,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey =  result[result.size-1].id
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}