package com.example.tvlauncherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.AppViewHolder> {

    private Context context;
    private List<ResolveInfo> apps;

    public AppsAdapter(Context context, List<ResolveInfo> apps) {
        this.context = context;
        this.apps = apps;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        final ResolveInfo app = apps.get(position);
        final PackageManager packageManager = context.getPackageManager();

        holder.icon.setImageDrawable(app.loadIcon(packageManager));
        holder.name.setText(app.loadLabel(packageManager));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = packageManager.getLaunchIntentForPackage(app.activityInfo.packageName);
                if (launchIntent != null) {
                    context.startActivity(launchIntent);
                } else {
                    // Agar ilovani ochib bo'lmasa foydalanuvchiga xabar beramiz
                    Toast.makeText(context, "Can't open this app", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    static class AppViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView name;

        AppViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.appIcon);
            name = itemView.findViewById(R.id.appName);
        }
    }
}
