package restuarent.sirumalayil.app.in.model;

import java.io.Serializable;

public class ItemX implements Serializable {

    String menu_type;
    String name;
    int price;

    public ItemX(String menu_type, String name, int price) {
        this.menu_type = menu_type;
        this.name = name;
        this.price = price;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
