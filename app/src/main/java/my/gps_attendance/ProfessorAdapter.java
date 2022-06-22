package my.gps_attendance;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ViewHolder> {
    ArrayList<Professor> list = new ArrayList<Professor>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.fragment3, parent, false);

        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Professor p = list.get(position);
        holder.setItem(p);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textName, textMobile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.p_name);
            textMobile = itemView.findViewById(R.id.p_phone);
        }

        public void setItem(Professor professor){
            textName.setText(professor.getName());
            textMobile.setText(professor.getMobile());
        }
    }

    public void addItem(Professor professor){
        list.add(professor);
    }
}
