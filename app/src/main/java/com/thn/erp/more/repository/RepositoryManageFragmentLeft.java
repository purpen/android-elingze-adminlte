package com.thn.erp.more.repository;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.common.ImpRecyclerViewItemClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RepositoryManageFragmentLeft extends BaseFragment {

    @BindView(R.id.recyclerView_repository_left)
    RecyclerView recyclerViewRepositoryLeft;
    Unbinder unbinder;

    private RepositoryRecyclerViewAdapter mRepositoryRecyclerViewAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_repository_manage_fragment_left;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void initView() {
        recyclerViewRepositoryLeft.setHasFixedSize(true);
        recyclerViewRepositoryLeft.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        recyclerViewRepositoryLeft.addItemDecoration(new ShopMarginDecoration(activity,R.dimen.dp5));
        mRepositoryRecyclerViewAdapter = new RepositoryRecyclerViewAdapter(getActivity(), new ImpRecyclerViewItemClick() {
            @Override
            public void click(int postion) {
//                Toast.makeText(getActivity(), "postion:" + postion, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LookRepositoryActivity.class));
            }
        });
        recyclerViewRepositoryLeft.setAdapter(mRepositoryRecyclerViewAdapter);
    }

    @Override
    protected void requestNet() {
        mRepositoryRecyclerViewAdapter.addList(generateListDatas());
    }

    private List<Map<String, String>> generateListDatas(){
        List<Map<String, String>> list = new ArrayList<>();
        for(int i = 0; i < 4; i ++) {
            Map<String, String> map = new HashMap<>();
            map.put(MapKeyInRepository.REPOSITORY_NAME, "仓库" + (i+1));
            map.put(MapKeyInRepository.REPOSITORY_MANAGER, "负责人" + (i+1));
            list.add(map);
        }
        return list;
    }
}
