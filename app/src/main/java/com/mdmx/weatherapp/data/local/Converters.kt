package com.mdmx.weatherapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.mdmx.weatherapp.data.util.JsonParser
import com.mdmx.weatherapp.domain.model.Data


@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromDataJson(json: String): List<Data> {
        return jsonParser.fromJson<ArrayList<Data>>(
            json,
            object : TypeToken<ArrayList<Data>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toDataJson(meanings: List<Data>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Data>>() {}.type
        ) ?: "[]"
    }
}