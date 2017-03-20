#### 一个类似前端的筛选控件FilterPopView
先来一张效果图
主要是比较奇葩的需求
使用方法
* 下载并依赖一下这个module

```
compile project(':filterpopview')
```
* 初始化

```
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
```
* 设置监听

```
filterPopup.addOnOkClickListener(new FilterPopup.OnButtonClickListener() {
            @Override
            public void onSure(HashMap<Integer, Integer> selectResult) {
                for (int i = 0; i < selectResult.size(); i++) {
                    mTextMessage.append("\n" + leftLists.get(i) + "选的是" + rightLists.get(i).get(selectResult.get(i)));
                }
            }
        });

```
* 设置数据源

```
filterPopup.setData(leftLists, rightLists);
```
#### 一丢丢说明
* 数据源设置
这个数据源的设置呢，只要传列表需要显示的数据就可以了，不用将整个数据都传进去，我里面对数据进行了再次重新调整，传进去的两个List一个是左边列表的数据显示，一个是右边多个列表的显示，因为右边是要与左边对应的，所以右边的数据需要这么添加,它是有两级的

```
        final List<List<String>> rightLists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> rightItem = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                rightItem.add("leftItem" + i + "    rightItem" + j);
            }
            rightLists.add(rightItem);
        }
```
* 回调的监听
选择后返回的结果是一个map，key是左边的列表的position，value是该左侧的position所对应的右侧列表选中的position，拿数据要用后台返回的数据，而不用我们自己拼装的数据

```
filterPopup.addOnOkClickListener(new FilterPopup.OnButtonClickListener() {
            @Override
            public void onSure(HashMap<Integer, Integer> selectResult) {
//还有为什么我这个是map却可以像arraylist那样拿数据，因为我的key是integer，而且在put进去的时候也是用i = 0开始的，所以不会为不存在的情况
                for (int i = 0; i < selectResult.size(); i++) {
                  //这边就可以根据左右两侧的position还有后台真正给我们的数据进行数据的获取，我此处只是简单的Demo，就没有那样子处理了
                    mTextMessage.append("\n" + leftLists.get(i) + "选的是" + rightLists.get(i).get(selectResult.get(i)));
                }
            }
        });
```
