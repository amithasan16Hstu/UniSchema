public class Room {
   String building;
   String floor;
   String roomNumber;
   int capacity;
   boolean isAvailable;

   public Room(String building, String floor, String roomNumber, int capacity, boolean isAvailable) {
       this.building = building;
       this.floor = floor;
       this.roomNumber = roomNumber;
       this.capacity = capacity;
       this.isAvailable = isAvailable;
   }
}

