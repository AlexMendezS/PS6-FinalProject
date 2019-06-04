package model;

public class user {

    private int queueNumber;
    private String firstName;
    private String name;
    private long id;

    public user (int queueNumber, String firstName, String name, long id){
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

    public long getId() { return id; }

}
