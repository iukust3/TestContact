package com.example.contacttext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.contacttext.model.Contact
import com.example.contacttext.ui.component.LoadingCircular
import com.example.contacttext.ui.fragment.EditFragment
import com.example.contacttext.ui.fragment.detials.DetailFragment
import com.example.contacttext.ui.fragment.home.HomeFragment
import com.example.contacttext.ui.theme.ContactTextTheme
import com.example.contacttext.utials.Const.DETAIL_ARG_ID
import com.example.contacttext.utials.Const.DETAIL_SCREEN
import com.example.contacttext.utials.Const.EDIT_SCREEN
import com.example.contacttext.utials.Const.HOME_SCREEN
import com.example.contacttext.utials.Result
import com.example.contacttext.utials.Route
import com.example.contacttext.utials.Utils
import com.example.contacttext.utials.XmlParser
import com.example.contacttext.viewmodel.ContactsViewModel
import com.google.gson.Gson
import com.thoughtworks.xstream.XStream
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                data.also {
                    Log.e("TAG", " On Result " + it!!.data)
                    var uri = it.data
                    if (uri != null) {
                        showProgressDilog.value = true;
                        if (uri.toString().endsWith(".xml")) {
                            GlobalScope.launch(Dispatchers.IO) {
                                var pars = xmlParser.parseXml()
                                when (pars) {
                                    is Result.Success -> {
                                        showProgressDilog.value = false;
                                        runOnUiThread {

                                            Toast.makeText(
                                                this@MainActivity,
                                                "Import Done",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }

                                    }
                                    else -> {
                                        showProgressDilog.value = false;
                                        runOnUiThread {

                                            Toast.makeText(
                                                this@MainActivity,
                                                "Error While Import",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }

                                    }
                                }
                            }

                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Only xml file supported",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                Log.e("TAG", " On Result ")
            }
        }

    val items = listOf(
        MenuItems(Icons.Default.Refresh, "Import Xml"),
        MenuItems(Icons.Default.Send, "Export Xml"),
        MenuItems(Icons.Default.Send, "Export Json")
    )

    @Inject
    lateinit var xmlParser: XmlParser

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactTextTheme {
                var contactsViewModel: ContactsViewModel = hiltViewModel()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val selectedItem = remember { mutableStateOf(items[0]) }
                val importXml = remember {
                    mutableStateOf(false)
                }
                // A surface container using the 'background' color from the theme


                ModalNavigationDrawer(
                    drawerContent = {
                        if (curentPage.value == HOME_SCREEN)
                            ModalDrawerSheet {
                                Spacer(Modifier.height(12.dp))
                                items.forEach { item ->
                                    NavigationDrawerItem(
                                        icon = { Icon(item.icon, contentDescription = null) },
                                        label = { Text(item.name) },
                                        selected = item == selectedItem.value,
                                        onClick = {
                                            scope.launch { drawerState.close() }
                                            selectedItem.value = item
                                            if (item.name == "Import Xml") {
                                                val intent =
                                                    Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                                        addCategory(Intent.CATEGORY_OPENABLE)
                                                        type = "*/*"

                                                        // Optionally, specify a URI for the file that should appear in the
                                                        // system file picker when it loads.

                                                    }

                                                resultLauncher.launch(intent)

                                                // navController.navigate(Route.Detail.createRoute(1))
                                                importXml.value = true
                                                showProgressDilog.value = true
                                                scope.launch {
                                                    var result = xmlParser.parseXml()
                                                    if (result is Result.Success) {
                                                        importXml.value = false
                                                        showProgressDilog.value = false
                                                    } else if (result is Result.Error) {
                                                        Log.e(
                                                            "TAG",
                                                            " Error " + result.exception.message
                                                        )
                                                        showProgressDilog.value = false
                                                        result.exception.printStackTrace()
                                                    }
                                                }
                                            } else if (item.name == "Export Xml") {
                                                showProgressDilog.value = true
                                                scope.launch {


                                                    try {
                                                        var data =
                                                            contactsViewModel.getAllContacts()
                                                        val xstream = XStream()
                                                        xstream.alias(
                                                            "Contact",
                                                            Contact::class.java
                                                        )
                                                        xstream.alias(
                                                            "AddressBook",
                                                            List::class.java
                                                        )


                                                        val xml = xstream.toXML(data)
                                                        var path = Utils().xmlToFile(xml)
                                                        var file = File(path)
                                                        val intent = Intent()
                                                        intent.action = Intent.ACTION_SEND
                                                        intent.setDataAndType(
                                                            Uri.fromFile(file),
                                                            "application/xhtml+xml"
                                                        )
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                    }
                                                    showProgressDilog.value = false
                                                }
                                            } else if (item.name == "Export Json") {
                                                showProgressDilog.value = true
                                                scope.launch {
                                                    try {
                                                        var data =
                                                            contactsViewModel.getAllContacts()
                                                        var path =
                                                            Utils().objectToFile(Gson().toJson(data))
                                                        var file = File(path)

                                                        val intent = Intent()
                                                        intent.action = Intent.ACTION_SEND
                                                        intent.setDataAndType(
                                                            Uri.fromFile(file),
                                                            "txt/*"
                                                        )
                                                        startActivity(intent)
                                                    } catch (anfe: ActivityNotFoundException) {
                                                        Toast.makeText(
                                                            this@MainActivity,
                                                            "No activity found to open this attachment.",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    }
                                                    startActivity(intent)
                                                    showProgressDilog.value = false
                                                }
                                            }
                                        },
                                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }
                            }
                    },
                    modifier = Modifier.fillMaxSize(),
                    drawerState = drawerState,
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = {
                                    if (curentPage.value == HOME_SCREEN)
                                        Text(
                                            "My Contact App",
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                },
                                actions = {
                                    if (curentPage.value == DETAIL_SCREEN) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "",
                                            modifier = Modifier.clickable {

                                                navController.navigate(
                                                    Route.Edit.createRoute(contact!!.id)
                                                )
                                                curentPage.value = EDIT_SCREEN
                                            }
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "",
                                            modifier = Modifier.clickable {
                                                var alertDialog =
                                                    android.app.AlertDialog.Builder(this@MainActivity)
                                                alertDialog.setTitle("Delete Contact")
                                                alertDialog.setMessage("Are you sure to delete this contact?")
                                                alertDialog.setPositiveButton(
                                                    "No"
                                                ) { p0, p1 -> p0?.dismiss() }
                                                alertDialog.setNegativeButton(
                                                    "Yes"
                                                ) { p0, p1 ->
                                                    kotlin.run {
                                                        scope.launch {
                                                            contactsViewModel.deleteContact(contact)

                                                            navController.navigate(
                                                                Route.Home.route
                                                            )
                                                            curentPage.value = HOME_SCREEN
                                                        }
                                                        p0?.dismiss()
                                                    }

                                                }
                                                alertDialog.show()
                                            }
                                        )
                                    }


                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        if (curentPage.value == HOME_SCREEN)
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        else {
                                            navController.navigate(Route.Home.route)
                                            curentPage.value = HOME_SCREEN
                                        }
                                    }) {
                                        Icon(
                                            imageVector = if (curentPage.value == HOME_SCREEN) Icons.Filled.Menu else Icons.Filled.ArrowBack,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                },
                            )

                        },
                    ) {

                        if (importXml.value) {
                            LoadingCircular()
                        } else {
                            Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                                JetpackComposeAppScreen(navController)
                            }
                        }

                    }
                    progressDilog()
                }
            }
        }
    }

    override fun onBackPressed() {
        curentPage.value = HOME_SCREEN
        super.onBackPressed()
    }
}

