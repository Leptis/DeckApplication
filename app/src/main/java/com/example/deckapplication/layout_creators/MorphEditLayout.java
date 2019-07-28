package com.example.deckapplication.layout_creators;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.deckapplication.R;

public class MorphEditLayout {

    ViewGroup parent;
    ViewGroup view_root;

    EditText        text_name;
    EditText        text_text;
    Switch          skip_show;
    EditText        text_duration;
    EditText        text_search;
    Button          button_edit;
    RecyclerView    list_pool;

    public MorphEditLayout(ViewGroup parent){
        this.parent = parent;
        onCreate();
    }
    void onCreate(){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view_root = ((ViewGroup) inflater.inflate(R.layout.layout_morph_edit, parent, false));

        setEnabledRequrse(parent, false);
        parent.addView(view_root);

        text_name = view_root.findViewById(R.id.morph_edit_text_name);
        text_text = view_root.findViewById(R.id.morph_edit_text_text);
        skip_show = view_root.findViewById(R.id.morph_edit_switch_skip_show);
        text_duration = view_root.findViewById(R.id.morph_edit_text_duration);
        text_search = view_root.findViewById(R.id.morph_edit_text_search);
        button_edit = view_root.findViewById(R.id.morph_edit_button_edit);
        list_pool = view_root.findViewById(R.id.morph_edit_list_pool);


    }

    void onDestroy(){
        parent.removeView(view_root);
        setEnabledRequrse(parent, true);

    }

    void setEnabledRequrse(View v, boolean enabled){
        v.setEnabled(enabled);
        if (v instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                View c = ((ViewGroup) v).getChildAt(i);
                setEnabledRequrse(c, enabled);
            }
        }
    }

    interface OnMorphEditListener{

    }

}
