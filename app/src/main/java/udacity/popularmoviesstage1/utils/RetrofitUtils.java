package udacity.popularmoviesstage1.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import udacity.popularmoviesstage1.Api;

public final class RetrofitUtils {

    public static Retrofit getClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
