package org.communitycookerfoundation.communitycookerfoundation.ui.success;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SuccessViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SuccessViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}