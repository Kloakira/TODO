package com.njupt.b16070706.todo.Todo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.njupt.b16070706.todo.Data.DateUtil;
import com.njupt.b16070706.todo.Data.GlobalUtil;

import java.util.LinkedList;

public class MainViewPagerAdapter extends FragmentPagerAdapter{
    //下半部分视图的适配器
    //Fragment
    LinkedList<MainFragment> fragments = new LinkedList<>();
    //Fragment中的日期
    LinkedList<String> dates = new LinkedList<>();

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        /*//若数据库中的为空，新建一个fragment，日期为当天
        if(GlobalUtil.getInstance() == null){
            dates.add(DateUtil.getFormattedDate());
        }else {
            dates = GlobalUtil.getInstance().databaseHelper.getAvaliableDate();
        }*/
        dates.add("2018-11-25");
        dates.add("2018-11-26");

        for (String date:dates){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);
        }
    }

    /**遍历fragment，重新加载所有（覆盖/刷新逻辑）*/
    public void reload(){
        for(MainFragment fragment: fragments){
            fragment.reload();
        }
    }

    /**获取最后一个fragment的Index值*/
    public int getLastIndex(){
        return fragments.size() - 1;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**用于新建今天的的fragment*/
    public void setFragments(String date)
    {
        dates.add(date);
        MainFragment fragment = new MainFragment(date);
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    /**最后一个fragment中的listview添加item时使用*/
    public MainFragment getLastFragment() {
        return fragments.getLast();
    }

}
