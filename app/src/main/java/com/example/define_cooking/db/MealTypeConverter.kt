package com.example.define_cooking.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun fromAntToString(attribute:Any?):String{
        if(attribute==null)
            return ""
        return attribute as String
    }


    fun fromStringToAny(attribute: String?):Any{
        if(attribute==null)
            return ""
        return attribute

    }

}