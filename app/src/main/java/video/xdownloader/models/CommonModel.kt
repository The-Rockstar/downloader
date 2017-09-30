package video.xdownloader.models

import android.text.TextUtils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by jaswinderwadali on 15/07/17.
 */

open class CommonModel : Serializable {

    @SerializedName("name")
    @Expose
    var name: String? = null
        get() {
            if (TextUtils.isEmpty(field))
                this.name = ""
            return field
        }
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("created_time")
    @Expose
    var createdTime: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null
        get() {
            if (!TextUtils.isEmpty(field))
                this.description = field!!.trim { it <= ' ' }
            else
                this.description = "name less"

            return field
        }

    @SerializedName("source")
    @Expose
    var source: String? = null

    @SerializedName("path")
    @Expose
    var path: String? = null

}
