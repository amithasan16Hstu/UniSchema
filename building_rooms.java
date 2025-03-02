public class building_rooms {
    String room_id;
    String building_name;
    String floor_number;
    String room_number;
    String room_type;
    Double room_size;
    public building_rooms(String room_id, String building_name, String floor_number, String room_number,
            String room_type, Double room_size) {
        this.room_id = room_id;
        this.building_name = building_name;
        this.floor_number = floor_number;
        this.room_number = room_number;
        this.room_type = room_type;
        this.room_size = room_size;
    }
    public String getRoom_id() {
        return room_id;
    }
    public String getBuilding_name() {
        return building_name;
    }
    public String getFloor_number() {
        return floor_number;
    }
    public String getRoom_number() {
        return room_number;
    }
    public String getRoom_type() {
        return room_type;
    }
    public Double getRoom_size() {
        return room_size;
    }
    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }
    public void setFloor_number(String floor_number) {
        this.floor_number = floor_number;
    }
    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }
    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }
    public void setRoom_size(Double room_size) {
        this.room_size = room_size;
    }
}
