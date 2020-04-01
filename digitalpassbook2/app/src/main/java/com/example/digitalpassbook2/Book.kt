package com.example.digitalpassbook2

import com.google.gson.annotations.SerializedName

class Book {
    @SerializedName("id")
    var id = 0

    @SerializedName("isbn")
    var isbn: String

    constructor(id: Int, isbn: String) {
        this.id = id
        this.isbn = isbn
    }

    constructor(isbn: String) {
        this.isbn = isbn
    }
}