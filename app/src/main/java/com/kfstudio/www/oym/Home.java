package com.kfstudio.www.oym;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Home extends AppCompatActivity{

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    RecyclerView recyclerView;
    String us_name="Loading";
    String us_email="Loading";
    String us_dob="Loading";
    String us_gender="Loading";
    int poll_vote=0;
    Uri downloadUri;
    private File actualImage;
    int poll_vote_1=0;
    int poll_vote_2=0;
    int poll_vote_3=0;

    Uri uriTask1,uriTask2,uriTask3,uriTask_profile,uriTask4;
    private File compressedImage;
    private File compressedImage1=null;
    private File compressedImage2=null;
    private File compressedImage3=null;
    private File compressedImage4=null;
    private File compressedImage5=null;
    private FirebaseAuth mAuth;
    ArrayList<String> arrayList;
    Uri poll_profile_image;
    DocumentReference documentReference;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference dbr = db.collection("Photographer");
    String currentDateTimeString;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    Uri us_profile_image_url;
    int calling;
    Dialog dialog;
    String email="Loading..";
    private int GALLERY = 1;
    String dob="Loading";
    String gender="Male";
    Map<String,Object> photo;
    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;
    EditText ph_name,ph_email,ph_phone,ph_location,ph_experience,ph_rating,ph_website,ph_description;
    TextView ph_nametv,ph_emailtv,ph_phonetv,ph_locationtv,ph_experiencetv,ph_ratingtv,ph_websitetv,ph_descriptiontv;
    String ph_name_string,ph_email_string,ph_phone_string,ph_location_string,ph_experience_string,ph_rating_string,ph_website_string,ph_description_string;
    Button publish,publishtv;
    CircularImageView ph_profile_image,ph_profile_imagetv;
    ImageView ph_image_1_upload,ph_image_2_upload,ph_image_3_upload,ph_image_4_upload;
    ImageView ph_image_1_uploadtv,ph_image_2_uploadtv,ph_image_3_uploadtv,ph_image_4_uploadtv;
    LinearLayout parentll;
    FirebaseUser currentUser;

    private PhotographerAdapter photograperAdapter;
    int clicked=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bottomAppBar=findViewById(R.id.bottom_app_bar);
        floatingActionButton = findViewById(R.id.fab);
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        //main line for setting menu in bottom app bar
        setSupportActionBar(bottomAppBar);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database(currentUser.getPhoneNumber());
        if (bundle != null) {

            us_profile_image_url = Uri.fromFile(new File(bundle.getString("image")));
        }else{
            us_profile_image_url = Uri.parse(getURLForResource(R.drawable.blank_profile_picture));
            downloadimage(currentUser.getPhoneNumber());
        }
        setUpRecyclerView();
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(us_name.equals("Loading")){
//                    downloadimage(currentUser.getPhoneNumber());
                    database(currentUser.getPhoneNumber());
                }
                Intent intent = new Intent(Home.this, Register_Activity.class);
                Bundle extras = new Bundle();
                extras.putString("Name", us_name);
                extras.putString("Email", us_email);
                extras.putString("Dob", us_dob);
                extras.putString("Gender", us_gender);
                extras.putString("Image", us_profile_image_url.toString());
                extras.putString("phoneNumber", currentUser.getPhoneNumber());
                extras.putBoolean("Main",true);
                intent.putExtras(extras);
                startActivity(intent);

                return false;
            }

        });
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open bottom sheet
                BottomSheetDialogFragment bottomSheetDialogFragment = BottomSheetNavigationFragment.newInstance(us_name,currentUser.getPhoneNumber(),us_gender,us_email,us_profile_image_url.toString());
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(Home.this);
                dialog.setContentView(R.layout.photographer_dialog);
              dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                ph_name =dialog.findViewById(R.id.ph_name_upload);
                ph_email =dialog.findViewById(R.id.ph_email_upload);
                ph_phone =dialog.findViewById(R.id.ph_phone_upload);
                ph_location=dialog.findViewById(R.id.ph_location_upload);
                ph_experience= dialog.findViewById(R.id.ph_experience_upload);
                ph_rating=dialog.findViewById(R.id.ph_rating_upload);
                ph_website=dialog.findViewById(R.id.ph_website_upload);
                ph_description=dialog.findViewById(R.id.ph_description_upload);
                publish=dialog.findViewById(R.id.ph_publish_upload);
                ph_profile_image=dialog.findViewById(R.id.ph_profile_image_upload);
                ph_image_1_upload=dialog.findViewById(R.id.ph_image_1_upload);
                ph_image_2_upload=dialog.findViewById(R.id.ph_image_2_upload);
                ph_image_3_upload=dialog.findViewById(R.id.ph_image_3_upload);
                ph_image_4_upload=dialog.findViewById(R.id.ph_image_4_upload);
                ph_profile_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calling =1;
                        choosePhotoFromGallary();
                    }
                });
                 ph_image_1_upload.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         calling =2;
                         choosePhotoFromGallary();
                     }
                 });
                ph_image_2_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calling =3;
                        choosePhotoFromGallary();
                    }
                });
                ph_image_3_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calling =4;
                        choosePhotoFromGallary();
                    }
                });
                ph_image_4_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calling =5;
                        choosePhotoFromGallary();
                    }
                });
                publish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ph_name_string =ph_name.getText().toString().trim();
                        ph_email_string = ph_email.getText().toString().trim();
                        ph_experience_string = ph_experience.getText().toString().trim();
                        ph_phone_string = ph_phone.getText().toString().trim();
                        ph_location_string =ph_location.getText().toString().trim();
                        ph_rating_string = ph_rating.getText().toString().trim();
                        ph_description_string = ph_description.getText().toString().trim();
                        ph_website_string = ph_website.getText().toString().trim();

                        if (!(TextUtils.isEmpty(ph_name_string)||
                              TextUtils.isEmpty(ph_email_string)||
                              TextUtils.isEmpty(ph_experience_string)||
                                TextUtils.isEmpty(ph_phone_string)||
                                TextUtils.isEmpty(ph_location_string)||
                                TextUtils.isEmpty(ph_rating_string)||
                                TextUtils.isEmpty(ph_description_string)||
                                TextUtils.isEmpty(ph_website_string)||
                                compressedImage1==null||
                                compressedImage2==null||
                                compressedImage3==null||
                                compressedImage4==null||
                                compressedImage5==null)) {
//                            if (compressedImage == null) {
//                                new Compressor(getApplicationContext())
//                                        .setMaxWidth(60)
//                                        .setMaxHeight(60)
//                                        .setQuality(50)
//                                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                                        .compressToFileAsFlowable(new File(getURLForResource(R.drawable.blank_profile_picture)))
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe(new Consumer<File>() {
//                                            @Override
//                                            public void accept(File file) {
//                                                compressedImage = file;
//                                            }
//                                        }, new Consumer<Throwable>() {
//                                            @Override
//                                            public void accept(Throwable throwable) {
//                                                throwable.printStackTrace();
//                                                showError(throwable.getMessage());
//                                            }
//                                        });
//                            }

                            uploadPoll(ph_name_string,
                                    ph_phone_string,ph_email_string,ph_experience_string,
                                    ph_location_string,ph_rating_string,
                                    ph_website_string,ph_description_string);
                            uploadImage("ph_profile_image_url");
                            uploadImage("ph_image_1");
                            uploadImage("ph_image_2");
                            uploadImage("ph_image_3");
                            uploadImage("ph_image_4");

                            dialog.dismiss();
                        }else{
                            Toast.makeText(Home.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                dialog.show();
            }


        });
    }

    private void setUpRecyclerView() {
        Query query = dbr.orderBy("ph_time", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Photographer> options = new FirestoreRecyclerOptions.Builder<Photographer>()
                .setQuery(query,Photographer.class).build();
        photograperAdapter = new PhotographerAdapter(options);
        recyclerView= findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(photograperAdapter);

        photograperAdapter.setOnItemClickListener(new PhotographerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                dialog = new Dialog(Home.this);
                dialog.setContentView(R.layout.photographer_profile);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                ph_nametv =dialog.findViewById(R.id.ph_name_upload1);
                ph_emailtv =dialog.findViewById(R.id.ph_email_upload1);
                ph_phonetv =dialog.findViewById(R.id.ph_phone_upload1);
                ph_locationtv=dialog.findViewById(R.id.ph_location_upload1);
                ph_experiencetv= dialog.findViewById(R.id.ph_experience_upload1);
                ph_ratingtv=dialog.findViewById(R.id.ph_rating_upload1);
                ph_websitetv=dialog.findViewById(R.id.ph_website_upload1);
                ph_descriptiontv=dialog.findViewById(R.id.ph_description_upload1);
                publishtv=dialog.findViewById(R.id.ph_publish_upload1);
                ph_profile_imagetv=dialog.findViewById(R.id.ph_profile_image_upload1);
                ph_image_1_uploadtv=dialog.findViewById(R.id.ph_image_1_upload1);
                ph_image_2_uploadtv=dialog.findViewById(R.id.ph_image_2_upload1);
                ph_image_3_uploadtv=dialog.findViewById(R.id.ph_image_3_upload1);
                ph_image_4_uploadtv=dialog.findViewById(R.id.ph_image_4_upload1);
                ph_nametv.setText(documentSnapshot.getString("ph_name"));
                ph_emailtv.setText(documentSnapshot.getString("ph_email"));
                ph_phonetv .setText(documentSnapshot.getString("ph_phone_number"));
                ph_locationtv.setText(documentSnapshot.getString("ph_location"));
                ph_experiencetv.setText(documentSnapshot.getString("ph_experience")+" yrs");
                ph_ratingtv.setText(documentSnapshot.getString("ph_rating")+"/5");
                ph_websitetv .setText(documentSnapshot.getString("ph_website"));
                ph_descriptiontv.setText(documentSnapshot.getString("ph_description"));
                publishtv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Home.this, ph_nametv.getText().toString()+" will contact you!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                Glide.with(getApplicationContext()).load(documentSnapshot.getString("ph_profile_image_url"))
                        .apply(new RequestOptions().placeholder(R.drawable.progress_animation))
                        .apply(RequestOptions.centerCropTransform())
                        .into(ph_profile_imagetv);
                arrayList = (ArrayList<String>) documentSnapshot.get("ph_photo");
                int i =  1;
                ImageView imageView = null;
                for(String item : arrayList) {
                    switch (i) {
                       case 1: imageView = ph_image_1_uploadtv;
                           break;
                        case 2:imageView = ph_image_2_uploadtv;
                            break;
                        case 3:imageView = ph_image_3_uploadtv;
                            break;
                        case 4:imageView = ph_image_4_uploadtv;
                            break;
                    }if(imageView!=null) {
                        Glide.with(getApplicationContext()).load(item)
                                .apply(new RequestOptions().placeholder(R.drawable.progress_animation))
                                .apply(RequestOptions.centerCropTransform())
                                .into(imageView);
                    }
                    i++;
                }
                dialog.show();

               }
        });
    }

    private void uploadPoll(String up_name, String up_phone, String up_email, String up_experience,
                            String up_location,String up_rating, String up_website, String up_description) {
        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Map<String,Object> photographer = new HashMap<>();
        photographer.put("ph_name",up_name);
        photographer.put("ph_email",up_email);
        photographer.put("ph_phone_number",up_phone);
        photographer.put("ph_experience",up_experience);
        photographer.put("ph_location",up_location);
        photographer.put("ph_rating",up_rating);
        photographer.put("ph_time",currentDateTimeString);
        photographer.put("ph_description",up_description);
        photographer.put("ph_website",up_website);
        db.collection("Photographer").document(currentDateTimeString).set(photographer);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottomappbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private  void database(final String phone){

        documentReference = db.collection("OYM").document("Users")
                .collection(phone).document("ProfileInformation");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    us_name = documentSnapshot.getString("us_name");
                    us_email = documentSnapshot.getString("us_email");
                    us_dob = documentSnapshot.getString("us_dob");
                    us_gender = documentSnapshot.getString("us_gender");

                }
            }
        });

    }
