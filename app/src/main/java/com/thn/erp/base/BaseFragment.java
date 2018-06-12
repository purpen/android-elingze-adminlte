package com.thn.erp.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thn.basemodule.tools.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


public abstract class BaseFragment extends Fragment {
    protected final String TAG = getClass().getSimpleName();
    protected FragmentActivity activity;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LogUtil.e("onCreate()" + TAG);
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(),null);
        unbinder=ButterKnife.bind(this, view);

        return view;
    }

    protected abstract int getLayout();

    protected void initView() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        requestNet();
        installListener();
    }

    protected void installListener() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected  void requestNet(){

    }


//    protected void refreshUI() {
//
//    }
//
//    protected void refreshUI(List<T> list) {
//
//    }

    @Override
    public void onDestroyView() {
        clearNet();
        super.onDestroyView();
        unbinder.unbind();
    }

    protected void addNet(Call httpHandler) {
        if (handlerList == null) {
            handlerList = new ArrayList<>();
        }
        handlerList.add(httpHandler);
    }

    private void clearNet() {
        if (handlerList != null) {
            for (Call httpHandler : handlerList) {
                if (httpHandler != null) {
                    httpHandler.cancel();
                }
            }
        }
    }

    private List<Call> handlerList;

}
