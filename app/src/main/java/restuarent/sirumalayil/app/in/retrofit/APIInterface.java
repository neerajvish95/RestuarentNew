package restuarent.sirumalayil.app.in.retrofit;

import restuarent.sirumalayil.app.in.model.InventoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIInterface {

    @GET
    Call<InventoryResponse> getInventory(@Url String url);
}
