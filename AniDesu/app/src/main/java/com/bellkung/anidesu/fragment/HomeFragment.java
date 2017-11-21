package com.bellkung.anidesu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.controller.PostActivity;
import com.bellkung.anidesu.utils.KeyUtils;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.home_recycleView) RecyclerView home_recycleView;
    @BindView(R.id.bmb_status) BoomMenuButton boomMenuBtn;

    public static HomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        setBoomMenuButton();
        return view;
    }

    private void setBoomMenuButton() {
        this.boomMenuBtn.setButtonEnum(ButtonEnum.TextOutsideCircle);
        this.boomMenuBtn.setPiecePlaceEnum(PiecePlaceEnum.DOT_1);
        this.boomMenuBtn.setButtonPlaceEnum(ButtonPlaceEnum.SC_1);

        for (int i = 0; i < this.boomMenuBtn.getPiecePlaceEnum().pieceNumber(); i++) {

            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .isRound(false)
                    .shadowCornerRadius(Util.dp2px(20))
                    .buttonCornerRadius(Util.dp2px(20))
                    .normalImageRes(KeyUtils.BMB_DRAWABLE[0])
                    .normalText(KeyUtils.BMB_POST);
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    Intent intent = new Intent(getActivity(), PostActivity.class);
                    startActivity(intent);
                }
            });

            this.boomMenuBtn.addBuilder(builder);
        }
    }

}
