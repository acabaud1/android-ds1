package com.ynov.aca.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.aca.R;
import com.ynov.aca.model.Entree;
import com.ynov.aca.service.ImageService;

import java.util.List;

public class EntryAdapter extends ArrayAdapter<Entree> {

    private LayoutInflater inflater;

    public EntryAdapter(@NonNull Context context, int resource, @NonNull List<Entree> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        EntryViewHolder viewHolder;
        Entree entry = getItem(position);
//
        try {
          if(convertView == null) {
                convertView = inflater.inflate(R.layout.row_entry, parent, false);
          }

//                viewHolder = new EntryViewHolder();
//                viewHolder.nom = (TextView) convertView.findViewById(R.id.entryName);
//                viewHolder.espece = (TextView) convertView.findViewById(R.id.entrySpecie);
//                viewHolder.image = (ImageView) convertView.findViewById(R.id.entryImage);
//
//                convertView.setTag(viewHolder);
//
//            } else {
//                viewHolder = (EntryViewHolder) convertView.getTag();
//            }
//
//            viewHolder.nom.setText(entry.getNom());
//            viewHolder.espece.setText(entry.getEspece());

            ImageView imageView = convertView.findViewById(R.id.entryImage);

            ImageService imageService = new ImageService(imageView);
            imageService.execute("http://thibault01.com:8081/images/" + entry.getId() + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    private class EntryViewHolder {
        TextView nom;
        TextView espece;
        ImageView image;
    }

}
