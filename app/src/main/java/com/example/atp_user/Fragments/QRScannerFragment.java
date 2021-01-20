package com.example.atp_user.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.example.atp_user.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRScannerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRScannerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CodeScanner mCodeScanner;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QRScannerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QRScannerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QRScannerFragment newInstance(String param1, String param2) {
        QRScannerFragment fragment = new QRScannerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(view.getContext(), scannerView);
        mCodeScanner.setDecodeCallback(result -> getActivity().runOnUiThread(() -> {
            Toast.makeText(view.getContext(), result.getText(), Toast.LENGTH_SHORT).show();
        }));

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onResume() {
        mCodeScanner.startPreview();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        mCodeScanner.releaseResources();
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_scanner, container, false);
    }
}