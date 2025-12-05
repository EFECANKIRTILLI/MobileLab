
package com.example.projeadi; 

import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


import com.example.projeadi.NoteFragment.OnNoteListInteractionListener;

public class MyNoteRecyclerViewAdapter extends
        RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder> {

    private final List<Note> mValues;
    private final OnNoteListInteractionListener mListener;

    public MyNoteRecyclerViewAdapter(List<Note> notes,
                                     NoteFragment.OnNoteListInteractionListener listener) {
        mValues = notes;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        
        holder.mItem = mValues.get(position);
        
        
        holder.mHeaderView.setText(mValues.get(position).getHeader());

        
        holder.mDateView.setText((new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())).
                format(mValues.get(position).getDate()));

        
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    /
                    mListener.onNoteSelected(holder.mItem);
                }
            }
        });

        
        if(position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFE0")); 
        }
        else{
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mHeaderView;
        public final TextView mDateView;
        public Note mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            
            mHeaderView = view.findViewById(R.id.note_header);
            mDateView = view.findViewById(R.id.note_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mHeaderView.getText() + "'";
        }
    }
}