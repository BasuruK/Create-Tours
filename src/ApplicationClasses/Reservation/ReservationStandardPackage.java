/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationClasses.Reservation;

import ApplicationClasses.DbConnect;
import ApplicationClasses.Packages.Pricing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ayesh Dilan
 */
public class ReservationStandardPackage {
    //private int stdPackageID;
    private ArrayList<Integer> stdPackageID = new ArrayList<>();
    private Integer stdPackageTypeID = null;
    private String custID;
    private String packageName;
    private int kmLimit;
    private ArrayList<Pricing> vehicleTypes= new ArrayList<>();
    private String dayOrNight;
    private String kmOrHour;

    
    Connection conn = null;   
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public ReservationStandardPackage()
    {
    
    }

    
    
    
    public ReservationStandardPackage(int stdPackageID, int stdPackageTypeID, String custID, String packageName, int kmLimit) {
       
        this.stdPackageTypeID = stdPackageTypeID;
        this.custID = custID;
        this.packageName = packageName;
        this.kmLimit = kmLimit;
    }
    
    
    
    
    
    public ReservationStandardPackage( Integer stdPackageTypeID){
        try {
            this.stdPackageTypeID = stdPackageTypeID;
            String sql = null;
            conn = null; pst = null; rs = null;
            conn = DbConnect.connect();
            
            sql = "SELECT* FROM standardpackage,standardpackagetype WHERE std_package_type_ID_ref = std_package_type_ID AND std_package_type_ID = '"+this.stdPackageTypeID+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            String vehi_type;
            Integer init_Price,add_per_km,add_per_hour;
            
            while(rs.next()){
                this.stdPackageID.add(rs.getInt("std_package_ID"));
                
                this.packageName = rs.getString("description_pak_type");
                this.kmLimit = rs.getInt("kmLimit");
                this.custID =rs.getString("cust_ID_ref");
                
                
                vehi_type = rs.getString("vehicle_type");
                init_Price = rs.getInt("initial_price");                
                add_per_km = rs.getInt("additional_rate_per_km");
                add_per_hour = rs.getInt("additional_rate_per_hour");                
                this.vehicleTypes.add(new Pricing(vehi_type,init_Price,add_per_km,add_per_hour));
                
                
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservationStandardPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    public void clear(){
        
        stdPackageID.clear();
        stdPackageTypeID = null;
        custID = null;
        packageName = null;
        kmLimit = 0;
        vehicleTypes.clear();
        
    }
    
    public String getDayOrNight() {
        return dayOrNight;
    }

    public void setDayOrNight(String dayOrNight) {
        this.dayOrNight = dayOrNight;
    }

    public String getKmOrHour() {
        return kmOrHour;
    }

    public void setKmOrHour(String kmOrHour) {
        this.kmOrHour = kmOrHour;
    }
    
    
    public void setValues( Integer stdPackageTypeID){
        try {
            this.stdPackageTypeID = stdPackageTypeID;
            String sql = null;
            conn = null; pst = null; rs = null;
            conn = DbConnect.connect();
            
            sql = "SELECT* FROM standardpackage,standardpackagetype WHERE std_package_type_ID_ref = std_package_type_ID AND std_package_type_ID = '"+this.stdPackageTypeID+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            String vehi_type;
            Integer init_Price,add_per_km,add_per_hour;
            
            while(rs.next()){
                this.stdPackageID.add(new Integer(rs.getInt("std_package_ID")));
                
                this.packageName = rs.getString("description_pak_type");
                this.kmLimit = rs.getInt("kmLimit");
                this.custID =rs.getString("cust_ID_ref");
                
                
                vehi_type = rs.getString("vehicle_type");
                init_Price = rs.getInt("initial_price");                
                add_per_km = rs.getInt("additional_rate_per_km");
                add_per_hour = rs.getInt("additional_rate_per_hour");                
                this.vehicleTypes.add(new Pricing(vehi_type,init_Price,add_per_km,add_per_hour));
                
                
                
                
            }
            System.out.println("ID ARRAY SIZE : " + this.stdPackageID.size());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservationStandardPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    
    
    
    
    
    

    public void insertPackage()
    {
    Connection conn =null;
    PreparedStatement pst =null;
                
    conn = DbConnect.connect();
    String SQL_queryadd;
    
    try {
    for (int i=0 ; i<vehicleTypes.size() ;i++)
    {
    
       
            SQL_queryadd = "CALL createtours.addStandardPackageType('"+this.packageName+"','"+this.kmLimit+"', '"+this.custID+"', '"+this.vehicleTypes.get(i).getVehicleType()+"', '"+this.vehicleTypes.get(i).getInitialPrice()+"', '"+this.vehicleTypes.get(i).getAdditionalRatePerKm()+"' , '"+this.vehicleTypes.get(i).getAdditionRatePerHour()+"')";
            pst = conn.prepareStatement(SQL_queryadd);
            pst.execute();
        
        }
    
    
    } catch (SQLException ex) {
            Logger.getLogger(ReservationStandardPackage.class.getName()).log(Level.SEVERE, null, ex);
    }    
    
    
    
    
    
    
    
    }    
    
    public void updatePackages()
    {
        Connection conn =null;
        PreparedStatement pst =null;
                
         conn = DbConnect.connect();
         String SQL_queryupd;
    
    try {
    for (int i=0 ; i<vehicleTypes.size() ;i++)
    {
    
            System.out.println("ARRAY " + this.stdPackageID.get(i));
            
            SQL_queryupd = "call createtours.updateStandardPackage('"+this.stdPackageID.get(i)+"','"+this.stdPackageTypeID+"', '"+this.packageName+"','"+this.kmLimit+"', '"+this.custID+"', '"+this.vehicleTypes.get(i).getVehicleType()+"', '"+this.vehicleTypes.get(i).getInitialPrice()+"', '"+this.vehicleTypes.get(i).getAdditionalRatePerKm()+"' , '"+this.vehicleTypes.get(i).getAdditionRatePerHour()+"')";
            pst = conn.prepareStatement(SQL_queryupd);
            pst.execute();
        
        }
    
    
    } catch (SQLException ex) {
            Logger.getLogger(ReservationStandardPackage.class.getName()).log(Level.SEVERE, null, ex);
    }    
    
    }
    

    public ArrayList<Integer> getStdPackageID() {
        return stdPackageID;
    }

    public void setStdPackageID(ArrayList<Integer> stdPackageID) {
        this.stdPackageID = stdPackageID;
    }

    public Integer getStdPackageTypeID() {
        return stdPackageTypeID;
    }

    public void setStdPackageTypeID(Integer stdPackageTypeID) {
        this.stdPackageTypeID = stdPackageTypeID;
    }          
           
    public void setStdPackageTypeID(int stdPackageTypeID) {
        this.stdPackageTypeID = stdPackageTypeID;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getKmLimit() {
        return kmLimit;
    }

    public void setKmLimit(int kmLimit) {
        this.kmLimit = kmLimit;
    }

    public ArrayList<Pricing> getVehicleTypes() {
        return vehicleTypes;
    }
    
    public Pricing getVehicleTypes(int index) {
        return vehicleTypes.get(index);
    }

    public void setVehicleTypes(ArrayList<Pricing> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }
   
    public int getNoOfVehicles()
    {
    
    return vehicleTypes.size();
    }


    public boolean isEmpty(){
        return (this.stdPackageTypeID == null || this.stdPackageTypeID == 0);
    }
    
    public void print()
    {
        System.out.println("--------stdPackage-------------");
        //System.out.println(this.stdPackageID);
        
        for(Integer  i :stdPackageID )  
            System.out.println("ID : " + i );
        
        System.out.println(this.stdPackageTypeID);
        System.out.println(this.kmLimit);
        System.out.println(this.custID);
        System.out.println(this.packageName);
        System.out.println(this.dayOrNight);
        System.out.println(this.kmOrHour);
        for (Pricing p : vehicleTypes )
        {
        
            p.print();
            
           
        }
        
    
    }
    
    
}
