package co.za.appic.teammanager.enums;

public enum UserStatus {
    unregistered("Unregistered", 'U', 0),
    pending("Pending", 'P', 1),
    registered("Registered", 'R', 2);

    private String description;
    private char letter;
    private int index;

    UserStatus(String description, char letter, int index) {
        this.description = description;
        this.letter = letter;
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

