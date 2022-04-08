package com.example.proyectofinal.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.R;
import com.example.proyectofinal.ui.adapter.CustomAdapter;

import java.util.ArrayList;

public class NotifyProfesorFragment extends Fragment {
    private ArrayList<String> localDataSet;
    private Context mContext;
    RecyclerView recycler;

    public NotifyProfesorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notify_profesor_fragment, container, false);

        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(),
                LinearLayoutManager.VERTICAL,
                false));
        localDataSet = new ArrayList<String>();

        for (int i = 0; i>50; i++){
            localDataSet.add("Dato # " + i + " ");
        }

        CustomAdapter adapter = new CustomAdapter(localDataSet);
        recycler.setAdapter(adapter);

        return view;
    }
}
