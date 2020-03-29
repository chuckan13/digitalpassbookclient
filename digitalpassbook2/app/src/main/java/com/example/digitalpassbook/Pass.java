package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Pass {

    @SerializedName("id")
    private int id;

    @SerializedName("orgId")
    private int orgId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("eventsId")
    private int eventsId;

    @SerializedName("passName")
    private String passName;

    public Pass (int orgID, int userID, int eventID, String passName) {
        this.orgId = orgID;
        this.userId = userID;
        this.eventsId = eventID;
        this.passName = passName;
    }

    public int getId() { return this.id; }
    public int getOrgId() { return this.orgId; }
    public int getUserId() { return this.userId; }
    public String getPassName() { return this.passName; }
    public int getEventId() { return this.eventsId; }

}
