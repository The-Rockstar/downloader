package video.xdownloader.resetapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import video.xdownloader.models.PixabayModel;

/**
 * Created by jaswinderwadali on 23/08/16.
 */

public interface ApiServices {

    @GET("/site/androidapksapps/")
    Call<ResponseBody> getHtmlData();

    @GET
    Call<ResponseBody> downloadFile(@Url String s);


    @GET("api/")
    Call<PixabayModel> getPixabayData(@Query("key") String key, @Query("q") String query);



}