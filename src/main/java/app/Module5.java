package app;
import java.time.*;
import java.time.temporal.*;
class Module5 {
 static void menu(){System.out.println("\n--- MODULE 5: REVIEWS ---\n1 Add review\n2 View hotel reviews");int c=Data.number("Choice: ");if(c==1)add();else if(c==2)view();}
 static void add(){Booking b=Data.booking(Data.number("Booking ID: "));if(b==null||!b.status.equals("Checked Out")){System.out.println("Review only after checkout.");return;}if(ChronoUnit.DAYS.between(b.out,LocalDate.now())>30){System.out.println("Review window (30 days) passed.");return;}for(Review r:Data.reviews)if(r.bookingId==b.id){System.out.println("Only one review per booking.");return;}int stars=Data.number("Rating 1-5: ");if(stars<1||stars>5){System.out.println("Invalid rating.");return;}String comment=Data.ask("Comment (optional): ");Data.reviews.add(new Review(b.id,b.hotelId,stars,comment));update(b.hotelId);System.out.println("Review saved.");}
 static void update(int id){int count=0,sum=0;for(Review r:Data.reviews)if(r.hotelId==id){count++;sum+=r.rating;}Hotel h=Data.hotel(id);if(h!=null){h.average=count==0?0:(double)sum/count;h.flagged=count>=10&&h.average<2.5;}}
 static void view(){int id=Data.number("Hotel ID: ");Hotel h=Data.hotel(id);if(h==null)return;System.out.println(h.name+" average rating: "+String.format("%.2f",h.average)+" | Admin review flag: "+h.flagged);for(Review r:Data.reviews)if(r.hotelId==id)System.out.println(r.rating+"/5 "+r.comment);}
}
