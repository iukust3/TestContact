package com.example.contacttext.ui.fragment.edit.screen



import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.contacttext.model.Contact
import com.example.contacttext.ui.theme.ContactTextTheme
import com.example.contacttext.utials.Route
import com.example.contacttext.viewmodel.ContactsViewModel
import kotlinx.coroutines.launch
var contectState= mutableStateOf<Contact>(Contact())
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    contact: Contact = Contact(),
    viewModel:ContactsViewModel= hiltViewModel(),
    onSave:(Contact)->Unit
) {
    val scrollState = rememberScrollState()
    if(contectState.value.contactName.isEmpty()){
        contectState.value=contact;
    }
val scope= rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
Spacer(modifier = Modifier.height(5.dp))
      TextFieldDemo(

          value = contectState.value.contactName,
          label =  "Name",

          onValueChange ={
              Log.e("TAG","On Value Change "+it)
              var newContact=contectState.value;
              newContact.contactName=it;
              contectState.value=newContact
      } )
        Spacer(modifier = Modifier.height(5.dp))

        TextFieldDemo(
          value = contectState.value.phone,


      label ="Phone Number",
      onValueChange ={
          var newContact=contectState.value;
          newContact.phone=it;
          contectState.value=newContact
      } )
        Spacer(modifier = Modifier.height(5.dp))

        TextFieldDemo(
          value = contectState.value.fax,


      label = "Fax Number",
      onValueChange ={
          var newContact=contectState.value;
          newContact.fax=it;
          contectState.value=newContact
      } )
        Spacer(modifier = Modifier.height(5.dp))

        TextFieldDemo(
          value = contectState.value.email,
      label = "Email",
      onValueChange ={
          var newContact=contectState.value;
          newContact.email=it;
          contectState.value=newContact
      } )

        Spacer(modifier = Modifier.height(5.dp))

        TextFieldDemo(
          value = contectState.value.contactTitle,
      label =  "Contact Title",
      onValueChange ={
          var newContact=contectState.value;
          newContact.contactTitle=it;
          contectState.value=newContact
      } )

        Spacer(modifier = Modifier.height(5.dp))

        TextFieldDemo(
          value = contectState.value.address,
      label =  "Address",
      onValueChange ={
          var newContact=contectState.value;
          newContact.address=it;
          contectState.value=newContact
      } )

        Spacer(modifier = Modifier.height(5.dp))

        TextFieldDemo(
          value = contectState.value.city,
      label = "City",
      onValueChange ={
          var newContact=contectState.value;
          newContact.city=it;
          contectState.value=newContact
      } )

        Spacer(modifier = Modifier.height(5.dp))

        TextFieldDemo(
          value = contectState.value.companyName,
      label =  "Company",
      onValueChange ={
          var newContact=contectState.value;
          newContact.contactName=it;
          contectState.value=newContact
      } )

        Spacer(modifier = Modifier.height(5.dp))

        TextFieldDemo(
          value = contectState.value.country,
      label = "Country",
      onValueChange ={
          var newContact=contectState.value;
          newContact.country=it;
          contectState.value=newContact
      } )

        Spacer(modifier = Modifier.height(5.dp))

        TextFieldDemo(
          value = contectState.value.postalCode,
      label = "Postal Code",
      onValueChange ={
          var newContact=contectState.value;
          newContact.postalCode=it;
          contectState.value=newContact
      } )
OutlinedButton(onClick = {
 scope.launch {
     if(contact.id>0)
     viewModel.saveContact(contectState.value)
     else {
         val id = viewModel.insertContact(contectState.value)
         val newContact=contectState.value;
         newContact.id= id.toInt();
         contectState.value=newContact
     }
     onSave(contectState.value)
 }

}, modifier = Modifier.fillMaxWidth().padding(start = 50.dp,end=50.dp) ) {
    Text(text = "Save")
}
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    ContactTextTheme {
        EditScreen(
            contact = Contact(
                0, "Test", "Test", "Ibrahim", "Ibrahim",
                "", "Pakistan", "", "test@gmail.con", "", "032133213", ""
            ),
            onSave = {}
        )


    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDemo(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label: String,
) {
    val text = remember { mutableStateOf(TextFieldValue(value)) }
    OutlinedTextField(value = text.value,
        onValueChange = { text.value = it
            onValueChange(text.value.text)
                        },
        label = { Text(label) })
}