package com.bellkung.anidesu.controller;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bellkung.anidesu.adapter.AnimeListOverviewPagerAdapter;
import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AnimeListActivity extends AppCompatActivity implements OnNetworkCallbackListener {

    @BindView(R.id.anime_list_overview_pager) ViewPager mOverviewPager;
    @BindView(R.id.anime_list_tab) SmartTabLayout mTabStrip;
    @BindView(R.id.anime_list_cover_image) ImageView mBannerImage;
    @BindView(R.id.bmb) BoomMenuButton boomMenuBtn;

    private Series thisSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_list);
        ButterKnife.bind(this);

        setBoomMenuButton();

        Series series = getIntent().getParcelableExtra(KeyUtils.KEY_SERIES);
        new NetworkConnectionManager().fetchThisSeriesData(this, series.getId());

    }

    private void setBoomMenuButton() {
        this.boomMenuBtn.setButtonEnum(ButtonEnum.TextInsideCircle);
        this.boomMenuBtn.setPiecePlaceEnum(PiecePlaceEnum.DOT_2_1);
        this.boomMenuBtn.setButtonPlaceEnum(ButtonPlaceEnum.SC_2_1);

        for (int i = 0; i < this.boomMenuBtn.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                    .normalImageRes(KeyUtils.BMB_DRAWABLE[i])
                    .normalText(KeyUtils.BMB_TEXT[i]);
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    Toast.makeText(AnimeListActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                }
            });

            this.boomMenuBtn.addBuilder(builder);
        }

    }

    private void initializeUI() {
        AnimeListOverviewPagerAdapter adapter = new AnimeListOverviewPagerAdapter(getSupportFragmentManager());
        adapter.setSeries(this.thisSeries);
        this.mOverviewPager.setAdapter(adapter);
        this.mTabStrip.setViewPager(this.mOverviewPager);

        Glide.with(this).load(this.thisSeries.getImage_url_banner()).into(this.mBannerImage);

    }


    @OnClick(R.id.back_btn)
    public void backBtnPressed() {
        finish();
    }

    @Override
    public void onResponse(String action, Call call, Response response) {
        switch (action) {
            case ApiConfig.FETCH_THIS_SERIES:
                this.thisSeries = (Series) response.body();
                initializeUI();
                break;
        }
    }

    @Override
    public void onBodyError(ResponseBody responseBodyError) {
        Log.i("Status", "onBodyError");
    }

    @Override
    public void onBodyErrorIsNull() {
        Log.i("Status", "onBodyErrorIsNull");
    }

    @Override
    public void onFailure(Throwable t) {
        Log.i("Status", "onBodyErrorIsNull");
    }
}
