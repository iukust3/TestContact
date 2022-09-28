package com.example.contacttext.ui.fragment.detials


import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contacttext.R
import com.example.contacttext.model.Contact
import com.example.contacttext.room.Response
import com.example.contacttext.ui.component.ErrorButton
import com.example.contacttext.ui.component.LoadingCircular
import com.example.contacttext.ui.fragment.detials.screen.DetailScreen
import com.example.contacttext.ui.theme.ContactTextTheme
import com.example.contacttext.viewmodel.ContactsViewModel

@Composable
fun DetailFragment(
    modifier: Modifier = Modifier,
    detailViewModel: ContactsViewModel = hiltViewModel(),
    id: Int = -1,
) {
    fun launch() {
        detailViewModel.getDetails(id)
    }

    launch()
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when(val contatcResponse=detailViewModel.contactState.value){
            is Response.Loading -> {
                LoadingCircular(
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is Response.Success<Contact> -> {
                DetailScreen(
                    contact = contatcResponse.data
                )
            }
            is Response.Failure -> {
                ErrorButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.error_message),
                    onClick = {
                        launch()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailFragmentPreview() {
    ContactTextTheme() {
        DetailFragment()
    }
}