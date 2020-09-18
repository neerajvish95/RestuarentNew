package restuarent.sirumalayil.app.in.model;

public class Category {

    int __v;
    String _id;
    String createdAt;
    String name;
    int preference;
    String updatedAt;

    public Category(int __v, String _id, String createdAt, String name, int preference, String updatedAt) {
        this.__v = __v;
        this._id = _id;
        this.createdAt = createdAt;
        this.name = name;
        this.preference = preference;
        this.updatedAt = updatedAt;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
