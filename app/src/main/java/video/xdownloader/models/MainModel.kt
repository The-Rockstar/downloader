package video.xdownloader.models

import android.text.TextUtils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by jaswinderwadali on 27/08/17.
 */

class MainModel : Serializable {

    @SerializedName("typeName")
    @Expose
    var typeName: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("dataList")
    @Expose
    var dataList: List<DataList>? = null

    class DataList : CommonModel() {

        @SerializedName("pageName")
        @Expose
        var pageName: String? = null
            get() {
                if (TextUtils.isEmpty(field))
                    this.pageName = super.name
                return field
            }
        @SerializedName("pageID")
        @Expose
        var pageID: String? = null
            get() {
                if (TextUtils.isEmpty(field))
                    this.pageID = super.id

                return field
            }
    }


}
