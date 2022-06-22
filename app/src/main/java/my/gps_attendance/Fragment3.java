package my.gps_attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        addProfessorInfo(view);
        return view;
    }
    void addProfessorInfo(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        ProfessorAdapter adapter = new ProfessorAdapter();

        adapter.addItem(new Professor("A 교수님", "010-1234-5678"));
        adapter.addItem(new Professor("B 교수님", "010-1111-1111"));
        adapter.addItem(new Professor("D 교수님", "010-2222-2222"));
        adapter.addItem(new Professor("E 교수님", "010-1234-5678"));
        adapter.addItem(new Professor("F 교수님", "010-1111-1111"));
        adapter.addItem(new Professor("G 교수님", "010-2222-2222"));
        adapter.addItem(new Professor("H 교수님", "010-1234-5678"));
        adapter.addItem(new Professor("I 교수님", "010-1111-1111"));
        adapter.addItem(new Professor("J 교수님", "010-2222-2222"));

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
