package com.han.my_friend_kim_jung_san

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    var name : String,
    var email : String,
    var password : String,
    var bank : String?,
    var account : Int?
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
