package main.java.protoactor26;

import java.time.Duration;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import io.javalin.http.Context;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class ProtoActorContext26 {
	private String name;
	private int port;
	private Javalin server = null;
	private Vector<Context> allConns = new Vector<Context>();
	private Map<String, AbstractProtoactor26> protoactors = new ConcurrentHashMap<>();

	public ProtoActorContext26(String name, int port) {
		this.name = name;
		this.port = port;
		configureTheSystem();
	}

	public void register(AbstractProtoactor26 pactor) {
		protoactors.put(pactor.name, pactor);
		CommUtils.outgreen("registered " + pactor.name + " in " + name);
	}

	/*
	 * ----------------------------------------------------
	 * CNFIGURAZIONE DEL SERVER
	 * ----------------------------------------------------
	 */
	protected void configureTheSystem() {
		setUpServer();
		setWorkHTTP();
	}

	protected void setUpServer() {
		if (server == null) {
			server = Javalin.create(config -> {
				config.bundledPlugins.enableCors(cors -> {
					cors.addRule(it -> it.anyHost());
				});
			}).start(8080);
		}else {
			CommUtils.outmagenta("Server già avviato. Configuro per CORS ");
    		server.before(ctx -> {
    		    ctx.header("Access-Control-Allow-Origin", "*"); // Permette a TUTTI (o metti il tuo dominio)
    		    ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    		    ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
    		    ctx.header("Access-Control-Allow-Credentials", "true");
    		});

    		// Gestisce le richieste OPTIONS (Preflight)
    		server.options("/*", ctx -> {
    		    ctx.status(204); // No Content - conferma che il server accetta la chiamata
    		});
		}
	}
    
    protected void setWorkHTTP( ) {
    	setUpServer();        
    	server.get("/eval/{n}", ctx -> {
    		System.out.println("Ricevuta richiesta su /eval");
    		if (allConns.stream().noneMatch(c -> c == ctx)) {
				allConns.add(ctx);
				CommUtils.outgreen("Nuova connessione HTTP: " + ctx);
			}
    		IApplMessage am = CommUtils.buildRequest("httpClient", "eval", ctx.pathParam("n"), "ANY");
			CommUtils.outyellow("			---- ProtoActorContext26 onMessage " + am);
			IApplMessage answer = elabMsg(am, ctx);
			CommUtils.outyellow("			---- ProtoActorContext26 reply " + answer);
			if (answer != null)
				ctx.result(answer.toString());
    	});
    }

	
	/*
	 * Individua il protoactor destinatario e gli fa accodare
	 * il task appropriato di elaborazione-messaggio
	 */
	public IApplMessage elabMsg(IApplMessage am, Context ctx) {
		CommUtils.outyellow(name + " elabMsg : " + am + " ctx null:" + (ctx == null));
		AbstractProtoactor26 pactor;
		String receiver = am.msgReceiver();
		if (receiver.equals("ANY"))
			pactor = protoactors.values().stream().findFirst().orElse(null);
		else
			pactor = protoactors.get(receiver);
		if (!am.isRequest()) return null;
		if (pactor != null) {
			IApplMessage answer = pactor.execMsg(am);
			return answer;
		} else {
			CommUtils.outred(name + " | elabMsg: No protoactor found for receiver " + am.msgReceiver());
			return am;
		}
	}

	/* Utility */

	protected void emitInfo(IApplMessage event) {
		// CommUtils.outcyan(" emitInfo " + s);
		// Invio a tutti i componenti esterni
		allConns.forEach((conn) -> {
			try{
				sendsafe(conn, event.toString());
			}catch(Exception e) {
				CommUtils.outred("Errore nell'invio del messaggio a " + conn + ": " + e.getMessage());
				allConns.remove(conn);
				CommUtils.outmagenta("Connessione rimossa: " + conn);
			}
		});

		// Invio a tutti gli attori locali al contesto
		protoactors.forEach((id, pa) -> {
			pa.execMsg(event);
		});
	}

	protected void sendsafe(Context ctx, String msg) throws Exception {	
		ctx.result(msg);
	}
}
