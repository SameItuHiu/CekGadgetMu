package id.example.bagasekaa.cekgadgetmu_2.model;


public class Message {
    private String text;
    private MemberData memberData;
    private boolean belongsToCurrentUser;

    public Message(String text, MemberData data, boolean belongsToCurrentUser) {
        this.text = text;
        this.memberData = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    public MemberData getMemberData() {
        return memberData;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}
