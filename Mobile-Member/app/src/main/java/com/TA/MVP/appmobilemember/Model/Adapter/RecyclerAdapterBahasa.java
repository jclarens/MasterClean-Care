package com.TA.MVP.appmobilemember.Model.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.Model.Basic.Language;
import com.TA.MVP.appmobilemember.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcla123ns on 23/07/17.
 */

public class RecyclerAdapterBahasa extends RecyclerView.Adapter<RecyclerAdapterBahasa.ViewHolder> {
    private List<Language> languages = new ArrayList<>();
    private List<Language> selectedlanguages = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        //objectview

        public ViewHolder(final View itemview) {
            super(itemview);
            checkBox = (CheckBox) itemview.findViewById(R.id.card_bahasa_item);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = getAdapterPosition();
                    if (checkBox.isChecked())
                        selectedlanguages.add(languages.get(position));
                    else selectedlanguages.remove(languages.get(position));
                }
            });
        }

    }

    @Override
    public RecyclerAdapterBahasa.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bahasa, parent, false);
        RecyclerAdapterBahasa.ViewHolder viewHolder = new RecyclerAdapterBahasa.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterBahasa.ViewHolder holder, int position) {
        //perubahan holder.
        holder.checkBox.setText(languages.get(position).getLanguage());
    }

    @Override
    public int getItemCount() {
        if (languages == null)
            return 0;
        return languages.size();
    }

    public void setlistbahasa(List<Language> languages) {
        this.languages = languages;
        notifyDataSetChanged();
    }
    public List<Language> getselectedlist(){
        return selectedlanguages;
    }
}