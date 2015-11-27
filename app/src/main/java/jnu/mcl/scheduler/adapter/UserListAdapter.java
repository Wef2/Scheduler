package jnu.mcl.scheduler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.model.UserModel;

/**
 * Created by Kim on 2015-11-27.
 */
public class UserListAdapter extends ArrayAdapter<UserModel> {

    public UserListAdapter(Context context, ArrayList<UserModel> userModelList) {
        super(context, 0, userModelList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        UserModel userModel = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.friend_item, parent, false);
        }
        TextView friendNameText = (TextView) view.findViewById(R.id.friendNameText);
        TextView friendDescriptionText = (TextView) view.findViewById(R.id.friendDescriptionText);
        friendNameText.setText(userModel.getNickname());
        friendDescriptionText.setText(userModel.getDescription());
        return view;
    }


}

