package com.kfstudio.www.oym;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

public class BookingAdapter extends FirestoreRecyclerAdapter<Booking, BookingAdapter.BookingHolder> {
    private View v;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    DocumentReference documentReference;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Uri us_profile_image_url;
    private String us_phone;
    private String ph_name;
    private String ph_email;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BookingAdapter(@NonNull FirestoreRecyclerOptions<Booking> options,String us_phone) {
        super(options);
        this.us_phone=us_phone;
    }

    @Override
    protected void onBindViewHolder(@NonNull BookingHolder bookingHolder, int i, @NonNull Booking booking) {
    if(us_phone.equals(booking.getBooker_phone())){
      downloadimage(booking.getBooking_for());
      database(booking.getBooking_for());
      bookingHolder.name.setText(ph_name);
      bookingHolder.email.setText(ph_email);
      bookingHolder.rate.setText("₹"+booking.getBooking_rate()+"/hr");
      bookingHolder.time.setText(booking.getBooking_time());
      bookingHolder.phone.setText(booking.getBooking_for());
      if(booking.getBooking_done()){
          bookingHolder.onGoing.setText("COMPLETED");
      }else{
           bookingHolder.onGoing.setText("On Going");
      }
        Glide.with(v).load(us_profile_image_url)
                .apply(new RequestOptions().placeholder(R.drawable.progress_animation))
                .apply(RequestOptions.centerCropTransform())
                .into(bookingHolder.profile_image);

    }
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
        return new BookingHolder(v);
    }

    class BookingHolder extends RecyclerView.ViewHolder{
        TextView name,email,phone,time,rate;
        CircularImageView profile_image;
        TextView onGoing;
        public BookingHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name4);
            email=itemView.findViewById(R.id.email4);
            phone=itemView.findViewById(R.id.phone4);
            time=itemView.findViewById(R.id.time4);
            rate=itemView.findViewById(R.id.rate4);
            profile_image=itemView.findViewById(R.id.profile_image4);
            onGoing=itemView.findViewById(R.id.onGoing4);
        }
    }

    private void downloadimage(String phone){

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://oym-1e52d.appspot.com/"+phone+"/photographer/");
        storageReference.child("ph_profile_image_url").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                us_profile_image_url = uri;
                notifyDataSetChanged();
            }
        });

    }
    private  void database(final String phone){

        documentReference = db.collection("Photographer")
                .document(phone);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    ph_name = documentSnapshot.getString("ph_name");
                    ph_email = documentSnapshot.getString("ph_email");
                    notifyDataSetChanged();

                }
            }
        });

    }
}
