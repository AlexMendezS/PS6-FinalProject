package model;

public class user {

    private int queueNumber;
    private String firstName;
    private String name;

    public user (int queueNumber, String firstName, String name){
        this.queueNumber = queueNumber;
        this.firstName = firstName;
        this.name = name;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

}
