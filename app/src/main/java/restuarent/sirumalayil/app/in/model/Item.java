package restuarent.sirumalayil.app.in.model;

import java.util.List;

public class Item {
    List<Category> category;
    List<ItemX> items;

    public Item(List<Category> categoryList, List<ItemX> items) {
        this.category = categoryList;
        this.items = items;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<ItemX> getItems() {
        return items;
    }

    public void setItems(List<ItemX> items) {
        this.items = items;
    }
}
