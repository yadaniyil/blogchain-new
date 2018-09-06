package com.yadaniil.blogchain.db.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [Index("name", unique = true)])
data class Portfolio(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val name: String,
        val description: String,
        val creationDate: Date,
        val lastUpdateDate: Date
) {

    override fun toString(): String {
        return "Name: $name, Id: $id, Description: $description, " +
                "creationDate: $creationDate, lastUpdated: $lastUpdateDate"
    }
}