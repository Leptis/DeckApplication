package com.example.deckapplication.norration.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deckapplication.R;

import java.util.ArrayList;

public class NorrationAdapter extends RecyclerView.Adapter<NorrationAdapter.NorrationViewHolder> {

    class NorrationViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        final int MENU_MODIFY = 0;
        final int MENU_DELETE = 1;
        TextView norration_name;

        public NorrationViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            norration_name = itemView.findViewById(R.id.norration_item_name);
        }

        void bind(int ind){
            norration_name.setText(nor_files.get(ind));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, MENU_MODIFY, 0, "Modify");
            menu.add(0, MENU_DELETE, 0, "Delete");
            context_menu_item_position = getAdapterPosition();
        }
    }

    public int context_menu_item_position = -1;

    public ArrayList<String> nor_files;

    public NorrationAdapter(ArrayList<String> new_nor_files){
        nor_files = new_nor_files;
    }

    @NonNull
    @Override
    public NorrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.norration_list_item, parent, false);



        NorrationViewHolder view_holder = new NorrationViewHolder(view);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NorrationViewHolder holder, int i) {
        holder.bind(i);
    }

    @Override
    public int getItemCount() {
        return nor_files.size();
    }

    public void removeItem(int ind){
        nor_files.remove(ind);
        notifyDataSetChanged();
    }

    public void refreshList(ArrayList<String> new_nor_files){
        nor_files = new_nor_files;
        notifyDataSetChanged();
    }



}