//    private void downloadimage(String phone){
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReferenceFromUrl("gs://voteapp-master-8201e.appspot.com/"+phone+"/");
//        storageReference.child("profile_image").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                image = uri;
//
//            }
//        });

  //  }
    public void choosePhotoFromGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                try {
                    actualImage = FileUtil.from(this, data.getData());
                    customCompressImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void customCompressImage() {
        if (actualImage == null) {
            showError("Please choose an image!");
        } else {
            new Compressor(this)
                    .setMaxWidth(80)
                    .setMaxHeight(80)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFileAsFlowable(actualImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) {
                            switch (calling) {
                                case 1: compressedImage1 = file;
                                Glide.with(getApplicationContext())
                                        .load(compressedImage1)
                                        .apply(new RequestOptions().placeholder(R.drawable.progress_animation))
                                        .apply(RequestOptions.centerCropTransform())
                                        .into(ph_profile_image);
                                break;
                                case 2: compressedImage2 = file;
                                    Glide.with(getApplicationContext())
                                            .load(compressedImage2)
                                            .apply(new RequestOptions().placeholder(R.drawable.progress_animation))
                                            .apply(RequestOptions.centerCropTransform())
                                            .into(ph_image_1_upload);
                                    break;
                                case 3: compressedImage3 = file;
                                    Glide.with(getApplicationContext())
                                            .load(compressedImage3)
                                            .apply(new RequestOptions().placeholder(R.drawable.progress_animation))
                                            .apply(RequestOptions.centerCropTransform())
                                            .into(ph_image_2_upload);
                                    break;
                                case 4: compressedImage4 = file;
                                    Glide.with(getApplicationContext())
                                            .load(compressedImage4)
                                            .apply(new RequestOptions().placeholder(R.drawable.progress_animation))
                                            .apply(RequestOptions.centerCropTransform())
                                            .into(ph_image_3_upload);
                                    break;
                                case 5: compressedImage5 = file;
                                    Glide.with(getApplicationContext())
                                            .load(compressedImage5)
                                            .apply(new RequestOptions().placeholder(R.drawable.progress_animation))
                                            .apply(RequestOptions.centerCropTransform())
                                            .into(ph_image_4_upload);
                                    break;

                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            throwable.printStackTrace();
                            showError(throwable.getMessage());
                        }
                    });
        }
    }
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
    private void uploadImage(final String name) {
            switch (name) {
                case "ph_profile_image_url":
                    if (compressedImage1 != null) {
                        compressedImage = compressedImage1;
                    }
                    break;
                case "ph_image_1":
                    if (compressedImage2 != null) {
                        compressedImage = compressedImage2;
                    }

                    break;
                case "ph_image_2":
                    if (compressedImage3 != null) {
                        compressedImage = compressedImage3;
                    }

                    break;
                case "ph_image_3":
                    if (compressedImage4 != null) {
                        compressedImage = compressedImage4;
                    }

                    break;
                case "ph_image_4":
                    if (compressedImage5 != null) {
                        compressedImage = compressedImage5;
                    }

                    break;
            }
            if (!name.isEmpty()) {
                storageReference = storage.getReferenceFromUrl
                        ("gs://oym-1e52d.appspot.com/"
                                + currentDateTimeString + "/");
                final StorageReference ref = storageReference.child(name);
                if (compressedImage != null) {
                    ref.putFile(Uri.fromFile(compressedImage)).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadUri = task.getResult();
                                photo = new HashMap<>();
                                switch (name) {
                                    case "ph_profile_image_url":
                                        uriTask_profile = downloadUri;
                                        photo.put("ph_profile_image_url", uriTask_profile.toString());
                                        db.collection("Photographer").document(currentDateTimeString).update(photo);
                                        break;
                                    case "ph_image_1":
                                        uriTask1 = downloadUri;
                                        photo.put("ph_photo", FieldValue.arrayUnion(uriTask1.toString()));
                                        db.collection("Photographer").document(currentDateTimeString).update(photo);
                                        break;
                                    case "ph_image_2":
                                        uriTask2 = downloadUri;
                                        photo.put("ph_photo", FieldValue.arrayUnion(uriTask2.toString()));
                                        db.collection("Photographer").document(currentDateTimeString).update(photo);
                                        break;
                                    case "ph_image_3":
                                        uriTask3 = downloadUri;
                                        photo.put("ph_photo", FieldValue.arrayUnion(uriTask3.toString()));
                                        db.collection("Photographer").document(currentDateTimeString).update(photo);
                                        break;
                                    case "ph_image_4":
                                        uriTask4 = downloadUri;
                                        photo.put("ph_photo", FieldValue.arrayUnion(uriTask4.toString()));
                                        db.collection("Photographer").document(currentDateTimeString).update(photo);
                                        break;
                                }
                            }
                        }
                    });
                }
            }
    }
