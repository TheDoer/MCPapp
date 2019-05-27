package zw.co.munaticommunications.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zw.co.munaticommunications.R;
import zw.co.munaticommunications.model.Message;

public class MessagesRecyclerviewAdapter extends RecyclerView.Adapter<MessagesRecyclerviewAdapter.MessageViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<Message> messages;

    public MessagesRecyclerviewAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.message,parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int position) {
        final Message message = messages.get(position);
        messageViewHolder.textViewMessage.setText(message.getMessage());
        messageViewHolder.textViewDate.setText(message.getDate());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage, textViewDate;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textView_message);
            textViewDate = itemView.findViewById(R.id.textView_date);
        }
    }
}
