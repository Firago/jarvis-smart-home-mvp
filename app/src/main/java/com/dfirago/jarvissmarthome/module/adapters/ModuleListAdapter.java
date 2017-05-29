package com.dfirago.jarvissmarthome.module.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dfirago.jarvissmarthome.R;
import com.dfirago.jarvissmarthome.databinding.ListItemModuleBinding;
import com.dfirago.jarvissmarthome.module.ModuleListPresenter;
import com.dfirago.jarvissmarthome.module.model.ModuleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfi on 30/10/2016.
 */

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ModuleViewHolder> {

    private final List<ModuleModel> data = new ArrayList<>();

    private final ModuleListPresenter presenter;

    public ModuleListAdapter(ModuleListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ModuleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final ListItemModuleBinding binding = DataBindingUtil
                .inflate(layoutInflater, R.layout.list_item_module, parent, false);
        return new ModuleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ModuleViewHolder holder, int position) {
        ModuleModel module = data.get(position);
        holder.binding.setModule(module);
        holder.binding.setPresenter(presenter);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ModuleModel> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {

        private ListItemModuleBinding binding;

        public ModuleViewHolder(ListItemModuleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}