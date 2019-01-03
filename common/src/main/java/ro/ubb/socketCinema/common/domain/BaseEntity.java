package ro.ubb.socketCinema.common.domain;

public class BaseEntity<ID> {

    private ID id;

    public BaseEntity() {
    }

    BaseEntity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }

}
