package co.za.appic.teammanager.customViews;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import java.util.concurrent.atomic.AtomicInteger;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.enums.UserGender;
import co.za.appic.teammanager.helpers.UnitConverterHelper;

public class GenderSelectorView extends RelativeLayout {
    private Context context;
    private UserGender selectedGender;
    private RadioGroup genderRadioGroup;
    private ImageView selectedGenderIcon;
    private RadioButton maleRdo;
    private RadioButton femaleRdo;

    public GenderSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        int width = (int) UnitConverterHelper.pixelToDp(230, context);
        int padding = (int) UnitConverterHelper.pixelToDp(20, context);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.width = width;
        setLayoutParams(params);
        setPadding(padding, 0 , padding, 0);

        addGenderIcon(context);
        addRadioGroup(context);
        addMaleRadioButton(context);
        addFemaleRadioButton(context);
    }

    public void setSelectedGender(UserGender selectedGender){
        switch(selectedGender){
            case male:
                maleRdo.setChecked(true);
                setMale();
                break;
            case female:
                femaleRdo.setChecked(true);
                setFemale();
                break;
        }
    }

    public UserGender getSelectedGender(){
        return selectedGender;
    }

    private void addGenderIcon(Context context){
        selectedGenderIcon = new ImageView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(this.ALIGN_PARENT_LEFT);
        params.addRule(this.ALIGN_PARENT_START);
        params.addRule(this.CENTER_VERTICAL);
        params.width = (int) UnitConverterHelper.pixelToDp(35, context);
        params.height = (int) UnitConverterHelper.pixelToDp(35, context);
        int rightMargin = (int) UnitConverterHelper.pixelToDp(10, context);
        params.setMargins(0,0, rightMargin,0);
        selectedGenderIcon.setLayoutParams(params);
        selectedGenderIcon.setImageResource(R.drawable.ic_gender_dark);
        selectedGenderIcon.setId(getUniqId());

        addView(selectedGenderIcon);
    }

    private void addRadioGroup(Context context){
        genderRadioGroup = new RadioGroup(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(this.CENTER_VERTICAL);
        params.addRule(RelativeLayout.RIGHT_OF, selectedGenderIcon.getId());
        params.setMargins(0,0, 0,0);
        genderRadioGroup.setLayoutParams(params);
        genderRadioGroup.setOrientation(LinearLayout.HORIZONTAL);

        addView(genderRadioGroup);
    }

    private void addMaleRadioButton(Context context){
        maleRdo = new RadioButton(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0, 0,0);
        maleRdo.setLayoutParams(params);
        maleRdo.setText("Male");
        maleRdo.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                setMale();
            }
        });

        genderRadioGroup.addView(maleRdo);
    }

    private void addFemaleRadioButton(Context context){
        femaleRdo = new RadioButton(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int leftMaring = (int) UnitConverterHelper.pixelToDp(20, context);
        params.setMargins(leftMaring,0, 0,0);
        femaleRdo.setLayoutParams(params);
        femaleRdo.setText("Female");
        femaleRdo.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                setFemale();
            }
        });

        genderRadioGroup.addView(femaleRdo);
    }

    private void setMale(){
        selectedGender = UserGender.male;
        selectedGenderIcon.setImageResource(R.drawable.ic_male_dark);
    }

    private void setFemale(){
        selectedGender = UserGender.female;
        selectedGenderIcon.setImageResource(R.drawable.ic_female_dark);
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private static int getUniqId() {

        if (Build.VERSION.SDK_INT < 17) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }
}