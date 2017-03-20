package org.demo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.demo.filterpopview.FilterPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View viewById = findViewById(R.id.button2);
        final FilterPopup filterPopup = new FilterPopup(this);
        final List<String> leftLists = new ArrayList<>();
        final List<List<String>> rightLists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            leftLists.add("leftItem" + i);
            List<String> rightItem = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                rightItem.add("leftItem" + i + "    rightItem" + j);
            }
            rightLists.add(rightItem);
        }

        filterPopup.addOnOkClickListener(new FilterPopup.OnButtonClickListener() {
            @Override
            public void onSure(HashMap<Integer, Integer> selectResult) {

                for (int i = 0; i < selectResult.size(); i++) {
                    Log.e("onSure", leftLists.get(i) + "选的是" + rightLists.get(i).get(selectResult.get(i)));
                }
            }
        });
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopup.show(v);
            }
        });
        filterPopup.setData(leftLists, rightLists);
    }
}
