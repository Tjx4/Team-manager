package co.za.appic.teammanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.models.TaskModel;

public class HistoryTaskViewAdapter extends RecyclerView.Adapter<HistoryTaskViewAdapter.ViewHolder>  {

    protected List<TaskModel> tasks;
    protected LayoutInflater mInflater;
    protected HistoryTaskViewAdapter.ItemClickListener mClickListener;

    public HistoryTaskViewAdapter(Context context, List<TaskModel> tasks) {
        this.mInflater = LayoutInflater.from(context);
        this.tasks = tasks;
    }
    @Override
    public HistoryTaskViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_task_layout, parent, false);
        return new HistoryTaskViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryTaskViewAdapter.ViewHolder holder, int position) {
        String description = tasks.get(position).getDescription();
        holder.decriptionTv.setText(description);

        String ceatedDateMessage = "Created on "+tasks.get(position).getCreationDateTime();
        holder.createdDateTv.setText(ceatedDateMessage);

        String copletionDateTime = tasks.get(position).getCompletionDateTime();
        String copletionMessage = (copletionDateTime.isEmpty())? "Task still pending" : "Completed on "+copletionDateTime;
        holder.completeDateTv.setText(copletionMessage);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView decriptionTv;
        TextView createdDateTv;
        TextView completeDateTv;

        ViewHolder(View itemView) {
            super(itemView);
            decriptionTv = itemView.findViewById(R.id.txtTaskDescription);
            createdDateTv = itemView.findViewById(R.id.txtTaskStartDate);
            completeDateTv = itemView.findViewById(R.id.txtTaskCompleteDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TaskModel taskModel = tasks.get(getAdapterPosition());

            if (mClickListener != null)
                mClickListener.onItemClick(view, taskModel);
        }
    }

    @Override
    public int getItemCount() {
        return  tasks.size();
    }

    TaskModel getItem(int index) {
        return tasks.get(index);
    }

    public void setClickListener(HistoryTaskViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, TaskModel taskModel);
    }

}