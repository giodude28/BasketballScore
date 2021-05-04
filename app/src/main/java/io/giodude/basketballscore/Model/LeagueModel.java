package io.giodude.basketballscore.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeagueModel {

    @SerializedName("leagues")
    @Expose
    private List<League> leagues = null;

    public class League{
        @SerializedName("idLeague")
        @Expose
        private String idLeague;
        @SerializedName("strLeague")
        @Expose
        private String strLeague;
        @SerializedName("strSport")
        @Expose
        private String strSport;
        @SerializedName("strLeagueAlternate")
        @Expose
        private String strLeagueAlternate;

        public League(String idLeague, String strLeague, String strSport, String strLeagueAlternate) {
            this.idLeague = idLeague;
            this.strLeague = strLeague;
            this.strSport = strSport;
            this.strLeagueAlternate = strLeagueAlternate;
        }

        public String getIdLeague() {
            return idLeague;
        }

        public String getStrLeague() {
            return strLeague;
        }

        public String getStrSport() {
            return strSport;
        }

        public String getStrLeagueAlternate() {
            return strLeagueAlternate;
        }
    }

    public List<League> getLeagues() {
        return leagues;
    }
}
