package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * MainPagerAdapter — ViewPager2 mate adapter
 * Tab index ne Fragment sathe map kare che:
 *   0 → ChatsFragment
 *   1 → StatusFragment
 *   2 → CallsFragment
 */
public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:  return new StatusFragment();
            case 2:  return new CallsFragment();
            default: return new ChatsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // CHATS, STATUS, CALLS
    }
}
