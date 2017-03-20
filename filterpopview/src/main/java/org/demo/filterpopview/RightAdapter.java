package org.demo.filterpopview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.RightHolder> {
    private Context context;
    private ArrayList<DataBean.DataRight> rightDataList;

    public RightAdapter(Context context, ArrayList<DataBean.DataRight> rightDataList) {
        this.context = context;
        this.rightDataList = rightDataList;
    }

    public void setRightDataList(ArrayList<DataBean.DataRight> rightDataList) {
        this.rightDataList = rightDataList;
        this.notifyDataSetChanged();
    }

    @Override
    public RightHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RightHolder(LayoutInflater.from(context).inflate(R.layout.right_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RightHolder holder, int position) {
        DataBean.DataRight dataRight = rightDataList.get(position);
        holder.cb_item.setText(dataRight.getName());
        holder.cb_item.setChecked(dataRight.isSelected);
    }

    @Override
    public int getItemCount() {
        return rightDataList.size();
    }

    class RightHolder extends RecyclerView.ViewHolder {
        CheckBox cb_item;

        public RightHolder(View itemView) {
            super(itemView);
            cb_item = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }
}