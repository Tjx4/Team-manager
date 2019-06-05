package co.za.appic.teammanager.models;

import co.za.appic.teammanager.fragments.BasePagerFragment;

public class ViewPagerFragmentModel {
    private BasePagerFragment fragment;
    private String title;
    private String description;

    public BasePagerFragment getFragment() {
        return fragment;
    }

    public void setFragment(BasePagerFragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}