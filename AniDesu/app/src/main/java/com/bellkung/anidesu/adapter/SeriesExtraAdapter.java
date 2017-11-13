package com.bellkung.anidesu.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.CharactersSmall;
import com.bellkung.anidesu.api.model.StaffSmall;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BellKunG on 11/11/2017 AD.
 */

public class SeriesExtraAdapter extends RecyclerView.Adapter<SeriesExtraAdapter.ViewHolder> {

    private ArrayList<StaffSmall> staffs;
    private ArrayList<CharactersSmall> characters;
    private String mAction;
    private Activity mActivity;

    public SeriesExtraAdapter(Activity activity, String action) {
        this.mActivity = activity;
        this.mAction = action;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_series_extra, null);

        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (this.mAction) {
            case KeyUtils.GET_CHARACTERS:
                CharactersSmall character = this.characters.get(position);
                Glide.with(this.mActivity).load(character.getImage_url_lge()).into(holder.coverImageView);
                holder.ex_nameTextView.setText(character.getFull_Name());
                holder.ex_role_TextView.setText(character.getRole());
                break;

            case KeyUtils.GET_STAFFS:
                StaffSmall staff = this.staffs.get(position);
                Glide.with(this.mActivity).load(staff.getImage_url_lge()).into(holder.coverImageView);
                holder.ex_nameTextView.setText(staff.getFull_Name());
                holder.ex_role_TextView.setText(staff.getRole());
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (this.mAction.equals(KeyUtils.GET_CHARACTERS)) {
            return this.characters.size();
        }
        return this.staffs.size();
    }


    public void setStaffs(ArrayList<StaffSmall> staffs) {
        this.staffs = staffs;
    }

    public void setCharacters(ArrayList<CharactersSmall> characters) {
        this.characters = characters;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.coverImageView) ImageView coverImageView;
        @BindView(R.id.ex_nameTextView) TextView ex_nameTextView;
        @BindView(R.id.ex_role_TextView) TextView ex_role_TextView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
