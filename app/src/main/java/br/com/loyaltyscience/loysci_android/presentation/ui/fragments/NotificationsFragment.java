package br.com.loyaltyscience.loysci_android.presentation.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import br.com.loyaltyscience.loysci_android.R;
import br.com.loyaltyscience.loysci_android.databinding.ActivityNotificationsBinding;
import br.com.loyaltyscience.loysci_android.model.Notification;
import br.com.loyaltyscience.loysci_android.presentation.ui.adapters.NotificationsAdapter;
import br.com.loyaltyscience.loysci_android.presentation.ui.listeners.SimpleItemClickListener;

import static br.com.loyaltyscience.loysci_android.util.Constants.NOTIFICATIONS_PARCELABLE;

public class NotificationsFragment extends Fragment implements SimpleItemClickListener {

    private ActivityNotificationsBinding binding;
    public List<Notification> notifications;
    public NotificationsAdapter adapter;

    protected void onCreateView(Bundle savedInstanceState, LayoutInflater inflater, ViewGroup container) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_notifications, container, false);
        View view = binding.getRoot();
        notifications = getActivity().getIntent().getParcelableArrayListExtra(NOTIFICATIONS_PARCELABLE);
        //((AppCompatActivity) getActivity()).setSupportActionBar(binding.includeToolbar.toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        //(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        //(binding.includeToolbar.toolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor((getContext()), R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.latest_notifications));

        adapter = new NotificationsAdapter(notifications, getContext(), this);



        binding.recyclerNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerNotifications.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setDisplayShowTitleEnabled(boolean b) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    private void finish() {
    }

    @Override
    public void onSimpleItemClick(Object object) {
        //Era pra fazer alguma coisa mas por enquanto não
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
