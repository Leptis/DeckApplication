package com.example.deckapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.deckapplication.norration.Norration;
import com.example.deckapplication.norration.adapters.NorrationAdapter;
import com.example.deckapplication.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int MENU_MODIFY = 0;
    final int MENU_DELETE = 1;

    private RecyclerView recycler_view_norrations;
    private NorrationAdapter norration_adapter;
    private Button button_add_norration;
    private Button button_settings;
    private EditText text_norration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> nor_files = getNorFileList();
        norration_adapter = new NorrationAdapter(nor_files);


        button_add_norration = findViewById(R.id.main_button_add_norration);
        button_settings = findViewById(R.id.main_button_settings);
        text_norration = findViewById(R.id.main_text_norration);

        recycler_view_norrations = findViewById(R.id.main_recycler_view_norrations);
        LinearLayoutManager layout_manager = new LinearLayoutManager(this);
        recycler_view_norrations.setLayoutManager(layout_manager);
        recycler_view_norrations.setHasFixedSize(true);
        recycler_view_norrations.setAdapter(norration_adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        norration_adapter.refreshList(getNorFileList());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case MENU_MODIFY:
                Intent intent = new Intent (this, NorrationActivity.class);
                int id = norration_adapter.context_menu_item_position;
                intent.putExtra("nor_file", norration_adapter.nor_files.get(id));
                startActivity(intent);
                return true;
            case MENU_DELETE:
//                norration_adapter.removeItem(norration_adapter.context_menu_item_position);
                deleteFile(norration_adapter.nor_files.get(norration_adapter.context_menu_item_position));
                norration_adapter.refreshList(getNorFileList());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onButtonCreateNorrationClick(View v){
        String name = text_norration.getText().toString();
        if (!name.isEmpty()){
            try {
                FileOutputStream file_output_stream = openFileOutput(name + ".nor", MODE_PRIVATE);
                ObjectOutputStream object_output_stream = new ObjectOutputStream(file_output_stream);
                object_output_stream.writeObject(new Norration(name));
                object_output_stream.close();
                file_output_stream.close();
            }catch(IOException e){

            }catch(Exception e){

            }
        }else{

        }
        norration_adapter.refreshList(getNorFileList());
    }

    ArrayList<String> getNorFileList() {
        String[] files = fileList();
        ArrayList<String> nor_files = new ArrayList<String>();

        for ( String fileName : files ) {
            if (fileName.matches(".+\\.nor")){
                nor_files.add(fileName);
            }
        }
        return nor_files;
    }



}
