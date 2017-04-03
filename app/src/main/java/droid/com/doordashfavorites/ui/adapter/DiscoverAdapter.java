package droid.com.doordashfavorites.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import droid.com.doordashfavorites.R;
import droid.com.doordashfavorites.app.Constants;
import droid.com.doordashfavorites.rest.dto.DiscoverDTO;
import droid.com.doordashfavorites.ui.DetailActivity;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ItemViewHolder> {

    private final Context context;
    private List<DiscoverDTO> discoverList = new ArrayList<>();

    public DiscoverAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        DiscoverDTO dto = discoverList.get(position);

        holder.status.setText(dto.getStatus());
        holder.cuisineType.setText(dto.getDescription());

        //Picasso Library throws an IllegalStateEx if the path is empty. Null is fine.
        String imageUrl = dto.getImageUrl() != null && dto.getImageUrl().isEmpty() ? null : dto.getImageUrl();

        if(holder.nameView != null) {
            holder.nameView.setText(dto.getName());
        }

        if(imageUrl != null) {
            Picasso.with(context)
                    .load(imageUrl)
                    .fit().centerCrop()
                    .placeholder(R.drawable.ic_restaurant_menu_black_24dp)
                    .into(holder.iconView);
        } else {
            Picasso.with(context)
                    .load(R.drawable.ic_restaurant_menu_black_24dp)
                    .fit().centerCrop()
                    .into(holder.iconView);
        }

        holder.itemView.setOnClickListener(view -> {
            String id = dto.getId();
            navigateToDetailScreen(id, dto.getName(), context);
        });
    }

    private void navigateToDetailScreen(String id, String name, Context context) {
        Intent detailIntent = new Intent(context, DetailActivity.class);
        detailIntent.putExtra(Constants.RESTAURANT_NAME, name );
        detailIntent.putExtra(Constants.ID, id);

        context.startActivity(detailIntent);
    }

    @Override
    public int getItemCount() {
        return discoverList.size();

    }

    public void addToAdapter(DiscoverDTO dto) {
        discoverList.add(dto);
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView status;
        private TextView cuisineType;
        TextView nameView;
        ImageView iconView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.restaurant_name);
            iconView = (ImageView) itemView.findViewById(R.id.restaurant_icon);
            status = (TextView) itemView.findViewById(R.id.status_view);
            cuisineType = (TextView) itemView.findViewById(R.id.cusine_type);
        }
    }
}
