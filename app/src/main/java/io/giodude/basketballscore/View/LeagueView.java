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

import io.giodude.basketballscore.Adapter.BBLeagueAdapter;
import io.giodude.basketballscore.Model.LeagueModel;
import io.giodude.basketballscore.Network.Connection;
import io.giodude.basketballscore.R;
import io.giodude.basketballscore.ViewModel.BasketBallViewModel;

public class LeagueView extends Fragment {
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager rvLayout;
    private BBLeagueAdapter leagueAdapter;
    private List<LeagueModel.League> leagueModel = new ArrayList<>();
    private BasketBallViewModel basketBallViewModel;
    public ProgressBar progressBar;
    Connection connection;
    private SwipeRefreshLayout swipeRefreshLayout;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_league_view, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        progressBar = view.findViewById(R.id.progress);
        connection = new Connection();
        if (connection.isConnected(getActivity())){
            showLeague();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            showLeague();

        });
        return view;
    }

    private void getLeaguess(List<LeagueModel.League> league) {
        recyclerView = view.findViewById(R.id.leagueRecyclerview);
        rvLayout = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(rvLayout);
        leagueAdapter = new BBLeagueAdapter(getActivity(), league);
        recyclerView.setAdapter(leagueAdapter);
    }

    private void showLeague() {
        basketBallViewModel = ViewModelProviders.of(LeagueView.this).get(BasketBallViewModel.class);
        basketBallViewModel.initLeague();
        basketBallViewModel.getLeague().observe(this, leagues -> {
            leagueModel.clear();
            if (leagues == null) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Can't Connect!", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            } else {
                if (leagues.size() == 0){
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    getLeaguess(leagues);
                    leagueModel.addAll(leagues);
                    leagueAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}