package m3.model.TableOperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import m3.model.Incentive;
import m3.model.checker.*;
import m3.model.filter.*;
import m3.model.offer.*;

public class TableOperations {

    private static Connection connection;
    public static void CreateConnection(){
        //The connectionURL should be modified according to your own server.
    	try {
    		//DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        String connectionUrl = "jdbc:sqlserver://is-swang01.ischool.uw.edu:1433;databaseName=VechileManagementSystem;user=INFO6210;password=NEUHusky!";
        try {
            // Load SQL Server JDBC driver and establish connection.
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Connect successfully.");
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }

    
    /*
     * Unit Test
     */
    public static void main(String[] args) {
//    	CreateConnection();
//    	if(connection != null) 
//    		System.out.println("Create successfully.");
//    	Incentive i = new Incentive();
//    	i.setDealerName("Honda");
//    	Date start = new Date();
//    	Date end = new Date(start.getTime()+100000);
//    	i.setStartDate(start);
//    	i.setEndDate(end);
//    	i.setOffer(new CashBackOffer(300));
//    	List<Filter> list = new ArrayList<>();
//    	list.add(new BrandFilter("Honda",new EqualChecker()));
//    	list.add(new PriceFilter((double) 300, new GreaterChecker()));
//    	i.setConditions(list);
//    	try {
//			Create(i);
//		} catch (JsonProcessingException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	List<Incentive> list = null;
    	System.out.println(list);
    	try {
			list = getIncentiveByDealer("Honda");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	System.out.println(list);
    	for(Incentive i : list) {
    		System.out.println(i.getDealerName());
    	}
	}

    //Create a new line in a table, if table doesn't exist, create table first.
    /*

    if not exists (select * from sysobjects where name='[DealerName]' and xtype='U')
    create table [I.DealerName]] (
        IncentiveID INT IDENTITY(1,1) primary key,
        startDate DATETIME,
        endDate DATETIME,
        Title VARCHAR(max),
        Disclaimer VARCHAR(max),
        DealerID VARCHAR(max),
        FilterList(Convert to String) VARCHAR(max),
        Offer(Convert to String) VARCHAR(max)
        )

        INSERT INTO [DealerName] (startDate,endDate,Title,Disclaimer,DealerID,FilterList,Offer)

        VALUES (
            [I.getStartDate],[I.getEndDate],
            [I.getTitle],[I.getDisclaimer],[DealerName],
            [I.getFilterList(Convert to String)],
            [I.getOffer(Convert to String)]
        )

     */
    public static void Create(Incentive I) throws SQLException, JsonProcessingException {
        CreateConnection(); //get connection;

        // convert format
        String filterList = null;
        String offer = null;
		try {
			filterList = DataFormatConversion.FilterToString(I);
			offer = DataFormatConversion.OfferToString(I);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Date startDate = DateToSqlDatetime.JavaStartDateToSqlDate(I);
        Date endDate = DateToSqlDatetime.JavaEndDateToSqlDate(I);

        String sql = new StringBuilder().append("if not exists (select * from sysobjects where name='").append(I.getDealerName()).append("' and xtype='U')").
                append("create table ").append(I.getDealerName()).append(" (").append("IncentiveID INT IDENTITY(1,1) primary key,").append("startDate DATETIME,").append("endDate DATETIME,").
                append("Title VARCHAR(max),").append("Disclaimer VARCHAR(max),").append("DealerName VARCHAR(max),").append("FilterList VARCHAR(max),").append("Offer VARCHAR(max) )").
                append("INSERT INTO ").append(I.getDealerName()).
                append(" (startDate,endDate,Title,Disclaimer,DealerName,FilterList,Offer) ").append("VALUES ('").
                append(startDate).append("','").append(endDate).append("','").append(I.getTitle()).append("','").append(I.getDisclaimer()).append("','").append(I.getDealerName()).
                append("','").append(filterList).append("','").append(offer).append("')").toString();

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        
        System.out.println("Create successfully.");
        connection.close();
    }

    //Edit a item
    /*

        UPDATE [DealerID]
        SET startDate = '[I.getStartDate(convert to sql format)]',
        endDate = '[I.getEndDate(convert to sql format)]',
        Title = '[I.getTitle()]',
        Disclaimer = '[I.getDisclaimer()]',
        FilterList = '[I.getFilterList(Convert to String)]',
        Offer = '[I.getOffer(Convert to String)]'
        WHERE IncentiveID = '[I.getIncentiveID()]';

    * */

    public void EditItem(Incentive I) throws SQLException, JsonProcessingException {
        CreateConnection(); //get connection;

        // convert format
        String filterList = null;
        String offer = null;
		try {
			filterList = DataFormatConversion.FilterToString(I);
			offer = DataFormatConversion.OfferToString(I);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Date startDate = DateToSqlDatetime.JavaStartDateToSqlDate(I);
        Date endDate = DateToSqlDatetime.JavaEndDateToSqlDate(I);

        String sql = new StringBuilder().append("UPDATE ").append(I.getDealerName()).
                append("SET startDate='"+startDate+"', endDate='"+ endDate
                        +"', Title='"+I.getTitle()+"', Disclaimer='"+I.getDisclaimer()
                        +"', FilterList='"+ filterList +"', FilterList='"+ offer +"'").
                append("WHERE IncentiveID='"+I.getIncentiveID()+"'").toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    //delete a item
    /*

        DELETE FROM [DealerID] WHERE IncentiveID = [I.IncentiveID];

    * */
    public static void DeleteItem(Incentive I) throws SQLException{
        CreateConnection();
        String sql = new StringBuilder().append("DELETE FROM  ").append(I.getDealerName()).append(" WHERE IncentiveID = '"+I.getIncentiveID()+"';").toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    // Get list of incentives by DealerID
    /*
        SELECT * FROM [DealerID]

    * */
    public static List<Incentive> getIncentiveByDealer(String DealerName) throws SQLException {
        List<Incentive> incentives = new ArrayList<>();
        CreateConnection();
        String sql = "SELECT * from " + DealerName;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println(rs.getRow());
            while(!rs.next()) {
                Incentive i = new Incentive();
                System.out.println(i.getEndDate());
                i.setIncentiveID(rs.getString("IncentiveID"));

                java.util.Date utilStartDate = rs.getDate("startDate");
                i.setStartDate(utilStartDate);
                java.util.Date utilEndDate = rs.getDate("endDate");
                i.setEndDate(utilEndDate);
                i.setTitle(rs.getString("Title"));
                i.setDisclaimer(rs.getString("Disclaimer"));
                ObjectMapper mapper = new ObjectMapper();
                i.setDealerName(rs.getString("DealerName"));
                List<Filter> filters = DataFormatConversion.stringToList(rs.getString("FilterList"));
                i.setConditions(filters); // JSON -> List<Filter>
                Offer offer = mapper.readValue(rs.getString("Offer"), Offer.class);
                i.setOffer(offer); // JSON -> Offer
                incentives.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return incentives;
    }

}

