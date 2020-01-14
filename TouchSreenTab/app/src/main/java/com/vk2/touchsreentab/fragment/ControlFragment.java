package com.vk2.touchsreentab.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentcz;
import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;
import com.vk2.touchsreentab.model.TextSearch;
import com.vk2.touchsreentab.model.viewmodel.TextSearchViewModel;
import com.vk2.touchsreentab.view.MyKeyboard;

import java.util.Timer;
import java.util.TimerTask;


public class ControlFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private EditText edtSearch;
    private ImageView imgEnter;

    private TextSearchViewModel textSearchViewModel;
    private TextSearch textSearch;
    private Timer timer;
    private static final long DELAY = 1000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_control, container, false);
        initView();
        return view;
    }

    private void initView() {
        if (getActivity() == null) return;
        timer = new Timer();
        textSearchViewModel = ViewModelProviders.of(getActivity()).get(TextSearchViewModel.class);
        textSearch = new TextSearch();
        edtSearch = view.findViewById(R.id.edtSearch);
        imgEnter = view.findViewById(R.id.imgEnter);
        imgEnter.setOnClickListener(this);
        final ImageView imgClear = view.findViewById(R.id.imgClear);
        imgClear.setOnClickListener(this);
        view.findViewById(R.id.imgVocal).setOnClickListener(this);
        view.findViewById(R.id.imgReplay).setOnClickListener(this);
        view.findViewById(R.id.imgPlay).setOnClickListener(this);
        view.findViewById(R.id.imgNext).setOnClickListener(this);
        view.findViewById(R.id.imgReduce).setOnClickListener(this);
        view.findViewById(R.id.imgAdd).setOnClickListener(this);

        MyKeyboard myKeyboard = view.findViewById(R.id.myKeyboard);

        myKeyboard.setOnMyKeyboardListener(new MyKeyboard.OnMyKeyboardListener() {
            @Override
            public void onKey(final CharSequence chr, final String tag) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tag.equals(MyKeyboard.BTN_SPACE)) {
                                edtSearch.append(" ");
                            } else if (tag.equals(MyKeyboard.BTN_CLEAR)) {
                                Editable s = edtSearch.getText();
                                if (s != null && !s.toString().isEmpty()) {
                                    String str = s.toString().substring(0, s.length() - 1);
                                    edtSearch.setText(str);
                                }
                            } else {
                                edtSearch.append(chr);
                            }
                        }
                    });
            }
        });


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (imgEnter.getVisibility() == View.GONE) {
                                        if (TextUtils.isEmpty(editable)) {
                                            imgClear.setVisibility(View.GONE);
                                            if (Fragmentcz.getCurrentFragment() == Fragmentez.SEARCH_COMPLEX_FRAGMENT)
                                                gotoRecommend();
                                        } else {
                                            imgClear.setVisibility(View.VISIBLE);
                                            if (Fragmentcz.getCurrentFragment() != Fragmentez.SEARCH_COMPLEX_FRAGMENT)
                                                gotoComplex();
                                            submitSearchInput(editable);
                                        }
                                    }
                                }
                            });
                    }
                }, DELAY);
            }
        });
    }

    private void submitSearchInput(final CharSequence s) {
        if (textSearchViewModel == null || textSearch == null) return;
        textSearch.setFrg(Fragmentcz.getCurrentFragment());
        textSearch.setTextSearch(s.toString());
        textSearchViewModel.setTextSearch(textSearch);
    }

    @Override
    protected void showEnterButton() {
        if (getActivity() != null && imgEnter != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imgEnter.setVisibility(View.VISIBLE);
                }
            });
    }

    @Override
    protected void hideEnterButton() {
        if (getActivity() != null && imgEnter != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imgEnter.setVisibility(View.GONE);
                }
            });
    }

    @Override
    protected void clearTextSearch() {
        if (getActivity() != null && edtSearch != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    edtSearch.setText("");
                    imgEnter.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgVocal:
                Toast.makeText(getActivity(), "Button vocal click!",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.imgReplay:
                Toast.makeText(getActivity(), "Button replay click!",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.imgPlay:
                Toast.makeText(getActivity(), "Button play click!",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.imgNext:
                Toast.makeText(getActivity(), "Button next click!",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.imgReduce:
                Toast.makeText(getActivity(), "Button reduce click!",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.imgAdd:
                Toast.makeText(getActivity(), "Button add click!",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.imgClear:
                edtSearch.setText("");
                break;
            case R.id.imgEnter:
                submitSearchInput(edtSearch.getText());
                break;
        }
    }
}