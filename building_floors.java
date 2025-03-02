public class building_floors {
    String floor_id;
    String building_name;
    String floor_number;
    int total_rooms;
    public building_floors(String floor_id) {
        this.floor_id = floor_id;
    }
    public void setFloor_id(String floor_id) {
        this.floor_id = floor_id;
    }
    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }
    public void setFloor_number(String floor_number) {
        this.floor_number = floor_number;
    }
    public void setTotal_rooms(int total_rooms) {
        this.total_rooms = total_rooms;
    }
    public String getFloor_id() {
        return floor_id;
    }
    public String getBuilding_name() {
        return building_name;
    }
    public String getFloor_number() {
        return floor_number;
    }
    public int getTotal_rooms() {
        return total_rooms;
    }
    public building_floors(String floor_id, String building_name, String floor_number, int total_rooms) {
        this.floor_id = floor_id;
        this.building_name = building_name;
        this.floor_number = floor_number;
        this.total_rooms = total_rooms;
    }
}
