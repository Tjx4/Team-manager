package co.za.appic.teammanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.models.WorkerModel;

public class WorkersViewAdapter extends RecyclerView.Adapter<WorkersViewAdapter.ViewHolder> {

    private List<WorkerModel> workers;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public WorkersViewAdapter(Context context, List<WorkerModel> workers) {
        this.mInflater = LayoutInflater.from(context);
        this.workers = workers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.asignee_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = workers.get(position).getName();
        String surname = workers.get(position).getSurname();
        String fullName = name+" "+surname;
        holder.fullNameTv.setText(fullName);
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fullNameTv;
        ImageView profPicImgv;

        ViewHolder(View itemView) {
            super(itemView);
            fullNameTv = itemView.findViewById(R.id.txtEmployeeName);
            profPicImgv = itemView.findViewById(R.id.imgUserProfpic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int id) {
        return workers.get(id).getName();
    }

   public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}