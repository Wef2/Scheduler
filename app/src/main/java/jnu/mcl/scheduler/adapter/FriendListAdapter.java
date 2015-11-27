package jnu.mcl.scheduler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.model.FriendModel;

/**
 * Created by Kim on 2015-11-27.
 */
public class FriendListAdapter extends ArrayAdapter<FriendModel> {

    public FriendListAdapter(Context context, ArrayList<FriendModel> friendModelList) {
        super(context, 0, friendModelList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        FriendModel friendModel = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.friend_item, parent, false);
        }
        TextView friendNameText = (TextView) view.findViewById(R.id.friendNameText);
        TextView friendDescriptionText = (TextView) view.findViewById(R.id.friendDescriptionText);
        friendNameText.setText(friendModel.getNickname());
        friendDescriptionText.setText(friendModel.getDescription());
        return view;
    }


}

