package com.dfirago.jarvissmarthome.hub.list.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dfirago.jarvissmarthome.R;
import com.dfirago.jarvissmarthome.databinding.ListItemHubBinding;
import com.dfirago.jarvissmarthome.hub.list.HubListPresenter;
import com.dfirago.jarvissmarthome.hub.list.model.HubModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfi on 01/05/2017.
 */

public class HubListAdapter extends RecyclerView.Adapter<HubListAdapter.HubViewHolder> {

    private final List<HubModel> data = new ArrayList<>();

    private final HubListPresenter presenter;

    public HubListAdapter(HubListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public HubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final ListItemHubBinding binding = DataBindingUtil
                .inflate(layoutInflater, R.layout.list_item_hub, parent, false);
        return new HubViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(HubViewHolder holder, int position) {
        HubModel hubModel = data.get(position);
        holder.binding.setHub(hubModel);
        holder.binding.setPresenter(presenter);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<HubModel> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    public static class HubViewHolder extends RecyclerView.ViewHolder {

        private ListItemHubBinding binding;

        public HubViewHolder(ListItemHubBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}