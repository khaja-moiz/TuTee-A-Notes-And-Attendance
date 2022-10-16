package com.jpnce.tutee.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jpnce.tutee.Model.NotesModel;
import com.jpnce.tutee.R;

import java.util.ArrayList;

public class viewNotesAdapter extends RecyclerView.Adapter<viewNotesAdapter.viewholder> {
    Context context;
    ArrayList<NotesModel> viewNotes;
    String studentName = "";

    public viewNotesAdapter(Context context, ArrayList<NotesModel> viewNotes ,String studentName) {
        this.context = context;
        this.viewNotes = viewNotes;
        this.studentName = studentName;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        NotesModel notes = viewNotes.get(position);
        Log.d("TAG1", "onBindViewHolder: "  + notes);
        holder.textView.setText(notes.getName());
    }

    @Override
    public int getItemCount() {
        return viewNotes.size();
    }
    class viewholder extends RecyclerView.ViewHolder {

        TextView textView;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textview);
        }
    }
}