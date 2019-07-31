package com.neon.lms.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.neon.lms.R;
import com.neon.lms.activity.CartListActivity;
import com.neon.lms.activity.CheckoutActivity;
import com.neon.lms.activity.FaqListActivity;
import com.neon.lms.activity.InvoiceActivity;
import com.neon.lms.activity.MassageListActivity;
import com.neon.lms.activity.ShopListActivity;
import com.neon.lms.activity.TeacherListActivity;
import com.neon.lms.adapter.HomeAdapter;
import com.neon.lms.basecomponent.BaseFragment;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.FragmentHomeBinding;
import com.neon.lms.model.HomeListModel;
import com.neon.lms.model.HomeModel;
import com.neon.lms.model.ShopListModel;
import com.neon.lms.util.Constants;

import java.util.ArrayList;

public class FragmentHome extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "Shop";
    public HomeListModel model;
    private FragmentHomeBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        model = new HomeListModel();
        model.setArrayList(new ArrayList<HomeModel>());
        binding.setHomeListModel(model);
        initView();
        return binding.getRoot();

    }

    /*
     * Init Method
     * */
    private void initView() {
        initRecycler();
        fillArraylist();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }

    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.recyclerView.setAdapter(new HomeAdapter(getActivity(),
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                switch (type) {
                    case Constants.ROW_CLICK:
                        switch (position) {
                            case 0:
                                openCartList();
                                break;
                            case 1:
                                openShopList();
                                break;
                            case 2:
                                openCheckout();
                                break;
                            case 4:
                                openTeacherList();
                                break;
                            case 5:
                                openFaqList();
                                break;
                            case 3:
                                openMessageListList();
                                break;
                            case 6:
                                openInvoiceListList();
                                break;
                        }

                        break;
                }
            }
        }));


    }


    private void openCartList() {
        startActivity(new Intent(getContext(), CartListActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openShopList() {
        startActivity(new Intent(getContext(), ShopListActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openCheckout() {
        startActivity(new Intent(getContext(), CheckoutActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openTeacherList() {
        startActivity(new Intent(getContext(), TeacherListActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openFaqList() {
        startActivity(new Intent(getContext(), FaqListActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openMessageListList() {
        startActivity(new Intent(getContext(), MassageListActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openInvoiceListList() {
        startActivity(new Intent(getContext(), InvoiceActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void fillArraylist() {
        model.getArrayList().clear();
        String[] fName = {getString(R.string.latest),
                getString(R.string.trending),
                getString(R.string.featured),
                getString(R.string.our),
                getString(R.string.our),
                getString(R.string.asked),
                getString(R.string.browse),
                getString(R.string.why),
                getString(R.string.our),
                getString(R.string.contect),

        };
        String[] lname = {getString(R.string.news),
                getString(R.string.courses),
                getString(R.string.course),
                getString(R.string.testimonial),
                getString(R.string.teacher),
                getString(R.string.question),
                getString(R.string.courses),
                getString(R.string.us),
                getString(R.string.sponser),
                getString(R.string.us),

        };
        int[] image = {R.drawable.newspaper,
                R.drawable.book,
                R.drawable.course,
                R.drawable.testimonial,
                R.drawable.professor,
                R.drawable.conversation,
                R.drawable.knowledge,
                R.drawable.information,
                R.drawable.knowledge,
                R.drawable.hand,
                R.drawable.contact,

        };


        for (int i = 0; i < fName.length; i++) {
            HomeModel itemModel = new HomeModel();
            itemModel.setfName(fName[i]);
            itemModel.setlName(lname[i]);
            itemModel.setImage(image[i]);
            model.getArrayList().add(itemModel);

        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }

}
