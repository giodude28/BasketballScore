package io.giodude.basketballscore.DI;


import io.giodude.basketballscore.Network.TeamApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {

    public static TeamApiService retrofitBuilder(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TeamApiService.TEAM_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        TeamApiService apiCall = retrofit.create(TeamApiService.class);

        return apiCall;
    }

    public static TeamApiService retrofitBuilderleague(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TeamApiService.LEAGUE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        TeamApiService apiCall = retrofit.create(TeamApiService.class);

        return apiCall;
    }

    public static TeamApiService retrofitBuilderPast(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TeamApiService.PAST_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        TeamApiService apiCall = retrofit.create(TeamApiService.class);

        return apiCall;
    }
}
