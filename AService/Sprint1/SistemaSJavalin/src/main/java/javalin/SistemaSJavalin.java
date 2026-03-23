package main.java.javalin; 
import java.util.Map;
import org.json.simple.JSONObject;
import io.javalin.Javalin;
import io.javalin.http.Context;
import main.java.domain.IEvaluator;
import unibo.basicomm23.utils.CommUtils;
 

public class SistemaSJavalin implements IEvaluator {

	private Javalin app = null;
	
	@Override
    public float eval(float x) {
    	System.out.println("eval: " + x);
        if (x > 4.0) {
            CommUtils.outmagenta("eval | Simulo ritardo per x=" + x);
            CommUtils.delay(8000);
          }
    	return (float)(Math.sin(x) + Math.cos( Math.sqrt(3)*x));
    }

	protected void setUpServer( ) {
		if (app == null) {
			app = Javalin.create(config -> {
				config.bundledPlugins.enableCors(cors -> {
					cors.addRule(it -> it.anyHost());
				});
			}).start(8080);
		}else {
			CommUtils.outmagenta("Server già avviato. Configuro per CORS ");
    		app.before(ctx -> {
    		    ctx.header("Access-Control-Allow-Origin", "*"); // Permette a TUTTI (o metti il tuo dominio)
    		    ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    		    ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
    		    ctx.header("Access-Control-Allow-Credentials", "true");
    		});

    		// Gestisce le richieste OPTIONS (Preflight)
    		app.options("/*", ctx -> {
    		    ctx.status(204); // No Content - conferma che il server accetta la chiamata
    		});
		}
		
 
	}
	
 /* 
  * -------------------------------------------------
  * PARTE HTTP  - Stile funzionale
  * -------------------------------------------------
  */
 
    protected float readInputHTTP(JSONObject b) throws NumberFormatException{
        String xs = ""+b.get("x");
        float x  = Float.parseFloat(xs);
        CommUtils.outblue("x="+x  );
        return x;
    }
    
    protected String handlerHTTP(Context ctx) {
   	 //See https://javalin.io/documentation#context
        try {
        	JSONObject m  = CommUtils.parseForJson(ctx.body());
        	CommUtils.outblue("m="+m  );
        	float x      = readInputHTTP(m);
            float result = eval(x);                
            return "risultato HTTP="+result;     
        } catch (NumberFormatException e) {
           return "Errore HTTP: numero non valido";
        }
    }
    
    protected void setWorkHTTP( ) {
    	setUpServer();
    	app.get("/", ctx -> ctx.result("Hello World via HTTP/1.1")); 
        
    	app.get("/eval/{n}", ctx -> {
    		System.out.println("Ricevuta richiesta per il valore: " + ctx.pathParam("n"));
    		//ctx.result("Valutato: " + 20);
    		String numeroStr = ctx.pathParam("n");
    		float valore    = Float.parseFloat(numeroStr);
    		float r         = eval( valore );
    		System.out.println("Risultato: " + r);
    		//ctx.json(Map.of("fullUrl", ctx.fullUrl(), "result", r));
    		ctx.result("Valutato: " + r);
    	});
    	
//        app.get("/eval", ctx -> {
//	          double x = Double.parseDouble(ctx.queryParam("x"));
//              double r = eval( x );
//              ctx.json(Map.of("fullUrl", ctx.fullUrl(), "result", r));
//	     });
              
        app.post("/evaluate", ctx -> {  //Warning: check CORS
    	  		 String result = handlerHTTP( ctx );
       	         //Invia risposta in JSON
                 ctx.json(Map.of("fullUrl", ctx.fullUrl(), "body", ctx.body(), "result", result));
       });
    }
   
    public void configureTheSystem() {   
    	setWorkHTTP();
        CommUtils.outblue("server avviato su HTTP");
    }

	
    public static void main(String[] args) {
    	new SistemaSJavalin().configureTheSystem();
     }
}//SistemaSJavalin


 
