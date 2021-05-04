package io.giodude.basketballscore.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.giodude.basketballscore.Model.TeamModel;
import io.giodude.basketballscore.R;

public class TeamIconAdapter  extends RecyclerView.Adapter<TeamIconAdapter.ViewHolder> {
    Context context;
    private List<TeamModel.Team> data;
    private TeamModel.Team ee;
    TextView name;
    ImageView img;
    public TeamIconAdapter(Context context, List<TeamModel.Team> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.teamiconitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));
        ee = data.get(position);
        holder.name.setText(ee.getStrTeam());

            Picasso.get().load(ee.getStrTeamBadge()).into(holder.img, new Callback() {
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


        final Dialog myDialog;
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.iconclick);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        name = myDialog.findViewById(R.id.title);
        img = myDialog.findViewById(R.id.jersey);
        for (int i = 0; i < data.size(); i++) {
            if (holder.name.getText() == data.get(position).getStrTeam()) {
                name.setText(data.get(position).getStrTeam());
                if (ee.getStrTeamJersey() == null){
                    Picasso.get().load(R.drawable.jersey).into(img);
                }else {
                    Picasso.get().load(ee.getStrTeamJersey()).into(img);
                }
            }
        }
        holder.itemView.setOnClickListener(v -> myDialog.show());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id);
            img = itemView.findViewById(R.id.teamicon);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
        }
    }
}
