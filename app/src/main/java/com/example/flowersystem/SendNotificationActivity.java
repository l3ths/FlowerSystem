package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flowersystem.api.NotificationApi;
import com.example.flowersystem.api.RetrofitClient;
import com.example.flowersystem.dto.MessageDTO;
import com.example.flowersystem.dto.NotificationDTO;
import com.example.flowersystem.notification.NotificationConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SendNotificationActivity extends AppCompatActivity {
    EditText etNotiTitle;
    EditText etNotiBody;
    Button btnSendNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        etNotiTitle = findViewById(R.id.etNotiTitle);
        etNotiBody = findViewById(R.id.etNotiBody);
        btnSendNoti = findViewById(R.id.btnSendNoti);
        btnSendNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNotiTitle.getText().toString())){
                    etNotiTitle.setError("*");
                    return;
                } else if (TextUtils.isEmpty(etNotiBody.getText().toString())) {
                    etNotiBody.setError("*");
                    return;
                }
                Retrofit retrofit = RetrofitClient.getInstance();
                NotificationApi api = retrofit.create(NotificationApi.class);
                NotificationDTO dto = new NotificationDTO();
                dto.setTitle(etNotiTitle.getText().toString());
                dto.setBody(etNotiBody.getText().toString());
                Call<MessageDTO> call = api.sendNotification(NotificationConstants.GENERAL_TOPIC,dto);
                call.enqueue(new Callback<MessageDTO>() {
                    @Override
                    public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                        clearInput();
                        Toast.makeText(SendNotificationActivity.this, "Gửi thông báo thành công!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MessageDTO> call, Throwable t) {
                        clearInput();
                        Toast.makeText(SendNotificationActivity.this, "Gửi thông báo thành công!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
    private void clearInput(){
        etNotiTitle.setText("");
        etNotiBody.setText("");
    }
}