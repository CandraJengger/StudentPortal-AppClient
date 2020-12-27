package com.jengger.studentportalappclient.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.Model.BundleOrtu;
import com.jengger.studentportalappclient.api.Model.Parents;
import com.jengger.studentportalappclient.ui.profile.edit.EditDataOrtuFragment;

public class BottomDialogButton extends BottomSheetDialogFragment implements View.OnClickListener {
    public static String KEY_BOTTOM_EXTRA = "key_bottom_extra";
    private Button btnDataAyah, btnDataIbu, btnDataWali;
    private Parents parentsAyah, parentsIbu, parentsWali;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_dialog_button, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle mBundle = getArguments();
        BundleOrtu bundleOrtu = (BundleOrtu) mBundle.getSerializable(KEY_BOTTOM_EXTRA);
        parentsAyah = bundleOrtu.getParentsAyah();
        parentsIbu = bundleOrtu.getParentsIbu();
        parentsWali = bundleOrtu.getParentsWali();

        btnDataAyah = view.findViewById(R.id.btn_edit_data_ayah);
        btnDataIbu = view.findViewById(R.id.btn_edit_data_ibu);
        btnDataWali = view.findViewById(R.id.btn_edit_data_wali);

        btnDataAyah.setOnClickListener(this);
        btnDataIbu.setOnClickListener(this);
        btnDataWali.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_data_ayah: {
                Bundle mBundleEdit = new Bundle();
                mBundleEdit.putSerializable(EditDataOrtuFragment.KEY_DATA_ORANGTUA_EXTRA, parentsAyah);

                NavHostFragment.findNavController(this).navigate(R.id.action_bottomDialogButton_to_editDataOrtuFragment, mBundleEdit);
                break;
            }
            case R.id.btn_edit_data_ibu: {
                Bundle mBundleEdit = new Bundle();
                mBundleEdit.putSerializable(EditDataOrtuFragment.KEY_DATA_ORANGTUA_EXTRA, parentsIbu);
                NavHostFragment.findNavController(this).navigate(R.id.action_bottomDialogButton_to_editDataOrtuFragment, mBundleEdit);
                break;
            }
            case R.id.btn_edit_data_wali:{
                Bundle mBundleEdit = new Bundle();
                mBundleEdit.putSerializable(EditDataOrtuFragment.KEY_DATA_ORANGTUA_EXTRA, parentsWali);
                NavHostFragment.findNavController(this).navigate(R.id.action_bottomDialogButton_to_editDataOrtuFragment, mBundleEdit);
                break;
            }

        }
    }
}
