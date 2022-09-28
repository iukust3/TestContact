package com.example.contacttext.utials

import android.os.Environment
import com.example.contacttext.ContactApplication
import com.example.contacttext.model.Contact
import com.example.contacttext.utials.Const.DETAIL_ARG_ID
import com.example.contacttext.utials.Const.DETAIL_SCREEN
import com.example.contacttext.utials.Const.EDIT_SCREEN
import com.example.contacttext.utials.Const.HOME_SCREEN
import java.io.*


class Utils {

    @Throws(IOException::class)
    fun objectToFile(`object`: Any?): String? {

      val path=fileFolder()+"contact.txt"


        val data = File(path)
        if (!data.createNewFile()) {
            data.delete()
            data.createNewFile()
        }
        val objectOutputStream = ObjectOutputStream(FileOutputStream(data))
        objectOutputStream.writeObject(`object`)
        objectOutputStream.close()
        return path
    }
    @Throws(IOException::class)
    fun xmlToFile(`object`: Any?): String? {
        var path=fileFolder()+"contact.xml"

        val data = File(path)
        if (!data.createNewFile()) {
            data.delete()
            data.createNewFile()
        }
        val objectOutputStream = ObjectOutputStream(FileOutputStream(data))
        objectOutputStream.writeObject(`object`)
        objectOutputStream.close()
        return path
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    fun objectFromFile(path: String?): Any? {
        var `object`: Any? = null
        val data = File(path)
        if (data.exists()) {
            val objectInputStream = ObjectInputStream(FileInputStream(data))
            `object` = objectInputStream.readObject()
            objectInputStream.close()
        }
        return `object`
    }

    private fun fileFolder():String{
        val dir=File( ContactApplication.instance .filesDir.absolutePath+ File.separator +
                "/ContactApp")
        if(!dir.exists())
            dir.mkdirs()
        return ContactApplication.instance .filesDir.absolutePath+ File.separator +
                "/ContactApp" + File.separator;

    }
}

sealed class Route(val route: String) {
    object Home: Route(HOME_SCREEN)
    object Detail: Route("$DETAIL_SCREEN/{$DETAIL_ARG_ID}") {
        fun createRoute(gamesId: Int) = "$DETAIL_SCREEN/$gamesId"
    }
    object Edit: Route("$EDIT_SCREEN/{$DETAIL_ARG_ID}") {
        fun createRoute(contatId: Int) = "$EDIT_SCREEN/$contatId"
    }
}
object Const {
    //Screens
    const val HOME_SCREEN = "home"
    const val DETAIL_SCREEN = "detail"
    const val EDIT_SCREEN = "edit"
    const val DETAIL_ARG_ID = "contactId"
    const val EDIT="Edit"

}
class AddressBook {
    private val list: MutableList<Contact>

    init {
        list = ArrayList<Contact>()
    }

    fun add(p: Contact) {
        list.add(p)
    }
}
