package br.com.loyaltyscience.loysci_android.presentation.ui.viewModels;

import android.arch.lifecycle.ViewModel;

import br.com.loyaltyscience.loysci_android.model.Challenge;
import br.com.loyaltyscience.loysci_android.model.MissionsActivityResponse;
import br.com.loyaltyscience.loysci_android.model.SubmitAnswerChallenge;
import br.com.loyaltyscience.loysci_android.networkUtils.LoyaltyApi;
import br.com.loyaltyscience.loysci_android.presentation.ui.listeners.ViewModelSimpleCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.loyaltyscience.loysci_android.util.Constants.CHALLENGE_CORRECT;
import static br.com.loyaltyscience.loysci_android.util.Constants.CHALLENGE_WRONG;
import static br.com.loyaltyscience.loysci_android.util.Constants.RESPONSE_FAILED_LOAD;

/**
 * Created by Pedro Mazarini on 24/Oct/2018
 */
public class QuizViewModel extends ViewModel {

    public Challenge challenge;

    public void loadActivityInfo(final String idMember, final String idActivity,  final ViewModelSimpleCallback callback){
        LoyaltyApi.getActivityInfo(idMember, idActivity, new Callback<MissionsActivityResponse>() {
            @Override
            public void onResponse(Call<MissionsActivityResponse> call, final Response<MissionsActivityResponse> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getChallenge() != null){
                    challenge.setPergunta(response.body().getChallenge().getPergunta());
                    challenge.setRespostas(response.body().getChallenge().getRespostas());
                    callback.onSuccess();
                }else{
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            }

            @Override
            public void onFailure(Call<MissionsActivityResponse> call, Throwable t) {
                callback.onFailed(RESPONSE_FAILED_LOAD);
            }
        });
    }

    public void setChallengeCorrect(final String idMember, String idTopic, String idMission,  final ViewModelSimpleCallback callback){
        LoyaltyApi.setChallengeCorrect(new SubmitAnswerChallenge(idMember, idTopic, idMission, challenge.getIdMision(), challenge.getNombre(), CHALLENGE_CORRECT, String.valueOf(challenge.getValor())), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, final Response<Void> response) {
                if(response.isSuccessful()){
                    callback.onSuccess();
                }else{
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailed(RESPONSE_FAILED_LOAD);
            }
        });
    }

    public void setChallengeWrong(final String idMember, String idTopic, String idMission,  final ViewModelSimpleCallback callback){
        LoyaltyApi.setChallengeCorrect(new SubmitAnswerChallenge(idMember, idTopic, idMission, challenge.getIdMision(), challenge.getNombre(), CHALLENGE_WRONG, String.valueOf(challenge.getValor())), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, final Response<Void> response) {
                if(response.isSuccessful()){
                    callback.onSuccess();
                }else{
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailed(RESPONSE_FAILED_LOAD);
            }
        });
    }

}
