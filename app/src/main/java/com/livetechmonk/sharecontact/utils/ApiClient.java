package com.livetechmonk.sharecontact.utils;

import com.livetechmonk.sharecontact.models.request.ContactDatarequest;
import com.livetechmonk.sharecontact.models.request.SupportRequest;
import com.livetechmonk.sharecontact.models.response.ContactData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ajay on 15/6/17.
 */

public class ApiClient {
    public static final String BASE_URL = "https://monarchprotrade.com/";
    private static ApiInterface gitApiInterface;
    public static ApiInterface getClient() {
        if (gitApiInterface == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            //logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder okClient = new OkHttpClient.Builder();
            okClient.addInterceptor(logging);
            okClient.connectTimeout(150, TimeUnit.SECONDS);
            okClient.readTimeout(150,TimeUnit.SECONDS);
            okClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            });
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            gitApiInterface = client.create(ApiInterface.class);
            return gitApiInterface;
        }
        return gitApiInterface;
    }


    /**
     * Interface hold various method for web service calling.
     */
    public interface ApiInterface {
        /** Method to execute contact share request.
         * @param shareContactData :  hold the user contact details.
         * @return : return raw responsne returned from server.
         */
        @POST("sure.svc/AddContacts")
        Call<ResponseBody> shareContact(@Body ContactDatarequest shareContactData);

        /** Method to execute contact share request.
         * @param supportData :  hold the support details entered by user.
         * @return : return raw responsne returned from server.
         */
        @POST("sure.svc/RequestForDistributer")
        Call<ResponseBody> support(@Body SupportRequest supportData);


    }
}
