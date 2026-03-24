package main.java.protoactor26;

public class MainProtoactors {

    public static void main(String[] args) {
    	ProtoActorContext26 ctx  = new ProtoActorContext26("ctx1234",1234);
    	AbstractProtoactor26 pa = new ProtoactorEval("paEval", ctx);
    	ctx.register(pa);
     }
}
