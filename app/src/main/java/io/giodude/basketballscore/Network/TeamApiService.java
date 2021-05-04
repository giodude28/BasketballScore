package io.giodude.basketballscore.Network;

import io.giodude.basketballscore.Model.LastMatches;
import io.giodude.basketballscore.Model.LeagueModel;
import io.giodude.basketballscore.Model.TeamModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TeamApiService {

    String TEAM_URL = "https://www.thesportsdb.com/api/v1/json/1/";

    @GET("search_all_teams.php")
    Call<TeamModel> getTeam(@Query("l")String team);


    String LEAGUE_URL = "https://www.thesportsdb.com/api/v1/json/1/";

    @GET("all_leagues.php")
    Call<LeagueModel> getLeague();


    String PAST_URL = "https://www.thesportsdb.com/api/v1/json/1/";

    @GET("eventspastleague.php")
    Call<LastMatches> getPast(@Query("id")String past);
}