//    private void uploadProfile(){
//        storageReference = storage.getReferenceFromUrl("gs://oym-1e52d.appspot.com/" + currentUser.getPhoneNumber() + "/");
//            final StorageReference ref = storageReference.child("profile_picture");
//            ref.putFile(image).continueWith(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if (!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//                    return ref.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Task<Uri>>() {
//                @Override
//                public void onComplete(@NonNull Task<Task<Uri>> task) {
//                    if(task.isSuccessful()) {
//                        Map<String,Object> user = new HashMap<>();
//                        user.put("profile_image", task.getResult().toString());
//                        db.collection("Vote").document("Users")
//                                .collection(currentUser.getPhoneNumber()).document("ProfileInformation")
//                                .update(user);
//
//                    }
//                }
//            });
//
//    }
    @Override
    protected void onResume() {
        super.onResume();
        photograperAdapter.startListening();
    }
    @Override
    protected void onStart(){
        super.onStart();
        photograperAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        photograperAdapter.stopListening();
    }
    @Override
    protected void onPause(){
        super.onPause();
        photograperAdapter.stopListening();
    }

    private void downloadimage(String phone){
        storageReference = storage.getReferenceFromUrl("gs://oym-1e52d.appspot.com/"+phone+"/");
        storageReference.child("profile_picture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                    us_profile_image_url = uri;
            }
        });

    }

    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }
}