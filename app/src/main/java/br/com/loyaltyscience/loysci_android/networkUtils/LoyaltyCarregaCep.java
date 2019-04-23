package br.com.loyaltyscience.loysci_android.networkUtils;

import br.com.loyaltyscience.loysci_android.model.CepModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoyaltyCarregaCep {
    @GET("web_cep.php")
    Call<CepModel> getCEP(@Query("cep") String cep, @Query("formato") String formato);
}
