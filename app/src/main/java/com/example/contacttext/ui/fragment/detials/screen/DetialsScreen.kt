package com.example.contacttext.ui.fragment.detials.screen


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Telephony
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import com.example.contacttext.ContactApplication
import com.example.contacttext.model.Contact
import com.example.contacttext.ui.component.ProductHeader
import com.example.contacttext.ui.theme.ContactTextTheme


private fun sendSMS(contact: Contact) {
    try {
        val defaultSmsPackageName =
            Telephony.Sms.getDefaultSmsPackage(ContactApplication.instance) // Need to change the build to API 19
        val sms_uri = Uri.parse("smsto:${contact.phone}")
        val sendIntent = Intent(Intent.ACTION_SEND,sms_uri)
        sendIntent.type = "text/plain"
        sendIntent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
        sendIntent.putExtra(Intent.EXTRA_TEXT, "text")
        if (defaultSmsPackageName != null) // Can be null in case that there is no default, then the user would be able to choose
        // any app that support this intent.
        {
            sendIntent.setPackage(defaultSmsPackageName)
        }
        startActivity(ContactApplication.instance,sendIntent,null)
    } catch (e: Exception) {
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    contact: Contact? = null
) {
    if (contact == null) return
    val scrollState = rememberScrollState()
    val name = contact.contactName

    val releaseDate = contact.companyName
    val description = HtmlCompat
        .fromHtml(contact.contactTitle, HtmlCompat.FROM_HTML_MODE_COMPACT)
        .toString()
    val listImageCarousel = mutableListOf<String>()


    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {

        ProductHeader(
            modifier = Modifier.padding(16.dp),

            name = name,
            releaseDate = releaseDate,
        )

        Text(
            text = description,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        )
        Divider(Modifier.padding(start = 16.dp, end = 16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Phone, contentDescription = null) },

                    selected = false,
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${contact.phone}")
                        intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(ContactApplication.instance,intent,null)
                    },
                    modifier = Modifier.wrapContentSize()
                )

            }
            Column {
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Send, contentDescription = null) },

                    selected = false,
                    onClick = {
                       sendSMS(contact)
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .wrapContentSize()
                )
            }
            Column {
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = null) },

                    selected = false,
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SEND)
                        emailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        emailIntent.type = "vnd.android.cursor.item/email"
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contact.email))
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Email Subject")
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "My email content")
                        var choser=Intent.createChooser(emailIntent, "Send mail using...")
                        choser.flags=Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(ContactApplication.instance,choser,null)
                    },
                    modifier = Modifier.wrapContentSize()
                )
            }


        }
        Divider(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 10.dp))

        Column(Modifier.fillMaxSize()) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

                    ,
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)

            ) {
                Text(
                    text = "Content Info",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 10.dp,
                        end = 16.dp,
                        bottom = 10.dp
                    )
                )

                Row(
                    Modifier
                        .padding(start = 20.dp, bottom = 10.dp)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "")
                    Text(
                        text = contact.phone,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            start = 16.dp,

                        )
                    )
                }

                Row(
                    Modifier
                        .padding(start = 20.dp, bottom = 10.dp)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "")
                    Text(
                        text = contact.email,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            start = 16.dp,

                            )
                    )
                }
                Row(
                    Modifier
                        .padding(start = 50.dp, bottom = 10.dp)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text ="Fax "+ contact.fax ,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            start = 16.dp,

                            )
                    )
                }
            }

            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Text(
                    text = "Additional Info",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 16.dp,
                        top = 16.dp
                    )
                )

                Row(
                    Modifier
                        .padding(start = 30.dp, bottom = 10.dp)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = contact.contactTitle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            )
                    )
                }
                Row(
                    Modifier
                        .padding(start = 30.dp, bottom = 10.dp)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = contact.address,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            start = 16.dp,
top = 16.dp
                            )
                    )
                }
                Row(
                    Modifier
                        .padding(start = 30.dp)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = contact.country,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            start = 16.dp,

                            )
                    )
                }
                Row(
                    Modifier
                        .padding(start = 30.dp, bottom = 10.dp)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = contact.city,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            start = 16.dp,

                            )
                    )
                }
                Row(
                    Modifier
                        .padding(start = 30.dp, bottom = 10.dp)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = contact.companyName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            start = 16.dp,

                            )
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    ContactTextTheme {
        DetailScreen(
            contact = Contact(
                0, "Test", "Test", "Ibrahim", "Ibrahim",
                "", "Pakistan", "", "test@gmail.con", "", "032133213", ""
            )
        )


    }
}