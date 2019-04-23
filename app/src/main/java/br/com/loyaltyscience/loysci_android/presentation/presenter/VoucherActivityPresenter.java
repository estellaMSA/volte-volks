package br.com.loyaltyscience.loysci_android.presentation.presenter;

import br.com.loyaltyscience.loysci_android.model.Reward;
import br.com.loyaltyscience.loysci_android.networkUtils.LoyaltyApi;
import br.com.loyaltyscience.loysci_android.presentation.ui.activities.VoucherActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherActivityPresenter {

    private VoucherActivity view;

    public VoucherActivityPresenter(VoucherActivity view) {
        this.view = view;
    }


    public void setReedem(Reward reward){
        LoyaltyApi.redeemReward(reward, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(RewardsRedeemActivity.this, R.string.reward_redeemed_successfully, Toast.LENGTH_SHORT).show();
                    view.finish();
                }
                else {
//                    Toast.makeText(RewardsRedeemActivity.this, ApiUtils.getError(response).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(RewardsRedeemActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
