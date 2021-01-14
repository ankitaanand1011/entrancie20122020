package com.exam.entranceinew.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.entranceinew.CategoryData;
import com.exam.entranceinew.R;
import com.exam.entranceinew.adapter.HomeAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    CardView cv_study_notes;
    RecyclerView rvHome;
    HomeAdapter homeAdapter;

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
        rvHome = view.findViewById(R.id.rvHome);
    }

    private void functions() {
        ArrayList<CategoryData> arrayList = new ArrayList<CategoryData>();
        arrayList.add(new CategoryData("NCERT Solutions", R.mipmap.my_reader, R.color.darkred));
        arrayList.add(new CategoryData("Reference Books", R.mipmap.book, R.color.darkgreen));
        //arrayList.add(new CategoryData("Online Test", R.mipmap.my_exam, R.color.darkpurple));
        arrayList.add(new CategoryData("Study Notes", R.mipmap.post_it, R.color.darkorange));
        arrayList.add(new CategoryData("Sample Papers", R.mipmap.paper, R.color.darkpurple));
     //   arrayList.add(new CategoryData("Study Materials", R.mipmap.material, R.color.darkred));


        rvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        homeAdapter = new HomeAdapter(getActivity(), arrayList);
        rvHome.setAdapter(homeAdapter);


      /*  cv_study_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StudyNotesScreen.class);
                startActivity(intent);
            }
        });*/

    }


}