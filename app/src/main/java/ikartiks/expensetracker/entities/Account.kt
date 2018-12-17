package ikartiks.expensetracker.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account") // by default same as class name
data class Account(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "name")// by default same as field name
    var name: String?

    // @Ignore var picture: Bitmap?
){
    constructor():this(null,null)
}

//https://github.com/ritesh-singh/WeatherApplication/blob/arch_components/app/src/main/java/com/example/riteshkumarsingh/weatherapplication/db/entities/WeatherData.kt