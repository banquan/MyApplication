package lgj.example.com.biyesheji.ui.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lgj.example.com.biyesheji.R;

/**
 * Created by yhdj on 2017/10/14.
 */

public class EditNameDialogFragment extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_edit_name, container);
        return view;
    }

    public void show(FragmentManager fragmentManager, String editNameDialog) {

    }

}
