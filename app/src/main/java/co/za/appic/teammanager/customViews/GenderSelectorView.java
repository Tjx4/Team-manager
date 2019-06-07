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
        addGenderIcon();
        addRadioGroup();
        addMaleRadioButton();
        addFemaleRadioButton();
    }

    public UserGender getSelectedGender(){
        return selectedGender;
    }

    private void addGenderIcon(){
        selectedGenderIcon = new ImageView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(this.ALIGN_PARENT_LEFT);
        params.addRule(this.ALIGN_PARENT_START);
        params.addRule(this.CENTER_VERTICAL);
        params.width = (int) UnitConverterHelper.pixelToDp(40, context);
        params.height = (int) UnitConverterHelper.pixelToDp(40, context);
        params.setMargins(0,0, 0,0);
        selectedGenderIcon.setLayoutParams(params);
        selectedGenderIcon.setImageResource(R.drawable.ic_menu_gallery);

        addView(selectedGenderIcon);
    }

    private void addRadioGroup(){
        genderRadioGroup = new RadioGroup(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(this.CENTER_VERTICAL);
        params.addRule(RelativeLayout.RIGHT_OF, selectedGenderIcon.getId());
        int leftMaring = (int) UnitConverterHelper.pixelToDp(20, context);
        params.setMargins(leftMaring,0, 0,0);
        genderRadioGroup.setLayoutParams(params);
        genderRadioGroup.setOrientation(LinearLayout.HORIZONTAL);

        addView(genderRadioGroup);
    }

    private void addMaleRadioButton(){
        maleRdo = new RadioButton(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0, 0,0);
        maleRdo.setLayoutParams(params);
        maleRdo.setText("Male");
        maleRdo.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                selectedGender = UserGender.male;
                selectedGenderIcon.setImageResource(R.drawable.ic_male_dark);
            }
        });

        genderRadioGroup.addView(maleRdo);
    }

    private void addFemaleRadioButton(){
        femaleRdo = new RadioButton(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int leftMaring = (int) UnitConverterHelper.pixelToDp(20, context);
        params.setMargins(leftMaring,0, 0,0);
        femaleRdo.setLayoutParams(params);
        femaleRdo.setText("Female");
        femaleRdo.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                selectedGender = UserGender.female;
                selectedGenderIcon.setImageResource(R.drawable.ic_female_dark);
            }
        });

        genderRadioGroup.addView(femaleRdo);
    }

}