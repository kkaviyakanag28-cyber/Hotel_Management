package app;
import java.time.*;
import java.util.*;
class Module2 {
 static void menu(){Data.expire();String city=Data.ask("Search city: ");LocalDate in=Data.date("Check-in"),out=Data.date("Check-out");int guests=Data.number("Guests: ");if(!Data.validDates(in,out)||guests<1){System.out.println("Invalid dates/guests. Check-in today or later, checkout later, max 15 nights.");return;}ArrayList<Room> found=new ArrayList<>();for(Room r:Data.rooms){Hotel h=Data.hotel(r.hotelId);if(h!=null&&h.city.equalsIgnoreCase(city)&&r.capacity>=guests&&!Data.blocked(r,in,out,-1))found.add(r);}found.sort(Comparator.comparingDouble(r->r.price));if(found.isEmpty())System.out.println("No rooms available.");for(Room r:found){Hotel h=Data.hotel(r.hotelId);System.out.println(h.name+" | Room ID "+r.id+" | "+r.type+" | Capacity "+r.capacity+" | Rs "+r.price+"/night");}System.out.println("Checkout 11 AM; next check-in 2 PM (same-day allowed).");}
}
