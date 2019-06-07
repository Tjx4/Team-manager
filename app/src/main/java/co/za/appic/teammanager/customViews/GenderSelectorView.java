package co.za.appic.teammanager.customViews;

import android.content.Context;
import android.os.Build;
import android.widget.ImageView;
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

    public GenderSelectorView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        addRadioGroup();
        addMaleRadioButton();
        addFemaleRadioButton();
        addGenderIcon();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(context.getResources().getDrawable(R.drawable.def_edittext_background));
        }
    }

    public UserGender getSelectedGender(){
        return selectedGender;
    }

    private void addRadioGroup(){
        genderRadioGroup = new RadioGroup(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(this.ALIGN_PARENT_RIGHT);
        params.addRule(this.ALIGN_PARENT_END);
        params.addRule(this.CENTER_VERTICAL);
        params.setMargins(0,0, 0,0);
        genderRadioGroup.setLayoutParams(params);

        this.addView(genderRadioGroup);
    }

    private void addGenderIcon(){
        selectedGenderIcon = new ImageView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(this.ALIGN_PARENT_LEFT);
        params.addRule(this.ALIGN_PARENT_START);
        params.addRule(this.CENTER_VERTICAL);
        params.width = (int) UnitConverterHelper.pixelToDp(40, context);
        params.height = (int) UnitConverterHelper.pixelToDp(40, context);
        params.setMargins(0,0, 0,0);
        selectedGenderIcon.setLayoutParams(params);

        this.addView(selectedGenderIcon);
    }

    private void addMaleRadioButton(){
        maleRdo = new RadioButton(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(0,0, 0,0);
        maleRdo.setLayoutParams(params);

        genderRadioGroup.addView(maleRdo);
    }

    private void addFemaleRadioButton(){
        femaleRdo = new RadioButton(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(0,0, 0,0);
        femaleRdo.setLayoutParams(params);

        genderRadioGroup.addView(femaleRdo);
    }

}
