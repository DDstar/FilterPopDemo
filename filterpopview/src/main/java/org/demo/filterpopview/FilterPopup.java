package org.demo.filterpopview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by GuoJi on 2017/3/19.
 * 记一下思路
 * 左边需要保存的是
 * 1 当前选中值
 * 2 右边的选中位置
 * 3 点击更新右边的列表和左边选中位置值
 * 右边需要记住的是
 * 1 自己是否被选中
 * 2 左边的位置值
 * 3 点击更新右边的当前item的选中位置
 */

public class FilterPopup {
    private Context context;
    private RecyclerView leftRcyView;
    private RecyclerView rightRcyView;
    private PopupWindow popupWindow;
    private ArrayList<DataBean.DataLeft> leftLists = new ArrayList<>();
    private ArrayList<ArrayList<DataBean.DataRight>> rightLists = new ArrayList<>();
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private HashMap<Integer, Integer> mResultMap = new HashMap<>();
    private View layout;
    private boolean isInitData;

    public FilterPopup(@NonNull Context context) {
        this.context = context;
        init();
    }

    private void init() {
        layout = LayoutInflater.from(context).inflate(R.layout.filter_dialog, null);
        popupWindow = new PopupWindow(layout, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT, true);
        leftRcyView = (RecyclerView) layout.findViewById(R.id.list_left);
        rightRcyView = (RecyclerView) layout.findViewById(R.id.list_right);
        leftRcyView.setLayoutManager(new LinearLayoutManager(context));
        rightRcyView.setLayoutManager(new LinearLayoutManager(context));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(context.getDrawable(R.drawable.round_bg));
//        initData();
    }

    public void addOnOkClickListener(final OnButtonClickListener buttonClickListener) {
        layout.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重置左边
                for (int i = 0; i < leftLists.size(); i++) {
                    mResultMap.put(i, 0);
                    if (i == 0) {
                        leftLists.get(i).setSelected(true);
                    } else {
                        leftLists.get(i).setSelected(false);
                    }
                    leftRcyView.scrollToPosition(0);
                }
                //重置右边
                for (int i = 0; i < rightLists.size(); i++) {
                    ArrayList<DataBean.DataRight> dataRights = rightLists.get(i);
                    for (int j = 0; j < dataRights.size(); j++) {
                        dataRights.get(j).setSelected(false);
                    }
                    dataRights.get(0).setSelected(true);
                }
                rightRcyView.scrollToPosition(0);
                leftAdapter.setLeftDataList(leftLists);
                rightAdapter.setRightDataList(rightLists.get(0));
            }
        });
        layout.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                buttonClickListener.onSure(mResultMap);
            }
        });
    }

    public void setData(List<String> leftDatas, List<List<String>> rightDatas) {
        isInitData = true;
        if (leftDatas == null || leftDatas.size() <= 0 || rightDatas == null || rightDatas.size() <= 0) {
            throw new IllegalArgumentException("参数不能为空或者为0");
        }
        mResultMap.clear();
        leftLists.clear();
        rightLists.clear();
        leftLists.add(new DataBean.DataLeft(leftDatas.get(0), true, 0));
        if (leftDatas.size() > 1) {
            for (int i = 1; i < leftDatas.size(); i++) {
                leftLists.add(new DataBean.DataLeft(leftDatas.get(i), false, 0));
            }
        }

        for (int i = 0; i < leftLists.size(); i++) {
            List<String> rightItems = rightDatas.get(i);
            mResultMap.put(i, 0);
            ArrayList<DataBean.DataRight> rightItemList = new ArrayList<>();
            rightItemList.add(new DataBean.DataRight(rightItems.get(0), true));
            if (rightDatas.size() > 1) {
                for (int j = 1; j < rightItems.size(); j++) {
                    rightItemList.add(new DataBean.DataRight(rightItems.get(j), false));
                }
            }
            rightLists.add(rightItemList);
        }
        initData();
        leftAdapter.setLeftDataList(leftLists);
        rightAdapter.setRightDataList(rightLists.get(0));
        leftRcyView.scrollToPosition(0);
        rightRcyView.scrollToPosition(0);
    }

    public void show(View parent, int x, int y) {
        if (!isInitData) {
            throw new IllegalStateException("请先调用setData方法设置显示数据");
        }
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, x, y);
    }

    public void show(View anchor) {
        if (!isInitData) {
            throw new IllegalStateException("请先调用setData方法设置显示数据");
        }
        popupWindow.showAsDropDown(anchor);
    }

    private void initData() {
        isInitData = true;
//        leftLists = new ArrayList<>();
//        leftLists.add(new DataBean.DataLeft("item = " + 0, true, 0));
//        for (int i = 1; i < 20; i++) {
//            leftLists.add(new DataBean.DataLeft("item = " + i, false, 0));
//        }
//        rightLists = new ArrayList<>();
//        for (int i = 0; i < leftLists.size(); i++) {
//            mResultMap.put(i, 0);
//            ArrayList<DataBean.DataRight> rightItemList = new ArrayList<>();
//            rightItemList.add(new DataBean.DataRight("item = " + 0, true));
//            for (int j = 1; j < 20; j++) {
//                rightItemList.add(new DataBean.DataRight("item = " + j, false));
//            }
//            rightLists.add(rightItemList);
//        }
        leftAdapter = new LeftAdapter(context, leftLists);
        leftRcyView.setAdapter(leftAdapter);
        rightAdapter = new RightAdapter(context, rightLists.get(0));
        rightRcyView.setAdapter(rightAdapter);
        leftRcyView.addOnItemTouchListener(new RcyItemClickerListener(leftRcyView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int pos = vh.getLayoutPosition();
                //左边修正
                for (int i = 0; i < leftLists.size(); i++) {
                    DataBean.DataLeft dataLeft = leftLists.get(i);
                    if (i == pos) {
                        dataLeft.setSelected(true);
                    } else {
                        dataLeft.setSelected(false);
                    }
                }
                leftAdapter.setLeftDataList(leftLists);
                leftAdapter.setCurPos(pos);
                //右边更新
                rightAdapter.setRightDataList(rightLists.get(pos));
                ArrayList<DataBean.DataRight> dataRights = rightLists.get(leftAdapter.getCurPos());
                for (int i = 0; i < dataRights.size(); i++) {
                    DataBean.DataRight dataRight = dataRights.get(i);
                    if (dataRight.isSelected) {
                        rightRcyView.scrollToPosition(i);
                        break;
                    }
                }
            }
        });
        rightRcyView.addOnItemTouchListener(new RcyItemClickerListener(rightRcyView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int pos = vh.getLayoutPosition();
                ArrayList<DataBean.DataRight> dataRights = rightLists.get(leftAdapter.getCurPos());
                for (int i = 0; i < dataRights.size(); i++) {
                    DataBean.DataRight dataRight = dataRights.get(i);
                    if (i == pos) {
                        dataRight.setSelected(true);
                    } else {
                        dataRight.setSelected(false);
                    }
                }
                mResultMap.put(leftAdapter.getCurPos(), pos);
                rightAdapter.setRightDataList(dataRights);//更新右边
                leftLists.get(leftAdapter.getCurPos()).setRightSelectedPos(pos);
            }
        });
    }

    public interface OnButtonClickListener {
        //key值为左边的列表的pos
        //value值为右边的列表的选中pos
        void onSure(HashMap<Integer, Integer> selectResult);
    }
}
