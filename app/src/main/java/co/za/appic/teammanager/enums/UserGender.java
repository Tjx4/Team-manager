package co.za.appic.teammanager.enums;

public enum  UserGender {
    male(1, "Male", "Man", "He"),
    female(2, "Female", "Woman", "She");

    int id;
    String gender;
    String genderTitle;
    String genderPronoun;

    UserGender(int id, String gender, String genderTitle, String genderPronoun) {
        this.id = id;
        this.gender = gender;
        this.genderTitle = genderTitle;
        this.genderPronoun = genderPronoun;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderTitle() {
        return genderTitle;
    }

    public void setGenderTitle(String genderTitle) {
        this.genderTitle = genderTitle;
    }

    public String getGenderPronoun() {
        return genderPronoun;
    }

    public void setGenderPronoun(String genderPronoun) {
        this.genderPronoun = genderPronoun;
    }
}