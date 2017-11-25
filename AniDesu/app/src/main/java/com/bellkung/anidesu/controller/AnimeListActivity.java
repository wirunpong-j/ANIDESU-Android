package com.bellkung.anidesu.controller;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bellkung.anidesu.adapter.viewpager.AnimeListOverviewPagerAdapter;
import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.MyAnimeList;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.utils.DialogManager;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;
import cn.hugeterry.coordinatortablayout.listener.LoadHeaderImagesListener;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AnimeListActivity extends AppCompatActivity implements OnNetworkCallbackListener,
        OnBMClickListener, DialogManager.DialogManagerListener {

    @BindView(R.id.anime_list_overview_pager) ViewPager mOverviewPager;
    @BindView(R.id.bmb) BoomMenuButton boomMenuBtn;
    @BindView(R.id.coordinatortablayout) CoordinatorTabLayout coorDinatorTabLayout;

    private Series thisSeries;
    private String bmb_status;
    private String anime_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_anime_list);
        ButterKnife.bind(this);

        Series series = getIntent().getParcelableExtra(KeyUtils.KEY_SERIES);
        this.anime_status = getIntent().getStringExtra(KeyUtils.KEY_ANIME_STATUS);

        checkThisSeriesInMyAnimeList(series);

        new NetworkConnectionManager().fetchThisSeriesData(this, series.getId(), ApiConfig.FETCH_THIS_SERIES);

    }

    private void checkThisSeriesInMyAnimeList(Series series) {
        boolean status = false;

        if (User.getInstance().getList_plan().get(series.getId()) != null) { status = true; this.anime_status = KeyUtils.STATUS_PLAN_TO_WATCH; }
        if (User.getInstance().getList_watching().get(series.getId()) != null) { status = true; this.anime_status = KeyUtils.STATUS_WATCHING; }
        if (User.getInstance().getList_completed().get(series.getId()) != null) { status = true; this.anime_status = KeyUtils.STATUS_COMPLETED; }
        if (User.getInstance().getList_dropped().get(series.getId()) != null) { status = true; this.anime_status = KeyUtils.STATUS_DROPPED; }

        if (status) {
            this.bmb_status = KeyUtils.BMB_STATUS_EDIT;
        } else {
            this.bmb_status = KeyUtils.BMB_STATUS_ADD;
        }
        setBoomMenuButton();
    }

    private void setBoomMenuButton() {

        this.boomMenuBtn.clearBuilders();

        this.boomMenuBtn.setButtonEnum(ButtonEnum.TextOutsideCircle);
        this.boomMenuBtn.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_4);
        this.boomMenuBtn.setButtonPlaceEnum(ButtonPlaceEnum.SC_3_4);

        for (int i = 0; i < this.boomMenuBtn.getPiecePlaceEnum().pieceNumber(); i++) {

            TextOutsideCircleButton.Builder builder = null;
            switch (this.bmb_status) {
                case KeyUtils.BMB_STATUS_ADD:
                    builder = new TextOutsideCircleButton.Builder()
                            .normalImageRes(KeyUtils.BMB_DRAWABLE[i])
                            .normalText(KeyUtils.BMB_TEXT[i]);
                    break;
                case KeyUtils.BMB_STATUS_EDIT:
                    builder = new TextOutsideCircleButton.Builder()
                            .normalImageRes(KeyUtils.BMB_DRAWABLE_EDIT[i])
                            .normalText(KeyUtils.BMB_EDIT_TEXT[i]);
                    break;
            }
            builder.listener(this);

            this.boomMenuBtn.addBuilder(builder);
        }

    }

    private void initializeUI() {
        AnimeListOverviewPagerAdapter adapter = new AnimeListOverviewPagerAdapter(getSupportFragmentManager(), this);
        adapter.setSeries(this.thisSeries);
        this.mOverviewPager.setAdapter(adapter);

        this.coorDinatorTabLayout.setTranslucentStatusBar(this)
                .setTitle(thisSeries.getTitle_romaji())
                .setBackEnable(true)
                .setLoadHeaderImagesListener(new LoadHeaderImagesListener() {
                    @Override
                    public void loadHeaderImages(ImageView imageView, TabLayout.Tab tab) {
                        Glide.with(AnimeListActivity.this).load(thisSeries.getImage_url_banner()).into(imageView);
                    }
                })
                .setupWithViewPager(this.mOverviewPager);

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

    @Override
    public void onBoomButtonClick(int index) {

        switch(index) {
            case KeyUtils.BMB_START:
                if (this.bmb_status.equals(KeyUtils.BMB_STATUS_ADD)) {
                    DialogManager addMyAnimeListDialog = new DialogManager(this);
                    addMyAnimeListDialog.setListener(this);
                    addMyAnimeListDialog.addMyAnimeListDialog(this.thisSeries);

                } else {
                    MyAnimeList myAnimeList = getAnimeFormThisAnimeStatus();
                    DialogManager editMyAnimeListDialog = new DialogManager(this);
                    editMyAnimeListDialog.setListener(this);
                    editMyAnimeListDialog.EditMyAnimeListDialog(this.anime_status, this.thisSeries, myAnimeList);

                }
                break;

            case KeyUtils.BMB_REVIEW:
                DialogManager reviewDialog = new DialogManager(this);
                reviewDialog.setListener(this);
                reviewDialog.reviewDialog(this.thisSeries);
                break;

            case KeyUtils.BMB_SHARE:

                break;
        }
    }

    private MyAnimeList getAnimeFormThisAnimeStatus() {
        switch(this.anime_status) {
            case KeyUtils.STATUS_PLAN_TO_WATCH:
                return User.getInstance().getList_plan().get(thisSeries.getId());
            case KeyUtils.STATUS_WATCHING:
                return User.getInstance().getList_watching().get(thisSeries.getId());
            case KeyUtils.STATUS_COMPLETED:
                return User.getInstance().getList_completed().get(thisSeries.getId());
            case KeyUtils.STATUS_DROPPED:
                return User.getInstance().getList_dropped().get(thisSeries.getId());
        }
        return null;
    }

    @Override
    public void onDialogDismiss() {
        checkThisSeriesInMyAnimeList(this.thisSeries);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
