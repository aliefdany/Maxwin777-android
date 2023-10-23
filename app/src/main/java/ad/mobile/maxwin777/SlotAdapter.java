package ad.mobile.maxwin777;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.ItemVH>{
    private final Context context;

    private SlotWheel slotWheel;


    @NonNull
    @Override
    public ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(context).inflate(R.layout.slot_item, parent, false);

        ItemVH viewHolder = new ItemVH(rowView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVH holder, int position) {

        Integer imageResId = this.slotWheel.slotDrawables.get(position);

        holder.slotImage.setImageResource(imageResId);
    }

    @Override
    public int getItemCount() {
        return this.slotWheel.slotDrawables.size();
    }


    public SlotAdapter(Context context) {
        this.context = context;
        this.slotWheel = new SlotWheel();
    }


    class ItemVH extends RecyclerView.ViewHolder {

        private ImageView slotImage;

        public ItemVH(@NonNull View itemView) {
            super(itemView);

            slotImage = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
