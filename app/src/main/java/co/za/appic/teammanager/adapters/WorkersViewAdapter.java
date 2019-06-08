package co.za.appic.teammanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.features.dashboard.supervisor.SupervisorDashboardActivity;
import co.za.appic.teammanager.features.dashboard.supervisor.SupervisorDashboardView;
import co.za.appic.teammanager.helpers.ImageHelper;
import co.za.appic.teammanager.helpers.RoundLoadingImageView;
import co.za.appic.teammanager.models.WorkerModel;

public class WorkersViewAdapter extends RecyclerView.Adapter<WorkersViewAdapter.ViewHolder> {

    private SupervisorDashboardView supervisorDashboardView;
    private List<WorkerModel> workers;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public WorkersViewAdapter(Context context, List<WorkerModel> workers) {
        this.mInflater = LayoutInflater.from(context);
        supervisorDashboardView = (SupervisorDashboardActivity)context;
        this.workers = workers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.asignee_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WorkerModel worker = workers.get(position);
        String name = worker.getName();
        String surname = worker.getSurname();
        String fullName = name+" "+surname;
        holder.fullNameTv.setText(fullName);

        String profPicUrl = ImageHelper.getProfilePicRootPath(worker)+worker.getProfilePic();
        holder.profPicImgv.setImageFromFirebaseStorage(supervisorDashboardView.getPresenter().getFirebaseStorage(), profPicUrl);
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fullNameTv;
        RoundLoadingImageView profPicImgv;

        ViewHolder(View itemView) {
            super(itemView);
            fullNameTv = itemView.findViewById(R.id.txtEmployeeName);
            profPicImgv = itemView.findViewById(R.id.imgProfpic);
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