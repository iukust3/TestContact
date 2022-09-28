package com.example.contacttext.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    indices = [Index(
        value = ["phone", "email", "fax", "customerID"],
        unique = true
    )]
)
data class Contact(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo
    @SerializedName("Address")
    var address: String,
    @ColumnInfo
    @SerializedName("City")
    var city: String,
    @ColumnInfo
    @SerializedName("CompanyName")
    var companyName: String,
    @ColumnInfo
    @SerializedName("ContactName")
    var contactName: String,
    @ColumnInfo
    @SerializedName("ContactTitle")
    var contactTitle: String,
    @ColumnInfo
    @SerializedName("Country")
    var country: String,
    @ColumnInfo(name = "customerID")
    @SerializedName("CustomerID")
    var customerID: String,

    @ColumnInfo(name = "email")
    @SerializedName("Email")
    var email: String,

    @ColumnInfo(name = "fax")
    @SerializedName("Fax")
    var fax: String,

    @ColumnInfo(name = "phone")
    @SerializedName("Phone")
    var phone: String,
    @SerializedName("PostalCode")
    var postalCode: String
) {
    constructor() : this(0, "", "", "", "", "", "", "", "", "", "", "")
}