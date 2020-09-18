package restuarent.sirumalayil.app.in.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import restuarent.sirumalayil.app.in.interfaces.ClickListener;
import restuarent.sirumalayil.app.in.R;
import restuarent.sirumalayil.app.in.model.ItemX;
import restuarent.sirumalayil.app.in.model.Cart;
import restuarent.sirumalayil.app.in.sqlDatabase.DatabaseHandler;


public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantRecyclerAdapter.ViewHolder>{

    private List<ItemX> inventoryList = new ArrayList<>();
    private Context context;

    private ClickListener onClickItem;
    DatabaseHandler dbHandler;



    @NonNull
    @Override
    public RestaurantRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        dbHandler = new DatabaseHandler(context.getApplicationContext());


        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantRecyclerAdapter.ViewHolder holder, int position) {

        holder.bind(inventoryList.get(position));
    }

    @Override
    public int getItemCount() {

        return inventoryList.size();
    }

    public void updateList(List<ItemX> inventoryList, Context context, ClickListener onClick) {
        this.inventoryList = inventoryList;
        this.context = context;
        this.onClickItem = onClick;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        int itemCount = 1;
        int totalPrice = 0;
        private TextView itemText, itemPrice, textItemCount, buttonIncrement, buttonDecrement;
        private Button buttonAdd;
        private LinearLayout layoutButtonAdd, layoutSelectedItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            textItemCount = itemView.findViewById(R.id.textItemCount);
            buttonAdd = itemView.findViewById(R.id.buttonAdd);
            buttonIncrement = itemView.findViewById(R.id.buttonIncrement);
            buttonDecrement = itemView.findViewById(R.id.buttonDecrement);
            layoutButtonAdd = itemView.findViewById(R.id.layoutButtonAdd);
            layoutSelectedItem = itemView.findViewById(R.id.layoutSelectedItem);

        }
        @SuppressLint("SetTextI18n")
        private void bind(final ItemX inventory){
            itemText.setText(inventory.getName());
            itemPrice.setText("$"+inventory.getPrice());

            int selected = dbHandler.getItemCount(inventory.getName());
            if (selected > 0){
                layoutButtonAdd.setVisibility(View.GONE);
                layoutSelectedItem.setVisibility(View.VISIBLE);
                textItemCount.setText(""+dbHandler.getItemCount(inventory.getName()));
            }

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layoutButtonAdd.setVisibility(View.GONE);
                    layoutSelectedItem.setVisibility(View.VISIBLE);

                    Cart cart = new Cart();
                    cart.setName(inventory.getName());
                    cart.setPrice(inventory.getPrice());
                    cart.setCount(itemCount);

                    String itemName = dbHandler.getItemName(inventory.getName());

                    if (!itemName.equals("")){
                        Log.e("TAG", "itemName: "+itemName);
                        int itemCount = dbHandler.getItemCount(inventory.getName());
                        dbHandler.updateItem(inventory.getName(), itemCount+1, inventory.getPrice());
                    }else {
                        dbHandler.insertItems(cart);
                    }
                    textItemCount.setText(""+dbHandler.getItemCount(inventory.getName()));

                    onClickItem.setOnClick();
                }
            });

            buttonIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Cart cart = new Cart();
                    cart.setName(inventory.getName());
                    cart.setPrice(inventory.getPrice());
                    cart.setCount(itemCount);

                    String itemName = dbHandler.getItemName(inventory.getName());

                    if (!itemName.equals("")){
                        Log.e("TAG", "itemName: "+itemName);
                        int itemCount = dbHandler.getItemCount(inventory.getName());
                        int itemPrice = dbHandler.getItemPrice(inventory.getName());
                        dbHandler.updateItem(inventory.getName(), itemCount+1, itemPrice+inventory.getPrice());
                    }else {
                        dbHandler.insertItems(cart);
                    }
                    textItemCount.setText(""+dbHandler.getItemCount(inventory.getName()));

                    onClickItem.setOnClick();
                }
            });

            buttonDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String itemName = dbHandler.getItemName(inventory.getName());

                    if (!itemName.equals("")){
                        Log.e("TAG", "itemName: "+itemName);
                        int itemCount = dbHandler.getItemCount(inventory.getName());
                        int itemPrice = dbHandler.getItemPrice(inventory.getName());
                        if (itemCount >1){
                            dbHandler.updateItem(inventory.getName(), itemCount-1, itemPrice - inventory.getPrice());
                        }else {
                            dbHandler.deleteItem(inventory.getName());
                        }
                    }
                    if (dbHandler.getItemCount(inventory.getName()) < 1){
                        layoutButtonAdd.setVisibility(View.VISIBLE);
                        layoutSelectedItem.setVisibility(View.GONE);
                    }
                    textItemCount.setText(""+dbHandler.getItemCount(inventory.getName()));
                    onClickItem.setOnClick();

                }
            });
        }
    }
}
