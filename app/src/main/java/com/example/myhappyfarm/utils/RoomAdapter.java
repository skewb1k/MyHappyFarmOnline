package com.example.myhappyfarm.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.myhappyfarm.R;
import com.example.myhappyfarm.activities.RoomsActivity;

import java.util.ArrayList;

public class RoomAdapter extends ArrayAdapter<Room> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Room> roomsList;
    private Context context;

    public RoomAdapter(Context context, int resource, ArrayList<Room> rooms) {
        super(context, resource, rooms);
        this.roomsList = rooms;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Room room = roomsList.get(position);

        viewHolder.players.setText(String.valueOf(room.getPlayers()).replace("[", "").replace("]", ""));

        viewHolder.joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof RoomsActivity) {
                    ((RoomsActivity) context).joinClicked(room.getId());
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        final Button joinButton;
        final TextView players;

        ViewHolder(View view) {
            joinButton = view.findViewById(R.id.joinButton);
            players = view.findViewById(R.id.players);
        }
    }
}
