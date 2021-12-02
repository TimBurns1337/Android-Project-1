package com.example.androidproject1;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CommunityFragment extends Fragment {

    private CommunityViewModel mViewModel;

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.community_fragment, container, false);

        // create the click handlers
        // handler for compete with friends
        Button competeBtn = (Button) view.findViewById(R.id.compete_with_friends_btn);
        competeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChallengeActivity.class );
                startActivity(intent);
            }
        });

        // handler for compete requests
        Button leaderboardBtn = (Button) view.findViewById(R.id.leaderboard_btn);
        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LeaderboardActivity.class );
                startActivity(intent);
            }
        });

        // handler for leaderboard
        Button competeReqBtn = (Button) view.findViewById(R.id.compete_requests_btn);
        competeReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChallengeRequestActivity.class );
                startActivity(intent);
            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);
        // TODO: Use the ViewModel
    }

}