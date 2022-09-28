package com.example.contacttext.utials

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.contacttext.model.Contact
import com.example.contacttext.room.Repository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.SAXException
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URI
import javax.inject.Inject
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class XmlParser @Inject constructor(val contactApplication: Context, private val repository: Repository)  {
suspend fun parseXml():Result<String> {
    try {
        val istream: InputStream = contactApplication.assets.open("ab.xml")

        // Steps to convert this input stream into a list
        val builderFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        val docBuilder: DocumentBuilder = builderFactory.newDocumentBuilder()
        val doc: Document = withContext(Dispatchers.IO) {
            docBuilder.parse(istream)
        }
        val nList: NodeList = doc.getElementsByTagName("Contact")
        // Iterating through this list
        Log.e("TAG","Node list "+nList.length)
        for (i in 0 until nList.length) {
            if (nList.item(0).nodeType === Node.ELEMENT_NODE) {
                val elm: Element = nList.item(i) as Element
              val contact=Contact();
                contact.customerID=getNodeValue("designation", elm)
                        contact.companyName=getNodeValue("CompanyName",elm)
                        contact.contactName=getNodeValue("ContactName",elm)
                        contact.contactTitle=getNodeValue("ContactTitle",elm)
                        contact.address=getNodeValue("Address",elm)
                        contact.city=getNodeValue("City",elm)
                        contact.email=getNodeValue("Email",elm)
                        contact.postalCode=getNodeValue("PostalCode",elm)
                        contact.country=getNodeValue("Country",elm)
                        contact.phone=getNodeValue("Phone",elm)
                        contact.fax=getNodeValue("Fax",elm)
                Log.e("TAG"," Node "+Gson().toJson(contact))
                try {
                    repository.insertContact(contact)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

return Result.Success("Insert Successfully")
        // Using Adapter to broadcast the information extracted
    } catch (e: IOException) {
        e.printStackTrace()

        return Result.Error(e)
    } catch (e: ParserConfigurationException) {
        e.printStackTrace()
        return Result.Error(e)
    } catch (e: SAXException) {
        e.printStackTrace()
        return Result.Error(e)
    }

}
    suspend fun parseXml( uri: URI):Result<String> {
    try {
        val istream: InputStream = File(uri).inputStream()

        // Steps to convert this input stream into a list
        val builderFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        val docBuilder: DocumentBuilder = builderFactory.newDocumentBuilder()
        val doc: Document = withContext(Dispatchers.IO) {
            docBuilder.parse(istream)
        }
        val nList: NodeList = doc.getElementsByTagName("Contact")
        // Iterating through this list
        Log.e("TAG","Node list "+nList.length)
        for (i in 0 until nList.length) {
            if (nList.item(0).nodeType === Node.ELEMENT_NODE) {
                val elm: Element = nList.item(i) as Element
              val contact=Contact();
                contact.customerID=getNodeValue("designation", elm)
                        contact.companyName=getNodeValue("CompanyName",elm)
                        contact.contactName=getNodeValue("ContactName",elm)
                        contact.contactTitle=getNodeValue("ContactTitle",elm)
                        contact.address=getNodeValue("Address",elm)
                        contact.city=getNodeValue("City",elm)
                        contact.email=getNodeValue("Email",elm)
                        contact.postalCode=getNodeValue("PostalCode",elm)
                        contact.country=getNodeValue("Country",elm)
                        contact.phone=getNodeValue("Phone",elm)
                        contact.fax=getNodeValue("Fax",elm)
                Log.e("TAG"," Node "+Gson().toJson(contact))
                try {
                    repository.insertContact(contact)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

return Result.Success("Insert Successfully")
        // Using Adapter to broadcast the information extracted
    } catch (e: IOException) {
        e.printStackTrace()

        return Result.Error(e)
    } catch (e: ParserConfigurationException) {
        e.printStackTrace()
        return Result.Error(e)
    } catch (e: SAXException) {
        e.printStackTrace()
        return Result.Error(e)
    }

}
// A function to get the node value while parsing
 private fun getNodeValue(tag: String?, element: Element): String {
    val nodeList = element.getElementsByTagName(tag)
    val node = nodeList.item(0)
    if (node != null) {
        if (node.hasChildNodes()) {
            val child = node.firstChild
            while (child != null) {
                if (child.nodeType == Node.TEXT_NODE) {
                    return child.nodeValue
                }
            }
        }
    }
    // Returns nothing if nothing was found
    return ""
}
}