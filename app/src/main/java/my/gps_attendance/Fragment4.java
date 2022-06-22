package my.gps_attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment4 extends Fragment {
    TextView numText, nameText, majorText, gradeText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4, container, false);
        numText = view.findViewById(R.id.info_num);
        nameText = view.findViewById(R.id.info_name);
        majorText = view.findViewById(R.id.info_major);
        gradeText = view.findViewById(R.id.info_grade);

        numText.setText(Integer.toString(UserInfo.USER_NUM));
        nameText.setText(UserInfo.USER_NAME);
        majorText.setText(UserInfo.USER_MAJOR);
        gradeText.setText(Integer.toString(UserInfo.USER_GRADE));

        return view;
    }
}
