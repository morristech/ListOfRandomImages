package omg.medvedomg.listofrandomimages1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import omg.medvedomg.listofrandomimages1.R;
import omg.medvedomg.listofrandomimages1.model.Image;
import omg.medvedomg.listofrandomimages1.model.ImageResponse;
import omg.medvedomg.listofrandomimages1.recyclerviewadapter.ImageAdapter;
import omg.medvedomg.listofrandomimages1.rest.ApiClient;
import omg.medvedomg.listofrandomimages1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = "TAG";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Call<ImageResponse> call = apiService.getImageList();
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                Log.d("TAG", "in onResponse ");
                int statusCode = response.code();
                Log.d("TAG", "response.code()" + response.code());

                ArrayList<Image> images = response.body().getImageList();

                Log.d(TAG, "images downloaded" + images.get(3).getId());

                recyclerView.setAdapter(new ImageAdapter(MainActivity.this,images));
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d("TAG", "image dowload fail");
            }
        });

    }
}