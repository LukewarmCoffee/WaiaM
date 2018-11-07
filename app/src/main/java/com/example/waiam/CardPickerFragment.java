package com.example.waiam;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class CardPickerFragment extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        GridView gridView = getActivity().findViewById(R.id.gridview);
        gridView.setAdapter(new GridAdapter(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        container.clearDisappearingChildren();

        //GridView gridView = getView().findViewById(R.id.gridview);
       // gridView.setAdapter(new GridAdapter(getActivity()));

        return inflater.inflate(R.layout.card_picker_view, container, false);
    }

}