var oneEdit = mutableStateOf(false)
var onDelete = mutableStateOf(false)
var curentPage = mutableStateOf(HOME_SCREEN)

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

var showProgressDilog = mutableStateOf(false)

@Composable
fun progressDilog() {
    if (showProgressDilog.value) {
        AlertDialog(
            onDismissRequest = {
                showProgressDilog.value = false
            },
            title = {
                Column() {
                    Text(text = "Loading data...")
                    LoadingCircular()
                }

            },
            confirmButton = {},


            )
    }
}

var contact: Contact? = null

@Composable
fun JetpackComposeAppScreen(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
    ) {
        composable(route = Route.Home.route) {
            HomeFragment(

                onClickToDetailScreen = { contatcId ->
                    contact = contatcId
                    curentPage.value = DETAIL_SCREEN
                    navController.navigate(
                        Route.Detail.createRoute(contatcId.id)
                    )
                    Log.e("TAG", " Id  " + navController.currentBackStackEntry?.id)
                },
                onclickToAddContact = {
                    curentPage.value = EDIT_SCREEN
                    navController.navigate(
                        Route.Edit.createRoute(0)
                    )
                }
            )
        }
        composable(
            route = Route.Detail.route,
            arguments = listOf(
                navArgument(DETAIL_ARG_ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt(DETAIL_ARG_ID)
            requireNotNull(contactId) { "contactId parameter wasn't found. Please make sure it's set!" }
            DetailFragment(id = contactId)
        }
        composable(
            route = Route.Edit.route,
            arguments = listOf(
                navArgument(DETAIL_ARG_ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt(DETAIL_ARG_ID)
            requireNotNull(contactId) { "contactId parameter wasn't found. Please make sure it's set!" }
            EditFragment(id = contactId, navController = navController) { contactId ->

                contact = contactId
                curentPage.value = DETAIL_SCREEN
                navController.navigate(
                    Route.Detail.createRoute(contactId.id)
                )
                Log.e("TAG", " Id  " + navController.currentBackStackEntry?.id)

            }
        }
    }
}

data class MenuItems(var icon: ImageVector, var name: String)