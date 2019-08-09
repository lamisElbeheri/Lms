package com.neon.lms.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class MyPurchaseListModel extends BaseObservable {
    private String noDataLable, noDataMessage, noDataButtonText;
    private ArrayList<MyPurchaseModel> arrayList;
    private boolean apiCallActive = false;
    private int page, count;
    private boolean isSwipeRefress;
    private boolean isAddTo;
    private String searchTxt;
    private Drawable noDataImage;

    public Drawable getNoDataImage() {
        return noDataImage;
    }

    public void setNoDataImage(Drawable noDataImage) {
        this.noDataImage = noDataImage;
        notifyChange();
    }

    public String getNoDataLable() {
        return noDataLable;
    }

    public void setNoDataLable(String noDataLable) {
        this.noDataLable = noDataLable;
    }

    public String getNoDataMessage() {
        return noDataMessage;
    }

    public void setNoDataMessage(String noDataMessage) {
        this.noDataMessage = noDataMessage;
    }

    public String getNoDataButtonText() {
        return noDataButtonText;
    }

    public void setNoDataButtonText(String noDataButtonText) {
        this.noDataButtonText = noDataButtonText;
    }

    public ArrayList<MyPurchaseModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<MyPurchaseModel> arrayList) {
        this.arrayList = arrayList;
    }

    @Bindable
    public boolean isApiCallActive() {
        return apiCallActive;
    }

    public void setApiCallActive(boolean apiCallActive) {
        this.apiCallActive = apiCallActive;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSwipeRefress() {
        return isSwipeRefress;
    }

    public void setSwipeRefress(boolean swipeRefress) {
        isSwipeRefress = swipeRefress;
    }

    public boolean isAddTo() {
        return isAddTo;
    }

    public void setAddTo(boolean addTo) {
        isAddTo = addTo;
    }

    public String getSearchTxt() {
        return searchTxt;
    }

    public void setSearchTxt(String searchTxt) {
        this.searchTxt = searchTxt;
    }


}
