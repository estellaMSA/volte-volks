package br.com.loyaltyscience.loysci_android.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import br.com.loyaltyscience.loysci_android.R;
import br.com.loyaltyscience.loysci_android.databinding.ActivityVoucherBinding;
import br.com.loyaltyscience.loysci_android.model.Badge;
import br.com.loyaltyscience.loysci_android.model.Reward;
import br.com.loyaltyscience.loysci_android.presentation.presenter.VoucherActivityPresenter;
import br.com.loyaltyscience.loysci_android.util.AddVoucherDialog;
import br.com.loyaltyscience.loysci_android.util.ConfirmationDialog;
import br.com.loyaltyscience.loysci_android.util.Prefs;
import br.com.loyaltyscience.loysci_android.util.VoucherAlertDialog;

import static br.com.loyaltyscience.loysci_android.util.Constants.REWARD_PARCELABLE;

public class VoucherActivity extends AppCompatActivity {

    private ActivityVoucherBinding binding;
    private AddVoucherDialog addVoucherDialog;
    private ConfirmationDialog confirmationDialog;
    private VoucherAlertDialog alertVoucherDialog;
    private VoucherActivityPresenter presenter;
    private Reward voucher;
    private Context context = this;
    private List<Badge> badges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_voucher);
        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(R.string.change_points);

        String jsonBadges = Prefs.getJewels();
        if (jsonBadges != null) {
            badges = new Gson().fromJson(jsonBadges, new TypeToken<List<Badge>>() {
            }.getType());
        }

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            voucher = bundle.getParcelable(REWARD_PARCELABLE);
            if (voucher != null) {
                populateView();
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }


    public void populateView() {

        Glide.with(this)
                .load(voucher.getImagenArte())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        binding.ivImageVoucherLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        binding.ivImageVoucherLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(binding.ivImageVoucher);
        

        String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"https://www.movidamovevoce.com.br/stylesheets/main.css\" />";
        binding.tvTitleVoucher.setText(voucher.getEncabezadoArte());
//        binding.webviewDetails.loadDataWithBaseURL(voucher.getDetalleArte(), htmlData, "text/html", "UTF-8", null);
        binding.webviewDetails.getSettings().setStandardFontFamily("montserrat");

        binding.webviewDetails.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                binding.webviewDetailsLoading.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                binding.webviewDetailsLoading.setVisibility(View.GONE);
            }
        });

        binding.webviewDetails.loadData(htmlData + voucher.getDetalleArte(), "text/html", "utf-8");
