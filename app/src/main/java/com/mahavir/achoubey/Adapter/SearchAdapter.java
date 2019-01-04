package com.mahavir.achoubey.Adapter;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.mahavir.achoubey.Entities.CountryData;
import com.mahavir.achoubey.Helpers.SvgDecoder;
import com.mahavir.achoubey.Helpers.SvgDrawableTranscoder;
import com.mahavir.achoubey.Helpers.SvgSoftwareLayerSetter;
import com.mahavir.achoubey.shellcountrysearch.R;

import java.io.InputStream;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    private List<CountryData> listItemStorage;
    private Context context;
    private ItemClickListener clickListener;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public SearchAdapter(Context ctx, List<CountryData> customizedListView) {
        context = ctx;
        listItemStorage = customizedListView;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder viewHolder, int position) {
        CountryData countryData = listItemStorage.get(position);
        String stringUrl = listItemStorage.get(position).getFlag();
        viewHolder.searchWord.setText(countryData.getName().replaceAll("\"", ""));
        //Picasso.with(context).load(stringUrl).into(viewHolder.searchImage);

        GenericRequestBuilder<Uri,InputStream,SVG,PictureDrawable>
                requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        Uri uri = Uri.parse(stringUrl.replaceAll("\"", ""));

        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(viewHolder.searchImage);
    }

    @Override
    public int getItemCount() {
        return listItemStorage.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView searchWord;
        private ImageView searchImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            searchWord = itemView.findViewById(R.id.list_item_search);
            searchImage = itemView.findViewById(R.id.image_search);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
}
