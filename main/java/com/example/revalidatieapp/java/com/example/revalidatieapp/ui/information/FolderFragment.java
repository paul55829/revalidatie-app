package com.example.revalidatieapp.ui.information;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.revalidatieapp.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class FolderFragment extends Fragment {

    private static final int PERMISSION_STORAGE_CODE = 1000;
    private String TAG = "FolderFragment";
    private Uri url;
    private String name;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_folder, container, false);
        Log.d(TAG, "test(3) FolderFragment begint nu");

        Bundle bundle = getArguments();
        String[] info = bundle.getStringArray("com.example.revalidatieapp.INFO");

        url = Uri.parse(info[0]);
        name = info[1];

        Log.d(TAG, "test(3) this is the url: " + url);

        //handles runtime permission for OS Marshmallow and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //permission denied, request it
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //show opup for runtime permission
                requestPermissions(permissions, PERMISSION_STORAGE_CODE);
            }
            else {
                //permission already granted, perform download
                startDownloading();
            }
        }
        else {
            //system os is less than marshmallow, perform download
            startDownloading();
    }


        PDFView pdfView = (PDFView) root.findViewById(R.id.folder);

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Log.d(TAG, "test(4) dit is Environment.DIRECTORY_DOWNLOADS: " + Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, name + ".pdf");
        Log.d(TAG, "test(4) dit is de file: " + file);
        pdfView.fromFile(file).load();
        Log.d(TAG, "test(4) fromFile method is geweest.");

        return root;
    }

    private void startDownloading(){

        //create download request
        DownloadManager.Request request = new DownloadManager.Request(url);

        //allow types of network to download file
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

        //set title and description in download notification
        request.setTitle("Download");
        request.setDescription("Downloading the folder....");

        request.allowScanningByMediaScanner();;
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name + ".pdf"); //gives current time as file name

        DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }


    //handles permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted from popup, perform download
                    startDownloading();
                }
                else {
                    Toast.makeText(getContext(), "cannot load folder", Toast.LENGTH_SHORT);
                }
            }
        }
    }
}
