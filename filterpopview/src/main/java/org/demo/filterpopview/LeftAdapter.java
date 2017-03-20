package org.demo.filterpopview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.LeftHolder> {
    private Context context;
    private List<DataBean.DataLeft> leftDataList;
    private int curPos;

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
    }

    public LeftAdapter(Context context, List<DataBean.DataLeft> leftDataList) {
        this.context = context;
        this.leftDataList = leftDataList;
    }

    public void setLeftDataList(List<DataBean.DataLeft> leftDataList) {
        this.leftDataList = leftDataList;
        this.notifyDataSetChanged();
    }

    @Override
    public LeftHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LeftHolder(LayoutInflater.from(context).inflate(R.layout.left_item, null));
    }

    @Override
    public void onBindViewHolder(LeftHolder holder, int position) {
        DataBean.DataLeft dataLeft = leftDataList.get(position);
        holder.tv_item.setText(dataLeft.getName());
        if (dataLeft.isSelected) {
            holder.tv_item.setBackgroundColor(0xff336644);
        } else {
            holder.tv_item.setBackgroundColor(0xffffffff);
        }
    }

    @Override
    public int getItemCount() {
        return leftDataList.size();
    }

    class LeftHolder extends RecyclerView.ViewHolder {
        TextView tv_item;

        public LeftHolder(View itemView) {
            super(itemView);
            tv_item = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}