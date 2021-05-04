package io.giodude.basketballscore.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.giodude.basketballscore.Adapter.TeamIconAdapter;
import io.giodude.basketballscore.Model.TeamModel;
import io.giodude.basketballscore.Network.Connection;
import io.giodude.basketballscore.R;
import io.giodude.basketballscore.ViewModel.BasketBallViewModel;

public class HomeView extends Fragment {
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager rvLayout;
    private List<TeamModel.Team> teamsModel = new ArrayList<>();
    private BasketBallViewModel basketBallViewModel;
    private TeamIconAdapter teamIconAdapter;
    public ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    Connection connection;
    TextView c, sees, titles, descs;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home_view, container, false);
        progressBar = view.findViewById(R.id.progress);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        sees = view.findViewById(R.id.see);
        c = view.findViewById(R.id.click);
        connection = new Connection();
        if (connection.isConnected(getActivity())){
            showIcon();
        }


        sees.setOnClickListener(v -> {
            final Dialog myDialog;
            myDialog = new Dialog(getActivity());
            myDialog.setContentView(R.layout.details);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            titles = myDialog.findViewById(R.id.title);
            descs = myDialog.findViewById(R.id.desc);
            titles.setText("Basketball");
            descs.setText(" \t Basketball is a limited-contact sport played on a rectangular court. While most often played as a team sport with five players on each side, three-on-three, two-on-two, and one-on-one competitions are also common. \\t\n" +
                    "        The objective is to shoot a ball through a hoop 18 inches (46 cm) in diameter and 10 feet (3.048 m) high that is mounted to a backboard at each end of the court. \n\n" +
                    "        The game was invented in 1891 by Dr. James Naismith. A team can score a field goal by shooting the ball through the basket being defended by the opposition team during regular play. A field goal scores three points for the shooting team if the player shoots from behind the three-point line, and two points if shot from in front of the line.\t\n" +
                    "        A team can also score via free throws, which are worth one point, after the other team is assessed with certain fouls. The team with the most points at the end of the game wins, but additional time (overtime) is mandated when the score is tied at the end of regulation. The ball can be advanced on the court by passing it to a teammate, or by bouncing it while walking or running (dribbling). It is a violation to lift, or drag, ones pivot foot without dribbling the ball, to carry it, or to hold the ball with both hands then resume dribbling. \n\n" +
                    "        The game has many individual techniques for displaying skill—ball-handling, shooting, passing, dribbling, dunking, shot-blocking, and rebounding. Basketball teams generally have player positions, the tallest and strongest members of a team are called a center or power forward, while slightly shorter and more agile players are called small forward, and the shortest players or those who possess the best ball handling skills are called a point guard or shooting guard. The point guard directs the on court action of the team, implementing the coach game plan, and managing the execution of offensive and defensive plays (player positioning). \n\n" +
                    "        Basketball is one of the worlds most popular and widely viewed sports. The National Basketball Association (NBA) is the most significant professional basketball league in the world in terms of popularity, salaries, talent, and level of competition.Outside North America, the top clubs from national leagues qualify to continental championships such as the Euroleague and FIBA Americas League. The FIBA Basketball World Cup and Men Olympic Basketball Tournament are the major international events of the sport and attract top national teams from around the world. Each continent hosts regional competitions for national teams, like EuroBasket and FIBA AmeriCup. \n\n" +
                    "        The FIBA Womens Basketball World Cup and Womens Olympic Basketball Tournament feature top national teams from continental championships. The main North American league is the WNBA (NCAA Womens Division I Basketball Championship is also popular), whereas strongest European clubs participate in the EuroLeague Women.");
            myDialog.show();
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            showIcon();

        });
        return view;
    }

    private void getIcon(List<TeamModel.Team> icon) {
        recyclerView = view.findViewById(R.id.teamsRecyclerview);
        rvLayout = new GridLayoutManager(getActivity(), 2, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(rvLayout);
        teamIconAdapter = new TeamIconAdapter(getActivity(), icon);
        recyclerView.setAdapter(teamIconAdapter);
    }

    public void showIcon() {

        basketBallViewModel = ViewModelProviders.of(HomeView.this).get(BasketBallViewModel.class);
        basketBallViewModel.init();
        basketBallViewModel.getTeams().observe(this, teams -> {
            teamsModel.clear();
            if (teams == null) {
                progressBar.setVisibility(View.INVISIBLE);
                c.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Can't Connect!", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                c.setVisibility(View.GONE);
                getIcon(teams);
                teamsModel.addAll(teams);
                teamIconAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}