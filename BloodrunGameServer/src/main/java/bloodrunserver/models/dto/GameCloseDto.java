package bloodrunserver.models.dto;

public class GameCloseDto {
    Boolean closed;

    public GameCloseDto() {
    }

    public GameCloseDto(Boolean closed) {
        this.closed = closed;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}