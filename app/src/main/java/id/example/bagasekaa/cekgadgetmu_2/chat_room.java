package id.example.bagasekaa.cekgadgetmu_2;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class chat_room extends AppCompatActivity {

    // replace this with a real channelID from Scaledrone dashboard
    private String channelID = "";
    private String roomName = "";
    private EditText editText;
    private MessageAdapter messageAdapter;
    private ListView messagesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat_room);

        editText = (EditText) findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);


    }
    public void back(View view) {
        startActivity(new Intent(chat_room.this, chat.class));

    }

    public void send(View view) {

    }
}


