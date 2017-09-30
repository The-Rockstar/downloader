package video.xdownloader.resetapi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import video.xdownloader.BuildConfig;

/**
 * Created by jaswinderwadali on 23/08/16.
 */
public class RestAdapter2 {

    OkHttpClient okClient;
    private static RestAdapter2 restAdapter;
    private ApiServices apiService;

    private RestAdapter2(final ProgressListener progressListener) {

        okClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        if (progressListener != null)
                            return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(), progressListener)).build();
                        else
                            return originalResponse.newBuilder().build();
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl((BuildConfig.HOST)).client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiService = retrofit.create(ApiServices.class);

    }

    public static RestAdapter2 getInstance(ProgressListener progressListener) {
        if (restAdapter == null)
            restAdapter = new RestAdapter2(progressListener);
        return restAdapter;
    }

    public static RestAdapter2 getInstance() {
        return getInstance(null);
    }


    public OkHttpClient getOkClient() {
        return okClient;
    }


    public ApiServices getApiService() {
        return apiService;
    }

    private static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    public interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }


}