//        binding.webviewDetails.loadUrl(voucher.getDetalleArte());

        if (voucher.getValorMoneda() > 0) {
            binding.tvPoints.setText(getString(R.string.points, voucher.getValorMoneda()));
            binding.tvPoints.setVisibility(View.VISIBLE);
        }

        if (voucher.getEncabezadoArte().toLowerCase().contains("zattini")) {
            binding.ivImageLogo.setImageDrawable(getDrawable(R.drawable.volkswagen_logo));
            binding.ivImageLogo.setVisibility(View.VISIBLE);
        } else if (voucher.getEncabezadoArte().toLowerCase().contains("netshoes")) {
            binding.ivImageLogo.setImageDrawable(getDrawable(R.drawable.volkswagen_logo));
            binding.ivImageLogo.setVisibility(View.VISIBLE);
        } else {
            binding.ivImageLogo.setImageDrawable(getDrawable(R.drawable.volkswagen_logo));
            binding.ivImageLogo.setVisibility(View.VISIBLE);
        }

        binding.addButtonLayout.setOnClickListener(view -> {
            List<Reward> cart = Prefs.getCart();
            Boolean containsGroup = false;
            Boolean containsDiary = false;
            Boolean containsContract = false;

            if (cart != null) {
                for (Reward r : cart) {

                    if(r.getEncabezadoArte().equals(voucher.getEncabezadoArte())) {

                        if(r.getEncabezadoArte().contains(getString(R.string.group))){
                            containsGroup = true;
                        }

                        if (r.getDetalleArte().contains(getString(R.string.item_per_contract))) {
                            containsContract = true;
                            break;
                        }else if (r.getDetalleArte().contains(getString(R.string.item_per_daily))) {
                            containsDiary = true;
                            break;
                        }
                    } else {
                        if(r.getEncabezadoArte().contains(getString(R.string.group))) {
                            containsGroup = true;
                        }
                        if (r.getDetalleArte().contains(getString(R.string.item_per_contract))) {
                            containsContract = true;
                        }
                    }
                }
            }


            if (voucher.getEncabezadoArte().contains(getString(R.string.group))) {
                if (!containsGroup) {
                    addVoucherDialog();
                } else {
                    alertDialog(VoucherAlertDialog.Type.DIARY);
                }
            } else if (voucher.getDetalleArte().contains(getString(R.string.item_per_contract))) {
                if (!containsContract) {
                    confirmationDialog();
                } else {
                    alertDialog(VoucherAlertDialog.Type.CONTRACT);
                }
            }else if (voucher.getDetalleArte().contains(getString(R.string.item_per_daily))) {
                if(!containsDiary){
                    addVoucherDialog();
                }else
                    alertDialog(VoucherAlertDialog.Type.DIARY);
            }
            //Alteracao Leandro Movida Connect
            else{
                addVoucherDialog();
            }
        });
    }


    private void addVoucherDialog() {
        String jsonBadges = Prefs.getJewels();

        if (jsonBadges != null) {
            badges = new Gson().fromJson(jsonBadges, new TypeToken<List<Badge>>() {
            }.getType());
        }

        addVoucherDialog = new AddVoucherDialog(this, voucher, new AddVoucherDialog.OnClickListener() {
            @Override
            public void onAdd() {

            }

            @Override
            public void onRemove() {

            }

            @Override
            public void onCancel() {
                addVoucherDialog.dismiss();
            }

            @Override
            public void onConfirm(int quantity) {
                List<Reward> rewardList = Prefs.getCart();

                if (rewardList != null) {
                    voucher.setQuantity(quantity);
                    rewardList.add(voucher);
                    Prefs.saveCart(rewardList);
                } else {
                    rewardList = new ArrayList<>();
                    voucher.setQuantity(quantity);
                    rewardList.add(voucher);
                    Prefs.saveCart(rewardList);
                }

                addVoucherDialog.dismiss();
                confirmationDialog = new ConfirmationDialog(context, voucher, quantity, new ConfirmationDialog.OnClickListener() {
                    @Override
                    public void onCancel() {
                        confirmationDialog.dismiss();
                    }

                    @Override
                    public void onGoToCart() {
                        confirmationDialog.dismiss();
                        Intent intent = new Intent(VoucherActivity.this, CartActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                confirmationDialog.show();

            }
        });
        addVoucherDialog.show();
    }

    private void confirmationDialog() {
        List<Reward> rewardList = Prefs.getCart();

        if (rewardList != null) {
            voucher.setQuantity(1);
            rewardList.add(voucher);
            Prefs.saveCart(rewardList);
        } else {
            rewardList = new ArrayList<>();
            voucher.setQuantity(1);
            rewardList.add(voucher);
            Prefs.saveCart(rewardList);
        }

        confirmationDialog = new ConfirmationDialog(context, voucher, 1, new ConfirmationDialog.OnClickListener() {
            @Override
            public void onCancel() {
                confirmationDialog.dismiss();
            }

            @Override
            public void onGoToCart() {
                confirmationDialog.dismiss();
                Intent intent = new Intent(VoucherActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        confirmationDialog.show();
    }

    private void alertDialog(VoucherAlertDialog.Type type) {
        alertVoucherDialog = new VoucherAlertDialog(context, voucher, type, new VoucherAlertDialog.OnClickListener() {
            @Override
            public void onCancel() {
                alertVoucherDialog.dismiss();
            }

            @Override
            public void onConfirm() {
                Intent intent = new Intent(VoucherActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alertVoucherDialog.show();
    }

}
