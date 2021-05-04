package io.giodude.basketballscore.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.giodude.basketballscore.Model.TeamModel;
import io.giodude.basketballscore.R;
import io.giodude.basketballscore.View.BBTeamView;

public class BBTeamAdapter extends RecyclerView.Adapter<BBTeamAdapter.ViewHolder> {
    Context context;
    private List<TeamModel.Team> data;
    private TeamModel.Team ee;
    TextView name, taon, laro, tdesc, sname, sdesc;
    ImageView jimg, timg, simg;
    public BBTeamAdapter(Context context, List<TeamModel.Team> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.teamitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(data.get(position));
        ee = data.get(position);
        holder.title.setText(ee.getStrTeam());
        holder.fb.setText(ee.getStrFacebook());
        holder.twitt.setText(ee.getStrTwitter());
        holder.inst.setText(ee.getStrInstagram());
        Picasso.get().load(ee.getStrTeamBadge()).into(holder.img);

        final Dialog myDialog;
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.teamclick);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        name = myDialog.findViewById(R.id.name);
        taon = myDialog.findViewById(R.id.year);
        laro = myDialog.findViewById(R.id.sport);
        tdesc = myDialog.findViewById(R.id.teamdesc);
        sname = myDialog.findViewById(R.id.stadiumname);
        sdesc = myDialog.findViewById(R.id.stadiumdesc);
        jimg = myDialog.findViewById(R.id.jerseyimg);
        timg = myDialog.findViewById(R.id.teamimg);
        simg = myDialog.findViewById(R.id.stadiumimg);
        for (int i = 0; i < data.size(); i++) {
            if (holder.title.getText() == data.get(position).getStrTeam()) {
                name.setText(data.get(position).getStrTeam());
                taon.setText(data.get(position).getIntFormedYear());
                laro.setText(data.get(position).getStrSport());
                tdesc.setText(data.get(position).getStrDescriptionEN());
                sname.setText(data.get(position).getStrStadium());
                sdesc.setText(data.get(position).getStrStadiumDescription());
                Picasso.get().load(data.get(position).getStrTeamJersey()).into(jimg);
                Picasso.get().load(data.get(position).getStrTeamLogo()).into(timg);
                Picasso.get().load(data.get(position).getStrStadiumThumb()).into(simg);
//                leagues.setText(ee.getLeague().getSlug());
//                season.setText(ee.getSeason().getName());
            }
        }

        holder.itemView.setOnClickListener(v -> myDialog.show());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,fb,inst,twitt;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fb = itemView.findViewById(R.id.fbb);
            inst = itemView.findViewById(R.id.insta);
            twitt = itemView.findViewById(R.id.twiiter);
            title = itemView.findViewById(R.id.teamname);
            img = itemView.findViewById(R.id.teamimg);
        }
    }
}
