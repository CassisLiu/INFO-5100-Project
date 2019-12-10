package m3.model.TableOperation;




import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import m3.model.Incentive;
import m3.model.filter.Filter;
import net.minidev.json.JSONObject;

public class DataFormatConversion {

    public static String FilterToString(Incentive i) {
    	ObjectMapper mapper = new ObjectMapper();
        String s = null;
        try {
			s = mapper.writeValueAsString(i.getConditions());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println(s);
        return s;
    }
    
    public static List<Filter> stringToList(String s) {
    	ObjectMapper mapper = new ObjectMapper();
    	List<Filter> list = null;
    	try {
			list = mapper.readValue(s, List.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	 if (list != null) {
             System.out.println("----- Filter List -----");

             for (Filter f : list) {
                 System.out.println("Type = " + f.getClass());
             }
         }
    	return list;
    }
    

    
    public static String OfferToString(Incentive i) {
    	JSONObject obj = new JSONObject();
    	 obj.put(i.getOffer().getClass().toString(), i.getOffer().toString());
    	 return obj.toString();
    }

}
