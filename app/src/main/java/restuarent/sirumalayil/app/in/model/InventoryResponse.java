package restuarent.sirumalayil.app.in.model;

public class InventoryResponse {

    Data data;
    String status;

    public InventoryResponse(Data data, String status) {
        this.data = data;
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
