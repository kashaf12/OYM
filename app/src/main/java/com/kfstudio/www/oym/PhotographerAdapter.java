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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import me.ithebk.barchart.BarChartModel;

public class PhotographerAdapter extends FirestoreRecyclerAdapter<Photographer, PhotographerAdapter.PhotographerHolder> {
            private OnItemClickListener listener;


    View v;
    public PhotographerAdapter(@NonNull FirestoreRecyclerOptions<Photographer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PhotographerHolder photographerHolder, int i, @NonNull final Photographer photographer) {

        photographerHolder.name.setText(photographer.getPh_name());
       photographerHolder.experience.setText("Exp : "+photographer.getPh_experience());
       photographerHolder.rating.setText("Rating : "+photographer.getPh_rating());
       photographerHolder.location.setText(photographer.getPh_location());

        Glide.with(v).load(photographer.getPh_profile_image_url())
                .apply(new RequestOptions().placeholder(R.drawable.progress_animation))
                .apply(RequestOptions.centerCropTransform())
                .into(photographerHolder.profile_image);
    }

    @NonNull
    @Override
    public PhotographerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v =LayoutInflater.from(parent.getContext()).inflate(R.layout.photographer_item, parent, false);
        return new PhotographerHolder(v);
    }

    class PhotographerHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView experience;
        TextView location;
        TextView rating;
        TextView bt_profile;
        CircularImageView profile_image;


        public PhotographerHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ph_name);
            experience = itemView.findViewById(R.id.ph_experience);
            location = itemView.findViewById(R.id.ph_location);
            rating = itemView.findViewById(R.id.ph_rating);
            profile_image=itemView.findViewById(R.id.ph_profile_image);
            bt_profile=itemView.findViewById(R.id.ph_know_more);
            bt_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });



        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener ){
        this.listener=listener;

    }

}
