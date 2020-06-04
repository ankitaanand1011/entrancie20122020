package com.exam.entranceinew.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.exam.entranceinew.R;
import com.exam.entranceinew.ui.activity.StudyNotesScreen;


public class HomeFragment extends Fragment {
    CardView cv_study_notes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initialize_view(view);
        functions();


        return view;

    }

    private void initialize_view(View view) {
        cv_study_notes = view.findViewById(R.id.cv_study_notes);
    }

    private void functions() {

        cv_study_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StudyNotesScreen.class);
                startActivity(intent);
            }
        });

    }


}