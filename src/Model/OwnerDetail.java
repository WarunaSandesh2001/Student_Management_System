package Model;

public class OwnerDetail {
    private String name;
    private String contact;

    public OwnerDetail(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public OwnerDetail() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "OwnerDetail{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
