package com.example.tvlauncherapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppsAdapter adapter;
    private List<ResolveInfo> appsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.appsRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); //

        loadApps();
    }

    private void loadApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> availableApps = packageManager.queryIntentActivities(intent, 0);

        appsList = new ArrayList<>();


        for (ResolveInfo app : availableApps) {
            String packageName = app.activityInfo.packageName;

            if (packageName.equals("com.android.vending") || // Play Store
                    packageName.equals(getPackageName()) || // TVLauncherApp
                    packageName.equals("com.google.android.youtube.tv") ||
                    packageName.equals("com.netflix.ninja")) {
           s     appsList.add(app);
            }
        }

        adapter = new AppsAdapter(this, appsList);
        recyclerView.setAdapter(adapter);
    }
}
