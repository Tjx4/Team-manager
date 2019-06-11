package co.za.appic.teammanager.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.models.TaskModel;

public class HistoryTaskViewAdapter extends TaskViewAdapter {

    public HistoryTaskViewAdapter(Context context, List<TaskModel> tasks) {
        super(context, tasks);
    }

    @Override
    public TaskViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_task_layout, parent, false);
        return new TaskViewAdapter.ViewHolder(view);
    }

    public class HistoryViewHolder extends TaskViewAdapter.ViewHolder implements View.OnClickListener {
        TextView createdDateTv;
        TextView completeDateTv;

        HistoryViewHolder(View itemView) {
            super(itemView);
            createdDateTv = itemView.findViewById(R.id.txtTaskStartDate);
            completeDateTv = itemView.findViewById(R.id.txtTaskCompleteDate);
            itemView.setOnClickListener(this);
        }
    }

    @Override
    public void onBindViewHolder(HistoryTaskViewAdapter.ViewHolder holder, int position) {
        HistoryViewHolder historyViewHolder = (HistoryViewHolder) holder;
        String description = tasks.get(position).getDescription();
        holder.decriptionTv.setText(description);

        String ceatedDate = "Started on "+tasks.get(position).getCreationDateTime();
        historyViewHolder.createdDateTv.setText(ceatedDate);

        String completeDate = "Completed on "+tasks.get(position).getCompletionDateTime();
        historyViewHolder.createdDateTv.setText(completeDate);
    }

}
