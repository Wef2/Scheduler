package jnu.mcl.scheduler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.model.UserModel;

/**
 * Created by Kim on 2015-11-27.
 */
public class UserListAdapter extends BaseAdapter {

    private ArrayList<UserModel> userModels;
    private LayoutInflater inflater;

    public UserListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.userModels = new ArrayList<>();
    }

    public void changeList(Collection<UserModel> newUserModels) {
        this.userModels.clear();
        this.userModels.addAll(newUserModels);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userModels.size();
    }

    @Override
    public UserModel getItem(int position) {
        return userModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflateIfRequired(view, position, parent);
        bind(getItem(position), view);
        return view;
    }

    private void bind(UserModel userModel, View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.friendNameText.setText(userModel.getNickname());
        viewHolder.friendDescriptionText.setText(userModel.getDescription());
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.friend_item, null);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }

    static class ViewHolder {
        final TextView friendNameText;
        final TextView friendDescriptionText;

        ViewHolder(View view) {
            friendNameText = (TextView) view.findViewById(R.id.friendNameText);
            friendDescriptionText = (TextView) view.findViewById(R.id.friendDescriptionText);
        }
    }

}

