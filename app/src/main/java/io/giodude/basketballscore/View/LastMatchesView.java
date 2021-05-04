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

import io.giodude.basketballscore.Adapter.LastMatchesAdapter;
import io.giodude.basketballscore.Model.LastMatches;
import io.giodude.basketballscore.Network.Connection;
import io.giodude.basketballscore.R;
import io.giodude.basketballscore.ViewModel.BasketBallViewModel;

public class LastMatchesView extends Fragment {
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager rvLayout;
    private LastMatchesAdapter lastMatchesAdapter;
    private BasketBallViewModel basketBallViewModel;
    private List<LastMatches.Event> lastModel = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    public ProgressBar progressBar;
    Connection connection;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_last_matches_view,container,false);
        progressBar = view.findViewById(R.id.progress);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        connection = new Connection();
        if (connection.isConnected(getActivity())){
            showPast();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            showPast();

        });
        return view;
    }

    private void getPast(List<LastMatches.Event> past) {
        recyclerView = view.findViewById(R.id.lastRecyclerview);
        rvLayout = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(rvLayout);
        lastMatchesAdapter = new LastMatchesAdapter(getActivity(), past);
        recyclerView.setAdapter(lastMatchesAdapter);
    }

    private void showPast() {
        basketBallViewModel = ViewModelProviders.of(LastMatchesView.this).get(BasketBallViewModel.class);
        basketBallViewModel.initPast();
        basketBallViewModel.getPast().observe(this, events -> {
            lastModel.clear();
            if(events == null)
            {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Can't Connect!", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }else {
                if (events.size() == 0){
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    getPast(events);
                    lastModel.addAll(events);
                    lastMatchesAdapter.notifyDataSetChanged();
                }
            }
            });

    }

}