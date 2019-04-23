package br.com.loyaltyscience.loysci_android.presentation.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import br.com.loyaltyscience.loysci_android.R;
import br.com.loyaltyscience.loysci_android.databinding.ItemChallengeBinding;
import br.com.loyaltyscience.loysci_android.model.Challenge;
import br.com.loyaltyscience.loysci_android.presentation.ui.fragments.GamesFragment;
import br.com.loyaltyscience.loysci_android.presentation.ui.listeners.SimpleItemClickListener;

public class GameCatergoryAdapter extends RecyclerView.Adapter<GameCatergoryAdapter.ChallengeViewHolder>{

    List<Challenge> challenges;
    GamesFragment gamesFragment;
    int NORMAL_ITEM =0;
    int LAST_ITEM = 1;
    SimpleItemClickListener simpleItemClickListener;

    public GameCatergoryAdapter(List<Challenge> challenges, GamesFragment gamesFragment, SimpleItemClickListener simpleItemClickListener) {
        this.challenges = challenges;
        this.gamesFragment = gamesFragment;
        this.simpleItemClickListener = simpleItemClickListener;
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemChallengeBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.item_challenge, parent, false);

        if(viewType == LAST_ITEM && gamesFragment.getActivity() != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, gamesFragment.getActivity().getResources().getDisplayMetrics());
            params.setMargins(0, margin, 0, margin);
            binding.getRoot().setLayoutParams(params);
        }
        return new ChallengeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder holder, int position) {
        final Challenge challenge = challenges.get(position);
        holder.binding.challengeTitle.setText(challenge.getEncabezadoArte());
        holder.binding.challengeDescription.setText(challenge.getDetalleArte());

        if(challenge.getImagen()!=null)
            Glide.with(gamesFragment)
                    .load(challenge.getImagen())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.binding.imgChallenge);
        holder.binding.getRoot().setOnClickListener(v -> simpleItemClickListener.onSimpleItemClick(challenge));
    }

    @Override
    public void onViewRecycled(@NonNull ChallengeViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.clear(holder.binding.imgChallenge);
        holder.binding.challengeDescription.setText("");
        holder.binding.challengeTitle.setText("");
    }

    @Override
    public int getItemViewType(int position) {
        if(challenges != null){
            if(position == (challenges.size()-1)){
                return LAST_ITEM;
            }else{
                return NORMAL_ITEM;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if(challenges!=null)
            return challenges.size();
        return 0;
    }

    public class ChallengeViewHolder extends RecyclerView.ViewHolder {
        ItemChallengeBinding binding;
        public ChallengeViewHolder(View itemView) {
            super(itemView);
        }

        public ChallengeViewHolder(ItemChallengeBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
