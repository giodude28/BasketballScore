package io.giodude.basketballscore.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.giodude.basketballscore.Adapter.BBTeamAdapter;
import io.giodude.basketballscore.Model.TeamModel;
import io.giodude.basketballscore.Network.Connection;
import io.giodude.basketballscore.R;
import io.giodude.basketballscore.ViewModel.BasketBallViewModel;

public class BBTeamView extends Fragment {
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager rv;
    private List<TeamModel.Team> teamModel = new ArrayList<>();
    private BBTeamAdapter bbTeamAdapter;
    private BasketBallViewModel basketBallViewModel;
    public ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    Connection connection;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_b_b_team_view, container, false);
        progressBar = view.findViewById(R.id.progress);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        recyclerView = view.findViewById(R.id.bbRecyclerview);
        rv = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(rv);
        connection = new Connection();
        if (connection.isConnected(getActivity())){
            showBBteam();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            showBBteam();

        });
        return view;
    }


    private void showBBteam() {
        basketBallViewModel = ViewModelProviders.of(BBTeamView.this).get(BasketBallViewModel.class);
        basketBallViewModel.init();
        basketBallViewModel.getTeams().observe(this, teams -> {
            teamModel.clear();
            if (teams == null) {
                progressBar.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Can't Connect!", Toast.LENGTH_SHORT).show();
            } else {
                if (teams.size() == 0){
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    bbTeamAdapter = new BBTeamAdapter(getActivity(), teams);
                    recyclerView.setAdapter(bbTeamAdapter);
                    teamModel.addAll(teams);
                    bbTeamAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}