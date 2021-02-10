package com.example.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class chatAdapter extends FirebaseRecyclerAdapter<chat, chatAdapter.chatViewHolder>{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public chatAdapter(@NonNull FirebaseRecyclerOptions<chat> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull chatViewHolder holder, int position, @NonNull chat model) {
      holder.tvname.setText(model.getName());
      holder.tvmessage.setText(model.getChat());
    }

    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatview, parent, false);

        return new chatViewHolder(view);
    }

    public class chatViewHolder extends RecyclerView.ViewHolder {

        TextView tvname, tvmessage;

        public chatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvname);
            tvmessage = itemView.findViewById(R.id.tvmessage);

        }
    }
}


