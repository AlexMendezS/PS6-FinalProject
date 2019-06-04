package model;

public class user {

    private int queueNumber;
    private String firstName;
    private String name;
    private int id;

    public user (int queueNumber, String firstName, String name, int id){
        this.queueNumber = queueNumber;
        this.firstName = firstName;
        this.name = name;
        this.id = id;
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

    public int getId() { return id; }

}
