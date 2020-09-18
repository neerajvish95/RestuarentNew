package restuarent.sirumalayil.app.in.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import restuarent.sirumalayil.app.in.R;
import restuarent.sirumalayil.app.in.model.Cart;
import restuarent.sirumalayil.app.in.sqlDatabase.DatabaseHandler;

public class CartActivity extends AppCompatActivity {

    ListView listView;
    private List<Cart> cartList = new ArrayList<Cart>();
    DatabaseHandler dbHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_items);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");

        dbHandler = new DatabaseHandler(this);
        listView = findViewById(R.id.cartlist);
        cartList = dbHandler.getCartItems();

        CartListAdapter mAdapter = new CartListAdapter();
        listView.setAdapter(mAdapter);


    }



    class CartListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return cartList.size();
        }

        @Override
        public Object getItem(int i) {
            return cartList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {

            if (view == null){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);

                TextView name = view.findViewById(R.id.name);
                TextView price = view.findViewById(R.id.price);
                TextView count = view.findViewById(R.id.count);

                Cart cartItems = cartList.get(position);
                name.setText(cartItems.getName());
                price.setText(""+cartItems.getPrice());
                count.setText(""+cartItems.getCount());
            }

            return view;
        }
    }
}
