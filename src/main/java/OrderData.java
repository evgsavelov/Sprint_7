public class OrderData {
    private Integer id;
    private Integer track;

    public OrderData(Integer id, Integer track) {
        this.id = id;
        this.track = track;
    }

    public OrderData() {
    }

    public Integer getId() {
        return id;
    }
    public Integer getTrack() {
        return track;
    }
}
