package video.xdownloader.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by jaswinderwadali on 30/09/17.
 */

class PixabayModel {

    @SerializedName("totalHits")
    @Expose
    var totalHits: Int? = null
    @SerializedName("hits")
    @Expose
    var hits: List<Hit>? = ArrayList()
    @SerializedName("total")
    @Expose
    var total: Int? = null


    inner class Hit {

        @SerializedName("previewHeight")
        @Expose
        var previewHeight: Int? = null
        @SerializedName("likes")
        @Expose
        var likes: Int? = null
        @SerializedName("favorites")
        @Expose
        var favorites: Int? = null
        @SerializedName("tags")
        @Expose
        var tags: String? = null
        @SerializedName("webformatHeight")
        @Expose
        var webformatHeight: Int? = null
        @SerializedName("views")
        @Expose
        var views: Int? = null
        @SerializedName("webformatWidth")
        @Expose
        var webformatWidth: Int? = null
        @SerializedName("previewWidth")
        @Expose
        var previewWidth: Int? = null
        @SerializedName("comments")
        @Expose
        var comments: Int? = null
        @SerializedName("downloads")
        @Expose
        var downloads: Int? = null
        @SerializedName("pageURL")
        @Expose
        var pageURL: String? = null
        @SerializedName("previewURL")
        @Expose
        var previewURL: String? = null
        @SerializedName("webformatURL")
        @Expose
        var webformatURL: String? = null
        @SerializedName("imageWidth")
        @Expose
        var imageWidth: Int? = null
        @SerializedName("user_id")
        @Expose
        var userId: Int? = null
        @SerializedName("user")
        @Expose
        var user: String? = null
        @SerializedName("type")
        @Expose
        var type: String? = null
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("userImageURL")
        @Expose
        var userImageURL: String? = null
        @SerializedName("imageHeight")
        @Expose
        var imageHeight: Int? = null

    }
}
