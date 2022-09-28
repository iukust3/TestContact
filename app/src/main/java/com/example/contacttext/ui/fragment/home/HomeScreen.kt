package com.example.contacttext.ui.fragment.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.contacttext.R
import com.example.contacttext.model.Contact
import com.example.contacttext.ui.component.ErrorButton
import com.example.contacttext.ui.component.LoadingCircular
import com.example.contacttext.ui.component.ContactCard
import com.example.contacttext.ui.theme.ContactTextTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    gamesList: LazyPagingItems<Contact>? = null,
    onClickToDetailScreen: (Contact) -> Unit = {},
) {
    if(gamesList == null) return
    LazyColumn(
        modifier = modifier.padding(top = 10.dp),

        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(gamesList.itemCount) { index ->
            gamesList[index].let { contact ->
              
                ContactCard(
                    modifier = modifier
                        .padding(8.dp),
                    contact = contact!!,
                    onClickProduct = {

                            onClickToDetailScreen.invoke(contact)

                    }
                )
            }
        }
        gamesList.apply {
            item(
            ) {
                when {
                    loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                        LoadingCircular(
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                        ErrorButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.error_message),
                            onClick = {
                                retry()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ContactTextTheme() {
        HomeScreen()
    }
}