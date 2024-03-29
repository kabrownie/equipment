package com.example.persondetail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.persondetail.tabfragments.rentItems;
import com.example.persondetail.tabfragments.rented;
import com.example.persondetail.tabfragments.myItems;

public class pagerAdapter extends FragmentStateAdapter {
     public pagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
switch (position){
    case 0:
        return new rentItems();
    case 1:
        return new rented();

    case 2:
        return new myItems();

}
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
