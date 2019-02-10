package cb;
 
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
 
public class Main {
    
    private static SessionFactory factory;
    
 
    public static void main(String[] args) {
        
        try {
            factory = new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        
       /* Integer empID1 = null;
        Integer empID2;
        Integer empID3;
        */
        
        //Add few employee records in database
        Integer empID1 = Main.addEmployee("Zara", "Ali", 5000);
        Integer empID2 = Main.addEmployee("Daisy", "Das", 5000);
        Integer empID3 = Main.addEmployee("John", "Paul", 10000);
        
        
        
      //Add few employee records in database ALTERNATIVE
        //empID1 = Main.addEmployee("Zara", "Ali", 1000);
        //empID2 = Main.addEmployee("Daisy", "Das", 5000);
        //empID3 = Main.addEmployee("John", "Paul", 10000);
        
       
		//Update employee's records 
		Main.updateEmployee(empID1, 1000);

		//Delete an employee from the database 
		//Main.deleteEmployee(empID1);
		//Main.deleteEmployee(empID2);
		//Main.deleteEmployee(empID3);

		//List down new list of the employees 
		//Main.listEmployees();*/
        
       // List down all the employees
        System.out.println("List of inserted employees:");
        Main.listEmployees();
        }
    
    /* Method to CREATE an employee in the database */
    public static Integer addEmployee(String fname, String lname, int salary) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        
        try {
            tx = session.beginTransaction();
            Employee employee = new Employee(fname, lname, salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();
            
        }
        catch(HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }    
        return employeeID;
    }
    
    
    // Method to READ all the employees
    public static void listEmployees() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List<?> employees = session.createQuery("FROM Employee").list();
			for (Iterator<?> iterator = employees.iterator(); iterator.hasNext();) {
				Employee employee = (Employee) iterator.next();
				System.out.print("First Name: " + employee.getFirstName());
				System.out.print("  Last Name: " + employee.getLastName());
				System.out.println("  Salary: " + employee.getSalary());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Method to UPDATE salary for an employee */
	public static void updateEmployee(Integer EmployeeID, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, EmployeeID);
			employee.setSalary(salary);
			session.update(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Method to DELETE an employee from the records */
	public static void deleteEmployee(Integer EmployeeID) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, EmployeeID);
			session.delete(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
 
}
 
 


 


 
