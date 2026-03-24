package main.java.protoactor26;

import main.java.domain.IEvaluator;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class ProtoactorEval extends AbstractProtoactor26 implements IEvaluator {
    /*
     * ------------------------------------
     * Specifica dei messaggi
     * ------------------------------------
     */
    private IApplMessage m = CommUtils.buildDispatch(name, "do", "2.0", "pa2");
    private IApplMessage req = CommUtils.buildRequest(name, "eval", "0.0", "pa2");
    private IApplMessage ev = CommUtils.buildEvent(name, "info", "pa1_working");
    private IApplMessage autoreq = CommUtils.buildRequest(name, "eval", "4.0", "pa1");

    public ProtoactorEval(String name, ProtoActorContext26 ctx) {
        super(name, ctx);
    }


    /*
     * ------------------------------------
     * Gestione dei messaggi
     * ------------------------------------
     */

    @Override
    protected void elabDispatch(IApplMessage m) {
        CommUtils.outblue(name + " | elabDispatch:" + m);
    }

    @Override
    protected IApplMessage elabRequest(IApplMessage req) {
        CommUtils.outblue(name + " | elabRequest:" + req);
        if (req.msgId().equals("eval")) {
            float x = Float.parseFloat(req.msgContent());
            float result = eval(x);
            IApplMessage replyMsg = CommUtils.buildReply(name, req.msgId(), "" + result, req.msgSender());
            return replyMsg;
        } else {
            IApplMessage replyMsg = CommUtils.buildReply(name, req.msgId(),
                    "requestUnkown", req.msgSender());
            return replyMsg;
        }
    }

    @Override
    protected void elabReply(IApplMessage r) {
        CommUtils.outblue(name + " | elabReply:" + r);

    }

    @Override
    protected void elabEvent(IApplMessage ev) {
        CommUtils.outcyan(name + " | elabEvent:" + ev);

    }

    
    @Override
    public float eval(float x) {
        CommUtils.outblue(name + " | eval: " + x);
        if (x > 4.0) {
            CommUtils.outmagenta(name + " | Simulo ritardo per x=" + x);
            CommUtils.delay(10000);
        }
        return (float)(Math.sin(x) + Math.cos(Math.sqrt(3) * x));
    }


	@Override
	protected void proactiveJob() {
	}

}
