package com.kaleido.cesmarttracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.data.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Suleiman on 14-04-2015.
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.VersionViewHolder> {
    List<Event> events;
    Context context;
    OnItemClickListener clickListener;
    View view;
    View sectionView;

    public InboxAdapter(List<Event> events) {
        this.events = events;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inbox_layout, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        if(events.get(i).isRead()) {
            versionViewHolder.inboxLayout.setBackgroundColor(Color.parseColor("#efefee"));
            if(events.get(i).getType().contentEquals("Assignment")){
                versionViewHolder.iconInbox.setImageResource(R.drawable.ic_read_assignment);
            } else if (events.get(i).getType().contentEquals("Message")){
                versionViewHolder.iconInbox.setImageResource(R.drawable.ic_read_msg);
            } else if (events.get(i).getType().contentEquals("Result")){
                versionViewHolder.iconInbox.setImageResource(R.drawable.ic_read_grade);
            }

        } else {
            versionViewHolder.inboxLayout.setBackgroundColor(Color.WHITE);
            if(events.get(i).getType().contentEquals("Assignment")){
                versionViewHolder.iconInbox.setImageResource(R.drawable.ic_unread_assignment);
            } else if (events.get(i).getType().contentEquals("Message")){
                versionViewHolder.iconInbox.setImageResource(R.drawable.ic_unread_msg);
            } else if (events.get(i).getType().contentEquals("Result")){
                versionViewHolder.iconInbox.setImageResource(R.drawable.ic_unread_grade);
            }
        }
        versionViewHolder.inboxTitle.setText(events.get(i).getCourseName());
        versionViewHolder.inboxContent.setText(events.get(i).getTitle());
        String todayDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String announcedDate;
        if(events.get(i).getAnnounceDate().contentEquals(todayDate)){
            announcedDate = "TODAY";
        }else{
            announcedDate = events.get(i).getAnnounceDate();
        }
        versionViewHolder.announcedDate.setText(announcedDate);
    }

    @Override
    public int getItemCount() {
        return events == null ? 0 : events.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout inboxLayout;
        TextView inboxTitle;
        TextView inboxContent;
        TextView announcedDate;
        ImageView iconInbox;

        public VersionViewHolder(View itemView) {
            super(itemView);
            inboxLayout = (RelativeLayout) itemView.findViewById(R.id.InboxLayout);
            iconInbox = (ImageView) itemView.findViewById(R.id.icon_inbox);
            inboxTitle = (TextView) itemView.findViewById(R.id.inboxtitle);
            inboxContent = (TextView) itemView.findViewById(R.id.inboxcontent);
            announcedDate = (TextView) itemView.findViewById(R.id.announce_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}