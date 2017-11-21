package com.bellkung.anidesu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.ExternalLinks;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BellKunG on 10/11/2017 AD.
 */

public class ExternalLinkAdapter extends RecyclerView.Adapter<ExternalLinkAdapter.ViewHolder> {

    private ArrayList<ExternalLinks> allExtLinks;
    private Activity mActivity;
    private Context mContext;

    public ExternalLinkAdapter(Activity mActivity, Context mContext) {
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_external_lists, null);

        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ExternalLinks externalLinks = this.allExtLinks.get(position);
        holder.linksBtn.setText(externalLinks.getSite());
    }

    public void setAllExtLinks(ArrayList<ExternalLinks> allExtLinks) {
        this.allExtLinks = allExtLinks;
    }

    @Override
    public int getItemCount() {
        return this.allExtLinks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.linksBtn) Button linksBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            linksBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ExternalLinks externalLinks = allExtLinks.get(getAdapterPosition());
            Uri uri = Uri.parse(externalLinks.getUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        }
    }


}
