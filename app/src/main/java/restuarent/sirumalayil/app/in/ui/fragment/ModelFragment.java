package restuarent.sirumalayil.app.in.ui.fragment;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import restuarent.sirumalayil.app.in.ui.activity.CartActivity;
import restuarent.sirumalayil.app.in.R;
import restuarent.sirumalayil.app.in.interfaces.ClickListener;
import restuarent.sirumalayil.app.in.adapter.RestaurantRecyclerAdapter;
import restuarent.sirumalayil.app.in.model.ItemX;
import restuarent.sirumalayil.app.in.sqlDatabase.DatabaseHandler;

public class ModelFragment extends Fragment implements ClickListener {

    private MaterialCardView selectedItemCardView;
    public TextView cartCount, cartPrice;

    private List<ItemX> inventoryList = new ArrayList<>();
    DatabaseHandler dbHandler;
    private static final String KEY_DATA = "key_data";

    public static ModelFragment newInstance(List<ItemX> items) {

        Bundle args = new Bundle();
        ModelFragment fragment = new ModelFragment();
        args.putSerializable(KEY_DATA, (Serializable) items);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHandler = new  DatabaseHandler(getContext());
        Bundle arguments = getArguments();
        inventoryList = (List<ItemX>) arguments.getSerializable(KEY_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        selectedItemCardView = view.findViewById(R.id.selectedItemCardView);
        cartCount = view.findViewById(R.id.cartCount);
        cartPrice = view.findViewById(R.id.totalPrice);
        LinearLayout viewCart = view.findViewById(R.id.viewCart);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        RestaurantRecyclerAdapter adapter = new RestaurantRecyclerAdapter();
        adapter.updateList(inventoryList, getContext(), ModelFragment.this);
        recyclerView.setAdapter(adapter);

        updateUi();

        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void showBottomDialog() {
        selectedItemCardView.setVisibility(View.VISIBLE);
        selectedItemCardView.setAlpha(0.0f);
        selectedItemCardView.animate()
            .setDuration(1000)
            .translationZ(100f)
            .alpha(1.0f)
            .setListener(null);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setOnClick() {
        updateUi();
    }

    private void updateUi(){
        int totalPrice = dbHandler.getTotalPrice();
        int totalCount = dbHandler.getTotalCount();

        if (totalCount > 0){
            showBottomDialog();
            cartCount.setText(""+totalCount+" Item $");
            cartPrice.setText(""+totalPrice);

        }else {
            dbHandler.deleteTable();
            hideBottomSheet();
        }
    }

    private void hideBottomSheet() {
        selectedItemCardView.animate()
            .translationY(0f)
            .alpha(0.0f)
            .setDuration(500)
            .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        selectedItemCardView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "onResume: ");
        updateUi();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("TAG", "onStart: ");
        updateUi();
    }
}
