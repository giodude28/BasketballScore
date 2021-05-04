package io.giodude.basketballscore.Network;


import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.giodude.basketballscore.DI.NetworkModule;
import io.giodude.basketballscore.Model.LastMatches;
import io.giodude.basketballscore.Model.LeagueModel;
import io.giodude.basketballscore.Model.TeamModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository instance;

    public static Repository getInstance(){
        if (instance == null){
            instance = new Repository();
        }
        return instance;
    }

}
