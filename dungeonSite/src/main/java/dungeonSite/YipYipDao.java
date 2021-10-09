package dungeonSite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
public class YipYipDao {

    public void crud() {
       SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
       Session session = sessionFactory.openSession();
        
       create(session);
       read(session);
       
       update(session);
       read(session);
        
       delete(session);
       read(session);
        
       session.close();
   }
    
   private void delete(Session session) {
       System.out.println("Deleting Appa took off...");
       YipYip yip = (YipYip) session.get(YipYip.class, 3L);
       session.beginTransaction();
       session.delete(yip);
       session.getTransaction().commit();
   }
    
   private void update(Session session) {
       System.out.println("Updating message...");
       YipYip yip = (YipYip) session.get(YipYip.class, 3L);
       yip.setMessage("Appa took off");
        
       session.beginTransaction();
       session.saveOrUpdate(yip);
       session.getTransaction().commit();
   }

   private void create(Session session) {
       System.out.println("Creating new message...");
       YipYip yip = new YipYip("Appa Yip Yip!");
       session.beginTransaction();
       session.save(yip);
       session.getTransaction().commit();
   }
    
   private void read(Session session) {
       Query q = session.createQuery("FROM YipYip");
        
       List<YipYip> y = q.list();
       YipYip ret = null;
       System.out.println("Reading messages...");
       for (Iterator<YipYip> iterator = y.iterator(); iterator.hasNext();) 
       {
    	   ret = iterator.next();
           System.out.println(ret.getMessage());
       }
   }
   
   public static void main(String[] args)
   {
	   System.out.println("hello");
	   YipYipDao yyd = new YipYipDao();
	   YipYip yip = new YipYip("Mike made this");
	   System.out.println(yip.getMessage());
	   yyd.crud();
   }
}