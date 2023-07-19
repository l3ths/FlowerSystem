package com.example.flowersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    ImageView ivLogout;
    ImageView ivShopLocate;
    ImageView ivOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        etNotiTitle = findViewById(R.id.etNotiTitle);
        etNotiBody = findViewById(R.id.etNotiBody);
        btnSendNoti = findViewById(R.id.btnSendNoti);
        ivLogout = findViewById(R.id.ivPersonal);
        ivShopLocate = findViewById(R.id.ivLove);
        ivOrders = findViewById(R.id.ivOrders);
        ivOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendNotificationActivity.this, OrderManagerActivity.class);
                startActivity(intent);
            }
        });

        ivShopLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendNotificationActivity.this, ShopInformationActivity.class);
                startActivity(intent);
            }
        });

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendNotificationActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
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