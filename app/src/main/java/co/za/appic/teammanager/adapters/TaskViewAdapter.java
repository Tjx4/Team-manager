package co.za.appic.teammanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.enums.TaskStatus;
import co.za.appic.teammanager.models.TaskModel;

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.ViewHolder> {

    private List<TaskModel> tasks;
    private LayoutInflater mInflater;
    private TaskViewAdapter.ItemClickListener mClickListener;

    public TaskViewAdapter(Context context, List<TaskModel> tasks) {
        this.mInflater = LayoutInflater.from(context);
        this.tasks = tasks;
    }

    @Override
    public TaskViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.task_layout, parent, false);
        return new TaskViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewAdapter.ViewHolder holder, int position) {
        String description = tasks.get(position).getDescription();
        holder.decriptionTv.setText(description);
        String dueDate = "Due on "+tasks.get(position).getDueDateTime();
        holder.dueDateTv.setText(dueDate);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView decriptionTv;
        TextView dueDateTv;

        ViewHolder(View itemView) {
            super(itemView);
            decriptionTv = itemView.findViewById(R.id.txtTaskDescription);
            dueDateTv = itemView.findViewById(R.id.txtTakDueDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TaskModel taskModel = tasks.get(getAdapterPosition());

            if(taskModel.getTaskStatus() == TaskStatus.completed)
                return;

            if (mClickListener != null)
                mClickListener.onItemClick(view, taskModel);
        }
    }

    String getItem(int id) {
        return tasks.get(id).getDescription();
    }

    public void setClickListener(TaskViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, TaskModel taskModel);
    }
}