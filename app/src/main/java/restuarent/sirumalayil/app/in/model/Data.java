package restuarent.sirumalayil.app.in.model;

import java.util.ArrayList;

public class Data {
    ArrayList<Item> item;

    public Data(ArrayList<Item> itemArrayList) {
        this.item = itemArrayList;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }
}
