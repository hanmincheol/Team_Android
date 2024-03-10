package com.ict.tablayoutviewpager16.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.ict.tablayoutviewpager16.ApiService;
import com.ict.tablayoutviewpager16.LocalStorage;
import com.ict.tablayoutviewpager16.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Content3 extends Fragment {
    private ImageView addPhotoImageView;
    private EditText explainEditText;
    private Button uploadButton;
    private Uri selectedImageUri;
    private Bitmap capturedBitmap;
    private Context context;
    private File capturedImageFile;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content3_layout, container, false);
        addPhotoImageView = view.findViewById(R.id.addphoto_image);
        explainEditText = view.findViewById(R.id.addphoto_edit_explain);
        uploadButton = view.findViewById(R.id.addphoto_btn_upload);

        context = getActivity();

        // 갤러리에서 이미지 선택 버튼 클릭 리스너 설정
        view.findViewById(R.id.addphoto_btn_gallery).setOnClickListener(v -> {
            pickImageFromGallery();
        });

        // 카메라로 사진 찍기 버튼 클릭 리스너 설정
        view.findViewById(R.id.addphoto_btn_camera).setOnClickListener(v -> {
            captureImageWithCamera();
        });

        // 사진 업로드 버튼 클릭 리스너 설정
        uploadButton.setOnClickListener(v -> {
            sendImageToServer();
        });

        // 갤러리 런처 등록
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        capturedImageFile = new File(getRealPathFromURI(selectedImageUri));
                        try {
                            InputStream is = requireContext().getContentResolver().openInputStream(selectedImageUri);
                            capturedBitmap = BitmapFactory.decodeStream(is);
                            is.close();
                            addPhotoImageView.setImageBitmap(capturedBitmap);
                            uploadButton.setEnabled(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("Content3", "pickImageFromGallery() result code: " + result.getResultCode());
                });

        return view;
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = requireActivity().getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = ((Cursor) cursor).getString(column_index);
        cursor.close();
        return path;
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
        Log.d("Content3", "pickImageFromGallery() called");
    }

    private void captureImageWithCamera() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String photoFileName = dateFormat.format(new Date()) + "_camera.png";

        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_DCIM);
        try {
            capturedImageFile = File.createTempFile(photoFileName, ".png", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (capturedImageFile != null) {
            selectedImageUri = FileProvider.getUriForFile(requireContext(), "com.ict.tablayoutviewpager16.fileprovider", capturedImageFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri);
            cameraLauncher.launch(intent);
            Log.d("Content3", "captureImageWithCamera() called");
        } else {
            Log.e("Content3", "Failed to create image file");
        }
    }

    private void sendImageToServer() {
        if (capturedImageFile == null || !capturedImageFile.exists()) {
            Toast.makeText(requireContext(), "이미지를 선택하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        // 텍스트 데이터 준비
        String explainText = explainEditText.getText().toString();
        String username = LocalStorage.getUsername(context);

        // 텍스트 데이터를 RequestBody로 변환
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody content = RequestBody.create(MediaType.parse("text/plain"), explainText);
        RequestBody hashTag = RequestBody.create(MediaType.parse("text/plain"), "#안드로이드");
        RequestBody disclosureYN = RequestBody.create(MediaType.parse("text/plain"), "Y");
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "4");

        // 이미지 파일 준비
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), capturedImageFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("files", capturedImageFile.getName(), requestFile);

        // Retrofit 초기화
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Retrofit을 사용하여 API 호출
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Integer> call = apiService.uploadImageAndText(id, filePart, content, hashTag, disclosureYN, type);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    int affected = response.body();
                    Toast.makeText(requireContext(), "이미지가 업로드되었습니다. 영향을 받은 행 수: " + affected, Toast.LENGTH_SHORT).show();
                    Log.d("Content3", "sendImageToServer() called. Affected rows: " + affected);
                    // 이미지 전송 후 값 초기화
                    capturedImageFile = null;
                    selectedImageUri = null;
                    capturedBitmap = null;
                    explainEditText.setText("");
                    addPhotoImageView.setImageResource(R.drawable.done_icon);
                    uploadButton.setEnabled(false);
                } else {
                    Toast.makeText(requireContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                    Log.e("Content3", "Failed to upload image. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(requireContext(), "서버와 통신 중 오류 발생", Toast.LENGTH_SHORT).show();
                Log.e("Content3", "Error during image upload: " + t.getMessage());
            }
        });
    }

    ActivityResultLauncher<Intent> galleryLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        capturedImageFile = new File(getRealPathFromURI(selectedImageUri));
                        try {
                            InputStream is = requireContext().getContentResolver().openInputStream(selectedImageUri);
                            capturedBitmap = BitmapFactory.decodeStream(is);
                            is.close();
                            addPhotoImageView.setImageBitmap(capturedBitmap);
                            uploadButton.setEnabled(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("Content3", "pickImageFromGallery() result code: " + result.getResultCode());
                });

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            InputStream is = requireContext().getContentResolver().openInputStream(selectedImageUri);
                            capturedBitmap = BitmapFactory.decodeStream(is);
                            is.close();
                            addPhotoImageView.setImageBitmap(capturedBitmap);
                            uploadButton.setEnabled(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("Content3", "captureImageWithCamera() result code: " + result.getResultCode());
                });
    }
}
