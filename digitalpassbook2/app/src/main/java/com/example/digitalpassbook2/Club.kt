package com.example.digitalpassbook2

import com.google.gson.annotations.SerializedName

class Club {
    @SerializedName("id")
    var id = 0

    @SerializedName("name")
    var name: String

    constructor(id: Int, name: String) {
        this.id = id
        this.name = name
    }

    constructor(name: String) {
        this.name = name
    }
}