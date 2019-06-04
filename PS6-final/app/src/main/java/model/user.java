package model;

public class user {

    private int queueNumber;
    private String firstName;
    private String name;
    private String educationStream;

    public user (int queueNumber, String firstName, String name){
        this.queueNumber = queueNumber;
        this.firstName = firstName;
        this.name = name;
    }
    public user (int queueNumber, String firstName, String name, String educationStream){
        this.queueNumber = queueNumber;
        this.firstName = firstName;
        this.name = name;
        this.educationStream = educationStream;
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

    public String getEducationStream() {
        return educationStream;
    }
}
