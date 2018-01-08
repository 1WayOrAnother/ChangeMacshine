
 import static spark.Spark.*;
 import java.util.HashMap;
 import java.util.Map;
 import spark.ModelAndView;
 import spark.template.handlebars.HandlebarsTemplateEngine;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        ChangeMachine change = new ChangeMachine();


        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/results", (request, response) -> {
            Map<String, String> model = new HashMap<String, String>();

            String money = request.queryParams("cash");
            System.out.println(money);
            Float cash = Float.parseFloat(money);

            String result;

            Double totalMoney = change.getQuarters()*0.25 + change.getDimes()*0.10 + change.getNickels()*0.05 + change.getPennies()*0.01;
            if (totalMoney < cash){
                result = "There is not enough money in the change machine";
            } else {
                result = change.makeChange(cash);
            }

            System.out.println(result);

            model.put("results", result);
//            System.out.println(model);

            return new ModelAndView(model, "results.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
