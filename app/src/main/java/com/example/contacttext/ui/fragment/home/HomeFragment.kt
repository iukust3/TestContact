package com.example.contacttext.ui.fragment.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.contacttext.model.Contact
import com.example.contacttext.ui.theme.ContactTextTheme
import com.example.contacttext.viewmodel.ContactsViewModel

@Composable
fun HomeFragment(
    modifier: Modifier = Modifier,
    homeViewModel: ContactsViewModel = hiltViewModel(),
    onClickToDetailScreen: (Contact) -> Unit = {},
    onclickToAddContact:()->Unit={}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column() {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        16.dp
                    )
                    .clickable { onclickToAddContact()},
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
Icon(imageVector = Icons.Default.Add, contentDescription ="" )
                Text(text = "Create New Contact")
            }
            HomeScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    ),
                gamesList = homeViewModel.contactPagging.collectAsLazyPagingItems(),
                onClickToDetailScreen = onClickToDetailScreen,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeFragmentPreview() {
    ContactTextTheme {
        HomeFragment()
    }
}