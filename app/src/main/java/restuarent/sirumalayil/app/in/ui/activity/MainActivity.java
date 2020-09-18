package restuarent.sirumalayil.app.in.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import restuarent.sirumalayil.app.in.R;
import restuarent.sirumalayil.app.in.adapter.ViewPagerAdapter;
import restuarent.sirumalayil.app.in.model.Category;
import restuarent.sirumalayil.app.in.model.InventoryResponse;
import restuarent.sirumalayil.app.in.model.Item;
import restuarent.sirumalayil.app.in.retrofit.APIClient;
import restuarent.sirumalayil.app.in.retrofit.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*getSupportActionBar();
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }*/

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        getInventory();

    }

    private void dashboardTabs(ArrayList<Item> inventoryItemList){

        adapter.updateTab(inventoryItemList);

        final List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i< inventoryItemList.size(); i++){
            categoryList.add(inventoryItemList.get(i).getCategory().get(0));
        }

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                // position of the current tab and that tab
                tab.setText(categoryList.get(position).getName());
            }
        });

        tabLayoutMediator.attach();

        /*TabLayoutMediator = new TabLayoutMediator(tabs, viewPager,
                TabLayoutMediator.TabConfigurationStrategy{ tab, position ->
            return@TabConfigurationStrategy when(position){
                //0-> tab.text = "One"
                //1-> tab.text = "Two"
                //2-> tab.text = "Three"
                   // else -> throw IllegalAccessException("No Position $position")
            }
        })*/
    }

    private ArrayList<Item> inventoryItemList = new ArrayList<>();
    private void getInventory(){
        APIInterface apiService = APIClient.getApiClientInterface(this, "http://immie-dev-rms-alb-1199175796.us-east-1.elb.amazonaws.com");
        Call<InventoryResponse> call = apiService.getInventory("api/v1/item/category/5f5275a5b5eaef001158c0b7");
        call.enqueue(new Callback<InventoryResponse>() {
            @Override
            public void onResponse(Call<InventoryResponse> call,
                                   Response<InventoryResponse> response) {

                if (response.isSuccessful()){
                    for (int i = 0; i< response.body().getData().getItem().size(); i++){
                        //Log.e("TAG", "size: " + i);
                        inventoryItemList.add(response.body().getData().getItem().get(i));
                    }

                    dashboardTabs(inventoryItemList);

                }

            }

            @Override
            public void onFailure(Call<InventoryResponse> call, Throwable t) {
                Log.e("TAG", "fail" + t.getMessage());
            }
        });

    }
}
