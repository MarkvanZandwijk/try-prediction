import java.util.*;
import java.io.FileInputStream;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson.JacksonFactory;

class json_test {
  public static void main(String[] args) {
    try {
      Map<String, Object> models = new HashMap<String, Object>();
      FileInputStream in = new FileInputStream("models.json");
      JacksonFactory factory = new JacksonFactory();
      JsonParser parser = factory.createJsonParser(in);

      parser.parse(models, null);
      Iterator it1 = models.entrySet().iterator();
      while (it1.hasNext()) {
        Map.Entry entry = (Map.Entry) it1.next();
        String name = (String) entry.getKey();
        System.out.println(name);
        Map<String, Object> model = (Map<String, Object>) entry.getValue();
        String desc = (String) model.get("description");
        System.out.println("  Description = " + desc); 
        String model_id = (String) model.get("model_id");
        System.out.println("  Model Id = " + model_id); 
        String user_list = (String) model.get("user_list");
        System.out.println("  User List = " + user_list); 
        List<Object> fields = (List<Object>) model.get("fields");
        Iterator it2 = fields.iterator();
        while (it2.hasNext()) {
          System.out.println("  field:");
          Map<String, String> field = (Map<String, String>) it2.next();
          System.out.println("    label = " + field.get("label"));
          System.out.println("    help = " + field.get("help"));
          System.out.println("    rows = " + field.get("rows"));
          System.out.println("    cols = " + field.get("cols"));
        }
      }
    } catch (Exception e) {
      System.out.println("error!");
    }
  }
}
