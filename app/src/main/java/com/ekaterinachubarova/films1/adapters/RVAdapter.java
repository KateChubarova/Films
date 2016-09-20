package com.ekaterinachubarova.films1.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ekaterinachubarova on 08.09.16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    private List <Film> films;
    public RVAdapter(List<Film> films, Context context){
        this.films = films;
        this.context = context;
    }
    private Context context;



    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item1, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.enName.setText(films.get(position).getNameEng());
        holder.year.setText(films.get(position).getPremiere());
        Picasso.with(context)
                .load(films.get(position).getImageUrl())
                .error(R.mipmap.ic_launcher)
                .into(holder.filmImage);
        holder.progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        
        TextView enName;
        ImageView filmImage;
        TextView year;
        ProgressBar progressBar;
        PersonViewHolder(View itemView) {
            super(itemView);
            enName = (TextView)itemView.findViewById(R.id.name);
            filmImage = (ImageView) itemView.findViewById(R.id.small_cover);
            year = (TextView) itemView.findViewById(R.id.year);
            progressBar = (ProgressBar) itemView.findViewById(R.id.cover_progress);
        }
    }
}