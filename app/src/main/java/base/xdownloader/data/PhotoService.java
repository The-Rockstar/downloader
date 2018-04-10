/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package base.xdownloader.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import base.xdownloader.data.model.Photo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Only gets photos once per runtime.
 */
public class PhotoService {

    public interface PhotoCallback {
        void success(ArrayList<Photo> photos);

        void error();
    }

    private static final int PHOTO_COUNT = 12;
    private static final String TAG = "PhotoService";
    private static ArrayList<Photo> mPhotos;
    private static PhotoService sPhotoService;

    public static PhotoService getInstance() {
        if (sPhotoService == null) {
            sPhotoService = new PhotoService();
        }
        return sPhotoService;
    }

    public void getPhotosAsync(final PhotoCallback callback) {
        if (mPhotos == null) {



            UnsplashService retrofit = new Retrofit.Builder()
                    .baseUrl(UnsplashService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(UnsplashService.class);

            Call<List<Photo>> listCall= retrofit.getFeed();
            listCall.enqueue(new Callback<List<Photo>>() {
                @Override
                public void onResponse(Call<List<Photo>> call, Response<List<Photo>> photos) {
                    mPhotos = new ArrayList<>(photos.body().subList(photos.body().size() - PHOTO_COUNT,
                            photos.body().size()));
                    callback.success(mPhotos);

                }

                @Override
                public void onFailure(Call<List<Photo>> call, Throwable t) {
                    callback.error();
                    Log.e(TAG, "Could not load photos, " + "");

                }
            });



        } else {
            callback.success(mPhotos);
        }
    }
}
