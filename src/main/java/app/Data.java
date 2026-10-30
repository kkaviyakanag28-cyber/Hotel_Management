package app;
import java.time.*;
import java.util.*;

// Shared simple data classes. All modules use the SAME lists.
class Data {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Hotel> hotels = new ArrayList<>();
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Guest> guests = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static ArrayList<Payment> payments = new ArrayList<>();
    static ArrayList<Review> reviews = new ArrayList<>();
    static int hotelId=1, roomId=1, guestId=1, bookingId=1;
    static String ask(String prompt) { System.out.print(prompt); return sc.nextLine().trim(); }
    static int number(String prompt) { while(true) { try {return Integer.parseInt(ask(prompt));} catch(Exception e){System.out.println("Enter a valid number.");} } }
    static double money(String prompt) { while(true) { try {return Double.parseDouble(ask(prompt));} catch(Exception e){System.out.println("Enter a valid amount.");} } }
    static LocalDate date(String prompt) { while(true) { try {return LocalDate.parse(ask(prompt+" (yyyy-MM-dd): "));} catch(Exception e){System.out.println("Use yyyy-MM-dd.");} } }
    static Hotel hotel(int id) {for(Hotel h:hotels) if(h.id==id)return h;return null;}
    static Room room(int id) {for(Room r:rooms)if(r.id==id)return r;return null;}
    static Guest guest(int id) {for(Guest g:guests)if(g.id==id)return g;return null;}
    static Booking booking(int id) {for(Booking b:bookings)if(b.id==id)return b;return null;}
    static boolean overlap(LocalDate a,LocalDate b,LocalDate c,LocalDate d){return a.isBefore(d)&&c.isBefore(b);}
    static boolean validDates(LocalDate in,LocalDate out){long n=java.time.temporal.ChronoUnit.DAYS.between(in,out);return !in.isBefore(LocalDate.now())&&n>=1&&n<=15;}
    static boolean blocked(Room r,LocalDate in,LocalDate out,int exceptBooking){
        for(DateBlock m:r.maintenance)if(overlap(in,out,m.start,m.end))return true;
        for(Booking b:bookings)if(b.id!=exceptBooking && b.roomIds.contains(r.id) && overlap(in,out,b.in,b.out)){
            if(b.status.equals("Confirmed")||b.status.equals("Checked In"))return true;
            if(b.status.equals("Pending Payment")&&LocalDateTime.now().isBefore(b.holdUntil))return true;
        }
        return false;
    }
    static void expire(){for(Booking b:bookings){if(b.status.equals("Pending Payment")&&!LocalDateTime.now().isBefore(b.holdUntil))b.status="Cancelled";
        if(b.status.equals("Confirmed")&&LocalDateTime.now().isAfter(b.in.plusDays(1).atTime(12,0))){b.status="No Show";}
    }}
    static void seed(){Hotel h=new Hotel(hotelId++,"Sample Stay","Chennai","Main Road",4);hotels.add(h);rooms.add(new Room(roomId++,h.id,"101","Double",2,2000));rooms.add(new Room(roomId++,h.id,"102","Deluxe",3,3500));}
}
class Hotel {int id,stars;String name,city,address;boolean flagged=false;double average=0;Hotel(int i,String n,String c,String a,int s){id=i;name=n;city=c;address=a;stars=s;}}
class DateBlock {LocalDate start,end;DateBlock(LocalDate a,LocalDate b){start=a;end=b;}}
class Room {int id,hotelId,capacity;String number,type;double price;ArrayList<DateBlock> maintenance=new ArrayList<>();Room(int i,int h,String n,String t,int c,double p){id=i;hotelId=h;number=n;type=t;capacity=c;price=p;}}
class Guest {int id;String name,phone,email,idType,idNumber;Guest(int i,String n,String p,String e,String t,String no){id=i;name=n;phone=p;email=e;idType=t;idNumber=no;}}
class Booking {int id,guestId,hotelId;ArrayList<Integer> roomIds;LocalDate in,out;String status="Pending Payment";LocalDateTime holdUntil=LocalDateTime.now().plusMinutes(15);double total;Booking(int i,int g,int h,ArrayList<Integer> r,LocalDate a,LocalDate z){id=i;guestId=g;hotelId=h;roomIds=r;in=a;out=z;}}
class Payment {int bookingId;double amount;LocalDateTime date;String method,status;Payment(int b,double a,String m,String s){bookingId=b;amount=a;method=m;status=s;date=LocalDateTime.now();}}
class Review {int bookingId,hotelId,rating;String comment;LocalDate date=LocalDate.now();Review(int b,int h,int r,String c){bookingId=b;hotelId=h;rating=r;comment=c;}}
