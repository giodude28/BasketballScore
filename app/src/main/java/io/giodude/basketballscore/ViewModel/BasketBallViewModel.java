package io.giodude.basketballscore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.giodude.basketballscore.DI.NetworkModule;
import io.giodude.basketballscore.Model.LastMatches;
import io.giodude.basketballscore.Model.LeagueModel;
import io.giodude.basketballscore.Model.TeamModel;
import io.giodude.basketballscore.Network.ParamKey;
import io.giodude.basketballscore.Network.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasketBallViewModel extends ViewModel {

    private MutableLiveData<List<TeamModel.Team>> teams;
    private MutableLiveData<List<LeagueModel.League>> league;
    private MutableLiveData<List<LastMatches.Event>> past;
    public Repository repositories;
    static NetworkModule rfit = new NetworkModule();


    public LiveData<List<TeamModel.Team>> getTeams(){
        return teams;
    }

    public LiveData<List<LeagueModel.League>> getLeague(){
        return league;
    }


    public LiveData<List<LastMatches.Event>> getPast(){
        return past;
    }


    public void init(){
        if (teams != null){
            return;
        }
        repositories = Repository.getInstance();
        teams = getTeam(ParamKey.team);

    }

    public void initLeague(){
        if (league != null){
            return;
        }
        repositories = Repository.getInstance();
        league = getLeagues();
    }

    public void initPast(){
        if (past != null){
            return;
        }
        repositories = Repository.getInstance();
        past = getPast(ParamKey.pastid);
    }


    public MutableLiveData<List<TeamModel.Team>> getTeam(String teamleague){
        final MutableLiveData<List<TeamModel.Team>> tData = new MutableLiveData<>();
        Call<TeamModel> call = rfit.retrofitBuilder().getTeam(teamleague);
        call.enqueue(new Callback<TeamModel>() {
            @Override
            public void onResponse(Call<TeamModel> call, Response<TeamModel> response) {
                TeamModel teamList = response.body();
                List<TeamModel.Team> teams = teamList.getTeams();
                tData.setValue(teams);
            }
            @Override
            public void onFailure(Call<TeamModel> call, Throwable t) {
                tData.setValue(null);
            }
        });
        return tData;
    }



    public MutableLiveData<List<LeagueModel.League>> getLeagues(){
        final MutableLiveData<List<LeagueModel.League>> lData = new MutableLiveData<>();
        Call<LeagueModel> call = rfit.retrofitBuilderleague().getLeague();
        call.enqueue(new Callback<LeagueModel>() {
            @Override
            public void onResponse(Call<LeagueModel> call, Response<LeagueModel> response) {
                LeagueModel leagueList = response.body();
                List<LeagueModel.League> league = leagueList.getLeagues();
                ArrayList<LeagueModel.League> newData = new ArrayList<>();
                for (int i = 0; i < league.size(); i++){
                    if (league.get(i).getStrSport().equals("Basketball")){
                        newData.add(league.get(i));
                    }
                }

                lData.setValue(newData);
            }

            @Override
            public void onFailure(Call<LeagueModel> call, Throwable t) {
                lData.setValue(null);
            }
        });
        return lData;
    }


    public MutableLiveData<List<LastMatches.Event>> getPast(String past){
        final MutableLiveData<List<LastMatches.Event>> pData = new MutableLiveData<>();
        Call<LastMatches> call = rfit.retrofitBuilderPast().getPast(past);
        call.enqueue(new Callback<LastMatches>() {
            @Override
            public void onResponse(Call<LastMatches> call, Response<LastMatches> response) {
                LastMatches pastList = response.body();
                List<LastMatches.Event> pasts = pastList.getEvents();
                pData.setValue(pasts);
            }

            @Override
            public void onFailure(Call<LastMatches> call, Throwable t) {
                pData.setValue(null);
            }
        });
        return pData;
    }
}
