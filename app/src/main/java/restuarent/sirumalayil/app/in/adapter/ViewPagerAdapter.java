package restuarent.sirumalayil.app.in.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

import restuarent.sirumalayil.app.in.ui.fragment.ModelFragment;
import restuarent.sirumalayil.app.in.model.Item;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Item> inventoryList = new ArrayList<>();


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ModelFragment.newInstance(inventoryList.get(position).getItems());
    }


    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public void updateTab(ArrayList<Item> fragmentList){
        this.inventoryList = fragmentList;
        notifyDataSetChanged();
    }
}
