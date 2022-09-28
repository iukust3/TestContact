package com.example.contacttext.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.contacttext.model.Contact
import com.example.contacttext.ui.theme.ContactTextTheme
import java.util.*

@Composable
fun LoadingCircular(
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (
            loadingCircular,
        ) = createRefs()
        CircularProgressIndicator(
            modifier = Modifier
                .constrainAs(loadingCircular){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingCircularPreview() {
    ContactTextTheme() {
        LoadingCircular()
    }
}


@Composable
fun ErrorButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (
            buttonText,
        ) = createRefs()
        TextButton(
            modifier = Modifier
                .constrainAs(buttonText){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            border = BorderStroke(
                1.dp,
               androidx.compose.ui.graphics.Color.Blue
            ),
            onClick = onClick,
        ){
            Text(text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorButtonPreview() {
    ContactTextTheme {
        ErrorButton(
            text = "Error"
        )
    }
}

val rnd = Random()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactCard(
    modifier: Modifier = Modifier.fillMaxWidth(),
  contact: Contact,
    onClickProduct: () -> Unit = {},
) {

    val color =  androidx.compose.ui.graphics.Color( rnd.nextInt(256),
        rnd.nextInt(256), rnd.nextInt(256),255,)
    Card(
        onClick = onClickProduct,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
          Box (
              Modifier
                  .padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
                  .height(30.dp)
                  .width(30.dp)
                  .clip(RoundedCornerShape(30))
                  .background(color = color, shape = RoundedCornerShape(30))
                  .align(Alignment.CenterVertically)
){
    Text(
        modifier=Modifier.fillMaxWidth(),
        text = contact.contactName.substring(0,1).toUpperCase(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign=TextAlign.Center,
        color = Color.LightGray
    )
}
Column(
    verticalArrangement = Arrangement.Center
) {

    Text(
        text =contact.contactName,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
    )

    Text(
        text = contact.phone,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 8.sp,
        fontWeight = FontWeight.Medium,
    )
}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ContactTextTheme() {
        ContactCard(
            contact =  Contact(0,"Test","Test","Ibrahim","Ibrahim",
        "","Pakistan","","test@gmail.con","","032133213",""))
    }
}



@Composable
fun ProductHeader(
    modifier: Modifier = Modifier,
    name: String = "Ibrahim",
    releaseDate: String = "",
) {

    val color =  androidx.compose.ui.graphics.Color( rnd.nextInt(256),
        rnd.nextInt(256), rnd.nextInt(256),255,)
    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Box(
            Modifier
                .padding()
                .height(50.dp)
                .width(50.dp)
                .clip(CircleShape)
                .background(color = color, shape = CircleShape)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = name.substring(0, 1).toUpperCase(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.LightGray
            )
        }
Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

        Spacer(modifier = Modifier.height(10.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun ProductHeaderPreview() {
    ContactTextTheme() {
        ProductHeader()
    }
}