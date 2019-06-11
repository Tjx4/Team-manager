package co.za.appic.teammanager.features.history;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.enums.EmployeeType;
import co.za.appic.teammanager.models.TaskModel;

public class HistoryPresenter extends BaseAsyncPresenter {
    private HistoryView historyView;
    private List<TaskModel> tasksWorkedOn;

    public HistoryPresenter(HistoryView historyView) {
        super(historyView);
        this.historyView = historyView;
    }

    public void syncTaskHistory(EmployeeType employeeType){
        String employeeId = firebaseAuth.getUid();
        String employee = (employeeType == EmployeeType.worker)? Constants.DB_WORKER : Constants.DB_SUPERVISOR;
        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference().child(Constants.DB_TASKS);
        Query query = tasksRef.orderByChild(employee).equalTo(employeeId);
        query.keepSynced(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasksWorkedOn = new ArrayList<>();

                for (DataSnapshot chatSnapshot: dataSnapshot.getChildren()) {

                    try{
                        TaskModel currentTask = getTaskFromDataSnapsho(chatSnapshot);
                        tasksWorkedOn.add(currentTask);
                    }
                    catch (Exception e){

                    }
                }

                historyView.showHistory(tasksWorkedOn);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}