package io.giodude.basketballscore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.giodude.basketballscore.Model.LastMatches;
import io.giodude.basketballscore.R;

public class LastMatchesAdapter extends RecyclerView.Adapter<LastMatchesAdapter.ViewHolder> {
    Context context;
    public List<LastMatches.Event> data;
    public LastMatches.Event ee;

    public LastMatchesAdapter(Context context, List<LastMatches.Event> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.lastitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));
        ee = data.get(position);
        holder.home.setText(ee.getStrHomeTeam());
        holder.away.setText(ee.getStrAwayTeam());

        if (ee.getIntHomeScore() == null){
            holder.hs.setText("0");
        }else {
            holder.hs.setText(ee.getIntHomeScore().toString());
        }

        if (ee.getIntAwayScore() == null){
            holder.as.setText("0");
        }else {
            holder.as.setText(ee.getIntAwayScore().toString());
        }

        if (ee.getStrThumb() == null){
            holder.img.setImageResource(R.drawable.adapbg);
        }else {
            Picasso.get().load(ee.getStrThumb()).into(holder.img, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    holder.img.setImageResource(R.drawable.ic_launcher_background);
                    holder.progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView home,hs,away,as;
        ImageView img;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
            home = itemView.findViewById(R.id.home);
            hs = itemView.findViewById(R.id.homescore);
            as = itemView.findViewById(R.id.awayscore);
            away = itemView.findViewById(R.id.away);
            img = itemView.findViewById(R.id.image);

        }
    }
}
